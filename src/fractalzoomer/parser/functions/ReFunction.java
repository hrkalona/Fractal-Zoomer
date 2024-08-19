
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class ReFunction extends AbstractOneArgumentFunction {
    
    public ReFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return new Complex(argument.getRe(), 0);
        
    }
    
}
