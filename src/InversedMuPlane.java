



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class InversedMuPlane extends Plane {

    public InversedMuPlane() {

        super();

    }

    @Override
    public Complex getPixel(Complex pixel) {

        return pixel.divide(1, pixel);

    }

}
