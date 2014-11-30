/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.planes.fold;

import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class FoldUpPlane extends Plane {

    private Complex center;

    public FoldUpPlane(double[] plane_transform_center) {

        super();
        center = new Complex(plane_transform_center[0], plane_transform_center[1]);

    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        return pixel.fold_up(center);
   
    }
}
