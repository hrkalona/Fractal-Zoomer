package fractalzoomer.core;

import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.core.mpir.mpf_t;
import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

public class MpirBigNumComplex extends GenericComplex {
    private MpirBigNum re;
    private MpirBigNum im;

    public MpirBigNumComplex(BigComplex c) {
        re = new MpirBigNum(c.getRe());
        im = new MpirBigNum(c.getIm());
    }

    public MpirBigNumComplex(MpfrBigNumComplex c) {
        re = new MpirBigNum(c.getRe());
        im = new MpirBigNum(c.getIm());
    }

    public MpirBigNumComplex(double re, double im) {
        this.re = new MpirBigNum(re);
        this.im = new MpirBigNum(im);
    }

    public MpirBigNumComplex(int re, int im) {
        this.re = new MpirBigNum(re);
        this.im = new MpirBigNum(im);
    }

    //Does Copy
    public MpirBigNumComplex(MpirBigNumComplex c) {
        re = new MpirBigNum(c.getRe());
        im = new MpirBigNum(c.getIm());
    }

    public MpirBigNumComplex(Complex c) {
        re = new MpirBigNum(c.getRe());
        im = new MpirBigNum(c.getIm());
    }

    public MpirBigNumComplex(MpirBigNum re, MpirBigNum im) {
        this.re = re;
        this.im = im;
    }

    public MpirBigNumComplex(mpf_t rePeer, mpf_t imPeer) {

        re = new MpirBigNum(rePeer);
        im = new MpirBigNum(imPeer);

    }

    public MpirBigNumComplex(Apfloat re, Apfloat im) {
        this.re = new MpirBigNum(re);
        this.im = new MpirBigNum(im);
    }

    public MpirBigNumComplex() {

        re = new MpirBigNum();
        im = new MpirBigNum();

    }

    public MpirBigNumComplex(String reStr, String imStr) {

        re = new MpirBigNum(reStr);
        im = new MpirBigNum(imStr);

    }

    @Override
    public MpirBigNumComplex toMpirBigNumComplex() { return this; }

    @Override
    public MpfrBigNumComplex toMpfrBigNumComplex() { return new MpfrBigNumComplex(this); }

    public final MpirBigNum getRe() {

        return re;

    }

    public final MpirBigNum getIm() {

        return im;

    }

    public final void setRe(MpirBigNum re) {

        this.re = re;

    }

    public final void setIm(MpirBigNum im) {

        this.im = im;

    }

    @Override
    public final Complex toComplex() {
        double[] res = MpirBigNum.get_d(re, im);
        return new Complex(res[0], res[1]);
        //return new Complex(re.doubleValue(), im.doubleValue());
    }

    /*
     * z1 + z2
     */
    public final MpirBigNumComplex plus(MpirBigNumComplex z) {

        return new MpirBigNumComplex(re.add(z.re), im.add(z.im));

    }


    public final MpirBigNumComplex plus_mutable(MpirBigNumComplex z) {

        //re.add(z.re, re);
        //im.add(z.im, im);
        MpirBigNum.self_add(re, im, z.re, z.im);

        return this;

    }


    @Override
    public final MpirBigNumComplex plus(int value) {

        return new MpirBigNumComplex(re.add(value), new MpirBigNum(im));

    }


    @Override
    public final MpirBigNumComplex plus_mutable(int value) {

        re.add(value, re);
        return this;

    }

    /*
     *  z + Real
     */
    public final MpirBigNumComplex plus(MpirBigNum number) {

        return new MpirBigNumComplex(re.add(number), new MpirBigNum(im));

    }

    public final MpirBigNumComplex plus_mutable(MpirBigNum number) {

        re.add(number, re);
        return this;

    }

    /*
     *  z + Imaginary
     */
    public final MpirBigNumComplex plus_i(MpirBigNum number) {

        return new MpirBigNumComplex(new MpirBigNum(re), im.add(number));

    }

    public final MpirBigNumComplex plus_i_mutable(MpirBigNum number) {

        im.add(number, im);
        return this;

    }


    public final MpirBigNumComplex plus_i(int number) {

        return new MpirBigNumComplex(new MpirBigNum(re), im.add(number));

    }


    public final MpirBigNumComplex plus_i_mutable(int number) {

        im.add(number, im);
        return this;

    }

    /*
     *  z1 - z2
     */
    public final MpirBigNumComplex sub(MpirBigNumComplex z) {

        return  new MpirBigNumComplex(re.sub(z.re), im.sub(z.im));

    }

    /*
     *  z1 - z2
     */
    public final MpirBigNumComplex sub(MpirBigNumComplex z, MpirBigNum temp1, MpirBigNum temp2) {

        return  new MpirBigNumComplex(re.sub(z.re, temp1), im.sub(z.im, temp2));

    }

    public final MpirBigNumComplex sub_mutable(MpirBigNumComplex z) {

        //re.sub(z.re, re);
        //im.sub(z.im, im);
        MpirBigNum.self_sub(re, im, z.re, z.im);
        return this;

    }


    /*
     *  z - Imaginary
     */
    public final MpirBigNumComplex sub_i(MpirBigNum number) {

        return new MpirBigNumComplex(new MpirBigNum(re), im.sub(number));

    }
    public final MpirBigNumComplex sub_i_mutable(MpirBigNum number) {

        im.sub(number, im);
        return this;

    }


    public final MpirBigNumComplex sub_i(int number) {

        return new MpirBigNumComplex(new MpirBigNum(re), im.sub(number));

    }
    public final MpirBigNumComplex sub_i_mutable(int number) {

        im.sub(number, im);
        return this;

    }

    @Override
    public final MpirBigNumComplex sub(int val) {

        return new MpirBigNumComplex(re.sub(val), new MpirBigNum(im));

    }

    @Override
    public final MpirBigNumComplex sub_mutable(int val) {

        re.sub(val, re);
        return this;

    }

    /*
     *  z - Real, Mutable
     */
    public final MpirBigNumComplex sub_mutable(MpirBigNum number) {

        re.sub(number, re);
        return this;

    }


    /*
     *  z - Real
     */
    public final MpirBigNumComplex sub(MpirBigNum number) {

        return  new MpirBigNumComplex(re.sub(number), new MpirBigNum(im));

    }

    /*
     *  z - Real
     */
    public final MpirBigNumComplex sub(MpirBigNum number, MpirBigNum temp1, MpirBigNum temp2) {

        temp2.set(im);
        return  new MpirBigNumComplex(re.sub(number, temp1), temp2);

    }

    /*
     *  Real - z1
     */
    public final MpirBigNumComplex r_sub(MpirBigNum number) {

        return  new MpirBigNumComplex(number.sub(re), im.negate());

    }

