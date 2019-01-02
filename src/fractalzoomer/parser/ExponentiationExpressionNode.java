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

import fractalzoomer.core.Complex;

/**
 * An ExpressionNode that handles exponentiation. The node holds
 * a base and an exponent and calulates base^exponent 
 * 
 */
public class ExponentiationExpressionNode implements ExpressionNode
{
  /** the node containing the base */
  private ExpressionNode base;
  /** the node containing the exponent */
  private ExpressionNode exponent;

  /**
   * Construct the ExponentiationExpressionNode with base and exponent
   * @param base the node containing the base
   * @param exponent the node containing the exponent
   */
  public ExponentiationExpressionNode(ExpressionNode base, ExpressionNode exponent)
  {
    this.base = base;
    this.exponent = exponent;
  }

  /**
   * Returns the type of the node, in this case ExpressionNode.EXPONENTIATION_NODE
   */
  @Override
  public int getType()
  {
    return ExpressionNode.EXPONENTIATION_NODE;
  }
  
  /**
   * Returns the value of the sub-expression that is rooted at this node.
   * 
   * Calculates base^exponent
   */
  @Override
  public Complex getValue()
  {
      Complex zexponent = exponent.getValue();
      
      double re = zexponent.getRe();
      double im = zexponent.getIm();
      
      if(im == 0) {
          if(re == 2) {
              return base.getValue().square();
          }
          else if(re == 3) {
              return base.getValue().cube();
          }
          else if(re == 4) {
              return base.getValue().fourth();
          }
          else if(re == 5) {
              return base.getValue().fifth();
          }
          else if(re == 6) {
              return base.getValue().sixth();
          }
          else if(re == 7) {
              return base.getValue().seventh();
          }
          else if(re == 8) {
              return base.getValue().eighth();
          }
          else if(re == 9) {
              return base.getValue().ninth();
          }
          else if(re == 10) {
              return base.getValue().tenth();
          }
          return base.getValue().pow(re);
      }
      else {
          return base.getValue().pow(zexponent);
      }
  }

  /**
   * Implementation of the visitor design pattern.
   * 
   * Calls visit on the visitor and then passes the visitor on to the accept
   * method of the base and the exponent.
   * 
   * @param visitor
   *          the visitor
   */
  @Override
  public void accept(ExpressionNodeVisitor visitor)
  {
    visitor.visit(this);
    base.accept(visitor);
    exponent.accept(visitor);
  }
}
