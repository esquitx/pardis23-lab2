public class SequentialSort implements Sorter {

    // Implement QuickSort
    public void sort(int[] arr) {

        int n = arr.length;
        quickSort(arr, 0, n - 1);

    }

    private static void exchange(int[] arr, int i, int j) {

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

    }

    private static int partition(int[] arr, int start, int end) {

        // Positions for start and end
        int l = start;
        int h = end + 1;

        // Get low value
        int value = arr[start];

        // Iterate to sort
        while (true) {

            while (arr[l++] < value)
                if (l == end)
                    break;
            while (value < arr[h--])
                if (h == start)
                    break;
            if (l >= h)
                break;
            exchange(arr, l, h);
        }
        // Insert partition integer
        exchange(arr, start, h);
        return h;
    }

    // Based on QuickSort chapter of Algortihms - Fourth Edition - Sedgewick & Wayne
    void quickSort(int[] arr, int start, int end) {

        if (start < end) {
            int pivot = partition(arr, start, end);
            quickSort(arr, start, pivot - 1);
            quickSort(arr, pivot, end);
        }

    }

    public int getThreads() {
        return 0;
    }
}
