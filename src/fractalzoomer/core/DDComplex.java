package fractalzoomer.core;

import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

public class DDComplex extends GenericComplex {

    private DoubleDouble re;
    private DoubleDouble im;

    public DDComplex(BigComplex c) {
        re = new DoubleDouble(c.getRe());
        im = new DoubleDouble(c.getIm());
    }

    public DDComplex(double re, double im) {
        this.re = new DoubleDouble(re);
        this.im = new DoubleDouble(im);
    }

    public DDComplex(DDComplex c) {
        re = new DoubleDouble(c.getRe());
        im = new DoubleDouble(c.getIm());
    }

    public DDComplex(Complex c) {
        re = new DoubleDouble(c.getRe());
        im = new DoubleDouble(c.getIm());
    }

    public DDComplex(DoubleDouble re, DoubleDouble im) {
        this.re = re;
        this.im = im;
    }

    public DDComplex(Apfloat re, Apfloat im) {
        this.re = new DoubleDouble(re);
        this.im = new DoubleDouble(im);
    }

    public DDComplex() {

        re = new DoubleDouble();
        im = new DoubleDouble();

    }

    public final DoubleDouble getRe() {

        return re;

    }

    public final DoubleDouble getIm() {

        return im;

    }

    public final void setRe(DoubleDouble re) {

        this.re = re;

    }

    public final void setIm(DoubleDouble im) {

        this.im = im;

    }

    @Override
    public final Complex toComplex() {
        return new Complex(re.doubleValue(), im.doubleValue());
    }

    /*
     * z1 + z2
     */
    public final DDComplex plus(DDComplex z) {

        return new DDComplex(re.add(z.re), im.add(z.im));

    }

    /*
     *  z + Real
     */
    public final DDComplex plus(DoubleDouble number) {

        return new DDComplex(re.add(number), im);

    }

    @Override
    public final DDComplex plus(int number) {

        return new DDComplex(re.add(new DoubleDouble(number)), im);

    }

    public final DDComplex plus(double number) {

        return new DDComplex(re.add(new DoubleDouble(number)), im);

    }

    /*
     *  z + Imaginary
     */
    public final DDComplex plus_i(DoubleDouble number) {

        return new DDComplex(re, im.add(number));

    }

    public final DDComplex plus_i(double number) {

        DoubleDouble num = new DoubleDouble(number);
        return new DDComplex(re, im.add(num));

    }

    public final DDComplex plus_i(int number) {

        DoubleDouble num = new DoubleDouble(number);
        return new DDComplex(re, im.add(num));

    }

    /*
     *  z1 - z2
     */
    public final DDComplex sub(DDComplex z) {

        return new DDComplex(re.subtract(z.re), im.subtract(z.im));

    }

    /*
     *  z - Real
     */
    public final DDComplex sub(DoubleDouble number) {

        return  new DDComplex(re.subtract(number), im);

    }

    @Override
    public final DDComplex sub(int number) {

        return  new DDComplex(re.subtract(new DoubleDouble(number)), im);

    }

    public final DDComplex sub(double number) {

        return  new DDComplex(re.subtract(new DoubleDouble(number)), im);

    }

    /*
     *  z - Imaginary
     */
    public final DDComplex sub_i(DoubleDouble number) {

        return new DDComplex(re, im.subtract(number));

    }

    public final DDComplex sub_i(int number) {

        return new DDComplex(re, im.subtract(new DoubleDouble(number)));

    }

    public final DDComplex sub_i(double number) {

        return new DDComplex(re, im.subtract(new DoubleDouble(number)));

    }


    /*
     *  Real - z1
     */
    public final DDComplex r_sub(DoubleDouble number) {

        return  new DDComplex(number.subtract(re), im.negate());

    }

    public final DDComplex r_sub(double number) {

        DoubleDouble num = new DoubleDouble(number);
        return  new DDComplex(num.subtract(re), im.negate());

    }

    @Override
    public final DDComplex r_sub(int number) {

        DoubleDouble num = new DoubleDouble(number);
        return  new DDComplex(num.subtract(re), im.negate());

    }

    /*
     *  Imaginary - z
     */
    public final DDComplex i_sub(DoubleDouble number) {

        return  new DDComplex(re.negate(), number.subtract(im));

    }

    public final DDComplex i_sub(double number) {

        DoubleDouble num = new DoubleDouble(number);
        return  new DDComplex(re.negate(), num.subtract(im));

    }

    public final DDComplex i_sub(int number) {

        DoubleDouble num = new DoubleDouble(number);
        return  new DDComplex(re.negate(), num.subtract(im));

    }

    /*
     *  z1 * z2
     */
    public final DDComplex times(DDComplex z) {

        return new DDComplex(re.multiply(z.re).subtract(im.multiply(z.im)),  re.multiply(z.im).add(im.multiply(z.re)));

        //DoubleDouble ac = re.multiply(z.re);
        //DoubleDouble bd = im.multiply(z.im);
        //return new DDComplex(ac.subtract(bd), re.add(im).multiply(z.re.add(z.im)).subtract(ac).subtract(bd));

    }

    /*
     *  z1 * Real
     */
    public final DDComplex times(DoubleDouble number) {

        return new DDComplex(re.multiply(number), im.multiply(number));

    }

