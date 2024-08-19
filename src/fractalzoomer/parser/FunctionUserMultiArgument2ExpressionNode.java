
package fractalzoomer.parser;

import fractalzoomer.core.Complex;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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
public class FunctionUserMultiArgument2ExpressionNode implements ExpressionNode {

    public static final int MAX_ARGUMENTS = 20;
    public static final int K_FUNCS = 60; //number of k functions
    public static final String FUNC_NAME = "k";

    /**
     * the id of the function to apply to the argument
     */
    private int function;

    /**
     * the argument of the function
     */
    private ExpressionNode[] arguments;

    private UserDefinedFunctionsInterface lambda;

    /**
     * Construct a function by id and argument.
     *
     * @param function the id of the function to apply
     * @param arguments the arguments of the function
     */
    public FunctionUserMultiArgument2ExpressionNode(int function, ExpressionNode[] arguments) {
        super();
        this.function = function;
        this.arguments = arguments;

        createUserFunction();

    }

    /**
     * Returns the type of the node, in this case
     * ExpressionNode.FUNCTION_USER_MULTI_ARG_NODE
     */
    @Override
    public int getType() {
        return ExpressionNode.FUNCTION_USER_MULTI_ARG_2_NODE;
    }

    /**
     * Converts a string to a function id.
     *
     * If the function is not found this method throws an error.
     *
     * @param stringInput the name of the function
     * @return the id of the function
     */
    public static int stringToFunction(String stringInput) {
        
        String str = stringInput.toLowerCase();

        for (int i = 0; i < K_FUNCS; i++) {
            if (str.equals(FUNC_NAME + (i + 1))) {
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
        for (int i = 0; i < K_FUNCS; i++) {
            if (i == 0) {
                temp_str += (FUNC_NAME + (i + 1));
            } else {
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
    @Override
    public Complex getValue() {

        try {
            return lambda.k(arguments[0].getValue(), arguments[1].getValue(), arguments[2].getValue(), arguments[3].getValue(), arguments[4].getValue(), arguments[5].getValue(), arguments[6].getValue(), arguments[7].getValue(), arguments[8].getValue(), arguments[9].getValue(), arguments[10].getValue(), arguments[11].getValue(), arguments[12].getValue(), arguments[13].getValue(), arguments[14].getValue(), arguments[15].getValue(), arguments[16].getValue(), arguments[17].getValue(), arguments[18].getValue(), arguments[19].getValue());
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
        for (int i = 0; i < arguments.length; i++) {
            arguments[i].accept(visitor);
        }
    }

    private void createUserFunction() {

        try {
            Method method = Parser.userClass.getDeclaredMethod(FUNC_NAME + (function + 1), Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class, Complex.class);

            if (!Modifier.isStatic(method.getModifiers())) {
                throw new ParserException("Method modifier error: Method " + method.getName() + " must be declared static.");
            }

            if (!method.getReturnType().equals(Complex.class)) {
                throw new ParserException("Return type error: Method " + method.getName() + " must have a Complex return type.");
            }
            
            lambda = LambdaFactory.create(Parser.lookup, method, UserDefinedFunctionsInterface.class, FUNC_NAME);
        } catch (NoSuchMethodException ex) {
            throw new ParserException("Method not found error: " + Parser.sanitizeMessage(ex.getMessage()) +".\n"
                    + "If the function is missing from UserDefinedFunctions.java\nrename the old file, for backup, and restart the application.");
        } catch (Throwable ex) {
            throw new ParserException("Error: " + Parser.sanitizeMessage(ex.getMessage()));
        }
    }
}
