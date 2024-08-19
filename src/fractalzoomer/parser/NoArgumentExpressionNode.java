
package fractalzoomer.parser;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona
 */
public class NoArgumentExpressionNode implements ExpressionNode {
    /** The value of the constant */
  private Complex value;

  /**
   * Construct with the fixed value.
   * 
   * @param value
   *          the value of the constant
   */
  public NoArgumentExpressionNode()
  {
    this.value = new Complex();
  }

  /**
   * Returns the value of the constant
   */
  @Override
  public Complex getValue()
  {
    return value;
  }

  /**
   * Returns the type of the node, in this case ExpressionNode.NO_ARGUMENT_NODE
   */
  @Override
  public int getType()
  {
    return ExpressionNode.NO_ARGUMENT_NODE;
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
