
package fractalzoomer.fractal_options.coupling;

import java.util.Random;

/**
 *
 * @author hrkalona
 */
public class RandomCoupling extends Coupling {
    private double coupling_amplitude;
    private int coupling_seed;
    private Random generator;
    
    public RandomCoupling(double coupling, double coupling_amplitude, int coupling_seed) {
        
        super(coupling);
        
        this.coupling_amplitude = coupling_amplitude;
        this.coupling_seed = coupling_seed;
        generator = new Random(coupling_seed);
        
    }

    @Override
    public double calculateFinalCoupling(int iterations) {
        
          if(iterations == 0) {
                generator = new Random(coupling_seed);
          }
          return coupling * (1 + coupling_amplitude * (generator.nextDouble() - 0.5) * 2);
          
    }
    
}