    /*
     *  z1 * Real
     */
    @Override
    public final DDComplex times(int number) {

        DoubleDouble num = new DoubleDouble(number);
        return new DDComplex(re.multiply(num), im.multiply(num));

    }

    public final DDComplex times(double number) {

        DoubleDouble num = new DoubleDouble(number);
        return new DDComplex(re.multiply(num), im.multiply(num));

    }

    /*
     *  z * Imaginary
     */
    public final DDComplex times_i(DoubleDouble number) {

        return new DDComplex(im.negate().multiply(number), re.multiply(number));

    }

    public final DDComplex times_i(int number) {

        DoubleDouble num = new DoubleDouble(number);
        return new DDComplex(im.negate().multiply(num), re.multiply(num));

    }

    public final DDComplex times_i(double number) {

        DoubleDouble num = new DoubleDouble(number);
        return new DDComplex(im.negate().multiply(num), re.multiply(num));

    }

    /*
     *  z^2
     */
    @Override
    public final DDComplex square() {
        DoubleDouble temp = re.multiply(im);
        return new DDComplex(re.add(im).multiply(re.subtract(im)), temp.add(temp));
    }

    /*
     *  z^2 + c
     */
    public final DDComplex squareFast_plus_c(NormComponents normComponents, GenericComplex ca) {
        DoubleDouble reSqr = (DoubleDouble) normComponents.reSqr;
        DoubleDouble imSqr = (DoubleDouble) normComponents.imSqr;
        //DoubleDouble normSquared = (DoubleDouble) normComponents.normSquared;
        DDComplex c = (DDComplex) ca;
        DoubleDouble temp = re.multiply(im);
        return new DDComplex(reSqr.subtract(imSqr).add(c.re), temp.add(temp).add(c.im));
    }

    /*
     *  z^2
     */
    @Override
    public final DDComplex squareFast(NormComponents normComponents) {
        DoubleDouble reSqr = (DoubleDouble) normComponents.reSqr;
        DoubleDouble imSqr = (DoubleDouble) normComponents.imSqr;
        DoubleDouble normSquared = (DoubleDouble) normComponents.normSquared;
        return new DDComplex(reSqr.subtract(imSqr), re.add(im).sqr().subtract(normSquared));
    }

    /*
     *  z^3
     */
    @Override
    public final DDComplex cubeFast(NormComponents normComponents) {

        DoubleDouble temp = (DoubleDouble)normComponents.reSqr;
        DoubleDouble temp2 = (DoubleDouble)normComponents.imSqr;

        DoubleDouble three = new DoubleDouble(3);
        return new DDComplex(re.multiply(temp.subtract(temp2.multiply(three))), im.multiply(temp.multiply(three).subtract(temp2)));

    }

    /*
     *  z^4
     */
    public final DDComplex fourthFast(NormComponents normComponents) {

        DoubleDouble temp = (DoubleDouble)normComponents.reSqr;
        DoubleDouble temp2 = (DoubleDouble)normComponents.imSqr;

        DoubleDouble six = new DoubleDouble(6);
        DoubleDouble four = new DoubleDouble(4);

        return new DDComplex(temp.multiply(temp.subtract(temp2.multiply(six))).add(temp2.sqr()), re.multiply(im).multiply(four).multiply(temp.subtract(temp2)));

    }

    /*
     *  z^5
     */
    public final DDComplex fifthFast(NormComponents normComponents) {

        DoubleDouble temp = (DoubleDouble)normComponents.reSqr;
        DoubleDouble temp2 = (DoubleDouble)normComponents.imSqr;

        DoubleDouble five = new DoubleDouble(5);
        DoubleDouble ten = new DoubleDouble(10);

        return  new DDComplex(re.multiply(temp.sqr().add(temp2.multiply(temp2.multiply(five).subtract(temp.multiply(ten))))), im.multiply(temp2.sqr().add(temp.multiply(temp.multiply(five).subtract(temp2.multiply(ten))))));

    }

    @Override
    public NormComponents normSquaredWithComponents(NormComponents n) {
        DoubleDouble reSqr = re.sqr();
        DoubleDouble imSqr = im.sqr();
        return new NormComponents(reSqr, imSqr, reSqr.add(imSqr));
    }

    /*
     *  |z|^2
     */
    public final DoubleDouble norm_squared() {

        return re.sqr().add(im.sqr());
        //return re.mult(re).add(im.mult(im));

    }


    /*
     *  |z|, euclidean norm
     */
    public final DoubleDouble norm() {

        return re.sqr().add(im.sqr()).sqrt();

    }

    /*
     *  |z1 - z2|^2
     */
    public final DoubleDouble distance_squared(DDComplex z) {

        DoubleDouble temp_re = re.subtract(z.re);
        DoubleDouble temp_im = im.subtract(z.im);
        return temp_re.sqr().add(temp_im.sqr());

    }

    /*
     *  |z1 - z2|
     */
    public final DoubleDouble distance(DDComplex z) {

        DoubleDouble temp_re = re.subtract(z.re);
        DoubleDouble temp_im = im.subtract(z.im);
        return temp_re.sqr().add(temp_im.sqr()).sqrt();

    }

    /*
     * n-norm
     */
    public final DoubleDouble nnorm(DoubleDouble n) {
        return re.abs().pow(n).add(im.abs().pow(n)).pow(n.reciprocal());
    }

