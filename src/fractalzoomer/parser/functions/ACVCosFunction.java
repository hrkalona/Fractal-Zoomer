
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class ACVCosFunction extends AbstractOneArgumentFunction {
    
    public ACVCosFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.acvcos();
        
    }
    
}
