
package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.OutColorData;

/**
 *
 * @author hrkalona2
 */
public class ColorDecompositionMagneticPendulum extends ColorDecomposition {
    private Complex[] magnets;
    
    public ColorDecompositionMagneticPendulum(Complex[] magnets) {
        super();
        this.magnets = magnets;
        OutUsingIncrement = false;
    }

    @Override
    public double getResult(OutColorData data) {
        
        Complex z = data.z;
        int min_i = 0;
        double min = Double.MAX_VALUE;
        
        for(int i = 0; i < magnets.length; i++) {
            double temp_dist = z.distance_squared(magnets[i]);
            if(temp_dist < min) {
                min = temp_dist;
                min_i  = i;
            }
        }
 
        return pi59 * (min_i + 1);

    }
    
    @Override
    public double getResult3D(OutColorData data, double result) {
  
        return data.c0.getRe();

    }
    
}
