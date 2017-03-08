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
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * A parser for mathematical expressions. The parser class defines a method
 * parse() which takes a string and returns an ExpressionNode that holds a
 * representation of the expression.
 *
 * Parsing is implemented in the form of a recursive descent parser.
 *
 */
public class Parser {

    /**
     * the tokens to parse
     */
    LinkedList<Token> tokens;
    /**
     * the next token
     */
    Token lookahead;

    /* Implemented for fractal zoomer purposes */
    boolean found_z;
    boolean found_c;
    boolean found_n;
    boolean found_p;
    boolean found_s;
    boolean found_pp;
    boolean found_bail;
    boolean found_cbail;
    boolean found_maxn;

    ArrayList<VariableExpressionNode> z_var;
    ArrayList<VariableExpressionNode> c_var;
    ArrayList<VariableExpressionNode> n_var;
    ArrayList<VariableExpressionNode> p_var;
    ArrayList<VariableExpressionNode> s_var;
    ArrayList<VariableExpressionNode> pp_var;
    ArrayList<VariableExpressionNode> bail_var;
    ArrayList<VariableExpressionNode> cbail_var;
    ArrayList<VariableExpressionNode> maxn_var;

    VariableExpressionNode[] z_var_arr;
    VariableExpressionNode[] c_var_arr;
    VariableExpressionNode[] n_var_arr;
    VariableExpressionNode[] p_var_arr;
    VariableExpressionNode[] s_var_arr;
    VariableExpressionNode[] pp_var_arr;
    VariableExpressionNode[] bail_var_arr;
    VariableExpressionNode[] cbail_var_arr;
    VariableExpressionNode[] maxn_var_arr;

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
        found_p = false;
        found_s = false;
        found_pp = false;
        found_bail = false;
        found_cbail = false;
        found_maxn = false;

        z_var = new ArrayList<VariableExpressionNode>();
        c_var = new ArrayList<VariableExpressionNode>();
        n_var = new ArrayList<VariableExpressionNode>();
        p_var = new ArrayList<VariableExpressionNode>();
        s_var = new ArrayList<VariableExpressionNode>();
        pp_var = new ArrayList<VariableExpressionNode>();
        bail_var = new ArrayList<VariableExpressionNode>();
        cbail_var = new ArrayList<VariableExpressionNode>();
        maxn_var = new ArrayList<VariableExpressionNode>();

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
        p_var_arr = new VariableExpressionNode[p_var.size()];
        s_var_arr = new VariableExpressionNode[s_var.size()];
        pp_var_arr = new VariableExpressionNode[pp_var.size()];
        bail_var_arr = new VariableExpressionNode[bail_var.size()];
        cbail_var_arr = new VariableExpressionNode[cbail_var.size()];
        maxn_var_arr = new VariableExpressionNode[maxn_var.size()];

