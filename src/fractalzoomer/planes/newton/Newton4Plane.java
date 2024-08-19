

package fractalzoomer.planes.newton;

import fractalzoomer.core.*;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class Newton4Plane extends Plane {
    
    public Newton4Plane() {
        
        super();
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        
        Complex temp = pixel;
        
        for(int iterations = 0; iterations < 5; iterations++) {
            Complex fz = temp.fourth().sub_mutable(1);
            Complex dfz = temp.cube().times4_mutable();
            
            temp = temp.sub(fz.divide_mutable(dfz));
        }

        return temp;
 
    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        BigIntNumComplex temp = pixel;

        for(int iterations = 0; iterations < 5; iterations++) {
            BigIntNumComplex fz = temp.fourth().sub(1);
            BigIntNumComplex dfz = temp.cube().times4();

            temp = temp.sub(fz.divide(dfz));
        }

        return temp;

    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        BigComplex temp = pixel;

        for(int iterations = 0; iterations < 5; iterations++) {
            BigComplex fz = temp.fourth().sub(MyApfloat.ONE);
            BigComplex dfz = temp.cube().times(MyApfloat.FOUR);

            temp = temp.sub(fz.divide(dfz));
        }

        return temp;

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        DDComplex temp = pixel;

        for(int iterations = 0; iterations < 5; iterations++) {
            DDComplex fz = temp.fourth().sub(1);
            DDComplex dfz = temp.cube().times4();

            temp = temp.sub(fz.divide(dfz));
        }

        return temp;

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        MpfrBigNumComplex temp = pixel;

        for(int iterations = 0; iterations < 5; iterations++) {
            MpfrBigNumComplex fz = temp.fourth().sub_mutable(1);
            MpfrBigNumComplex dfz = temp.cube().times4_mutable();

            temp = temp.sub(fz.divide_mutable(dfz));
        }

        return temp;

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        MpirBigNumComplex temp = pixel;

        for(int iterations = 0; iterations < 5; iterations++) {
            MpirBigNumComplex fz = temp.fourth().sub_mutable(1);
            MpirBigNumComplex dfz = temp.cube().times4_mutable();

            temp = temp.sub(fz.divide_mutable(dfz));
        }

        return temp;

    }
}
