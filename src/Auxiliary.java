
import java.util.Arrays;
import java.util.Random;

class Auxiliary {

    Random random = new Random();

    public static void printArray(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
    }

    void measure() {
    }

    void sequentialChecker() {

        int n = random.nextInt(100) + 1;

        // Generate a random array;
        int[] arr = new int[n];
        // Populate it
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(100) + 1;
        }
        // Sort it
        int[] test = Arrays.copyOf(arr, arr.length);
        Arrays.sort(test);

        // Sort same array with sequential sort
        SequentialSort sorter = new SequentialSort();
        sorter.sort(arr);

        if (Arrays.equals(test, arr))
            System.out.println("Test passed!");
        else
            System.out.println("Test failed :( ");

    }

    void executorChecker(int[] arr) {

    }

    void threadChecker() {

        int n = random.nextInt(100) + 1;

        // Generate a random array;
        int[] arr = new int[n];
        // Populate it
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(100) + 1;
        }
        // Sort it
        int[] test = Arrays.copyOf(arr, arr.length);
        Arrays.sort(test);

        // Start test

        System.out.println();
        System.out.println("Start time : " + System.nanoTime());

        ThreadSort sorter = new ThreadSort();
        sorter.sort(arr);

        System.out.println("End time: " + System.nanoTime());

        printArray(arr);

        if (Arrays.equals(test, arr))
            System.out.println("Test passed!");
        else
            System.out.println("Test failed :( ");

    }

    void executorChecker() {

        int n = random.nextInt(100) + 1;

        // Generate a random array;
        int[] arr = new int[n];
        // Populate it
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(100) + 1;
        }
        // Sort it
        int[] test = Arrays.copyOf(arr, arr.length);
        Arrays.sort(test);

        // Start test

        System.out.println();
        System.out.println("Start time : " + System.nanoTime());

        ExecutorServiceSort sorter = new ExecutorServiceSort();
        sorter.sort(arr);

        System.out.println("End time: " + System.nanoTime());

        printArray(arr);

        if (Arrays.equals(test, arr))
            System.out.println("Test passed!");
        else
            System.out.println("Test failed :( ");

    }

    public static void main(String[] args) {

        Auxiliary aux = new Auxiliary();

        // Sequential checker
        // aux.sequentialChecker();

        // ThreadSort checker
        // aux.threadChecker();

        // ExecutorServiceSort
        aux.executorChecker();
    }
}