
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class CompareFunction extends AbstractTwoArgumentFunction {
    
    public CompareFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return new Complex(argument.compare(argument2), 0);
        
    }
    
}
