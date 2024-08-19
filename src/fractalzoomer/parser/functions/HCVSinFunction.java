
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class HCVSinFunction extends AbstractOneArgumentFunction {
    
    public HCVSinFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.hcvsin();
        
    }
    
}
