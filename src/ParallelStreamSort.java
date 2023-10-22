import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

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

    // Based on MergeSort chapter of Algorithms - Fourth Edition - Sedgewick & Wayne
    private void mergeSort(int[] arr, int fromIndex, int toIndex) {
        if (fromIndex < toIndex) {
            int mid = (fromIndex + toIndex) >>> 1;
            mergeSort(arr, fromIndex, mid);
            mergeSort(arr, mid + 1, toIndex);
            Auxiliary.merge(arr, fromIndex, mid, toIndex);
        }
    }

    private void parallelMergeSort(int[] arr, int fromIndex, int toIndex, int availableThreads) {

        int fragmentSize = (toIndex - fromIndex) + 1;
        if ((availableThreads <= 1) || (fragmentSize <= MIN_THRESHOLD)) {
            mergeSort(arr, fromIndex, toIndex);
        } else {
            int mid = (fromIndex + toIndex) >>> 1;

            Thread left = new Thread(() -> parallelMergeSort(arr, fromIndex, mid, availableThreads / 2));
            Thread right = new Thread(() -> parallelMergeSort(arr, mid + 1, toIndex, availableThreads / 2));

            List<Thread> taskList = Arrays.asList(left, right);
            taskList.parallelStream().forEach(task -> task.run());

            for (Thread task : taskList) {
                try {
                    task.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Auxiliary.merge(arr, fromIndex, mid, toIndex);

        }
    }

    public void sort(int[] arr) {

        ForkJoinPool pool = new ForkJoinPool(threads);
        Future<?> mainTask = pool.submit(() -> parallelMergeSort(arr, 0, arr.length - 1, threads));

        try {
            mainTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }

    }

    public int getThreads() {
        return threads;
    }

}
