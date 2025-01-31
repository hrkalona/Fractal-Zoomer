
package fractalzoomer.parser;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.ColorAlgorithm;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * A parser for mathematical expressions. The parser class defines a method
 * parse() which takes a string and returns an ExpressionNode that holds a
 * representation of the expression.
 *
 * Parsing is implemented in the form of a recursive descent parser.
 *
 */

/*
The defined grammar:

expression -> signed_term sum_op
signed_term -> PLUSMINUS signed_term
signed_term -> term
sum_op -> PLUSMINUS signed_term sum_op
sum_op -> EPSILON
term -> factor term_op
factor -> argument factor_op
term_op -> MULTDIV signed_factor term_op
term_op -> EPSILON
signed_factor -> PLUSMINUS signed_factor
signed_factor -> factor
factor_op -> RAISED signed_factor
factor_op -> EPSILON
argument -> FUNCTION function_argument
argument -> FUNCTION_2ARG function_argument2
argument -> FUNCTION_DERIVATIVE_2_ARG function_argument2
argument -> FUNCTION_USER_1_ARG function_argument
argument -> FUNCTION_USER_2_ARG function_argument2
argument -> FUNCTION_USER_MULTI_ARG function_max_multi_arg
argument -> FUNCTION_USER_MULTI_2_ARG function_max_multi_arg
argument -> OPEN_BRACKET expression CLOSE_BRACKET
argument -> value
function_argument -> OPEN_BRACKET expression CLOSE_BRACKET
function_argument2 -> OPEN_BRACKET expression COMMA expression CLOSE_BRACKET
function_max_multi_arg -> OPEN_BRACKET expression more_args CLOSE_BRACKET
more_args -> COMMA expression more_args
more_args -> EPSILON
value -> REAL
value -> IMAGINARY
value -> VARIABLE

*/
public class Parser {

    public static URLClassLoader loader;
    public static final String DEFAULT_USER_CODE_FILE = "UserDefinedFunctions.java";
    public static final String DEFAULT_USER_CODE_CLASS = "UserDefinedFunctions";

    public static final String SAVED_USER_CODE_FILE = "SavedUserDefinedFunctions.java";
    public static final String SAVED_USER_CODE_CLASS = "SavedUserDefinedFunctions";

    public static String CURRENT_USER_CODE_FILE = DEFAULT_USER_CODE_FILE;
    public static String CURRENT_USER_CODE_CLASS = DEFAULT_USER_CODE_CLASS;
    public static Class<?> userClass;
    public static MethodHandles.Lookup lookup;
    public static boolean usesUserCode = false;
    private boolean parseOnlyMode;
    
    public Parser() {
        parseOnlyMode = false;
    }
    
    public Parser(boolean parserOnlyMode) {
        this.parseOnlyMode = parserOnlyMode;
    }

    /**
     * the tokens to parse
     */
    LinkedList<Token> tokens;
    /**
     * the next token
     */
    Token lookahead;

    /* Implemented for fractal zoomer purposes */
    public static final int EXTRA_VARS = 30;
    boolean found_z;
    boolean found_c;
    boolean found_n;

    boolean found_nf;
    boolean found_p;
    boolean found_s;
    boolean found_pp;
    boolean found_bail;
    boolean found_cbail;
    boolean found_maxn;
    boolean found_center;
    boolean found_size;
    boolean found_point;
    boolean found_isize;

    boolean found_width;

    boolean found_height;
    boolean found_r;
    boolean found_stat;
    boolean found_trap;
    boolean found_c0;
    boolean found_pixel;

    boolean[] found_vars;
    boolean found_any_var;

    ArrayList<VariableExpressionNode> z_var;
    ArrayList<VariableExpressionNode> c_var;
    ArrayList<VariableExpressionNode> n_var;

    ArrayList<VariableExpressionNode> nf_var;
    ArrayList<VariableExpressionNode> p_var;
    ArrayList<VariableExpressionNode> s_var;
    ArrayList<VariableExpressionNode> pp_var;
    ArrayList<VariableExpressionNode> bail_var;
    ArrayList<VariableExpressionNode> cbail_var;
    ArrayList<VariableExpressionNode> maxn_var;
    ArrayList<VariableExpressionNode> center_var;
    ArrayList<VariableExpressionNode> size_var;
    ArrayList<VariableExpressionNode> isize_var;

    ArrayList<VariableExpressionNode> width_var;
    ArrayList<VariableExpressionNode> height_var;
    ArrayList<ArrayList<VariableExpressionNode>> vars_var;
    ArrayList<VariableExpressionNode> point_var;
    ArrayList<VariableExpressionNode> r_var;
    ArrayList<VariableExpressionNode> stat_var;
    ArrayList<VariableExpressionNode> trap_var;
    ArrayList<VariableExpressionNode> c0_var;
    ArrayList<VariableExpressionNode> pixel_var;

