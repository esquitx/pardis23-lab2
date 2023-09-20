import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
 * 
 * @param <T>
 */

public class ParallelStreamSort<T> implements Sorter {

    public final int threads;
    private final int MIN_THRESHOLD = 8192;

    public ParallelStreamSort(int threads) {
        this.threads = threads;
    }

    void 

    public void sort(int[] arr) {

        ForkJoinPool pool = new ForkJoinPool(threads);
        pool.submit(() -> parallelStreamMergeSort(arr, 0, arr.length -1));
        pool.shutdown();
    }

    public int getThreads() {
        return threads;
    }

}
