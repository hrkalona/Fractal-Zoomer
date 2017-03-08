/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.parser;

import fractalzoomer.core.Complex;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * An ExpressionNode that handles mathematical functions with 2 arguments.
 *
 * Some pre-defined functions are handled, others can easily be added.
 */
public class Function2ArgumentsExpressionNode implements ExpressionNode {

    /**
     * function id for the bipolar function
     */
    public static final int TO_BIPOLAR = 0;

    /**
     * function id for the inversed bipolar function
     */
    public static final int FROM_BIPOLAR = 1;

    /**
     * function id for the inflection function
     */
    public static final int INFLECTION = 2;

    /**
     * function id for the fold up function
     */
    public static final int FOLD_UP = 3;

    /**
     * function id for the fold down function
     */
    public static final int FOLD_DOWN = 4;

    /**
     * function id for the fold left function
     */
    public static final int FOLD_LEFT = 5;

    /**
     * function id for the fold right function
     */
    public static final int FOLD_RIGHT = 6;

    /**
     * function id for the fold in function
     */
    public static final int FOLD_IN = 7;

    /**
     * function id for the fold out function
     */
    public static final int FOLD_OUT = 8;

    /**
     * function id for the shear function
     */
    public static final int SHEAR = 9;

    /**
     * function id for the compare function
     */
    public static final int COMPARE = 10;

    /**
     * function id for the add function
     */
    public static final int ADD = 11;

    /**
     * function id for the subtract function
     */
    public static final int SUB = 12;

    /**
     * function id for the multiply function
     */
    public static final int MUL = 13;

    /**
     * function id for the divide function
     */
    public static final int DIV = 14;

    /**
     * function id for the reminder function
     */
    public static final int REM = 15;

    /**
     * function id for the power function
     */
    public static final int POW = 16;

    /**
     * function id for the log base n function
     */
    public static final int LOGN = 17;

    /**
     * function id for the user g1 function
     */
    public static final int G1 = 18;

    /**
     * function id for the user g2 function
     */
    public static final int G2 = 19;

    /**
     * function id for the user g3 function
     */
    public static final int G3 = 20;

    /**
     * function id for the user g4 function
     */
    public static final int G4 = 21;

    /**
     * function id for the user g5 function
     */
    public static final int G5 = 22;

    /**
     * function id for the user g6 function
     */
    public static final int G6 = 23;

    /**
     * function id for the user g7 function
     */
    public static final int G7 = 24;

    /**
     * function id for the user g8 function
     */
    public static final int G8 = 25;

    /**
     * function id for the user g9 function
     */
    public static final int G9 = 26;

    /**
     * function id for the user g10 function
     */
    public static final int G10 = 27;
    
    /**
     * function id for the user g11 function
     */
    public static final int G11 = 28;

    /**
     * function id for the user g12 function
     */
    public static final int G12 = 29;

    /**
     * function id for the user g13 function
     */
    public static final int G13 = 30;

    /**
     * function id for the user g14 function
     */
    public static final int G14 = 31;

    /**
     * function id for the user g15 function
     */
    public static final int G15 = 32;

    /**
     * function id for the user g16 function
     */
    public static final int G16 = 33;

    /**
     * function id for the user g17 function
     */
    public static final int G17 = 34;

    /**
     * function id for the user g18 function
     */
    public static final int G18 = 35;

    /**
     * function id for the user g19 function
     */
    public static final int G19 = 36;

    /**
     * function id for the user g20 function
     */
    public static final int G20 = 37;

    /**
     * the id of the function to apply to the argument
     */
    private int function;

    /**
     * the argument of the function
     */
    private ExpressionNode argument;

    /**
     * the second argument of the function
     */
    private ExpressionNode argument2;

    private final static String[] USER_FUNCS = {"g1", "g2", "g3", "g4", "g5", "g6", "g7", "g8", "g9", "g10", "g11", "g12", "g13", "g14", "g15", "g16", "g17", "g18", "g19", "g20"};

    private Method method;

    /**
     * Construct a function by id and argument.
     *
     * @param function the id of the function to apply
     * @param argument the first argument of the function
     * @param argument2 the second argument of the function
     */
    public Function2ArgumentsExpressionNode(int function, ExpressionNode argument, ExpressionNode argument2) {
        super();
        this.function = function;
        this.argument = argument;
        this.argument2 = argument2;

        if (function >= G1 && function <= (G1 + USER_FUNCS.length)) {
            getUserFunction();
        }
    }

    /**
     * Returns the type of the node, in this case
     * ExpressionNode.FUNCTION_2_ARG_NODE
     */
    public int getType() {
        return ExpressionNode.FUNCTION_2_ARG_NODE;
    }

