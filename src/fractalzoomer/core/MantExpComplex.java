package fractalzoomer.core;


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

    public MantExpComplex(MantExpComplex other) {
        this.mantissaReal = other.mantissaReal;
        this.mantissaImag = other.mantissaImag;
        this.exp = other.exp;
    }

    public MantExpComplex(Complex c) {
        setMantexp(new MantExp(c.getRe()), new MantExp(c.getIm()));
    }

    public MantExpComplex(BigComplex c) {
        setMantexp(new MantExp(c.getRe()), new MantExp(c.getIm()));
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

        return new MantExpComplex(temp_mantissa_real, temp_mantissa_imag, temp_exp);

    }

    public MantExpComplex plus_mutable(MantExpComplex value) {

        double temp_mantissa_real = 0;
        double temp_mantissa_imag = 0;
        long temp_exp = 0;

        if(exp == value.exp) {
            temp_exp = exp;
            temp_mantissa_real = mantissaReal + value.mantissaReal;
            temp_mantissa_imag = mantissaImag + value.mantissaImag;
        }
        else if (exp > value.exp) {
            temp_exp = exp;

            long diff = value.exp - exp;
            //temp_mantissa_real = mantissaReal + MantExp.toDouble(value.mantissaReal, diff);
            //temp_mantissa_imag = mantissaImag + MantExp.toDouble(value.mantissaImag, diff);
            double d = MantExp.getMultiplier(diff);
            temp_mantissa_real = mantissaReal + value.mantissaReal * d;
            temp_mantissa_imag = mantissaImag + value.mantissaImag * d;

        }
        else {
            temp_exp = value.exp;

            long diff = exp - value.exp;
            //temp_mantissa_real = MantExp.toDouble(mantissaReal, diff) + value.mantissaReal;
            //temp_mantissa_imag = MantExp.toDouble(mantissaImag, diff) + value.mantissaImag;
            double d = MantExp.getMultiplier(diff);
            temp_mantissa_real = mantissaReal * d + value.mantissaReal;
            temp_mantissa_imag = mantissaImag * d + value.mantissaImag;

        }

        mantissaReal = temp_mantissa_real;
        mantissaImag = temp_mantissa_imag;
        exp = temp_exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : temp_exp;

        return this;

    }


    public MantExpComplex times(MantExpComplex factor) {
        double tempMantissaReal = (mantissaReal * factor.mantissaReal) - (mantissaImag * factor.mantissaImag);

        double tempMantissaImag = (mantissaReal * factor.mantissaImag) + (mantissaImag * factor.mantissaReal);

        MantExpComplex p = new MantExpComplex(tempMantissaReal, tempMantissaImag, exp + factor.exp);

        /*double absRe = Math.abs(tempMantissaReal);
        double absIm = Math.abs(tempMantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            p.Reduce();
        }*/

        return p;
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

        MantExpComplex p = new MantExpComplex(tempMantissaReal, tempMantissaImag, exp + factor.exp);

        /*double absRe = Math.abs(tempMantissaReal);
        double absIm = Math.abs(tempMantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            p.Reduce();
        }*/

        return p;
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

    public MantExpComplex times2() {
        MantExpComplex p = new MantExpComplex(mantissaReal, mantissaImag, exp + 1);
        return p;
    }

    public MantExpComplex times2_mutable() {

        long exp = this.exp + 1;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;
        return this;

    }

    public MantExpComplex times4() {
        MantExpComplex p = new MantExpComplex(mantissaReal, mantissaImag, exp + 2);
        return p;
    }

    public MantExpComplex times4_mutable() {

        long exp = this.exp + 2;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;
        return this;

    }

    public MantExpComplex plus(double real) {
        return plus(new MantExp(real));
    }

    public MantExpComplex plus_mutable(double real) {
        return plus_mutable(new MantExp(real));
    }

    public MantExpComplex plus(MantExp real) {

        double temp_mantissa_real = 0;
        double temp_mantissa_imag;
        long temp_exp = 0;

        if(exp == real.exp) {
            temp_exp = exp;
            temp_mantissa_real = mantissaReal + real.mantissa;
            temp_mantissa_imag = mantissaImag;
        }
        else if (exp > real.exp) {
            //double temp = MantExp.toExp(exp - real.exp);
            temp_exp = exp;
            //temp_mantissa_real = mantissaReal + MantExp.toDouble(real.mantissa, real.exp - exp);
            //temp_mantissa_real = mantissaReal + real.mantissa / temp;
            temp_mantissa_real = mantissaReal + real.mantissa * MantExp.getMultiplier(real.exp - exp);
            temp_mantissa_imag = mantissaImag;

        }
        else {
            //double temp = MantExp.toExp(real.exp - exp);
            temp_exp = real.exp;
            //temp_mantissa_real  = mantissaReal / temp + real.mantissa;
            //temp_mantissa_imag = mantissaImag / temp;
            long diff = exp - real.exp;
            //temp_mantissa_real = MantExp.toDouble(mantissaReal, diff) + real.mantissa;
            //temp_mantissa_imag = MantExp.toDouble(mantissaImag, diff);
            double d = MantExp.getMultiplier(diff);
            temp_mantissa_real = mantissaReal * d + real.mantissa;
            temp_mantissa_imag = mantissaImag * d;
        }

        return new MantExpComplex(temp_mantissa_real, temp_mantissa_imag, temp_exp);
    }

    public MantExpComplex plus_mutable(MantExp real) {

        double temp_mantissa_real = 0;
        double temp_mantissa_imag;
        long temp_exp = 0;

        if(exp == real.exp) {
            temp_exp = exp;
            temp_mantissa_real = mantissaReal + real.mantissa;
            temp_mantissa_imag = mantissaImag;
        }
        else if (exp > real.exp) {
            temp_exp = exp;
            //temp_mantissa_real = mantissaReal + MantExp.toDouble(real.mantissa, real.exp - exp);
            temp_mantissa_real = mantissaReal + real.mantissa * MantExp.getMultiplier(real.exp - exp);
            temp_mantissa_imag = mantissaImag;

        }
        else {
            temp_exp = real.exp;
            long diff = exp - real.exp;
            //temp_mantissa_real = MantExp.toDouble(mantissaReal, diff) + real.mantissa;
            //temp_mantissa_imag = MantExp.toDouble(mantissaImag, diff);
            double d = MantExp.getMultiplier(diff);
            temp_mantissa_real = mantissaReal * d + real.mantissa;
            temp_mantissa_imag = mantissaImag * d;
        }

        mantissaReal = temp_mantissa_real;
        mantissaImag = temp_mantissa_imag;
        exp =  temp_exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : temp_exp;
        return this;
    }

    public MantExpComplex sub(MantExpComplex value) {

        double temp_mantissa_real = 0;
        double temp_mantissa_imag = 0;
        long temp_exp = 0;

        if(exp == value.exp) {
            temp_exp = exp;
            temp_mantissa_real = mantissaReal - value.mantissaReal;
            temp_mantissa_imag = mantissaImag - value.mantissaImag;
        }
        else if (exp > value.exp) {
            //double temp = MantExp.toExp(exp - value.exp);
            temp_exp = exp;
            //temp_mantissa_real = mantissaReal - value.mantissaReal / temp;
            //temp_mantissa_imag = mantissaImag - value.mantissaImag / temp;
            long diff = value.exp - exp;
            //temp_mantissa_real = mantissaReal - MantExp.toDouble(value.mantissaReal, diff);
            //temp_mantissa_imag = mantissaImag - MantExp.toDouble(value.mantissaImag, diff);
            double d = MantExp.getMultiplier(diff);
            temp_mantissa_real = mantissaReal - value.mantissaReal * d;
            temp_mantissa_imag = mantissaImag - value.mantissaImag * d;

        }
        else {
            //double temp = MantExp.toExp(value.exp - exp);
            temp_exp = value.exp;
            //temp_mantissa_real  = mantissaReal / temp - value.mantissaReal;
            //temp_mantissa_imag  = mantissaImag / temp - value.mantissaImag;
            long diff = exp - value.exp;
            //temp_mantissa_real = MantExp.toDouble(mantissaReal, diff) - value.mantissaReal;
            //temp_mantissa_imag = MantExp.toDouble(mantissaImag, diff) - value.mantissaImag;
            double d = MantExp.getMultiplier(diff);
            temp_mantissa_real = mantissaReal * d - value.mantissaReal;
            temp_mantissa_imag = mantissaImag * d - value.mantissaImag;
        }

        return new MantExpComplex(temp_mantissa_real, temp_mantissa_imag, temp_exp);

    }

    public MantExpComplex sub_mutable(MantExpComplex value) {

        double temp_mantissa_real = 0;
        double temp_mantissa_imag = 0;
        long temp_exp = 0;

        if(exp == value.exp) {
            temp_exp = exp;
            temp_mantissa_real = mantissaReal - value.mantissaReal;
            temp_mantissa_imag = mantissaImag - value.mantissaImag;
        }
        else if (exp > value.exp) {
            temp_exp = exp;
            long diff = value.exp - exp;
            //temp_mantissa_real = mantissaReal - MantExp.toDouble(value.mantissaReal, diff);
            //temp_mantissa_imag = mantissaImag - MantExp.toDouble(value.mantissaImag, diff);
            double d = MantExp.getMultiplier(diff);
            temp_mantissa_real = mantissaReal - value.mantissaReal * d;
            temp_mantissa_imag = mantissaImag - value.mantissaImag * d;
        }
        else {
            temp_exp = value.exp;
            long diff = exp - value.exp;
            //temp_mantissa_real = MantExp.toDouble(mantissaReal, diff) - value.mantissaReal;
            //temp_mantissa_imag = MantExp.toDouble(mantissaImag, diff) - value.mantissaImag;
            double d = MantExp.getMultiplier(diff);
            temp_mantissa_real = mantissaReal * d - value.mantissaReal;
            temp_mantissa_imag = mantissaImag * d - value.mantissaImag;
        }

        mantissaReal = temp_mantissa_real;
        mantissaImag = temp_mantissa_imag;
        exp =  temp_exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : temp_exp;
        return this;

    }

    public MantExpComplex sub(MantExp real) {

        double temp_mantissa_real = 0;
        double temp_mantissa_imag;
        long temp_exp = 0;

        if(exp == real.exp) {
            temp_exp = exp;
            temp_mantissa_real = mantissaReal - real.mantissa;
            temp_mantissa_imag = mantissaImag;
        }
        else if (exp > real.exp) {
            //double temp = MantExp.toExp(exp - real.exp);
            temp_exp = exp;
            //temp_mantissa_real = mantissaReal - real.mantissa / temp;
            //temp_mantissa_real = mantissaReal - MantExp.toDouble(real.mantissa, real.exp - exp);
            temp_mantissa_real = mantissaReal - real.mantissa *  MantExp.getMultiplier(real.exp - exp);
            temp_mantissa_imag = mantissaImag;

        }
        else {
            //double temp = MantExp.toExp(real.exp - exp);
            temp_exp = real.exp;
            //temp_mantissa_real  = mantissaReal / temp - real.mantissa;
            //temp_mantissa_imag = mantissaImag / temp;
            long diff = exp - real.exp;
            //temp_mantissa_real = MantExp.toDouble(mantissaReal, diff) - real.mantissa;
            //temp_mantissa_imag = MantExp.toDouble(mantissaImag, diff);
            double d = MantExp.getMultiplier(diff);
            temp_mantissa_real = mantissaReal * d - real.mantissa;
            temp_mantissa_imag = mantissaImag * d;
        }

        return new MantExpComplex(temp_mantissa_real, temp_mantissa_imag, temp_exp);
    }

    public MantExpComplex sub_mutable(MantExp real) {

        double temp_mantissa_real = 0;
        double temp_mantissa_imag;
        long temp_exp = 0;

        if(exp == real.exp) {
            temp_exp = exp;
            temp_mantissa_real = mantissaReal - real.mantissa;
            temp_mantissa_imag = mantissaImag;
        }
        else if (exp > real.exp) {
            //double temp = MantExp.toExp(exp - real.exp);
            temp_exp = exp;
            //temp_mantissa_real = mantissaReal - real.mantissa / temp;
            //temp_mantissa_real = mantissaReal - MantExp.toDouble(real.mantissa, real.exp - exp);
            temp_mantissa_real = mantissaReal - real.mantissa * MantExp.getMultiplier(real.exp - exp);
            temp_mantissa_imag = mantissaImag;

        }
        else {
            //double temp = MantExp.toExp(real.exp - exp);
            temp_exp = real.exp;
            //temp_mantissa_real  = mantissaReal / temp - real.mantissa;
            //temp_mantissa_imag = mantissaImag / temp;
            long diff = exp - real.exp;
            //temp_mantissa_real = MantExp.toDouble(mantissaReal, diff) - real.mantissa;
            //temp_mantissa_imag = MantExp.toDouble(mantissaImag, diff);
            double d = MantExp.getMultiplier(diff);
            temp_mantissa_real = mantissaReal * d - real.mantissa;
            temp_mantissa_imag = mantissaImag * d;
        }

        mantissaReal = temp_mantissa_real;
        mantissaImag = temp_mantissa_imag;
        exp =  temp_exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : temp_exp;
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

        long expDiffRe = Math.getExponent(mantissaReal);
        long expDiffIm = Math.getExponent(mantissaImag);

        long expDiff = Math.max(expDiffRe, expDiffIm);
        long expCombined = exp + expDiff;
        double mul = MantExp.getMultiplier(exp-expCombined);
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

    public MantExpComplex square() {
        double temp = mantissaReal * mantissaImag;
        MantExpComplex p  = new MantExpComplex((mantissaReal + mantissaImag) * (mantissaReal - mantissaImag), temp + temp, exp << 1);

        /*double absRe = Math.abs(p.mantissaReal);
        double absIm = Math.abs(p.mantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            p.Reduce();
        }*/

        return p;
    }

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

    public MantExpComplex cube() {
        double temp = mantissaReal * mantissaReal;
        double temp2 = mantissaImag * mantissaImag;

        MantExpComplex p  = new MantExpComplex(mantissaReal * (temp - 3 * temp2), mantissaImag * (3 * temp - temp2), exp + (exp << 1));

        /*double absRe = Math.abs(p.mantissaReal);
        double absIm = Math.abs(p.mantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            p.Reduce();
        }*/

        return p;
    }

    public MantExpComplex cube_mutable() {
        double temp = mantissaReal * mantissaReal;
        double temp2 = mantissaImag * mantissaImag;

        long exp = this.exp + (this.exp << 1);
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

    public MantExpComplex fourth() {
        double temp = mantissaReal * mantissaReal;
        double temp2 = mantissaImag * mantissaImag;

        MantExpComplex p  = new MantExpComplex(temp * (temp - 6 * temp2) + temp2 * temp2, 4 * mantissaReal * mantissaImag * (temp - temp2), exp << 2);

        /*double absRe = Math.abs(p.mantissaReal);
        double absIm = Math.abs(p.mantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            p.Reduce();
        }*/

        return p;
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

    public MantExp norm_squared() {
        return new MantExp(mantissaReal * mantissaReal + mantissaImag * mantissaImag, exp << 1);
    }

    public MantExp norm() {
        return new MantExp(Math.sqrt(mantissaReal * mantissaReal + mantissaImag * mantissaImag), exp);
    }

    public final MantExpComplex divide(MantExpComplex factor) {

        double temp = factor.mantissaReal * factor.mantissaReal + factor.mantissaImag * factor.mantissaImag;

        double tempMantissaReal = (mantissaReal * factor.mantissaReal) + (mantissaImag * factor.mantissaImag) / temp;

        double tempMantissaImag = (mantissaImag * factor.mantissaReal) - (mantissaReal * factor.mantissaImag)  / temp;

        MantExpComplex p = new MantExpComplex(tempMantissaReal , tempMantissaImag, exp - factor.exp);

        /*double absRe = Math.abs(tempMantissaReal);
        double absIm = Math.abs(tempMantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            p.Reduce();
        }*/

        return p;
    }

    public final MantExpComplex divide_mutable(MantExpComplex factor) {

        double temp = factor.mantissaReal * factor.mantissaReal + factor.mantissaImag * factor.mantissaImag;

        double tempMantissaReal = (mantissaReal * factor.mantissaReal) + (mantissaImag * factor.mantissaImag) / temp;

        double tempMantissaImag = (mantissaImag * factor.mantissaReal) - (mantissaReal * factor.mantissaImag)  / temp;

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

        MantExpComplex p = new MantExpComplex(mantissaReal / real.mantissa, mantissaImag / real.mantissa, exp - real.exp);

        /*double absRe = Math.abs(p.mantissaReal);
        double absIm = Math.abs(p.mantissaImag);
        if (absRe > 1e50 || absIm > 1e50 || absRe < 1e-50 || absIm < 1e-50) {
            p.Reduce();
        }*/

        return p;
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

    public MantExpComplex negative() {

        return new MantExpComplex(-mantissaReal, -mantissaImag, exp);

    }

    public MantExpComplex negative_mutable() {

        mantissaReal = -mantissaReal;
        mantissaImag = -mantissaImag;
        return this;

    }

    public MantExpComplex abs() {

        return new MantExpComplex(Math.abs(mantissaReal), Math.abs(mantissaImag), exp);

    }

    public MantExpComplex abs_mutable() {

        mantissaReal = Math.abs(mantissaReal);
        mantissaImag = Math.abs(mantissaImag);
        return this;

    }

    public MantExpComplex conjugate() {

        return new MantExpComplex(mantissaReal, -mantissaImag, exp);

    }

    public MantExpComplex conjugate_mutable() {

        mantissaImag = -mantissaImag;
        return this;

    }

    public Complex toComplex() {
        //return new Complex(mantissaReal * MantExp.toExp(exp), mantissaImag * MantExp.toExp(exp));
        double d = MantExp.getMultiplier(exp);
        //return new Complex(MantExp.toDouble(mantissaReal, exp), MantExp.toDouble(mantissaImag, exp));
        return new Complex(mantissaReal * d, mantissaImag * d);
    }

    public String toString() {
        return "" + mantissaReal + "*2^" + exp + " " + mantissaImag + "*2^" + exp + " " + toComplex();
    }

    public final MantExp getRe() {

        return new MantExp(mantissaReal, exp);

    }

    public final MantExp getIm() {

        return new MantExp(mantissaImag, exp);

    }

    public final long getExp() {

        return exp;

    }

    public static MantExp DiffAbs(MantExp c, MantExp d)
    {
        MantExp cd = c.add(d);
        if (c.compareTo(MantExp.ZERO) >= 0.0)
            if (cd.compareTo(MantExp.ZERO) >= 0.0)      return d;
            else      return d.negate().subtract_mutable(c.multiply2());
        else
            if (cd.compareTo(MantExp.ZERO) > 0.0)      return d.add(c.multiply2());
            else      return d.negate();
    }

    public long log2normApprox() {
        return (Math.getExponent(mantissaReal*mantissaReal + mantissaImag*mantissaImag) >> 1) + exp;
    }

    public static void main(String[] args) {

        Complex c1 = new Complex(3123423.42342, -482394.32142134);
        Complex c2 = new Complex(-2313, -0.5);


        MantExpComplex mc11 = new MantExpComplex(c1);
        MantExpComplex mc21 = new MantExpComplex(c2);

        //System.out.println(mc11.plus(mc21) + " " + mc11.plus2(mc21));
        //System.out.println(mc21.plus(mc11) + " " + mc21.plus2(mc11));

        long runs = Long.MAX_VALUE >> 25;


        for(long i = 0; i < runs; i++) {
            mc11.plus(mc21);
            //mc11.plus2(mc21);
        }

        for(long i = 0; i < runs; i++) {
            mc11.plus(mc21);
            //mc11.plus2(mc21);
        }






        long time = System.currentTimeMillis();
        for(long i = 0; i < runs; i++) {
            mc11.plus(mc21);
            mc21.plus(mc11);
        }
        System.out.println(System.currentTimeMillis() - time);


        time = System.currentTimeMillis();
        for(long i = 0; i < runs; i++) {
            //mc11.plus2(mc21);
            //mc21.plus2(mc11);
        }
        System.out.println(System.currentTimeMillis() - time);


    }

}

