
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class FoldOutFunction extends AbstractTwoArgumentFunction {
    
    public FoldOutFunction() {
        
        super();
        
    }
    
    @Override
    public Complex evaluate(Complex argument, Complex argument2) {
        
        return argument.fold_out(argument2);
        
    }
    
}
