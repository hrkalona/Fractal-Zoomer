
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class DEtaFunction extends AbstractOneArgumentFunction {
    
    public DEtaFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.dirichlet_eta();
        
    }
    
}
