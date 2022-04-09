package fractalzoomer.core;

import org.apfloat.Apfloat;

public class MantExp {
    public static final long MIN_SMALL_EXPONENT = -1023;
    public static final long MIN_BIG_EXPONENT = Long.MIN_VALUE >> 3;
    public static final MantExp ZERO = new MantExp(MIN_BIG_EXPONENT, 0.0);
    public static final MantExp POINTTWOFIVE = new MantExp(0.25);
    public static final MantExp ONEPOINTFIVE = new MantExp(1.5);
    public static final MantExp ONE = new MantExp(1.0);
    public static final MantExp TWO = new MantExp(2.0);
    public static final MantExp THREE = new MantExp(3.0);
    public static final MantExp FOUR = new MantExp(4.0);
    public static final MantExp FIVE = new MantExp(5.0);
    public static final MantExp SIX = new MantExp(6.0);
    public static final MantExp SEVEN = new MantExp(7.0);
    public static final MantExp EIGHT = new MantExp(8.0);
    public static final MantExp NINE = new MantExp(9.0);
    public static final MantExp TEN = new MantExp(10.0);
    public static final MantExp TWELVE = new MantExp(12.0);
    public static final MantExp SIXTEEN = new MantExp(16.0);
    public static final MantExp EIGHTEEN = new MantExp(18.0);
    public static final MantExp TWENTY = new MantExp(20.0);
    public static final MantExp TWENTYFOUR = new MantExp(24.0);
    public static final MantExp TWENTYSEVEN = new MantExp(27.0);
    public static final MantExp THIRTY = new MantExp(30.0);
    public static final MantExp FOURTY = new MantExp(40.0);
    public static final MantExp FIFTYFOUR = new MantExp(54.0);
    public static final MantExp EIGHTYONE = new MantExp(81.0);
    public static final double LOG_10_2 = Math.log10(2);
    private static final double LN2 = Math.log(2);
    private static final double LN2_REC = 1.0/LN2;
    double mantissa;
    long exp;

    public static double[] twoPowExp;

    static {
        twoPowExp = new double[Double.MAX_EXPONENT-Double.MIN_EXPONENT+1];
        for (int i = Double.MIN_EXPONENT; i <= Double.MAX_EXPONENT; i++) {
            double d = Math.scalb(1.0, i);
            int index = i-Double.MIN_EXPONENT;
            twoPowExp[index] = d;
        }
    }

    public MantExp() {
        mantissa = 0.0;
        exp = MIN_BIG_EXPONENT;
    }

    public MantExp(MantExp other) {
        mantissa = other.mantissa;
        exp = other.exp;
    }

    public MantExp(double mantissa, long exp) {
        this.mantissa = mantissa;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;
    }

    public MantExp(long exp, double mantissa) {
        this.mantissa = mantissa;
        this.exp = exp;
    }

    public MantExp(double number) {
        if(number == 0) {
            mantissa = ZERO.mantissa;
            exp = ZERO.exp;
            return;
        }

        long bits = Double.doubleToRawLongBits(number);
        long f_exp = ((bits & 0x7FF0000000000000L) >> 52) + MIN_SMALL_EXPONENT;
        double f_val = Double.longBitsToDouble((bits & 0x800FFFFFFFFFFFFFL) | 0x3FF0000000000000L);

        mantissa = f_val;
        exp = f_exp;
    }

    public MantExp(Apfloat number) {

        if(number.compareTo(Apfloat.ZERO) == 0) {
            mantissa = 0.0;
            exp = MIN_BIG_EXPONENT;
            return;
        }

        Apfloat exp = MyApfloat.fp.multiply(new MyApfloat(number.scale() - 1), MyApfloat.RECIPROCAL_LOG_TWO_BASE_TEN);

        long long_exp = 0;

        double double_exp = exp.doubleValue();

        if(double_exp < 0) {
            long_exp = (long)(double_exp - 0.5);
            Apfloat twoToExp = MyApfloat.fp.pow(MyApfloat.TWO, -long_exp);
            mantissa = MyApfloat.fp.multiply(number, twoToExp).doubleValue();
        }
        else {
            long_exp = (long)(double_exp + 0.5);
            Apfloat twoToExp = MyApfloat.fp.pow(MyApfloat.TWO, long_exp);
            mantissa = MyApfloat.fp.divide(number, twoToExp).doubleValue();
        }

        this.exp = long_exp;

    }

