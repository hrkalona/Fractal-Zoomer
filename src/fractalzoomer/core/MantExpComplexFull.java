package fractalzoomer.core;


import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.core.unused.BigDecNumComplex;
import org.apfloat.Apfloat;

public class MantExpComplexFull extends MantExpComplex {
    public static final MantExpComplex ONE = new MantExpComplexFull(MantExp.ONE, MantExp.ZERO);
    private MantExp re;
    private MantExp im;

    public static MantExpComplexFull from(MantExp re, MantExp im) {
        return new MantExpComplexFull(re, im);
    }

    protected MantExpComplexFull() {
        re = new MantExp();
        im = new MantExp();
    }

    public MantExpComplexFull(long expRe, long expIm, double mantissaReal, double mantissaImag) {
        re = new MantExp(expRe, mantissaReal);
        im = new MantExp(expIm, mantissaImag);
    }

    protected MantExpComplexFull(MantExpComplexFull other) {
        this.re = new MantExp(other.re);
        this.im = new MantExp(other.im);
    }
    protected MantExpComplexFull(Complex c) {
        re = new MantExp(c.getRe());
        im = new MantExp(c.getIm());
    }

    protected MantExpComplexFull(DDComplex c) {
        re = new MantExp(c.getRe().doubleValue());
        im = new MantExp(c.getIm().doubleValue());
    }

    protected MantExpComplexFull(BigComplex c) {
        re = new MantExp(c.getRe());
        im = new MantExp(c.getIm());
    }

    protected MantExpComplexFull(BigNumComplex c) {
        re = c.getRe().getMantExp();
        im = c.getIm().getMantExp();
    }

    protected MantExpComplexFull(BigIntNumComplex c) {
        re = c.getRe().getMantExp();
        im = c.getIm().getMantExp();
    }

    protected MantExpComplexFull(BigDecNumComplex c) {
        re = new MantExp(c.getRe());
        im = new MantExp(c.getIm());
    }

    protected MantExpComplexFull(MpfrBigNumComplex c) {

        //setMantexp(c.getRe().getMantExp(), c.getIm().getMantExp());
        MantExp[] res = MpfrBigNum.get_d_2exp(c.getRe(), c.getIm());
        re = new MantExp(res[0]);
        im = new MantExp(res[1]);
    }

    protected MantExpComplexFull(MpirBigNumComplex c) {

        //setMantexp(c.getRe().getMantExp(), c.getIm().getMantExp());
        MantExp[] res = MpirBigNum.get_d_2exp(c.getRe(), c.getIm());
        re = new MantExp(res[0]);
        im = new MantExp(res[1]);
    }

    protected MantExpComplexFull(Apfloat re, Apfloat im) {
        this.re = new MantExp(re);
        this.im = new MantExp(im);
    }

    protected MantExpComplexFull(MantExp re, MantExp im) {
        this.re = new MantExp(re);
        this.im = new MantExp(im);
    }

    public MantExpComplexFull(double re, double im) {
        this.re = new MantExp(re);
        this.im = new MantExp(im);
    }

    @Override
    public MantExpComplexFull plus(MantExpComplex value) {

        MantExpComplexFull v = (MantExpComplexFull)value;

        return new MantExpComplexFull(re.add(v.re), im.add(v.im));

    }

    @Override
    public MantExpComplexFull plus_mutable(MantExpComplex value) {

        MantExpComplexFull v = (MantExpComplexFull)value;

        re.add_mutable(v.re);
        im.add_mutable(v.im);
        return this;

    }


    @Override
    public MantExpComplexFull times(MantExpComplex factor) {

        MantExpComplexFull v = (MantExpComplexFull)factor;
        MantExp temp = v.re;
        MantExp temp2 = v.im;

        return new MantExpComplexFull(re.multiply(temp).subtract_mutable(im.multiply(temp2)), re.multiply(temp2).add_mutable(im.multiply(temp)));

    }

    @Override
    public MantExpComplexFull times_mutable(MantExpComplex factor) {

        MantExpComplexFull v = (MantExpComplexFull)factor;
        MantExp temp = v.re;
        MantExp temp2 = v.im;

        MantExp temp3 = re.multiply(temp).subtract_mutable(im.multiply(temp2));
        im = re.multiply(temp2).add_mutable(im.multiply(temp));
        re = temp3;

        return this;
    }

    @Override
    public MantExpComplexFull times(MantExp factor) {

        return new MantExpComplexFull(re.multiply(factor), im.multiply(factor));

    }

