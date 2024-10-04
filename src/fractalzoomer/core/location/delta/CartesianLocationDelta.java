package fractalzoomer.core.location.delta;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.app_settings.JitterSettings;
import fractalzoomer.planes.Plane;
import org.apfloat.Apfloat;

public class CartesianLocationDelta extends Location {
    private double height_ratio;
    private double dsize;
    private double temp_size_image_size_x;
    private double temp_size_image_size_y;
    private double temp_x_corner;
    private double temp_y_corner;

    private double image_size_2x;
    private double image_size_2y;
    private JitterSettings js;

    private double tempY;
    private double tempX;

    private Apfloat ddxcenter;
    private Apfloat ddycenter;

    private int bignumLib;

    private double[] antialiasing_x;

    private double[] antialiasing_y;

    private int width;
    private int height;

    private Rotation rotation;
    private boolean hasRotation;
    private double rotationRe;
    private double rotationIm;

    public CartesianLocationDelta(Apfloat xCenter, Apfloat yCenter, Apfloat ddsize, double height_ratio, int width, int height, Apfloat[] rotation_center, Apfloat[] rotation_vals, JitterSettings js, Fractal fractal, int bignumLib) {

        super();

        this.fractal = fractal;
        this.height_ratio = height_ratio;
        ddxcenter = xCenter;
        ddycenter = yCenter;
        //Apfloat ddheight_ratio = new MyApfloat(height_ratio);
        this.bignumLib = bignumLib;

        this.js = js;
        //this.ddsize = ddsize;
        dsize = ddsize.doubleValue();

        width = offset.getWidth(width);
        height = offset.getHeight(height);
        int image_size = Math.min(width, height);
        double coefx = width == image_size ? 0.5 : (1 + (width - (double)height) / height) * 0.5;
        double coefy = height == image_size ? 0.5 : (1 + (height - (double)width) / width) * 0.5;

        this.width = width;
        this.height = height;

        image_size_2x = width * 0.5;
        image_size_2y = height * 0.5;

        double size_2_x = dsize * coefx;
        double temp = dsize * height_ratio;
        double size_2_y = temp * coefy;
        temp_size_image_size_x = dsize / image_size;
        temp_size_image_size_y = temp / image_size;

        temp_x_corner = - size_2_x;
        temp_y_corner = size_2_y;

        createRotation(rotation_center, rotation_vals);
    }

    public CartesianLocationDelta(CartesianLocationDelta other) {
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
        dsize = other.dsize;
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

    private Complex getComplexBase(int x, int y) {
        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);
//            tempX = temp_x_corner + (x + res[1]) * temp_size_image_size_x;
//            tempY = temp_y_corner - (y + res[0]) * temp_size_image_size_y;
            tempX = ((x + res[1]) - image_size_2x) * temp_size_image_size_x;
            tempY = (image_size_2y - (y + res[0])) * temp_size_image_size_y;
        }
        else {
            //tempX = temp_x_corner + x * temp_size_image_size_x;
            //tempY = temp_y_corner - y * temp_size_image_size_y;
            tempX = (x - image_size_2x) * temp_size_image_size_x;
            tempY = (image_size_2y - y ) * temp_size_image_size_y;
        }

        indexX = x;
        indexY = y;

        double finalX = tempX;
        double finalY = tempY;

