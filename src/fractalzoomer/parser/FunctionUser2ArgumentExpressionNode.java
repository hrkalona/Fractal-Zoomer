/*
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 *
 * @author hrkalona2
 */
public class FunctionUser2ArgumentExpressionNode implements ExpressionNode {

    /**
     * function id for the user g1 function
     */
    public static final int G1 = 0;

    /**
     * function id for the user g2 function
     */
    public static final int G2 = 1;

    /**
     * function id for the user g3 function
     */
    public static final int G3 = 2;

    /**
     * function id for the user g4 function
     */
    public static final int G4 = 3;

    /**
     * function id for the user g5 function
     */
    public static final int G5 = 4;

    /**
     * function id for the user g6 function
     */
    public static final int G6 = 5;

    /**
     * function id for the user g7 function
     */
    public static final int G7 = 6;

    /**
     * function id for the user g8 function
     */
    public static final int G8 = 7;

    /**
     * function id for the user g9 function
     */
    public static final int G9 = 8;

    /**
     * function id for the user g10 function
     */
    public static final int G10 = 9;

    /**
     * function id for the user g11 function
     */
    public static final int G11 = 10;

    /**
     * function id for the user g12 function
     */
    public static final int G12 = 11;

    /**
     * function id for the user g13 function
     */
    public static final int G13 = 12;

    /**
     * function id for the user g14 function
     */
    public static final int G14 = 13;

    /**
     * function id for the user g15 function
     */
    public static final int G15 = 14;

    /**
     * function id for the user g16 function
     */
    public static final int G16 = 15;

    /**
     * function id for the user g17 function
     */
    public static final int G17 = 16;

    /**
     * function id for the user g18 function
     */
    public static final int G18 = 17;

    /**
     * function id for the user g19 function
     */
    public static final int G19 = 18;

    /**
     * function id for the user g20 function
     */
    public static final int G20 = 19;

    /**
     * function id for the user g21 function
     */
    public static final int G21 = 20;

    /**
     * function id for the user g22 function
     */
    public static final int G22 = 21;

    /**
     * function id for the user g23 function
     */
    public static final int G23 = 22;

    /**
     * function id for the user g24 function
     */
    public static final int G24 = 23;

    /**
     * function id for the user g25 function
     */
    public static final int G25 = 24;

    /**
     * function id for the user g26 function
     */
    public static final int G26 = 25;

    /**
     * function id for the user g27 function
     */
    public static final int G27 = 26;

    /**
     * function id for the user g28 function
     */
    public static final int G28 = 27;

    /**
     * function id for the user g29 function
     */
    public static final int G29 = 28;

    /**
     * function id for the user g30 function
     */
    public static final int G30 = 29;

    /**
     * function id for the user g31 function
     */
    public static final int G31 = 30;

    /**
     * function id for the user g32 function
     */
    public static final int G32 = 31;

    /**
     * function id for the user g33 function
     */
    public static final int G33 = 32;

    /**
     * function id for the user g34 function
     */
    public static final int G34 = 33;

    /**
     * function id for the user g35 function
     */
    public static final int G35 = 34;

    /**
     * function id for the user g36 function
     */
    public static final int G36 = 35;

    /**
     * function id for the user g37 function
     */
    public static final int G37 = 36;

    /**
     * function id for the user g38 function
     */
    public static final int G38 = 37;

    /**
     * function id for the user g39 function
     */
    public static final int G39 = 38;

    /**
     * function id for the user g40 function
     */
    public static final int G40 = 39;

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

    private final static String[] USER_FUNCS = {"g1", "g2", "g3", "g4", "g5", "g6", "g7", "g8", "g9", "g10", "g11", "g12", "g13", "g14", "g15", "g16", "g17", "g18", "g19", "g20", "g21", "g22", "g23", "g24", "g25", "g26", "g27", "g28", "g29", "g30", "g31", "g32", "g33", "g34", "g35", "g36", "g37", "g38", "g39", "g40"};

    private Method method;

    /**
     * Construct a function by id and argument.
     *
     * @param function the id of the function to apply
     * @param argument the first argument of the function
     * @param argument2 the second argument of the function
     */
    public FunctionUser2ArgumentExpressionNode(int function, ExpressionNode argument, ExpressionNode argument2) {
        super();
        this.function = function;
        this.argument = argument;
        this.argument2 = argument2;

        getUserFunction();
    }

    /**
     * Returns the type of the node, in this case
     * ExpressionNode.FUNCTION_USER_TWO_ARG_NODE
     */
    public int getType() {
        return ExpressionNode.FUNCTION_USER_TWO_ARG_NODE;
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

        for(int i = 0; i < USER_FUNCS.length; i++) {
            if(str.equals(USER_FUNCS[i])) {
                return FunctionUser2ArgumentExpressionNode.G1 + i;
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
        for(int i = 0; i < USER_FUNCS.length; i++) {
            if(i == 0) {
                temp_str += USER_FUNCS[i];
            }
            else {
                temp_str += "|" + USER_FUNCS[i];
            }
        }

        return temp_str;
    }

    /**
     * Returns the value of the sub-expression that is rooted at this node.
     *
     * The argument is evaluated and then the function is applied to the
     * resulting value.
     */
    public Complex getValue() {

        try {
            return (Complex)method.invoke(null, argument.getValue(), argument2.getValue());
        }
        catch(Exception ex) {
            return new Complex();
        }

        //throw new EvaluationException("Invalid function id " + function + "!");
    }

    /**
     * Implementation of the visitor design pattern.
     *
     * Calls visit on the visitor and then passes the visitor on to the accept
     * method of the argument.
     *
     * @param visitor the visitor
     */
    @Override
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
