
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public class RZetaFunction extends AbstractOneArgumentFunction {
    
    public RZetaFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument) {
        
        return argument.riemann_zeta();
        
    }
    
}
