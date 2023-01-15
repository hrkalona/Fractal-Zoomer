package fractalzoomer.core;


import fractalzoomer.core.mpfr.MpfrBigNum;
import org.apfloat.Apfloat;

public class MantExpComplex extends GenericComplex {

    private long exp;
    private double mantissaReal;
    private double mantissaImag;

    public MantExpComplex() {
        mantissaReal = 0.0;
        mantissaImag = 0.0;
        exp = MantExp.MIN_BIG_EXPONENT;
    }

    public MantExpComplex(double mantissaReal, double mantissaImag, long exp) {
        this.mantissaReal = mantissaReal;
        this.mantissaImag = mantissaImag;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;
    }

    public MantExpComplex(long exp, double mantissaReal, double mantissaImag) {
        this.mantissaReal = mantissaReal;
        this.mantissaImag = mantissaImag;
        this.exp = exp;
    }

    public MantExpComplex(MantExpComplex other) {
        this.mantissaReal = other.mantissaReal;
        this.mantissaImag = other.mantissaImag;
        this.exp = other.exp;
    }

    public MantExpComplex(MantExpComplex other, int exp) {
        this.mantissaReal = other.mantissaReal;
        this.mantissaImag = other.mantissaImag;
        this.exp = exp;
    }

    public MantExpComplex(Complex c) {
        setMantexp(new MantExp(c.getRe()), new MantExp(c.getIm()));
    }

    public MantExpComplex(DDComplex c) {
        setMantexp(new MantExp(c.getRe().doubleValue()), new MantExp(c.getIm().doubleValue()));
    }

    public MantExpComplex(BigComplex c) {
        setMantexp(new MantExp(c.getRe()), new MantExp(c.getIm()));
    }

    public MantExpComplex(BigNumComplex c) {
        setMantexp(c.getRe().getMantExp(), c.getIm().getMantExp());
    }

    public MantExpComplex(MpfrBigNumComplex c) {

        //setMantexp(c.getRe().getMantExp(), c.getIm().getMantExp());
        MantExp[] res = MpfrBigNum.get_d_2exp(c.getRe(), c.getIm());
        setMantexp(res[0], res[1]);
    }

    public MantExpComplex(Apfloat re, Apfloat im) {
        setMantexp(new MantExp(re), new MantExp(im));
    }

    public MantExpComplex(MantExp re, MantExp im) {
        setMantexp(re, im);
    }

    public MantExpComplex(double re, double im) {
        setMantexp(new MantExp(re), new MantExp(im));
    }

    private void setMantexp(MantExp realIn, MantExp imagIn) {

        exp = Math.max(realIn.exp, imagIn.exp);
        mantissaReal = realIn.mantissa * MantExp.getMultiplier(realIn.exp-exp);
        mantissaImag = imagIn.mantissa * MantExp.getMultiplier(imagIn.exp-exp);

        /*if (realIn.exp == imagIn.exp) {
            exp = realIn.exp;
            mantissaReal = realIn.mantissa;
            mantissaImag = imagIn.mantissa;
        }
        else if (realIn.exp > imagIn.exp) {

            //double temp = imagIn.mantissa / MantExp.toExp(realIn.exp - imagIn.exp);

            exp = realIn.exp;

            mantissaReal = realIn.mantissa;
            //mantissaImag = temp;
            //mantissaImag = MantExp.toDouble(imagIn.mantissa, imagIn.exp - realIn.exp);
            mantissaImag = imagIn.mantissa * MantExp.getMultiplier(imagIn.exp - realIn.exp);
        }
        else {
            //double temp = realIn.mantissa / MantExp.toExp(imagIn.exp - realIn.exp);

            exp = imagIn.exp;

            //mantissaReal = temp;
            //mantissaReal = MantExp.toDouble(realIn.mantissa, realIn.exp - imagIn.exp);
            mantissaReal = realIn.mantissa * MantExp.getMultiplier(realIn.exp - imagIn.exp);
            mantissaImag = imagIn.mantissa;
        }*/
    }

