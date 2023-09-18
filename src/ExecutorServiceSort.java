
public class ExecutorServiceSort implements Sorter {

    private final int threads;
    private final int MIN_THRESHOLD = 128;

    public ExecutorServiceSort(int threads) {
        this.threads = threads;
    }

    @Override
    public void sort(int[] arr) {

    }

    @Override
    public int getThreads() {
        return threads;
    }

}
