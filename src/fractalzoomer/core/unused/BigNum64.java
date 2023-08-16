package fractalzoomer.core.unused;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.core.TaskDraw;
import org.apfloat.Apfloat;
import org.apfloat.internal.LongMemoryDataStorage;

import java.util.Arrays;

import static fractalzoomer.core.MyApfloat.TWO;
import static org.apfloat.internal.LongRadixConstants.BASE_DIGITS;

public class BigNum64 {
    private long[] digits = null;
    private int sign;
    private boolean isOne;
    private long scale;
    private int offset;
    private int bitOffset;
    public static int fracDigits = 2;
    public static int fracDigitsm1 = fracDigits - 1;
    public static int fracDigitsp1 = fracDigits + 1;

    public static final long MASK = 0x0FFFFFFFFFFFFFFFL;
    public static final int SHIFT = 60;
    public static final long MASKD0 = 0x7FFFFFFFFFFFFFFFL;

    public static final long MASKI = 0x3FFFFFFFL;
    public static final int SHIFTI = 30;
    public static final long MASK31 = 0x40000000L;
    public static final int SHIFTM1 = SHIFT - 1;
    public static final int SHIFTM52 = SHIFT - 52;
    public static int fracDigitsShift = fracDigits * SHIFT;
    public static boolean useToDouble2 = fracDigits > 60;
    public static boolean useKaratsuba = fracDigits > 15;
    public static int fracDigits2 = fracDigits << 1;
    public static int fracDigits05 = fracDigits >> 1;

    private static final double TWO_TO_SHIFT = Math.pow(2,-SHIFT);

    public static void reinitialize(double digits) {

        fracDigits = (int)((digits / SHIFT) * TaskDraw.BIGNUM_PRECISION_FACTOR) + 1;
        fracDigitsm1 = fracDigits - 1;
        fracDigitsp1 = fracDigits + 1;
        fracDigitsShift = fracDigits * SHIFT;
        useToDouble2 = fracDigits > 60;
        useKaratsuba = fracDigits > 15;
        fracDigits2 = fracDigits << 1;
        fracDigits05 = fracDigits >> 1;

    }

    public static BigNum64 getMax() {
        BigNum64 n = new BigNum64();
        n.digits[0] = MASKD0;
        n.sign = 1;
        return n;
    }

