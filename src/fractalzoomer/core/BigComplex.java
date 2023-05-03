package fractalzoomer.core;

import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

public class BigComplex extends GenericComplex {
    private Apfloat re;
    private Apfloat im;

    public BigComplex() {

        re = new MyApfloat(0.0);
        im = new MyApfloat(0.0);

    }

    public BigComplex(Apfloat re, Apfloat im) {

        this.re = re;
        this.im = im;

    }

    public BigComplex(double re, double im) {

        this.re = new MyApfloat(re);
        this.im = new MyApfloat(im);

    }

    public BigComplex(BigComplex z) {

        re = z.re;
        im = z.im;

    }

    public BigComplex(Complex z) {

        re = new MyApfloat(z.getRe());
        im = new MyApfloat(z.getIm());

    }

    @Override
    public BigNumComplex toBigNumComplex() { return new BigNumComplex(this); }

    @Override
    public MpfrBigNumComplex toMpfrBigNumComplex() { return new MpfrBigNumComplex(this);}

    @Override
    public MpirBigNumComplex toMpirBigNumComplex() { return new MpirBigNumComplex(this);}

    public final Apfloat getRe() {

        return re;

    }

    public final Apfloat getIm() {

        return im;

    }

    public final void setRe(Apfloat re) {

        this.re = re;

    }

    public final void setIm(Apfloat im) {

        this.im = im;

    }

    @Override
    public final Complex toComplex() {
        return new Complex(this);
    }

    /*
     * z1 + z2
     */
    public final BigComplex plus(BigComplex z) {

        return new BigComplex(MyApfloat.fp.add(re, z.re), MyApfloat.fp.add(im, z.im));

    }

    /*
     *  z + Real
     */
    @Override
    public final BigComplex plus(Apfloat number) {

        return new BigComplex(MyApfloat.fp.add(re, number), im);

    }

    /*
     *  z + Imaginary
     */
    public final BigComplex plus_i(Apfloat number) {

        return new BigComplex(re, MyApfloat.fp.add(im, number));

    }

    /*
     *  z1 - z2
     */
    public final BigComplex sub(BigComplex z) {

        return  new BigComplex(MyApfloat.fp.subtract(re, z.re), MyApfloat.fp.subtract(im, z.im));

    }

    /*
     *  z - Real
     */
    @Override
    public final BigComplex sub(Apfloat number) {

        return  new BigComplex(MyApfloat.fp.subtract(re, number),im);

    }

    /*
     *  z - Imaginary
     */
    public final BigComplex sub_i(Apfloat number) {

        return new BigComplex(re, MyApfloat.fp.subtract(im , number));

    }

    /*
     *  Real - z1
     */
    @Override
    public final BigComplex r_sub(Apfloat number) {

        return  new BigComplex(MyApfloat.fp.subtract(number, re), im.negate());

    }

    /*
     *  Imaginary - z
     */
    public final BigComplex i_sub(Apfloat number) {

        return  new BigComplex(re.negate(), MyApfloat.fp.subtract(number, im));

    }

    /*
     *  z1 * z2
     */
    public final BigComplex times(BigComplex z) {

        //return new BigComplex(MyApfloat.fp.subtract(MyApfloat.fp.multiply(re, z.re), MyApfloat.fp.multiply(im, z.im)), MyApfloat.fp.add(MyApfloat.fp.multiply(re, z.im), MyApfloat.fp.multiply(im, z.re)));

        Apfloat ac = MyApfloat.fp.multiply(re, z.re);
        Apfloat bd = MyApfloat.fp.multiply(im, z.im);
        return new BigComplex(MyApfloat.fp.subtract(ac, bd), MyApfloat.fp.subtract(MyApfloat.fp.subtract(MyApfloat.fp.multiply(MyApfloat.fp.add(re, im), MyApfloat.fp.add(z.re, z.im)), ac), bd));
    }

    /*
     *  z1 * Real
     */
    @Override
    public final BigComplex times(Apfloat number) {

        return new BigComplex(MyApfloat.fp.multiply(re, number), MyApfloat.fp.multiply(im, number));

    }

    /*
     *  z * Imaginary
     */
    public final BigComplex times_i(Apfloat number) {

        return new BigComplex(MyApfloat.fp.multiply(im.negate(), number), MyApfloat.fp.multiply(re, number));

    }

