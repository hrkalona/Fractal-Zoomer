
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class FoldInFunction extends AbstractTwoArgumentFunction {
    
    public FoldInFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return argument.fold_in(argument2);
        
    }
    
}
