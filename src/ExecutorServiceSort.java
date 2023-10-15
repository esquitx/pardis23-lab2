
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceSort implements Sorter {

    private final int threads;
    private final int MIN_THRESHOLD = 8192;

    public ExecutorServiceSort(int threads) {
        this.threads = threads;
    }

    // Based on MergeSort chapter of Algorithms - Fourth Edition - Sedgewick & Wayne
    void mergeSort(int[] arr, int fromIndex, int toIndex) {
        if (fromIndex < toIndex) {
            int mid = (fromIndex + toIndex) >>> 1;
            mergeSort(arr, fromIndex, mid);
            mergeSort(arr, mid + 1, toIndex);
            Auxiliary.merge(arr, fromIndex, mid, toIndex);

        }
    }

    private void parallelMergeSort(int[] arr, int fromIndex, int toIndex, ExecutorService executor,
            int availableThreads) {

        int fragmentSize = (toIndex - fromIndex) + 1;
        if ((availableThreads <= 1) || (fragmentSize <= MIN_THRESHOLD)) {
            mergeSort(arr, fromIndex, toIndex);
        } else {

            int mid = (fromIndex + toIndex) >>> 1;

            Future<?> left = executor
                    .submit(() -> parallelMergeSort(arr, fromIndex, mid, executor, availableThreads / 2));
            Future<?> right = executor
                    .submit(() -> parallelMergeSort(arr, mid + 1, toIndex, executor, availableThreads / 2));

            try {
                left.get();
                right.get();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Auxiliary.merge(arr, fromIndex, mid, toIndex);
            }

        }
    }

    @Override
    public void sort(int[] arr) {

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        Future<?> task = executor.submit(() -> parallelMergeSort(arr, 0, arr.length - 1, executor, threads));

        try {
            task.get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

    }

    @Override
    public int getThreads() {
        return threads;
    }

}