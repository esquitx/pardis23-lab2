
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinPoolSort implements Sorter {

    public final int threads;
    private final int MIN_THRESHOLD = 128;

    public ForkJoinPoolSort(int threads) {
        this.threads = threads;
    }

    public class MergeWorker extends RecursiveAction {

        int[] arr;
        int fromIndex;
        int toIndex;
        int remainingThreads;

        public MergeWorker(int[] arr, int fromIndex, int toIndex) {
            this.arr = arr;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;

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

            if (toIndex - fromIndex > 0) {
                int mid = (fromIndex + toIndex) / 2;
                mergeSort(arr, fromIndex, mid);
                mergeSort(arr, mid + 1, toIndex);
                merge(arr, fromIndex, mid, toIndex);
            }
        }

        private void parallelMergeSort(int[] arr, int fromIndex, int toIndex) {

            int fragmentSize = (toIndex - fromIndex);
            if (fragmentSize < MIN_THRESHOLD) {
                mergeSort(arr, fromIndex, toIndex);
            } else {
                int mid = fromIndex + Math.floorDiv(fragmentSize, 2);

                invokeAll(new MergeWorker(arr, fromIndex, mid),
                        new MergeWorker(arr, mid + 1, toIndex));

                merge(arr, fromIndex, mid, toIndex);

            }
        }

        @Override
        protected void compute() {
            parallelMergeSort(arr, fromIndex, toIndex);
        }
    }

    @Override
    public void sort(int[] arr) {

        ForkJoinPool pool = new ForkJoinPool(threads);
        pool.invoke(new MergeWorker(arr, 0, arr.length - 1));
        pool.shutdown();

    }

    @Override
    public int getThreads() {
        return threads;
    }
}
