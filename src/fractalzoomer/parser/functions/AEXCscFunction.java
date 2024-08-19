
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class AEXCscFunction extends AbstractOneArgumentFunction {
    
    public AEXCscFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.aexcsc();
        
    }
    
}
