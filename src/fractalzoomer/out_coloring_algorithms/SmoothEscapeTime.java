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
public class SmoothEscapeTime extends OutColorAlgorithm {

    protected double log_bailout_squared;
    protected int algorithm;

    public SmoothEscapeTime(double log_bailout_squared, int algorithm) {

        super();
        this.log_bailout_squared = log_bailout_squared;
        this.algorithm = algorithm;

    }

    @Override
    public double getResult(Object[] object) {
        
        if(algorithm == 0) {
            double temp = ((Complex)object[2]).norm_squared();
            double temp2 = ((Complex)object[1]).norm_squared();

            temp += 0.000000001;
            temp = Math.log(temp);

            return (Integer)object[0] + (log_bailout_squared - temp) / (Math.log(temp2) - temp) + 100800;         
            
        }
        else {
            //double temp2 = ((Complex)object[1]).norm_squared();
            //return (Integer)object[0] + 1 - Math.log(Math.log(temp2) / log_bailout_squared) / log_power + 100800;
            
            
            double temp = ((Complex)object[2]).norm_squared();
            double temp2 = ((Complex)object[1]).norm_squared();
            
            temp2 = Math.log(temp2);
            double p = temp2 / Math.log(temp);
           
            p = p <= 0 ? 1e-33 : p;
            
            double a = Math.log(temp2 / log_bailout_squared);
            double f =  a / Math.log(p);
            
            return (Integer)object[0] + 1 - f + 100800;
        }

    }

    @Override
    public double getResult3D(Object[] object) {

        return getResult(object);

    }
}
