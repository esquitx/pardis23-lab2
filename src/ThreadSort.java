
import java.util.ArrayList;

public class ThreadSort implements Sorter {

    Auxiliary aux = new Auxiliary();

    private static final int NUM_THREADS = 4;

    private static class SorterThread extends Thread {
        SorterThread(int[] arr, int start, int end) {
            super(() -> {
                SequentialSort sorter = new SequentialSort();
                sorter.mergeSort(arr, start, end);
            });
            this.start();
        }
    }

    @Override
    public void sort(int[] arr) {

        // Decide in which index bracket each thread works on;
        boolean isFair = arr.length % NUM_THREADS == 0; // Check if division is fair
        int maxLim = isFair ? arr.length / NUM_THREADS : arr.length / (NUM_THREADS - 1);
        maxLim = maxLim < NUM_THREADS ? NUM_THREADS : maxLim; // If only one thread needed, assign all to that thread

        // Keep thread record
        ArrayList<SorterThread> threads = new ArrayList();
        for (int i = 0; i < arr.length; i += maxLim) {
            int beg = i;
            int remain = (arr.length) - i;
            int end = remain < maxLim ? i + (remain - 1) : i + (maxLim - 1);
            final SorterThread t = new SorterThread(arr, beg, end);
            // Add thread to thread register
            threads.add(t);
        }

        // Join threads
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < arr.length; i += maxLim) {
            int mid = i == 0 ? 0 : i - 1;
            int remain = (arr.length - i);
            int end = remain < maxLim ? i + (remain - 1) : i + (maxLim - 1);

            aux.merge(arr, 0, mid, end);
        }

    }

    @Override
    public int getThreads() {
        return 0;
    }

}
