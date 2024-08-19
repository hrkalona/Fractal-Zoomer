
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public abstract class AbstractOneArgumentFunction {
    
    protected AbstractOneArgumentFunction() {
        
    }
    
    public abstract Complex evaluate(Complex argument);
    
}
