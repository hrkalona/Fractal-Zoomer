

package fractalzoomer.parser;

import java.util.Arrays;

/**
 * A base class for AdditionExpressionNode and MultiplicationExpressionNode.
 * 
 * Holds an arbitrary number of ExpressionNodes together with boolean flags.
 * 
 */
public abstract class SequenceExpressionNode implements ExpressionNode
{
  private static final int INIT_TERM_SIZE = 10;
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
  protected Term[] terms;
  protected int termCount;
  protected ExpressionNode firstNode;
  protected int firstMode;

  /**
   * Default constructor.
   */
  public SequenceExpressionNode()
  {
      terms = new Term[INIT_TERM_SIZE];
      termCount = 0;
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
    terms = new Term[INIT_TERM_SIZE];
    termCount = 0;
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
     if(termCount >= terms.length) {
         terms = Arrays.copyOf(terms, 2 * terms.length);
     }
     terms[termCount] = new Term(mode, node);
     termCount++;
  }

}