    /*
     * n-norm
     */
    public final DoubleDouble nnorm(DoubleDouble n, DoubleDouble n_reciprocal) {
        return re.abs().pow(n).add(im.abs().pow(n)).pow(n_reciprocal);
    }

    public final DoubleDouble nnorm(DoubleDouble n, DoubleDouble a, DoubleDouble b) {
        return re.abs().pow(n).multiply(a).add(im.abs().pow(n).multiply(b)).pow(n.reciprocal());
    }

    /*
     * n-norm
     */
    public final DoubleDouble nnorm(DoubleDouble n, DoubleDouble a, DoubleDouble b, DoubleDouble n_reciprocal) {
        return re.abs().pow(n).multiply(a).add(im.abs().pow(n).multiply(b)).pow(n_reciprocal);
    }

    /*
     *  |z1 - z2|^2
     */
    public final DoubleDouble distance_squared(DoubleDouble rootRe) {

        DoubleDouble temp_re = re.subtract(rootRe);
        return temp_re.sqr().add(im.sqr());

    }

    /*
     *  |z1 - z2|
     */
    public final DoubleDouble distance(DoubleDouble rootRe) {

        DoubleDouble temp_re = re.subtract(rootRe);
        return temp_re.sqr().add(im.sqr()).sqrt();

    }

    /*
     *  |Real|
     */
    public final DoubleDouble getAbsRe() {

        return re.abs();

    }

    /*
     *  |Imaginary|
     */
    public final DoubleDouble getAbsIm() {

        return im.abs();

    }

    /*
     *  |Re(z)| + Im(z)i
     */
    public final DDComplex absre() {

        return new DDComplex(re.abs(), im);

    }

    /*
     *  Re(z) + |Im(z)|i
     */
    public final DDComplex absim() {

        return new DDComplex(re, im.abs());

    }

    /*
     *  Real -Imaginary i
     */
    public final DDComplex conjugate() {

        return new DDComplex(re, im.negate());

    }

    /*
     *  -Real + Imaginary i
     */
    @Override
    public final DDComplex negate_re() {

        return new DDComplex(re.negate(), im);

    }

    /*
     *  -z
     */
    @Override
    public final DDComplex negative() {

        return new DDComplex(re.negate(), im.negate());

    }

    /*
     *  z^3
     */
    @Override
    public final DDComplex cube() {

        DoubleDouble temp = re.sqr();
        DoubleDouble temp2 = im.sqr();

        DoubleDouble three = new DoubleDouble(3);

        return new DDComplex(re.multiply(temp.subtract(temp2.multiply(three))), im.multiply(temp.multiply(three).subtract(temp2)));

    }

    /*
     *  z^4
     */
    @Override
    public final DDComplex fourth() {

        DoubleDouble temp = re.sqr();
        DoubleDouble temp2 = im.sqr();

        DoubleDouble six = new DoubleDouble(6);
        DoubleDouble four = new DoubleDouble(4);

        return new DDComplex(temp.multiply(temp.subtract(temp2.multiply(six))).add(temp2.sqr()), re.multiply(im).multiply(four).multiply(temp.subtract(temp2)));

    }

    /*
     *  z^5
     */
    @Override
    public final DDComplex fifth() {

        DoubleDouble temp = re.sqr();
        DoubleDouble temp2 = im.sqr();

        DoubleDouble five = new DoubleDouble(5);
        DoubleDouble ten = new DoubleDouble(10);

        return  new DDComplex(re.multiply(temp.sqr().add(temp2.multiply(temp2.multiply(five).subtract(temp.multiply(ten))))), im.multiply(temp2.sqr().add(temp.multiply(temp.multiply(five).subtract(temp2.multiply(ten))))));

    }


    /*
     *  abs(z)
     */
    public final DDComplex abs() {

        return new DDComplex(re.abs(), im.abs());

    }

    /* more efficient z^2 + c */
    public final DDComplex square_plus_c(DDComplex c) {

        DoubleDouble temp = re.multiply(im);

        return new DDComplex(re.add(im).multiply(re.subtract(im)).add(c.re), temp.add(temp).add(c.im));

    }

    @Override
    public final String toString() {

        String temp = "";

        if(im.isPositive()) {
            temp = re.doubleValue() + "+" + im.doubleValue() + "i";
        }
        else if (im.isZero()) {
            temp = re.doubleValue() + "+" + (0.0) + "i";
        } else {
            temp = re.doubleValue() + "" + im.doubleValue() + "i";
        }

        return temp;

    }

    /*
     *  lexicographical comparison between two complex numbers
     * -1 when z1 > z2
     *  1 when z1 < z2
     *  0 when z1 == z2
     */
    public final int compare(DDComplex z2) {

        int comparisonRe = re.compareTo(z2.re);
        int comparisonIm = im.compareTo(z2.im);

        if (comparisonRe >  0) {
            return -1;
        } else if (comparisonRe < 0) {
            return 1;
        } else if (comparisonIm > 0) {
            return -1;
        } else if (comparisonIm < 0) {
            return 1;
        } else if (comparisonRe == 0 && comparisonIm == 0) {
            return 0;
        }

        return 2;
    }

    /*
     *  1 / z
     */
    @Override
    public final DDComplex reciprocal() {

        DoubleDouble temp = new DoubleDouble(1.0).divide(re.sqr().add(im.sqr()));

        return new DDComplex(re.multiply(temp), im.negate().multiply(temp));

    }

