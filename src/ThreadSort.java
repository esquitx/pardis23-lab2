
public class ThreadSort implements Sorter {

    public final int threads;

    private final int MIN_THRESHOLD = 8192;

    public ThreadSort(int threads) {
        this.threads = threads;
    }

    private class SorterThread extends Thread {

        int[] arr;
        int fromIndex, toIndex;
        int remainingThreads;

        SorterThread(int[] arr, int fromIndex, int toIndex, int remainingThreads) {
            this.arr = arr;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            this.remainingThreads = remainingThreads;

        }

        @Override
        public void run() {
            parallelMergeSort(arr, fromIndex, toIndex, remainingThreads);
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
    void mergeSort(int[] arr, int fromIndex, int toIndex) {

        if (toIndex - fromIndex > 0) {
            int mid = (fromIndex + toIndex) >>> 1;
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

            SorterThread left = new SorterThread(arr, fromIndex, mid, remainingThreads / 2);
            SorterThread right = new SorterThread(arr, mid + 1, toIndex, remainingThreads / 2);

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