    /**
     * Converts a string to a function id.
     *
     * If the function is not found this method throws an error.
     *
     * @param str the name of the function
     * @return the id of the function
     */
    public static int stringToFunction(String str) {
        if (str.equals("bipol")) {
            return Function2ArgumentsExpressionNode.TO_BIPOLAR;
        }

        if (str.equals("ibipol")) {
            return Function2ArgumentsExpressionNode.FROM_BIPOLAR;
        }

        if (str.equals("inflect")) {
            return Function2ArgumentsExpressionNode.INFLECTION;
        }

        if (str.equals("foldu")) {
            return Function2ArgumentsExpressionNode.FOLD_UP;
        }

        if (str.equals("foldd")) {
            return Function2ArgumentsExpressionNode.FOLD_DOWN;
        }

        if (str.equals("foldl")) {
            return Function2ArgumentsExpressionNode.FOLD_LEFT;
        }

        if (str.equals("foldr")) {
            return Function2ArgumentsExpressionNode.FOLD_RIGHT;
        }

        if (str.equals("foldi")) {
            return Function2ArgumentsExpressionNode.FOLD_IN;
        }

        if (str.equals("foldo")) {
            return Function2ArgumentsExpressionNode.FOLD_OUT;
        }

        if (str.equals("shear")) {
            return Function2ArgumentsExpressionNode.SHEAR;
        }

        if (str.equals("cmp")) {
            return Function2ArgumentsExpressionNode.COMPARE;
        }

        if (str.equals("add")) {
            return Function2ArgumentsExpressionNode.ADD;
        }

        if (str.equals("sub")) {
            return Function2ArgumentsExpressionNode.SUB;
        }

        if (str.equals("mul")) {
            return Function2ArgumentsExpressionNode.MUL;
        }

        if (str.equals("div")) {
            return Function2ArgumentsExpressionNode.DIV;
        }

        if (str.equals("rem")) {
            return Function2ArgumentsExpressionNode.REM;
        }

        if (str.equals("pow")) {
            return Function2ArgumentsExpressionNode.POW;
        }

        if (str.equals("logn")) {
            return Function2ArgumentsExpressionNode.LOGN;
        }

        for (int i = 0; i < USER_FUNCS.length; i++) {
            if (str.equals(USER_FUNCS[i])) {
                return Function2ArgumentsExpressionNode.G1 + i;
            }
        }

        throw new ParserException("Unexpected Function " + str + " found.");
    }

    /**
     * Returns a string with all the function names concatenated by a | symbol.
     *
     * This string is used in Tokenizer.createExpressionTokenizer to create a
     * regular expression for recognizing function names.
     *
     * @return a string containing all the function names
     */
    public static String getAllFunctions() {
        String temp_str = "";
        for (int i = 0; i < USER_FUNCS.length; i++) {
            temp_str += "|" + USER_FUNCS[i];
        }

        return "bipol|ibipol|inflect|foldu|foldd|foldl|foldr|foldi|foldo|shear|cmp|add|sub|mul|div|rem|pow|logn" + temp_str;
    }

    /**
     * Returns the value of the sub-expression that is rooted at this node.
     *
     * The argument is evaluated and then the function is applied to the
     * resulting value.
     */
    public Complex getValue() {
        switch (function) {

            case TO_BIPOLAR:
                return argument.getValue().toBiPolar(argument2.getValue());

            case FROM_BIPOLAR:
                return argument.getValue().fromBiPolar(argument2.getValue());

            case INFLECTION:
                return argument.getValue().inflection(argument2.getValue());

            case FOLD_UP:
                return argument.getValue().fold_up(argument2.getValue());

            case FOLD_DOWN:
                return argument.getValue().fold_down(argument2.getValue());

            case FOLD_LEFT:
                return argument.getValue().fold_left(argument2.getValue());

            case FOLD_RIGHT:
                return argument.getValue().fold_right(argument2.getValue());

            case FOLD_IN:
                return argument.getValue().fold_in(argument2.getValue());

            case FOLD_OUT:
                return argument.getValue().fold_out(argument2.getValue());

            case SHEAR:
                return argument.getValue().shear(argument2.getValue());

            case COMPARE:
                return new Complex(argument.getValue().compare(argument2.getValue()), 0);

            case ADD:
                return argument.getValue().plus(argument2.getValue());

            case SUB:
                return argument.getValue().sub(argument2.getValue());

            case MUL:
                return argument.getValue().times(argument2.getValue());

            case DIV:
                return argument.getValue().divide(argument2.getValue());

            case REM:
                return argument.getValue().remainder(argument2.getValue());

            case POW:
                return argument.getValue().pow(argument2.getValue());

            case LOGN:
                return argument.getValue().log().divide(argument2.getValue().log());

            case G1:
            case G2:
            case G3:
            case G4:
            case G5:
            case G6:
            case G7:
            case G8:
            case G9:
            case G10:
            case G11:
            case G12:
            case G13:
            case G14:
            case G15:
            case G16:
            case G17:
            case G18:
            case G19:
            case G20:
                try {
                    return (Complex) method.invoke(null, argument.getValue(), argument2.getValue());
                } catch (Exception ex) {
                    return new Complex();
                }

        }

        throw new EvaluationException("Invalid function id " + function + "!");
    }

    /**
     * Implementation of the visitor design pattern.
     *
     * Calls visit on the visitor and then passes the visitor on to the accept
     * method of the argument.
     *
     * @param visitor the visitor
     */
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
        argument.accept(visitor);
        argument2.accept(visitor);
    }

    private void getUserFunction() {

        try {
            int i = function - G1;
            File file = new File("");
            URL url = file.toURI().toURL();
            URLClassLoader loader = new URLClassLoader(new URL[]{url});
            Class<?> userClass = loader.loadClass("UserDefinedFunctions");
            method = userClass.getMethod("g" + (i + 1), Complex.class, Complex.class);

            if (!Modifier.isStatic(method.getModifiers())) {
                throw new ParserException("Method modifier error: Method " + method.getName() + " must be declared static.");
            }

            if (!method.getReturnType().equals(Complex.class)) {
                throw new ParserException("Return type error: Method " + method.getName() + " must have a Complex return type.");
            }
        } catch (NoSuchMethodException ex) {
            throw new ParserException("Method not found error: " + ex.getMessage());
        } catch (SecurityException ex) {
            throw new ParserException("Security error: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new ParserException("Illegal Argument error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            throw new ParserException("Class not found error: " + ex.getMessage());
        } catch (MalformedURLException ex) {
            throw new ParserException("File not found error: " + ex.getMessage());
        }

    }

}
