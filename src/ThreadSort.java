
public class ThreadSort implements Sorter {

    private final int threads;
    private final int MIN_THRESHOLD = 8192;

    public ThreadSort(int threads) {
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
