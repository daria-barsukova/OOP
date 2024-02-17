package dbarsukova;

import java.util.Arrays;


public class Parallel extends Sequence {
    @Override
    public boolean search(int[] arr) throws NullPointerException {
        if (arr == null) {
            throw new NullPointerException();
        }
        return Arrays.stream(arr).parallel().anyMatch(a -> !isPrime(a));
    }
}
