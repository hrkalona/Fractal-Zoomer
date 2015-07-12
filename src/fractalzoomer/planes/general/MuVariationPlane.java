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
public class MuVariationPlane extends Plane {
    
    public MuVariationPlane() {

        super();

    }

    @Override
    public Complex getPixel(Complex pixel) {

        return pixel.square().divide(pixel.fourth().sub(0.25));

    }
}
