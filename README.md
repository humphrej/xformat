# XFormat
xformat is an extensible source code formatter designed for use as a stage in a multi-stage container
based build.

xformat delegates all formatting to other language dependent formatters which are packaged into the
image at build time. The formatters are:

| **Languages** | **Formatter** |
|-----------|-----------|
| BAZEL | https://github.com/bazelbuild/buildtools/tree/master/buildifier
| CPP, PROTO | http://clang.llvm.org/docs/ClangFormat.html
| DHALL | https://dhall-lang.org/
| GO | https://golang.org/pkg/go/format/
| JAVA | https://github.com/google/google-java-format
| PYTHON | https://github.com/google/yapf 
| SH | https://github.com/mvdan/sh
| TYPESCRIPT | https://github.com/vvakame/typescript-formatter

Based on ideas in [StartupOS](https://github.com/google/startup-os).

## Building
Use the supplied Makefile to build / run:

| Target         | Description |
|----------------|----------------------------------------------------------|
|load_image      | Loads xformat into the local docker                      |
|load_test_image | Loads the xformat integration test into the local docker |
|format          | Formats the xformat source code using xformat            |
|test            | loads and runs the test image                            |

## Quickstart
* Build xformat and load the container into the local docker: 
```make load_image```
* Format a directory of source code, by mounting the directory onto a docker container. 
```
SOURCE_CODE=<insert_path_here>
docker run -ti -v $SOURCE_CODE:/workspace bazel/image:xformat_image -v 1 /workspace
```


## Command Options
```
<command> [options...] arguments...
 VAL                                    : Directory roots where the formatting
                                          should begin
 --format [JAVA | BAZEL | DHALL | GO |  : Formatters to include (default:
 SH | CPP | TYPESCRIPT | PYTHON |         [JAVA,BAZEL,DHALL,GO,SH,CPP,TYPESCRIPT
 PROTO]                                   ,PYTHON,PROTO])
 --ignore_directories d1,d2             : Ignored directories, split by commas
                                          (default: )
 -v 0,1                                 : Log Level (default: 0)

  Example: <command> --format [JAVA | BAZEL | DHALL | GO | SH | CPP | TYPESCRIPT | PYTHON | PROTO] --ignore_directories d1,d2 -v 0,1

```

## GCP Cloud Builder usage
To configure Cloud Builder to fail a build if the source is incorrectly formatted:
1. Push xformat to your GCP Container Registry
1. Add to your cloudbuild.yaml
```yaml
steps:
#...
# This will run xformat. GCP mounts the source at /workspace.
- name : gcr.io/<yourproject>/xformat:latest  
  args: [ '-v', '1', '/workspace' ]
# This will fail the build if there are local changes
- name: gcr.io/cloud-builders/git
  args: ['diff', '--quiet']
```