    public final DDComplex toBiPolar(DDComplex a) {

        return this.times(0.5).cot().times(a).times_i(1);

    }

    public final DDComplex fromBiPolar(DDComplex a) {

        return this.divide(a.times_i(1)).acot().times2();

    }

    /*
     *  cot(z) = i(1 + exp(-2zi)) / (1 - exp(-2zi))
     */
    public final DDComplex cot() {

        DoubleDouble temp = im.multiply(2).exp();

        DoubleDouble temp3 = re.multiply(2);

        DoubleDouble cos_re = temp3.cos();
        DoubleDouble sin_re = temp3.sin();

        DDComplex temp2 = new DDComplex(temp.multiply(cos_re), temp.multiply(sin_re.negate()));

        return (temp2.times_i(1).plus_i(1)).divide(temp2.r_sub(1));

    }

    /*
     *  acot(z) = (i / 2)log((z^2 - iz) / (z^2 + iz))
     */
    public final DDComplex acot() {

        DDComplex temp = this.times_i(1);
        DDComplex temp2 = this.square();

        return ((temp2.sub(temp)).divide(temp2.plus(temp))).log().times_i(0.5);

    }

    /*
     *  cos(z) = (exp(iz) + exp(-iz)) / 2
     */
    public final DDComplex cos() {

        DoubleDouble temp = im.negate().exp();

        DoubleDouble cos_re = re.cos();
        DoubleDouble sin_re = re.sin();

        DDComplex temp2 = new DDComplex(temp.multiply(cos_re), temp.multiply(sin_re));

        DoubleDouble temp3 = temp.reciprocal();
        DDComplex temp4 = new DDComplex(temp3.multiply(cos_re), temp3.multiply(sin_re.negate()));

        return (temp2.plus(temp4)).times(0.5);

    }

    /*
     *  cosh(z) = (exp(z) + exp(-z)) / 2
     */
    public final DDComplex cosh() {

        DoubleDouble temp = re.exp();

        DoubleDouble cos_im = im.cos();
        DoubleDouble sin_im = im.sin();

        DDComplex temp2 = new DDComplex(temp.multiply(cos_im), temp.multiply(sin_im));

        DoubleDouble temp3 = temp.reciprocal();
        DDComplex temp4 = new DDComplex(temp3.multiply(cos_im), temp3.multiply(sin_im.negate()));

        return (temp2.plus(temp4)).times(0.5);

    }

    /*
     *  acos(z) = pi / 2 + ilog(iz + sqrt(1 - z^2))
     */
    public final DDComplex acos() {

        return this.asin().r_sub(DoubleDouble.PI_2);

    }

    /*
     *  acosh(z) = log(z + sqrt(z^2 - 1))
     */
    public final DDComplex acosh() {

        return this.plus((this.square().sub(1)).sqrt()).log();

    }

    /*
     *  sin(z) = (exp(iz) - exp(-iz)) / 2i
     */
    public final DDComplex sin() {

        DoubleDouble temp = im.negate().exp();

        DoubleDouble cos_re = re.cos();
        DoubleDouble sin_re = re.sin();

        DDComplex temp2 = new DDComplex(temp.multiply(cos_re), temp.multiply(sin_re));

        DoubleDouble temp3 = temp.reciprocal();
        DDComplex temp4 = new DDComplex(temp3.multiply(cos_re), temp3.multiply(sin_re.negate()));

        return (temp2.sub(temp4)).times_i(-0.5);

    }

    /*
     *  sinh(z) = (exp(z) - exp(-z)) / 2
     */
    public final DDComplex sinh() {

        DoubleDouble temp = re.exp();

        DoubleDouble cos_im = im.cos();
        DoubleDouble sin_im = im.sin();

        DDComplex temp2 = new DDComplex(temp.multiply(cos_im), temp.multiply(sin_im));

        DoubleDouble temp3 = temp.reciprocal();
        DDComplex temp4 = new DDComplex(temp3.multiply(cos_im), temp3.multiply(sin_im.negate()));

        return (temp2.sub(temp4)).times(0.5);

    }

    /*
     *  asin(z) =-ilog(iz + sqrt(1 - z^2))
     */
    public final DDComplex asin() {

        return this.times_i(1).plus((this.square().r_sub(1)).sqrt()).log().times_i(-1);

    }

    /*
     *  asinh(z) = log(z + sqrt(z^2 + 1))
     */
    public final DDComplex asinh() {

        return this.plus((this.square().plus(1)).sqrt()).log();

    }

    /*
     *  tan(z) = (1 - exp(-2zi)) / i(1 + exp(-2zi))
     */
    public final DDComplex tan() {

        DoubleDouble temp = im.multiply(2).exp();

        DoubleDouble temp3 = re.multiply(2);

        DoubleDouble cos_re = temp3.cos();
        DoubleDouble sin_re = temp3.sin();

        DDComplex temp2 = new DDComplex(temp.multiply(cos_re), temp.multiply(sin_re.negate()));

        return (temp2.r_sub(1)).divide((temp2.plus(1)).times_i(1));

    }

