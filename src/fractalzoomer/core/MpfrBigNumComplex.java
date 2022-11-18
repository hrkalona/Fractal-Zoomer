package fractalzoomer.core;

import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpfr.mpfr_t;
import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

public class MpfrBigNumComplex extends GenericComplex {
    private MpfrBigNum re;
    private MpfrBigNum im;

    public MpfrBigNumComplex(BigComplex c) {
        re = new MpfrBigNum(c.getRe());
        im = new MpfrBigNum(c.getIm());
    }

    public MpfrBigNumComplex(double re, double im) {
        this.re = new MpfrBigNum(re);
        this.im = new MpfrBigNum(im);
    }

    public MpfrBigNumComplex(int re, int im) {
        this.re = new MpfrBigNum(re);
        this.im = new MpfrBigNum(im);
    }

    //Does Copy
    public MpfrBigNumComplex(MpfrBigNumComplex c) {
        re = new MpfrBigNum(c.getRe());
        im = new MpfrBigNum(c.getIm());
    }

    public MpfrBigNumComplex(Complex c) {
        re = new MpfrBigNum(c.getRe());
        im = new MpfrBigNum(c.getIm());
    }

    public MpfrBigNumComplex(MpfrBigNum re, MpfrBigNum im) {
        this.re = re;
        this.im = im;
    }

    public MpfrBigNumComplex(mpfr_t rePeer, mpfr_t imPeer) {

        re = new MpfrBigNum(rePeer);
        im = new MpfrBigNum(imPeer);

    }

    public MpfrBigNumComplex(Apfloat re, Apfloat im) {
        this.re = new MpfrBigNum(re);
        this.im = new MpfrBigNum(im);
    }

    public MpfrBigNumComplex() {

        re = new MpfrBigNum();
        im = new MpfrBigNum();

    }

    public MpfrBigNumComplex(String reStr, String imStr) {

        re = new MpfrBigNum(reStr);
        im = new MpfrBigNum(imStr);

    }

    @Override
    public MpfrBigNumComplex toMpfrBigNumComplex() { return this; }

    public final MpfrBigNum getRe() {

        return re;

    }

    public final MpfrBigNum getIm() {

        return im;

    }

    public final void setRe(MpfrBigNum re) {

        this.re = re;

    }

    public final void setIm(MpfrBigNum im) {

        this.im = im;

    }

    @Override
    public final Complex toComplex() {
        return new Complex(re.doubleValue(), im.doubleValue());
    }

    /*
     * z1 + z2
     */
    public final MpfrBigNumComplex plus(MpfrBigNumComplex z) {

        return new MpfrBigNumComplex(re.add(z.re), im.add(z.im));

    }


    public final MpfrBigNumComplex plus_mutable(MpfrBigNumComplex z) {

        re.add(z.re, re);
        im.add(z.im, im);

        return this;

    }

    public final MpfrBigNumComplex plus(double value) {

        return new MpfrBigNumComplex(re.add(value), new MpfrBigNum(im));

    }

    @Override
    public final MpfrBigNumComplex plus(int value) {

        return new MpfrBigNumComplex(re.add(value), new MpfrBigNum(im));

    }

    public final MpfrBigNumComplex plus_mutable(double value) {

        re.add(value, re);
        return this;

    }

    @Override
    public final MpfrBigNumComplex plus_mutable(int value) {

        re.add(value, re);
        return this;

    }

    /*
     *  z + Real
     */
    public final MpfrBigNumComplex plus(MpfrBigNum number) {

        return new MpfrBigNumComplex(re.add(number), new MpfrBigNum(im));

    }

    public final MpfrBigNumComplex plus_mutable(MpfrBigNum number) {

        re.add(number, re);
        return this;

    }

    /*
     *  z + Imaginary
     */
    public final MpfrBigNumComplex plus_i(MpfrBigNum number) {

        return new MpfrBigNumComplex(new MpfrBigNum(re), im.add(number));

    }

    public final MpfrBigNumComplex plus_i_mutable(MpfrBigNum number) {

        im.add(number, im);
        return this;

    }

    public final MpfrBigNumComplex plus_i(double number) {

        return new MpfrBigNumComplex(new MpfrBigNum(re), im.add(number));

    }

    public final MpfrBigNumComplex plus_i(int number) {

        return new MpfrBigNumComplex(new MpfrBigNum(re), im.add(number));

    }

    public final MpfrBigNumComplex plus_i_mutable(double number) {

        im.add(number, im);
        return this;

    }

    public final MpfrBigNumComplex plus_i_mutable(int number) {

        im.add(number, im);
        return this;

    }

    /*
     *  z1 - z2
     */
    public final MpfrBigNumComplex sub(MpfrBigNumComplex z) {

        return  new MpfrBigNumComplex(re.sub(z.re), im.sub(z.im));

    }

    public final MpfrBigNumComplex sub_mutable(MpfrBigNumComplex z) {

        re.sub(z.re, re);
        im.sub(z.im, im);
        return this;

    }

    public final MpfrBigNumComplex sub(double val) {

        return new MpfrBigNumComplex(re.sub(val), new MpfrBigNum(im));

    }

    public final MpfrBigNumComplex sub_mutable(double val) {

        re.sub(val, re);
        return this;

    }

    /*
     *  z - Imaginary
     */
    public final MpfrBigNumComplex sub_i(MpfrBigNum number) {

        return new MpfrBigNumComplex(new MpfrBigNum(re), im.sub(number));

    }
    public final MpfrBigNumComplex sub_i_mutable(MpfrBigNum number) {

        im.sub(number, im);
        return this;

    }

    public final MpfrBigNumComplex sub_i(double number) {

        return new MpfrBigNumComplex(new MpfrBigNum(re), im.sub(number));

    }
    public final MpfrBigNumComplex sub_i_mutable(double number) {

        im.sub(number, im);
        return this;

    }

    public final MpfrBigNumComplex sub_i(int number) {

        return new MpfrBigNumComplex(new MpfrBigNum(re), im.sub(number));

    }
    public final MpfrBigNumComplex sub_i_mutable(int number) {

        im.sub(number, im);
        return this;

    }

    @Override
    public final MpfrBigNumComplex sub(int val) {

        return new MpfrBigNumComplex(re.sub(val), new MpfrBigNum(im));

    }

    @Override
    public final MpfrBigNumComplex sub_mutable(int val) {

        re.sub(val, re);
        return this;

    }

    /*
     *  z - Real, Mutable
     */
    public final MpfrBigNumComplex sub_mutable(MpfrBigNum number) {

        re.sub(number, re);
        return this;

    }


    /*
     *  z - Real
     */
    public final MpfrBigNumComplex sub(MpfrBigNum number) {

        return  new MpfrBigNumComplex(re.sub(number), new MpfrBigNum(im));

    }

    /*
     *  Real - z1
     */
    public final MpfrBigNumComplex r_sub(MpfrBigNum number) {

        return  new MpfrBigNumComplex(number.sub(re), im.negate());

    }

    public final MpfrBigNumComplex r_sub_mutable(MpfrBigNum number) {

        number.sub(re, re);
        im.negate(im);
        return this;

    }

    public final MpfrBigNumComplex r_sub(double number) {

        return new MpfrBigNumComplex(re.r_sub(number), im.negate());

    }

    public final MpfrBigNumComplex r_sub_mutable(double number) {

        re.r_sub(number, re);
        im.negate(im);
        return this;

    }

    public final MpfrBigNumComplex r_sub(int number) {

        return new MpfrBigNumComplex(re.r_sub(number), im.negate());

    }

    public final MpfrBigNumComplex r_sub_mutable(int number) {

        re.r_sub(number, re);
        im.negate(im);
        return this;

    }

    /*
     *  Imaginary - z
     */
    public final MpfrBigNumComplex i_sub(MpfrBigNum number) {

        return  new MpfrBigNumComplex(re.negate(), number.sub(im));

    }

    public final MpfrBigNumComplex i_sub_mutable(MpfrBigNum number) {

        re.negate(re);
        number.sub(im, im);
        return this;

    }

    public final MpfrBigNumComplex i_sub(double number) {

        return  new MpfrBigNumComplex(re.negate(), im.r_sub(number));

    }

    public final MpfrBigNumComplex i_sub_mutable(double number) {

        re.negate(re);
        im.r_sub(number, im);
        return this;

    }

    public final MpfrBigNumComplex i_sub(int number) {

        return  new MpfrBigNumComplex(re.negate(), im.r_sub(number));

    }