    VariableExpressionNode[] z_var_arr;
    VariableExpressionNode[] c_var_arr;
    VariableExpressionNode[] n_var_arr;

    VariableExpressionNode[] nf_var_arr;
    VariableExpressionNode[] p_var_arr;
    VariableExpressionNode[] s_var_arr;
    VariableExpressionNode[] pp_var_arr;
    VariableExpressionNode[] bail_var_arr;
    VariableExpressionNode[] cbail_var_arr;
    VariableExpressionNode[] maxn_var_arr;
    VariableExpressionNode[] center_var_arr;
    VariableExpressionNode[] size_var_arr;
    VariableExpressionNode[] isize_var_arr;
    VariableExpressionNode[] width_var_arr;
    VariableExpressionNode[] height_var_arr;
    VariableExpressionNode[][] vars_var_arr;
    VariableExpressionNode[] point_var_arr;
    VariableExpressionNode[] r_var_arr;
    VariableExpressionNode[] stat_var_arr;
    VariableExpressionNode[] trap_var_arr;
    VariableExpressionNode[] c0_var_arr;
    VariableExpressionNode[] pixel_var_arr;

    /**
     * ************************************
     */
    /**
     * Parse a mathematical expression in a string and return an ExpressionNode.
     *
     * This is a convenience method that first converts the string into a linked
     * list of tokens using the expression tokenizer provided by the Tokenizer
     * class.
     *
     * @param expression the string holding the input
     * @return the internal representation of the expression in form of an
     * expression tree made out of ExpressionNode objects
     */
    public ExpressionNode parse(String expression) {
        found_z = false;
        found_c = false;
        found_n = false;
        found_nf = false;
        found_p = false;
        found_s = false;
        found_pp = false;
        found_bail = false;
        found_cbail = false;
        found_maxn = false;
        found_center = false;
        found_size = false;
        found_isize = false;
        found_width = false;
        found_height = false;
        found_point = false;
        found_r = false;
        found_stat = false;
        found_trap = false;
        found_c0 = false;
        found_pixel = false;

        found_vars = new boolean[EXTRA_VARS];
        found_any_var = false;

        for(int i = 0; i < found_vars.length; i++) {
            found_vars[i] = false;
        }

        z_var = new ArrayList<>();
        c_var = new ArrayList<>();
        n_var = new ArrayList<>();
        nf_var = new ArrayList<>();
        p_var = new ArrayList<>();
        s_var = new ArrayList<>();
        pp_var = new ArrayList<>();
        bail_var = new ArrayList<>();
        cbail_var = new ArrayList<>();
        maxn_var = new ArrayList<>();
        center_var = new ArrayList<>();
        size_var = new ArrayList<>();
        isize_var = new ArrayList<>();
        width_var = new ArrayList<>();
        height_var = new ArrayList<>();
        vars_var = new ArrayList<>();
        r_var = new ArrayList<>();
        stat_var = new ArrayList<>();
        trap_var = new ArrayList<>();
        c0_var = new ArrayList<>();
        pixel_var = new ArrayList<>();

        for(int i = 0; i < EXTRA_VARS; i++) {
            vars_var.add(new ArrayList<>());
        }

        point_var = new ArrayList<>();

        Tokenizer tokenizer = Tokenizer.getExpressionTokenizer();
        tokenizer.tokenize(expression);
        LinkedList<Token> tokens = tokenizer.getTokens();
        return this.parse(tokens);
    }

