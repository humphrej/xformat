workspace(name = "xformat")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive", "http_file")

# rules_docker section ------------------------------------
http_archive(
    name = "io_bazel_rules_docker",
    sha256 = "59d5b42ac315e7eadffa944e86e90c2990110a1c8075f1cd145f487e999d22b3",
    strip_prefix = "rules_docker-0.17.0",
    urls = ["https://github.com/bazelbuild/rules_docker/releases/download/v0.17.0/rules_docker-v0.17.0.tar.gz"],
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
    name = "ubuntu2204",
    digest = "sha256:ca5534a51dd04bbcebe9b23ba05f389466cf0c190f1f8f182d7eea92a9671d00",
    registry = "index.docker.io",
    repository = "library/ubuntu",
)

container_pull(
    name = "eclipse-temurin",
    digest = "sha256:9de4aabba13e1dd532283497f98eff7bc89c2a158075f0021d536058d3f5a082",
    registry = "index.docker.io",
    repository = "library/eclipse-temurin",
    #tag = "11",
)

# rules_docker section ------------------------------------

load("//thirdparty:formatter.bzl", "load_formatter_dependencies")

load_formatter_dependencies()

# java section ------------------------------------

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "4.5"

RULES_JVM_EXTERNAL_SHA = "b17d7388feb9bfa7f2fa09031b32707df529f26c91ab9e5d909eb1676badd9a6"

#RULES_JVM_EXTERNAL_TAG = "4.0"
#RULES_JVM_EXTERNAL_SHA = "31701ad93dbfe544d597dbe62c9a1fdd76d81d8a9150c2bf1ecf928ecdf97169"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@rules_jvm_external//:specs.bzl", "maven")

GUAVA_VERSION = "31.1-jre"

maven_install(
    artifacts = [
        "args4j:args4j:2.33",
        # circular dependencies without these exclusions Using versions from rules_clojure https://github.com/simuons/rules_clojure/blob/master/repositories.bzl
        maven.artifact(
            artifact = "clojure",
            exclusions = [
                "org.clojure:spec.alpha",
                "org.clojure:core.specs.alpha",
            ],
            group = "org.clojure",
            version = "1.11.1",
        ),
        maven.artifact(
            artifact = "spec.alpha",
            exclusions = ["org.clojure:clojure"],
            group = "org.clojure",
            version = "0.3.218",
        ),
        maven.artifact(
            artifact = "core.specs.alpha",
            exclusions = [
                "org.clojure:clojure",
                "org.clojure:spec.alpha",
            ],
            group = "org.clojure",
            version = "0.2.62",
        ),
        maven.artifact(
            artifact = "cljfmt",
            exclusions = [
                "org.clojure:clojure",
                "org.clojure:spec.alpha",
                "org.clojure:core.specs.alpha",
            ],
            group = "cljfmt",
            version = "0.9.2",
        ),
        "com.google.flogger:flogger:0.4",
        "com.google.flogger:flogger-system-backend:jar:0.4",
        "com.google.googlejavaformat:google-java-format:1.17.0",
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

RULES_CLOJURE_SHA = "bad7ead30e3426425d4ae44d974a2bfa868d61e8"

http_archive(
    name = "rules_clojure",
    strip_prefix = "rules_clojure-%s" % RULES_CLOJURE_SHA,
    url = "https://github.com/griffinbank/rules_clojure/archive/%s.zip" % RULES_CLOJURE_SHA,
)

load("@rules_clojure//:repositories.bzl", "rules_clojure_deps")

rules_clojure_deps()

load("@rules_clojure//:setup.bzl", "rules_clojure_setup")

rules_clojure_setup()

# rules_clojure section ------------------------------------
