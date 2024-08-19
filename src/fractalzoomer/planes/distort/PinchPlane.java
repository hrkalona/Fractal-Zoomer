

package fractalzoomer.planes.distort;


import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class PinchPlane extends Plane {

    private double plane_transform_angle;
    private double plane_transform_radius;
    private double plane_transform_amount;
    private Complex center;

    public PinchPlane(double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double plane_transform_amount) {

        super();
        center = new Complex(plane_transform_center[0], plane_transform_center[1]);
        this.plane_transform_angle = plane_transform_angle;
        this.plane_transform_radius = plane_transform_radius;
        this.plane_transform_amount = plane_transform_amount;
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {
        
        return pixel.pinch(center, plane_transform_radius, plane_transform_amount, plane_transform_angle);
        
    }
}
