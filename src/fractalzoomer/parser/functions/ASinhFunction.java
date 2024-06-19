
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class ASinhFunction extends AbstractOneArgumentFunction {
    
    public ASinhFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.asinh();
        
    }
    
}