    public final MpfrBigNumComplex i_sub_mutable(int number) {

        re.negate(re);
        im.r_sub(number, im);
        return this;

    }

    /*
     *  z1 * z2
     */
    public final MpfrBigNumComplex times(MpfrBigNumComplex z) {

        MpfrBigNum tempRe = re.mult(z.re);
        MpfrBigNum tempRe2 = im.mult(z.im);
        tempRe.sub(tempRe2, tempRe);

        MpfrBigNum tempIm = re.mult(z.im);
        MpfrBigNum tempIm2 = im.mult(z.re);
        tempIm.add(tempIm2, tempIm);

        return new MpfrBigNumComplex(tempRe, tempIm);

    }

    public final MpfrBigNumComplex times_mutable(MpfrBigNumComplex z) {

        MpfrBigNum tempRe = new MpfrBigNum(re);
        MpfrBigNum tempIm = new MpfrBigNum(im);

        tempRe = tempRe.mult(z.re, tempRe);
        tempIm = tempIm.mult(z.im, tempIm);
        tempRe = tempRe.sub(tempIm, tempRe);

        re.mult(z.im, re);
        im.mult(z.re, im);
        im.add(re, im);

        re = tempRe;

        return this;

    }

    /*
     *  z1 * Real
     */public final MpfrBigNumComplex times(MpfrBigNum number) {

        return new MpfrBigNumComplex(re.mult(number), im.mult(number));

    }

    public final MpfrBigNumComplex times(double number) {

        return new MpfrBigNumComplex(re.mult(number), im.mult(number));

    }

    @Override
    public final MpfrBigNumComplex times(int number) {

        return new MpfrBigNumComplex(re.mult(number), im.mult(number));

    }

    public final MpfrBigNumComplex times_mutable(MpfrBigNum number) {

        re.mult(number, re);
        im.mult(number, im);
        return this;

    }

    public final MpfrBigNumComplex times_mutable(double number) {

        re.mult(number, re);
        im.mult(number, im);
        return this;

    }

    @Override
    public final MpfrBigNumComplex times_mutable(int number) {

        re.mult(number, re);
        im.mult(number, im);
        return this;

    }

    /*
     *  z * Imaginary
     */
    public final MpfrBigNumComplex times_i(MpfrBigNum number) {

        return new MpfrBigNumComplex(im.negate().mult(number), re.mult(number));

    }

    public final MpfrBigNumComplex times_i(double number) {

        return new MpfrBigNumComplex(im.negate().mult(number), re.mult(number));

    }

    public final MpfrBigNumComplex times_i(int number) {

        return new MpfrBigNumComplex(im.negate().mult(number), re.mult(number));

    }

    public final MpfrBigNumComplex times_i_mutable(MpfrBigNum number) {

        im.negate(im);
        im.mult(number, im);
        re.mult(number, re);

        MpfrBigNum temp = re;
        re = im;
        im = temp;

        return this;

    }

    public final MpfrBigNumComplex times_i_mutable(double number) {

        im.negate(im);
        im.mult(number, im);
        re.mult(number, re);

        MpfrBigNum temp = re;
        re = im;
        im = temp;

        return this;

    }

    public final MpfrBigNumComplex times_i_mutable(int number) {

        im.negate(im);
        im.mult(number, im);
        re.mult(number, re);

        MpfrBigNum temp = re;
        re = im;
        im = temp;

        return this;

    }

    /*
     *  z^2
     */
    @Override
    public final MpfrBigNumComplex square() {
        MpfrBigNum tempIm = re.mult(im);
        tempIm.mult2(tempIm);

        MpfrBigNum tempRe = re.add(im);
        MpfrBigNum tempRe2 = re.sub(im);
        tempRe.mult(tempRe2, tempRe);

        return new MpfrBigNumComplex(tempRe, tempIm);
    }

    @Override
    public final MpfrBigNumComplex square_mutable() {

        MpfrBigNum tempRe = re.add(im);
        MpfrBigNum tempRe2 = re.sub(im);
        tempRe.mult(tempRe2, tempRe);

        re.mult(im, im);
        im.mult2(im);

        re = tempRe;

        return this;
    }

    /*
     *  z^2 + c
     */
    public final MpfrBigNumComplex squareFast_plus_c_non_mutable(NormComponents normComponents, GenericComplex ca) {
        MpfrBigNum reSqr = (MpfrBigNum) normComponents.reSqr;
        MpfrBigNum imSqr = (MpfrBigNum) normComponents.imSqr;
        MpfrBigNum normSquared = (MpfrBigNum) normComponents.normSquared;
        MpfrBigNumComplex c = (MpfrBigNumComplex) ca;

        MpfrBigNum tempRe = new MpfrBigNum(reSqr);
        tempRe.sub(imSqr, tempRe);
        tempRe.add(c.re, tempRe);

        MpfrBigNum tempIm = new MpfrBigNum(re);
        tempIm.add(im, tempIm);
        tempIm.square(tempIm);
        tempIm.sub(normSquared, tempIm);
        tempIm.add(c.im, tempIm);

        return new MpfrBigNumComplex(tempRe, tempIm);
    }

    /*
     *  z^2 + c, Mutable for performance
     */
    @Override
    public final MpfrBigNumComplex squareFast_plus_c(NormComponents normComponents, GenericComplex ca) {
        MpfrBigNum reSqr = (MpfrBigNum) normComponents.reSqr;
        MpfrBigNum imSqr = (MpfrBigNum) normComponents.imSqr;
        MpfrBigNum normSquared = (MpfrBigNum) normComponents.normSquared;
        MpfrBigNumComplex c = (MpfrBigNumComplex) ca;

        MpfrBigNum tempRe = (MpfrBigNum) normComponents.tempRe;
        reSqr.sub(imSqr, tempRe);
        tempRe.add(c.re, tempRe);

        MpfrBigNum tempIm = (MpfrBigNum) normComponents.tempIm;
        re.add(im, tempIm);
        tempIm.square(tempIm);
        tempIm.sub(normSquared, tempIm);
        tempIm.add(c.im, im);

        re.set(tempRe);

        return this;
    }

    /*
     *  z^2 , Mutable for performance
     */
    @Override
    public final MpfrBigNumComplex squareFast(NormComponents normComponents) {
        MpfrBigNum reSqr = (MpfrBigNum) normComponents.reSqr;
        MpfrBigNum imSqr = (MpfrBigNum) normComponents.imSqr;
        MpfrBigNum normSquared = (MpfrBigNum) normComponents.normSquared;

        MpfrBigNum tempRe = (MpfrBigNum) normComponents.tempRe;
        reSqr.sub(imSqr, tempRe);

        MpfrBigNum tempIm = (MpfrBigNum) normComponents.tempIm;
        re.add(im, tempIm);
        tempIm.square(tempIm);
        tempIm.sub(normSquared, im);

        re.set(tempRe);

        return this;
    }

    /*
     *  z^2
     */
    @Override
    public final MpfrBigNumComplex squareFast_non_mutable(NormComponents normComponents) {
        MpfrBigNum reSqr = (MpfrBigNum) normComponents.reSqr;
        MpfrBigNum imSqr = (MpfrBigNum) normComponents.imSqr;
        MpfrBigNum normSquared = (MpfrBigNum) normComponents.normSquared;

        MpfrBigNum tempRe = new MpfrBigNum(reSqr);
        tempRe.sub(imSqr, tempRe);

        MpfrBigNum tempIm = new MpfrBigNum(re);
        tempIm.add(im, tempIm);
        tempIm.square(tempIm);
        tempIm.sub(normSquared, tempIm);

        return new MpfrBigNumComplex(tempRe, tempIm);
    }

    /*
     *  z^3, Mutable
     */
    @Override
    public final MpfrBigNumComplex cubeFast(NormComponents normComponents) {

        MpfrBigNum temp = (MpfrBigNum)normComponents.reSqr;
        MpfrBigNum temp2 = (MpfrBigNum)normComponents.imSqr;

        MpfrBigNum tempRe = (MpfrBigNum) normComponents.tempRe;
        temp2.mult(3, tempRe);
        temp.sub(tempRe, tempRe);
        re.mult(tempRe, tempRe);

        MpfrBigNum tempIm = (MpfrBigNum) normComponents.tempIm;
        temp.mult(3, tempIm);
        tempIm.sub(temp2, tempIm);
        im.mult(tempIm, im);

        re.set(tempRe);

        return this;

    }

