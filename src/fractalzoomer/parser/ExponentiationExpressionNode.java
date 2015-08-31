/* 
 * Fractal Zoomer, Copyright (C) 2015 hrkalona2
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
  public int getType()
  {
    return ExpressionNode.EXPONENTIATION_NODE;
  }
  
  /**
   * Returns the value of the sub-expression that is rooted at this node.
   * 
   * Calculates base^exponent
   */
  public Complex getValue()
  {
      Complex zexponent = exponent.getValue();
      
      if(zexponent.getIm() == 0) {
          if(zexponent.getRe() == 2) {
              return base.getValue().square();
          }
          else if(zexponent.getRe() == 3) {
              return base.getValue().cube();
          }
          else if(zexponent.getRe() == 4) {
              return base.getValue().fourth();
          }
          else if(zexponent.getRe() == 5) {
              return base.getValue().fifth();
          }
          else if(zexponent.getRe() == 6) {
              return base.getValue().sixth();
          }
          else if(zexponent.getRe() == 7) {
              return base.getValue().seventh();
          }
          else if(zexponent.getRe() == 8) {
              return base.getValue().eighth();
          }
          else if(zexponent.getRe() == 9) {
              return base.getValue().ninth();
          }
          else if(zexponent.getRe() == 10) {
              return base.getValue().tenth();
          }
          return base.getValue().pow(exponent.getValue().getRe());
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
  public void accept(ExpressionNodeVisitor visitor)
  {
    visitor.visit(this);
    base.accept(visitor);
    exponent.accept(visitor);
  }
}
