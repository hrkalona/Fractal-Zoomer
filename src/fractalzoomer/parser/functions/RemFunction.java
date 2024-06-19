
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class RemFunction extends AbstractTwoArgumentFunction {
    
    public RemFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return argument.remainder(argument2);
        
    }
    
}
