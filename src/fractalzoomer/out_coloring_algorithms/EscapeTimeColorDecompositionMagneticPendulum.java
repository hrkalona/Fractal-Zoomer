
package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeColorDecompositionMagneticPendulum extends EscapeTimeColorDecomposition {
    private Complex[] magnets;
    
    public EscapeTimeColorDecompositionMagneticPendulum(Complex[] magnets, OutColorAlgorithm EscapeTimeAlg) {
        super(EscapeTimeAlg);
        this.magnets = magnets;
        OutUsingIncrement = false;
    }

    @Override
    public double getResult(Object[] object) {

        Complex z = ((Complex)object[1]);
        int min_i = 0;
        double min = Double.MAX_VALUE;
        
        for(int i = 0; i < magnets.length; i++) {
            double temp_dist = z.distance_squared(magnets[i]);
            if(temp_dist < min) {
                min = temp_dist;
                min_i  = i;
            }
        }
 
        return pi59 * (min_i + 1) + EscapeTimeAlg.getResult(object);
        
    }
    
    @Override
    public double getResult3D(Object[] object, double result) {
        
        return ((Complex)object[7]).getRe();
        
    }
    
}
