
current_dir = $(shell pwd)

load_image: 
	bazel run //image:xformat_image -- --norun

load_test_image: 
	bazel run  //test/container:xformat_test_image -- --norun

run_test_image: 
	docker run -ti bazel/test/container:xformat_test_image

test: load_test_image run_test_image

fix_test_data:
	docker run -ti -v $(current_dir)/test/data/formatted:/workspace bazel/image:xformat_image -v 1  /workspace

format:
	docker run -ti -u nonroot -v $(current_dir):/workspace bazel/image:xformat_image -v 1 --ignore_directories=data,.git,.ijwb /workspace

repin:
	bazel run @unpinned_maven//:pin

