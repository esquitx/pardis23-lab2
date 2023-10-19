#!/bin/bash
# Set the allocation to be charged for this job
# not required if you have set a default allocation
#SBATCH -A edu23.dd2443
# The name of the script
#SBATCH -J measureExecutorLab2
# 10 minutes wall-clock time will be given to this job
#SBATCH -t 00:00:15
# The partition
#SBATCH -p shared

echo "Compiling ..."
cd ..
cd src
javac -d ../bin MeasureMain.java
cd ..

echo "Script initiated at `date` on `hostname`"

# Get filepath
filepath=data/ExecutorService.dat

# Number of threads to test
numThreads=(2 4 8 16 32 48 96)

# measurement loop
for threadCount in "${numThreads[@]}"
do
srun java -cp ./bin MeasureMain ExecutorService $threadCount 8192000 50 200 42 >> $filepath
done
##

echo "Script finished at `date` on `hostname`"