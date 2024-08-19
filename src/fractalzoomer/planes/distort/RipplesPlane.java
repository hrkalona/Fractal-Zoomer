
package fractalzoomer.planes.distort;

import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class RipplesPlane extends Plane {

    private Complex scales;
    private Complex wavelength;
    private int waveType;

    public RipplesPlane(double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType) {

        super();
        wavelength = new Complex(plane_transform_wavelength[0], plane_transform_wavelength[1]);
        scales =  new Complex(plane_transform_scales[0], plane_transform_scales[1]);
        this.waveType = waveType;

    }

    @Override
    public Complex transform_internal(Complex pixel) {

        return pixel.ripples(wavelength, scales, waveType);

    }
}