    /**
     * Parse a mathematical expression in contained in a list of tokens and
     * return an ExpressionNode.
     *
     * @param tokens a list of tokens holding the tokenized input
     * @return the internal representation of the expression in form of an
     * expression tree made out of ExpressionNode objects
     */
    public ExpressionNode parse(LinkedList<Token> tokens) {
        // implementing a recursive descent parser
        this.tokens = (LinkedList<Token>)tokens.clone();

        try {
            lookahead = this.tokens.getFirst();
        }
        catch(NoSuchElementException ex) {
            throw new ParserException("No input found.");
        }

        // top level non-terminal is expression
        ExpressionNode expr = expression();

        if(lookahead.token != Token.EPSILON) {
            throw new ParserException("Unexpected symbol %s found.", lookahead);
        }

        z_var_arr = new VariableExpressionNode[z_var.size()];
        c_var_arr = new VariableExpressionNode[c_var.size()];
        n_var_arr = new VariableExpressionNode[n_var.size()];
        nf_var_arr = new VariableExpressionNode[nf_var.size()];
        p_var_arr = new VariableExpressionNode[p_var.size()];
        s_var_arr = new VariableExpressionNode[s_var.size()];
        pp_var_arr = new VariableExpressionNode[pp_var.size()];
        bail_var_arr = new VariableExpressionNode[bail_var.size()];
        cbail_var_arr = new VariableExpressionNode[cbail_var.size()];
        maxn_var_arr = new VariableExpressionNode[maxn_var.size()];
        center_var_arr = new VariableExpressionNode[center_var.size()];
        size_var_arr = new VariableExpressionNode[size_var.size()];
        isize_var_arr = new VariableExpressionNode[isize_var.size()];
        width_var_arr = new VariableExpressionNode[width_var.size()];
        height_var_arr = new VariableExpressionNode[height_var.size()];
        point_var_arr = new VariableExpressionNode[point_var.size()];
        r_var_arr = new VariableExpressionNode[r_var.size()];
        stat_var_arr = new VariableExpressionNode[stat_var.size()];
        trap_var_arr = new VariableExpressionNode[trap_var.size()];
        c0_var_arr = new VariableExpressionNode[c0_var.size()];
        pixel_var_arr = new VariableExpressionNode[pixel_var.size()];

        z_var_arr = z_var.toArray(z_var_arr);
        c_var_arr = c_var.toArray(c_var_arr);
        n_var_arr = n_var.toArray(n_var_arr);
        nf_var_arr = nf_var.toArray(nf_var_arr);
        p_var_arr = p_var.toArray(p_var_arr);
        s_var_arr = s_var.toArray(s_var_arr);
        pp_var_arr = pp_var.toArray(pp_var_arr);
        bail_var_arr = bail_var.toArray(bail_var_arr);
        cbail_var_arr = cbail_var.toArray(cbail_var_arr);
        maxn_var_arr = maxn_var.toArray(maxn_var_arr);
        center_var_arr = center_var.toArray(center_var_arr);
        size_var_arr = size_var.toArray(size_var_arr);
        isize_var_arr = isize_var.toArray(isize_var_arr);
        width_var_arr = width_var.toArray(width_var_arr);
        height_var_arr = height_var.toArray(height_var_arr);
        point_var_arr = point_var.toArray(point_var_arr);
        r_var_arr = r_var.toArray(r_var_arr);
        stat_var_arr = stat_var.toArray(stat_var_arr);
        trap_var_arr = trap_var.toArray(trap_var_arr);
        c0_var_arr = c0_var.toArray(c0_var_arr);
        pixel_var_arr = pixel_var.toArray(pixel_var_arr);

        vars_var_arr = new VariableExpressionNode[EXTRA_VARS][];

        for(int i = 0; i < vars_var_arr.length; i++) {
            vars_var_arr[i] = new VariableExpressionNode[vars_var.get(i).size()];
            vars_var_arr[i] = vars_var.get(i).toArray(vars_var_arr[i]);
        }

        return expr;
    }

    /**
     * handles the non-terminal expression
     */
    private ExpressionNode expression() {
        // only one rule
        // expression -> signed_term sum_op
        ExpressionNode expr = signedTerm();
        expr = sumOp(expr);
        return expr;
    }

    /**
     * handles the non-terminal sum_op
     */
    private ExpressionNode sumOp(ExpressionNode expr) {
        // sum_op -> PLUSMINUS signed_term sum_op
        if(lookahead.token == Token.PLUSMINUS) {
            AdditionExpressionNode sum;
            // This means we are actually dealing with a sum
            // If expr is not already a sum, we have to create one
            if(expr.getType() == ExpressionNode.ADDITION_NODE) {
                sum = (AdditionExpressionNode)expr;
            }
            else {
                sum = new AdditionExpressionNode(expr, AdditionExpressionNode.ADD);
            }

            // reduce the input and recursively call sum_op
            int mode = lookahead.sequence.equals("+") ? AdditionExpressionNode.ADD : AdditionExpressionNode.SUB;
            nextToken();
            ExpressionNode t = signedTerm();
            sum.add(t, mode);

            return sumOp(sum);
        }

        // sum_op -> EPSILON
        return expr;
    }

    /**
     * handles the non-terminal signed_term
     */
    private ExpressionNode signedTerm() {
        // signed_term -> PLUSMINUS signed_term
        if(lookahead.token == Token.PLUSMINUS) {
            int mode = lookahead.sequence.equals("+") ? AdditionExpressionNode.ADD : AdditionExpressionNode.SUB;
            nextToken();
            ExpressionNode t = signedTerm();
            if(mode == AdditionExpressionNode.ADD) {
                return t;
            }
            else {
                return new AdditionExpressionNode(t, AdditionExpressionNode.SUB);
            }
        }

        // signed_term -> term
        return term();
    }

