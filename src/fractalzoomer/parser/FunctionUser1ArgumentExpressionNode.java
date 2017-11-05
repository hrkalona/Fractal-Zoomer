package fractalzoomer.parser;

import fractalzoomer.core.Complex;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

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
/**
 *
 * @author hrkalona2
 */
public class FunctionUser1ArgumentExpressionNode implements ExpressionNode {

    /**
     * function id for the user f1 function
     */
    public static final int F1 = 0;

    /**
     * function id for the user f2 function
     */
    public static final int F2 = 1;

    /**
     * function id for the user f3 function
     */
    public static final int F3 = 2;

    /**
     * function id for the user f4 function
     */
    public static final int F4 = 3;

    /**
     * function id for the user f5 function
     */
    public static final int F5 = 4;

    /**
     * function id for the user f6 function
     */
    public static final int F6 = 5;

    /**
     * function id for the user f7 function
     */
    public static final int F7 = 6;

    /**
     * function id for the user f8 function
     */
    public static final int F8 = 7;

    /**
     * function id for the user f9 function
     */
    public static final int F9 = 8;

    /**
     * function id for the user f10 function
     */
    public static final int F10 = 9;

    /**
     * function id for the user f11 function
     */
    public static final int F11 = 10;

    /**
     * function id for the user f12 function
     */
    public static final int F12 = 11;

    /**
     * function id for the user f13 function
     */
    public static final int F13 = 12;

    /**
     * function id for the user f14 function
     */
    public static final int F14 = 13;

    /**
     * function id for the user f15 function
     */
    public static final int F15 = 14;

    /**
     * function id for the user f16 function
     */
    public static final int F16 = 15;

    /**
     * function id for the user f17 function
     */
    public static final int F17 = 16;

    /**
     * function id for the user f18 function
     */
    public static final int F18 = 17;

    /**
     * function id for the user f19 function
     */
    public static final int F19 = 18;

    /**
     * function id for the user f20 function
     */
    public static final int F20 = 19;

    /**
     * function id for the user f21 function
     */
    public static final int F21 = 20;

    /**
     * function id for the user f22 function
     */
    public static final int F22 = 21;

    /**
     * function id for the user f23 function
     */
    public static final int F23 = 22;

    /**
     * function id for the user f24 function
     */
    public static final int F24 = 23;

    /**
     * function id for the user f5 function
     */
    public static final int F25 = 24;

    /**
     * function id for the user f26 function
     */
    public static final int F26 = 25;

    /**
     * function id for the user f27 function
     */
    public static final int F27 = 26;

    /**
     * function id for the user f8 function
     */
    public static final int F28 = 27;

    /**
     * function id for the user f29 function
     */
    public static final int F29 = 28;

    /**
     * function id for the user f30 function
     */
    public static final int F30 = 29;

    /**
     * function id for the user f31 function
     */
    public static final int F31 = 30;

    /**
     * function id for the user f32 function
     */
    public static final int F32 = 31;

    /**
     * function id for the user f33 function
     */
    public static final int F33 = 32;

    /**
     * function id for the user f34 function
     */
    public static final int F34 = 33;

    /**
     * function id for the user f35 function
     */
    public static final int F35 = 34;

    /**
     * function id for the user f36 function
     */
    public static final int F36 = 35;

    /**
     * function id for the user f37 function
     */
    public static final int F37 = 36;

    /**
     * function id for the user f38 function
     */
    public static final int F38 = 37;

    /**
     * function id for the user f39 function
     */
    public static final int F39 = 38;

    /**
     * function id for the user f40 function
     */
    public static final int F40 = 39;

    private Method method;

    /**
     * the id of the function to apply to the argument
     */
    private int function;

    /**
     * the argument of the function
     */
    private ExpressionNode argument;

    private final static String[] USER_FUNCS = {"f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "f10", "f11", "f12", "f13", "f14", "f15", "f16", "f17", "f18", "f19", "f20", "f21", "f22", "f23", "f24", "f25", "f26", "f27", "f28", "f29", "f30", "f31", "f32", "f33", "f34", "f35", "f36", "f37", "f38", "f39", "f40"};

    /**
     * Construct a function by id and argument.
     *
     * @param function the id of the function to apply
     * @param argument the argument of the function
     */
    public FunctionUser1ArgumentExpressionNode(int function, ExpressionNode argument) {
        super();
        this.function = function;
        this.argument = argument;

        getUserFunction();
    }
    
    /**
     * Returns the type of the node, in this case ExpressionNode.FUNCTION_USER_ONE_ARG_NODE
     */
    public int getType() {
        return ExpressionNode.FUNCTION_USER_ONE_ARG_NODE;
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
                return FunctionUser1ArgumentExpressionNode.F1 + i;
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
    @Override
    public Complex getValue() {

        try {
            return (Complex)method.invoke(null, argument.getValue());
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
        argument.accept(visitor);
    }
    
    private void getUserFunction() {

        try {
            int i = function - F1;
            File file = new File("");
            URL url = file.toURI().toURL();
            URLClassLoader loader = new URLClassLoader(new URL[]{url});
            Class<?> userClass = loader.loadClass("UserDefinedFunctions");
            method = userClass.getMethod("f" + (i + 1), Complex.class);

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
