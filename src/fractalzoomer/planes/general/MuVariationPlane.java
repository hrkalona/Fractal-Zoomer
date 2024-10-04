

package fractalzoomer.planes.general;

import fractalzoomer.core.*;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.functions.Fractal;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class MuVariationPlane extends Plane {
    private MpirBigNum _025;
    
    public MuVariationPlane(Fractal f) {

        super();

        if (NumericLibrary.allocateMPIR(f)) {
            _025 = new MpirBigNum(0.25);
        }

    }

    @Override
    public Complex transform_internal(Complex pixel) {

        return pixel.square().divide_mutable(pixel.fourth().sub_mutable(0.25));

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        return pixel.square().divide(pixel.fourth().sub(0.25));

    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        return pixel.square().divide(pixel.fourth().sub(new MyApfloat(0.25)));

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel.square().divide_mutable(pixel.fourth().sub_mutable(0.25));

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        return pixel.square().divide_mutable(pixel.fourth().sub_mutable(_025));

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel.square().divide(pixel.fourth().sub(new DoubleDouble(0.25)));

    }

}
