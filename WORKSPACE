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
    digest = "sha256:93fd0705706e5bdda6cc450b384d8d5afb18fecc19e054fe3d7a2c8c2aeb2c83",
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

RULES_JVM_EXTERNAL_TAG = "3.3"

RULES_JVM_EXTERNAL_SHA = "d85951a92c0908c80bd8551002d66cb23c3434409c814179c0ff026b53544dab"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

GUAVA_VERSION = "28.1-jre"

maven_install(
    artifacts = [
        "args4j:args4j:2.33",
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
    ],
    version_conflict_policy = "pinned",
)

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()

# java section ------------------------------------
