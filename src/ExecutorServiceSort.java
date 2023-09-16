
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        // Sanity check - no need to sort this array :)
        if (arr.length < 1) {
            return;
        }
        // Decide in which index bracket each thread works on;
        boolean isFair = arr.length % MAX_THREADS == 0; // Check if division is fair
        int maxLim = isFair ? arr.length / MAX_THREADS : arr.length / (MAX_THREADS - 1);
        maxLim = maxLim < MAX_THREADS ? MAX_THREADS : maxLim; // If only one thread needed, assign all to that thread

        ArrayList<Future<?>> taskList = new ArrayList<Future<?>>();
        for (int i = 0; i < arr.length; i += maxLim) {
            int beg = i;
            int remain = (arr.length) - i;
            int end = remain < maxLim ? i + (remain - 1) : i + (maxLim - 1);
            Future<?> task = executor.submit(new SorterThread(arr, beg, end));
            taskList.add(task);
        }

        // Wait for tasks to finish
        for (Future<?> task : taskList) {
            try {
                task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
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

        // SECURITY : shutdown executor
        executor.shutdown();
        try {

            if (!executor.awaitTermination(10_000, TimeUnit.MILLISECONDS)) {
                System.out.println("Some tasks are still pending!");
            }
            ;
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    @Override
    public int getThreads() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getThreads'");
    }

}
