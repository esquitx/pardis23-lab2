
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

        public MergeSortTask(int[] arr, int fromIndex, int toIndex) {
            this.arr = arr;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        protected void compute() {

            int fragmentSize = toIndex - fromIndex;

            if (fragmentSize <= MIN_THRESHOLD) {
                mergeSort(arr, fromIndex, toIndex);
            } else {
                int mid = (fromIndex + toIndex) >>> 1;
                MergeSortTask leftTask = new MergeSortTask(arr, fromIndex, mid);
                MergeSortTask rightTask = new MergeSortTask(arr, mid + 1, toIndex);

                invokeAll(leftTask, rightTask);

                merge(arr, fromIndex, mid, toIndex);
            }
        }
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

    @Override
    public void sort(int[] arr) {

        ForkJoinPool pool = new ForkJoinPool(threads);
        pool.invoke(new MergeSortTask(arr, 0, arr.length - 1));
        pool.shutdown();
    }

    @Override
    public int getThreads() {
        return threads;
    }

}
