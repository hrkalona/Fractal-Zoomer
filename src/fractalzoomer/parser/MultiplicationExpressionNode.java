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
 * An ExpressionNode that handles multiplications and divisions. The node can hold
 * an arbitrary number of factors that are either multiplied or divided to the product.
 * 
 */
public class MultiplicationExpressionNode extends SequenceExpressionNode
{
  /**
   * Default constructor.
   */
  public MultiplicationExpressionNode()
  {}

  /**
   * Constructor to create a multiplication with the first term already added.
   * 
   * @param node
   *          the term to be added
   * @param positive
   *          a flag indicating whether the term is multiplied or divided
   */
  public MultiplicationExpressionNode(ExpressionNode a, boolean positive)
  {
    super(a, positive);
  }

  /**
   * Returns the type of the node, in this case ExpressionNode.MULTIPLICATION_NODE
   */
  public int getType()
  {
    return ExpressionNode.MULTIPLICATION_NODE;
  }

  /**
   * Returns the value of the sub-expression that is rooted at this node.
   * 
   * All the terms are evaluated and multiplied or divided to the product.
   */
  public Complex getValue()
  {
    Complex prod = new Complex(1.0, 0);
    for (Term t : terms)
    {
      if (t.positive)
        prod.times_mutable(t.expression.getValue());
      else
        prod.divide_mutable(t.expression.getValue());
    }
    return prod;
  }

  /**
   * Implementation of the visitor design pattern.
   * 
   * Calls visit on the visitor and then passes the visitor on to the accept
   * method of all the terms in the product.
   * 
   * @param visitor
   *          the visitor
   */
  public void accept(ExpressionNodeVisitor visitor)
  {
    visitor.visit(this);  
    for (Term t: terms)
      t.expression.accept(visitor);
  }
}
