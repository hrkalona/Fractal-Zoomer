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
public class SmoothColorDecompositionRootFindingMethod extends ColorDecomposition {
  private double log_convergent_bailout;

    public SmoothColorDecompositionRootFindingMethod(double log_convergent_bailout) {

        super();
        this.log_convergent_bailout = log_convergent_bailout;

    }


    @Override
    public double getResult(Object[] object) {
  
        double temp = Math.floor(1000 * ((Complex)object[1]).getRe() + 0.5) / 1000;
        double temp2 = Math.floor(1000 * ((Complex)object[1]).getIm() + 0.5) / 1000;
        
        return Math.abs((long)(((Math.atan2(temp2, temp) / (pi2)  + 0.75) * pi59)  + (temp * temp + temp2 * temp2) * 2.5)) + 100800;

    }
    
    @Override
    public double getResult3D(Object[] object) {
  
        double temp = Math.log(((Complex)object[3]).distance_squared((Complex)object[4]));
        return (Integer)object[0] + (log_convergent_bailout - temp) / (Math.log((Double)object[2]) - temp) + 100800;  

    }
    
}
