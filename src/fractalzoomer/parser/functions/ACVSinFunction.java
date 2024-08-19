
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class ACVSinFunction extends AbstractOneArgumentFunction {
    
    public ACVSinFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.acvsin();
        
    }
    
}