    /*
     *  tahn(z) = (1 - exp(-2z)) / (1 + exp(-2z))
     */
    public final DDComplex tanh() {

        DoubleDouble temp = re.multiply(-2).exp();

        DoubleDouble temp3 = im.multiply(2);

        DoubleDouble cos_im = temp3.cos();
        DoubleDouble sin_im = temp3.sin();

        DDComplex temp2 = new DDComplex(temp.multiply(cos_im), temp.multiply(sin_im.negate()));

        return (temp2.r_sub(1)).divide(temp2.plus(1));

    }

    /*
     *  atan(z) = (i / 2)log((1 - iz) / (iz + 1))
     */
    public final DDComplex atan() {

        DDComplex temp = this.times_i(1);

        return ((temp.r_sub(1)).divide(temp.plus(1))).log().times_i(0.5);

    }

    /*
     *  atanh(z) = (1 / 2)log((z + 1) / (1 - z))
     */
    public final DDComplex atanh() {

        return ((this.plus(1)).divide(this.r_sub(1))).log().times(0.5);

    }

    /*
     *  coth(z) =  (1 + exp(-2z)) / (1 - exp(-2z))
     */
    public final DDComplex coth() {

        DoubleDouble temp = re.multiply(-2).exp();

        DoubleDouble temp3 = im.multiply(2);

        DoubleDouble cos_im = temp3.cos();
        DoubleDouble sin_im = temp3.sin();

        DDComplex temp2 = new DDComplex(temp.multiply(cos_im), temp.multiply(sin_im.negate()));

        return (temp2.plus(1)).divide(temp2.r_sub(1));

    }

    /*
     *  acoth(z) = (1 / 2)log((1 + 1/z) / (1 - 1/z))
     */
    public final DDComplex acoth() {

        DDComplex temp = this.reciprocal();

        return ((temp.plus(1)).divide(temp.r_sub(1))).log().times(0.5);

    }


    /*
     *  sec(z) = 1 / cos(z)
     */
    public final DDComplex sec() {

        return this.cos().reciprocal();

    }

    /*
     *  asec(z) = pi / 2 + ilog(sqrt(1 - 1 / z^2) + i / z)
     */
    public final DDComplex asec() {

        return (((this.square().reciprocal()).r_sub(1).sqrt()).plus(this.i_divide(1))).log().times_i(1).plus(DoubleDouble.PI_2);

    }

    /*
     *  sech(z) = 1 / cosh(z)
     */
    public final DDComplex sech() {

        return this.cosh().reciprocal();

    }

    /*
     *  asech(z) = log(sqrt(1 / z^2 - 1) + 1 / z)
     */
    public final DDComplex asech() {

        return (((this.square().reciprocal()).sub(1).sqrt()).plus(this.reciprocal())).log();

    }

    /*
     *  csc(z) = 1 / sin(z)
     */
    public final DDComplex csc() {

        return this.sin().reciprocal();

    }

    /*
     *  acsc(z) = -ilog(sqrt(1 - 1 / z^2) + i / z)
     */
    public final DDComplex acsc() {

        return (((this.square().reciprocal()).r_sub(1).sqrt()).plus(this.i_divide(1))).log().times_i(-1);

    }

    /*
     *  csch(z) = 1 / sinh(z)
     */
    public final DDComplex csch() {

        return this.sinh().reciprocal();

    }

    /*
     *  acsch(z) = log(sqrt(1 / z^2 + 1) + 1 / z)
     */
    public final DDComplex acsch() {

        return (((this.square().reciprocal()).plus(1).sqrt()).plus(this.reciprocal())).log();

    }

    /*
     * versine(z) = 1 - cos(z)
     */
    public final DDComplex vsin() {

        return this.cos().r_sub(1);

    }

    /*
     * arc versine(z) = acos(1 - z)
     */
    public final DDComplex avsin() {

        return this.r_sub(1).acos();

    }

    /*
     * vercosine(z) = 1 + cos(z)
     */
    public final DDComplex vcos() {

        return this.cos().plus(1);

    }

    /*
     * arc vercosine(z) = acos(1 + z)
     */
    public final DDComplex avcos() {

        return this.plus(1).acos();

    }

    /*
     * coversine(z) = 1 - sin(z)
     */
    public final DDComplex cvsin() {

        return this.sin().r_sub(1);

    }

    /*
     * arc coversine(z) = asin(1 - z)
     */
    public final DDComplex acvsin() {

        return this.r_sub(1).asin();

    }

    /*
     * covercosine(z) = 1 + sin(z)
     */
    public final DDComplex cvcos() {

        return this.sin().plus(1);

    }

    /*
     * arc covercosine(z) = asin(1 + z)
     */
    public final DDComplex acvcos() {

        return this.plus(1).asin();

    }

    /*
     * haversine(z) = versine(z) / 2
     */
    public final DDComplex hvsin() {

        return this.vsin().times(0.5);

    }

    /*
     * arc haversine(z) = 2 * asin(sqrt(z))
     */
    public final DDComplex ahvsin() {

        return this.sqrt().asin().times2();

    }

    /*
     * havercosine(z) = vercosine(z) / 2
     */
    public final DDComplex hvcos() {

        return this.vcos().times(0.5);

    }

    /*
     * arc havercosine(z) = 2 * acos(sqrt(z))
     */
    public final DDComplex ahvcos() {

        return this.sqrt().acos().times2();

    }

    /*
     * hacoversine(z) = coversine(z) / 2
     */
    public final DDComplex hcvsin() {

        return this.cvsin().times(0.5);

    }

