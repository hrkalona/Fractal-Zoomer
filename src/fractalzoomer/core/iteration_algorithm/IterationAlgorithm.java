
package fractalzoomer.core.iteration_algorithm;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.functions.Fractal;

/**
 *
 * @author hrkalona2
 */
public abstract class IterationAlgorithm {
    protected Fractal fractal;
    
    protected IterationAlgorithm(Fractal fractal) {
        this.fractal = fractal;
    }
    
    public abstract double calculate(GenericComplex number);
    public abstract Complex calculateDomain(GenericComplex number);
    public abstract void calculateOrbit();
    public abstract double[] calculate3D(GenericComplex number);

    public abstract double[] calculateVectorized(GenericComplex[] numbers);
    
    public boolean escaped() {
        
        return fractal.escaped();
        
    }

    public boolean[] vescaped() {

        return fractal.vescaped();

    }

    public abstract boolean isJulia();
      
}
