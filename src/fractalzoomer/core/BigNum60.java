package fractalzoomer.core;

import org.apfloat.Apfloat;
import org.apfloat.internal.LongMemoryDataStorage;

import java.util.Arrays;

import static fractalzoomer.core.MyApfloat.TWO;
import static org.apfloat.internal.LongRadixConstants.BASE_DIGITS;

public class BigNum60 extends BigNum {

    public static long getPrecision() {
        return (long)fracDigits * SHIFT;
    }
    public static String getName() {
        return "Built-in (60)";
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
    public static final long MASK = 0x0FFFFFFFFFFFFFFFL;
    public static final int SHIFT = 60;
    public static final long MASKD0 = 0x7FFFFFFFFFFFFFFFL;

    public static final long MASKI = 0x3FFFFFFFL;
    public static final int SHIFTI = 30;
    public static final int SHIFTIM1 = SHIFTI - 1;
    public static final long MASK31 = 0x40000000L;
    public static final int SHIFTM1 = SHIFT - 1;
    public static final int SHIFTM52 = SHIFT - 52;

    private static final double TWO_TO_SHIFT = Math.pow(2,-SHIFT);

    //Todo change this
    public static int THREADS_THRESHOLD = 141;

    public static void reinitialize(double digits) {

        double res = digits / SHIFT;
        int temp = (int) (res);

        if (temp == 0) {
            temp = 1;
        } else if (digits % SHIFT != 0) {
            //0 is floor
            if(TaskDraw.BIGNUM_INITIALIZATION_ALGORITHM == 1) { //always
                temp++;
            }
            else if (TaskDraw.BIGNUM_INITIALIZATION_ALGORITHM == 2) { //round
                temp = (int)(res + 0.5);
            }
        }

        fracDigits = temp * TaskDraw.BIGNUM_PRECISION_FACTOR;

        initializeInternal();
    }

    public static void reinitializeTest(double digits) {
        fracDigits = ((int)(digits / SHIFT) + 1) * TaskDraw.BIGNUM_PRECISION_FACTOR;
        initializeInternal();
    }

    private static void initializeInternal() {
        fracDigitsm1 = fracDigits - 1;
        fracDigitsp1 = fracDigits + 1;
        //useToDouble2 = fracDigits > 80;
        useKaratsuba = fracDigits > 20;
        fracDigitsHalf = fracDigits >> 1;
        fracDigits2 = fracDigits << 1;
        initialLength = fracDigitsHalf - ((fracDigitsp1) % 2);
        evenFracDigits = (fracDigits & 1) == 0;

        use_threads = TaskDraw.USE_THREADS_IN_BIGNUM_LIBS && fracDigits >= THREADS_THRESHOLD && Runtime.getRuntime().availableProcessors() >= 2;
    }

    private static BigNum60 getMax() {
        BigNum60 n = new BigNum60();
        n.digits[0] = MASKD0;
        n.sign = 1;
        return n;
    }

    protected BigNum60() {
        digits = new long[fracDigitsp1];
        sign = 0;
        isOne = false;
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    protected BigNum60(BigNum60 other) {
        digits = Arrays.copyOf(other.digits, other.digits.length);
        sign = other.sign;
        isOne = other.isOne;
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    protected BigNum60(String val) {
        this(new MyApfloat(val));
    }

    protected BigNum60(int val) {
        digits = new long[fracDigitsp1];
        digits[0] = Math.abs(val);
        isOne = val == 1 || val == -1;
        sign = (int)Math.signum(val);
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }
    protected BigNum60(double val) {

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
                        digits[i] = (mantissa << r) & MASK;
                    } else {
                        digits[i] = (mantissa >>> (-r)) & MASK;
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

    protected BigNum60(Apfloat val) {

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
        long v = digit;
        double d = v & ~(v >>> 1);
        //int r = (int)(((Double.doubleToRawLongBits(d) >> 52) & 0x7ff) - 1023); //Fix for the 64 bit, not needed here
        bitOffset = (int)((Double.doubleToRawLongBits(d) >> 52) - 1023);
        //r |= (r >> 31); //Fix for zero, not needed here

        scale = i == 0 ? bitOffset : -(((long)i) * SHIFT) + bitOffset;

        return scale;
    }

    public static BigNum60 getNegative(BigNum60 other) {

        BigNum60 copy = new BigNum60();
        copy.sign = other.sign;
        copy.isOne = other.isOne;

        long[] otherDigits = other.digits;
        long[] digits = copy.digits;

        long s = (~otherDigits[fracDigits] & MASK) + 1;
        digits[fracDigits] = s & MASK;
        for (int i = fracDigitsm1; i > 0 ; i--) {
            s = (~otherDigits[i] & MASK) + (s>>>SHIFT);
            digits[i] = s & MASK;
        }

        s = (~otherDigits[0]) + (s>>>SHIFT);
        digits[0] = s;

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

        long s = (~digits[fracDigits] & MASK) + 1;
        digits[fracDigits] = s & MASK;
        for (int i = fracDigitsm1; i > 0 ; i--) {
            s = (~digits[i] & MASK) + (s>>>SHIFT);
            digits[i] = s & MASK;
        }

        s = (~digits[0]) + (s>>>SHIFT);
        digits[0] = s;

    }

    @Override
    public BigNum60 negate() {

        BigNum60 res = new BigNum60(this);

        res.sign *= -1;

        return res;

    }

    @Override
    public BigNum60 abs() {

        BigNum60 res = new BigNum60(this);

        if(sign == -1) {
            res.sign = 1;
        }

        return res;
    }

    @Override
    public BigNum60 abs_mutable() {

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
        BigNum60 other = (BigNum60) otherb;

        int signA = sign;

        long[] otherDigits = other.digits;

        int signB = other.sign;

        long digit, otherDigit;

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
                    if (digit < otherDigit) {
                        return 1;
                    } else if (digit > otherDigit) {
                        return -1;
                    }
                }
            }
            else {
                for (int i = 0; i < digits.length; i++) {
                    digit = digits[i];
                    otherDigit = otherDigits[i];
                    if (digit < otherDigit) {
                        return -1;
                    } else if (digit > otherDigit) {
                        return 1;
                    }
                }
            }
        }

        return 0;

    }

    @Override
    public int compareBothPositive(BigNum otherb) {
        BigNum60 other = (BigNum60) otherb;

        int signA = sign;
        int signB = other.sign;

        if(signA == 0 && signB == 0) {
            return 0;
        }

        long[] otherDigits = other.digits;
        long digit, otherDigit;

        for (int i = 0; i < digits.length; i++) {
            digit = digits[i];
            otherDigit = otherDigits[i];
            if (digit < otherDigit) {
                return -1;
            } else if (digit > otherDigit) {
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
//    static int debruijn64[] =
//            {
//                    0, 58, 1, 59, 47, 53, 2, 60, 39, 48, 27, 54, 33, 42, 3, 61, 51, 37, 40, 49,
//                    18, 28, 20, 55, 30, 34, 11, 43, 14, 22, 4, 62, 57, 46, 52, 38, 26, 32, 41,
//                    50, 36, 17, 19, 29, 10, 13, 21, 56, 45, 25, 31, 35, 16, 9, 12, 44, 24, 15,
//                    8, 23, 7, 6, 5, 63
//            };
//
//    static long msbDeBruijn64(long v) {
//
//
//        /* Round down to one less than a power of 2. */
//        v |= v >>> 1;
//        v |= v >>> 2;
//        v |= v >>> 4;
//        v |= v >>> 8;
//        v |= v >>> 16;
//        v |= v >>> 32;
//
//        /* 0x03f6eaf2cd271461 is a hexadecimal representation of a De Bruijn
//         * sequence for binary words of length 6. The binary representation
//         * starts with 000000111111. This is required to make it work with one less
//         * than a power of 2 instead of an actual power of 2.
//         */
//
//        return debruijn64[(int)((v * 0x03f6eaf2cd271461L) >>> 58)];
//    }

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
            long v = digit;
            double d = v & ~(v >>> 1);
            //int r = (int)(((Double.doubleToRawLongBits(d) >> 52) & 0x7ff) - 1023); //Fix for the 64 bit, not needed here
            bitOffset = (int) ((Double.doubleToRawLongBits(d) >> 52) - 1023);
            //r |= (r >> 31); //Fix for zero, not needed here

            scale = i == 0 ? bitOffset : -(((long)i) * SHIFT) + bitOffset;
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

        for(int i=fracDigits; i>=0; i--) {
            ret *= TWO_TO_SHIFT;
            ret += digits[i];
        }

        ret *= sign;
        return ret;
    }

    @Override
    public BigNum60 add(BigNum aa) {
        BigNum60 a = (BigNum60) aa;

        BigNum60 result = new BigNum60();

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
                BigNum60 copy = getNegative(this);
                digits = copy.digits;
            }
            else
            {
                BigNum60 copy = getNegative(a);
                otherDigits = copy.digits;
            }
        }

        long s = 0;
        long isNonZero = 0;
        long temp;
        for(int i=fracDigits; i>0; i--) {
            s += digits[i]+otherDigits[i];
            temp = s&MASK;
            isNonZero |= temp;
            resDigits[i] = temp;
            s >>>= SHIFT;
        }
        temp = digits[0]+otherDigits[0]+s;
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
    public BigNum60 add(int a) {

        BigNum60 result = new BigNum60();

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
                BigNum60 copy = getNegative(this);
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
   public BigNum60 sub(BigNum aa) {
        BigNum60 a = (BigNum60) aa;

        BigNum60 result = new BigNum60();

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
                BigNum60 copy = getNegative(this);
                digits = copy.digits;
            }
            else {
                BigNum60 copy = getNegative(a);
                otherDigits = copy.digits;
            }
        }

       long s = 0;
       long isNonZero = 0;
       long temp;
       for(int i=fracDigits; i>0; i--) {
           s += digits[i]+otherDigits[i];
           temp = s&MASK;
           isNonZero |= temp;
           resDigits[i] = temp;
           s >>>= SHIFT;
       }
       temp = digits[0]+otherDigits[0]+s;
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
    public BigNum60 sub(int a) {

        BigNum60 result = new BigNum60();

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
                BigNum60 copy = getNegative(this);
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
    public BigNum60 square() {
        return squareFull();
    }

    @Override
    public BigNum60 squareFull() {
        BigNum60 result = new BigNum60();

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

        int newLength = (length >>> 1) << 1;
        boolean isOdd = (length & 1) == 1;

        for (j = 1, k = fracDigits; j <= newLength;) {
            bdigit = digits[j];
            adigit = digits[k];

            bjlo = bdigit & MASKI;
            aklo = adigit & MASKI;

            bjhi = bdigit >>> SHIFTI;
            akhi = adigit >>> SHIFTI;

            sum += bjhi * aklo;
            //System.out.println("1: a" + j + "hi  *  a"+k+"lo");
            sum += bjlo * akhi;
            //System.out.println("1: a" + j + "lo  *  a"+k+"hi");

            j++;k--;

            bdigit = digits[j];
            adigit = digits[k];

            bjlo = bdigit & MASKI;
            aklo = adigit & MASKI;

            bjhi = bdigit >>> SHIFTI;
            akhi = adigit >>> SHIFTI;

            sum += bjhi * aklo;
            //System.out.println("1: a" + j + "hi  *  a"+k+"lo");
            sum += bjlo * akhi;
            //System.out.println("1: a" + j + "lo  *  a"+k+"hi");

            j++;k--;

            carry += sum >>> SHIFTI;
            sum &= MASKI;
        }

        if(isOdd) {
            bdigit = digits[j];
            adigit = digits[k];

            bjlo = bdigit & MASKI;
            aklo = adigit & MASKI;

            bjhi = bdigit >>> SHIFTI;
            akhi = adigit >>> SHIFTI;

            sum += bjhi * aklo;
            //System.out.println("1: a" + j + "hi  *  a"+k+"lo");
            sum += bjlo * akhi;
            //System.out.println("1: a" + j + "lo  *  a"+k+"hi");
            carry += sum >>> SHIFTI;
            sum &= MASKI;
            j++;k--;
        }

        carry <<= 1;

        if(j == k) {
            bdigit = digits[j];
            bjhi = bdigit >>> SHIFTI;
            bjlo = bdigit & MASKI;
            sum += (bjhi * bjlo);
            sum <<= 1;
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
            //System.out.println("1: a" + j + "hi  *  a"+k+"lo");
            sum += bjlo * akhi;
            //System.out.println("1: a" + j + "lo  *  a"+k+"hi");

            sum <<= 1;
        }

        carry += sum >>> SHIFTI;
        sum &= MASKI;

        old_sum = carry + (sum >>> SHIFTIM1); // Roundish

        long d0 = digits[0];
        long d0lo = (int)d0;

        length = fracDigitsHalf;

        int toggle = evenFracDigits ? 0 : 1;

        for(int i = fracDigits; i > 0; i--, length -= toggle, toggle ^= 1) {

            sum = old_sum >>> 1;
            carry = 0;
            long sum2 = 0;
            long carry2 = 0;
            long extra = old_sum & 0x1; //For some reason this is better than adding old_sum in the end

            k = i;

            adigit = digits[k];

            aklo = adigit & MASKI;
            akhi = adigit >>> SHIFTI;

            bjlo = d0lo;

            int length2 = evenFracDigits ? length : length + 1;
            int length2m1 = length2 - 1;

            newLength = (length2m1 >>> 1) << 1;
            isOdd = (length2m1 & 1) == 1;

            sum += bjlo * aklo;
            sum2 += bjlo * akhi;
            carry += sum >>> SHIFTI;
            sum &= MASKI;

            carry2 += sum2 >>> SHIFTI;
            sum2 &= MASKI;

            for(j = 1; j < newLength;) {
                bdigit = digits[j];
                bjlo = bdigit & MASKI;
                bjhi = bdigit >>> SHIFTI;

                sum += bjhi * akhi;
                //System.out.println("1: b" + j + "hi  *  a"+k+"hi");

                k--;

                adigit = digits[k];

                aklo = adigit & MASKI;
                akhi = adigit >>> SHIFTI;

                //System.out.println("2: b" + j + "hi  *  a"+k+"lo");
                sum += bjlo * aklo;
                sum2 += bjhi * aklo + bjlo * akhi;

                j++;

                bdigit = digits[j];
                bjlo = bdigit & MASKI;
                bjhi = bdigit >>> SHIFTI;

                sum += bjhi * akhi;
                //System.out.println("1: b" + j + "hi  *  a"+k+"hi");

                k--;

                adigit = digits[k];

                aklo = adigit & MASKI;
                akhi = adigit >>> SHIFTI;

                //System.out.println("2: b" + j + "hi  *  a"+k+"lo");
                sum += bjlo * aklo;
                sum2 += bjhi * aklo + bjlo * akhi;

                j++;

                carry += sum >>> SHIFTI;
                sum &= MASKI;

                carry2 += sum2 >>> SHIFTI;
                sum2 &= MASKI;
            }

            if(isOdd) {
                bdigit = digits[j];
                bjlo = bdigit & MASKI;
                bjhi = bdigit >>> SHIFTI;

                sum += bjhi * akhi;
                //System.out.println("1: b" + j + "hi  *  a"+k+"hi");

                k--;

                adigit = digits[k];

                aklo = adigit & MASKI;
                akhi = adigit >>> SHIFTI;

                //System.out.println("2: b" + j + "hi  *  a"+k+"lo");
                sum += bjlo * aklo;
                sum2 += bjhi * aklo + bjlo * akhi;

                j++;
            }

            if(j == k) {
                sum <<= 1;
                sum += akhi * akhi + extra;
            }
            else {
                bdigit = digits[j];
                bjhi = bdigit >>> SHIFTI;

                sum += bjhi * akhi;

                k--;

                adigit = digits[k];

                aklo = adigit & MASKI;

                sum2 += bjhi * aklo;

                sum <<= 1;
                sum += aklo * aklo + extra;
            }

            sum2 <<= 1;
            carry <<= 1;
            carry2 <<= 1;

            /*newLength = (length >>> 1) << 1;
            isOdd = (length & 1) == 1;

            for(j = 0; j < newLength;) {

                sum += bjlo * aklo;
                //System.out.println("1: b" + j + "lo  *  a"+k+"lo");

                sum2 += bjlo * akhi;
                //System.out.println("2: b" + j + "lo  *  a"+k+"hi");

                j++;

                bdigit = digits[j];
                bjlo = bdigit & MASKI;
                bjhi = bdigit >>> SHIFTI;

                sum += bjhi * akhi;
                //System.out.println("1: b" + j + "hi  *  a"+k+"hi");

                k--;

                adigit = digits[k];

                aklo = adigit & MASKI;
                akhi = adigit >>> SHIFTI;

                //System.out.println("2: b" + j + "hi  *  a"+k+"lo");

                //2nd
                sum += bjlo * aklo;
                //System.out.println("1: b" + j + "lo  *  a"+k+"lo");

                sum2 += bjhi * aklo + bjlo * akhi;
                //System.out.println("2: b" + j + "lo  *  a"+k+"hi");

                j++;

                bdigit = digits[j];
                bjlo = bdigit & MASKI;
                bjhi = bdigit >>> SHIFTI;

                sum += bjhi * akhi;
                //System.out.println("1: b" + j + "hi  *  a"+k+"hi");

                k--;

                adigit = digits[k];

                aklo = adigit & MASKI;
                akhi = adigit >>> SHIFTI;

                sum2 += bjhi * aklo;
                //System.out.println("2: b" + j + "hi  *  a"+k+"lo");


                carry += sum >>> SHIFTI;
                sum &= MASKI;

                carry2 += sum2 >>> SHIFTI;
                sum2 &= MASKI;
            }

            if(isOdd) {
                sum += bjlo * aklo;
                //System.out.println("1: b" + j + "lo  *  a"+k+"lo");

                sum2 += bjlo * akhi;
                //System.out.println("2: b" + j + "lo  *  a"+k+"hi");

                j++;

                bdigit = digits[j];
                bjlo = bdigit & MASKI;
                bjhi = bdigit >>> SHIFTI;

                sum += bjhi * akhi;
                //System.out.println("1: b" + j + "hi  *  a"+k+"hi");

                k--;

                adigit = digits[k];

                aklo = adigit & MASKI;
                akhi = adigit >>> SHIFTI;

                //System.out.println("2: b" + j + "hi  *  a"+k+"lo");

                sum2 += bjhi * aklo;

            }

            if(j == k) {
                sum <<= 1;
                sum += aklo * aklo;
                //System.out.println("1: a" + j + "lo  *  a"+j+"lo");
            }
            else {
                sum += bjlo * aklo;
                sum2 += bjlo * akhi;
                //System.out.println("1: a" + j + "lo  *  a"+k+"lo");
                sum <<= 1;
                sum += akhi * akhi;
                //System.out.println("1: a" + k + "hi  *  a"+k+"hi");

//                carry1 += temp_sum1 >>> SHIFT;
//                temp_sum1 &= MASK;



                //System.out.println("2: a" + j + "lo  *  a"+k+"hi");

            }



            sum2 <<= 1;
            carry += carry; // <<= 1 is slower for some reason
            carry2 <<= 1;

             */

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
    public BigNum60 squareFullGolden() {


        BigNum60 result = new BigNum60();

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

        digits[0] = (int)origDigitsA[0];
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
                bj = digits[j];
                ak = digits[k];
                temp_sum += bj * ak;
                carry += temp_sum >>> SHIFTI;
                temp_sum &= MASKI;
            }

            carry <<= 1;

            bj = digits[j];
            ak = digits[k];
            temp_sum = (temp_sum << 1) + bj * ak;

            //carry += temp_sum >>> SHIFT; //Maybe its ok
            //temp_sum &= MASK;

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
                bj = digits[j];
                ak = digits[k];
                temp_sum += bj * ak;
                carry += temp_sum >>> SHIFTI;
                temp_sum &= MASKI;
            }

            carry <<= 1;
            temp_sum <<= 1;

            //carry += temp_sum >>> SHIFT; //Maybe its ok
            //temp_sum &= MASK;

            sum += temp_sum;
            carry += sum >>> SHIFTI;
            sum &= MASKI;

            finalVal |= sum << SHIFTI;

            resDigits[m] = finalVal;
            old_sum = carry;
        }

        sum = old_sum;

        long bj = digits[0];
        long ak = digits[0];
        sum += bj * ak;

        result.sign = 1;

        result.digits[0] = sum;

        return  result;
    }

    @Override
    public BigNum60 mult(BigNum bb) {
        BigNum60 b = (BigNum60) bb;
        if(useKaratsuba) {
            return multKaratsuba(b);
        }
        else {
            return multFull(b);
        }
    }

    @Deprecated
    public BigNum60 multFullGolden(BigNum60 b) {

        BigNum60 result = new BigNum60();

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
        digits[0] = (int)origDigitsA[0];
        bdigits[0] = (int)origDigitsB[0];

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
            carry = 0;
            for(int j = 0, k = i; j <= i; j++, k--) {
                long bj = bdigits[j];
                long ak = digits[k];

                sum += bj * ak;
                carry += sum >>> SHIFTI;
                sum &= MASKI;

            }

            long finalVal = sum;

            i--;//Unrolling

            sum = carry; //carry from prev
            carry = 0;
            for(int j = 0, k = i; j <= i; j++, k--) {
                long bj = bdigits[j];
                long ak = digits[k];

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

        long bj = bdigits[0];
        long ak = digits[0];

        sum += bj * ak;

        long d0 = sum & MASKD0;
        result.digits[0] = sum;
        result.isOne = d0 == 1 && isNonZero == 0;
        result.sign = sign * b.sign;

        return result;

    }

    public BigNum60 multFull(BigNum60 b) {

        BigNum60 result = new BigNum60();

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

        int newLength = fracDigitsHalf << 1;
        int j, k;

        for (j = 1, k = fracDigits; j <= newLength;) {

            bdigit = bdigits[j];
            adigit = digits[k];

            bjlo = bdigit & MASKI;
            aklo = adigit & MASKI;

            bjhi = bdigit >>> SHIFTI;
            akhi = adigit >>> SHIFTI;

            sum += bjhi * aklo;
            sum += bjlo * akhi;

            j++;k--;

            bdigit = bdigits[j];
            adigit = digits[k];

            bjlo = bdigit & MASKI;
            aklo = adigit & MASKI;

            bjhi = bdigit >>> SHIFTI;
            akhi = adigit >>> SHIFTI;

            sum += bjhi * aklo;
            sum += bjlo * akhi;

            carry += sum >>> SHIFTI;
            sum &= MASKI;

            j++;k--;
        }

        if(!evenFracDigits) {
            bdigit = bdigits[j];
            adigit = digits[k];

            bjlo = bdigit & MASKI;
            aklo = adigit & MASKI;

            bjhi = bdigit >>> SHIFTI;
            akhi = adigit >>> SHIFTI;

            sum += bjhi * aklo;
            sum += bjlo * akhi;

            carry += sum >>> SHIFTI;
            sum &= MASKI;
        }

        old_sum = carry + (sum >>> SHIFTIM1); // Roundish

        long carry2;
        long sum2;

        long isNonZero = 0;

        long bd0 = bdigits[0];
        long bd0lo = (int)bd0;

        for(int i = fracDigits; i > 0; i--) {

            sum = old_sum; //carry from prev
            carry = 0;
            carry2 = 0;
            sum2 = 0;
            k = i;
            adigit = digits[k];

            aklo = adigit & MASKI;
            akhi = adigit >>> SHIFTI;

            newLength = (i >>> 1) << 1;
            boolean isOdd = (i & 1) == 1;

            bjlo = bd0lo;

            for(j = 0; j < newLength;) {

                sum += bjlo * aklo;
                //System.out.println("1: b" + j + "lo  *  a"+k+"lo");

                sum2 += bjlo * akhi;
                //System.out.println("2: b" + j + "lo  *  a"+k+"hi");

                j++;

                bdigit = bdigits[j];
                bjlo = bdigit & MASKI;
                bjhi = bdigit >>> SHIFTI;

                sum += bjhi * akhi;
                //System.out.println("1: b" + j + "hi  *  a"+k+"hi");

                k--;

                adigit = digits[k];

                aklo = adigit & MASKI;
                akhi = adigit >>> SHIFTI;

                //System.out.println("2: b" + j + "hi  *  a"+k+"lo");

                //2nd
                sum += bjlo * aklo;
                //System.out.println("1: b" + j + "lo  *  a"+k+"lo");

                sum2 += bjhi * aklo + bjlo * akhi;
                //System.out.println("2: b" + j + "lo  *  a"+k+"hi");

                j++;

                bdigit = bdigits[j];
                bjlo = bdigit & MASKI;
                bjhi = bdigit >>> SHIFTI;

                sum += bjhi * akhi;
                //System.out.println("1: b" + j + "hi  *  a"+k+"hi");

                k--;

                adigit = digits[k];

                aklo = adigit & MASKI;
                akhi = adigit >>> SHIFTI;

                sum2 += bjhi * aklo;
                //System.out.println("2: b" + j + "hi  *  a"+k+"lo");


                carry += sum >>> SHIFTI;
                sum &= MASKI;

                carry2 += sum2 >>> SHIFTI;
                sum2 &= MASKI;
            }

            if(isOdd) {
                sum += bjlo * aklo;
                //System.out.println("1: b" + j + "lo  *  a"+k+"lo");

                sum2 += bjlo * akhi;
                //System.out.println("2: b" + j + "lo  *  a"+k+"hi");

                j++;

                bdigit = bdigits[j];
                bjlo = bdigit & MASKI;
                bjhi = bdigit >>> SHIFTI;

                sum += bjhi * akhi;
                //System.out.println("1: b" + j + "hi  *  a"+k+"hi");

                k--;

                adigit = digits[k];

                aklo = adigit & MASKI;

                sum2 += bjhi * aklo;
                //System.out.println("2: b" + j + "hi  *  a"+k+"lo");


//                carry += sum >>> SHIFTI;
//                sum &= MASKI;
//
//                carry2 += sum2 >>> SHIFTI;
//                sum2 &= MASKI;
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


    public BigNum60 multKaratsuba(BigNum60 b) {

        BigNum60 result = new BigNum60();

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

        long[] partialshi = new long[fracDigitsp1];
        long[] partialslo = new long[fracDigitsp1];

        long PartialSum = 0;
        long PartialCarry = 0;

        int newLength = fracDigitsHalf << 1;
        boolean isOdd = (fracDigits & 1) == 1;
        long p;
        int i;
        long bdigit;
        long adigit;
        long bhi, ahi, blo, alo;

        long bd0 = bdigits[0];
        long bd0lo = (int)bd0;
        long ad0 = digits[0];
        long ad0lo = (int)ad0;

        p = ad0lo * bd0lo;
        partialslo[0] = p;
        PartialSum += p;

        PartialCarry += PartialSum >>> SHIFTI;
        PartialSum &= MASKI;

        for(i = 1; i < newLength;) {
            adigit = digits[i];
            bdigit = bdigits[i];

            ahi = adigit >>> SHIFTI;
            alo = adigit & MASKI;

            bhi = bdigit >>> SHIFTI;
            blo = bdigit & MASKI;

            p = ahi * bhi;
            partialshi[i] = p;
            PartialSum += p;

            p = alo * blo;
            partialslo[i] = p;
            PartialSum += p;

            i++;

            adigit = digits[i];
            bdigit = bdigits[i];

            ahi = adigit >>> SHIFTI;
            alo = adigit & MASKI;

            bhi = bdigit >>> SHIFTI;
            blo = bdigit & MASKI;

            p = ahi * bhi;
            partialshi[i] = p;
            PartialSum += p;

            p = alo * blo;
            partialslo[i] = p;
            PartialSum += p;

            i++;

            PartialCarry += PartialSum >>> SHIFTI;
            PartialSum &= MASKI;
        }

        if(isOdd) {
            adigit = digits[i];
            bdigit = bdigits[i];

            ahi = adigit >>> SHIFTI;
            alo = adigit & MASKI;

            bhi = bdigit >>> SHIFTI;
            blo = bdigit & MASKI;

            p = ahi * bhi;
            partialshi[i] = p;
            PartialSum += p;

            p = alo * blo;
            partialslo[i] = p;
            PartialSum += p;

            PartialCarry += PartialSum >>> SHIFTI;
            PartialSum &= MASKI;
        }

        long old_sum;

        long p0 = partialslo[0];

        long sum = p0;

        int length = initialLength;

        int j;
        int k;
        long carry = sum >>> SHIFTI;
        sum &= MASKI;

        long bj, bk, aj, ak;

        newLength = (length >>> 1) << 1;
        isOdd = (length & 1) == 1;
        long bklo, ajhi, bjhi, aklo;
        long bjlo, akhi, bkhi, ajlo;

        for (j = 1, k = fracDigits; j <= newLength;) {
            bj = bdigits[j];
            bk = bdigits[k];
            aj = digits[j];
            ak = digits[k];

            bjhi = bj >>> SHIFTI;
            bjlo = bj & MASKI;

            ajhi = aj >>> SHIFTI;
            ajlo = aj & MASKI;

            bkhi = bk >>> SHIFTI;
            bklo = bk & MASKI;

            akhi = ak >>> SHIFTI;
            aklo = ak & MASKI;

            sum += (bjhi + bklo) * (aklo + ajhi) + (bjlo + bkhi) * (akhi + ajlo);


            j++; k--;

            bj = bdigits[j];
            bk = bdigits[k];
            aj = digits[j];
            ak = digits[k];

            bjhi = bj >>> SHIFTI;
            bjlo = bj & MASKI;

            ajhi = aj >>> SHIFTI;
            ajlo = aj & MASKI;

            bkhi = bk >>> SHIFTI;
            bklo = bk & MASKI;

            akhi = ak >>> SHIFTI;
            aklo = ak & MASKI;

            sum += (bjhi + bklo) * (aklo + ajhi) + (bjlo + bkhi) * (akhi + ajlo);

            j++; k--;

            carry += sum >>> SHIFTI;
            sum &= MASKI;

            //System.out.println("partials " + k + " partials " + j);

        }

        if(isOdd) {
            bj = bdigits[j];
            bk = bdigits[k];
            aj = digits[j];
            ak = digits[k];

            bjhi = bj >>> SHIFTI;
            bjlo = bj & MASKI;

            ajhi = aj >>> SHIFTI;
            ajlo = aj & MASKI;

            bkhi = bk >>> SHIFTI;
            bklo = bk & MASKI;

            akhi = ak >>> SHIFTI;
            aklo = ak & MASKI;

            sum += (bjhi + bklo) * (aklo + ajhi) + (bjlo + bkhi) * (akhi + ajlo);

            j++; k--;

            //carry += sum >>> SHIFTI;
            //sum &= MASKI;
        }

        if(j == k) {
            bj = bdigits[j];
            aj = digits[j];

            bjhi = bj >>> SHIFTI;
            bjlo = bj & MASKI;

            ajhi = aj >>> SHIFTI;
            ajlo = aj & MASKI;

            sum += (bjhi + bjlo) * (ajlo + ajhi);
        }
        else  {
            bj = bdigits[j];
            bk = bdigits[k];
            aj = digits[j];
            ak = digits[k];

            bjhi = bj >>> SHIFTI;
            bjlo = bj & MASKI;

            ajhi = aj >>> SHIFTI;
            ajlo = aj & MASKI;

            bkhi = bk >>> SHIFTI;
            bklo = bk & MASKI;

            akhi = ak >>> SHIFTI;
            aklo = ak & MASKI;

            sum += (bjhi + bklo) * (aklo + ajhi) + (bjlo + bkhi) * (akhi + ajlo);
        }

        carry += sum >>> SHIFTI;
        sum &= MASKI;

        sum -= PartialSum;
        carry -= ((sum & MASK31) >>> SHIFTI) + PartialCarry;
        sum &= MASKI;

        old_sum = carry + (sum >>> SHIFTIM1); // Roundish, adds bias because 10000 is always shifted upwards


        long isNonZero = 0;

        length = fracDigitsHalf;

        int toggle = evenFracDigits ? 0 : 1;

        for(i = fracDigits; i > 0; i--, length -= toggle, toggle ^= 1) {

            sum = old_sum;
            long sum2 = 0;
            carry = 0;
            long carry2 = 0;

            k = i;

            ak = digits[k];
            bk = bdigits[k];

            bkhi = bk >>> SHIFTI;
            bklo = bk & MASKI;
            akhi = ak >>> SHIFTI;
            aklo = ak & MASKI;

//            bjlo = bd0lo;
//            ajlo = ad0lo;
            //newLength = (length >>> 1) << 1;
            //isOdd = (length & 1) == 1;

           /* for(j = 0; j < newLength;) {
                sum += (bjlo + bklo) * (aklo + ajlo);
                sum2 += (bjlo + bkhi) * (akhi + ajlo);

                j++;
                aj = digits[j];
                bj = bdigits[j];

                bjhi = bj >>> SHIFTI;
                bjlo = bj & MASKI;
                ajhi = aj >>> SHIFTI;
                ajlo = aj & MASKI;

                sum += (bjhi + bkhi) * (akhi + ajhi);

                k--;
                ak = digits[k];
                bk = bdigits[k];

                bkhi = bk >>> SHIFTI;
                bklo = bk & MASKI;
                akhi = ak >>> SHIFTI;
                aklo = ak & MASKI;

                //2nd

                sum += (bjlo + bklo) * (aklo + ajlo);
                sum2 += (bjhi + bklo) * (aklo + ajhi) + (bjlo + bkhi) * (akhi + ajlo);

                j++;
                aj = digits[j];
                bj = bdigits[j];

                bjhi = bj >>> SHIFTI;
                bjlo = bj & MASKI;
                ajhi = aj >>> SHIFTI;
                ajlo = aj & MASKI;

                sum += (bjhi + bkhi) * (akhi + ajhi);

                k--;
                ak = digits[k];
                bk = bdigits[k];

                bkhi = bk >>> SHIFTI;
                bklo = bk & MASKI;
                akhi = ak >>> SHIFTI;
                aklo = ak & MASKI;

                sum2 += (bjhi + bklo) * (aklo + ajhi);

                carry += sum >>> SHIFTI;
                sum &= MASKI;
                carry2 += sum2 >>> SHIFTI;
                sum2 &= MASKI;
            }

            if(isOdd) {
                sum += (bjlo + bklo) * (aklo + ajlo);
                sum2 += (bjlo + bkhi) * (akhi + ajlo);

                j++;
                aj = digits[j];
                bj = bdigits[j];

                bjhi = bj >>> SHIFTI;
                bjlo = bj & MASKI;
                ajhi = aj >>> SHIFTI;
                ajlo = aj & MASKI;

                sum += (bjhi + bkhi) * (akhi + ajhi);

                k--;
                ak = digits[k];
                bk = bdigits[k];

                bkhi = bk >>> SHIFTI;
                bklo = bk & MASKI;
                akhi = ak >>> SHIFTI;
                aklo = ak & MASKI;

                sum2 += (bjhi + bklo) * (aklo + ajhi);


//                carry1 += sum1 >>> SHIFT;
//                sum1 &= MASK;
//                carry2 += sum2 >>> SHIFT;
//                sum2 &= MASK;
            }

            if(j == k) {
                sum += (partialslo[length] << 1);
            }
            else {
                sum += (bjlo + bklo) * (aklo + ajlo) + (partialshi[length + 1] << 1);
                sum2 += (bjlo + bkhi) * (akhi + ajlo);
            }*/

            sum += (bd0lo + bklo) * (aklo + ad0lo);
            sum2 += (bd0lo + bkhi) * (akhi + ad0lo);
            carry += sum >>> SHIFTI;
            sum &= MASKI;
            carry2 += sum2 >>> SHIFTI;
            sum2 &= MASKI;

            int length2 = evenFracDigits ? length : length + 1;
            int length2m1 = length2 - 1;

            newLength = (length2m1 >>> 1) << 1;
            isOdd = (length2m1 & 1) == 1;

            for(j = 1; j < newLength;) {
                aj = digits[j];
                bj = bdigits[j];

                bjhi = bj >>> SHIFTI;
                bjlo = bj & MASKI;
                ajhi = aj >>> SHIFTI;
                ajlo = aj & MASKI;

                sum += (bjhi + bkhi) * (akhi + ajhi);

                k--;

                ak = digits[k];
                bk = bdigits[k];

                bkhi = bk >>> SHIFTI;
                bklo = bk & MASKI;
                akhi = ak >>> SHIFTI;
                aklo = ak & MASKI;

                sum += (bjlo + bklo) * (aklo + ajlo);
                sum2 += (bjhi + bklo) * (aklo + ajhi) + (bjlo + bkhi) * (akhi + ajlo);

                j++;
                //2nd

                aj = digits[j];
                bj = bdigits[j];

                bjhi = bj >>> SHIFTI;
                bjlo = bj & MASKI;
                ajhi = aj >>> SHIFTI;
                ajlo = aj & MASKI;

                sum += (bjhi + bkhi) * (akhi + ajhi);

                k--;

                ak = digits[k];
                bk = bdigits[k];

                bkhi = bk >>> SHIFTI;
                bklo = bk & MASKI;
                akhi = ak >>> SHIFTI;
                aklo = ak & MASKI;

                sum += (bjlo + bklo) * (aklo + ajlo);
                sum2 += (bjhi + bklo) * (aklo + ajhi) + (bjlo + bkhi) * (akhi + ajlo);
                j++;

                carry += sum >>> SHIFTI;
                sum &= MASKI;
                carry2 += sum2 >>> SHIFTI;
                sum2 &= MASKI;
            }

            if(isOdd) {
                aj = digits[j];
                bj = bdigits[j];

                bjhi = bj >>> SHIFTI;
                bjlo = bj & MASKI;
                ajhi = aj >>> SHIFTI;
                ajlo = aj & MASKI;

                sum += (bjhi + bkhi) * (akhi + ajhi);

                k--;

                ak = digits[k];
                bk = bdigits[k];

                bkhi = bk >>> SHIFTI;
                bklo = bk & MASKI;
                akhi = ak >>> SHIFTI;
                aklo = ak & MASKI;

                sum += (bjlo + bklo) * (aklo + ajlo);
                sum2 += (bjhi + bklo) * (aklo + ajhi) + (bjlo + bkhi) * (akhi + ajlo);
                j++;

//                carry += sum >>> SHIFTI;
//                sum &= MASKI;
//                carry2 += sum2 >>> SHIFTI;
//                sum2 &= MASKI;
            }


            if(j == k) {
                sum += (partialshi[length2] << 1);
            }
            else {
                aj = digits[j];
                bj = bdigits[j];

                bjhi = bj >>> SHIFTI;
                //bjlo = bj & MASKI;
                ajhi = aj >>> SHIFTI;
                //ajlo = aj & MASKI;

                sum += (bjhi + bkhi) * (akhi + ajhi);

                k--;

                ak = digits[k];
                bk = bdigits[k];

                //bkhi = bk >>> SHIFTI;
                bklo = bk & MASKI;
                //akhi = ak >>> SHIFTI;
                aklo = ak & MASKI;

                sum2 += (bjhi + bklo) * (aklo + ajhi);


                sum += (partialslo[length2] << 1);
            }

            //System.out.println("NEXT");

            carry += sum >>> SHIFTI;
            sum &= MASKI;

            sum -= PartialSum;
            carry -= ((sum & MASK31) >>> SHIFTI) + PartialCarry;
            sum &= MASKI;

            long partialI = partialslo[i];

            PartialSum -= partialI & MASKI;
            PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFTI;
            PartialSum &= MASKI;

            sum2 += carry;
            carry2 += sum2 >>> SHIFTI;
            sum2 &= MASKI;


            sum2 -= PartialSum;

            carry2 -= ((sum2 & MASK31) >>> SHIFTI) + PartialCarry;
            sum2 &= MASKI;

            partialI = partialshi[i];

            PartialSum -= partialI & MASKI;
            PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFTI;
            PartialSum &= MASKI;

            long di = (sum2 << SHIFTI) | sum;
            isNonZero |= di;
            resDigits[i] = di;

            old_sum = carry2;
        }

        sum = p0 + old_sum;

        long d0 = sum & MASKD0;
        resDigits[0] = d0;

        //result.isOne = ( (((d0 ^ isNonZero) | d0) & ~(isNonZero & 0x1)) )  == 1
        result.isOne = d0 == 1 && isNonZero == 0;

        result.sign = sign * b.sign;

        return result;

    }

    @Deprecated
    public BigNum60 multKaratsubaGolden(BigNum60 b) {

        BigNum60 result = new BigNum60();

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

        long[] partials = new long[newLength];


        long PartialSum = 0;
        long PartialCarry = 0;

        long adigit = (int)origDigitsA[0];
        long bdigit = (int)origDigitsB[0];
        digits[0] = (int)adigit;
        bdigits[0] = (int)bdigit;

        long p = adigit * bdigit;
        long p0 = p;
        partials[0] = p;
        PartialSum += p;

        PartialCarry += PartialSum >>> SHIFTI;
        PartialSum &= MASKI;

        for(int i = 1, base = (i << 1) - 1; i <=fracDigits; i++) {

            bdigit = origDigitsB[i];
            adigit = origDigitsA[i];

            long bjlo = bdigit & MASKI;
            long aklo = adigit & MASKI;

            long bjhi = bdigit >>> SHIFTI;
            long akhi = adigit >>> SHIFTI;

            digits[base] = (int)(akhi);
            bdigits[base] = (int)(bjhi);
            p = akhi * bjhi;
            partials[base] = p;
            PartialSum += p;
            PartialCarry += PartialSum >>> SHIFTI;
            PartialSum &= MASKI;

            base++;
            digits[base] = (int)(aklo);
            bdigits[base] = (int)(bjlo);
            p = aklo * bjlo;
            partials[base] = p;
            PartialSum += p;

            PartialCarry += PartialSum >>> SHIFTI;
            PartialSum &= MASKI;
            base++;


        }

        long old_sum;

        long sum = p0;

        int length = fracDigits;//newFracDigits >> 1;

        int j;
        int k;
        long carry = 0;
        for (j = 1, k = newFracDigits; j <= length; j++, k--) {
            long bj = bdigits[j];
            long bk = bdigits[k];
            long aj = digits[j];
            long ak = digits[k];

            sum += (bk + bj) * (ak + aj);
            carry += sum >>> SHIFTI;
            sum &= MASKI;

            //System.out.println("partials " + k + " partials " + j);

        }

        long bj, bk, aj, ak;

        sum -= PartialSum;
        carry -= ((sum & MASK31) >>> SHIFTI) + PartialCarry;
        sum &= MASKI;

        old_sum = carry + (sum >>> SHIFTIM1); // Roundish


        long isNonZero = 0;
        int m = fracDigits;
        for(int i = newFracDigits; i > 0; i--, m--) {

            length = (i >> 1) - 1;

            sum = old_sum; //carry from prev

            carry = 0;
            for(j = 0, k = i; j <= length; j++, k--) {
                bj = bdigits[j];
                bk = bdigits[k];
                aj =  digits[j];
                ak = digits[k];

                sum += (bk + bj) * (ak + aj);

                carry += sum >>> SHIFTI;
                sum &= MASKI;
                //System.out.println("b " + k + " + b " + j + " * a " + k + " + a " + j);
                //System.out.println("partials " + k + " partials " + j);

            }


            // System.out.println("partials " + k);
            //System.out.println("NEXT");
            sum += partials[k] << 1;

            carry += sum >>> SHIFTI;
            sum &= MASKI;

            sum -= PartialSum;
            carry -= ((sum & MASK31) >>> SHIFTI) + PartialCarry;
            sum &= MASKI;

            long partialI = partials[i];
            PartialSum -= partialI & MASKI;
            PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFTI;
            //PartialCarry -= ((PartialSum & MASK31) >>> SHIFTI);
            PartialSum &= MASKI;
            //PartialCarry -= partialI >>> SHIFTI;

            long finalVal = sum;

            i--;//Unrolling

            length = i >> 1;

            sum = carry;

            carry = 0;
            for(j = 0, k = i; j <= length; j++, k--) {
                bj = bdigits[j];
                bk = bdigits[k];
                aj =  digits[j];
                ak = digits[k];

                sum += (bk + bj) * (ak + aj);

                carry += sum >>> SHIFTI;
                sum &= MASKI;

                //System.out.println("b " + k + " + b " + j + " * a " + k + " + a " + j);
            }

            sum -= PartialSum;
            carry -= ((sum & MASK31) >>> SHIFTI) + PartialCarry;
            finalVal |= (sum & MASKI) << SHIFTI;

            partialI = partials[i];
            PartialSum -= partialI & MASKI;
            PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFTI;
            //PartialCarry -= ((PartialSum & MASK31) >>> SHIFTI);
            PartialSum &= PartialSum & MASKI;
            //PartialCarry -= partialI >>> SHIFTI;

            isNonZero |= finalVal;
            resDigits[m] = finalVal;
            old_sum = carry;
        }

        sum = old_sum;

        sum += p0;

        long d0 = sum & MASKD0;
        result.digits[0] = sum;
        result.isOne = d0 == 1 && isNonZero == 0;
        result.sign = sign * b.sign;

        return result;

    }

    @Override
    /* Sign is positive */
    public BigNum60 mult(int value) {
        BigNum60 result = new BigNum60();

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

        long isNonZero = 0;
        long digit, digitlo, digithi;
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
    public BigNum60 mult2() {

        BigNum60 res = new BigNum60();

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

        long temp = (digits[fracDigits] << 1);
        long val = temp & MASK;
        resDigits[fracDigits] = val;
        isNonZero |= val;

        for(int i=fracDigits - 1; i > 0; i--) {
            temp = (digits[i] << 1) | ((temp >>> (SHIFT)));
            val = temp & MASK;
            isNonZero |= val;
            resDigits[i] = val;
        }

        temp = ((digits[0]) << 1) | ((temp >>> (SHIFT)));
        long d0 = temp & MASKD0;
        resDigits[0] = d0;
        res.sign = sign;
        res.isOne = d0 == 1 && isNonZero == 0;
        return res;

    }

    @Override
    public BigNum60 mult4() {

        BigNum60 res = new BigNum60();

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

        long temp = (digits[fracDigits] << 2);
        long val = temp & MASK;
        resDigits[fracDigits] = val;
        isNonZero |= val;

        for(int i=fracDigits - 1; i > 0; i--) {
            temp = (digits[i] << 2) | ((temp >>> (SHIFT)));
            val = temp & MASK;
            isNonZero |= val;
            resDigits[i] = val;
        }

        temp = ((digits[0]) << 2) | ((temp >>> (SHIFT)));
        long d0 = temp & MASKD0;
        resDigits[0] = d0;
        res.sign = sign;
        res.isOne = d0 == 1 && isNonZero == 0;
        return res;

    }

    @Override
    public BigNum60 divide2() {

        BigNum60 res = new BigNum60();

        if(sign == 0) {
            return res;
        }

        long [] resDigits = res.digits;

        if(isOne) {
            resDigits[1] = 0x800000000000000L;
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
            temp |= (bit << SHIFT);
            bit = temp & 0x1;
            temp = temp >>> 1;
            val = temp & MASK;
            resDigits[i] = val;
            isNonZero |= val;
        }

        temp = digits[fracDigits];
        temp |= (bit << SHIFT);
        temp = temp >>> 1;
        val = temp & MASK;
        resDigits[fracDigits] = val;
        isNonZero |= val;
        res.sign = sign;
        res.isOne = d0 == 1 && isNonZero == 0;
        return res;

    }

    @Override
    public BigNum60 divide4() {

        BigNum60 res = new BigNum60();

        if(sign == 0) {
            return res;
        }

        long [] resDigits = res.digits;

        if(isOne) {
            resDigits[1] = 0x400000000000000L;
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
            temp |= (bits << SHIFT);
            bits = temp & 0x3;
            temp = temp >>> 2;
            val = temp & MASK;
            resDigits[i] = val;
            isNonZero |= val;
        }

        temp = digits[fracDigits];
        temp |= (bits << SHIFT);
        temp = temp >>> 2;
        val = temp & MASK;
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
            long v = digit;
            double d = v & ~(v >>> 1);
            //int r = (int)(((Double.doubleToRawLongBits(d) >> 52) & 0x7ff) - 1023); //Fix for the 64 bit, not needed here
            bitOffset = (int) (((Double.doubleToRawLongBits(d) >> 52)) - 1023);
            //r |= (r >> 31); //Fix for zero, not needed here

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

    public static BigNum60 max(BigNum60 a, BigNum60 b) {
        return a.compare(b) > 0 ? a : b;
    }

    public static BigNum60 min(BigNum60 a, BigNum60 b) {
        return a.compare(b) < 0 ? a : b;
    }

    public BigNum60 mult2to60(int times) {

        BigNum60 result = new BigNum60(this);

        if(times <= 0) {
            return result;
        }

        long[] digits = result.digits;
        int total = digits.length + times;
        long isNonZero = 0;

        for(int i = times, location = 0; i < total; i++, location++) {
            if(i < digits.length) {
                if(location == 0) {
                    long value = digits[i - 1];
                    long finalValue = digits[i];
                    finalValue |= (value & 0x1) << SHIFT;
                    digits[location] = finalValue;
                }
                else {
                    digits[location] = digits[i];
                }
            }
            else {
                int im1 = i - 1;
                if(location == 0 && im1 < digits.length) {
                    long value = digits[im1];
                    long finalValue = (value & 0x1) << SHIFT;
                    digits[location] = finalValue;
                }
                else {
                    digits[location] = 0;
                }
            }

            if(location != 0) {
                isNonZero |= digits[location];
            }
        }

        result.isOne = digits[0] == 1 && isNonZero == 0;
        return result;
    }

    @Override
    public BigNum60 div2toi(long power) {
        BigNum60 result = new BigNum60(this);

        if(power <= 0) {
            return result;
        }

        int times = (int)(power / SHIFT);

        int internalShift = (int)(power % SHIFT);

        long[] digits = result.digits;
        int total = -times;
        long isNonZero = 0;

        for(int i = digits.length - 1 - times, location = digits.length - 1; i >= total; i--, location--) {
            if(i >= 0) {
                long value = digits[i] >>> internalShift;

                if(value > MASK) {
                    if(location != 0) {
                        digits[location] = value & MASK;
                    }
                    else {
                        digits[location] = value;
                    }

                    i--;
                    location--;
                    if(location >= 0) {
                        digits[location] = 0x1;
                    }
                }
                else {
                    long finalValue = value;
                    int im1 = i - 1;
                    if(im1 >= 0) {
                        int diff = SHIFT - internalShift;
                        finalValue |= (digits[im1] & (0xFFFFFFFFFFFFFFFFL >>> diff)) << diff;
                    }
                    if(location == 0) {
                        digits[location] = finalValue & MASKD0;
                    }
                    else {
                        digits[location] = finalValue & MASK;
                    }
                }
            }
            else {
                int ip1 = i + 1;
                if(ip1 == 0 && (digits[ip1] >>> internalShift) > MASK) {
                    digits[location] = 0x1;
                }
                else {
                    digits[location] = 0;
                }
            }

            if(location != 0) {
                isNonZero |= digits[location];
            }
        }

        result.isOne = digits[0] == 1 && isNonZero == 0;
        return result;
    }

    @Override
    public BigNum60 mult2toi(long power) {

        BigNum60 result = new BigNum60(this);

        if(power <= 0) {
            return result;
        }

        int times = (int)(power / SHIFT);

        int internalShift = (int)(power % SHIFT);

        long[] digits = result.digits;
        int total = digits.length + times;
        long isNonZero = 0;

        for(int i = times, location = 0; i < total; i++, location++) {
            if(i < digits.length) {

                long value = digits[i] << internalShift;

                int im1 = i - 1;
                if(location == 0 && im1 >= 0 && internalShift == 0) {
                    value |= (digits[im1] & 0x1) << SHIFT;
                }

                int ip1 = i + 1;
                if(ip1 < digits.length) {
                    value |= digits[ip1] >>> (SHIFT - internalShift);
                }
                if(location == 0) {
                    digits[location] = value & MASKD0;
                }
                else {
                    digits[location] = value & MASK;
                }

            }
            else {
                int im1 = i - 1;
                if(location == 0 && im1 < digits.length && internalShift == 0) {
                    long value = (digits[im1] & 0x1) << SHIFT;
                    digits[location] = value & MASKD0;
                }
                else {
                    digits[location] = 0;
                }
            }

            if(location != 0) {
                isNonZero |= digits[location];
            }
        }

        result.isOne = digits[0] == 1 && isNonZero == 0;
        return result;
    }

    public BigNum60 divide2to60(int times) {

        BigNum60 result = new BigNum60(this);

        if(times <= 0) {
            return result;
        }

        long[] digits = result.digits;
        int total = -times;
        long isNonZero = 0;

        for(int i = digits.length - 1 - times, location = digits.length - 1; i >= total; i--, location--) {
            if(i >= 0) {
                long value = digits[i];

                if(value > MASK) {
                    digits[location] = value & MASK;
                    i--;
                    location--;
                    digits[location] = 0x1;
                }
                else {
                    digits[location] = value;
                }
            }
            else {
                int ip1 = i + 1;
                if(ip1 == 0 && digits[ip1]  > MASK) {
                    digits[location] = 0x1;
                }
                else {
                    digits[location] = 0;
                }
            }

            if(location != 0) {
                isNonZero |= digits[location];
            }
        }

        result.isOne = digits[0] == 1 && isNonZero == 0;
        return result;
    }

    public BigNum60 shift2to60(int times) {
        if(times > 0) {
            return mult2to60(times);
        }
        else if(times < 0) {
            return divide2to60(-times);
        }

        return new BigNum60(this);
    }

    @Override
    public BigNum60 shift2toi(long power) {
        if(power > 0) {
            return mult2toi(power);
        }
        else if(power < 0) {
            return div2toi(-power);
        }

        return new BigNum60(this);
    }

    @Override
    public BigNum60 sqrt() {
        if(sign == 0 || isOne) {
            return new BigNum60(this);
        }
        if(sign < 0) {
            return new BigNum60();
        }
        BigNum60 one = new BigNum60(1);
        BigNum60 oneFourth = new BigNum60(0.25);

        BigNum60 a = new BigNum60(this);
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
                a = a.mult2to60(numberOfLimbs);
            }

            do {
                a = a.mult4();
                multiplications++;
            } while (a.compareBothPositive(oneFourth) < 0);
        }

        BigNum60 oneHalf = new BigNum60(1.5);
        BigNum60 aHalf = a.divide2(); // a / 2

        BigNum60 x = new BigNum60(1 / Math.sqrt(a.doubleValue())); // set the initial value to an approximation of 1 /sqrt(a)

        //Newton steps
        BigNum60 newX = x.mult(oneHalf.sub(aHalf.mult(x.squareFull()))); // x = (3/2 - (a/2)*x^2)*x

        BigNum60 epsilon = new BigNum60(1);
        epsilon.digits[epsilon.digits.length - 1] = 0x3;
        epsilon.digits[0] = 0;

        int iter = 0;
        while (newX.sub(x).abs_mutable().compareBothPositive(epsilon) >= 0 && iter < SQRT_MAX_ITERATIONS) {
            x = newX;
            newX = x.mult(oneHalf.sub(aHalf.mult(x.squareFull())));
            iter++;
        }

        BigNum60 sqrta = newX.mult(a); //sqrt(a) = a * (1 /sqrt(a));
        if(multiplications > 0) { //scale it up again
            sqrta = sqrta.div2toi(multiplications);
        }
        else if(divisions > 0) {
            sqrta = sqrta.mult2toi(divisions);
        }

        return sqrta;
    }

    public static void main(String[] args) {
        MyApfloat.setPrecision(141);

        MyApfloat re = new MyApfloat("-0.74836379425363001495034832147870348765915884117169331481867446308835696434466935951547711102992018544177150733728857158059944201760589035");
        MyApfloat im = new MyApfloat("-0.06744780927701335119415722995059170914204410549488939856276708324527235898639356887734291279309469373068713442093807553848565543917545272");

        BigNum60 zreb = new BigNum60();
        BigNum60 zimb = new BigNum60();
        BigNum60 creb = new BigNum60(re);
        BigNum60 cimb = new BigNum60(im);

        int max_iterations = 16125356;

        long time = System.currentTimeMillis();
        int i;
        for(i = 0; i < max_iterations; i++) {
            double dre = zreb.doubleValue();
            double dim = zimb.doubleValue();
            if(dre * dre + dim * dim >= 4) {
                break;
            }

            BigNum60 temp = zreb.add(zimb).mult(zreb.sub(zimb)).add(creb);
            zimb = zreb.mult(zimb).mult2().add(cimb);
            zreb = temp;
        }

        System.out.println("BigNum60");
        System.out.println(i);
        System.out.println(System.currentTimeMillis() - time);
        System.out.println((BigNum60.fracDigits + 1) * 60);


        BigNum30 zreb30 = new BigNum30();
        BigNum30 zimb30 = new BigNum30();
        BigNum30 creb30 = new BigNum30(re);
        BigNum30 cimb30 = new BigNum30(im);


        time = System.currentTimeMillis();
        for(i = 0; i < max_iterations; i++) {
            double dre = zreb30.doubleValue();
            double dim = zimb30.doubleValue();
            if(dre * dre + dim * dim >= 4) {
                break;
            }

            BigNum30 temp = zreb30.add(zimb30).mult(zreb30.sub(zimb30)).add(creb30);
            zimb30 = zreb30.mult(zimb30).mult2().add(cimb30);
            zreb30 = temp;
        }

        System.out.println("BigNum30");
        System.out.println(i);
        System.out.println(System.currentTimeMillis() - time);


        BigIntNum zrebi = new BigIntNum();
        BigIntNum zimbi = new BigIntNum();
        BigIntNum crebi = new BigIntNum(re);
        BigIntNum cimbi = new BigIntNum(im);


//        double bitCount1 = 0;
//        double bitCount2 = 0;
        time = System.currentTimeMillis();
        for(i = 0; i < max_iterations; i++) {
            double dre = zrebi.doubleValue();
            double dim = zimbi.doubleValue();
            if(dre * dre + dim * dim >= 4) {
                break;
            }

            BigIntNum temp = zrebi.add(zimbi).mult(zrebi.sub(zimbi)).add(crebi);
//            bitCount2 += (zrebi.add(zimbi).getBitLength() + zrebi.sub(zimbi).getBitLength()) / 2.0;
//            bitCount1 += (zrebi.getBitLength() + zimbi.getBitLength()) / 2.0;
            zimbi = zrebi.mult(zimbi).mult2().add(cimbi);
            zrebi = temp;

        }

        System.out.println("BigIntNum");
        System.out.println(i);
        System.out.println(System.currentTimeMillis() - time);
        //System.out.println(bitCount1 /i + " " + bitCount2 / i);
    }
}
