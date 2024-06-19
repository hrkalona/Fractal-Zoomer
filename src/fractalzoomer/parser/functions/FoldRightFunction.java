
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class FoldRightFunction extends AbstractTwoArgumentFunction {
    
    public FoldRightFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return argument.fold_right(argument2);
        
    }
    
}
