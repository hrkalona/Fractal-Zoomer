package fractalzoomer.core;

import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

public class BigNumComplex extends GenericComplex {
    private BigNum re;
    private BigNum im;

    public BigNumComplex(BigComplex c) {
        re = new BigNum(c.getRe());
        im = new BigNum(c.getIm());
    }

    public BigNumComplex(double re, double im) {
        this.re = new BigNum(re);
        this.im = new BigNum(im);
    }

    public BigNumComplex(BigNumComplex c) {
        re = new BigNum(c.getRe());
        im = new BigNum(c.getIm());
    }

    public BigNumComplex(Complex c) {
        re = new BigNum(c.getRe());
        im = new BigNum(c.getIm());
    }

    public BigNumComplex(BigNum re, BigNum im) {
       this.re = re;
       this.im = im;
    }

    public BigNumComplex(Apfloat re, Apfloat im) {
        this.re = new BigNum(re);
        this.im = new BigNum(im);
    }

    public BigNumComplex(String re, String im) {
        this.re = new BigNum(re);
        this.im = new BigNum(im);
    }

    public BigNumComplex() {

        re = new BigNum();
        im = new BigNum();

    }

    @Override
    public BigNumComplex toBigNumComplex() { return this; }

    public final BigNum getRe() {

        return re;

    }

    public final BigNum getIm() {

        return im;

    }

    public final void setRe(BigNum re) {

        this.re = re;

    }

    public final void setIm(BigNum im) {

        this.im = im;

    }

    @Override
    public final Complex toComplex() {
        return new Complex(re.doubleValue(), im.doubleValue());
    }

    /*
     * z1 + z2
     */
    public final BigNumComplex plus(BigNumComplex z) {

        return new BigNumComplex(re.add(z.re), im.add(z.im));

    }

    /*
     *  z + Real
     */
    public final BigNumComplex plus(BigNum number) {

        return new BigNumComplex(re.add(number), im);

    }

    @Override
    public final BigNumComplex plus(int number) {

        return new BigNumComplex(re.add(number), im);

    }

    /*
     *  z + Imaginary
     */
    public final BigNumComplex plus_i(BigNum number) {

        return new BigNumComplex(re, im.add(number));

    }

    /*
     *  z1 - z2
     */
    public final BigNumComplex sub(BigNumComplex z) {

        return  new BigNumComplex(re.sub(z.re), im.sub(z.im));

    }

    /*
     *  z - Real
     */
    public final BigNumComplex sub(BigNum number) {

        return  new BigNumComplex(re.sub(number), im);

    }

    @Override
    public final BigNumComplex sub(int number) {

        return  new BigNumComplex(re.sub(number), im);

    }

    /*
     *  z - Imaginary
     */
    public final BigNumComplex sub_i(BigNum number) {

        return new BigNumComplex(re, im.sub(number));

    }

    /*
     *  Real - z1
     */
    public final BigNumComplex r_sub(BigNum number) {

        return  new BigNumComplex(number.sub(re), im.negate());

    }

    /*
     *  Real - z1
     */
    @Override
    public final BigNumComplex r_sub(int number) {

        return  new BigNumComplex(new BigNum(number).sub(re), im.negate());

    }

    /*
     *  Imaginary - z
     */
    public final BigNumComplex i_sub(BigNum number) {

        return  new BigNumComplex(re.negate(), number.sub(im));

    }

    /*
     *  z1 * z2
     */
    public final BigNumComplex times(BigNumComplex z) {

        //return new BigNumComplex(re.mult(z.re).sub(im.mult(z.im)),  re.mult(z.im).add(im.mult(z.re)));
        BigNum ac = re.mult(z.re);
        BigNum bd = im.mult(z.im);
        return new BigNumComplex(ac.sub(bd), re.add(im).mult(z.re.add(z.im)).sub(ac).sub(bd));

    }

    /*
     *  z1 * Real
     */
    public final BigNumComplex times(BigNum number) {

        return new BigNumComplex(re.mult(number), im.mult(number));

    }