    public final MpirBigNumComplex r_sub_mutable(MpirBigNum number) {

        number.sub(re, re);
        im.negate(im);
        return this;

    }


    @Override
    public final MpirBigNumComplex r_sub(int number) {

        return new MpirBigNumComplex(re.r_sub(number), im.negate());

    }

    public final MpirBigNumComplex r_sub_mutable(int number) {

        re.r_sub(number, re);
        im.negate(im);
        return this;

    }

    /*
     *  Imaginary - z
     */
    public final MpirBigNumComplex i_sub(MpirBigNum number) {

        return  new MpirBigNumComplex(re.negate(), number.sub(im));

    }

    public final MpirBigNumComplex i_sub_mutable(MpirBigNum number) {

        re.negate(re);
        number.sub(im, im);
        return this;

    }

    public final MpirBigNumComplex i_sub(int number) {

        return  new MpirBigNumComplex(re.negate(), im.r_sub(number));

    }

    public final MpirBigNumComplex i_sub_mutable(int number) {

        re.negate(re);
        im.r_sub(number, im);
        return this;

    }

    /*
     *  z1 * z2
     */
    public final MpirBigNumComplex times(MpirBigNumComplex z) {

        /*MpirBigNum tempRe = re.mult(z.re);
        MpirBigNum tempRe2 = im.mult(z.im);
        tempRe.sub(tempRe2, tempRe);

        MpirBigNum tempIm = re.mult(z.im);
        MpirBigNum tempIm2 = im.mult(z.re);
        tempIm.add(tempIm2, tempIm);

        return new MpirBigNumComplex(tempRe, tempIm);*/

        MpirBigNum ac = re.mult(z.re);
        MpirBigNum bd = im.mult(z.im);

        MpirBigNum tempIm = re.add(im);
        MpirBigNum tempIm2 = z.re.add(z.im);
        tempIm.mult(tempIm2, tempIm);
        tempIm.sub(ac, tempIm);
        tempIm.sub(bd, tempIm);

        return new MpirBigNumComplex(ac.sub(bd, ac), tempIm);

    }

    public final MpirBigNumComplex times_mutable(MpirBigNumComplex z) {

        MpirBigNum ac = re.mult(z.re);
        MpirBigNum bd = im.mult(z.im);

        MpirBigNum tempIm = re.add(im);
        MpirBigNum tempIm2 = z.re.add(z.im);
        tempIm.mult(tempIm2, tempIm);
        tempIm.sub(ac, tempIm);
        tempIm.sub(bd, im);
        ac.sub(bd, re);

        return this;

    }

    /*
     *  z1 * Real
     */public final MpirBigNumComplex times(MpirBigNum number) {

        return new MpirBigNumComplex(re.mult(number), im.mult(number));

    }


    @Override
    public final MpirBigNumComplex times(int number) {

        return new MpirBigNumComplex(re.mult(number), im.mult(number));

    }

    public final MpirBigNumComplex times_mutable(MpirBigNum number) {

        re.mult(number, re);
        im.mult(number, im);
        return this;

    }


    @Override
    public final MpirBigNumComplex times_mutable(int number) {

        re.mult(number, re);
        im.mult(number, im);
        return this;

    }

    /*
     *  z * Imaginary
     */
    public final MpirBigNumComplex times_i(MpirBigNum number) {

        return new MpirBigNumComplex(im.negate().mult(number), re.mult(number));

    }

    public final MpirBigNumComplex times_i(int number) {

        return new MpirBigNumComplex(im.negate().mult(number), re.mult(number));

    }

    public final MpirBigNumComplex times_i_mutable(MpirBigNum number) {

        im.negate(im);
        im.mult(number, im);
        re.mult(number, re);

        MpirBigNum temp = re;
        re = im;
        im = temp;

        return this;

    }


    public final MpirBigNumComplex times_i_mutable(int number) {

        im.negate(im);
        im.mult(number, im);
        re.mult(number, re);

        MpirBigNum temp = re;
        re = im;
        im = temp;

        return this;

    }

    /*
     *  z^2
     */
    @Override
    public final MpirBigNumComplex square() {
        MpirBigNum tempIm = re.mult(im);
        tempIm.mult2(tempIm);

        MpirBigNum tempRe = re.add(im);
        MpirBigNum tempRe2 = re.sub(im);
        tempRe.mult(tempRe2, tempRe);

        return new MpirBigNumComplex(tempRe, tempIm);
    }

    @Override
    public final MpirBigNumComplex square_mutable() {

        MpirBigNum tempRe = re.add(im);
        MpirBigNum tempRe2 = re.sub(im);
        tempRe.mult(tempRe2, tempRe);

        re.mult(im, im);
        im.mult2(im);

        re = tempRe;

        return this;
    }

    /*
     *  z^2 + c
     */
    public final MpirBigNumComplex squareFast_plus_c(NormComponents normComponents, GenericComplex ca) {
        MpirBigNum reSqr = (MpirBigNum) normComponents.reSqr;
        MpirBigNum imSqr = (MpirBigNum) normComponents.imSqr;
        MpirBigNum normSquared = (MpirBigNum) normComponents.normSquared;
        MpirBigNumComplex c = (MpirBigNumComplex) ca;

        MpirBigNum tempRe = new MpirBigNum(reSqr);
        tempRe.sub(imSqr, tempRe);
        tempRe.add(c.re, tempRe);

        MpirBigNum tempIm = new MpirBigNum(re);
        tempIm.add(im, tempIm);
        tempIm.square(tempIm);
        tempIm.sub(normSquared, tempIm);
        tempIm.add(c.im, tempIm);

        return new MpirBigNumComplex(tempRe, tempIm);
    }

    /*
     *  z^2 + c, Mutable for performance
     */
    @Override
    public final MpirBigNumComplex squareFast_plus_c_mutable(NormComponents normComponents, GenericComplex ca) {
//        MpirBigNum reSqr = (MpirBigNum) normComponents.reSqr;
//        MpirBigNum imSqr = (MpirBigNum) normComponents.imSqr;
//        MpirBigNum normSquared = (MpirBigNum) normComponents.normSquared;
//        MpirBigNumComplex c = (MpirBigNumComplex) ca;
//
//        MpirBigNum tempIm = (MpirBigNum) normComponents.tempIm;
//        re.add(im, tempIm); // x + y
//        tempIm.square(tempIm); // (x + y)^2
//        tempIm.sub(normSquared, tempIm); // (x + y)^2 - x^2 - y^2
//        tempIm.add(c.im, im);
//
//        reSqr.sub(imSqr, re); //x^2 - y^2
//        re.add(c.re, re);

        MpirBigNum reSqr = (MpirBigNum) normComponents.reSqr;
        MpirBigNum imSqr = (MpirBigNum) normComponents.imSqr;
        MpirBigNum normSquared = (MpirBigNum) normComponents.normSquared;
        MpirBigNumComplex c = (MpirBigNumComplex) ca;
        MpirBigNum tempIm = (MpirBigNum) normComponents.tempIm;

        MpirBigNum.z_sqr_p_c(re, im, tempIm, reSqr, imSqr, normSquared, c.re, c.im);

        return this;
    }

