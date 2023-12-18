package fractalzoomer.core;

import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

import java.util.concurrent.Future;

public class BigNumComplex extends GenericComplex {
    private BigNum re;
    private BigNum im;

    public BigNumComplex(BigComplex c) {
        re = BigNum.create(c.getRe());
        im = BigNum.create(c.getIm());
    }

    public BigNumComplex(double re, double im) {
        this.re = BigNum.create(re);
        this.im = BigNum.create(im);
    }

    public BigNumComplex(BigNumComplex c) {
        re = BigNum.copy(c.getRe());
        im = BigNum.copy(c.getIm());
    }

    public BigNumComplex(Complex c) {
        re = BigNum.create(c.getRe());
        im = BigNum.create(c.getIm());
    }

    public BigNumComplex(BigNum re, BigNum im) {
       this.re = re;
       this.im = im;
    }

    public BigNumComplex(Apfloat re, Apfloat im) {
        this.re = BigNum.create(re);
        this.im = BigNum.create(im);
    }

    public BigNumComplex(String re, String im) {
        this.re = BigNum.create(re);
        this.im = BigNum.create(im);
    }

    public BigNumComplex() {

        re = BigNum.create();
        im = BigNum.create();

    }

    @Override
    public BigNumComplex toBigNumComplex() { return this; }

    public final BigNum getRe() {

        return re;

    }

    public final BigNum getIm() {

        return im;

    }

    public final void setRe(BigNum re) {

        this.re = re;

    }

    public final void setIm(BigNum im) {

        this.im = im;

    }

    @Override
    public final Complex toComplex() {
        return new Complex(re.doubleValue(), im.doubleValue());
    }

    /*
     * z1 + z2
     */
    public final BigNumComplex plus(BigNumComplex z) {

        return new BigNumComplex(re.add(z.re), im.add(z.im));

    }

    /*
     *  z + Real
     */
    public final BigNumComplex plus(BigNum number) {

        return new BigNumComplex(re.add(number), im);

    }

    @Override
    public final BigNumComplex plus(int number) {

        return new BigNumComplex(re.add(number), im);

    }

    /*
     *  z + Imaginary
     */
    public final BigNumComplex plus_i(BigNum number) {

        return new BigNumComplex(re, im.add(number));

    }

    /*
     *  z1 - z2
     */
    public final BigNumComplex sub(BigNumComplex z) {

        return  new BigNumComplex(re.sub(z.re), im.sub(z.im));

    }

    /*
     *  z - Real
     */
    public final BigNumComplex sub(BigNum number) {

        return  new BigNumComplex(re.sub(number), im);

    }

    @Override
    public final BigNumComplex sub(int number) {

        return  new BigNumComplex(re.sub(number), im);

    }

    /*
     *  z - Imaginary
     */
    public final BigNumComplex sub_i(BigNum number) {

        return new BigNumComplex(re, im.sub(number));

    }

    /*
     *  Real - z1
     */
    public final BigNumComplex r_sub(BigNum number) {

        return  new BigNumComplex(number.sub(re), im.negate());

    }

    /*
     *  Real - z1
     */
    @Override
    public final BigNumComplex r_sub(int number) {

        return  new BigNumComplex(BigNum.create(number).sub(re), im.negate());

    }

    /*
     *  Imaginary - z
     */
    public final BigNumComplex i_sub(BigNum number) {

        return  new BigNumComplex(re.negate(), number.sub(im));

    }

    /*
     *  z1 * z2
     */
    public final BigNumComplex times(BigNumComplex z) {

        //return new BigNumComplex(re.mult(z.re).sub(im.mult(z.im)),  re.mult(z.im).add(im.mult(z.re)));
        BigNum ac = re.mult(z.re);
        BigNum bd = im.mult(z.im);
        return new BigNumComplex(ac.sub(bd), re.add(im).mult(z.re.add(z.im)).sub(ac).sub(bd));

    }

    /*
     *  z1 * Real
     */
    public final BigNumComplex times(BigNum number) {

        return new BigNumComplex(re.mult(number), im.mult(number));

    }

    /*
     *  z1 * Real
     */
    @Override
    public final BigNumComplex times(int number) {

        return new BigNumComplex(re.mult(number), im.mult(number));

    }

    /*
     *  z * Imaginary
     */
    public final BigNumComplex times_i(BigNum number) {

        return new BigNumComplex(im.negate().mult(number), re.mult(number));

    }

