package dbarsukova;


/**
 * class for degree number.
 */

public class Degree extends Expression {
    private double data;

    Degree(double data) {
        this.data = data;
    }

    private void checkSign() {
        if (data < 0) {
            throw new RuntimeException("must be positive");
        }
    }

    double getData() {
        return data;
    }

    @Override
    double getImPrt() {
        return 0;
    }

    /**
     * adds one to another.
     *
     * @param n number.
     */
    @Override
    void sum(Expression.Unit n) {
        if (n.type().equals(Type.Complex)) {
            throw new IllegalArgumentException("illegal type");
        }
        data += n.exp().getData();
        checkSign();
    }

    /**
     * subtracts one from another.
     *
     * @param n number.
     */
    @Override
    void sub(Expression.Unit n) {
        if (n.type().equals(Type.Complex)) {
            throw new IllegalArgumentException("illegal type");
        }
        data -= n.exp().getData();
        checkSign();
    }

    /**
     * multiplies one by another.
     *
     * @param n number.
     */
    @Override
    void mul(Expression.Unit n) {
        if (n.type().equals(Type.Degree) || n.exp().getImPrt() != 0) {
            throw new IllegalArgumentException("illegal type");
        }
        data *= n.exp().getData();
        checkSign();
    }

    /**
     * divides one by another.
     *
     * @param n number.
     */
    @Override
    void div(Expression.Unit n) {
        if (n.type().equals(Type.Degree) || n.exp().getImPrt() != 0) {
            throw new IllegalArgumentException("illegal type");
        }
        data /= n.exp().getData();
        checkSign();
    }

    /**
     * elevates to degree.
     *
     * @param n number.
     */
    @Override
    void pow(Expression.Unit n) {
        if (n.type().equals(Type.Degree) || n.exp().getImPrt() != 0) {
            throw new IllegalArgumentException("illegal type");
        }
        data = Math.pow(data, n.exp().getData());
        checkSign();
    }

    /**
     * taking logarithm.
     */
    @Override
    void log() {
        data = Math.log(data);
        checkSign();
    }

    /**
     * root of number.
     */
    @Override
    void sqrt() {
        data = Math.sqrt(data);
        checkSign();
    }

    /**
     * takes sin.
     */
    @Override
    Complex sin() {
        if (data % 180 == 0) {
            return new Complex(0, 0);
        }
        return new Complex(Math.sin(Math.toRadians(data % 360)), 0);
    }

    /**
     * takes cos.
     */
    @Override
    Complex cos() {
        if (data % 180 == 90) {
            return new Complex(0, 0);
        }
        return new Complex(Math.cos(Math.toRadians(data % 360)), 0);
    }
}
