package fractalzoomer.core;

import java.math.BigInteger;
import org.apfloat.Apfloat;
import org.apfloat.internal.LongMemoryDataStorage;



import static org.apfloat.internal.LongRadixConstants.BASE_DIGITS;

public class BigIntNum {

    //This fixed point representation stores 64 bits as the integer part and fracDigits as the fractional part

    public static long getPrecision() {
        return (long)fracDigits * SHIFT32;
    }
    private static final int INTEGER_PART = 2;
    private static final int INTEGER_PART_BYTES = INTEGER_PART << 2;

    public static final int SHIFT8 = 8;
    public static final int SHIFT32 = 32;
    public static final int SHIFT32M1 = SHIFT32 - 1;

    public static final int MSHIFTP52 = 52 - SHIFT8;

    public static int fracDigits = 2;
    public static int totalDigits = fracDigits + INTEGER_PART;

    public static int totalDigitsByteCount = totalDigits << 2;


    private static BigIntNum MAX_VAL = new BigIntNum(Integer.MAX_VALUE);
    private static BigIntNum ONE = new BigIntNum(1);
    private static int fracDigitsBits = fracDigits << 5;
    public static int fracDigitsBitsp1 = fracDigitsBits + 1;
    private static BigIntNum ONESHIFTED = new BigIntNum(ONE.digits.shiftLeft(fracDigitsBits));

    public static int THREADS_THRESHOLD = 245;
    public static boolean use_threads = false;
   // public static boolean use_threads2 = false;

    private long mantissa;
    private boolean hasMantissa;
    private BigInteger digits;

    public static void reinitialize(double digits) {

        double res = digits / SHIFT32;
        int temp = (int) (res);

        if (temp == 0) {
            temp = 1;
        } else if (digits % SHIFT32 != 0) {
            temp++;
        }

        fracDigits = temp * TaskRender.BIGNUM_PRECISION_FACTOR;

        totalDigits = fracDigits + INTEGER_PART;
        totalDigitsByteCount = totalDigits << 2;

        fracDigitsBits = fracDigits << 5;

        fracDigitsBitsp1 = fracDigitsBits + 1;

        MAX_VAL = new BigIntNum(Integer.MAX_VALUE);
        ONE = new BigIntNum(1);
        ONESHIFTED = new BigIntNum(ONE.digits.shiftLeft(fracDigitsBits));

        use_threads = TaskRender.USE_THREADS_IN_BIGNUM_LIBS && fracDigits >= THREADS_THRESHOLD && Runtime.getRuntime().availableProcessors() >= 2;

        //use_threads2 = TaskRender.USE_THREADS_IN_BIGNUM_LIBS && fracDigits >= THREADS_THRESHOLD && Runtime.getRuntime().availableProcessors() >= 3;
    }

    public BigIntNum() {
        digits = new BigInteger(0, new byte[] {0});
        mantissa = 0;
        hasMantissa = false;
    }

    public BigIntNum(BigInteger val) {
        digits = val;
        mantissa = 0;
        hasMantissa = false;
    }

    public BigIntNum(BigIntNum other) {
        digits = other.digits;
        mantissa = other.mantissa;
        hasMantissa = other.hasMantissa;
    }

    public BigIntNum(String val) {
        this(new MyApfloat(val));
    }

