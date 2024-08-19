
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class SqrtFunction extends AbstractOneArgumentFunction {
    
    public SqrtFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.sqrt();
        
    }
    
}
