

package fractalzoomer.parser;

import fractalzoomer.core.Complex;

/**
 * A visitor that sets a variable with a specific name to a given value
 */
public class SetVariable implements ExpressionNodeVisitor
{

  private String name;
  private Complex value;
  private int hash;

  /**
   * Construct the visitor with the name and the value of the variable to set
   * 
   * @param name
   *          the name of the variable
   * @param value
   *          the value of the variable
   */
  public SetVariable(String name, Complex value)
  {
    super();
    this.name = name;
    this.value = value;
    hash = name.hashCode();
  }

  public void setValue(Complex value) {
    this.value = value;
  }

  /**
   * Checks the nodes name against the name to set and sets the value if the two
   * strings match
   */
  @Override
  public void visit(VariableExpressionNode node)
  {
    //Todo hopefully no collisions
    //if (node.getName().equals(name))
    if(node.getHash() == hash)
      node.setValue(new Complex(value));
  }

  /** Do nothing */
  @Override
  public void visit(RealConstantExpressionNode node)
  {}
  
  /** Do nothing */
  @Override
  public void visit(ImaginaryConstantExpressionNode node)
  {}

  /** Do nothing */
  @Override
  public void visit(AdditionExpressionNode node)
  {}

  /** Do nothing */
  @Override
  public void visit(MultiplicationExpressionNode node)
  {}

  /** Do nothing */
  @Override
  public void visit(ExponentiationExpressionNode node)
  {}

  /** Do nothing */
  @Override
  public void visit(FunctionExpressionNode node)
  {}
  
  /** Do nothing */
  @Override
  public void visit(Function2ArgumentsExpressionNode node)
  {}
  
  /** Do nothing */
  @Override
  public void visit(FunctionUserMultiArgumentExpressionNode node)
  {}
  
  @Override
  public void visit(FunctionUserMultiArgument2ExpressionNode node)
  {}
  
  /** Do nothing */
  @Override
  public void visit(FunctionUser1ArgumentExpressionNode node)
  {}
  
  /** Do nothing */
  @Override
  public void visit(FunctionUser2ArgumentExpressionNode node)
  {}
  
  /** Do nothing */
  @Override
  public void visit(NoArgumentExpressionNode node)
  {}

  /** Do nothing */
  @Override
  public void visit(FunctionDerivative2ArgumentsExpressionNode node)
  {}

  /** Do nothing */
  @Override
  public void visit(ConstantExpressionNode node)
  {}

  /** Do nothing */
  @Override
  public void visit(RandomConstantExpressionNode node)
  {}

}
