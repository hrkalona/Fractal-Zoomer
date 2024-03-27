package fractalzoomer.core;

import org.apfloat.Apfloat;
import org.apfloat.internal.LongMemoryDataStorage;

import java.util.Arrays;

import static fractalzoomer.core.MyApfloat.TWO;
import static org.apfloat.internal.LongRadixConstants.BASE_DIGITS;

public class BigNum30 extends BigNum {

    public static long getPrecision() {
        return (long)fracDigits * SHIFT;
    }
    public static String getName() {
        return "Built-in (30)";
    }

    public int[] digits;

    protected static int fracDigits;
    private static int fracDigitsm1;
    private static int fracDigitsp1;
    private static int fracDigitsHalf;
    private static boolean useToDouble2;
    private static boolean useKaratsuba;
    private static int initialLength;
    private static boolean evenFracDigits;

    protected static boolean use_threads;

    public static final long MASK = 0x3FFFFFFFL;
    public static final int MASKINT = 0x3FFFFFFF;
    public static final int SHIFT = 30;
    public static final long MASKD0 = 0x7FFFFFFFL;
    public static final long MASK31 = 0x40000000L;
    public static final int SHIFTM1 = SHIFT - 1;
    public static final int SHIFTM52 = SHIFT - 52;
    public static final int MSHIFTP52 = 52 - SHIFT;
    private static final double TWO_TO_SHIFT = Math.pow(2,-SHIFT);
    public static int THREADS_THRESHOLD = 275;

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

        //Lets always have even fracDigits
//        if((temp & 1) == 0) {
//            fracDigits = temp * TaskDraw.BIGNUM_PRECISION_FACTOR;
//        }
//        else {
//            fracDigits = (temp + 1) * TaskDraw.BIGNUM_PRECISION_FACTOR;
//        }
        //Lets always have odd fracDigits
        if((temp & 1) == 1) {
            fracDigits = temp * TaskDraw.BIGNUM_PRECISION_FACTOR;
        }
        else {
            fracDigits = (temp + 1) * TaskDraw.BIGNUM_PRECISION_FACTOR;
        }

