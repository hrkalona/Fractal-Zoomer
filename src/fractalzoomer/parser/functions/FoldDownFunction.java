
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class FoldDownFunction extends AbstractTwoArgumentFunction {
    
    public FoldDownFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return argument.fold_down(argument2);
        
    }
    
}
