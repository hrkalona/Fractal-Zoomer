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
public class SmoothBinaryDecompositionNova extends SmoothBinaryDecomposition {
  protected double log_convergent_bailout;

    public SmoothBinaryDecompositionNova(double log_convergent_bailout) {

        super(0);
        this.log_convergent_bailout = log_convergent_bailout;

    }

    @Override
    public double getResult(Object[] object) {

        double temp = Math.log(((Complex)object[3]).distance_squared((Complex)object[4]));
        double temp3 = (Integer)object[0] + (log_convergent_bailout - temp) / (Math.log((Double)object[2]) - temp);  

        return ((Complex)object[1]).getIm() < 0 ? temp3 + 100850 : temp3 + 100800;

    }
}
