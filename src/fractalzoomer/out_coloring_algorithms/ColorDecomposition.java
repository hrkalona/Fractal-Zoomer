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
public class ColorDecomposition extends OutColorAlgorithm {
  protected double pi2;
  protected double pi59;
    
    public ColorDecomposition() {
        super();
        
        pi2 = 2 * Math.PI;
        pi59 = 59 * Math.PI;
        
    }

    @Override
    public double getResult(Object[] object) {

        return Math.abs((((Complex)object[1]).arg() / (pi2)  + 0.75) * pi59) + 100800;
        
    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
