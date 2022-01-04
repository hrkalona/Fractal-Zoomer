package fractalzoomer.core;

import org.apfloat.Apfloat;
import org.apfloat.internal.LongMemoryDataStorage;

import java.util.Arrays;
import java.util.stream.IntStream;

import static fractalzoomer.core.MyApfloat.TWO;
import static org.apfloat.internal.LongRadixConstants.BASE_DIGITS;

public class BigNum {
    private int[] digits = null;
    private int sign;
    private boolean isOne;
    private long scale;
    private int offset;
    private int bitOffset;
    public static int fracDigits = 2;
    public static int fracDigitsm1 = fracDigits - 1;
    public static int fracDigitsp1 = fracDigits + 1;

    public static final long MASK = 0x3FFFFFFFL;
    public static final int MASKINT = 0x3FFFFFFF;
    public static final int SHIFT = 30;
    public static final long MASKD0 = 0x7FFFFFFFL;
    public static final long MASK31 = 0x40000000L;
    public static final int SHIFTM1 = SHIFT - 1;
    public static final int SHIFTP1 = SHIFT + 1;
    public static final int SHIFTM52 = SHIFT - 52;
    public static final int MSHIFTP52 = 52 - SHIFT;

    public static int fracDigitsShift = fracDigits * SHIFT;
    public static boolean useToDouble2 = fracDigits > 120;
    public static boolean useKaratsuba = fracDigits > 30;
    public static int initialLength = (fracDigits >> 1) - ((fracDigitsp1) % 2);

    private static final double TWO_TO_SHIFT = Math.pow(2,-SHIFT);

    public static void reinitialize(double digits) {
        fracDigits = (int)((digits / SHIFT) * ThreadDraw.BIGNUM_PRECISION_FACTOR) + 1;//should be two if comparing with 64version
        fracDigitsm1 = fracDigits - 1;
        fracDigitsp1 = fracDigits + 1;
        fracDigitsShift = fracDigits * SHIFT;
        useToDouble2 = fracDigits > 120;
        useKaratsuba = fracDigits > 30;
        initialLength = (fracDigits >> 1) - ((fracDigitsp1) % 2);
    }

    public static BigNum getMax() {
        BigNum n = new BigNum();
        n.digits[0] = (int)MASKD0;
        n.sign = 1;
        return n;
    }

