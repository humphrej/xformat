#!/bin/bash
# Required because yq has no overwrite flag

if [ $# -ne 1 ]; then
  echo Need file as parameter 1
  exit 1
fi

file=$1
tmpfile=${file}.tmp

yq eval --indent 2 -M . $file >$tmpfile
mv $tmpfile $file
