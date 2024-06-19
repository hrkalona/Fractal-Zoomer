
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class ToBipolarFunction extends AbstractTwoArgumentFunction {
    
    public ToBipolarFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return argument.toBiPolar(argument2);
        
    }
    
}