    /*
     *  z^2
     */
    @Override
    public final MpirBigNumComplex squareFast(NormComponents normComponents) {
        MpirBigNum reSqr = (MpirBigNum) normComponents.reSqr;
        MpirBigNum imSqr = (MpirBigNum) normComponents.imSqr;
        MpirBigNum normSquared = (MpirBigNum) normComponents.normSquared;

        MpirBigNum tempRe = (MpirBigNum) normComponents.tempRe;
        reSqr.sub(imSqr, tempRe);

        MpirBigNum tempIm = (MpirBigNum) normComponents.tempIm;
        re.add(im, tempIm);
        tempIm.square(tempIm);
        tempIm.sub(normSquared, tempIm);

        return new MpirBigNumComplex(tempRe, tempIm);
    }

    /*
     *  z^2
     */
    @Override
    public final MpirBigNumComplex squareFast_mutable(NormComponents normComponents) {
//        MpirBigNum reSqr = (MpirBigNum) normComponents.reSqr;
//        MpirBigNum imSqr = (MpirBigNum) normComponents.imSqr;
//        MpirBigNum normSquared = (MpirBigNum) normComponents.normSquared;
//
//        MpirBigNum tempIm = (MpirBigNum) normComponents.tempIm;
//        re.add(im, tempIm);
//        tempIm.square(tempIm);
//        tempIm.sub(normSquared, im);
//
//        reSqr.sub(imSqr, re);

        MpirBigNum reSqr = (MpirBigNum) normComponents.reSqr;
        MpirBigNum imSqr = (MpirBigNum) normComponents.imSqr;
        MpirBigNum normSquared = (MpirBigNum) normComponents.normSquared;
        MpirBigNum tempIm = (MpirBigNum) normComponents.tempIm;

        MpirBigNum.z_sqr(re, im, tempIm, reSqr, imSqr, normSquared);

        return this;
    }

    /*
     *  z^3, Mutable
     */
    @Override
    public final MpirBigNumComplex cubeFast_mutable(NormComponents normComponents) {

        MpirBigNum temp = (MpirBigNum)normComponents.reSqr;
        MpirBigNum temp2 = (MpirBigNum)normComponents.imSqr;

        MpirBigNum tempRe = (MpirBigNum) normComponents.tempRe;
        temp2.mult(3, tempRe);
        temp.sub(tempRe, tempRe);
        re.mult(tempRe, re);

        MpirBigNum tempIm = (MpirBigNum) normComponents.tempIm;
        temp.mult(3, tempIm);
        tempIm.sub(temp2, tempIm);
        im.mult(tempIm, im);

        return this;

    }

    public final MpirBigNumComplex cubeFast(NormComponents normComponents) {

        MpirBigNum temp = (MpirBigNum)normComponents.reSqr;
        MpirBigNum temp2 = (MpirBigNum)normComponents.imSqr;

        MpirBigNum tempRe = temp2.mult(3);
        temp.sub(tempRe, tempRe);
        tempRe.mult(re, tempRe);

        MpirBigNum tempIm = temp.mult(3);
        tempIm.sub(temp2, tempIm);
        tempIm.mult(im, tempIm);

        return new MpirBigNumComplex(tempRe, tempIm);

    }

    /*
     *  z^4
     */
    @Override
    public final MpirBigNumComplex fourthFast_mutable(NormComponents normComponents) {

        MpirBigNum temp = (MpirBigNum)normComponents.reSqr;
        MpirBigNum temp2 = (MpirBigNum)normComponents.imSqr;

        MpirBigNum tempRe = (MpirBigNum) normComponents.tempRe;
        temp2.mult(6, tempRe);
        temp.sub(tempRe, tempRe);
        temp.mult(tempRe, tempRe);

        MpirBigNum temp1 = (MpirBigNum) normComponents.temp1;
        temp2.square(temp1);
        tempRe.add(temp1, tempRe);


        MpirBigNum tempIm = (MpirBigNum) normComponents.tempIm;
        re.mult(im, tempIm);
        tempIm.mult4(tempIm);

        temp.sub(temp2, temp1);
        tempIm.mult(temp1, im);

        re.set(tempRe);

        return this;

    }

    public final MpirBigNumComplex fourthFast(NormComponents normComponents) {

        MpirBigNum temp = (MpirBigNum)normComponents.reSqr;
        MpirBigNum temp2 = (MpirBigNum)normComponents.imSqr;

        temp.mult(temp.sub(temp2.mult(6))).add(temp2.square());

        MpirBigNum tempRe = temp2.mult(6);
        temp.sub(tempRe, tempRe);
        temp.mult(tempRe, tempRe);
        tempRe.add(temp2.square(), tempRe);

        MpirBigNum tempIm = re.mult(im);
        tempIm.mult4(tempIm);
        tempIm.mult(temp.sub(temp2), tempIm);


        return new MpirBigNumComplex(tempRe, tempIm);

    }

    /*
     *  z^5
     */
    @Override
    public final MpirBigNumComplex fifthFast_mutable(NormComponents normComponents) {

        MpirBigNum temp = (MpirBigNum)normComponents.reSqr;
        MpirBigNum temp2 = (MpirBigNum)normComponents.imSqr;

        MpirBigNum tempRe = (MpirBigNum) normComponents.tempRe;
        temp.mult(10, tempRe);

        MpirBigNum temp1 = (MpirBigNum) normComponents.temp1;
        temp2.mult(5, temp1);
        temp1.sub(tempRe, tempRe);
        temp2.mult(tempRe, tempRe);
        temp.square(temp1);
        temp1.add(tempRe, tempRe);
        re.mult(tempRe, re);

        MpirBigNum tempIm = (MpirBigNum) normComponents.tempIm;
        temp2.mult(10, tempIm);
        temp.mult(5, temp1);
        temp1.sub(tempIm, tempIm);
        temp.mult(tempIm, tempIm);
        temp2.square(temp1);
        temp1.add(tempIm, tempIm);
        im.mult(tempIm, im);

        return this;

    }

