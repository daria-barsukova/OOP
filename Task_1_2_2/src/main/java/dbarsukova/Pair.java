package dbarsukova;

/**
 * simple generic class representing a pair of values.
 *
 * @param <X> type of first value
 * @param <Y> type of second value
 */
public class Pair<X, Y> {
    private final X one;
    private final Y two;

    Pair(X one, Y two) {
        this.one = one;
        this.two = two;
    }

    X getOne() {
        return one;
    }

    Y getTwo() {
        return two;
    }
}