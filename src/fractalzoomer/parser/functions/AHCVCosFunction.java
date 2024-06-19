
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class AHCVCosFunction extends AbstractOneArgumentFunction {
    
    public AHCVCosFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.ahcvcos();
        
    }
    
}
