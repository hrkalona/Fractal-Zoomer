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
public class SmoothEscapeTimeEscapeRadiusMagnet1 extends OutColorAlgorithm {
  private double log_convergent_bailout;
  private double log_bailout_squared;
  protected double pi2;
    
    public SmoothEscapeTimeEscapeRadiusMagnet1(double log_bailout_squared, double log_convergent_bailout) {

        super();
        
        this.log_bailout_squared = log_bailout_squared;
        this.log_convergent_bailout = log_convergent_bailout;
        pi2 = Math.PI * 2;

    }

    @Override
    public double getResult(Object[] object) {
        
        double temp2 = Math.log(((Complex)object[1]).norm_squared());
        double zabs =  temp2 / log_bailout_squared - 1.0f;
        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;
        
        double temp3 = (Integer)object[0] + zabs + zarg;
        
        if((Boolean)object[2]) {
            double temp = ((Complex)object[4]).norm_squared();
            temp += 0.000000001;
            temp = Math.log(temp);
            return temp3 + (log_bailout_squared - temp) / (temp2 - temp) + 100906;
        }
        else {
            return temp3 - (log_convergent_bailout - (Double)object[3]) / ((Double)object[3] - Math.log(((Complex)object[1]).distance_squared((Complex)object[4]))) + 100800;
        }

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
