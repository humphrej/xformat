#!/bin/bash
# Required because jq has no overwrite flag

if [ $# -ne 1 ]; then
  echo Need file as parameter 1
  exit 1
fi

file=$1
tmpfile=${file}.tmp

jq --indent 2 -M . $file >$tmpfile
mv $tmpfile $file