    /*
     * arc hacoversine(z) = asin(1 - 2*z)
     */
    public final DDComplex ahcvsin() {

        return this.times2().r_sub(1).asin();

    }

    /*
     * hacovercosine(z) = covercosine(z) / 2
     */
    public final DDComplex hcvcos() {

        return this.cvcos().times(0.5);

    }

    /*
     * arc hacovercosine(z) = asin(-1 - 2*z)
     */
    public final DDComplex ahcvcos() {

        return this.times(-2).r_sub(1).asin();

    }

    /*
     * exsecant(z) = sec(z) - 1
     */
    public final DDComplex exsec() {

        return this.sec().sub(1);

    }

    /*
     * arc exsecant(z) = asec(z + 1)
     */
    public final DDComplex aexsec() {

        return this.plus(1).asec();

    }

    /*
     * excosecant(z) = csc(z) - 1
     */
    public final DDComplex excsc() {

        return this.csc().sub(1);

    }

    /*
     * arc excosecant(z) = acsc(z + 1)
     */
    public final DDComplex aexcsc() {

        return this.plus(1).acsc();

    }

    public final DDComplex fold_out(DDComplex z2) {

        DoubleDouble norm_sqr = re.sqr().add(im.sqr());

        return norm_sqr.compareTo(z2.norm_squared()) > 0 ? this.divide(norm_sqr) : this;

    }

    public final DDComplex fold_in(DDComplex z2) {

        DoubleDouble norm_sqr = re.sqr().add(im.sqr());

        return norm_sqr.compareTo(z2.norm_squared()) < 0 ? this.divide(norm_sqr) : this;

    }

    public final DDComplex fold_right(DDComplex z2) {

        return re.compareTo(z2.re) < 0 ? new DDComplex(re.add(z2.re.subtract(re).multiply(new DoubleDouble(2))), im) : this;

    }

    public final DDComplex fold_left(DDComplex z2) {

        return re.compareTo(z2.re) > 0 ? new DDComplex(re.subtract(re.subtract(z2.re).multiply(new DoubleDouble(2))), im) : this;

    }

    public final DDComplex fold_up(DDComplex z2) {

        return im.compareTo(z2.im) < 0 ? new DDComplex(re, im.add(z2.im.subtract(im).multiply(new DoubleDouble(2)))) : this;

    }

    public final DDComplex fold_down(DDComplex z2) {

        return im.compareTo(z2.im) > 0 ? new DDComplex(re, im.subtract(im.subtract(z2.im).multiply(new DoubleDouble(2)))) : this;

    }

    public final DDComplex inflection(DDComplex inf) {

        DDComplex diff = this.sub(inf);

        return inf.plus(diff.square());

    }

    public final DDComplex inflectionPower(DDComplex inf, double power) {

        if(power == 1) {
            return plus(inf);
        }
        else if(power == 2) {
            return square().plus(inf);
        }
        else if(power == 3) {
            return cube().plus(inf);
        }
        else if(power == 4) {
            return fourth().plus(inf);
        }
        if(power == 5) {
            return fifth().plus(inf);
        }

        return pow(power).plus(inf);

    }

    public final DDComplex shear(DDComplex sh) {

        return new DDComplex(re.add(im.multiply(sh.re)), im.add(re.multiply(sh.im)));

    }

    /*
     * y + xi
     */
    public final DDComplex flip() {

        return new DDComplex(im, re);

    }

    /*
     *  z1 / z2
     */
    public final DDComplex divide(DDComplex z) {

        DoubleDouble temp = z.re;
        DoubleDouble temp2 = z.im;
        DoubleDouble temp3 = new DoubleDouble(1.0).divide(temp.sqr().add(temp2.sqr()));

        return new DDComplex(re.multiply(temp).add(im.multiply(temp2)).multiply(temp3), im.multiply(temp).subtract(re.multiply(temp2)).multiply(temp3));
    }

    /*
     *  z / Real
     */
    public final DDComplex divide(DoubleDouble number) {

        DoubleDouble temp = new DoubleDouble(1.0).divide(number);
        return new DDComplex(re.multiply(temp), im.multiply(temp));

    }

    @Override
    public final DDComplex divide(int number) {

        DoubleDouble num = new DoubleDouble(1.0).divide(new DoubleDouble(number));
        return new DDComplex(re.multiply(num), im.multiply(num));

    }

    public final DDComplex divide(double number) {

        DoubleDouble num = new DoubleDouble(1.0).divide(new DoubleDouble(number));
        return new DDComplex(re.multiply(num), im.multiply(num));

    }

    /*
     *  z1 / Imaginary
     */
    public final DDComplex divide_i(DoubleDouble number) {

        DoubleDouble temp3 = new DoubleDouble(1.0).divide(number.sqr());
        return new DDComplex(re.add(im.multiply(number)).multiply(temp3), im.subtract(re.multiply(number)).multiply(temp3));

    }

    public final DDComplex divide_i(int number) {

        DoubleDouble num = new DoubleDouble(number);
        DoubleDouble temp3 = new DoubleDouble(1.0).divide(num.sqr());
        return new DDComplex(re.add(im.multiply(number)).multiply(temp3), im.subtract(re.multiply(number)).multiply(temp3));

    }

