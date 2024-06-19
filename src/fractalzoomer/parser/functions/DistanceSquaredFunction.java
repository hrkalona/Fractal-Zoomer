
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class DistanceSquaredFunction extends AbstractTwoArgumentFunction {
    
    public DistanceSquaredFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return new Complex(argument.distance_squared(argument2), 0);
        
    }
    
}
