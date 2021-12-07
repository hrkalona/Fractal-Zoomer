package fractalzoomer.core;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.FixedPrecisionApfloatHelper;

public class BigComplex extends GenericComplex {
    private Apfloat re;
    private Apfloat im;

    public BigComplex() {

        re = new MyApfloat(0.0);
        im = new MyApfloat(0.0);

    }

    public BigComplex(Apfloat re, Apfloat im) {

        this.re = re;
        this.im = im;

    }

    public BigComplex(double re, double im) {

        this.re = new MyApfloat(re);
        this.im = new MyApfloat(im);

    }

    public BigComplex(BigComplex z) {

        re = z.re;
        im = z.im;

    }

    public BigComplex(Complex z) {

        re = new MyApfloat(z.getRe());
        im = new MyApfloat(z.getIm());

    }

    public final Apfloat getRe() {

        return re;

    }

    public final Apfloat getIm() {

        return im;

    }

    public final void setRe(Apfloat re) {

        this.re = re;

    }

    public final void setIm(Apfloat im) {

        this.im = im;

    }

    public final Complex toComplex() {
        return new Complex(re.doubleValue(), im.doubleValue());
    }

    /*
     * z1 + z2
     */
    public final BigComplex plus(BigComplex z) {

        return new BigComplex(MyApfloat.fp.add(re, z.re), MyApfloat.fp.add(im, z.im));

    }

    /*
     *  z + Real
     */
    public final BigComplex plus(Apfloat number) {

        return new BigComplex(MyApfloat.fp.add(re, number), im);

    }

    /*
     *  z + Imaginary
     */
    public final BigComplex plus_i(Apfloat number) {

        return new BigComplex(re, MyApfloat.fp.add(im, number));

    }

    /*
     *  z1 - z2
     */
    public final BigComplex sub(BigComplex z) {

        return  new BigComplex(MyApfloat.fp.subtract(re, z.re), MyApfloat.fp.subtract(im, z.im));

    }

    /*
     *  z - Real
     */
    public final BigComplex sub(Apfloat number) {

        return  new BigComplex(MyApfloat.fp.subtract(re, number),im);

    }

    /*
     *  Real - z1
     */
    public final BigComplex r_sub(Apfloat number) {

        return  new BigComplex(MyApfloat.fp.subtract(number, re), im.negate());

    }

    /*
     *  Imaginary - z
     */
    public final BigComplex i_sub(Apfloat number) {

        return  new BigComplex(MyApfloat.fp.subtract(number, re), im.negate());

    }

    /*
     *  z1 * z2
     */
    public final BigComplex times(BigComplex z) {

        return new BigComplex(MyApfloat.fp.subtract(MyApfloat.fp.multiply(re, z.re), MyApfloat.fp.multiply(im, z.im)), MyApfloat.fp.add(MyApfloat.fp.multiply(re, z.im), MyApfloat.fp.multiply(im, z.re)));

    }

    /*
     *  z1 * Real
     */
    public final BigComplex times(Apfloat number) {

        return new BigComplex(MyApfloat.fp.multiply(re, number), MyApfloat.fp.multiply(im, number));

    }

    /*
     *  z * Imaginary
     */
    public final BigComplex times_i(Apfloat number) {

        return new BigComplex(MyApfloat.fp.multiply(im.negate(), number), MyApfloat.fp.multiply(re, number));

    }

    /*
     *  z1 / z2
     */
    public final BigComplex divide(BigComplex z) {

        Apfloat temp = z.re;
        Apfloat temp2 = z.im;
        Apfloat temp3 = MyApfloat.fp.add(MyApfloat.fp.multiply(temp, temp), MyApfloat.fp.multiply(temp2, temp2));

        return new BigComplex(MyApfloat.fp.divide(MyApfloat.fp.add(MyApfloat.fp.multiply(re, temp), MyApfloat.fp.multiply(im, temp2)), temp3), MyApfloat.fp.divide(MyApfloat.fp.subtract(MyApfloat.fp.multiply(im, temp), MyApfloat.fp.multiply(re, temp2)), temp3));

    }

    /*
     *  z / Real
     */
    public final BigComplex divide(Apfloat number) {

        return new BigComplex(MyApfloat.fp.divide(re, number), MyApfloat.fp.divide(im, number));

    }

