package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class MaximumIterations extends InColorAlgorithm {
    private int max_iterations;
    
    public MaximumIterations(int max_iterations) { 
        super();
        this.max_iterations = max_iterations;
    }
    
    @Override
    public double getResult(Object[] object) {
  
        return max_iterations;
 
    }
    
}
