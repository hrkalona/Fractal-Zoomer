
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class Norm2Function extends AbstractOneArgumentFunction {
    
    public Norm2Function() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return new Complex(argument.norm(), 0);
        
    }
    
}
