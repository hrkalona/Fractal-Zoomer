package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

public class HypotFunction extends AbstractOneArgumentFunction {

    public HypotFunction() {

        super();

    }

    @Override
    public Complex evaluate(Complex argument) {

        return new Complex(argument.hypot(), 0);

    }
}
