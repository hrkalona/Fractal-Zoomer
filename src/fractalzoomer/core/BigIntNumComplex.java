package fractalzoomer.core;

import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

import java.util.concurrent.Future;

public class BigIntNumComplex extends GenericComplex {
    private BigIntNum re;
    private BigIntNum im;

    public BigIntNumComplex(BigComplex c) {
        re = new BigIntNum(c.getRe());
        im = new BigIntNum(c.getIm());
    }

    public BigIntNumComplex(double re, double im) {
        this.re = new BigIntNum(re);
        this.im = new BigIntNum(im);
    }

    public BigIntNumComplex(BigIntNumComplex c) {
        re = new BigIntNum(c.getRe());
        im = new BigIntNum(c.getIm());
    }

    public BigIntNumComplex(Complex c) {
        re = new BigIntNum(c.getRe());
        im = new BigIntNum(c.getIm());
    }

    public BigIntNumComplex(BigIntNum re, BigIntNum im) {
       this.re = re;
       this.im = im;
    }

    public BigIntNumComplex(Apfloat re, Apfloat im) {
        this.re = new BigIntNum(re);
        this.im = new BigIntNum(im);
    }

    public BigIntNumComplex(String re, String im) {
        this.re = new BigIntNum(re);
        this.im = new BigIntNum(im);
    }

    public BigIntNumComplex() {

        re = new BigIntNum();
        im = new BigIntNum();

    }

    @Override
    public BigIntNumComplex toBigIntNumComplex() { return this; }

    public final BigIntNum getRe() {

        return re;

    }

    public final BigIntNum getIm() {

        return im;

    }

    public final void setRe(BigIntNum re) {

        this.re = re;

    }

    public final void setIm(BigIntNum im) {

        this.im = im;

    }

    @Override
    public final Complex toComplex() {
        return new Complex(re.doubleValue(), im.doubleValue());
    }

    /*
     * z1 + z2
     */
    public final BigIntNumComplex plus(BigIntNumComplex z) {

        return new BigIntNumComplex(re.add(z.re), im.add(z.im));

    }

    /*
     *  z + Real
     */
    public final BigIntNumComplex plus(BigIntNum number) {

        return new BigIntNumComplex(re.add(number), im);

    }

    @Override
    public final BigIntNumComplex plus(int number) {

        return new BigIntNumComplex(re.add(number), im);

    }

    public final BigIntNumComplex plus(double number) {

        return new BigIntNumComplex(re.add(number), im);

    }

    /*
     *  z + Imaginary
     */
    public final BigIntNumComplex plus_i(BigIntNum number) {

        return new BigIntNumComplex(re, im.add(number));

    }

    /*
     *  z1 - z2
     */
    public final BigIntNumComplex sub(BigIntNumComplex z) {

        return  new BigIntNumComplex(re.sub(z.re), im.sub(z.im));

    }

    /*
     *  z - Real
     */
    public final BigIntNumComplex sub(BigIntNum number) {

        return  new BigIntNumComplex(re.sub(number), im);

    }

    @Override
    public final BigIntNumComplex sub(int number) {

        return  new BigIntNumComplex(re.sub(number), im);

    }

    public final BigIntNumComplex sub(double number) {

        return  new BigIntNumComplex(re.sub(number), im);

    }

    /*
     *  z - Imaginary
     */
    public final BigIntNumComplex sub_i(BigIntNum number) {

        return new BigIntNumComplex(re, im.sub(number));

    }

    public final BigIntNumComplex sub_i(int number) {

        return new BigIntNumComplex(re, im.sub(number));

    }

    public final BigIntNumComplex sub_i(double number) {

        return new BigIntNumComplex(re, im.sub(number));

    }

    /*
     *  Real - z1
     */
    public final BigIntNumComplex r_sub(BigIntNum number) {

        return  new BigIntNumComplex(number.sub(re), im.negate());

    }

    /*
     *  Real - z1
     */
    @Override
    public final BigIntNumComplex r_sub(int number) {

        return  new BigIntNumComplex(new BigIntNum(number).sub(re), im.negate());

    }

