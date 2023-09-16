
/**
 * Helper methods.
 */

import java.util.Arrays;
import java.util.Random;

public class Auxiliary {
    /**
     * Generate a pseudo-random array of length `n`.
     */
    public static int[] arrayGenerate(int seed, int n) {
        Random prng = new Random(seed);
        int[] arr = new int[n];
        for (int i = 0; i < n; ++i)
            arr[i] = prng.nextInt();
        return arr;
    }

    /**
     * Measures the execution time of the 'sorter'.
     * 
     * @param sorter   Sorting algorithm
     * @param n        Size of list to sort
     * @param initSeed Initial seed used for array generation
     * @param m        Measurment rounds.
     * @return result[0]: average execution time
     *         result[1]: standard deviation of execution time
     */
    public static double[] measure(Sorter sorter, int n, int initSeed, int m) {
        double[] result = new double[2];
        // TODO Measure the avg. execution time and std of sorter.
        return result;
    }

    /**
     * Checks that the 'sorter' sorts.
     * 
     * @param sorter   Sorting algorithm
     * @param n        Size of list to sort
     * @param initSeed Initial seed used for array generation
     * @param m        Number of attempts.
     * @return True if the sorter successfully sorted all generated arrays.
     */
    public static boolean validate(Sorter sorter, int n, int initSeed, int m) {

        // Reference array
        int seed = initSeed;

        for (int i = 0; i < m; i++) {

            // Generate array and reference copy
            int[] arr = arrayGenerate(seed, n);
            int[] test = Arrays.copyOf(arr, arr.length);

            // Sort reference array
            Arrays.sort(test);

            // Start test
            System.out.println("Attempt - " + (i + 1));
            System.out.println("-------------");
            long startTime = System.nanoTime();
            sorter.sort(arr);
            long endTime = System.nanoTime();

            System.out.println("Start time : " + startTime);
            System.out.println("End time: " + endTime);
            System.out.println("Time taken: " + (endTime - startTime));
            System.out.println();

            if (!Arrays.equals(test, arr))
                return false;

            // Change seed
            seed = 2 * seed;
        }

        // If all tests successful, return true
        return true;

    }

    public static void main(String[] args) {

        // Test parameters
        int numThreads = 8;
        int arrLength = 1_000_000;
        int initSeed = 69;
        int numTests = 10;

        // Testing

        System.out.println("Starting tests...");
        System.out.println("# threads : " + numThreads + " | arr length : " + arrLength);
        System.out.println("...");
        // --------------------
        ExecutorServiceSort sorter = new ExecutorServiceSort(numThreads);
        if (validate(sorter, arrLength, initSeed, numTests))
            System.out.println(numTests + " tests passed successfully");
        else
            System.out.println("Failed");
    }

}
