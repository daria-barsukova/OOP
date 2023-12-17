package dbarsukova;

import java.util.Locale;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;


/**
 * task-1-5-1 test.
 */

class CalculatorTests {

    @Test
    void test1() {
        String res = new Calculator().parser("sin + - 1 2 1");
        Scanner scanner = new Scanner(res).useLocale(Locale.US);
        Assertions.assertEquals(0, scanner.nextDouble());
    }

    @Test
    void test2() {
        String res = new Calculator().parser(" + 3.14 + 30i log sqrt * cos 32% sin 850%")
                .replace("i", "").replace("+", " +");
        Scanner scanner = new Scanner(res).useLocale(Locale.US);
        Assertions.assertEquals(2.9243334905899743, scanner.nextDouble());
        Assertions.assertEquals(30, scanner.nextDouble());
    }

    @Test
    void test3() {
        String res = new Calculator().parser("pow + - * / + log sqrt 1 3.14 2.3-2.2i 805.7 0 7 1")
                .replace("i", "").replace("+", " +");
        System.out.println(res);
        Scanner scanner = new Scanner(res).useLocale(Locale.US);
        Assertions.assertEquals(581.4092201382033, scanner.nextDouble());
        Assertions.assertEquals(549.4349062191511, scanner.nextDouble());
    }

    @Test
    void test4() {
        String res = new Calculator().parser("sin cos sqrt pow 365 + 1.7 -15.15").replace("i", "");
        System.out.println(res);
        Scanner scanner = new Scanner(res).useLocale(Locale.US);
        Assertions.assertEquals(0.84147098, scanner.nextDouble(), 1e-7);
    }

    @Test
    void test5() {
        Calculator calc = new Calculator();
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> calc.parser("+ 1% 1"));
        Assertions.assertEquals("illegal type",
                exception.getMessage());
        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> calc.parser("- 1% 1"));
        Assertions.assertEquals("illegal type",
                exception.getMessage());
        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> calc.parser("* 1% 2i"));
        Assertions.assertEquals("illegal type",
                exception.getMessage());
        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> calc.parser("/ 1% 2i"));
        Assertions.assertEquals("illegal type",
                exception.getMessage());
        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> calc.parser("pow 1% 2i"));
        Assertions.assertEquals(
                "illegal type",
                exception.getMessage());
    }
}
