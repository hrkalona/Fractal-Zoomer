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

import fractalzoomer.core.Complex;


/**
 * An ExpressionNode that handles additions and subtractions. The node can hold
 * an arbitrary number of terms that are either added or subtraced from the sum.
 * 
 */
public class AdditionExpressionNode extends SequenceExpressionNode
{
    public static final int ADD = 0;
    public static final int SUB = 1;

  /**
   * Default constructor.
   */
  public AdditionExpressionNode()
  {}

  /**
   * Constructor to create an addition with the first term already added.
   * 
   * @param node
   *          the term to be added
   * @param mode
   *          a flag indicating whether the term is added or subtracted
   */
  public AdditionExpressionNode(ExpressionNode node, int mode)
  {
    super(node, mode);
  }

  /**
   * Returns the type of the node, in this case ExpressionNode.ADDITION_NODE
   */
  public int getType()
  {
    return ExpressionNode.ADDITION_NODE;
  }

  /**
   * Returns the value of the sub-expression that is rooted at this node.
   * 
   * All the terms are evaluated and added or subtracted from the total sum.
   */
  public Complex getValue()
  {
    Complex sum = new Complex();
    for (Term t : terms)
    {
      if (t.mode == ADD)
        sum.plus_mutable(t.expression.getValue());
      else
        sum.sub_mutable(t.expression.getValue());
    }
    return sum;
  }

  /**
   * Implementation of the visitor design pattern.
   * 
   * Calls visit on the visitor and then passes the visitor on to the accept
   * method of all the terms in the sum.
   * 
   * @param visitor
   *          the visitor
   */
  public void accept(ExpressionNodeVisitor visitor)
  {
    visitor.visit(this);
    for (Term t : terms)
      t.expression.accept(visitor);
  }

}
