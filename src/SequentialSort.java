class SequentialSort implements Sorter {

    // Based on MergeSort chapter of Algorithms - Fourth Edition - Sedgewick & Wayne
    void mergeSort(int[] arr, int fromIndex, int toIndex) {
        if (fromIndex < toIndex) {
            int mid = (fromIndex + toIndex) >>> 1;
            mergeSort(arr, fromIndex, mid);
            mergeSort(arr, mid + 1, toIndex);
            Auxiliary.merge(arr, fromIndex, mid, toIndex);
        }
    }

    // Implement MergeSort
    @Override
    public void sort(int[] arr) {
        // MERGE_SORT
        mergeSort(arr, 0, arr.length - 1);
    }

    @Override
    public int getThreads() {
        return 0;
    }
}
