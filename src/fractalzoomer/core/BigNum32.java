package fractalzoomer.core;

import org.apfloat.Apfloat;
import org.apfloat.internal.LongMemoryDataStorage;

import java.util.Arrays;

import static org.apfloat.internal.LongRadixConstants.BASE_DIGITS;

public class BigNum32 extends BigNum {

    public static long getPrecision() {

        return (long)fracDigits * SHIFT;

    }

    public static String getName() {
        return "Built-in (32)";
    }

    public int[] digits;

    private static int fracDigits;
    private static int fracDigitsm1;
    private static int fracDigitsp1;
    private static int fracDigitsHalf;
    private static boolean useToDouble2;
    private static boolean useKaratsuba;
    private static int initialLength;
    private static boolean evenFracDigits;

    protected static boolean use_threads;
    public static final long MASK = 0xFFFFFFFFL;
    public static final int SHIFT = 32;
    public static final long MASKD0 = 0x7FFFFFFFL;
    public static final long MASK33 = 0x100000000L;
    public static final int SHIFTM1 = SHIFT - 1;
    public static final int SHIFTM52 = SHIFT - 52;
    public static final int MSHIFTP52 = 52 - SHIFT;
    private static final double TWO_TO_SHIFT = Math.pow(2,-SHIFT);

    public static void reinitialize(double digits) {

        double res = digits / SHIFT;
        int temp = (int) (res);

        if (temp == 0) {
            temp = 1;
        } else if (digits % SHIFT != 0) {
            temp++;
        }

        //Lets always have even fracDigits
//        if((temp & 1) == 0) {
//            fracDigits = temp * TaskRender.BIGNUM_PRECISION_FACTOR;
//        }
//        else {
//            fracDigits = (temp + 1) * TaskRender.BIGNUM_PRECISION_FACTOR;
//        }
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
        //useToDouble2 = fracDigits > 120;
        //useToDouble2 = false;
        //useKaratsuba = fracDigits > 60;
        useKaratsuba = false;
        fracDigitsHalf = fracDigits >> 1;
        initialLength = fracDigitsHalf - ((fracDigitsp1) % 2);
        evenFracDigits = (fracDigits & 1) == 0;

        use_threads = false;
    }

    private static BigNum32 getMax() {
        BigNum32 n = new BigNum32();
        n.digits[0] = (int)MASKD0;
        n.sign = 1;
        return n;
    }

