# XFormat

xformat is an extensible source code formatter indended to use as a stage in a multi-stage container based build.

## Building

Use the supplied Makefile to build / run:

| Target | Description |
|----------------|-------------|
|update          | Update BUILD files with go dependencies (gazelle update)  |
|update-repos    | Update go_dependencies.bzl                                |
|load_image      | Loads xformat into the local docker                       |
|load_test_image | Loads the xformat integration test into the local docker |
|format          | Formats the xformat source code using xformat             |

## Quickstart
* Build xformat and load the container into the local docker: 
```make load_image```
* Format a directory of source code, by mounting the directory onto a docker container.  The xformat_image 
  defaults to /workspace as the mount point.
```
SOURCE_CODE=<insert_path_here>
docker run -ti -v $SOURCE_CODE:/workspace bazel/image:xformat_image
```

## Run integration test

```bash
$ make load_test_image
$ docker run -ti bazel/test/container:xformat_test_image
```