    /**
     * handles the non-terminal term
     */
    private ExpressionNode term() {
        // term -> factor term_op
        ExpressionNode f = factor();
        return termOp(f);
    }

    /**
     * handles the non-terminal term_op
     */
    private ExpressionNode termOp(ExpressionNode expression) {
        // term_op -> MULTDIV signed_factor term_op
        if(lookahead.token == Token.MULTDIVREM) {
            MultiplicationExpressionNode prod;

            // This means we are actually dealing with a product
            // If expr is not already a product, we have to create one
            if(expression.getType() == ExpressionNode.MULTIPLICATION_NODE) {
                prod = (MultiplicationExpressionNode)expression;
            }
            else {
                prod = new MultiplicationExpressionNode(expression, MultiplicationExpressionNode.MULT);
            }

            // reduce the input and recursively call sum_op
            int mode;

            if(lookahead.sequence.equals("*")) {
                mode = MultiplicationExpressionNode.MULT;
            }
            else if(lookahead.sequence.equals("/")) {
                mode = MultiplicationExpressionNode.DIV;
            }
            else {
                mode = MultiplicationExpressionNode.REM;
            }

            nextToken();
            ExpressionNode f = signedFactor();
            prod.add(f, mode);

            return termOp(prod);
        }

        // term_op -> EPSILON
        return expression;
    }

    /**
     * handles the non-terminal signed_factor
     */
    private ExpressionNode signedFactor() {
        // signed_factor -> PLUSMINUS signed_factor
        if(lookahead.token == Token.PLUSMINUS) {
            int mode = lookahead.sequence.equals("+") ? AdditionExpressionNode.ADD : AdditionExpressionNode.SUB;
            nextToken();
            ExpressionNode t = signedFactor();
            if(mode == AdditionExpressionNode.ADD) {
                return t;
            }
            else {
                return new AdditionExpressionNode(t, AdditionExpressionNode.SUB);
            }
        }

        // signed_factor -> factor
        return factor();
    }

    /**
     * handles the non-terminal factor
     */
    private ExpressionNode factor() {
        // factor -> argument factor_op
        ExpressionNode a = argument();
        return factorOp(a);
    }

    /**
     * handles the non-terminal factor_op
     */
    private ExpressionNode factorOp(ExpressionNode expr) {
        // factor_op -> RAISED signed_factor
        if(lookahead.token == Token.RAISED) {
            nextToken();
            ExpressionNode exponent = signedFactor();

            return new ExponentiationExpressionNode(expr, exponent);
        }

        // factor_op -> EPSILON
        return expr;
    }