    /*
     *  z^2
     */
    @Override
    public final BigNumComplex square() {
        return new BigNumComplex(re.add(im).mult(re.sub(im)), re.mult(im).mult2());
    }

    /*
     *  z^2 + c
     */
    public final BigNumComplex squareFast_plus_c(NormComponents normComponents, GenericComplex ca) {
        BigNum reSqr = (BigNum) normComponents.reSqr;
        BigNum imSqr = (BigNum) normComponents.imSqr;
        BigNum normSquared = (BigNum) normComponents.normSquared;
        BigNumComplex c = (BigNumComplex) ca;
        return new BigNumComplex(reSqr.sub(imSqr).add(c.re), re.add(im).squareFull().sub(normSquared).add(c.im));
    }

    /*
     *  z^2
     */
    @Override
    public final BigNumComplex squareFast(NormComponents normComponents) {
        BigNum reSqr = (BigNum) normComponents.reSqr;
        BigNum imSqr = (BigNum) normComponents.imSqr;
        BigNum normSquared = (BigNum) normComponents.normSquared;
        return new BigNumComplex(reSqr.sub(imSqr), re.add(im).squareFull().sub(normSquared));
    }

    /*
     *  z^3
     */
    public final BigNumComplex cubeFast(NormComponents normComponents) {

        BigNum temp = (BigNum)normComponents.reSqr;
        BigNum temp2 = (BigNum)normComponents.imSqr;

        return new BigNumComplex(re.mult(temp.sub(temp2.mult(3))), im.mult(temp.mult(3).sub(temp2)));

    }

    /*
     *  z^4
     */
    public final BigNumComplex fourthFast(NormComponents normComponents) {

        BigNum temp = (BigNum)normComponents.reSqr;
        BigNum temp2 = (BigNum)normComponents.imSqr;

        return new BigNumComplex(temp.mult(temp.sub(temp2.mult(6))).add(temp2.squareFull()), re.mult(im).mult4().mult(temp.sub(temp2)));

    }

    /*
     *  z^5
     */
    public final BigNumComplex fifthFast(NormComponents normComponents) {

        BigNum temp = (BigNum)normComponents.reSqr;
        BigNum temp2 = (BigNum)normComponents.imSqr;

        return  new BigNumComplex(re.mult(temp.squareFull().add(temp2.mult(temp2.mult(5).sub(temp.mult(10))))), im.mult(temp2.squareFull().add(temp.mult(temp.mult(5).sub(temp2.mult(10))))));

    }

    @Override
    public NormComponents normSquaredWithComponents(NormComponents n) {
        BigNum reSqr = re.square();
        BigNum imSqr = im.square();
        return new NormComponents(reSqr, imSqr, reSqr.add(imSqr));
    }

    /*
     *  |z|^2
     */
    public final BigNum norm_squared() {

        if(BigNum.useThreads()) {
            Future<BigNum> temp1 = TaskDraw.reference_thread_executor.submit(() -> re.square());
            Future<BigNum> temp2 = TaskDraw.reference_thread_executor.submit(() -> im.square());

            try {
                return temp1.get().add(temp2.get());
            }
            catch (Exception ex) {
                return BigNum.create();
            }
        }
        else {
            return re.square().add(im.square());
        }

    }

    public final BigNum norm_squared_no_threads() {

        return re.square().add(im.square());

    }

    /*
     *  |z|
     */
    public final BigNum norm() {

        return re.square().add(im.square()).sqrt();

    }

    /*
     *  |z1 - z2|^2
     */
    public final BigNum distance_squared(BigNumComplex z) {

        BigNum temp_re = re.sub(z.re);
        BigNum temp_im = im.sub(z.im);
        return temp_re.squareFull().add(temp_im.squareFull());

    }

    /*
     *  |z1 - z2|
     */
    public final BigNum distance(BigNumComplex z) {

        BigNum temp_re = re.sub(z.re);
        BigNum temp_im = im.sub(z.im);
        return temp_re.squareFull().add(temp_im.squareFull()).sqrt();

    }

    /*
     *  |Real|
     */
    public final BigNum getAbsRe() {

        return re.abs();

    }

    /*
     *  |Imaginary|
     */
    public final BigNum getAbsIm() {

        return im.abs();

    }

    /*
     *  |Re(z)| + Im(z)i
     */
    public final BigNumComplex absre() {

        return new BigNumComplex(re.abs(), im);

    }

