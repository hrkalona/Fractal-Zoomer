

package fractalzoomer.parser;

public class Token
{
  /** Token id for the epsilon terminal */
  public static final int EPSILON = 0;
  /** Token id for plus or minus */
  public static final int PLUSMINUS = 1;
  /** Token id for multiplication division or remainder */
  public static final int MULTDIVREM = 2;
  /** Token id for the exponentiation symbol */
  public static final int RAISED = 3;
  /** Token id for function names */
  public static final int FUNCTION = 4;
  /** Token id for opening brackets */
  public static final int OPEN_BRACKET = 5;
  /** Token id for closing brackets */
  public static final int CLOSE_BRACKET = 6;
  /** Token id for real numbers */
  public static final int REAL_NUMBER = 7;
  /** Token id for variable names */
  public static final int VARIABLE = 8;
  /** Token id for imaginary numbers */
  public static final int IMAGINARY_NUMBER = 9;
  /** Token id for function names with 2 arguments*/
  public static final int FUNCTION_2ARGUMENTS = 10;
  /** Token id for function names with user multiple arguments (10)*/
  public static final int FUNCTION_USER_MULTI_ARGUMENTS = 11;
  /** Token id for function names with user one argument*/
  public static final int FUNCTION_USER_ONE_ARGUMENT = 12;
  /** Token id for function names with user two arguments*/
  public static final int FUNCTION_USER_TWO_ARGUMENTS = 13;
  /** Token id for comma */
  public static final int COMMA = 14;
  /** Token id for function names with user multiple arguments (20)*/
  public static final int FUNCTION_USER_MULTI_2_ARGUMENTS = 15;
  /** Token id for function derivative names with 2 arguments*/
  public static final int FUNCTION_DERIVATIVE_2ARGUMENTS = 16;

  /** the token identifier */
  public final int token;
  /** the string that the token was created from */
  public final String sequence;
  /** the position of the token in the input string */
  public final int pos;

  /**
   * Construct the token with its values
   * @param token the token identifier
   * @param sequence the string that the token was created from
   * @param pos the position of the token in the input string
   */
  public Token(int token, String sequence, int pos)
  {
    super();
    this.token = token;
    this.sequence = sequence;
    this.pos = pos;
  }

}