
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class ATanhFunction extends AbstractOneArgumentFunction {
    
    public ATanhFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.atanh();
        
    }
    
}
