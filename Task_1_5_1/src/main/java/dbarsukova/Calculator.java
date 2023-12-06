package dbarsukova;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


/**
 * task-1-5-1 calculator.
 */

public class Calculator {
    public static final EnumSet<Operation> singleFunc = EnumSet.of(Operation.SIN, Operation.COS, Operation.SQRT);
    public static final EnumSet<Operation> doubleFunc = EnumSet.of(Operation.SUM, Operation.SUB, Operation.MUL, Operation.DIV, Operation.POW, Operation.LOG);
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
        return switch (operation.trim()) {
            case "sin" -> Operation.SIN;
            case "cos" -> Operation.COS;
            case "sqrt" -> Operation.SQRT;
            case "+" -> Operation.SUM;
            case "-" -> Operation.SUB;
            case "/" -> Operation.DIV;
            case "*" -> Operation.MUL;
            case "log" -> Operation.LOG;
            case "^" -> Operation.POW;
            default -> Operation.INVALID;
        };
    }

    /**
     * working with single functions.
     *
     * @param operation operation for processing.
     * @param number    argument.
     */
    public static double applicationOfSingleFunc(Operation operation, double number) {
        return switch (operation) {
            case SIN -> Math.sin(number);
            case COS -> Math.cos(number);
            case SQRT -> Math.sqrt(number);
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * working with double functions.
     *
     * @param operation operation for processing.
     * @param number1   first argument.
     * @param number2   second argument.
     */
    public static double applicationOfDoubleFunc(Operation operation, double number1, double number2) {
        return switch (operation) {
            case SUM -> number1 + number2;
            case SUB -> number1 - number2;
            case DIV -> number1 / number2;
            case MUL -> number1 * number2;
            case LOG -> Math.log(number2) / Math.log(number1);
            case POW -> Math.pow(number1, number2);
            default -> throw new IllegalArgumentException();
        };
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
                    numbers.set(counter - 1, applicationOfSingleFunc(parse(data[i]), numbers.get(counter - 1)));
                } else if (doubleFunc.contains(parse(data[i]))) {
                    if (counter < 2) {
                        throw new IllegalArgumentException("not enough arguments");
                    }
                    --counter;
                    numbers.set(counter - 1, applicationOfDoubleFunc(parse(data[i]), numbers.remove(counter), numbers.get(counter - 1)));
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