    public final DDComplex divide_i(double number) {

        DoubleDouble num = new DoubleDouble(number);
        DoubleDouble temp3 = new DoubleDouble(1.0).divide(num.sqr());
        return new DDComplex(re.add(im.multiply(number)).multiply(temp3), im.subtract(re.multiply(number)).multiply(temp3));

    }

    /*
     *  Real / z
     */
    public final DDComplex r_divide(DoubleDouble number) {

        DoubleDouble temp = number.divide(re.sqr().add(im.sqr()));

        return new DDComplex(re.multiply(temp), im.negate().multiply(temp));

    }

    public final DDComplex r_divide(double number) {

        DoubleDouble num = new DoubleDouble(number);
        DoubleDouble temp = num.divide(re.sqr().add(im.sqr()));

        return new DDComplex(re.multiply(temp), im.negate().multiply(temp));

    }

    public final DDComplex r_divide(int number) {

        DoubleDouble num = new DoubleDouble(number);
        DoubleDouble temp = num.divide(re.sqr().add(im.sqr()));

        return new DDComplex(re.multiply(temp), im.negate().multiply(temp));

    }

    /*
     *  Imaginary / z
     */
    public final DDComplex i_divide(DoubleDouble number) {

        DoubleDouble temp = number.divide(re.sqr().add(im.sqr()));

        return new DDComplex(im.multiply(temp), re.multiply(temp));

    }

    public final DDComplex i_divide(double number) {

        DoubleDouble num = new DoubleDouble(number);

        DoubleDouble temp = num.divide(re.sqr().add(im.sqr()));

        return new DDComplex(im.multiply(temp), re.multiply(temp));

    }

    public final DDComplex i_divide(int number) {

        DoubleDouble num = new DoubleDouble(number);

        DoubleDouble temp = num.divide(re.sqr().add(im.sqr()));

        return new DDComplex(im.multiply(temp), re.multiply(temp));

    }

    /*
     *  exp(z) = exp(Re(z)) * (cos(Im(z)) + sin(Im(z))i)
     */
    public final DDComplex exp() {

        DoubleDouble temp = re.exp();

        return new DDComplex(temp.multiply(im.cos()), temp.multiply(im.sin()));

    }

    /*
     *  log(z) = ln|z| + arctan(Im/Re)i
     */
    //atan2 infinite loop
    public final DDComplex log() {

        return new DDComplex(re.sqr().add(im.sqr()).log().multiply(0.5), im.atan2(re));

    }

    /*
     *  z1 ^ z2 = exp(z2 * log(z1))
     */
    public final DDComplex pow(DDComplex z) {

        return (z.times(this.log())).exp();

    }

    /*
     *  z^n
     */
    public final DDComplex pow(double exponent) {

        DoubleDouble temp = re.sqr().add(im.sqr()).pow(exponent * 0.5);
        DoubleDouble temp2 = im.atan2(re).multiply(exponent);

        return new DDComplex(temp.multiply(temp2.cos()), temp.multiply(temp2.sin()));

    }

    /*
     * sqrt(z) = z^0.5
     */
    public final DDComplex sqrt() {

        DoubleDouble temp = re.sqr().add(im.sqr()).pow(0.25);
        DoubleDouble temp2 = im.atan2(re).multiply(0.5);

        return new DDComplex(temp.multiply(temp2.cos()), temp.multiply(temp2.sin()));

    }

    public final DDComplex circle_inversion(DDComplex center, DoubleDouble radius2) {

        DoubleDouble distance = this.distance_squared(center);

        DoubleDouble temp = radius2.divide(distance);

        return new DDComplex(center.re.add(re.subtract(center.re).multiply(temp)), center.im.add(im.subtract(center.im).multiply(temp)));

    }


    /*
     *  z1 - z2
     */
    @Override
    public final DDComplex sub(GenericComplex zn) {
        DDComplex z = (DDComplex)zn;

        return new DDComplex(re.subtract(z.re), im.subtract(z.im));

    }

    /*
     * z1 + z2
     */
    @Override
    public final DDComplex plus(GenericComplex zn) {

        DDComplex z = (DDComplex)zn;
        return new DDComplex(re.add(z.re), im.add(z.im));

    }

    /* more efficient z^2 + c */
    public final DDComplex square_plus_c(GenericComplex cn) {

        DDComplex c = (DDComplex)cn;

        DoubleDouble temp = re.multiply(im);

        return new DDComplex(re.add(im).multiply(re.subtract(im)).add(c.re), temp.add(temp).add(c.im));

    }

    /*
     *  z1 * z2
     */
    @Override
    public final DDComplex times(GenericComplex zn) {

        DDComplex z = (DDComplex)zn;
        return new DDComplex(re.multiply(z.re).subtract(im.multiply(z.im)),  re.multiply(z.im).add(im.multiply(z.re)));
        //DoubleDouble ac = re.multiply(z.re);
        //DoubleDouble bd = im.multiply(z.im);
        //return new DDComplex(ac.subtract(bd), re.add(im).multiply(z.re.add(z.im)).subtract(ac).subtract(bd));

    }