    public final MpirBigNumComplex fifthFast(NormComponents normComponents) {

        MpirBigNum temp = (MpirBigNum)normComponents.reSqr;
        MpirBigNum temp2 = (MpirBigNum)normComponents.imSqr;

        MpirBigNum tempRe = temp.mult(10);
        temp2.mult(5).sub(tempRe, tempRe);
        temp2.mult(tempRe, tempRe);
        temp.square().add(tempRe, tempRe);
        re.mult(tempRe, tempRe);

        MpirBigNum tempIm = temp2.mult(10);
        temp.mult(5).sub(tempIm, tempIm);
        temp.mult(tempIm, tempIm);
        temp2.square().add(tempIm, tempIm);
        im.mult(tempIm, tempIm);

        return new MpirBigNumComplex(tempRe, tempIm);
    }

    @Override
    public NormComponents normSquaredWithComponents(NormComponents n) {

        if(n == null) {
            //MpirBigNum reSqr = re.square();
            //MpirBigNum imSqr = im.square();

            MpirBigNum reSqr = new MpirBigNum();
            MpirBigNum imSqr = new MpirBigNum();
            MpirBigNum normSquared = new MpirBigNum();

            MpirBigNum tempRe = new MpirBigNum();
            MpirBigNum tempIm = new MpirBigNum();
            MpirBigNum temp1 = new MpirBigNum();

            MpirBigNum.norm_sqr_with_components(reSqr, imSqr, normSquared, re, im);
            return new NormComponents(reSqr, imSqr, normSquared, tempRe, tempIm, temp1);

            //return new NormComponents(reSqr, imSqr, reSqr.add(imSqr), tempRe, tempIm, temp1);
        }

        MpirBigNum reSqr = (MpirBigNum)n.reSqr;
        MpirBigNum imSqr = (MpirBigNum)n.imSqr;
        MpirBigNum normSquared = (MpirBigNum)n.normSquared;
//
//        re.square(reSqr);
//        im.square(imSqr);
//        reSqr.add(imSqr, normSquared);
        MpirBigNum.norm_sqr_with_components(reSqr, imSqr, normSquared, re, im);

        return n;
    }

    /*
     *  |z|, euclidean norm
     */
    public final MpirBigNum norm() {

        MpirBigNum reSqr = re.square();
        MpirBigNum imSqr = im.square();

        reSqr.add(imSqr, reSqr);
        reSqr.sqrt(reSqr);

        return reSqr;

    }

    /*
     *  |z|, euclidean norm
     */
    public final MpirBigNum norm(MpirBigNum temp1, MpirBigNum temp2) {

        re.square(temp1);
        im.square(temp2);

        temp1.add(temp2, temp1);
        temp1.sqrt(temp1);

        return temp1;

    }

    /*
     *  |z|^2
     */
    public final MpirBigNum norm_squared() {

        MpirBigNum reSqr = re.square();
        MpirBigNum imSqr = im.square();

        reSqr.add(imSqr, reSqr);

        return reSqr;

    }

    /*
     *  |z|^2
     */
    public final MpirBigNum norm_squared(MpirBigNum temp1, MpirBigNum temp2) {

//        re.square(temp1);
//        im.square(temp2);
//
//        temp1.add(temp2, temp1);
        MpirBigNum.norm_sqr(temp1, temp2, re, im);

        return temp1;

    }

    /*
     *  |z1 - z2|^2
     */
    public final MpirBigNum distance_squared(MpirBigNumComplex z) {

        MpirBigNum temp_re = re.sub(z.re);
        MpirBigNum temp_im = im.sub(z.im);
        temp_re.square(temp_re);
        temp_im.square(temp_im);
        temp_re.add(temp_im, temp_re);
        return temp_re;

    }

    /*
     *  |z1 - z2|^2
     */
    public final MpirBigNum distance_squared(MpirBigNumComplex z, MpirBigNum temp1, MpirBigNum temp2) {

        re.sub(z.re, temp1);
        im.sub(z.im, temp2);
        temp1.square(temp1);
        temp2.square(temp2);
        temp1.add(temp2, temp1);
        return temp1;

    }

    /*
     *  |z1 - z2|^2
     */
    public final MpirBigNum distance_squared(MpirBigNum rootRe) {

        MpirBigNum temp_re = re.sub(rootRe);
        temp_re.square(temp_re);
        temp_re.add(im.square(), temp_re);
        return temp_re;

    }

    /*
     *  |z1 - z2|^2
     */
    public final MpirBigNum distance_squared(MpirBigNum rootRe, MpirBigNum temp1, MpirBigNum temp2) {

        re.sub(rootRe, temp1);
        temp1.square(temp1);
        temp1.add(im.square(temp2), temp1);
        return temp1;

    }

    /*
     *  |z1 - z2|
     */
    public final MpirBigNum distance(MpirBigNumComplex z) {

        MpirBigNum temp_re = re.sub(z.re);
        MpirBigNum temp_im = im.sub(z.im);
        temp_re.square(temp_re);
        temp_im.square(temp_im);
        temp_re.add(temp_im, temp_re);
        temp_re.sqrt(temp_re);
        return temp_re;

    }

    /*
     *  |z1 - z2|
     */
    public final MpirBigNum distance(MpirBigNum rootRe) {

        MpirBigNum temp_re = re.sub(rootRe);
        temp_re.square(temp_re);
        temp_re.add(im.square(), temp_re);
        temp_re.sqrt(temp_re);
        return temp_re;

    }

    /*
     *  |Real|
     */
    public final MpirBigNum getAbsRe() {

        return re.abs();

    }

    /*
     *  |Real|
     */
    public final MpirBigNum getAbsRe(MpirBigNum temp1) {

        return re.abs(temp1);

    }

    /*
     *  |Imaginary|
     */
    public final MpirBigNum getAbsIm() {

        return im.abs();

    }

    /*
     *  |Imaginary|
     */
    public final MpirBigNum getAbsIm(MpirBigNum temp2) {

        return im.abs(temp2);

    }

    /*
     *  |Re(z)| + Im(z)i
     */
    public final MpirBigNumComplex absre() {

        return new MpirBigNumComplex(re.abs(), new MpirBigNum(im));

    }

    public final MpirBigNumComplex absre_mutable() {

        re.abs(re);
        return this;

    }

    /*
     *  Re(z) + |Im(z)|i
     */
    public final MpirBigNumComplex absim() {

        return new MpirBigNumComplex(new MpirBigNum(re), im.abs());

    }

    public final MpirBigNumComplex absim_mutable() {

        im.abs(im);
        return this;

    }

    /*
     *  Real -Imaginary i
     */
    public final MpirBigNumComplex conjugate() {


        return new MpirBigNumComplex(new MpirBigNum(re), im.negate());

    }

    @Override
    public final MpirBigNumComplex conjugate_mutable() {

        im.negate(im);
        return this;

    }