    /*
     *  z1 * Real
     */
    @Override
    public final BigNumComplex times(int number) {

        return new BigNumComplex(re.mult(number), im.mult(number));

    }

    /*
     *  z * Imaginary
     */
    public final BigNumComplex times_i(BigNum number) {

        return new BigNumComplex(im.negate().mult(number), re.mult(number));

    }

    /*
     *  z^2
     */
    @Override
    public final BigNumComplex square() {
        BigNum temp = re.mult(im);
        return new BigNumComplex(re.add(im).mult(re.sub(im)), temp.mult2());
    }

    /*
     *  z^2 + c
     */
    public final BigNumComplex squareFast_plus_c(NormComponents normComponents, GenericComplex ca) {
        BigNum reSqr = (BigNum) normComponents.reSqr;
        BigNum imSqr = (BigNum) normComponents.imSqr;
        BigNum normSquared = (BigNum) normComponents.normSquared;
        BigNumComplex c = (BigNumComplex) ca;
        return new BigNumComplex(reSqr.sub(imSqr).add(c.re), re.add(im).squareFull().sub(normSquared).add(c.im));
    }

    /*
     *  z^2
     */
    @Override
    public final BigNumComplex squareFast(NormComponents normComponents) {
        BigNum reSqr = (BigNum) normComponents.reSqr;
        BigNum imSqr = (BigNum) normComponents.imSqr;
        BigNum normSquared = (BigNum) normComponents.normSquared;
        return new BigNumComplex(reSqr.sub(imSqr), re.add(im).squareFull().sub(normSquared));
    }

    /*
     *  z^3
     */
    public final BigNumComplex cubeFast(NormComponents normComponents) {

        BigNum temp = (BigNum)normComponents.reSqr;
        BigNum temp2 = (BigNum)normComponents.imSqr;

        return new BigNumComplex(re.mult(temp.sub(temp2.mult(3))), im.mult(temp.mult(3).sub(temp2)));

    }

    /*
     *  z^4
     */
    public final BigNumComplex fourthFast(NormComponents normComponents) {

        BigNum temp = (BigNum)normComponents.reSqr;
        BigNum temp2 = (BigNum)normComponents.imSqr;

        return new BigNumComplex(temp.mult(temp.sub(temp2.mult(6))).add(temp2.squareFull()), re.mult(im).mult4().mult(temp.sub(temp2)));

    }

    /*
     *  z^5
     */
    public final BigNumComplex fifthFast(NormComponents normComponents) {

        BigNum temp = (BigNum)normComponents.reSqr;
        BigNum temp2 = (BigNum)normComponents.imSqr;

        return  new BigNumComplex(re.mult(temp.squareFull().add(temp2.mult(temp2.mult(5).sub(temp.mult(10))))), im.mult(temp2.squareFull().add(temp.mult(temp.mult(5).sub(temp2.mult(10))))));

    }

    @Override
    public NormComponents normSquaredWithComponents(NormComponents n) {
        BigNum reSqr = re.square();
        BigNum imSqr = im.square();
        return new NormComponents(reSqr, imSqr, reSqr.add(imSqr));
    }

    /*
     *  |z|^2
     */
    public final BigNum norm_squared() {

        return re.square().add(im.square());
        //return re.mult(re).add(im.mult(im));

    }

    /*
     *  |z|
     */
    public final BigNum norm() {

        return re.square().add(im.square()).sqrt();

    }

    /*
     *  |z1 - z2|^2
     */
    public final BigNum distance_squared(BigNumComplex z) {

        BigNum temp_re = re.sub(z.re);
        BigNum temp_im = im.sub(z.im);
        return temp_re.squareFull().add(temp_im.squareFull());

    }

    /*
     *  |z1 - z2|
     */
    public final BigNum distance(BigNumComplex z) {

        BigNum temp_re = re.sub(z.re);
        BigNum temp_im = im.sub(z.im);
        return temp_re.squareFull().add(temp_im.squareFull()).sqrt();

    }

