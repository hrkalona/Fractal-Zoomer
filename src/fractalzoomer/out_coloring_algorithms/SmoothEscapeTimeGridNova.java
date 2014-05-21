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
public class SmoothEscapeTimeGridNova extends OutColorAlgorithm {
  private double log_convergent_bailout;
  protected double pi2;
  protected double log_bailout_squared;
    
    public SmoothEscapeTimeGridNova(double log_convergent_bailout) {

        super();
        this.log_convergent_bailout = log_convergent_bailout;
        pi2 = Math.PI * 2;
        log_bailout_squared = Math.log(4.0);

    }

    @Override
    public double getResult(Object[] object) {
        
        double temp2 = Math.log(((Complex)object[1]).norm_squared());
        
        double zabs = Math.abs(temp2 / log_bailout_squared - 1.0f);
        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;
         
        double k = Math.pow(0.5, 0.5 - zabs);
        
        double grid_weight = 0.05;
        
        boolean grid = grid_weight < zabs && zabs < (1.0 - grid_weight) && (grid_weight * k) < zarg && zarg < (1.0 - grid_weight * k);
         
        
        
        double temp = Math.log(((Complex)object[3]).distance_squared((Complex)object[4]));
        double temp3 =  (Integer)object[0] + (log_convergent_bailout - temp) / (Math.log((Double)object[2]) - temp);
        
        return grid ? temp3 + 100800 : temp3 + 100850;

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
