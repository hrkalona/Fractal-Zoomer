package fractalzoomer.core;

import org.apfloat.Apfloat;

import java.io.Serializable;

/**
 * Immutable, extended-precision floating-point numbers
 * which maintain 106 bits (approximately 30 decimal digits) of precision.
 * <p>
 * A DoubleDouble uses a representation containing two double-precision values.
 * A number x is represented as a pair of doubles, x.hi and x.lo,
 * such that the number represented by x is x.hi + x.lo, where
 * <pre>
 *    |x.lo| <= 0.5*ulp(x.hi)
 * </pre>
 * and ulp(y) means "unit in the last place of y".
 * The basic arithmetic operations are implemented using
 * convenient properties of IEEE-754 floating-point arithmetic.
 * <p>
 * The range of values which can be represented is the same as in IEEE-754.
 * The precision of the representable numbers
 * is twice as great as IEEE-754 double precision.
 * <p>
 * The correctness of the arithmetic algorithms relies on operations
 * being performed with standard IEEE-754 double precision and rounding.
 * This is the Java standard arithmetic model, but for performance reasons
 * Java implementations are not
 * constrained to using this standard by default.
 * Some processors (notably the Intel Pentium architecure) perform
 * floating point operations in (non-IEEE-754-standard) extended-precision.
 * A JVM implementation may choose to use the non-standard extended-precision
 * as its default arithmetic mode.
 * To prevent this from happening, this code uses the
 * Java <tt>strictfp</tt> modifier,
 * which forces all operations to take place in the standard IEEE-754 rounding model.
 * <p>
 * The API provides a value-oriented interface.  DoubleDouble values are
 * immutable; operations on them return new objects carrying the result
 * of the operation.  This provides a much simpler semantics for
 * writing DoubleDouble expressions, and Java memory management is efficient enough that
 * this imposes very little performance penalty.
 * <p>
 * This implementation uses algorithms originally designed variously by Knuth, Kahan, Dekker, and
 * Linnainmaa.  Douglas Priest developed the first C implementation of these techniques.
 * Other more recent C++ implementation are due to Keith M. Briggs and David Bailey et al.
 *
 * <h3>References</h3>
 * <ul>
 * <li>Priest, D., <i>Algorithms for Arbitrary Precision Floating Point Arithmetic</i>,
 * in P. Kornerup and D. Matula, Eds., Proc. 10th Symposium on Computer Arithmetic,
 * IEEE Computer Society Press, Los Alamitos, Calif., 1991.
 * <li>Yozo Hida, Xiaoye S. Li and David H. Bailey,
 * <i>Quad-Double Arithmetic: Algorithms, Implementation, and Application</i>,
 * manuscript, Oct 2000; Lawrence Berkeley National Laboratory Report BNL-46996.
 * <li>David Bailey, <i>High Precision Software Directory</i>;
 * <tt>http://crd.lbl.gov/~dhbailey/mpdist/index.html</tt>
 * </ul>
 *
 * <hr>
 *
 * This class is based on the DoubleDouble created by Martin Davis and distributed under the following license:
 *
 * <pre>
 * Copyright (c) 2008-2012 Martin Davis. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. The name of the author may not be used to endorse or promote products derived from this software without specific
 * prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Martin Davis "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * </pre>
 *
 * @author Martin Davis
 *
 */
