#!/bin/bash

javac -d bin src/MeasureMain.java

sorterName=$1
numThreads="10000000"
warmupRounds="25"
measureRounds="50"
seed="69"

# Executing program ...
java -cp bin MeasureMain $sorterName $numThreads $warmupRounds $measureRoun>

echo "Program execution ended."