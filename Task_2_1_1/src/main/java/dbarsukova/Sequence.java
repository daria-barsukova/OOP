package dbarsukova;


/**
 * task-2-1-1 prime numbers.
 */

public class Sequence {

    /**
     * checking whether number is prime or not.
     *
     * @param a number.
     */
    public boolean isPrime(int a) {
        if (a < 0) {
            return false;
        }
        if (a == 0 || a == 1) {
            return false;
        }
        if (a % 2 == 0 || a % 3 == 0) {
            return false;
        }
        for (int i = 3; i <= Math.sqrt(a); i += 2) {
            if (a % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * determining if there is at least one non-simple number in array.
     *
     * @param array array to search for.
     */
    public boolean search(int[] array) throws NullPointerException {
        if (array == null) {
            throw new NullPointerException();
        }
        for (int number : array) {
            if (!isPrime(number)) {
                return true;
            }
        }
        return false;
    }
}