    /*
     *  Re(z) + |Im(z)|i
     */
    public final BigNumComplex absim() {

        return new BigNumComplex(re, im.abs());

    }

    /*
     *  Real -Imaginary i
     */
    public final BigNumComplex conjugate() {

        return new BigNumComplex(re, im.negate());

    }

    /*
     *  -z
     */
    @Override
    public final BigNumComplex negative() {

        return new BigNumComplex(re.negate(), im.negate());

    }

    /*
     *  z^3
     */
    @Override
    public final BigNumComplex cube() {

        BigNum temp = re.square();
        BigNum temp2 = im.square();

        return new BigNumComplex(re.mult(temp.sub(temp2.mult(3))), im.mult(temp.mult(3).sub(temp2)));

    }

    /*
     *  z^4
     */
    @Override
    public final BigNumComplex fourth() {

        BigNum temp = re.square();
        BigNum temp2 = im.square();

        return new BigNumComplex(temp.mult(temp.sub(temp2.mult(6))).add(temp2.squareFull()), re.mult(im).mult4().mult(temp.sub(temp2)));

    }

    /*
     *  z^5
     */
    @Override
    public final BigNumComplex fifth() {

        BigNum temp = re.square();
        BigNum temp2 = im.square();

        return  new BigNumComplex(re.mult(temp.squareFull().add(temp2.mult(temp2.mult(5).sub(temp.mult(10))))), im.mult(temp2.squareFull().add(temp.mult(temp.mult(5).sub(temp2.mult(10))))));

    }


    /*
     *  abs(z)
     */
    public final BigNumComplex abs() {

        return new BigNumComplex(re.abs(), im.abs());

    }

