load("@bazel_tools//tools/build_defs/pkg:pkg.bzl", "pkg_tar")
load("@io_bazel_rules_docker//container:container.bzl", "container_image")

pkg_tar(
    name = "testdata_tar",
    srcs = glob(["**"]),
    package_dir = "/testdata",
    strip_prefix = ".",
    visibility = [
        "//visibility:public",
    ],
    mode = "0666",
    owner = "61000.61000" # nonroot user - see //thirdparty:yapf_layer
)
