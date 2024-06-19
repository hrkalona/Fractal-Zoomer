
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class LogNFunction extends AbstractTwoArgumentFunction {
    
    public LogNFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return argument.log().divide(argument2.log());
        
    }
    
}
