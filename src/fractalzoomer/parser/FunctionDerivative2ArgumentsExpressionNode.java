/*
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
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
import fractalzoomer.core.Derivative;

/**
 * An ExpressionNode that handles function derivatives with 2 arguments.
 *
 * Some pre-defined functions are handled, others can easily be added.
 */
public class FunctionDerivative2ArgumentsExpressionNode implements ExpressionNode {


    /**
     * function id for the first derivative function
     */
    public static final int FIRST_DERIVATIVE = 0;

    /**
     * function id for the second derivative function
     */
    public static final int SECOND_DERIVATIVE = 1;

    /**
     * function id for the third derivative function
     */
    public static final int THIRD_DERIVATIVE = 2;

    public static int USER_FORMULA_DERIVATIVE_METHOD = Derivative.NUMERICAL_CENTRAL;

    /**
     * the function to apply to the arguments
     */
    private int functionId;

    /**
     * the argument of the function
     */
    private ExpressionNode argument;

    /**
     * the second argument of the function
     */
    private ExpressionNode argument2;

    private SetVariable visitor;

    /**
     * Construct a function by id and argument.
     *
     * @param functionId the id of the function to apply
     * @param argument the first argument of the function
     * @param argument2 the second argument of the function
     */
    public FunctionDerivative2ArgumentsExpressionNode(int functionId, ExpressionNode argument, ExpressionNode argument2) {
        super();
        this.functionId = functionId;
        this.argument = argument;
        this.argument2 = argument2;
        visitor = new SetVariable(((VariableExpressionNode)argument2).getName(), null);
    }

