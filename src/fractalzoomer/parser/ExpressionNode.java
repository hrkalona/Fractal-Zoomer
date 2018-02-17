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

import fractalzoomer.core.Complex;

/**
 * An interface for expression nodes.
 * 
 * Every concrete type of expression node has to implement this interface.
 */
public interface ExpressionNode
{
  /** Node id for variable nodes */
  public static final int VARIABLE_NODE = 1;
  /** Node id for real constant nodes */
  public static final int REAL_CONSTANT_NODE = 2;
  /** Node id for addition nodes */
  public static final int ADDITION_NODE = 3;
  /** Node id for multiplication nodes */
  public static final int MULTIPLICATION_NODE = 4;
  /** Node id for exponentiation nodes */
  public static final int EXPONENTIATION_NODE = 5;
  /** Node id for function nodes */
  public static final int FUNCTION_NODE = 6;
  /** Node id for imaginary constant nodes */
  public static final int IMAGINARY_CONSTANT_NODE = 7;
  /** Node id for function nodes with 2 arguments */
  public static final int FUNCTION_2_ARG_NODE = 8;
  /** Node id for function nodes multiple arguments made by the user */
  public static final int FUNCTION_USER_MULTI_ARG_NODE = 9;
  /** Node id for function nodes one argument made by the user */
  public static final int FUNCTION_USER_ONE_ARG_NODE = 10;
  /** Node id for function nodes two arguments made by the user */
  public static final int FUNCTION_USER_TWO_ARG_NODE = 11;

  /**
   * Returns the type of the node.ExpressionNode
   *
   * Each class derived from ExpressionNode representing a specific
   * role in the expression should return the type according to that
   * role.
   * 
   * @return type of the node
   */
  public int getType();
  
  /**
   * Calculates and returns the value of the sub-expression represented by
   * the node.
   * 
   * @return value of expression
   */
  public Complex getValue();
  
  /**
   * Method needed for the visitor design pattern
   * 
   * @param visitor
   *          the visitor
   */
  public void accept(ExpressionNodeVisitor visitor);

}
