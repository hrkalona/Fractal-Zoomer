/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class Perturbation extends InitialValue {
    
    public Perturbation(double re, double im) {
    
        super(re, im);
        
    }


    @Override
    public Complex getPixel(Complex pixel) {
        
        return this.pixel;
        
    }
    
}
