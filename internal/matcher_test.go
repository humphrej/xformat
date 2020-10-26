package formatter

import (
	"testing"
)

func TestMatchingLogic(t *testing.T) {
	tests := []struct {
		file   string
		format Format
	}{
		{file: "foo.java", format: JAVA},
		{file: "prefixed/foo.java", format: JAVA},
		{file: "foo.dhall", format: DHALL},
		{file: "prefixed/foo.dhall", format: DHALL},
		{file: "BUILD", format: BAZEL},
		{file: "prefixed/BUILD", format: BAZEL},
		{file: "BUILD.bazel", format: BAZEL},
		{file: "prefixed/BUILD.bazel", format: BAZEL},
		{file: "macro.bzl", format: BAZEL},
		{file: "prefixed/macro.bzl", format: BAZEL},
		{file: "foo.cpp", format: CPP},
		{file: "prefixed/foo.cpp", format: CPP},
		{file: "foo.cc", format: CPP},
		{file: "prefixed/foo.cc", format: CPP},
		{file: "foo.ts", format: TYPESCRIPT},
		{file: "prefixed/foo.ts", format: TYPESCRIPT},
		{file: "foo.py", format: PYTHON},
		{file: "prefixed/foo.py", format: PYTHON},
		{file: "foo.go", format: GO},
		{file: "prefixed/foo.go", format: GO},
	}
	for _, test := range tests {
		t.Run(test.file, func(t *testing.T) {

			var _filename string
			var _format Format

			fn := MatchFormatterFn(func(s string, format Format) {
				_filename = s
				_format = format
			})

			fn(test.file, 1, 1)

			if _filename != test.file || _format != test.format {
				t.Fatalf("Expected %v but received %v", test.format, _format)
			}
		})
	}
}
