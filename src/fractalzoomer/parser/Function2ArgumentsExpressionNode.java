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
import fractalzoomer.parser.functions.*;

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
     * function id for the fuzz function
     */
    public static final int FUZZ = 18;
    
    /**
     * function id for the norm-n function
     */
    public static final int NORMN = 19;
    
    /**
     * function id for the rotation function
     */
    public static final int ROT = 20;
    
    /**
     * function id for the distance function
     */
    public static final int DIST = 21;
    
    /**
     * function id for the squared distance function
     */
    public static final int SDIST = 22;

    /**
     * function id for the Nth root function
     */
    public static final int ROOT = 23;

    /**
     * the function to apply to the arguments
     */
    private AbstractTwoArgumentFunction function;
    private int functionId;

    /**
     * the argument of the function
     */
    private ExpressionNode argument;

    /**
     * the second argument of the function
     */
    private ExpressionNode argument2;

    /**
     * Construct a function by id and argument.
     *
     * @param functionId the id of the function to apply
     * @param argument the first argument of the function
     * @param argument2 the second argument of the function
     */
    public Function2ArgumentsExpressionNode(int functionId, ExpressionNode argument, ExpressionNode argument2) {
        super();
        this.functionId = functionId;
        this.argument = argument;
        this.argument2 = argument2;
        
        switch (functionId) {

            case TO_BIPOLAR:
                function = new ToBipolarFunction();
                break;

            case FROM_BIPOLAR:
                function = new FromBipolarFunction();
                break;

            case INFLECTION:
                function = new InflectFunction();
                break;

            case FOLD_UP:
                function = new FoldUpFunction();
                break;

            case FOLD_DOWN:
                function = new FoldDownFunction();
                break;

            case FOLD_LEFT:
                function = new FoldLeftFunction();
                break;

            case FOLD_RIGHT:
                function = new FoldRightFunction();
                break;

            case FOLD_IN:
                function = new FoldInFunction();
                break;

            case FOLD_OUT:
                function = new FoldOutFunction();
                break;

            case SHEAR:
                function = new ShearFunction();
                break;

            case COMPARE:
                function = new CompareFunction();
                break;

            case ADD:
                function = new AddFunction();
                break;

            case SUB:
                function = new SubFunction();
                break;

            case MUL:
                function = new MulFunction();
                break;

            case DIV:
                function = new DivFunction();
                break;

            case REM:
                function = new RemFunction();
                break;

            case POW:
                function = new PowFunction();
                break;

            case LOGN:
                function = new LogNFunction();
                break;
                
            case FUZZ:
                function = new FuzzFunction();
                break;
                
            case NORMN:
                function = new NormNFunction();
                break;
                
            case ROT:
                function = new RotFunction();
                break;
                
            case DIST:
                function = new DistanceFunction();
                break;
                
            case SDIST:
                function = new DistanceSquaredFunction();
                break;

            case ROOT:
                function = new RootFunction();
                break;

        }
    }

    /**
     * Returns the type of the node, in this case
     * ExpressionNode.FUNCTION_2_ARG_NODE
     */
    @Override
    public int getType() {
        return ExpressionNode.FUNCTION_2_ARG_NODE;
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
        
        if (str.equals("fuzz")) {
            return Function2ArgumentsExpressionNode.FUZZ;
        }
        
        if (str.equals("normn")) {
            return Function2ArgumentsExpressionNode.NORMN;
        } 
        
        if (str.equals("rot")) {
            return Function2ArgumentsExpressionNode.ROT;
        }
        
        if (str.equals("dist")) {
            return Function2ArgumentsExpressionNode.DIST;
        } 
        
        if (str.equals("sdist")) {
            return Function2ArgumentsExpressionNode.SDIST;
        }

        if (str.equals("root")) {
            return Function2ArgumentsExpressionNode.ROOT;
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
        return "bipol|ibipol|inflect|foldu|foldd|foldl|foldr|foldi|foldo|shear|cmp|add|sub|mul|div|rem|pow|logn|fuzz|normn|rot|dist|sdist|root";
    }

    /**
     * Returns the value of the sub-expression that is rooted at this node.
     *
     * The argument is evaluated and then the function is applied to the
     * resulting value.
     */
    @Override
    public Complex getValue() {
 
        return function.evaluate(argument.getValue(), argument2.getValue());
        
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
}
