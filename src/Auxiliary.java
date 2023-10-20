
/**
 * Helper methods.
 */

import java.util.Arrays;
import java.util.Random;

public class Auxiliary {

    /**
     * Merge into array using fromIndex-mid-toIndex split
     */
    public static void merge(int arr[], int fromIndex, int mid, int toIndex) {

        // Store merge in temporary array
        int sz = toIndex - fromIndex + 1;
        int[] temp = new int[sz];

        // Initialize traverse indexes
        int i = fromIndex;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= toIndex) {
            if (arr[i] < arr[j]) {
                temp[k] = arr[i];
                i++;
            } else {
                temp[k] = arr[j];
                j++;
            }
            k++;
        }

        // Remaining entries in first half
        while (i <= mid) {
            temp[k] = arr[i];
            i++;
            k++;
        }

        // Remainin in second half
        while (j <= toIndex) {
            temp[k] = arr[j];
            j++;
            k++;
        }

        for (int idx = 0; idx < sz; idx++) {
            arr[fromIndex + idx] = temp[idx];
        }

    }

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

        // Get array size
        int sz = data.length;

        // Calculate mean
        double mean = 0.0;
        for (int i = 0; i < sz; i++) {
            mean += (data[i] / sz);
        }

        // Calculate stdev (from variance)
        double variance = 0.0;
        for (int i = 0; i < sz; i++) {
            variance += Math.pow(data[i] - mean, 2);
        }
        double stdev = Math.sqrt(variance / sz);

        // Group in array
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
        long[] executionTimes = new long[m];

        int seed = initSeed;
        for (int i = 0; i < m; i++) {
            // Set new seed to generate array
            seed++;
            int[] arr = arrayGenerate(n, seed);

            // Time sorting
            long startTime = System.nanoTime();
            sorter.sort(arr);
            long endTime = System.nanoTime();

            executionTimes[i] = (endTime - startTime);
        }

        // Calculate data
        result = getMeanAndStDev(executionTimes);
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
            // Set new seed and generate array
            seed++;
            int[] arr = arrayGenerate(seed, n);

            // Copy for reference
            int[] test = Arrays.copyOf(arr, arr.length);

            // Sort reference array
            Arrays.sort(test);

            // Sort using our method
            sorter.sort(arr);

            if (!Arrays.equals(test, arr))
                return false;
        }

        // If all tests successful, return true
        return true;

    }

}
