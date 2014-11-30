/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.planes.distort;

import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class ShearPlane extends Plane {

    private double[] plane_transform_scales;

    public ShearPlane(double[] plane_transform_scales) {

        super();
        this.plane_transform_scales = plane_transform_scales;

    }

    @Override
    public Complex getPixel(Complex pixel) {

        return pixel.shear(plane_transform_scales[0], plane_transform_scales[1]);

    }
}
