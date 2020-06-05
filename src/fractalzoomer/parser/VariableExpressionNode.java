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
 * An ExpressionNode that stores a named variable
 */
public class VariableExpressionNode implements ExpressionNode
{
  /** The name of the variable */
  private String name;
  /** The value of the variable */
  private Complex value;
  /** indicates if the value has been set */
  private boolean valueSet;
  /*The hash of the name, for optimization */
  private int hash;

  /**
   * Construct with the name of the variable.
   * 
   * @param name
   *          the name of the variable
   */
  public VariableExpressionNode(String name)
  {
    this.name = name;
    valueSet = false;
    value = new Complex();
    hash = name.hashCode();
  }

  /**
   * @return the name of the variable
   */
  public String getName()
  {
    return name;
  }

  /**
   * @return the hash of the variable
   */
  public int getHash()
  {
    return hash;
  }

  /**
   * Returns the type of the node, in this case ExpressionNode.VARIABLE_NODE
   */
  @Override
  public int getType()
  {
    return ExpressionNode.VARIABLE_NODE;
  }

  /**
   * Sets the value of the variable
   * 
   * @param value
   *          the value of the variable
   */
  public void setValue(Complex value)
  {
    this.value = value;
    this.valueSet = true;
  }

  /**
   * Returns the value of the variable but throws an exception if the value has
   * not been set
   */
  @Override
  public Complex getValue()
  {
    //if (valueSet) //We might crash but we improve performance
      return value;
    //else
      //throw new EvaluationException("Variable '" + name + "' was not initialized.");
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
