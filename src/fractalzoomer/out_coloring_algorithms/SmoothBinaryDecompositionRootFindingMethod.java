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
public class SmoothBinaryDecompositionRootFindingMethod extends OutColorAlgorithm {

    protected double log_convergent_bailout;
    protected int algorithm;

    public SmoothBinaryDecompositionRootFindingMethod(double log_convergent_bailout, int algorithm) {

        super();
        this.log_convergent_bailout = log_convergent_bailout;
        this.algorithm = algorithm;

    }

    @Override
    public double getResult(Object[] object) {

        if(algorithm == 0) {
            double temp = Math.log(((Complex)object[3]).distance_squared((Complex)object[4]));
            double temp3 = (Integer)object[0] + (log_convergent_bailout - temp) / (Math.log((Double)object[2]) - temp);

            return ((Complex)object[1]).getIm() < 0 ? temp3 + 100850 : temp3 + 100800;
        }
        else {
            double temp4 = Math.log(((Double)object[2]) + 1e-33);

            double power = temp4 / Math.log(((Complex)object[3]).distance_squared(((Complex)object[4])));
            
            power = power <= 0 ? 1e-33 : power;

            double temp3 = (Integer)object[0] + Math.log(log_convergent_bailout / temp4) / Math.log(power);

            return ((Complex)object[1]).getIm() < 0 ? temp3 + 100850 : temp3 + 100800;
        }

    }

    @Override
    public double getResult3D(Object[] object) {

        return getResult(object);

    }
}
