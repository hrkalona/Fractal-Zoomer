

package fractalzoomer.parser;

import fractalzoomer.core.Complex;

/**
 * An ExpressionNode that stores a named variable
 */
public class VariableExpressionNode implements ExpressionNode
{
  /** The name of the variable */
  private String name;
  /** The value of the variable */
  private Complex value;
  /** indicates if the value has been set */
  private boolean valueSet;
  /*The hash of the name, for optimization */
  private int hash;

  /**
   * Construct with the name of the variable.
   * 
   * @param name
   *          the name of the variable
   */
  public VariableExpressionNode(String name)
  {
    this.name = name;
    valueSet = false;
    value = new Complex();
    hash = name.hashCode();
  }

  /**
   * @return the name of the variable
   */
  public String getName()
  {
    return name;
  }

  /**
   * @return the hash of the variable
   */
  public int getHash()
  {
    return hash;
  }

  /**
   * Returns the type of the node, in this case ExpressionNode.VARIABLE_NODE
   */
  @Override
  public int getType()
  {
    return ExpressionNode.VARIABLE_NODE;
  }

  /**
   * Sets the value of the variable
   * 
   * @param value
   *          the value of the variable
   */
  public void setValue(Complex value)
  {
    this.value = value;
    this.valueSet = true;
  }

  /**
   * Returns the value of the variable but throws an exception if the value has
   * not been set
   */
  @Override
  public Complex getValue()
  {
    //if (valueSet) //We might crash but we improve performance
      return value;
    //else
      //throw new EvaluationException("Variable '" + name + "' was not initialized.");
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
