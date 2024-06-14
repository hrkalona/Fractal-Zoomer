package fractalzoomer.core.unused;

import fractalzoomer.core.*;
import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

import java.util.concurrent.Future;

public class BigDecNumComplex extends GenericComplex {
    private BigDecNum re;
    private BigDecNum im;

    public BigDecNumComplex(BigComplex c) {
        re = new BigDecNum(c.getRe());
        im = new BigDecNum(c.getIm());
    }

    public BigDecNumComplex(double re, double im) {
        this.re = new BigDecNum(re);
        this.im = new BigDecNum(im);
    }

    public BigDecNumComplex(BigDecNumComplex c) {
        re = new BigDecNum(c.getRe());
        im = new BigDecNum(c.getIm());
    }

    public BigDecNumComplex(Complex c) {
        re = new BigDecNum(c.getRe());
        im = new BigDecNum(c.getIm());
    }

    public BigDecNumComplex(BigDecNum re, BigDecNum im) {
       this.re = re;
       this.im = im;
    }

    public BigDecNumComplex(Apfloat re, Apfloat im) {
        this.re = new BigDecNum(re);
        this.im = new BigDecNum(im);
    }

    public BigDecNumComplex(String re, String im) {
        this.re = new BigDecNum(re);
        this.im = new BigDecNum(im);
    }

    public BigDecNumComplex() {

        re = new BigDecNum();
        im = new BigDecNum();

    }

    @Override
    public BigDecNumComplex toBigDecNumComplex() { return this; }

    public final BigDecNum getRe() {

        return re;

    }

    public final BigDecNum getIm() {

        return im;

    }

    public final void setRe(BigDecNum re) {

        this.re = re;

    }

    public final void setIm(BigDecNum im) {

        this.im = im;

    }

    @Override
    public final Complex toComplex() {
        return new Complex(re.doubleValue(), im.doubleValue());
    }

    /*
     * z1 + z2
     */
    public final BigDecNumComplex plus(BigDecNumComplex z) {

        return new BigDecNumComplex(re.add(z.re), im.add(z.im));

    }

    /*
     *  z + Real
     */
    public final BigDecNumComplex plus(BigDecNum number) {

        return new BigDecNumComplex(re.add(number), im);

    }

    @Override
    public final BigDecNumComplex plus(int number) {

        return new BigDecNumComplex(re.add(number), im);

    }

    public final BigDecNumComplex plus(double number) {

        return new BigDecNumComplex(re.add(number), im);

    }

    /*
     *  z + Imaginary
     */
    public final BigDecNumComplex plus_i(BigDecNum number) {

        return new BigDecNumComplex(re, im.add(number));

    }

    /*
     *  z1 - z2
     */
    public final BigDecNumComplex sub(BigDecNumComplex z) {

        return  new BigDecNumComplex(re.sub(z.re), im.sub(z.im));

    }

    /*
     *  z - Real
     */
    public final BigDecNumComplex sub(BigDecNum number) {

        return  new BigDecNumComplex(re.sub(number), im);

    }

    @Override
    public final BigDecNumComplex sub(int number) {

        return  new BigDecNumComplex(re.sub(number), im);

    }

    public final BigDecNumComplex sub(double number) {

        return  new BigDecNumComplex(re.sub(number), im);

    }

    /*
     *  z - Imaginary
     */
    public final BigDecNumComplex sub_i(BigDecNum number) {

        return new BigDecNumComplex(re, im.sub(number));

    }

    public final BigDecNumComplex sub_i(int number) {

        return new BigDecNumComplex(re, im.sub(number));

    }

    public final BigDecNumComplex sub_i(double number) {

        return new BigDecNumComplex(re, im.sub(number));

    }

    /*
     *  Real - z1
     */
    public final BigDecNumComplex r_sub(BigDecNum number) {

        return  new BigDecNumComplex(number.sub(re), im.negate());

    }

    /*
     *  Real - z1
     */
    @Override
    public final BigDecNumComplex r_sub(int number) {

        return  new BigDecNumComplex(new BigDecNum(number).sub(re), im.negate());

    }

    public final BigDecNumComplex r_sub(double number) {

        return  new BigDecNumComplex(new BigDecNum(number).sub(re), im.negate());

    }

    /*
     *  Imaginary - z
     */
    public final BigDecNumComplex i_sub(BigDecNum number) {

        return  new BigDecNumComplex(re.negate(), number.sub(im));

    }

