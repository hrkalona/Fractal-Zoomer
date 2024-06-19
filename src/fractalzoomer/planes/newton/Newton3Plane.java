

package fractalzoomer.planes.newton;

import fractalzoomer.core.*;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class Newton3Plane extends Plane {
    
    public Newton3Plane() {
        
        super();
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        
        Complex temp = pixel;
        
        for(int iterations = 0; iterations < 5; iterations++) {
            Complex fz = temp.cube().sub_mutable(1);
            Complex dfz = temp.square().times_mutable(3);
            
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
            BigIntNumComplex fz = temp.cube().sub(1);
            BigIntNumComplex dfz = temp.square().times(3);

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
            BigComplex fz = temp.cube().sub(MyApfloat.ONE);
            BigComplex dfz = temp.square().times(MyApfloat.THREE);

            temp = temp.sub(fz.divide(dfz));
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
            MpirBigNumComplex fz = temp.cube().sub_mutable(1);
            MpirBigNumComplex dfz = temp.square().times_mutable(3);

            temp = temp.sub(fz.divide_mutable(dfz));
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
            MpfrBigNumComplex fz = temp.cube().sub_mutable(1);
            MpfrBigNumComplex dfz = temp.square().times_mutable(3);

            temp = temp.sub(fz.divide_mutable(dfz));
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
            DDComplex fz = temp.cube().sub(1);
            DDComplex dfz = temp.square().times(3);

            temp = temp.sub(fz.divide(dfz));
        }

        return temp;

    }
}
/*//Complex fz = pixel.cube().sub(1);
        //Complex dfz = pixel.square().times(3);
        
        //Complex fz = pixel.fourth().sub(1);
        //Complex dfz = pixel.cube().times4();
        
        //Complex[] temp = pixel.der01_cos();
        
        //Complex fz = temp[0];
        //Complex dfz = temp[1];
        
        Complex fz = pixel.cube().sub_mutable(pixel.times2()).plus_mutable(2);
        Complex dfz = pixel.square().times_mutable(3).sub_mutable(2);*/
        
        