    public MantExpComplex plus(MantExpComplex value) {

        long expDiff = exp - value.exp;

        if(expDiff >= MantExp.EXPONENT_DIFF_IGNORED) {
            return new MantExpComplex(exp, mantissaReal, mantissaImag);
        } else if(expDiff >= 0) {
            double mul = MantExp.getMultiplier(-expDiff);
            return new MantExpComplex(exp, mantissaReal + value.mantissaReal * mul, mantissaImag + value.mantissaImag * mul);
        }
        /*else if(expDiff == 0) {
            return new MantExpComplex(exp, mantissaReal + value.mantissaReal, mantissaImag + value.mantissaImag);
        }*/
        else if(expDiff > MantExp.MINUS_EXPONENT_DIFF_IGNORED) {
            double mul = MantExp.getMultiplier(expDiff);
            return new MantExpComplex(value.exp, mantissaReal * mul + value.mantissaReal, mantissaImag * mul + value.mantissaImag);
        } else {
            return new MantExpComplex(value.exp, value.mantissaReal, value.mantissaImag);
        }

    }

    @Deprecated
    public MantExpComplex plusOld(MantExpComplex value) {

        double temp_mantissa_real = 0;
        double temp_mantissa_imag = 0;
        long temp_exp = 0;

        if(exp == value.exp) {
            temp_exp = exp;
            temp_mantissa_real = mantissaReal + value.mantissaReal;
            temp_mantissa_imag = mantissaImag + value.mantissaImag;
        }
        else if (exp > value.exp) {
            //double temp = MantExp.toExp(exp - value.exp);
            //temp_mantissa_real = mantissaReal + value.mantissaReal / temp;
            //temp_mantissa_imag = mantissaImag + value.mantissaImag / temp;

            temp_exp = exp;

            long diff = value.exp - exp;
            //temp_mantissa_real = mantissaReal + MantExp.toDouble(value.mantissaReal, diff);
            //temp_mantissa_imag = mantissaImag + MantExp.toDouble(value.mantissaImag, diff);
            double d = MantExp.getMultiplier(diff);
            temp_mantissa_real = mantissaReal + value.mantissaReal * d;
            temp_mantissa_imag = mantissaImag + value.mantissaImag * d;

        }
        else {
            //double temp = MantExp.toExp(value.exp - exp);
            //temp_mantissa_real  = mantissaReal / temp + value.mantissaReal;
            //temp_mantissa_imag  = mantissaImag / temp + value.mantissaImag;

            temp_exp = value.exp;

            long diff = exp - value.exp;
            //temp_mantissa_real = MantExp.toDouble(mantissaReal, diff) + value.mantissaReal;
            //temp_mantissa_imag = MantExp.toDouble(mantissaImag, diff) + value.mantissaImag;
            double d = MantExp.getMultiplier(diff);
            temp_mantissa_real = mantissaReal * d + value.mantissaReal;
            temp_mantissa_imag = mantissaImag * d + value.mantissaImag;

        }

        return new MantExpComplex(temp_exp, temp_mantissa_real, temp_mantissa_imag);

    }

    public MantExpComplex plus_mutable(MantExpComplex value) {

        long expDiff = exp - value.exp;

        if(expDiff >= MantExp.EXPONENT_DIFF_IGNORED) {
            return this;
        } else if(expDiff >= 0) {
            double mul = MantExp.getMultiplier(-expDiff);
            mantissaReal = mantissaReal + value.mantissaReal * mul;
            mantissaImag = mantissaImag + value.mantissaImag * mul;
        }
        /*else if(expDiff == 0) {
            mantissaReal = mantissaReal + value.mantissaReal;
            mantissaImag = mantissaImag + value.mantissaImag;
        }*/
        else if(expDiff > MantExp.MINUS_EXPONENT_DIFF_IGNORED) {
            double mul = MantExp.getMultiplier(expDiff);
            exp = value.exp;
            mantissaReal = mantissaReal * mul + value.mantissaReal;
            mantissaImag =  mantissaImag * mul + value.mantissaImag;
        } else {
            exp = value.exp;
            mantissaReal = value.mantissaReal;
            mantissaImag = value.mantissaImag;
        }
        return this;

    }


