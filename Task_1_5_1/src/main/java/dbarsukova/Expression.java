package dbarsukova;


/**
 * general class.
 */

public abstract class Expression {

    /**
     * characteristics of number.
     */
    public enum Type {
        Complex, Degree
    }

    abstract Complex sin();

    abstract Complex cos();

    abstract void pow(Unit n);

    abstract void log();

    abstract void sqrt();

    abstract double getData();

    abstract double getImPrt();

    abstract void sum(Unit n);

    abstract void sub(Unit n);

    abstract void mul(Unit n);

    abstract void div(Unit n);
}
