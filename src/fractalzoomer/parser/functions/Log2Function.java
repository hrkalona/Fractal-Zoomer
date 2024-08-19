
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class Log2Function extends AbstractOneArgumentFunction {
    
    public Log2Function() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.log().times(1.442695040888963407360);
        
    }
    
}
