package fractalzoomer.core.location.delta;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.app_settings.JitterSettings;
import fractalzoomer.planes.Plane;
import org.apfloat.Apfloat;

public class CartesianLocationDeltaDeep extends Location {
    private MantExp mheight_ratio;
    private double height_ratio;
    private MantExp size;
    private MantExp temp_size_image_size_x;
    private MantExp temp_size_image_size_y;
    private MantExp temp_x_corner;
    private MantExp temp_y_corner;
    private JitterSettings js;

    private MantExp tempY;
    private MantExp tempX;

    private Apfloat ddxcenter;
    private Apfloat ddycenter;

    private double image_size_2x;
    private double image_size_2y;

    private int bignumLib;

    private MantExp[] antialiasing_x;

    private MantExp[] antialiasing_y;

    private int width;
    private int height;
    private Rotation rotation;
    private boolean hasRotation;
    private MantExp rotationRe;
    private MantExp rotationIm;

    public CartesianLocationDeltaDeep(Apfloat xCenter, Apfloat yCenter, Apfloat ddsize, double height_ratio, int width, int height, Apfloat[] rotation_center, Apfloat[] rotation_vals, JitterSettings js, Fractal fractal, int bignumLib) {

        super();

        this.fractal = fractal;
        this.mheight_ratio = new MantExp(height_ratio);
        ddxcenter = xCenter;
        ddycenter = yCenter;
        //Apfloat ddheight_ratio = new MyApfloat(height_ratio);
        this.bignumLib = bignumLib;

        this.js = js;
        //this.ddsize = ddsize;
        size = new MantExp(ddsize);

        width = offset.getWidth(width);
        height = offset.getHeight(height);
        int image_size = Math.min(width, height);
        double coefx = width == image_size ? 0.5 : (1 + (width - (double)height) / height) * 0.5;
        double coefy = height == image_size ? 0.5 : (1 + (height - (double)width) / width) * 0.5;

        this.width = width;
        this.height = height;

        image_size_2x = width * 0.5;
        image_size_2y = height * 0.5;

        MantExp size_2_x = size.multiply(coefx);
        MantExp temp = size.multiply(mheight_ratio);
        MantExp size_2_y = temp.multiply(coefy);
        size_2_x.Normalize();
        size_2_y.Normalize();

        temp_size_image_size_x = size.divide(image_size);
        temp_size_image_size_y = temp.divide(image_size);
        temp_size_image_size_x.Normalize();
        temp_size_image_size_y.Normalize();

        temp_x_corner = size_2_x.negate();
        temp_y_corner = size_2_y;
        createRotation(rotation_center, rotation_vals);

    }

    public CartesianLocationDeltaDeep(CartesianLocationDeltaDeep other) {
        super(other);
        fractal = other.fractal;
        reference = other.reference;
        temp_x_corner = other.temp_x_corner;
        temp_y_corner = other.temp_y_corner;
        temp_size_image_size_x = other.temp_size_image_size_x;
        temp_size_image_size_y = other.temp_size_image_size_y;
        js = other.js;
        ddxcenter = other.ddxcenter;
        ddycenter = other.ddycenter;
        bignumLib = other.bignumLib;
        //ddsize = other.ddsize;
        antialiasing_x = other.antialiasing_x;
        antialiasing_y = other.antialiasing_y;
        size = other.size;
        height_ratio = other.height_ratio;
        image_size_2x = other.image_size_2x;
        image_size_2y = other.image_size_2y;
        width = other.width;
        height = other.height;
        rotation = other.rotation;
        hasRotation = other.hasRotation;
        rotationRe = other.rotationRe;
        rotationIm = other.rotationIm;
    }

    private MantExpComplex getComplexBase(int x, int y) {
        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);

//            tempX = temp_x_corner.add(temp_size_image_size_x.multiply(x + res[1]));
//            tempX.Normalize();
//            tempY = temp_y_corner.subtract(temp_size_image_size_y.multiply(y + res[0]));
//            tempY.Normalize();

