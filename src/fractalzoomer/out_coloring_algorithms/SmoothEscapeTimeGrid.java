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
public class SmoothEscapeTimeGrid extends OutColorAlgorithm {
  protected double log_bailout_squared;
  protected double pi2;
    
    public SmoothEscapeTimeGrid(double log_bailout_squared) {

        super();
        this.log_bailout_squared = log_bailout_squared;
        pi2 = Math.PI * 2;

    }

    @Override
    public double getResult(Object[] object) {
        
        double temp2 = Math.log(((Complex)object[1]).norm_squared());
        
        double zabs = temp2 / log_bailout_squared - 1.0f;
        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;
         
        double k = Math.pow(0.5, 0.5 - zabs);
        
        double grid_weight = 0.05;
        
        boolean grid = grid_weight < zabs && zabs < (1.0 - grid_weight) && (grid_weight * k) < zarg && zarg < (1.0 - grid_weight * k);
         
        double temp = ((Complex)object[2]).norm_squared();
        
        temp += 0.000000001;
        temp = Math.log(temp);
  
        double temp3 = (Integer)object[0] + (log_bailout_squared - temp) / (temp2 - temp);
        
        return grid ? temp3 + 100800 : temp3 + 100850;

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
