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
 * A visitor that sets a variable with a specific name to a given value
 */
public class SetVariable implements ExpressionNodeVisitor
{

  private String name;
  private Complex value;
  private int hash;

  /**
   * Construct the visitor with the name and the value of the variable to set
   * 
   * @param name
   *          the name of the variable
   * @param value
   *          the value of the variable
   */
  public SetVariable(String name, Complex value)
  {
    super();
    this.name = name;
    this.value = value;
    hash = name.hashCode();
  }

  public void setValue(Complex value) {
    this.value = value;
  }

  /**
   * Checks the nodes name against the name to set and sets the value if the two
   * strings match
   */
  @Override
  public void visit(VariableExpressionNode node)
  {
    //if (node.getName().equals(name))
    if(node.getHash() == hash)
      node.setValue(new Complex(value));
  }

  /** Do nothing */
  @Override
  public void visit(RealConstantExpressionNode node)
  {}
  
  /** Do nothing */
  @Override
  public void visit(ImaginaryConstantExpressionNode node)
  {}

  /** Do nothing */
  @Override
  public void visit(AdditionExpressionNode node)
  {}

  /** Do nothing */
  @Override
  public void visit(MultiplicationExpressionNode node)
  {}

  /** Do nothing */
  @Override
  public void visit(ExponentiationExpressionNode node)
  {}

  /** Do nothing */
  @Override
  public void visit(FunctionExpressionNode node)
  {}
  
  /** Do nothing */
  @Override
  public void visit(Function2ArgumentsExpressionNode node)
  {}
  
  /** Do nothing */
  @Override
  public void visit(FunctionUserMultiArgumentExpressionNode node)
  {}
  
  @Override
  public void visit(FunctionUserMultiArgument2ExpressionNode node)
  {}
  
  /** Do nothing */
  @Override
  public void visit(FunctionUser1ArgumentExpressionNode node)
  {}
  
  /** Do nothing */
  @Override
  public void visit(FunctionUser2ArgumentExpressionNode node)
  {}
  
  /** Do nothing */
  @Override
  public void visit(NoArgumentExpressionNode node)
  {}

  /** Do nothing */
  @Override
  public void visit(FunctionDerivative2ArgumentsExpressionNode node)
  {}

  /** Do nothing */
  @Override
  public void visit(ConstantExpressionNode node)
  {}

  /** Do nothing */
  @Override
  public void visit(RandomConstantExpressionNode node)
  {}

}
