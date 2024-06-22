package fractalzoomer.planes.distort;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.planes.Plane;
import org.apfloat.Apcomplex;
import org.apfloat.Apfloat;

public class StretchPlane extends Plane {
    private double r;
    private double r_reciprocal;

    private Apfloat v1, v2, v3, v4;
    private double v1d, v2d, v3d, v4d;
    private DoubleDouble v1dd, v2dd, v3dd, v4dd;

    private BigNum v1bn, v2bn, v3bn, v4bn;
    private BigIntNum v1bin, v2bin, v3bin, v4bin;

    private MpfrBigNum v1mpfr, v2mpfr, v3mpfr, v4mpfr;
    private MpirBigNum v1mpir, v2mpir, v3mpir, v4mpir;

    private MpfrBigNum tempxmpfr;
    private MpfrBigNum tempympfr;

    private MpfrBigNum temp1mpfr;
    private MpfrBigNum temp2mpfr;
    private MpfrBigNum temp3mpfr;
    private MpfrBigNum temp4mpfr;

    private MpirBigNum tempxmpir;
    private MpirBigNum tempympir;

    private MpirBigNum temp1mpir;
    private MpirBigNum temp2mpir;
    private MpirBigNum temp3mpir;
    private MpirBigNum temp4mpir;
    private double xcenterd;
    private double ycenterd;

    private Apfloat xcenter;
    private Apfloat ycenter;

    private DoubleDouble xcenterdd;
    private DoubleDouble ycenterdd;

    private BigNum xcenterbn;
    private BigNum ycenterbn;

    private BigIntNum xcenterbin;
    private BigIntNum ycenterbin;

    private MpfrBigNum xcentermpfr;
    private MpfrBigNum ycentermpfr;

    private MpirBigNum xcentermpir;
    private MpirBigNum ycentermpir;
    private boolean use_center;

    public StretchPlane(double stretchAngle, double strechAmount, Apfloat[] center) {
        r = Math.pow(2, strechAmount);
        double rad_angle = Math.toRadians(stretchAngle);
        double cosanagle = Math.cos(rad_angle);
        double sinangle = Math.sin(rad_angle);
        r_reciprocal = 1 / r;
        double cossqr = cosanagle * cosanagle;
        double sinsqr = sinangle * sinangle;
        v1d = r * cossqr + r_reciprocal * sinsqr;
        v2d = cosanagle * sinangle * (r_reciprocal - r);
        v3d = v2d;
        v4d = r * sinsqr + r_reciprocal * cossqr;

        use_center = center[0].compareTo(Apfloat.ZERO) != 0 && center[1].compareTo(Apcomplex.ZERO) != 0;

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            v1dd = new DoubleDouble(v1d);
            v2dd = new DoubleDouble(v2d);
            v3dd = new DoubleDouble(v3d);
            v4dd = new DoubleDouble(v4d);

            v1 = new MyApfloat(v1d);
            v2 = new MyApfloat(v2d);
            v3 = new MyApfloat(v3d);
            v4 = new MyApfloat(v4d);

            v1bn = BigNum.create(v1d);
            v2bn = BigNum.create(v2d);
            v3bn = BigNum.create(v3d);
            v4bn = BigNum.create(v4d);

            v1bin = new BigIntNum(v1d);
            v2bin = new BigIntNum(v2d);
            v3bin = new BigIntNum(v3d);
            v4bin = new BigIntNum(v4d);

            if (TaskRender.allocateMPFR()) {
                v1mpfr = new MpfrBigNum(v1d);
                v2mpfr = new MpfrBigNum(v2d);
                v3mpfr = new MpfrBigNum(v3d);
                v4mpfr = new MpfrBigNum(v4d);

                tempxmpfr = new MpfrBigNum();
                tempympfr = new MpfrBigNum();
                temp1mpfr = new MpfrBigNum();
                temp2mpfr = new MpfrBigNum();
                temp3mpfr = new MpfrBigNum();
                temp4mpfr = new MpfrBigNum();
            } else if (TaskRender.allocateMPIR()) {
                v1mpir = new MpirBigNum(v1d);
                v2mpir = new MpirBigNum(v2d);
                v3mpir = new MpirBigNum(v3d);
                v4mpir = new MpirBigNum(v4d);

                tempxmpir = new MpirBigNum();
                tempympir = new MpirBigNum();
                temp1mpir = new MpirBigNum();
                temp2mpir = new MpirBigNum();
                temp3mpir = new MpirBigNum();
                temp4mpir = new MpirBigNum();
            }
        }

