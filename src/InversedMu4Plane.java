/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class InversedMu4Plane extends Plane {

    public InversedMu4Plane() {

        super();

    }

    @Override
    public Complex getPixel(Complex pixel) {

        return pixel.divide(1, 0).sub(2);

    }
}
