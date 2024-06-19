

package fractalzoomer.parser;

import fractalzoomer.core.Complex;

/**
 * An interface for expression nodes.
 * 
 * Every concrete type of expression node has to implement this interface.
 */
public interface ExpressionNode
{
  /** Node id for variable nodes */
  public static final int VARIABLE_NODE = 1;
  /** Node id for real constant nodes */
  public static final int REAL_CONSTANT_NODE = 2;
  /** Node id for addition nodes */
  public static final int ADDITION_NODE = 3;
  /** Node id for multiplication nodes */
  public static final int MULTIPLICATION_NODE = 4;
  /** Node id for exponentiation nodes */
  public static final int EXPONENTIATION_NODE = 5;
  /** Node id for function nodes */
  public static final int FUNCTION_NODE = 6;
  /** Node id for imaginary constant nodes */
  public static final int IMAGINARY_CONSTANT_NODE = 7;
  /** Node id for function nodes with 2 arguments */
  public static final int FUNCTION_2_ARG_NODE = 8;
  /** Node id for function nodes multiple arguments (10) made by the user */
  public static final int FUNCTION_USER_MULTI_ARG_NODE = 9;
  /** Node id for function nodes one argument made by the user */
  public static final int FUNCTION_USER_ONE_ARG_NODE = 10;
  /** Node id for function nodes two arguments made by the user */
  public static final int FUNCTION_USER_TWO_ARG_NODE = 11;
  /** Node id for function nodes multiple arguments (20) made by the user */
  public static final int FUNCTION_USER_MULTI_ARG_2_NODE = 12;
  /** Node id for the no argument */
  public static final int NO_ARGUMENT_NODE = 13;
  /** Node id for derivative function nodes with 2 arguments */
  public static final int FUNCTION_DERIVATIVE_2_ARG_NODE = 14;
  /** Node id for complex constant nodes */
  public static final int CONSTANT_NODE = 15;
  /** Node id for complex random constant nodes */
  public static final int RANDOM_CONSTANT_NODE = 16;

  /**
   * Returns the type of the node.ExpressionNode
   *
   * Each class derived from ExpressionNode representing a specific
   * role in the expression should return the type according to that
   * role.
   * 
   * @return type of the node
   */
  public int getType();
  
  /**
   * Calculates and returns the value of the sub-expression represented by
   * the node.
   * 
   * @return value of expression
   */
  public Complex getValue();
  
  /**
   * Method needed for the visitor design pattern
   * 
   * @param visitor
   *          the visitor
   */
  public void accept(ExpressionNodeVisitor visitor);

}
