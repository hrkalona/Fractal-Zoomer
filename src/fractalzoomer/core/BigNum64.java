package fractalzoomer.core;

import org.apfloat.Apfloat;
import org.apfloat.internal.LongMemoryDataStorage;

import java.util.Arrays;

import static org.apfloat.internal.LongRadixConstants.BASE_DIGITS;

public class BigNum64 extends BigNum {

    public static long getPrecision() {
        return (long)fracDigits * SHIFT;
    }
    public static String getName() {
        return "Built-in (64)";
    }

    public long[] digits;

    private static int fracDigits;
    private static int fracDigitsm1;
    private static int fracDigitsp1;
    private static int fracDigitsHalf;

    private static int fracDigits2;
    private static boolean useToDouble2;
    private static boolean useKaratsuba;
    private static int initialLength;
    private static boolean evenFracDigits;

    protected static boolean use_threads;
    public static final int SHIFT = 64;
    public static final long MASKD0 = 0x7FFFFFFFFFFFFFFFL;
    public static final long MASKI = 0xFFFFFFFFL;
    public static final int SHIFTI = 32;
    public static final int SHIFTIM1 = SHIFTI - 1;
    public static final int SHIFTM1 = SHIFT - 1;
    public static final int SHIFTM2 = SHIFT - 2;
    public static final int SHIFTM52 = SHIFT - 52;
    private static final double TWO_TO_SHIFT2 = Math.pow(2,-(SHIFT >>> 1));


    public static void reinitialize(double digits) {

        double res = digits / SHIFT;
        int temp = (int) (res);

        if (temp == 0) {
            temp = 1;
        } else if (digits % SHIFT != 0) {
            temp++;
        }

        fracDigits = temp * TaskRender.BIGNUM_PRECISION_FACTOR;

       initializeInternal();
    }

    public static void reinitializeTest(double digits) {
        fracDigits = ((int)(digits / SHIFT) + 1) * TaskRender.BIGNUM_PRECISION_FACTOR;
        initializeInternal();
    }

    private static void initializeInternal() {
        fracDigitsm1 = fracDigits - 1;
        fracDigitsp1 = fracDigits + 1;
        //useToDouble2 = fracDigits > 80;
        useKaratsuba = false;
        fracDigitsHalf = fracDigits >> 1;
        fracDigits2 = fracDigits << 1;
        initialLength = fracDigitsHalf - ((fracDigitsp1) % 2);
        evenFracDigits = (fracDigits & 1) == 0;

        use_threads = false;
    }

    private static BigNum64 getMax() {
        BigNum64 n = new BigNum64();
        n.digits[0] = MASKD0;
        n.sign = 1;
        return n;
    }

