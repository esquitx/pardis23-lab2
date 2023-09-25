# Compile this plot using
# 'gnuplot pdc.p'

# Output size
set terminal png size 800,500

# Output filename
set output 'pdc.png'

# Graphics title
set title "MergeSort sort performance on PDC Dardel"

# Set x and y label
set xlabel 'threads'
set ylabel 'speedup'

# Plot the data
# using X:Y means plot using column X and column Y
# Here column 1 is number of threads
# Column 2, 3, 4, 5 & 6 are the speedup
plot "Sequential.dat" using 2:3 with lines title 'Sequential', \
     "ThreadSort.dat" using 2:3 with lines title 'Thread', \
     "ExecutorService.dat" using 2:3 with lines title 'ExecutorService', \
     "ForkJoinPool.dat" using 2:3 with lines title 'ForkJoinPool', \
     "ParallelStream.dat" using 2:3 with lines title 'ParallelStream'
