

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
    @Override
  public int getType()
  {
    return ExpressionNode.ADDITION_NODE;
  }

  /**
   * Returns the value of the sub-expression that is rooted at this node.
   * 
   * All the terms are evaluated and added or subtracted from the total sum.
   */
    @Override
  public Complex getValue()
  {
    Complex sum = new Complex(firstNode.getValue());  
    if(firstMode == SUB) {
        sum.negative_mutable();
    }
    
    for (int i = 0; i < termCount; i++)
    {
      Term t = terms[i];
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
