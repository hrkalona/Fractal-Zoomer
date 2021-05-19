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

public interface ExpressionNodeVisitor
{
  /** Visit a VariableExpressionNode */
  public void visit(VariableExpressionNode node);

  /**  Visit a RealConstantExpressionNode */
  public void visit(RealConstantExpressionNode node);
  
  /**  Visit a ImaginaryConstantExpressionNode */
  public void visit(ImaginaryConstantExpressionNode node);

  /**  Visit a AdditionExpressionNode */
  public void visit(AdditionExpressionNode node);

  /**  Visit a MultiplicationExpressionNode */
  public void visit(MultiplicationExpressionNode node);

  /**  Visit a ExponentiationExpressionNode */
  public void visit(ExponentiationExpressionNode node);

  /**  Visit a FunctionExpressionNode */
  public void visit(FunctionExpressionNode node);
  
  /**  Visit a Function2ArgumentsExpressionNode */
  public void visit(Function2ArgumentsExpressionNode node);
  
  /**  Visit a FunctionUserMultiArgumentExpressionNode */
  public void visit(FunctionUserMultiArgumentExpressionNode node);
  
  /**  Visit a FunctionUserMultiArgument2ExpressionNode */
  public void visit(FunctionUserMultiArgument2ExpressionNode node);
  
  /**  Visit a FunctionUserOneArgumentExpressionNode */
  public void visit(FunctionUser1ArgumentExpressionNode node);
  
  /**  Visit a FunctionUser2ArgumentExpressionNode */
  public void visit(FunctionUser2ArgumentExpressionNode node);
  
  /**  Visit a NoArgumentExpressionNode */
  public void visit(NoArgumentExpressionNode node);

  /**  Visit a FunctionDerivative2ArgumentsExpressionNode */
  public void visit(FunctionDerivative2ArgumentsExpressionNode node);

  /**  Visit a ConstantExpressionNode */
  public void visit(ConstantExpressionNode node);

  /**  Visit a RandomConstantExpressionNode */
  public void visit(RandomConstantExpressionNode node);
}
