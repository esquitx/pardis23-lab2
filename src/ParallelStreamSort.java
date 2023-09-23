import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

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
 * 
 * @param <T>
 */

public class ParallelStreamSort implements Sorter {

    public final int threads;
    private final int MIN_THRESHOLD = 8192;

    public ParallelStreamSort(int threads) {
        this.threads = threads;
    }

    private void merge(int arr[], int fromIndex, int mid, int toIndex) {

        // Lentgh of two fragmentss
        int szleft = mid - fromIndex + 1;
        int szright = toIndex - mid;

        // Create temp arrays for left side and rigth side
        int L[] = new int[szleft];
        int R[] = new int[szright];

        // Copy data to temp arrays
        for (int i = 0; i < szleft; i++)
            L[i] = arr[fromIndex + i];
        for (int j = 0; j < szright; j++)
            R[j] = arr[mid + 1 + j];

        // Merge the left/right temp arrays
        int i = 0, j = 0;

        // Copy elements in order
        int k = fromIndex;
        while (i < szleft && j < szright) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of left side
        while (i < szleft) {
            arr[k] = L[i];
            i++;
            k++;
        }

        // Copy remaining elements of right
        while (j < szright) {
            arr[k] = R[j];
            j++;
            k++;
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

    private void parallelMergeSort(int[] arr, int fromIndex, int toIndex, int availableThreads) {

        int fragmentSize = (toIndex - fromIndex) + 1;

        if ((availableThreads <= 1) || (fragmentSize <= MIN_THRESHOLD)) {
            mergeSort(arr, fromIndex, toIndex);
            // TODO : figure out why this is not working
        } else {

            int mid = (fromIndex + toIndex) >>> 1;

            Thread left = new Thread(() -> parallelMergeSort(arr, fromIndex, mid, availableThreads / 2));
            Thread right = new Thread(() -> parallelMergeSort(arr, mid + 1, toIndex, availableThreads / 2));

            List<Thread> taskList = Arrays.asList(left, right);
            taskList.stream().parallel().forEach(task -> task.start());

            merge(arr, fromIndex, mid, toIndex);

        }
    }

    public void sort(int[] arr) {

        ForkJoinPool pool = new ForkJoinPool(threads);
        pool.submit(() -> parallelMergeSort(arr, 0, arr.length - 1, threads));
        pool.shutdown();

    }

    public int getThreads() {
        return threads;
    }

}
