
/**
 * Sort using Java's ParallelStreams and Lambda functions.
 *
 * Hints:
 * - Do not take advice from StackOverflow.
 * - Think outside the box.
 * - Stream of threads?
 * - Stream of function invocations?
 *
 * By default, the number of threads in parallel stream is limited by the
 * number of cores in the system. You can limit the number of threads used by
 * parallel streams by wrapping it in a ForkJoinPool.
 * ForkJoinPool myPool = new ForkJoinPool(threads);
 * myPool.submit(() -> "my parallel stream method / function");
 */

public class ParallelStreamSort implements Sorter {

    public final int threads;
    private final int MIN_THRESHOLD = 8192;

    public ParallelStreamSort(int threads) {
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
    void mergeSort(int[] arr, int fromIndex, int toIndex) {

        if (toIndex - fromIndex > 0) {
            int mid = (fromIndex + toIndex) / 2;
            mergeSort(arr, fromIndex, mid);
            mergeSort(arr, mid + 1, toIndex);
            merge(arr, fromIndex, mid, toIndex);
        }
    }

    void parallelMergeSort(int[] arr, int fromIndex, int toIndex, int remainingThreads) {

        int fragmentSize = (toIndex - fromIndex);
        if ((remainingThreads <= 1) || fragmentSize < MIN_THRESHOLD) {
            mergeSort(arr, fromIndex, toIndex);
        } else {

            int mid = (fromIndex + toIndex) >>> 1;
            // TODO : code in parallel stream

            merge(arr, fromIndex, mid, toIndex);

        }
    }

    public void sort(int[] arr) {

    }

    public int getThreads() {
        return threads;
    }

}
