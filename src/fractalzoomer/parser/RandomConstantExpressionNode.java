package fractalzoomer.parser;

import fractalzoomer.core.Complex;

public class RandomConstantExpressionNode implements ExpressionNode
{
    /**
     * Returns the value of the constant
     */
    @Override
    public Complex getValue()
    {
        return Complex.random();
    }

    /**
     * Returns the type of the node, in this case ExpressionNode.RANDOM_CONSTANT_NODE
     */
    @Override
    public int getType()
    {
        return ExpressionNode.RANDOM_CONSTANT_NODE;
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
