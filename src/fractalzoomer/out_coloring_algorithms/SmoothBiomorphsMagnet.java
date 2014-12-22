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
public class SmoothBiomorphsMagnet extends SmoothBiomorphs {

    protected double log_convergent_bailout;
    protected int algorithm2;

    public SmoothBiomorphsMagnet(double log_bailout_squared, double log_convergent_bailout, double bailout, int algorithm, int algorithm2) {

        super(log_bailout_squared, bailout, algorithm);
        this.log_convergent_bailout = log_convergent_bailout;
        this.algorithm2 = algorithm2;

    }

    @Override
    public double getResult(Object[] object) {

        double temp3;
        if((Boolean)object[2]) {
            if(algorithm == 0) {
                double temp = ((Complex)object[4]).norm_squared();
                double temp2 = ((Complex)object[1]).norm_squared();
                temp += 0.000000001;
                temp = Math.log(temp);
                temp3 = (Integer)object[0] + (log_bailout_squared - temp) / (Math.log(temp2) - temp);
            }
            else {
                double temp = ((Complex)object[4]).norm_squared();
                double temp2 = ((Complex)object[1]).norm_squared();

                temp2 = Math.log(temp2);
                double p = temp2 / Math.log(temp);
                
                p = p <= 0 ? 1e-33 : p;

                double a = Math.log(temp2 / log_bailout_squared);
                double f = a / Math.log(p);

                temp3 = (Integer)object[0] + 1 - f;
            }
        }
        else {
            if(algorithm2 == 0) {
                double temp = Math.log(((Complex)object[4]).distance_squared(1));
                temp3 = (Integer)object[0] + (log_convergent_bailout - temp) / (Math.log((Double)object[3]) - temp);
            }
            else {
                double temp4 = Math.log(((Double)object[3]));

                double power = temp4 / Math.log(((Complex)object[4]).distance_squared(1));

                double f = Math.log(log_convergent_bailout / temp4) / Math.log(power);

                temp3 = (Integer)object[0] + f;
            }
        }

        double temp = ((Complex)object[1]).getRe();
        double temp2 = ((Complex)object[1]).getIm();

        return (Boolean)object[2] ? (temp > -bailout && temp < bailout || temp2 > -bailout && temp2 < bailout ? temp3 + 100906 : temp3 + 100850) : (temp > -bailout && temp < bailout || temp2 > -bailout && temp2 < bailout ? temp3 + 100800 : temp3 + 100850);

    }
}
