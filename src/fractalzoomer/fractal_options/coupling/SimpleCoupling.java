
package fractalzoomer.fractal_options.coupling;

/**
 *
 * @author hrkalona
 */
public class SimpleCoupling extends Coupling {
    
    public SimpleCoupling(double coupling) {
        
        super(coupling);
        
    }

    @Override
    public double calculateFinalCoupling(int iterations) {
        
        return coupling;
        
    }
    
}
