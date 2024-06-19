
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */ 
public class CoshFunction extends AbstractOneArgumentFunction {
    
    public CoshFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.cosh();
        
    }
    
}
