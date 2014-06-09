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
public class SmoothEscapeTimeGridMagnet extends SmoothEscapeTimeGrid {
  private double log_convergent_bailout;
    
    public SmoothEscapeTimeGridMagnet(double log_bailout_squared, double log_convergent_bailout) {

        super(log_bailout_squared);
        this.log_convergent_bailout = log_convergent_bailout;

    }

    @Override
    public double getResult(Object[] object) {
        
        double temp3 = Math.log(((Complex)object[1]).norm_squared());
        
        double zabs = temp3 / log_bailout_squared - 1.0f;
        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;
         
        double k = Math.pow(0.5, 0.5 - zabs);
        
        double grid_weight = 0.05;
        
        boolean grid = grid_weight < zabs && zabs < (1.0 - grid_weight) && (grid_weight * k) < zarg && zarg < (1.0 - grid_weight * k);
         
        double temp2 = grid ? (Integer)object[0] : (Integer)object[0] + 50;
        
        if((Boolean)object[2]) {
            double temp = ((Complex)object[4]).norm_squared();
            temp += 0.000000001;
            temp = Math.log(temp);
            return temp2 + (log_bailout_squared - temp) / (temp3 - temp) + 100906;
        }
        else {
            double temp = Math.log(((Complex)object[4]).distance_squared(1));
            return temp2 + (log_convergent_bailout - temp) / (Math.log((Double)object[3]) - temp) + 100800; 
        }

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
    

