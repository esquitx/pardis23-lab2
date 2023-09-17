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
#SBATCH -c 1

echo "Script initiated at `date` on `hostname`"
srun -n 64 java ../bin/MeasureMain.java ExecutorService 4 1000000 10 20 69
echo "Script finished at `date` on `hostname`"