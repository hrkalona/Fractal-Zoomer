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
public class SmoothBinaryDecomposition2Magnet1 extends SmoothBinaryDecomposition2 {
  protected double log_convergent_bailout;

    public SmoothBinaryDecomposition2Magnet1(double log_bailout_squared, double log_convergent_bailout) {

        super(log_bailout_squared);
        this.log_convergent_bailout = log_convergent_bailout;

    }

    @Override
    public double getResult(Object[] object) {

        double temp3;
        if((Boolean)object[2]) {
            double temp = ((Complex)object[4]).norm_squared();
            double temp2 = ((Complex)object[1]).norm_squared();
            temp += 0.000000001;
            temp = Math.log(temp);
            temp3 = (Integer)object[0] + (log_bailout_squared - temp) / (Math.log(temp2) - temp);
        }
        else {
            temp3 = (Integer)object[0] - (log_convergent_bailout - (Double)object[3]) / ((Double)object[3] - Math.log(((Complex)object[1]).distance_squared((Complex)object[4])));
        }
        
        return ((Boolean)object[2] ? (((Complex)object[1]).getRe() < 0 ? temp3 + 100850 : temp3 + 100906) : ((Complex)object[1]).getRe() < 0 ? temp3 + 100850 : temp3 + 100800);

    }
}
