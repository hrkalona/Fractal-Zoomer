package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

public class RootFunction extends AbstractTwoArgumentFunction {

    public RootFunction() {

        super();

    }

    @Override
    public Complex evaluate(Complex argument, Complex argument2) {

        return argument.pow(argument2.reciprocal());

    }
}
