package dbarsukova;


/**
 * class for double and complex number.
 */

public class Complex extends Expression {
    private double re;
    private double im;

    Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    double getData() {
        return re;
    }

    double getImPrt() {
        return im;
    }

    /**
     * adds one to another.
     *
     * @param n number.
     */
    @Override
    void sum(Node n) {
        if (n.type().equals(Type.Degree)) {
            throw new IllegalArgumentException("illegal types");
        }
        re += n.exp().getData();
        im += n.exp().getImPrt();
    }

    /**
     * subtracts one from another.
     *
     * @param n number.
     */
    @Override
    void sub(Node n) {
        if (n.type().equals(Type.Degree)) {
            throw new IllegalArgumentException("illegal types");
        }
        re -= n.exp().getData();
        im -= n.exp().getImPrt();
    }

    /**
     * multiplies one by another.
     *
     * @param n number.
     */
    @Override
    void mul(Node n) {
        if (n.type().equals(Type.Degree)) {
            throw new IllegalArgumentException("illegal types");
        }
        double temp = re;
        re = temp * n.exp().getData() - im * n.exp().getImPrt();
        im = temp * n.exp().getImPrt() + im * n.exp().getData();
    }

    /**
     * divides one by another.
     *
     * @param n number.
     */
    @Override
    void div(Node n) {
        if (n.type().equals(Type.Degree) ||
                Math.pow(n.exp().getData(), 2) + Math.pow(n.exp().getImPrt(), 2) == 0) {
            throw new IllegalArgumentException("illegal types");
        }
        double expData = n.exp().getData();
        double expImPrt = n.exp().getImPrt();
        double d = Math.pow(expData, 2) + Math.pow(expImPrt, 2);
        double temp = (re * expData + im * expImPrt) / d;
        im = (im * expData - re * expImPrt) / d;
        re = temp;
    }

    /**
     * elevates to degree.
     *
     * @param n number.
     */
    @Override
    void pow(Node n) {
        if (n.exp().getImPrt() == 0 && im == 0) {
            re = Math.pow(re, n.exp().getData());
        } else {
            log();
            mul(n);
            double exp = Math.exp(re);
            double cos = Math.cos(im);
            double sin = Math.sin(im);
            re = exp * cos;
            im = exp * sin;
        }
        if (n.type().equals(Type.Degree)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * taking logarithm.
     */
    @Override
    void log() {
        double d = Math.hypot(re, im);
        if (d == 0 || re == 0) {
            throw new ArithmeticException();
        }
        im = Math.atan2(im, re);
        re = Math.log(d);
    }

    /**
     * root of number.
     */
    @Override
    void sqrt() {
        double r = Math.sqrt(re * re + im * im);
        if (im != 0) {
            double sign = Math.abs(im) / im;
            im = sign * Math.sqrt((-re + r) / 2);
        }
        re = Math.sqrt((re + r) / 2);
    }

    /**
     * takes sin.
     */
    @Override
    Complex sin() {
        if (im != 0) {
            throw new ArithmeticException();
        }
        re = Math.sin(re);
        return this;
    }

    /**
     * takes cos.
     */
    @Override
    Complex cos() {
        if (im != 0) {
            throw new ArithmeticException();
        }
        re = Math.cos(re);
        return this;
    }
}
