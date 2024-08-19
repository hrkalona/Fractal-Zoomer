
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class NormSquaredFunction extends AbstractOneArgumentFunction {
    
    public NormSquaredFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return new Complex(argument.norm_squared(), 0);
        
    }
    
}
