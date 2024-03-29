package(default_visibility = ["//visibility:public"])

load("@bazel_tools//tools/build_defs/pkg:pkg.bzl", "pkg_tar")
load("@io_bazel_rules_docker//docker/util:run.bzl", "container_run_and_commit", "container_run_and_extract")
load("@io_bazel_rules_docker//container:container.bzl", "container_image")
load("@io_bazel_rules_docker//java:image.bzl", "java_image")
load("//:variables.bzl", "FORMATTER_BIN")

container_image(
    name = "base_image_preparation",
    base = "//thirdparty:yapf_layer_commit.tar",
    env = {"PATH": "%s:/usr/local/bin:/usr/bin" % FORMATTER_BIN},
    layers = [
        "//thirdparty:shrunken_jdk",
    ],
    tars = [
        "//thirdparty:binary_formatters_tar",
    ],
)

java_image(
    name = "xformat_image",
    base = ":base_image_preparation",
    jvm_flags = [
        "--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED",
        "--add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED",
        "--add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED",
        "--add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED",
        "--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
        "--add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED",
    ],
    main_class = "xformat.XFormatter",
    runtime_deps = [
        "//src/main/java/xformat:xformat_lib",
        "@maven//:com_google_flogger_flogger_system_backend",
    ],
)
