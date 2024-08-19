
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class Log10Function extends AbstractOneArgumentFunction {
    
    public Log10Function() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.log().times(0.43429448190325182765);
        
    }
    
}
