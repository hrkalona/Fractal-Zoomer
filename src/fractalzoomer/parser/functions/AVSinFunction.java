
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class AVSinFunction extends AbstractOneArgumentFunction {
    
    public AVSinFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.avsin();
        
    }
    
}