    public void Reduce() {

        if(mantissa == 0) {
            return;
        }

        long bits = Double.doubleToRawLongBits(mantissa);
        long f_exp = ((bits & 0x7FF0000000000000L) >> 52) + MIN_SMALL_EXPONENT;
        double f_val = Double.longBitsToDouble((bits & 0x800FFFFFFFFFFFFFL) | 0x3FF0000000000000L);
        double temp_mantissa = f_val;
        long temp_exp = f_exp;

        if (mantissa != temp_mantissa) {
            exp += temp_exp;
            mantissa = temp_mantissa;
        }
    }

    /*public double toDouble() {
      return mantissa * toExp(exp);
    }*/

    /*public static double toExp(double exp) {

        if (exp <= MIN_SMALL_EXPONENT) {
            return 0.0;
        }
        else if (exp >= 1024) {
            return Math.pow(2, 1024);
        }
        return Math.pow(2, exp);

    }*/

    public static double getMultiplier(long scaleFactor) {
        if (scaleFactor <= MantExp.MIN_SMALL_EXPONENT) {
            return 0.0;
        }
        else if (scaleFactor >= 1024) {
            return Double.POSITIVE_INFINITY;
        }

        return twoPowExp[(int)scaleFactor - Double.MIN_EXPONENT];
    }

    public double toDouble()
    {
        return toDouble(mantissa, exp);
    }

    public double getMantissa() {return  mantissa;}

    public long getExp() { return exp;}

    public static double toDouble(double mantissa, long exp)
    {
        if(mantissa == 0) {
            return 0.0;
        }

        long bits = Double.doubleToRawLongBits(mantissa);
        long f_exp = ((bits & 0x7FF0000000000000L) >> 52) + MIN_SMALL_EXPONENT;

        long sum_exp = exp + f_exp;

        if (sum_exp <= MIN_SMALL_EXPONENT) {
            return 0.0;
        }
        else if (sum_exp >= 1024) {
            return mantissa / 0.0;
        }

        return Double.longBitsToDouble((bits & 0x800FFFFFFFFFFFFFL) | ((sum_exp - MIN_SMALL_EXPONENT) << 52));
    }

    public MantExp divide(MantExp factor) {
        double mantissa = this.mantissa / factor.mantissa;
        long exp = this.exp - factor.exp;

        MantExp res = new MantExp(mantissa, exp);

        /*double abs = Math.abs(res.mantissa);
        if (abs > 1e50 || abs < 1e-50) {
            res.Reduce();
        }*/

        return res;
    }

    public MantExp divide_mutable(MantExp factor) {
        double mantissa = this.mantissa / factor.mantissa;
        long exp = this.exp - factor.exp;

        this.mantissa = mantissa;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;

        /*double abs = Math.abs(mantissa);
        if (abs > 1e50 || abs < 1e-50) {
            Reduce();
        }*/

        return this;
    }

    public MantExp divide(double factor) {
        MantExp factorMant = new MantExp(factor);
        return divide(factorMant);
    }

    public MantExp divide_mutable(double factor) {
        MantExp factorMant = new MantExp(factor);
        return divide_mutable(factorMant);
    }

    public MantExp multiply(MantExp factor) {
        double mantissa = this.mantissa * factor.mantissa;
        long exp = this.exp + factor.exp;

        MantExp res = new MantExp(mantissa, exp);

        /*double abs = Math.abs(res.mantissa);
        if (abs > 1e50 || abs < 1e-50) {
            res.Reduce();
        }*/

        return res;
    }

    public MantExp multiply(double factor) {
        MantExp factorMant = new MantExp(factor);
        return multiply(factorMant);
    }

    public MantExp multiply_mutable(MantExp factor) {
        double mantissa = this.mantissa * factor.mantissa;
        long exp = this.exp + factor.exp;

        this.mantissa = mantissa;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;

        /*double abs = Math.abs(mantissa);
        if (abs > 1e50 || abs < 1e-50) {
            Reduce();
        }*/

        return this;
    }

    public MantExp multiply_mutable(double factor) {
        MantExp factorMant = new MantExp(factor);
        return multiply_mutable(factorMant);
    }

    public MantExp square() {
        double mantissa = this.mantissa * this.mantissa;
        long exp = this.exp << 1;

        MantExp res = new MantExp(mantissa, exp);

        /*double abs = Math.abs(res.mantissa);
        if (abs > 1e50 || abs < 1e-50) {
            res.Reduce();
        }*/

        return res;
    }

    public MantExp square_mutable() {
        double mantissa = this.mantissa * this.mantissa;
        long exp = this.exp << 1;

        this.mantissa = mantissa;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;

        /*double abs = Math.abs(mantissa);
        if (abs > 1e50 || abs < 1e-50) {
            Reduce();
        }*/

        return this;
    }

    public MantExp multiply05() {
        MantExp res = new MantExp(mantissa, exp - 1);
        return res;
    }