    public MantExpComplex times(MantExpComplex factor) {
        double tempMantissaReal = (mantissaReal * factor.mantissaReal) - (mantissaImag * factor.mantissaImag);

        double tempMantissaImag = (mantissaReal * factor.mantissaImag) + (mantissaImag * factor.mantissaReal);

        return new MantExpComplex(tempMantissaReal, tempMantissaImag, exp + factor.exp);

        /*double absRe = Math.abs(tempMantissaReal);
        double absIm = Math.abs(tempMantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            p.Reduce();
        }*/

    }

    public MantExpComplex times_mutable(MantExpComplex factor) {
        double tempMantissaReal = (mantissaReal * factor.mantissaReal) - (mantissaImag * factor.mantissaImag);

        double tempMantissaImag = (mantissaReal * factor.mantissaImag) + (mantissaImag * factor.mantissaReal);

        long exp = this.exp + factor.exp;

        mantissaReal = tempMantissaReal;
        mantissaImag = tempMantissaImag;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;

        /*double absRe = Math.abs(tempMantissaReal);
        double absIm = Math.abs(tempMantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            Reduce();
        }*/

        return this;
    }

    public MantExpComplex times(double factor) {
        return times(new MantExp(factor));
    }

    public MantExpComplex times_mutable(double factor) {
        return times_mutable(new MantExp(factor));
    }

    public MantExpComplex times(MantExp factor) {
        double tempMantissaReal = mantissaReal * factor.mantissa;

        double tempMantissaImag = mantissaImag * factor.mantissa;

        return new MantExpComplex(tempMantissaReal, tempMantissaImag, exp + factor.exp);

        /*double absRe = Math.abs(tempMantissaReal);
        double absIm = Math.abs(tempMantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            p.Reduce();
        }*/
    }

    public MantExpComplex times_mutable(MantExp factor) {
        double tempMantissaReal = mantissaReal * factor.mantissa;

        double tempMantissaImag = mantissaImag * factor.mantissa;

        long exp = this.exp + factor.exp;

        mantissaReal = tempMantissaReal;
        mantissaImag = tempMantissaImag;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;

        /*double absRe = Math.abs(tempMantissaReal);
        double absIm = Math.abs(tempMantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            Reduce();
        }*/

        return this;
    }

    public MantExpComplex divide2() {
        return new MantExpComplex(mantissaReal, mantissaImag, exp - 1);
    }

    public MantExpComplex divide2_mutable() {

        long exp = this.exp - 1;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;
        return this;

    }

    public MantExpComplex divide4() {
        return new MantExpComplex(mantissaReal, mantissaImag, exp - 2);
    }

    public MantExpComplex divide4_mutable() {

        long exp = this.exp - 2;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;
        return this;

    }

    @Override
    public MantExpComplex times2() {
        return new MantExpComplex(exp + 1, mantissaReal, mantissaImag);
    }

    @Override
    public MantExpComplex times2_mutable() {

        exp++;
        return this;

    }

    @Override
    public MantExpComplex times4() {
        return new MantExpComplex(exp + 2, mantissaReal, mantissaImag);
    }

    public MantExpComplex times4_mutable() {

        exp += 2;
        return this;

    }

    public MantExpComplex times8() {
        return new MantExpComplex(exp + 3, mantissaReal, mantissaImag);
    }

    public MantExpComplex times8_mutable() {

        exp += 3;
        return this;

    }

    public MantExpComplex times16() {
        return new MantExpComplex(exp + 4, mantissaReal, mantissaImag);
    }

    public MantExpComplex times16_mutable() {

        exp += 4;
        return this;

    }

    public MantExpComplex times32() {
        return new MantExpComplex(exp + 5, mantissaReal, mantissaImag);
    }

    public MantExpComplex times32_mutable() {

        exp += 5;
        return this;

    }

    public MantExpComplex plus(double real) {
        return plus(new MantExp(real));
    }

    public MantExpComplex plus_mutable(double real) {
        return plus_mutable(new MantExp(real));
    }

