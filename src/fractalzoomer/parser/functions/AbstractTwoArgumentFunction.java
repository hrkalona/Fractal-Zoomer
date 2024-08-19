
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public abstract class AbstractTwoArgumentFunction {
    
    protected AbstractTwoArgumentFunction() {
        
    }
    
    public abstract Complex evaluate(Complex argument, Complex argument2);
    
}
