load("@io_bazel_rules_go//go:def.bzl", "go_binary", "go_library")

go_library(
    name = "go_default_library",
    srcs = ["main.go"],
    importpath = "github.com/humphrej/xformat/cmd",
    visibility = ["//visibility:public"],
    deps = [
        "//internal:go_default_library",
        "@com_github_golang_glog//:go_default_library",
    ],
)

go_binary(
    name = "cmd",
    embed = [":go_default_library"],
    visibility = ["//visibility:public"],
)
