
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */ 
public class SechFunction extends AbstractOneArgumentFunction {
    
    public SechFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.sech();
        
    }
    
}
