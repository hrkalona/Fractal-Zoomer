
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class DistanceFunction extends AbstractTwoArgumentFunction {
    
    public DistanceFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return new Complex(argument.distance(argument2), 0);
        
    }
    
}
