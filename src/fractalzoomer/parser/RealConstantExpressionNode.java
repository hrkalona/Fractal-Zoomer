

package fractalzoomer.parser;

import fractalzoomer.core.Complex;

/**
 * An ExpressionNode that stores a constant value
 */
public class RealConstantExpressionNode implements ExpressionNode
{
  /** The value of the constant */
  private Complex value;


  public RealConstantExpressionNode(double value)
  {
    this.value = new Complex(value, 0);
  }

  /**
   * Convenience constructor that takes a string and converts it to a double
   * before storing the value.
   * 
   * @param value
   *          the string representation of the value
   */
  public RealConstantExpressionNode(String value)
  {
    this.value = new Complex(Double.valueOf(value), 0);
  }
  /**
   * Returns the value of the constant
   */
  @Override
  public Complex getValue()
  {
    return new Complex(value);
  }

  /**
   * Returns the type of the node, in this case ExpressionNode.REAL_CONSTANT_NODE
   */
  @Override
  public int getType()
  {
    return ExpressionNode.REAL_CONSTANT_NODE;
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
