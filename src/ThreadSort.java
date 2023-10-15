
public class ThreadSort implements Sorter {

    private final int threads;
    private final int MIN_THRESHOLD = 8192;

    public ThreadSort(int threads) {
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

            left.start();
            right.start();

            try {
                left.join();
                right.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            merge(arr, fromIndex, mid, toIndex);

        }
    }

    @Override
    public void sort(int[] arr) {
        parallelMergeSort(arr, 0, arr.length - 1, threads);
    }

    public int getThreads() {
        return threads;
    }

}
