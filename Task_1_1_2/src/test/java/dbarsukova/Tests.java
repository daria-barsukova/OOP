package dbarsukova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;


/**
 * task-1-1-2 test.
 */

public class Tests {

    @Test
    void test1() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 8});
        assertEquals(p1.plus(p2.differentiate(1)).toString(), "7x^3 + 6x^2 + 19x + 6");
        assertEquals(p1.times(p2).evaluate(2), 3510);
    }

    @Test
    void test2() {
        Polynomial p1 = new Polynomial(new int[]{});
        Polynomial p2 = new Polynomial(new int[]{7, 13, 6});
        assertEquals(p1.plus(p2).toString(), "6x^2 + 13x + 7");
    }

    @Test
    void test3() {
        Polynomial p1 = new Polynomial(new int[]{});
        Polynomial p2 = new Polynomial(new int[]{7, 13, 6});
        assertEquals(p1.times(p2).toString(), "0");
    }

    @Test
    void test4() {
        Polynomial p1 = new Polynomial(new int[]{0, 7});
        Polynomial p2 = new Polynomial(new int[]{-8, 9, 6});
        assertEquals(p1.minus(p2).toString(), "6x^2 - 2x + 8");
    }

    @Test
    void test5() {
        Polynomial p1 = new Polynomial(new int[]{-9, 6, 8, 0, 5});
        assertEquals(p1.evaluate(3), 486);
    }

    @Test
    void test6() {
        Polynomial p1 = new Polynomial(new int[]{6, 0, -3, 5, -6});
        assertEquals(p1.differentiate(3).toString(), "144x + 30");
    }

    @Test
    void test7() {
        Polynomial p1 = new Polynomial(new int[]{4, 5, 6});
        Polynomial p2 = new Polynomial(new int[]{4, 6, 5});
        assertFalse(p1.equality(p2));
    }
}