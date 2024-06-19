
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */ 
public class TanhFunction extends AbstractOneArgumentFunction {
    
    public TanhFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.tanh();
        
    }
    
}