    /**
     * handles the non-terminal argument
     */
    private ExpressionNode argument() {
        // argument -> FUNCTION function_argument
        if(lookahead.token == Token.FUNCTION) {
            int function = FunctionExpressionNode.stringToFunction(lookahead.sequence);

            nextToken();
            ExpressionNode expr = functionArgument();
            return new FunctionExpressionNode(function, expr);
        } // argument -> FUNCTION_2ARG function_argument2
        else if(lookahead.token == Token.FUNCTION_2ARGUMENTS) {
            int function = Function2ArgumentsExpressionNode.stringToFunction(lookahead.sequence);

            nextToken();
            ExpressionNode[] expr = functionArgument2();
            return new Function2ArgumentsExpressionNode(function, expr[0], expr[1]);
        } // argument -> FUNCTION_DERIVATIVE_2_ARG function_argument2
        else if(lookahead.token == Token.FUNCTION_DERIVATIVE_2ARGUMENTS) {
            int function = FunctionDerivative2ArgumentsExpressionNode.stringToFunction(lookahead.sequence);

            nextToken();
            ExpressionNode[] expr = functionArgument2();

            if(!(expr[1] instanceof VariableExpressionNode)) {
                throw new ParserException("The second argument of a derivative function must be a variable.", lookahead);
            }

            return new FunctionDerivative2ArgumentsExpressionNode(function, expr[0], expr[1]);
        }// argument -> FUNCTION_USER_1_ARG function_argument
        else if(lookahead.token == Token.FUNCTION_USER_ONE_ARGUMENT) {
            int function = FunctionUser1ArgumentExpressionNode.stringToFunction(lookahead.sequence);

            if(!parseOnlyMode) {
                usesUserCode = true;
            }
            
            nextToken();
            ExpressionNode expr = functionArgument();
            return new FunctionUser1ArgumentExpressionNode(function, expr);
        } // argument -> FUNCTION_USER_2_ARG function_argument2
        else if(lookahead.token == Token.FUNCTION_USER_TWO_ARGUMENTS) {
            int function = FunctionUser2ArgumentExpressionNode.stringToFunction(lookahead.sequence);

            if(!parseOnlyMode) {
                usesUserCode = true;
            }
            
            nextToken();
            ExpressionNode[] expr = functionArgument2();
            return new FunctionUser2ArgumentExpressionNode(function, expr[0], expr[1]);
        } // argument -> FUNCTION_USER_MULTI_ARG function_max_multi_arg
        else if(lookahead.token == Token.FUNCTION_USER_MULTI_ARGUMENTS) {
            int function = FunctionUserMultiArgumentExpressionNode.stringToFunction(lookahead.sequence);

            if(!parseOnlyMode) {
                usesUserCode = true;
            }
            
            nextToken();
            ExpressionNode[] expr = functionUserMultiArguments(FunctionUserMultiArgumentExpressionNode.MAX_ARGUMENTS);
            return new FunctionUserMultiArgumentExpressionNode(function, expr);
        } // argument -> FUNCTION_USER_MULTI_2_ARG function_max_multi_arg
        else if(lookahead.token == Token.FUNCTION_USER_MULTI_2_ARGUMENTS) {
            int function = FunctionUserMultiArgument2ExpressionNode.stringToFunction(lookahead.sequence);

            if(!parseOnlyMode) {
                usesUserCode = true;
            }
            
            nextToken();
            ExpressionNode[] expr = functionUserMultiArguments(FunctionUserMultiArgument2ExpressionNode.MAX_ARGUMENTS);
            return new FunctionUserMultiArgument2ExpressionNode(function, expr);
        }// argument -> OPEN_BRACKET expression CLOSE_BRACKET
        else if(lookahead.token == Token.OPEN_BRACKET) {
            nextToken();
            ExpressionNode expr = expression();
            if(lookahead.token != Token.CLOSE_BRACKET) {
                throw new ParserException("Closing brackets expected.", lookahead);
            }
            nextToken();
            return expr;
        }

        // argument -> value
        return value();
    }

    /*handles the function with 2 arguments */
    private ExpressionNode[] functionArgument2() {
        // function_argument2 -> OPEN_BRACKET expression COMMA expression CLOSE_BRACKET
        if(lookahead.token == Token.OPEN_BRACKET) {
            ExpressionNode[] exprs = new ExpressionNode[2];

            nextToken();
            exprs[0] = expression();
            if(lookahead.token != Token.COMMA) {
                throw new ParserException("Comma expected.", lookahead);
            }
            nextToken();
            exprs[1] = expression();
            if(lookahead.token != Token.CLOSE_BRACKET) {
                throw new ParserException("Closing brackets expected.", lookahead);
            }
            nextToken();
            return exprs;
        }

        throw new ParserException("Opening brackets expected.", lookahead);
    }

    /*handles the function with user multi arguments */
    private ExpressionNode[] functionUserMultiArguments(int max_arguments) {
        
        //function_max_multi_arg -> OPEN_BRACKET expression more_args CLOSE_BRACKET
        if(lookahead.token == Token.OPEN_BRACKET) {
            ExpressionNode[] exprs = new ExpressionNode[max_arguments];

            nextToken();
            exprs[0] = expression();

            // more_args -> COMMA expression more_args
            // more_args -> EPSILON
            for(int i = 1; i < exprs.length; i++) {
                if(lookahead.token == Token.CLOSE_BRACKET) {
                    break;
                }

                if(lookahead.token != Token.COMMA) {
                    throw new ParserException("Comma expected.", lookahead);
                }
                nextToken();
                exprs[i] = expression();
            }

            for(int i = 0; i < exprs.length; i++) {
                if(exprs[i] == null) {
                    exprs[i] = new NoArgumentExpressionNode();
                }
            }

            if(lookahead.token != Token.CLOSE_BRACKET) {
                throw new ParserException("Closing brackets expected.", lookahead);
            }
            nextToken();
            return exprs;
        }

        throw new ParserException("Opening brackets expected.", lookahead);
    }

    /*handles the function with 1 argument */
    private ExpressionNode functionArgument() {
        // function_argument -> OPEN_BRACKET expression CLOSE_BRACKET
        if(lookahead.token == Token.OPEN_BRACKET) {
            nextToken();
            ExpressionNode expr = expression();
            if(lookahead.token != Token.CLOSE_BRACKET) {
                throw new ParserException("Closing brackets expected.", lookahead);
            }
            nextToken();
            return expr;
        }

        throw new ParserException("Opening brackets expected.", lookahead);
    }

