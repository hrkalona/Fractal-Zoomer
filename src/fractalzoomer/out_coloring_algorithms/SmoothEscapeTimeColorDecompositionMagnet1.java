/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class SmoothEscapeTimeColorDecompositionMagnet1 extends ColorDecomposition {
  protected double log_convergent_bailout;

    
    public SmoothEscapeTimeColorDecompositionMagnet1(double log_convergent_bailout) {
        
        super();
        this.log_convergent_bailout = log_convergent_bailout;
        
    }
    
    @Override
    public double getResult(Object[] object) {

        
        double temp3 = 0;
        if(!(Boolean)object[2]) {
            temp3 = -(log_convergent_bailout - (Double)object[3]) / ((Double)object[3] - Math.log(((Complex)object[1]).distance_squared((Complex)object[4])));
        }
        
        return Math.abs(((Integer)object[0]) + (Math.atan2(((Complex)object[1]).getIm(), ((Complex)object[1]).getRe()) / pi2  + 0.75) * pi59 + 100800 + temp3);

    }
        
}