    /*
     *  z1 / Imaginary
     */
    public final BigComplex divide_i(Apfloat number) {

        Apfloat temp3 = MyApfloat.fp.multiply(number, number);

        return new BigComplex(MyApfloat.fp.divide(MyApfloat.fp.add(re, MyApfloat.fp.multiply(im, number)), temp3), MyApfloat.fp.divide(MyApfloat.fp.subtract(im, MyApfloat.fp.multiply(re, number)), temp3));

    }

    /*
     *  Real / z
     */
    public final BigComplex r_divide(Apfloat number) {

        Apfloat temp = MyApfloat.fp.divide(number, MyApfloat.fp.add(MyApfloat.fp.multiply(re, re), MyApfloat.fp.multiply(im, im)));

        return new BigComplex(MyApfloat.fp.multiply(re, temp), MyApfloat.fp.multiply(im.negate(), temp));

    }

    /*
     *  Imaginary / z
     */
    public final BigComplex i_divide(Apfloat number) {

        Apfloat temp = MyApfloat.fp.divide(number, MyApfloat.fp.add(MyApfloat.fp.multiply(re, re), MyApfloat.fp.multiply(im, im)));

        return new BigComplex(MyApfloat.fp.multiply(im, temp), MyApfloat.fp.multiply(re, temp));

    }


    /*
     *  z^2
     */
    public final BigComplex square() {
        Apfloat temp = MyApfloat.fp.multiply(re, im);
        return new BigComplex(MyApfloat.fp.multiply(MyApfloat.fp.add(re, im), MyApfloat.fp.subtract(re, im)), MyApfloat.fp.add(temp, temp));
    }

