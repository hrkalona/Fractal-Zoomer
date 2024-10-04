

package fractalzoomer.planes.general;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class InversedBipolarPlane extends Plane {
    private Complex focal_point;
    private MpfrBigNumComplex mpfrbnfocal_point;

    private DDComplex ddfocal_point;

    public InversedBipolarPlane(double[] focal_point) {
        
        super();
        this.focal_point = new Complex(focal_point[0], focal_point[1]);

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            ddfocal_point = new DDComplex(focal_point[0], focal_point[1]);

            if(!LibMpfr.mpfrHasError()) {
                mpfrbnfocal_point = new MpfrBigNumComplex(focal_point[0], focal_point[1]);
            }
        }
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {

        if(pixel.isZero() || focal_point.isZero()) {
            return pixel;
        }
        
        return pixel.fromBiPolar(focal_point);
        
    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        return new BigComplex(transform_internal(pixel.toComplex()));

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        if(pixel.isZero() || mpfrbnfocal_point.isZero()) {
            return pixel;
        }

        return pixel.fromBiPolar(mpfrbnfocal_point);

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        return new MpirBigNumComplex(transform_internal(pixel.toComplex()));

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        if(pixel.isZero() || ddfocal_point.isZero()) {
            return pixel;
        }

        return pixel.fromBiPolar(ddfocal_point);

    }
}
