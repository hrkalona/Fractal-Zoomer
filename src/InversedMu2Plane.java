/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class InversedMu2Plane extends Plane {

    public InversedMu2Plane() {

        super();

    }

    @Override
    public Complex getPixel(Complex pixel) {

        return pixel.divide(1, 0).plus(0.25);

    }
}
