package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

public class FibonacciFunction  extends AbstractOneArgumentFunction {

    public FibonacciFunction() {

        super();

    }

    @Override
    public Complex evaluate(Complex argument) {

        return argument.fibonacci();

    }
}
