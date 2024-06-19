
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class AEXSecFunction extends AbstractOneArgumentFunction {
    
    public AEXSecFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.aexsec();
        
    }
    
}
