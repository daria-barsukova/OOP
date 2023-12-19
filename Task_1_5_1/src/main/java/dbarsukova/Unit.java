package dbarsukova;

import java.util.Objects;

/**
 * class for representing a number with its type.
 */
public final class Unit {
    private final Expression exp;
    private final Expression.Type type;

    public Unit(Expression exp, Expression.Type type) {
        this.exp = exp;
        this.type = type;
    }

    public Expression exp() {
        return exp;
    }

    public Expression.Type type() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Unit) obj;
        return Objects.equals(this.exp, that.exp)
                && Objects.equals(this.type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exp, type);
    }

    @Override
    public String toString() {
        return "Unit[" + "exp=" + exp + ", " + "type=" + type + ']';
    }
}
