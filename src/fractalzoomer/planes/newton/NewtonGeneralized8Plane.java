

package fractalzoomer.planes.newton;

import fractalzoomer.core.*;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class NewtonGeneralized8Plane extends Plane {
    
    public NewtonGeneralized8Plane() {
        
        super();
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        
        Complex temp = pixel;
        
        for(int iterations = 0; iterations < 5; iterations++) {
            Complex fz = temp.eighth().plus_mutable(temp.fourth().times_mutable(15)).sub_mutable(16);
            Complex dfz = temp.seventh().times_mutable(8).plus_mutable(temp.cube().times_mutable(60));

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
            MpfrBigNumComplex fz = temp.pow(8).plus_mutable(temp.fourth().times_mutable(15)).sub_mutable(16);
            MpfrBigNumComplex dfz = temp.pow(7).times_mutable(8).plus_mutable(temp.cube().times_mutable(60));

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
            DDComplex fz = temp.pow(8).plus(temp.fourth().times(15)).sub(16);
            DDComplex dfz = temp.pow(7).times(8).plus(temp.cube().times(60));

            temp = temp.sub(fz.divide(dfz));
        }

        return temp;

    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        return new BigComplex(transform_internal(pixel.toComplex()));

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        return new MpirBigNumComplex(transform_internal(pixel.toComplex()));

    }
}
