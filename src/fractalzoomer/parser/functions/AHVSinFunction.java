
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class AHVSinFunction extends AbstractOneArgumentFunction {
    
    public AHVSinFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.ahvsin();
        
    }
    
}