    /*
     *  -z
     */
    @Override
    public final MpirBigNumComplex negative() {

        return new MpirBigNumComplex(re.negate(), im.negate());

    }

    @Override
    public final MpirBigNumComplex negative_mutable() {

        re.negate(re);
        im.negate(im);
        return this;

    }

    /*
     *  z^3
     */
    @Override
    public final MpirBigNumComplex cube() {

        MpirBigNum temp = re.square();
        MpirBigNum temp2 = im.square();

        MpirBigNum tempRe = temp2.mult(3);
        temp.sub(tempRe, tempRe);
        tempRe.mult(re, tempRe);

        MpirBigNum tempIm = temp.mult(3);
        tempIm.sub(temp2, tempIm);
        tempIm.mult(im, tempIm);

        return new MpirBigNumComplex(tempRe, tempIm);

    }

    public final MpirBigNumComplex cube_mutable() {

        MpirBigNum temp = re.square();
        MpirBigNum temp2 = im.square();

        MpirBigNum tempRe = temp2.mult(3);
        temp.sub(tempRe, tempRe);
        tempRe.mult(re, re);

        MpirBigNum tempIm = temp.mult(3);
        tempIm.sub(temp2, tempIm);
        tempIm.mult(im, im);

        return this;

    }

    @Override
    public final MpirBigNumComplex cube_mutable(MpirBigNum temp, MpirBigNum temp2, MpirBigNum temp3, MpirBigNum temp4) {

        re.square(temp);
        im.square(temp2);

        MpirBigNum tempRe = temp2.mult(3, temp3);
        temp.sub(tempRe, tempRe);
        tempRe.mult(re, re);

        MpirBigNum tempIm = temp.mult(3, temp4);
        tempIm.sub(temp2, tempIm);
        tempIm.mult(im, im);

        return this;

    }

    /*
     *  z^4
     */
    @Override
    public final MpirBigNumComplex fourth() {

        MpirBigNum temp = re.square();
        MpirBigNum temp2 = im.square();

        temp.mult(temp.sub(temp2.mult(6))).add(temp2.square());

        MpirBigNum tempRe = temp2.mult(6);
        temp.sub(tempRe, tempRe);
        temp.mult(tempRe, tempRe);
        tempRe.add(temp2.square(), tempRe);

        MpirBigNum tempIm = re.mult(im);
        tempIm.mult4(tempIm);
        tempIm.mult(temp.sub(temp2), tempIm);


        return new MpirBigNumComplex(tempRe, tempIm);

    }

    public final MpirBigNumComplex fourth_mutable() {

        MpirBigNum temp = re.square();
        MpirBigNum temp2 = im.square();

        temp.mult(temp.sub(temp2.mult(6))).add(temp2.square());

        MpirBigNum tempRe = temp2.mult(6);
        temp.sub(tempRe, tempRe);
        temp.mult(tempRe, tempRe);
        tempRe.add(temp2.square(), tempRe);

        MpirBigNum tempIm = re.mult(im);
        tempIm.mult4(tempIm);
        tempIm.mult(temp.sub(temp2), im);

        re = tempRe;


        return this;

    }


    /*
     *  z^5
     */
    @Override
    public final MpirBigNumComplex fifth() {

        MpirBigNum temp = re.square();
        MpirBigNum temp2 = im.square();

        MpirBigNum tempRe = temp.mult(10);
        temp2.mult(5).sub(tempRe, tempRe);
        temp2.mult(tempRe, tempRe);
        temp.square().add(tempRe, tempRe);
        re.mult(tempRe, tempRe);

        MpirBigNum tempIm = temp2.mult(10);
        temp.mult(5).sub(tempIm, tempIm);
        temp.mult(tempIm, tempIm);
        temp2.square().add(tempIm, tempIm);
        im.mult(tempIm, tempIm);

        return new MpirBigNumComplex(tempRe, tempIm);

    }

