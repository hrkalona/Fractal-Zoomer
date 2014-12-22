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
public class SmoothBiomorphsNova extends SmoothBiomorphs {

    protected double log_convergent_bailout;

    public SmoothBiomorphsNova(double log_convergent_bailout, int algorithm) {

        super(0, 2.0, algorithm);
        this.log_convergent_bailout = log_convergent_bailout;

    }

    @Override
    public double getResult(Object[] object) {

        double temp3;
        if(algorithm == 0) {
            double temp2 = Math.log(((Complex)object[3]).distance_squared((Complex)object[4]));
            temp3 = (Integer)object[0] + (log_convergent_bailout - temp2) / (Math.log((Double)object[2]) - temp2);
        }
        else {
            double temp4 = Math.log(((Double)object[2]) + 1e-33);

            double power = temp4 / Math.log(((Complex)object[3]).distance_squared(((Complex)object[4])));

            double f = Math.log(log_convergent_bailout / temp4) / Math.log(power);

            temp3 = (Integer)object[0] + f;
        }

        double temp4 = ((Complex)object[1]).getRe();
        double temp5 = ((Complex)object[1]).getIm();

        return temp4 > -bailout && temp4 < bailout || temp5 > -bailout && temp5 < bailout ? temp3 + 100800 : temp3 + 100850;

    }
}
