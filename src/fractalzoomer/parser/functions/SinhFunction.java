
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */ 
public class SinhFunction extends AbstractOneArgumentFunction {
    
    public SinhFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.sinh();
        
    }
    
}
