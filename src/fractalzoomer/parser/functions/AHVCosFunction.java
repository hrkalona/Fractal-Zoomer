
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class AHVCosFunction extends AbstractOneArgumentFunction {
    
    public AHVCosFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.ahvcos();
        
    }
    
}
