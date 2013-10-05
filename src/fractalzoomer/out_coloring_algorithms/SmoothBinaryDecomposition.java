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
public class SmoothBinaryDecomposition extends OutColorAlgorithm {
  protected double log_bailout_squared;

    public SmoothBinaryDecomposition(double log_bailout_squared) {

        super();
        this.log_bailout_squared = log_bailout_squared;

    }

    @Override
    public double getResult(Object[] object) {

        double temp = ((Complex)object[2]).norm_squared();
        double temp2 = ((Complex)object[1]).norm_squared();
        
        temp += 0.000000001;
        temp = Math.log(temp);
  
        double temp3 = (Integer)object[0] + (log_bailout_squared - temp) / (Math.log(temp2) - temp);
        
        return ((Complex)object[1]).getIm() < 0 ? temp3 + 100850 : temp3 + 100800;

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
