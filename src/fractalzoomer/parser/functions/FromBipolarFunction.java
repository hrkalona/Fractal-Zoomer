
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class FromBipolarFunction extends AbstractTwoArgumentFunction {
    
    public FromBipolarFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return argument.fromBiPolar(argument2);
        
    }
    
}