    /*
     *  |Real|
     */
    public final BigNum getAbsRe() {

        return re.abs();

    }

    /*
     *  |Imaginary|
     */
    public final BigNum getAbsIm() {

        return im.abs();

    }

    /*
     *  |Re(z)| + Im(z)i
     */
    public final BigNumComplex absre() {

        return new BigNumComplex(re.abs(), im);

    }

    /*
     *  Re(z) + |Im(z)|i
     */
    public final BigNumComplex absim() {

        return new BigNumComplex(re, im.abs());

    }

    /*
     *  Real -Imaginary i
     */
    public final BigNumComplex conjugate() {

        return new BigNumComplex(re, im.negate());

    }

    /*
     *  -z
     */
    @Override
    public final BigNumComplex negative() {

        return new BigNumComplex(re.negate(), im.negate());

    }

    /*
     *  z^3
     */
    @Override
    public final BigNumComplex cube() {

        BigNum temp = re.square();
        BigNum temp2 = im.square();

        return new BigNumComplex(re.mult(temp.sub(temp2.mult(3))), im.mult(temp.mult(3).sub(temp2)));

    }

    /*
     *  z^4
     */
    @Override
    public final BigNumComplex fourth() {

        BigNum temp = re.square();
        BigNum temp2 = im.square();

        return new BigNumComplex(temp.mult(temp.sub(temp2.mult(6))).add(temp2.squareFull()), re.mult(im).mult4().mult(temp.sub(temp2)));

    }

    /*
     *  z^5
     */
    @Override
    public final BigNumComplex fifth() {

        BigNum temp = re.square();
        BigNum temp2 = im.square();

        return  new BigNumComplex(re.mult(temp.squareFull().add(temp2.mult(temp2.mult(5).sub(temp.mult(10))))), im.mult(temp2.squareFull().add(temp.mult(temp.mult(5).sub(temp2.mult(10))))));

    }


    /*
     *  abs(z)
     */
    public final BigNumComplex abs() {

        return new BigNumComplex(re.abs(), im.abs());

    }

