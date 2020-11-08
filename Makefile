
current_dir = $(shell pwd)

update:
	bazel run :gazelle -- update --exclude test/data

update-repos:
	bazel run :gazelle -- update-repos -from_file=$(current_dir)/go.mod -to_macro=go_dependencies.bzl%go_repositories

load_image: 
	bazel run //image:xformat_image

load_test_image: 
	bazel run  //test/container:xformat_test_image

fix_test_data:
	docker run -ti -v $(current_dir)/test/data/formatted:/workspace bazel/image:xformat_image -v 1  /workspace

format:
	docker run -ti -v $(current_dir):/workspace bazel/image:xformat_image -v 1 --ignore_directories=data,.git,.ijwb /workspace


repin:
	bazel run @unpinned_maven//:pin