    public MantExpComplex plus(MantExp real) {

        long expDiff = exp - real.exp;

        if(expDiff >= MantExp.EXPONENT_DIFF_IGNORED) {
            return new MantExpComplex(exp, mantissaReal, mantissaImag);
        } else if(expDiff >= 0) {
            double mul = MantExp.getMultiplier(-expDiff);
            return new MantExpComplex(exp, mantissaReal + real.mantissa * mul, mantissaImag);
        }
        /*else if(expDiff == 0) {
            return new MantExpComplex(exp, mantissaReal + real.mantissa, mantissaImag);
        }*/
        else if(expDiff > MantExp.MINUS_EXPONENT_DIFF_IGNORED) {
            double mul = MantExp.getMultiplier(expDiff);
            return new MantExpComplex(real.exp, mantissaReal * mul + real.mantissa, mantissaImag * mul);
        } else {
            return new MantExpComplex(real.exp, real.mantissa, 0.0);
        }
    }

    public MantExpComplex plus_mutable(MantExp real) {

        long expDiff = exp - real.exp;

        if(expDiff >= MantExp.EXPONENT_DIFF_IGNORED) {
            return this;
        } else if(expDiff >= 0) {
            double mul = MantExp.getMultiplier(-expDiff);
            mantissaReal = mantissaReal + real.mantissa * mul;
        }
        /*else if(expDiff == 0) {
            mantissaReal = mantissaReal + real.mantissa;
        }*/
        else if(expDiff > MantExp.MINUS_EXPONENT_DIFF_IGNORED) {
            double mul = MantExp.getMultiplier(expDiff);
            exp = real.exp;
            mantissaReal = mantissaReal * mul + real.mantissa;
            mantissaImag =  mantissaImag * mul;
        } else {
            exp = real.exp;
            mantissaReal = real.mantissa;
            mantissaImag = 0.0;
        }
        return this;

    }

    public MantExpComplex sub(MantExpComplex value) {

        long expDiff = exp - value.exp;

        if(expDiff >= MantExp.EXPONENT_DIFF_IGNORED) {
            return new MantExpComplex(exp, mantissaReal, mantissaImag);
        } else if(expDiff >= 0) {
            double mul = MantExp.getMultiplier(-expDiff);
            return new MantExpComplex(exp, mantissaReal - value.mantissaReal * mul, mantissaImag - value.mantissaImag * mul);
        }
        /*else if(expDiff == 0) {
            return new MantExpComplex(exp, mantissaReal - value.mantissaReal, mantissaImag - value.mantissaImag);
        }*/
        else if(expDiff > MantExp.MINUS_EXPONENT_DIFF_IGNORED) {
            double mul = MantExp.getMultiplier(expDiff);
            return new MantExpComplex(value.exp, mantissaReal * mul - value.mantissaReal, mantissaImag * mul - value.mantissaImag);
        } else {
            return new MantExpComplex(value.exp, -value.mantissaReal, -value.mantissaImag);
        }

    }

    public MantExpComplex sub_mutable(MantExpComplex value) {

        long expDiff = exp - value.exp;

        if(expDiff >= MantExp.EXPONENT_DIFF_IGNORED) {
            return this;
        } else if(expDiff >= 0) {
            double mul = MantExp.getMultiplier(-expDiff);
            mantissaReal = mantissaReal - value.mantissaReal * mul;
            mantissaImag = mantissaImag - value.mantissaImag * mul;
        }
        /*else if(expDiff == 0) {
            mantissaReal = mantissaReal - value.mantissaReal;
            mantissaImag = mantissaImag - value.mantissaImag;
        }*/
        else if(expDiff > MantExp.MINUS_EXPONENT_DIFF_IGNORED) {
            double mul = MantExp.getMultiplier(expDiff);
            exp = value.exp;
            mantissaReal = mantissaReal * mul - value.mantissaReal;
            mantissaImag =  mantissaImag * mul - value.mantissaImag;
        } else {
            exp = value.exp;
            mantissaReal = -value.mantissaReal;
            mantissaImag = -value.mantissaImag;
        }
        return this;

    }

    public MantExpComplex sub(MantExp real) {

        long expDiff = exp - real.exp;

        if(expDiff >= MantExp.EXPONENT_DIFF_IGNORED) {
            return new MantExpComplex(exp, mantissaReal, mantissaImag);
        } else if(expDiff >= 0) {
            double mul = MantExp.getMultiplier(-expDiff);
            return new MantExpComplex(exp, mantissaReal - real.mantissa * mul, mantissaImag);
        }
        /*else if(expDiff == 0) {
            return new MantExpComplex(exp, mantissaReal - real.mantissa, mantissaImag);
        }*/
        else if(expDiff > MantExp.MINUS_EXPONENT_DIFF_IGNORED) {
            double mul = MantExp.getMultiplier(expDiff);
            return new MantExpComplex(real.exp, mantissaReal * mul - real.mantissa, mantissaImag * mul);
        } else {
            return new MantExpComplex(real.exp, -real.mantissa, 0.0);
        }
    }

