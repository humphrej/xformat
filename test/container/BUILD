package(default_visibility = ["//visibility:public"])

load("@bazel_tools//tools/build_defs/pkg:pkg.bzl", "pkg_tar")
load("@io_bazel_rules_docker//docker/util:run.bzl", "container_run_and_commit", "container_run_and_extract")
load("@io_bazel_rules_docker//container:container.bzl", "container_image")

container_image(
    name = "xformat_test_image",
    base = "//image:xformat_image",
    entrypoint = [
        "/bin/bash",
        "-e",
        "/test_workload.sh",
    ],
    files = [
        "test_workload.sh",
    ],
    tars = [
        "//test/data:testdata_tar",
    ],
)