            tempX = temp_size_image_size_x.multiply((x + res[1]) - image_size_2x);
            tempX.Normalize();
            tempY = temp_size_image_size_y.multiply(image_size_2y - (y + res[0]));
            tempY.Normalize();
        }
        else {
//            tempX = temp_x_corner.add(temp_size_image_size_x.multiply(x));
//            tempX.Normalize();
//            tempY = temp_y_corner.subtract(temp_size_image_size_y.multiply(y));
//            tempY.Normalize();

            tempX = temp_size_image_size_x.multiply(x - image_size_2x);
            tempX.Normalize();
            tempY = temp_size_image_size_y.multiply(image_size_2y - y);
            tempY.Normalize();
        }

        indexX = x;
        indexY = y;

        MantExp finalX = new MantExp(tempX);
        MantExp finalY = new MantExp(tempY);

        if(hasRotation) {
            MantExp temp = finalX.multiply(rotationRe).subtract_mutable(finalY.multiply(rotationIm));
            finalY = finalX.multiply(rotationIm).add_mutable(finalY.multiply(rotationRe));
            finalX = temp;
            finalX.Normalize();
            finalY.Normalize();
        }
        if(Plane.FLIP_REAL) {
            finalX.negate_mutable();
        }
        if(Plane.FLIP_IMAGINARY) {
            finalY.negate_mutable();
        }

        return MantExpComplex.create(finalX, finalY);
    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexBase(offset.getX(x), offset.getY(y));
    }

    private MantExpComplex getComplexWithXBase(int x) {
        if(js.enableJitter) {
            return getComplexBase(x, indexY);
        }

//        tempX = temp_x_corner.add(temp_size_image_size_x.multiply(x));
//        tempX.Normalize();

        tempX = temp_size_image_size_x.multiply(x - image_size_2x);
        tempX.Normalize();
        indexX = x;

        MantExp finalX = new MantExp(tempX);
        MantExp finalY = new MantExp(tempY);

        if(hasRotation) {
            MantExp temp = finalX.multiply(rotationRe).subtract_mutable(finalY.multiply(rotationIm));
            finalY = finalX.multiply(rotationIm).add_mutable(finalY.multiply(rotationRe));
            finalX = temp;
            finalX.Normalize();
            finalY.Normalize();
        }
        if(Plane.FLIP_REAL) {
            finalX.negate_mutable();
        }
        if(Plane.FLIP_IMAGINARY) {
            finalY.negate_mutable();
        }

        return MantExpComplex.create(finalX, finalY);
    }

    @Override
    public GenericComplex getComplexWithX(int x) {
       return getComplexWithXBase(offset.getX(x));
    }

    private MantExpComplex getComplexWithYBase(int y) {
        if(js.enableJitter) {
            return getComplexBase(indexX, y);
        }

//        tempY = temp_y_corner.subtract(temp_size_image_size_y.multiply(y));
//        tempY.Normalize();
        tempY = temp_size_image_size_y.multiply(image_size_2y - y);
        tempY.Normalize();

        indexY = y;

        MantExp finalX = new MantExp(tempX);
        MantExp finalY = new MantExp(tempY);

        if(hasRotation) {
            MantExp temp = finalX.multiply(rotationRe).subtract_mutable(finalY.multiply(rotationIm));
            finalY = finalX.multiply(rotationIm).add_mutable(finalY.multiply(rotationRe));
            finalX = temp;
            finalX.Normalize();
            finalY.Normalize();
        }
        if(Plane.FLIP_REAL) {
            finalX.negate_mutable();
        }
        if(Plane.FLIP_IMAGINARY) {
            finalY.negate_mutable();
        }

        return MantExpComplex.create(finalX, finalY);
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        return getComplexWithYBase(offset.getY(y));
    }

    @Override
    public void precalculateY(int y) {

        y = offset.getY(y);

        if(!js.enableJitter) {
//            tempY = temp_y_corner.subtract(temp_size_image_size_y.multiply(y));
//            tempY.Normalize();
            tempY = temp_size_image_size_y.multiply(image_size_2y - y);
            tempY.Normalize();
        }

        indexY = y;

    }

    @Override
    public void precalculateX(int x) {

        x = offset.getX(x);

        if(!js.enableJitter) {
//            tempX = temp_x_corner.add(temp_size_image_size_x.multiply(x));
//            tempX.Normalize();
            tempX = temp_size_image_size_x.multiply(x - image_size_2x);
            tempX.Normalize();
        }

        indexX = x;

    }

    private void createRotation(Apfloat[] rotation_center, Apfloat[] rotation_vals) {
        hasRotation = Rotation.usesRotation(rotation_center, rotation_vals);
        if (!hasRotation) {
            return;
        }

        if(bignumLib == Constants.BIGNUM_BUILT_IN) {
            rotation = new Rotation(new BigNumComplex(rotation_vals[0], rotation_vals[1]), new BigNumComplex(rotation_center[0], rotation_center[1]));
        }
        else if(bignumLib == Constants.BIGNUM_BIGINT) {
            rotation = new Rotation(new BigIntNumComplex(rotation_vals[0], rotation_vals[1]), new BigIntNumComplex(rotation_center[0], rotation_center[1]));
        }
        else if (bignumLib == Constants.BIGNUM_MPFR) {
            rotation = new Rotation(new MpfrBigNumComplex(rotation_vals[0], rotation_vals[1]), new MpfrBigNumComplex(rotation_center[0], rotation_center[1]));
        }
        else if (bignumLib == Constants.BIGNUM_MPIR) {
            rotation = new Rotation(new MpirBigNumComplex(rotation_vals[0], rotation_vals[1]), new MpirBigNumComplex(rotation_center[0], rotation_center[1]));
        }
        else if (bignumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            rotation = new Rotation(new DDComplex(rotation_vals[0], rotation_vals[1]), new DDComplex(rotation_center[0], rotation_center[1]));
        }
        else if (bignumLib == Constants.BIGNUM_DOUBLE) {
            rotation = new Rotation(new Complex(rotation_vals[0].doubleValue(), rotation_vals[1].doubleValue()), new Complex(rotation_center[0].doubleValue(), rotation_center[1].doubleValue()));
        }
        else {
            rotation = new Rotation(new BigComplex(rotation_vals[0], rotation_vals[1]), new BigComplex(rotation_center[0], rotation_center[1]));
        }
        rotationRe = new MantExp(rotation_vals[0]);
        rotationIm = new MantExp(rotation_vals[1]);
    }

    @Override
    public GenericComplex getReferencePoint() {

        if(bignumLib == Constants.BIGNUM_BUILT_IN) {
            BigNumComplex c = new BigNumComplex(ddxcenter, ddycenter);
            if(hasRotation) {
                c = rotation.rotate(c);
            }
            if (Plane.FLIP_REAL) {
                c = c.negate_re_mutable();
            }
            if (Plane.FLIP_IMAGINARY) {
                c = c.conjugate_mutable();
            }
            return c;
        }
        else if(bignumLib == Constants.BIGNUM_BIGINT) {
            BigIntNumComplex c = new BigIntNumComplex(ddxcenter, ddycenter);
            if(hasRotation) {
                c = rotation.rotate(c);
            }
            if (Plane.FLIP_REAL) {
                c = c.negate_re_mutable();
            }
            if (Plane.FLIP_IMAGINARY) {
                c = c.conjugate_mutable();
            }
            return c;
        }
        else if (bignumLib == Constants.BIGNUM_MPFR) {
            MpfrBigNumComplex c = new MpfrBigNumComplex(ddxcenter, ddycenter);
            if(hasRotation) {
                c = rotation.rotate(c);
            }
            if (Plane.FLIP_REAL) {
                c = c.negate_re_mutable();
            }
            if (Plane.FLIP_IMAGINARY) {
                c = c.conjugate_mutable();
            }
            return c;
        }
        else if (bignumLib == Constants.BIGNUM_MPIR) {
            MpirBigNumComplex c = new MpirBigNumComplex(ddxcenter, ddycenter);
            if(hasRotation) {
                c = rotation.rotate(c);
            }
            if (Plane.FLIP_REAL) {
                c = c.negate_re_mutable();
            }
            if (Plane.FLIP_IMAGINARY) {
                c = c.conjugate_mutable();
            }
            return c;
        }
        else if (bignumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            DDComplex c = new DDComplex(ddxcenter, ddycenter);
            if(hasRotation) {
                c = rotation.rotate(c);
            }
            if (Plane.FLIP_REAL) {
                c = c.negate_re_mutable();
            }
            if (Plane.FLIP_IMAGINARY) {
                c = c.conjugate_mutable();
            }
            return c;
        }
        else if (bignumLib == Constants.BIGNUM_DOUBLE) {
            Complex c = new Complex(ddxcenter, ddycenter);
            if(hasRotation) {
                c = rotation.rotate(c);
            }
            if (Plane.FLIP_REAL) {
                c = c.negate_re_mutable();
            }
            if (Plane.FLIP_IMAGINARY) {
                c = c.conjugate_mutable();
            }
            return c;
        }
        else {
            BigComplex c = new BigComplex(ddxcenter, ddycenter);
            if(hasRotation) {
                c = rotation.rotate(c);
            }
            if (Plane.FLIP_REAL) {
                c = c.negate_re_mutable();
            }
            if (Plane.FLIP_IMAGINARY) {
                c = c.conjugate_mutable();
            }
            return c;
        }
    }

    @Override
    public MantExp getMaxSizeInImage() {

        MantExp temp = temp_size_image_size_x.multiply(width * 0.5);
        temp = temp.square();
        temp.Normalize();
        MantExp temp2 = temp_size_image_size_y.multiply(height * 0.5);
        temp2 = temp2.square();
        temp2.Normalize();
        MantExp temp3 = temp.add(temp2).sqrt();
        temp3.Normalize();
        return temp3;

    }

    @Override
    public MantExp getSize() {
        return new MantExp(size);
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample, int loc) {

        MantExp finalX;
        MantExp finalY;

        if(aaJitter) {
            int r = (int)(hash(loc) % NUMBER_OF_AA_JITTER_KERNELS);
            MantExp[] antialiasing_x = precalculatedJitterDataMantexp[r][0];
            MantExp[] antialiasing_y = precalculatedJitterDataMantexp[r][1];
            finalX = tempX.add(antialiasing_x[sample]);
            finalX.Normalize();
            finalY = tempY.add(antialiasing_y[sample]);
            finalY.Normalize();
        }
        else {
            finalX = tempX.add(antialiasing_x[sample]);
            finalX.Normalize();
            finalY = tempY.add(antialiasing_y[sample]);
            finalY.Normalize();
        }

        if(hasRotation) {
            MantExp temp = finalX.multiply(rotationRe).subtract_mutable(finalY.multiply(rotationIm));
            finalY = finalX.multiply(rotationIm).add_mutable(finalY.multiply(rotationRe));
            finalX = temp;
            finalX.Normalize();
            finalY.Normalize();
        }
        if(Plane.FLIP_REAL) {
            finalX.negate_mutable();
        }
        if(Plane.FLIP_IMAGINARY) {
            finalY.negate_mutable();
        }

        return MantExpComplex.create(finalX, finalY);

    }

    @Override
    public void createAntialiasingSteps(boolean adaptive, boolean jitter, int numberOfExtraSamples) {
        super.createAntialiasingSteps(adaptive, jitter, numberOfExtraSamples);
        MantExp[][] steps = createAntialiasingStepsMantexp(temp_size_image_size_x, temp_size_image_size_y, adaptive, jitter, numberOfExtraSamples);
        antialiasing_x = steps[0];
        antialiasing_y = steps[1];
    }
}
