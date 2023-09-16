class SequentialSort implements Sorter {

    Auxiliary aux = new Auxiliary();

    // Implement MergeSort
    @Override
    public void sort(int[] arr) {

        // Record last index of the array
        int lastIndex = arr.length - 1;

        // MERGE_SORT
        mergeSort(arr, 0, lastIndex);

    }

    // Based on MergeSort chapter of Algorithms - Fourth Edition - Sedgewick & Wayne
    void mergeSort(int[] arr, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(arr, start, mid);
            mergeSort(arr, mid + 1, end);
            aux.merge(arr, start, mid, end);
        }
    }

    @Override
    public int getThreads() {
        return 0;
    }
}