    /**
     * Returns the type of the node, in this case
     * ExpressionNode.FUNCTION_2_ARG_NODE
     */
    @Override
    public int getType() {
        return ExpressionNode.FUNCTION_DERIVATIVE_2_ARG_NODE;
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

        if(str.equals("f'")) {
            return FunctionDerivative2ArgumentsExpressionNode.FIRST_DERIVATIVE;
        }

        if(str.equals("f''")) {
            return FunctionDerivative2ArgumentsExpressionNode.SECOND_DERIVATIVE;
        }

        if(str.equals("f'''")) {
            return FunctionDerivative2ArgumentsExpressionNode.THIRD_DERIVATIVE;
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
        return "f'''|f''|f'";
    }

    /**
     * Returns the value of the sub-expression that is rooted at this node.
     *
     * The argument is evaluated and then the function is applied to the
     * resulting value.
     */
    @Override
    public Complex getValue() {

        Complex secondArgument = new Complex(argument2.getValue());

        if(functionId == FIRST_DERIVATIVE) {
            if(USER_FORMULA_DERIVATIVE_METHOD == Derivative.NUMERICAL_CENTRAL) {
                visitor.setValue(secondArgument.plus(Derivative.DZ));
                setDerivativeDelta(visitor);
                Complex fzdz = argument.getValue();

                visitor.setValue(secondArgument.sub(Derivative.DZ));
                setDerivativeDelta(visitor);
                Complex fzmdz = argument.getValue();

                return Derivative.numericalCentralDerivativeFirstOrder(fzdz, fzmdz);
            }
            else if(USER_FORMULA_DERIVATIVE_METHOD == Derivative.NUMERICAL_FORWARD) {
                Complex fz = argument.getValue();

                visitor.setValue(secondArgument.plus(Derivative.DZ));
                setDerivativeDelta(visitor);
                Complex fzdz = argument.getValue();

                return Derivative.numericalForwardDerivativeFirstOrder(fz, fzdz);
            }
            else if(USER_FORMULA_DERIVATIVE_METHOD == Derivative.NUMERICAL_BACKWARD) {
                Complex fz = argument.getValue();

                visitor.setValue(secondArgument.sub(Derivative.DZ));
                setDerivativeDelta(visitor);
                Complex fzmdz = argument.getValue();

                return Derivative.numericalBackwardDerivativeFirstOrder(fz, fzmdz);
            }
        }
        else if (functionId == SECOND_DERIVATIVE) {
            if(USER_FORMULA_DERIVATIVE_METHOD == Derivative.NUMERICAL_CENTRAL) {
                Complex fz = argument.getValue();

                visitor.setValue(secondArgument.plus(Derivative.DZ));
                setDerivativeDelta(visitor);
                Complex fzdz = argument.getValue();

                visitor.setValue(secondArgument.sub(Derivative.DZ));
                setDerivativeDelta(visitor);
                Complex fzmdz = argument.getValue();

                return Derivative.numericalCentralDerivativeSecondOrder(fz, fzdz, fzmdz);
            }
            else if(USER_FORMULA_DERIVATIVE_METHOD == Derivative.NUMERICAL_FORWARD) {
                Complex fz = argument.getValue();

                visitor.setValue(secondArgument.plus(Derivative.DZ));
                setDerivativeDelta(visitor);
                Complex fzdz = argument.getValue();

                visitor.setValue(secondArgument.plus(Derivative.DZ_2));
                setDerivativeDelta(visitor);
                Complex fz2dz = argument.getValue();

                return Derivative.numericalForwardDerivativeSecondOrder(fz, fzdz, fz2dz);
            }
            else if(USER_FORMULA_DERIVATIVE_METHOD == Derivative.NUMERICAL_BACKWARD) {
                Complex fz = argument.getValue();

                visitor.setValue(secondArgument.sub(Derivative.DZ));
                setDerivativeDelta(visitor);
                Complex fzmdz = argument.getValue();

                visitor.setValue(secondArgument.sub(Derivative.DZ_2));
                setDerivativeDelta(visitor);
                Complex fzm2dz = argument.getValue();

                return Derivative.numericalBackwardDerivativeSecondOrder(fz, fzmdz, fzm2dz);
            }
        }
        else if (functionId == THIRD_DERIVATIVE) {
            if(USER_FORMULA_DERIVATIVE_METHOD == Derivative.NUMERICAL_CENTRAL) {
                visitor.setValue(secondArgument.plus(Derivative.DZ));
                setDerivativeDelta(visitor);
                Complex fzdz = argument.getValue();

                visitor.setValue(secondArgument.plus(Derivative.DZ_2));
                setDerivativeDelta(visitor);
                Complex fz2dz = argument.getValue();

                visitor.setValue(secondArgument.sub(Derivative.DZ));
                setDerivativeDelta(visitor);
                Complex fzmdz = argument.getValue();

                visitor.setValue(secondArgument.sub(Derivative.DZ_2));
                setDerivativeDelta(visitor);
                Complex fzm2dz = argument.getValue();

                return Derivative.numericalCentralDerivativeThirdOrder(fzdz, fz2dz, fzmdz, fzm2dz);
            }
            else if(USER_FORMULA_DERIVATIVE_METHOD == Derivative.NUMERICAL_FORWARD) {
                Complex fz = argument.getValue();

                visitor.setValue(secondArgument.plus(Derivative.DZ));
                setDerivativeDelta(visitor);
                Complex fzdz = argument.getValue();

                visitor.setValue(secondArgument.plus(Derivative.DZ_2));
                setDerivativeDelta(visitor);
                Complex fz2dz = argument.getValue();

                visitor.setValue(secondArgument.plus(Derivative.DZ_3));
                setDerivativeDelta(visitor);
                Complex fz3dz = argument.getValue();

                return Derivative.numericalForwardDerivativeThirdOrder(fz, fzdz, fz2dz, fz3dz);
            }
            else if(USER_FORMULA_DERIVATIVE_METHOD == Derivative.NUMERICAL_BACKWARD) {
                Complex fz = argument.getValue();

                visitor.setValue(secondArgument.sub(Derivative.DZ));
                setDerivativeDelta(visitor);
                Complex fzmdz = argument.getValue();

                visitor.setValue(secondArgument.sub(Derivative.DZ_2));
                setDerivativeDelta(visitor);
                Complex fzm2dz = argument.getValue();

                visitor.setValue(secondArgument.sub(Derivative.DZ_3));
                setDerivativeDelta(visitor);
                Complex fzm3dz = argument.getValue();

                return Derivative.numericalBackwardDerivativeThirdOrder(fz, fzmdz, fzm2dz, fzm3dz);
            }
        }

        return new Complex();
        
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

    public void setDerivativeDelta(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
        argument.accept(visitor);
    }
}
