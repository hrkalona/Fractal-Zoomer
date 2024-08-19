

package fractalzoomer.parser;

public interface ExpressionNodeVisitor
{
  /** Visit a VariableExpressionNode */
  public void visit(VariableExpressionNode node);

  /**  Visit a RealConstantExpressionNode */
  public void visit(RealConstantExpressionNode node);
  
  /**  Visit a ImaginaryConstantExpressionNode */
  public void visit(ImaginaryConstantExpressionNode node);

  /**  Visit a AdditionExpressionNode */
  public void visit(AdditionExpressionNode node);

  /**  Visit a MultiplicationExpressionNode */
  public void visit(MultiplicationExpressionNode node);

  /**  Visit a ExponentiationExpressionNode */
  public void visit(ExponentiationExpressionNode node);

  /**  Visit a FunctionExpressionNode */
  public void visit(FunctionExpressionNode node);
  
  /**  Visit a Function2ArgumentsExpressionNode */
  public void visit(Function2ArgumentsExpressionNode node);
  
  /**  Visit a FunctionUserMultiArgumentExpressionNode */
  public void visit(FunctionUserMultiArgumentExpressionNode node);
  
  /**  Visit a FunctionUserMultiArgument2ExpressionNode */
  public void visit(FunctionUserMultiArgument2ExpressionNode node);
  
  /**  Visit a FunctionUserOneArgumentExpressionNode */
  public void visit(FunctionUser1ArgumentExpressionNode node);
  
  /**  Visit a FunctionUser2ArgumentExpressionNode */
  public void visit(FunctionUser2ArgumentExpressionNode node);
  
  /**  Visit a NoArgumentExpressionNode */
  public void visit(NoArgumentExpressionNode node);

  /**  Visit a FunctionDerivative2ArgumentsExpressionNode */
  public void visit(FunctionDerivative2ArgumentsExpressionNode node);

  /**  Visit a ConstantExpressionNode */
  public void visit(ConstantExpressionNode node);

  /**  Visit a RandomConstantExpressionNode */
  public void visit(RandomConstantExpressionNode node);
}
