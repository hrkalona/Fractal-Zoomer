/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class InversedPlane extends Plane {

    public InversedPlane() {

        super();

    }

    @Override
    public Complex getPixel(Complex pixel) {

        return pixel.divideNormalInv(1);

    }

}