    @Override
    public MantExpComplexFull times_mutable(MantExp factor) {

        re.multiply_mutable(factor);
        im.multiply_mutable(factor);

        return this;
    }

    @Override
    public MantExpComplexFull divide2() {
        return new MantExpComplexFull(re.divide2(), im.divide2());
    }

    @Override
    public MantExpComplexFull divide2_mutable() {

        re.divide2_mutable();
        im.divide2_mutable();
        return this;

    }

    @Override
    public MantExpComplexFull divide4() {
        return new MantExpComplexFull(re.divide4(), im.divide4());
    }

    @Override
    public MantExpComplexFull divide4_mutable() {

        re.divide4_mutable();
        im.divide4_mutable();
        return this;

    }

    @Override
    public MantExpComplexFull times2() {
        return new MantExpComplexFull(re.multiply2(), im.multiply2());
    }

    @Override
    public MantExpComplexFull times2_mutable() {

        re.multiply2_mutable();
        im.multiply2_mutable();
        return this;

    }

    @Override
    public MantExpComplexFull times4() {
        return new MantExpComplexFull(re.multiply4(), im.multiply4());
    }

    @Override
    public MantExpComplexFull times4_mutable() {

        re.multiply4_mutable();
        im.multiply4_mutable();
        return this;

    }

    @Override
    public MantExpComplexFull times8() {
        return new MantExpComplexFull(re.multiply8(), im.multiply8());
    }

    @Override
    public MantExpComplexFull times8_mutable() {

        re.multiply8_mutable();
        im.multiply8_mutable();
        return this;

    }

    @Override
    public MantExpComplexFull times16() {
        return new MantExpComplexFull(re.multiply16(), im.multiply16());
    }

    @Override
    public MantExpComplexFull times16_mutable() {

        re.multiply16_mutable();
        im.multiply16_mutable();
        return this;

    }

    @Override
    public MantExpComplexFull times32() {
        return new MantExpComplexFull(re.multiply32(), im.multiply32());
    }

    @Override
    public MantExpComplexFull times32_mutable() {

        re.multiply32_mutable();
        im.multiply32_mutable();
        return this;

    }

    @Override
    public MantExpComplexFull plus(MantExp real) {

        return new MantExpComplexFull(re.add(real), im);

    }

    @Override
    public MantExpComplexFull plus_mutable(MantExp real) {

        re.add_mutable(real);
        return this;

    }

    @Override
    public MantExpComplexFull sub(MantExpComplex value) {

        MantExpComplexFull v = (MantExpComplexFull)value;
        return new MantExpComplexFull(re.subtract(v.re), im.subtract(v.im));

    }

    @Override
    public MantExpComplexFull sub_mutable(MantExpComplex value) {

        MantExpComplexFull v = (MantExpComplexFull)value;

        re.subtract_mutable(v.re);
        im.subtract_mutable(v.im);

        return this;

    }

    @Override
    public MantExpComplexFull sub(MantExp real) {

        return new MantExpComplexFull(re.subtract(real), im);
    }

    @Override
    public MantExpComplexFull sub_mutable(MantExp real) {

        re.subtract_mutable(real);
        return this;

    }

    @Override
    public void Normalize() {
        re.Normalize();
        im.Normalize();
    }

    @Override
    public MantExpComplexFull square() {
        return new MantExpComplexFull(re.add(im).multiply_mutable(re.subtract(im)), re.multiply(im).multiply2_mutable());
    }

    @Override
    public MantExpComplexFull square_mutable() {

        MantExp temp = re.add(im).multiply_mutable(re.subtract(im));
        im = re.multiply(im).multiply2_mutable();
        re = temp;

        return this;
    }

    @Override
    public MantExpComplexFull cube() {
        MantExp temp = re.square();
        MantExp temp2 = im.square();

        return new MantExpComplexFull(re.multiply(temp.subtract(temp2.multiply(MantExp.THREE))), im.multiply(temp.multiply(MantExp.THREE).subtract(temp2)));
    }

    @Override
    public MantExpComplexFull cube_mutable() {
        MantExp temp = re.square();
        MantExp temp2 = im.square();

        MantExp temp3 = re.multiply(temp.subtract(temp2.multiply(MantExp.THREE)));
        im = im.multiply(temp.multiply(MantExp.THREE).subtract(temp2));
        re = temp3;

        return this;
    }

    @Override
    public MantExpComplexFull fourth() {

        MantExp temp = re.square();
        MantExp temp2 = im.square();

        return new MantExpComplexFull(temp.multiply(temp.subtract(temp2.multiply(MantExp.SIX))).add_mutable(temp2.square()),
                re.multiply(im).multiply_mutable(MantExp.FOUR).multiply_mutable(temp.subtract(temp2)));

    }