    public final BigIntNumComplex r_sub(double number) {

        return  new BigIntNumComplex(new BigIntNum(number).sub(re), im.negate());

    }

    /*
     *  Imaginary - z
     */
    public final BigIntNumComplex i_sub(BigIntNum number) {

        return  new BigIntNumComplex(re.negate(), number.sub(im));

    }

    /*
     *  z1 * z2
     */
    public final BigIntNumComplex times(BigIntNumComplex z) {

        //return new BigNumComplex(re.mult(z.re).sub(im.mult(z.im)),  re.mult(z.im).add(im.mult(z.re)));
        BigIntNum ac = re.mult(z.re);
        BigIntNum bd = im.mult(z.im);
        return new BigIntNumComplex(ac.sub(bd), re.add(im).mult(z.re.add(z.im)).sub(ac).sub(bd));

    }

    /*
     *  z1 * Real
     */
    public final BigIntNumComplex times(BigIntNum number) {

        return new BigIntNumComplex(re.mult(number), im.mult(number));

    }

    /*
     *  z1 * Real
     */
    @Override
    public final BigIntNumComplex times(int number) {

        return new BigIntNumComplex(re.mult(number), im.mult(number));

    }

    public final BigIntNumComplex times(double number) {

        return new BigIntNumComplex(re.mult(number), im.mult(number));

    }

    /*
     *  z * Imaginary
     */
    public final BigIntNumComplex times_i(BigIntNum number) {

        return new BigIntNumComplex(im.negate().mult(number), re.mult(number));

    }

    public final BigIntNumComplex divide(BigIntNumComplex z) {

        BigIntNum temp = z.re;
        BigIntNum temp2 = z.im;
        BigIntNum temp3 = temp.square().add(temp2.square()).reciprocal();

        return new BigIntNumComplex(re.mult(temp).add(im.mult(temp2)).mult(temp3), im.mult(temp).sub(re.mult(temp2)).mult(temp3));

    }

    @Override
    public final BigIntNumComplex divide(GenericComplex zn) {

        BigIntNumComplex z = (BigIntNumComplex)zn;
        BigIntNum temp = z.re;
        BigIntNum temp2 = z.im;
        BigIntNum temp3 = temp.square().add(temp2.square()).reciprocal();

        return new BigIntNumComplex(re.mult(temp).add(im.mult(temp2)).mult(temp3), im.mult(temp).sub(re.mult(temp2)).mult(temp3));

    }

    public final BigIntNumComplex divide(double number) {

        BigIntNum temp = new BigIntNum(number).reciprocal();
        return new BigIntNumComplex(re.mult(temp), im.mult(temp));

    }

    public final BigIntNumComplex divide(BigIntNum number) {

        BigIntNum temp = number.reciprocal();
        return new BigIntNumComplex(re.mult(temp), im.mult(temp));

    }

    @Override
    public final BigIntNumComplex divide(int number) {

        BigIntNum temp = new BigIntNum(number).reciprocal();
        return new BigIntNumComplex(re.mult(temp), im.mult(temp));

    }

    public final BigIntNumComplex divide_i(double number) {

        BigIntNum temp3 = new BigIntNum(number).square().reciprocal();

        return new BigIntNumComplex(re.add(im.mult(number)).mult(temp3), im.sub(re.mult(number)).mult(temp3));

    }

    public final BigIntNumComplex r_divide(double number) {

        BigIntNum temp = new BigIntNum(number).divide(re.square().add(im.square()));

        return new BigIntNumComplex(re.mult(temp), im.negate().mult(temp));

    }

    public final BigIntNumComplex i_divide(double number) {

        BigIntNum temp = new BigIntNum(number).divide(re.square().add(im.square()));

        return new BigIntNumComplex(im.mult(temp), re.mult(temp));

    }

    public final BigIntNumComplex reciprocal() {

        BigIntNum temp = re.square().add(im.square()).reciprocal();

        return new BigIntNumComplex(re.mult(temp), im.negate().mult(temp));

    }

    /*
     *  z^2
     */
    @Override
    public final BigIntNumComplex square() {
        return new BigIntNumComplex(re.add(im).mult(re.sub(im)), re.mult(im).mult2());
    }

