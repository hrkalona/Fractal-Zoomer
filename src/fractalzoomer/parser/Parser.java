/*
 * This software and all files contained in it are distrubted under the MIT license.
 * 
 * Copyright (c) 2013 Cogito Learning Ltd
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
 
/**
 * @mainpage
 * CogPar is lightweight but versatile parser for mathematical expressions. 
 *
 * It can be used to analyse expressions and store them in an internal data structure for later
 * evaluation. Repeated evaluation of the same expression using CogPar is fast.
 *
 * CogPar comes with a highly configurable tokenizer which can be adapted for your own needs.
 *
 * Arbitrary named variables are supported and values can be assigned in a single line of code.
 *
 * The parser, it's grammar an the tokenizer are well documented. You can read more about the internal 
 * workings of CogPar <a href="http://cogitolearning.co.uk/?p=523" alt="CogPar tutorial">in these posts</a>.
 * 
 * CogPar is distributed under the MIT license, so feel free to use it in your own projects.
 * 
 * To download CogPar, <a href="" alt="Download CogPar">follow this link.</a>
 */

package fractalzoomer.parser;

import fractalzoomer.core.Complex;
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
public class Parser
{
  /** the tokens to parse */
  LinkedList<Token> tokens;
  /** the next token */
  Token lookahead;
  
  /* Implement for fractal zoomer purposes */
  boolean found_z;
  boolean found_c;
  boolean found_n;
  boolean found_p;
  boolean found_s;
  
  ArrayList<VariableExpressionNode> z_var;
  ArrayList<VariableExpressionNode> c_var;
  ArrayList<VariableExpressionNode> n_var;
  ArrayList<VariableExpressionNode> p_var;
  ArrayList<VariableExpressionNode> s_var;
  
  VariableExpressionNode[] z_var_arr;
  VariableExpressionNode[] c_var_arr;
  VariableExpressionNode[] n_var_arr;
  VariableExpressionNode[] p_var_arr;
  VariableExpressionNode[] s_var_arr;
  /***************************************/
  
  /**
   * Parse a mathematical expression in a string and return an ExpressionNode.
   * 
   * This is a convenience method that first converts the string into a linked
   * list of tokens using the expression tokenizer provided by the Tokenizer
   * class.
   * 
   * @param expression
   *          the string holding the input
   * @return the internal representation of the expression in form of an
   *         expression tree made out of ExpressionNode objects
   */
  public ExpressionNode parse(String expression)
  {
    found_z = false;
    found_c = false;
    found_n = false;
    found_p = false;
    found_s = false;
    
    z_var = new ArrayList<VariableExpressionNode>();
    c_var = new ArrayList<VariableExpressionNode>();
    n_var = new ArrayList<VariableExpressionNode>();
    p_var = new ArrayList<VariableExpressionNode>();
    s_var = new ArrayList<VariableExpressionNode>();
    
    Tokenizer tokenizer = Tokenizer.getExpressionTokenizer();
    tokenizer.tokenize(expression);
    LinkedList<Token> tokens = tokenizer.getTokens();
    return this.parse(tokens);
  }

  /**
   * Parse a mathematical expression in contained in a list of tokens and return
   * an ExpressionNode.
   * 
   * @param tokens
   *          a list of tokens holding the tokenized input
   * @return the internal representation of the expression in form of an
   *         expression tree made out of ExpressionNode objects
   */
  public ExpressionNode parse(LinkedList<Token> tokens)
  {
    // implementing a recursive descent parser
    this.tokens = (LinkedList<Token>) tokens.clone();
    
    try {
        lookahead = this.tokens.getFirst();
    }
    catch(NoSuchElementException ex) {
        throw new ParserException("No input found.");
    }

    // top level non-terminal is expression
    ExpressionNode expr = expression();
    
    if (lookahead.token != Token.EPSILON)
      throw new ParserException("Unexpected symbol %s found.", lookahead);
    
    z_var_arr = new VariableExpressionNode[z_var.size()];
    c_var_arr = new VariableExpressionNode[c_var.size()];
    n_var_arr = new VariableExpressionNode[n_var.size()];
    p_var_arr = new VariableExpressionNode[p_var.size()];
    s_var_arr = new VariableExpressionNode[s_var.size()];
  
    z_var_arr = z_var.toArray(z_var_arr);
    c_var_arr = c_var.toArray(c_var_arr);
    n_var_arr = n_var.toArray(n_var_arr);
    p_var_arr = p_var.toArray(p_var_arr);
    s_var_arr = s_var.toArray(s_var_arr);
    
    return expr;
  }

  /** handles the non-terminal expression */
  private ExpressionNode expression()
  {
    // only one rule
    // expression -> signed_term sum_op
    ExpressionNode expr = signedTerm();
    expr = sumOp(expr);
    return expr;
  }

  /** handles the non-terminal sum_op */
  private ExpressionNode sumOp(ExpressionNode expr)
  {
    // sum_op -> PLUSMINUS term sum_op
    if (lookahead.token == Token.PLUSMINUS)
    {
      AdditionExpressionNode sum;
      // This means we are actually dealing with a sum
      // If expr is not already a sum, we have to create one
      if (expr.getType() == ExpressionNode.ADDITION_NODE)
        sum = (AdditionExpressionNode) expr;
      else
        sum = new AdditionExpressionNode(expr, true);

      // reduce the input and recursively call sum_op
      boolean positive = lookahead.sequence.equals("+");
      nextToken();
      ExpressionNode t = term();
      sum.add(t, positive);

      return sumOp(sum);
    }

    // sum_op -> EPSILON
    return expr;
  }

