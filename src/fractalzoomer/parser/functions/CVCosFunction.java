
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class CVCosFunction extends AbstractOneArgumentFunction {
    
    public CVCosFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.cvcos();
        
    }
    
}
