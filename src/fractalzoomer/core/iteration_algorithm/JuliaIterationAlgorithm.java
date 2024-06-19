
package fractalzoomer.core.iteration_algorithm;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.functions.Fractal;

/**
 *
 * @author hrkalona2
 */
public class JuliaIterationAlgorithm extends IterationAlgorithm {
    
    public JuliaIterationAlgorithm(Fractal fractal) {
        
        super(fractal);
        
    }
    
    @Override
    public double calculate(GenericComplex number) {
        
        return fractal.calculateJulia(number);
        
    }

    @Override
    public double[] calculateVectorized(GenericComplex[] numbers) {

        return fractal.calculateJuliaVectorized(numbers);

    }
    
    @Override
    public Complex calculateDomain(GenericComplex number) {
        
        return fractal.calculateJuliaDomain((Complex)number);
        
    }

    @Override
    public void calculateOrbit() {
        
        fractal.calculateJuliaOrbit();
        
    }

    @Override
    public double[] calculate3D(GenericComplex number) {
        
        double value = fractal.calculateJulia(number);
        return new double[] {fractal.getJulia3DHeight(value), value};
        
    }

    @Override
    public boolean isJulia() {
        return true;
    }

}