    public MantExp multiply05_mutable() {
        long exp = this.exp - 1;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;
        return this;
    }

    public MantExp multiply2() {
        MantExp res = new MantExp(mantissa, exp + 1);
        return res;
    }

    public MantExp multiply2_mutable() {
        long exp = this.exp + 1;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;
        return this;
    }

    public MantExp multiply4() {
        MantExp res = new MantExp(mantissa, exp + 2);
        return res;
    }

    public MantExp multiply4_mutable() {
        long exp = this.exp + 2;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;
        return this;
    }

    public MantExp add(MantExp value) {

        double temp_mantissa = 0;
        long temp_exp = exp;

        if (exp == value.exp) {
            temp_mantissa = mantissa + value.mantissa;
        }
        else if(exp > value.exp){
            //temp_mantissa = value.mantissa / toExp(exp - value.exp);
            temp_mantissa = toDouble(value.mantissa, value.exp - exp);
            temp_mantissa = mantissa + temp_mantissa;
        }
        else {
            //temp_mantissa  = mantissa / toExp(value.exp - exp);
            temp_mantissa = toDouble(mantissa, exp - value.exp);
            temp_exp = value.exp;
            temp_mantissa = temp_mantissa + value.mantissa;
        }

        return new MantExp(temp_mantissa, temp_exp);

    }

    public MantExp add_mutable(MantExp value) {

        double temp_mantissa = 0;
        long temp_exp = exp;

        if (exp == value.exp) {
            temp_mantissa = mantissa + value.mantissa;
        }
        else if(exp > value.exp){
            //temp_mantissa = value.mantissa / toExp(exp - value.exp);
            temp_mantissa = toDouble(value.mantissa, value.exp - exp);
            temp_mantissa = mantissa + temp_mantissa;
        }
        else {
            //temp_mantissa  = mantissa / toExp(value.exp - exp);
            temp_mantissa = toDouble(mantissa, exp - value.exp);
            temp_exp = value.exp;
            temp_mantissa = temp_mantissa + value.mantissa;
        }

        mantissa = temp_mantissa;
        exp = temp_exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : temp_exp;

        return this;

    }

    public MantExp subtract(MantExp value) {

        double temp_mantissa = 0;
        long temp_exp = exp;

        if (exp == value.exp) {
            temp_mantissa = mantissa - value.mantissa;
        }
        else if(exp > value.exp){
            //temp_mantissa = value.mantissa / toExp(exp - value.exp);
            temp_mantissa = toDouble(value.mantissa, value.exp - exp);
            temp_mantissa = mantissa - temp_mantissa;
        }
        else {
            //temp_mantissa  = mantissa / toExp(value.exp - exp);
            temp_mantissa = toDouble(mantissa, exp - value.exp);
            temp_exp = value.exp;
            temp_mantissa = temp_mantissa - value.mantissa;
        }

        return new MantExp(temp_mantissa, temp_exp);
    }

    public MantExp subtract_mutable(MantExp value) {

        double temp_mantissa = 0;
        long temp_exp = exp;

        if (exp == value.exp) {
            temp_mantissa = mantissa - value.mantissa;
        }
        else if(exp > value.exp){
            //temp_mantissa = value.mantissa / toExp(exp - value.exp);
            temp_mantissa = toDouble(value.mantissa, value.exp - exp);
            temp_mantissa = mantissa - temp_mantissa;
        }
        else {
            //temp_mantissa  = mantissa / toExp(value.exp - exp);
            temp_mantissa = toDouble(mantissa, exp - value.exp);
            temp_exp = value.exp;
            temp_mantissa = temp_mantissa - value.mantissa;
        }

        mantissa = temp_mantissa;
        exp = temp_exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : temp_exp;

        return this;
    }

    public MantExp add(double value) {
        return add(new MantExp(value));
    }

    public MantExp add_mutable(double value) {
        return add_mutable(new MantExp(value));
    }

    public MantExp subtract(double value) {
        return subtract(new MantExp(value));
    }

    public MantExp subtract_mutable(double value) {
        return subtract_mutable(new MantExp(value));
    }

    public MantExp negate() {
        return new MantExp(-mantissa, exp);
    }

    public MantExp negate_mutable() {
        mantissa = -mantissa;
        return this;
    }

    public MantExp abs() {
        return new MantExp(Math.abs(mantissa), exp);
    }

    public MantExp abs_mutable() {
        mantissa = Math.abs(mantissa);
        return this;
    }