    public final MpfrBigNumComplex cubeFast_non_mutable(NormComponents normComponents) {

        MpfrBigNum temp = (MpfrBigNum)normComponents.reSqr;
        MpfrBigNum temp2 = (MpfrBigNum)normComponents.imSqr;

        MpfrBigNum tempRe = temp2.mult(3);
        temp.sub(tempRe, tempRe);
        tempRe.mult(re, tempRe);

        MpfrBigNum tempIm = temp.mult(3);
        tempIm.sub(temp2, tempIm);
        tempIm.mult(im, tempIm);

        return new MpfrBigNumComplex(tempRe, tempIm);

    }

    /*
     *  z^4
     */
    @Override
    public final MpfrBigNumComplex fourthFast(NormComponents normComponents) {

        MpfrBigNum temp = (MpfrBigNum)normComponents.reSqr;
        MpfrBigNum temp2 = (MpfrBigNum)normComponents.imSqr;

        MpfrBigNum tempRe = (MpfrBigNum) normComponents.tempRe;
        temp2.mult(6, tempRe);
        temp.sub(tempRe, tempRe);
        temp.mult(tempRe, tempRe);

        MpfrBigNum temp1 = (MpfrBigNum) normComponents.temp1;
        temp2.square(temp1);
        tempRe.add(temp1, tempRe);


        MpfrBigNum tempIm = (MpfrBigNum) normComponents.tempIm;
        re.mult(im, tempIm);
        tempIm.mult4(tempIm);

        temp.sub(temp2, temp1);
        tempIm.mult(temp1, im);

        re.set(tempRe);

        return this;

    }

    public final MpfrBigNumComplex fourthFast_non_mutable(NormComponents normComponents) {

        MpfrBigNum temp = (MpfrBigNum)normComponents.reSqr;
        MpfrBigNum temp2 = (MpfrBigNum)normComponents.imSqr;

        temp.mult(temp.sub(temp2.mult(6))).add(temp2.square());

        MpfrBigNum tempRe = temp2.mult(6);
        temp.sub(tempRe, tempRe);
        temp.mult(tempRe, tempRe);
        tempRe.add(temp2.square(), tempRe);

        MpfrBigNum tempIm = re.mult(im);
        tempIm.mult4(tempIm);
        tempIm.mult(temp.sub(temp2), tempIm);


        return new MpfrBigNumComplex(tempRe, tempIm);

    }

    /*
     *  z^5
     */
    @Override
    public final MpfrBigNumComplex fifthFast(NormComponents normComponents) {

        MpfrBigNum temp = (MpfrBigNum)normComponents.reSqr;
        MpfrBigNum temp2 = (MpfrBigNum)normComponents.imSqr;

        MpfrBigNum tempRe = (MpfrBigNum) normComponents.tempRe;
        temp.mult(10, tempRe);

        MpfrBigNum temp1 = (MpfrBigNum) normComponents.temp1;
        temp2.mult(5, temp1);
        temp1.sub(tempRe, tempRe);
        temp.square(temp1);
        temp1.add(tempRe, tempRe);
        re.mult(tempRe, tempRe);

        MpfrBigNum tempIm = (MpfrBigNum) normComponents.tempIm;
        temp2.mult(10, tempIm);
        temp.mult(5, temp1);
        temp1.sub(tempRe, tempRe);
        temp2.square(temp1);
        temp1.add(tempIm, tempIm);
        im.mult(tempIm, im);

        re.set(tempRe);

        return this;

    }

    public final MpfrBigNumComplex fifthFast_non_mutable(NormComponents normComponents) {

        MpfrBigNum temp = (MpfrBigNum)normComponents.reSqr;
        MpfrBigNum temp2 = (MpfrBigNum)normComponents.imSqr;

        MpfrBigNum tempRe = temp.mult(10);
        temp2.mult(5).sub(tempRe, tempRe);
        temp2.mult(tempRe, tempRe);
        temp.square().add(tempRe, tempRe);
        re.mult(tempRe, tempRe);

        MpfrBigNum tempIm = temp2.mult(10);
        temp.mult(5).sub(tempIm, tempIm);
        temp.mult(tempIm, tempIm);
        temp2.square().add(tempIm, tempIm);
        im.mult(tempIm, tempIm);

        return new MpfrBigNumComplex(tempRe, tempIm);
    }

    @Override
    public NormComponents normSquaredWithComponents(NormComponents n) {

        if(n == null) {
            MpfrBigNum reSqr = re.square();
            MpfrBigNum imSqr = im.square();
            MpfrBigNum tempRe = new MpfrBigNum();
            MpfrBigNum tempIm = new MpfrBigNum();
            MpfrBigNum temp1 = new MpfrBigNum();
            return new NormComponents(reSqr, imSqr, reSqr.add(imSqr), tempRe, tempIm, temp1);
        }

        MpfrBigNum reSqr = (MpfrBigNum)n.reSqr;
        MpfrBigNum imSqr = (MpfrBigNum)n.imSqr;
        MpfrBigNum normSquared = (MpfrBigNum)n.normSquared;

        re.square(reSqr);
        im.square(imSqr);
        reSqr.add(imSqr, normSquared);

        return n;
    }

    /*
     *  |z|, euclidean norm
     */
    public final MpfrBigNum norm() {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();

        reSqr.add(imSqr, reSqr);
        reSqr.sqrt(reSqr);

        return reSqr;

    }

    /*
     *  |z|^2
     */
    public final MpfrBigNum norm_squared() {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();

        reSqr.add(imSqr, reSqr);

        return reSqr;

    }

    /*
     *  |z1 - z2|^2
     */
    public final MpfrBigNum distance_squared(MpfrBigNumComplex z) {

        MpfrBigNum temp_re = re.sub(z.re);
        MpfrBigNum temp_im = im.sub(z.im);
        temp_re.square(temp_re);
        temp_im.square(temp_im);
        temp_re.add(temp_im, temp_re);
        return temp_re;

    }

    /*
     *  |z1 - z2|^2
     */
    public final MpfrBigNum distance_squared(MpfrBigNum rootRe) {

        MpfrBigNum temp_re = re.sub(rootRe);
        temp_re.square(temp_re);
        temp_re.add(im.square(), temp_re);
        return temp_re;

    }

    /*
     *  |z1 - z2|
     */
    public final MpfrBigNum distance(MpfrBigNumComplex z) {

        MpfrBigNum temp_re = re.sub(z.re);
        MpfrBigNum temp_im = im.sub(z.im);
        temp_re.square(temp_re);
        temp_im.square(temp_im);
        temp_re.add(temp_im, temp_re);
        temp_re.sqrt(temp_re);
        return temp_re;

    }

    /*
     *  |z1 - z2|
     */
    public final MpfrBigNum distance(MpfrBigNum rootRe) {

        MpfrBigNum temp_re = re.sub(rootRe);
        temp_re.square(temp_re);
        temp_re.add(im.square(), temp_re);
        temp_re.sqrt(temp_re);
        return temp_re;

    }

    /*
     *  |Real|
     */
    public final MpfrBigNum getAbsRe() {

        return re.abs();

    }

    /*
     *  |Imaginary|
     */
    public final MpfrBigNum getAbsIm() {

        return im.abs();

    }

    /*
     *  |Re(z)| + Im(z)i
     */
    public final MpfrBigNumComplex absre() {

        return new MpfrBigNumComplex(re.abs(), new MpfrBigNum(im));

    }

    public final MpfrBigNumComplex absre_mutable() {

        re.abs(re);
        return this;

    }

    /*
     *  Re(z) + |Im(z)|i
     */
    public final MpfrBigNumComplex absim() {

        return new MpfrBigNumComplex(new MpfrBigNum(re), im.abs());

    }

    public final MpfrBigNumComplex absim_mutable() {

        im.abs(im);
        return this;

    }

    /*
     *  Real -Imaginary i, mutable
     */
    @Override
    public final MpfrBigNumComplex conjugate() {

        im.negate(im);
        return this;

    }

    public final MpfrBigNumComplex conjugate_non_mutable() {

        return new MpfrBigNumComplex(new MpfrBigNum(re), im.negate());

    }

    /*
     *  -z
     */
    @Override
    public final MpfrBigNumComplex negative() {

        return new MpfrBigNumComplex(re.negate(), im.negate());

    }

    @Override
    public final MpfrBigNumComplex negative_mutable() {

        re.negate(re);
        im.negate(im);
        return this;

    }

