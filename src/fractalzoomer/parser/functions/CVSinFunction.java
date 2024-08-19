
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class CVSinFunction extends AbstractOneArgumentFunction {
    
    public CVSinFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.cvsin();
        
    }
    
}
