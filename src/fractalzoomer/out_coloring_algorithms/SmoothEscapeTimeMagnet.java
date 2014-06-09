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
public class SmoothEscapeTimeMagnet extends SmoothEscapeTime {
  private double log_convergent_bailout;
    
    public SmoothEscapeTimeMagnet(double log_bailout_squared, double log_convergent_bailout) {
        
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
            return (Integer)object[0] + (log_bailout_squared - temp) / (Math.log(temp2) - temp) + 100906;
        }
        else {
            //return (Integer)object[0] - (log_convergent_bailout - (Double)object[3]) / ((Double)object[3] - Math.log(((Complex)object[4]).distance_squared(1))) + 100800;
            double temp = Math.log(((Complex)object[4]).distance_squared(1));
            return (Integer)object[0] + (log_convergent_bailout - temp) / (Math.log((Double)object[3]) - temp) + 100800; 
        }

    }
    

}