    /**
     * handles the non-terminal value
     */
    private ExpressionNode value() {
        // value -> REAL_NUMBER
        if(lookahead.token == Token.REAL_NUMBER) {
            ExpressionNode expr = new RealConstantExpressionNode(lookahead.sequence);
            nextToken();
            return expr;
        }

        // value -> IMAGINARY_NUMBER
        if(lookahead.token == Token.IMAGINARY_NUMBER) {
            StringTokenizer tok = new StringTokenizer(lookahead.sequence, "iI");
            ExpressionNode expr;
            if(tok.hasMoreTokens()) {
                expr = new ImaginaryConstantExpressionNode(tok.nextToken());
            }
            else {
                expr = new ImaginaryConstantExpressionNode(1.0);
            }
            nextToken();
            return expr;
        }

        // value -> VARIABLE
        if(lookahead.token == Token.VARIABLE) {

            String temp = lookahead.sequence;

            boolean vars_exist = false;
            
            for(int i = 0; i < vars_var.size(); i++) {
                vars_exist |= temp.equalsIgnoreCase("v" + (i + 1));
            }
            
            if(!temp.equalsIgnoreCase("z") && !temp.equalsIgnoreCase("c") && !temp.equalsIgnoreCase("n") && !temp.equalsIgnoreCase("nf") && !temp.equalsIgnoreCase("p") && !temp.equalsIgnoreCase("s") && !temp.equalsIgnoreCase("pp") && !temp.equalsIgnoreCase("bail") && !temp.equalsIgnoreCase("cbail") && !temp.equalsIgnoreCase("maxn") && !temp.equalsIgnoreCase("pi") && !temp.equalsIgnoreCase("e") && !temp.equalsIgnoreCase("c10")
                    && !temp.equalsIgnoreCase("phi") && !temp.equalsIgnoreCase("alpha") && !temp.equalsIgnoreCase("delta") && !temp.equalsIgnoreCase("center") && !temp.equalsIgnoreCase("size")
                    && !vars_exist
                    && !temp.equalsIgnoreCase("point") && !temp.equalsIgnoreCase("sizei")
                    && !temp.equalsIgnoreCase("r")
                    && !temp.equalsIgnoreCase("stat")
                    && !temp.equalsIgnoreCase("trap")
                    && !temp.equalsIgnoreCase("rand")
                    && !temp.equalsIgnoreCase("c0")
                    && !temp.equalsIgnoreCase("pixel")
                    && !temp.equalsIgnoreCase("width")
                    && !temp.equalsIgnoreCase("height")
                    && !temp.equalsIgnoreCase("maxnde")
            ) {
                throw new ParserException("Unrecognized variable %s found.", lookahead);
            }

            ExpressionNode expr = null;

            if(temp.equalsIgnoreCase("pi") || temp.equalsIgnoreCase("e") || temp.equalsIgnoreCase("c10") || temp.equalsIgnoreCase("phi") || temp.equalsIgnoreCase("alpha") || temp.equalsIgnoreCase("delta") || temp.equalsIgnoreCase("rand") || temp.equalsIgnoreCase("maxnde")) {
                if(temp.equalsIgnoreCase("pi")) {
                    expr = new RealConstantExpressionNode(Math.PI);
                }

                if(temp.equalsIgnoreCase("e")) {
                    expr = new RealConstantExpressionNode(Math.E);
                }

                if(temp.equalsIgnoreCase("maxnde")) {
                    expr = new RealConstantExpressionNode(ColorAlgorithm.MAXIMUM_ITERATIONS_DE);
                }

                if(temp.equalsIgnoreCase("c10")) {
                    expr = new RealConstantExpressionNode(0.1234567891011121314);
                }

                if(temp.equalsIgnoreCase("phi")) {
                    expr = new RealConstantExpressionNode(1.618033988749895);
                }

                if(temp.equalsIgnoreCase("alpha")) {
                    expr = new RealConstantExpressionNode(2.5029078750958928);
                }

                if(temp.equalsIgnoreCase("delta")) {
                    expr = new RealConstantExpressionNode(4.669201609102990);
                }

                if(temp.equalsIgnoreCase("rand")) {
                    expr = new RandomConstantExpressionNode();
                }

                nextToken();
                return expr;
            }

            expr = new VariableExpressionNode(temp);

            if(temp.equalsIgnoreCase("z")) {
                found_z = true;
                z_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("c")) {
                found_c = true;
                c_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("n")) {
                found_n = true;
                n_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("nf")) {
                found_nf = true;
                nf_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("p")) {
                found_p = true;
                p_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("s")) {
                found_s = true;
                s_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("pp")) {
                found_pp = true;
                pp_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("bail")) {
                found_bail = true;
                bail_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("cbail")) {
                found_cbail = true;
                cbail_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("center")) {
                found_center = true;
                center_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("size")) {
                found_size = true;
                size_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("sizei")) {
                found_isize = true;
                isize_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("width")) {
                found_width = true;
                width_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("height")) {
                found_height = true;
                height_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("maxn")) {
                found_maxn = true;
                maxn_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("c0")) {
                found_c0 = true;
                c0_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("pixel")) {
                found_pixel = true;
                pixel_var.add((VariableExpressionNode)expr);
            }


            for(int i = 0; i < vars_var.size(); i++) {
                if(temp.equalsIgnoreCase("v" + (i + 1))) {
                    found_vars[i] = true;
                    found_any_var = true;
                    vars_var.get(i).add((VariableExpressionNode)expr);
                    break;
                }
            }

            if(temp.equalsIgnoreCase("point")) {
                found_point = true;
                point_var.add((VariableExpressionNode)expr);
            }
            
            if(temp.equalsIgnoreCase("r")) {
                found_r = true;
                r_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("stat")) {
                found_stat = true;
                stat_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("trap")) {
                found_trap = true;
                trap_var.add((VariableExpressionNode)expr);
            }


            nextToken();
            return expr;
        }

        if(lookahead.token == Token.EPSILON) {
            throw new ParserException("Unexpected end of input.");
        }
        else {
            throw new ParserException("Unexpected symbol %s found.", lookahead);
        }
    }

