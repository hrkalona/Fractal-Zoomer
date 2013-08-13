/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.planes;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class MuSquaredImaginaryPlane extends Plane {
    
    public MuSquaredImaginaryPlane() {

        super();

    }

    @Override
    public Complex getPixel(Complex pixel) {

        return pixel.pow(new Complex(0, 2));

    }
    
}