    public MantExpComplex sub_mutable(MantExp real) {

        long expDiff = exp - real.exp;

        if(expDiff >= MantExp.EXPONENT_DIFF_IGNORED) {
            return this;
        } else if(expDiff >= 0) {
            double mul = MantExp.getMultiplier(-expDiff);
            mantissaReal = mantissaReal - real.mantissa * mul;
        }
        /*else if(expDiff == 0) {
            mantissaReal = mantissaReal - real.mantissa;
        }*/
        else if(expDiff > MantExp.MINUS_EXPONENT_DIFF_IGNORED) {
            double mul = MantExp.getMultiplier(expDiff);
            exp = real.exp;
            mantissaReal = mantissaReal * mul - real.mantissa;
            mantissaImag =  mantissaImag * mul;
        } else {
            exp = real.exp;
            mantissaReal = -real.mantissa;
            mantissaImag = 0;
        }
        return this;

    }

    public MantExpComplex sub(double real) {
        return sub(new MantExp(real));
    }

    public MantExpComplex sub_mutable(double real) {
        return sub_mutable(new MantExp(real));
    }

    public void Reduce() {
        if(mantissaReal == 0 && mantissaImag == 0) {
            return;
        }

        long bitsRe = Double.doubleToRawLongBits(mantissaReal);
        long expDiffRe = ((bitsRe & 0x7FF0000000000000L) >> 52);

        long bitsIm = Double.doubleToRawLongBits(mantissaImag);
        long expDiffIm = ((bitsIm & 0x7FF0000000000000L) >> 52);

        long expDiff = Math.max(expDiffRe, expDiffIm) + MantExp.MIN_SMALL_EXPONENT;

        long expCombined = exp + expDiff;
        double mul = MantExp.getMultiplier(-expDiff);
        mantissaReal *= mul;
        mantissaImag *= mul;
        exp = expCombined;
    }


    /*public void Reduce2() {

        MantExp mantissaRealTemp = new MantExp(mantissaReal);

        MantExp mantissaImagTemp = new MantExp(mantissaImag);

        long realExp = exp;
        long imagExp = exp;

        boolean a1 = mantissaRealTemp.mantissa == mantissaReal;
        boolean a2 =  mantissaImagTemp.mantissa == mantissaImag;

        if(a1 && a2) {
           return;
        }
        else if(a1) {
            imagExp = mantissaImagTemp.exp + exp;
        }
        else if(a2) {
            realExp = mantissaRealTemp.exp + exp;
        }
        else {
            realExp = mantissaRealTemp.exp + exp;
            imagExp = mantissaImagTemp.exp + exp;
        }

        if (realExp == imagExp) {
            exp = realExp;

            mantissaImag = mantissaImagTemp.mantissa;
            mantissaReal = mantissaRealTemp.mantissa;
        }
        else if (realExp > imagExp) {
            //double mantissa_temp = mantissaImagTemp.mantissa / MantExp.toExp(realExp - imagExp);

            exp = realExp;
            mantissaReal = mantissaRealTemp.mantissa;
            //mantissaImag = mantissa_temp;
            //mantissaImag = MantExp.toDouble(mantissaImagTemp.mantissa, imagExp - realExp);
            mantissaImag = mantissaImagTemp.mantissa * MantExp.getMultiplier(imagExp - realExp);
        }
        else {
            //double mantissa_temp = mantissaRealTemp.mantissa / MantExp.toExp(imagExp - realExp);

            exp = imagExp;
            //mantissaReal = mantissa_temp;
            //mantissaReal = MantExp.toDouble(mantissaRealTemp.mantissa, realExp - imagExp);
            mantissaReal = mantissaRealTemp.mantissa * MantExp.getMultiplier(realExp - imagExp);
            mantissaImag = mantissaImagTemp.mantissa;
        }
    }*/