    public BigIntNum(Apfloat val) {

        mantissa = 0;
        hasMantissa = false;

        Apfloat baseTwo = val.toRadix(2);

        int sign = baseTwo.getImpl().signum();

        digits = new BigInteger(new byte[] {0});

        LongMemoryDataStorage str = (LongMemoryDataStorage)baseTwo.getImpl().getDataStorage();

        if(str == null || sign == 0) { //for zero
            return;
        }

        long[] data = str.getData();

        byte[] bytes = new byte[totalDigitsByteCount];

        if(data.length == 1 && data[0] == 1) {
            bytes[7] = 1;
            digits = new BigInteger(sign, bytes);
            return;
        }

        int base_digits = BASE_DIGITS[2];

        int offset = (int)str.getOffset();

        int exponent = (int)baseTwo.getImpl().getExponent();

        if(exponent == 1) {
            int index = data.length >= 2 ? 1 : 0;
            long data_val = data[index];
            bytes[0] = (byte) (data_val >> 56);
            bytes[1] = (byte) (data_val >> 48);
            bytes[2] = (byte) (data_val >> 40);
            bytes[3] = (byte) (data_val >> 32);
            bytes[4] = (byte) (data_val >> 24);
            bytes[5] = (byte) (data_val >> 16);
            bytes[6] = (byte) (data_val >> 8);
            bytes[7] = (byte) (data_val);
            offset++;
        }
        else if(exponent == 2) {
            int index = data.length >= 2 ? 1 : 0;
            long data_val = data[index];
            long data_val2 = data[index + 1];

            long full_val = (data_val << base_digits) | data_val2;

            bytes[0] = (byte) (full_val >> 56);
            bytes[1] = (byte) (full_val >> 48);
            bytes[2] = (byte) (full_val >> 40);
            bytes[3] = (byte) (full_val >> 32);
            bytes[4] = (byte) (full_val >> 24);
            bytes[5] = (byte) (full_val >> 16);
            bytes[6] = (byte) (full_val >> 8);
            bytes[7] = (byte) (full_val);

            offset+=2;
        }

        int dataOffset = exponent < 0 ? Math.abs(exponent) * base_digits : 0;

        for(int i = dataOffset, j = 0; ; i++, j++) {

            int index2 = (i / SHIFT8) + INTEGER_PART_BYTES;
            int index = j / base_digits + offset;

            if(index2 >= bytes.length || index >= data.length) {
                break;
            }

            int shift = base_digits - (j % base_digits) - 1;
            int bit =  (int) ((data[index] >>> shift) & 0x1);

            bytes[index2] |= bit << (SHIFT8 - (i % SHIFT8) - 1);
        }

        digits = new BigInteger(sign, bytes);

    }

    public BigIntNum(double val) {

        mantissa = 0;
        hasMantissa = false;

        if(val == 0 ) {
            digits = new BigInteger(new byte[] {0});
            return;
        }


        byte[] bytes = new byte[totalDigitsByteCount];

        int sign = (int)Math.signum(val);
        val = Math.abs(val);

        long longVal = (long)val;

        bytes[0] = (byte) (longVal >> 56);
        bytes[1] = (byte) (longVal >> 48);
        bytes[2] = (byte) (longVal >> 40);
        bytes[3] = (byte) (longVal >> 32);
        bytes[4] = (byte) (longVal >> 24);
        bytes[5] = (byte) (longVal >> 16);
        bytes[6] = (byte) (longVal >> 8);
        bytes[7] = (byte) (longVal);

        double fractionalPart = val - longVal;

        if(fractionalPart != 0) {

            long bits = Double.doubleToRawLongBits(fractionalPart);
            long f_exp = ((bits & 0x7FF0000000000000L) >>> 52) - Double.MAX_EXPONENT;
            long mantissa = (bits & 0xFFFFFFFFFFFFFL) | (0x10000000000000L);

            if(f_exp < 0) {

                long posExp = -f_exp;

                int index = (int) (posExp / SHIFT8) + INTEGER_PART_BYTES;
                int bitOffset = (int) (posExp % SHIFT8);

                if (bitOffset == 0) {
                    index--;
                    bitOffset = SHIFT8;
                }

                int k;
                int i = index;
                for (k = 53; k > 0 && i < bytes.length; i++) {
                    int r;

                    if (k > SHIFT8) {

                        if (k == 53) {
                            r = MSHIFTP52 + bitOffset;
                            k -= 53 - r;
                        } else {
                            r = k - SHIFT8;
                            k -= SHIFT8;
                        }

                        bytes[i] = (byte) (mantissa >>> r);
                    } else {
                        r = SHIFT8 - k;
                        k -= k;
                        bytes[i] = (byte) (mantissa << r);
                    }

                }
            }

        }

        digits = new BigInteger(sign, bytes);

    }