    public final MpirBigNumComplex fifth_mutable() {

        MpirBigNum temp = re.square();
        MpirBigNum temp2 = im.square();

        MpirBigNum tempRe = temp.mult(10);
        temp2.mult(5).sub(tempRe, tempRe);
        temp2.mult(tempRe, tempRe);
        temp.square().add(tempRe, tempRe);
        re.mult(tempRe, tempRe);

        MpirBigNum tempIm = temp2.mult(10);
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
    @Override
    public final MpirBigNumComplex abs_mutable() {

        re.abs(re);
        im.abs(im);
        return this;

    }

    /*
     *  abs(z)
     */
    public final MpirBigNumComplex abs() {

        return new MpirBigNumComplex(re.abs(), im.abs());

    }

    /* more efficient z^2 + c */
    public final MpirBigNumComplex square_plus_c(MpirBigNumComplex c) {

        MpirBigNum temp = re.mult(im);
        temp.mult2(temp);
        temp.add(c.im, temp);


        MpirBigNum temp2 = new MpirBigNum(re);
        temp2.add(im, temp2);

        MpirBigNum temp3 = re.sub(im);

        temp2.mult(temp3, temp2);
        temp2.add(c.re, temp2);

        return new MpirBigNumComplex(temp2, temp);

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
    public final MpirBigNumComplex sub(GenericComplex zn) {
        MpirBigNumComplex z = (MpirBigNumComplex)zn;

        return new MpirBigNumComplex(re.sub(z.re), im.sub(z.im));

    }


    /*
     *  z1 = z1 - z2
     */
    @Override
    public final MpirBigNumComplex sub_mutable(GenericComplex zn) {
        MpirBigNumComplex z = (MpirBigNumComplex)zn;

        //re = re.sub(z.re, re);
        //im = im.sub(z.im, im);
        MpirBigNum.self_sub(re, im, z.re, z.im);

        return this;

    }

    /*
     * z1 + z2
     */
    @Override
    public final MpirBigNumComplex plus(GenericComplex zn) {

        MpirBigNumComplex z = (MpirBigNumComplex)zn;
        return new MpirBigNumComplex(re.add(z.re), im.add(z.im));

    }

    /*
     * z1 = z1 + z2
     */
    @Override
    public final MpirBigNumComplex plus_mutable(GenericComplex zn) {

        MpirBigNumComplex z = (MpirBigNumComplex)zn;

        //re = re.add(z.re, re);
        //im = im.add(z.im, im);
        MpirBigNum.self_add(re, im, z.re, z.im);

        return this;

    }

    public final MpirBigNumComplex square_plus_c(GenericComplex cn) {

        MpirBigNumComplex c = (MpirBigNumComplex)cn;

        MpirBigNum temp = re.mult(im);
        temp.mult2(temp);
        temp.add(c.im, temp);


        MpirBigNum temp2 = new MpirBigNum(re);
        temp2.add(im, temp2);

        MpirBigNum temp3 = re.sub(im);

        temp2.mult(temp3, temp2);
        temp2.add(c.re, temp2);

        return new MpirBigNumComplex(temp2, temp);

    }

    @Override
    public final MpirBigNumComplex square_plus_c_mutable(GenericComplex ca, MpirBigNum temp1, MpirBigNum temp2) {
        MpirBigNumComplex c = (MpirBigNumComplex)ca;

//        re.add(im, temp1);
//
//        re.sub(im, temp2);
//
//        temp2.mult(temp1, temp2);
//
//        re.mult(im, im);
//        temp2.add(c.re, re);
//
//        im.mult2(im);
//        im.add(c.im, im);
        MpirBigNum.z_sqr_p_c(re, im, temp1, temp2, c.re, c.im);

        return this;

    }

    /*
     *  z1 * z2
     */
    @Override
    public final MpirBigNumComplex times(GenericComplex zn) {
        MpirBigNumComplex z = (MpirBigNumComplex)zn;

//        MpfrBigNum tempRe = re.mult(z.re);
//        MpfrBigNum tempRe2 = im.mult(z.im);
//        tempRe.sub(tempRe2, tempRe);
//
//        MpfrBigNum tempIm = re.mult(z.im);
//        MpfrBigNum tempIm2 = im.mult(z.re);
//        tempIm.add(tempIm2, tempIm);
//
//        return new MpfrBigNumComplex(tempRe, tempIm);

        MpirBigNum ac = re.mult(z.re);
        MpirBigNum bd = im.mult(z.im);

        MpirBigNum tempIm = re.add(im);
        MpirBigNum tempIm2 = z.re.add(z.im);
        tempIm.mult(tempIm2, tempIm);
        tempIm.sub(ac, tempIm);
        tempIm.sub(bd, tempIm);

        return new MpirBigNumComplex(ac.sub(bd, ac), tempIm);

    }

    /*
     *  z1 / z2
     */
    public final MpirBigNumComplex divide(MpirBigNumComplex z) {

        MpirBigNum temp = z.re;
        MpirBigNum temp2 = z.im;

        MpirBigNum temp3 = temp.square();
        MpirBigNum temp4 = temp2.square();
        temp3.add(temp4, temp3);
        temp3.reciprocal(temp3);

        re.mult(temp, temp4);
        MpirBigNum temp5 = im.mult(temp2);
        temp4.add(temp5, temp4);
        temp4.mult(temp3, temp4);

        im.mult(temp, temp5);
        MpirBigNum temp6 = re.mult(temp2);
        temp5.sub(temp6, temp5);
        temp5.mult(temp3, temp5);

        return new MpirBigNumComplex(temp4, temp5);
    }

    public final MpirBigNumComplex divide_mutable(MpirBigNumComplex z) {

        MpirBigNum temp = z.re;
        MpirBigNum temp2 = z.im;

        MpirBigNum temp3 = temp.square();
        MpirBigNum temp4 = temp2.square();
        temp3.add(temp4, temp3);
        temp3.reciprocal(temp3);

        re.mult(temp, temp4);
        MpirBigNum temp5 = im.mult(temp2);
        temp4.add(temp5, temp4);
        temp4.mult(temp3, temp4);

        im.mult(temp, im);
        re.mult(temp2, temp5);
        im.sub(temp5, im);
        im.mult(temp3, im);

        re = temp4;

        return this;
    }

    /*
     *  z / Real
     */
    public final MpirBigNumComplex divide(MpirBigNum number) {

        MpirBigNum temp = number.reciprocal();
        return new MpirBigNumComplex(re.mult(temp), im.mult(temp));

    }

    public final MpirBigNumComplex divide_mutable(MpirBigNum number) {

        MpirBigNum temp = number.reciprocal();
        re.mult(temp, re);
        im.mult(temp, im);
        return this;
    }


    @Override
    public final MpirBigNumComplex divide(int number) {

        return new MpirBigNumComplex(re.divide(number), im.divide(number));

    }

    public final MpirBigNumComplex divide_mutable(int number) {
        re.divide(number, re);
        im.divide(number, im);
        return this;
    }

    /*
     *  z1 / Imaginary
     */
    public final MpirBigNumComplex divide_i(MpirBigNum number) {

        MpirBigNum temp3 = number.square();
        temp3.reciprocal(temp3);

        MpirBigNum tempRe = im.mult(number);
        tempRe.add(re, tempRe);
        tempRe.mult(temp3, tempRe);

        MpirBigNum tempIm = re.mult(number);
        im.sub(tempIm, tempIm);
        tempIm.mult(temp3, tempIm);

        return new MpirBigNumComplex(tempRe, tempIm);
    }

    public final MpirBigNumComplex divide_i_mutable(MpirBigNum number) {

        MpirBigNum temp3 = number.square();
        temp3.reciprocal(temp3);

        MpirBigNum tempRe = im.mult(number);
        tempRe.add(re, tempRe);
        tempRe.mult(temp3, tempRe);

        MpirBigNum tempIm = re.mult(number);
        im.sub(tempIm, im);
        im.mult(temp3, im);

        re = tempRe;

        return this;
    }

    public final MpirBigNumComplex divide_i(int number) {

        int temp3 = number * number;

        MpirBigNum tempRe = im.mult(number);
        tempRe.add(re, tempRe);
        tempRe.divide(temp3, tempRe);

        MpirBigNum tempIm = re.mult(number);
        im.sub(tempIm, tempIm);
        tempIm.divide(temp3, tempIm);

        return new MpirBigNumComplex(tempRe, tempIm);
    }

    public final MpirBigNumComplex divide_i_mutable(int number) {

        int temp3 = number * number;

        MpirBigNum tempRe = im.mult(number);
        tempRe.add(re, tempRe);
        tempRe.divide(temp3, tempRe);

        MpirBigNum tempIm = re.mult(number);
        im.sub(tempIm, im);
        im.divide(temp3, im);

        re = tempRe;

        return this;
    }

    /*
     *  Real / z
     */
    public final MpirBigNumComplex r_divide(MpirBigNum number) {

        MpirBigNum reSqr = re.square();
        MpirBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        number.divide(reSqr, reSqr);

        MpirBigNum tempIm = im.negate();
        tempIm.mult(reSqr, tempIm);
        return new MpirBigNumComplex(re.mult(reSqr), tempIm);

    }

    public final MpirBigNumComplex r_divide_mutable(MpirBigNum number) {

        MpirBigNum reSqr = re.square();
        MpirBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        number.divide(reSqr, reSqr);

        re.mult(reSqr, re);
        im.negate(im);
        im.mult(reSqr, im);

        return this;

    }

    public final MpirBigNumComplex r_divide(int number) {

        MpirBigNum reSqr = re.square();
        MpirBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.r_divide(number, reSqr);

        MpirBigNum tempIm = im.negate();
        tempIm.mult(reSqr, tempIm);
        return new MpirBigNumComplex(re.mult(reSqr), tempIm);

    }

    public final MpirBigNumComplex r_divide_mutable(int number) {

        MpirBigNum reSqr = re.square();
        MpirBigNum imSqr = im.square();
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
    public final MpirBigNumComplex i_divide(MpirBigNum number) {

        MpirBigNum reSqr = re.square();
        MpirBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        number.divide(reSqr, reSqr);


        return new MpirBigNumComplex(im.mult(reSqr), re.mult(reSqr));

    }

    public final MpirBigNumComplex i_divide_mutable(MpirBigNum number) {

        MpirBigNum reSqr = re.square();
        MpirBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        number.divide(reSqr, reSqr);

        im.mult(reSqr, im);
        re.mult(reSqr, re);

        MpirBigNum temp = re;
        re = im;
        im = temp;

        return  this;

    }

    public final MpirBigNumComplex i_divide(int number) {

        MpirBigNum reSqr = re.square();
        MpirBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.r_divide(number, reSqr);


        return new MpirBigNumComplex(im.mult(reSqr), re.mult(reSqr));

    }

    public final MpirBigNumComplex i_divide_mutable(int number) {

        MpirBigNum reSqr = re.square();
        MpirBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        reSqr.r_divide(number, reSqr);

        im.mult(reSqr, im);
        re.mult(reSqr, re);

        MpirBigNum temp = re;
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
    public final int compare(MpirBigNumComplex z2) {


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

        MpirBigNumComplex z2 = (MpirBigNumComplex)z2c;

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

    public final MpirBigNumComplex fold_right(MpirBigNumComplex z2) {

        if(re.compare(z2.re) < 0) {
            MpirBigNum tempRe = z2.re.sub(re);
            tempRe.mult2(tempRe);
            re.add(tempRe, tempRe);
            return new MpirBigNumComplex(tempRe, new MpirBigNum(im));
        }
        return this;

    }

    public final MpirBigNumComplex fold_left(MpirBigNumComplex z2) {

        if(re.compare(z2.re) > 0) {
            MpirBigNum tempRe = re.sub(z2.re);
            tempRe.mult2(tempRe);
            re.sub(tempRe, tempRe);
            return new MpirBigNumComplex(tempRe, new MpirBigNum(im));
        }
        return this;

    }

    public final MpirBigNumComplex fold_up(MpirBigNumComplex z2) {

        if(im.compare(z2.im) < 0) {
            MpirBigNum tempIm = z2.im.sub(im);
            tempIm.mult2(tempIm);
            im.add(tempIm, tempIm);
            return new MpirBigNumComplex(new MpirBigNum(re), tempIm);
        }
        return  this;


    }

    public final MpirBigNumComplex fold_down(MpirBigNumComplex z2) {

        if(im.compare(z2.im) > 0) {
            MpirBigNum tempIm = im.sub(z2.im);
            tempIm.mult2(tempIm);
            im.sub(tempIm, tempIm);
            new MpirBigNumComplex(new MpirBigNum(re), tempIm);
        }

        return this;

    }

    public final MpirBigNumComplex inflection(MpirBigNumComplex inf) {

        MpirBigNumComplex diff = this.sub(inf);

        return inf.plus(diff.square_mutable());

    }

    public final MpirBigNumComplex shear(MpirBigNumComplex sh) {

        return new MpirBigNumComplex(re.add(im.mult(sh.re)), im.add(re.mult(sh.im)));

    }

    public final MpirBigNumComplex shear_mutable(MpirBigNumComplex sh) {

        MpirBigNum tempRe = im.mult(sh.re);
        tempRe.add(re, tempRe);

        im.add(re.mult(sh.im), im);
        re = tempRe;

        return this;

    }

    /*
     * y + xi
     */
    public final MpirBigNumComplex flip() {

        return new MpirBigNumComplex(new MpirBigNum(im), new MpirBigNum(re));

    }

    public final MpirBigNumComplex flip_mutable() {

        MpirBigNum temp = re;
        re = im;
        im = temp;
        return this;

    }

    /*
     *  |z|^2
     */
    @Override
    public final MpirBigNum normSquared() {

        MpirBigNum reSqr = re.square();
        MpirBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);
        return reSqr;

    }

    /*
     *  |z1 - z2|^2
     */
    @Override
    public final MpirBigNum distanceSquared(GenericComplex za) {
        MpirBigNumComplex z = (MpirBigNumComplex) za;

        MpirBigNum temp_re = re.sub(z.re);
        MpirBigNum temp_im = im.sub(z.im);
        temp_re.square(temp_re);
        temp_im.square(temp_im);
        temp_re.add(temp_im, temp_re);
        return temp_re;

    }

    @Override
    public MpirBigNumComplex times2() {
        return new MpirBigNumComplex(re.mult2(), im.mult2());
    }

    @Override
    public MpirBigNumComplex times2_mutable() {
        re.mult2(re);
        im.mult2(im);
        return this;
    }

    @Override
    public MpirBigNumComplex times4() {
        return new MpirBigNumComplex(re.mult4(), im.mult4());
    }

    public MpirBigNumComplex times4_mutable() {
        re.mult4(re);
        im.mult4(im);
        return this;
    }

    public MpirBigNumComplex divide2() {
        return new MpirBigNumComplex(re.divide2(), im.divide2());
    }

    public MpirBigNumComplex divide2_mutable() {

        re.divide2(re);
        im.divide2(im);
        return this;
    }

    public MpirBigNumComplex divide4() {
        return new MpirBigNumComplex(re.divide4(), im.divide4());
    }

    public MpirBigNumComplex divide4_mutable() {

        re.divide4(re);
        im.divide4(im);
        return this;
    }



    /*
     *  1 / z
     */
    public final MpirBigNumComplex reciprocal() {
        MpirBigNum tempRe = re.square();
        MpirBigNum tempIm = im.square();
        tempRe.add(tempIm, tempRe);
        tempRe.reciprocal(tempRe);

        im.negate(tempIm);
        tempIm.mult(tempRe, tempIm);

        re.mult(tempRe, tempRe);

        return new MpirBigNumComplex(tempRe, tempIm);

    }

    public final MpirBigNumComplex reciprocal_mutable() {
        MpirBigNum tempRe = re.square();
        MpirBigNum tempIm = im.square();
        tempRe.add(tempIm, tempRe);
        tempRe.reciprocal(tempRe);

        im.negate(im);
        im.mult(tempRe, im);

        re.mult(tempRe, re);

        return this;

    }

    public final MpirBigNumComplex reciprocal_mutable(MpirBigNum tempRe, MpirBigNum tempIm) {
        re.square(tempRe);
        im.square(tempIm);
        tempRe.add(tempIm, tempRe);
        tempRe.reciprocal(tempRe);

        im.negate(im);
        im.mult(tempRe, im);

        re.mult(tempRe, re);

        return this;

    }


    public final MpirBigNumComplex fold_out(MpirBigNumComplex z2) {

        MpirBigNum reSqr = re.square();
        MpirBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);

        return reSqr.compare(z2.norm_squared()) > 0 ? this.divide(reSqr) : this;

    }

    public final MpirBigNumComplex fold_in(MpirBigNumComplex z2) {

        MpirBigNum reSqr = re.square();
        MpirBigNum imSqr = im.square();
        reSqr.add(imSqr, reSqr);

        return reSqr.compare(z2.norm_squared()) < 0 ? this.divide(reSqr) : this;

    }

    public final MpirBigNumComplex circle_inversion(MpirBigNumComplex center, MpirBigNum radius) {

        MpirBigNum distance = this.distance_squared(center);
        MpirBigNum radius2 = radius.square();

        radius2.divide(distance, radius2);

        MpirBigNum tempRe = re.sub(center.re);
        tempRe.add(center.re, tempRe);
        tempRe.mult(radius2, tempRe);

        MpirBigNum tempIm = im.sub(center.im);
        tempIm.add(center.im, tempIm);
        tempIm.mult(radius2, tempIm);
        return new MpirBigNumComplex(tempRe, tempIm);

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
        MpirBigNumComplex z = (MpirBigNumComplex) za;
        //re.set(z.re);
        //im.set(z.im);
        MpirBigNum.set(re, im, z.re, z.im);
    }

    public void set(BigComplex z) {
        re.set(z.getRe().toString(true));
        im.set(z.getIm().toString(true));
    }

    public void set(Complex z) {
        re.set(z.getRe());
        im.set(z.getIm());
    }

    public void reset() {
        re.set(0);
        im.set(0);
    }



    /*s
     *  z1 / z2
     */
    @Override
    public final MpirBigNumComplex divide(GenericComplex za) {
        MpirBigNumComplex z = (MpirBigNumComplex)za;

        MpirBigNum temp = z.re;
        MpirBigNum temp2 = z.im;

        MpirBigNum temp3 = temp.square();
        MpirBigNum temp4 = temp2.square();
        temp3.add(temp4, temp3);
        temp3.reciprocal(temp3);

        re.mult(temp, temp4);
        MpirBigNum temp5 = im.mult(temp2);
        temp4.add(temp5, temp4);
        temp4.mult(temp3, temp4);

        im.mult(temp, temp5);
        MpirBigNum temp6 = re.mult(temp2);
        temp5.sub(temp6, temp5);
        temp5.mult(temp3, temp5);

        return new MpirBigNumComplex(temp4, temp5);
    }

    @Override
    public final MpirBigNumComplex divide_mutable(GenericComplex za) {
        MpirBigNumComplex z = (MpirBigNumComplex)za;

        MpirBigNum temp = z.re;
        MpirBigNum temp2 = z.im;

        MpirBigNum temp3 = temp.square();
        MpirBigNum temp4 = temp2.square();
        temp3.add(temp4, temp3);
        temp3.reciprocal(temp3);

        re.mult(temp, temp4);
        MpirBigNum temp5 = im.mult(temp2);
        temp4.add(temp5, temp4);
        temp4.mult(temp3, temp4);

        im.mult(temp, im);
        re.mult(temp2, temp5);
        im.sub(temp5, im);
        im.mult(temp3, im);

        re = temp4;

        return this;
    }

    @Override
    public final MpirBigNumComplex times_mutable(GenericComplex za) {
        MpirBigNumComplex z = (MpirBigNumComplex)za;

//        MpfrBigNum tempRe = new MpfrBigNum(re);
//        MpfrBigNum tempIm = new MpfrBigNum(im);
//
//        tempRe = tempRe.mult(z.re, tempRe);
//        tempIm = tempIm.mult(z.im, tempIm);
//        tempRe = tempRe.sub(tempIm, tempRe);
//
//        re.mult(z.im, re);
//        im.mult(z.re, im);
//        im.add(re, im);
//
//        re = tempRe;

        MpirBigNum ac = re.mult(z.re);
        MpirBigNum bd = im.mult(z.im);

        MpirBigNum tempIm = re.add(im);
        MpirBigNum tempIm2 = z.re.add(z.im);
        tempIm.mult(tempIm2, tempIm);
        tempIm.sub(ac, tempIm);
        tempIm.sub(bd, im);
        ac.sub(bd, re);

        return this;

    }

    /* more efficient z^2 + c */
    @Override
    public final MpirBigNumComplex square_plus_c_mutable(GenericComplex cn) {

        return square_plus_c(cn);

    }

    /*
     *  z1 - z2
     */
    @Override
    public final MpirBigNumComplex sub(GenericComplex zn, MpirBigNum temp, MpirBigNum temp2) {
        MpirBigNumComplex z = (MpirBigNumComplex)zn;

        return new MpirBigNumComplex(re.sub(z.re, temp), im.sub(z.im, temp2));
    }

    public final MpirBigNumComplex times_mutable(MpirBigNumComplex z, MpirBigNum temp1, MpirBigNum temp2, MpirBigNum temp3, MpirBigNum temp4) {

        MpirBigNum ac = re.mult(z.re, temp1);
        MpirBigNum bd = im.mult(z.im, temp2);

        MpirBigNum tempIm = re.add(im, temp3);
        MpirBigNum tempIm2 = z.re.add(z.im, temp4);
        tempIm.mult(tempIm2, tempIm);
        tempIm.sub(ac, tempIm);
        tempIm.sub(bd, im);
        ac.sub(bd, re);

        return this;

    }

    public final MpirBigNumComplex r_sub(int number, MpirBigNum temp, MpirBigNum temp2) {

        return new MpirBigNumComplex(re.r_sub(number, temp), im.negate(temp2));

    }

    public final MpirBigNumComplex square_mutable(MpirBigNum temp1, MpirBigNum temp2) {

        MpirBigNum tempRe = re.add(im, temp1);
        MpirBigNum tempRe2 = re.sub(im, temp2);
        tempRe.mult(tempRe2, tempRe);

        re.mult(im, im);
        im.mult2(im);

        re.set(tempRe);

        return this;
    }

    @Override
    public MpirBigNumComplex times2(MpirBigNum temp1, MpirBigNum temp2) {
        return new MpirBigNumComplex(re.mult2(temp1), im.mult2(temp2));
    }

}
