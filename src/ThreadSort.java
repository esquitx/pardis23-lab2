
import java.util.ArrayList;

public class ThreadSort implements Sorter {

    public final int threads;
    private final int MIN_CHUNK = 16;

    public ThreadSort(int threads) {
        this.threads = threads;
    }

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
            // Call meger sort in the required indexes
            mergeSort(arr, fromIndex, toIndex);
        }

    }

    public void sort(int[] arr) {

        // Decide in which index bracket each thread works on;
        boolean isFair = arr.length % threads == 0; // Check if division is fair ( no unbalanced work load )
        int maxLim = isFair ? arr.length / threads : arr.length / (threads - 1); // if fair,divide evenly. If
                                                                                 // not, use one less thread and
                                                                                 // leave "extra" for last
                                                                                 // thread.
        maxLim = maxLim < threads || maxLim < MIN_CHUNK ? threads : maxLim; // If only one thread needed,
                                                                            // assign all to that thread

        // Keep thread record
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < arr.length; i += maxLim) {
            int beg = i;
            int remain = (arr.length) - i;
            int end = remain < maxLim ? i + (remain - 1) : i + (maxLim - 1);
            final Thread t = new Thread(new SorterThread(arr, beg, end));
            // Add thread to thread register
            threads.add(t);
            // Start thread task
            t.start();
        }

        // Join threads
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Merge results x thread
        for (int i = 0; i < arr.length; i += maxLim) {
            int mid = i == 0 ? 0 : i - 1;
            int remain = (arr.length - i);
            int end = remain < maxLim ? i + (remain - 1) : i + (maxLim - 1);

            merge(arr, 0, mid, end);
        }

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

    public int getThreads() {
        return threads;
    }

}