    @Override
    public MantExpComplex square() {
        double temp = mantissaReal * mantissaImag;
        return new MantExpComplex((mantissaReal + mantissaImag) * (mantissaReal - mantissaImag), temp + temp, exp << 1);

        /*double absRe = Math.abs(p.mantissaReal);
        double absIm = Math.abs(p.mantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            p.Reduce();
        }*/
    }

    @Override
    public MantExpComplex square_mutable() {
        double temp = mantissaReal * mantissaImag;

        long exp = this.exp << 1;
        mantissaReal = (mantissaReal + mantissaImag) * (mantissaReal - mantissaImag);
        mantissaImag = temp + temp;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;

        /*double absRe = Math.abs(mantissaReal);
        double absIm = Math.abs(mantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            Reduce();
        }*/

        return this;
    }

    @Override
    public MantExpComplex cube() {
        double temp = mantissaReal * mantissaReal;
        double temp2 = mantissaImag * mantissaImag;

        return new MantExpComplex(mantissaReal * (temp - 3 * temp2), mantissaImag * (3 * temp - temp2), 3 * exp);

        /*double absRe = Math.abs(p.mantissaReal);
        double absIm = Math.abs(p.mantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            p.Reduce();
        }*/
    }

    public MantExpComplex cube_mutable() {
        double temp = mantissaReal * mantissaReal;
        double temp2 = mantissaImag * mantissaImag;

        long exp = 3 * this.exp;
        mantissaReal = mantissaReal * (temp - 3 * temp2);
        mantissaImag = mantissaImag * (3 * temp - temp2);
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;

        /*double absRe = Math.abs(mantissaReal);
        double absIm = Math.abs(mantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            Reduce();
        }*/

        return this;
    }

    @Override
    public MantExpComplex fourth() {
        double temp = mantissaReal * mantissaReal;
        double temp2 = mantissaImag * mantissaImag;

        return new MantExpComplex(temp * (temp - 6 * temp2) + temp2 * temp2, 4 * mantissaReal * mantissaImag * (temp - temp2), exp << 2);

        /*double absRe = Math.abs(p.mantissaReal);
        double absIm = Math.abs(p.mantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            p.Reduce();
        }*/
    }

    public MantExpComplex fourth_mutable() {
        double temp = mantissaReal * mantissaReal;
        double temp2 = mantissaImag * mantissaImag;

        long exp = this.exp << 2;

        double temp_re = temp * (temp - 6 * temp2) + temp2 * temp2;
        mantissaImag = 4 * mantissaReal * mantissaImag * (temp - temp2);
        mantissaReal = temp_re;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;

        /*double absRe = Math.abs(mantissaReal);
        double absIm = Math.abs(mantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            Reduce();
        }*/

        return this;
    }

    @Override
    public MantExpComplex fifth() {

        double temp = mantissaReal * mantissaReal;
        double temp2 = mantissaImag * mantissaImag;

        return new MantExpComplex(mantissaReal * (temp * temp + temp2 * (5 * temp2 - 10 * temp)), mantissaImag * (temp2 * temp2 + temp * (5 * temp - 10 * temp2)), 5 * exp);

        /*double absRe = Math.abs(p.mantissaReal);
        double absIm = Math.abs(p.mantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            p.Reduce();
        }*/
    }

    public MantExpComplex fifth_mutable() {
        double temp = mantissaReal * mantissaReal;
        double temp2 = mantissaImag * mantissaImag;

        long exp = 5 * this.exp;
        mantissaReal = mantissaReal * (temp * temp + temp2 * (5 * temp2 - 10 * temp));
        mantissaImag = mantissaImag * (temp2 * temp2 + temp * (5 * temp - 10 * temp2));
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;

        /*double absRe = Math.abs(mantissaReal);
        double absIm = Math.abs(mantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            Reduce();
        }*/

        return this;
    }

    public MantExp norm_squared() {
        return new MantExp(mantissaReal * mantissaReal + mantissaImag * mantissaImag, exp << 1);
    }

    public MantExp norm() {
        return new MantExp(exp, Math.sqrt(mantissaReal * mantissaReal + mantissaImag * mantissaImag));
    }


