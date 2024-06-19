
package fractalzoomer.fractal_options.coupling;

/**
 *
 * @author hrkalona
 */
public class CosineCoupling extends Coupling {
    private double coupling_amplitude;
    private double coupling_frequency;
    
    public CosineCoupling(double coupling, double coupling_amplitude, double coupling_frequency) {
        
        super(coupling);
        
        this.coupling_amplitude = coupling_amplitude;
        this.coupling_frequency = coupling_frequency;
        
    }

    @Override
    public double calculateFinalCoupling(int iterations) {
        
        return coupling * (1 + coupling_amplitude * Math.cos(iterations * coupling_frequency));
        
    }
    
}
