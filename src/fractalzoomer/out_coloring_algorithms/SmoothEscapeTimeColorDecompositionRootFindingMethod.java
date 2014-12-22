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
public class SmoothEscapeTimeColorDecompositionRootFindingMethod extends EscapeTimeColorDecomposition {

    protected double log_convergent_bailout;
    private int algorithm;

    public SmoothEscapeTimeColorDecompositionRootFindingMethod(double log_convergent_bailout, int algorithm) {

        super();
        this.log_convergent_bailout = log_convergent_bailout;
        this.algorithm = algorithm;

    }

    @Override
    public double getResult(Object[] object) {

        if(algorithm == 0) {
            double temp = Math.floor(1000 * ((Complex)object[1]).getRe() + 0.5) / 1000;
            double temp2 = Math.floor(1000 * ((Complex)object[1]).getIm() + 0.5) / 1000;

            double temp3 = Math.log(((Complex)object[3]).distance_squared((Complex)object[4]));
            double temp4 = (log_convergent_bailout - temp3) / (Math.log((Double)object[2]) - temp3);

            return Math.abs(((Integer)object[0]) + (long)(((Math.atan2(temp2, temp) / (pi2) + 0.75) * pi59) + (temp * temp + temp2 * temp2) * 2.5) + temp4) + 100800;
        }
        else {

            double temp = Math.floor(1000 * ((Complex)object[1]).getRe() + 0.5) / 1000;
            double temp2 = Math.floor(1000 * ((Complex)object[1]).getIm() + 0.5) / 1000;

            double temp4 = Math.log(((Double)object[2]) + 1e-33);

            double power = temp4 / Math.log(((Complex)object[3]).distance_squared(((Complex)object[4])));

            double f = Math.log(log_convergent_bailout / temp4) / Math.log(power);

            return Math.abs(((Integer)object[0]) + (long)(((Math.atan2(temp2, temp) / (pi2) + 0.75) * pi59) + (temp * temp + temp2 * temp2) * 2.5) + f) + 100800;
        }
    }

    @Override
    public double getResult3D(Object[] object) {

        if(algorithm == 0) {
            double temp = Math.log(((Complex)object[3]).distance_squared((Complex)object[4]));
            return (Integer)object[0] + (log_convergent_bailout - temp) / (Math.log((Double)object[2]) - temp) + 100800;
        }
        else {
            double temp4 = Math.log(((Double)object[2]) + 1e-33);

            double power = temp4 / Math.log(((Complex)object[3]).distance_squared(((Complex)object[4])));

            double f = Math.log(log_convergent_bailout / temp4) / Math.log(power);

            return (Integer)object[0] + f + 100800;
        }

    }
}
