
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class ACscFunction extends AbstractOneArgumentFunction {
    
    public ACscFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.acsc();
        
    }
    
}