    @Override
    public MantExpComplexFull fourth_mutable() {
        MantExp temp = re.square();
        MantExp temp2 = im.square();

        MantExp temp3 = temp.multiply(temp.subtract(temp2.multiply(MantExp.SIX))).add_mutable(temp2.square());
        im = re.multiply(im).multiply_mutable(MantExp.FOUR).multiply_mutable(temp.subtract(temp2));
        re = temp3;


        return this;
    }

    @Override
    public MantExpComplexFull fifth() {

        MantExp temp = re.square();
        MantExp temp2 = im.square();

        return new MantExpComplexFull( re.multiply(temp.square().add_mutable(temp2.multiply(temp2.multiply(MantExp.FIVE).subtract_mutable(temp.multiply(MantExp.TEN))))),
                im.multiply(temp2.square().add_mutable(temp.multiply(temp.multiply(MantExp.FIVE).subtract_mutable(temp2.multiply(MantExp.TEN))))));


    }

    @Override
    public MantExpComplexFull fifth_mutable() {
        MantExp temp = re.square();
        MantExp temp2 = im.square();

        re.multiply_mutable(temp.square().add_mutable(temp2.multiply(temp2.multiply(MantExp.FIVE).subtract_mutable(temp.multiply(MantExp.TEN)))));
        im.multiply_mutable(temp2.square().add_mutable(temp.multiply(temp.multiply(MantExp.FIVE).subtract_mutable(temp2.multiply(MantExp.TEN)))));


        return this;
    }

    @Override
    public MantExp norm_squared() {
        return re.square().add_mutable(im.square());
    }

    @Override
    public MantExp norm() {
        return re.square().add_mutable(im.square()).sqrt_mutable();
    }

    @Override
    public MantExp hypot() {
        return norm();
    }


    @Override
    public MantExpComplexFull divide(MantExpComplex factor) {

        MantExpComplexFull v = (MantExpComplexFull)factor;
        MantExp temp = v.re;
        MantExp temp2 = v.im;

        MantExp temp3 = temp.square().add_mutable(temp2.square());

        return new MantExpComplexFull( re.multiply(temp).add_mutable(im.multiply(temp2)).divide_mutable(temp3),
                im.multiply(temp).subtract_mutable(re.multiply(temp2)).divide_mutable(temp3)
                );

    }

    @Override
    public MantExpComplexFull divide_mutable(MantExpComplex factor) {

        MantExpComplexFull v = (MantExpComplexFull)factor;
        MantExp temp = v.re;
        MantExp temp2 = v.im;

        MantExp temp3 = temp.square().add_mutable(temp2.square());

        MantExp temp4 = re.multiply(temp).add_mutable(im.multiply(temp2)).divide_mutable(temp3);
        im = im.multiply(temp).subtract_mutable(re.multiply(temp2)).divide_mutable(temp3);
        re = temp4;

        return this;
    }

    @Override
    public MantExpComplexFull reciprocal() {

        MantExp temp = re.square().add_mutable(im.square());

        return new MantExpComplexFull( re.divide(temp), im.divide(temp).negate_mutable());

    }

    @Override
    public MantExpComplexFull reciprocal_mutable() {

        MantExp temp = re.square().add_mutable(im.square());

        re.divide_mutable(temp);
        im.divide_mutable(temp).negate_mutable();

        return this;

    }


    @Override
    public MantExpComplexFull divide(MantExp real) {

        return new MantExpComplexFull( re.divide(real), im.divide(real));

    }

    @Override
    public MantExpComplexFull divide_mutable(MantExp real) {

        re.divide_mutable(real);
        im.divide_mutable(real);

        return this;
    }

    @Override
    public MantExpComplexFull negative() {

        return new MantExpComplexFull(re.negate(), im.negate());

    }

    @Override
    public MantExpComplexFull negative_mutable() {

        re.negate_mutable();
        im.negate_mutable();
        return this;

    }

    @Override
    public MantExpComplexFull abs() {

        return new MantExpComplexFull(re.abs(), im.abs());

    }

    @Override
    public MantExpComplexFull abs_mutable() {

        re.abs_mutable();
        im.abs_mutable();
        return this;

    }

    @Override
    public MantExpComplexFull absre_mutable() {

        re.abs_mutable();
        return this;

    }

    @Override
    public MantExpComplexFull absNegateRe_mutable() {
        re.abs_mutable().negate_mutable();
        return this;
    }

