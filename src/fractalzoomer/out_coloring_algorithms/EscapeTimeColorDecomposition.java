package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeColorDecomposition extends OutColorAlgorithm {
  protected double pi2;
  protected double pi59;
    
    public EscapeTimeColorDecomposition() {
        super();
        
        pi2 = 2 * Math.PI;
        pi59 = Math.PI * 59;
        
    }

    @Override
    public double getResult(Object[] object) {

        return Math.abs(((Integer)object[0]) + (((Complex)object[1]).arg() / pi2  + 0.75) * pi59) + 100800;

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
