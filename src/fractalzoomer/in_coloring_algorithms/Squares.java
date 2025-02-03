

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.InColorData;

/**
 *
 * @author hrkalona2
 */
public class Squares extends InColorAlgorithm {
  private double pi2;
  private double pi59;
  private int max_iterations;
    

    public Squares(int max_iterations) { 
       
        super();
        
        pi2 = 2 * Math.PI;
        pi59 = 59 * Math.PI;
        InUsingIncrement = false;
        this.max_iterations = max_iterations;
        
    }
    
    @Override
    public double getResult(InColorData data) {

        Complex z = data.z;
        double re = z.getRe();
        double im = z.getIm();
        
        return ((Math.abs((int)(re * 40)) % 2) ^ (Math.abs((int)(im * 40)) % 2)) == 1 ? max_iterations + Math.abs((Math.atan2(im, re) / (pi2)  + 0.75) * pi59) :  max_iterations + Math.abs((Math.atan2(re, im) / (pi2)  + 0.75) * pi59);

    }
    
}
