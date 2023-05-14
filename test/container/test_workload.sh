#/bin/bash

echo Running xformat...

JVMARGS="--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED \
--add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED \
--add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED \
--add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED \
--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED \
--add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED"

/usr/bin/java $JVMARGS -cp $(</app/xformat/image/xformat_image.classpath) xformat.XFormatter /testdata/unformatted

echo Diff is...
diff -Br /testdata/unformatted /testdata/formatted
ret=$?

if [ "$ret" -eq 0 ]; then
  echo PASS
else
  echo FAIL
fi