    public BigIntNum(int val) {

        mantissa = 0;
        hasMantissa = false;

        if(val == 0) {
            digits = new BigInteger(new byte[] {0});
        }
        else if(val < 0) {
            val = -val;
            byte[] bytes = new byte[totalDigitsByteCount];
            bytes[4] = (byte) (val >> 24);
            bytes[5] = (byte) (val >> 16);
            bytes[6] = (byte) (val >> 8);
            bytes[7] = (byte) (val);
            digits = new BigInteger(-1, bytes);
        }
        else {

            byte[] bytes = new byte[totalDigitsByteCount];
            bytes[4] = (byte) (val >> 24);
            bytes[5] = (byte) (val >> 16);
            bytes[6] = (byte) (val >> 8);
            bytes[7] = (byte) (val);
            digits = new BigInteger(1, bytes);
        }
    }

    public BigIntNum(long val) {

        mantissa = 0;
        hasMantissa = false;

        if(val == 0) {
            digits = new BigInteger(new byte[] {0});
        }
        else if(val < 0) {
            val = -val;
            byte[] bytes = new byte[totalDigitsByteCount];
            bytes[0] = (byte) (val >> 56);
            bytes[1] = (byte) (val >> 48);
            bytes[2] = (byte) (val >> 40);
            bytes[3] = (byte) (val >> 32);
            bytes[4] = (byte) (val >> 24);
            bytes[5] = (byte) (val >> 16);
            bytes[6] = (byte) (val >> 8);
            bytes[7] = (byte) (val);
            digits = new BigInteger(-1, bytes);
        }
        else {

            byte[] bytes = new byte[totalDigitsByteCount];
            bytes[0] = (byte) (val >> 56);
            bytes[1] = (byte) (val >> 48);
            bytes[2] = (byte) (val >> 40);
            bytes[3] = (byte) (val >> 32);
            bytes[4] = (byte) (val >> 24);
            bytes[5] = (byte) (val >> 16);
            bytes[6] = (byte) (val >> 8);
            bytes[7] = (byte) (val);
            digits = new BigInteger(1, bytes);
        }
    }

    @Override
    public String toString() {
        return digits.toString();
    }

    public long getScale() {

        int sign = digits.signum();

        if(sign == 0) {
            return Long.MIN_VALUE;
        }

        BigInteger temp = digits;
        if(sign < 0) {
            temp = digits.abs();
        }

        return temp.bitLength() - fracDigitsBitsp1;

    }

   public double doubleValue() {

       int sign = digits.signum();

       if(sign == 0) {
           return 0;
       }

       BigInteger temp = digits;
       if(sign < 0) {
           temp = digits.abs();
       }

       int numBits = temp.bitLength();

       long scale = numBits - fracDigitsBitsp1;

       if (scale < Double.MIN_EXPONENT) { //accounting for +1
           return 0.0;
       }
       else if (scale >= Double.MAX_EXPONENT) {
           return sign == -1 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
       }

       long mantissa;
       if(hasMantissa) {
           mantissa = this.mantissa;
       }
       else {
           if (numBits >= 54) {
               mantissa = temp.shiftRight(numBits - 54).longValue();
               mantissa = ((mantissa >>> 1) & 0xFFFFFFFFFFFFFL) + (mantissa & 0x1); // + round
           } else {
               mantissa = temp.longValue();
               mantissa <<= (53 - numBits);
               mantissa &= 0xFFFFFFFFFFFFFL;
           }

           this.mantissa = mantissa;
           hasMantissa = true;
       }

       long finalScale = scale + (mantissa >>> 52);
       if (finalScale <= Double.MIN_EXPONENT) {
           return 0.0;
       }
       else if (finalScale >= Double.MAX_EXPONENT) {
           return sign == -1 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
       }

       // Bit 63 (the bit that is selected by the mask 0x8000000000000000L) represents the sign of the floating-point number.
       // Bits 62-52 (the bits that are selected by the mask 0x7ff0000000000000L) represent the exponent.
       // Bits 51-0 (the bits that are selected by the mask 0x000fffffffffffffL) represent the significand (sometimes called the mantissa) of the floating-point number.

       if(sign == -1) {
           return Double.longBitsToDouble( (0x8000000000000000L) | (mantissa & 0xFFFFFFFFFFFFFL) | ((finalScale + Double.MAX_EXPONENT) << 52));
       }
       else {
           return Double.longBitsToDouble((mantissa & 0xFFFFFFFFFFFFFL) | ((finalScale + Double.MAX_EXPONENT) << 52));
       }

    }