    /*
     *  z^3
     */
    @Override
    public final MpfrBigNumComplex cube() {

        MpfrBigNum temp = re.square();
        MpfrBigNum temp2 = im.square();

        MpfrBigNum tempRe = temp2.mult(3);
        temp.sub(tempRe, tempRe);
        tempRe.mult(re, tempRe);

        MpfrBigNum tempIm = temp.mult(3);
        tempIm.sub(temp2, tempIm);
        tempIm.mult(im, tempIm);

        return new MpfrBigNumComplex(tempRe, tempIm);

    }

    public final MpfrBigNumComplex cube_mutable() {

        MpfrBigNum temp = re.square();
        MpfrBigNum temp2 = im.square();

        MpfrBigNum tempRe = temp2.mult(3);
        temp.sub(tempRe, tempRe);
        tempRe.mult(re, re);

        MpfrBigNum tempIm = temp.mult(3);
        tempIm.sub(temp2, tempIm);
        tempIm.mult(im, im);

        return this;

    }

    /*
     *  z^4
     */
    @Override
    public final MpfrBigNumComplex fourth() {

        MpfrBigNum temp = re.square();
        MpfrBigNum temp2 = im.square();

        temp.mult(temp.sub(temp2.mult(6))).add(temp2.square());

        MpfrBigNum tempRe = temp2.mult(6);
        temp.sub(tempRe, tempRe);
        temp.mult(tempRe, tempRe);
        tempRe.add(temp2.square(), tempRe);

        MpfrBigNum tempIm = re.mult(im);
        tempIm.mult4(tempIm);
        tempIm.mult(temp.sub(temp2), tempIm);


        return new MpfrBigNumComplex(tempRe, tempIm);

    }

    public final MpfrBigNumComplex fourth_mutable() {

        MpfrBigNum temp = re.square();
        MpfrBigNum temp2 = im.square();

        temp.mult(temp.sub(temp2.mult(6))).add(temp2.square());

        MpfrBigNum tempRe = temp2.mult(6);
        temp.sub(tempRe, tempRe);
        temp.mult(tempRe, tempRe);
        tempRe.add(temp2.square(), tempRe);

        MpfrBigNum tempIm = re.mult(im);
        tempIm.mult4(tempIm);
        tempIm.mult(temp.sub(temp2), im);

        re = tempRe;


        return this;

    }


    /*
     *  z^5
     */
    @Override
    public final MpfrBigNumComplex fifth() {

        MpfrBigNum temp = re.square();
        MpfrBigNum temp2 = im.square();

        MpfrBigNum tempRe = temp.mult(10);
        temp2.mult(5).sub(tempRe, tempRe);
        temp2.mult(tempRe, tempRe);
        temp.square().add(tempRe, tempRe);
        re.mult(tempRe, tempRe);

        MpfrBigNum tempIm = temp2.mult(10);
        temp.mult(5).sub(tempIm, tempIm);
        temp.mult(tempIm, tempIm);
        temp2.square().add(tempIm, tempIm);
        im.mult(tempIm, tempIm);

        return new MpfrBigNumComplex(tempRe, tempIm);

    }

    public final MpfrBigNumComplex fifth_mutable() {

        MpfrBigNum temp = re.square();
        MpfrBigNum temp2 = im.square();

        MpfrBigNum tempRe = temp.mult(10);
        temp2.mult(5).sub(tempRe, tempRe);
        temp2.mult(tempRe, tempRe);
        temp.square().add(tempRe, tempRe);
        re.mult(tempRe, tempRe);

        MpfrBigNum tempIm = temp2.mult(10);
        temp.mult(5).sub(tempIm, tempIm);
        temp.mult(tempIm, tempIm);
        temp2.square().add(tempIm, tempIm);
        im.mult(tempIm, im);

        re = tempRe;

        return this;

    }

    /*
     *  abs(z)
     */
    public final MpfrBigNumComplex abs_non_mutable() {

        return new MpfrBigNumComplex(re.abs(), im.abs());

    }

    /*
     *  abs(z), mutable
     */
    @Override
    public final MpfrBigNumComplex abs() {

        re.abs(re);
        im.abs(im);
        return this;

    }

    /* more efficient z^2 + c */
    public final MpfrBigNumComplex square_plus_c(MpfrBigNumComplex c) {

        MpfrBigNum temp = re.mult(im);

        return new MpfrBigNumComplex(re.add(im).mult(re.sub(im)).add(c.re), temp.mult2().add(c.im));

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
     *  z1 - z2
     */
    @Override
    public final MpfrBigNumComplex sub(GenericComplex zn) {
        MpfrBigNumComplex z = (MpfrBigNumComplex)zn;

        return new MpfrBigNumComplex(re.sub(z.re), im.sub(z.im));

    }


    /*
     *  z1 = z1 - z2
     */
    @Override
    public final MpfrBigNumComplex sub_mutable(GenericComplex zn) {
        MpfrBigNumComplex z = (MpfrBigNumComplex)zn;

        re = re.sub(z.re, re);
        im = im.sub(z.im, im);

        return this;

    }

    /*
     * z1 + z2
     */
    @Override
    public final MpfrBigNumComplex plus(GenericComplex zn) {

        MpfrBigNumComplex z = (MpfrBigNumComplex)zn;
        return new MpfrBigNumComplex(re.add(z.re), im.add(z.im));

    }

    /*
     * z1 = z1 + z2
     */
    @Override
    public final MpfrBigNumComplex plus_mutable(GenericComplex zn) {

        MpfrBigNumComplex z = (MpfrBigNumComplex)zn;

        re = re.add(z.re, re);
        im = im.add(z.im, im);

        return this;

    }

    /* more efficient z^2 + c, Mutable for performance */
    @Override
    public final MpfrBigNumComplex square_plus_c(GenericComplex cn) {

        MpfrBigNumComplex c = (MpfrBigNumComplex)cn;

        MpfrBigNum temp = re.mult(im);
        temp.mult2(temp);
        temp.add(c.im, temp);


        MpfrBigNum temp2 = new MpfrBigNum(re);
        temp2.add(im, temp2);

        MpfrBigNum temp3 = re.sub(im);

        temp2.mult(temp3, temp2);
        temp2.add(c.re, temp2);

        return new MpfrBigNumComplex(temp2, temp);

    }

    /*
     *  z1 * z2
     */
    @Override
    public final MpfrBigNumComplex times(GenericComplex zn) {
        MpfrBigNumComplex z = (MpfrBigNumComplex)zn;

        MpfrBigNum tempRe = re.mult(z.re);
        MpfrBigNum tempRe2 = im.mult(z.im);
        tempRe.sub(tempRe2, tempRe);

        MpfrBigNum tempIm = re.mult(z.im);
        MpfrBigNum tempIm2 = im.mult(z.re);
        tempIm.add(tempIm2, tempIm);

        return new MpfrBigNumComplex(tempRe, tempIm);

    }

    /*
     *  z1 = z1 * z2
     */
    public final MpfrBigNumComplex times_mutable(MpfrBigNumComplex z, MpfrBigNum tempRe, MpfrBigNum tempIm) {

        re.mult(z.re, tempRe);
        im.mult(z.im, tempIm);
        tempRe.sub(tempIm, tempRe);

        re.mult(z.im, re);
        im.mult(z.re, im);
        im.add(re, im);

        re.set(tempRe);

        return this;

    }

    /*
     *  z1 / z2
     */
    public final MpfrBigNumComplex divide(MpfrBigNumComplex z) {

        MpfrBigNum temp = z.re;
        MpfrBigNum temp2 = z.im;

        MpfrBigNum temp3 = temp.square();
        MpfrBigNum temp4 = temp2.square();
        temp3.add(temp4, temp3);

        re.mult(temp, temp4);
        MpfrBigNum temp5 = im.mult(temp2);
        temp4.add(temp5, temp4);
        temp4.divide(temp3, temp4);

        im.mult(temp, temp5);
        MpfrBigNum temp6 = re.mult(temp2);
        temp5.sub(temp6, temp5);
        temp5.divide(temp3, temp5);

        return new MpfrBigNumComplex(temp4, temp5);
    }

    public final MpfrBigNumComplex divide_mutable(MpfrBigNumComplex z) {

        MpfrBigNum temp = z.re;
        MpfrBigNum temp2 = z.im;

        MpfrBigNum temp3 = temp.square();
        MpfrBigNum temp4 = temp2.square();
        temp3.add(temp4, temp3);

        re.mult(temp, temp4);
        MpfrBigNum temp5 = im.mult(temp2);
        temp4.add(temp5, temp4);
        temp4.divide(temp3, temp4);

        im.mult(temp, im);
        re.mult(temp2, temp5);
        im.sub(temp5, im);
        im.divide(temp3, im);

        re = temp4;

        return this;
    }

    /*
     *  z / Real
     */
    public final MpfrBigNumComplex divide(MpfrBigNum number) {

        return new MpfrBigNumComplex(re.divide(number), im.divide(number));

    }

    public final MpfrBigNumComplex divide_mutable(MpfrBigNum number) {
        re.divide(number, re);
        im.divide(number, im);
        return this;
    }

    public final MpfrBigNumComplex divide(double number) {

        return new MpfrBigNumComplex(re.divide(number), im.divide(number));

    }

    public final MpfrBigNumComplex divide_mutable(double number) {
        re.divide(number, re);
        im.divide(number, im);
        return this;
    }

    @Override
    public final MpfrBigNumComplex divide(int number) {

        return new MpfrBigNumComplex(re.divide(number), im.divide(number));

    }

    public final MpfrBigNumComplex divide_mutable(int number) {
        re.divide(number, re);
        im.divide(number, im);
        return this;
    }

    /*
     *  z1 / Imaginary
     */
    public final MpfrBigNumComplex divide_i(MpfrBigNum number) {

        MpfrBigNum temp3 = number.square();

        MpfrBigNum tempRe = im.mult(number);
        tempRe.add(re, tempRe);
        tempRe.divide(temp3, tempRe);

        MpfrBigNum tempIm = re.mult(number);
        im.sub(tempIm, tempIm);
        tempIm.divide(temp3, tempIm);

        return new MpfrBigNumComplex(tempRe, tempIm);
    }

    public final MpfrBigNumComplex divide_i_mutable(MpfrBigNum number) {

        MpfrBigNum temp3 = number.square();

        MpfrBigNum tempRe = im.mult(number);
        tempRe.add(re, tempRe);
        tempRe.divide(temp3, tempRe);

        MpfrBigNum tempIm = re.mult(number);
        im.sub(tempIm, im);
        im.divide(temp3, im);

        re = tempRe;

        return this;
    }

    public final MpfrBigNumComplex divide_i(double number) {

        double temp3 = number * number;

        MpfrBigNum tempRe = im.mult(number);
        tempRe.add(re, tempRe);
        tempRe.divide(temp3, tempRe);

        MpfrBigNum tempIm = re.mult(number);
        im.sub(tempIm, tempIm);
        tempIm.divide(temp3, tempIm);

        return new MpfrBigNumComplex(tempRe, tempIm);
    }

    public final MpfrBigNumComplex divide_i_mutable(double number) {

        double temp3 = number * number;

        MpfrBigNum tempRe = im.mult(number);
        tempRe.add(re, tempRe);
        tempRe.divide(temp3, tempRe);

        MpfrBigNum tempIm = re.mult(number);
        im.sub(tempIm, im);
        im.divide(temp3, im);

        re = tempRe;

        return this;
    }

    public final MpfrBigNumComplex divide_i(int number) {

        int temp3 = number * number;

        MpfrBigNum tempRe = im.mult(number);
        tempRe.add(re, tempRe);
        tempRe.divide(temp3, tempRe);

        MpfrBigNum tempIm = re.mult(number);
        im.sub(tempIm, tempIm);
        tempIm.divide(temp3, tempIm);

        return new MpfrBigNumComplex(tempRe, tempIm);
    }

    public final MpfrBigNumComplex divide_i_mutable(int number) {

        int temp3 = number * number;

        MpfrBigNum tempRe = im.mult(number);
        tempRe.add(re, tempRe);
        tempRe.divide(temp3, tempRe);

        MpfrBigNum tempIm = re.mult(number);
        im.sub(tempIm, im);
        im.divide(temp3, im);

        re = tempRe;

        return this;
    }

    /*
     *  Real / z
     */
    public final MpfrBigNumComplex r_divide(MpfrBigNum number) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        number.divide(reSqr, reSqr);

        MpfrBigNum tempIm = im.negate();
        tempIm.mult(reSqr, tempIm);
        return new MpfrBigNumComplex(re.mult(reSqr), tempIm);

    }