    /*
     *  z1 * z2
     */
    public final BigDecNumComplex times(BigDecNumComplex z) {

        //return new BigNumComplex(re.mult(z.re).sub(im.mult(z.im)),  re.mult(z.im).add(im.mult(z.re)));
        BigDecNum ac = re.mult(z.re);
        BigDecNum bd = im.mult(z.im);
        return new BigDecNumComplex(ac.sub(bd), re.add(im).mult(z.re.add(z.im)).sub(ac).sub(bd));

    }

    /*
     *  z1 * Real
     */
    public final BigDecNumComplex times(BigDecNum number) {

        return new BigDecNumComplex(re.mult(number), im.mult(number));

    }

    /*
     *  z1 * Real
     */
    @Override
    public final BigDecNumComplex times(int number) {

        return new BigDecNumComplex(re.mult(number), im.mult(number));

    }

    public final BigDecNumComplex times(double number) {

        return new BigDecNumComplex(re.mult(number), im.mult(number));

    }

    /*
     *  z * Imaginary
     */
    public final BigDecNumComplex times_i(BigDecNum number) {

        return new BigDecNumComplex(im.negate().mult(number), re.mult(number));

    }

    public final BigDecNumComplex divide(BigDecNumComplex z) {

        BigDecNum temp = z.re;
        BigDecNum temp2 = z.im;
        BigDecNum temp3 = temp.square().add(temp2.square()).reciprocal();

        return new BigDecNumComplex(re.mult(temp).add(im.mult(temp2)).mult(temp3), im.mult(temp).sub(re.mult(temp2)).mult(temp3));

    }

    @Override
    public final BigDecNumComplex divide(GenericComplex zn) {

        BigDecNumComplex z = (BigDecNumComplex)zn;
        BigDecNum temp = z.re;
        BigDecNum temp2 = z.im;
        BigDecNum temp3 = temp.square().add(temp2.square()).reciprocal();

        return new BigDecNumComplex(re.mult(temp).add(im.mult(temp2)).mult(temp3), im.mult(temp).sub(re.mult(temp2)).mult(temp3));

    }

    public final BigDecNumComplex divide(double number) {

        BigDecNum temp = new BigDecNum(number).reciprocal();
        return new BigDecNumComplex(re.mult(temp), im.mult(temp));

    }

    public final BigDecNumComplex divide(BigDecNum number) {

        BigDecNum temp = number.reciprocal();
        return new BigDecNumComplex(re.mult(temp), im.mult(temp));

    }

    @Override
    public final BigDecNumComplex divide(int number) {

        BigDecNum temp = new BigDecNum(number).reciprocal();
        return new BigDecNumComplex(re.mult(temp), im.mult(temp));

    }

    public final BigDecNumComplex divide_i(double number) {

        BigDecNum temp3 = new BigDecNum(number).square().reciprocal();

        return new BigDecNumComplex(re.add(im.mult(number)).mult(temp3), im.sub(re.mult(number)).mult(temp3));

    }

    public final BigDecNumComplex r_divide(double number) {

        BigDecNum temp = new BigDecNum(number).divide(re.square().add(im.square()));

        return new BigDecNumComplex(re.mult(temp), im.negate().mult(temp));

    }

    public final BigDecNumComplex i_divide(double number) {

        BigDecNum temp = new BigDecNum(number).divide(re.square().add(im.square()));

        return new BigDecNumComplex(im.mult(temp), re.mult(temp));

    }

    @Override
    public final BigDecNumComplex reciprocal() {

        BigDecNum temp = re.square().add(im.square()).reciprocal();

        return new BigDecNumComplex(re.mult(temp), im.negate().mult(temp));

    }

    /*
     *  z^2
     */
    @Override
    public final BigDecNumComplex square() {
        return new BigDecNumComplex(re.add(im).mult(re.sub(im)), re.mult(im).mult2());
    }

    /*
     *  z^2 + c
     */
    public final BigDecNumComplex squareFast_plus_c(NormComponents normComponents, GenericComplex ca) {
        BigDecNum reSqr = (BigDecNum) normComponents.reSqr;
        BigDecNum imSqr = (BigDecNum) normComponents.imSqr;
        BigDecNum normSquared = (BigDecNum) normComponents.normSquared;
        BigDecNumComplex c = (BigDecNumComplex) ca;
        return new BigDecNumComplex(reSqr.sub(imSqr).add(c.re), re.add(im).square().sub(normSquared).add(c.im));
    }

    /*
     *  z^2
     */
    @Override
    public final BigDecNumComplex squareFast(NormComponents normComponents) {
        BigDecNum reSqr = (BigDecNum) normComponents.reSqr;
        BigDecNum imSqr = (BigDecNum) normComponents.imSqr;
        BigDecNum normSquared = (BigDecNum) normComponents.normSquared;
        return new BigDecNumComplex(reSqr.sub(imSqr), re.add(im).square().sub(normSquared));
    }

