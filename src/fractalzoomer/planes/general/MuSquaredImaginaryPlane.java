

package fractalzoomer.planes.general;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class MuSquaredImaginaryPlane extends Plane {
    private Complex exponent;
    private MpfrBigNumComplex mpfrbnexponent;

    private DDComplex ddcexponent;

    public MuSquaredImaginaryPlane() {

        super();
        exponent = new Complex(0, 2);

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {

            ddcexponent = new DDComplex(0, 2);

            if(!LibMpfr.mpfrHasError()) {
                mpfrbnexponent = new MpfrBigNumComplex(0, 2);
            }

        }

    }

    @Override
    public Complex transform_internal(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.pow(exponent);

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {
        if(pixel.isZero()) {
            return pixel;
        }

        return pixel.pow(mpfrbnexponent);

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {
        if(pixel.isZero()) {
            return pixel;
        }

        return new MpirBigNumComplex(transform_internal(pixel.toComplex()));

    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {
        if(pixel.isZero()) {
            return pixel;
        }

        return new BigComplex(transform_internal(pixel.toComplex()));

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {
        if(pixel.isZero()) {
            return pixel;
        }

        return pixel.pow(ddcexponent);

    }
    
}
