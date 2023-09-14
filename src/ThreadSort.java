public class ThreadSort {

    private static final int NUM_THREADS = 4;

    private static class SorterThread extends Thread {
        SorterThread(int[] arr, int start, int end) {
            super(() -> {
                ThreadSort.quickSort(arr, start, end); });
            });this.start();
    }
}

}