    protected BigNum64() {
        digits = new long[fracDigitsp1];
        sign = 0;
        isOne = false;
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    protected BigNum64(BigNum64 other) {
        digits = Arrays.copyOf(other.digits, other.digits.length);
        sign = other.sign;
        isOne = other.isOne;
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    protected BigNum64(String val) {
        this(new MyApfloat(val));
    }

    protected BigNum64(int val) {
        digits = new long[fracDigitsp1];
        digits[0] = Math.abs(val);
        isOne = val == 1 || val == -1;
        sign = (int)Math.signum(val);
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }
    protected BigNum64(double val) {

        digits = new long[fracDigitsp1];

        scale = 0;
        offset = -1;
        bitOffset = -1;

        if (val == 0) {
            sign = 0;
            isOne = false;
            return;
        } else if (Math.abs(val) == 1) {
            isOne = true;
            sign = (int) val;
            digits[0] = 1;
            return;
        }

        sign = (int) Math.signum(val);
        val = Math.abs(val);
        digits[0] = (long) (val);
        double fractionalPart = val - (long) val;

        if (fractionalPart != 0) {

            long bits = Double.doubleToRawLongBits(fractionalPart);
            long f_exp = ((bits & 0x7FF0000000000000L) >>> 52) - Double.MAX_EXPONENT;
            long mantissa = (bits & 0xFFFFFFFFFFFFFL) | (0x10000000000000L);

            if(f_exp < 0) {
                long posExp = -f_exp;

                int index = (int) (posExp / SHIFT) + 1;
                int bitOffset = (int) (posExp % SHIFT);

                if (bitOffset == 0) {
                    index--;
                    bitOffset = SHIFT;
                }

                int k;
                int i = index;
                for (k = 53; k > 0 && i < digits.length; i++) {
                    int r;

                    if (k == 53) {
                        r = SHIFT - k - bitOffset + 1;
                        k -= r < 0 ? 53 + r : 53 - r;
                    } else {
                        r = SHIFT - k;
                        k -= k;
                    }

                    if (r >= 0) {
                        digits[i] = (mantissa << r);
                    } else {
                        digits[i] = (mantissa >>> (-r));
                    }

                }
            }

        }

        boolean isZero = true;
        for(int i = 0; i < digits.length; i++) {
            if(digits[i] != 0) {
                isZero = false;
                break;
            }
        }

        if(isZero) {
            sign = 0;
        }
    }

    public BigNum64(Apfloat val) {

        scale = 0;
        this.offset = -1;
        bitOffset = -1;

        Apfloat baseTwo = val.toRadix(2);

        sign = baseTwo.getImpl().signum();

        isOne = false;

        digits = new long[fracDigitsp1];

        LongMemoryDataStorage str = (LongMemoryDataStorage)baseTwo.getImpl().getDataStorage();

        if(str == null || sign == 0) { //for zero
            return;
        }

        long[] data = str.getData();

        if(data.length == 1 && data[0] == 1) {
            isOne = true;
        }

        int base_digits = BASE_DIGITS[2];

        int offset = (int)str.getOffset();

        int exponent = (int)baseTwo.getImpl().getExponent();

        if(exponent == 1) {
            int index = data.length >= 2 ? 1 : 0;
            digits[0] = MASKD0 & data[index];
            offset++;
        }

        int dataOffset = exponent < 0 ? Math.abs(exponent) * base_digits : 0;

        for(int i = dataOffset, j = 0; ; i++, j++) {

            int index2 = (i / SHIFT) + 1;
            int index = j / base_digits + offset;

            if(index2 >= digits.length || index >= data.length) {
                break;
            }

            int shift = base_digits - (j % base_digits) - 1;
            long bit =  ((data[index] >>> shift) & 0x1);

            digits[index2] |= bit << (SHIFT - (i % SHIFT) - 1);
        }

        boolean isZero = true;
        for(int i = 0; i < digits.length; i++) {
            if(digits[i] != 0) {
                isZero = false;
                break;
            }
        }

        if(isZero) {
            sign = 0;
        }

    }

    @Override
    public long getScale() {

        if(sign == 0) {
            return Long.MIN_VALUE;
        }

        if(offset != -1) {
            return scale;
        }

        int i;
        long digit = 0;
        for(i = 0; i < digits.length; i++) {
            digit = digits[i];
            if(digit != 0) {
                break;
            }
        }

        if(i == digits.length) {
            return Long.MIN_VALUE;
        }

        /*int r;
        for(r = i == 0 ? SHIFT : SHIFTM1; r >= 0; r--) {
            if(digits[i] >>> r != 0) {
                break;
            }
        }*/

        offset = i;
        bitOffset = (int)msbDeBruijn64(digit);
        scale = i == 0 ? bitOffset : -(((long) i) * SHIFT) + bitOffset;

        return scale;
    }

    public static BigNum64 getNegative(BigNum64 other) {

        BigNum64 copy = new BigNum64();
        copy.sign = other.sign;
        copy.isOne = other.isOne;

        long[] otherDigits = other.digits;
        long[] digits = copy.digits;

//        long digit = otherDigits[fracDigits];
//        long digitlo = digit & MASKI;
//        long digithi = digit >>> SHIFTI;
//        long slo = ((~digitlo) & MASKI) + 1;
//        long shi = ((~digithi) & MASKI) + (slo >>> SHIFTI);
//        digits[fracDigits] = (shi << SHIFTI) | (slo & MASKI);
//        for (int i = fracDigitsm1; i >= 0 ; i--) {
//            digit = otherDigits[i];
//            digitlo = digit & MASKI;
//            digithi = digit >>> SHIFTI;
//            slo = ((~digitlo) & MASKI) + (shi >>> SHIFTI);
//            shi = ((~digithi) & MASKI) + (slo >>> SHIFTI);
//            digits[i] = (shi << SHIFTI) | (slo & MASKI);
//        }

        long digit = otherDigits[fracDigits];
        long temp = ~digit;
        long bit = temp >>> SHIFTM1;
        temp++;
        digits[fracDigits] = temp;
        long carry = bit & ((~(temp >>> SHIFTM1)) & 0x1);

        for (int i = fracDigitsm1; i >= 0 ; i--) {
            digit = otherDigits[i];
            temp = ~digit;
            bit = temp >>> SHIFTM1;
            temp += carry;
            digits[i] = temp;
            carry = bit & ((~(temp >>> SHIFTM1)) & 0x1);
        }

        return copy;
    }

    @Override
    public void negSelf() {

        if(sign == 0) {
            return;
        }
        /*if(digits[fracDigits]>0) {
            digits[fracDigits] = (int)((digits[fracDigits]-1)^MASK);
            for(int i=fracDigits-1; i>0; i--) digits[i] ^= MASK;
            digits[0] = ~digits[0];
        } else {
            long s = digits[fracDigits-1]-1; digits[fracDigits-1] = (int)(~s&MASK);
            for(int i=fracDigits-2; i>0; i--) {
                s = digits[i]+(s>>SHIFT); digits[i] = (int)(~s&MASK);
            }
            digits[0] = (int)(~(digits[0]+(s>>SHIFT)));
        }*/

//        long digit = digits[fracDigits];
//        long digitlo = digit & MASKI;
//        long digithi = digit >>> SHIFTI;
//        long slo = ((~digitlo) & MASKI) + 1;
//        long shi = ((~digithi) & MASKI) + (slo >>> SHIFTI);
//        digits[fracDigits] = (shi << SHIFTI) | (slo & MASKI);
//        for (int i = fracDigitsm1; i >= 0 ; i--) {
//            digit = digits[i];
//            digitlo = digit & MASKI;
//            digithi = digit >>> SHIFTI;
//            slo = ((~digitlo) & MASKI) + (shi >>> SHIFTI);
//            shi = ((~digithi) & MASKI) + (slo >>> SHIFTI);
//            digits[i] = (shi << SHIFTI) | (slo & MASKI);
//        }

        long digit = digits[fracDigits];
        long temp = ~digit;
        long bit = temp >>> SHIFTM1;
        temp++;
        digits[fracDigits] = temp;
        long carry = bit & ((~(temp >>> SHIFTM1)) & 0x1);

        for (int i = fracDigitsm1; i >= 0 ; i--) {
            digit = digits[i];
            temp = ~digit;
            bit = temp >>> SHIFTM1;
            temp += carry;
            digits[i] = temp;
            carry = bit & ((~(temp >>> SHIFTM1)) & 0x1);
        }

    }

    @Override
    public BigNum64 negate() {

        BigNum64 res = new BigNum64(this);

        res.sign *= -1;

        return res;

    }

    @Override
    public BigNum64 abs() {

        BigNum64 res = new BigNum64(this);

        if(sign == -1) {
            res.sign = 1;
        }

        return res;
    }

    @Override
    public BigNum64 abs_mutable() {

        if(sign == -1) {
            sign = 1;
        }

        return this;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder((sign == -1 ? "-" : "") + toHexString(digits[0]));
        for(int i=1; i<=fracDigits; i++) {
            ret.append(i==1 ? "." : " ");
            ret.append(toHexString(digits[i]));
        }
        return doubleValueOld() + " " + ret.toString();
    }

    @Override
    public String bits() {

        String value = "";
        if(sign == -1) {
            value = "-";
        }
        for (int i = 0; i < digits.length; i++) {

            if (i == 0) {
                boolean zero = true;
                for (int j = SHIFTM1; j >= 0; j--) {
                    if (((digits[i] >>> j) & 0x1) == 1) {
                        zero = false;
                    }

                    if (!zero) {
                        value += "" + ((digits[i] >>> j) & 0x1);
                    }
                }
                if(zero) {
                    value += "0";
                }
                value += ".";
            } else {
                for (int j = SHIFTM1; j >= 0; j--) {
                    value += "" + ((digits[i] >>> j) & 0x1);
                }
            }
        }

        return value;

    }

    @Override
    public int compare(BigNum otherb) {
        BigNum64 other = (BigNum64) otherb;

        int signA = sign;

        long[] otherDigits = other.digits;

        int signB = other.sign;

        long digit, otherDigit;
        long digitHi, digitLo, otherDigitHi, otherDigitLo;

        if(signA < signB) {
            return -1;
        }
        else if(signA > signB) {
            return 1;
        }
        else {
            if (signA == 0) {
                return 0;
            }

            if(signA < 0) {
                for (int i = 0; i < digits.length; i++) {
                    digit = digits[i];
                    otherDigit = otherDigits[i];
                    digitHi = digit >>> SHIFTI;
                    otherDigitHi = otherDigit >>> SHIFTI;

                    if (digitHi < otherDigitHi) {
                        return 1;
                    } else if (digitHi > otherDigitHi) {
                        return -1;
                    }
                    digitLo = digit & MASKI;
                    otherDigitLo = otherDigit & MASKI;

                    if (digitLo < otherDigitLo) {
                        return 1;
                    } else if (digitLo > otherDigitLo) {
                        return -1;
                    }
                }
            }
            else {
                for (int i = 0; i < digits.length; i++) {
                    digit = digits[i];
                    otherDigit = otherDigits[i];

                    digitHi = digit >>> SHIFTI;
                    otherDigitHi = otherDigit >>> SHIFTI;

                    if (digitHi < otherDigitHi) {
                        return -1;
                    } else if (digitHi > otherDigitHi) {
                        return 1;
                    }

                    digitLo = digit & MASKI;
                    otherDigitLo = otherDigit & MASKI;

                    if (digitLo < otherDigitLo) {
                        return -1;
                    } else if (digitLo > otherDigitLo) {
                        return 1;
                    }
                }
            }
        }

        return 0;

    }

    @Override
    public int compareBothPositive(BigNum otherb) {
        BigNum64 other = (BigNum64) otherb;

        int signA = sign;
        int signB = other.sign;

        if(signA == 0 && signB == 0) {
            return 0;
        }

        long[] otherDigits = other.digits;

        long digit, otherDigit;
        long digitHi, digitLo, otherDigitHi, otherDigitLo;

        for (int i = 0; i < digits.length; i++) {
            digit = digits[i];
            otherDigit = otherDigits[i];

            digitHi = digit >>> SHIFTI;
            otherDigitHi = otherDigit >>> SHIFTI;

            if (digitHi < otherDigitHi) {
                return -1;
            } else if (digitHi > otherDigitHi) {
                return 1;
            }

            digitLo = digit & MASKI;
            otherDigitLo = otherDigit & MASKI;

            if (digitLo < otherDigitLo) {
                return -1;
            } else if (digitLo > otherDigitLo) {
                return 1;
            }
        }

        return 0;

    }

//    static long binarySearch64(long v) {
//
//        int shift = 32;
//
//        long res = v >>> shift;
//
//        if(res == 1) {
//            return shift;
//        }
//        else if (res > 1) {
//            shift +=  16; //+16
//        }
//        else {
//            shift -=  16; // -16
//        }
//
//        res = v >>> shift;
//
//        if(res == 1) {
//            return shift;
//        }
//        else if (res > 1) {
//            shift +=  8; //+8
//        }
//        else {
//            shift -=  8; // -8
//        }
//
//        res = v >>> shift;
//
//        if(res == 1) {
//            return shift;
//        }
//        else if (res > 1) {
//            shift +=  4; // +4
//        }
//        else {
//            shift -=  4; // -4
//        }
//
//        res = v >>> shift;
//
//        if(res == 1) {
//            return shift;
//        }
//        else if (res > 1) {
//            shift +=  2; // + 2
//        }
//        else {
//            shift -=  2; // -2
//        }
//
//        res = v >>> shift;
//
//        if(res == 1) {
//            return shift;
//        }
//        else if (res > 1) {
//            shift +=  1; // + 1
//        }
//        else {
//
//            shift -=  1; // -1
//        }
//
//        return shift;
//    }
//
//
    static int[] debruijn64 =
            {
                    0, 58, 1, 59, 47, 53, 2, 60, 39, 48, 27, 54, 33, 42, 3, 61, 51, 37, 40, 49,
                    18, 28, 20, 55, 30, 34, 11, 43, 14, 22, 4, 62, 57, 46, 52, 38, 26, 32, 41,
                    50, 36, 17, 19, 29, 10, 13, 21, 56, 45, 25, 31, 35, 16, 9, 12, 44, 24, 15,
                    8, 23, 7, 6, 5, 63
            };

    static long msbDeBruijn64(long v) {


        /* Round down to one less than a power of 2. */
        v |= v >>> 1;
        v |= v >>> 2;
        v |= v >>> 4;
        v |= v >>> 8;
        v |= v >>> 16;
        v |= v >>> 32;

        /* 0x03f6eaf2cd271461 is a hexadecimal representation of a De Bruijn
         * sequence for binary words of length 6. The binary representation
         * starts with 000000111111. This is required to make it work with one less
         * than a power of 2 instead of an actual power of 2.
         */

        return debruijn64[(int)((v * 0x03f6eaf2cd271461L) >>> 58)];
    }

    @Override
    public double doubleValue() {

        long[] digits = this.digits;

        int i;
        long digit = 0;
        if(offset == -1) {
            for (i = 0; i < digits.length; i++) {
                digit = digits[i];
                if (digit != 0) {
                    break;
                }
            }

            if (i == digits.length) {
                return 0;
            }

        /*int r;
        for(r = i == 0 ? SHIFT : SHIFTM1; r >= 0; r--) {
            if(digits[i] >>> r != 0) {
                break;
            }
        }*/
            offset = i;
            bitOffset = (int)msbDeBruijn64(digit);
            scale = i == 0 ? bitOffset : -(((long) i) * SHIFT) + bitOffset;
        }
        else {
            i = offset;
            digit = digits[i];
        }

        if (scale < Double.MIN_EXPONENT) { //accounting for +1
            return 0.0;
        }
        else if (scale >= Double.MAX_EXPONENT) {
            return sign == -1 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }

        long mantissa = 0;

        int temp = 52 - bitOffset;

        long val = digit;
        if(temp >= 0) {
            mantissa |= val << temp;
            mantissa &= 0xFFFFFFFFFFFFFL;
            if(temp == 0 && i + 1 < digits.length) {
                mantissa += (digits[i + 1] >>> (SHIFTM1)) & 0x1;
            }
        }
        else {
            int shift = -temp;
            mantissa |= val >>> shift;
            mantissa &= 0xFFFFFFFFFFFFFL;
            mantissa += (val >>> (shift - 1)) & 0x1;
        }

        i++;

        int k;
        for(k = bitOffset ; k < 52 && i < digits.length; k+=SHIFT, i++) {
            val = digits[i];
            temp = 52 - k;

            if(temp > SHIFT) {
                mantissa |= val << (temp - SHIFT);
                mantissa &= 0xFFFFFFFFFFFFFL;
            }
            else {
                int temp2 = k + SHIFTM52;
                mantissa |= val >>> temp2;
                mantissa &= 0xFFFFFFFFFFFFFL;

                if(temp2 == 0 && i + 1 < digits.length) {
                    mantissa += (digits[i + 1] >>> (SHIFTM1)) & 0x1;
                }
                else {
                    mantissa += (val >>> (temp2 - 1)) & 0x1;
                }
            }
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

        double res;
        if(sign == -1) {
            return Double.longBitsToDouble( (0x8000000000000000L) | (mantissa & 0xFFFFFFFFFFFFFL) | ((finalScale + Double.MAX_EXPONENT) << 52));
        }
        else {
            return Double.longBitsToDouble((mantissa & 0xFFFFFFFFFFFFFL) | ((finalScale + Double.MAX_EXPONENT) << 52));
        }

    }

    @Override
    public double doubleValueOld() {

        if (sign == 0) {
            return 0;
        }

        if(isOne) {
            return sign;
        }

        if(useToDouble2) {
            return doubleValue();
        }

        double ret = 0;


        /*if(digits[0]>=0) {
            for(int i=fracDigits; i>=0; i--) {
                ret *= TWO_TO_SHIFT;
                ret += digits[i];
            }
        } else {
            ret--;
            for(int i=fracDigits; i>0; i--) {
                ret += digits[i] - MASK;
                ret *= TWO_TO_SHIFT;
            }
            ret += digits[0]+1;
        }*/

        for(int i=fracDigits; i > 0; i--) {
            long v = digits[i];
            long v1 = v & MASKI;
            ret *= TWO_TO_SHIFT2;
            ret += v1;
            long v2 = v >>> SHIFTI;
            ret *= TWO_TO_SHIFT2;
            ret += v2;
        }

        long v = digits[0] & MASKD0;
        ret *= TWO_TO_SHIFT2;
        ret += v;

        ret *= sign;
        return ret;
    }

    @Override
    public BigNum64 add(BigNum aa) {
        BigNum64 a = (BigNum64) aa;

        BigNum64 result = new BigNum64();

        long[] otherDigits = a.digits;
        int otherSign = a.sign;

        long[] resDigits = result.digits;

        long[] digits = this.digits;
        int sign = this.sign;

        if(sign == 0 || otherSign == 0) {
            if(sign == 0 && otherSign == 0){
                return result;
            }
            else if(sign == 0) {
                result.sign = otherSign;
                result.isOne = a.isOne;
                System.arraycopy(otherDigits, 0, resDigits, 0, otherDigits.length);
            }
            else {
                result.sign = sign;
                result.isOne = isOne;
                System.arraycopy(digits, 0, resDigits, 0, digits.length);
            }
            return result;
        }

        if(sign != otherSign) {
            if(sign == -1) {
                BigNum64 copy = getNegative(this);
                digits = copy.digits;
            }
            else
            {
                BigNum64 copy = getNegative(a);
                otherDigits = copy.digits;
            }
        }

        long s = 0;
        long isNonZero = 0;
        long digitA, digitB, bitA, bitB, notBitC;
        for(int i=fracDigits; i>0; i--) {
            digitA = digits[i];
            digitB = otherDigits[i];
            bitA = digitA >>> SHIFTM1;
            bitB = digitB >>> SHIFTM1;
            s += digitA + digitB;
            isNonZero |= s;
            resDigits[i] = s;
            notBitC = (~(s >>> SHIFTM1)) & 0x1;

            s = (bitA & bitB) | (bitA & notBitC) | (bitB & notBitC);
        }
        s += digits[0] + otherDigits[0];

        result.isOne = (s == 1 || s == -1) && isNonZero == 0;
        isNonZero |= s;
        resDigits[0] = s;



        /*long s = 0;
        long s2;

        long isNonZero = 0;
        long temp, carry2;
        long digitA, digitB, Alo, Ahi, Blo, Bhi;
        for(int i=fracDigits; i>0; i--) {
            digitA = digits[i];
            digitB = otherDigits[i];
            Alo = digitA & MASKI;
            Ahi = digitA >>> SHIFTI;
            Blo = digitB & MASKI;
            Bhi = digitB >>> SHIFTI;

            s += Alo + Blo;

            s2 = Ahi + Bhi + (s >>> SHIFTI);

            s &= MASKI;
            carry2 = s2 >>> SHIFTI;
            s2 &= MASKI;


            temp = (s2 << SHIFTI) | s;
            isNonZero |= temp;
            resDigits[i] = temp;

            s = carry2;
        }

        digitA = digits[0];
        digitB = otherDigits[0];
        Alo = digitA & MASKI;
        Ahi = digitA >>> SHIFTI;
        Blo = digitB & MASKI;
        Bhi = digitB >>> SHIFTI;

        s += Alo + Blo;

        s2 = Ahi + Bhi + (s >>> SHIFTI);

        s &= MASKI;
        s2 &= MASKI;

        temp = (s2 << SHIFTI) | s;


        result.isOne = (temp == 1 || temp == -1) && isNonZero == 0;
        isNonZero |= s;
        resDigits[0] = s;*/

        if(sign != otherSign) {
            if(resDigits[0] < 0) {
                result.sign = -1;
                result.negSelf();
            }
            else {
                result.sign = isNonZero != 0 ? 1 : 0;
            }
        }
        else {
            result.sign = isNonZero != 0 ? sign : 0;
        }

        return result;
    }

    @Override
    public BigNum64 add(int a) {

        BigNum64 result = new BigNum64();

        int otherSign;
        if(a == 0) {
            otherSign = 0;
        }
        else if(a < 0) {
            otherSign = -1;
        }
        else {
            otherSign = 1;
        }

        int otherDigits = a;

        boolean aIsOne = a == 1 || a == -1;

        long[] resDigits = result.digits;

        long[] digits = this.digits;
        int sign = this.sign;

        if(sign == 0 || otherSign == 0) {
            if(sign == 0 && otherSign == 0){
                return result;
            }
            else if(sign == 0) {
                result.sign = otherSign;
                result.isOne = aIsOne;

                if(otherSign < 0) {
                    a = ~a + 1;
                }

                resDigits[0] = a;
            }
            else {
                result.sign = sign;
                result.isOne = isOne;
                System.arraycopy(digits, 0, resDigits, 0, digits.length);
            }
            return result;
        }

        if(sign != otherSign) {
            if(sign == -1) {
                BigNum64 copy = getNegative(this);
                digits = copy.digits;
            }
        }
        else {
            if(otherSign < 0) {
                otherDigits = ~otherDigits + 1;
            }
        }

        long isNonZero = 0;
        long temp;
        for(int i=fracDigits; i>0; i--) {
            temp = resDigits[i] = digits[i];
            isNonZero |= temp;
        }

        temp = digits[0] + otherDigits;
        result.isOne = (temp == 1 || temp == -1) && isNonZero == 0;
        isNonZero |= temp;
        resDigits[0] = temp;

        if(sign != otherSign) {
            if(resDigits[0] < 0) {
                result.sign = -1;
                result.negSelf();
            }
            else {
                result.sign = isNonZero != 0 ? 1 : 0;
            }
        }
        else {
            result.sign = isNonZero != 0 ? sign : 0;
        }

        return result;
    }

    @Override
   public BigNum64 sub(BigNum aa) {
        BigNum64 a = (BigNum64) aa;

        BigNum64 result = new BigNum64();

       long[] otherDigits = a.digits;
       int otherSign = a.sign;

       long[] resDigits = result.digits;

       long[] digits = this.digits;
       int sign = this.sign;

       if(sign == 0 || otherSign == 0) {
           if(sign == 0 && otherSign == 0){
               return result;
           }
           else if(sign == 0) {
               result.sign = -otherSign;
               result.isOne = a.isOne;
               System.arraycopy(otherDigits, 0, resDigits, 0, otherDigits.length);
           }
           else {
               result.sign = sign;
               result.isOne = isOne;
               System.arraycopy(digits, 0, resDigits, 0, digits.length);
           }
           return result;
       }

        if(sign == otherSign) {
            // + +   -> + -
            // - -  -> - +
            if(sign == -1) {
                BigNum64 copy = getNegative(this);
                digits = copy.digits;
            }
            else {
                BigNum64 copy = getNegative(a);
                otherDigits = copy.digits;
            }
        }

        /*long s = 0;
        long s2;

        long isNonZero = 0;
        long temp, carry2;
        long digitA, digitB, Alo, Ahi, Blo, Bhi;
        for(int i=fracDigits; i>0; i--) {
            digitA = digits[i];
            digitB = otherDigits[i];
            Alo = digitA & MASKI;
            Ahi = digitA >>> SHIFTI;
            Blo = digitB & MASKI;
            Bhi = digitB >>> SHIFTI;

            s += Alo + Blo;

            s2 = Ahi + Bhi + (s >>> SHIFTI);

            s &= MASKI;
            carry2 = s2 >>> SHIFTI;
            s2 &= MASKI;


            temp = (s2 << SHIFTI) | s;
            isNonZero |= temp;
            resDigits[i] = temp;

            s = carry2;
        }

        digitA = digits[0];
        digitB = otherDigits[0];
        Alo = digitA & MASKI;
        Ahi = digitA >>> SHIFTI;
        Blo = digitB & MASKI;
        Bhi = digitB >>> SHIFTI;

        s += Alo + Blo;

        s2 = Ahi + Bhi + (s >>> SHIFTI);

        s &= MASKI;
        s2 &= MASKI;

        temp = (s2 << SHIFTI) | s;

        result.isOne = (temp == 1 || temp == -1) && isNonZero == 0;
        isNonZero |= temp;
        resDigits[0] = temp;*/

        long s = 0;
        long isNonZero = 0;
        long digitA, digitB, bitA, bitB, notBitC;
        for(int i=fracDigits; i>0; i--) {
            digitA = digits[i];
            digitB = otherDigits[i];
            bitA = digitA >>> SHIFTM1;
            bitB = digitB >>> SHIFTM1;
            s += digitA + digitB;
            isNonZero |= s;
            resDigits[i] = s;
            notBitC = (~(s >>> SHIFTM1)) & 0x1;

            s = (bitA & bitB) | (bitA & notBitC) | (bitB & notBitC);
        }
        s += digits[0] + otherDigits[0];

       result.isOne = (s == 1 || s == -1) && isNonZero == 0;
       isNonZero |= s;
       resDigits[0] = s;

        if(sign == otherSign) {
            if(resDigits[0] < 0) {
                result.sign = -1;
                result.negSelf();
            }
            else {
                result.sign = isNonZero != 0 ? 1 : 0;
            }
        }
        else {
            result.sign = isNonZero != 0 ? sign : 0;
        }

        return result;
    }

    @Override
    public BigNum64 sub(int a) {

        BigNum64 result = new BigNum64();

        int otherSign;
        if(a == 0) {
            otherSign = 0;
        }
        else if(a < 0) {
            otherSign = -1;
        }
        else {
            otherSign = 1;
        }

        int otherDigits = a;

        boolean aIsOne = a == 1 || a == -1;

        long[] resDigits = result.digits;

        long[] digits = this.digits;
        int sign = this.sign;

        if(sign == 0 || otherSign == 0) {
            if(sign == 0 && otherSign == 0){
                return result;
            }
            else if(sign == 0) {
                result.sign = -otherSign;
                result.isOne = aIsOne;

                if(otherSign < 0) {
                    a = ~a + 1;
                }

                resDigits[0] = a;
            }
            else {
                result.sign = sign;
                result.isOne = isOne;
                System.arraycopy(digits, 0, resDigits, 0, digits.length);
            }
            return result;
        }

        if(sign == otherSign) {
            // + +   -> + -
            // - -  -> - +
            if(sign == -1) {
                BigNum64 copy = getNegative(this);
                digits = copy.digits;
            }
            otherDigits = ~otherDigits + 1;
        }
        else {
            if(otherSign < 0) {
                otherDigits = ~otherDigits + 1;
            }
        }


        long isNonZero = 0;
        long temp;
        for(int i=fracDigits; i>0; i--) {
            temp = resDigits[i] = digits[i];
            isNonZero |= temp;
        }

        temp = digits[0] + otherDigits;
        result.isOne = (temp == 1 || temp == -1) && isNonZero == 0;
        isNonZero |= temp;
        resDigits[0] = temp;

        if(sign == otherSign) {
            if(resDigits[0] < 0) {
                result.sign = -1;
                result.negSelf();
            }
            else {
                result.sign = isNonZero != 0 ? 1 : 0;
            }
        }
        else {
            result.sign = isNonZero != 0 ? sign : 0;
        }

        return result;
    }

    @Override
    public BigNum64 square() {
        return squareFull();
    }

    @Override
    public BigNum64 squareFull() {
        BigNum64 result = new BigNum64();

        if(sign == 0) {
            return result;
        }

        long[] resDigits = result.digits;

        if(isOne) {
            resDigits[0] = 1;
            result.isOne = true;
            result.sign = 1;
            return result;
        }

        long old_sum;

        long sum = 0;

        int length = initialLength;

        int j;
        int k;
        long carry = 0;

        long bdigit;
        long adigit;
        long bjhi, akhi, bjlo, aklo;

        for (j = 1, k = fracDigits; j <= length;) {
            bdigit = digits[j];
            adigit = digits[k];

            bjlo = bdigit & MASKI;
            aklo = adigit & MASKI;

            bjhi = bdigit >>> SHIFTI;
            akhi = adigit >>> SHIFTI;

            sum += bjhi * aklo;
            carry += sum >>> SHIFTI;
            sum &= MASKI;
            //System.out.println("1: a" + j + "hi  *  a"+k+"lo");
            sum += bjlo * akhi;
            carry += sum >>> SHIFTI;
            sum &= MASKI;
            //System.out.println("1: a" + j + "lo  *  a"+k+"hi");

            j++;k--;
        }

        if(j == k) {
            bdigit = digits[j];
            bjhi = bdigit >>> SHIFTI;
            bjlo = bdigit & MASKI;
            sum += (bjhi * bjlo);
            carry += sum >>> SHIFTI;
            sum &= MASKI;

            sum <<= 1;
            carry <<= 1;
            //System.out.println("1: a" + j + "lo  *  a"+j+"lo");
        }
        else  {
            bdigit = digits[j];
            adigit = digits[k];

            bjlo = bdigit & MASKI;
            aklo = adigit & MASKI;

            bjhi = bdigit >>> SHIFTI;
            akhi = adigit >>> SHIFTI;

            sum += bjhi * aklo;
            carry += sum >>> SHIFTI;
            sum &= MASKI;
            //System.out.println("1: a" + j + "hi  *  a"+k+"lo");
            sum += bjlo * akhi;
            carry += sum >>> SHIFTI;
            sum &= MASKI;
            //System.out.println("1: a" + j + "lo  *  a"+k+"hi");

            sum <<= 1;
            carry <<= 1;
        }

        carry += sum >>> SHIFTI;
        sum &= MASKI;

        old_sum = carry + (sum >>> SHIFTIM1); // Roundish

        long d0 = digits[0];
        long d0lo = d0 & MASKI;

        length = fracDigitsHalf;

        int toggle = evenFracDigits ? 0 : 1;

        for(int i = fracDigits; i > 0; i--, length -= toggle, toggle ^= 1) {

            sum = 0;
            carry = 0;
            long sum2 = 0;
            long carry2 = 0;

            k = i;

            adigit = digits[k];

            aklo = adigit & MASKI;
            akhi = adigit >>> SHIFTI;

            bjlo = d0lo;

            int length2 = evenFracDigits ? length : length + 1;

            sum += bjlo * aklo;
            sum2 += bjlo * akhi;
            carry += sum >>> SHIFTI;
            sum &= MASKI;

            carry2 += sum2 >>> SHIFTI;
            sum2 &= MASKI;

            for(j = 1; j < length2;) {
                bdigit = digits[j];
                bjlo = bdigit & MASKI;
                bjhi = bdigit >>> SHIFTI;

                sum += bjhi * akhi;
                carry += sum >>> SHIFTI;
                sum &= MASKI;
                //System.out.println("1: b" + j + "hi  *  a"+k+"hi");

                k--;

                adigit = digits[k];

                aklo = adigit & MASKI;
                akhi = adigit >>> SHIFTI;

                //System.out.println("2: b" + j + "hi  *  a"+k+"lo");
                sum += bjlo * aklo;
                carry += sum >>> SHIFTI;
                sum &= MASKI;

                sum2 += bjhi * aklo;
                carry2 += sum2 >>> SHIFTI;
                sum2 &= MASKI;

                sum2 += bjlo * akhi;
                carry2 += sum2 >>> SHIFTI;
                sum2 &= MASKI;

                j++;

            }

            if(j == k) {
                sum <<= 1;
                carry <<= 1;
                carry += sum >>> SHIFTI;
                sum &= MASKI;
                sum += akhi * akhi;
            }
            else {
                bdigit = digits[j];
                bjhi = bdigit >>> SHIFTI;

                sum += bjhi * akhi;
                carry += sum >>> SHIFTI;
                sum &= MASKI;

                k--;

                adigit = digits[k];

                aklo = adigit & MASKI;

                sum2 += bjhi * aklo;
                carry2 += sum2 >>> SHIFTI;
                sum2 &= MASKI;

                sum <<= 1;
                carry <<= 1;
                carry += sum >>> SHIFTI;
                sum &= MASKI;
                sum += aklo * aklo;
            }

            sum2 <<= 1;
            carry2 <<= 1;

            carry += sum >>> SHIFTI;
            sum &= MASKI;

            sum += old_sum;
            carry += sum >>> SHIFTI;
            sum &= MASKI;

            sum2 += carry;
            carry2 += sum2 >>> SHIFTI;
            sum2 &= MASKI;

            //System.out.println("NEXT");

            long di = (sum2 << SHIFTI) | sum;
            resDigits[i] = di;
            old_sum = carry2;
        }

        sum = old_sum;

        sum += d0 * d0;

        resDigits[0] = sum & MASKD0;
        result.sign = 1;

        return  result;
    }

    @Deprecated
    @Override
    public BigNum64 squareFullGolden() {


        BigNum64 result = new BigNum64();

        if(sign == 0) {
            return result;
        }

        long[] resDigits = result.digits;
        long[] origDigitsA = this.digits;

        if(isOne) {
            resDigits[0] = 1;
            result.isOne = true;
            result.sign = 1;
            return result;
        }

        int newFracDigits = fracDigits2;
        int newLength = newFracDigits + 1;
        int[] digits = new int[newLength];

        long old_sum;

        long sum = 0;

        int length = fracDigitsHalf;

        long carry = 0;

        digits[0] = (int)(origDigitsA[0] & MASKI);
        int j = 1, k = fracDigits;
        int base = (j << 1) - 1, base2 = (k << 1);
        for (; j <= length; j++, k--) {
            long bdigit = origDigitsA[j];
            long adigit = origDigitsA[k];

            long bjlo = bdigit & MASKI;
            long aklo = adigit & MASKI;

            long bjhi = bdigit >>> SHIFTI;
            long akhi = adigit >>> SHIFTI;

            digits[base2] = (int)(aklo);
            digits[base] = (int)(bjhi);
            base++;
            base2--;
            digits[base2] = (int)(akhi);
            digits[base] = (int)(bjlo);
            base++;
            base2--;

            sum += bjlo * akhi;
            carry += sum >>> SHIFTI;
            sum &= MASKI;

            sum += bjhi * aklo;
            carry += sum >>> SHIFTI;
            sum &= MASKI;

        }

        if(j == k) {
            long bdigit = origDigitsA[j];
            long aklo = bdigit & MASKI;
            long bjhi = bdigit >>> SHIFTI;

            digits[base2] = (int)(aklo);
            digits[base] = (int)(bjhi);

            sum += bjhi * aklo;
            carry += sum >>> SHIFTI;
            sum &= MASKI;
        }

        carry <<= 1;
        sum <<= 1;
        carry += sum >>> SHIFTI;
        sum &= MASKI;

        old_sum = carry + (sum >>> SHIFTIM1); // Roundish

        int m = fracDigits;
        for(int i = newFracDigits; i > 0; i--, m--) {

            sum = old_sum;

            length = (i >> 1) - 1;

            long temp_sum = 0;
            carry = 0;

            long bj, ak;

            for(j = 0, k = i; j <= length; j++, k--) {
                bj = digits[j] & MASKI;
                ak = digits[k] & MASKI;
                temp_sum += bj * ak;
                carry += temp_sum >>> SHIFTI;
                temp_sum &= MASKI;
            }

            carry <<= 1;
            temp_sum <<= 1;

            carry += temp_sum >>> SHIFTI;
            temp_sum &= MASKI;

            bj = digits[j] & MASKI;
            ak = digits[k] & MASKI;
            temp_sum += bj * ak;

            carry += temp_sum >>> SHIFTI;
            temp_sum &= MASKI;

            sum += temp_sum;
            carry += sum >>> SHIFTI;
            sum &= MASKI;

            long finalVal = sum;

            i--;//Unrolling

            sum = carry;

            length = i >> 1;

            temp_sum = 0;
            carry = 0;

            for(j = 0, k = i; j <= length; j++, k--) {
                bj = digits[j] & MASKI;
                ak = digits[k] & MASKI;
                temp_sum += bj * ak;
                carry += temp_sum >>> SHIFTI;
                temp_sum &= MASKI;
            }

            carry <<= 1;
            temp_sum <<= 1;

            carry += temp_sum >>> SHIFTI;
            temp_sum &= MASKI;

            sum += temp_sum;
            carry += sum >>> SHIFTI;
            sum &= MASKI;

            finalVal |= sum << SHIFTI;

            resDigits[m] = finalVal;
            old_sum = carry;
        }

        sum = old_sum;

        long bj = digits[0] & MASKI;
        long ak = digits[0] & MASKI;
        sum += bj * ak;

        result.sign = 1;

        result.digits[0] = sum;

        return  result;
    }

    @Override
    public BigNum64 mult(BigNum bb) {
        BigNum64 b = (BigNum64) bb;
        return multFull(b);
    }

    @Deprecated
    public BigNum64 multFullGolden(BigNum64 b) {

        BigNum64 result = new BigNum64();

        if(sign == 0 || b.sign == 0) {
            return result;
        }

        long[] resDigits = result.digits;
        long[] origDigitsA = this.digits;
        long[] origDigitsB = b.digits;

        if(isOne || b.isOne) {
            if (isOne && b.isOne) {
                resDigits[0] = 1;
                result.isOne = true;
                result.sign = sign * b.sign;
            }
            else if(isOne) {
                System.arraycopy(origDigitsB, 0, resDigits, 0, origDigitsB.length);
                result.sign = sign * b.sign;
            }
            else {
                System.arraycopy(origDigitsA, 0, resDigits, 0, origDigitsA.length);
                result.sign = sign * b.sign;
            }
            return result;
        }

        int newFracDigits = fracDigits2;
        int newLength = newFracDigits + 1;
        int[] digits = new int[newLength];
        int[] bdigits = new int[newLength];

        long old_sum;

        long sum = 0;
        long carry = 0;
        digits[0] = (int)(origDigitsA[0] & MASKI);
        bdigits[0] = (int)(origDigitsB[0] & MASKI);

        for (int j = 1, base = (j << 1) - 1, k = fracDigits, base2 = (k << 1); j <= fracDigits; j++, k--) {

            long bdigit = origDigitsB[j];
            long adigit = origDigitsA[k];

            long bjlo = bdigit & MASKI;
            long aklo = adigit & MASKI;

            long bjhi = bdigit >>> SHIFTI;
            long akhi = adigit >>> SHIFTI;

            digits[base2] = (int)(aklo);
            bdigits[base] = (int)(bjhi);
            base++;
            base2--;
            digits[base2] = (int)(akhi);
            bdigits[base] = (int)(bjlo);
            base++;
            base2--;

            sum += bjlo * akhi;
            carry += sum >>> SHIFTI;
            sum &= MASKI;

            sum += bjhi * aklo;
            carry += sum >>> SHIFTI;
            sum &= MASKI;

        }

        old_sum = carry + (sum >>> SHIFTIM1); // Roundish

        long isNonZero = 0;
        for(int i = newFracDigits, m = fracDigits; i > 0; i--, m--) {

            sum = old_sum; //carry from prev
            carry = sum >>> SHIFTI;
            sum &= MASKI;

            for(int j = 0, k = i; j <= i; j++, k--) {
                long bj = bdigits[j] & MASKI;
                long ak = digits[k] & MASKI;

                sum += bj * ak;
                carry += sum >>> SHIFTI;
                sum &= MASKI;

            }

            long finalVal = sum;

            i--;//Unrolling

            sum = carry; //carry from prev
            carry = sum >>> SHIFTI;
            sum &= MASKI;

            for(int j = 0, k = i; j <= i; j++, k--) {
                long bj = bdigits[j] & MASKI;
                long ak = digits[k] & MASKI;

                sum += bj * ak;
                carry += sum >>> SHIFTI;
                sum &= MASKI;

            }

            finalVal |= sum << SHIFTI;
            resDigits[m] = finalVal;

            isNonZero |= finalVal;
            old_sum = carry;
        }

        sum = old_sum;

        long bj = bdigits[0] & MASKI;
        long ak = digits[0] & MASKI;

        sum += bj * ak;

        long d0 = sum & MASKD0;
        result.digits[0] = sum;
        result.isOne = d0 == 1 && isNonZero == 0;
        result.sign = sign * b.sign;

        return result;

    }

    public BigNum64 multFull(BigNum64 b) {

        BigNum64 result = new BigNum64();

        if(sign == 0 || b.sign == 0) {
            return result;
        }

        long[] bdigits = b.digits;

        long[] resDigits = result.digits;

        if(isOne || b.isOne) {
            if (isOne && b.isOne) {
                resDigits[0] = 1;
                result.isOne = true;
                result.sign = sign * b.sign;
            }
            else if(isOne) {
                System.arraycopy(bdigits, 0, resDigits, 0, bdigits.length);
                result.sign = sign * b.sign;
            }
            else {
                System.arraycopy(digits, 0, resDigits, 0, digits.length);
                result.sign = sign * b.sign;
            }
            return result;
        }


        long old_sum;

        long sum = 0;
        long carry = 0;
        long bdigit;
        long adigit;
        long bjhi, akhi, bjlo, aklo;

        int j, k;

        for (j = 1, k = fracDigits; j <= fracDigits;) {

            bdigit = bdigits[j];
            adigit = digits[k];

            bjlo = bdigit & MASKI;
            aklo = adigit & MASKI;

            bjhi = bdigit >>> SHIFTI;
            akhi = adigit >>> SHIFTI;

            sum += bjhi * aklo;
            carry += sum >>> SHIFTI;
            sum &= MASKI;

            sum += bjlo * akhi;
            carry += sum >>> SHIFTI;
            sum &= MASKI;

            j++;k--;
        }

        old_sum = carry + (sum >>> SHIFTIM1); // Roundish

        long carry2;
        long sum2;

        long isNonZero = 0;

        long bd0 = bdigits[0];
        long bd0lo = bd0 & MASKI;

        for(int i = fracDigits; i > 0; i--) {

            sum = old_sum; //carry from prev
            carry = sum >>> SHIFTI;
            sum &= MASKI;

            carry2 = 0;
            sum2 = 0;
            k = i;
            adigit = digits[k];

            aklo = adigit & MASKI;
            akhi = adigit >>> SHIFTI;

            bjlo = bd0lo;

            for(j = 0; j < i;) {

                sum += bjlo * aklo;
                carry += sum >>> SHIFTI;
                sum &= MASKI;
                //System.out.println("1: b" + j + "lo  *  a"+k+"lo");

                sum2 += bjlo * akhi;
                carry2 += sum2 >>> SHIFTI;
                sum2 &= MASKI;
                //System.out.println("2: b" + j + "lo  *  a"+k+"hi");

                j++;

                bdigit = bdigits[j];
                bjlo = bdigit & MASKI;
                bjhi = bdigit >>> SHIFTI;

                sum += bjhi * akhi;
                carry += sum >>> SHIFTI;
                sum &= MASKI;
                //System.out.println("1: b" + j + "hi  *  a"+k+"hi");

                k--;

                adigit = digits[k];

                aklo = adigit & MASKI;
                akhi = adigit >>> SHIFTI;

                sum2 += bjhi * aklo;
                carry2 += sum2 >>> SHIFTI;
                sum2 &= MASKI;

            }

            sum += bjlo * aklo;
            //System.out.println("1: b" + j + "lo  *  a"+k+"lo");
            carry += sum >>> SHIFTI;
            sum &= MASKI;

            sum2 += carry;
            carry2 += sum2 >>> SHIFTI;
            sum2 &= MASKI;

            //System.out.println("Next");

            long di = (sum2 << SHIFTI) | sum;
            resDigits[i] = di;
            isNonZero |= di;
            //System.out.println(result.digits[i] );
            old_sum = carry2;
            //System.out.println("NEXT");
        }

        long d0 = (bd0 * digits[0] + old_sum) & MASKD0;
        resDigits[0] = d0;

        result.isOne = d0 == 1 && isNonZero == 0;

        result.sign = sign * b.sign;

        return result;

    }

    @Override
    /* Sign is positive */
    public BigNum64 mult(int value) {
        BigNum64 result = new BigNum64();

        if(sign == 0 || value == 0) {
            return result;
        }

        long[] resDigits = result.digits;

        boolean bIsOne = value == 1 || value == -1;

        int bsign;

        if(value < 0) {
            bsign = -1;
            value = ~value + 1;
        }
        else {
            bsign = 1;
        }

        if(isOne || bIsOne) {
            if (isOne && bIsOne) {
                resDigits[0] = 1;
                result.isOne = true;
                result.sign = sign * bsign;
            }
            else if(isOne) {
                resDigits[0] = value;
                result.sign = sign * bsign;
            }
            else {
                System.arraycopy(digits, 0, resDigits, 0, digits.length);
                result.sign = sign * bsign;
            }
            return result;
        }

        long old_sum = 0;
        long sum, sum2;
        long carry, carry2;
        long digit, digitlo, digithi;

        long isNonZero = 0;
        for(int i = fracDigits; i > 0; i--) {
            digit = digits[i];
            digithi = digit >>> SHIFTI;
            digitlo = digit & MASKI;

            sum = digitlo * value + old_sum;
            carry = sum >>> SHIFTI;
            sum &= MASKI;

            sum2 = digithi * value + carry;
            carry2 = sum2 >>> SHIFTI;
            sum2 &= MASKI;

            long di = (sum2 << SHIFTI) | sum;
            resDigits[i] = di;
            isNonZero |= di;
            old_sum = carry2;
        }

        sum = digits[0] * value + old_sum;

        long d0 = sum & MASKD0;
        resDigits[0] = d0 & MASKD0;

        result.isOne = d0 == 1 && isNonZero == 0;

        result.sign = sign * bsign;

        return result;
    }

    @Override
    public BigNum64 mult2() {

        BigNum64 res = new BigNum64();

        if(sign == 0) {
            return res;
        }

        long [] resDigits = res.digits;

        if(isOne) {
            resDigits[0] = 2;
            res.sign = sign;
            return res;
        }

        long isNonZero = 0;

        long digit = digits[fracDigits];
        long bit = digit >>> (SHIFTM1);
        long temp = digit << 1;
        resDigits[fracDigits] = temp;
        isNonZero |= temp;

        for(int i=fracDigits - 1; i > 0; i--) {
            digit = digits[i];
            temp = (digit << 1) | bit;
            bit = digit >>> (SHIFTM1);
            isNonZero |= temp;
            resDigits[i] = temp;
        }

        digit = digits[0];
        temp = (digit << 1) | bit;

        long d0 = temp & MASKD0;
        resDigits[0] = d0;
        res.sign = sign;
        res.isOne = d0 == 1 && isNonZero == 0;
        return res;

    }

    @Override
    public BigNum64 mult4() {

        BigNum64 res = new BigNum64();

        if(sign == 0) {
            return res;
        }

        long [] resDigits = res.digits;

        if(isOne) {
            resDigits[0] = 4;
            res.sign = sign;
            return res;
        }

        long isNonZero = 0;

        long digit = digits[fracDigits];
        long bit = digit >>> (SHIFTM2);
        long temp = digit << 2;
        resDigits[fracDigits] = temp;
        isNonZero |= temp;

        for(int i=fracDigits - 1; i > 0; i--) {
            digit = digits[i];
            temp = (digit << 2) | bit;
            bit = digit >>> (SHIFTM2);
            isNonZero |= temp;
            resDigits[i] = temp;
        }

        digit = digits[0];
        temp = (digit << 2) | bit;

        long d0 = temp & MASKD0;
        resDigits[0] = d0;
        res.sign = sign;
        res.isOne = d0 == 1 && isNonZero == 0;
        return res;

    }

    @Override
    public BigNum64 divide2() {

        BigNum64 res = new BigNum64();

        if(sign == 0) {
            return res;
        }

        long [] resDigits = res.digits;

        if(isOne) {
            resDigits[1] = 0x8000000000000000L;
            res.sign = sign;
            return res;
        }

        long isNonZero = 0;

        long d0 = digits[0];
        long temp = d0;
        long val = temp >>> 1;

        long bit = temp & 0x1;

        resDigits[0] = val;
        isNonZero |= val;

        for(int i=1; i < fracDigits; i++) {
            temp = digits[i];
            val = (bit << SHIFTM1) | (temp >>> 1);
            bit = temp & 0x1;
            resDigits[i] = val;
            isNonZero |= val;
        }

        temp = digits[fracDigits];
        val = (bit << SHIFTM1) | (temp >>> 1);
        resDigits[fracDigits] = val;
        isNonZero |= val;
        res.sign = sign;
        res.isOne = d0 == 1 && isNonZero == 0;
        return res;

    }

    @Override
    public BigNum64 divide4() {

        BigNum64 res = new BigNum64();

        if(sign == 0) {
            return res;
        }

        long [] resDigits = res.digits;

        if(isOne) {
            resDigits[1] = 0x4000000000000000L;
            res.sign = sign;
            return res;
        }

        long isNonZero = 0;


        long d0 = digits[0];
        long temp = d0;
        long val = temp >>> 2;

        long bits = temp & 0x3;

        resDigits[0] = val;
        isNonZero |= val;

        for(int i=1; i < fracDigits; i++) {
            temp = digits[i];
            val = (temp >>> 2) | (bits << SHIFTM2);
            bits = temp & 0x3;
            resDigits[i] = val;
            isNonZero |= val;
        }

        temp = digits[fracDigits];
        val = (temp >>> 2) | (bits << SHIFTM2);
        resDigits[fracDigits] = val;
        isNonZero |= val;
        res.sign = sign;
        res.isOne = d0 == 1 && isNonZero == 0;
        return res;

    }

    @Override
    public MantExp getMantExp() {

        if (sign == 0) {
            return new MantExp();
        }

        if(isOne) {
            return new MantExp(sign);
        }

        long[] digits = this.digits;

        int i;
        long digit = 0;
        if(offset == -1) {
            for (i = 0; i < digits.length; i++) {
                digit = digits[i];
                if (digit != 0) {
                    break;
                }
            }

            if (i == digits.length) {
                return new MantExp();
            }

        /*int r;
        for(r = i == 0 ? SHIFT : SHIFTM1; r >= 0; r--) {
            if(digits[i] >>> r != 0) {
                break;
            }
        }*/

            offset = i;
            bitOffset = (int)msbDeBruijn64(digit);
            scale = i == 0 ? bitOffset : -(((long) i) * SHIFT) + bitOffset;
        }
        else {
            i = offset;
            digit = digits[i];
        }

        long mantissa = 0;

        int temp = 52 - bitOffset;

        long val = digit;
        if(temp >= 0) {
            mantissa |= val << temp;
            mantissa &= 0xFFFFFFFFFFFFFL;
            if(temp == 0 && i + 1 < digits.length) {
                mantissa += (digits[i + 1] >>> (SHIFTM1)) & 0x1;
            }
        }
        else {
            int shift = -temp;
            mantissa |= val >>> shift;
            mantissa &= 0xFFFFFFFFFFFFFL;
            mantissa += (val >>> (shift - 1)) & 0x1;
        }

        //System.out.println(digits[i]);

        i++;

        int k;
        for(k = bitOffset ; k < 52 && i < digits.length; k+=SHIFT, i++) {
            val = digits[i];
            temp = 52 - k;

            if(temp > SHIFT) {
                mantissa |= val << (temp - SHIFT);
                mantissa &= 0xFFFFFFFFFFFFFL;
            }
            else {
                long temp2 = k + SHIFTM52;
                mantissa |= val >>> temp2;
                mantissa &= 0xFFFFFFFFFFFFFL;

                if(temp2 == 0 && i + 1 < digits.length) {
                    mantissa += (digits[i + 1] >>> (SHIFTM1)) & 0x1;
                }
                else {
                    mantissa += (val >>> (temp2 - 1)) & 0x1;
                }
            }
        }

        // Bit 63 (the bit that is selected by the mask 0x8000000000000000L) represents the sign of the floating-point number.
        // Bits 62-52 (the bits that are selected by the mask 0x7ff0000000000000L) represent the exponent.
        // Bits 51-0 (the bits that are selected by the mask 0x000fffffffffffffL) represent the significand (sometimes called the mantissa) of the floating-point number.

        long exp = scale + (mantissa >>> 52);
        double mantissaDouble;

        if(sign == -1) {
            mantissaDouble = Double.longBitsToDouble( (0x8000000000000000L) | (mantissa & 0xFFFFFFFFFFFFFL) | (0x3FF0000000000000L));
        }
        else {
            mantissaDouble = Double.longBitsToDouble((mantissa & 0xFFFFFFFFFFFFFL) | (0x3FF0000000000000L));
        }

        return new MantExp(exp, mantissaDouble);



    }

    public static BigNum64 max(BigNum64 a, BigNum64 b) {
        return a.compare(b) > 0 ? a : b;
    }

    public static BigNum64 min(BigNum64 a, BigNum64 b) {
        return a.compare(b) < 0 ? a : b;
    }

    public BigNum64 mult2to64(int times) {

        BigNum64 result = new BigNum64(this);

        if(times <= 0) {
            return result;
        }

        long[] digits = result.digits;
        int total = digits.length + times;
        long isNonZero = 0;

        for(int i = times, location = 0; i < total; i++, location++) {
            if(i < digits.length) {
                digits[location] = digits[i];
            }
            else {
                digits[location] = 0;
            }

            if(location != 0) {
                isNonZero |= digits[location];
            }
        }

        result.isOne = digits[0] == 1 && isNonZero == 0;
        return result;
    }

    @Override
    public BigNum64 div2toi(long power) {
        BigNum64 result = new BigNum64(this);

        if(power <= 0) {
            return result;
        }

        int times = (int)(power / SHIFT);

        int internalShift = (int)(power % SHIFT);

        long[] digits = result.digits;
        int total = -times;
        long isNonZero = 0;
        long val;

        for(int i = digits.length - 1 - times, location = digits.length - 1; i >= total; i--, location--) {
            if(i == 0) {
                digits[location] = val = digits[i] >>> internalShift;
            }
            else if (i > 0) {
                digits[location] = val = (digits[i - 1] << (SHIFT - internalShift)) | (digits[i] >>> internalShift);
            }
            else {
                digits[location] = val = 0;
            }

            if(location != 0) {
                isNonZero |= val;
            }
        }

        result.isOne = digits[0] == 1 && isNonZero == 0;
        return result;
    }

    @Override
    public BigNum64 mult2toi(long power) {

        BigNum64 result = new BigNum64(this);

        if(power <= 0) {
            return result;
        }

        int times = (int)(power / SHIFT);

        int internalShift = (int)(power % SHIFT);

        long[] digits = result.digits;
        int total = digits.length + times;
        long isNonZero = 0;
        long val;

        for(int i = times, location = 0; i < total; i++, location++) {
            if(i < digits.length - 1) {
                digits[location] = val = (digits[i] << internalShift) | (digits[i + 1] >>> (SHIFT - internalShift));
            }
            else if(i < digits.length) {
                digits[location] = val = (digits[i] << internalShift);
            }
            else {
                digits[location] = val = 0;
            }

            if(location != 0) {
                isNonZero |= val;
            }
        }

        result.isOne = digits[0] == 1 && isNonZero == 0;
        return result;
    }

    public BigNum64 divide2to64(int times) {

        BigNum64 result = new BigNum64(this);

        if(times <= 0) {
            return result;
        }

        long[] digits = result.digits;
        int total = -times;
        long isNonZero = 0;

        for(int i = digits.length - 1 - times, location = digits.length - 1; i >= total; i--, location--) {
            if(i >= 0) {
                digits[location] = digits[i];
            }
            else {
                digits[location] = 0;
            }

            if(location != 0) {
                isNonZero |= digits[location];
            }
        }

        result.isOne = digits[0] == 1 && isNonZero == 0;
        return result;
    }

    public BigNum64 shift2to64(int times) {
        if(times > 0) {
            return mult2to64(times);
        }
        else if(times < 0) {
            return divide2to64(-times);
        }

        return new BigNum64(this);
    }

    @Override
    public BigNum64 shift2toi(long power) {
        if(power > 0) {
            return mult2toi(power);
        }
        else if(power < 0) {
            return div2toi(-power);
        }

        return new BigNum64(this);
    }

    @Override
    public BigNum64 sqrt() {
        if(sign == 0 || isOne) {
            return new BigNum64(this);
        }
        if(sign < 0) {
            return new BigNum64();
        }
        BigNum64 one = new BigNum64(1);
        BigNum64 oneFourth = new BigNum64(0.25);

        BigNum64 a = new BigNum64(this);
        long divisions = 0;
        long multiplications = 0;
        if(a.compareBothPositive(one) > 0) { //scale it down between 1 and 1/4
            do {
                a = a.divide4();
                divisions++;
            } while (a.compareBothPositive(one) > 0);
        }
        else if(a.compareBothPositive(oneFourth) < 0) {

            int i;

            long[] digits = a.digits;
            for(i = 2; i < digits.length; i++) {
                if(digits[i] != 0) {
                    break;
                }
            }

            int numberOfLimbs = i - 2;

            if(numberOfLimbs > 0) {
                multiplications += ((long)numberOfLimbs * SHIFT) >>> 1;
                a = a.mult2to64(numberOfLimbs);
            }

            do {
                a = a.mult4();
                multiplications++;
            } while (a.compareBothPositive(oneFourth) < 0);
        }

        BigNum64 oneHalf = new BigNum64(1.5);
        BigNum64 aHalf = a.divide2(); // a / 2

        BigNum64 x = new BigNum64(1 / Math.sqrt(a.doubleValue())); // set the initial value to an approximation of 1 /sqrt(a)

        //Newton steps
        BigNum64 newX = x.mult(oneHalf.sub(aHalf.mult(x.squareFull()))); // x = (3/2 - (a/2)*x^2)*x

        BigNum64 epsilon = new BigNum64(1);
        epsilon.digits[epsilon.digits.length - 1] = 0x3;
        epsilon.digits[0] = 0;

        int iter = 0;
        while (newX.sub(x).abs_mutable().compareBothPositive(epsilon) >= 0 && iter < SQRT_MAX_ITERATIONS) {
            x = newX;
            newX = x.mult(oneHalf.sub(aHalf.mult(x.squareFull())));
            iter++;
        }

        BigNum64 sqrta = newX.mult(a); //sqrt(a) = a * (1 /sqrt(a));
        if(multiplications > 0) { //scale it up again
            sqrta = sqrta.div2toi(multiplications);
        }
        else if(divisions > 0) {
            sqrta = sqrta.mult2toi(divisions);
        }

        return sqrta;
    }

    public static void main(String[] args) {
        MyApfloat a = new MyApfloat("1.999999999999");
        BigNum64.reinitializeTest(64);//180
        BigNum64 b = new BigNum64(a);

//        MyApfloat.precision = 2000000;
//        BigNum64.reinitializeTest(240);
//
//        Apfloat a = new MyApfloat("1.999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
//        BigNum64 k = new BigNum64(a);

//        BigNum.reinitializeTest(110);
//        BigNum c = new BigNum(a);
//        c.multKaratsuba(c);

        //System.out.println(b);
        //System.out.println(b.multFull(b));
        //System.out.println(b.multFull(b).doubleValue());
        //System.out.println(b.doubleValue());
        //System.out.println(b.doubleValue2());

        double p = 1312.4325345;

//        BigNum64 r = new BigNum64(p);
//        System.out.println(r);
//        System.out.println(r.mult2());
//        r = r.mult4();
//        System.out.println(r);
//        //r = r.divide2();
//        r = r.divide2();
//        System.out.println(r);


        //System.out.println(Math.sqrt(p));

        //System.out.println(b);

        //System.out.println(b.div2toi(2));

        //System.out.println(b.multFullGolden(b));
        //System.out.println(b.squareFullGolden());

        //BigNum.reinitializeTest(110);
        //BigNum c = new BigNum(a);

//        System.out.println(c.sqrt());
//        System.out.println(b.sqrt());

        //System.out.println(c.squareFull());
        //System.out.println(b.squareFull());
        //System.out.println(b.squareFullGolden());
        //System.out.println(b.multFull(b));
//        System.out.println(k.multKaratsuba(k));
//        System.out.println(k.multFullGolden(k));
        //System.out.println(b.multKaratsubaGolden(b));
        //System.out.println(c.multKaratsuba(c));
        //System.out.println(b.squareFullGolden());

//        for(int i = -300; i <= 300; i++) {
//            if(b.shift2toi(i).doubleValue() != c.shift2toi(i).doubleValue()) {
//                System.out.println(i);
//            }
//        }

        /*long init = (1073741823L << 5) + 15;
        System.out.println(init);
        long sum = init;

        sum += (1073741823L + 1073741823L) * (1073741823L + 1073741823L);
        sum += (1073741823L + 1073741823L) * (1073741823L + 1073741823L);
        sum += (1073741823L + 1073741823L) * (1073741823L + 1073741823L);
        sum += (1073741823L + 1073741823L) * (1073741823L + 1073741823L);

        long carry = sum >>> 30;
        sum &= 0x3FFFFFFFL;

        System.out.println(sum);
        System.out.println(carry);


        sum = init;
        carry = 0;

        sum += (1073741823L + 1073741823L) * (1073741823L + 1073741823L);
        carry += sum >>> 30;
        sum &= 0x3FFFFFFFL;
        sum += (1073741823L + 1073741823L) * (1073741823L + 1073741823L);
        carry += sum >>> 30;
        sum &= 0x3FFFFFFFL;
        sum += (1073741823L + 1073741823L) * (1073741823L + 1073741823L);
        carry += sum >>> 30;
        sum &= 0x3FFFFFFFL;
        sum += (1073741823L + 1073741823L) * (1073741823L + 1073741823L);
        carry += sum >>> 30;
        sum &= 0x3FFFFFFFL;


        System.out.println(sum);
        System.out.println(carry);


        System.out.println("NEXT");

        init = 0;
        System.out.println(init);
        sum = init;

        long val = 1073741823L + 7;

        sum += (val + 1073741823L) * (1073741823L + val);
        sum += (1073741823L + 1073741823L) * (1073741823L + 1073741823L);
        sum += (1073741823L + 1073741823L) * (1073741823L + 1073741823L);
        sum += (1073741823L + 1073741823L) * (1073741823L + 1073741823L);

        carry = sum >>> 30;
        sum &= 0x3FFFFFFFL;

        System.out.println(sum);
        System.out.println(carry);


        sum = init;
        carry = 0;

        sum += (val + 1073741823L) * (1073741823L + val);
        carry += sum >>> 30;
        sum &= 0x3FFFFFFFL;
        sum += (1073741823L + 1073741823L) * (1073741823L + 1073741823L);
        carry += sum >>> 30;
        sum &= 0x3FFFFFFFL;
        sum += (1073741823L + 1073741823L) * (1073741823L + 1073741823L);
        carry += sum >>> 30;
        sum &= 0x3FFFFFFFL;
        sum += (1073741823L + 1073741823L) * (1073741823L + 1073741823L);
        carry += sum >>> 30;
        sum &= 0x3FFFFFFFL;


        System.out.println(sum);
        System.out.println(carry);*/


    }
}
