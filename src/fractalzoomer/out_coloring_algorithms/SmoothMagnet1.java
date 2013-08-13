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
public class SmoothMagnet1 extends Smooth {
  private double log_convergent_bailout;
    
    public SmoothMagnet1(double log_bailout_squared, double log_convergent_bailout) {
        
        super(log_bailout_squared);
        this.log_convergent_bailout = log_convergent_bailout;
        
    }
    
    @Override
    public double getResult(Object[] object) {

        if((Boolean)object[2]) {
            double temp = ((Complex)object[4]).norm_squared();
            double temp2 = ((Complex)object[1]).norm_squared();
            temp += 0.000000001;
            temp = Math.log(temp);
            return (Integer)object[0] + 100234 + (log_bailout_squared - temp) / (Math.log(temp2) - temp);
        }
        else {
            return (Integer)object[0] - (log_convergent_bailout - (Double)object[3]) / ((Double)object[3] - Math.log(((Complex)object[1]).distance_squared((Complex)object[4]))) + 100800;
        }

    }
    

}
