

package fractalzoomer.parser;

import fractalzoomer.core.Complex;

/**
 * An ExpressionNode that handles multiplications divisions and remainders. The node can hold
 * an arbitrary number of factors that are either multiplied or divided to the product.
 * 
 */
public class MultiplicationExpressionNode extends SequenceExpressionNode
{
    public static final int MULT = 3;
    public static final int DIV = 4;
    public static final int REM = 5;
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
   * @param mode
   *          a flag indicating whether the term is multiplied, divided or remaindered
   */
  public MultiplicationExpressionNode(ExpressionNode a, int mode)
  {
    super(a, mode);
  }

  /**
   * Returns the type of the node, in this case ExpressionNode.MULTIPLICATION_NODE
   */
    @Override
  public int getType()
  {
    return ExpressionNode.MULTIPLICATION_NODE;
  }

  /**
   * Returns the value of the sub-expression that is rooted at this node.
   * 
   * All the terms are evaluated and multiplied or divided to the product.
   */
    @Override
  public Complex getValue()
  {
    Complex prod = new Complex(firstNode.getValue());
      
    for (int i = 0; i < termCount; i++)
    {
      Term t = terms[i];
      if (t.mode == MULT)
        prod.times_mutable(t.expression.getValue());
      else if(t.mode == DIV)
        prod.divide_mutable(t.expression.getValue());
      else
        prod.remainder_mutable(t.expression.getValue());
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
    @Override
  public void accept(ExpressionNodeVisitor visitor)
  {
    visitor.visit(this);

    firstNode.accept(visitor);
    for (int i = 0; i < termCount; i++)
    {
      terms[i].expression.accept(visitor);
    }
  }
}
