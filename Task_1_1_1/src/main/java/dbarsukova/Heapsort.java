package dbarsukova;

/**
 * task-1-1-1 heapsort.
 */

public class Heapsort {

    /**
     * `sort` is a method that implements pyramid sorting.
     * initially, we regroup array to build a heap.
     * then we extract elements one by one, move current root to the end.
     * call the `increaseHeap` function on reduced heap.
     *
     * @param array for sorting
     *              
     */

    static int[] sort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            increaseHeap(arr, n, i);
        }
        for (int i = n - 1; i >= 0; i--) {
            int tmp = arr[0];
            arr[0] = arr[i];
            arr[i] = tmp;
            increaseHeap(arr, i, 0);
        }
        return arr;
    }

    private static void increaseHeap(int[] arr, int n, int i) {
        int max = i;
        int left;
        int right;
        int tmp;
        left = 2 * i + 1;
        right = 2 * i + 2;
        if (left < n && arr[left] > arr[max]) {          // left child is bigger than root
            max = left;
        }
        if (right < n && arr[right] > arr[max]) {        // right child is bigger than root
            max = right;
        }
        if (max != i) {                                  // maximum element isn`t root
            tmp = arr[i];
            arr[i] = arr[max];
            arr[max] = tmp;
            increaseHeap(arr, n, max);
        }
    }
}