    /*
     *  z^2 + c
     */
    public final BigIntNumComplex squareFast_plus_c(NormComponents normComponents, GenericComplex ca) {
        BigIntNum reSqr = (BigIntNum) normComponents.reSqr;
        BigIntNum imSqr = (BigIntNum) normComponents.imSqr;
        BigIntNum normSquared = (BigIntNum) normComponents.normSquared;
        BigIntNumComplex c = (BigIntNumComplex) ca;
        return new BigIntNumComplex(reSqr.sub(imSqr).add(c.re), re.add(im).square().sub(normSquared).add(c.im));
    }

    /*
     *  z^2
     */
    @Override
    public final BigIntNumComplex squareFast(NormComponents normComponents) {
        BigIntNum reSqr = (BigIntNum) normComponents.reSqr;
        BigIntNum imSqr = (BigIntNum) normComponents.imSqr;
        BigIntNum normSquared = (BigIntNum) normComponents.normSquared;
        return new BigIntNumComplex(reSqr.sub(imSqr), re.add(im).square().sub(normSquared));
    }

    /*
     *  z^3
     */
    public final BigIntNumComplex cubeFast(NormComponents normComponents) {

        BigIntNum temp = (BigIntNum)normComponents.reSqr;
        BigIntNum temp2 = (BigIntNum)normComponents.imSqr;

        return new BigIntNumComplex(re.mult(temp.sub(temp2.mult(3))), im.mult(temp.mult(3).sub(temp2)));

    }

    /*
     *  z^4
     */
    public final BigIntNumComplex fourthFast(NormComponents normComponents) {

        BigIntNum temp = (BigIntNum)normComponents.reSqr;
        BigIntNum temp2 = (BigIntNum)normComponents.imSqr;

        return new BigIntNumComplex(temp.mult(temp.sub(temp2.mult(6))).add(temp2.square()), re.mult(im).mult4().mult(temp.sub(temp2)));

    }

    /*
     *  z^5
     */
    public final BigIntNumComplex fifthFast(NormComponents normComponents) {

        BigIntNum temp = (BigIntNum)normComponents.reSqr;
        BigIntNum temp2 = (BigIntNum)normComponents.imSqr;

        return  new BigIntNumComplex(re.mult(temp.square().add(temp2.mult(temp2.mult(5).sub(temp.mult(10))))), im.mult(temp2.square().add(temp.mult(temp.mult(5).sub(temp2.mult(10))))));

    }

    @Override
    public NormComponents normSquaredWithComponents(NormComponents n) {
        BigIntNum reSqr = re.square();
        BigIntNum imSqr = im.square();
        return new NormComponents(reSqr, imSqr, reSqr.add(imSqr));
    }

    /*
     *  |z|^2
     */
    public final BigIntNum norm_squared() {

        if(BigIntNum.use_threads) {
            Future<BigIntNum> temp1 = TaskDraw.reference_thread_executor.submit(() -> re.square());
            Future<BigIntNum> temp2 = TaskDraw.reference_thread_executor.submit(() -> im.square());

            try {
                return temp1.get().add(temp2.get());
            }
            catch (Exception ex) {
                return new BigIntNum();
            }
        }
        else {
            return re.square().add(im.square());
        }

    }

    public final BigIntNum norm_squared_no_threads() {

        return re.square().add(im.square());

    }

    /*
     *  |z|
     */
    public final BigIntNum norm() {

        return re.square().add(im.square()).sqrt();

    }

    /*
     *  |z1 - z2|^2
     */
    public final BigIntNum distance_squared(BigIntNumComplex z) {

        BigIntNum temp_re = re.sub(z.re);
        BigIntNum temp_im = im.sub(z.im);
        return temp_re.square().add(temp_im.square());

    }

    public final BigIntNum distance_squared(BigIntNum val) {

        BigIntNum temp_re = re.sub(val);
        return temp_re.square().add(im.square());

    }

    /*
     *  |z1 - z2|
     */
    public final BigIntNum distance(BigIntNumComplex z) {

        BigIntNum temp_re = re.sub(z.re);
        BigIntNum temp_im = im.sub(z.im);
        return temp_re.square().add(temp_im.square()).sqrt();

    }

