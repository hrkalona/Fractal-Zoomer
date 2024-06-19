
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class FuzzFunction extends AbstractTwoArgumentFunction {
    
    public FuzzFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return argument.fuzz(argument2);
        
    }
    
}
