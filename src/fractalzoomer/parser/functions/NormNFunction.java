
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class NormNFunction extends AbstractTwoArgumentFunction {
    
    public NormNFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return argument.nnorm(argument2);
        
    }
    
}
