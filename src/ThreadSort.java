
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
            int pivot = i == 0 ? 0 : i - 1;
            int remain = (arr.length - i);
            int end = remain < maxLim ? i + (remain - 1) : i + (maxLim - 1);

        }

    }

    private static void exchange(int[] arr, int i, int j) {

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

    }

    private static int partition(int[] arr, int start, int end) {

        // Select pivot
        int pivot = arr[end];

        // Correct starting position
        int k = (start - 1);

        for (int i = start; i <= end - 1; i++) {

            if (arr[i] < pivot) {

                k++;
                exchange(arr, k, i);
            }
        }
        exchange(arr, k + 1, end);
        return (k + 1);
    }

    // Based on QuickSort chapter of Algortihms - Fourth Edition - Sedgewick & Wayne
    public static void quickSort(int[] arr, int start, int end) {

        if (start < end) {

            int pi = partition(arr, start, end);
            quickSort(arr, start, pi - 1);
            quickSort(arr, pi + 1, end);
        }

    }
}