    /**
     * Remove the first token from the list and store the next token in
     * lookahead
     */
    private void nextToken() {
        tokens.pop();
        // at the end of input we return an epsilon token
        if(tokens.isEmpty()) {
            lookahead = new Token(Token.EPSILON, "", -1);
        }
        else {
            lookahead = tokens.getFirst();
        }
    }

    static byte[] gimmeLookupClassDef() {
        return ( "\u00CA\u00FE\u00BA\u00BE\0\0\0001\0\21\1\0\13GimmeLookup\7\0\1\1\0\20"
                +"java/lang/Object\7\0\3\1\0\10<clinit>\1\0\3()V\1\0\4Code\1\0\6lookup\1\0'Ljav"
                +"a/lang/invoke/MethodHandles$Lookup;\14\0\10\0\11\11\0\2\0\12\1\0)()Ljava/lang"
                +"/invoke/MethodHandles$Lookup;\1\0\36java/lang/invoke/MethodHandles\7\0\15\14\0"
                +"\10\0\14\12\0\16\0\17\26\1\0\2\0\4\0\0\0\1\20\31\0\10\0\11\0\0\0\1\20\11\0\5\0"
                +"\6\0\1\0\7\0\0\0\23\0\3\0\3\0\0\0\7\u00B8\0\20\u00B3\0\13\u00B1\0\0\0\0\0\0" )
                .getBytes(StandardCharsets.ISO_8859_1);
    }

    public static void compileUserFunctions() throws Exception {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if(compiler != null) {
            OutputStream out = new ByteArrayOutputStream();

            int compilationResult = compiler.run(null, null, out, CURRENT_USER_CODE_FILE);

            if(compilationResult != 0) {
                throw new ParserException("Compilation error: " + out);
            }

            try {
                File file = new File("");
                URL url = file.toURI().toURL();

                if(loader != null) {
                    loader.close();
                    loader = null;
                }

                loader = new URLClassLoader(new URL[] {url}) {
                    { byte[] code = gimmeLookupClassDef();
                        defineClass("GimmeLookup", code, 0, code.length);
                    }
                };


                lookup = (MethodHandles.Lookup) loader.loadClass("GimmeLookup").getField("lookup").get(null);
                userClass = loader.loadClass(CURRENT_USER_CODE_CLASS);

                Path path = Paths.get(CURRENT_USER_CODE_CLASS + ".class");

                if(Files.exists(path) && Files.isRegularFile(path)) {
                    path.toFile().deleteOnExit();
                }
            }
            catch(ClassNotFoundException ex) {
                throw new ParserException("Class not found error: " + ex.getMessage());
            }
            catch(SecurityException ex) {
                throw new ParserException("Security error: " + sanitizeMessage(ex.getMessage()));
            }
            catch(IllegalArgumentException ex) {
                throw new ParserException("Illegal Argument error: " + sanitizeMessage(ex.getMessage()));
            }
            catch(MalformedURLException ex) {
                throw new ParserException("File not found error: " + sanitizeMessage(ex.getMessage()));
            }
            catch(IOException ex) {
                throw new ParserException("Error: " + sanitizeMessage(ex.getMessage()));
            }
        }
        else {
            throw new Exception("Java Compiler was not found.\nUser code cannot be compiled.\nMake sure that you have a JDK installed.");
        }
    }

