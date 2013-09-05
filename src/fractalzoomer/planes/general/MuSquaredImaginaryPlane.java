/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.planes.general;

import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;

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
