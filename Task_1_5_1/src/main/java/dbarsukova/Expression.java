package dbarsukova;


/**
 * general class.
 */

public abstract class Expression {
    public enum Type {
        Complex, Degree
    }

    public record Unit(Expression exp, Type type) {
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
