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
public class SmoothEscapeTimeGaussianInteger4Magnet extends OutColorAlgorithm {
  protected double log_convergent_bailout;

    
    public SmoothEscapeTimeGaussianInteger4Magnet(double log_convergent_bailout) {
        
        super();
        this.log_convergent_bailout = log_convergent_bailout;
        
    }
    
    @Override
    public double getResult(Object[] object) {

        
        double temp3 = 0;
        if(!(Boolean)object[2]) {
            double temp = Math.log(((Complex)object[4]).distance_squared(1));
            temp3 = (log_convergent_bailout - temp) / (Math.log((Double)object[3]) - temp); 
        }
        
        Complex temp = ((Complex)object[1]).sub(((Complex)object[1]).gaussian_integer());
        
        return Math.abs((Integer)object[0] +  temp.getRe() + temp.getIm() + temp3) + 100800;

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
        
}
