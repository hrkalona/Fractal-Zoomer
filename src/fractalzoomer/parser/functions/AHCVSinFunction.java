
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class AHCVSinFunction extends AbstractOneArgumentFunction {
    
    public AHCVSinFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.ahcvsin();
        
    }
    
}
