
/**
 * Sort using Java's ParallelStreams and Lambda functions.
 *
 * Hints:
 * - Do not take advice from StackOverflow.
 * - Think outside the box.
 * - Stream of threads?
 * - Stream of function invocations?
 *
 * By default, the number of threads in parallel stream is limited by the
 * number of cores in the system. You can limit the number of threads used by
 * parallel streams by wrapping it in a ForkJoinPool.
 * ForkJoinPool myPool = new ForkJoinPool(threads);
 * myPool.submit(() -> "my parallel stream method / function");
 */

import java.util.concurrent.ForkJoinPool;

public class ParallelStreamSort implements Sorter {

    public final int threads;
    private final int MIN_CHUNK = 16;

    public ParallelStreamSort(int threads) {
        this.threads = threads;
    }

    public void sort(int[] arr) {

        // Divide array workload in chunks
        boolean isFair = arr.length % threads == 0;
        int maxLim = isFair ? arr.length / threads : arr.length / (threads - 1);
        maxLim = maxLim < threads || maxLim < MIN_CHUNK ? threads : maxLim;

        ForkJoinPool workerPool = new ForkJoinPool(threads);
        for (int i = 0; i < threads; i++) {
            int beg = i;
            int remain = (arr.length) - i;
            int end = remain < maxLim ? i + (remain - 1) : i + (maxLim - 1);
            workerPool.submit(() -> System.out.println("I am thread " + Thread.currentThread().getName()));
        }

        workerPool.shutdown();

    }

    public int getThreads() {
        return threads;
    }

    public static void main(String[] args) {

        int[] arr = { 1, 2, 3, 4 };

        ParallelStreamSort sorter = new ParallelStreamSort(4);
        sorter.sort(arr);
    }
}
