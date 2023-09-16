
import java.util.concurrent.*;

public class ExecutorServiceSort implements Sorter {

    int MAX_THREADS = 4;

    private class SorterThread implements Runnable {

        int[] arr;
        final int fromIndex, toIndex;

        SorterThread(int[] arr, int fromIndex, int toIndex) {
            this.arr = arr;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        public void run() {
            // Call merge sort in the required indexes
            mergeSort(arr, fromIndex, toIndex);
        }

    }

    @Override
    public void sort(int[] arr) {

        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

        // Decide in which index bracket each thread works on;
        boolean isFair = arr.length % MAX_THREADS == 0; // Check if division is fair
        int maxLim = isFair ? arr.length / MAX_THREADS : arr.length / (MAX_THREADS - 1);
        maxLim = maxLim < MAX_THREADS ? MAX_THREADS : maxLim; // If only one thread needed, assign all to that thread

        for (int i = 0; i < arr.length; i += maxLim) {
            int beg = i;
            int remain = (arr.length) - i;
            int end = remain < maxLim ? i + (remain - 1) : i + (maxLim - 1);
            executor.submit(new SorterThread(arr, beg, end));
        }

        executor.shutdown();

    }

    public static void merge(int[] arr, int start, int mid, int end) {
        int[] temp = new int[(end - start) + 1];

        // Initialize swapping indexes
        int i = start;
        int j = mid + 1;
        int k = 0;

        //
        while (i <= mid && j <= end) {
            if (arr[i] <= arr[j]) {
                temp[k] = arr[i];
                i += 1;
            } else {
                temp[k] = arr[j];
                j += 1;
            }
            k += 1;
        }

        // Add "forgotten" elements from first
        while (i <= mid) {
            temp[k] = arr[i];
            i += 1;
            k += 1;
        }

        // Add "forgotten" elements to temp array from second half
        while (j <= end) {
            temp[k] = arr[j];
            j += 1;
            k += 1;
        }

        // Copy from temp to orginal array
        for (i = start, k = 0; i <= end; i++, k++) {
            arr[i] = temp[k];
        }
    }

    // Based on MergeSort chapter of Algorithms - Fourth Edition - Sedgewick & Wayne
    void mergeSort(int[] arr, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(arr, start, mid);
            mergeSort(arr, mid + 1, end);
            merge(arr, start, mid, end);
        }
    }

    @Override
    public int getThreads() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getThreads'");
    }

}
