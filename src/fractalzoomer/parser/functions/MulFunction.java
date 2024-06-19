
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class MulFunction extends AbstractTwoArgumentFunction {
    
    public MulFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return argument.times(argument2);
        
    }
    
}
