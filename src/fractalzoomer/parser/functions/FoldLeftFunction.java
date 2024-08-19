
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class FoldLeftFunction extends AbstractTwoArgumentFunction {
    
    public FoldLeftFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return argument.fold_left(argument2);
        
    }
    
}
