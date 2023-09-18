
/**
 * Helper methods.
 */

import java.util.Arrays;
import java.util.Random;
import java.util.stream.LongStream;

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
     * Returns mean and standard deviation from measurements
     * 
     * @param data
     * @return
     */
    public static double[] getMeanAndStDev(long[] data) {

        // Calculate mean
        double sum = 0.0;
        for (double n : data) {
            sum += n;
        }
        double mean = sum / data.length;

        // Calculate stdev (from variance)
        double variance = 0.0;
        for (long value : data) {
            variance += Math.pow(value - mean, 2);
        }
        double stdev = Math.sqrt(variance / data.length);

        double[] results = { mean, stdev };
        return results;
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
        long[] measurements = new long[m];

        int seed = 0;
        seed = seed + initSeed;
        for (int i = 0; i < m; i++) {
            int[] arr = arrayGenerate(n, seed);
            long startTime = System.nanoTime();
            sorter.sort(arr);
            long endTime = System.nanoTime();
            measurements[i] = (endTime - startTime);
        }

        // Calculate data
        result = getMeanAndStDev(measurements);
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
        int seed = 0;
        seed = seed + initSeed;
        for (int i = 0; i < m; i++) {

            // Generate array and reference copy
            int[] arr = arrayGenerate(seed, n);
            int[] test = Arrays.copyOf(arr, arr.length);

            // Sort reference array
            Arrays.sort(test);

            // // Start test
            // System.out.println("Attempt - " + (i + 1));
            // System.out.println("-------------");

            // long startTime = System.nanoTime();
            sorter.sort(arr);
            // long endTime = System.nanoTime();

            // System.out.println("Start time : " + startTime);
            // System.out.println("End time: " + endTime);
            // System.out.println("Time taken: " + (endTime - startTime));
            // System.out.println();

            if (!Arrays.equals(test, arr))
                return false;

            // Change seed
            seed = seed + initSeed;
        }

        // If all tests successful, return true
        return true;

    }

}