    public MantExp getMantExp() {

        int sign = digits.signum();

        if (sign == 0) {
            return new MantExp();
        }

        BigInteger temp = digits;
        if(sign < 0) {
            temp = digits.abs();
        }


        int numBits = temp.bitLength();

        long scale = numBits - fracDigitsBitsp1;


        long mantissa;
        if(hasMantissa) {
            mantissa = this.mantissa;
        }
        else {
            if (numBits >= 54) {
                mantissa = temp.shiftRight(numBits - 54).longValue();
                mantissa = ((mantissa >>> 1) & 0xFFFFFFFFFFFFFL) + (mantissa & 0x1);
            } else {
                mantissa = temp.longValue();
                mantissa <<= (53 - numBits);
                mantissa &= 0xFFFFFFFFFFFFFL;
            }
            this.mantissa = mantissa;
            hasMantissa = true;
        }

        // Bit 63 (the bit that is selected by the mask 0x8000000000000000L) represents the sign of the floating-point number.
        // Bits 62-52 (the bits that are selected by the mask 0x7ff0000000000000L) represent the exponent.
        // Bits 51-0 (the bits that are selected by the mask 0x000fffffffffffffL) represent the significand (sometimes called the mantissa) of the floating-point number.

        int exp = (int)(scale + (mantissa >>> 52));
        double mantissaDouble;

        if(sign == -1) {
            mantissaDouble = Double.longBitsToDouble( (0x8000000000000000L) | (mantissa & 0xFFFFFFFFFFFFFL) | (0x3FF0000000000000L));
        }
        else {
            mantissaDouble = Double.longBitsToDouble((mantissa & 0xFFFFFFFFFFFFFL) | (0x3FF0000000000000L));
        }

        return new MantExp(exp, mantissaDouble);



    }

    public BigIntNum add(BigIntNum b) {
        return new BigIntNum(digits.add(b.digits));
    }

    public BigIntNum add(int number) {
        return add(new BigIntNum(number));
    }

    public BigIntNum add(double number) {
        return add(new BigIntNum(number));
    }

    public BigIntNum sub(BigIntNum b) {
        return new BigIntNum(digits.subtract(b.digits));
    }

    public BigIntNum sub(int b) {
        return sub(new BigIntNum(b));
    }

    public BigIntNum sub(double b) {
        return sub(new BigIntNum(b));
    }

    public BigIntNum mult(BigIntNum b) {
        return new BigIntNum(digits.multiply(b.digits).shiftRight(fracDigitsBits));
    }

    public BigIntNum mult(int number) { return mult(new BigIntNum(number)); }

    public BigIntNum mult(double number) { return mult(new BigIntNum(number)); }

    public BigIntNum divide(BigIntNum b) {
        return new BigIntNum(digits.shiftLeft(fracDigitsBits).divide(b.digits));
    }

    public BigIntNum divide(int b) {
        return divide(new BigIntNum(b));
    }

    public BigIntNum divide(double b) {
        return divide(new BigIntNum(b));
    }

    public BigIntNum reciprocal() {
        return new BigIntNum(ONESHIFTED.digits.divide(digits));
    }

    public BigIntNum mult2() {
        return new BigIntNum(digits.shiftLeft(1));
    }

    public BigIntNum mult4() {
        return new BigIntNum(digits.shiftLeft(2));
    }

    public BigIntNum divide2() {
        return new BigIntNum(digits.shiftRight(1));
    }

    public BigIntNum divide4() {
        return new BigIntNum(digits.shiftRight(2));
    }

    public BigIntNum square() {
        return new BigIntNum(digits.multiply(digits).shiftRight(fracDigitsBits));
    }

    public int compare(BigIntNum other) {
        return this.digits.compareTo(other.digits);
    }

    public static BigIntNum getMax() {
        return MAX_VAL;
    }

    public BigIntNum mult2toi(long n) {
        return new BigIntNum(digits.shiftLeft((int)n));
    }