public strictfp class DoubleDouble
        implements Serializable, Comparable, Cloneable
{
    /**
     * The value nearest to the constant Pi.
     */
    public static final DoubleDouble PI = new DoubleDouble(
            3.141592653589793116e+00,
            1.224646799147353207e-16);

    /**
     * The value nearest to the constant 2 * Pi.
     */
    public static final DoubleDouble TWO_PI = new DoubleDouble(
            6.283185307179586232e+00,
            2.449293598294706414e-16);

    /**
     * The value nearest to the constant Pi / 2.
     */
    public static final DoubleDouble PI_2 = new DoubleDouble(
            1.570796326794896558e+00,
            6.123233995736766036e-17);

    /**
     * The value nearest to the constant e (the natural logarithm base).
     */
    public static final DoubleDouble E = new DoubleDouble(
            2.718281828459045091e+00,
            1.445646891729250158e-16);

    /**
     * A value representing the result of an operation which does not return a valid number.
     */
    public static final DoubleDouble NaN = new DoubleDouble(Double.NaN, Double.NaN);

    public static final DoubleDouble POSITIVE_INFINITY = new DoubleDouble(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

    public static final DoubleDouble NEGATIVE_INFINITY = new DoubleDouble(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

    /**
     * The smallest representable relative difference between two {link @ DoubleDouble} values
     */
    public static final double EPS = 1.23259516440783e-32;  /* = 2^-106 */

    /**
     * Converts the string argument to a DoubleDouble number.
     *
     * @param str a string containing a representation of a numeric value
     * @return the extended precision version of the value
     * @throws NumberFormatException if <tt>s</tt> is not a valid representation of a number
     */
    public static DoubleDouble valueOf(String str, boolean skipDigitsAfterANumber)
            throws NumberFormatException
    {
        return parse(str, Integer.MAX_VALUE);
    }

    /**
     * Converts the <tt>double</tt> argument to a DoubleDouble number.
     *
     * @param x a numeric value
     * @return the extended precision version of the value
     */
    public static DoubleDouble valueOf(double x) { return new DoubleDouble(x); }

    /**
     * The value to split a double-precision value on during multiplication
     */
    private static final double SPLIT = 134217729.0D; // 2^27+1, for IEEE double

    /**
     * The high-order component of the double-double precision value.
     */
    public double hi = 0.0;

    /**
     * The low-order component of the double-double precision value.
     */
    public double lo = 0.0;

    /**
     * Creates a new DoubleDouble with value 0.0.
     */
    public DoubleDouble()
    {
        init(0.0);
    }

    /**
     * Creates a new DoubleDouble with value x.
     *
     * @param x the value to initialize
     */
    public DoubleDouble(double x)
    {
        init(x);
    }

    /**
     * Creates a new DoubleDouble with value (hi, lo).
     *
     * @param hi the high-order component
     * @param lo the high-order component
     */
    public DoubleDouble(double hi, double lo)
    {
        init(hi, lo);
    }

    /**
     * Creates a new DoubleDouble with value equal to the argument.
     *
     * @param dd the value to initialize
     */
    public DoubleDouble(DoubleDouble dd)
    {
        init(dd);
    }

    /**
     * Creates a new DoubleDouble with value equal to the argument.
     *
     * @param str the value to initialize by
     * @throws NumberFormatException if <tt>str</tt> is not a valid representation of a number
     */
    public DoubleDouble(String str)
            throws NumberFormatException
    {
        this(parse(str, Integer.MAX_VALUE));
    }

    public DoubleDouble(String str, int skipDecimals)
            throws NumberFormatException
    {
        this(parse(str,skipDecimals));
    }

    public DoubleDouble(Apfloat a) {
        this(a.toString(true), 100);
    }

    /**
     * Creates and returns a copy of this value.
     *
     * @return a copy of this value
     */
    public Object clone()
    {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            // should never reach here
            return null;
        }
    }

    private void init(double x)
    {
        init(x, 0.0);
    }

    private void init(double hi, double lo)
    {
        this.hi = hi;
        this.lo = lo;
    }

    private void init(DoubleDouble dd)
    {
        init(dd.hi, dd.lo);
    }

	/*
	double getHighComponent() { return hi; }

	double getLowComponent() { return lo; }
	*/

    // Testing only - should not be public
	/*
	public void RENORM()
	{
		double s = hi + lo;
		double err = lo - (s - hi);
		hi = s;
		lo = err;
	}
	*/

    /**
     * Returns a DoubleDouble whose value is <tt>(this + y)</tt>.
     *
     * @param y the addend
     * @return <tt>(this + y)</tt>
     */
    public DoubleDouble add(DoubleDouble y)
    {
        if (isNaN()) return this;
        return (new DoubleDouble(this)).selfAdd(y);
    }

    /**
     * Adds the argument to the value of <tt>this</tt>.
     * To prevent altering constants,
     * this method <b>must only</b> be used on values known to
     * be newly created.
     *
     * @param y the addend
     * @return <tt>this</tt>, with its value incremented by <tt>y</tt>
     */
    private DoubleDouble selfAdd(DoubleDouble y)
    {
        double H, h, T, t, S, s, e, f;
        S = hi + y.hi;
        T = lo + y.lo;
        e = S - hi;
        f = T - lo;
        s = S-e;
        t = T-f;
        s = (y.hi-e)+(hi-s);
        t = (y.lo-f)+(lo-t);
        e = s+T; H = S+e; h = e+(S-H); e = t+h;

        double zhi = H + e;
        double zlo = e + (H - zhi);
        hi = zhi;
        lo = zlo;

        return this;
    }

	/*
	 // experimental
	private DoubleDouble selfAdd(double yhi, double ylo)
	{
		double H, h, T, t, S, s, e, f;
  	S = hi + yhi;
  	T = lo + ylo;
  	e = S - hi;
  	f = T - lo;
  	s = S-e;
  	t = T-f;
  	s = (yhi-e)+(hi-s);
  	t = (ylo-f)+(lo-t);
  	e = s+T; H = S+e; h = e+(S-H); e = t+h;

  	double zhi = H + e;
  	double zlo = e + (H - zhi);
  	hi = zhi;
  	lo = zlo;

  	return this;
	}
	*/

    /**
     * Returns a DoubleDouble whose value is <tt>(this - y)</tt>.
     *
     * @param y the subtrahend
     * @return <tt>(this - y)</tt>
     */
    public DoubleDouble subtract(DoubleDouble y)
    {
        if (isNaN()) return this;
        return add(y.negate());
    }

	/*
	public DoubleDouble selfSubtract(DoubleDouble y)
	{
		if (isNaN()) return this;
		return selfAdd(-y.hi, -y.lo);
	}
*/

    /**
     * Returns a DoubleDouble whose value is <tt>-this</tt>.
     *
     * @return <tt>-this</tt>
     */
    public DoubleDouble negate()
    {
        if (isNaN()) return this;
        return new DoubleDouble(-hi, -lo);
    }

    /**
     * Returns a DoubleDouble whose value is <tt>(this * y)</tt>.
     *
     * @param y the multiplicand
     * @return <tt>(this * y)</tt>
     */
    public DoubleDouble multiply(DoubleDouble y)
    {
        if (isNaN()) return this;
        if (y.isNaN()) return y;
        return (new DoubleDouble(this)).selfMultiply(y);
    }

    public DoubleDouble multiply(int y) {
        return multiply(new DoubleDouble(y));
    }

    public DoubleDouble multiply(double y) {
        return multiply(new DoubleDouble(y));
    }

    /**
     * Multiplies this by the argument, returning this.
     * To prevent altering constants,
     * this method <b>must only</b> be used on values known to
     * be newly created.
     *
     * @param y a DoubleDouble value to multiply by
     * @return this
     */
    private DoubleDouble selfMultiply(DoubleDouble y)
    {
        double hx, tx, hy, ty, C, c;
        C = SPLIT * hi; hx = C-hi; c = SPLIT * y.hi;
        hx = C-hx; tx = hi-hx; hy = c-y.hi;
        C = hi*y.hi; hy = c-hy; ty = y.hi-hy;
        c = ((((hx*hy-C)+hx*ty)+tx*hy)+tx*ty)+(hi*y.lo+lo*y.hi);
        double zhi = C+c; hx = C-zhi;
        double zlo = c+hx;
        hi = zhi;
        lo = zlo;
        return this;
    }

    /**
     * Returns a DoubleDouble whose value is <tt>(this / y)</tt>.
     *
     * @param y the divisor
     * @return <tt>(this / y)</tt>
     */
    public DoubleDouble divide(DoubleDouble y)
    {
        double hc, tc, hy, ty, C, c, U, u;
        C = hi/y.hi; c = SPLIT*C; hc =c-C;  u = SPLIT*y.hi; hc = c-hc;
        tc = C-hc; hy = u-y.hi; U = C * y.hi; hy = u-hy; ty = y.hi-hy;
        u = (((hc*hy-U)+hc*ty)+tc*hy)+tc*ty;
        c = ((((hi-U)-u)+lo)-C*y.lo)/y.hi;
        u = C+c;

        double zhi = u;
        double zlo = (C-u)+c;
        return new DoubleDouble(zhi, zlo);
    }

    public DoubleDouble divide(double y) {
        return divide(new DoubleDouble(y));
    }

    public DoubleDouble divide(int y) {
        return divide(new DoubleDouble(y));
    }

	/*

	// experimental
	public DoubleDouble selfDivide(DoubleDouble y)
	{
	  double hc, tc, hy, ty, C, c, U, u;
	  C = hi/y.hi; c = SPLIT*C; hc =c-C;  u = SPLIT*y.hi; hc = c-hc;
	  tc = C-hc; hy = u-y.hi; U = C * y.hi; hy = u-hy; ty = y.hi-hy;
	  u = (((hc*hy-U)+hc*ty)+tc*hy)+tc*ty;
	  c = ((((hi-U)-u)+lo)-C*y.lo)/y.hi;
	  u = C+c;

	  hi = u;
	  lo = (C-u)+c;
	  return this;
	}
	*/

    /**
     * Returns a DoubleDouble whose value is  <tt>1 / this</tt>.
     *
     * @return the reciprocal of this value
     */
    public DoubleDouble reciprocal()
    {
        double  hc, tc, hy, ty, C, c, U, u;
        C = 1.0/hi;
        c = SPLIT*C;
        hc =c-C;
        u = SPLIT*hi;
        hc = c-hc; tc = C-hc; hy = u-hi; U = C*hi; hy = u-hy; ty = hi-hy;
        u = (((hc*hy-U)+hc*ty)+tc*hy)+tc*ty;
        c = ((((1.0-U)-u))-C*lo)/hi;

        double  zhi = C+c;
        double  zlo = (C-zhi)+c;
        return new DoubleDouble(zhi, zlo);
    }

    /**
     * Returns the largest (closest to positive infinity)
     * value that is not greater than the argument
     * and is equal to a mathematical integer.
     * Special cases:
     * <ul>
     * <li>If this value is NaN, returns NaN.
     * </ul>
     *
     * @return the largest (closest to positive infinity)
     * value that is not greater than the argument
     * and is equal to a mathematical integer.
     */
    public DoubleDouble floor()
    {
        if (isNaN()) return NaN;
        double fhi=Math.floor(hi);
        double flo = 0.0;
        // Hi is already integral.  Floor the low word
        if (fhi == hi) {
            flo = Math.floor(lo);
        }
        // do we need to renormalize here?
        return new DoubleDouble(fhi, flo);
    }

    /**
     * Returns the smallest (closest to negative infinity) value
     * that is not less than the argument and is equal to a mathematical integer.
     * Special cases:
     * <ul>
     * <li>If this value is NaN, returns NaN.
     * </ul>
     *
     * @return the smallest (closest to negative infinity) value
     * that is not less than the argument and is equal to a mathematical integer.
     */
    public DoubleDouble ceil()
    {
        if (isNaN()) return NaN;
        double fhi=Math.ceil(hi);
        double flo = 0.0;
        // Hi is already integral.  Ceil the low word
        if (fhi == hi) {
            flo = Math.ceil(lo);
            // do we need to renormalize here?
        }
        return new DoubleDouble(fhi, flo);
    }

    /**
     * Returns an integer indicating the sign of this value.
     * <ul>
     * <li>if this value is > 0, returns 1
     * <li>if this value is < 0, returns -1
     * <li>if this value is = 0, returns 0
     * <li>if this value is NaN, returns 0
     * </ul>
     *
     * @return an integer indicating the sign of this value
     */
    public int signum()
    {
        if (isPositive()) return 1;
        if (isNegative()) return -1;
        return 0;
    }

    /**
     * Rounds this value to the nearest integer.
     * The value is rounded to an integer by adding 1/2 and taking the floor of the result.
     * Special cases:
     * <ul>
     * <li>If this value is NaN, returns NaN.
     * </ul>
     *
     * @return this value rounded to the nearest integer
     */
    public DoubleDouble rint()
    {
        if (isNaN()) return this;
        // may not be 100% correct
        DoubleDouble plus5 = this.add(new DoubleDouble(0.5));
        return plus5.floor();
    }

    /**
     * Returns the integer which is largest in absolute value and not further
     * from zero than this value.
     * Special cases:
     * <ul>
     * <li>If this value is NaN, returns NaN.
     * </ul>
     *
     * @return the integer which is largest in absolute value and not further from zero than this value
     */
    public DoubleDouble trunc()
    {
        if (isNaN()) return NaN;
        if (isPositive())
            return floor();
        else
            return ceil();
    }

    /**
     * Returns the absolute value of this value.
     * Special cases:
     * <ul>
     * <li>If this value is NaN, it is returned.
     * </ul>
     *
     * @return the absolute value of this value
     */
    public DoubleDouble abs()
    {
        if (isNaN()) return NaN;
        if (isNegative())
            return negate();
        return new DoubleDouble(this);
    }

    /**
     * Computes the square of this value.
     *
     * @return the square of this value.
     */
    public DoubleDouble sqr()
    {
        return this.multiply(this);
    }

    /**
     * Computes the positive square root of this value.
     * If the number is NaN or negative, NaN is returned.
     *
     * @return the positive square root of this number.
     * If the argument is NaN or less than zero, the result is NaN.
     */
    public DoubleDouble sqrt()
    {
	  /* Strategy:  Use Karp's trick:  if x is an approximation
    to sqrt(a), then

       sqrt(a) = a*x + [a - (a*x)^2] * x / 2   (approx)

    The approximation is accurate to twice the accuracy of x.
    Also, the multiplication (a*x) and [-]*x can be done with
    only half the precision.
 */

        if (isZero())
            return new DoubleDouble(0.0);

        if (isNegative()) {
            return NaN;
        }

        double x = 1.0 / Math.sqrt(hi);
        double ax = hi * x;

        DoubleDouble axdd = new DoubleDouble(ax);
        DoubleDouble diffSq = this.subtract(axdd.sqr());
        double d2 = diffSq.hi * (x * 0.5);

        return axdd.add(new DoubleDouble(d2));
    }

    /**
     * For all real, x exp(x) = 1 + x + x**2/2! + x**3/3! + x**4/4! + ...
     * @return
     */
    public DoubleDouble exp() {
        boolean invert = false;
        // Return the exponential of a DoubleDouble number
        if (isNaN()) {
            return NaN;
        }
        DoubleDouble x = this;
        int intX = x.intValue();
        DoubleDouble baseVal = DoubleDouble.valueOf(1.0);
        if (intX >= 1) {
            for (int i = 1; i <= intX; i++) {
                baseVal = baseVal.multiply(E);
            }
        }
        else if (intX <= -1) {
            for (int i = 1; i <= Math.abs(intX); i++) {
                baseVal = baseVal.divide(E);
            }
        }
        DoubleDouble fractionX = x.subtract(DoubleDouble.valueOf(intX));
        if (x.lt(DoubleDouble.valueOf(0.0))) {
            // Much greater precision if all numbers in the series have the same sign.
            fractionX = fractionX.negate();
            invert = true;
        }
        DoubleDouble s = (DoubleDouble.valueOf(1.0)).add(fractionX);
        DoubleDouble sOld = (DoubleDouble)s.clone();
        DoubleDouble t = new DoubleDouble(fractionX);
        double n = 1.0;

        int iters = 0;
        do {
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            t = t.multiply(fractionX);
            sOld = s;
            s = s.add(t);
            iters++;
        } while (s.ne(sOld) && iters < MAX_ITER);
        if (invert) {
            s = s.reciprocal();
        }
        s = s.multiply(baseVal);
        return s;

    }

    public static final int MAX_ITER = 5000;

    /**
     * For x > 0, ratio = (x-1)/(x+1), log(x) = 2(ratio + ratio**3/3 + ratio**5/5 + ...)
     * @return
     */
    public DoubleDouble log() {
        // Return the natural log of a DoubleDouble number
        if (isNaN()) {
            return NaN;
        }
        if (isZero()) {
            return NEGATIVE_INFINITY;
        }

        if (isNegative()) {
            return NaN;
        }

        DoubleDouble number = this;
        int intPart = 0;
        while (number.gt(E)) {
            number = number.divide(E);
            intPart++;
        }
        while (number.lt(E.reciprocal())) {
            number = number.multiply(E);
            intPart--;
        }

        DoubleDouble num = number.subtract(DoubleDouble.valueOf(1.0));
        DoubleDouble denom = number.add(DoubleDouble.valueOf(1.0));
        DoubleDouble ratio = num.divide(denom);
        DoubleDouble ratioSquare = ratio.multiply(ratio);
        DoubleDouble s = DoubleDouble.valueOf(2.0).multiply(ratio);
        DoubleDouble sOld = (DoubleDouble)s.clone();
        DoubleDouble t = (DoubleDouble)s.clone();
        DoubleDouble w = (DoubleDouble)s.clone();
        double n = 1.0;

        int iters = 0;
        do {
            n += 2.0;
            t = t.multiply(ratioSquare);
            w = t.divide(DoubleDouble.valueOf(n));
            sOld = s;
            s = s.add(w);
            iters++;
        } while (s.ne(sOld) && iters < MAX_ITER);
        return s.add(DoubleDouble.valueOf(intPart));
    }

    /**
     * For all real x, sinh(x) = x + x**3/3! + x**5/5! + x**7/7! + ... + x**(2n+1)/(2n+1)! + ...
     * @return
     */
    public DoubleDouble sinh() {
        // Return the sinh of a DoubleDouble number
        if (isNaN())  {
            return NaN;
        }
        DoubleDouble square = this.multiply(this);
        DoubleDouble s = new DoubleDouble(this);
        DoubleDouble sOld = (DoubleDouble)s.clone();
        DoubleDouble t = new DoubleDouble(this);
        double n = 1.0;
        int iters = 0;
        do {
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            t = t.multiply(square);
            sOld = s;
            s = s.add(t);
            iters++;
        } while (s.ne(sOld) && iters < MAX_ITER);
        return s;
    }

    /**
     * For all real x, cosh(x) = 1 + x**2/2! + x**4/4! + x**6/6! + ... + x**(2*n)/((2*n)!) + ...
     * @return
     */
    public DoubleDouble cosh() {
        // Return the cosh of a DoubleDouble number
        if (isNaN()) {
            return NaN;
        }
        DoubleDouble square = this.multiply(this);
        DoubleDouble s = DoubleDouble.valueOf(1.0);
        DoubleDouble sOld = (DoubleDouble)s.clone();
        DoubleDouble t = DoubleDouble.valueOf(1.0);
        double n = 0.0;
        int iters = 0 ;
        do {
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            t = t.multiply(square);
            sOld = s;
            s = s.add(t);
            iters++;
        } while (s.ne(sOld) && iters < MAX_ITER);
        return s;
    }

    /**
     * For all real x, sin(x) = x - x**3/3! + x**5/5! - x**7/7! + ...
     * @return
     */
    public DoubleDouble sin() {
        boolean negate = false;
        // Return the sine of a DoubleDouble number
        if (isNaN())  {
            return NaN;
        }
        DoubleDouble TWO_PIFullTimes;
        DoubleDouble TWO_PIremainder;
        if ((this.abs()).gt(TWO_PI)) {
            TWO_PIFullTimes = (this.divide(TWO_PI)).trunc();
            TWO_PIremainder = this.subtract(TWO_PI.multiply(TWO_PIFullTimes));
        }
        else {
            TWO_PIremainder = this;
        }
        if (TWO_PIremainder.gt(PI)) {
            TWO_PIremainder = TWO_PIremainder.subtract(PI);
            negate = true;
        }
        else if (TWO_PIremainder.lt(PI.negate())) {
            TWO_PIremainder = TWO_PIremainder.add(PI);
            negate = true;
        }
        DoubleDouble msquare = (TWO_PIremainder.multiply(TWO_PIremainder)).negate();
        DoubleDouble s = new DoubleDouble(TWO_PIremainder);
        DoubleDouble sOld = (DoubleDouble)s.clone();
        DoubleDouble t = new DoubleDouble(TWO_PIremainder);
        double n = 1.0;
        int iters = 0;
        do {
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            t = t.multiply(msquare);
            sOld = s;
            s = s.add(t);
            iters++;
        } while (s.ne(sOld) && iters < MAX_ITER);
        if (negate) {
            s = s.negate();
        }
        return s;
    }

    public DoubleDouble erf() {
        if (isNaN()) {
            return NaN;
        }
        DoubleDouble coefficient = (DoubleDouble.valueOf(2.0)).divide(PI.sqrt());
        DoubleDouble x = this;
        DoubleDouble x2 = x.multiply(x);
        DoubleDouble oldSum = x;
        DoubleDouble sum = x;
        DoubleDouble num = DoubleDouble.valueOf(1.0);
        DoubleDouble denom = DoubleDouble.valueOf(1.0);
        DoubleDouble xPow = x;
        DoubleDouble term;
        int n = 0;
        do {
            num = num.negate();
            denom.divide(DoubleDouble.valueOf(2*n+1));
            n = n + 1;
            denom = denom.multiply((DoubleDouble.valueOf(n)));
            denom = denom.multiply(DoubleDouble.valueOf(2*n+1));
            xPow = xPow.multiply(x2);
            term = num.divide(denom);
            term = term.multiply(xPow);
            oldSum = sum;
            sum = sum.add(term);
        } while(sum.ne(oldSum));
        DoubleDouble ans = coefficient.multiply(sum);
        return ans;
    }

    /**
     * For all real x, cos(x) = 1 - x**2/2! + x**4/4! - x**6/6! + ...
     * @return
     */
    public DoubleDouble cos() {
        boolean negate = false;
        // Return the cosine of a DoubleDouble number
        if (isNaN()) {
            return NaN;
        }
        DoubleDouble TWO_PIFullTimes;
        DoubleDouble TWO_PIremainder;
        if ((this.abs()).gt(TWO_PI)) {
            TWO_PIFullTimes = (this.divide(TWO_PI)).trunc();
            TWO_PIremainder = this.subtract(TWO_PI.multiply(TWO_PIFullTimes));
        }
        else {
            TWO_PIremainder = this;
        }
        if (TWO_PIremainder.gt(PI)) {
            TWO_PIremainder = TWO_PIremainder.subtract(PI);
            negate = true;
        }
        else if (TWO_PIremainder.lt(PI.negate())) {
            TWO_PIremainder = TWO_PIremainder.add(PI);
            negate = true;
        }
        DoubleDouble msquare = (TWO_PIremainder.multiply(TWO_PIremainder)).negate();
        DoubleDouble s = DoubleDouble.valueOf(1.0);
        DoubleDouble sOld = (DoubleDouble)s.clone();
        DoubleDouble t = DoubleDouble.valueOf(1.0);
        double n = 0.0;
        int iters = 0;
        do {
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            t = t.multiply(msquare);
            sOld = s;
            s = s.add(t);
            iters++;
        } while (s.ne(sOld) && iters < MAX_ITER);
        if (negate) {
            s = s.negate();
        }
        return s;
    }

    /**
     * For -PI/2 < x < PI/2, tan(x) = x + (x**3)/3 + 2*(x**5)/15 + 17*(x**7)/315 + 62*(x**9)/2835 + ... +
     *                                (2**(2*n))*((2**(2*n)) - 1)*Bn*(x**(2*n-1))/((2*n)!) + ...
     * @return
     */
    public DoubleDouble tan() {
        // Return the tangent of a DoubleDouble number
        if (isNaN()) {
            return NaN;
        }
        DoubleDouble PIFullTimes;
        DoubleDouble PIremainder;
        if ((this.abs()).gt(PI)) {
            PIFullTimes = (this.divide(PI)).trunc();
            PIremainder = this.subtract(PI.multiply(PIFullTimes));
        }
        else {
            PIremainder = this;
        }
        if (PIremainder.gt(PI_2)) {
            PIremainder = PIremainder.subtract(PI);
        }
        else if (PIremainder.lt(PI_2.negate())) {
            PIremainder = PIremainder.add(PI);
        }
        if (PIremainder.equals(PI_2)) {
            return POSITIVE_INFINITY;
        }
        else if (PIremainder.equals(PI_2.negate())) {
            return NEGATIVE_INFINITY;
        }
        int twon;
        DoubleDouble twotwon;
        DoubleDouble twotwonm1;
        DoubleDouble square = PIremainder.multiply(PIremainder);
        DoubleDouble s = new DoubleDouble(PIremainder);
        DoubleDouble sOld = (DoubleDouble)s.clone();
        DoubleDouble t = new DoubleDouble(PIremainder);
        int n = 1;
        int iters = 0;
        do {
            n++;
            twon = 2*n;
            t = t.divide(factorial(twon));
            twotwon = (DoubleDouble.valueOf(2.0)).pow(twon);
            twotwonm1 = twotwon.subtract(DoubleDouble.valueOf(1.0));
            t = t.multiply(twotwon);
            t = t.multiply(twotwonm1);
            t = t.multiply(BernoulliB(n));
            t = t.multiply(square);
            sOld = s;
            s = s.add(t);
            iters++;
        } while (s.ne(sOld) && iters < MAX_ITER);
        return s;
    }

    /**
     * For all -1 < x < 1, arcsin(x) = x + x**3/(2*3) + (1 * 3 * x**5)/(2 * 4 * 5) + (1 * 3 * 5 * x**7)/(2 * 4 * 6 * 7) + ...
     * @return
     */
    public DoubleDouble asin() {
        // Return the arcsine of a DoubleDouble number
        if (isNaN()) {
            return NaN;
        }
        if ((this.abs()).gt(DoubleDouble.valueOf(1.0))) {
            return NaN;
        }
        if (this.equals(DoubleDouble.valueOf(1.0))) {
            return PI_2;
        }
        if (this.equals(DoubleDouble.valueOf(-1.0))) {
            return PI_2.negate();
        }
        DoubleDouble square = this.multiply(this);
        DoubleDouble s = new DoubleDouble(this);
        DoubleDouble sOld = (DoubleDouble)s.clone();
        DoubleDouble t = new DoubleDouble(this);
        DoubleDouble w = new DoubleDouble(this);
        double n = 1.0;
        double numn = 1.0;
        double denomn = 1.0;
        int iters = 0;
        do {
            n += 2.0;
            numn = (n - 2.0);
            denomn = (n - 1.0);
            t = t.divide(DoubleDouble.valueOf(denomn));
            t = t.multiply(DoubleDouble.valueOf(numn));
            t = t.multiply(square);
            w = t.divide(DoubleDouble.valueOf(n));
            sOld = s;
            s = s.add(w);
            iters++;
        } while (s.ne(sOld) && iters < MAX_ITER);
        return s;
    }

    /**
     * For all -1 < x < 1, arccos(x) = PI/2 - arcsin(x)
     * @return
     */
    public DoubleDouble acos() {
        if (isNaN()) {
            return NaN;
        }
        if ((this.abs()).gt(DoubleDouble.valueOf(1.0))) {
            return NaN;
        }
        DoubleDouble s = PI_2.subtract(this.asin());
        return s;
    }

    /**
     * The atan of this(the imaginary y component) divided by the real component x
     * Value will range from -PI to PI.
     * Strays a bit from actual atan2 definition which recognizes positive zero
     * and negative zero.  Here both positive zero and negative zero are combined as zero.
     * @param x
     * @return
     */
    public DoubleDouble atan2(DoubleDouble x) {
        if (isNaN() || x.isNaN()) {
            return NaN;
        }
        else if ((this.isZero() && x.isPositive()) || (this.isPositiveFinite() && x.isPositiveInfinity()) ||
                (this.isNegativeFinite() && x.isPositiveInfinity())) {
            return DoubleDouble.valueOf(0.0);
        }
        else if ((this.isZero() && x.isNegative()) || (this.isPositiveFinite() && x.isNegativeInfinity())) {
            return PI;
        }
        else if (this.isNegativeFinite() && x.isNegativeInfinity()) {
            return PI.negate();
        }
        else if ((this.isPositive() && x.isZero()) || (this.isPositiveInfinity() && (!x.isInfinite()))) {
            return PI_2;
        }
        else if ((this.isNegative() && x.isZero()) || (this.isNegativeInfinity() && (!x.isInfinite()))) {
            return PI_2.negate();
        }
        else if (this.isPositiveInfinity() && x.isPositiveInfinity()) {
            return PI_2.divide(DoubleDouble.valueOf(2.0));
        }
        else if (this.isPositiveInfinity() && x.isNegativeInfinity()) {
            return PI.multiply(DoubleDouble.valueOf(0.75));
        }
        else if (this.isNegativeInfinity() && x.isPositiveInfinity()) {
            return PI_2.divide(DoubleDouble.valueOf(-2.0));
        }
        else if (this.isNegativeInfinity() && x.isNegativeInfinity()) {
            return PI.multiply(DoubleDouble.valueOf(-0.75));
        }
        DoubleDouble val = this.divide(x);
       // System.out.println("val = " + val);
        DoubleDouble valatan = val.atan();
        if (x.isPositive()) {
            return valatan;
        }
        else if (x.isNegative() && (this.isZero() || this.isPositive())) {
            return valatan.add(PI);
        }
        else if (x.isNegative() && this.isNegative()) {
            return valatan.subtract(PI);
        }
        else {
            // This should never happen
            return NaN;
        }
    }

    /**
     * For -1 < x < 1, arctan(x) = x - x**3/3 + x**5/5 - x**7/7 + ...
     * For x > 1, arctan(x) = PI/2 - 1/x + 1/(3*x**3) - 1/(5*x**5) +1/(7*x**7) - ...
     * * For x < -1, arctan(x) = -PI/2 - 1/x + 1/(3*x**3) - 1/(5*x**5) +1/(7*x**7) - ...
     * @return
     */
    public DoubleDouble atan() {
        if (isNaN()) {
            return NaN;
        }
        DoubleDouble s;
        DoubleDouble sOld;
        if (this.equals(DoubleDouble.valueOf(1.0))) {
            s = PI_2.divide(DoubleDouble.valueOf(2.0));
        }
        else if ((this.subtract(DoubleDouble.valueOf(-1.0))).abs().lt(DoubleDouble.valueOf(1.0E-7))) {
            s = PI_2.divide(DoubleDouble.valueOf(-2.0));
        }
        else if (this.abs().lt(DoubleDouble.valueOf(1.0))) {
            DoubleDouble msquare = (this.multiply(this)).negate();
            s = new DoubleDouble(this);
            sOld = (DoubleDouble)s.clone();
            DoubleDouble t = new DoubleDouble(this);
            DoubleDouble w = new DoubleDouble(this);

            int iter = 0;
            double n = 1.0;
            do {
                n += 2.0;
                t = t.multiply(msquare);
                w = t.divide(DoubleDouble.valueOf(n));
                sOld = s;
                s = s.add(w);
                iter++;
            } while (s.ne(sOld) && iter < MAX_ITER);
        }
        else {
            DoubleDouble msquare = (this.multiply(this)).negate();
            s = this.reciprocal().negate();
            sOld = (DoubleDouble)s.clone();
            DoubleDouble t = (DoubleDouble)s.clone();
            DoubleDouble w = (DoubleDouble)s.clone();
            double n = 1.0;
            int iter = 0;
            do {
                n += 2.0;
                t = t.divide(msquare);
                w = t.divide(DoubleDouble.valueOf(n));
                sOld = s;
                s = s.add(w);
                iter++;
            } while (s.ne(sOld) && iter < MAX_ITER);
            if (isPositive()) {
                s = s.add(PI_2);
            }
            else {
                s = s.subtract(PI_2);
            }
        }
        return s;
    }

    public DoubleDouble BernoulliA(int n) {
        // For PI/2 < x < PI, sum from k = 1 to infinity of ((-1)**(k-1))*sin(kx)/(k**2) =
        // x*ln(2) - sum from k = 1 to infinity of
        // ((-1)**(k-1))*(2**(2k) - 1)*B2k*(x**(2*k+1))/(((2*k)!)*(2*k)*(2*k+1))
        // Compute the DoubleDouble Bernoulli number Bn
        // Ported from subroutine BERNOA in
        // Computation of Special Functions by Shanjie Zhang and Jianming Jin
        // I thought of creating a version with all the Bernoulli numbers from
        // B0 to Bn-1 passed in as an input to calculate Bn.  However, according
        // Zhang and Jin using the correct zero values for B3, B5, B7 actually gives
        // a much worse result than using the incorrect intermediate B3, B5, B7
        // values caluclated by this algorithm.
        int m;
        int k;
        int j;
        DoubleDouble s;
        DoubleDouble r;
        DoubleDouble temp;
        if (n < 0) {
            return NaN;
        }
        else if ((n >= 3) && (((n - 1) % 2) == 0)) {
            // B2*n+1 = 0 for n = 1,2,3
            return DoubleDouble.valueOf(0.0);
        }
        DoubleDouble BN[] = new DoubleDouble[n+1];
        BN[0] = DoubleDouble.valueOf(1.0);
        if (n == 0) {
            return BN[0];
        }
        BN[1] = DoubleDouble.valueOf(-0.5);
        if (n == 1) {
            return BN[1];
        }
        for (m = 2; m <= n; m++) {
            s = (DoubleDouble.valueOf(m)).add(DoubleDouble.valueOf(1.0));
            s = s.reciprocal();
            s = (DoubleDouble.valueOf(0.5)).subtract(s);
            for (k = 2; k <= m-1; k++) {
                r = DoubleDouble.valueOf(1.0);
                for (j = 2; j <= k; j++) {
                    temp = (DoubleDouble.valueOf(j)).add(DoubleDouble.valueOf(m));
                    temp = temp.subtract(DoubleDouble.valueOf(k));
                    temp = temp.divide(DoubleDouble.valueOf(j));
                    r = r.multiply(temp);
                } // for (j = 2; j <= k; j++)
                temp = r.multiply(BN[k]);
                s = s.subtract(temp);
            } // for (k = 2; k <= m-1; k++)
            BN[m] = s;
        } // for (m = 2; m <= n; m++)
        return BN[n];
    }

    public DoubleDouble BernoulliB(int n) {
        // B2n = ((-1)**(n-1))*2*((2*n)!)*(1 + 1/(2**(2*n)) + 1/(3**(2*n)) + ...)/((2*PI)**(2*n))
        // = ((-1)**(n-1))*2*(1 + 1/(2**(2*n)) + 1/(3**(2*n)) + ...) * product from m = 1 to 2n of m/(2*PI)
        // for n = 1, 2, 3, ...
        // Compute the DoubleDouble Bernoulli number Bn
        // More efficient than BernoulliA
        // Ported from subroutine BERNOB in
        // Computation of Special Functions by Shanjie Zhang and Jianming Jin
        int m;
        int k;
        DoubleDouble r1;
        DoubleDouble twoPISqr;
        DoubleDouble r2;
        DoubleDouble s;
        DoubleDouble sOld;
        DoubleDouble temp;
        if (n < 0) {
            return NaN;
        }
        else if ((n >= 3) && (((n - 1) % 2) == 0)) {
            // B2*n+1 = 0 for n = 1,2,3
            return DoubleDouble.valueOf(0.0);
        }
        DoubleDouble BN[] = new DoubleDouble[n+1];
        BN[0] = DoubleDouble.valueOf(1.0);
        if (n == 0) {
            return BN[0];
        }
        BN[1] = DoubleDouble.valueOf(-0.5);
        if (n == 1) {
            return BN[1];
        }
        BN[2] = (DoubleDouble.valueOf(1.0)).divide(DoubleDouble.valueOf(6.0));
        if (n == 2) {
            return BN[2];
        }
        r1 = ((DoubleDouble.valueOf(1.0)).divide(PI)).sqr();
        twoPISqr = TWO_PI.multiply(TWO_PI);
        for (m = 4; m <= n; m+=2) {
            temp = (DoubleDouble.valueOf(m)).divide(twoPISqr);
            temp = (DoubleDouble.valueOf(m-1)).multiply(temp);
            r1 = (r1.multiply(temp)).negate();
            r2 = DoubleDouble.valueOf(1.0);
            s = DoubleDouble.valueOf(1.0);
            sOld = (DoubleDouble)s.clone();
            k = 2;
            do {
                sOld = s;
                s = (DoubleDouble.valueOf(1.0)).divide(valueOf(k++));
                s = s.pow(m);
                r2 = r2.add(s);
            } while (s.ne(sOld));
            BN[m] = r1.multiply(r2);
        } // for (m = 4; m <= n; m+=2)
        return BN[n];
    }

    public DoubleDouble Ci() {
        DoubleDouble x = new DoubleDouble(this);
        DoubleDouble Ci = DoubleDouble.valueOf(0.0);
        DoubleDouble Si = DoubleDouble.valueOf(0.0);
        cisia(x, Ci, Si);
        return Ci;
    }

    public DoubleDouble Si() {
        DoubleDouble x = new DoubleDouble(this);
        DoubleDouble Ci = DoubleDouble.valueOf(0.0);
        DoubleDouble Si = DoubleDouble.valueOf(0.0);
        cisia(x, Ci, Si);
        return Si;
    }

    /**
     * This is a port of subroutine CISIA which computes cosine and sine integrals from
     * Computation of Special Functions by Shanjie Zhang and Jianming Jin.
     * Waiting for Professor Jin's reply.
     * Dear Professor Jianming Jin:

     There is an error in subroutine CISIA in Computation of Special Functions.  Under ELSE IF (x .LE. 32.0D0) THEN in the DO 25 K =M,1,-1 loop the values of BJ(1) thru BJ(M) are set.  You then have the loop
     DO 30 K=2,M,2
     30 XS=XS+2.0D0*BJ(K+1)
     so for M even the value of BJ(M+1) will be used, but the value of BJ(M+1) has not been set.

     Sincerely,

     William Gandler

     *
     */
    public void cisia(DoubleDouble x, DoubleDouble Ci, DoubleDouble Si) {
        // Purpose: Compute cosine and sine integrals Si(x) and
        // Ci(x) (x >= 0)
        // Input x: Argument of Ci(x) and Si(x)
        // Output: Ci(x), Si(x)
        DoubleDouble bj[] = new DoubleDouble[101];
        // Euler's constant
        DoubleDouble el = DoubleDouble.valueOf(.57721566490153286060651209008240243104215933593992);
        DoubleDouble x2;
        DoubleDouble xr;
        int k;
        int m;
        DoubleDouble xa1;
        DoubleDouble xa0;
        DoubleDouble xa;
        DoubleDouble xs;
        DoubleDouble xg1;
        DoubleDouble xg2;
        DoubleDouble xcs;
        DoubleDouble xss;
        DoubleDouble xf;
        DoubleDouble xg;
        int i1;
        int i2;
        DoubleDouble var1;
        DoubleDouble var2;

        x2 = x.multiply(x);
        if (x.isZero()) {
            Ci = DoubleDouble.valueOf(-1.0E300);
            Si = DoubleDouble.valueOf(0.0);
        }
        else if (x.le(DoubleDouble.valueOf(16.0))) {
            xr = (DoubleDouble.valueOf(-0.25)).multiply(x2);
            Ci = (el.add(x.log())).add(xr);
            for (k = 2; k <= 40; k++) {
                xr = ((((DoubleDouble.valueOf(-0.5)).multiply(xr)).multiply(DoubleDouble.valueOf((double)(k - 1)))).
                        divide(DoubleDouble.valueOf((double)(k*k*(2*k-1))))).multiply(x2);
                Ci = Ci.add(xr);
                if ((xr.abs()).lt((Ci.abs()).multiply(DoubleDouble.valueOf(DoubleDouble.EPS)))) {
                    break;
                }
            } // for (k = 2; k <= 40; k++)
            xr = (DoubleDouble)x.clone();
            Si = (DoubleDouble)x.clone();
            for (k = 1; k <= 40; k++) {
                xr = (((((DoubleDouble.valueOf(-0.5)).multiply(xr)).multiply(DoubleDouble.valueOf((double)(2*k - 1)))).
                        divide(DoubleDouble.valueOf((double)k))).divide(DoubleDouble.valueOf((double)(4*k*k+4*k+1)))).multiply(x2);
                Si = Si.add(xr);
                if ((xr.abs()).lt((Si.abs()).multiply(DoubleDouble.valueOf(DoubleDouble.EPS)))) {
                    return;
                }
            } // for (k = 1; k <= 40; k++)

        } // else if x <= 16
        else if (x.le(DoubleDouble.valueOf(32.0))) {
            m = (((DoubleDouble.valueOf(47.2)).add((DoubleDouble.valueOf(0.82)).multiply(x))).trunc()).intValue();
            xa1 = DoubleDouble.valueOf(0.0);
            xa0 = DoubleDouble.valueOf(1.0E-100);
            for (k = m; k >= 1; k--) {
                xa = ((((DoubleDouble.valueOf(4.0)).multiply(DoubleDouble.valueOf((double)k))).multiply(xa0)).divide(x)).subtract(xa1);
                bj[k-1] = (DoubleDouble)xa.clone();
                xa1 = (DoubleDouble)xa0.clone();
                xa0 = (DoubleDouble)xa.clone();
            } // for (k = m; k >= 1; k--)
            xs = (DoubleDouble)bj[0].clone();
            for (k = 2; k <= m; k += 2) {
                xs = xs.add((DoubleDouble.valueOf(2.0)).multiply(bj[k]));
            }
            bj[0] = bj[0].divide(xs);
            for (k = 1; k <= m; k++) {
                bj[k] = bj[k].divide(xs);
            }
            xr = DoubleDouble.valueOf(1.0);
            xg1 = (DoubleDouble)bj[0].clone();
            for (k = 1; k <= m; k++) {
                i1 = (2*k-1)*(2*k-1);
                var1 = DoubleDouble.valueOf((double)i1);
                i2 = k*(2*k+1)*(2*k+1);
                var2 = DoubleDouble.valueOf((double)i2);
                xr = ((((DoubleDouble.valueOf(0.25)).multiply(xr)).multiply(var1)).divide(var2)).multiply(x);
                xg1 = xg1.add(bj[k].multiply(xr));
            }
            xr = DoubleDouble.valueOf(1.0);
            xg2 = (DoubleDouble)bj[0].clone();
            for (k = 1; k <= m; k++) {
                i1 = (2*k-3)*(2*k-3);
                var1 = DoubleDouble.valueOf((double)i1);
                i2 = k*(2*k-1)*(2*k-1);
                var2 = DoubleDouble.valueOf((double)i2);
                xr = ((((DoubleDouble.valueOf(0.25)).multiply(xr)).multiply(var1)).divide(var2)).multiply(x);
                xg2 = xg2.add(bj[k].multiply(xr));
            }
            xcs = ((DoubleDouble.valueOf(0.5)).multiply(x)).cos();
            xss = ((DoubleDouble.valueOf(0.5)).multiply(x)).sin();
            Ci = (((el.add(x.log())).subtract((x.multiply(xss)).multiply(xg1))).add(((DoubleDouble.valueOf(2.0)).multiply(xcs)).multiply(xg2))).
                    subtract(((DoubleDouble.valueOf(2.0)).multiply(xcs)).multiply(xcs));
            Si = (((x.multiply(xcs)).multiply(xg1)).add(((DoubleDouble.valueOf(2.0)).multiply(xss)).multiply(xg2))).subtract(x.sin());
        } // else if x <= 32
        else {
            xr = DoubleDouble.valueOf(1.0);
            xf = DoubleDouble.valueOf(1.0);
            for (k = 1; k <= 9; k++) {
                i1 = k*(2*k-1);
                var1 = DoubleDouble.valueOf((double)i1);
                xr = (((DoubleDouble.valueOf(-2.0)).multiply(xr)).multiply(var1)).divide(x2);
                xf = xf.add(xr);
            }
            xr = x.reciprocal();
            xg = (DoubleDouble)xr.clone();
            for (k = 1; k <= 8; k++) {
                i1 = (2*k+1)*k;
                var1 = DoubleDouble.valueOf((double)i1);
                xr = (((DoubleDouble.valueOf(-2.0)).multiply(xr)).multiply(var1)).divide(x2);
                xg = xg.add(xr);
            }
            Ci = ((xf.multiply(x.sin())).divide(x)).subtract((xg.multiply(x.cos())).divide(x));
            Si = (DoubleDouble.PI_2.subtract((xf.multiply(x.cos())).divide(x))).subtract((xg.multiply(x.sin())).divide(x));
        }
        return;
    }

    /**
     *
     * @param fac
     * @return
     */

    public DoubleDouble factorial(int fac) {
        DoubleDouble prod;
        if (fac < 0) {
            return NaN;
        }
        if ((fac >= 0) && (fac <= 1)) {
            return DoubleDouble.valueOf(1.0);
        }
        prod = DoubleDouble.valueOf(fac--);
        while (fac > 1) {
            prod = prod.multiply(DoubleDouble.valueOf(fac--));
        }
        return prod;
    }

    /**
     * Computes the value of this number raised to an integral power.
     * Follows semantics of Java Math.pow as closely as possible.
     *
     * @param exp the integer exponent
     * @return x raised to the integral power exp
     */
    public DoubleDouble pow(int exp)
    {
        if (exp == 0.0)
            return valueOf(1.0);

        DoubleDouble r = new DoubleDouble(this);
        DoubleDouble s = valueOf(1.0);
        int n = Math.abs(exp);

        if (n > 1) {
            /* Use binary exponentiation */
            while (n > 0) {
                if (n % 2 == 1) {
                    s.selfMultiply(r);
                }
                n /= 2;
                if (n > 0)
                    r = r.sqr();
            }
        } else {
            s = r;
        }

        /* Compute the reciprocal if n is negative. */
        if (exp < 0)
            return s.reciprocal();
        return s;
    }

    /**
     *
     * @param x the double exponent
     * @return a raised to the double power x
     * For a > 0, base = x * log(a), a**x = 1 + base + base**2/2! + base**3/3! + ...
     */
    public DoubleDouble pow(double x) {
        boolean invert = false;
        if (Double.isNaN(x)) {
            return NaN;
        }
        if (Double.isInfinite(x)) {
            return NaN;
        }
        if (isNaN()) {
            return NaN;
        }
        if (x == 0.0) {
            return valueOf(1.0);
        }

        if (isZero()) {
            return NaN;
        }

        if (isNegative()) {
            return NaN;
        }
        DoubleDouble loga = this.log();
        DoubleDouble base = DoubleDouble.valueOf(x).multiply(loga);
        if (base.lt(DoubleDouble.valueOf(0.0))) {
            // Much greater precision if all numbers in the series have the same sign.
            base = base.negate();
            invert = true;
        }
        DoubleDouble s = DoubleDouble.valueOf(1.0).add(base);
        DoubleDouble sOld = (DoubleDouble)s.clone();
        DoubleDouble t = (DoubleDouble)base.clone();
        double n = 1.0;

        do {
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            t = t.multiply(base);
            sOld = s;
            s = s.add(t);
        } while (s.ne(sOld));
        if (invert) {
            s = s.reciprocal();
        }
        return s;
    }

    public DoubleDouble mod(DoubleDouble x) {
        DoubleDouble div = this.divide(x);
        DoubleDouble rdiv = DoubleDouble.valueOf(div.intValue());
        DoubleDouble mul = rdiv.multiply(x);
        DoubleDouble ans = this.subtract(mul);
        return ans;
    }

    /**
     *
     * @param x the DoubleDouble exponent
     * @return a raised to the DoubleDouble power x
     * For a > 0, base = x * log(a), a**x = 1 + base + base**2/2! + base**3/3! + ...
     */
    public DoubleDouble pow(DoubleDouble x) {
        boolean invert = false;
        if (x.isNaN()) {
            return NaN;
        }
        if (x.isInfinite()) {
            return NaN;
        }
        if (isNaN()) {
            return NaN;
        }
        if (x.isZero()) {
            return valueOf(1.0);
        }

        if (isZero()) {
            return NaN;
        }

        if (isNegative()) {
            return NaN;
        }
        DoubleDouble loga = this.log();
        DoubleDouble base = x.multiply(loga);
        if (base.lt(DoubleDouble.valueOf(0.0))) {
            // Much greater precision if all numbers in the series have the same sign.
            base = base.negate();
            invert = true;
        }
        DoubleDouble s = DoubleDouble.valueOf(1.0).add(base);
        DoubleDouble sOld = (DoubleDouble)s.clone();
        DoubleDouble t = (DoubleDouble)base.clone();
        double n = 1.0;

        do {
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            t = t.multiply(base);
            sOld = s;
            s = s.add(t);
        } while (s.ne(sOld));
        if (invert) {
            s = s.reciprocal();
        }
        return s;
    }

    public DoubleDouble min(DoubleDouble x) {
        if (this.le(x)) {
            return this;
        }
        else {
            return x;
        }
    }

    public DoubleDouble max(DoubleDouble x) {
        if (this.ge(x)) {
            return this;
        }
        else {
            return x;
        }
    }

    /*------------------------------------------------------------
     *   Conversion Functions
     *------------------------------------------------------------
     */

    /**
     * Converts this value to the nearest double-precision number.
     *
     * @return the nearest double-precision number to this value
     */
    public double doubleValue()
    {
        return hi + lo;
    }

    /**
     * Converts this value to the nearest integer.
     *
     * @return the nearest integer to this value
     */
    public int intValue()
    {
        return (int) hi;
    }

    /*------------------------------------------------------------
     *   Predicates
     *------------------------------------------------------------
     */

    public boolean isPositiveFinite() {
        return isPositive() && (!isInfinite());
    }

    public boolean isNegativeFinite() {
        return isNegative() && (!isInfinite());
    }

    public boolean isPositiveInfinity() {
        return isInfinite() && isPositive();
    }

    public boolean isNegativeInfinity() {
        return isInfinite() && isNegative();
    }

    /**
     * Tests whether this value is equal to 0.
     *
     * @return true if this value is equal to 0
     */
    public boolean isZero()
    {
        return hi == 0.0 && lo == 0.0;
    }

    public boolean isOne()
    {
        return hi == 1.0 && lo == 0.0;
    }

    /**
     * Tests whether this value is less than 0.
     *
     * @return true if this value is less than 0
     */
    public boolean isNegative()
    {
        return hi < 0.0 || (hi == 0.0 && lo < 0.0);
    }

    /**
     * Tests whether this value is greater than 0.
     *
     * @return true if this value is greater than 0
     */
    public boolean isPositive()
    {
        return hi > 0.0 || (hi == 0.0 && lo > 0.0);
    }

    /**
     * Tests whether this value is NaN.
     *
     * @return true if this value is NaN
     */
    public boolean isNaN() { return Double.isNaN(hi); }

    public boolean isInfinite() {return Double.isInfinite(hi); }

    /**
     * Tests whether this value is equal to another <tt>DoubleDouble</tt> value.
     *
     * @param y a DoubleDouble value
     * @return true if this value = y
     */
    public boolean equals(DoubleDouble y)
    {
        return hi == y.hi && lo == y.lo;
    }

    public boolean ne(DoubleDouble y) {
        return hi != y.hi || lo != y.lo;
    }

    /**
     * Tests whether this value is greater than another <tt>DoubleDouble</tt> value.
     * @param y a DoubleDouble value
     * @return true if this value > y
     */
    public boolean gt(DoubleDouble y)
    {
        return (hi > y.hi) || (hi == y.hi && lo > y.lo);
    }
    /**
     * Tests whether this value is greater than or equals to another <tt>DoubleDouble</tt> value.
     * @param y a DoubleDouble value
     * @return true if this value >= y
     */
    public boolean ge(DoubleDouble y)
    {
        return (hi > y.hi) || (hi == y.hi && lo >= y.lo);
    }
    /**
     * Tests whether this value is less than another <tt>DoubleDouble</tt> value.
     * @param y a DoubleDouble value
     * @return true if this value < y
     */
    public boolean lt(DoubleDouble y)
    {
        return (hi < y.hi) || (hi == y.hi && lo < y.lo);
    }
    /**
     * Tests whether this value is less than or equal to another <tt>DoubleDouble</tt> value.
     * @param y a DoubleDouble value
     * @return true if this value <= y
     */
    public boolean le(DoubleDouble y)
    {
        return (hi < y.hi) || (hi == y.hi && lo <= y.lo);
    }

    /**
     * Compares two DoubleDouble objects numerically.
     *
     * @return -1,0 or 1 depending on whether this value is less than, equal to
     * or greater than the value of <tt>o</tt>
     */
    public int compareTo(Object o)
    {
        DoubleDouble other = (DoubleDouble) o;

        if (hi < other.hi) return -1;
        if (hi > other.hi) return 1;
        if (lo < other.lo) return -1;
        if (lo > other.lo) return 1;
        return 0;
    }


    /*------------------------------------------------------------
     *   Output
     *------------------------------------------------------------
     */

    private static final int MAX_PRINT_DIGITS = 32;
    private static final DoubleDouble TEN = new DoubleDouble(10.0);
    private static final DoubleDouble ONE = new DoubleDouble(1.0);
    private static final String SCI_NOT_EXPONENT_CHAR = "E";
    private static final String SCI_NOT_ZERO = "0.0E0";

    /**
     * Dumps the components of this number to a string.
     *
     * @return a string showing the components of the number
     */
    public String dump()
    {
        return "DD<" + hi + ", " + lo + ">";
    }

    /**
     * Returns a string representation of this number, in either standard or scientific notation.
     * If the magnitude of the number is in the range [ 10<sup>-3</sup>, 10<sup>8</sup> ]
     * standard notation will be used.  Otherwise, scientific notation will be used.
     *
     * @return a string representation of this number
     */
    public String toString()
    {
        int mag = magnitude(hi);
        if (mag >= -3 && mag <= 20) {
            return toStandardNotation();
        }
        else if ((mag <= -301) || (mag >= 301)) {
            return String.valueOf(hi);
        }
        return toSciNotation();
    }

    /**
     * Returns the string representation of this value in standard notation.
     *
     * @return the string representation in standard notation
     */
    public String toStandardNotation()
    {
        String specialStr = getSpecialNumberString();
        if (specialStr != null)
            return specialStr;

        int[] magnitude = new int[1];
        String sigDigits = extractSignificantDigits(true, magnitude);
        int decimalPointPos = magnitude[0] + 1;

        String num = sigDigits;
        // add a leading 0 if the decimal point is the first char
        if (sigDigits.charAt(0) == '.') {
            num = "0" + sigDigits;
        }
        else if (decimalPointPos < 0) {
            num = "0." + stringOfChar('0', -decimalPointPos) + sigDigits;
        }
        else if (sigDigits.indexOf('.') == -1) {
            // no point inserted - sig digits must be smaller than magnitude of number
            // add zeroes to end to make number the correct size
            int numZeroes = decimalPointPos - sigDigits.length();
            String zeroes = stringOfChar('0', numZeroes);
            num = sigDigits + zeroes + ".0";
        }

        if (this.isNegative())
            return "-" + num;
        return num;
    }

    /**
     * Returns the string representation of this value in scientific notation.
     *
     * @return the string representation in scientific notation
     */
    public String toSciNotation()
    {
        // special case zero, to allow as
        if (isZero())
            return SCI_NOT_ZERO;

        String specialStr = getSpecialNumberString();
        if (specialStr != null)
            return specialStr;

        int[] magnitude = new int[1];
        String digits = extractSignificantDigits(false, magnitude);
        String expStr = SCI_NOT_EXPONENT_CHAR + magnitude[0];

        // should never have leading zeroes
        // MD - is this correct?  Or should we simply strip them if they are present?
        if (digits.charAt(0) == '0') {
            throw new IllegalStateException("Found leading zero: " + digits);
        }

        // add decimal point
        String digitsWithDecimal = "";

        if (digits.length() > 1) {
            String trailingDigits = digits.substring(1);
            digitsWithDecimal = digits.charAt(0) + "." + trailingDigits;
        }
        else {
            digitsWithDecimal = digits.charAt(0) + ".0";
        }

        if (this.isNegative())
            return "-" + digitsWithDecimal + expStr;
        return digitsWithDecimal + expStr;
    }


    /**
     * Extracts the significant digits in the decimal representation of the argument.
     * A decimal point may be optionally inserted in the string of digits
     * (as long as its position lies within the extracted digits
     * - if not, the caller must prepend or append the appropriate zeroes and decimal point).
     *
     * @param y the number to extract ( >= 0)
     * @param decimalPointPos the position in which to insert a decimal point
     * @return the string containing the significant digits and possibly a decimal point
     */
    private String extractSignificantDigits(boolean insertDecimalPoint, int[] magnitude)
    {
        DoubleDouble y = this.abs();
        // compute *correct* magnitude of y
        int mag = magnitude(y.hi);
        DoubleDouble scale = TEN.pow(mag);
        y = y.divide(scale);

        // fix magnitude if off by one
        if (y.gt(TEN)) {
            y = y.divide(TEN);
            mag += 1;
        }
        else if (y.lt(ONE)) {
            y = y.multiply(TEN);
            mag -= 1;
        }

        int decimalPointPos = mag + 1;
        StringBuffer buf = new StringBuffer();
        int numDigits = MAX_PRINT_DIGITS - 1;
        for (int i = 0; i <= numDigits; i++) {
            if (insertDecimalPoint && i == decimalPointPos) {
                buf.append('.');
            }
            int digit = (int) y.hi;
//	    System.out.println("printDump: [" + i + "] digit: " + digit + "  y: " + y.dump() + "  buf: " + buf);

            /**
             * This should never happen, due to heuristic checks on remainder below
             */
            if (digit < 0 || digit > 9) {
//	    	System.out.println("digit > 10 : " + digit);
//	    	throw new IllegalStateException("Internal errror: found digit = " + digit);
            }
            /**
             * If a negative remainder is encountered, simply terminate the extraction.
             * This is robust, but maybe slightly inaccurate.
             * My current hypothesis is that negative remainders only occur for very small lo components,
             * so the inaccuracy is tolerable
             */
            if (digit < 0) {
                break;
                // throw new IllegalStateException("Internal errror: found digit = " + digit);
            }
            boolean rebiasBy10 = false;
            char digitChar = 0;
            if (digit > 9) {
                // set flag to re-bias after next 10-shift
                rebiasBy10 = true;
                // output digit will end up being '9'
                digitChar = '9';
            }
            else {
                digitChar = (char) ('0' + digit);
            }
            buf.append(digitChar);
            y = (y.subtract(DoubleDouble.valueOf(digit))
                    .multiply(TEN));
            if (rebiasBy10)
                y.selfAdd(TEN);

            boolean continueExtractingDigits = true;
            /**
             * Heuristic check: if the remaining portion of
             * y is non-positive, assume that output is complete
             */
//	    if (y.hi <= 0.0)
//		    if (y.hi < 0.0)
//	    	continueExtractingDigits = false;
            /**
             * Check if remaining digits will be 0, and if so don't output them.
             * Do this by comparing the magnitude of the remainder with the expected precision.
             */
            int remMag = magnitude(y.hi);
            if (remMag < 0 && Math.abs(remMag) >= (numDigits - i))
                continueExtractingDigits = false;
            if (! continueExtractingDigits)
                break;
        }
        magnitude[0] = mag;
        return buf.toString();
    }


    /**
     * Creates a string of a given length containing the given character
     *
     * @param ch the character to be repeated
     * @param len the len of the desired string
     * @return the string
     */
    private static String stringOfChar(char ch, int len)
    {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < len; i++) {
            buf.append(ch);
        }
        return buf.toString();
    }

    /**
     * Returns the string for this value if it has a known representation.
     * (E.g. NaN or 0.0)
     *
     * @return the string for this special number
     * @return null if the number is not a special number
     */
    private String getSpecialNumberString()
    {
        if (isZero())	return "0.0";
        if (isNaN()) 	return "NaN ";
        return null;
    }



    /**
     * Determines the decimal magnitude of a number.
     * The magnitude is the exponent of the greatest power of 10 which is less than
     * or equal to the number.
     *
     * @param x the number to find the magnitude of
     * @return the decimal magnitude of x
     */
    private static int magnitude(double x)
    {
        double xAbs = Math.abs(x);
        double xLog10 = Math.log(xAbs) / Math.log(10);
        int xMag = (int) Math.floor(xLog10);
        /**
         * Since log computation is inexact, there may be an off-by-one error
         * in the computed magnitude.
         * Following tests that magnitude is correct, and adjusts it if not
         */
        double xApprox = Math.pow(10, xMag);
        if (xApprox * 10 <= xAbs)
            xMag += 1;

        return xMag;
    }


    /*------------------------------------------------------------
     *   Input
     *------------------------------------------------------------
     */

    /**
     * Converts a string representation of a real number into a DoubleDouble value.
     * The format accepted is similar to the standard Java real number syntax.
     * It is defined by the following regular expression:
     * <pre>
     * [<tt>+</tt>|<tt>-</tt>] {<i>digit</i>} [ <tt>.</tt> {<i>digit</i>} ] [ ( <tt>e</tt> | <tt>E</tt> ) [<tt>+</tt>|<tt>-</tt>] {<i>digit</i>}+
     * <pre>
     *
     * @param str the string to parse
     * @return the value of the parsed number
     * @throws NumberFormatException if <tt>str</tt> is not a valid representation of a number
     */
    public static DoubleDouble parse(String str, int skipDecimalValue)
            throws NumberFormatException
    {
        int i = 0;
        int strlen = str.length();

        // skip leading whitespace
        while (Character.isWhitespace(str.charAt(i)))
            i++;

        // check for sign
        boolean isNegative = false;
        if (i < strlen) {
            char signCh = str.charAt(i);
            if (signCh == '-' || signCh == '+') {
                i++;
                if (signCh == '-') isNegative = true;
            }
        }

        // scan all digits and accumulate into an integral value
        // Keep track of the location of the decimal point (if any) to allow scaling later
        DoubleDouble val = new DoubleDouble();

        int numDigits = 0;
        int numBeforeDec = 0;
        int exp = 0;
        boolean hasDecimalChar = false;
        while (true) {
            if (i >= strlen)
                break;

            if(numDigits > skipDecimalValue) {
                break;
            }

            char ch = str.charAt(i);
            i++;
            if (Character.isDigit(ch)) {
                double d = ch - '0';
                val.selfMultiply(TEN);
                // MD: need to optimize this
                val.selfAdd(new DoubleDouble(d));
                numDigits++;
                continue;
            }
            if (ch == '.') {
                numBeforeDec = numDigits;
                hasDecimalChar = true;
                continue;
            }
            if (ch == 'e' || ch == 'E') {
                String expStr = str.substring(i);
                // this should catch any format problems with the exponent
                try {
                    exp = Integer.parseInt(expStr);
                }
                catch (NumberFormatException ex) {
                    throw new NumberFormatException("Invalid exponent " + expStr + " in string " + str);
                }
                break;
            }
            throw new NumberFormatException("Unexpected character '" + ch
                    + "' at position " + i
                    + " in string " + str);
        }
        DoubleDouble val2 = val;

        // correct number of digits before decimal sign if we don't have a decimal sign in the string
        if (!hasDecimalChar) numBeforeDec = numDigits;

        // scale the number correctly
        int numDecPlaces = numDigits - numBeforeDec - exp;
        if (numDecPlaces == 0) {
            val2 = val;
        }
        else if (numDecPlaces > 0) {
            DoubleDouble scale = TEN.pow(numDecPlaces);
            val2 = val.divide(scale);
        }
        else if (numDecPlaces < 0) {
            DoubleDouble scale = TEN.pow(-numDecPlaces);
            val2 = val.multiply(scale);
        }
        // apply leading sign, if any
        if (isNegative) {
            return val2.negate();
        }

        if(val2.isNaN()) {
            val2 = new DoubleDouble(Double.parseDouble(str));
        }

        return val2;

    }

    public DoubleDouble toRadians() {
        return this.divide(new DoubleDouble(180.0)).multiply(PI);
    }

    public DoubleDouble toDegrees() {
        return this.multiply(new DoubleDouble(180.0)).divide(PI);
    }

    public MantExp getMantExp() {
        return new MantExp(doubleValue());
    }
}
