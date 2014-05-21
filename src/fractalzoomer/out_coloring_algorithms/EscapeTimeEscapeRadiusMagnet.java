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
public class EscapeTimeEscapeRadiusMagnet extends EscapeTimeEscapeRadius {
    
    public EscapeTimeEscapeRadiusMagnet(double log_bailout_squared) {

        super(log_bailout_squared);

    }

    @Override
    public double getResult(Object[] object) {
        
        double zabs = Math.log(((Complex)object[1]).norm_squared()) / log_bailout_squared - 1.0f;
        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;
        
        double temp = (Integer)object[0] + zabs + zarg;
        
        return (Boolean)object[2] ? temp + 100906  : temp + 100800;

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
