
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class ArgFunction extends AbstractOneArgumentFunction {
    
    public ArgFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return new Complex(argument.arg(), 0);
        
    }
    
}
