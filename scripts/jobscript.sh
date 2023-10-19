#!/bin/bash
# Set the allocation to be charged for this job
# not required if you have set a default allocation
#SBATCH -A edu23.dd2443
# The name of the script
#SBATCH -J measureLab2
# 10 minutes wall-clock time will be given to this job
#SBATCH -t 01:00:00
# The partition
#SBATCH -p main


echo "Compiling ..."
cd ..
cd src
javac -d ../bin MeasureMain.java
cd ..

echo "Script initiated at `date` on `hostname`"

# First run sequential
srun java -cp ./bin MeasureMain "Sequential" 1 8192000 50 200 42 >> data/Sequential.dat

# Now run all other threads
sorters=("ThreadSort" "ExecutorService" "ForkJoinPool" "ParallelStream")

for sorter in "${sorters[@]}"
do

# Get filepath
filepath=data/${sorter}.dat

# Number of threads to test
numThreads=(2 4 8 16 32 48 96)

# measurement loop
for threadCount in "${numThreads[@]}"
do
srun java -cp ./bin MeasureMain "$sorter" $threadCount 8192000 50 200 42 >> $filepath
done
##

done
echo "Script finished at `date` on `hostname`"