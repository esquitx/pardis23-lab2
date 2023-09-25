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
filepath=data/${sorter}.dat


echo "Script initiated at `date` on `hostname`"

counter=1

while [ $counter -lt 165 ]
do
srun java -cp ./bin MeasureMain "$sorter" $counter 1024000 40 80 42 >> $filepath
((counter*=2))
done
echo "Script finished at `date` on `hostname`"