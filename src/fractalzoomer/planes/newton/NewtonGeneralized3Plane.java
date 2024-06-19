

package fractalzoomer.planes.newton;

import fractalzoomer.core.*;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class NewtonGeneralized3Plane extends Plane {
    
    public NewtonGeneralized3Plane() {
        
        super();
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        
        Complex temp = pixel;
        
        for(int iterations = 0; iterations < 5; iterations++) {
            Complex fz = temp.cube().sub_mutable(temp.times2()).plus_mutable(2);
            Complex dfz = temp.square().times_mutable(3).sub_mutable(2);

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
            BigIntNumComplex fz = temp.cube().sub(temp.times2()).plus(2);
            BigIntNumComplex dfz = temp.square().times(3).sub(2);

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
            BigComplex fz = temp.cube().sub(temp.times(MyApfloat.TWO)).plus(MyApfloat.TWO);
            BigComplex dfz = temp.square().times(MyApfloat.THREE).sub(MyApfloat.TWO);

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
            DDComplex fz = temp.cube().sub(temp.times2()).plus(2);
            DDComplex dfz = temp.square().times(3).sub(2);

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
            MpfrBigNumComplex fz = temp.cube().sub_mutable(temp.times2()).plus_mutable(2);
            MpfrBigNumComplex dfz = temp.square().times_mutable(3).sub_mutable(2);

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
            MpirBigNumComplex fz = temp.cube().sub_mutable(temp.times2()).plus_mutable(2);
            MpirBigNumComplex dfz = temp.square().times_mutable(3).sub_mutable(2);

            temp = temp.sub(fz.divide_mutable(dfz));
        }

        return temp;

    }
}
