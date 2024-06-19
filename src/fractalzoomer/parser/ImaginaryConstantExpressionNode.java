

package fractalzoomer.parser;

import fractalzoomer.core.Complex;

/**
 * An ExpressionNode that stores a constant value
 */
public class ImaginaryConstantExpressionNode implements ExpressionNode
{
  /** The value of the constant */
  private Complex value;

  /**
   * Convenience constructor that takes a string and converts it to a double
   * before storing the value.
   * 
   * @param value
   *          the string representation of the value
   */
  public ImaginaryConstantExpressionNode(String value)
  {     
      this.value = new Complex(0, Double.valueOf(value));
  }
  
  public ImaginaryConstantExpressionNode(double value)
  {
      this.value = new Complex(0, value);
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
   * Returns the type of the node, in this case ExpressionNode.IMAGINARY_CONSTANT_NODE
   */
  @Override
  public int getType()
  {
    return ExpressionNode.IMAGINARY_CONSTANT_NODE;
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
