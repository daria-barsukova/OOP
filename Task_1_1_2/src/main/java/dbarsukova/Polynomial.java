package dbarsukova;

import java.util.Objects;


/**
 * task-1-1-2 operations with polynomials.
 */

public class Polynomial {
    private final int[] coef;
    private final int degree;

    /**
     * constructor for a given set of coefficients.
     *
     * @param args set of coefficients.
     */
    public Polynomial(int[] args) {
        degree = args.length;
        if (degree == 0) {
            System.out.println("empty array");
        }
        coef = args;
    }

    /**
     * adds the second polynomial to the first.
     *
     * @param term the second polynomial.
     */
    public Polynomial plus(Polynomial term) {
        int newDegree = Math.max(this.degree, term.degree);
        int[] newCoef = new int[newDegree];
        for (int i = 0; i < newDegree; i++) {
            if (i < this.degree) {
                newCoef[i] += this.coef[i];
            }
            if (i < term.degree) {
                newCoef[i] += term.coef[i];
            }
        }
        return new Polynomial(newCoef);
    }

    /**
     * subtracts the second polynomial from the first by adding with a negative coefficient.
     *
     * @param term the second polynomial.
     */
    public Polynomial minus(Polynomial term) {
        int[] newCoef = new int[term.degree];
        for (int i = 0; i < term.degree; i++) {
            newCoef[i] = -term.coef[i];
        }
        return this.plus(new Polynomial(newCoef));
    }

    /**
     * multiplies the second polynomial by the first.
     *
     * @param term the second polynomial.
     */
    public Polynomial times(Polynomial term) {
        int[] newCoef = new int[this.degree + term.degree - 1];
        for (int i = 0; i < term.degree; i++) {
            for (int j = 0; j < this.degree; j++) {
                newCoef[i + j] += (term.coef[i] * this.coef[j]);
            }
        }
        return new Polynomial(newCoef);
    }

    /**
     * calculates the value at a given point.
     *
     * @param x given point.
     */
    public int evaluate(int x) {
        int pow = x;
        int ans = this.coef[0];
        for (int i = 1; i < this.degree; i++) {
            ans += (this.coef[i] * pow);
            pow *= x;
        }
        return ans;
    }

    /**
     * takes the i'th derivative.
     *
     * @param i order of derivative.
     */
    public Polynomial differentiate(int i) {
        if (i <= 0) {
            return this;
        }
        if (this.degree - 1 < i) {
            return new Polynomial(new int[]{0});
        }
        int[] newCoef = new int[this.degree - i];
        int newDegree = newCoef.length;
        int order = i;
        System.arraycopy(this.coef, i, newCoef, 0, newDegree);
        while (order > 0) {
            for (int j = 0; j < newDegree; j++) {
                newCoef[j] *= (order + j);
            }
            order--;
        }
        return new Polynomial(newCoef);
    }

    /**
     * compares 2 polynomials with each other.
     *
     * @param term the second polynomial.
     */
    public boolean equality(Polynomial term) {
        if (this.degree != term.degree) {
            return false;
        }
        for (int i = 0; i < this.degree; i++) {
            if (this.coef[i] != term.coef[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * recommended redefinition of `hashCode` method.
     */
    public int hashCode() {
        return Objects.hash(this.coef);
    }

    /**
     * gets a string representation of a polynomial.
     */
    public String toString() {
        String ans = "";
        int newDegree = this.degree;
        int order = newDegree - 1;
        while (order >= 0) {
            if (this.coef[order] != 0) {
                if (newDegree != this.degree) {
                    ans += (this.coef[order] > 0 ? " + " : " - ");
                }
                newDegree--;
                ans += String.format("%d", Math.abs(this.coef[order]));
                if (newDegree > 1) {
                    ans += "x^";
                    ans += String.format("%s", newDegree);
                }
                if (newDegree == 1) {
                    ans += "x";
                }
                if (newDegree < 1) {
                    newDegree--;
                }
            }
            order--;
        }
        return ans.isEmpty() ? "0" : ans;
    }
}