    public MantExp hypot() {
        return new MantExp(exp, Math.hypot(mantissaReal, mantissaImag));
    }

    public static MantExp hypot(double mRe, double mIm, long exp) {return new MantExp(exp, Math.hypot(mRe, mIm));}

    public final MantExpComplex divide(MantExpComplex factor) {

        double temp = factor.mantissaReal * factor.mantissaReal + factor.mantissaImag * factor.mantissaImag;

        double tempMantissaReal = (mantissaReal * factor.mantissaReal + mantissaImag * factor.mantissaImag) / temp;

        double tempMantissaImag = (mantissaImag * factor.mantissaReal - mantissaReal * factor.mantissaImag)  / temp;

        return new MantExpComplex(tempMantissaReal , tempMantissaImag, exp - factor.exp);

        /*double absRe = Math.abs(tempMantissaReal);
        double absIm = Math.abs(tempMantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            p.Reduce();
        }*/
    }

    public final MantExpComplex divide_mutable(MantExpComplex factor) {

        double temp = factor.mantissaReal * factor.mantissaReal + factor.mantissaImag * factor.mantissaImag;

        double tempMantissaReal = (mantissaReal * factor.mantissaReal + mantissaImag * factor.mantissaImag) / temp;

        double tempMantissaImag = (mantissaImag * factor.mantissaReal - mantissaReal * factor.mantissaImag)  / temp;

        long exp = this.exp - factor.exp;
        mantissaReal = tempMantissaReal;
        mantissaImag = tempMantissaImag;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;

        /*double absRe = Math.abs(tempMantissaReal);
        double absIm = Math.abs(tempMantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            Reduce();
        }*/

        return this;
    }

    public final MantExpComplex divide(MantExp real) {

        return new MantExpComplex(mantissaReal / real.mantissa, mantissaImag / real.mantissa, exp - real.exp);

        /*double absRe = Math.abs(p.mantissaReal);
        double absIm = Math.abs(p.mantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            p.Reduce();
        }*/
    }

    public final MantExpComplex divide_mutable(MantExp real) {

        long exp = this.exp - real.exp;
        mantissaReal = mantissaReal / real.mantissa;
        mantissaImag = mantissaImag / real.mantissa;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;

        /*double absRe = Math.abs(mantissaReal);
        double absIm = Math.abs(mantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            Reduce();
        }*/

        return this;
    }

    public final MantExpComplex divide(double real) {

        return divide(new MantExp(real));

    }

    public final MantExpComplex divide_mutable(double real) {

        return divide_mutable(new MantExp(real));

    }

    @Override
    public MantExpComplex negative() {

        return new MantExpComplex(exp, -mantissaReal, -mantissaImag);

    }

    @Override
    public MantExpComplex negative_mutable() {

        mantissaReal = -mantissaReal;
        mantissaImag = -mantissaImag;
        return this;

    }

    public MantExpComplex abs() {

        return new MantExpComplex(exp, Math.abs(mantissaReal), Math.abs(mantissaImag));

    }

    @Override
    public MantExpComplex abs_mutable() {

        mantissaReal = Math.abs(mantissaReal);
        mantissaImag = Math.abs(mantissaImag);
        return this;

    }

    public MantExpComplex conjugate() {

        return new MantExpComplex(exp, mantissaReal, -mantissaImag);

    }

    @Override
    public MantExpComplex conjugate_mutable() {

        mantissaImag = -mantissaImag;
        return this;

    }

    @Override
    public Complex toComplex() {
        //return new Complex(mantissaReal * MantExp.toExp(exp), mantissaImag * MantExp.toExp(exp));
        double d = MantExp.getMultiplier(exp);
        //return new Complex(MantExp.toDouble(mantissaReal, exp), MantExp.toDouble(mantissaImag, exp));
        return new Complex(mantissaReal * d, mantissaImag * d);
    }

    @Override
    public String toString() {
        return "" + mantissaReal + "*2^" + exp + " " + mantissaImag + "*2^" + exp + " " + toComplex();
    }

    public final MantExp getRe() {

        return new MantExp(exp, mantissaReal);

    }

    public final MantExp getIm() {

        return new MantExp(exp, mantissaImag);

    }