    public BigNum() {
        digits = new int[fracDigitsp1];
        sign = 0;
        isOne = false;
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    public BigNum(BigNum other) {
        digits = Arrays.copyOf(other.digits, other.digits.length);
        sign = other.sign;
        isOne = other.isOne;
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    public BigNum(int val) {
        digits = new int[fracDigitsp1];
        digits[0] = val;
        isOne = val == 1 || val == -1;
        sign = (int)Math.signum(val);
        scale = 0;
        offset = -1;
        bitOffset = -1;
    }

    public BigNum(double val) {

        digits = new int[fracDigitsp1];
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

    }

    public BigNum(Apfloat val) {

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
            for (int i = 0; i < SHIFTP1; i++) {
                digits[0] |= MASKD0 & (data[index] & (0x1 << i));
            }
            offset++;
        }

        int length = (data.length - offset) * base_digits;
        int length2 = fracDigitsShift;
        int dataOffset = exponent < 0 ? Math.abs(exponent) * base_digits : 0;

        for(int i = dataOffset, j = 0; i <  length2 && i < length; i++, j++) {
            int shift = base_digits - (j % base_digits) - 1;
            int bit =  (int) ((data[j / base_digits + offset] >>> shift) & 0x1);

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
        bitOffset = (int)(((Double.doubleToRawLongBits(d) >> 52)) - 1023);
        //r |= (r >> 31); //Fix for zero, not needed here

        scale = digits[0] != 0 ? ((long)i) * SHIFT + bitOffset : -(((long)i) * SHIFT) + bitOffset;

        return scale;

    }

    public static BigNum getNegative(BigNum other) {

        BigNum copy = new BigNum();
        copy.sign = other.sign;;
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

    public BigNum negate() {

        BigNum res = new BigNum(this);

        res.sign *= -1;

        return res;

    }

    public BigNum abs() {

        BigNum res = new BigNum(this);

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

    public void print() {

        Apfloat sum = new MyApfloat(0);
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

    public int compare(BigNum other) {

        int signA = sign;

        int[] otherDigits = other.digits;

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
                    int digit = digits[i];
                    int otherDigit = otherDigits[i];
                    if (digit < otherDigit) {
                        return 1;
                    } else if (digit > otherDigit) {
                        return -1;
                    }
                }
            }
            else {
                for (int i = 0; i < digits.length; i++) {
                    int digit = digits[i];
                    int otherDigit = otherDigits[i];
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

    public double doubleValue2() {

        int[] digits = this.digits;

        int i;
        int digit = 0;
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

        mantissa |= v << (52 - bitOffset);
        i++;

        int k;
        for(k = bitOffset ; k < 52 && i < digits.length; k+=SHIFT, i++) {
            long val = digits[i];
            int temp = 52 - k;

            if(temp > SHIFT) {
                mantissa |= val << (temp - SHIFT);
                mantissa = mantissa & 0xFFFFFFFFFFFFFL;
            }
            else {
                int temp2 = k + SHIFTM52;
                mantissa |= val >>> temp2;
                mantissa = mantissa & 0xFFFFFFFFFFFFFL;

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

    public BigNum add(BigNum a) {

        BigNum result = new BigNum();

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
                BigNum copy = getNegative(this);
                digits = copy.digits;
            }
            else
            {
                BigNum copy = getNegative(a);
                otherDigits = copy.digits;
            }
        }

        int s = 0;
        int isNonZero = 0;
        for(int i=fracDigits; i>0; i--) {
            s += digits[i] + otherDigits[i];
            int temp = s & MASKINT;
            isNonZero |= temp;
            resDigits[i] = temp;
            s >>>= SHIFT;
        }
        int temp = digits[0] + otherDigits[0] + s;
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
    public BigNum sub(BigNum a) {
        BigNum result = new BigNum();

        int[] otherDigits = a.digits;
        int[] resDigits = result.digits;
        long s = 0;
        for(int i=fracDigits; i>0; i--) {
            s += ((long)digits[i])-((long)otherDigits[i]); resDigits[i] = (int)(s&MASK); s >>= SHIFT;
        }
        resDigits[0] = (int)(digits[0]-otherDigits[0]+s);
        return result;
    }

    public BigNum add(BigNum a) {
        BigNum result = new BigNum();

        int[] otherDigits = a.digits;
        int[] resDigits = result.digits;
        long s = 0;
        for(int i=fracDigits; i>0; i--) {
            s += ((long)digits[i])+((long)otherDigits[i]); resDigits[i] = (int)(s&MASK); s >>>= SHIFT;
        }
        resDigits[0] = (int)(digits[0]+otherDigits[0]+s);
        return result;
    }*/

    public BigNum sub(BigNum a) {

        BigNum result = new BigNum();

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
                BigNum copy = getNegative(this);
                digits = copy.digits;
            }
            else {
                BigNum copy = getNegative(a);
                otherDigits = copy.digits;
            }
        }

        int s = 0;
        int isNonZero = 0;
        for(int i=fracDigits; i>0; i--) {
            s += digits[i] + otherDigits[i];
            int temp = s & MASKINT;
            isNonZero |= temp;
            resDigits[i] = temp;
            s >>>= SHIFT;
        }
        int temp = digits[0] + otherDigits[0] + s;
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

    public BigNum square() {
       if(offset <= 0) {
            return squareFull();
       }
       else {
           return squareWithZeroSkip();
       }
    }

    public BigNum squareWithZeroSkip() {


        BigNum result = new BigNum();

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

        long old_sum = 0;

        long sum = 0;

        int length = initialLength;

        int j;
        int k;
        long carry = 0;

        for (j = 1, k = fracDigits; j <= length; j++, k--) {
            long bj = digits[j];
            long ak = digits[k];

            sum += bj * ak;
            carry += sum >>> SHIFT;
            sum = sum & MASK;

            //System.out.println("a" + j + " * " + "a " + k);
        }

        long bj = digits[j];
        long ak = digits[k];


        long mult = bj * ak;

        //System.out.println("OUT");
        //System.out.println("a" + j + " * " + "a " + k);


//        int diff = k - j;
//        int branchlessCheck = (diff >> 31) - (-diff >> 31);

        //sum = sum << 1;
        //carry = carry << 1; //Maybe its ok?
        //carry += sum >>> SHIFT;
        //sum = sum & MASK;

        carry = carry << 1;
        //sum = (sum<< 1) + (1 - branchlessCheck) * mult + branchlessCheck * (mult << 1);

        if(j == k) {
            sum = sum << 1;
            sum += mult;
        }
        else  {
            sum += mult;
            sum = sum << 1;
        }

        carry += sum >>> SHIFT;
        sum = sum & MASK;

//

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish

        //System.out.println();
        //}

        int newJStart = offset != -1 ? offset : 0; //To avoid multiplication on zeros

        for(int i = fracDigits; i > 0; i--) {

            sum = old_sum;

            length = (i >> 1) - 1;

            long temp_sum = 0;
            carry = 0;

            for(j = newJStart, k = i - newJStart; j <= length; j++, k--) {
                bj = digits[j];
                ak = digits[k];
                temp_sum += bj * ak;
                carry += temp_sum >>> SHIFT;
                temp_sum = temp_sum & MASK;
            }


            bj = digits[j];
            ak = digits[k];

            mult = bj * ak;

            //diff = k - j;
            //branchlessCheck = (diff >> 31) - (-diff >> 31);

            //temp_sum = temp_sum << 1; //Maybe its ok
            //carry = carry << 1;
            //carry += temp_sum >>> SHIFT;
            //temp_sum = temp_sum & MASK;

            carry = carry << 1;
            //temp_sum = (temp_sum<< 1) + (1 - branchlessCheck) * mult + branchlessCheck * (mult << 1);

            if(j == k) {
                temp_sum = temp_sum << 1;
                temp_sum += mult;
            }
            else  {
                temp_sum += mult;
                temp_sum = temp_sum << 1;
            }


            //carry += temp_sum >>> SHIFT; //Maybe its ok
            //temp_sum = temp_sum & MASK;

            sum += temp_sum;
            carry += sum >>> SHIFT;
            sum = sum & MASK;


            resDigits[i] = (int) (sum);
            //System.out.println(result.digits[i] );
            old_sum = carry;
            //System.out.println("NEXT");

            if(k <= offset && carry == 0) {
                result.sign = 1;
                return  result;
            }
        }

        sum = old_sum;

        bj = digits[0];
        ak = digits[0];
        sum += bj * ak;

        resDigits[0] = (int) (sum);
        result.sign = 1;

        return  result;
    }

    public BigNum squareFull() {


        BigNum result = new BigNum();

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

        long old_sum = 0;

        long sum = 0;

        int length = initialLength;

        int j;
        int k;
        long carry = 0;

        for (j = 1, k = fracDigits; j <= length; j++, k--) {
            long bj = digits[j];
            long ak = digits[k];

            sum += bj * ak;
            carry += sum >>> SHIFT;
            sum = sum & MASK;

            //System.out.println("a" + j + " * " + "a " + k);
        }

        long bj = digits[j];
        long ak = digits[k];

        long mult = bj * ak;

        //System.out.println("OUT");
        //System.out.println("a" + j + " * " + "a " + k);


//        int diff = k - j;
//        int branchlessCheck = (diff >> 31) - (-diff >> 31);

        //sum = sum << 1;
        //carry = carry << 1; //Maybe its ok?
        //carry += sum >>> SHIFT;
        //sum = sum & MASK;

        carry = carry << 1;
        //sum = (sum<< 1) + (1 - branchlessCheck) * mult + branchlessCheck * (mult << 1);

        if(j == k) {
            sum = sum << 1;
            sum += mult;
        }
        else  {
            sum += mult;
            sum = sum << 1;
        }

        carry += sum >>> SHIFT;
        sum = sum & MASK;

//

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish

        //System.out.println();
        //}

        for(int i = fracDigits; i > 0; i--) {

            sum = old_sum;

            length = (i >> 1) - 1;

            long temp_sum = 0;
            carry = 0;
            for(j = 0, k = i; j <= length; j++, k--) {
                bj = digits[j];
                ak = digits[k];
                temp_sum += bj * ak;
                carry += temp_sum >>> SHIFT;
                temp_sum = temp_sum & MASK;
            }


            bj = digits[j];
            ak = digits[k];

            mult = bj * ak;

            //diff = k - j;
            //branchlessCheck = (diff >> 31) - (-diff >> 31);

            //temp_sum = temp_sum << 1; //Maybe its ok
            //carry = carry << 1;
            //carry += temp_sum >>> SHIFT;
            //temp_sum = temp_sum & MASK;

            carry = carry << 1;
            //temp_sum = (temp_sum<< 1) + (1 - branchlessCheck) * mult + branchlessCheck * (mult << 1);

            if(j == k) {
                temp_sum = temp_sum << 1;
                temp_sum += mult;
            }
            else  {
                temp_sum += mult;
                temp_sum = temp_sum << 1;
            }


            //carry += temp_sum >>> SHIFT; //Maybe its ok
            //temp_sum = temp_sum & MASK;

            sum += temp_sum;
            carry += sum >>> SHIFT;
            sum = sum & MASK;


            resDigits[i] = (int) (sum);
            //System.out.println(result.digits[i] );
            old_sum = carry;
            //System.out.println("NEXT");
        }

        sum = old_sum;

        bj = digits[0];
        ak = digits[0];
        sum += bj * ak;

        resDigits[0] = (int) (sum);
        result.sign = 1;

        return  result;
    }

    static IntStream revRange(int from, int to) {
        return IntStream.range(from, to)
                .map(i -> to - i + from - 1);
    }

    public BigNum squareFullWithThreads() {


        BigNum result = new BigNum();

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

        long[] carries = new long[fracDigitsp1];

        {
            int length = initialLength;

            int j;
            int k;
            long carry = 0;
            long sum = 0;

            for (j = 1, k = fracDigits; j <= length; j++, k--) {
                long bj = digits[j];
                long ak = digits[k];

                sum += bj * ak;
                carry += sum >>> SHIFT;
                sum = sum & MASK;

            }

            long bj = digits[j];
            long ak = digits[k];

            long mult = bj * ak;


            carry = carry << 1;

            if (j == k) {
                sum = sum << 1;
                sum += mult;
            } else {
                sum += mult;
                sum = sum << 1;
            }

            carry += sum >>> SHIFT;
            sum = sum & MASK;


            carries[fracDigits] = carry + (sum >>> (SHIFTM1)); // Roundish
        }

       revRange(1, fracDigitsp1).parallel().forEach(i -> {

            long sum = 0;

            int length = (i >> 1) - 1;

            long temp_sum = 0;
            long carry = 0;

            long bj, ak;
            int j, k;
            long mult;

            for(j = 0, k = i; j <= length; j++, k--) {
                bj = digits[j];
                ak = digits[k];
                temp_sum += bj * ak;
                carry += temp_sum >>> SHIFT;
                temp_sum = temp_sum & MASK;
            }


            bj = digits[j];
            ak = digits[k];

            mult = bj * ak;


            carry = carry << 1;

            if(j == k) {
                temp_sum = temp_sum << 1;
                temp_sum += mult;
            }
            else  {
                temp_sum += mult;
                temp_sum = temp_sum << 1;
            }


            sum += temp_sum;
            carry += sum >>> SHIFT;
            sum = sum & MASK;


            resDigits[i] = (int) (sum);
            carries[i - 1] = carry;
        });

        long sum = 0;
        long carry = 0;
        long old_sum = 0;
        for(int i = fracDigits; i > 0; i--) {
            sum = resDigits[i] + carries[i] + old_sum;
            carry = sum >>> SHIFT;
            sum = sum & MASK;
            resDigits[i] = (int) (sum);
            old_sum = carry;
        }

        sum = old_sum + carries[0];

        long bj = digits[0];
        long ak = digits[0];
        sum += bj * ak;

        resDigits[0] = (int) (sum);
        result.sign = 1;

        return  result;
    }

    public BigNum squareKaratsuba() {

        BigNum result = new BigNum();

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
        for(int i = 0; i < partials.length; i++) {
            long temp = digits[i];
            long p = temp * temp;

            partials[i] = p;
            PartialSum += p;

            PartialCarry += PartialSum >>> SHIFT;
            PartialSum = PartialSum & MASK;
        }

        long old_sum = 0;

        //for(int i = MAX_FRAC_DIGITS; i > 0; i--) {
        //long sum += (int)(old_sum >>> SHIFT30);

        //long sum = (old_sum >>> SHIFT31);

        long p0 = partials[0];
        long sum = p0;

        int length = initialLength;

        int j;
        int k;
        long carry = 0;
        for (j = 1, k = fracDigits; j <= length; j++, k--) {
            long bj = digits[j];
            long bk = digits[k];


            long temp = bj + bk;

            sum += temp * temp;
            carry += sum >>> SHIFT;
            sum = sum & MASK;

            //System.out.println("a" + j + " * " + "a " + k);
        }

        //System.out.println("OUT");
        //System.out.println("a" + j + " * " + "a " + k);

        long bj, bk;

        long temp = 0;
        //int diff = k - j;
        //int branchlessCheck = (diff >> 31) - (-diff >> 31);

        if(j == k) {
            sum += (partials[k] << 1);
        }
        else  {
            bj = digits[j];
            bk = digits[k];
            temp = bk + bj;
            sum += temp * temp;
        }

        carry += sum >>> SHIFT;
        sum = sum & MASK;

        sum -= PartialSum;
        carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
        sum = sum & MASK;

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish

        for(int i = fracDigits; i > 0; i--) {

            sum = old_sum;

            length = (i >> 1) - 1;

            carry = 0;
            for(j = 0, k = i; j <= length; j++, k--) {
                bj = digits[j];
                bk = digits[k];

                temp = bj + bk;

                sum += temp * temp;
                carry += sum >>> SHIFT;
                sum = sum & MASK;
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
                bj = digits[j];
                bk = digits[k];
                temp = bk + bj;
                sum += temp * temp;
            }

            carry += sum >>> SHIFT;
            sum = sum & MASK;

            sum -= PartialSum;
            carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
            sum = sum & MASK;

            long partialI = partials[i];
            PartialSum -= partialI & MASK;
            PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFT;
            //PartialCarry -= (PartialSum & MASK31)>>> SHIFT;
            //PartialCarry -= partialI >>> SHIFT;
            PartialSum = PartialSum & MASK;

            resDigits[i] = (int) (sum);
            //System.out.println(result.digits[i] );
            old_sum = carry;
            //System.out.println("NEXT");
        }

        sum = old_sum;

        sum += p0;

        resDigits[0] = (int) (sum);
        result.sign = 1;

        return  result;
    }

    public BigNum mult(BigNum b) {
        if(useKaratsuba) {
            return multKaratsuba(b);
        }
        else {
            return multFull(b);
        }
    }

    public BigNum multFull(BigNum b) {

        BigNum result = new BigNum();

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

        long old_sum = 0;

        //for(int i = MAX_FRAC_DIGITS; i > 0; i--) {

        //long sum = (old_sum >>> SHIFT31);
        //
        long sum = 0;
        long carry = 0;
        for (int j = 1, k = fracDigits; j <= fracDigits; j++, k--) {
            long bj = bdigits[j];
            long ak = digits[k];

            sum += bj * ak;
            carry += sum >>> SHIFT;
            sum = sum & MASK;

        }

        old_sum = carry + (sum >>> (SHIFTM1)); // Roundish

        //System.out.println();
        //}

        int isNonZero = 0;
        for(int i = fracDigits; i > 0; i--) {

            sum = old_sum; //carry from prev
            carry = 0;
            for(int j = 0, k = i; j <= i; j++, k--) {
                long bj = bdigits[j];
                long ak = digits[k];

                sum += bj * ak;
                carry += sum >>> SHIFT;
                sum = sum & MASK;

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

        long bj = bdigits[0];
        long ak = digits[0];

        sum += bj * ak;

        int d0 = (int) (sum);
        resDigits[0] = d0;

        result.isOne = d0 == 1 && isNonZero == 0;

        result.sign = sign * b.sign;

        return result;

    }

    public BigNum multKaratsuba(BigNum b) {

        BigNum result = new BigNum();

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
        for(int i = 0; i < partials.length; i++) {
            long p = ((long)digits[i]) * ((long)bdigits[i]);
            partials[i] = p;
            PartialSum += p;

            PartialCarry += PartialSum >>> SHIFT;
            PartialSum = PartialSum & MASK;
        }

        long old_sum = 0;

        long p0 = partials[0];

        long sum = p0;

        int length = initialLength;

        int j;
        int k;
        long carry = 0;
        for (j = 1, k = fracDigits; j <= length; j++, k--) {
            long bj = bdigits[j];
            long bk = bdigits[k];
            long aj = digits[j];
            long ak = digits[k];

            sum += (bk + bj) * (ak + aj);
            carry += sum >>> SHIFT;
            sum = sum & MASK;

            //System.out.println("partials " + k + " partials " + j);

        }

        //System.out.println("partials " + k + " partials " + j);

        //System.out.println("Next");

        //int diff = k - j;
        //int branchlessCheck = (diff >> 31) - (-diff >> 31);


        //System.out.println("partials " + k + " partials " + j);

        //System.out.println("NEXT");
        long bj, bk, aj, ak;

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
        sum = sum & MASK;

        sum -= PartialSum;
        carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
        sum = sum & MASK;

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
                sum = sum & MASK;
                //System.out.println("partials " + k + " partials " + j);

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
            sum = sum & MASK;

            sum -= PartialSum;
            carry -= ((sum & MASK31) >>> SHIFT) + PartialCarry;
            sum = sum & MASK;

            long partialI = partials[i];

            PartialSum -= partialI & MASK;
            PartialCarry -= ((PartialSum & MASK31) + partialI) >>> SHIFT;
            //PartialCarry -= ((PartialSum & MASK31) >>> SHIFT);
            PartialSum = PartialSum & MASK;
            //PartialCarry -= partialI >>> SHIFT;


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

        int d0 = (int) (sum);
        resDigits[0] = d0;

        result.sign = sign * b.sign;

        result.isOne = d0 == 1 && isNonZero == 0;

        return result;

    }

    /*mult2, mult4, divide2, divide4, multFull, multKaratsuba, square and squareKaratsuba
     might overflow or underflow leading to zero but the sign will not be set to 0
     */

    public BigNum mult2() {

        BigNum res = new BigNum();

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
        resDigits[0] = temp;
        res.sign = sign;
        res.isOne = temp == 1 && isNonZero == 0;
        return res;

    }

    public BigNum divide2() {

        BigNum res = new BigNum();

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

        int temp = digits[0];
        int val = temp >>> 1;

        int bit = temp & 0x1;

        resDigits[0] = val;
        isNonZero |= val;

        for(int i=1; i < fracDigits; i++) {
            temp = digits[i];
            temp |= (bit << SHIFT);
            bit = temp & 0x1;
            temp = temp >>> 1;
            val = temp & MASKINT;
            resDigits[i] = val;
            isNonZero |= val;
        }

        temp = digits[fracDigits];
        temp |= (bit << SHIFT);
        temp = temp >>> 1;
        resDigits[fracDigits] = temp & MASKINT;
        res.sign = sign;
        res.isOne = temp == 1 && isNonZero == 0;
        return res;

    }

    public BigNum divide4() {

        BigNum res = new BigNum();

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


        int temp = digits[0];
        int val = temp >>> 2;

        int bits = temp & 0x3;

        resDigits[0] = val;
        isNonZero |= val;

        for(int i=1; i < fracDigits; i++) {
            temp = digits[i];
            temp |= (bits << SHIFT);
            bits = temp & 0x3;
            temp = temp >>> 2;
            val = temp & MASKINT;
            resDigits[i] = val;
            isNonZero |= val;
        }

        temp = digits[fracDigits];
        temp |= (bits << SHIFT);
        temp = temp >>> 2;
        resDigits[fracDigits] = temp & MASKINT;
        res.sign = sign;
        res.isOne = temp == 1 && isNonZero == 0;
        return res;

    }

    public BigNum mult4() {

        BigNum res = new BigNum();

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
        resDigits[0] = temp;
        res.sign = sign;
        res.isOne = temp == 1 && isNonZero == 0;
        return res;

    }

    static int debruijn32[] =
            {
                    0, 9, 1, 10, 13, 21, 2, 29, 11, 14, 16, 18, 22, 25, 3, 30,
                    8, 12, 20, 28, 15, 17, 24, 7, 19, 27, 23, 6, 26, 5, 4, 31
            };

    static int msbDeBruijn32( int v )
    {


        v |= v >>> 1; // first round down to one less than a power of 2
        v |= v >>> 2;
        v |= v >>> 4;
        v |= v >>> 8;
        v |= v >>> 16;

        /* 0x07C4ACDD is a hexadecimal representation of a De Bruijn sequence for
         * binary words of length 5. The binary representation starts with
         * 0000011111. This is required to make it work with one less than a power of
         * 2 instead of an actual power of 2.
         */

        return debruijn32[( v * 0x07C4ACDD ) >>> 27];
    }

    static long binarySearch32(long v) {

        int shift = 16;

        long res = v >>> shift;

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

    private static final String ZEROS = "0000000000000000000000000000000000000000000000000000000000000000";

    public static String toHexString(int i) {
        String s = Integer.toHexString(i).toUpperCase();
        return ZEROS.substring(0,8-s.length())+s;
    }

    /*
         Too slow
     */
    public BigNum divide(BigNum b) {

        if(b.sign == 0) {
            BigNum result = getMax();
            result.sign = sign;
        }

        BigNum result = new BigNum();

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

        BigNum low = new BigNum();
        BigNum high = this.compare(b) < 0 ? new BigNum(1) : new BigNum(0x7fffffff);

        BigNum oldVal = null;

        int iterations = 0;

        while (true) {
            BigNum mid = low.add(high.sub(low).divide2());

            BigNum temp = b.mult(mid);

            BigNum val = temp.sub(this);

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
            iterations++;
        }


    }

    /*
         Too slow
     */
    public BigNum reciprocal() {

        if(sign == 0) {
            BigNum result = getMax();
            result.sign = sign;
        }

        BigNum result = new BigNum();

        if (isOne) {
            result.digits[0] = 1;
            result.isOne = true;
            result.sign = sign;
            return result;
        }

        BigNum low = new BigNum();
        BigNum high  = digits[0] != 0 ? new BigNum(1) : new BigNum(0x7fffffff);

        BigNum oldVal = null;

        BigNum one = new BigNum(1);

        while (true) {
            BigNum mid = low.add(high.sub(low).divide2());

            BigNum temp = this.mult(mid);

            BigNum val = temp.sub(one);

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

    public MantExp getMantExp() {

        if (sign == 0) {
            return new MantExp();
        }

        if(isOne) {
            return new MantExp(sign);
        }

        int[] digits = this.digits;

        if(offset == -1) {
            int i;
            int digit = 0;
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

        int i = offset;
        long mantissa = 0;

        int temp = 52 - bitOffset;
        mantissa |= ((long)digits[i]) << temp; //Will always be positive
        mantissa = mantissa & 0xFFFFFFFFFFFFFL;

        //System.out.println(digits[i]);

        i++;

        int k;
        for(k = bitOffset ; k < 52 && i < digits.length; k+=SHIFT, i++) {
            long val = digits[i];
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

        long exp = scale + (mantissa >>> 52);
        double mantissaDouble;

        if(sign == -1) {
            mantissaDouble = Double.longBitsToDouble( (0x8000000000000000L) | (mantissa & 0xFFFFFFFFFFFFFL) | (0x3FF0000000000000L));
        }
        else {
            mantissaDouble = Double.longBitsToDouble((mantissa & 0xFFFFFFFFFFFFFL) | (0x3FF0000000000000L));
        }

        return new MantExp(mantissaDouble, exp);



    }

    public static boolean isFractionalPartZero(int[] digits) {

        for(int i = 1; i < digits.length; i++) {
            if(digits[i] != 0) {
                return  false;
            }
        }

        return true;
    }

    public static BigNum max(BigNum a, BigNum b) {
        return a.compare(b) > 0 ? a : b;
    }

    public static BigNum min(BigNum a, BigNum b) {
        return a.compare(b) < 0 ? a : b;
    }
}