    /* Implemented for fractal zoomer purposes */
    public boolean foundZ() {
        return found_z;
    }

    public boolean foundC() {
        return found_c;
    }

    public boolean foundN() {
        return found_n;
    }

    public boolean foundNF() {
        return found_nf;
    }

    public boolean foundP() {
        return found_p;
    }

    public boolean foundS() {
        return found_s;
    }

    public boolean foundPP() {
        return found_pp;
    }

    public boolean foundBail() {
        return found_bail;
    }

    public boolean foundCbail() {
        return found_cbail;
    }

    public boolean foundMaxn() {
        return found_maxn;
    }

    public boolean foundCenter() {
        return found_center;
    }

    public boolean foundSize() {
        return found_size;
    }

    public boolean foundISize() {
        return found_isize;
    }

    public boolean foundWidth() {
        return found_width;
    }

    public boolean foundHeight() {
        return found_height;
    }

    public boolean foundPoint() {
        return found_point;
    }

    public boolean foundR() {
        return found_r;
    }

    public boolean foundStat() {
        return found_stat;
    }

    public boolean foundTrap() {
        return found_trap;
    }

    public boolean foundC0() {
        return found_c0;
    }

    public boolean foundPixel() {
        return found_pixel;
    }

    public boolean foundVar(int i) {
        try {
            return found_vars[i];
        }
        catch(Exception ex) {
        }

        return false;
    }

    public boolean foundAnyVar() {

        return found_any_var;

    }


    /* Instead of using the visitor pattern, in order to save comparison time
     I have decided to keep every object that is a variable into a seperate array
     and update its value manually.
     */
    public void setZvalue(Complex value) {

        for(int i = 0; i < z_var_arr.length; i++) {
            z_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setCvalue(Complex value) {

        for(int i = 0; i < c_var_arr.length; i++) {
            c_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setNvalue(Complex value) {

        for(int i = 0; i < n_var_arr.length; i++) {
            n_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setNFvalue(Complex value) {

        for(int i = 0; i < nf_var_arr.length; i++) {
            nf_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setPvalue(Complex value) {

        for(int i = 0; i < p_var_arr.length; i++) {
            p_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setSvalue(Complex value) {

        for(int i = 0; i < s_var_arr.length; i++) {
            s_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setPPvalue(Complex value) {

        for(int i = 0; i < pp_var_arr.length; i++) {
            pp_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setBailvalue(Complex value) {

        for(int i = 0; i < bail_var_arr.length; i++) {
            bail_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setCbailvalue(Complex value) {

        for(int i = 0; i < cbail_var_arr.length; i++) {
            cbail_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setMaxnvalue(Complex value) {

        for(int i = 0; i < maxn_var_arr.length; i++) {
            maxn_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setCentervalue(Complex value) {

        for(int i = 0; i < center_var_arr.length; i++) {
            center_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setSizevalue(Complex value) {

        for(int i = 0; i < size_var_arr.length; i++) {
            size_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setISizevalue(Complex value) {

        for(int i = 0; i < isize_var_arr.length; i++) {
            isize_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setWidthvalue(Complex value) {

        for(int i = 0; i < width_var_arr.length; i++) {
            width_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setHeightvalue(Complex value) {

        for(int i = 0; i < height_var_arr.length; i++) {
            height_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setPointvalue(Complex value) {

        for(int i = 0; i < point_var_arr.length; i++) {
            point_var_arr[i].setValue(new Complex(value));
        }

    }
    
    public void setRvalue(Complex value) {

        for(int i = 0; i < r_var_arr.length; i++) {
            r_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setStatvalue(Complex value) {

        for(int i = 0; i < stat_var_arr.length; i++) {
            stat_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setTrapvalue(Complex value) {

        for(int i = 0; i < trap_var_arr.length; i++) {
            trap_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setC0value(Complex value) {

        for(int i = 0; i < c0_var_arr.length; i++) {
            c0_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setPixelvalue(Complex value) {

        for(int i = 0; i < pixel_var_arr.length; i++) {
            pixel_var_arr[i].setValue(new Complex(value));
        }

    }

    public void setVarsvalue(int i, Complex value) {

        try {
            for(int j = 0; j < vars_var_arr[i].length; i++) {
                vars_var_arr[i][j].setValue(value); // dont create an new object for these vars
            }
        }
        catch(Exception ex) {
        }

    }

    /**
     * **********************************************
     */

    public static String sanitizeMessage(String message) {

        if(message.length() > 100) {
            return message.substring(0, 100) + "...";
        }

        return message;
    }

}
