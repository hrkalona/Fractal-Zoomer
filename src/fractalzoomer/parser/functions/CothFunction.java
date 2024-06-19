
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */ 
public class CothFunction extends AbstractOneArgumentFunction {
    
    public CothFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.coth();
        
    }
    
}
