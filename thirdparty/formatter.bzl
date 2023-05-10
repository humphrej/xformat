load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

def load_formatter_dependencies():
    http_file(
        name = "buildifier",
        executable = True,
        sha256 = "51bc947dabb7b14ec6fb1224464fbcf7a7cb138f1a10a3b328f00835f72852ce",
        urls = ["https://github.com/bazelbuild/buildtools/releases/download/v6.1.2/buildifier-linux-amd64"],
        downloaded_file_path = "buildifier",
    )

    http_archive(
        name = "clang_format_bin",
        sha256 = "638d32fd0032f99bafaab3bae63a406adb771825a02b6b7da119ee7e71af26c6",
        urls = ["https://github.com/llvm/llvm-project/releases/download/llvmorg-16.0.3/clang+llvm-16.0.3-x86_64-linux-gnu-ubuntu-22.04.tar.xz"],
        build_file_content = "exports_files(['clang+llvm-16.0.3-x86_64-linux-gnu-ubuntu-22.04/bin/clang-format'])",
    )

    http_file(
        name = "shfmt_bin",
        executable = True,
        sha256 = "5741a02a641de7e56b8da170e71a97e58050d66a3cf485fb268d6a5a8bb74afb",
        urls = ["https://github.com/mvdan/sh/releases/download/v3.6.0/shfmt_v3.6.0_linux_amd64"],
        downloaded_file_path = "shfmt",
    )

    http_archive(
        name = "dhall_bin",
        sha256 = "23721443c52417c2116dbebf551c6413f2744078ef645d9334492ca21ac51dde",
        urls = ["https://github.com/dhall-lang/dhall-haskell/releases/download/1.42.0/dhall-1.42.0-x86_64-linux.tar.bz2"],
        build_file_content = "exports_files(['bin/dhall'])",
    )
