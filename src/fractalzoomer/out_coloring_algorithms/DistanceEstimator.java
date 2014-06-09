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
public class DistanceEstimator  extends OutColorAlgorithm {
    
    public DistanceEstimator() {
        super();
    }

    @Override
    public double getResult(Object[] object) {

         double temp2 = (((Complex)object[1]).norm_squared());
         double temp = -4.*(Math.log(Math.log(temp2)*Math.sqrt(temp2 /(((Complex)object[2]).norm_squared())))-(Integer)object[0]*.693);
         
         return temp < 0 ? 100800 : temp + 100800;

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
