package(default_visibility = ["//visibility:public"])

load("@bazel_tools//tools/build_defs/pkg:pkg.bzl", "pkg_tar")
load("@io_bazel_rules_docker//docker/util:run.bzl", "container_run_and_commit", "container_run_and_extract")
load("@io_bazel_rules_docker//container:container.bzl", "container_image")
load("@io_bazel_rules_docker//docker/package_managers:install_pkgs.bzl", "install_pkgs")
load("@io_bazel_rules_docker//docker/package_managers:download_pkgs.bzl", "download_pkgs")
load("//:variables.bzl", "FORMATTER_BIN")

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

pkg_tar(
    name = "binary_formatters_tar",
    files = {
        "@clang_format_bin//:clang+llvm-6.0.0-x86_64-linux-gnu-ubuntu-16.04/bin/clang-format": "%s/clang-format" % FORMATTER_BIN,
        "@shfmt_bin//file": "%s/shfmt" % FORMATTER_BIN,
        ":tsfmt_compile/typescript-formatter/cli-linux": "%s/tsfmt" % FORMATTER_BIN,
        "@buildifier//file": "%s/buildifier" % FORMATTER_BIN,
        "@dhall_bin//:bin/dhall": "%s/dhall" % FORMATTER_BIN,
    },
    mode = "0o555",
    package_dir = "/",
)

# download ubuntu packages
download_pkgs(
    name = "formatter_pkgs",
    image_tar = "@ubuntu2004//image",
    packages = [
        "golang",
        "openjdk-11-jre",
        "python3",
        "python3-pip",
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
        "groupadd -g 61000 nonroot && useradd -g 61000 -l -m -s /bin/false -u 61000 nonroot",
    ],
    image = ":formatter_bare_image.tar",
)