        if(hasRotation) {
            double temp = finalX * rotationRe - finalY * rotationIm;
            finalY = finalX * rotationIm + finalY * rotationRe;
            finalX = temp;
        }
        if(Plane.FLIP_REAL) {
            finalX = -finalX;
        }
        if(Plane.FLIP_IMAGINARY) {
            finalY = -finalY;
        }
        return new Complex(finalX, finalY);
    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexBase(offset.getX(x), offset.getY(y));
    }

    private Complex getComplexWithXBase(int x) {
        if(js.enableJitter) {
            return getComplexBase(x, indexY);
        }

        //tempX = temp_x_corner + x * temp_size_image_size_x;
        tempX = (x - image_size_2x) * temp_size_image_size_x;

        indexX = x;

        double finalX = tempX;
        double finalY = tempY;

        if(hasRotation) {
            double temp = finalX * rotationRe - finalY * rotationIm;
            finalY = finalX * rotationIm + finalY * rotationRe;
            finalX = temp;
        }
        if(Plane.FLIP_REAL) {
            finalX = -finalX;
        }
        if(Plane.FLIP_IMAGINARY) {
            finalY = -finalY;
        }
        return new Complex(finalX, finalY);
    }

    @Override
    public GenericComplex getComplexWithX(int x) {
       return getComplexWithXBase(offset.getX(x));
    }

    private Complex getComplexWithYBase(int y) {
        if(js.enableJitter) {
            return getComplexBase(indexX, y);
        }

        //tempY = temp_y_corner - y * temp_size_image_size_y;
        tempY = (image_size_2y - y ) * temp_size_image_size_y;

        indexY = y;

        double finalX = tempX;
        double finalY = tempY;

        if(hasRotation) {
            double temp = finalX * rotationRe - finalY * rotationIm;
            finalY = finalX * rotationIm + finalY * rotationRe;
            finalX = temp;
        }
        if(Plane.FLIP_REAL) {
            finalX = -finalX;
        }
        if(Plane.FLIP_IMAGINARY) {
            finalY = -finalY;
        }
        return new Complex(finalX, finalY);
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        return getComplexWithYBase(offset.getY(y));
    }

    @Override
    public void precalculateY(int y) {

        y = offset.getY(y);

        if(!js.enableJitter) {
            //tempY = temp_y_corner - y * temp_size_image_size_y;
            tempY = (image_size_2y - y ) * temp_size_image_size_y;
        }

        indexY = y;

    }

    @Override
    public void precalculateX(int x) {

        x = offset.getX(x);

        if(!js.enableJitter) {
            //tempX = temp_x_corner + x * temp_size_image_size_x;
            tempX = (x - image_size_2x) * temp_size_image_size_x;
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
        rotationRe = rotation_vals[0].doubleValue();
        rotationIm = rotation_vals[1].doubleValue();
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

        return new MantExp(Math.hypot(temp_size_image_size_x * width * 0.5, temp_size_image_size_y * height * 0.5));

    }

    @Override
    public MantExp getSize() {
        return new MantExp(dsize);
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample, int loc) {

        double finalX;
        double finalY;

        if(aaJitter) {
            int r = (int)(hash(loc) % NUMBER_OF_AA_JITTER_KERNELS);
            double[] antialiasing_x = precalculatedJitterDataDouble[r][0];
            double[] antialiasing_y = precalculatedJitterDataDouble[r][1];
            finalX = tempX + antialiasing_x[sample];
            finalY = tempY + antialiasing_y[sample];
        }
        else {
            finalX = tempX + antialiasing_x[sample];
            finalY = tempY + antialiasing_y[sample];
        }

        if(hasRotation) {
            double temp = finalX * rotationRe - finalY * rotationIm;
            finalY = finalX * rotationIm + finalY * rotationRe;
            finalX = temp;
        }
        if(Plane.FLIP_REAL) {
            finalX = -finalX;
        }
        if(Plane.FLIP_IMAGINARY) {
            finalY = -finalY;
        }
        return new Complex(finalX, finalY);

    }

    @Override
    public void createAntialiasingSteps(boolean adaptive, boolean jitter, int numberOfExtraSamples) {
        super.createAntialiasingSteps(adaptive, jitter, numberOfExtraSamples);
        double[][] steps = createAntialiasingStepsDouble(temp_size_image_size_x, temp_size_image_size_y, adaptive, jitter, numberOfExtraSamples);
        antialiasing_x = steps[0];
        antialiasing_y = steps[1];
    }
}