    /* more efficient z^2 + c */
    public final BigNumComplex square_plus_c(BigNumComplex c) {

        return new BigNumComplex(re.add(im).mult(re.sub(im)).add(c.re), re.mult(im).mult2().add(c.im));

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
    public final BigNumComplex sub(GenericComplex zn) {
        BigNumComplex z = (BigNumComplex)zn;

        return new BigNumComplex(re.sub(z.re), im.sub(z.im));

    }

    /*
     * z1 + z2
     */
    @Override
    public final BigNumComplex plus(GenericComplex zn) {

        BigNumComplex z = (BigNumComplex)zn;
        return new BigNumComplex(re.add(z.re), im.add(z.im));

    }

    /* more efficient z^2 + c */
    public final BigNumComplex square_plus_c(GenericComplex cn) {

        BigNumComplex c = (BigNumComplex)cn;

        /*if(BigNum.useThreads2()) {
            Future<BigNum> temp1 = TaskDraw.reference_thread_executor2.submit(() -> re.squareFull());
            Future<BigNum> temp2 = TaskDraw.reference_thread_executor2.submit(() -> im.squareFull());
            Future<BigNum> temp3 = TaskDraw.reference_thread_executor2.submit(() -> re.add(im).squareFull());

            try {
                BigNum resqr = temp1.get();
                BigNum imsqr = temp2.get();
                BigNum resqrpimsqr = temp3.get();

                return new BigNumComplex(resqr.sub(imsqr).add(c.re), resqrpimsqr.sub(resqr).sub(imsqr).add(c.im));
            }
            catch (Exception ex) {
                return new BigNumComplex();
            }
        }
        else {
            BigNum resqr = re.squareFull();
            BigNum imsqr = im.squareFull();
            BigNum resqrpimsqr = re.add(im).squareFull();

            return new BigNumComplex(resqr.sub(imsqr).add(c.re), resqrpimsqr.sub(resqr).sub(imsqr).add(c.im));
        }*/
//        else
        if(BigNum.useThreads()) {
            Future<BigNum> temp1 = TaskDraw.reference_thread_executor.submit(() -> re.add(im).mult(re.sub(im)).add(c.re));
            Future<BigNum> temp2 = TaskDraw.reference_thread_executor.submit(() -> re.mult(im).mult2().add(c.im));

            try {
                return new BigNumComplex(temp1.get(), temp2.get());
            }
            catch (Exception ex) {
                return new BigNumComplex();
            }
        }
        else {
            return new BigNumComplex(re.add(im).mult(re.sub(im)).add(c.re), re.mult(im).mult2().add(c.im));
        }

    }

    public final BigNumComplex square_plus_c_no_threads(GenericComplex cn) {

        BigNumComplex c = (BigNumComplex)cn;

        return new BigNumComplex(re.add(im).mult(re.sub(im)).add(c.re), re.mult(im).mult2().add(c.im));

    }

    /*
     *  z1 * z2
     */
    @Override
    public final BigNumComplex times(GenericComplex zn) {

        BigNumComplex z = (BigNumComplex)zn;
        //return new BigNumComplex(re.mult(z.re).sub(im.mult(z.im)),  re.mult(z.im).add(im.mult(z.re)));
        BigNum ac = re.mult(z.re);
        BigNum bd = im.mult(z.im);
        return new BigNumComplex(ac.sub(bd), re.add(im).mult(z.re.add(z.im)).sub(ac).sub(bd));

    }

    /*
     *  lexicographical comparison between two complex numbers
     * -1 when z1 > z2
     *  1 when z1 < z2
     *  0 when z1 == z2
     */
    public final int compare(BigNumComplex z2) {

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

        BigNumComplex z2 = (BigNumComplex)z2c;

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

    public final BigNumComplex fold_right(BigNumComplex z2) {

        return re.compare(z2.re) < 0 ? new BigNumComplex(re.add(z2.re.sub(re).mult2()), im) : this;

    }

    public final BigNumComplex fold_left(BigNumComplex z2) {

        return re.compare(z2.re) > 0 ? new BigNumComplex(re.sub(re.sub(z2.re).mult2()), im) : this;

    }

    public final BigNumComplex fold_up(BigNumComplex z2) {

        return im.compare(z2.im) < 0 ? new BigNumComplex(re, im.add(z2.im.sub(im).mult2())) : this;

    }

    public final BigNumComplex fold_down(BigNumComplex z2) {

        return im.compare(z2.im) > 0 ? new BigNumComplex(re, im.sub(im.sub(z2.im).mult2())) : this;

    }

    public final BigNumComplex inflection(BigNumComplex inf) {

        BigNumComplex diff = this.sub(inf);

        return inf.plus(diff.square());

    }

    public final BigNumComplex inflectionPower(BigNumComplex inf, double power) {

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

        return new BigNumComplex();

    }

    public final BigNumComplex shear(BigNumComplex sh) {

        return new BigNumComplex(re.add(im.mult(sh.re)), im.add(re.mult(sh.im)));

    }

    /*
     * y + xi
     */
    public final BigNumComplex flip() {

        return new BigNumComplex(im, re);

    }

    /*
     *  |z|^2
     */
    @Override
    public final BigNum normSquared() {

        return re.square().add(im.square());
        //return re.mult(re).add(im.mult(im));

    }

    /*
     *  |z1 - z2|^2
     */
    @Override
    public final BigNum distanceSquared(GenericComplex za) {
        BigNumComplex z = (BigNumComplex) za;

        BigNum temp_re = re.sub(z.re);
        BigNum temp_im = im.sub(z.im);
        return temp_re.squareFull().add(temp_im.squareFull());

    }

    @Override
    public BigNumComplex times2() {
        return new BigNumComplex(re.mult2(), im.mult2());
    }

    @Override
    public BigNumComplex times4() {
        return new BigNumComplex(re.mult4(), im.mult4());
    }

    @Override
    public MantExpComplex toMantExpComplex() { return MantExpComplex.create(this);}

    @Override
    public void set(GenericComplex za) {
        BigNumComplex z = (BigNumComplex) za;
        re = z.re;
        im = z.im;
    }

    @Override
    public GenericComplex sub_mutable(int a) {return sub(a);}

    @Override
    public GenericComplex plus_mutable(int a) {return plus(a);}

    @Override
    public GenericComplex times_mutable(GenericComplex a) {return times(a);}

    @Override
    public GenericComplex plus_mutable(GenericComplex v) { return plus(v); }

    @Override
    public GenericComplex abs_mutable() { return abs(); }

    public boolean isZero() {
        return re.isZero() && im.isZero();
    }

    public boolean isOne() {
        return re.isOne() && im.isZero();
    }

    /*
     *  z^3
     */
    @Override
    public final BigNumComplex squareFast_mutable(NormComponents normComponents) {

        return squareFast(normComponents);

    }

    /*
     *  z^3
     */
    @Override
    public final BigNumComplex cubeFast_mutable(NormComponents normComponents) {

        return cubeFast(normComponents);

    }

    /*
     *  z^4
     */
    @Override
    public final BigNumComplex fourthFast_mutable(NormComponents normComponents) {

        return fourthFast(normComponents);

    }

    /*
     *  z^5
     */
    @Override
    public final BigNumComplex fifthFast_mutable(NormComponents normComponents) {

        return fifthFast(normComponents);

    }

    /*
     *  z^2 + c
     */
    @Override
    public final BigNumComplex squareFast_plus_c_mutable(NormComponents normComponents, GenericComplex ca) {
        return squareFast_plus_c(normComponents, ca);
    }

    /* more efficient z^2 + c */
    @Override
    public final BigNumComplex square_plus_c_mutable(GenericComplex cn) {

        return square_plus_c(cn);

    }

    @Override
    public final BigNumComplex square_plus_c_mutable_no_threads(GenericComplex cn) {

        return square_plus_c_no_threads(cn);

    }

    /*
     *  Real -Imaginary i
     */
    @Override
    public final BigNumComplex conjugate_mutable() {

        return conjugate();

    }

    @Override
    public final BigNumComplex times_mutable(int number) {

        return times(number);

    }

    @Override
    public GenericComplex sub_mutable(GenericComplex v) { return sub(v); }

    @Override
    public BigNumComplex times2_mutable() {
        return times2();
    }

    @Override
    public BigNumComplex negative_mutable() {

        return negative();

    }

    @Override
    public BigNumComplex square_mutable() {

        return square();

    }

    @Override
    public BigNumComplex absNegateRe_mutable() {
        return new BigNumComplex(re.abs().negate(), im);
    }

    @Override
    public BigNumComplex absNegateIm_mutable() {
        return new BigNumComplex(re, im.abs().negate());
    }

    @Override
    public BigNumComplex absre_mutable() { return  absre(); }

    @Override
    public BigComplex toBigComplex() {return new BigComplex(re.toApfloat(), im.toApfloat());}

    public static void main(String[] args) {

        MyApfloat.setPrecision(3000);
        MyApfloat re = new MyApfloat("-1.7685653943536636812525937129345323689264178203655023808403568327813984751602057983694075544809385380635853233562272021595486038792597346267621026418533261924962244609714446430169626331467057579947033159779441985348128008304981334471441266997824980553144563791567792114724296121872944832614093450944724492334683449190475005836849397812504637683710510408861059046995537629068401998856337076707729082352934525561410799942777533219989198396355688230764560224323159046285245196155466314399804239661358274503819615557368332052446821888562141313581523317892262380088027074432482039394916527640029121955174213203521566050696042500969919946717868654263385845711621571084795576591138961066064076009303183875949501143195217469003173308513486183081233774459137115288547807735477262202007250717008710283162223251492449408111203225925774749231087606075791025786880373414419640567812051244300178691506999924356763498211814367907336500103294768040948403104863777932123346593677739181711573037377537612597105572567432844531248119698069826986109774175430124235000261952935148608089775593025955571455701166888054695292300393363716767974622037919908584052332982637466");
        MyApfloat im = new MyApfloat("0.00149693415390767795776818884840489556855946301445691471574014563855527433886417969977385819538260268120841953162872636930325763746322273045770475720864573841501787930094585669029854545526055550254240550601638349230447392478835897915689588386917873306732459133130195499040290663241163281171562214964938877814041525983714426684720617999806166857035264185620487882712073265176954914054913266203287997924901540871019242527521230712886590484380712839459054394699971951683593643432733875864612142164058384584027531954686991700717520592706134315477867770419967332102686480959769035927998828366145957010260008071330081671951130257876517738836139132327131150083875547829353693231330986024536074662266149266972020406424662729505261246207754916338512723205243386084554727716044392705072728590247105881028092304993724655676823686703579759639901910397135711042548453158584111749222905493046484296618244721966973379997931675069363108125568864266991641443350605262290076130999673222331940884558082142583551902556005768303536299446355536559649684565312212482597275388117026700207573378170627060834006934127513560312023382257072757055987599151386137785304306581858");

        BigNumComplex z = new BigNumComplex();
        BigNumComplex c = new BigNumComplex(re, im);

        int iterations = 1500000;

        long time = System.currentTimeMillis();

        int i;
        for(i = 0; i < iterations ; i++) {

            if(z.toComplex().norm_squared() >= 4) {
                break;
            }

            z = z.square_plus_c(c);

        }

        System.out.println(System.currentTimeMillis() - time);
        System.out.println(i);

        TaskDraw.reference_thread_executor.shutdown();
    }
}
