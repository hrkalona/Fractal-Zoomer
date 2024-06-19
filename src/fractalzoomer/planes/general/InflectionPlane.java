
package fractalzoomer.planes.general;

import fractalzoomer.core.*;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class InflectionPlane extends Plane {
    private Complex center;
    private BigComplex ddcenter;
    private BigNumComplex bncenter;

    private BigIntNumComplex bnicenter;

    private DDComplex ddccenter;

    private MpfrBigNumComplex mpfrbncenter;
    private MpirBigNumComplex mpirbncenter;
    
    public InflectionPlane(double[] plane_transform_center) {
        
        super();
        center = new Complex(plane_transform_center[0], plane_transform_center[1]);
        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            ddcenter = new BigComplex(center);
            bncenter = new BigNumComplex(center);
            bnicenter = new BigIntNumComplex(center);
            ddccenter = new DDComplex(center);

            if (TaskRender.allocateMPFR()) {
                mpfrbncenter = new MpfrBigNumComplex(center);
            } else if (TaskRender.allocateMPIR()) {
                mpirbncenter = new MpirBigNumComplex(center);
            }
        }
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {
      
        return  pixel.inflection(center);
        
    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        return  pixel.inflection(ddcenter);

    }

    @Override
    public BigNumComplex transform_internal(BigNumComplex pixel) {

        return  pixel.inflection(bncenter);

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        return  pixel.inflection(bnicenter);

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel.inflection(mpfrbncenter);

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        return pixel.inflection(mpirbncenter);

    }


    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel.inflection(ddccenter);

    }

}
