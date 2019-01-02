/* 
 * Fractal Zoomer, Copyright (C) 2019 hrkalona2
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

public class ParserException extends RuntimeException
{
  private static final long serialVersionUID = -1009747984332258423L;
 
  /** the token that caused the error */
  private Token token = null;

  /**
   * Construct the evaluation exception with a message.
   * @param message the message containing the cause of the exception
   */
  public ParserException(String message)
  {
    super(message);
  }

  /**
   * Construct the evaluation exception with a message and a token.
   * @param message the message containing the cause of the exception
   * @param token the token that caused the exception
   */
  public ParserException(String message, Token token)
  {
    super(message);
    this.token = token;
  }
  
  /**
   * Get the token.
   * @return the token that caused the exception
   */
  public Token getToken()
  {
    return token;
  }
  
  /**
   * Overrides RuntimeException.getMessage to add the token information
   * into the error message.
   * 
   *  @return the error message
   */
  @Override
  public String getMessage()
  {
    String msg = super.getMessage();
    if (token != null)
    {
      msg = msg.replace("%s", token.sequence);
    }
    return msg;
  }

}
