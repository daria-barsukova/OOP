package dbarsukova;

import dbarsukova.Expression.Type;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.MissingFormatArgumentException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * task-1-5-1 calculator.
 */

public class Calculator {
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("-?\\d+(.\\d+)?");
    private static final Pattern COMPLEX_PATTERN = Pattern.compile("(-?\\d+(.\\d+)?)?([-|+](\\d+(.\\d+)?))?[i$]");
    private static final Pattern DEGREE_PATTERN = Pattern.compile("(\\d+)(.\\d+)?%");

    private static boolean sin_type(String s) {
        return s.equals("sin");
    }

    private static boolean cos_type(String s) {
        return s.equals("cos");
    }

    private static boolean sqrt_type(String s) {
        return s.equals("sqrt");
    }

    private static boolean sum_type(String s) {
        return s.equals("+");
    }

    private static boolean sub_type(String s) {
        return s.equals("-");
    }

    private static boolean mul_type(String s) {
        return s.equals("*");
    }

    private static boolean div_type(String s) {
        return s.equals("/");
    }

    private static boolean pow_type(String s) {
        return s.equals("pow");
    }

    private static boolean log_type(String s) {
        return s.equals("log");
    }

    private static boolean singleFunc(String s) {
        return sin_type(s) || cos_type(s) || sqrt_type(s) || log_type(s);
    }

    private static boolean doubleFunc(String s) {
        return sum_type(s) || sub_type(s) || mul_type(s) || div_type(s) || pow_type(s);
    }

    private static boolean isDouble(String s) {
        Matcher m = DOUBLE_PATTERN.matcher(s);
        return m.matches();
    }

    private static boolean isComplex(String s) {
        Matcher m = COMPLEX_PATTERN.matcher(s);
        return m.matches();
    }

    private static boolean isDegree(String s) {
        Matcher m = DEGREE_PATTERN.matcher(s);
        return m.matches();
    }

    private final Deque<Unit> numbers = new ArrayDeque<>();
    private final List<String> expressions = new ArrayList<>();

    /**
     * working with single functions.
     *
     * @param operation operation for processing.
     */
    public void applicationOfSingleFunc(String operation) {
        Unit data = Objects.requireNonNull(numbers.pollLast());
        if (log_type(operation)) {
            data.exp().log();
        }
        if (sqrt_type(operation)) {
            data.exp().sqrt();
        }
        if (sin_type(operation)) {
            data = new Unit(data.exp().sin(), Type.Complex);
        }
        if (cos_type(operation)) {
            data = new Unit(data.exp().cos(), Type.Complex);
        }
        numbers.addLast(data);
    }

    /**
     * working with double functions.
     *
     * @param operation operation for processing.
     */
    public void applicationOfDoubleFunc(String operation) {
        Unit data1 = Objects.requireNonNull(numbers.pollLast());
        Unit data2 = Objects.requireNonNull(numbers.pollLast());
        if (sum_type(operation)) {
            data1.exp().sum(data2);
        }
        if (sub_type(operation)) {
            data1.exp().sub(data2);
        }
        if (mul_type(operation)) {
            data1.exp().mul(data2);
        }
        if (div_type(operation)) {
            data1.exp().div(data2);
        }
        if (pow_type(operation)) {
            data1.exp().pow(data2);
        }
        numbers.addLast(data1);
    }

    /**
     * converts the specified string representation of a number or complex
     * number to the appropriate data type and adds it to the list.
     *
     * @param s unparsed string.
     */
    public void toParser(String s) {
        if (isDouble(s)) {
            numbers.addLast(new Unit(new Complex(Double.parseDouble(s), 0), Type.Complex));
        }
        if (isDegree(s)) {
            numbers.addLast(new Unit(new Degree(Double.parseDouble(s.replace("%",
                    ""))), Type.Degree));
        }
        if (isComplex(s)) {
            if (s.matches("(-?\\d+(.\\d+)?)([-|+](\\d+(.\\d+)?))[i$]")) {
                s = s.replace("i", "").replace("-",
                        " -").replace("+", " ");
                Scanner scan = new Scanner(s).useLocale(Locale.US);
                numbers.addLast(new Unit(new Complex(scan.nextDouble(),
                        scan.nextDouble()), Type.Complex));
            }
            if (s.matches("(-?(\\d+(.\\d+)?))[i$]")) {
                s = s.replace("i", "");
                numbers.addLast(new Unit(new Complex(0,
                        Double.parseDouble(s)), Type.Complex));
            }
        }
    }

    /**
     * solves the expression by evaluating each item in the list.
     * method iterates through the list of expressions in
     * reverse order and performs the appropriate operations
     * depending on the type of expression encountered.
     */
    public Unit solver() {
        int counter = expressions.size() - 1;
        String s;
        for (int i = counter; i >= 0; i--) {
            s = expressions.get(i);
            if (isComplex(s) || isDegree(s) || isDouble(s)) {
                toParser(s);
            } else if (singleFunc(s) || doubleFunc(s)) {
                if (singleFunc(s)) {
                    applicationOfSingleFunc(s);
                } else {
                    applicationOfDoubleFunc(s);
                }
            }
        }
        if (numbers.size() != 1) {
            throw new MissingFormatArgumentException("incorrect expression format");
        }
        return numbers.pop();
    }

    /**
     * analyzes input string and performs various checks on each element
     * to determine its type. if the element meets the conditions, it is
     * added to the list of expressions.
     * otherwise, a MissingFormatArgumentException is thrown, indicating
     * an incorrect expression format.
     * method uses a solver to calculate the result based on a list of
     * expressions and returns a formatted string.
     *
     * @param input input string.
     */
    public String parser(String input) {
        Scanner scanner = new Scanner(input).useDelimiter("\\s+");
        while (scanner.hasNext()) {
            String s = scanner.next();
            if (singleFunc(s) || doubleFunc(s) || isComplex(s) || isDegree(s) || isDouble(s)) {
                expressions.add(s);
            } else {
                throw new MissingFormatArgumentException("incorrect expression format");
            }
        }
        if (solver().type().equals(Type.Degree)) {
            return BigDecimal.valueOf(solver().exp().getData()).doubleValue() + "%";
        }
        if (solver().exp().getImPrt() == 0) {
            return String.valueOf(BigDecimal.valueOf(solver().exp().getData()).doubleValue());
        }
        if (solver().exp().getImPrt() < 0) {
            return BigDecimal.valueOf(solver().exp().getData()).doubleValue() + "" + BigDecimal
                    .valueOf(solver().exp().getImPrt()).doubleValue() + "i";
        } else {
            return BigDecimal.valueOf(solver().exp().getData()).doubleValue() + "+" + BigDecimal
                    .valueOf(solver().exp().getImPrt()).doubleValue() + "i";
        }
    }
}
