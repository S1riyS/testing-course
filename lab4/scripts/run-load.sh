#!/bin/sh
set -e

ROOT=$(cd "$(dirname "$0")/.." && pwd)
cd "$ROOT"

rm -f load/result/results.csv
rm -rf load/result/html-report
mkdir -p load/result

# JMETER=jmeter
JMETER=jmeter/apache-jmeter-5.6.3/bin/jmeter

$JMETER -n -t load/test-plan.jmx -l load/result/results.csv -e -o load/result/html-report