        initializeInternal();
    }

    public static void reinitializeTest(double digits) {
        fracDigits = ((int)(digits / SHIFT) + 1) * TaskDraw.BIGNUM_PRECISION_FACTOR;
        initializeInternal();
    }

    private static void initializeInternal() {
        fracDigitsm1 = fracDigits - 1;
        fracDigitsp1 = fracDigits + 1;
        //useToDouble2 = fracDigits > 160;
        useKaratsuba = fracDigits > 40;
        fracDigitsHalf = fracDigits >> 1;
        initialLength = fracDigitsHalf - ((fracDigitsp1) % 2);
        evenFracDigits = (fracDigits & 1) == 0;

        use_threads = TaskDraw.USE_THREADS_IN_BIGNUM_LIBS && fracDigits >= THREADS_THRESHOLD && Runtime.getRuntime().availableProcessors() >= 2;
        //use_threads2 = TaskDraw.USE_THREADS_IN_BIGNUM_LIBS && fracDigits >= THREADS_THRESHOLD && Runtime.getRuntime().availableProcessors() >= 3;
    }

    private static BigNum30 getMax() {
        BigNum30 n = new BigNum30();
        n.digits[0] = (int)MASKD0;
        n.sign = 1;
        return n;
    }

    protected BigNum30() {
        digits = new int[fracDigitsp1];
        sign = 0;
        isOne = false;
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    protected BigNum30(BigNum30 other) {
        digits = Arrays.copyOf(other.digits, other.digits.length);
        sign = other.sign;
        isOne = other.isOne;
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    protected BigNum30(int val) {
        digits = new int[fracDigitsp1];
        digits[0] = Math.abs(val);
        isOne = val == 1 || val == -1;
        sign = (int)Math.signum(val);
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    protected BigNum30(double val) {

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

    //Has similarities with the double constructor
    private BigNum30(MantExp inputVal) {

        digits = new int[fracDigitsp1];

        scale = 0;
        offset = -1;
        bitOffset = -1;

        inputVal.Normalize();
        double doubleMantissa = inputVal.getMantissa();
        long inputExp = inputVal.getExp();

        if(doubleMantissa == 0) {
            sign = 0;
            isOne = false;
            return;
        }
        else if(Math.abs(doubleMantissa) == 1 && inputExp == 0) {
            isOne = true;
            sign = (int)doubleMantissa;
            digits[0] = 1;
            return;
        }

        sign = (int)Math.signum(doubleMantissa);
        double val = Math.abs(doubleMantissa);


        long bits = Double.doubleToRawLongBits(val);
        long f_exp = ((bits & 0x7FF0000000000000L) >>> 52) - Double.MAX_EXPONENT;
        long mantissa = (bits & 0xFFFFFFFFFFFFFL) | (0x10000000000000L);


        long totalExp = f_exp + inputExp;

        int index;
        int bitOffset;
        if(totalExp < 0) {
            long posExp = Math.abs(totalExp);
            index = (int) (posExp / SHIFT) + 1;
            bitOffset = (int) (posExp % SHIFT);
        }
        else {
            index = (int) (-totalExp / SHIFT);
            bitOffset = (int) (totalExp % SHIFT);
        }


        int k;
        int i = index;
        for (k = 53; k > 0 && i < digits.length; i++) {
            int r;

            if (k > SHIFT) {

                if(i == -1) {
                    i = 0;
                    r = MSHIFTP52;
                    k -= 53 - r;
                    digits[i] = (int) ((mantissa >>> r) & MASKD0);
                }
                else if(i == 0) {
                    r = 52 - bitOffset;
                    k -= 53 - r;
                    digits[i] = (int) ((mantissa >>> r) & MASKD0);
                }
                else if (i > 0) {
                    if (k == 53) {
                        r = MSHIFTP52 + bitOffset;
                        k -= 53 - r;
                    } else {
                        r = k - SHIFT;
                        k -= SHIFT;
                    }

                    digits[i] = (int) ((mantissa >>> r) & MASK);
                }
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

        boolean isZero = true;
        for(i = 0; i < digits.length; i++) {
            if(digits[i] != 0) {
                isZero = false;
                break;
            }
        }

        if(isZero) {
            sign = 0;
        }
    }

    protected BigNum30(String val) {
        this(new MyApfloat(val));
    }

    protected BigNum30(Apfloat val) {

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
        long v = digit;
        double d = v & ~(v >>> 1);
        //int r = (int)(((Double.doubleToRawLongBits(d) >> 52) & 0x7ff) - 1023); //Fix for the 64 bit, not needed here
        bitOffset = (int)((Double.doubleToRawLongBits(d) >> 52) - 1023);
        //r |= (r >> 31); //Fix for zero, not needed here

        //bitOffset = 31 - Integer.numberOfLeadingZeros(digit);

        scale = i == 0 ? bitOffset : -(((long)i) * SHIFT) + bitOffset;

        return scale;

    }

    public static BigNum30 getNegative(BigNum30 other) {

        BigNum30 copy = new BigNum30();
        copy.sign = other.sign;
        copy.isOne = other.isOne;

        int[] otherDigits = other.digits;
        int[] digits = copy.digits;

        int s = (~otherDigits[fracDigits] & MASKINT) + 1;
        digits[fracDigits] = s & MASKINT;
        for (int i = fracDigitsm1; i > 0 ; i--) {
            s = (~otherDigits[i] & MASKINT) + (s>>>SHIFT);
            digits[i] = s & MASKINT;
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

        int s = (~digits[fracDigits] & MASKINT) + 1;
        digits[fracDigits] = s & MASKINT;
        for (int i = fracDigitsm1; i > 0 ; i--) {
            s = (~digits[i] & MASKINT) + (s>>>SHIFT);
            digits[i] = s & MASKINT;
        }

        s = (~digits[0]) + (s>>>SHIFT);
        digits[0] = s;

    }

    @Override
    public BigNum30 negate() {

        BigNum30 res = new BigNum30(this);

        res.sign *= -1;

        return res;

    }

    @Override
    public BigNum30 abs() {

        BigNum30 res = new BigNum30(this);

        if(sign == -1) {
            res.sign = 1;
        }

        return res;
    }

    @Override
    public BigNum30 abs_mutable() {

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
        BigNum30 other = (BigNum30) otherr;

        int signA = sign;

        int[] otherDigits = other.digits;

        int signB = other.sign;

        int digit, otherDigit;

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
    public int compareBothPositive(BigNum otherr) {
        BigNum30 other = (BigNum30) otherr;

        int signA = sign;
        int signB = other.sign;

        if(signA == 0 && signB == 0) {
            return 0;
        }

        int[] otherDigits = other.digits;

        int digit, otherDigit;

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

    @Override
    public double doubleValue() {

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
            //bitOffset = 31 - Integer.numberOfLeadingZeros(digit);

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
        long v = digit;

        mantissa |= v << (52 - bitOffset);
        i++;

        int k;
        for(k = bitOffset ; k < 52 && i < digits.length; k+=SHIFT, i++) {
            long val = digits[i];
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
                    long val2 = digits[i + 1];
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
            ret += digits[i];
        }

        ret *= sign;
        return ret;
    }

    @Override
    public BigNum30 add(BigNum aa) {
        BigNum30 a = (BigNum30) aa;

        BigNum30 result = new BigNum30();

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
                BigNum30 copy = getNegative(this);
                digits = copy.digits;
            }
            else
            {
                BigNum30 copy = getNegative(a);
                otherDigits = copy.digits;
            }
        }

        int s = 0;
        int isNonZero = 0;
        int temp;
        for(int i=fracDigits; i>0; i--) {
            s += digits[i] + otherDigits[i];
            temp = s & MASKINT;
            isNonZero |= temp;
            resDigits[i] = temp;
            s >>>= SHIFT;
        }
        temp = digits[0] + otherDigits[0] + s;
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
    public BigNum30 add(int a) {

        BigNum30 result = new BigNum30();

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
                BigNum30 copy = getNegative(this);
                digits = copy.digits;
            }
        }
        else {
            if(otherSign < 0) {
                otherDigits = ~otherDigits + 1;
            }
        }

        int isNonZero = 0;
        int temp;
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

    /* When sign was not an extra field
    public BigNum30 sub(BigNum30 a) {
        BigNum30 result = new BigNum30();

        int[] otherDigits = a.digits;
        int[] resDigits = result.digits;
        long s = 0;
        for(int i=fracDigits; i>0; i--) {
            s += ((long)digits[i])-((long)otherDigits[i]); resDigits[i] = (int)(s&MASK); s >>= SHIFT;
        }
        resDigits[0] = (int)(digits[0]-otherDigits[0]+s);
        return result;
    }

    public BigNum30 add(BigNum30 a) {
        BigNum30 result = new BigNum30();

        int[] otherDigits = a.digits;
        int[] resDigits = result.digits;
        long s = 0;
        for(int i=fracDigits; i>0; i--) {
            s += ((long)digits[i])+((long)otherDigits[i]); resDigits[i] = (int)(s&MASK); s >>>= SHIFT;
        }
        resDigits[0] = (int)(digits[0]+otherDigits[0]+s);
        return result;
    }*/

    @Override
    public BigNum30 sub(BigNum aa) {
        BigNum30 a = (BigNum30) aa;

        BigNum30 result = new BigNum30();

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
                BigNum30 copy = getNegative(this);
                digits = copy.digits;
            }
            else {
                BigNum30 copy = getNegative(a);
                otherDigits = copy.digits;
            }
        }

        int s = 0;
        int isNonZero = 0;
        int temp;
        for(int i=fracDigits; i>0; i--) {
            s += digits[i] + otherDigits[i];
            temp = s & MASKINT;
            isNonZero |= temp;
            resDigits[i] = temp;
            s >>>= SHIFT;
        }
        temp = digits[0] + otherDigits[0] + s;
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
    public BigNum30 sub(int a) {

        BigNum30 result = new BigNum30();

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
                BigNum30 copy = getNegative(this);
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
        int temp;
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
    public BigNum30 square() {
       if(offset <= 15) {
            return squareFull();
       }
       else {
           return squareWithZeroSkip();
       }
    }

    public BigNum30 squareWithZeroSkip() {


        BigNum30 result = new BigNum30();

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

        int newLength = (length >>> 1) << 1;
        boolean isOdd = (length & 1) == 1;

        for (j = 1, k = fracDigits; j <= newLength;) {
            bj = digits[j];
            ak = digits[k];

            sum += bj * ak;

            j++;k--;

            bj = digits[j];
            ak = digits[k];

            sum += bj * ak;

            j++;k--;

            carry += sum >>> SHIFT;
            sum &= MASK;
        }

        if(isOdd) {
            bj = digits[j];
            ak = digits[k];
            sum += bj * ak;
            carry += sum >>> SHIFT;
            sum &= MASK;
            j++;k--;
        }

        carry <<=  1;

        if(j == k) {
            bj = digits[j];
            sum <<= 1;
            sum += bj * bj;
        }
        else  {
            bj = digits[j];
            ak = digits[k];
            sum += bj * ak;
            sum <<= 1;
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
                bj = digits[j];
                ak = digits[k];
                sum += bj * ak;
                carry += sum >>> SHIFT;
                sum &= MASK;
            }

            carry <<= 1;

            if(j == k) {
                bj = digits[j];
                sum <<= 1;
                sum += bj * bj;
            }
            else  {
                bj = digits[j];
                ak = digits[k];
                sum += bj * ak;
                sum <<= 1;
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

        bj = digits[0];
        sum += bj * bj;

        resDigits[0] = (int) (sum & MASKD0);
        result.sign = 1;

        return  result;
    }
    @Deprecated
    @Override
    public BigNum30 squareFullGolden() {


        BigNum30 result = new BigNum30();

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
            bj = digits[j];
            ak = digits[k];

            sum += bj * ak;
            carry += sum >>> SHIFT;
            sum &= MASK;
        }

        carry <<= 1;

        if(j == k) {
            bj = digits[j];
            sum <<= 1;
            sum += bj * bj;
        }
        else  {
            bj = digits[j];
            ak = digits[k];
            sum += bj * ak;
            sum <<= 1;
        }

        carry += sum >>> SHIFT;
        sum &= MASK;

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish


        for(int i = fracDigits; i > 0; i--) {

            sum = 0;

            length = (i >> 1) - 1;

            carry = 0;
            for(j = 0, k = i; j <= length; j++, k--) {
                bj = digits[j];
                ak = digits[k];
                sum += bj * ak;
                carry += sum >>> SHIFT;
                sum &= MASK;
            }



            if(j == k) {
                bj = digits[j];
                sum <<= 1;
                carry <<= 1;
                sum += bj * bj;
                carry += sum >>> SHIFT;
                sum &= MASK;
            }
            else  {
                bj = digits[j];
                ak = digits[k];
                sum += bj * ak;
                carry += sum >>> SHIFT;
                sum &= MASK;
                sum <<= 1;
                carry <<= 1;
            }

            sum += old_sum;
            carry += sum >>> SHIFT;
            sum &= MASK;


            resDigits[i] = (int) (sum);
            old_sum = carry;
        }

        sum = old_sum;

        bj = digits[0];
        sum += bj * bj;

        resDigits[0] = (int) (sum & MASKD0);
        result.sign = 1;

        return  result;
    }


    @Override
    public BigNum30 squareFull() {


        BigNum30 result = new BigNum30();

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

        int newLength = (length >>> 1) << 1;
        boolean isOdd = (length & 1) == 1;

        for (j = 1, k = fracDigits; j <= newLength;) {
            bj = digits[j];
            ak = digits[k];

            sum += bj * ak;

            j++;k--;

            bj = digits[j];
            ak = digits[k];

            sum += bj * ak;

            j++;k--;

            carry += sum >>> SHIFT;
            sum &= MASK;
        }

        if(isOdd) {
            bj = digits[j];
            ak = digits[k];
            sum += bj * ak;
            //carry += sum >>> SHIFT;
            //sum &= MASK;
            j++;k--;
        }

        carry <<= 1;

        if(j == k) {
            bj = digits[j];
            sum <<= 1;
            sum += bj * bj;
        }
        else  {
            bj = digits[j];
            ak = digits[k];
            sum += bj * ak;
            sum <<= 1;
        }

        carry += sum >>> SHIFT;
        sum &= MASK;

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish

        length = fracDigitsHalf;

        for(int i = fracDigits; i > 0; i-=2, length--) {

            sum = 0;
            long sum2 = 0;
            carry = 0;
            long carry2 = 0;

            k = i;
            long prevDigit = digits[k];

            newLength = (length >>> 1) << 1;
            isOdd = (length & 1) == 1;

            for(j = 0; j < newLength;) {
                //1st
                bj = digits[j];
                sum += prevDigit * bj;

                k--;

                prevDigit = digits[k]; //ak
                sum2 += prevDigit * bj;

                j++;

                //2nd
                bj = digits[j];
                sum += prevDigit * bj;

                k--;

                prevDigit = digits[k]; //ak
                sum2 += prevDigit * bj;

                carry += sum >>> SHIFT;
                sum &= MASK;
                carry2 += sum2 >>> SHIFT;
                sum2 &= MASK;
                j++;
            }

            if(isOdd) {
                bj = digits[j];
                sum += prevDigit * bj;

                k--;

                prevDigit = digits[k]; //ak

                sum2 += prevDigit * bj;

//                carry1 += temp_sum1 >>> SHIFT;
//                temp_sum1 &= MASK;
//                carry2 += temp_sum2 >>> SHIFT;
//                temp_sum2 &= MASK;
            }

            if(evenFracDigits) {
                sum <<= 1;
                sum += prevDigit * prevDigit;

                sum2 <<= 1;
            }
            else {
                bj = digits[length];

                sum += prevDigit * bj;
                //carry1 += temp_sum1 >>> SHIFT;
                //temp_sum1 &= MASK;

                sum2 <<= 1;
                sum2 += bj * bj;

                sum <<= 1;
            }

            carry <<= 1;
            carry2 <<= 1;

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

            bj = digits[0];
            sum += bj * bj;

            resDigits[0] = (int) (sum & MASKD0);
        }
        result.sign = 1;

        return  result;
    }

    @Deprecated
    public BigNum30 squareKaratsubaOLD() {

        BigNum30 result = new BigNum30();

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

        long[] partials = new long[fracDigitsp1];
        long[] partialSums = new long[fracDigitsp1];
        long[] partialCarries = new long[fracDigitsp1];

        long PartialSum = 0;
        long PartialCarry = 0;
        for(int i = 0; i < fracDigitsp1; i++) {
            long temp = digits[i];
            long p = temp * temp;

            partials[i] = p;
            PartialSum += p;

            PartialCarry += PartialSum >>> SHIFT;
            PartialSum &= MASK;

            partialSums[i] = PartialSum;
            partialCarries[i] = PartialCarry;
        }

        long old_sum;

        //for(int i = MAX_FRAC_DIGITS; i > 0; i--) {
        //long sum += (int)(old_sum >>> SHIFT30);

        //long sum = (old_sum >>> SHIFT31);

        long p0 = partials[0];
        long sum = p0;

        int length = initialLength;

        long aj;
        long ak;

        int j;
        int k;
        long carry = 0;
        long temp;
        for (j = 1, k = fracDigits; j <= length; j++, k--) {
            aj = digits[j];
            ak = digits[k];


            temp = aj + ak;

            sum += temp * temp;
            carry += sum >>> SHIFT;
            sum &= MASK;

            //System.out.println("a" + j + " * " + "a " + k);
        }

        //System.out.println("OUT");
        //System.out.println("a" + j + " * " + "a " + k);
        //int diff = k - j;
        //int branchlessCheck = (diff >> 31) - (-diff >> 31);

        if(j == k) {
            sum += (partials[k] << 1);
        }
        else  {
            aj = digits[j];
            ak = digits[k];
            temp = ak + aj;
            sum += temp * temp;
        }

        carry += sum >>> SHIFT;
        sum &= MASK;

        sum -= PartialSum;
        carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
        sum &= MASK;

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish


        for(int i = fracDigits; i > 0; i--) {

            sum = old_sum;

            length = (i >> 1) - 1;

            carry = 0;
            for(j = 0, k = i; j <= length; j++, k--) {
                aj = digits[j];
                ak = digits[k];

                temp = aj + ak;

                sum += temp * temp;
                carry += sum >>> SHIFT;
                sum &= MASK;
                //System.out.println("a" + j + " * " + "a " + k);
            }

            //System.out.println("OUT");
            //System.out.println("a" + j + " * " + "a " + k);

            //System.out.println("NEXT");

            //diff = k - j;
            //branchlessCheck = (diff >> 31) - (-diff >> 31);

            if(j == k) {
                sum += (partials[k] << 1);
            }
            else  {
                aj = digits[j];
                ak = digits[k];
                temp = ak + aj;
                sum += temp * temp;
            }

            carry += sum >>> SHIFT;
            sum &= MASK;

            sum -= PartialSum;
            carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
            sum &= MASK;

            if(i > 1) {
//                long partialI = partials[i];
//                PartialSum -= partialI & MASK;
//                PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFT;
//                //PartialCarry -= (PartialSum & MASK31)>>> SHIFT;
//                //PartialCarry -= partialI >>> SHIFT;
//                PartialSum &= MASK;
                int im1 = i - 1;
                PartialSum = partialSums[im1];
                PartialCarry = partialCarries[im1];
            }

            resDigits[i] = (int) (sum);
            //System.out.println(result.digits[i] );
            old_sum = carry;
            //System.out.println("NEXT");
        }

        sum = old_sum;

        sum += p0;

        resDigits[0] = (int) (sum & MASKD0);
        result.sign = 1;

        return  result;
    }

    public BigNum30 squareKaratsuba() {

        BigNum30 result = new BigNum30();

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

        long[] partials = new long[fracDigitsp1];

        long PartialSum = 0;
        long PartialCarry = 0;

        int newLength = (fracDigitsp1 >>> 1) << 1;
        boolean isOdd = (fracDigitsp1 & 1) == 1;
        long temp, p;

        int i;
        for(i = 0; i < newLength;) {
            temp = digits[i];
            p = temp * temp;

            partials[i] = p;
            PartialSum += p;

            i++;

            temp = digits[i];
            p = temp * temp;

            partials[i] = p;
            PartialSum += p;

            i++;

            PartialCarry += PartialSum >>> SHIFT;
            PartialSum &= MASK;
        }

        if(isOdd) {
            temp = digits[i];
            p = temp * temp;

            partials[i] = p;
            PartialSum += p;

            PartialCarry += PartialSum >>> SHIFT;
            PartialSum &= MASK;
        }

        long old_sum;

        //for(int i = MAX_FRAC_DIGITS; i > 0; i--) {
        //long sum += (int)(old_sum >>> SHIFT30);

        //long sum = (old_sum >>> SHIFT31);

        long p0 = partials[0];
        long sum = p0;

        int length = initialLength;

        long aj;
        long ak;

        int j;
        int k;
        long carry = sum >>> SHIFT;
        sum &= MASK;

        newLength = (length >>> 1) << 1;
        isOdd = (length & 1) == 1;

        for (j = 1, k = fracDigits; j <= newLength;) {
            aj = digits[j];
            ak = digits[k];


            temp = aj + ak;

            sum += temp * temp;
            j++;k--;

            aj = digits[j];
            ak = digits[k];


            temp = aj + ak;

            sum += temp * temp;
            j++;k--;

            carry += sum >>> SHIFT;
            sum &= MASK;

            //System.out.println("a" + j + " * " + "a " + k);
        }

        if(isOdd) {
            aj = digits[j];
            ak = digits[k];

            temp = aj + ak;

            sum += temp * temp;
            //carry += sum >>> SHIFT;
            //sum &= MASK;
            j++;k--;
        }

        //System.out.println("OUT");
        //System.out.println("a" + j + " * " + "a " + k);
        //int diff = k - j;
        //int branchlessCheck = (diff >> 31) - (-diff >> 31);

        if(j == k) {
            sum += (partials[k] << 1);
        }
        else  {
            aj = digits[j];
            ak = digits[k];
            temp = ak + aj;
            sum += temp * temp;
        }

        carry += sum >>> SHIFT;
        sum &= MASK;

        sum -= PartialSum;
        carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
        sum &= MASK;

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish

        length = fracDigitsHalf;

        for(i = fracDigits; i > 0; i-=2, length--) {

            carry = old_sum >>> SHIFT;
            old_sum &= MASK;

            sum = old_sum;
            long sum2 = 0;
            long carry2 = 0;

            k = i;
            long prevDigit = digits[k];

            newLength = (length >>> 1) << 1;
            isOdd = (length & 1) == 1;

            for(j = 0; j < newLength;) {
                aj = digits[j];

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                temp = prevDigit + aj;

                sum += temp * temp;

                k--;

                prevDigit = digits[k]; // ak

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                temp = prevDigit + aj;
                sum2 += temp * temp;
                j++;

                aj = digits[j];

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                temp = prevDigit + aj;

                sum += temp * temp;

                k--;

                prevDigit = digits[k]; // ak

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                temp = prevDigit + aj;
                sum2 += temp * temp;
                j++;

                carry += sum >>> SHIFT;
                sum &= MASK;
                carry2 += sum2 >>> SHIFT;
                sum2 &= MASK;
            }

            if(isOdd) {
                aj = digits[j];

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                temp = prevDigit + aj;

                sum += temp * temp;

                k--;

                prevDigit = digits[k]; // ak

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                temp = prevDigit + aj;
                sum2 += temp * temp;

//                carry1 += sum1 >>> SHIFT;
//                sum1 &= MASK;
//                carry2 += sum2 >>> SHIFT;
//                sum2 &= MASK;
            }

            if(evenFracDigits) {
                sum += (partials[length] << 1);
            }
            else {
                aj = digits[length];

                temp = prevDigit + aj;

                sum += temp * temp;

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                sum2 += (partials[length] << 1);
//                carry2 += sum2 >>> SHIFT;
//                sum2 &= MASK;
            }

            carry += sum >>> SHIFT;
            sum &= MASK;

            sum -= PartialSum;
            carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
            sum &= MASK;

            long partialI = partials[i];

            PartialSum -= partialI & MASK;
            PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFT;
            PartialSum &= MASK;

            sum2 += carry;
            carry2 += sum2 >>> SHIFT;
            sum2 &= MASK;


            sum2 -= PartialSum;

            resDigits[i] = (int) (sum);

            if(i > 1) {

                int im1 = i - 1;
                carry2 -= ((sum2 & MASK31) >>> SHIFT) + PartialCarry;
                sum2 &= MASK;

                partialI = partials[im1];

                PartialSum -= partialI & MASK;
                PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFT;
                PartialSum &= MASK;

                old_sum = carry2;

                resDigits[im1] = (int) (sum2);
            }
            else {
                sum2 &= MASK;
                resDigits[0] = (int) (sum2);
            }
        }

        if(evenFracDigits) {
            sum = p0 + old_sum;

            resDigits[0] = (int) (sum & MASKD0);
        }

        result.sign = 1;

        return  result;
    }

    /* This function has better performance than the normal one after about 3333 digits */
    public BigNum30 squareFullBackwards() {


        BigNum30 result = new BigNum30();

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

        int newLength = (length >>> 1) << 1;
        boolean isOdd = (length & 1) == 1;

        for (j = 1, k = fracDigits; j <= newLength;) {
            bj = digits[j];
            ak = digits[k];

            sum += bj * ak;

            j++; k--;

            bj = digits[j];
            ak = digits[k];

            sum += bj * ak;

            j++; k--;

            carry += sum >>> SHIFT;
            sum &= MASK;
        }

        if(isOdd) {
            bj = digits[j];
            ak = digits[k];

            sum += bj * ak;

            j++; k--;
            //carry += sum >>> SHIFT;
            //sum &= MASK;
        }

        carry <<= 1;

        if(j == k) {
            bj = digits[j];
            sum <<= 1;
            sum += bj * bj;
        }
        else  {
            bj = digits[j];
            ak = digits[k];
            sum += bj * ak;
            sum <<= 1;
        }

        carry += sum >>> SHIFT;
        sum &= MASK;

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish

        length = fracDigitsHalf;
        int length2 = evenFracDigits ? length : length + 1;

        for(int i = fracDigits; i > 0; i-=2, length--, length2--) {

            sum = 0;
            long sum2 = 0;
            carry = 0;
            long carry2 = 0;

            k = length2;
            long prevDigit = digits[k];
            long startDigit = prevDigit;

            int range = length - ((length >>> 1) << 1);
            isOdd = (length & 1) == 1;

            for(j = length - 1; j >= range;) {
                bj = digits[j];

                sum2 += prevDigit * bj;

                k++;

                prevDigit = digits[k]; //ak

                sum += prevDigit * bj;

                j--;

                bj = digits[j];

                sum2 += prevDigit * bj;

                k++;

                prevDigit = digits[k]; //ak

                sum += prevDigit * bj;

                j--;

                carry += sum >>> SHIFT;
                sum &= MASK;
                carry2 += sum2 >>> SHIFT;
                sum2 &= MASK;
            }

            if(isOdd) {
                bj = digits[j];

                sum2 += prevDigit * bj;

                k++;

                prevDigit = digits[k]; //ak

                sum += prevDigit * bj;

//                carry1 += temp_sum1 >>> SHIFT;
//                temp_sum1 &= MASK;
//                carry2 += temp_sum2 >>> SHIFT;
//                temp_sum2 &= MASK;
            }

            if(evenFracDigits) {
                sum <<= 1;
                sum += startDigit * startDigit;

                sum2 <<= 1;
            }
            else {
                bj = digits[length];

                sum += startDigit * bj;
                //carry1 += temp_sum1 >>> SHIFT;
                //temp_sum1 &= MASK;

                sum2 <<= 1;
                sum2 += bj * bj;

                sum <<= 1;
            }

            carry <<= 1;
            carry2 <<= 1;

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
            bj = digits[0];
            sum = bj * bj + old_sum;

            resDigits[0] = (int) (sum & MASKD0);
        }
        result.sign = 1;

        return  result;
    }

    /* This function has better performance than the normal one after about 3333 digits */
    public BigNum30 squareKaratsubaBackwards() {

        BigNum30 result = new BigNum30();

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

        long[] partials = new long[fracDigitsp1];

        long PartialSum = 0;
        long PartialCarry = 0;

        int newLength = (fracDigitsp1 >>> 1) << 1;
        boolean isOdd = (fracDigitsp1 & 1) == 1;
        int i;
        long temp, p;

        for(i = 0; i < newLength;) {
            temp = digits[i];
            p = temp * temp;

            partials[i] = p;
            PartialSum += p;
            i++;

            temp = digits[i];
            p = temp * temp;

            partials[i] = p;
            PartialSum += p;
            i++;

            PartialCarry += PartialSum >>> SHIFT;
            PartialSum &= MASK;
        }

        if(isOdd) {
            temp = digits[i];
            p = temp * temp;

            partials[i] = p;
            PartialSum += p;

            PartialCarry += PartialSum >>> SHIFT;
            PartialSum &= MASK;
        }

        long old_sum;

        //for(int i = MAX_FRAC_DIGITS; i > 0; i--) {
        //long sum += (int)(old_sum >>> SHIFT30);

        //long sum = (old_sum >>> SHIFT31);

        long p0 = partials[0];
        long sum = p0;

        int length = initialLength;

        long aj;
        long ak;

        int j;
        int k;
        long carry = sum >>> SHIFT;
        sum &= MASK;

        newLength = (length >>> 1) << 1;
        isOdd = (length & 1) == 1;

        for (j = 1, k = fracDigits; j <= newLength; ) {
            aj = digits[j];
            ak = digits[k];


            temp = aj + ak;

            sum += temp * temp;

            j++; k--;

            aj = digits[j];
            ak = digits[k];


            temp = aj + ak;

            sum += temp * temp;

            j++; k--;

            carry += sum >>> SHIFT;
            sum &= MASK;

            //System.out.println("a" + j + " * " + "a " + k);
        }

        if(isOdd) {
            aj = digits[j];
            ak = digits[k];

            temp = aj + ak;

            sum += temp * temp;

            j++; k--;

            //carry += sum >>> SHIFT;
            //sum &= MASK;
        }

        //System.out.println("OUT");
        //System.out.println("a" + j + " * " + "a " + k);
        //int diff = k - j;
        //int branchlessCheck = (diff >> 31) - (-diff >> 31);

        if(j == k) {
            sum += (partials[k] << 1);
        }
        else  {
            aj = digits[j];
            ak = digits[k];
            temp = ak + aj;
            sum += temp * temp;
        }

        carry += sum >>> SHIFT;
        sum &= MASK;

        sum -= PartialSum;
        carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
        sum &= MASK;

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish

        length = fracDigitsHalf;
        int length2 = evenFracDigits ? length : length + 1;

        for(i = fracDigits; i > 0; i-=2, length--, length2--) {

            carry = old_sum >>> SHIFT;
            old_sum &= MASK;
            sum = old_sum;
            long sum2 = 0;
            long carry2 = 0;

            k = length2;
            long prevDigit = digits[k];
            long firstDigit = prevDigit;

            int range = length - ((length >>> 1) << 1);
            isOdd = (length & 1) == 1;

            for(j = length - 1; j >= range;) {
                aj = digits[j];

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                temp = prevDigit + aj;
                sum2 += temp * temp;

                k++;

                prevDigit = digits[k]; // ak

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                temp = prevDigit + aj;
                sum += temp * temp;

                j--;

                aj = digits[j];

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                temp = prevDigit + aj;
                sum2 += temp * temp;

                k++;

                prevDigit = digits[k]; // ak

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                temp = prevDigit + aj;
                sum += temp * temp;

                j--;

                carry += sum >>> SHIFT;
                sum &= MASK;
                carry2 += sum2 >>> SHIFT;
                sum2 &= MASK;
            }

            if(isOdd) {
                aj = digits[j];

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                temp = prevDigit + aj;
                sum2 += temp * temp;

                k++;

                prevDigit = digits[k]; // ak

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                temp = prevDigit + aj;
                sum += temp * temp;

//                carry1 += sum1 >>> SHIFT;
//                sum1 &= MASK;
//                carry2 += sum2 >>> SHIFT;
//                sum2 &= MASK;
            }

            if(evenFracDigits) {
                sum += (partials[length] << 1);
            }
            else {
                aj = digits[length];

                temp = firstDigit + aj;

                sum += temp * temp;

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                sum2 += (partials[length] << 1);
//                carry2 += sum2 >>> SHIFT;
//                sum2 &= MASK;
            }

            carry += sum >>> SHIFT;
            sum &= MASK;

            sum -= PartialSum;
            carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
            sum &= MASK;

            long partialI = partials[i];

            PartialSum -= partialI & MASK;
            PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFT;
            PartialSum &= MASK;

            sum2 += carry;
            carry2 += sum2 >>> SHIFT;
            sum2 &= MASK;


            sum2 -= PartialSum;

            resDigits[i] = (int) (sum);

            if(i > 1) {

                int im1 = i - 1;
                carry2 -= ((sum2 & MASK31) >>> SHIFT) + PartialCarry;
                sum2 &= MASK;

                partialI = partials[im1];

                PartialSum -= partialI & MASK;
                PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFT;
                PartialSum &= MASK;

                old_sum = carry2;

                resDigits[im1] = (int) (sum2);
            }
            else {
                sum2 &= MASK;
                resDigits[0] = (int) (sum2);
            }
        }

        if(evenFracDigits) {
            sum = p0 + old_sum;

            resDigits[0] = (int) (sum & MASKD0);
        }

        result.sign = 1;

        return  result;
    }

    @Override
    public BigNum30 mult(BigNum bb) {
       BigNum30 b = (BigNum30) bb;
       if(useKaratsuba) {
            return multKaratsuba(b);
        }
        else {
            return multFull(b);
        }
    }

    /* Sign is positive */
    @Override
    public BigNum30 mult(int value) {
        BigNum30 result = new BigNum30();

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
            sum = (long)digits[i] * value + old_sum;
            carry = sum >>> SHIFT;
            sum &= MASK;

            int di = (int) (sum);
            resDigits[i] = di;
            isNonZero |= di;
            old_sum = carry;
        }

        sum = (long)digits[0] * value + old_sum;

        int d0 = (int) (sum & MASKD0);
        resDigits[0] = d0;

        result.isOne = d0 == 1 && isNonZero == 0;

        result.sign = sign * bsign;

        return result;
    }

    @Deprecated
    public BigNum30 multFullGolden(BigNum30 b) {

        BigNum30 result = new BigNum30();

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
            bj = bdigits[j];
            ak = digits[k];

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
                bj = bdigits[j];
                ak = digits[k];

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

        bj = bdigits[0];
        ak = digits[0];

        sum += bj * ak;

        int d0 = (int) (sum & MASKD0);
        resDigits[0] = d0;

        result.isOne = d0 == 1 && isNonZero == 0;

        result.sign = sign * b.sign;

        return result;

    }

    /* A backwards function might have better performance after 3333 digits */
    //Todo: test it
    public BigNum30 multFull(BigNum30 b) {

        BigNum30 result = new BigNum30();

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

        int newLength = fracDigitsHalf << 1;

        int j, k;

        for (j = 1, k = fracDigits; j <= newLength;) {
            bj = bdigits[j];
            ak = digits[k];

            sum += bj * ak;

            j++;k--;

            bj = bdigits[j];
            ak = digits[k];

            sum += bj * ak;

            j++;k--;

            carry += sum >>> SHIFT;
            sum &= MASK;
        }

        if(!evenFracDigits) {
            bj = bdigits[j];
            ak = digits[k];
            sum += bj * ak;
            carry += sum >>> SHIFT;
            sum &= MASK;
        }

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish

        //System.out.println();
        //}

        long a0 = digits[0];

        int isNonZero = 0;
        for(int i = fracDigits; i > 0; i-=2) {
            sum = 0;
            long sum2 = 0;
            carry = old_sum >>> SHIFT;
            old_sum &= MASK;
            long carry2 = 0;

            k = i;
            long prevDigit = digits[k];

            newLength = (i >>> 1) << 1;
            boolean isOdd = (i & 1) == 1;

            for(j = 0; j < newLength;) {
                bj = bdigits[j];
                sum += prevDigit * bj;

                //System.out.println("b" + j + " * " + "a " + k);

                k--;

                prevDigit = digits[k]; //ak
                sum2 += prevDigit * bj;
                //System.out.println("b" + j + " * " + "a " + k);

                j++;

                bj = bdigits[j];
                sum += prevDigit * bj;
                //System.out.println("b" + j + " * " + "a " + k);

                k--;

                prevDigit = digits[k]; //ak
                sum2 += prevDigit * bj;
                //System.out.println("b" + j + " * " + "a " + k);

                j++;

                carry += sum >>> SHIFT;
                sum &= MASK;

                carry2 += sum2 >>> SHIFT;
                sum2 &= MASK;
            }

            if(isOdd) {
                bj = bdigits[j];
                sum += prevDigit * bj;
                //System.out.println("b" + j + " * " + "a " + k);

                k--;

                prevDigit = digits[k]; //ak
                sum2 += prevDigit * bj;
                //System.out.println("b" + j + " * " + "a " + k);

//                carry1 += temp_sum1 >>> SHIFT;
//                temp_sum1 &= MASK;
//
//                carry2 += temp_sum2 >>> SHIFT;
//                temp_sum2 &= MASK;
                j++;
            }

            bj = bdigits[j];

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
            bj = bdigits[0];

            sum = bj * a0 + old_sum;

            int d0 = (int) (sum & MASKD0);
            resDigits[0] = d0;

            result.isOne = d0 == 1 && isNonZero == 0;
        }

        result.sign = sign * b.sign;

        return result;

    }

    public BigNum30 multFullBackwards(BigNum30 b) {

        BigNum30 result = new BigNum30();

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

        int newLength = fracDigitsHalf << 1;

        int j, k;

        for (j = 1, k = fracDigits; j <= newLength;) {
            bj = bdigits[j];
            ak = digits[k];

            sum += bj * ak;

            j++;k--;

            bj = bdigits[j];
            ak = digits[k];

            sum += bj * ak;

            j++;k--;

            carry += sum >>> SHIFT;
            sum &= MASK;
        }

        if(!evenFracDigits) {
            bj = bdigits[j];
            ak = digits[k];
            sum += bj * ak;
            carry += sum >>> SHIFT;
            sum &= MASK;
        }

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish

        //System.out.println();
        //}

        long a0 = digits[0];

        int isNonZero = 0;
        for(int i = fracDigits; i > 0; i-=2) {
            int im1 = i - 1;
            carry = old_sum >>> SHIFT;
            old_sum &= MASK;
            sum = 0;
            long sum2 = 0;
            long carry2 = 0;

            k = 0;

            long prevDigit = bdigits[i];

            int range = i - ((i >>> 1) << 1);
            boolean isOdd = (i & 1) == 1;

            for(j = i; j > range;) {
                ak = digits[k];
                sum += prevDigit * ak;

                //System.out.println("b" + j + " * " + "a " + k);
                j--;

                prevDigit = bdigits[j];
                sum2 += prevDigit * ak;
                //System.out.println("b" + j + " * " + "a " + k);
                k++;

                ak = digits[k];
                sum += prevDigit * ak;

                //System.out.println("b" + j + " * " + "a " + k);
                j--;

                prevDigit = bdigits[j];
                sum2 += prevDigit * ak;
                //System.out.println("b" + j + " * " + "a " + k);
                k++;

                carry += sum >>> SHIFT;
                sum &= MASK;

                carry2 += sum2 >>> SHIFT;
                sum2 &= MASK;
            }

            if(isOdd) {
                ak = digits[k];
                sum += prevDigit * ak;

                //System.out.println("b" + j + " * " + "a " + k);
                j--;

                prevDigit = bdigits[j];
                sum2 += prevDigit * ak;
                //System.out.println("b" + j + " * " + "a " + k);
                k++;

//                carry1 += temp_sum1 >>> SHIFT;
//                temp_sum1 &= MASK;
//
//                carry2 += temp_sum2 >>> SHIFT;
//                temp_sum2 &= MASK;
            }


            //System.out.println("b" + j + " * " + "a " + k);
            ak = digits[k];
            sum += prevDigit * ak + old_sum;
            //carry1 += temp_sum1 >>> SHIFT;
            //temp_sum1 &= MASK;

            //System.out.println("b" + i + " * " + "a " + startIndex);

            carry += sum >>> SHIFT;
            sum &= MASK;

            //System.out.println("NEXT");

            int di = (int) (sum);
            isNonZero |= di;
            resDigits[i] = di;


            if(i > 1) {
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
            bj = bdigits[0];

            sum = bj * a0 + old_sum;

            int d0 = (int) (sum & MASKD0);
            resDigits[0] = d0;

            result.isOne = d0 == 1 && isNonZero == 0;
        }

        result.sign = sign * b.sign;

        return result;

    }

    @Deprecated
    public BigNum30 multKaratsubaGolden(BigNum30 b) {

        BigNum30 result = new BigNum30();

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
            long p = ((long) digits[i]) * ((long) bdigits[i]);
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
            bj = bdigits[j];
            bk = bdigits[k];
            aj = digits[j];
            ak = digits[k];

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
            bj = bdigits[j];
            bk = bdigits[k];
            aj = digits[j];
            ak = digits[k];
            sum += (bk + bj) * (ak + aj);
        }

        carry += sum >>> SHIFT;
        sum &= MASK;

        sum -= PartialSum;
        carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
        sum &= MASK;

        //sum += (1 - branchlessCheck) * partials[k] + branchlessCheck * ((bk + bj) * (ak + aj) - partials[k] - partials[j]);

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish, adds bias because 10000 is always shifted upwards


        int isNonZero = 0;
        for(int i = fracDigits; i > 0; i--) {

            length = (i >> 1) - 1;

            sum = old_sum; //carry from prev

            carry = 0;
            for(j = 0, k = i; j <= length; j++, k--) {
                bj = bdigits[j];
                bk = bdigits[k];
                aj =  digits[j];
                ak = digits[k];

                sum += (bk + bj) * (ak + aj);

                carry += sum >>> SHIFT;
                sum &= MASK;
                //System.out.println("partials " + k + " partials " + j);
                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

            }

            if(j == k) {
                sum += (partials[k] << 1);
            }
            else  {
                bj = bdigits[j];
                bk = bdigits[k];
                aj = digits[j];
                ak = digits[k];
                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);
                sum += (bk + bj) * (ak + aj);
            }

            //System.out.println("NEXT");

            carry += sum >>> SHIFT;
            sum &= MASK;

            sum -= PartialSum;
            carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
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

    public BigNum30 multKaratsubaBackwards(BigNum30 b) {

        BigNum30 result = new BigNum30();

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

        long PartialSum = 0;
        long PartialCarry = 0;

        int newLength = (fracDigitsp1 >>> 1) << 1;
        boolean isOdd = (fracDigitsp1 & 1) == 1;
        long p;
        int i;
        for(i = 0; i < newLength;) {
            p = ((long)digits[i]) * ((long)bdigits[i]);
            partials[i] = p;
            PartialSum += p;

            i++;

            p = ((long)digits[i]) * ((long)bdigits[i]);
            partials[i] = p;
            PartialSum += p;

            i++;

            PartialCarry += PartialSum >>> SHIFT;
            PartialSum &= MASK;
        }

        if(isOdd) {
            p = ((long)digits[i]) * ((long)bdigits[i]);
            partials[i] = p;
            PartialSum += p;
            PartialCarry += PartialSum >>> SHIFT;
            PartialSum &= MASK;
        }

        long old_sum;

        long p0 = partials[0];

        long sum = p0;

        int length = initialLength;

        int j;
        int k;
        //        long carry = sum >>> SHIFT;
        //        sum &= MASK;
        long carry = 0;

        long bj, bk, aj, ak;

        newLength = (length >>> 1) << 1;
        isOdd = (length & 1) == 1;

        for (j = 1, k = fracDigits; j <= newLength;) {
            bj = bdigits[j];
            bk = bdigits[k];
            aj = digits[j];
            ak = digits[k];

            sum += (bk + bj) * (ak + aj);

            j++; k--;

            bj = bdigits[j];
            bk = bdigits[k];
            aj = digits[j];
            ak = digits[k];

            sum += (bk + bj) * (ak + aj);

            j++; k--;

            carry += sum >>> SHIFT;
            sum &= MASK;

            //System.out.println("partials " + k + " partials " + j);

        }

        if(isOdd) {
            bj = bdigits[j];
            bk = bdigits[k];
            aj = digits[j];
            ak = digits[k];

            sum += (bk + bj) * (ak + aj);

            j++; k--;

            //carry += sum >>> SHIFT;
            //sum &= MASK;
        }

        if(j == k) {
            sum += (partials[k] << 1);
        }
        else  {
            bj = bdigits[j];
            bk = bdigits[k];
            aj = digits[j];
            ak = digits[k];
            sum += (bk + bj) * (ak + aj);
        }

        carry += sum >>> SHIFT;
        sum &= MASK;

        sum -= PartialSum;
        carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
        sum &= MASK;

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish, adds bias because 10000 is always shifted upwards


        int isNonZero = 0;

        length = fracDigitsHalf;
        int length2 = evenFracDigits ? length : length + 1;

        for(i = fracDigits; i > 0; i-=2, length--, length2--) {

            //carry = old_sum >>> SHIFT;
            //old_sum &= MASK;
            carry = 0;
            sum = old_sum;
            long sum2 = 0;
            long carry2 = 0;

            k = length2;
            long prevDigit = digits[k];
            long prevDigit2 = bdigits[k];
            long startDigit = prevDigit;
            long startDigit2 = prevDigit2;

            int range = length - ((length >>> 1) << 1);
            isOdd = (length & 1) == 1;

            for(j = length - 1; j >= range;) {
                aj = digits[j];
                bj = bdigits[j];

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                sum2 += (prevDigit2 + bj) * (prevDigit + aj);

                k++;

                prevDigit = digits[k]; //ak
                prevDigit2 = bdigits[k]; //bk

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                sum += (prevDigit2 + bj) * (prevDigit + aj);
                j--;

                aj = digits[j];
                bj = bdigits[j];

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                sum2 += (prevDigit2 + bj) * (prevDigit + aj);

                k++;

                prevDigit = digits[k]; //ak
                prevDigit2 = bdigits[k]; //bk

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                sum += (prevDigit2 + bj) * (prevDigit + aj);
                j--;

                carry += sum >>> SHIFT;
                sum &= MASK;
                carry2 += sum2 >>> SHIFT;
                sum2 &= MASK;

            }

            if(isOdd) {
                aj = digits[j];
                bj = bdigits[j];

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                sum2 += (prevDigit2 + bj) * (prevDigit + aj);

                k++;

                prevDigit = digits[k]; //ak
                prevDigit2 = bdigits[k]; //bk

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                sum += (prevDigit2 + bj) * (prevDigit + aj);

//                carry1 += sum1 >>> SHIFT;
//                sum1 &= MASK;
//                carry2 += sum2 >>> SHIFT;
//                sum2 &= MASK;
            }

            if(evenFracDigits) {
                sum += (partials[length] << 1);
            }
            else {
                aj = digits[length];
                bj = bdigits[length];

                sum += (startDigit2 + bj) * (startDigit + aj);

                //System.out.println("b " + length + " b " + length2 + " a " + length + " a " + length2);

                sum2 += (partials[length] << 1);
//                carry2 += sum2 >>> SHIFT;
//                sum2 &= MASK;
            }

            //System.out.println("NEXT");

            carry += sum >>> SHIFT;
            sum &= MASK;

            sum -= PartialSum;
            carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
            sum &= MASK;

            long partialI = partials[i];

            PartialSum -= partialI & MASK;
            PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFT;
            PartialSum &= MASK;

            sum2 += carry;
            carry2 += sum2 >>> SHIFT;
            sum2 &= MASK;


            sum2 -= PartialSum;

            int di = (int) (sum);
            isNonZero |= di;
            resDigits[i] = di;

            if(i > 1) {

                int im1 = i - 1;
                carry2 -= ((sum2 & MASK31) >>> SHIFT) + PartialCarry;
                sum2 &= MASK;

                partialI = partials[im1];

                PartialSum -= partialI & MASK;
                PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFT;
                PartialSum &= MASK;

                old_sum = carry2;

                di = (int) (sum2);
                isNonZero |= di;
                resDigits[im1] = di;
            }
            else {
                sum2 &= MASK;
                int d0 = (int) (sum2 & MASKD0);
                resDigits[0] = d0;
                result.isOne = d0 == 1 && isNonZero == 0;
            }
        }

        if(evenFracDigits) {
            sum = p0 + old_sum;

            int d0 = (int) (sum & MASKD0);
            resDigits[0] = d0;

            //result.isOne = ( (((d0 ^ isNonZero) | d0) & ~(isNonZero & 0x1)) )  == 1
            result.isOne = d0 == 1 && isNonZero == 0;
        }

        result.sign = sign * b.sign;

        return result;

    }

    public BigNum30 multKaratsuba(BigNum30 b) {

        BigNum30 result = new BigNum30();

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

        long PartialSum = 0;
        long PartialCarry = 0;

        int newLength = (fracDigitsp1 >>> 1) << 1;
        boolean isOdd = (fracDigitsp1 & 1) == 1;
        long p;
        int i;
        for(i = 0; i < newLength;) {
            p = ((long)digits[i]) * ((long)bdigits[i]);
            partials[i] = p;
            PartialSum += p;

            i++;

            p = ((long)digits[i]) * ((long)bdigits[i]);
            partials[i] = p;
            PartialSum += p;

            i++;

            PartialCarry += PartialSum >>> SHIFT;
            PartialSum &= MASK;
        }

        if(isOdd) {
            p = ((long)digits[i]) * ((long)bdigits[i]);
            partials[i] = p;
            PartialSum += p;
            PartialCarry += PartialSum >>> SHIFT;
            PartialSum &= MASK;
        }

        long old_sum;

        long p0 = partials[0];

        long sum = p0;

        int length = initialLength;

        int j;
        int k;
        //        long carry = sum >>> SHIFT;
        //        sum &= MASK;
        long carry = 0;
        long bj, bk, aj, ak;

        newLength = (length >>> 1) << 1;
        isOdd = (length & 1) == 1;

        for (j = 1, k = fracDigits; j <= newLength;) {
            bj = bdigits[j];
            bk = bdigits[k];
            aj = digits[j];
            ak = digits[k];

            sum += (bk + bj) * (ak + aj);

            j++; k--;

            bj = bdigits[j];
            bk = bdigits[k];
            aj = digits[j];
            ak = digits[k];

            sum += (bk + bj) * (ak + aj);

            j++; k--;

            carry += sum >>> SHIFT;
            sum &= MASK;

            //System.out.println("partials " + k + " partials " + j);

        }

        if(isOdd) {
            bj = bdigits[j];
            bk = bdigits[k];
            aj = digits[j];
            ak = digits[k];

            sum += (bk + bj) * (ak + aj);

            j++; k--;

            //carry += sum >>> SHIFT;
            //sum &= MASK;
        }

        if(j == k) {
            sum += (partials[k] << 1);
        }
        else  {
            bj = bdigits[j];
            bk = bdigits[k];
            aj = digits[j];
            ak = digits[k];
            sum += (bk + bj) * (ak + aj);
        }

        carry += sum >>> SHIFT;
        sum &= MASK;

        sum -= PartialSum;
        carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
        sum &= MASK;

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish, adds bias because 10000 is always shifted upwards


        int isNonZero = 0;

        length = fracDigitsHalf;

        for(i = fracDigits; i > 0; i-=2, length--) {

            //carry = old_sum >>> SHIFT;
            //old_sum &= MASK;
            carry = 0;
            sum = old_sum;
            long sum2 = 0;
            long carry2 = 0;

            k = i;

            long prevDigit = digits[k];
            long prevDigit2 = bdigits[k];

            newLength = (length >>> 1) << 1;
            isOdd = (length & 1) == 1;

            for(j = 0; j < newLength;) {
                aj = digits[j];
                bj = bdigits[j];

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);


                sum += (prevDigit2 + bj) * (prevDigit + aj);

                k--;

                prevDigit = digits[k]; // ak
                prevDigit2 = bdigits[k];

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                sum2 += (prevDigit2 + bj) * (prevDigit + aj);
                j++;

                aj = digits[j];
                bj = bdigits[j];

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);


                sum += (prevDigit2 + bj) * (prevDigit + aj);

                k--;

                prevDigit = digits[k]; // ak
                prevDigit2 = bdigits[k];

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                sum2 += (prevDigit2 + bj) * (prevDigit + aj);
                j++;

                carry += sum >>> SHIFT;
                sum &= MASK;
                carry2 += sum2 >>> SHIFT;
                sum2 &= MASK;
            }

            if(isOdd) {
                aj = digits[j];
                bj = bdigits[j];

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                sum += (prevDigit2 + bj) * (prevDigit + aj);

                k--;

                prevDigit = digits[k]; // ak
                prevDigit2 = bdigits[k];

                //System.out.println("b " + j + " b " + k + " a " + j + " a " + k);

                sum2 += (prevDigit2 + bj) * (prevDigit + aj);

//                carry1 += sum1 >>> SHIFT;
//                sum1 &= MASK;
//                carry2 += sum2 >>> SHIFT;
//                sum2 &= MASK;
            }

            if(evenFracDigits) {
                sum += (partials[length] << 1);
            }
            else {
                aj = digits[length];
                bj = bdigits[length];

                sum += (prevDigit2 + bj) * (prevDigit + aj);

                //System.out.println("b " + length + " b " + length2 + " a " + length + " a " + length2);

                sum2 += (partials[length] << 1);

//                carry2 += sum2 >>> SHIFT;
//                sum2 &= MASK;
            }

            //System.out.println("NEXT");

            carry += sum >>> SHIFT;
            sum &= MASK;

            sum -= PartialSum;
            carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
            sum &= MASK;

            long partialI = partials[i];

            PartialSum -= partialI & MASK;
            PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFT;
            PartialSum &= MASK;

            sum2 += carry;
            carry2 += sum2 >>> SHIFT;
            sum2 &= MASK;


            sum2 -= PartialSum;

            int di = (int) (sum);
            isNonZero |= di;
            resDigits[i] = di;

            if(i > 1) {

                int im1 = i - 1;
                carry2 -= ((sum2 & MASK31) >>> SHIFT) + PartialCarry;
                sum2 &= MASK;

                partialI = partials[im1];

                PartialSum -= partialI & MASK;
                PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFT;
                PartialSum &= MASK;

                old_sum = carry2;

                di = (int) (sum2);
                isNonZero |= di;
                resDigits[im1] = di;
            }
            else {
                sum2 &= MASK;
                int d0 = (int) (sum2 & MASKD0);
                resDigits[0] = d0;
                result.isOne = d0 == 1 && isNonZero == 0;
            }
        }

        if(evenFracDigits) {
            sum = p0 + old_sum;

            int d0 = (int) (sum & MASKD0);
            resDigits[0] = d0;

            //result.isOne = ( (((d0 ^ isNonZero) | d0) & ~(isNonZero & 0x1)) )  == 1
            result.isOne = d0 == 1 && isNonZero == 0;
        }

        result.sign = sign * b.sign;

        return result;

    }

    /*mult2, mult4, divide2, divide4, multFull, multKaratsuba, square and squareKaratsuba
     might overflow or underflow leading to zero but the sign will not be set to 0
     */

    @Override
    public BigNum30 mult2() {

        BigNum30 res = new BigNum30();

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

        int temp = digits[fracDigits] << 1;
        int val = temp & MASKINT;
        resDigits[fracDigits] = val;
        isNonZero |= val;

        for(int i = fracDigitsm1; i > 0; i--) {
            temp = (digits[i] << 1) | (temp >>> SHIFT);
            val = temp & MASKINT;
            resDigits[i] = val;
            isNonZero |= val;
        }

        temp = (digits[0] << 1) | (temp >>> SHIFT);
        int d0 = (int)(temp & MASKD0);
        resDigits[0] = d0;
        res.sign = sign;
        res.isOne = d0 == 1 && isNonZero == 0;
        return res;

    }

    @Override
    public BigNum30 divide2() {

        BigNum30 res = new BigNum30();

        if(sign == 0) {
            return res;
        }

        int [] resDigits = res.digits;

        if(isOne) {
            resDigits[1] = 0x20000000;
            res.sign = sign;
            return res;
        }

        int isNonZero = 0;

        int d0 = digits[0];
        int temp = d0;
        int val = temp >>> 1;

        int bit = temp & 0x1;

        resDigits[0] = val;
        isNonZero |= val;

        for(int i=1; i < fracDigits; i++) {
            temp = digits[i];
            temp |= (bit << SHIFT);
            bit = temp & 0x1;
            temp >>>= 1;
            val = temp & MASKINT;
            resDigits[i] = val;
            isNonZero |= val;
        }

        temp = digits[fracDigits];
        temp |= (bit << SHIFT);
        temp >>>= 1;
        val = temp & MASKINT;
        resDigits[fracDigits] = val;
        isNonZero |= val;
        res.sign = sign;
        res.isOne = d0 == 1 && isNonZero == 0;
        return res;

    }

    @Override
    public BigNum30 divide4() {

        BigNum30 res = new BigNum30();

        if(sign == 0) {
            return res;
        }

        int [] resDigits = res.digits;

        if(isOne) {
            resDigits[1] = 0x10000000;
            res.sign = sign;
            return res;
        }

        int isNonZero = 0;

        int d0 = digits[0];
        int temp = d0;
        int val = temp >>> 2;

        int bits = temp & 0x3;

        resDigits[0] = val;
        isNonZero |= val;

        for(int i=1; i < fracDigits; i++) {
            temp = digits[i];
            temp |= (bits << SHIFT);
            bits = temp & 0x3;
            temp >>>= 2;
            val = temp & MASKINT;
            resDigits[i] = val;
            isNonZero |= val;
        }

        temp = digits[fracDigits];
        temp |= (bits << SHIFT);
        temp >>>= 2;
        val = temp & MASKINT;
        resDigits[fracDigits] = val;
        isNonZero |= val;
        res.sign = sign;
        res.isOne = d0 == 1 && isNonZero == 0;
        return res;

    }

    @Override
    public BigNum30 mult4() {

        BigNum30 res = new BigNum30();

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

        int temp = digits[fracDigits] << 2;
        int val = temp & MASKINT;
        resDigits[fracDigits] = val;
        isNonZero |= val;

        for(int i=fracDigitsm1; i > 0; i--) {
            temp = (digits[i] << 2) | (temp >>> SHIFT);
            val = temp & MASKINT;
            resDigits[i] = val;
            isNonZero |= val;
        }

        temp = (digits[0] << 2) | (temp >>> SHIFT);
        int d0 = (int)(temp & MASKD0);
        resDigits[0] = d0;
        res.sign = sign;
        res.isOne = d0 == 1 && isNonZero == 0;
        return res;

    }

    /*
         Too slow
     */
    public BigNum30 divide(BigNum30 b) {

        if(b.sign == 0) {
            BigNum30 result = getMax();
            result.sign = sign;
        }

        BigNum30 result = new BigNum30();

        if(sign == 0) {
            return result;
        }

        if (isOne && b.isOne) {
            result.digits[0] = 1;
            result.isOne = true;
            result.sign = sign * b.sign;
            return result;
        }
        else if (b.isOne){
            System.arraycopy(digits, 0, result.digits, 0, digits.length);
            result.sign = sign * b.sign;
            return result;
        }

        BigNum30 low = new BigNum30();
        BigNum30 high = this.compare(b) < 0 ? new BigNum30(1) : new BigNum30(0x7fffffff);

        BigNum30 oldVal = null;

        //int iterations = 0;

        while (true) {
            BigNum30 mid = low.add(high.sub(low).divide2());

            BigNum30 temp = b.mult(mid);

            BigNum30 val = temp.sub(this);

            if(oldVal != null && val.compare(oldVal) == 0) {
                result.digits = mid.digits;
                result.sign = sign * b.sign;
                result.isOne = result.digits[0] == 1 && isFractionalPartZero(result.digits);
                return result;
            }

            oldVal = val;

            if(temp.compare(this) < 0) {
                low = mid;
            }
            else {
                high = mid;
            }
            //iterations++;
        }


    }

    /*
         Too slow
     */
    public BigNum30 reciprocal() {

        if(sign == 0) {
            BigNum30 result = getMax();
            result.sign = sign;
        }

        BigNum30 result = new BigNum30();

        if (isOne) {
            result.digits[0] = 1;
            result.isOne = true;
            result.sign = sign;
            return result;
        }

        BigNum30 low = new BigNum30();
        BigNum30 high  = digits[0] != 0 ? new BigNum30(1) : new BigNum30(0x7fffffff);

        BigNum30 oldVal = null;

        BigNum30 one = new BigNum30(1);

        while (true) {
            BigNum30 mid = low.add(high.sub(low).divide2());

            BigNum30 temp = this.mult(mid);

            BigNum30 val = temp.sub(one);

            if(oldVal != null && val.compare(oldVal) == 0) {
                result.digits = mid.digits;
                result.sign = sign;
                result.isOne = result.digits[0] == 1 && isFractionalPartZero(result.digits);
                return result;
            }

            oldVal = val;

            if(temp.compare(one) < 0) {
                low = mid;
            }
            else {
                high = mid;
            }
        }


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
            long v = digit;
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
        mantissa |= ((long)digit) << temp; //Will always be positive
        mantissa &= 0xFFFFFFFFFFFFFL;

        //System.out.println(digits[i]);

        i++;

        int k;
        for(k = bitOffset ; k < 52 && i < digits.length; k+=SHIFT, i++) {
            long val = digits[i];
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
                    long val2 = digits[i + 1];
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

    public static boolean isFractionalPartZero(int[] digits) {

        for(int i = 1; i < digits.length; i++) {
            if(digits[i] != 0) {
                return  false;
            }
        }

        return true;
    }

    public static BigNum30 max(BigNum30 a, BigNum30 b) {
        return a.compare(b) > 0 ? a : b;
    }

    public static BigNum30 min(BigNum30 a, BigNum30 b) {
        return a.compare(b) < 0 ? a : b;
    }

    public BigNum30 mult2to30(int times) {

        BigNum30 result = new BigNum30(this);

        if(times <= 0) {
            return result;
        }

        int[] digits = result.digits;
        int total = digits.length + times;
        int isNonZero = 0;

        for(int i = times, location = 0; i < total; i++, location++) {
            if(i < digits.length) {
                if(location == 0) {
                    int value = digits[i - 1];
                    int finalValue = digits[i];
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
                    int value = digits[im1];
                    int finalValue = (value & 0x1) << SHIFT;
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
    public BigNum30 div2toi(long power) {
        BigNum30 result = new BigNum30(this);

        if(power <= 0) {
            return result;
        }

        int times = (int)(power / SHIFT);

        int internalShift = (int)(power % SHIFT);

        int[] digits = result.digits;
        int total = -times;
        int isNonZero = 0;

        for(int i = digits.length - 1 - times, location = digits.length - 1; i >= total; i--, location--) {
            if(i >= 0) {
                int value = digits[i] >>> internalShift;

                if(value > MASKINT) {
                    if(location != 0) {
                        digits[location] = value & MASKINT;
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
                    int finalValue = value;
                    int im1 = i - 1;
                    if(im1 >= 0) {
                        int diff = SHIFT - internalShift;
                        finalValue |= (digits[im1] & (0xFFFFFFFF >>> diff)) << diff;
                    }
                    if(location == 0) {
                        digits[location] = (int) (finalValue & MASKD0);
                    }
                    else {
                        digits[location] = finalValue & MASKINT;
                    }
                }
            }
            else {
                int ip1 = i + 1;
                if(ip1 == 0 && (digits[ip1] >>> internalShift) > MASKINT) {
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
    public BigNum30 mult2toi(long power) {

        BigNum30 result = new BigNum30(this);

        if(power <= 0) {
            return result;
        }

        int times = (int)(power / SHIFT);

        int internalShift = (int)(power % SHIFT);

        int[] digits = result.digits;
        int total = digits.length + times;
        int isNonZero = 0;

        for(int i = times, location = 0; i < total; i++, location++) {
            if(i < digits.length) {

                int value = digits[i] << internalShift;

                int im1 = i - 1;
                if(location == 0 && im1 >= 0 && internalShift == 0) {
                    value |= (digits[im1] & 0x1) << SHIFT;
                }

                int ip1 = i + 1;
                if(ip1 < digits.length) {
                    value |= digits[ip1] >>> (SHIFT - internalShift);
                }
                if(location == 0) {
                    digits[location] = (int)(value & MASKD0);
                }
                else {
                    digits[location] = value & MASKINT;
                }

            }
            else {
                int im1 = i - 1;
                if(location == 0 && im1 < digits.length && internalShift == 0) {
                    int value = (digits[im1] & 0x1) << SHIFT;
                    digits[location] = (int)(value & MASKD0);
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

    public BigNum30 divide2to30(int times) {

        BigNum30 result = new BigNum30(this);

        if(times <= 0) {
            return result;
        }

        int[] digits = result.digits;
        int total = -times;
        int isNonZero = 0;
        
        for(int i = digits.length - 1 - times, location = digits.length - 1; i >= total; i--, location--) {
            if(i >= 0) {
                int value = digits[i];

                if(value > MASKINT) {
                    digits[location] = value & MASKINT;
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
                if(ip1 == 0 && digits[ip1]  > MASKINT) {
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

    public BigNum30 shift2to30(int times) {
        if(times > 0) {
            return mult2to30(times);
        }
        else if(times < 0) {
            return divide2to30(-times);
        }

        return new BigNum30(this);
    }

    @Override
    public BigNum30 shift2toi(long power) {
        if(power > 0) {
            return mult2toi(power);
        }
        else if(power < 0) {
            return div2toi(-power);
        }

        return new BigNum30(this);
    }

    @Override
    public BigNum30 sqrt() {
        if(sign == 0 || isOne) {
            return new BigNum30(this);
        }
        if(sign < 0) {
            return new BigNum30();
        }
        BigNum30 one = new BigNum30(1);
        BigNum30 oneFourth = new BigNum30(0.25);

        BigNum30 a = new BigNum30(this);
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
            int multiplesOfTwo = numberOfLimbs >>> 1;

            if(multiplesOfTwo > 0) {
                int temp = multiplesOfTwo << 1;
                multiplications += ((long)temp * SHIFT) >>> 1;
                a = a.mult2to30(temp);
            }

            do {
                a = a.mult4();
                multiplications++;
            } while (a.compareBothPositive(oneFourth) < 0);
        }

        BigNum30 oneHalf = new BigNum30(1.5);
        BigNum30 aHalf = a.divide2(); // a / 2

        BigNum30 x = new BigNum30(1 / Math.sqrt(a.doubleValue())); // set the initial value to an approximation of 1 /sqrt(a)

        //Newton steps
        BigNum30 newX = x.mult(oneHalf.sub(aHalf.mult(x.squareFull()))); // x = (3/2 - (a/2)*x^2)*x

        BigNum30 epsilon = new BigNum30(1);
        epsilon.digits[epsilon.digits.length - 1] = 0x3;
        epsilon.digits[0] = 0;

        int iter = 0;
        while (newX.sub(x).abs_mutable().compareBothPositive(epsilon) >= 0 && iter < SQRT_MAX_ITERATIONS) {
            x = newX;
            newX = x.mult(oneHalf.sub(aHalf.mult(x.squareFull())));
            iter++;
        }

        BigNum30 sqrta = newX.mult(a); //sqrt(a) = a * (1 /sqrt(a));

        if(multiplications > 0) { //scale it up again
            sqrta = sqrta.div2toi(multiplications);
        }
        else if(divisions > 0) {
            sqrta = sqrta.mult2toi(divisions);
        }

        return sqrta;
    }
}