    /*
     *  z1 / z2
     */
    public final BigComplex divide(BigComplex z) {

        Apfloat temp = z.re;
        Apfloat temp2 = z.im;
        Apfloat temp3 = MyApfloat.fp.divide(MyApfloat.ONE, MyApfloat.fp.add(MyApfloat.fp.multiply(temp, temp), MyApfloat.fp.multiply(temp2, temp2)));

        return new BigComplex(MyApfloat.fp.multiply(MyApfloat.fp.add(MyApfloat.fp.multiply(re, temp), MyApfloat.fp.multiply(im, temp2)), temp3), MyApfloat.fp.multiply(MyApfloat.fp.subtract(MyApfloat.fp.multiply(im, temp), MyApfloat.fp.multiply(re, temp2)), temp3));

    }

    /*
     *  z / Real
     */
    @Override
    public final BigComplex divide(Apfloat number) {

        Apfloat temp = MyApfloat.fp.divide(MyApfloat.ONE, number);
        return new BigComplex(MyApfloat.fp.multiply(re, temp), MyApfloat.fp.multiply(im, temp));

    }

    /*
     *  z1 / Imaginary
     */
    public final BigComplex divide_i(Apfloat number) {

        Apfloat temp3 =  MyApfloat.fp.divide(MyApfloat.ONE, MyApfloat.fp.multiply(number, number));

        return new BigComplex(MyApfloat.fp.multiply(MyApfloat.fp.add(re, MyApfloat.fp.multiply(im, number)), temp3), MyApfloat.fp.multiply(MyApfloat.fp.subtract(im, MyApfloat.fp.multiply(re, number)), temp3));

    }

    /*
     *  Real / z
     */
    public final BigComplex r_divide(Apfloat number) {

        Apfloat temp = MyApfloat.fp.divide(number, MyApfloat.fp.add(MyApfloat.fp.multiply(re, re), MyApfloat.fp.multiply(im, im)));

        return new BigComplex(MyApfloat.fp.multiply(re, temp), MyApfloat.fp.multiply(im.negate(), temp));

    }

    /*
     *  Imaginary / z
     */
    public final BigComplex i_divide(Apfloat number) {

        Apfloat temp = MyApfloat.fp.divide(number, MyApfloat.fp.add(MyApfloat.fp.multiply(re, re), MyApfloat.fp.multiply(im, im)));

        return new BigComplex(MyApfloat.fp.multiply(im, temp), MyApfloat.fp.multiply(re, temp));

    }


    /*
     *  z^2
     */
    @Override
    public final BigComplex square() {
        Apfloat temp = MyApfloat.fp.multiply(re, im);
        return new BigComplex(MyApfloat.fp.multiply(MyApfloat.fp.add(re, im), MyApfloat.fp.subtract(re, im)), MyApfloat.fp.add(temp, temp));
    }

