
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class ImFunction extends AbstractOneArgumentFunction {
    
    public ImFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return new Complex(argument.getIm(), 0);
        
    }
    
}
