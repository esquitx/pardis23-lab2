import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

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

    private List<Integer> mergeFragments(List<Integer> list1, List<Integer> list2) {
        List<Integer> merged = new ArrayList<>();
        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {
            if (list1.get(i) <= list2.get(j)) {
                merged.add(list1.get(i++));
            } else {
                merged.add(list2.get(j++));
            }
        }
        while (i < list1.size()) {
            merged.add(list1.get(i++));
        }
        while (j < list2.size()) {
            merged.add(list2.get(j++));
        }
        return merged;
    }

    // Based on MergeSort chapter of Algorithms - Fourth Edition - Sedgewick & Wayne
    void mergeSort(List<Integer> list) {

    }

    int[] parallelMergeSort(int[] arr, int fromIndex, int toIndex, int remainingThreads) {

        int fragmentSize = arr.length / (threads - 1);

        List<List<Integer>> fragments = new ArrayList<>();

        for (int i = 0; i < arr.length; i += fragmentSize) {
            List<Integer> fragment = new ArrayList<>(
                    Arrays.asList(Arrays.copyOfRange(arr, i, Math.min(arr.length, i + fragmentSize))));
            fragments.add(fragment);

        }

        fragments.parallelStream().forEach(fragment -> Collections.sort(fragment));

        List<Integer> sorted = new ArrayList<>();
        for (List<Integer> fragment : fragments) {
            sorted = mergeFragments(sorted, fragment);
        }
    }

    public void sort(int[] arr) {

    }

    public int getThreads() {
        return threads;
    }

}