    protected BigNum32() {
        digits = new int[fracDigitsp1];
        sign = 0;
        isOne = false;
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    protected BigNum32(BigNum32 other) {
        digits = Arrays.copyOf(other.digits, other.digits.length);
        sign = other.sign;
        isOne = other.isOne;
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    protected BigNum32(int val) {
        digits = new int[fracDigitsp1];
        digits[0] = Math.abs(val);
        isOne = val == 1 || val == -1;
        sign = (int)Math.signum(val);
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    protected BigNum32(double val) {

        digits = new int[fracDigitsp1];

        scale = 0;
        offset = -1;
        bitOffset = -1;

        if(val == 0 ) {
            sign = 0;
            isOne = false;
            return;
        }
        else if(Math.abs(val) == 1) {
            isOne = true;
            sign = (int)val;
            digits[0] = 1;
            return;
        }

        sign = (int)Math.signum(val);
        val = Math.abs(val);
        digits[0] = (int)(val);
        double fractionalPart = val - (int) val;

        if(fractionalPart != 0) {

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

                    if (k > SHIFT) {

                        if (k == 53) {
                            r = MSHIFTP52 + bitOffset;
                            k -= 53 - r;
                        } else {
                            r = k - SHIFT;
                            k -= SHIFT;
                        }

                        digits[i] = (int) ((mantissa >>> r) & MASK);
                    } else {

                        r = SHIFT - k;
                        k -= k;


                        //if(r >= 0) {
                        digits[i] = (int) ((mantissa << r) & MASK);
                        //}
                        //else {
                        //   digits[i] = (int) ((mantissa >>> (-r)) & MASK);
                        // }
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

    protected BigNum32(String val) {
        this(new MyApfloat(val));
    }

    protected BigNum32(Apfloat val) {

        scale = 0;
        this.offset = -1;
        bitOffset = -1;

        Apfloat baseTwo = val.toRadix(2);

        sign = baseTwo.getImpl().signum();

        isOne = false;

        digits = new int[fracDigitsp1];

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
            digits[0] = (int)(MASKD0 & data[index]);
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
            int bit =  (int) ((data[index] >>> shift) & 0x1);

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
        int digit = 0;
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
        long v = digit & MASK;
        double d = v & ~(v >>> 1);
        //int r = (int)(((Double.doubleToRawLongBits(d) >> 52) & 0x7ff) - 1023); //Fix for the 64 bit, not needed here
        bitOffset = (int)((Double.doubleToRawLongBits(d) >> 52) - 1023);
        //r |= (r >> 31); //Fix for zero, not needed here

        //bitOffset = 31 - Integer.numberOfLeadingZeros(digit);

        scale = i == 0 ? bitOffset : -(((long)i) * SHIFT) + bitOffset;

        return scale;

    }

    public static BigNum32 getNegative(BigNum32 other) {

        BigNum32 copy = new BigNum32();
        copy.sign = other.sign;
        copy.isOne = other.isOne;

        int[] otherDigits = other.digits;
        int[] digits = copy.digits;

        long s = (~otherDigits[fracDigits]) & MASK + 1;
        digits[fracDigits] = (int)s;
        for (int i = fracDigitsm1; i >= 0 ; i--) {
            s = (~(otherDigits[i]) & MASK) + (s>>>SHIFT);
            digits[i] = (int)s;
        }

        return copy;
    }

    @Override
    public void negSelf() {

        if(sign == 0) {
            return;
        }

        long s = (~digits[fracDigits]) & MASK + 1;
        digits[fracDigits] = (int)s;
        for (int i = fracDigitsm1; i >= 0 ; i--) {
            s = (~(digits[i]) & MASK) + (s>>>SHIFT);
            digits[i] = (int)s;
        }

    }

    @Override
    public BigNum32 negate() {

        BigNum32 res = new BigNum32(this);

        res.sign *= -1;

        return res;

    }

    @Override
    public BigNum32 abs() {

        BigNum32 res = new BigNum32(this);

        if(sign == -1) {
            res.sign = 1;
        }

        return res;
    }

    @Override
    public BigNum32 abs_mutable() {

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
    public int compare(BigNum otherr) {
        BigNum32 other = (BigNum32) otherr;

        int signA = sign;

        int[] otherDigits = other.digits;

        int signB = other.sign;

        long digit, otherDigit;

        if(signA < signB) {
            return -1;
        }
        else if(signA > signB) {
            return 1;
        }
        else {//if(signA == signB)
            if (signA == 0) {
                return 0;
            }

            if(signA < 0) {
                for (int i = 0; i < digits.length; i++) {
                    digit = digits[i] & MASK;
                    otherDigit = otherDigits[i] & MASK;
                    if (digit < otherDigit) {
                        return 1;
                    } else if (digit > otherDigit) {
                        return -1;
                    }
                }
            }
            else {
                for (int i = 0; i < digits.length; i++) {
                    digit = digits[i] & MASK;
                    otherDigit = otherDigits[i] & MASK;
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
    public int compareBothPositive(BigNum otherr) {

        BigNum32 other = (BigNum32) otherr;

        int signA = sign;
        int signB = other.sign;

        if(signA == 0 && signB == 0) {
            return 0;
        }

        int[] otherDigits = other.digits;
        long digit, otherDigit;

        for (int i = 0; i < digits.length; i++) {
            digit = digits[i] & MASK;
            otherDigit = otherDigits[i] & MASK;
            if (digit < otherDigit) {
                return -1;
            } else if (digit > otherDigit) {
                return 1;
            }
        }

        return 0;

    }

    @Override
    public double doubleValue() {

        int[] digits = this.digits;

        int i;
        long v;

        if(offset == -1) {
            int digit = 0;
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
            v = digit & MASK;
            double d = v & ~(v >>> 1);
            //int r = (int)(((Double.doubleToRawLongBits(d) >> 52) & 0x7ff) - 1023); //Fix for the 64 bit, not needed here
            bitOffset = (int) ((Double.doubleToRawLongBits(d) >> 52) - 1023);
            //r |= (r >> 31); //Fix for zero, not needed here
            //bitOffset = 31 - Integer.numberOfLeadingZeros(digit);

            scale = i == 0 ? bitOffset : -(((long) i) * SHIFT) + bitOffset;
        }
        else {
            i = offset;
            v = digits[i] & MASK;
        }

        if (scale < Double.MIN_EXPONENT) { //accounting for +1
            return 0.0;
        }
        else if (scale >= Double.MAX_EXPONENT) {
            return sign == -1 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }

        long mantissa = 0;

        mantissa |= v << (52 - bitOffset);
        i++;

        int k;
        for(k = bitOffset ; k < 52 && i < digits.length; k+=SHIFT, i++) {
            long val = digits[i] & MASK;
            int temp = 52 - k;

            if(temp > SHIFT) {
                mantissa |= val << (temp - SHIFT);
                mantissa &= 0xFFFFFFFFFFFFFL;
            }
            else {
                int temp2 = k + SHIFTM52;
                mantissa |= val >>> temp2;
                mantissa &= 0xFFFFFFFFFFFFFL;

                if(temp2 == 0 && i + 1 < digits.length) {
                    long val2 = digits[i + 1] & MASK;
                    mantissa += (val2 >>> (SHIFTM1)) & 0x1;
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
            ret += digits[i] & MASK;
        }

        ret *= sign;
        return ret;
    }

    @Override
    public BigNum32 add(BigNum aa) {
        BigNum32 a = (BigNum32)aa;

        BigNum32 result = new BigNum32();

        int[] otherDigits = a.digits;
        int otherSign = a.sign;

        int[] resDigits = result.digits;

        int[] digits = this.digits;
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
                BigNum32 copy = getNegative(this);
                digits = copy.digits;
            }
            else
            {
                BigNum32 copy = getNegative(a);
                otherDigits = copy.digits;
            }
        }

        long s = 0;
        int isNonZero = 0;
        long temp;
        for(int i=fracDigits; i>0; i--) {
            s += (digits[i] & MASK) + (otherDigits[i] & MASK);
            temp = s & MASK;
            isNonZero |= temp;
            resDigits[i] = (int)temp;
            s >>>= SHIFT;
        }
        temp = (digits[0] & MASK) + (otherDigits[0] & MASK) + s;

        int temp2 = (int) temp;
        result.isOne = (temp2 == 1 || temp2 == -1) && isNonZero == 0;
        isNonZero |= temp2;
        resDigits[0] = temp2;

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
    public BigNum32 add(int a) {

        BigNum32 result = new BigNum32();

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

        int[] resDigits = result.digits;

        int[] digits = this.digits;
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
                BigNum32 copy = getNegative(this);
                digits = copy.digits;
            }
        }
        else {
            if(otherSign < 0) {
                otherDigits = ~otherDigits + 1;
            }
        }

        int isNonZero = 0;
        long temp;
        for(int i=fracDigits; i>0; i--) {
            temp = resDigits[i] = digits[i];
            isNonZero |= temp;
        }

        temp = (digits[0] & MASK) + otherDigits;
        result.isOne = (temp == 1 || temp == -1) && isNonZero == 0;
        isNonZero |= temp;
        resDigits[0] = (int)temp;

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
    public BigNum32 sub(BigNum aa) {
        BigNum32 a = (BigNum32)aa;

        BigNum32 result = new BigNum32();

        int[] otherDigits = a.digits;
        int otherSign = a.sign;

        int[] resDigits = result.digits;

        int[] digits = this.digits;
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
                BigNum32 copy = getNegative(this);
                digits = copy.digits;
            }
            else {
                BigNum32 copy = getNegative(a);
                otherDigits = copy.digits;
            }
        }

        long s = 0;
        int isNonZero = 0;
        long temp;
        for(int i=fracDigits; i>0; i--) {
            s += (digits[i] & MASK) + (otherDigits[i] & MASK);
            temp = s & MASK;
            isNonZero |= temp;
            resDigits[i] = (int)temp;
            s >>>= SHIFT;
        }
        temp = (digits[0] & MASK) + (otherDigits[0] & MASK) + s;

        int temp2 = (int) temp;
        result.isOne = (temp2 == 1 || temp2 == -1) && isNonZero == 0;
        isNonZero |= temp2;
        resDigits[0] = temp2;

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
    public BigNum32 sub(int a) {

        BigNum32 result = new BigNum32();

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

        int[] resDigits = result.digits;

        int[] digits = this.digits;
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
                BigNum32 copy = getNegative(this);
                digits = copy.digits;
            }
            otherDigits = ~otherDigits + 1;
        }
        else {
            if(otherSign < 0) {
                otherDigits = ~otherDigits + 1;
            }
        }


        int isNonZero = 0;
        long temp;
        for(int i=fracDigits; i>0; i--) {
            temp = resDigits[i] = digits[i];
            isNonZero |= temp;
        }

        temp = (digits[0] & MASK) + otherDigits;
        result.isOne = (temp == 1 || temp == -1) && isNonZero == 0;
        isNonZero |= temp;
        resDigits[0] = (int)temp;

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
    public BigNum32 square() {
       if(offset <= 15) {
            return squareFull();
       }
       else {
           return squareWithZeroSkip();
       }
    }

    public BigNum32 squareWithZeroSkip() {


        BigNum32 result = new BigNum32();

        if(sign == 0) {
            return result;
        }

        int[] resDigits = result.digits;

        if(isOne) {
            resDigits[0] = 1;
            result.isOne = true;
            result.sign = 1;
            return result;
        }

        if ((offset << 1) > fracDigits) {
            return result; // zero
        }

        long old_sum;

        long sum = 0;

        int length = initialLength;

        int j;
        int k;
        long carry = 0;
        long bj;
        long ak;


        for (j = 1, k = fracDigits; j <= length;) {
            bj = digits[j] & MASK;
            ak = digits[k] & MASK;

            sum += bj * ak;

            j++;k--;

            carry += sum >>> SHIFT;
            sum &= MASK;
        }

        if(j == k) {
            bj = digits[j] & MASK;
            sum <<= 1;
            //carry += sum >>> SHIFT;
            //sum &= MASK;
            carry <<=  1;
            sum += bj * bj;
        }
        else  {
            bj = digits[j] & MASK;
            ak = digits[k] & MASK;
            sum += bj * ak;
            carry += sum >>> SHIFT;
            sum &= MASK;
            sum <<= 1;
            carry <<= 1;
        }

        carry += sum >>> SHIFT;
        sum &= MASK;



        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish

        int newJStart = offset != -1 ? offset : 0; //To avoid multiplication on zeros

        for(int i = fracDigits; i > 0; i--) {

            sum = 0;

            length = (i >> 1) - 1;

            carry = 0;

            for(j = newJStart, k = i - newJStart; j <= length; j++, k--) {
                bj = digits[j] & MASK;
                ak = digits[k] & MASK;
                sum += bj * ak;
                carry += sum >>> SHIFT;
                sum &= MASK;
            }


            if(j == k) {
                bj = digits[j] & MASK;
                sum <<= 1;
                carry <<= 1;
                carry += sum >>> SHIFT;
                sum &= MASK;
                sum += bj * bj;
                carry += sum >>> SHIFT;
                sum &= MASK;
            }
            else  {
                bj = digits[j] & MASK;
                ak = digits[k] & MASK;
                sum += bj * ak;
                carry += sum >>> SHIFT;
                sum &= MASK;
                sum <<= 1;
                carry <<= 1;
                //carry += sum >>> SHIFT;
                //sum &= MASK;
            }

            sum += old_sum;
            carry += sum >>> SHIFT;
            sum &= MASK;


            resDigits[i] = (int) (sum);
            old_sum = carry;

            if(k <= offset && carry == 0) {
                result.sign = 1;
                return  result;
            }
        }

        sum = old_sum;

        bj = digits[0] & MASK;
        sum += bj * bj;

        resDigits[0] = (int) (sum & MASKD0);
        result.sign = 1;

        return  result;
    }
    @Deprecated
    @Override
    public BigNum32 squareFullGolden() {


        BigNum32 result = new BigNum32();

        if(sign == 0) {
            return result;
        }

        int[] resDigits = result.digits;

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
        long bj;
        long ak;

        for (j = 1, k = fracDigits; j <= length; j++, k--) {
            bj = digits[j] & MASK;
            ak = digits[k] & MASK;

            sum += bj * ak;
            carry += sum >>> SHIFT;
            sum &= MASK;
        }


        if(j == k) {
            bj = digits[j] & MASK;
            sum <<= 1;
            carry <<= 1;
            sum += bj * bj;
        }
        else  {
            bj = digits[j] & MASK;
            ak = digits[k] & MASK;
            sum += bj * ak;
            carry += sum >>> SHIFT;
            sum &= MASK;
            sum <<= 1;
            carry <<= 1;
        }

        carry += sum >>> SHIFT;
        sum &= MASK;

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish


        for(int i = fracDigits; i > 0; i--) {

            sum = old_sum;

            length = (i >> 1) - 1;

            long temp_sum = 0;
            carry = 0;
            for(j = 0, k = i; j <= length; j++, k--) {
                bj = digits[j] & MASK;
                ak = digits[k] & MASK;
                temp_sum += bj * ak;
                carry += temp_sum >>> SHIFT;
                temp_sum &= MASK;
            }


            if(j == k) {
                bj = digits[j] & MASK;
                temp_sum <<= 1;
                carry <<= 1;
                carry += temp_sum >>> SHIFT;
                temp_sum &= MASK;
                temp_sum += bj * bj;
                carry += temp_sum >>> SHIFT;
                temp_sum &= MASK;
            }
            else  {
                bj = digits[j] & MASK;
                ak = digits[k] & MASK;
                temp_sum += bj * ak;
                carry += temp_sum >>> SHIFT;
                temp_sum &= MASK;
                temp_sum <<= 1;
                carry <<= 1;
                carry += temp_sum >>> SHIFT;
                temp_sum &= MASK;
            }

            sum += temp_sum;
            carry += sum >>> SHIFT;
            sum &= MASK;


            resDigits[i] = (int) (sum);
            old_sum = carry;
        }

        sum = old_sum;

        bj = digits[0] & MASK;
        sum += bj * bj;

        resDigits[0] = (int) (sum & MASKD0);
        result.sign = 1;

        return  result;
    }


    //Todo check 32 impl for overflows
    @Override
    public BigNum32 squareFull() {


        BigNum32 result = new BigNum32();

        if(sign == 0) {
            return result;
        }

        int[] resDigits = result.digits;

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

        long bj;
        long ak;

        for (j = 1, k = fracDigits; j <= length;) {
            bj = digits[j] & MASK;
            ak = digits[k] & MASK;

            sum += bj * ak;

            j++;k--;

            carry += sum >>> SHIFT;
            sum &= MASK;
        }

        if(j == k) {
            bj = digits[j] & MASK;
            sum <<= 1;
            carry <<= 1;
            //carry += sum >>> SHIFT;
            //sum &= MASK;
            sum += bj * bj;
        }
        else  {
            bj = digits[j] & MASK;
            ak = digits[k] & MASK;
            sum += bj * ak;
            carry += sum >>> SHIFT;
            sum &= MASK;
            sum <<= 1;
            carry <<= 1;
        }

        carry += sum >>> SHIFT;
        sum &= MASK;

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish

       // System.out.println(old_sum);

        length = fracDigitsHalf;

        for(int i = fracDigits; i > 0; i-=2, length--) {

            sum = 0;
            long sum2 = 0;
            carry = 0;
            long carry2 = 0;

            k = i;
            long prevDigit = digits[k] & MASK;

            for(j = 0; j < length;) {
                bj = digits[j] & MASK;
                sum += prevDigit * bj;

                k--;

                prevDigit = digits[k] & MASK; //ak
                sum2 += prevDigit * bj;

                j++;

                carry += sum >>> SHIFT;
                sum &= MASK;
                carry2 += sum2 >>> SHIFT;
                sum2 &= MASK;
            }

            if(evenFracDigits) {
                sum <<= 1;
                carry <<= 1;
                carry += sum >>> SHIFT;
                sum &= MASK;
                sum += prevDigit * prevDigit;

                sum2 <<= 1;
                carry2 <<= 1;
                //carry2 += sum2 >>> SHIFT;
                //sum2 &= MASK;
            }
            else {
                bj = digits[length] & MASK;

                sum += prevDigit * bj;
                carry += sum >>> SHIFT;
                sum &= MASK;
                //carry1 += temp_sum1 >>> SHIFT;
                //temp_sum1 &= MASK;

                sum2 <<= 1;
                carry2 <<= 1;
                sum2 += bj * bj;
                carry2 += sum2 >>> SHIFT;
                sum2 &= MASK;

                sum <<= 1;
                carry <<= 1;
            }


            carry += sum >>> SHIFT;
            sum &= MASK;

            sum += old_sum;
            carry += sum >>> SHIFT;
            sum &= MASK;

            sum2 += carry;


            if(i > 1) {
                carry2 += sum2 >>> SHIFT;
                sum2 &= MASK;
                old_sum = carry2;
            }

            resDigits[i] = (int) (sum);
            resDigits[i - 1] = (int) (sum2);

        }

        if(evenFracDigits) {
            sum = old_sum;

            bj = digits[0] & MASK;
            sum += bj * bj;

            resDigits[0] = (int) (sum & MASKD0);
        }
        result.sign = 1;

        return  result;
    }


    @Override
    public BigNum32 mult(BigNum bb) {
        BigNum32 b = (BigNum32) bb;
        return multFull(b);
    }

    /* Sign is positive */
    @Override
    public BigNum32 mult(int value) {
        BigNum32 result = new BigNum32();

        if(sign == 0 || value == 0) {
            return result;
        }

        int[] resDigits = result.digits;

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
        long sum;
        long carry;

        int isNonZero = 0;
        for(int i = fracDigits; i > 0; i--) {
            sum = (digits[i] & MASK) * value + old_sum;
            carry = sum >>> SHIFT;
            sum &= MASK;

            int di = (int) (sum);
            resDigits[i] = di;
            isNonZero |= di;
            old_sum = carry;
        }

        sum = (digits[0] & MASK) * value + old_sum;

        int d0 = (int) (sum & MASKD0);
        resDigits[0] = d0;

        result.isOne = d0 == 1 && isNonZero == 0;

        result.sign = sign * bsign;

        return result;
    }

    @Deprecated
    public BigNum32 multFullGolden(BigNum32 b) {

        BigNum32 result = new BigNum32();

        if(sign == 0 || b.sign == 0) {
            return result;
        }

        int[] bdigits = b.digits;

        int[] resDigits = result.digits;

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

        //for(int i = MAX_FRAC_DIGITS; i > 0; i--) {

        //long sum = (old_sum >>> SHIFT31);
        //
        long sum = 0;
        long carry = 0;
        long bj;
        long ak;
        for (int j = 1, k = fracDigits; j <= fracDigits; j++, k--) {
            bj = bdigits[j] & MASK;
            ak = digits[k] & MASK;

            sum += bj * ak;
            carry += sum >>> SHIFT;
            sum &= MASK;

        }

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish

        //System.out.println();
        //}

        int isNonZero = 0;
        for(int i = fracDigits; i > 0; i--) {

            sum = old_sum; //carry from prev
            carry = 0;
            for(int j = 0, k = i; j <= i; j++, k--) {
                bj = bdigits[j] & MASK;
                ak = digits[k] & MASK;

                sum += bj * ak;
                carry += sum >>> SHIFT;
                sum &= MASK;

               //System.out.println("b" + j + " * " + "a " + k);
            }

            int di = (int) (sum);
            resDigits[i] = di;
            isNonZero |= di;
            //System.out.println(result.digits[i] );
            old_sum = carry;
            //System.out.println("NEXT");
        }

        sum = old_sum;

        bj = bdigits[0] & MASK;
        ak = digits[0] & MASK;

        sum += bj * ak;

        int d0 = (int) (sum & MASKD0);
        resDigits[0] = d0;

        result.isOne = d0 == 1 && isNonZero == 0;

        result.sign = sign * b.sign;

        return result;

    }

    public BigNum32 multFull(BigNum32 b) {

        BigNum32 result = new BigNum32();

        if(sign == 0 || b.sign == 0) {
            return result;
        }

        int[] bdigits = b.digits;

        int[] resDigits = result.digits;

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

        //for(int i = MAX_FRAC_DIGITS; i > 0; i--) {

        //long sum = (old_sum >>> SHIFT31);
        //
        long sum = 0;
        long carry = 0;
        long bj;
        long ak;

        int j, k;

        for (j = 1, k = fracDigits; j <= fracDigits;) {
            bj = bdigits[j] & MASK;
            ak = digits[k] & MASK;

            sum += bj * ak;

            j++;k--;


            carry += sum >>> SHIFT;
            sum &= MASK;
        }

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish

        //System.out.println();
        //}


        long a0 = digits[0] & MASK;

        int isNonZero = 0;
        for(int i = fracDigits; i > 0; i-=2) {
            sum = 0;
            long sum2 = 0;
            carry = old_sum >>> SHIFT;
            old_sum &= MASK;
            long carry2 = 0;

            k = i;
            long prevDigit = digits[k] & MASK;

            for(j = 0; j < i;) {
                bj = bdigits[j] & MASK;
                sum += prevDigit * bj;

                //System.out.println("b" + j + " * " + "a " + k);

                k--;

                prevDigit = digits[k] & MASK; //ak
                sum2 += prevDigit * bj;
                //System.out.println("b" + j + " * " + "a " + k);

                j++;

                carry += sum >>> SHIFT;
                sum &= MASK;

                carry2 += sum2 >>> SHIFT;
                sum2 &= MASK;
            }

            bj = bdigits[j] & MASK;

            sum += prevDigit * bj + old_sum;
            //System.out.println("b" + j + " * " + "a " + k);
            //carry1 += temp_sum1 >>> SHIFT;
            //temp_sum1 &= MASK;

            carry += sum >>> SHIFT;
            sum &= MASK;

            //System.out.println("NEXT");

            int di = (int) (sum);
            isNonZero |= di;
            resDigits[i] = di;


            if(i > 1) {
                int im1 = i - 1;
                sum2 += carry;

                carry2 += sum2 >>> SHIFT;
                sum2 &= MASK;
                old_sum = carry2;

                di = (int) (sum2);
                isNonZero |= di;
                resDigits[im1] = di;
            }
            else {
                sum2 += carry;
                int d0 = (int) (sum2 & MASKD0);
                resDigits[0] = d0;
                result.isOne = d0 == 1 && isNonZero == 0;
            }

        }

        if(evenFracDigits) {
            bj = bdigits[0] & MASK;

            sum = bj * a0 + old_sum;

            int d0 = (int) (sum & MASKD0);
            resDigits[0] = d0;

            result.isOne = d0 == 1 && isNonZero == 0;
        }

        result.sign = sign * b.sign;

        return result;

    }

    @Deprecated
    public BigNum32 multKaratsubaGolden(BigNum32 b) {

        BigNum32 result = new BigNum32();

        if(sign == 0 || b.sign == 0) {
            return result;
        }

        int[] bdigits = b.digits;
        int[] resDigits = result.digits;

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

        long[] partials = new long[fracDigitsp1];
        long[] partialSums = new long[fracDigitsp1];
        long[] partialCarries = new long[fracDigitsp1];

        long PartialSum = 0;
        long PartialCarry = 0;
        for (int i = 0; i < fracDigitsp1; i++) {
            long p = (digits[i] & MASK) * (bdigits[i] & MASK);
            partials[i] = p;
            PartialSum += p;

            PartialCarry += PartialSum >>> SHIFT;
            PartialSum &= MASK;

            partialSums[i] = PartialSum;
            partialCarries[i] = PartialCarry;
        }


        long old_sum;

        long p0 = partials[0];

        long sum = p0;

        int length = initialLength;

        int j;
        int k;
        long carry = 0;
        long bj, bk, aj, ak;
        for (j = 1, k = fracDigits; j <= length; j++, k--) {
            bj = bdigits[j] & MASK;
            bk = bdigits[k] & MASK;
            aj = digits[j] & MASK;
            ak = digits[k] & MASK;

            sum += (bk + bj) * (ak + aj);
            carry += sum >>> SHIFT;
            sum &= MASK;

            //System.out.println("partials " + k + " partials " + j);

        }

        //System.out.println("partials " + k + " partials " + j);

        //System.out.println("Next");

        //int diff = k - j;
        //int branchlessCheck = (diff >> 31) - (-diff >> 31);


        //System.out.println("partials " + k + " partials " + j);

        //System.out.println("NEXT");

        //System.out.println("OUT");
        //System.out.println("b" + j + " * " + "a " + k);

        if(j == k) {
            sum += (partials[k] << 1);
        }
        else  {
            bj = bdigits[j] & MASK;
            bk = bdigits[k] & MASK;
            aj = digits[j] & MASK;
            ak = digits[k] & MASK;
            sum += (bk + bj) * (ak + aj);
        }

        carry += sum >>> SHIFT;
        sum &= MASK;

        sum -= PartialSum;
        carry -= ((sum & MASK33) >>> SHIFT) + PartialCarry;
        sum &= MASK;

        //sum += (1 - branchlessCheck) * partials[k] + branchlessCheck * ((bk + bj) * (ak + aj) - partials[k] - partials[j]);

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish, adds bias because 10000 is always shifted upwards

        int isNonZero = 0;
        for(int i = fracDigits; i > 0; i--) {

            length = (i >> 1) - 1;

            sum = old_sum; //carry from prev

            carry = 0;
            for(j = 0, k = i; j <= length; j++, k--) {
                bj = bdigits[j] & MASK;
                bk = bdigits[k] & MASK;
                aj =  digits[j] & MASK;
                ak = digits[k] & MASK;

                sum += (bk + bj) * (ak + aj); //Overflows

                carry += sum >>> SHIFT;
                sum &= MASK;
                //System.out.println("partials " + k + " partials " + j);
                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

            }

            if(j == k) {
                sum += (partials[k] << 1);
            }
            else  {
                bj = bdigits[j] & MASK;
                bk = bdigits[k] & MASK;
                aj = digits[j] & MASK;
                ak = digits[k] & MASK;
                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);
                sum += (bk + bj) * (ak + aj);
            }

            //System.out.println("NEXT");

            carry += sum >>> SHIFT;
            sum &= MASK;

            sum -= PartialSum;
            carry -= ((sum & MASK33) >>> SHIFT) + PartialCarry;
            sum &= MASK;

            if(i > 1) {
                int im1 = i - 1;
                PartialSum = partialSums[im1];
                PartialCarry = partialCarries[im1];

                //long partialI = partials[i];

                //PartialSum -= partialI & MASK;
                //PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFT;
                //PartialSum &= MASK;
            }


            //System.out.println("partials " + k + " partials " + j);
            //System.out.println("NEXT");


            int di = (int) (sum);
            isNonZero |= di;
            resDigits[i] = di;
            //System.out.println(result.digits[i] );
            old_sum = carry;
           // System.out.println(old_sum);
            //System.out.println("NEXT");
        }

        sum = old_sum;

        sum += p0;

        int d0 = (int) (sum & MASKD0);
        resDigits[0] = d0;

        result.sign = sign * b.sign;

        result.isOne = d0 == 1 && isNonZero == 0;

        return result;

    }

    /*mult2, mult4, divide2, divide4, multFull, multKaratsuba, square and squareKaratsuba
     might overflow or underflow leading to zero but the sign will not be set to 0
     */

    @Override
    public BigNum32 mult2() {

        BigNum32 res = new BigNum32();

        if(sign == 0) {
            return res;
        }

        int [] resDigits = res.digits;

        if(isOne) {
            resDigits[0] = 2;
            res.sign = sign;
            return res;
        }

        int isNonZero = 0;

        long temp = (digits[fracDigits] & MASK) << 1;
        long val = temp & MASK;
        resDigits[fracDigits] = (int) val;
        isNonZero |= val;

        for(int i = fracDigitsm1; i > 0; i--) {
            temp = ((digits[i] & MASK) << 1) | (temp >>> SHIFT);
            val = temp & MASK;
            resDigits[i] = (int)val;
            isNonZero |= val;
        }

        temp = ((digits[0] & MASK) << 1) | (temp >>> SHIFT);
        int d0 = (int)(temp & MASKD0);
        resDigits[0] = d0;
        res.sign = sign;
        res.isOne = d0 == 1 && isNonZero == 0;
        return res;

    }

    @Override
    public BigNum32 divide2() {

        BigNum32 res = new BigNum32();

        if(sign == 0) {
            return res;
        }

        int [] resDigits = res.digits;

        if(isOne) {
            resDigits[1] = 0x80000000;
            res.sign = sign;
            return res;
        }

        int isNonZero = 0;

        long d0 = digits[0] & MASK;
        long temp = d0;
        long val = temp >>> 1;

        long bit = temp & 0x1;

        resDigits[0] = (int)val;
        isNonZero |= val;

        for(int i=1; i < fracDigits; i++) {
            temp = digits[i] & MASK;
            temp |= (bit << SHIFT);
            bit = temp & 0x1;
            temp >>>= 1;
            val = temp & MASK;
            resDigits[i] = (int)val;
            isNonZero |= val;
        }

        temp = digits[fracDigits] & MASK;
        temp |= (bit << SHIFT);
        temp >>>= 1;
        val = temp & MASK;
        resDigits[fracDigits] = (int)val;
        isNonZero |= val;

        res.sign = sign;
        res.isOne = d0 == 1 && isNonZero == 0;
        return res;

    }

    @Override
    public BigNum32 divide4() {

        BigNum32 res = new BigNum32();

        if(sign == 0) {
            return res;
        }

        int [] resDigits = res.digits;

        if(isOne) {
            resDigits[1] = 0x40000000;
            res.sign = sign;
            return res;
        }

        int isNonZero = 0;

        long d0 = digits[0] & MASK;
        long temp = d0;
        long val = temp >>> 2;

        long bits = temp & 0x3;

        resDigits[0] = (int)val;
        isNonZero |= val;

        for(int i=1; i < fracDigits; i++) {
            temp = digits[i] & MASK;
            temp |= (bits << SHIFT);
            bits = temp & 0x3;
            temp >>>= 2;
            val = temp & MASK;
            resDigits[i] = (int)val;
            isNonZero |= val;
        }

        temp = digits[fracDigits] & MASK;
        temp |= (bits << SHIFT);
        temp >>>= 2;
        val = temp & MASK;
        resDigits[fracDigits] = (int)val;
        isNonZero |= val;
        res.sign = sign;
        res.isOne = d0 == 1 && isNonZero == 0;
        return res;

    }

    @Override
    public BigNum32 mult4() {

        BigNum32 res = new BigNum32();

        if(sign == 0) {
            return res;
        }

        int [] resDigits = res.digits;

        if(isOne) {
            resDigits[0] = 4;
            res.sign = sign;
            return res;
        }

        int isNonZero = 0;

        long temp = (digits[fracDigits] & MASK) << 2;
        long val = temp & MASK;
        resDigits[fracDigits] = (int)val;
        isNonZero |= val;

        for(int i=fracDigitsm1; i > 0; i--) {
            temp = ((digits[i] & MASK) << 2) | (temp >>> SHIFT);
            val = temp & MASK;
            resDigits[i] = (int)val;
            isNonZero |= val;
        }

        temp = ((digits[0] & MASK) << 2) | (temp >>> SHIFT);
        int d0 = (int)(temp & MASKD0);
        resDigits[0] = d0;
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

        int[] digits = this.digits;

        int i;
        int digit = 0;
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
            long v = digit & MASK;
            double d = v & ~(v >>> 1);
            //int r = (int)(((Double.doubleToRawLongBits(d) >> 52) & 0x7ff) - 1023); //Fix for the 64 bit, not needed here
            bitOffset = (int) ((Double.doubleToRawLongBits(d) >> 52) - 1023);
            //r |= (r >> 31); //Fix for zero, not needed here

            //bitOffset = 31 - Integer.numberOfLeadingZeros(digit);

            scale = i == 0 ? bitOffset : -(((long) i) * SHIFT) + bitOffset;
        }
        else {
            i = offset;
            digit = digits[i];
        }

        long mantissa = 0;

        int temp = 52 - bitOffset;
        mantissa |= (digit & MASK) << temp; //Will always be positive
        mantissa &= 0xFFFFFFFFFFFFFL;

        //System.out.println(digits[i]);

        i++;

        int k;
        for(k = bitOffset ; k < 52 && i < digits.length; k+=SHIFT, i++) {
            long val = digits[i] & MASK;
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
                    long val2 = digits[i + 1] & MASK;
                    mantissa += (val2 >>> (SHIFTM1)) & 0x1; //Rounding
                }
                else {
                    mantissa += (val >>> (temp2 - 1)) & 0x1; //Rounding
                }
            }
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

    public static BigNum32 max(BigNum32 a, BigNum32 b) {
        return a.compare(b) > 0 ? a : b;
    }

    public static BigNum32 min(BigNum32 a, BigNum32 b) {
        return a.compare(b) < 0 ? a : b;
    }

    public BigNum32 mult2to32(int times) {

        BigNum32 result = new BigNum32(this);

        if(times <= 0) {
            return result;
        }

        int[] digits = result.digits;
        int total = digits.length + times;
        int isNonZero = 0;

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
    public BigNum32 div2toi(long power) {
        BigNum32 result = new BigNum32(this);

        if(power <= 0) {
            return result;
        }

        int times = (int)(power / SHIFT);

        int internalShift = (int)(power % SHIFT);

        int[] digits = result.digits;
        int total = -times;
        int isNonZero = 0;
        int val;

        for(int i = digits.length - 1 - times, location = digits.length - 1; i >= total; i--, location--) {
            if(i == 0) {
                digits[location] = val = digits[i] >>> internalShift;
            }
            else if (i > 0) {
                digits[location] = val = ((int)(((digits[i - 1] & MASK) << (SHIFT - internalShift)))) | (digits[i] >>> internalShift);
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
    public BigNum32 mult2toi(long power) {

        BigNum32 result = new BigNum32(this);

        if(power <= 0) {
            return result;
        }

        int times = (int)(power / SHIFT);

        int internalShift = (int)(power % SHIFT);

        int[] digits = result.digits;
        int total = digits.length + times;
        int isNonZero = 0;
        int val;

        for(int i = times, location = 0; i < total; i++, location++) {
            if(i < digits.length - 1) {
                digits[location] = val = (digits[i] << internalShift) | ((int)((digits[i + 1] & MASK) >>> (SHIFT - internalShift)));
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

    public BigNum32 divide2to32(int times) {

        BigNum32 result = new BigNum32(this);

        if(times <= 0) {
            return result;
        }

        int[] digits = result.digits;
        int total = -times;
        int isNonZero = 0;
        
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

    public BigNum32 shift2to32(int times) {
        if(times > 0) {
            return mult2to32(times);
        }
        else if(times < 0) {
            return divide2to32(-times);
        }

        return new BigNum32(this);
    }

    @Override
    public BigNum32 shift2toi(long power) {
        if(power > 0) {
            return mult2toi(power);
        }
        else if(power < 0) {
            return div2toi(-power);
        }

        return new BigNum32(this);
    }

    @Override
    public BigNum32 sqrt() {
        if(sign == 0 || isOne) {
            return new BigNum32(this);
        }
        if(sign < 0) {
            return new BigNum32();
        }
        BigNum32 one = new BigNum32(1);
        BigNum32 oneFourth = new BigNum32(0.25);

        BigNum32 a = new BigNum32(this);
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

            int[] digits = a.digits;
            for(i = 2; i < digits.length; i++) {
                if(digits[i] != 0) {
                    break;
                }
            }

            int numberOfLimbs = i - 2;

            if(numberOfLimbs > 0) {
                multiplications += ((long)numberOfLimbs * SHIFT) >>> 1;
                a = a.mult2to32(numberOfLimbs);
            }

            do {
                a = a.mult4();
                multiplications++;
            } while (a.compareBothPositive(oneFourth) < 0);
        }

        BigNum32 oneHalf = new BigNum32(1.5);
        BigNum32 aHalf = a.divide2(); // a / 2

        BigNum32 x = new BigNum32(1 / Math.sqrt(a.doubleValue())); // set the initial value to an approximation of 1 /sqrt(a)

        //Newton steps
        BigNum32 newX = x.mult(oneHalf.sub(aHalf.mult(x.squareFull()))); // x = (3/2 - (a/2)*x^2)*x

        BigNum32 epsilon = new BigNum32(1);
        epsilon.digits[epsilon.digits.length - 1] = 0x3;
        epsilon.digits[0] = 0;

        int iter = 0;
        while (newX.sub(x).abs_mutable().compareBothPositive(epsilon) >= 0 && iter < SQRT_MAX_ITERATIONS) {
            x = newX;
            newX = x.mult(oneHalf.sub(aHalf.mult(x.squareFull())));
            iter++;
        }

        BigNum32 sqrta = newX.mult(a); //sqrt(a) = a * (1 /sqrt(a));

        if(multiplications > 0) { //scale it up again
           sqrta = sqrta.div2toi(multiplications);
        }
        else if(divisions > 0) {
            sqrta = sqrta.mult2toi(divisions);
        }

        return sqrta;
    }

    public static void main(String[] args) {
        MyApfloat a = new MyApfloat("-2");
        BigNum32.reinitializeTest(2000);
        BigNum30.reinitializeTest(2000);
        BigNum64.reinitializeTest(2000);
        BigNum60.reinitializeTest(2000);
        BigNum32 b = new BigNum32(0.999949292246963);


        System.out.println(b.mult2toi(1));
    }
}