    public final BigIntNum distance(BigIntNum val) {

        BigIntNum temp_re = re.sub(val);
        return temp_re.square().add(im.square()).sqrt();

    }

    /*
     *  |Real|
     */
    public final BigIntNum getAbsRe() {

        return re.abs();

    }

    /*
     *  |Imaginary|
     */
    public final BigIntNum getAbsIm() {

        return im.abs();

    }

    /*
     *  |Re(z)| + Im(z)i
     */
    public final BigIntNumComplex absre() {

        return new BigIntNumComplex(re.abs(), im);

    }

    /*
     *  Re(z) + |Im(z)|i
     */
    public final BigIntNumComplex absim() {

        return new BigIntNumComplex(re, im.abs());

    }

    /*
     *  Real -Imaginary i
     */
    public final BigIntNumComplex conjugate() {

        return new BigIntNumComplex(re, im.negate());

    }

    /*
     *  -z
     */
    @Override
    public final BigIntNumComplex negative() {

        return new BigIntNumComplex(re.negate(), im.negate());

    }

    /*
     *  z^3
     */
    @Override
    public final BigIntNumComplex cube() {

        BigIntNum temp = re.square();
        BigIntNum temp2 = im.square();

        return new BigIntNumComplex(re.mult(temp.sub(temp2.mult(3))), im.mult(temp.mult(3).sub(temp2)));

    }

    /*
     *  z^4
     */
    @Override
    public final BigIntNumComplex fourth() {

        BigIntNum temp = re.square();
        BigIntNum temp2 = im.square();

        return new BigIntNumComplex(temp.mult(temp.sub(temp2.mult(6))).add(temp2.square()), re.mult(im).mult4().mult(temp.sub(temp2)));

    }

    /*
     *  z^5
     */
    @Override
    public final BigIntNumComplex fifth() {

        BigIntNum temp = re.square();
        BigIntNum temp2 = im.square();

        return  new BigIntNumComplex(re.mult(temp.square().add(temp2.mult(temp2.mult(5).sub(temp.mult(10))))), im.mult(temp2.square().add(temp.mult(temp.mult(5).sub(temp2.mult(10))))));

    }


    /*
     *  abs(z)
     */
    public final BigIntNumComplex abs() {

        return new BigIntNumComplex(re.abs(), im.abs());

    }

    /* more efficient z^2 + c */
    public final BigIntNumComplex square_plus_c(BigIntNumComplex c) {

        return new BigIntNumComplex(re.add(im).mult(re.sub(im)).add(c.re), re.mult(im).mult2().add(c.im));

    }

    @Override
    public final String toString() {

        String temp = "";

        if(im.isPositive()) {
            temp = re.doubleValue() + "+" + im.doubleValue() + "i";
        }
        else if (im.isZero()) {
            temp = re.doubleValue() + "+" + (0.0) + "i";
        } else {
            temp = re.doubleValue() + "" + im.doubleValue() + "i";
        }

        return temp;

    }

    /*
     *  z1 - z2
     */
    @Override
    public final BigIntNumComplex sub(GenericComplex zn) {
        BigIntNumComplex z = (BigIntNumComplex)zn;

        return new BigIntNumComplex(re.sub(z.re), im.sub(z.im));

    }

    /*
     * z1 + z2
     */
    @Override
    public final BigIntNumComplex plus(GenericComplex zn) {

        BigIntNumComplex z = (BigIntNumComplex)zn;
        return new BigIntNumComplex(re.add(z.re), im.add(z.im));

    }

    /* more efficient z^2 + c */
    public final BigIntNumComplex square_plus_c(GenericComplex cn) {

        BigIntNumComplex c = (BigIntNumComplex)cn;

        if(BigIntNum.use_threads) {
            Future<BigIntNum> temp1 = TaskDraw.reference_thread_executor.submit(() -> re.add(im).mult(re.sub(im)).add(c.re));
            Future<BigIntNum> temp2 = TaskDraw.reference_thread_executor.submit(() -> re.mult(im).mult2().add(c.im));

            try {
                return new BigIntNumComplex(temp1.get(), temp2.get());
            }
            catch (Exception ex) {
                return new BigIntNumComplex();
            }
        }
        else {
            return new BigIntNumComplex(re.add(im).mult(re.sub(im)).add(c.re), re.mult(im).mult2().add(c.im));
        }

    }