    /*
     *  z^3
     */
    public final BigDecNumComplex cubeFast(NormComponents normComponents) {

        BigDecNum temp = (BigDecNum)normComponents.reSqr;
        BigDecNum temp2 = (BigDecNum)normComponents.imSqr;

        return new BigDecNumComplex(re.mult(temp.sub(temp2.mult(3))), im.mult(temp.mult(3).sub(temp2)));

    }

    /*
     *  z^4
     */
    public final BigDecNumComplex fourthFast(NormComponents normComponents) {

        BigDecNum temp = (BigDecNum)normComponents.reSqr;
        BigDecNum temp2 = (BigDecNum)normComponents.imSqr;

        return new BigDecNumComplex(temp.mult(temp.sub(temp2.mult(6))).add(temp2.square()), re.mult(im).mult(4).mult(temp.sub(temp2)));

    }

    /*
     *  z^5
     */
    public final BigDecNumComplex fifthFast(NormComponents normComponents) {

        BigDecNum temp = (BigDecNum)normComponents.reSqr;
        BigDecNum temp2 = (BigDecNum)normComponents.imSqr;

        return  new BigDecNumComplex(re.mult(temp.square().add(temp2.mult(temp2.mult(5).sub(temp.mult(10))))), im.mult(temp2.square().add(temp.mult(temp.mult(5).sub(temp2.mult(10))))));

    }

    @Override
    public NormComponents normSquaredWithComponents(NormComponents n) {
        BigDecNum reSqr = re.square();
        BigDecNum imSqr = im.square();
        return new NormComponents(reSqr, imSqr, reSqr.add(imSqr));
    }

    /*
     *  |z|^2
     */
    public final BigDecNum norm_squared() {

        if(BigDecNum.use_threads) {
            Future<BigDecNum> temp1 = TaskRender.reference_thread_executor.submit(() -> re.square());
            Future<BigDecNum> temp2 = TaskRender.reference_thread_executor.submit(() -> im.square());

            try {
                return temp1.get().add(temp2.get());
            }
            catch (Exception ex) {
                return new BigDecNum();
            }
        }
        else {
            return re.square().add(im.square());
        }

    }

    public final BigDecNum norm_squared_no_threads() {

        return re.square().add(im.square());

    }

    /*
     *  |z|
     */
//    public final BigIntNum norm() {
//
//        return re.square().add(im.square()).sqrt();
//
//    }

    /*
     *  |z1 - z2|^2
     */
    public final BigDecNum distance_squared(BigDecNumComplex z) {

        BigDecNum temp_re = re.sub(z.re);
        BigDecNum temp_im = im.sub(z.im);
        return temp_re.square().add(temp_im.square());

    }

    public final BigDecNum distance_squared(BigDecNum val) {

        BigDecNum temp_re = re.sub(val);
        return temp_re.square().add(im.square());

    }

    /*
     *  |z1 - z2|
     */
//    public final BigDecNum distance(BigDecNumComplex z) {
//
//        BigDecNum temp_re = re.sub(z.re);
//        BigDecNum temp_im = im.sub(z.im);
//        return temp_re.square().add(temp_im.square()).sqrt();
//
//    }

//    public final BigDecNum distance(BigDecNum val) {
//
//        BigDecNum temp_re = re.sub(val);
//        return temp_re.square().add(im.square()).sqrt();
//
//    }

    /*
     *  |Real|
     */
    public final BigDecNum getAbsRe() {

        return re.abs();

    }

    /*
     *  |Imaginary|
     */
    public final BigDecNum getAbsIm() {

        return im.abs();

    }

    /*
     *  |Re(z)| + Im(z)i
     */
    public final BigDecNumComplex absre() {

        return new BigDecNumComplex(re.abs(), im);

    }

    /*
     *  Re(z) + |Im(z)|i
     */
    public final BigDecNumComplex absim() {

        return new BigDecNumComplex(re, im.abs());

    }

    /*
     *  Real -Imaginary i
     */
    public final BigDecNumComplex conjugate() {

        return new BigDecNumComplex(re, im.negate());

    }

    /*
     *  -z
     */
    @Override
    public final BigDecNumComplex negative() {

        return new BigDecNumComplex(re.negate(), im.negate());

    }

    /*
     *  z^3
     */
    @Override
    public final BigDecNumComplex cube() {

        BigDecNum temp = re.square();
        BigDecNum temp2 = im.square();

        return new BigDecNumComplex(re.mult(temp.sub(temp2.mult(3))), im.mult(temp.mult(3).sub(temp2)));

    }

