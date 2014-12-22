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
public class SmoothEscapeTimeMagnet extends SmoothEscapeTime {

    private double log_convergent_bailout;
    private int algorithm2;

    public SmoothEscapeTimeMagnet(double log_bailout_squared, double log_convergent_bailout, int algorithm, int algorithm2) {

        super(log_bailout_squared, algorithm);
        this.log_convergent_bailout = log_convergent_bailout;
        this.algorithm2 = algorithm2;

    }

    @Override
    public double getResult(Object[] object) {

        if((Boolean)object[2]) {

            if(algorithm == 0) {
                double temp = ((Complex)object[4]).norm_squared();
                double temp2 = ((Complex)object[1]).norm_squared();
                temp += 0.000000001;
                temp = Math.log(temp);
                return (Integer)object[0] + (log_bailout_squared - temp) / (Math.log(temp2) - temp) + 100906;
            }
            else {
                //double temp2 = ((Complex)object[1]).norm_squared();
                //return (Integer)object[0] + 1 - Math.log((Math.log(temp2)) / log_bailout_squared) / log_power + 100906;

                double temp = ((Complex)object[4]).norm_squared();
                double temp2 = ((Complex)object[1]).norm_squared();

                temp2 = Math.log(temp2);
                double p = temp2 / Math.log(temp);
                
                p = p <= 0 ? 1e-33 : p;

                double a = Math.log(temp2 / log_bailout_squared);
                double f = a / Math.log(p);

                return (Integer)object[0] + 1 - f + 100906;
            }
        }
        else {
            if(algorithm2 == 0) {
                double temp = Math.log(((Complex)object[4]).distance_squared(1));
                return (Integer)object[0] + (log_convergent_bailout - temp) / (Math.log((Double)object[3]) - temp) + 100800;
            }
            else {
                double temp4 = Math.log(((Double)object[3]));

                double power = temp4 / Math.log(((Complex)object[4]).distance_squared(1));

                double f = Math.log(log_convergent_bailout / temp4) / Math.log(power);

                return (Integer)object[0] + f + 100800;
            }
        }

    }
}
