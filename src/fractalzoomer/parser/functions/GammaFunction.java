
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class GammaFunction extends AbstractOneArgumentFunction {
    
    public GammaFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.gamma_la();
        
    }
    
}