    /*
     *  z^3
     */
    public final BigComplex cube() {

        Apfloat temp = MyApfloat.fp.multiply(re, re);
        Apfloat temp2 = MyApfloat.fp.multiply(im, im);
        Apfloat three = new MyApfloat(3.0);

        return new BigComplex(MyApfloat.fp.multiply(re, MyApfloat.fp.subtract(temp, MyApfloat.fp.multiply(temp2, three))), MyApfloat.fp.multiply(im, MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp, three), temp2)));

    }

    /*
     *  z^4
     */
    public final BigComplex fourth() {

        Apfloat temp = MyApfloat.fp.multiply(re, re);
        Apfloat temp2 = MyApfloat.fp.multiply(im, im);

        return new BigComplex(MyApfloat.fp.add(MyApfloat.fp.multiply(temp, MyApfloat.fp.subtract(temp, MyApfloat.fp.multiply(new MyApfloat(6), temp2))), MyApfloat.fp.multiply(temp2, temp2)), MyApfloat.fp.multiply(new MyApfloat(4), MyApfloat.fp.multiply(re, MyApfloat.fp.multiply(im, MyApfloat.fp.subtract(temp, temp2)))));

    }

    /*
     *  z^5
     */
    public final BigComplex fifth() {

        Apfloat temp = MyApfloat.fp.multiply(re, re);
        Apfloat temp2 = MyApfloat.fp.multiply(im, im);

        Apfloat five = new MyApfloat(5);
        Apfloat ten = new MyApfloat(10);

        return new BigComplex(MyApfloat.fp.multiply(re, MyApfloat.fp.add(MyApfloat.fp.multiply(temp, temp), MyApfloat.fp.multiply(temp2, MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp2, five), MyApfloat.fp.multiply(temp, ten))))), MyApfloat.fp.multiply(im, MyApfloat.fp.add(MyApfloat.fp.multiply(temp2, temp2), MyApfloat.fp.multiply(temp, MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp, five), MyApfloat.fp.multiply(temp2, ten))))));
    }


    /*
     *  |z|^2
     */
    public final Apfloat norm_squared() {

        return MyApfloat.fp.add(MyApfloat.fp.multiply(re, re), MyApfloat.fp.multiply(im, im));

    }

    /*
     *  |z|, euclidean norm
     */
    public final Apfloat norm() {

        return MyApfloat.fp.sqrt(MyApfloat.fp.add(MyApfloat.fp.multiply(re, re), MyApfloat.fp.multiply(im, im)));

    }

    /*
     *  |z1 - z2|^2
     */
    public final Apfloat distance_squared(BigComplex z) {

        Apfloat temp_re = MyApfloat.fp.subtract(re, z.re);
        Apfloat temp_im = MyApfloat.fp.subtract(im, z.im);
        return MyApfloat.fp.add(MyApfloat.fp.multiply(temp_re, temp_re), MyApfloat.fp.multiply(temp_im, temp_im));

    }

    /*
     *  |z1 - z2|^2
     */
    public final Apfloat distance_squared(Apfloat rootRe) {

        Apfloat temp_re = MyApfloat.fp.subtract(re, rootRe);
        return MyApfloat.fp.add(MyApfloat.fp.multiply(temp_re, temp_re), MyApfloat.fp.multiply(im, im));

    }

    /*
     *  |z1 - z2|
     */
    public final Apfloat distance(BigComplex z) {

        Apfloat temp_re = MyApfloat.fp.subtract(re, z.re);
        Apfloat temp_im = MyApfloat.fp.subtract(im, z.im);
        return MyApfloat.fp.sqrt(MyApfloat.fp.add(MyApfloat.fp.multiply(temp_re, temp_re), MyApfloat.fp.multiply(temp_im, temp_im)));

    }

    /*
     *  |z1 - z2|
     */
    public final Apfloat distance(Apfloat rootRe) {

        Apfloat temp_re = MyApfloat.fp.subtract(re, rootRe);
        return MyApfloat.fp.sqrt(MyApfloat.fp.add(MyApfloat.fp.multiply(temp_re, temp_re), MyApfloat.fp.multiply(im, im)));

    }

    /*
     *  |Real|
     */
    public final Apfloat getAbsRe() {

        return ApfloatMath.abs(re);

    }

    /*
     *  |Imaginary|
     */
    public final Apfloat getAbsIm() {

        return ApfloatMath.abs(im);

    }

    /*
     *  |Re(z)| + Im(z)i
     */
    public final BigComplex absre() {

        return new BigComplex(ApfloatMath.abs(re), im);

    }

    /*
     *  Re(z) + |Im(z)|i
     */
    public final BigComplex absim() {

        return new BigComplex(re,ApfloatMath.abs(im));

    }

    /*
     *  Real -Imaginary i
     */
    public final BigComplex conjugate() {

        return new BigComplex(re, im.negate());

    }

    /*
     *  -z
     */
    public final BigComplex negative() {

        return new BigComplex(re.negate(), im.negate());

    }

    /*
     *  exp(z) = exp(Re(z)) * (cos(Im(z)) + sin(Im(z))i)
     */
    public final BigComplex exp() {

        Apfloat temp = MyApfloat.exp(re);

        return new BigComplex(MyApfloat.fp.multiply(temp, MyApfloat.cos(im)), MyApfloat.fp.multiply(temp, MyApfloat.sin(im)));

    }


    /*
     *  1 / z
     */
    public final BigComplex reciprocal() {

        Apfloat temp = MyApfloat.fp.add(MyApfloat.fp.multiply(re, re), MyApfloat.fp.multiply(im, im));
        return new BigComplex(MyApfloat.fp.divide(re, temp), MyApfloat.fp.divide(im.negate(), temp));

    }

    /*
     * n-norm
     */
    public final Apfloat nnorm(Apfloat n) {
        return MyApfloat.pow(MyApfloat.fp.add(MyApfloat.pow(ApfloatMath.abs(re), n), MyApfloat.pow(ApfloatMath.abs(im), n)), MyApfloat.fp.divide(MyApfloat.ONE, n));
    }

    /*
     *  abs(z)
     */
    public final BigComplex abs() {

        return new BigComplex(ApfloatMath.abs(re), ApfloatMath.abs(im));

    }

    /*
     *  sin(z) = (exp(iz) - exp(-iz)) / 2i
     */
    public final BigComplex sin() {

        Apfloat temp = MyApfloat.exp(im.negate());

        Apfloat cos_re = MyApfloat.cos(re);
        Apfloat sin_re = MyApfloat.sin(re);

        BigComplex temp2 = new BigComplex(MyApfloat.fp.multiply(temp, cos_re), MyApfloat.fp.multiply(temp, sin_re));

        Apfloat temp3 = MyApfloat.reciprocal(temp);

        BigComplex temp4 = new BigComplex(MyApfloat.fp.multiply(temp3, cos_re), MyApfloat.fp.multiply(temp3, sin_re.negate()));

        return (temp2.sub(temp4)).times_i(new MyApfloat(-0.5));

    }

    /*
     *  cos(z) = (exp(iz) + exp(-iz)) / 2
     */
    public final BigComplex cos() {

        Apfloat temp = MyApfloat.exp(im.negate());

        Apfloat cos_re = MyApfloat.cos(re);
        Apfloat sin_re = MyApfloat.sin(re);

        BigComplex temp2 = new BigComplex(MyApfloat.fp.multiply(temp, cos_re), MyApfloat.fp.multiply(temp, sin_re));

        Apfloat temp3 = MyApfloat.reciprocal(temp);

        BigComplex temp4 = new BigComplex(MyApfloat.fp.multiply(temp3, cos_re), MyApfloat.fp.multiply(temp3, sin_re.negate()));

        return (temp2.plus(temp4)).times(new MyApfloat(0.5));

    }

    public final BigComplex inflection(BigComplex inf) {

        BigComplex diff = this.sub(inf);

        return inf.plus(diff.square());

    }

    public final BigComplex fold_out(BigComplex z2) {

        Apfloat norm_sqr = MyApfloat.fp.add(MyApfloat.fp.multiply(re, re), MyApfloat.fp.multiply(im, im));

        return norm_sqr.compareTo(z2.norm_squared()) > 0 ? this.divide(norm_sqr) : this;

    }

    public final BigComplex fold_in(BigComplex z2) {

        Apfloat norm_sqr = MyApfloat.fp.add(MyApfloat.fp.multiply(re, re), MyApfloat.fp.multiply(im, im));

        return norm_sqr.compareTo(z2.norm_squared()) < 0 ? this.divide(norm_sqr) : this;

    }

    public final BigComplex fold_right(BigComplex z2) {

        return re.compareTo(z2.re) < 0 ? new BigComplex(MyApfloat.fp.add(re, MyApfloat.fp.multiply(MyApfloat.TWO, MyApfloat.fp.subtract(z2.re, re))), im) : this;

    }

    public final BigComplex fold_left(BigComplex z2) {

        return re.compareTo(z2.re) > 0 ? new BigComplex(MyApfloat.fp.subtract(re, MyApfloat.fp.multiply(MyApfloat.TWO, MyApfloat.fp.subtract(re, z2.re))), im) : this;

    }

    public final BigComplex fold_up(BigComplex z2) {

        return im.compareTo(z2.im) < 0 ? new BigComplex(re, MyApfloat.fp.add(im, MyApfloat.fp.multiply(MyApfloat.TWO, MyApfloat.fp.subtract(z2.im, im)))) : this;

    }

    public final BigComplex fold_down(BigComplex z2) {

        return im.compareTo(z2.im) > 0 ? new BigComplex(re,  MyApfloat.fp.subtract(im, MyApfloat.fp.multiply(MyApfloat.TWO, MyApfloat.fp.subtract(im, z2.im)))) : this;

    }

    public final BigComplex shear(BigComplex sh) {

        return new BigComplex(MyApfloat.fp.add(re, MyApfloat.fp.multiply(im, sh.re)), MyApfloat.fp.add(im, MyApfloat.fp.multiply(re, sh.im)));

    }

    public final BigComplex circle_inversion(BigComplex center, Apfloat radius) {

        Apfloat distance = this.distance_squared(center);
        Apfloat radius2 = MyApfloat.fp.multiply(radius, radius);

        Apfloat temp = MyApfloat.fp.divide(radius2, distance);

        return new BigComplex(MyApfloat.fp.multiply(MyApfloat.fp.add(center.re, MyApfloat.fp.subtract(re, center.re)), temp), MyApfloat.fp.multiply(MyApfloat.fp.add(center.im, MyApfloat.fp.subtract(im, center.im)), temp));

    }

    /*
     *  lexicographical comparison between two complex numbers
     * -1 when z1 > z2
     *  1 when z1 < z2
     *  0 when z1 == z2
     */
    public final int compare(BigComplex z2) {

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
     * y + xi
     */
    public final BigComplex flip() {

        return new BigComplex(im, re);

    }


    public static final String toString2(Apfloat real, Apfloat imaginary, Apfloat size) {
        String temp = "";

        real = real.compareTo(MyApfloat.ZERO) == 0 ? MyApfloat.ZERO : real;
        imaginary = imaginary.compareTo(MyApfloat.ZERO) == 0 ? MyApfloat.ZERO : imaginary;

        if (imaginary.compareTo(MyApfloat.ZERO) >= 0) {
            temp = MyApfloat.toString(real, size) + "+" + MyApfloat.toString(imaginary, size) + "i";
        } else {
            temp = MyApfloat.toString(real, size) + "" + MyApfloat.toString(imaginary, size) + "i";
        }
        return temp;
    }

    public static final String toString2Truncated(Apfloat real, Apfloat imaginary, Apfloat size) {
        String temp = "";

        real = real.compareTo(MyApfloat.ZERO) == 0 ? MyApfloat.ZERO : real;
        imaginary = imaginary.compareTo(MyApfloat.ZERO) == 0 ? MyApfloat.ZERO : imaginary;

        if (imaginary.compareTo(MyApfloat.ZERO) >= 0) {
            temp = MyApfloat.toStringTruncated(real, size) + "+" + MyApfloat.toStringTruncated(imaginary, size) + "i";
        } else {
            temp = MyApfloat.toStringTruncated(real, size) + "" + MyApfloat.toStringTruncated(imaginary, size) + "i";
        }
        return temp;
    }

    public static final String toString2Pretty(Apfloat real, Apfloat imaginary, Apfloat size) {
        String temp = "";

        real = real.compareTo(MyApfloat.ZERO) == 0 ? MyApfloat.ZERO : real;
        imaginary = imaginary.compareTo(MyApfloat.ZERO) == 0 ? MyApfloat.ZERO : imaginary;

        if (imaginary.compareTo(MyApfloat.ZERO) >= 0) {
            temp = MyApfloat.toStringPretty(real, size) + "+" + MyApfloat.toStringPretty(imaginary, size) + "i";
        } else {
            temp = MyApfloat.toStringPretty(real, size) + "" + MyApfloat.toStringPretty(imaginary, size) + "i";
        }
        return temp;
    }

    /* more efficient z^2 + c */
    public final BigComplex square_plus_c(BigComplex c) {

        Apfloat temp = MyApfloat.fp.multiply(re, im);

        return new BigComplex(MyApfloat.fp.add(MyApfloat.fp.multiply(MyApfloat.fp.add(re, im), MyApfloat.fp.subtract(re, im)), c.re), MyApfloat.fp.add(MyApfloat.fp.add(temp, temp), c.im));

    }


    @Override
    public final String toString() {

        String temp = "";

        if(im.compareTo(MyApfloat.ZERO) > 0) {
            temp = re + "+" + im + "i";
        }
        else if (im.compareTo(MyApfloat.ZERO) == 0) {
            temp = re + "+" + (0.0) + "i";
        } else {
            temp = re + "" + im + "i";
        }

        return temp;

    }

    public final String toStringPretty() {

        String temp = "";

        if(im.compareTo(MyApfloat.ZERO) > 0) {
            temp = re.toString(true) + "+" + im.toString(true) + "i";
        }
        else if (im.compareTo(MyApfloat.ZERO) == 0) {
            temp = re.toString(true) + "+" + (0.0) + "i";
        } else {
            temp = re.toString(true) + "" + im.toString(true) + "i";
        }

        return temp;

    }

    public final boolean isZero() {
        return re.compareTo(Apfloat.ZERO) == 0 && im.compareTo(Apfloat.ZERO) == 0;
    }


    public static void main(String[] args) {


        MyApfloat.precision = 1500;
        MyApfloat.fp = new FixedPrecisionApfloatHelper( MyApfloat.precision);
        BigComplex c1 = new BigComplex(new MyApfloat("-1.76856539435366368125259371293453236892641782036550238084035683278139847516020579836940755448093853806358532335622720215954860387925973462676210264185332619249622446097144464301696263314670575799470331597794419853481280083049813344714412669978249805531445637915677921147242961218729448326140934509447244923346834491904750058368493978125046376837105104088610590469955376290684019988563370767077290823529345255614107999427775332199891983963556882307645602243231590462852451961554663143998042396613582745038196155573683320524468218885621413135815233178922623800880270744324820393949165276400291219551742132035215660506960425009699199467178686542633858457116215710847955765911389610660640760093031838759495011431952174690031733085134861830812337744591371152885478077354772622020072507170087102831622232514924494081112032259257747492310876060757910257868803734144196405678120512443001786915069999243567634982118143679073365001032947680409484031048637779321233465936777391817115730373775376125971055725674328445312481196980698269861097741754301242350002619529351486080897755930259555714557011668880546952923003933637167679746220379199085840523329826374660"), new MyApfloat("0.00149693415390767795776818884840489556855946301445691471574014563855527433886417969977385819538260268120841953162872636930325763746322273045770475720864573841501787930094585669029854545526055550254240550601638349230447392478835897915689588386917873306732459133130195499040290663241163281171562214964938877814041525983714426684720617999806166857035264185620487882712073265176954914054913266203287997924901540871019242527521230712886590484380712839459054394699971951683593643432733875864612142164058384584027531954686991700717520592706134315477867770419967332102686480959769035927998828366145957010260008071330081671951130257876517738836139132327131150083875547829353693231330986024536074662266149266972020406424662729505261246207754916338512723205243386084554727716044392705072728590247105881028092304993724655676823686703579759639901910397135711042548453158584111749222905493046484296618244721966973379997931675069363108125568864266991641443350605262290076130999673222331940884558082142583551902556005768303536299446355536559649684565312212482597275388117026700207573378170627060834006934127513560312023382257072757055987599151386137785304306581858"));
        //Apcomplex c3 = new Apcomplex(new Apfloat("-1.76856539435366368125259371293453236892641782036550238084035683278139847516020579836940755448093853806358532335622720215954860387925973462676210264185332619249622446097144464301696263314670575799470331597794419853481280083049813344714412669978249805531445637915677921147242961218729448326140934509447244923346834491904750058368493978125046376837105104088610590469955376290684019988563370767077290823529345255614107999427775332199891983963556882307645602243231590462852451961554663143998042396613582745038196155573683320524468218885621413135815233178922623800880270744324820393949165276400291219551742132035215660506960425009699199467178686542633858457116215710847955765911389610660640760093031838759495011431952174690031733085134861830812337744591371152885478077354772622020072507170087102831622232514924494081112032259257747492310876060757910257868803734144196405678120512443001786915069999243567634982118143679073365001032947680409484031048637779321233465936777391817115730373775376125971055725674328445312481196980698269861097741754301242350002619529351486080897755930259555714557011668880546952923003933637167679746220379199085840523329826374660", 1200), new Apfloat("0.00149693415390767795776818884840489556855946301445691471574014563855527433886417969977385819538260268120841953162872636930325763746322273045770475720864573841501787930094585669029854545526055550254240550601638349230447392478835897915689588386917873306732459133130195499040290663241163281171562214964938877814041525983714426684720617999806166857035264185620487882712073265176954914054913266203287997924901540871019242527521230712886590484380712839459054394699971951683593643432733875864612142164058384584027531954686991700717520592706134315477867770419967332102686480959769035927998828366145957010260008071330081671951130257876517738836139132327131150083875547829353693231330986024536074662266149266972020406424662729505261246207754916338512723205243386084554727716044392705072728590247105881028092304993724655676823686703579759639901910397135711042548453158584111749222905493046484296618244721966973379997931675069363108125568864266991641443350605262290076130999673222331940884558082142583551902556005768303536299446355536559649684565312212482597275388117026700207573378170627060834006934127513560312023382257072757055987599151386137785304306581858", 1200));


        long runs = 100000;


        BigComplex z;

        z = new BigComplex();
        for(long i = 0; i < runs; i++) {
            z = z.square_plus_c(c1);
        }

        z = new BigComplex();
        for(long i = 0; i < runs; i++) {
           // z = z.square_plus_c2(c1);
        }

        z = new BigComplex();
        for(long i = 0; i < runs; i++) {
            z = z.square_plus_c(c1);
        }

        z = new BigComplex();
        for(long i = 0; i < runs; i++) {
            //z = z.square_plus_c2(c1);
        }


        z = new BigComplex();
        long time = System.currentTimeMillis();
        for(long i = 0; i < runs; i++) {
            z = z.square_plus_c(c1);
        }
        System.out.println(System.currentTimeMillis() - time);
        System.out.println(z.getRe().precision() + " " + z.getIm().precision());
        System.out.println(z);

        z = new BigComplex();
        time = System.currentTimeMillis();
        for(long i = 0; i < runs; i++) {
            //z = z.square_plus_c2(c1);
        }

        System.out.println(System.currentTimeMillis() - time);
        System.out.println(z.getRe().precision() + " " + z.getIm().precision());
        System.out.println(z);




    }
}
