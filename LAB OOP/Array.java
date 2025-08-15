public class Array{
    static int Max(int[] arr, int n) {
        if (n == 1) {
            return arr[0]; // Base case
        }

        int maxOfRest = Max(arr, n - 1); // Recursive step
        return Math.max(arr[n - 1], maxOfRest);
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 1, 7};
        System.out.println("Max: " + Max(arr, arr.length));
    }
}
