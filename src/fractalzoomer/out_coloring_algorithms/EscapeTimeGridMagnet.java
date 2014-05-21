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
public class EscapeTimeGridMagnet extends EscapeTimeGrid {
    
    public EscapeTimeGridMagnet(double log_bailout_squared) {

        super(log_bailout_squared);

    }

    @Override
    public double getResult(Object[] object) {
        
        double zabs = Math.log(((Complex)object[1]).norm_squared()) / log_bailout_squared - 1.0f;
        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;
        boolean grid = 0.05 < zabs && zabs < 0.95 && 0.05 < zarg && zarg < 0.95;
        
        double temp = grid ? (Integer)object[0] : (Integer)object[0] + 50;
        
        return (Boolean)object[2] ? temp + 100906 : temp + 100800;

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
