#!/bin/bash

# First compile program
javac -d bin src/MeasureMain.java


sorterName=$1
numThreads=10000000
warmupRounds=25
measureRounds=50
seed=60

# Executing program ...
java -cp bin src/MeasureMain {$sorterName} {$numThreads} {$warmupRounds} {$measureRounds} {$seed} | sed -n '10p' > "data/{$sorterName}_data.txt"

echo "Program execution ended."
