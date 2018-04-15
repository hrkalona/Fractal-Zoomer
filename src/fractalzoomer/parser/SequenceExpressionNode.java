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

import java.util.ArrayList;

/**
 * A base class for AdditionExpressionNode and MultiplicationExpressionNode.
 * 
 * Holds an arbitrary number of ExpressionNodes together with boolean flags.
 * 
 */
public abstract class SequenceExpressionNode implements ExpressionNode
{
  /**
   * An inner class that defines a pair containing an ExpressionNode and a
   * boolean flag.
   */
  public class Term
  {
    /** the boolean flag */
    public int mode;
    /** the expression node */
    public ExpressionNode expression;

    /**
     * Construct the Term object with some values.
     * @param mode the int flag 
     * @param expression the expression node
     */
    public Term(int mode, ExpressionNode expression)
    {
      super();
      this.mode = mode;
      this.expression = expression;
    }
  }

  /** the list of terms in the sequence */
  protected ArrayList<Term> terms;
  protected ExpressionNode firstNode;
  protected int firstMode;

  /**
   * Default constructor.
   */
  public SequenceExpressionNode()
  {
    this.terms = new ArrayList<Term>();
  }

  /**
   * Constructor to create a sequence with the first term already added.
   * 
   * @param node
   *          the term to be added
   * @param mode
   *          an int flag 
   */
  public SequenceExpressionNode(ExpressionNode a, int mode)
  {
    this.terms = new ArrayList<Term>();
    this.firstNode = a;
    this.firstMode = mode;
  }

  /**
   * Add another term to the sequence
   * @param node
   *          the term to be added
   * @param mode
   *          an int flag 
   */
  public void add(ExpressionNode node, int mode)
  {
    this.terms.add(new Term(mode, node));
  }

}