    /*
     *  lexicographical comparison between two complex numbers
     * -1 when z1 > z2
     *  1 when z1 < z2
     *  0 when z1 == z2
     */
    @Override
    public final int compare(GenericComplex z2c) {

        DDComplex z2 = (DDComplex)z2c;

        int comparisonRe = re.compareTo(z2.re);
        int comparisonIm = im.compareTo(z2.im);

        if (comparisonRe >  0) {
            return -1;
        } else if (comparisonRe < 0) {
            return 1;
        } else if (comparisonIm > 0) {
            return -1;
        } else if (comparisonIm < 0) {
            return 1;
        } else if (comparisonRe == 0 && comparisonIm == 0) {
            return 0;
        }

        return 2;
    }

    /*
     *  |z|^2
     */
    @Override
    public final DoubleDouble normSquared() {

        return re.sqr().add(im.sqr());
        //return re.mult(re).add(im.mult(im));

    }

    /*
     *  |z1 - z2|^2
     */
    @Override
    public final DoubleDouble distanceSquared(GenericComplex za) {
        DDComplex z = (DDComplex) za;

        DoubleDouble temp_re = re.subtract(z.re);
        DoubleDouble temp_im = im.subtract(z.im);
        return temp_re.sqr().add(temp_im.sqr());

    }

    @Override
    public DDComplex times2() {
        return new DDComplex(re.add(re), im.add(im));
    }

    @Override
    public DDComplex times4() {
        DoubleDouble four = new DoubleDouble(4);
        return new DDComplex(re.multiply(four), im.multiply(four));
    }

    @Override
    public MantExpComplex toMantExpComplex() { return MantExpComplex.create(this.toComplex());}

    @Override
    public void set(GenericComplex za) {
        DDComplex z = (DDComplex) za;
        re = z.re;
        im = z.im;
    }

    public boolean isZero() {
        return re.isZero() && im.isZero();
    }

    public boolean isOne() {
        return re.isOne() && im.isZero();
    }

    @Override
    public DDComplex toDDComplex() { return this; }

    @Override
    public final DDComplex divide(GenericComplex za) {

        DDComplex z = (DDComplex)za;
        DoubleDouble temp = z.re;
        DoubleDouble temp2 = z.im;
        DoubleDouble temp3 = new DoubleDouble(1.0).divide(temp.sqr().add(temp2.sqr()));

        return new DDComplex(re.multiply(temp).add(im.multiply(temp2)).multiply(temp3), im.multiply(temp).subtract(re.multiply(temp2)).multiply(temp3));
    }

    @Override
    public GenericComplex sub_mutable(int a) {
        return sub(a);
    }

    @Override
    public GenericComplex plus_mutable(int a) {return plus(a);}

    @Override
    public GenericComplex negative_mutable() {return negative();}

    @Override
    public GenericComplex sub_mutable(GenericComplex v) { return sub(v); }

    @Override
    public GenericComplex plus_mutable(GenericComplex v) { return plus(v); }

    @Override
    public GenericComplex times_mutable(GenericComplex a) {return times(a);}

    @Override
    public GenericComplex times2_mutable() {return times2();}

    @Override
    public GenericComplex divide_mutable(GenericComplex a) {return divide(a);}

    @Override
    public GenericComplex times_mutable(int a) {return times(a);}

    @Override
    public GenericComplex square_mutable() {return square();}

    @Override
    public GenericComplex abs_mutable() { return abs(); }

    /*
     *  z^3
     */
    @Override
    public final DDComplex squareFast_mutable(NormComponents normComponents) {

        return squareFast(normComponents);

    }

    /*
     *  z^3
     */
    @Override
    public final DDComplex cubeFast_mutable(NormComponents normComponents) {

        return cubeFast(normComponents);

    }

    /*
     *  z^4
     */
    @Override
    public final DDComplex fourthFast_mutable(NormComponents normComponents) {

        return fourthFast(normComponents);

    }

    /*
     *  z^5
     */
    @Override
    public final DDComplex fifthFast_mutable(NormComponents normComponents) {

        return fifthFast(normComponents);

    }

    /*
     *  z^2 + c
     */
    @Override
    public final DDComplex squareFast_plus_c_mutable(NormComponents normComponents, GenericComplex ca) {
        return squareFast_plus_c(normComponents, ca);
    }

    /* more efficient z^2 + c */
    @Override
    public final DDComplex square_plus_c_mutable(GenericComplex cn) {

        return square_plus_c(cn);

    }

    @Override
    public final DDComplex square_plus_c_mutable_no_threads(GenericComplex cn) {

        return square_plus_c(cn);

    }

    /*
     *  Real -Imaginary i
     */
    @Override
    public final DDComplex conjugate_mutable() {

        return conjugate();

    }

    @Override
    public DDComplex absNegateRe_mutable() {
        return new DDComplex(re.abs().negate(), im);
    }
    @Override
    public DDComplex absNegateIm_mutable() {
        return new DDComplex(re, im.abs().negate());
    }


    @Override
    public DDComplex absre_mutable() { return  absre(); }

    public final boolean isNaN() {
        return re.isNaN() || im.isNaN();
    }

    public final boolean isInfinite() {
        return re.isInfinite() || im.isInfinite();
    }

    @Override
    public BigComplex toBigComplex() {
        return new BigComplex(re.toApfloat(), im.toApfloat());
    }

    @Override
    public Object re() {
        return getRe();
    }

    @Override
    public Object im() {
        return getIm();
    }

    @Override
    public Object Norm() {
        return norm();
    }

    @Override
    public final DDComplex negate_re_mutable() {

        return negate_re();

    }
}
