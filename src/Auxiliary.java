
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

    void merge(int[] arr, int start, int mid, int end) {
        int[] temp = new int[(end - start) + 1];

        // Initialize swapping indexes
        int i = start;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= end) {
            if (arr[i] <= arr[j]) {
                temp[k] = arr[i];
                i += 1;
            } else {
                temp[k] = arr[j];
                j += 1;
            }
            k += 1;
        }

        // Add remaining elements to temp array from first half that are left over
        while (i <= mid) {
            temp[k] = arr[i];
            i += 1;
            k += 1;
        }

        // Add remaining elements to temp array from second half that are left over
        while (j <= end) {
            temp[k] = arr[j];
            j += 1;
            k += 1;
        }

        for (i = start, k = 0; i <= end; i++, k++) {
            arr[i] = temp[k];
        }
    }

    void threadChecker() {

        int n = random.nextInt(100) + 1;

        // Generate a random array;
        int[] arr = new int[n];
        // Populate it
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(100) + 1;
        }

        printArray(arr);
        System.out.println();
        System.out.println("Start time : " + System.nanoTime());

        ThreadSort sorter = new ThreadSort();
        sorter.sort(arr);

        System.out.println("End time: " + System.nanoTime());

        printArray(arr);

        // // Sort it
        // int[] test = Arrays.copyOf(arr, arr.length);
        // Arrays.sort(test);

        // // Sort same array with sequential sort
        // SequentialSort sorter = new SequentialSort();
        // sorter.sort(arr);

        // if (Arrays.equals(test, arr))
        // System.out.println("Test passed!");
        // else
        // System.out.println("Test failed :( ");

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

    public static void main(String[] args) {

        Auxiliary aux = new Auxiliary();

        // Sequential checker
        // aux.sequentialChecker();

        // ThreadSort checker
        aux.threadChecker();
    }
}