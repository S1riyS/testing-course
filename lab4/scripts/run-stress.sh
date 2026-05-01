#!/bin/sh
set -e

ROOT=$(cd "$(dirname "$0")/.." && pwd)
cd "$ROOT"

rm -f stress/result/results.csv
rm -rf stress/result/html-report
mkdir -p stress/result

# JMETER=jmeter
JMETER=jmeter/apache-jmeter-5.6.3/bin/jmeter

$JMETER -n -t stress/test-plan.jmx -l stress/result/results.csv -e -o stress/result/html-report
