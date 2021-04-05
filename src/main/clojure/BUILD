load("@rules_clojure//:rules.bzl", "clojure_java_library", "clojure_library")

clojure_library(
    name = "cljfmt_shim_library",
    srcs = ["cljfmt-shim.clj"],
    deps = [
        "@maven//:cljfmt_cljfmt",
    ],
)

clojure_java_library(
    name = "cljfmt_shim_java_library",
    namespaces = ["cljfmt-shim"],
    visibility = ["//visibility:public"],
    deps = [
        ":cljfmt_shim_library",
    ],
)
