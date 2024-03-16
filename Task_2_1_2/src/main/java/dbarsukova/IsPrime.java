package dbarsukova;

/**
 * task-2-1-2 prime numbers.
 */

public class IsPrime {

    /**
     * checking whether number is prime or not.
     *
     * @param a number.
     */
    public static boolean isPrime(int a) {
        if (a < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(a); i++) {
            if (a % i == 0) {
                return false;
            }
        }
        return true;
    }
}
