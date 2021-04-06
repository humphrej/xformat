#/bin/bash

echo Running xformat...
/usr/bin/java -cp $(</app/xformat/image/xformat_image.classpath) xformat.XFormatter /testdata/unformatted

echo Diff is...
diff -Br /testdata/unformatted /testdata/formatted
ret=$?

if [ "$ret" -eq 0 ]; then
  echo PASS
else
  echo FAIL
fi