    public int compareTo(MantExp compareTo) {

        Reduce();
        compareTo.Reduce();

        if(mantissa == 0 && compareTo.mantissa == 0) {
            return 0;
        }

        if(mantissa > 0) {
            if(compareTo.mantissa <= 0) {
                return 1;
            }
            else if(exp > compareTo.exp) {
                return 1;
            }
            else if(exp < compareTo.exp){
                return -1;
            }
            else {
                if(mantissa > compareTo.mantissa) {
                    return 1;
                }
                else if(mantissa < compareTo.mantissa) {
                    return - 1;
                }
                else {
                    return 0;
                }
            }
        }
        else {
            if(compareTo.mantissa > 0) {
                return -1;
            }
            else if(exp > compareTo.exp) {
                return -1;
            }
            else if(exp < compareTo.exp) {
                return 1;
            }
            else {
                if(mantissa > compareTo.mantissa) {
                    return 1;
                }
                else if(mantissa < compareTo.mantissa) {
                    return - 1;
                }
                else {
                    return 0;
                }
            }
        }
    }

    public int compareToReduced(MantExp compareToReduced) {

        if(mantissa == 0 && compareToReduced.mantissa == 0) {
            return 0;
        }

        if(mantissa > 0) {
            if(compareToReduced.mantissa <= 0) {
                return 1;
            }
            else if(exp > compareToReduced.exp) {
                return 1;
            }
            else if(exp < compareToReduced.exp){
                return -1;
            }
            else {
                if(mantissa > compareToReduced.mantissa) {
                    return 1;
                }
                else if(mantissa < compareToReduced.mantissa) {
                    return - 1;
                }
                else {
                    return 0;
                }
            }
        }
        else {
            if(compareToReduced.mantissa > 0) {
                return -1;
            }
            else if(exp > compareToReduced.exp) {
                return -1;
            }
            else if(exp < compareToReduced.exp) {
                return 1;
            }
            else {
                if(mantissa > compareToReduced.mantissa) {
                    return 1;
                }
                else if(mantissa < compareToReduced.mantissa) {
                    return - 1;
                }
                else {
                    return 0;
                }
            }
        }
    }

    @Override
    public String toString() {

        return "" + mantissa + "*2^" + exp + " = " + toDouble();
    }

    public double log10() {
        return Math.log10(mantissa) + exp * LOG_10_2;
    }

    public double log2() {
        return Math.log(mantissa) * LN2_REC + exp;
    }

    public long log2approx() {
        return MantExp.getExponent(mantissa) + exp;
    }

    public double log() {
        return Math.log(mantissa) + exp * LN2;
    }

    public static long getExponent(double val) {
        long bits = Double.doubleToRawLongBits(val);
        return  ((bits & 0x7FF0000000000000L) >> 52) + MIN_SMALL_EXPONENT;
    }

    public MantExp sqrt() {
        //double dexp = exp * 0.5;
        //return new MantExp(Math.sqrt(mantissa) * Math.pow(2, dexp - (int)dexp), (int)dexp);

        boolean isOdd = (exp & 1) != 0;
        return new MantExp(Math.sqrt(isOdd ? 2.0 * mantissa : mantissa), isOdd ? (exp - 1) >> 1 : exp >> 1);
    }

    public MantExp sqrt_mutable() {
        boolean isOdd = (exp & 1) != 0;

        mantissa = Math.sqrt(isOdd ? 2.0 * mantissa : mantissa);
        long exp = isOdd ? (this.exp - 1) >> 1 : this.exp >> 1;
        this.exp = exp < MantExp.MIN_BIG_EXPONENT ? MantExp.MIN_BIG_EXPONENT : exp;

        return this;
    }

    public static MantExp hypot(MantExp a, MantExp b) {
        return a.square().add_mutable(b.square()).sqrt_mutable();
    }

    public static MantExp hypot(double mRe, double mIm, long exp) {return new MantExp(exp, Math.hypot(mRe, mIm));}

    public static MantExp max(MantExp a, MantExp b) {
        return a.compareTo(b) > 0 ? a : b;
    }

    public static MantExp min(MantExp a, MantExp b) {
        return a.compareTo(b) < 0 ? a : b;
    }

    public MantExp pow(int n) {
        switch (n) {
            case 0: return new MantExp(ONE);
            case 1: return new MantExp(this);
            case 2: return square();
            case 3: return multiply(square());
            case 4: return square().square();
            case 5: return multiply(square().square());
            case 6: return multiply(square()).square();
            case 7: return multiply(multiply(square()).square());
            case 8: return square().square().square();
            default:
            {
                if(n < 0) {
                    return new MantExp();
                }
                MantExp y = new MantExp(ONE);
                MantExp x = new MantExp(this);
                while (n > 1)
                {
                    if ((n & 1) != 0)
                        y.multiply_mutable(x);

                    x.square_mutable();
                    n >>= 1;
                 }
                return x.multiply(y);
            }
        }
    }
 }