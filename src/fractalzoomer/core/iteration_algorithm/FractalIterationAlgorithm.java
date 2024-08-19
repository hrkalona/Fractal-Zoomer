
package fractalzoomer.core.iteration_algorithm;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.functions.Fractal;

/**
 *
 * @author hrkalona2
 */
public class FractalIterationAlgorithm extends IterationAlgorithm {
    public FractalIterationAlgorithm(Fractal fractal) {
        
        super(fractal);
        
    }
    
    @Override
    public double calculate(GenericComplex number) {
        
        return fractal.calculateFractal(number);
        
    }

    @Override
    public Complex calculateDomain(GenericComplex number) {
        
        return fractal.calculateFractalDomain((Complex)number);
        
    }

    @Override
    public double[] calculateVectorized(GenericComplex[] numbers) {

        return fractal.calculateFractalVectorized(numbers);

    }

    @Override
    public void calculateOrbit() {
        
        fractal.calculateFractalOrbit();
        
    }

    @Override
    public double[] calculate3D(GenericComplex number) {
        
        double value = fractal.calculateFractal(number);
        return new double[] {fractal.getFractal3DHeight(value), value};
        
    }

    @Override
    public boolean isJulia() {
        return false;
    }
    
}
