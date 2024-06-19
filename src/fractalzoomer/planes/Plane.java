

package fractalzoomer.planes;

import fractalzoomer.core.*;

/**
 *
 * @author hrkalona
 */
public abstract class Plane {
    public static boolean FLIP_IMAGINARY = false;
    public static boolean FLIP_REAL = false;

    protected Plane() {

    }

    public Complex transform(Complex pixel) {
        Complex v = transform_internal(pixel);
        v = FLIP_IMAGINARY ? v.conjugate() : v;
        return FLIP_REAL ? v.negate_re() : v;
    }

    public BigComplex transform(BigComplex pixel) {
        BigComplex v = transform_internal(pixel);
        v = FLIP_IMAGINARY ? v.conjugate() : v;
        return FLIP_REAL ? v.negate_re() : v;
    }

    public BigIntNumComplex transform(BigIntNumComplex pixel) {
        BigIntNumComplex v = transform_internal(pixel);
        v = FLIP_IMAGINARY ? v.conjugate() : v;
        return FLIP_REAL ? v.negate_re() : v;
    }

    public BigNumComplex transform(BigNumComplex pixel) {
        BigNumComplex v = transform_internal(pixel);
        v = FLIP_IMAGINARY ? v.conjugate() : v;
        return FLIP_REAL ? v.negate_re() : v;
    }

    public MpfrBigNumComplex transform(MpfrBigNumComplex pixel) {
        MpfrBigNumComplex v = transform_internal(pixel);
        v = FLIP_IMAGINARY ? v.conjugate_mutable() : v;
        return FLIP_REAL ? v.negate_re_mutable() : v;
    }

    public MpirBigNumComplex transform(MpirBigNumComplex pixel) {
        MpirBigNumComplex v = transform_internal(pixel);
        v = FLIP_IMAGINARY ? v.conjugate_mutable() : v;
        return FLIP_REAL ? v.negate_re_mutable() : v;
    }

    public DDComplex transform(DDComplex pixel) {
        DDComplex v = transform_internal(pixel);
        v = FLIP_IMAGINARY ? v.conjugate() : v;
        return FLIP_REAL ? v.negate_re() : v;
    }

    protected abstract Complex transform_internal(Complex pixel);

    //Todo fix the low precision ones
    protected BigComplex transform_internal(BigComplex pixel) {

        return new BigComplex(transform_internal(pixel.toComplex()));

    }

    //Todo fix the low precision ones
    protected BigNumComplex transform_internal(BigNumComplex pixel) {

        return new BigNumComplex(transform_internal(pixel.toComplex()));

    }

    protected BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        return new BigIntNumComplex(transform_internal(pixel.toComplex()));

    }

    //Todo fix, erf, gamma, factorial, rzeta, distort
    protected MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return new MpfrBigNumComplex(transform_internal(pixel.toComplex()));

    }

    //Todo fix the low precision ones
    protected DDComplex transform_internal(DDComplex pixel) {

        return new DDComplex(transform_internal(pixel.toComplex()));

    }

    protected MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        return new MpirBigNumComplex(transform_internal(pixel.toComplex()));

    }

    public GenericComplex Transform(GenericComplex pixel) {

        if(pixel instanceof MpfrBigNumComplex) {
            return transform((MpfrBigNumComplex) pixel);
        }
        else if(pixel instanceof MpirBigNumComplex) {
            return transform((MpirBigNumComplex) pixel);
        }
        else if(pixel instanceof BigNumComplex) {
            return transform((BigNumComplex) pixel);
        }
        else if(pixel instanceof BigIntNumComplex) {
            return transform((BigIntNumComplex) pixel);
        }
        else if(pixel instanceof BigComplex) {
            return transform((BigComplex) pixel);
        }
        else if(pixel instanceof DDComplex) {
            return transform((DDComplex) pixel);
        }
        else {
            return transform((Complex) pixel);
        }

    }

}
