
current_dir = $(shell pwd)

update:
	bazel run :gazelle -- update --exclude test/data

update-repos:
	bazel run :gazelle -- update-repos -from_file=$(current_dir)/go.mod -to_macro=go_dependencies.bzl%go_repositories

load_image: 
	bazel run --platforms=@io_bazel_rules_go//go/toolchain:linux_amd64 //thirdparty:xformat_image

load_test_image: 
	bazel run --platforms=@io_bazel_rules_go//go/toolchain:linux_amd64 //test/container:xformat_test_image

fix_test_data:
	docker run -ti -v $(current_dir)/test/data/formatted:/workspace bazel/thirdparty:xformat_image -v 1 -logtostderr /workspace

format:
	docker run -ti -v $(current_dir):/workspace bazel/thirdparty:xformat_image -v 1 -logtostderr -ignore_directories=data /workspace


