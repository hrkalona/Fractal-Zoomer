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
  /** Token id for function names with user multiple arguments*/
  public static final int FUNCTION_USER_MULTI_ARGUMENTS = 11;
  /** Token id for function names with user one argument*/
  public static final int FUNCTION_USER_ONE_ARGUMENT = 12;
  /** Token id for function names with user two arguments*/
  public static final int FUNCTION_USER_TWO_ARGUMENTS = 13;
  /** Token id for comma */
  public static final int COMMA = 12;

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