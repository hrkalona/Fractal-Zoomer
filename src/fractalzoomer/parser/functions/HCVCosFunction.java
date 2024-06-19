
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class HCVCosFunction extends AbstractOneArgumentFunction {
    
    public HCVCosFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.hcvcos();
        
    }
    
}
