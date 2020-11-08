java_library(
    name = "xformat_tests_lib",
    srcs = glob(["*.java"]),
    deps = [
        "//src/main/java/xformat:xformat_lib",
        "@maven//:com_google_guava_guava",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
    ],
)

java_test(
    name = "xformat_tests",
    size = "small",
    test_class = "xformat.XFormatSuite",
    runtime_deps = [
        "xformat_tests_lib",
        #        "@maven//:ch_qos_logback_logback_classic",
        #        "@maven//:com_google_flogger_flogger_slf4j_backend",
        "@maven//:com_google_flogger_flogger_system_backend",
        #        "@maven//:org_slf4j_slf4j_api",
    ],
)
