

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class BurningShip extends MandelVariation {
    
    public BurningShip() {
        
        super();

    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        return pixel.abs();
        
    }
    
}
