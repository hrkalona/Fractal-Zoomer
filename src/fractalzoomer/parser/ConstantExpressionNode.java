package fractalzoomer.parser;

import fractalzoomer.core.Complex;

public class ConstantExpressionNode implements ExpressionNode
{
    /** The value of the constant */
    private Complex value;

    /**
     * Construct with the fixed value.
     *
     * @param value
     *          the value of the constant
     */
    public ConstantExpressionNode(Complex value)
    {
        this.value = value;
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
     * Returns the type of the node, in this case ExpressionNode.CONSTANT_NODE
     */
    @Override
    public int getType()
    {
        return ExpressionNode.CONSTANT_NODE;
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
