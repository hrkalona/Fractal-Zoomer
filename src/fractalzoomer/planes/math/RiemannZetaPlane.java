

package fractalzoomer.planes.math;

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
public class RiemannZetaPlane extends Plane {
    
    public RiemannZetaPlane() {
        
        super();
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.riemann_zeta();

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {
        if(pixel.isZero()) {
            return pixel;
        }

        return new MpfrBigNumComplex(transform_internal(pixel.toComplex()));

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
        if(pixel.isZero()) {
            return pixel;
        }

        return new DDComplex(transform_internal(pixel.toComplex()));

    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {
        if(pixel.isZero()) {
            return pixel;
        }

        return new BigComplex(transform_internal(pixel.toComplex()));

    }
    
}
