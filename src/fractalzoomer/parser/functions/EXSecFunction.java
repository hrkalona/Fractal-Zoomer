
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class EXSecFunction extends AbstractOneArgumentFunction {
    
    public EXSecFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.exsec();
        
    }
    
}