    /*
     *  z^4
     */
    @Override
    public final BigDecNumComplex fourth() {

        BigDecNum temp = re.square();
        BigDecNum temp2 = im.square();

        return new BigDecNumComplex(temp.mult(temp.sub(temp2.mult(6))).add(temp2.square()), re.mult(im).mult(4).mult(temp.sub(temp2)));

    }

    /*
     *  z^5
     */
    @Override
    public final BigDecNumComplex fifth() {

        BigDecNum temp = re.square();
        BigDecNum temp2 = im.square();

        return  new BigDecNumComplex(re.mult(temp.square().add(temp2.mult(temp2.mult(5).sub(temp.mult(10))))), im.mult(temp2.square().add(temp.mult(temp.mult(5).sub(temp2.mult(10))))));

    }


    /*
     *  abs(z)
     */
    public final BigDecNumComplex abs() {

        return new BigDecNumComplex(re.abs(), im.abs());

    }

    /* more efficient z^2 + c */
    public final BigDecNumComplex square_plus_c(BigDecNumComplex c) {

        return new BigDecNumComplex(re.add(im).mult(re.sub(im)).add(c.re), re.mult(im).mult2().add(c.im));

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
    public final BigDecNumComplex sub(GenericComplex zn) {
        BigDecNumComplex z = (BigDecNumComplex)zn;

        return new BigDecNumComplex(re.sub(z.re), im.sub(z.im));

    }

    /*
     * z1 + z2
     */
    @Override
    public final BigDecNumComplex plus(GenericComplex zn) {

        BigDecNumComplex z = (BigDecNumComplex)zn;
        return new BigDecNumComplex(re.add(z.re), im.add(z.im));

    }

    /* more efficient z^2 + c */
    public final BigDecNumComplex square_plus_c(GenericComplex cn) {

        BigDecNumComplex c = (BigDecNumComplex)cn;

        if(BigDecNum.use_threads) {
            Future<BigDecNum> temp1 = TaskRender.reference_thread_executor.submit(() -> re.add(im).mult(re.sub(im)).add(c.re));
            Future<BigDecNum> temp2 = TaskRender.reference_thread_executor.submit(() -> re.mult(im).mult2().add(c.im));

            try {
                return new BigDecNumComplex(temp1.get(), temp2.get());
            }
            catch (Exception ex) {
                return new BigDecNumComplex();
            }
        }
        else {
            return new BigDecNumComplex(re.add(im).mult(re.sub(im)).add(c.re), re.mult(im).mult2().add(c.im));
        }

    }

    public final BigDecNumComplex square_plus_c_no_threads(GenericComplex cn) {

        BigDecNumComplex c = (BigDecNumComplex)cn;

        return new BigDecNumComplex(re.add(im).mult(re.sub(im)).add(c.re), re.mult(im).mult2().add(c.im));

    }

    /*
     *  z1 * z2
     */
    @Override
    public final BigDecNumComplex times(GenericComplex zn) {

        BigDecNumComplex z = (BigDecNumComplex)zn;
        //return new BigNumComplex(re.mult(z.re).sub(im.mult(z.im)),  re.mult(z.im).add(im.mult(z.re)));
        BigDecNum ac = re.mult(z.re);
        BigDecNum bd = im.mult(z.im);
        return new BigDecNumComplex(ac.sub(bd), re.add(im).mult(z.re.add(z.im)).sub(ac).sub(bd));

    }

    /*
     *  lexicographical comparison between two complex numbers
     * -1 when z1 > z2
     *  1 when z1 < z2
     *  0 when z1 == z2
     */
    public final int compare(BigDecNumComplex z2) {

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

        BigDecNumComplex z2 = (BigDecNumComplex)z2c;

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

    public final BigDecNumComplex circle_inversion(BigDecNumComplex center, BigDecNum radius2) {

        BigDecNum distance = this.distance_squared(center);

        BigDecNum temp = radius2.divide(distance);

        return new BigDecNumComplex(center.re.add(re.sub(center.re).mult(temp)), center.im.add(im.sub(center.im).mult(temp)));

    }

    public final BigDecNumComplex fold_out(BigDecNumComplex z2) {

        BigDecNum norm_sqr = re.square().add(im.square());

        return norm_sqr.compare(z2.norm_squared()) > 0 ? this.divide(norm_sqr) : this;

    }

    public final BigDecNumComplex fold_in(BigDecNumComplex z2) {

        BigDecNum norm_sqr = re.square().add(im.square());

        return norm_sqr.compare(z2.norm_squared()) < 0 ? this.divide(norm_sqr) : this;

    }

    public final BigDecNumComplex fold_right(BigDecNumComplex z2) {

        return re.compare(z2.re) < 0 ? new BigDecNumComplex(re.add(z2.re.sub(re).mult2()), im) : this;

    }

    public final BigDecNumComplex fold_left(BigDecNumComplex z2) {

        return re.compare(z2.re) > 0 ? new BigDecNumComplex(re.sub(re.sub(z2.re).mult2()), im) : this;

    }

    public final BigDecNumComplex fold_up(BigDecNumComplex z2) {

        return im.compare(z2.im) < 0 ? new BigDecNumComplex(re, im.add(z2.im.sub(im).mult2())) : this;

    }

    public final BigDecNumComplex fold_down(BigDecNumComplex z2) {

        return im.compare(z2.im) > 0 ? new BigDecNumComplex(re, im.sub(im.sub(z2.im).mult2())) : this;

    }

    public final BigDecNumComplex inflection(BigDecNumComplex inf) {

        BigDecNumComplex diff = this.sub(inf);

        return inf.plus(diff.square());

    }

    public final BigDecNumComplex inflectionPower(BigDecNumComplex inf, double power) {

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

        return new BigDecNumComplex();

    }

    public final BigDecNumComplex shear(BigDecNumComplex sh) {

        return new BigDecNumComplex(re.add(im.mult(sh.re)), im.add(re.mult(sh.im)));

    }

    /*
     * y + xi
     */
    public final BigDecNumComplex flip() {

        return new BigDecNumComplex(im, re);

    }

    /*
     *  |z|^2
     */
    @Override
    public final BigDecNum normSquared() {

        return re.square().add(im.square());

    }

    /*
     *  |z1 - z2|^2
     */
    @Override
    public final BigDecNum distanceSquared(GenericComplex za) {
        BigDecNumComplex z = (BigDecNumComplex) za;

        BigDecNum temp_re = re.sub(z.re);
        BigDecNum temp_im = im.sub(z.im);
        return temp_re.square().add(temp_im.square());

    }

    @Override
    public BigDecNumComplex times2() {
        return new BigDecNumComplex(re.mult2(), im.mult2());
    }

    @Override
    public BigDecNumComplex times4() {
        return new BigDecNumComplex(re.mult(4), im.mult(4));
    }

    @Override
    public MantExpComplex toMantExpComplex() { return MantExpComplex.create(this);}


    @Override
    public void set(GenericComplex za) {
        BigDecNumComplex z = (BigDecNumComplex) za;
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
    public final BigDecNumComplex squareFast_mutable(NormComponents normComponents) {

        return squareFast(normComponents);

    }

    /*
     *  z^3
     */
    @Override
    public final BigDecNumComplex cubeFast_mutable(NormComponents normComponents) {

        return cubeFast(normComponents);

    }

    /*
     *  z^4
     */
    @Override
    public final BigDecNumComplex fourthFast_mutable(NormComponents normComponents) {

        return fourthFast(normComponents);

    }

    /*
     *  z^5
     */
    @Override
    public final BigDecNumComplex fifthFast_mutable(NormComponents normComponents) {

        return fifthFast(normComponents);

    }

    /*
     *  z^2 + c
     */
    @Override
    public final BigDecNumComplex squareFast_plus_c_mutable(NormComponents normComponents, GenericComplex ca) {
        return squareFast_plus_c(normComponents, ca);
    }

    /* more efficient z^2 + c */
    @Override
    public final BigDecNumComplex square_plus_c_mutable(GenericComplex cn) {

        return square_plus_c(cn);

    }

    @Override
    public final BigDecNumComplex square_plus_c_mutable_no_threads(GenericComplex cn) {

        return square_plus_c_no_threads(cn);

    }

    /*
     *  Real -Imaginary i
     */
    @Override
    public final BigDecNumComplex conjugate_mutable() {

        return conjugate();

    }

    @Override
    public final BigDecNumComplex times_mutable(int number) {

        return times(number);

    }

    @Override
    public GenericComplex sub_mutable(GenericComplex v) { return sub(v); }

    @Override
    public BigDecNumComplex times2_mutable() {
        return times2();
    }

    @Override
    public BigDecNumComplex negative_mutable() {

        return negative();

    }

    @Override
    public BigDecNumComplex square_mutable() {

        return square();

    }

    @Override
    public BigComplex toBigComplex() {
        return new BigComplex(re.toApfloat(), im.toApfloat());
    }

    @Override
    public Object re() {
        return getRe();
    }

    @Override
    public Object im() {
        return getIm();
    }
}
