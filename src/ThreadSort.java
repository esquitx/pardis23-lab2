
import java.util.ArrayList;

public class ThreadSort {

    private static final int NUM_THREADS = 4;

    private static class SorterThread extends Thread {
        SorterThread(int[] arr, int start, int end) {
            super(() -> {
                ThreadSort.quickSort(arr, start, end);
            });
            this.start();
        }
    }

    public static void sort(int[] arr) {

        // Decide in which index bracket each thread works on;
        boolean isFair = arr.length % NUM_THREADS == 0; // Check if division is fair
        int maxLim = isFair ? arr.length / NUM_THREADS : arr.length / (NUM_THREADS - 1);
        maxLim = maxLim < NUM_THREADS ? NUM_THREADS : maxLim; // If only one thread needed, assign all to that thread

        // Keep thread record
        final ArrayList<SorterThread> threads = new ArrayList();
        for (int i = 0; i < arr.length; i += maxLim) {
            int beg = i;
            int remain = (arr.length) - i;
            int end = remain < maxLim ? i + (remain - 1) : i + (maxLim - 1);
            final SorterThread t = new SorterThread(arr, beg, end);
            // Add thread to thread register
            threads.add(t);
        }

        // Join threads
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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

        // Add remaining elements to temp array from first half that are left over
        while (i <= mid) {
            temp[k] = arr[i];
            i += 1;
            k += 1;
        }

        // Add remaining elements to temp array from second half that are left over
        while (j <= end) {
            temp[k] = arr[j];
            j += 1;
            k += 1;
        }

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
        return 0;
    }
}
