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
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 *
 * @author hrkalona2
 */
public class FunctionUser2ArgumentExpressionNode implements ExpressionNode {

    public static final int G_FUNCS = 60; //number of g functions
    public static final String FUNC_NAME = "g";

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
    
    private UserDefinedFunctionsInterface lambda;

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

        createUserFunction();
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

        for(int i = 0; i < G_FUNCS; i++) {
            if(str.equals(FUNC_NAME + (i + 1))) {
                return i;
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
        for(int i = 0; i < G_FUNCS; i++) {
            if(i == 0) {
                temp_str += (FUNC_NAME + (i + 1));
            }
            else {
                temp_str += "|" + (FUNC_NAME + (i + 1));
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
            return lambda.g(argument.getValue(), argument2.getValue());
        } catch (Exception ex) {
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

    private void createUserFunction() {

        try {
            Method method = Parser.userClass.getDeclaredMethod(FUNC_NAME + (function + 1), Complex.class, Complex.class);

            if (!Modifier.isStatic(method.getModifiers())) {
                throw new ParserException("Method modifier error: Method " + method.getName() + " must be declared static.");
            }

            if (!method.getReturnType().equals(Complex.class)) {
                throw new ParserException("Return type error: Method " + method.getName() + " must have a Complex return type.");
            }
            
            lambda = LambdaFactory.create(method, UserDefinedFunctionsInterface.class, FUNC_NAME);                       
        } catch (NoSuchMethodException ex) {
            throw new ParserException("Method not found error: " + Parser.sanitizeMessage(ex.getMessage()) +".\n"
                    + "If the function is missing from UserDefinedFunctions.java\nrename the old file, for backup, and restart the application.");
        } catch (Throwable ex) {
            throw new ParserException("Error: " + Parser.sanitizeMessage(ex.getMessage()));
        }

    }
}
