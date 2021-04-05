workspace(name = "xformat")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive", "http_file")

# rules_docker section ------------------------------------
http_archive(
    name = "io_bazel_rules_docker",
    sha256 = "1698624e878b0607052ae6131aa216d45ebb63871ec497f26c67455b34119c80",
    strip_prefix = "rules_docker-0.15.0",
    urls = ["https://github.com/bazelbuild/rules_docker/releases/download/v0.15.0/rules_docker-v0.15.0.tar.gz"],
)

load(
    "@io_bazel_rules_docker//repositories:repositories.bzl",
    container_repositories = "repositories",
)

container_repositories()

# This is NOT needed when going through the language lang_image
# "repositories" function(s).
load("@io_bazel_rules_docker//repositories:deps.bzl", container_deps = "deps")

container_deps()

load(
    "@io_bazel_rules_docker//go:image.bzl",
    _go_image_repos = "repositories",
)

_go_image_repos()

load(
    "@io_bazel_rules_docker//container:container.bzl",
    "container_pull",
)

container_pull(
    name = "node10",
    #tag = "10",
    digest = "sha256:59c26cf58705ef08a6b52eaa3664db1820c70530f461bc8e983e4b37c8cc6be0",
    registry = "index.docker.io",
    repository = "library/node",
)

container_pull(
    name = "ubuntu2004",
    digest = "sha256:1d7b639619bdca2d008eca2d5293e3c43ff84cbee597ff76de3b7a7de3e84956",
    registry = "index.docker.io",
    repository = "library/ubuntu",
)

container_pull(
    name = "adoptopenjdk11",
    digest = "sha256:4561d5406dc9ee953f7141b5052be89dcec396e1789ba6aa7666434542ab2e13",
    registry = "index.docker.io",
    repository = "library/adoptopenjdk",
    #tag = "11",
)

# rules_docker section ------------------------------------

load("//thirdparty:formatter.bzl", "load_formatter_dependencies")

load_formatter_dependencies()

# java section ------------------------------------

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "4.0"

RULES_JVM_EXTERNAL_SHA = "31701ad93dbfe544d597dbe62c9a1fdd76d81d8a9150c2bf1ecf928ecdf97169"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@rules_jvm_external//:specs.bzl", "maven")

GUAVA_VERSION = "28.1-jre"

maven_install(
    artifacts = [
        "args4j:args4j:2.33",
        # circular dependencies without these exclusions Using versions from rules_clojure https://github.com/simuons/rules_clojure/blob/master/repositories.bzl
        maven.artifact(
            group = "cljfmt",
            artifact = "cljfmt",
            version = "0.7.0",
            exclusions = [
                "org.clojure:clojure",
            ],
        ),
        maven.artifact(
            group = "org.clojure",
            artifact = "clojure",
            version = "1.10.1",
            exclusions = [
                "org.clojure:spec.alpha",
            ],
        ),
        maven.artifact(
            group = "org.clojure",
            artifact = "spec.alpha",
            version = "0.2.176",
            exclusions = [
                "org.clojure:clojure",
            ],
        ),
        "com.google.flogger:flogger:0.4",
        "com.google.flogger:flogger-system-backend:jar:0.4",
        "com.google.googlejavaformat:google-java-format:1.9",
        "com.google.guava:guava:%s" % GUAVA_VERSION,
        "com.google.truth:truth:1.0.1",
        "junit:junit:4.12",
    ],
    fail_on_missing_checksum = False,
    fetch_sources = True,
    maven_install_json = "@xformat//thirdparty:maven_install.json",
    repositories = [
        "https://repo1.maven.org/maven2",
        "https://maven.google.com",
        "https://repo.clojars.org",
    ],
    version_conflict_policy = "pinned",
)

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()

# java section ------------------------------------
# rules_clojure section ------------------------------------

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

http_archive(
    name = "rules_clojure",
    sha256 = "c841fbf94af331f0f8f02de788ca9981d7c73a10cec798d3be0dd4f79d1d627d",
    strip_prefix = "rules_clojure-c044cb8608a2c3180cbfee89e66bbeb604afb146",
    urls = ["https://github.com/simuons/rules_clojure/archive/c044cb8608a2c3180cbfee89e66bbeb604afb146.tar.gz"],
)

load("@rules_clojure//:repositories.bzl", "rules_clojure_dependencies", "rules_clojure_toolchains")

rules_clojure_dependencies()

rules_clojure_toolchains()

# rules_clojure section ------------------------------------
