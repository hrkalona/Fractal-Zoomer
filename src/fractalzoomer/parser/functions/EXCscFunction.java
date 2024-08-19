
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class EXCscFunction extends AbstractOneArgumentFunction {
    
    public EXCscFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.excsc();
        
    }
    
}