    public BigIntNum div2toi(long n) {
        return new BigIntNum(digits.shiftRight((int)n));
    }

    public BigIntNum abs() {
        return new BigIntNum(digits.abs());
    }

    //Was added in java9
    public BigIntNum sqrt() {

        int sign = digits.signum();
        if(sign == 0) {
            return new BigIntNum();
        }
        if(sign < 0) {
            return new BigIntNum();
        }

        BigIntNum oneFourth = new BigIntNum(0.25);

        BigIntNum a = new BigIntNum(this);
        long divisions = 0;
        long multiplications = 0;
        if(a.compare(ONE) > 0) { //scale it down between 1 and 1/4
            do {
                a = a.divide4();
                divisions++;
            } while (a.compare(ONE) > 0);
        }
        else if(a.compare(oneFourth) < 0) {
            do {
                a = a.mult4();
                multiplications++;
            } while (a.compare(oneFourth) < 0);
        }

        BigIntNum oneHalf = new BigIntNum(1.5);
        BigIntNum aHalf = a.divide2(); // a / 2

        BigIntNum x = new BigIntNum(1 / Math.sqrt(a.doubleValue())); // set the initial value to an approximation of 1 /sqrt(a)

        //Newton steps
        BigIntNum newX = x.mult(oneHalf.sub(aHalf.mult(x.square()))); // x = (3/2 - (a/2)*x^2)*x

        byte[] bytes = new byte[totalDigitsByteCount];
        bytes[totalDigitsByteCount - 1] = 0x3;
        BigIntNum epsilon = new BigIntNum(new BigInteger(1, bytes));

        int iter = 0;
        while (newX.sub(x).abs().compare(epsilon) >= 0 && iter < BigNum.SQRT_MAX_ITERATIONS) {
            x = newX;
            newX = x.mult(oneHalf.sub(aHalf.mult(x.square())));
            iter++;
        }

        BigIntNum sqrta = newX.mult(a); //sqrt(a) = a * (1 /sqrt(a));
        if(multiplications > 0) { //scale it up again
            sqrta = sqrta.div2toi(multiplications);
        }
        else if(divisions > 0) {
            sqrta = sqrta.mult2toi(divisions);
        }

        return sqrta;
    }

    public BigIntNum shift2toi(long power) {
        if(power > 0) {
            return mult2toi(power);
        }
        else if(power < 0) {
            return div2toi(-power);
        }

        return new BigIntNum(this);
    }

    public static BigIntNum max(BigIntNum a, BigIntNum b) {
        return a.compare(b) > 0 ? a : b;
    }

    public static BigIntNum min(BigIntNum a, BigIntNum b) {
        return a.compare(b) < 0 ? a : b;
    }

    public BigIntNum negate() {
        return new BigIntNum(digits.negate());
    }

    public boolean isPositive() {
        return digits.signum() == 1;
    }
    public boolean isZero() {
        return digits.signum() == 0;
    }

    public boolean isOne() {
        return digits.compareTo(ONE.digits) == 0;
    }

    public boolean isNegative() {
        return digits.signum() == -1;
    }

    public int getBitLength() {
        return digits.bitLength();
    }

    public String bits() {

        String value = "";

        BigInteger nv = digits.abs();

        for(int i = 0; i < fracDigits; i++) {
            int v = nv.intValue();

            String temp = "";
            for (int j = SHIFT32M1; j >= 0; j--) {
                temp += "" + ((v >>> j) & 0x1);
            }

            value = temp + value;

            nv = nv.shiftRight(SHIFT32);
        }

        value = "." + value;

        for(int i = 0; i < INTEGER_PART; i++) {
            int v = nv.intValue();

            String temp = "";
            for (int j = SHIFT32M1; j >= 0; j--) {
                temp += "" + ((v >>> j) & 0x1);
            }

            value = temp + value;

            nv = nv.shiftRight(SHIFT32);
        }

        if(digits.signum() == -1) {
            value = "-" + value;
        }

        return value;

    }

    public Apfloat toApfloat() { return new MyApfloat(bits(), 2).toRadix(10);}

    public int signum() {
        return digits.signum();
    }

}
