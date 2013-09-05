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
public class SmoothEscapeTimeAlgorithm2Magnet2 extends OutColorAlgorithm {
  protected double log_convergent_bailout;

    
    public SmoothEscapeTimeAlgorithm2Magnet2(double log_convergent_bailout) {
        
        super();
        this.log_convergent_bailout = log_convergent_bailout;
        
    }
    
    @Override
    public double getResult(Object[] object) {

        
        double temp3 = 0;
        if(!(Boolean)object[2]) {
            double temp = Math.log(((Complex)object[4]).distance_squared((Complex)object[5]));
            temp3 = (log_convergent_bailout - temp) / (Math.log((Double)object[3]) - temp);  
        }
        
        Complex temp = ((Complex)object[1]).sub(((Complex)object[1]).sin());
        
        return (Integer)object[0] +  Math.abs(Math.atan(temp.getIm() / temp.getRe())) * 8  + temp3 + 100800; 

    }
        
}