    public final MpfrBigNumComplex r_divide_mutable(MpfrBigNum number) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        number.divide(reSqr, reSqr);

        re.mult(reSqr, re);
        im.negate(im);
        im.mult(reSqr, im);

        return this;

    }

    public final MpfrBigNumComplex r_divide(double number) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.r_divide(number, reSqr);

        MpfrBigNum tempIm = im.negate();
        tempIm.mult(reSqr, tempIm);
        return new MpfrBigNumComplex(re.mult(reSqr), tempIm);

    }

    public final MpfrBigNumComplex r_divide_mutable(double number) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.r_divide(number, reSqr);

        re.mult(reSqr, re);
        im.negate(im);
        im.mult(reSqr, im);

        return this;

    }

    public final MpfrBigNumComplex r_divide(int number) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.r_divide(number, reSqr);

        MpfrBigNum tempIm = im.negate();
        tempIm.mult(reSqr, tempIm);
        return new MpfrBigNumComplex(re.mult(reSqr), tempIm);

    }

    public final MpfrBigNumComplex r_divide_mutable(int number) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.r_divide(number, reSqr);

        re.mult(reSqr, re);
        im.negate(im);
        im.mult(reSqr, im);

        return this;

    }

    /*
     *  Imaginary / z
     */
    public final MpfrBigNumComplex i_divide(MpfrBigNum number) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        number.divide(reSqr, reSqr);


        return new MpfrBigNumComplex(im.mult(reSqr), re.mult(reSqr));

    }

    public final MpfrBigNumComplex i_divide_mutable(MpfrBigNum number) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        number.divide(reSqr, reSqr);

        im.mult(reSqr, im);
        re.mult(reSqr, re);

        MpfrBigNum temp = re;
        re = im;
        im = temp;

        return  this;

    }

    public final MpfrBigNumComplex i_divide(double number) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.r_divide(number, reSqr);


        return new MpfrBigNumComplex(im.mult(reSqr), re.mult(reSqr));

    }

    public final MpfrBigNumComplex i_divide_mutable(double number) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.r_divide(number, reSqr);

        im.mult(reSqr, im);
        re.mult(reSqr, re);

        MpfrBigNum temp = re;
        re = im;
        im = temp;

        return  this;

    }

    public final MpfrBigNumComplex i_divide(int number) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.r_divide(number, reSqr);


        return new MpfrBigNumComplex(im.mult(reSqr), re.mult(reSqr));

    }

    public final MpfrBigNumComplex i_divide_mutable(int number) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.r_divide(number, reSqr);

        im.mult(reSqr, im);
        re.mult(reSqr, re);

        MpfrBigNum temp = re;
        re = im;
        im = temp;

        return  this;

    }

    /*
     *  lexicographical comparison between two complex numbers
     * -1 when z1 > z2
     *  1 when z1 < z2
     *  0 when z1 == z2
     */
    public final int compare(MpfrBigNumComplex z2) {

        if(isNaN() || z2.isNaN()) {
            return 2;
        }

        int comparisonRe = re.compare(z2.re);
        int comparisonIm = im.compare(z2.im);

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
     *  lexicographical comparison between two complex numbers
     * -1 when z1 > z2
     *  1 when z1 < z2
     *  0 when z1 == z2
     */
    @Override
    public final int compare(GenericComplex z2c) {

        MpfrBigNumComplex z2 = (MpfrBigNumComplex)z2c;

        if(isNaN() || z2.isNaN()) {
            return 2;
        }

        int comparisonRe = re.compare(z2.re);
        int comparisonIm = im.compare(z2.im);

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

    public final MpfrBigNumComplex fold_right(MpfrBigNumComplex z2) {

        if(re.compare(z2.re) < 0) {
            MpfrBigNum tempRe = z2.re.sub(re);
            tempRe.mult2(tempRe);
            re.add(tempRe, tempRe);
            return new MpfrBigNumComplex(tempRe, new MpfrBigNum(im));
        }
        return this;

    }

    public final MpfrBigNumComplex fold_left(MpfrBigNumComplex z2) {

        if(re.compare(z2.re) > 0) {
            MpfrBigNum tempRe = re.sub(z2.re);
            tempRe.mult2(tempRe);
            re.sub(tempRe, tempRe);
            return new MpfrBigNumComplex(tempRe, new MpfrBigNum(im));
        }
        return this;

    }

    public final MpfrBigNumComplex fold_up(MpfrBigNumComplex z2) {

        if(im.compare(z2.im) < 0) {
            MpfrBigNum tempIm = z2.im.sub(im);
            tempIm.mult2(tempIm);
            im.add(tempIm, tempIm);
            return new MpfrBigNumComplex(new MpfrBigNum(re), tempIm);
        }
        return  this;


    }

    public final MpfrBigNumComplex fold_down(MpfrBigNumComplex z2) {

        if(im.compare(z2.im) > 0) {
            MpfrBigNum tempIm = im.sub(z2.im);
            tempIm.mult2(tempIm);
            im.sub(tempIm, tempIm);
            new MpfrBigNumComplex(new MpfrBigNum(re), tempIm);
        }

        return this;

    }

    public final MpfrBigNumComplex inflection(MpfrBigNumComplex inf) {

        MpfrBigNumComplex diff = this.sub(inf);

        return inf.plus(diff.square_mutable());

    }

    public final MpfrBigNumComplex shear(MpfrBigNumComplex sh) {

        return new MpfrBigNumComplex(re.add(im.mult(sh.re)), im.add(re.mult(sh.im)));

    }

    public final MpfrBigNumComplex shear_mutable(MpfrBigNumComplex sh) {

        MpfrBigNum tempRe = im.mult(sh.re);
        tempRe.add(re, tempRe);

        im.add(re.mult(sh.im), im);
        re = tempRe;

        return this;

    }

    /*
     * y + xi
     */
    public final MpfrBigNumComplex flip() {

        return new MpfrBigNumComplex(new MpfrBigNum(im), new MpfrBigNum(re));

    }

    public final MpfrBigNumComplex flip_mutable() {

        MpfrBigNum temp = re;
        re = im;
        im = temp;
        return this;

    }

    /*
     *  |z|^2
     */
    @Override
    public final MpfrBigNum normSquared() {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        return reSqr;

    }

    /*
     *  |z1 - z2|^2
     */
    @Override
    public final MpfrBigNum distanceSquared(GenericComplex za) {
        MpfrBigNumComplex z = (MpfrBigNumComplex) za;

        MpfrBigNum temp_re = re.sub(z.re);
        MpfrBigNum temp_im = im.sub(z.im);
        temp_re.square(temp_re);
        temp_im.square(temp_im);
        temp_re.add(temp_im, temp_re);
        return temp_re;

    }

    @Override
    public MpfrBigNumComplex times2() {
        return new MpfrBigNumComplex(re.mult2(), im.mult2());
    }

    @Override
    public MpfrBigNumComplex times2_mutable() {
        re.mult2(re);
        im.mult2(im);
        return this;
    }

    @Override
    public MpfrBigNumComplex times4() {
        return new MpfrBigNumComplex(re.mult4(), im.mult4());
    }

    public MpfrBigNumComplex times4_mutable() {
        re.mult4(re);
        im.mult4(im);
        return this;
    }

    public MpfrBigNumComplex divide2() {
        return new MpfrBigNumComplex(re.divide2(), im.divide2());
    }

    public MpfrBigNumComplex divide2_mutable() {

        re.divide2(re);
        im.divide2(im);
        return this;
    }

    public MpfrBigNumComplex divide4() {
        return new MpfrBigNumComplex(re.divide4(), im.divide4());
    }

    public MpfrBigNumComplex divide4_mutable() {

        re.divide4(re);
        im.divide4(im);
        return this;
    }


    /*
     * n-norm
     */
    public final MpfrBigNum nnorm(MpfrBigNum n) {

        MpfrBigNum tempRe = re.abs();
        tempRe.pow(n, tempRe);
        MpfrBigNum tempIm = im.abs();
        tempIm.pow(n, tempIm);

        tempRe.add(tempIm, tempRe);
        tempRe.pow(n.reciprocal(), tempRe);
        return tempRe;

    }

    /*
     *  exp(z) = exp(Re(z)) * (cos(Im(z)) + sin(Im(z))i)
     */
    public final MpfrBigNumComplex exp() {

        MpfrBigNum temp = re.exp();

        MpfrBigNum[] res = im.sin_cos();

        return new MpfrBigNumComplex(temp.mult(res[1]), temp.mult(res[0]));

    }

    public final MpfrBigNumComplex exp_mutable() {

        MpfrBigNum temp = re.exp();

        MpfrBigNum[] res = im.sin_cos();

        temp.mult(res[1], re);
        temp.mult(res[0], im);
        return this;

    }

    /*
     *  log(z) = ln|z| + arctan(Im/Re)i
     */
    public final MpfrBigNumComplex log() {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.log(reSqr);
        reSqr.divide2(reSqr);


        return new MpfrBigNumComplex(reSqr, MpfrBigNum.atan2(im, re));

    }

    public final MpfrBigNumComplex log_mutable() {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.log(reSqr);
        reSqr.divide2(reSqr);

        MpfrBigNum.atan2(im, re, im);
        re = reSqr;

        return this;

    }


    /*
     *  1 / z
     */
    public final MpfrBigNumComplex reciprocal() {
        MpfrBigNum tempRe = re.square();
        MpfrBigNum tempIm = im.square();
        tempRe.add(tempIm, tempRe);

        im.negate(tempIm);
        tempIm.divide(tempRe, tempIm);

        re.divide(tempRe, tempRe);

        return new MpfrBigNumComplex(tempRe, tempIm);

    }

    public final MpfrBigNumComplex reciprocal_mutable() {
        MpfrBigNum tempRe = re.square();
        MpfrBigNum tempIm = im.square();
        tempRe.add(tempIm, tempRe);

        im.negate(im);
        im.divide(tempRe, im);

        re.divide(tempRe, re);

        return this;

    }

    /*
     *  sin(z) = (exp(iz) - exp(-iz)) / 2i
     */
    public final MpfrBigNumComplex sin() {

        MpfrBigNum temp = im.negate();
        temp.exp(temp);

        MpfrBigNum[] res = re.sin_cos();
        MpfrBigNum cos_re = res[1];
        MpfrBigNum sin_re = res[0];

        MpfrBigNumComplex temp2 = new MpfrBigNumComplex(temp.mult(cos_re), temp.mult(sin_re));

        temp.reciprocal(temp);

        sin_re.negate(sin_re);
        MpfrBigNumComplex temp4 = new MpfrBigNumComplex(temp.mult(cos_re), temp.mult(sin_re));

        return (temp2.sub_mutable(temp4)).times_i_mutable(-0.5);

    }

    /*
     *  cos(z) = (exp(iz) + exp(-iz)) / 2
     */
    public final MpfrBigNumComplex cos() {

        MpfrBigNum temp = im.negate();
        temp.exp(temp);

        MpfrBigNum[] res = re.sin_cos();
        MpfrBigNum cos_re = res[1];
        MpfrBigNum sin_re = res[0];

        MpfrBigNumComplex temp2 = new MpfrBigNumComplex(temp.mult(cos_re), temp.mult(sin_re));

        temp.reciprocal(temp);

        sin_re.negate(sin_re);
        MpfrBigNumComplex temp4 = new MpfrBigNumComplex(temp.mult(cos_re), temp.mult(sin_re));

        return (temp2.plus_mutable(temp4)).divide2_mutable();

    }

    /*
     *  tan(z) = (1 - exp(-2zi)) / i(1 + exp(-2zi))
     */
    public final MpfrBigNumComplex tan() {

        MpfrBigNum temp = im.mult2();
        temp.exp(temp);

        MpfrBigNum temp3 = re.mult2();

        MpfrBigNum[] res = temp3.sin_cos();

        MpfrBigNum cos_re = res[1];
        MpfrBigNum sin_re = res[0];
        sin_re.negate(sin_re);

        MpfrBigNumComplex temp2 = new MpfrBigNumComplex(temp.mult(cos_re), temp.mult(sin_re));

        return (temp2.r_sub(1)).divide_mutable((temp2.plus(1)).times_i_mutable(1));

    }

    /*
     *  cot(z) = i(1 + exp(-2zi)) / (1 - exp(-2zi))
     */
    public final MpfrBigNumComplex cot() {

        MpfrBigNum temp = im.mult2();
        temp.exp(temp);

        MpfrBigNum temp3 = re.mult2();

        MpfrBigNum[] res = temp3.sin_cos();

        MpfrBigNum cos_re = res[1];
        MpfrBigNum sin_re = res[0];

        sin_re.negate(sin_re);
        MpfrBigNumComplex temp2 = new MpfrBigNumComplex(temp.mult(cos_re), temp.mult(sin_re));

        return (temp2.times_i(1).plus_i_mutable(1)).divide_mutable(temp2.r_sub(1));

    }

    /*
     *  csc(z) = 1 / sin(z)
     */
    public final MpfrBigNumComplex csc() {

        return this.sin().reciprocal_mutable();

    }

    /*
     *  cosh(z) = (exp(z) + exp(-z)) / 2
     */
    public final MpfrBigNumComplex cosh() {

        MpfrBigNum temp = re.exp();

        MpfrBigNum[] res = im.sin_cos();

        MpfrBigNum cos_im = res[1];
        MpfrBigNum sin_im = res[0];

        MpfrBigNumComplex temp2 = new MpfrBigNumComplex(temp.mult(cos_im), temp.mult(sin_im));

        temp.reciprocal(temp);
        sin_im.negate(sin_im);
        MpfrBigNumComplex temp4 = new MpfrBigNumComplex(temp.mult(cos_im), temp.mult(sin_im));

        return (temp2.plus_mutable(temp4)).divide2_mutable();

    }

    /*
     *  coth(z) =  (1 + exp(-2z)) / (1 - exp(-2z))
     */
    public final MpfrBigNumComplex coth() {

        MpfrBigNum temp = re.mult2();
        temp.negate(temp);
        temp.exp(temp);

        MpfrBigNum temp3 = im.mult2();

        MpfrBigNum[] res = temp3.sin_cos();

        MpfrBigNum cos_im = res[1];
        MpfrBigNum sin_im = res[0];

        sin_im.negate(sin_im);
        MpfrBigNumComplex temp2 = new MpfrBigNumComplex(temp.mult(cos_im), temp.mult(sin_im));

        return (temp2.plus(1)).divide_mutable(temp2.r_sub(1));

    }

    /*
     *  sech(z) = 1 / cosh(z)
     */
    public final MpfrBigNumComplex sech() {

        return this.cosh().reciprocal_mutable();

    }

    /*
     *  csch(z) = 1 / sinh(z)
     */
    public final MpfrBigNumComplex csch() {

        return this.sinh().reciprocal_mutable();

    }

    /*
     *  sec(z) = 1 / cos(z)
     */
    public final MpfrBigNumComplex sec() {

        return this.cos().reciprocal_mutable();

    }

    /*
     *  sinh(z) = (exp(z) - exp(-z)) / 2
     */
    public final MpfrBigNumComplex sinh() {

        MpfrBigNum temp = re.exp();

        MpfrBigNum[] res = im.sin_cos();

        MpfrBigNum cos_im = res[1];
        MpfrBigNum sin_im = res[0];

        MpfrBigNumComplex temp2 = new MpfrBigNumComplex(temp.mult(cos_im), temp.mult(sin_im));

        temp.reciprocal(temp);
        sin_im.negate(sin_im);
        MpfrBigNumComplex temp4 = new MpfrBigNumComplex(temp.mult(cos_im), temp.mult(sin_im));

        return (temp2.sub_mutable(temp4)).divide2_mutable();


    }

    /*
     *  tahn(z) = (1 - exp(-2z)) / (1 + exp(-2z))
     */
    public final MpfrBigNumComplex tanh() {

        MpfrBigNum temp = re.mult2();
        temp.negate(temp);
        temp.exp(temp);

        MpfrBigNum temp3 = im.mult2();

        MpfrBigNum[] res = temp3.sin_cos();

        MpfrBigNum cos_im = res[1];
        MpfrBigNum sin_im = res[0];

        sin_im.negate(sin_im);
        MpfrBigNumComplex temp2 = new MpfrBigNumComplex(temp.mult(cos_im), temp.mult(sin_im));

        return (temp2.r_sub(1)).divide_mutable(temp2.plus(1));

    }


    /*
     *  acot(z) = (i / 2)log((z^2 - iz) / (z^2 + iz))
     */
    public final MpfrBigNumComplex acot() {

        MpfrBigNumComplex temp = this.times_i(1);
        MpfrBigNumComplex temp2 = this.square();

        return ((temp2.sub(temp)).divide_mutable(temp2.plus(temp))).log_mutable().times_i_mutable(0.5);

    }

    /*
     *  asin(z) =-ilog(iz + sqrt(1 - z^2))
     */
    public final MpfrBigNumComplex asin() {

        return this.times_i(1).plus_mutable((this.square().r_sub_mutable(1)).sqrt_mutable()).log_mutable().times_i_mutable(-1);

    }

    /*
     *  asec(z) = pi / 2 + ilog(sqrt(1 - 1 / z^2) + i / z)
     */
    public final MpfrBigNumComplex asec() {

        return (((this.square().reciprocal_mutable()).r_sub_mutable(1).sqrt_mutable()).plus_mutable(this.i_divide(1))).log_mutable().times_i_mutable(1).plus_mutable(MpfrBigNum.HALF_PI);

    }

    /*
     *  atan(z) = (i / 2)log((1 - iz) / (iz + 1))
     */
    public final MpfrBigNumComplex atan() {

        MpfrBigNumComplex temp = this.times_i(1);

        return ((temp.r_sub(1)).divide_mutable(temp.plus(1))).log_mutable().times_i_mutable(0.5);

    }

    /*
     *  acos(z) = pi / 2 + ilog(iz + sqrt(1 - z^2))
     */
    public final MpfrBigNumComplex acos() {

        return this.asin().r_sub_mutable(MpfrBigNum.HALF_PI);

    }

    /*
     *  atanh(z) = (1 / 2)log((z + 1) / (1 - z))
     */
    public final MpfrBigNumComplex atanh() {

        return ((this.plus(1)).divide_mutable(this.r_sub(1))).log_mutable().divide2_mutable();

    }

    /*
     *  acsch(z) = log(sqrt(1 / z^2 + 1) + 1 / z)
     */
    public final MpfrBigNumComplex acsch() {

        return (((this.square().reciprocal_mutable()).plus_mutable(1).sqrt_mutable()).plus_mutable(this.reciprocal())).log_mutable();

    }

    /*
     *  acsc(z) = -ilog(sqrt(1 - 1 / z^2) + i / z)
     */
    public final MpfrBigNumComplex acsc() {

        return (((this.square().reciprocal_mutable()).r_sub_mutable(1).sqrt_mutable()).plus_mutable(this.i_divide(1))).log_mutable().times_i_mutable(-1);

    }

    /*
     *  asech(z) = log(sqrt(1 / z^2 - 1) + 1 / z)
     */
    public final MpfrBigNumComplex asech() {

        return (((this.square().reciprocal_mutable()).sub_mutable(1).sqrt_mutable()).plus_mutable(this.reciprocal())).log_mutable();

    }

    /*
     *  asinh(z) = log(z + sqrt(z^2 + 1))
     */
    public final MpfrBigNumComplex asinh() {

        return this.plus((this.square().plus_mutable(1)).sqrt_mutable()).log_mutable();

    }

    /*
     *  acoth(z) = (1 / 2)log((1 + 1/z) / (1 - 1/z))
     */
    public final MpfrBigNumComplex acoth() {

        MpfrBigNumComplex temp = this.reciprocal();

        return ((temp.plus(1)).divide_mutable(temp.r_sub(1))).log_mutable().divide2_mutable();

    }

    /*
     *  acosh(z) = log(z + sqrt(z^2 - 1))
     */
    public final MpfrBigNumComplex acosh() {

        return this.plus((this.square().sub_mutable(1)).sqrt_mutable()).log_mutable();

    }

    public final MpfrBigNumComplex fold_out(MpfrBigNumComplex z2) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);

        return reSqr.compare(z2.norm_squared()) > 0 ? this.divide(reSqr) : this;

    }

    public final MpfrBigNumComplex fold_in(MpfrBigNumComplex z2) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);

        return reSqr.compare(z2.norm_squared()) < 0 ? this.divide(reSqr) : this;

    }

    public final MpfrBigNumComplex circle_inversion(MpfrBigNumComplex center, MpfrBigNum radius) {

        MpfrBigNum distance = this.distance_squared(center);
        MpfrBigNum radius2 = radius.square();

        radius2.divide(distance, radius2);

        MpfrBigNum tempRe = re.sub(center.re);
        tempRe.add(center.re, tempRe);
        tempRe.mult(radius2, tempRe);

        MpfrBigNum tempIm = im.sub(center.im);
        tempIm.add(center.im, tempIm);
        tempIm.mult(radius2, tempIm);
        return new MpfrBigNumComplex(tempRe, tempIm);

    }

    public final MpfrBigNumComplex toBiPolar(MpfrBigNumComplex a) {

        return this.divide2().cot().times_mutable(a).times_i_mutable(1);

    }

    public final MpfrBigNumComplex fromBiPolar(MpfrBigNumComplex a) {

        return this.divide(a.times_i(1)).acot().times2_mutable();

    }

    @Override
    public MantExpComplex toMantExpComplex() { return new MantExpComplex(this);}

    public boolean isZero() {
        return re.isZero() && im.isZero();
    }
    public boolean isOne() {
        return re.isOne() && im.isZero();
    }

    @Override
    public void set(GenericComplex za) {
        MpfrBigNumComplex z = (MpfrBigNumComplex) za;
        re.set(z.re);
        im.set(z.im);
    }

    /*
     *  z1 ^ z2 = exp(z2 * log(z1))
     */
    public final MpfrBigNumComplex pow(MpfrBigNumComplex z) {

        return (z.times(this.log())).exp();

    }

    /*
     *  z^n
     */
    public final MpfrBigNumComplex pow(double exponent) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.pow(new MpfrBigNum(exponent * 0.5), reSqr);

        MpfrBigNum temp2 = MpfrBigNum.atan2(im, re);
        temp2.mult(exponent, temp2);

        MpfrBigNum[] res = temp2.sin_cos();

        return new MpfrBigNumComplex(reSqr.mult(res[1]), reSqr.mult(res[0]));

    }

    /*
     *  z^n
     */
    public final MpfrBigNumComplex pow_mutable(double exponent) {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.pow(new MpfrBigNum(exponent * 0.5), reSqr);

        MpfrBigNum temp2 = MpfrBigNum.atan2(im, re);
        temp2.mult(exponent, temp2);

        MpfrBigNum[] res = temp2.sin_cos();

        reSqr.mult(res[1], re);
        reSqr.mult(res[0], im);

        return this;

    }

    /*
     * sqrt(z) = z^0.5
     */
    public final MpfrBigNumComplex sqrt() {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.pow(new MpfrBigNum(0.25), reSqr);

        MpfrBigNum temp2 = MpfrBigNum.atan2(im, re);
        temp2.divide2(temp2);

        MpfrBigNum[] res = temp2.sin_cos();

        return new MpfrBigNumComplex(reSqr.mult(res[1]), reSqr.mult(res[0]));

    }

    /*
     * z = sqrt(z) = z^0.5
     */
    public final MpfrBigNumComplex sqrt_mutable() {

        MpfrBigNum reSqr = re.square();
        MpfrBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.pow(new MpfrBigNum(0.25), reSqr);

        MpfrBigNum temp2 = MpfrBigNum.atan2(im, re);
        temp2.divide2(temp2);

        MpfrBigNum[] res = temp2.sin_cos();

        reSqr.mult(res[1], re);
        reSqr.mult(res[0], im);

        return this;

    }

    public boolean isNaN() {
        if(re.isNaN() || im.isNaN()) {
            return true;
        }
        return false;
    }

    /*
     * versine(z) = 1 - cos(z)
     */
    public final MpfrBigNumComplex vsin() {

        return this.cos().r_sub_mutable(1);

    }

    /*
     * arc versine(z) = acos(1 - z)
     */
    public final MpfrBigNumComplex avsin() {

        return this.r_sub(1).acos();

    }

    /*
     * vercosine(z) = 1 + cos(z)
     */
    public final MpfrBigNumComplex vcos() {

        return this.cos().plus_mutable(1);

    }

    /*
     * arc vercosine(z) = acos(1 + z)
     */
    public final MpfrBigNumComplex avcos() {

        return this.plus(1).acos();

    }

    /*
     * coversine(z) = 1 - sin(z)
     */
    public final MpfrBigNumComplex cvsin() {

        return this.sin().r_sub_mutable(1);

    }

    /*
     * arc coversine(z) = asin(1 - z)
     */
    public final MpfrBigNumComplex acvsin() {

        return this.r_sub(1).asin();

    }

    /*
     * covercosine(z) = 1 + sin(z)
     */
    public final MpfrBigNumComplex cvcos() {

        return this.sin().plus_mutable(1);

    }

    /*
     * arc covercosine(z) = asin(1 + z)
     */
    public final MpfrBigNumComplex acvcos() {

        return this.plus(1).asin();

    }

    /*
     * haversine(z) = versine(z) / 2
     */
    public final MpfrBigNumComplex hvsin() {

        return this.vsin().divide2_mutable();

    }

    /*
     * arc haversine(z) = 2 * asin(sqrt(z))
     */
    public final MpfrBigNumComplex ahvsin() {

        return this.sqrt().asin().times2_mutable();

    }

    /*
     * havercosine(z) = vercosine(z) / 2
     */
    public final MpfrBigNumComplex hvcos() {

        return this.vcos().divide2_mutable();

    }

    /*
     * arc havercosine(z) = 2 * acos(sqrt(z))
     */
    public final MpfrBigNumComplex ahvcos() {

        return this.sqrt().acos().times2_mutable();

    }

    /*
     * hacoversine(z) = coversine(z) / 2
     */
    public final MpfrBigNumComplex hcvsin() {

        return this.cvsin().divide2_mutable();

    }

    /*
     * arc hacoversine(z) = asin(1 - 2*z)
     */
    public final MpfrBigNumComplex ahcvsin() {

        return this.times2().r_sub_mutable(1).asin();

    }

    /*
     * hacovercosine(z) = covercosine(z) / 2
     */
    public final MpfrBigNumComplex hcvcos() {

        return this.cvcos().divide2_mutable();

    }

    /*
     * arc hacovercosine(z) = asin(-1 - 2*z)
     */
    public final MpfrBigNumComplex ahcvcos() {

        return this.times(-2).r_sub_mutable(1).asin();

    }

    /*
     * exsecant(z) = sec(z) - 1
     */
    public final MpfrBigNumComplex exsec() {

        return this.sec().sub_mutable(1);

    }

    /*
     * arc exsecant(z) = asec(z + 1)
     */
    public final MpfrBigNumComplex aexsec() {

        return this.plus(1).asec();

    }

    /*
     * excosecant(z) = csc(z) - 1
     */
    public final MpfrBigNumComplex excsc() {

        return this.csc().sub_mutable(1);

    }

    /*
     * arc excosecant(z) = acsc(z + 1)
     */
    public final MpfrBigNumComplex aexcsc() {

        return this.plus(1).acsc();

    }

    /*s
     *  z1 / z2
     */
    @Override
    public final MpfrBigNumComplex divide(GenericComplex za) {
        MpfrBigNumComplex z = (MpfrBigNumComplex)za;

        MpfrBigNum temp = z.re;
        MpfrBigNum temp2 = z.im;

        MpfrBigNum temp3 = temp.square();
        MpfrBigNum temp4 = temp2.square();
        temp3.add(temp4, temp3);

        re.mult(temp, temp4);
        MpfrBigNum temp5 = im.mult(temp2);
        temp4.add(temp5, temp4);
        temp4.divide(temp3, temp4);

        im.mult(temp, temp5);
        MpfrBigNum temp6 = re.mult(temp2);
        temp5.sub(temp6, temp5);
        temp5.divide(temp3, temp5);

        return new MpfrBigNumComplex(temp4, temp5);
    }

    @Override
    public final MpfrBigNumComplex divide_mutable(GenericComplex za) {
        MpfrBigNumComplex z = (MpfrBigNumComplex)za;

        MpfrBigNum temp = z.re;
        MpfrBigNum temp2 = z.im;

        MpfrBigNum temp3 = temp.square();
        MpfrBigNum temp4 = temp2.square();
        temp3.add(temp4, temp3);

        re.mult(temp, temp4);
        MpfrBigNum temp5 = im.mult(temp2);
        temp4.add(temp5, temp4);
        temp4.divide(temp3, temp4);

        im.mult(temp, im);
        re.mult(temp2, temp5);
        im.sub(temp5, im);
        im.divide(temp3, im);

        re = temp4;

        return this;
    }

    @Override
    public final MpfrBigNumComplex times_mutable(GenericComplex za) {
        MpfrBigNumComplex z = (MpfrBigNumComplex)za;

        MpfrBigNum tempRe = new MpfrBigNum(re);
        MpfrBigNum tempIm = new MpfrBigNum(im);

        tempRe = tempRe.mult(z.re, tempRe);
        tempIm = tempIm.mult(z.im, tempIm);
        tempRe = tempRe.sub(tempIm, tempRe);

        re.mult(z.im, re);
        im.mult(z.re, im);
        im.add(re, im);

        re = tempRe;

        return this;

    }


}
