#!/bin/bash
# Set the allocation to be charged for this job
# not required if you have set a default allocation
#SBATCH -A edu23.dd2443
# The name of the script is myjob
#SBATCH -J measureJob
# 10 minutes wall-clock time will be given to this job
#SBATCH -t 00:10:00
# The partition
#SBATCH -p shared
# The number of tasks requested
#SBATCH -n 64
# The number of cores per task
#SBATCH -c 8

echo "Compiling ..."
cd ..
cd src
javac -d ../bin MeasureMain.java
cd ..

sorter=$1
filepath=data/${sorter}.txt


echo "Script initiated at `date` on `hostname`"
srun -n 1 java -cp ./bin MeasureMain "$sorter" 1 10000000 10 25 42 >> $filepath
srun -n 1 java -cp ./bin MeasureMain "$sorter" 2 10000000 10 25 42 >> $filepath
srun -n 1 java -cp ./bin MeasureMain "$sorter" 4 10000000 10 25 42 >> $filepath
srun -n 1 java -cp ./bin MeasureMain "$sorter" 8 10000000 10 25 42 >> $filepath
echo "Script finished at `date` on `hostname`"