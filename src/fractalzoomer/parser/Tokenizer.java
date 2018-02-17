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

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class for reading an input string and separating it into tokens that can be
 * fed into Parser.
 * 
 * The user can add regular expressions that will be matched against the front
 * of the string. Regular expressions should not contain beginning-of-string or
 * end-of-string anchors or any capturing groups as these will be added by the
 * tokenizer itslef.
 */
public class Tokenizer
{
  /**
   * Internal class holding the information about a token type.
   */
  private class TokenInfo
  {
    /** the regular expression to match against */
    public final Pattern regex;
    /** the token id that the regular expression is linked to */
    public final int token;

    /**
     * Construct TokenInfo with its values
     */
    public TokenInfo(Pattern regex, int token)
    {
      super();
      this.regex = regex;
      this.token = token;
    }
  }

  /** 
   * a list of TokenInfo objects
   * 
   * Each token type corresponds to one entry in the list
   */
  private LinkedList<TokenInfo> tokenInfos;
  
  /** the list of tokens produced when tokenizing the input */
  private LinkedList<Token> tokens;

  /** a tokenizer that can handle mathematical expressions */
  private static Tokenizer expressionTokenizer = null;

  /**
   * Default constructor
   */
  public Tokenizer()
  {
    super();
    tokenInfos = new LinkedList<TokenInfo>();
    tokens = new LinkedList<Token>();
  }

  /**
   * A static method that returns a tokenizer for mathematical expressions
   * @return a tokenizer that can handle mathematical expressions
   */
  public static Tokenizer getExpressionTokenizer()
  {
    if (expressionTokenizer == null)
      expressionTokenizer = createExpressionTokenizer();
    return expressionTokenizer;
  }

  /**
   * A static method that actually creates a tokenizer for mathematical expressions
   * @return a tokenizer that can handle mathematical expressions
   */
  private static Tokenizer createExpressionTokenizer()
  {
    Tokenizer tokenizer = new Tokenizer();

    tokenizer.add("[+-]", Token.PLUSMINUS);
    tokenizer.add("[*/%]", Token.MULTDIVREM);
    tokenizer.add("\\^", Token.RAISED);

    tokenizer.add("(" + FunctionExpressionNode.getAllFunctions() + ")(?!\\w)", Token.FUNCTION);
    tokenizer.add("(" + Function2ArgumentsExpressionNode.getAllFunctions() + ")(?!\\w)", Token.FUNCTION_2ARGUMENTS);
    tokenizer.add("(" + FunctionUser1ArgumentExpressionNode.getAllFunctions() + ")(?!\\w)", Token.FUNCTION_USER_ONE_ARGUMENT);
    tokenizer.add("(" + FunctionUser2ArgumentExpressionNode.getAllFunctions() + ")(?!\\w)", Token.FUNCTION_USER_TWO_ARGUMENTS);
    tokenizer.add("(" + FunctionUserMultiArgumentExpressionNode.getAllFunctions() + ")(?!\\w)", Token.FUNCTION_USER_MULTI_ARGUMENTS);

    tokenizer.add("\\(", Token.OPEN_BRACKET);
    tokenizer.add(",", Token.COMMA);
    tokenizer.add("\\)", Token.CLOSE_BRACKET);
    tokenizer.add("((?:\\d+\\.?|\\.\\d)\\d*(?:[Ee][-+]?\\d+)?)?( )*(i|I)", Token.IMAGINARY_NUMBER);
    tokenizer.add("(?:\\d+\\.?|\\.\\d)\\d*(?:[Ee][-+]?\\d+)?", Token.REAL_NUMBER);   
    tokenizer.add("[a-zA-Z]\\w*", Token.VARIABLE);

    return tokenizer;
  }

  /**
   * Add a regular expression and a token id to the internal list of recognized tokens
   * @param regex the regular expression to match against 
   * @param token the token id that the regular expression is linked to
   */
  public void add(String regex, int token)
  {
    tokenInfos.add(new TokenInfo(Pattern.compile("^(" + regex+")"), token));
  }

  /**
   * Tokenize an input string.
   * 
   * The reult of tokenizing can be accessed via getTokens
   * 
   * @param str the string to tokenize
   */
  public void tokenize(String str)
  {
    String s = str.trim();
    int totalLength = s.length();
    tokens.clear();
    while (!s.equals(""))
    {
      int remaining = s.length();
      boolean match = false;
      for (TokenInfo info : tokenInfos)
      {
        Matcher m = info.regex.matcher(s);
        if (m.find())
        {
          match = true;
          String tok = m.group().trim();
          // System.out.println("Success matching " + s + " against " +
          // info.regex.pattern() + " : " + tok);
          s = m.replaceFirst("").trim();
          tokens.add(new Token(info.token, tok, totalLength - remaining));
          break;
        }      }
      if (!match)
        throw new ParserException("Unexpected character in input: " + s);
    }
  }

  /**
   * Get the tokens generated in the last call to tokenize.
   * @return a list of tokens to be fed to Parser
   */
  public LinkedList<Token> getTokens()
  {
    return tokens;
  }

}
