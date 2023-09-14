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

    public int getThreads() {
        return 0;
    }
}