    public BigNum64() {
        digits = new long[fracDigitsp1];
        sign = 0;
        isOne = false;
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    public BigNum64(int val) {
        digits = new long[fracDigitsp1];
        digits[0] = val;
        isOne = val == 1 || val == -1;
        sign = (int)Math.signum(val);
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    public BigNum64(BigNum64 other) {
        digits = Arrays.copyOf(other.digits, other.digits.length);
        sign = other.sign;
        isOne = other.isOne;
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    public BigNum64(double val) {

        digits = new long[fracDigitsp1];
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
    }

    public BigNum64(Apfloat val) {

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

        int length = (data.length - offset) * base_digits;
        int length2 = fracDigitsShift;
        int dataOffset = exponent < 0 ? Math.abs(exponent) * base_digits : 0;

        for(int i = dataOffset, j = 0; i <  length2 && i < length; i++, j++) {
            int shift = base_digits - (j % base_digits) - 1;
            long bit =  (data[j / base_digits + offset] >>> shift) & 0x1;

            digits[(i / SHIFT) + 1] |= bit << (SHIFT - (i % SHIFT) - 1);
        }

        scale = 0;
        this.offset = -1;
        bitOffset = -1;

    }

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
        bitOffset = (int)(((Double.doubleToRawLongBits(d) >> 52)) - 1023);
        //r |= (r >> 31); //Fix for zero, not needed here

        scale = digits[0] != 0 ? ((long)i) * SHIFT + bitOffset : -(((long)i) * SHIFT) + bitOffset;

        return scale;
    }

    public static BigNum64 getNegative(BigNum64 other) {

        BigNum64 copy = new BigNum64();
        copy.sign = other.sign;;
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

    public BigNum64 negate() {

        BigNum64 res = new BigNum64(this);

        res.sign *= -1;

        return res;

    }

    public BigNum64 abs() {

        BigNum64 res = new BigNum64(this);

        if(sign == -1) {
            res.sign = 1;
        }

        return res;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder((sign == -1 ? "-" : "") + toHexString(digits[0]));
        for(int i=1; i<=fracDigits; i++) {
            ret.append(i==1 ? "." : " ");
            ret.append(toHexString(digits[i]));
        }
        return ret.toString();
    }

    private static final String ZEROS = "0000000000000000000000000000000000000000000000000000000000000000";


    public static String toHexString(long l) {
        String s = Long.toHexString(l);
        return ZEROS.substring(0,16-s.length())+s;
    }

    public void print() {

        Apfloat sum = MyApfloat.ZERO;
        int decimal_bit_count = -1;

        if(sign == -1) {
            System.out.print("-");
        }
        for (int i = 0; i < digits.length; i++) {

            if (i == 0) {
                boolean zero = true;
                for (int j = SHIFTM1; j >= 0; j--) {
                    sum = MyApfloat.fp.add(sum, MyApfloat.fp.multiply(MyApfloat.fp.pow(TWO, j), new MyApfloat((digits[i] >> j) & 0x1)));

                    if (((digits[i] >> j) & 0x1) == 1) {
                        zero = false;
                    }

                    if (!zero) {
                        System.out.print((digits[i] >> j) & 0x1);
                    }
                }
                if(zero) {
                    System.out.print("0");
                }
                System.out.print(".");
            } else {
                for (int j = SHIFTM1; j >= 0; j--) {
                    sum = MyApfloat.fp.add(sum, MyApfloat.fp.multiply(MyApfloat.fp.pow(TWO, decimal_bit_count), new MyApfloat((digits[i] >> j) & 0x1)));
                    decimal_bit_count--;

                    System.out.print((digits[i] >> j) & 0x1);

                }

            }
        }

        sum = MyApfloat.fp.multiply(sum, new MyApfloat(sign));

        System.out.println();
        //System.out.println(sum.toRadix(2).toString(true));
        //System.out.println(sum);



    }

    public int compare(BigNum64 other) {

        int signA = sign;

        long[] otherDigits = other.digits;

        int signB = other.sign;

        if(signA < signB) {
            return -1;
        }
        else if(signA > signB) {
            return 1;
        }
        else if(signA == signB) {
            if (signA == 0) {
                return 0;
            }

            if(signA < 0) {
                for (int i = 0; i < digits.length; i++) {
                    long digit = digits[i];
                    long otherDigit = otherDigits[i];
                    if (digit < otherDigit) {
                        return 1;
                    } else if (digit > otherDigit) {
                        return -1;
                    }
                }
            }
            else {
                for (int i = 0; i < digits.length; i++) {
                    long digit = digits[i];
                    long otherDigit = otherDigits[i];
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

    static long binarySearch64(long v) {

        int shift = 32;

        long res = v >>> shift;

        if(res == 1) {
            return shift;
        }
        else if (res > 1) {
            shift +=  16; //+16
        }
        else {
            shift -=  16; // -16
        }

        res = v >>> shift;

        if(res == 1) {
            return shift;
        }
        else if (res > 1) {
            shift +=  8; //+8
        }
        else {
            shift -=  8; // -8
        }

        res = v >>> shift;

        if(res == 1) {
            return shift;
        }
        else if (res > 1) {
            shift +=  4; // +4
        }
        else {
            shift -=  4; // -4
        }

        res = v >>> shift;

        if(res == 1) {
            return shift;
        }
        else if (res > 1) {
            shift +=  2; // + 2
        }
        else {
            shift -=  2; // -2
        }

        res = v >>> shift;

        if(res == 1) {
            return shift;
        }
        else if (res > 1) {
            shift +=  1; // + 1
        }
        else {

            shift -=  1; // -1
        }

        return shift;
    }


    static int debruijn64[] =
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

    public double doubleValue2() {

        long[] digits = this.digits;

        int i;
        long digit = 0;
        for(i = 0; i < digits.length; i++) {
            digit = digits[i];
            if(digit != 0) {
                break;
            }
        }

        if(i == digits.length) {
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
        bitOffset = (int)(((Double.doubleToRawLongBits(d) >> 52)) - 1023);
        //r |= (r >> 31); //Fix for zero, not needed here

        scale = digits[0] != 0 ? ((long)i) * SHIFT + bitOffset : -(((long)i) * SHIFT) + bitOffset;

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
            mantissa = mantissa & 0xFFFFFFFFFFFFFL;
            if(temp == 0 && i + 1 < digits.length) {
                mantissa += (digits[i + 1] >>> (SHIFTM1)) & 0x1;
            }
        }
        else {
            int shift = -temp;
            mantissa |= val >>> shift;
            mantissa = mantissa & 0xFFFFFFFFFFFFFL;
            mantissa += (val >>> (shift - 1)) & 0x1;
        }

        i++;

        int k;
        for(k = bitOffset ; k < 52 && i < digits.length; k+=SHIFT, i++) {
            val = digits[i];
            temp = 52 - k;

            if(temp > SHIFT) {
                mantissa |= val << (temp - SHIFT);
                mantissa = mantissa & 0xFFFFFFFFFFFFFL;
            }
            else {
                int temp2 = k + SHIFTM52;
                mantissa |= val >>> temp2;
                mantissa = mantissa & 0xFFFFFFFFFFFFFL;

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

    public double doubleValue() {

        if (sign == 0) {
            return 0;
        }

        if(isOne) {
            return sign;
        }

        if(useToDouble2) {
            return doubleValue2();
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

    public BigNum64 add(BigNum64 a) {

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
        for(int i=fracDigits; i>0; i--) {
            s += digits[i]+otherDigits[i];
            long temp = s&MASK;
            isNonZero |= temp;
            resDigits[i] = temp;
            s >>>= SHIFT;
        }
        long temp = digits[0]+otherDigits[0]+s;
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

   public BigNum64 sub(BigNum64 a) {

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
                digits = copy.digits;;
            }
            else {
                BigNum64 copy = getNegative(a);
                otherDigits = copy.digits;;
            }
        }

       long s = 0;
       long isNonZero = 0;
       for(int i=fracDigits; i>0; i--) {
           s += digits[i]+otherDigits[i];
           long temp = s&MASK;
           isNonZero |= temp;
           resDigits[i] = temp;
           s >>>= SHIFT;
       }
       long temp = digits[0]+otherDigits[0]+s;
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

    public BigNum64 square() {


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

        long old_sum = 0;

        long sum = 0;

        int length = fracDigits05;

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
            sum = sum & MASKI;

            sum += bjhi * aklo;
            carry += sum >>> SHIFTI;
            sum = sum & MASKI;

        }

        if(j == k) {
            long bdigit = origDigitsA[j];
            long aklo = bdigit & MASKI;
            long bjhi = bdigit >>> SHIFTI;

            digits[base2] = (int)(aklo);
            digits[base] = (int)(bjhi);

            sum += bjhi * aklo;
            carry += sum >>> SHIFTI;
            sum = sum & MASKI;
        }

        carry = carry << 1;
        sum = sum << 1;
        carry += sum >>> SHIFTI;
        sum = sum & MASKI;


        old_sum = carry + (sum >>> (SHIFTI - 1)); // Roundish

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
                temp_sum = temp_sum & MASKI;
            }

            carry = carry << 1;

            bj = digits[j];
            ak = digits[k];
            temp_sum = (temp_sum << 1) + bj * ak;

            //carry += temp_sum >>> SHIFT; //Maybe its ok
            //temp_sum = temp_sum & MASK;

            sum += temp_sum;
            carry += sum >>> SHIFTI;
            sum = sum & MASKI;

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
                temp_sum = temp_sum & MASKI;
            }

            carry = carry << 1;
            temp_sum = temp_sum << 1;

            //carry += temp_sum >>> SHIFT; //Maybe its ok
            //temp_sum = temp_sum & MASK;

            sum += temp_sum;
            carry += sum >>> SHIFTI;
            sum = sum & MASKI;

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

    public BigNum64 squareKaratsuba() {

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

        long[] partials = new long[newLength];

        long PartialSum = 0;
        long PartialCarry = 0;

        long adigit = (int)origDigitsA[0];
        digits[0] = (int)adigit;

        long p = adigit * adigit;
        long p0 = p;
        partials[0] = p;
        PartialSum += p;

        PartialCarry += PartialSum >>> SHIFTI;
        PartialSum = PartialSum & MASKI;

        for(int i = 1, base = (i << 1) - 1; i <=fracDigits; i++) {

            adigit = origDigitsA[i];
            long aklo = adigit & MASKI;

            long akhi = adigit >>> SHIFTI;

            digits[base] = (int)(akhi);
            p = akhi * akhi;
            partials[base] = p;
            PartialSum += p;
            PartialCarry += PartialSum >>> SHIFTI;
            PartialSum = PartialSum & MASKI;

            base++;
            digits[base] = (int)(aklo);
            p = aklo * aklo;
            partials[base] = p;
            PartialSum += p;

            PartialCarry += PartialSum >>> SHIFTI;
            PartialSum = PartialSum & MASKI;
            base++;


        }

        long old_sum = 0;

        //for(int i = MAX_FRAC_DIGITS; i > 0; i--) {
        //long sum += (int)(old_sum >>> SHIFT30);

        //long sum = (old_sum >>> SHIFT31);

        long sum = p0;

        int length = fracDigits; //newFracDigits >> 1;

        int j;
        int k;
        long carry = 0;
        for (j = 1, k = newFracDigits; j <= length; j++, k--) {
            long bj = digits[j];
            long bk = digits[k];


            long temp = bj + bk;

            sum += temp * temp;
            carry += sum >>> SHIFTI;
            sum = sum & MASKI;

            //System.out.println("a" + j + " * " + "a " + k);
        }

        long bj, bk;

        sum -= PartialSum;
        carry -= ((sum & MASK31) >>> SHIFTI) + PartialCarry;
        sum = sum & MASKI;

        old_sum = carry + (sum >>> (SHIFTI - 1)); // Roundish

        long temp;

        //long shift = 0;
        int m = fracDigits;
        for(int i = newFracDigits; i > 0; i--, m--) {

            sum = old_sum;

            length = (i >> 1) - 1;

            carry = 0;
            for(j = 0, k = i; j <= length; j++, k--) {
                bj = digits[j];
                bk = digits[k];

                temp = bj + bk;

                sum += temp * temp;
                carry += sum >>> SHIFTI;
                sum = sum & MASKI;
                //System.out.println("a" + j + " * " + "a " + k);
            }

            sum += (partials[k] << 1);
            carry += sum >>> SHIFTI;
            sum = sum & MASKI;


            sum -= PartialSum;
            carry -= ((sum & MASK31) >>> SHIFTI) + PartialCarry;
            sum = sum & MASKI;

            long partialI = partials[i];
            PartialSum -= partialI & MASKI;
            PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFTI;
            PartialSum = PartialSum & MASKI;

            long finalVal = sum;

            i--; //Unrolling

            sum = carry;

            length = i >> 1;

            carry = 0;
            for(j = 0, k = i; j <= length; j++, k--) {
                bj = digits[j];
                bk = digits[k];

                temp = bj + bk;

                sum += temp * temp;
                carry += sum >>> SHIFTI;
                sum = sum & MASKI;
                //System.out.println("a" + j + " * " + "a " + k);
            }

            sum -= PartialSum;
            carry -= ((sum & MASK31) >>> SHIFTI) + PartialCarry;
            finalVal |= (sum & MASKI) << SHIFTI;

            partialI = partials[i];
            PartialSum -= partialI & MASKI;
            PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFTI;
            PartialSum = PartialSum & MASKI;


            resDigits[m] = finalVal;
            old_sum = carry;

        }

        sum = old_sum;

        sum += p0;

        result.sign = 1;

        result.digits[0] = sum;

        return  result;
    }

    public BigNum64 mult(BigNum64 b) {
        if(useKaratsuba) {
            return multKaratsuba(b);
        }
        else {
            return multFull(b);
        }
    }

    public BigNum64 multFull(BigNum64 b) {

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

        long old_sum = 0;

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
            sum = sum & MASKI;

            sum += bjhi * aklo;
            carry += sum >>> SHIFTI;
            sum = sum & MASKI;

        }

        old_sum = carry + (sum >>> (SHIFTI - 1)); // Roundish

        long isNonZero = 0;
        for(int i = newFracDigits, m = fracDigits; i > 0; i--, m--) {

            sum = old_sum; //carry from prev
            carry = 0;
            for(int j = 0, k = i; j <= i; j++, k--) {
                long bj = bdigits[j];
                long ak = digits[k];

                sum += bj * ak;
                carry += sum >>> SHIFTI;
                sum = sum & MASKI;

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
                sum = sum & MASKI;

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

        long d0 = sum;
        result.digits[0] = sum;
        result.isOne = d0 == 1 && isNonZero == 0;
        result.sign = sign * b.sign;

        return result;

    }

    public BigNum64 multKaratsuba(BigNum64 b) {

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
        PartialSum = PartialSum & MASKI;

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
            PartialSum = PartialSum & MASKI;

            base++;
            digits[base] = (int)(aklo);
            bdigits[base] = (int)(bjlo);
            p = aklo * bjlo;
            partials[base] = p;
            PartialSum += p;

            PartialCarry += PartialSum >>> SHIFTI;
            PartialSum = PartialSum & MASKI;
            base++;


        }

        long old_sum = 0;

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
            sum = sum & MASKI;

            //System.out.println("partials " + k + " partials " + j);

        }

        long bj, bk, aj, ak;

        sum -= PartialSum;
        carry -= ((sum & MASK31) >>> SHIFTI) + PartialCarry;
        sum = sum & MASKI;

        old_sum = carry + (sum >>> (SHIFTI - 1)); // Roundish


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
                sum = sum & MASKI;
                //System.out.println("b " + k + " + b " + j + " * a " + k + " + a " + j);
                //System.out.println("partials " + k + " partials " + j);

            }

           // System.out.println("partials " + k);
            //System.out.println("NEXT");
            sum += partials[k] << 1;

            carry += sum >>> SHIFTI;
            sum = sum & MASKI;

            sum -= PartialSum;
            carry -= ((sum & MASK31) >>> SHIFTI) + PartialCarry;
            sum = sum & MASKI;

            long partialI = partials[i];
            PartialSum -= partialI & MASKI;
            PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFTI;
            //PartialCarry -= ((PartialSum & MASK31) >>> SHIFTI);
            PartialSum = PartialSum & MASKI;
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
                sum = sum & MASKI;

                //System.out.println("b " + k + " + b " + j + " * a " + k + " + a " + j);
            }

            sum -= PartialSum;
            carry -= ((sum & MASK31) >>> SHIFTI) + PartialCarry;
            finalVal |= (sum & MASKI) << SHIFTI;

            partialI = partials[i];
            PartialSum -= partialI & MASKI;
            PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFTI;
            //PartialCarry -= ((PartialSum & MASK31) >>> SHIFTI);
            PartialSum = PartialSum & MASKI;
            //PartialCarry -= partialI >>> SHIFTI;

            isNonZero |= finalVal;
            resDigits[m] = finalVal;
            old_sum = carry;
        }

        sum = old_sum;

        sum += p0;

        long d0 = sum;
        result.digits[0] = sum;
        result.isOne = d0 == 1 && isNonZero == 0;
        result.sign = sign * b.sign;

        return result;

    }

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
        resDigits[0] = temp;
        res.sign = sign;
        res.isOne = temp == 1 && isNonZero == 0;
        return res;

    }

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
        resDigits[0] = temp;
        res.sign = sign;
        res.isOne = temp == 1 && isNonZero == 0;
        return res;

    }

    public BigNum64 divide2() {

        BigNum64 res = new BigNum64();

        if(sign == 0) {
            return res;
        }

        long [] resDigits = res.digits;

        if(isOne) {
            resDigits[1] = 0x800000000000000L;
            res.sign = sign;
            return res;
        }

        int isNonZero = 0;

        long temp = digits[0];
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
        resDigits[fracDigits] = temp & MASK;
        res.sign = sign;
        res.isOne = temp == 1 && isNonZero == 0;
        return res;

    }

    public BigNum64 divide4() {

        BigNum64 res = new BigNum64();

        if(sign == 0) {
            return res;
        }

        long [] resDigits = res.digits;

        if(isOne) {
            resDigits[1] = 0x400000000000000L;
            res.sign = sign;
            return res;
        }

        int isNonZero = 0;


        long temp = digits[0];
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
        resDigits[fracDigits] = temp & MASK;
        res.sign = sign;
        res.isOne = temp == 1 && isNonZero == 0;
        return res;

    }

    public boolean isPositive() {
        //return  digits[0] > 0;
        return sign == 1;
    }
    public boolean isZero() {
        return sign == 0;
    }
    public boolean isNegative() {
        return sign == -1;
        //return  digits[0] < 0;
    }

    public MantExp getMantExp() {

        if (sign == 0) {
            return new MantExp();
        }

        if(isOne) {
            return new MantExp(sign);
        }

        long[] digits = this.digits;

        if(offset == -1) {
            int i;
            long digit = 0;
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

            scale = digits[0] != 0 ? ((long) i) * SHIFT + bitOffset : -(((long) i) * SHIFT) + bitOffset;
        }

        long mantissa = 0;

        int temp = 52 - bitOffset;
        int i = offset;

        long val = digits[i];
        if(temp >= 0) {
            mantissa |= val << temp;
            mantissa = mantissa & 0xFFFFFFFFFFFFFL;
            if(temp == 0 && i + 1 < digits.length) {
                mantissa += (digits[i + 1] >>> (SHIFTM1)) & 0x1;
            }
        }
        else {
            int shift = -temp;
            mantissa |= val >>> shift;
            mantissa = mantissa & 0xFFFFFFFFFFFFFL;
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
                mantissa = mantissa & 0xFFFFFFFFFFFFFL;
            }
            else {
                long temp2 = k + SHIFTM52;
                mantissa |= val >>> temp2;
                mantissa = mantissa & 0xFFFFFFFFFFFFFL;

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
}