  /** handles the non-terminal signed_term */
  private ExpressionNode signedTerm()
  {
    // signed_term -> PLUSMINUS term
    if (lookahead.token == Token.PLUSMINUS)
    {
      boolean positive = lookahead.sequence.equals("+");
      nextToken();
      ExpressionNode t = term();
      if (positive)
        return t;
      else
        return new AdditionExpressionNode(t, false);
    }

    // signed_term -> term
    return term();
  }

  /** handles the non-terminal term */
  private ExpressionNode term()
  {
    // term -> factor term_op
    ExpressionNode f = factor();
    return termOp(f);
  }

  /** handles the non-terminal term_op */
  private ExpressionNode termOp(ExpressionNode expression)
  {
    // term_op -> MULTDIV factor term_op
    if (lookahead.token == Token.MULTDIV)
    {
      MultiplicationExpressionNode prod;

      // This means we are actually dealing with a product
      // If expr is not already a sum, we have to create one
      if (expression.getType() == ExpressionNode.MULTIPLICATION_NODE)
        prod = (MultiplicationExpressionNode) expression;
      else
        prod = new MultiplicationExpressionNode(expression, true);

      // reduce the input and recursively call sum_op
      boolean positive = lookahead.sequence.equals("*");
      nextToken();
      ExpressionNode f = factor();
      prod.add(f, positive);

      return termOp(prod);
    }

    // term_op -> EPSILON
    return expression;
  }

  /** handles the non-terminal factor */
  private ExpressionNode factor()
  {
    // factor -> argument factor_op
    ExpressionNode a = argument();
    return factorOp(a);
  }

  /** handles the non-terminal factor_op */
  private ExpressionNode factorOp(ExpressionNode expr)
  {
    // factor_op -> RAISED expression
    if (lookahead.token == Token.RAISED)
    {
      nextToken();
      ExpressionNode exponent = factor();

      return new ExponentiationExpressionNode(expr, exponent);
    }

    // factor_op -> EPSILON
    return expr;
  }

  /** handles the non-terminal argument */
  private ExpressionNode argument()
  {
    // argument -> FUNCTION argument
    if (lookahead.token == Token.FUNCTION)
    {
      int function = FunctionExpressionNode.stringToFunction(lookahead.sequence);
      if (function < 0) throw new ParserException("Unexpected Function %s found.", lookahead);
      nextToken();
      ExpressionNode expr = argument();
      return new FunctionExpressionNode(function, expr);
    }
    // argument -> OPEN_BRACKET sum CLOSE_BRACKET
    else if (lookahead.token == Token.OPEN_BRACKET)
    {
      nextToken();
      ExpressionNode expr = expression();
      if (lookahead.token != Token.CLOSE_BRACKET)
        throw new ParserException("Closing brackets expected.", lookahead);
      nextToken();
      return expr;
    }

    // argument -> value
    return value();
  }

  /** handles the non-terminal value */
  private ExpressionNode value()
  {
    // argument -> REAL_NUMBER
    if (lookahead.token == Token.REAL_NUMBER)
    {
      ExpressionNode expr = new RealConstantExpressionNode(lookahead.sequence);
      nextToken();
      return expr;
    }
    
    // argument -> IMAGINARY_NUMBER
    if (lookahead.token == Token.IMAGINARY_NUMBER)
    {
      StringTokenizer tok = new StringTokenizer(lookahead.sequence, "i");
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
    if (lookahead.token == Token.VARIABLE)
    {
      
      String temp = lookahead.sequence;
 
      
      if(!temp.equalsIgnoreCase("z") && !temp.equalsIgnoreCase("c") && !temp.equalsIgnoreCase("n") && !temp.equalsIgnoreCase("p") && !temp.equalsIgnoreCase("s") && !temp.equalsIgnoreCase("pi") && !temp.equalsIgnoreCase("e") && !temp.equalsIgnoreCase("c10") && !temp.equalsIgnoreCase("phi") && !temp.equalsIgnoreCase("alpha") && !temp.equalsIgnoreCase("delta")) {
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

    if (lookahead.token == Token.EPSILON)
      throw new ParserException("Unexpected end of input.");
    else
      throw new ParserException("Unexpected symbol %s found.", lookahead);
  }

  /**
   * Remove the first token from the list and store the next token in lookahead
   */
  private void nextToken()
  {
    tokens.pop();
    // at the end of input we return an epsilon token
    if (tokens.isEmpty())
      lookahead = new Token(Token.EPSILON, "", -1);
    else
      lookahead = tokens.getFirst();
  }
  
  public boolean foundZ()
  {
      return found_z;
  }
  
  public boolean foundC()
  {
      return found_c;
  }
  
  public boolean foundN()
  {
      return found_n;
  }
  
  public boolean foundP()
  {
      return found_p;
  }
  
  public boolean foundS()
  {
      return found_s;
  }
  
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
  
}
