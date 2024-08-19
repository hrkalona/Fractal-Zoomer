

package fractalzoomer.planes.distort;

import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class TwirlPlane extends Plane {

    private double plane_transform_angle;
    private double plane_transform_radius;
    private Complex center;

    public TwirlPlane(double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius) {
        
        super();
        center = new Complex(plane_transform_center[0], plane_transform_center[1]);
        this.plane_transform_angle = plane_transform_angle;
        this.plane_transform_radius = plane_transform_radius;

    }

    @Override
    public Complex transform_internal(Complex pixel) {

        return pixel.twirl(center, plane_transform_angle, plane_transform_radius);
        
    }
}
