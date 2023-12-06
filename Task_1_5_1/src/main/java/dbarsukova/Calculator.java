package dbarsukova;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


/**
 * task-1-5-1 calculator.
 */

public class Calculator {
    public static final EnumSet<Operation> singleFunc = EnumSet.of(Operation.SIN,
            Operation.COS,
            Operation.SQRT);
    public static final EnumSet<Operation> doubleFunc = EnumSet.of(Operation.SUM,
            Operation.SUB,
            Operation.MUL,
            Operation.DIV,
            Operation.POW,
            Operation.LOG);
    private final List<Double> numbers = new ArrayList<>();

    /**
     * enum contains atomic mathematical operations - single and double.
     */
    public enum Operation {
        POW,
        LOG,
        SIN,
        COS,
        SQRT,
        SUM,
        SUB,
        MUL,
        DIV,
        INVALID
    }

    /**
     * string conversion.
     *
     * @param operation operation for processing.
     */
    public static Operation parse(String operation) {
        if (operation.trim().equals("sin")) {
            return Operation.SIN;
        }
        if (operation.trim().equals("cos")) {
            return Operation.COS;
        }
        if (operation.trim().equals("sqrt")) {
            return Operation.SQRT;
        }
        if (operation.trim().equals("+")) {
            return Operation.SUM;
        }
        if (operation.trim().equals("-")) {
            return Operation.SUB;
        }
        if (operation.trim().equals("/")) {
            return Operation.DIV;
        }
        if (operation.trim().equals("*")) {
            return Operation.MUL;
        }
        if (operation.trim().equals("log")) {
            return Operation.LOG;
        }
        if (operation.trim().equals("^")) {
            return Operation.POW;
        }
        return Operation.INVALID;
    }

    /**
     * working with single functions.
     *
     * @param operation operation for processing.
     * @param number    argument.
     */
    public static double applicationOfSingleFunc(Operation operation,
                                                 double number) {
        if (operation == Operation.SIN) {
            return Math.sin(number);
        }
        if (operation == Operation.COS) {
            return Math.cos(number);
        }
        if (operation == Operation.SQRT) {
            return Math.sqrt(number);
        }
        throw new IllegalArgumentException();
    }

    /**
     * working with double functions.
     *
     * @param operation operation for processing.
     * @param number1   first argument.
     * @param number2   second argument.
     */
    public static double applicationOfDoubleFunc(Operation operation,
                                                 double number1,
                                                 double number2) {
        if (operation == Operation.SUM) {
            return number1 + number2;
        }
        if (operation == Operation.SUB) {
            return number1 - number2;
        }
        if (operation == Operation.DIV) {
            return number1 / number2;
        }
        if (operation == Operation.MUL) {
            return number1 * number2;
        }
        if (operation == Operation.LOG) {
            return Math.log(number2) / Math.log(number1);
        }
        if (operation == Operation.POW) {
            return Math.pow(number1, number2);
        }
        throw new IllegalArgumentException();
    }

    /**
     * accepts expression as array and finds its value.
     * throws IllegalArgumentException if
     * empty array is supplied/operation is invalid/too few arguments.
     * warning is displayed in the stdin if more numbers than necessary are supplied to input.
     *
     * @param data array of atomic expression elements in prefix form.
     */
    public Double solver(String[] data) {
        int counter = 0;
        int length = data.length - 1;
        if (length == -1) {
            throw new IllegalArgumentException("missed expression");
        }
        for (int i = length; i != -1; --i) {
            try {
                numbers.add(Double.parseDouble(data[i]));
                ++counter;
            } catch (NumberFormatException exc) {
                if (singleFunc.contains(parse(data[i]))) {
                    if (counter == 0) {
                        throw new IllegalArgumentException("not enough arguments");
                    }
                    numbers.set(counter - 1,
                            applicationOfSingleFunc(parse(data[i]), numbers.get(counter - 1)));
                } else if (doubleFunc.contains(parse(data[i]))) {
                    if (counter < 2) {
                        throw new IllegalArgumentException("not enough arguments");
                    }
                    --counter;
                    numbers.set(counter - 1,
                            applicationOfDoubleFunc(parse(data[i]),
                                    numbers.remove(counter),
                                    numbers.get(counter - 1)));
                } else {
                    throw new IllegalArgumentException("invalid operation");
                }
            }
        }
        if (numbers.size() > 1) {
            System.out.println("warning: last " + (numbers.size() - 1) + " symbols are not used");
        }
        return numbers.get(counter - 1);
    }

    /**
     * method takes expression and evaluates it,
     * converting string into array of strings.
     *
     * @param str input string.
     */
    public Double strSolver(String str) {
        return solver(str.trim().replaceAll("\\s{2,}", " ").split(" "));
    }
}
