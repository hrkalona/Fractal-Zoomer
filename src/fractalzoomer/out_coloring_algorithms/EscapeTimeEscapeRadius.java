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
public class EscapeTimeEscapeRadius extends OutColorAlgorithm {
  protected double log_bailout_squared;
  protected double pi2;
    
    public EscapeTimeEscapeRadius(double log_bailout_squared) {

        super();
        this.log_bailout_squared = log_bailout_squared;
        pi2 = Math.PI * 2;

    }

    @Override
    public double getResult(Object[] object) {
        
        double zabs = Math.log(((Complex)object[1]).norm_squared()) / log_bailout_squared - 1.0f;
        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;
        
        return (Integer)object[0] + 100800 + zabs + zarg;

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
