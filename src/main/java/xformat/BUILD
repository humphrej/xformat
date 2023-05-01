java_library(
    name = "xformat_lib",
    srcs = glob(["*.java"]),
    visibility = [
        "//:__subpackages__",
    ],
    runtime_deps = [
    ],
    deps = [
        "//src/main/clojure:cljfmt_shim_library",
        "@maven//:args4j_args4j",
        "@maven//:com_google_flogger_flogger",
        "@maven//:com_google_googlejavaformat_google_java_format",
        "@maven//:com_google_guava_guava",
    ],
)
