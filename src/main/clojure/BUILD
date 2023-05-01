load("@rules_clojure//:rules.bzl", "clojure_library")

clojure_library(
    name = "cljfmt_shim_library",
    srcs = ["cljfmt_shim.clj"],
    aot = ["cljfmt-shim"],
    resource_strip_prefix = "src/main/clojure",
    visibility = ["//visibility:public"],
    deps = [
        "@maven//:cljfmt_cljfmt",
        "@maven//:org_clojure_clojure",
        "@maven//:org_clojure_core_specs_alpha",
        "@maven//:org_clojure_spec_alpha",
    ],
)