    /* more efficient z^2 + c */
    public final BigNumComplex square_plus_c(BigNumComplex c) {

        BigNum temp = re.mult(im);

        return new BigNumComplex(re.add(im).mult(re.sub(im)).add(c.re), temp.mult2().add(c.im));

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
    public final BigNumComplex sub(GenericComplex zn) {
        BigNumComplex z = (BigNumComplex)zn;

        return new BigNumComplex(re.sub(z.re), im.sub(z.im));

    }

    /*
     * z1 + z2
     */
    @Override
    public final BigNumComplex plus(GenericComplex zn) {

        BigNumComplex z = (BigNumComplex)zn;
        return new BigNumComplex(re.add(z.re), im.add(z.im));

    }

    /* more efficient z^2 + c */
    public final BigNumComplex square_plus_c(GenericComplex cn) {

        BigNumComplex c = (BigNumComplex)cn;

        BigNum temp = re.mult(im);

        return new BigNumComplex(re.add(im).mult(re.sub(im)).add(c.re), temp.mult2().add(c.im));

    }

    /*
     *  z1 * z2
     */
    @Override
    public final BigNumComplex times(GenericComplex zn) {

        BigNumComplex z = (BigNumComplex)zn;
        //return new BigNumComplex(re.mult(z.re).sub(im.mult(z.im)),  re.mult(z.im).add(im.mult(z.re)));
        BigNum ac = re.mult(z.re);
        BigNum bd = im.mult(z.im);
        return new BigNumComplex(ac.sub(bd), re.add(im).mult(z.re.add(z.im)).sub(ac).sub(bd));

    }

    /*
     *  lexicographical comparison between two complex numbers
     * -1 when z1 > z2
     *  1 when z1 < z2
     *  0 when z1 == z2
     */
    public final int compare(BigNumComplex z2) {

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

        BigNumComplex z2 = (BigNumComplex)z2c;

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

    public final BigNumComplex fold_right(BigNumComplex z2) {

        return re.compare(z2.re) < 0 ? new BigNumComplex(re.add(z2.re.sub(re).mult2()), im) : this;

    }

    public final BigNumComplex fold_left(BigNumComplex z2) {

        return re.compare(z2.re) > 0 ? new BigNumComplex(re.sub(re.sub(z2.re).mult2()), im) : this;

    }

    public final BigNumComplex fold_up(BigNumComplex z2) {

        return im.compare(z2.im) < 0 ? new BigNumComplex(re, im.add(z2.im.sub(im).mult2())) : this;

    }

    public final BigNumComplex fold_down(BigNumComplex z2) {

        return im.compare(z2.im) > 0 ? new BigNumComplex(re, im.sub(im.sub(z2.im).mult2())) : this;

    }

    public final BigNumComplex inflection(BigNumComplex inf) {

        BigNumComplex diff = this.sub(inf);

        return inf.plus(diff.square());

    }

    public final BigNumComplex shear(BigNumComplex sh) {

        return new BigNumComplex(re.add(im.mult(sh.re)), im.add(re.mult(sh.im)));

    }

    /*
     * y + xi
     */
    public final BigNumComplex flip() {

        return new BigNumComplex(im, re);

    }

    /*
     *  |z|^2
     */
    @Override
    public final BigNum normSquared() {

        return re.square().add(im.square());
        //return re.mult(re).add(im.mult(im));

    }

    /*
     *  |z1 - z2|^2
     */
    @Override
    public final BigNum distanceSquared(GenericComplex za) {
        BigNumComplex z = (BigNumComplex) za;

        BigNum temp_re = re.sub(z.re);
        BigNum temp_im = im.sub(z.im);
        return temp_re.squareFull().add(temp_im.squareFull());

    }

    @Override
    public BigNumComplex times2() {
        return new BigNumComplex(re.mult2(), im.mult2());
    }

    @Override
    public BigNumComplex times4() {
        return new BigNumComplex(re.mult4(), im.mult4());
    }

    @Override
    public MantExpComplex toMantExpComplex() { return new MantExpComplex(this);}

    @Override
    public void set(GenericComplex za) {
        BigNumComplex z = (BigNumComplex) za;
        re = z.re;
        im = z.im;
    }

    @Override
    public GenericComplex sub_mutable(int a) {return sub(a);}

    @Override
    public GenericComplex times_mutable(GenericComplex a) {return times(a);}

    @Override
    public GenericComplex plus_mutable(GenericComplex v) { return plus(v); }

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
    public final BigNumComplex squareFast_mutable(NormComponents normComponents) {

        return squareFast(normComponents);

    }

    /*
     *  z^3
     */
    @Override
    public final BigNumComplex cubeFast_mutable(NormComponents normComponents) {

        return cubeFast(normComponents);

    }

    /*
     *  z^4
     */
    @Override
    public final BigNumComplex fourthFast_mutable(NormComponents normComponents) {

        return fourthFast(normComponents);

    }

    /*
     *  z^5
     */
    @Override
    public final BigNumComplex fifthFast_mutable(NormComponents normComponents) {

        return fifthFast(normComponents);

    }

    /*
     *  z^2 + c
     */
    @Override
    public final BigNumComplex squareFast_plus_c_mutable(NormComponents normComponents, GenericComplex ca) {
        return squareFast_plus_c(normComponents, ca);
    }

    /* more efficient z^2 + c */
    @Override
    public final BigNumComplex square_plus_c_mutable(GenericComplex cn) {

        return square_plus_c(cn);

    }

    /*
     *  Real -Imaginary i
     */
    @Override
    public final BigNumComplex conjugate_mutable() {

        return conjugate();

    }

    @Override
    public final BigNumComplex times_mutable(int number) {

        return times(number);

    }
}
