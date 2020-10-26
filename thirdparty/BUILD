package(default_visibility = ["//visibility:public"])

load("@bazel_tools//tools/build_defs/pkg:pkg.bzl", "pkg_tar")
load("@io_bazel_rules_docker//docker/util:run.bzl", "container_run_and_extract", "container_run_and_commit")
load("@io_bazel_rules_docker//container:container.bzl", "container_image")

# compile tsfmt in a container
container_run_and_extract(
    name = "tsfmt_compile",
    commands = [
        "npm i -g pkg",
        "git clone https://github.com/vvakame/typescript-formatter.git",
        "cd typescript-formatter",
        "npm i",
        "npm run build",
        "pkg -t node10-linux-x64 -o cli-linux lib/cli.js",
    ],
    extract_file = "/typescript-formatter/cli-linux",
    image = "@node10//image",
)

# build a tar file of the downloaded binaries and bazel-format
FORMATTER_BIN = "/opt/formatters/bin"

pkg_tar(
    name = "binary_formatters_tar",
    files = {
        "@clang_format_bin//:clang+llvm-6.0.0-x86_64-linux-gnu-ubuntu-16.04/bin/clang-format": "%s/clang-format" % FORMATTER_BIN,
        "@shfmt_bin//file": "%s/shfmt" % FORMATTER_BIN,
        ":tsfmt_compile/typescript-formatter/cli-linux": "%s/tsfmt" % FORMATTER_BIN,
        "@buildifier//file": "%s/buildifier" % FORMATTER_BIN,
        "@google_java_format_bin//file": "%s/google-java-format.jar" % FORMATTER_BIN,
        "@dhall_bin//:bin/dhall": "%s/dhall" % FORMATTER_BIN,
        "//cmd:cmd": "%s/xformat" % FORMATTER_BIN,
    },
    mode = "0o555",
    package_dir = "/",
)

load("@bazel_tools//tools/build_defs/pkg:pkg.bzl", "pkg_tar")
load("@io_bazel_rules_docker//docker/package_managers:install_pkgs.bzl", "install_pkgs")
load("@io_bazel_rules_docker//docker/package_managers:download_pkgs.bzl", "download_pkgs")
load("@io_bazel_rules_docker//container:container.bzl", "container_image")

# download ubuntu packages
download_pkgs(
    name = "formatter_pkgs",
    image_tar = "@ubuntu2004//image",
    packages = [
        "openjdk-11-jre",
        "python3",
        "python3-pip",
        "golang",
    ],
)

# install packages into a base ubuntu
install_pkgs(
    name = "formatter_bare_image",
    image_tar = "@ubuntu2004//image",
    installables_tar = ":formatter_pkgs.tar",
    output_image_name = "formatter_bare_image",
)

# now that pip is installed, download yapf
container_run_and_commit(
    name = "yapf_layer",
    commands = [
        "pip3 install yapf",
    ],
    image = ":formatter_bare_image.tar",
)

# build a final container containing everything
container_image(
    name = "xformat_image",
    base = ":yapf_layer_commit.tar",
    tars = [
        ":binary_formatters_tar",
    ],
    env = {"PATH": "%s:/usr/local/bin:/usr/bin" % FORMATTER_BIN},
    cmd = "/workspace",
    entrypoint = ["%s/xformat" % FORMATTER_BIN],
    workdir = FORMATTER_BIN,
)