    @Override
    public MantExpComplexFull absNegateIm_mutable() {
        im.abs_mutable().negate_mutable();
        return this;
    }

    @Override
    public MantExpComplexFull conjugate() {

        return new MantExpComplexFull(re, im.negate());

    }

    @Override
    public MantExpComplexFull conjugate_mutable() {

        im.negate_mutable();
        return this;

    }

    @Override
    public Complex toComplex() {

        return new Complex(re.toDouble(), im.toDouble());
    }

    @Override
    public String toString() {
        return "" + re.mantissa + "*2^" + re.exp + " " + im.mantissa + "*2^" + im.exp + " " + toComplex();
    }

    @Override
    public MantExp getRe() {

        return re;

    }

    @Override
    public MantExp getIm() {

        return im;

    }

    @Override
    public double getMantissaReal() {
        return re.mantissa;
    }

    @Override
    public double getMantissaImag() {
        return im.mantissa;
    }

    @Override
    public long getMinExp() {
        return Math.min(re.exp, im.exp);
    }

    @Override
    public long getMaxExp() {
        return Math.max(re.exp, im.exp);
    }

    @Override
    public long getAverageExp() {
        return (long)((re.exp + im.exp) * 0.5);
    }

    @Override
    public long getExp() {

        return re.exp;

    }

    public void addExp(long exp) {
        re.exp += exp;
        im.exp += exp;
    }

    public void subExp(long exp) {
        re.exp -= exp;
        im.exp -= exp;
    }

    @Override
    public long getExpImag() {

        return im.exp;

    }

    @Override
    public long log2normApprox() {
        return re.square().add_mutable(im.square()).log2approx() / 2;
    }

    //Should call Reduce first
    @Override
    public boolean equals(MantExpComplex z2) {

        MantExpComplexFull v = (MantExpComplexFull)z2;

        MantExp otherRe = v.re;
        MantExp otherIm = v.im;

        if(re.mantissa == 0 && im.mantissa == 0 && otherRe.mantissa == 0 && otherIm.mantissa == 0) {
            return true;
        }

        return re.exp == otherRe.exp && im.exp == otherIm.exp
                && re.mantissa == otherRe.mantissa && im.mantissa == otherIm.mantissa;

    }

    @Override
    public void assign(MantExpComplex z) {
        MantExpComplexFull v = (MantExpComplexFull)z;
        MantExp otherRe = v.re;
        MantExp otherIm = v.im;
        re.mantissa = otherRe.mantissa;
        im.mantissa = otherIm.mantissa;
        re.exp = otherRe.exp;
        im.exp = otherIm.exp;
    }

    @Override
    public void set(GenericComplex za) {
        MantExpComplexFull z = (MantExpComplexFull) za;
        re.mantissa = z.re.mantissa;
        im.mantissa = z.im.mantissa;
        re.exp = z.re.exp;
        im.exp = z.im.exp;
    }

    @Override
    public MantExpComplexFull plus_mutable(GenericComplex v) {

        MantExpComplexFull value = (MantExpComplexFull) v;

        return plus_mutable(value);

    }

    public void assign(long exp1, long expIm1, double mantissaReal1, double mantissaImag1) {
        re.mantissa = mantissaReal1;
        im.mantissa = mantissaImag1;
        re.exp = exp1;
        im.exp = expIm1;
    }

    @Override
    public void set(long expRe, long expIm, double valRe, double valIm) {

        if(valRe == 0 && valIm == 0) {
            expRe = MantExp.MIN_BIG_EXPONENT;
            expIm = MantExp.MIN_BIG_EXPONENT;
        }
        else if(valRe == 0) {
            expRe = MantExp.MIN_BIG_EXPONENT;
        }
        else if(valIm == 0) {
            expIm = MantExp.MIN_BIG_EXPONENT;
        }

        re.exp = expRe;
        im.exp = expIm;
        re.mantissa = valRe;
        im.mantissa = valIm;
    }

    @Override
    public boolean isInfinite() {
        return Double.isInfinite(re.mantissa) || Double.isInfinite(im.mantissa);
    }

    @Override
    public boolean isNaN() {
        return Double.isNaN(re.mantissa) || Double.isNaN(im.mantissa);
    }

    @Override
    public boolean isZero() {
        return re.mantissa == 0 && im.mantissa == 0;
    }

    @Override
    public Object re() {
        return getRe();
    }

    @Override
    public Object im() {
        return getIm();
    }

    @Override
    public Object Norm() {
        return norm();
    }
}

