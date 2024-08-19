
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class HVSinFunction extends AbstractOneArgumentFunction {
    
    public HVSinFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.hvsin();
        
    }
    
}
