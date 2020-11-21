load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

def load_formatter_dependencies():
    http_file(
        name = "buildifier",
        executable = True,
        sha256 = "f9a9c082b8190b9260fce2986aeba02a25d41c00178855a1425e1ce6f1169843",
        urls = ["https://github.com/bazelbuild/buildtools/releases/download/3.5.0/buildifier"],
        downloaded_file_path = "buildifier",
    )

    http_archive(
        name = "clang_format_bin",
        sha256 = "829f5fb0ebda1d8716464394f97d5475d465ddc7bea2879c0601316b611ff6db",
        urls = ["https://github.com/llvm/llvm-project/releases/download/llvmorg-11.0.0/clang+llvm-11.0.0-x86_64-linux-gnu-ubuntu-20.04.tar.xz"],
        build_file_content = "exports_files(['clang+llvm-11.0.0-x86_64-linux-gnu-ubuntu-20.04/bin/clang-format'])",
    )

    http_file(
        name = "shfmt_bin",
        executable = True,
        sha256 = "84cf01d220bfb606d52af983e1afdbf6e25aff4a8aff6d5cf053dad29a1740f1",
        urls = ["https://github.com/mvdan/sh/releases/download/v3.2.0/shfmt_v3.2.0_linux_amd64"],
        downloaded_file_path = "shfmt",
    )

    http_archive(
        name = "dhall_bin",
        sha256 = "9fc42f2f537bf62fd5e3afc959ec176936be448afe1ed3d3121c0d3a45b730b2",
        urls = ["https://github.com/dhall-lang/dhall-haskell/releases/download/1.36.0/dhall-1.36.0-x86_64-linux.tar.bz2"],
        build_file_content = "exports_files(['bin/dhall'])",
    )

    http_file(
        name = "jq_bin",
        executable = True,
        sha256 = "af986793a515d500ab2d35f8d2aecd656e764504b789b66d7e1a0b727a124c44",
        urls = ["https://github.com/stedolan/jq/releases/download/jq-1.6/jq-linux64"],
        downloaded_file_path = "jq",
    )

    http_file(
        name = "yq_bin",
        executable = True,
        sha256 = "93fa4bddd27cf7c21b23110039cf7def1069ebbc8895bb9d2b7e1a639127e881",
        urls = ["https://github.com/mikefarah/yq/releases/download/4.0.0-alpha1/yq_linux_amd64"],
        downloaded_file_path = "yq",
    )
