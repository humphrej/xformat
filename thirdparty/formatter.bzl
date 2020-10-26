load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

def load_formatter_dependencies():

  http_file(
      name = "buildifier",
      executable = True,
      sha256 = "ec064a5edd2a2a210cf8162305869a27b3ed6c7e50caa70687bc9d72177f61f3",
      urls = ["https://github.com/bazelbuild/buildtools/releases/download/1.0.0/buildifier"],
      downloaded_file_path = "buildifier"
  )

  http_archive(
      name = "clang_format_bin",
      sha256 = "cc99fda45b4c740f35d0a367985a2bf55491065a501e2dd5d1ad3f97dcac89da",
      urls = ["https://releases.llvm.org/6.0.0/clang+llvm-6.0.0-x86_64-linux-gnu-ubuntu-16.04.tar.xz"],
      build_file_content = "exports_files(['clang+llvm-6.0.0-x86_64-linux-gnu-ubuntu-16.04/bin/clang-format'])"
  )

  http_file(
      name = "shfmt_bin",
      executable = True,
      sha256 = "adb6022679f230270c87fd447de0eca08e694189a18bcc9490cd3971917fbcb4",
      urls = ["https://github.com/mvdan/sh/releases/download/v2.6.3/shfmt_v2.6.3_linux_amd64"],
      downloaded_file_path = "shfmt"
  )

  http_file(
      name = "google_java_format_bin",
      executable = True,
      urls = ["https://github.com/google/google-java-format/releases/download/google-java-format-1.9/google-java-format-1.9-all-deps.jar"],
      sha256 = "1d98720a5984de85a822aa32a378eeacd4d17480d31cba6e730caae313466b97",
      downloaded_file_path = "google-java-format.jar"
  )

  http_archive(
      name = "dhall_bin",
      sha256 = "f8312727bbd4af74d183efce2e22f7b7807246a600fcc85600945f4790e4294a",
      urls = ["https://github.com/dhall-lang/dhall-haskell/releases/download/1.31.1/dhall-1.31.1-x86_64-linux.tar.bz2"],
      build_file_content = "exports_files(['bin/dhall'])"
  )




