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
public class SmoothEscapeTimeRootFindingMethod extends OutColorAlgorithm {

    private double log_convergent_bailout;
    private int algorithm;

    public SmoothEscapeTimeRootFindingMethod(double log_convergent_bailout, int algorithm) {

        super();
        this.log_convergent_bailout = log_convergent_bailout;
        this.algorithm = algorithm;
    }

    @Override
    public double getResult(Object[] object) {


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

    @Override
    public double getResult3D(Object[] object) {

        return getResult(object);

    }
}
