
import java.util.Arrays;
import java.util.Random;

class Auxiliary {

    Random random = new Random();

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

    public static void main(String[] args) {

        Auxiliary aux = new Auxiliary();

        // Sequential checker
        aux.sequentialChecker();
    }
}