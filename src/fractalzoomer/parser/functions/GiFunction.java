
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class GiFunction extends AbstractOneArgumentFunction {
    
    public GiFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.gaussian_integer();
        
    }
    
}
