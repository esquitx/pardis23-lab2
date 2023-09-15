import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executor;

public class ExecutorServiceSort implements Sorter {

    int MAX_THREADS = 4;

    // ExecutorService executor = Executor.newFixedThreadPool(MAX_THREADS);

    class SorterThread implements Runnable {

        @Override
        public void run() {

        }
    }

    @Override
    public void sort(int[] arr) {

    }

    @Override
    public int getThreads() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getThreads'");
    }

}
