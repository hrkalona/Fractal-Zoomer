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
public class DistanceEstimator2 extends OutColorAlgorithm {
    protected int max_iterations;
    protected double limit;
    
    public DistanceEstimator2(double size, int max_iterations, double bailout) {
        super();
        this.limit = (size / 33000) * bailout;
        this.max_iterations = max_iterations;
    }

    @Override
    public double getResult(Object[] object) {

        double temp2 = (((Complex)object[1]).norm());
        
        return (temp2 * Math.log(temp2) / ((Complex)object[2]).norm()) > limit ? (Integer)object[0] + 100800 : max_iterations;

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return getResult(object);
        
    }
    
}
