class SequentialSort implements Sorter {

    // Implement QuickSort or MergeSort
    public void sort(int[] arr) {
        int n = arr.length;
        // QUICK_SORT
        // quickSort(arr, 0, n - 1);
        // MERGE_SORT
        mergeSort(arr, 0, n - 1);

    }

    private static void exchange(int[] arr, int i, int j) {

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

    }

    private static int partition(int[] arr, int start, int end) {

        // Select pivot
        int pivot = arr[end];

        // Correct starting position
        int k = (start - 1);

        for (int i = start; i <= end - 1; i++) {

            if (arr[i] < pivot) {

                k++;
                exchange(arr, k, i);
            }
        }
        exchange(arr, k + 1, end);
        return (k + 1);
    }

    // Based on QuickSort chapter of Algortihms - Fourth Edition - Sedgewick & Wayne
    void quickSort(int[] arr, int start, int end) {

        if (start < end) {

            int pi = partition(arr, start, end);
            quickSort(arr, start, pi - 1);
            quickSort(arr, pi + 1, end);
        }

    }

    void merge(int[] arr, int start, int mid, int end) {
        int[] temp = new int[(end - start) + 1];

        // Initialize swapping indexes
        int i = start;
        int j = mid + 1;
        int k = 0;

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

        // Add remaining elements to temp array from first half that are left over
        while (i <= mid) {
            temp[k] = arr[i];
            i += 1;
            k += 1;
        }

        // Add remaining elements to temp array from second half that are left over
        while (j <= end) {
            temp[k] = arr[j];
            j += 1;
            k += 1;
        }

        for (i = start, k = 0; i <= end; i++, k++) {
            arr[i] = temp[k];
        }
    }

    // Based on MergeSort chapter of Algorithms - Fourth Edition - Sedgewick & Wayne
    void mergeSort(int[] arr, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(arr, start, mid);
            mergeSort(arr, mid + 1, end);
            merge(arr, start, mid, end);
        }
    }

    public int getThreads() {
        return 0;
    }
}