    public final double getMantissaReal() {
        return mantissaReal;
    }

    public final double getMantissaImag() {
        return mantissaImag;
    }

    public final long getExp() {

        return exp;

    }

    public void setExp(long exp) {
        this.exp = exp;
    }
    public void addExp(long exp) {
        this.exp += exp;
    }

    public void subExp(long exp) {
        this.exp -= exp;
        this.exp = this.exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : this.exp;
    }

    public static MantExp DiffAbs(MantExp c, MantExp d)
    {
        MantExp cd = c.add(d);
        if (c.compareTo(MantExp.ZERO) >= 0.0) {
            if (cd.compareTo(MantExp.ZERO) >= 0.0) {
                return d;
            }
            else {
                return d.negate().subtract_mutable(c.multiply2());
            }
        }
        else {
            if (cd.compareTo(MantExp.ZERO) > 0.0)  {
                return d.add(c.multiply2());
            }
            else {
                return d.negate();
            }
        }
    }

    public long log2normApprox() {
        double temp = mantissaReal * mantissaReal + mantissaImag * mantissaImag;
        long bits = Double.doubleToRawLongBits(temp);
        long exponent = ((bits & 0x7FF0000000000000L) >> 52) + MantExp.MIN_SMALL_EXPONENT;
        return (exponent / 2) + exp;
    }

    /*
     *  A*X + B*Y
     */
    public static final MantExpComplex AtXpBtY(MantExpComplex A, MantExpComplex X, MantExpComplex B, MantExpComplex Y) {

        return A.times(X).plus_mutable(B.times(Y));

    }

    /*
     *  A*X + B*Y
     */
    public static final MantExpComplex AtXpBtY(MantExpComplex A, MantExpComplex X, MantExpComplex B, MantExp Y) {

        return A.times(X).plus_mutable(B.times(Y));

    }

    /*
     *  A*X +Y
     */
    public static final MantExpComplex AtXpY(MantExpComplex A, MantExpComplex X, MantExpComplex Y) {

        return A.times(X).plus_mutable(Y);

    }

    /*
     *  A*X +Y
     */
    public static final MantExpComplex AtXpY(MantExpComplex A, MantExpComplex X, MantExp Y) {

        return A.times(X).plus_mutable(Y);

    }

    /*
     *  A*X
     */
    public static final MantExpComplex AtX(MantExpComplex A, MantExpComplex X) {

        return A.times(X);

    }


    public final boolean equals(MantExpComplex z2) {

        return z2.exp == exp && z2.mantissaReal == mantissaReal && z2.mantissaImag == mantissaImag;

    }

    public final void assign(MantExpComplex z) {
        mantissaReal = z.mantissaReal;
        mantissaImag = z.mantissaImag;
        exp = z.exp;
    }

    @Override
    public void set(GenericComplex za) {
        MantExpComplex z = (MantExpComplex) za;
        mantissaReal = z.mantissaReal;
        mantissaImag = z.mantissaImag;
        exp = z.exp;
    }

    @Override
    public MantExpComplex plus_mutable(GenericComplex v) {

        MantExpComplex value = (MantExpComplex) v;

        long expDiff = exp - value.exp;

        if(expDiff >= MantExp.EXPONENT_DIFF_IGNORED) {
            return this;
        } else if(expDiff >= 0) {
            double mul = MantExp.getMultiplier(-expDiff);
            mantissaReal = mantissaReal + value.mantissaReal * mul;
            mantissaImag = mantissaImag + value.mantissaImag * mul;
        }
        /*else if(expDiff == 0) {
            mantissaReal = mantissaReal + value.mantissaReal;
            mantissaImag = mantissaImag + value.mantissaImag;
        }*/
        else if(expDiff > MantExp.MINUS_EXPONENT_DIFF_IGNORED) {
            double mul = MantExp.getMultiplier(expDiff);
            exp = value.exp;
            mantissaReal = mantissaReal * mul + value.mantissaReal;
            mantissaImag =  mantissaImag * mul + value.mantissaImag;
        } else {
            exp = value.exp;
            mantissaReal = value.mantissaReal;
            mantissaImag = value.mantissaImag;
        }
        return this;

    }
}

