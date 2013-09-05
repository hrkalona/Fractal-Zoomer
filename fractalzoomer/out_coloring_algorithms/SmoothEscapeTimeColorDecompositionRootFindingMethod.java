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
public class SmoothEscapeTimeColorDecompositionRootFindingMethod extends EscapeTimeColorDecomposition {
  protected double log_convergent_bailout;
    
    public SmoothEscapeTimeColorDecompositionRootFindingMethod(double log_convergent_bailout) {
        
        super();    
        this.log_convergent_bailout = log_convergent_bailout;
        
    }

    @Override
    public double getResult(Object[] object) {

        double temp = Math.floor(1000 * ((Complex)object[1]).getRe() + 0.5) / 1000;
        double temp2 = Math.floor(1000 * ((Complex)object[1]).getIm() + 0.5) / 1000;
        
        double temp3 = Math.log(((Complex)object[3]).distance_squared((Complex)object[4]));
        double temp4 = (log_convergent_bailout - temp3) / (Math.log((Double)object[2]) - temp3);  

        return Math.abs(((Integer)object[0]) + (long)(((Math.atan2(temp2, temp) / (pi2)  + 0.75) * pi59)  + (temp * temp + temp2 * temp2) * 2.5) + temp4 + 100800);
       
    }
    
}
