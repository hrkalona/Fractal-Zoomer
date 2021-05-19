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

/**
 * An ExpressionNode that stores a constant value
 */
public class RealConstantExpressionNode implements ExpressionNode
{
  /** The value of the constant */
  private Complex value;


  public RealConstantExpressionNode(double value)
  {
    this.value = new Complex(value, 0);
  }

  /**
   * Convenience constructor that takes a string and converts it to a double
   * before storing the value.
   * 
   * @param value
   *          the string representation of the value
   */
  public RealConstantExpressionNode(String value)
  {
    this.value = new Complex(Double.valueOf(value), 0);
  }
  /**
   * Returns the value of the constant
   */
  @Override
  public Complex getValue()
  {
    return new Complex(value);
  }

  /**
   * Returns the type of the node, in this case ExpressionNode.REAL_CONSTANT_NODE
   */
  @Override
  public int getType()
  {
    return ExpressionNode.REAL_CONSTANT_NODE;
  }

  /**
   * Implementation of the visitor design pattern.
   * 
   * Calls visit on the visitor.
   * 
   * @param visitor
   *          the visitor
   */
  @Override
  public void accept(ExpressionNodeVisitor visitor)
  {
    visitor.visit(this);
  }
}
