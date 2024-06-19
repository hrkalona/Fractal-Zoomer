
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class ShearFunction extends AbstractTwoArgumentFunction {
    
    public ShearFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return argument.shear(argument2);
        
    }
    
}
