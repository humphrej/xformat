# XFormat
xformat is an extensible source code formatter indended to use as a stage in a multi-stage container based build.

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
