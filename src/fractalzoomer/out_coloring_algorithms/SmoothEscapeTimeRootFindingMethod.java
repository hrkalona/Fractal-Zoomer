package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;





/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class SmoothEscapeTimeRootFindingMethod extends OutColorAlgorithm {
  private double log_convergent_bailout;

    public SmoothEscapeTimeRootFindingMethod(double log_convergent_bailout) {

        super();
        this.log_convergent_bailout = log_convergent_bailout;

    }

    @Override
    public double getResult(Object[] object) {

        //double temp4 = Math.log(((Complex)object[3]).distance_squared((Complex)object[4]));
        //return (Integer)object[0] - (log_convergent_bailout - temp4) / ((Double)object[2] - temp4);
  
        double temp = Math.log(((Complex)object[3]).distance_squared((Complex)object[4]));
        return (Integer)object[0] + (log_convergent_bailout - temp) / (Math.log((Double)object[2]) - temp) + 100800;  

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }

}