    public final BigIntNumComplex square_plus_c_no_threads(GenericComplex cn) {

        BigIntNumComplex c = (BigIntNumComplex)cn;

        return new BigIntNumComplex(re.add(im).mult(re.sub(im)).add(c.re), re.mult(im).mult2().add(c.im));

    }

    /*
     *  z1 * z2
     */
    @Override
    public final BigIntNumComplex times(GenericComplex zn) {

        BigIntNumComplex z = (BigIntNumComplex)zn;
        //return new BigNumComplex(re.mult(z.re).sub(im.mult(z.im)),  re.mult(z.im).add(im.mult(z.re)));
        BigIntNum ac = re.mult(z.re);
        BigIntNum bd = im.mult(z.im);
        return new BigIntNumComplex(ac.sub(bd), re.add(im).mult(z.re.add(z.im)).sub(ac).sub(bd));

    }

    /*
     *  lexicographical comparison between two complex numbers
     * -1 when z1 > z2
     *  1 when z1 < z2
     *  0 when z1 == z2
     */
    public final int compare(BigIntNumComplex z2) {

        int comparisonRe = re.compare(z2.re);
        int comparisonIm = im.compare(z2.im);

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

        BigIntNumComplex z2 = (BigIntNumComplex)z2c;

        int comparisonRe = re.compare(z2.re);
        int comparisonIm = im.compare(z2.im);

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

    public final BigIntNumComplex circle_inversion(BigIntNumComplex center, BigIntNum radius2) {

        BigIntNum distance = this.distance_squared(center);

        BigIntNum temp = radius2.divide(distance);

        return new BigIntNumComplex(center.re.add(re.sub(center.re).mult(temp)), center.im.add(im.sub(center.im).mult(temp)));

    }

    public final BigIntNumComplex fold_out(BigIntNumComplex z2) {

        BigIntNum norm_sqr = re.square().add(im.square());

        return norm_sqr.compare(z2.norm_squared()) > 0 ? this.divide(norm_sqr) : this;

    }

    public final BigIntNumComplex fold_in(BigIntNumComplex z2) {

        BigIntNum norm_sqr = re.square().add(im.square());

        return norm_sqr.compare(z2.norm_squared()) < 0 ? this.divide(norm_sqr) : this;

    }

    public final BigIntNumComplex fold_right(BigIntNumComplex z2) {

        return re.compare(z2.re) < 0 ? new BigIntNumComplex(re.add(z2.re.sub(re).mult2()), im) : this;

    }

    public final BigIntNumComplex fold_left(BigIntNumComplex z2) {

        return re.compare(z2.re) > 0 ? new BigIntNumComplex(re.sub(re.sub(z2.re).mult2()), im) : this;

    }

    public final BigIntNumComplex fold_up(BigIntNumComplex z2) {

        return im.compare(z2.im) < 0 ? new BigIntNumComplex(re, im.add(z2.im.sub(im).mult2())) : this;

    }

    public final BigIntNumComplex fold_down(BigIntNumComplex z2) {

        return im.compare(z2.im) > 0 ? new BigIntNumComplex(re, im.sub(im.sub(z2.im).mult2())) : this;

    }

    public final BigIntNumComplex inflection(BigIntNumComplex inf) {

        BigIntNumComplex diff = this.sub(inf);

        return inf.plus(diff.square());

    }

    public final BigIntNumComplex inflectionPower(BigIntNumComplex inf, double power) {

        if(power == 1) {
            return plus(inf);
        }
        else if(power == 2) {
            return square().plus(inf);
        }
        else if(power == 3) {
            return cube().plus(inf);
        }
        else if(power == 4) {
            return fourth().plus(inf);
        }
        if(power == 5) {
            return fifth().plus(inf);
        }

        return new BigIntNumComplex();

    }

    public final BigIntNumComplex shear(BigIntNumComplex sh) {

        return new BigIntNumComplex(re.add(im.mult(sh.re)), im.add(re.mult(sh.im)));

    }

    /*
     * y + xi
     */
    public final BigIntNumComplex flip() {

        return new BigIntNumComplex(im, re);

    }

    /*
     *  |z|^2
     */
    @Override
    public final BigIntNum normSquared() {

        return re.square().add(im.square());

    }

    /*
     *  |z1 - z2|^2
     */
    @Override
    public final BigIntNum distanceSquared(GenericComplex za) {
        BigIntNumComplex z = (BigIntNumComplex) za;

        BigIntNum temp_re = re.sub(z.re);
        BigIntNum temp_im = im.sub(z.im);
        return temp_re.square().add(temp_im.square());

    }

    @Override
    public BigIntNumComplex times2() {
        return new BigIntNumComplex(re.mult2(), im.mult2());
    }

    @Override
    public BigIntNumComplex times4() {
        return new BigIntNumComplex(re.mult4(), im.mult4());
    }

    @Override
    public MantExpComplex toMantExpComplex() { return MantExpComplex.create(this);}


    @Override
    public void set(GenericComplex za) {
        BigIntNumComplex z = (BigIntNumComplex) za;
        re = z.re;
        im = z.im;
    }

    @Override
    public GenericComplex sub_mutable(int a) {return sub(a);}

    @Override
    public GenericComplex plus_mutable(int a) {return plus(a);}

    @Override
    public GenericComplex times_mutable(GenericComplex a) {return times(a);}

    @Override
    public GenericComplex plus_mutable(GenericComplex v) { return plus(v); }

    @Override
    public GenericComplex divide_mutable(GenericComplex z) { return divide(z); }

    @Override
    public GenericComplex abs_mutable() { return abs(); }

    public boolean isZero() {
        return re.isZero() && im.isZero();
    }

    public boolean isOne() {
        return re.isOne() && im.isZero();
    }

    /*
     *  z^3
     */
    @Override
    public final BigIntNumComplex squareFast_mutable(NormComponents normComponents) {

        return squareFast(normComponents);

    }

    /*
     *  z^3
     */
    @Override
    public final BigIntNumComplex cubeFast_mutable(NormComponents normComponents) {

        return cubeFast(normComponents);

    }

    /*
     *  z^4
     */
    @Override
    public final BigIntNumComplex fourthFast_mutable(NormComponents normComponents) {

        return fourthFast(normComponents);

    }

    /*
     *  z^5
     */
    @Override
    public final BigIntNumComplex fifthFast_mutable(NormComponents normComponents) {

        return fifthFast(normComponents);

    }

    /*
     *  z^2 + c
     */
    @Override
    public final BigIntNumComplex squareFast_plus_c_mutable(NormComponents normComponents, GenericComplex ca) {
        return squareFast_plus_c(normComponents, ca);
    }

    /* more efficient z^2 + c */
    @Override
    public final BigIntNumComplex square_plus_c_mutable(GenericComplex cn) {

        return square_plus_c(cn);

    }

    @Override
    public final BigIntNumComplex square_plus_c_mutable_no_threads(GenericComplex cn) {

        return square_plus_c_no_threads(cn);

    }

    /*
     *  Real -Imaginary i
     */
    @Override
    public final BigIntNumComplex conjugate_mutable() {

        return conjugate();

    }

    @Override
    public final BigIntNumComplex times_mutable(int number) {

        return times(number);

    }

    @Override
    public GenericComplex sub_mutable(GenericComplex v) { return sub(v); }

    @Override
    public BigIntNumComplex times2_mutable() {
        return times2();
    }

    @Override
    public BigIntNumComplex negative_mutable() {

        return negative();

    }

    @Override
    public BigIntNumComplex square_mutable() {

        return square();

    }

    @Override
    public BigIntNumComplex absNegateRe_mutable() {
        return new BigIntNumComplex(re.abs().negate(), im);
    }

    @Override
    public BigIntNumComplex absNegateIm_mutable() {
        return new BigIntNumComplex(re, im.abs().negate());
    }

    @Override
    public BigIntNumComplex absre_mutable() { return  absre(); }
}
