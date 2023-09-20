
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceSort implements Sorter {

    private final int threads;
    private final int MIN_THRESHOLD = 8192;

    public ExecutorServiceSort(int threads) {
        this.threads = threads;
    }

    private void merge(int[] arr, int start, int mid, int end) {

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
    private void mergeSort(int[] arr, int fromIndex, int toIndex) {

        if (fromIndex < toIndex) {
            int mid = (fromIndex + toIndex) >>> 1;
            mergeSort(arr, fromIndex, mid);
            mergeSort(arr, mid + 1, toIndex);
            merge(arr, fromIndex, mid, toIndex);
        }

    }

    private void parallelMergeSort(ExecutorService executor, int[] arr, int fromIndex, int toIndex) {

        int fragmentSize = (toIndex - fromIndex);
        int availableThreads = (threads - Thread.activeCount());
        if ((fragmentSize <= MIN_THRESHOLD) || availableThreads <= 1) {
            mergeSort(arr, fromIndex, toIndex);
        } else {

            int mid = (fromIndex + toIndex) >>> 1;

            Future<?> left = executor.submit(() -> parallelMergeSort(executor, arr, fromIndex, mid));
            Future<?> right = executor.submit(() -> parallelMergeSort(executor, arr, mid + 1, toIndex));

            try {
                left.get();
                right.get();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                merge(arr, fromIndex, mid, toIndex);
            }

        }
    }

    @Override
    public void sort(int[] arr) {

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        parallelMergeSort(executor, arr, 0, arr.length - 1);
        executor.shutdown();

    }

    @Override
    public int getThreads() {
        return threads;
    }

}