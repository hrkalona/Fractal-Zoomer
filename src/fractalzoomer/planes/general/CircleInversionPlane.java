

package fractalzoomer.planes.general;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.functions.Fractal;
import fractalzoomer.planes.Plane;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public class CircleInversionPlane extends Plane {

    private Complex center;
    private BigComplex ddcenter;
    private double plane_transform_radius;
    private Apfloat ddplane_transform_radius;

    private MpfrBigNumComplex mpfrbncenter;
    private MpfrBigNum mpfrbnplane_transform_radius;

    private DDComplex ddccenter;
    private DoubleDouble ddcplane_transform_radius;

    private BigIntNumComplex bnicenter;
    private BigIntNum bniplane_transform_radius;

    private MpirBigNumComplex mpirbncenter;
    private MpirBigNum mpirbnplane_transform_radius;

    public CircleInversionPlane(double[] plane_transform_center, double plane_transform_radius, Fractal f) {

        super();
        center = new Complex(plane_transform_center[0], plane_transform_center[1]);
        this.plane_transform_radius = plane_transform_radius * plane_transform_radius;

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            ddplane_transform_radius = new MyApfloat(this.plane_transform_radius);
            ddcenter = new BigComplex(center);

            bnicenter = new BigIntNumComplex(center);
            bniplane_transform_radius = new BigIntNum(this.plane_transform_radius);

            ddccenter = new DDComplex(center);
            ddcplane_transform_radius = new DoubleDouble(this.plane_transform_radius);

            if (NumericLibrary.allocateMPFR(f)) {
                mpfrbncenter = new MpfrBigNumComplex(center);
                mpfrbnplane_transform_radius = new MpfrBigNum(this.plane_transform_radius);
            } else if (NumericLibrary.allocateMPIR(f)) {
                mpirbncenter = new MpirBigNumComplex(center);
                mpirbnplane_transform_radius = new MpirBigNum(this.plane_transform_radius);
            }
        }

    }

    @Override
    public Complex transform_internal(Complex pixel) {

        if(pixel.compare(center) == 0) {
            return pixel;
        }
        
        return pixel.circle_inversion(center, plane_transform_radius);
        
    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        if(pixel.compare(ddcenter) == 0) {
            return pixel;
        }

        return pixel.circle_inversion(ddcenter, ddplane_transform_radius);

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        if(pixel.compare(bnicenter) == 0) {
            return pixel;
        }

        return pixel.circle_inversion(bnicenter, bniplane_transform_radius);

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        if(pixel.compare(mpfrbncenter) == 0) {
            return pixel;
        }

        return pixel.circle_inversion(mpfrbncenter, mpfrbnplane_transform_radius);

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        if(pixel.compare(mpirbncenter) == 0) {
            return pixel;
        }

        return pixel.circle_inversion(mpirbncenter, mpirbnplane_transform_radius);

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        if(pixel.compare(ddccenter) == 0) {
            return pixel;
        }

        return pixel.circle_inversion(ddccenter, ddcplane_transform_radius);

    }

}
