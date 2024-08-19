
package fractalzoomer.fractal_options.coupling;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona
 */
public abstract class Coupling {
    protected double coupling;
    
    protected Coupling(double coupling) {
        
        this.coupling = coupling;
        
    }
    
    public abstract double calculateFinalCoupling(int iterations);
            
    public Complex[] couple(Complex a1, Complex a2, int iterations) {
        
        double final_coupling = calculateFinalCoupling(iterations);
        
        return new Complex[] {(a2.sub(a1)).times_mutable(final_coupling).plus_mutable(a1), (a1.sub(a2)).times_mutable(final_coupling).plus_mutable(a2)};
        
    }
}
