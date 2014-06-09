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
public class SmoothDistanceEstimator2 extends DistanceEstimator2 {
  private double log_bailout_squared;
  
    public SmoothDistanceEstimator2(double size, int max_iterations, double bailout, double log_bailout_squared) {
        
        super(size, max_iterations, bailout);
        
        this.log_bailout_squared = log_bailout_squared;
        
    }
    
    @Override
    public double getResult(Object[] object) {

        double temp3 = (((Complex)object[1]).norm());
        
        if((temp3 * Math.log(temp3) / ((Complex)object[2]).norm()) > limit) {
            double temp = ((Complex)object[3]).norm_squared();
            double temp2 = temp3 * temp3;

            temp += 0.000000001;
            temp = Math.log(temp);
        
            return (Integer)object[0] + (log_bailout_squared - temp) / (Math.log(temp2) - temp) + 100800;
        }
        else {
            return max_iterations;
        }

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
