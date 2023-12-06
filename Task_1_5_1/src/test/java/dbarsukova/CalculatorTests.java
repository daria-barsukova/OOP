package dbarsukova;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;


/**
 * task-1-5-1 test.
 */

class CalculatorTests {

    @Test
    void test1() {
        assertSame(Calculator.Operation.INVALID, Calculator.parse(""));
        assertSame(Calculator.Operation.SIN, Calculator.parse("sin"));
        assertSame(Calculator.Operation.COS, Calculator.parse("cos"));
        assertSame(Calculator.Operation.SQRT, Calculator.parse("sqrt"));
        assertSame(Calculator.Operation.SUM, Calculator.parse("+"));
        assertSame(Calculator.Operation.SUB, Calculator.parse("-"));
        assertSame(Calculator.Operation.MUL, Calculator.parse("*"));
        assertSame(Calculator.Operation.DIV, Calculator.parse("/"));
        assertSame(Calculator.Operation.LOG, Calculator.parse("log"));
    }

    @Test
    void test2() {
        assertEquals(Calculator.applicationOfSingleFunc(Calculator.Operation.SIN, Math.PI), Math.sin(Math.PI));
        assertEquals(1, Calculator.applicationOfSingleFunc(Calculator.Operation.COS, 0.0));
        assertEquals(3, Calculator.applicationOfSingleFunc(Calculator.Operation.SQRT, 9.0));
        assertThrows(IllegalArgumentException.class, () -> Calculator.applicationOfSingleFunc(Calculator.Operation.DIV, 4));
    }

    @Test
    void test3() {
        assertEquals(247, Calculator.applicationOfDoubleFunc(Calculator.Operation.SUM, 123, 124));
        assertEquals(-246, Calculator.applicationOfDoubleFunc(Calculator.Operation.SUB, -123, 123));
        assertEquals(156, Calculator.applicationOfDoubleFunc(Calculator.Operation.MUL, 12, 13));
        assertEquals(-13.5, Calculator.applicationOfDoubleFunc(Calculator.Operation.DIV, -27, 2));
        assertEquals(3, Calculator.applicationOfDoubleFunc(Calculator.Operation.LOG, 2, 8));
        assertThrows(IllegalArgumentException.class, () -> Calculator.applicationOfDoubleFunc(Calculator.Operation.INVALID, 4, 4));
    }

    @Test
    void test4() {
        assertEquals(Math.sin(1 - 2 + 1), new Calculator().solver(new String[]{"sin", "+", "-", "1", "2", "1"}));
    }

    @Test
    void test5() {
        assertEquals(Math.sin(1 - 2 + 1), new Calculator().strSolver("sin + - 1 2 1 0"));
    }

    @Test
    void test6() {
        double ans = Calculator.applicationOfDoubleFunc(Calculator.Operation.LOG, 2, Math.pow(2, 7) / (2 * 2));
        String[] str = new String[]{"log", "2", "/", "^", "2", "7", "*", "2", "2"};
        assertEquals(ans, new Calculator().solver(str));
    }

    @Test
    void test7() {
        assertEquals(Math.sin(3) + Math.cos(3. / 7.), new Calculator().strSolver("+ sin 3 cos / 3 7"));
    }

    @Test
    void test8() {
        Calculator calc = new Calculator();
        assertThrows(IllegalArgumentException.class, () -> calc.solver(new String[0]));
        assertThrows(IllegalArgumentException.class, () -> calc.solver(new String[]{"tg", "0"}));
        assertThrows(IllegalArgumentException.class, () -> calc.solver(new String[]{"log"}));
        assertThrows(IllegalArgumentException.class, () -> calc.solver(new String[]{"*", "1"}));
    }
}
