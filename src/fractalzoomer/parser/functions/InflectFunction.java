
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class InflectFunction extends AbstractTwoArgumentFunction {
    
    public InflectFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return argument.inflection(argument2);
        
    }
    
}
