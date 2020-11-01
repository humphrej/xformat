#/bin/bash

echo Running xformat...
xformat /testdata/unformatted

echo Diff is...
diff -r /testdata/unformatted /testdata/formatted
ret=$?

if [ "$ret" -eq 0 ]; then
	echo PASS
else
	echo FAIL
fi
