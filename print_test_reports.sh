#!/usr/bin/env sh
echo "Current directory is $(pwd)"
echo "\n=== SUREFIRE REPORTS ===\n"

echo build/reports/tests/test/index.html
cat build/reports/tests/test/index.html
for F in build/reports/tests/test/classes/*.html
do
    echo $F
    cat $F
    echo
done