
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinPoolSort implements Sorter {

    public final int threads;
    private final int MIN_THRESHOLD = 8192;

    public ForkJoinPoolSort(int threads) {
        this.threads = threads;
    }

    class MergeSortTask extends RecursiveAction {

        int[] arr;
        int fromIndex;
        int toIndex;
        int availableThreads;

        public MergeSortTask(int[] arr, int fromIndex, int toIndex, int availableThreads) {
            this.arr = arr;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            this.availableThreads = availableThreads;
        }

        @Override
        protected void compute() {

            int fragmentSize = (toIndex - fromIndex) + 1;
            if ((fragmentSize <= MIN_THRESHOLD) || (availableThreads <= 1)) {
                mergeSort(arr, fromIndex, toIndex);
            } else {
                int mid = (fromIndex + toIndex) >>> 1;
                MergeSortTask leftTask = new MergeSortTask(arr, fromIndex, mid, availableThreads / 2);
                MergeSortTask rightTask = new MergeSortTask(arr, mid + 1, toIndex, availableThreads / 2);

                invokeAll(leftTask, rightTask);

                Auxiliary.merge(arr, fromIndex, mid, toIndex);
            }
        }
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

    @Override
    public void sort(int[] arr) {
        ForkJoinPool pool = new ForkJoinPool(threads);
        pool.invoke(new MergeSortTask(arr, 0, arr.length - 1, threads));
        pool.shutdown();
    }

    @Override
    public int getThreads() {
        return threads;
    }

}