        if(use_center) {
            xcenter = center[0];
            ycenter = center[1];

            xcenterd = xcenter.doubleValue();
            ycenterd = ycenter.doubleValue();

            if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
                xcenterdd = new DoubleDouble(xcenter);
                ycenterdd = new DoubleDouble(ycenter);

                xcenterbn = BigNum.create(xcenter);
                ycenterbn = BigNum.create(ycenter);

                xcenterbin = new BigIntNum(xcenter);
                ycenterbin = new BigIntNum(ycenter);

                if (TaskRender.allocateMPFR()) {
                    xcentermpfr = new MpfrBigNum(xcenter);
                    ycentermpfr = new MpfrBigNum(ycenter);
                } else if (TaskRender.allocateMPIR()) {
                    xcentermpir = MpirBigNum.fromApfloat(xcenter);
                    ycentermpir = MpirBigNum.fromApfloat(ycenter);
                }
            }

        }
    }

    @Override
    public Complex transform_internal(Complex pixel) {

        double x = pixel.getRe();
        double y = pixel.getIm();
        if(use_center) {
           x -= xcenterd;
           y -= ycenterd;
        }

        double xprime = x * v1d + y * v2d;
        double yprime = x * v3d + y * v4d;

        if(use_center) {
            xprime += xcenterd;
            yprime += ycenterd;
        }

        return new Complex(xprime, yprime);
    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        DoubleDouble x = pixel.getRe();
        DoubleDouble y = pixel.getIm();
        if(use_center) {
            x = x.subtract(xcenterdd);
            y = y.subtract(ycenterdd);
        }

        DoubleDouble xprime = x.multiply(v1dd).add(y.multiply(v2dd));
        DoubleDouble yprime = x.multiply(v3dd).add(y.multiply(v4dd));

        if(use_center) {
            xprime = xprime.add(xcenterdd);
            yprime = yprime.add(ycenterdd);
        }

        return new DDComplex(xprime, yprime);
    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        Apfloat x = pixel.getRe();
        Apfloat y = pixel.getIm();
        if(use_center) {
            x = MyApfloat.fp.subtract(x, xcenter);
            y = MyApfloat.fp.subtract(y, ycenter);
        }

        Apfloat xprime = MyApfloat.fp.add(MyApfloat.fp.multiply(x, v1), MyApfloat.fp.multiply(y, v2));
        Apfloat yprime = MyApfloat.fp.add(MyApfloat.fp.multiply(x, v3), MyApfloat.fp.multiply(y, v4));

        if(use_center) {
            xprime = MyApfloat.fp.add(xprime, xcenter);
            yprime = MyApfloat.fp.add(yprime, ycenter);
        }

        return new BigComplex(xprime, yprime);
    }

    @Override
    public BigNumComplex transform_internal(BigNumComplex pixel) {

        BigNum x = pixel.getRe();
        BigNum y = pixel.getIm();
        if(use_center) {
            x = x.sub(xcenterbn);
            y = y.sub(ycenterbn);
        }

        BigNum xprime = x.mult(v1bn).add(y.mult(v2bn));
        BigNum yprime = x.mult(v3bn).add(y.mult(v4bn));

        if(use_center) {
            xprime = xprime.add(xcenterbn);
            yprime = yprime.add(ycenterbn);
        }

        return new BigNumComplex(xprime, yprime);
    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        BigIntNum x = pixel.getRe();
        BigIntNum y = pixel.getIm();
        if(use_center) {
            x = x.sub(xcenterbin);
            y = y.sub(ycenterbin);
        }

        BigIntNum xprime = x.mult(v1bin).add(y.mult(v2bin));
        BigIntNum yprime = x.mult(v3bin).add(y.mult(v4bin));

        if(use_center) {
            xprime = xprime.add(xcenterbin);
            yprime = yprime.add(ycenterbin);
        }

        return new BigIntNumComplex(xprime, yprime);
    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {


        tempxmpfr.set(pixel.getRe());
        tempympfr.set(pixel.getIm());
        if(use_center) {
            tempxmpfr.sub(xcentermpfr, tempxmpfr);
            tempympfr.sub(ycentermpfr, tempympfr);
        }

        tempxmpfr.mult(v1mpfr, temp1mpfr);
        tempympfr.mult(v2mpfr, temp2mpfr);

        tempxmpfr.mult(v3mpfr, temp3mpfr);
        tempympfr.mult(v4mpfr, temp4mpfr);

        MpfrBigNum xprime = temp1mpfr.add(temp2mpfr, temp1mpfr);
        MpfrBigNum yprime = temp3mpfr.add(temp4mpfr, temp3mpfr);

        if(use_center) {
            xprime.add(xcentermpfr, xprime);
            yprime.add(ycentermpfr, yprime);
        }

        return new MpfrBigNumComplex(new MpfrBigNum(xprime), new MpfrBigNum(yprime));
    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {


        tempxmpir.set(pixel.getRe());
        tempympir.set(pixel.getIm());
        if(use_center) {
            tempxmpir.sub(xcentermpir, tempxmpir);
            tempympir.sub(ycentermpir, tempympir);
        }

        tempxmpir.mult(v1mpir, temp1mpir);
        tempympir.mult(v2mpir, temp2mpir);

        tempxmpir.mult(v3mpir, temp3mpir);
        tempympir.mult(v4mpir, temp4mpir);

        MpirBigNum xprime = temp1mpir.add(temp2mpir, temp1mpir);
        MpirBigNum yprime = temp3mpir.add(temp4mpir, temp3mpir);

        if(use_center) {
            xprime.add(xcentermpir, xprime);
            yprime.add(ycentermpir, yprime);
        }

        return new MpirBigNumComplex(new MpirBigNum(xprime), new MpirBigNum(yprime));
    }
}
