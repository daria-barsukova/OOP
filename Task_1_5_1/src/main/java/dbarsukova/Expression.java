package dbarsukova;


/**
 * general class.
 */

public abstract class Expression {
    public enum Type {
        Complex, Degree
    }

    public record Node(Expression exp, Type type) {
    }

    abstract Complex sin();

    abstract Complex cos();

    abstract void pow(Node n);

    abstract void log();

    abstract void sqrt();

    abstract double getData();

    abstract double getImPrt();

    abstract void sum(Node n);

    abstract void sub(Node n);

    abstract void mul(Node n);

    abstract void div(Node n);
}