    /*
     *  z^3
     */
    @Override
    public final BigComplex cube() {

        Apfloat temp = MyApfloat.fp.multiply(re, re);
        Apfloat temp2 = MyApfloat.fp.multiply(im, im);

        return new BigComplex(MyApfloat.fp.multiply(re, MyApfloat.fp.subtract(temp, MyApfloat.fp.multiply(temp2, MyApfloat.THREE))), MyApfloat.fp.multiply(im, MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp, MyApfloat.THREE), temp2)));

    }

    /*
     *  z^4
     */
    @Override
    public final BigComplex fourth() {

        Apfloat temp = MyApfloat.fp.multiply(re, re);
        Apfloat temp2 = MyApfloat.fp.multiply(im, im);

        return new BigComplex(MyApfloat.fp.add(MyApfloat.fp.multiply(temp, MyApfloat.fp.subtract(temp, MyApfloat.fp.multiply(MyApfloat.SIX, temp2))), MyApfloat.fp.multiply(temp2, temp2)), MyApfloat.fp.multiply(MyApfloat.FOUR, MyApfloat.fp.multiply(re, MyApfloat.fp.multiply(im, MyApfloat.fp.subtract(temp, temp2)))));

    }

    /*
     *  z^5
     */
    @Override
    public final BigComplex fifth() {

        Apfloat temp = MyApfloat.fp.multiply(re, re);
        Apfloat temp2 = MyApfloat.fp.multiply(im, im);

        return new BigComplex(MyApfloat.fp.multiply(re, MyApfloat.fp.add(MyApfloat.fp.multiply(temp, temp), MyApfloat.fp.multiply(temp2, MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp2, MyApfloat.FIVE), MyApfloat.fp.multiply(temp, MyApfloat.TEN))))), MyApfloat.fp.multiply(im, MyApfloat.fp.add(MyApfloat.fp.multiply(temp2, temp2), MyApfloat.fp.multiply(temp, MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp, MyApfloat.FIVE), MyApfloat.fp.multiply(temp2, MyApfloat.TEN))))));
    }


    /*
     *  |z|^2
     */
    public final Apfloat norm_squared() {

        return MyApfloat.fp.add(MyApfloat.fp.multiply(re, re), MyApfloat.fp.multiply(im, im));

    }

    @Override
    public NormComponents normSquaredWithComponents(NormComponents n) {
        Apfloat reSqr = MyApfloat.fp.multiply(re, re);
        Apfloat imSqr = MyApfloat.fp.multiply(im, im);
        return new NormComponents(reSqr, imSqr, MyApfloat.fp.add(reSqr, imSqr));
    }

    /*
     *  |z|, euclidean norm
     */
    public final Apfloat norm() {

        return MyApfloat.fp.sqrt(MyApfloat.fp.add(MyApfloat.fp.multiply(re, re), MyApfloat.fp.multiply(im, im)));

    }

    /*
     *  |z1 - z2|^2
     */
    public final Apfloat distance_squared(BigComplex z) {

        Apfloat temp_re = MyApfloat.fp.subtract(re, z.re);
        Apfloat temp_im = MyApfloat.fp.subtract(im, z.im);
        return MyApfloat.fp.add(MyApfloat.fp.multiply(temp_re, temp_re), MyApfloat.fp.multiply(temp_im, temp_im));

    }

    /*
     *  |z1 - z2|^2
     */
    public final Apfloat distance_squared(Apfloat rootRe) {

        Apfloat temp_re = MyApfloat.fp.subtract(re, rootRe);
        return MyApfloat.fp.add(MyApfloat.fp.multiply(temp_re, temp_re), MyApfloat.fp.multiply(im, im));

    }

    /*
     *  |z1 - z2|
     */
    public final Apfloat distance(BigComplex z) {

        Apfloat temp_re = MyApfloat.fp.subtract(re, z.re);
        Apfloat temp_im = MyApfloat.fp.subtract(im, z.im);
        return MyApfloat.fp.sqrt(MyApfloat.fp.add(MyApfloat.fp.multiply(temp_re, temp_re), MyApfloat.fp.multiply(temp_im, temp_im)));

    }

    /*
     *  |z1 - z2|
     */
    public final Apfloat distance(Apfloat rootRe) {

        Apfloat temp_re = MyApfloat.fp.subtract(re, rootRe);
        return MyApfloat.fp.sqrt(MyApfloat.fp.add(MyApfloat.fp.multiply(temp_re, temp_re), MyApfloat.fp.multiply(im, im)));

    }

    /*
     *  |Real|
     */
    public final Apfloat getAbsRe() {

        return ApfloatMath.abs(re);

    }

    /*
     *  |Imaginary|
     */
    public final Apfloat getAbsIm() {

        return ApfloatMath.abs(im);

    }

    /*
     *  |Re(z)| + Im(z)i
     */
    public final BigComplex absre() {

        return new BigComplex(ApfloatMath.abs(re), im);

    }

    /*
     *  Re(z) + |Im(z)|i
     */
    public final BigComplex absim() {

        return new BigComplex(re,ApfloatMath.abs(im));

    }

    /*
     *  Real -Imaginary i
     */
    public final BigComplex conjugate() {

        return new BigComplex(re, im.negate());

    }

    /*
     *  -z
     */
    @Override
    public final BigComplex negative() {

        return new BigComplex(re.negate(), im.negate());

    }

    /*
     *  exp(z) = exp(Re(z)) * (cos(Im(z)) + sin(Im(z))i)
     */
    public final BigComplex exp() {

        Apfloat temp = MyApfloat.exp(re);

        return new BigComplex(MyApfloat.fp.multiply(temp, MyApfloat.cos(im)), MyApfloat.fp.multiply(temp, MyApfloat.sin(im)));

    }


    /*
     *  1 / z
     */
    public final BigComplex reciprocal() {

        Apfloat temp = MyApfloat.fp.divide(MyApfloat.ONE, MyApfloat.fp.add(MyApfloat.fp.multiply(re, re), MyApfloat.fp.multiply(im, im)));
        return new BigComplex(MyApfloat.fp.multiply(re, temp), MyApfloat.fp.multiply(im.negate(), temp));

    }

    /*
     * n-norm
     */
    public final Apfloat nnorm(Apfloat n) {
        return MyApfloat.fp.pow(MyApfloat.fp.add(MyApfloat.fp.pow(ApfloatMath.abs(re), n), MyApfloat.fp.pow(ApfloatMath.abs(im), n)), MyApfloat.fp.divide(MyApfloat.ONE, n));
    }

    /*
     * n-norm
     */
    public final Apfloat nnorm(Apfloat n, Apfloat n_reciprocal) {
        return MyApfloat.fp.pow(MyApfloat.fp.add(MyApfloat.fp.pow(ApfloatMath.abs(re), n), MyApfloat.fp.pow(ApfloatMath.abs(im), n)), n_reciprocal);
    }

    /*
     *  abs(z)
     */
    public final BigComplex abs() {

        return new BigComplex(ApfloatMath.abs(re), ApfloatMath.abs(im));

    }

    /*
     *  sin(z) = (exp(iz) - exp(-iz)) / 2i
     */
    public final BigComplex sin() {

        Apfloat temp = MyApfloat.exp(im.negate());

        Apfloat cos_re = MyApfloat.cos(re);
        Apfloat sin_re = MyApfloat.sin(re);

        BigComplex temp2 = new BigComplex(MyApfloat.fp.multiply(temp, cos_re), MyApfloat.fp.multiply(temp, sin_re));

        Apfloat temp3 = MyApfloat.reciprocal(temp);

        BigComplex temp4 = new BigComplex(MyApfloat.fp.multiply(temp3, cos_re), MyApfloat.fp.multiply(temp3, sin_re.negate()));

        return (temp2.sub(temp4)).times_i(new MyApfloat(-0.5));

    }

    /*
     *  cos(z) = (exp(iz) + exp(-iz)) / 2
     */
    public final BigComplex cos() {

        Apfloat temp = MyApfloat.exp(im.negate());

        Apfloat cos_re = MyApfloat.cos(re);
        Apfloat sin_re = MyApfloat.sin(re);

        BigComplex temp2 = new BigComplex(MyApfloat.fp.multiply(temp, cos_re), MyApfloat.fp.multiply(temp, sin_re));

        Apfloat temp3 = MyApfloat.reciprocal(temp);

        BigComplex temp4 = new BigComplex(MyApfloat.fp.multiply(temp3, cos_re), MyApfloat.fp.multiply(temp3, sin_re.negate()));

        return (temp2.plus(temp4)).times(new MyApfloat(0.5));

    }

    public final BigComplex inflection(BigComplex inf) {

        BigComplex diff = this.sub(inf);

        return inf.plus(diff.square());

    }

    public final BigComplex fold_out(BigComplex z2) {

        Apfloat norm_sqr = MyApfloat.fp.add(MyApfloat.fp.multiply(re, re), MyApfloat.fp.multiply(im, im));

        return norm_sqr.compareTo(z2.norm_squared()) > 0 ? this.divide(norm_sqr) : this;

    }

    public final BigComplex fold_in(BigComplex z2) {

        Apfloat norm_sqr = MyApfloat.fp.add(MyApfloat.fp.multiply(re, re), MyApfloat.fp.multiply(im, im));

        return norm_sqr.compareTo(z2.norm_squared()) < 0 ? this.divide(norm_sqr) : this;

    }

    public final BigComplex fold_right(BigComplex z2) {

        return re.compareTo(z2.re) < 0 ? new BigComplex(MyApfloat.fp.add(re, MyApfloat.fp.multiply(MyApfloat.TWO, MyApfloat.fp.subtract(z2.re, re))), im) : this;

    }

    public final BigComplex fold_left(BigComplex z2) {

        return re.compareTo(z2.re) > 0 ? new BigComplex(MyApfloat.fp.subtract(re, MyApfloat.fp.multiply(MyApfloat.TWO, MyApfloat.fp.subtract(re, z2.re))), im) : this;

    }

    public final BigComplex fold_up(BigComplex z2) {

        return im.compareTo(z2.im) < 0 ? new BigComplex(re, MyApfloat.fp.add(im, MyApfloat.fp.multiply(MyApfloat.TWO, MyApfloat.fp.subtract(z2.im, im)))) : this;

    }

    public final BigComplex fold_down(BigComplex z2) {

        return im.compareTo(z2.im) > 0 ? new BigComplex(re,  MyApfloat.fp.subtract(im, MyApfloat.fp.multiply(MyApfloat.TWO, MyApfloat.fp.subtract(im, z2.im)))) : this;

    }

    public final BigComplex shear(BigComplex sh) {

        return new BigComplex(MyApfloat.fp.add(re, MyApfloat.fp.multiply(im, sh.re)), MyApfloat.fp.add(im, MyApfloat.fp.multiply(re, sh.im)));

    }

    public final BigComplex circle_inversion(BigComplex center, Apfloat radius) {

        Apfloat distance = this.distance_squared(center);
        Apfloat radius2 = MyApfloat.fp.multiply(radius, radius);

        Apfloat temp = MyApfloat.fp.divide(radius2, distance);

        return new BigComplex(MyApfloat.fp.multiply(MyApfloat.fp.add(center.re, MyApfloat.fp.subtract(re, center.re)), temp), MyApfloat.fp.multiply(MyApfloat.fp.add(center.im, MyApfloat.fp.subtract(im, center.im)), temp));

    }

    /*
     *  lexicographical comparison between two complex numbers
     * -1 when z1 > z2
     *  1 when z1 < z2
     *  0 when z1 == z2
     */
    public final int compare(BigComplex z2) {

        int comparisonRe = re.compareTo(z2.re);
        int comparisonIm = im.compareTo(z2.im);

        if (comparisonRe >  0) {
            return -1;
        } else if (comparisonRe < 0) {
            return 1;
        } else if (comparisonIm > 0) {
            return -1;
        } else if (comparisonIm < 0) {
            return 1;
        } else if (comparisonRe == 0 && comparisonIm == 0) {
            return 0;
        }

        return 2;
    }

    /*
     *  lexicographical comparison between two complex numbers
     * -1 when z1 > z2
     *  1 when z1 < z2
     *  0 when z1 == z2
     */
    @Override
    public final int compare(GenericComplex z2c) {

        BigComplex z2 = (BigComplex)z2c;

        int comparisonRe = re.compareTo(z2.re);
        int comparisonIm = im.compareTo(z2.im);

        if (comparisonRe >  0) {
            return -1;
        } else if (comparisonRe < 0) {
            return 1;
        } else if (comparisonIm > 0) {
            return -1;
        } else if (comparisonIm < 0) {
            return 1;
        } else if (comparisonRe == 0 && comparisonIm == 0) {
            return 0;
        }

        return 2;
    }

    /*
     * y + xi
     */
    public final BigComplex flip() {

        return new BigComplex(im, re);

    }


    public static final String toString2(Apfloat real, Apfloat imaginary, Apfloat size) {
        String temp = "";

        real = real.compareTo(MyApfloat.ZERO) == 0 ? MyApfloat.ZERO : real;
        imaginary = imaginary.compareTo(MyApfloat.ZERO) == 0 ? MyApfloat.ZERO : imaginary;

        if (imaginary.compareTo(MyApfloat.ZERO) >= 0) {
            temp = MyApfloat.toString(real, size) + "+" + MyApfloat.toString(imaginary, size) + "i";
        } else {
            temp = MyApfloat.toString(real, size) + "" + MyApfloat.toString(imaginary, size) + "i";
        }
        return temp;
    }

    public static final String toString2Truncated(Apfloat real, Apfloat imaginary, Apfloat size) {
        String temp = "";

        real = real.compareTo(MyApfloat.ZERO) == 0 ? MyApfloat.ZERO : real;
        imaginary = imaginary.compareTo(MyApfloat.ZERO) == 0 ? MyApfloat.ZERO : imaginary;

        if (imaginary.compareTo(MyApfloat.ZERO) >= 0) {
            temp = MyApfloat.toStringTruncated(real, size) + "+" + MyApfloat.toStringTruncated(imaginary, size) + "i";
        } else {
            temp = MyApfloat.toStringTruncated(real, size) + "" + MyApfloat.toStringTruncated(imaginary, size) + "i";
        }
        return temp;
    }

    public static final String toString2Pretty(Apfloat real, Apfloat imaginary, Apfloat size) {
        String temp = "";

        real = real.compareTo(MyApfloat.ZERO) == 0 ? MyApfloat.ZERO : real;
        imaginary = imaginary.compareTo(MyApfloat.ZERO) == 0 ? MyApfloat.ZERO : imaginary;

        if (imaginary.compareTo(MyApfloat.ZERO) >= 0) {
            temp = MyApfloat.toStringPretty(real, size) + "+" + MyApfloat.toStringPretty(imaginary, size) + "i";
        } else {
            temp = MyApfloat.toStringPretty(real, size) + "" + MyApfloat.toStringPretty(imaginary, size) + "i";
        }
        return temp;
    }

    public static final String toString2Pretty(Apfloat real, Apfloat imaginary) {
        String temp = "";

        real = real.compareTo(MyApfloat.ZERO) == 0 ? MyApfloat.ZERO : real;
        imaginary = imaginary.compareTo(MyApfloat.ZERO) == 0 ? MyApfloat.ZERO : imaginary;

        if (imaginary.compareTo(MyApfloat.ZERO) >= 0) {
            temp = MyApfloat.toStringPretty(real) + "+" + MyApfloat.toStringPretty(imaginary) + "i";
        } else {
            temp = MyApfloat.toStringPretty(real) + "" + MyApfloat.toStringPretty(imaginary) + "i";
        }
        return temp;
    }

    /* more efficient z^2 + c */
    public final BigComplex square_plus_c(BigComplex c) {

        Apfloat temp = MyApfloat.fp.multiply(re, im);

        return new BigComplex(MyApfloat.fp.add(MyApfloat.fp.multiply(MyApfloat.fp.add(re, im), MyApfloat.fp.subtract(re, im)), c.re), MyApfloat.fp.add(MyApfloat.fp.add(temp, temp), c.im));

    }


    @Override
    public final String toString() {

        String temp = "";

        if(im.compareTo(MyApfloat.ZERO) > 0) {
            temp = re + "+" + im + "i";
        }
        else if (im.compareTo(MyApfloat.ZERO) == 0) {
            temp = re + "+" + (0.0) + "i";
        } else {
            temp = re + "" + im + "i";
        }

        return temp;

    }

    public final String toStringPretty() {

        String temp = "";

        if(im.compareTo(MyApfloat.ZERO) > 0) {
            temp = re.toString(true) + "+" + im.toString(true) + "i";
        }
        else if (im.compareTo(MyApfloat.ZERO) == 0) {
            temp = re.toString(true) + "+" + (0.0) + "i";
        } else {
            temp = re.toString(true) + "" + im.toString(true) + "i";
        }

        return temp;

    }

    public final boolean isZero() {
        return re.compareTo(Apfloat.ZERO) == 0 && im.compareTo(Apfloat.ZERO) == 0;
    }

    public final boolean isOne() {
        return re.compareTo(Apfloat.ONE) == 0 && im.compareTo(Apfloat.ZERO) == 0;
    }


    /*
     *  z^2 + c
     */
    public final BigComplex squareFast_plus_c(NormComponents normComponents, GenericComplex ca) {
        Apfloat reSqr = (Apfloat) normComponents.reSqr;
        Apfloat imSqr = (Apfloat) normComponents.imSqr;
        Apfloat normSquared = (Apfloat) normComponents.normSquared;
        BigComplex c = (BigComplex) ca;
        Apfloat temp = MyApfloat.fp.add(re, im);
        return new BigComplex(MyApfloat.fp.add(MyApfloat.fp.subtract(reSqr, imSqr), c.re), MyApfloat.fp.add(MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp, temp), normSquared), c.im));
    }

    /*
     *  z^2 + c
     */
    @Override
    public final BigComplex squareFast(NormComponents normComponents) {
        Apfloat reSqr = (Apfloat) normComponents.reSqr;
        Apfloat imSqr = (Apfloat) normComponents.imSqr;
        Apfloat normSquared = (Apfloat) normComponents.normSquared;
        Apfloat temp = MyApfloat.fp.add(re, im);
        return new BigComplex(MyApfloat.fp.subtract(reSqr, imSqr), MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp, temp), normSquared));
    }

    /*
     *  z^3
     */
    public final BigComplex cubeFast(NormComponents normComponents) {

        Apfloat temp = (Apfloat) normComponents.reSqr;
        Apfloat temp2 = (Apfloat) normComponents.imSqr;

        return new BigComplex(MyApfloat.fp.multiply(re, MyApfloat.fp.subtract(temp, MyApfloat.fp.multiply(temp2, MyApfloat.THREE))), MyApfloat.fp.multiply(im, MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp, MyApfloat.THREE), temp2)));

    }

    /*
     *  z^4
     */
    public final BigComplex fourthFast(NormComponents normComponents) {

        Apfloat temp = (Apfloat) normComponents.reSqr;
        Apfloat temp2 = (Apfloat) normComponents.imSqr;

        return new BigComplex(MyApfloat.fp.add(MyApfloat.fp.multiply(temp, MyApfloat.fp.subtract(temp, MyApfloat.fp.multiply(MyApfloat.SIX, temp2))), MyApfloat.fp.multiply(temp2, temp2)), MyApfloat.fp.multiply(MyApfloat.FOUR, MyApfloat.fp.multiply(re, MyApfloat.fp.multiply(im, MyApfloat.fp.subtract(temp, temp2)))));

    }

    /*
     *  z^5
     */
    public final BigComplex fifthFast(NormComponents normComponents) {

        Apfloat temp = (Apfloat) normComponents.reSqr;
        Apfloat temp2 = (Apfloat) normComponents.imSqr;

        return new BigComplex(MyApfloat.fp.multiply(re, MyApfloat.fp.add(MyApfloat.fp.multiply(temp, temp), MyApfloat.fp.multiply(temp2, MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp2, MyApfloat.FIVE), MyApfloat.fp.multiply(temp, MyApfloat.TEN))))), MyApfloat.fp.multiply(im, MyApfloat.fp.add(MyApfloat.fp.multiply(temp2, temp2), MyApfloat.fp.multiply(temp, MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp, MyApfloat.FIVE), MyApfloat.fp.multiply(temp2, MyApfloat.TEN))))));
    }

    /*
     *  z1 - z2
     */
    @Override
    public final BigComplex sub(GenericComplex zn) {

        BigComplex z = (BigComplex)zn;

        return  new BigComplex(MyApfloat.fp.subtract(re, z.re), MyApfloat.fp.subtract(im, z.im));

    }

    /*
     * z1 + z2
     */
    @Override
    public final BigComplex plus(GenericComplex zn) {

        BigComplex z = (BigComplex)zn;
        return new BigComplex(MyApfloat.fp.add(re, z.re), MyApfloat.fp.add(im, z.im));

    }

    /*
     *  z1 * z2
     */
    @Override
    public final BigComplex times(GenericComplex zn) {

        BigComplex z = (BigComplex)zn;
        //return new BigComplex(MyApfloat.fp.subtract(MyApfloat.fp.multiply(re, z.re), MyApfloat.fp.multiply(im, z.im)), MyApfloat.fp.add(MyApfloat.fp.multiply(re, z.im), MyApfloat.fp.multiply(im, z.re)));

        Apfloat ac = MyApfloat.fp.multiply(re, z.re);
        Apfloat bd = MyApfloat.fp.multiply(im, z.im);
        return new BigComplex(MyApfloat.fp.subtract(ac, bd), MyApfloat.fp.subtract(MyApfloat.fp.subtract(MyApfloat.fp.multiply(MyApfloat.fp.add(re, im), MyApfloat.fp.add(z.re, z.im)), ac), bd));

    }

    /*
     *  |z|^2
     */
    @Override
    public final Apfloat normSquared() {

        return MyApfloat.fp.add(MyApfloat.fp.multiply(re, re), MyApfloat.fp.multiply(im, im));

    }

    /*
     *  |z1 - z2|^2
     */
    @Override
    public final Apfloat distanceSquared(GenericComplex za) {
        BigComplex z = (BigComplex) za;
        Apfloat temp_re = MyApfloat.fp.subtract(re, z.re);
        Apfloat temp_im = MyApfloat.fp.subtract(im, z.im);
        return MyApfloat.fp.add(MyApfloat.fp.multiply(temp_re, temp_re), MyApfloat.fp.multiply(temp_im, temp_im));

    }

    @Override
    public BigComplex times2() {
        return new BigComplex(re.multiply(MyApfloat.TWO), im.multiply(MyApfloat.TWO));
    }

    @Override
    public BigComplex times4() {
        return new BigComplex(re.multiply(MyApfloat.FOUR), im.multiply(MyApfloat.FOUR));
    }

    @Override
    public MantExpComplex toMantExpComplex() { return new MantExpComplex(this);}

    @Override
    public void set(GenericComplex za) {
        BigComplex z = (BigComplex) za;
        re = z.re;
        im = z.im;
    }

    /* more efficient z^2 + c */
    public final BigComplex square_plus_c(GenericComplex cn) {

        BigComplex c = (BigComplex)cn;
        Apfloat temp = MyApfloat.fp.multiply(re, im);
        return new BigComplex(MyApfloat.fp.add(MyApfloat.fp.multiply(MyApfloat.fp.add(re, im), MyApfloat.fp.subtract(re, im)), c.re), MyApfloat.fp.add(MyApfloat.fp.add(temp, temp), c.im));

    }

    /*
     *  z1 / z2
     */
    @Override
    public final BigComplex divide(GenericComplex za) {

        BigComplex z = (BigComplex)za;
        Apfloat temp = z.re;
        Apfloat temp2 = z.im;
        Apfloat temp3 =  MyApfloat.fp.divide(MyApfloat.ONE, MyApfloat.fp.add(MyApfloat.fp.multiply(temp, temp), MyApfloat.fp.multiply(temp2, temp2)));

        return new BigComplex(MyApfloat.fp.multiply(MyApfloat.fp.add(MyApfloat.fp.multiply(re, temp), MyApfloat.fp.multiply(im, temp2)), temp3), MyApfloat.fp.multiply(MyApfloat.fp.subtract(MyApfloat.fp.multiply(im, temp), MyApfloat.fp.multiply(re, temp2)), temp3));

    }

    @Override
    public DDComplex toDDComplex() { return new DDComplex(this); }

    @Override
    public GenericComplex times_mutable(GenericComplex a) {return times(a);}

    @Override
    public GenericComplex divide_mutable(GenericComplex a) {return divide(a);}

    @Override
    public GenericComplex sub_mutable(GenericComplex v) { return sub(v); }

    @Override
    public GenericComplex plus_mutable(GenericComplex v) { return plus(v); }

    @Override
    public GenericComplex abs_mutable() { return abs(); }

    @Override
    public GenericComplex square_mutable() { return square(); }

    /*
     *  z^3
     */
    @Override
    public final BigComplex squareFast_mutable(NormComponents normComponents) {

        return squareFast(normComponents);

    }

    /*
     *  z^3
     */
    @Override
    public final BigComplex cubeFast_mutable(NormComponents normComponents) {

       return cubeFast(normComponents);

    }

    /*
      *  z^4
            */
    @Override
    public final BigComplex fourthFast_mutable(NormComponents normComponents) {

       return fourthFast(normComponents);

    }

    /*
     *  z^5
     */
    @Override
    public final BigComplex fifthFast_mutable(NormComponents normComponents) {

       return fifthFast(normComponents);

    }

    /*
     *  z^2 + c
     */
    @Override
    public final BigComplex squareFast_plus_c_mutable(NormComponents normComponents, GenericComplex ca) {
        return squareFast_plus_c(normComponents, ca);
    }

    /* more efficient z^2 + c */
    @Override
    public final BigComplex square_plus_c_mutable(GenericComplex cn) {

        return square_plus_c(cn);

    }

    /*
     *  Real -Imaginary i
     */
    @Override
    public final BigComplex conjugate_mutable() {

        return conjugate();

    }
}
