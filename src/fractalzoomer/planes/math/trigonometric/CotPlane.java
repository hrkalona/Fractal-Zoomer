

package fractalzoomer.planes.math.trigonometric;

import fractalzoomer.core.Complex;
import fractalzoomer.core.numerics.BigComplex;
import fractalzoomer.core.numerics.DDComplex;
import fractalzoomer.core.numerics.MpfrBigNumComplex;
import fractalzoomer.core.numerics.MpirBigNumComplex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class CotPlane extends Plane {
    
    public CotPlane() {
        
        super();
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        return pixel.cot();

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        return pixel.cot();

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        return pixel.cot();

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
}
