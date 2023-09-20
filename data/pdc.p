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
set ylabel 'ns'

# Plot the data
# using X:Y means plot using column X and column Y
# Here column 1 is number of threads
# Column 2, 3, 4, 5 & 6 are the speedup
plot "speedup.dat" using 1:2 with lines title 'Sequential', \
     "speedup.dat" using 1:3 with lines title 'Thread', \
     "speedup.dat" using 1:4 with lines title 'ExecutorService', \
     "speedup.dat" using 1:5 with lines title 'ForkJoinPool', \
     "speedup.dat" using 1:6 with lines title 'ParallelStream'
