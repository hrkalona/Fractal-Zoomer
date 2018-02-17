/*
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
/**
 * An ExpressionNode that handles mathematical functions with multiple arguments
 * defined by the user.
 *
 *
 */
public class FunctionUserMultiArgumentExpressionNode implements ExpressionNode {

    public static final int MAX_ARGUMENTS = 10;
    /**
     * function id for the user m1 function
     */
    public static final int M1 = 0;

    /**
     * function id for the user m2 function
     */
    public static final int M2 = 1;

    /**
     * function id for the user m3 function
     */
    public static final int M3 = 2;

    /**
     * function id for the user M4 function
     */
    public static final int M4 = 3;

    /**
     * function id for the user m5 function
     */
    public static final int M5 = 4;

    /**
     * function id for the user m6 function
     */
    public static final int M6 = 5;

    /**
     * function id for the user m7 function
     */
    public static final int M7 = 6;

    /**
     * function id for the user m8 function
     */
    public static final int M8 = 7;

    /**
     * function id for the user m9 function
     */
    public static final int M9 = 8;

    /**
     * function id for the user m10 function
     */
    public static final int M10 = 9;

    /**
     * function id for the user m11 function
     */
    public static final int M11 = 10;

    /**
     * function id for the user m12 function
     */
    public static final int M12 = 11;

    /**
     * function id for the user m13 function
     */
    public static final int M13 = 12;

    /**
     * function id for the user m14 function
     */
    public static final int M14 = 13;

    /**
     * function id for the user m15 function
     */
    public static final int M15 = 14;

    /**
     * function id for the user m16 function
     */
    public static final int M16 = 15;

    /**
     * function id for the user m17 function
     */
    public static final int M17 = 16;

    /**
     * function id for the user m18 function
     */
    public static final int M18 = 17;

    /**
     * function id for the user m19 function
     */
    public static final int M19 = 18;

    /**
     * function id for the user m20 function
     */
    public static final int M20 = 19;

    /**
     * function id for the user m21 function
     */
    public static final int M21 = 20;

    /**
     * function id for the user m22 function
     */
    public static final int M22 = 21;

    /**
     * function id for the user m23 function
     */
    public static final int M23 = 22;

    /**
     * function id for the user M24 function
     */
    public static final int M24 = 23;

    /**
     * function id for the user m25 function
     */
    public static final int M25 = 24;

    /**
     * function id for the user m26 function
     */
    public static final int M26 = 25;

    /**
     * function id for the user m27 function
     */
    public static final int M27 = 26;

    /**
     * function id for the user m28 function
     */
    public static final int M28 = 27;

    /**
     * function id for the user m29 function
     */
    public static final int M29 = 28;

    /**
     * function id for the user m30 function
     */
    public static final int M30 = 29;

    /**
     * function id for the user m31 function
     */
    public static final int M31 = 30;

    /**
     * function id for the user m32 function
     */
    public static final int M32 = 31;

    /**
     * function id for the user m33 function
     */
    public static final int M33 = 32;

    /**
     * function id for the user m34 function
     */
    public static final int M34 = 33;

    /**
     * function id for the user m35 function
     */
    public static final int M35 = 34;

    /**
     * function id for the user m36 function
     */
    public static final int M36 = 35;

    /**
     * function id for the user m37 function
     */
    public static final int M37 = 36;

    /**
     * function id for the user m38 function
     */
    public static final int M38 = 37;

    /**
     * function id for the user m39 function
     */
    public static final int M39 = 38;

    /**
     * function id for the user m40 function
     */
    public static final int M40 = 39;

    /**
     * the id of the function to apply to the argument
     */
    private int function;

    /**
     * the argument of the function
     */
    private ExpressionNode[] arguments;

    private final static String[] USER_FUNCS = {"m1", "m2", "m3", "m4", "m5", "m6", "m7", "m8", "m9", "m10", "m11", "m12", "m13", "m14", "m15", "m16", "m17", "m18", "m19", "m20",
        "m21", "m22", "m23", "m24", "m25", "m26", "m27", "m28", "m29", "m30", "m31", "m32", "m33", "m34", "m35", "m36", "m37", "m38", "m39", "m40"};

    private Method method;

    /**
     * Construct a function by id and argument.
     *
     * @param function the id of the function to apply
     * @param arguments the arguments of the function
     */
    public FunctionUserMultiArgumentExpressionNode(int function, ExpressionNode[] arguments) {
        super();
        this.function = function;
        this.arguments = arguments;

        getUserFunction();

    }

    /**
     * Returns the type of the node, in this case
     * ExpressionNode.FUNCTION_USER_MULTI_ARG_NODE
     */
    public int getType() {
        return ExpressionNode.FUNCTION_USER_MULTI_ARG_NODE;
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
                return FunctionUserMultiArgumentExpressionNode.M1 + i;
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
        Complex[] values = new Complex[arguments.length];

        for(int i = 0; i < arguments.length; i++) {
            if(arguments[i] == null) {
                values[i] = new Complex(); //zero arg default
            }
            else {
                values[i] = arguments[i].getValue();
            }
        }

        try {
            return (Complex)method.invoke(null, values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9]);
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
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
        for(int i = 0; i < arguments.length; i++) {
            if(arguments[i] != null) {
                arguments[i].accept(visitor);
            }
        }
    }

    private void getUserFunction() {

        try {
            int i = function - M1;
            File file = new File("");
            URL url = file.toURI().toURL();
            URLClassLoader loader = new URLClassLoader(new URL[] {url});
            Class<?> userClass = loader.loadClass("UserDefinedFunctions");
            method = userClass.getMethod("m" + (i + 1), Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class);

            if(!Modifier.isStatic(method.getModifiers())) {
                throw new ParserException("Method modifier error: Method " + method.getName() + " must be declared static.");
            }

            if(!method.getReturnType().equals(Complex.class)) {
                throw new ParserException("Return type error: Method " + method.getName() + " must have a Complex return type.");
            }
        }
        catch(NoSuchMethodException ex) {
            throw new ParserException("Method not found error: " + ex.getMessage());
        }
        catch(SecurityException ex) {
            throw new ParserException("Security error: " + ex.getMessage());
        }
        catch(IllegalArgumentException ex) {
            throw new ParserException("Illegal Argument error: " + ex.getMessage());
        }
        catch(ClassNotFoundException ex) {
            throw new ParserException("Class not found error: " + ex.getMessage());
        }
        catch(MalformedURLException ex) {
            throw new ParserException("File not found error: " + ex.getMessage());
        }

    }
}
