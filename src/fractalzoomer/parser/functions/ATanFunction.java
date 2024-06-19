
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class ATanFunction extends AbstractOneArgumentFunction {
    
    public ATanFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.atan();
        
    }
    
}