        z_var_arr = z_var.toArray(z_var_arr);
        c_var_arr = c_var.toArray(c_var_arr);
        n_var_arr = n_var.toArray(n_var_arr);
        p_var_arr = p_var.toArray(p_var_arr);
        s_var_arr = s_var.toArray(s_var_arr);
        pp_var_arr = pp_var.toArray(pp_var_arr);
        bail_var_arr = bail_var.toArray(bail_var_arr);
        cbail_var_arr = cbail_var.toArray(cbail_var_arr);
        maxn_var_arr = maxn_var.toArray(maxn_var_arr);

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
            int mode =  lookahead.sequence.equals("+") ? AdditionExpressionNode.ADD : AdditionExpressionNode.SUB;
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
        // factor_op -> RAISED expression
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
        // argument -> FUNCTION argument
        if(lookahead.token == Token.FUNCTION) {
            int function = FunctionExpressionNode.stringToFunction(lookahead.sequence);

            nextToken();
            ExpressionNode expr = functionArgument();
            return new FunctionExpressionNode(function, expr);
        }
        // argument -> FUNCTION_2ARG argument
        else if(lookahead.token == Token.FUNCTION_2ARGUMENTS) {
            int function = Function2ArgumentsExpressionNode.stringToFunction(lookahead.sequence);

            nextToken();
            ExpressionNode expr[] = functionArgument2();
            return new Function2ArgumentsExpressionNode(function, expr[0], expr[1]);
        }
        // argument -> FUNCTION_USER_MULTI_ARG argument
        else if(lookahead.token == Token.FUNCTION_USER_MULTI_ARGUMENTS) {
            int function = FunctionUserMultiArgumentExpressionNode.stringToFunction(lookahead.sequence);

            nextToken();
            ExpressionNode expr[] = functionUserMultiArguments();
            return new FunctionUserMultiArgumentExpressionNode(function, expr);
        }
        // argument -> OPEN_BRACKET sum CLOSE_BRACKET
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
    private ExpressionNode[] functionUserMultiArguments() {
        if(lookahead.token == Token.OPEN_BRACKET) {
            ExpressionNode[] exprs = new ExpressionNode[FunctionUserMultiArgumentExpressionNode.MAX_ARGUMENTS];
            
            nextToken();
            exprs[0] = expression();
            
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
        // argument -> REAL_NUMBER
        if(lookahead.token == Token.REAL_NUMBER) {
            ExpressionNode expr = new RealConstantExpressionNode(lookahead.sequence);
            nextToken();
            return expr;
        }

        // argument -> IMAGINARY_NUMBER
        if(lookahead.token == Token.IMAGINARY_NUMBER) {
            StringTokenizer tok = new StringTokenizer(lookahead.sequence, "iI");
            ExpressionNode expr;
            if(tok.hasMoreTokens()) {
                expr = new ImaginaryConstantExpressionNode(tok.nextToken());
            }
            else {
                expr = new ImaginaryConstantExpressionNode("1.0");
            }
            nextToken();
            return expr;
        }

        // argument -> VARIABLE
        if(lookahead.token == Token.VARIABLE) {

            String temp = lookahead.sequence;

            if(!temp.equalsIgnoreCase("z") && !temp.equalsIgnoreCase("c") && !temp.equalsIgnoreCase("n") && !temp.equalsIgnoreCase("p") && !temp.equalsIgnoreCase("s") && !temp.equalsIgnoreCase("pp") && !temp.equalsIgnoreCase("bail") && !temp.equalsIgnoreCase("cbail") && !temp.equalsIgnoreCase("maxn") && !temp.equalsIgnoreCase("pi") && !temp.equalsIgnoreCase("e") && !temp.equalsIgnoreCase("c10") && !temp.equalsIgnoreCase("phi") && !temp.equalsIgnoreCase("alpha") && !temp.equalsIgnoreCase("delta")) {
                throw new ParserException("Unrecognized variable %s found.", lookahead);
            }

            ExpressionNode expr = new VariableExpressionNode(temp);

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

            if(temp.equalsIgnoreCase("maxn")) {
                found_maxn = true;
                maxn_var.add((VariableExpressionNode)expr);
            }

            if(temp.equalsIgnoreCase("pi")) {
                ((VariableExpressionNode)expr).setValue(new Complex(Math.PI, 0));
            }

            if(temp.equalsIgnoreCase("e")) {
                ((VariableExpressionNode)expr).setValue(new Complex(Math.E, 0));
            }

            if(temp.equalsIgnoreCase("c10")) {
                ((VariableExpressionNode)expr).setValue(new Complex(0.1234567891011121314, 0));
            }

            if(temp.equalsIgnoreCase("phi")) {
                ((VariableExpressionNode)expr).setValue(new Complex(1.618033988749895, 0));
            }

            if(temp.equalsIgnoreCase("alpha")) {
                ((VariableExpressionNode)expr).setValue(new Complex(2.5029078750958928, 0));
            }

            if(temp.equalsIgnoreCase("delta")) {
                ((VariableExpressionNode)expr).setValue(new Complex(4.669201609102990, 0));
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
    
    
    public static void compileUserFunctions() {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if(compiler != null) {
            OutputStream out = new ByteArrayOutputStream();

            int compilationResult = compiler.run(null, null, out, "UserDefinedFunctions.java");

            if(compilationResult != 0) {
                throw new ParserException("Compilation error: " + out.toString());
            }
        }
        else {
            throw new ParserException("Java Compiler was not found.\nUser code cannot be compiled.");
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

    /* Instead of using the visitor pattern, in order to save comparison time
     I have decided to keep every object that is a variable into a seperate array
     and update its value manually.
     */
    public void setZvalue(Complex value) {

        for(int i = 0; i < z_var_arr.length; i++) {
            z_var_arr[i].setValue(value);
        }

    }

    public void setCvalue(Complex value) {

        for(int i = 0; i < c_var_arr.length; i++) {
            c_var_arr[i].setValue(value);
        }

    }

    public void setNvalue(Complex value) {

        for(int i = 0; i < n_var_arr.length; i++) {
            n_var_arr[i].setValue(value);
        }

    }

    public void setPvalue(Complex value) {

        for(int i = 0; i < p_var_arr.length; i++) {
            p_var_arr[i].setValue(value);
        }

    }

    public void setSvalue(Complex value) {

        for(int i = 0; i < s_var_arr.length; i++) {
            s_var_arr[i].setValue(value);
        }

    }

    public void setPPvalue(Complex value) {

        for(int i = 0; i < pp_var_arr.length; i++) {
            pp_var_arr[i].setValue(value);
        }

    }

    public void setBailvalue(Complex value) {

        for(int i = 0; i < bail_var_arr.length; i++) {
            bail_var_arr[i].setValue(value);
        }

    }

    public void setCbailvalue(Complex value) {

        for(int i = 0; i < cbail_var_arr.length; i++) {
            cbail_var_arr[i].setValue(value);
        }

    }

    public void setMaxnvalue(Complex value) {

        for(int i = 0; i < maxn_var_arr.length; i++) {
            maxn_var_arr[i].setValue(value);
        }

    }
    /**
     * **********************************************
     */

}
