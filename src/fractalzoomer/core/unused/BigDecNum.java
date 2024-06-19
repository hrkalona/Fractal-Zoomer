package fractalzoomer.core.unused;

import fractalzoomer.core.MyApfloat;
import fractalzoomer.core.TaskRender;
import org.apfloat.Apfloat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;

public class BigDecNum {
    private static MathContext mc;
    private BigDecimal bd;

    /*
     * Let l = log_2(10).
     * Then, L < l < L + ulp(L) / 2, that is, L = roundTiesToEven(l).
     */
    private static final double L = 3.321928094887362;
    private static final int P_D = 53;  // 53
    private static final int Q_MIN_D = (Double.MIN_EXPONENT - (P_D - 1));  // -1_074
    private static final int Q_MAX_D = (Double.MAX_EXPONENT - (P_D - 1));  // 971

    public static BigDecNum RECIPROCAL_LOG_TWO_BASE_TEN;
    public static BigDecNum TWO;

    public static boolean use_threads;
    public static int THREADS_THRESHOLD = 625;


    static {
        mc = new MathContext((int) MyApfloat.precision, RoundingMode.HALF_EVEN);
        RECIPROCAL_LOG_TWO_BASE_TEN = new BigDecNum(MyApfloat.RECIPROCAL_LOG_TWO_BASE_TEN);
        TWO = new BigDecNum(2);
        use_threads = TaskRender.USE_THREADS_IN_BIGNUM_LIBS && MyApfloat.precision >= THREADS_THRESHOLD && Runtime.getRuntime().availableProcessors() >= 2;
    }

    public static void reinitialize(double prec) {
        int precision = (int)(prec + 0.5);
        mc = new MathContext(precision, RoundingMode.HALF_EVEN);
        RECIPROCAL_LOG_TWO_BASE_TEN = new BigDecNum(MyApfloat.RECIPROCAL_LOG_TWO_BASE_TEN);
        TWO = new BigDecNum(2);
        use_threads = TaskRender.USE_THREADS_IN_BIGNUM_LIBS && precision >= THREADS_THRESHOLD && Runtime.getRuntime().availableProcessors() >= 2;
    }

    public BigDecNum(BigDecNum bd) {
        this.bd = bd.bd;
    }

    public BigDecNum(BigDecimal bd) {
        this.bd = bd;
    }

    public BigDecNum() {
        bd = BigDecimal.ZERO;
    }

    public BigDecNum(double b) {
        bd = new BigDecimal(b, mc);
    }

    public BigDecNum(int b) {
        bd = new BigDecimal(b, mc);
    }

    public BigDecNum(String b) {
        bd = new BigDecimal(b, mc);
    }

    public BigDecNum(Apfloat b) {
        bd = new BigDecimal(b.toString(true), mc);
    }

    public double doubleValue() {
        //return bd.doubleValue(); //Very inneficient
        return fullDoubleValue();
    }

    public BigDecNum add(BigDecNum b) {
        return new BigDecNum(bd.add(b.bd, mc));
    }

    public BigDecNum add(double b) {
        return new BigDecNum(bd.add(new BigDecimal(b, mc), mc));
    }

    public BigDecNum add(int b) {
        return new BigDecNum(bd.add(new BigDecimal(b, mc), mc));
    }

    public BigDecNum sub(BigDecNum b) {
        return new BigDecNum(bd.subtract(b.bd, mc));
    }

    public BigDecNum sub(double b) {
        return new BigDecNum(bd.subtract(new BigDecimal(b, mc), mc));
    }

    public BigDecNum sub(int b) {
        return new BigDecNum(bd.subtract(new BigDecimal(b, mc), mc));
    }

    public BigDecNum mult(BigDecNum b) {
        return new BigDecNum(bd.multiply(b.bd, mc));
    }

    public BigDecNum mult(double b) {
        return new BigDecNum(bd.multiply(new BigDecimal(b, mc), mc));
    }

    public BigDecNum mult(int b) {
        return new BigDecNum(bd.multiply(new BigDecimal(b, mc), mc));
    }

    public BigDecNum mult2() {
        return new BigDecNum(bd.add(bd, mc));
    }

    public BigDecNum divide(BigDecNum b) {
        return new BigDecNum(bd.divide(b.bd, mc));
    }

    public BigDecNum divide(double b) {
        return new BigDecNum(bd.divide(new BigDecimal(b, mc), mc));
    }

    public BigDecNum divide(int b) {
        return new BigDecNum(bd.divide(new BigDecimal(b, mc), mc));
    }

    public BigDecNum reciprocal() {
        return new BigDecNum(BigDecimal.ONE.divide(bd, mc));
    }

    public BigDecNum square() {
        return new BigDecNum(bd.multiply(bd, mc));
    }

    public int compare(BigDecNum other) {
        return bd.compareTo(other.bd);
    }

    public BigDecNum abs() {
        return new BigDecNum(bd.abs());
    }

    public BigDecNum negate() {
        return new BigDecNum(bd.negate());
    }

    public static BigDecNum max(BigDecNum a, BigDecNum b) {
        return new BigDecNum(a.bd.max(b.bd));
    }

    public static BigDecNum min(BigDecNum a, BigDecNum b) {
        return new BigDecNum(a.bd.min(b.bd));
    }

    public boolean isPositive() {
        return bd.signum() == 1;
    }
    public boolean isZero() {
        return bd.signum() == 0;
    }
    public boolean isNegative() {
        return bd.signum() == -1;
    }

    public boolean isOne() { return bd.compareTo(BigDecimal.ONE) == 0;}

    @Override
    public String toString() {
        return bd.toString();
    }

    public int scale() {

        if(bd.scale() != 0) {
            return -(bd.scale() - bd.precision() + 1);
        }
        else {
            return bd.precision() - 1;
        }

    }

    public BigDecNum pow(int n) {
        return new BigDecNum(bd.pow(n, mc));
    }

    public Apfloat toApfloat() { return new MyApfloat(bd.toString());}

    //Copy from BigDecimal code
    private double fullDoubleValue() {
        /*
         * This method works on all instances but might throw or consume a lot
         * of memory and cpu on huge scales or huge significands.
         *
         * It is expected that this computations might exhaust memory or consume
         * an unreasonable amount of cpu when both the significand and the scale
         * are huge and conjure to meet MIN < |this| < MAX, where MIN and MAX
         * are approximately Double.MIN_VALUE and Double.MAX_VALUE, resp.
         */


        /*
         * Let
         *      w = |unscaledValue()|
         *      s = scale
         *      bl = w.bitLength()
         *      P = Double.PRECISION  // 53
         *      Q_MIN = Double.MIN_EXPONENT - (P - 1)  // -1_074
         *      Q_MAX = Double.MAX_EXPONENT - (P - 1)  // 971
         * Thus
         *      |this| = w 10^{-s}
         *      Double.MIN_VALUE = 2^Q_MIN
         *      Double.MAX_VALUE = (2^P - 1) 2^Q_MAX
         * Here w > 0, so 2^{bl-1} <= w < 2^bl, hence
         *      bl = floor(log_2(w)) + 1
         *
         * To determine the return value, it helps to define real beta
         * and integer q meeting
         *      w 10^{-s} = beta 2^q such that 2^{P+1} <= beta < 2^{P+2}
         * Note that floor(log_2(beta)) = P + 1.
         * The reason for having beta meet these inequalities rather than the
         * more "natural" 2^{P-1} <= beta < 2^P will become clearer below.
         * (They ensure that there's room for a "round" and a "sticky" bit.)
         *
         * Determining beta and q, however, requires costly computations.
         * Instead, try to quickly determine integer bounds ql, qh such that
         * ql <= q <= qh and with qh - ql as small as reasonably possible.
         * They help to quickly filter out most values that do not round
         * to a finite, non-zero double.
         *
         * To this end, let l = log_2(10). Then
         *      log_2(w) - s l = log_2(w 10^{-s}) = log_2(beta) + q
         * Mathematically, for any real x, y:
         *      floor(x) + floor(y) <= floor(x + y) <= floor(x) + floor(y) + 1
         *      floor(-x) = -ceil(x)
         * Therefore, remembering that
         *      floor(log_2(w)) = bl - 1 and floor(log_2(beta)) = P + 1
         * the above leads to
         *      bl - ceil(s l) - P - 2 <= q <= bl - ceil(s l) - P - 1
         *
         * However, ceil(s l) is still a purely mathematical quantity.
         * To determine computable bounds for it, let L = roundTiesToEven(l)
         * and let u = 2^{-P} (see the comment about constant L).
         * Let * denote multiplication on doubles, which is subject to errors.
         * Then, since all involved values are not subnormals, it follows that
         * (see any textbook on numerical algorithms):
         *      s * L = s l (1 + delta_1) (1 + delta_2) = s l (1 + theta)
         * where |delta_i| <= u, |theta| <= 2u / (1 - 2u) < 4u = 2^{2-P}
         * The delta_i account for the relative error of l and of *.
         * Note that s (the int scale) converts exactly as double.
         * Hence, as 3 < l < 4
         *      |s * L - s l| = |s| l |theta| < 2^31 4 2^{2-P} = 2^{-18} < 1
         * For reals x, y, |x - y| <= 1 entails |ceil(x) - ceil(y)| <= 1. Thus,
         *      ceil(s * L) - 1 <= ceil(s l) <= ceil(s * L) + 1
         *
         * Using these inequalities implies
         *      bl - ceil(s * L) - P - 3 <= q <= bl - ceil(s * L) - P
         * finally leading to the definitions
         *      qb = bl - ceil(s * L), ql = qb - P - 3, qh = qb - P
         * meeting
         *      ql <= q <= qh and qh - ql = 3, which is small enough.
         * Note that qb doesn't always fit in an int.
         *
         * To filter out most values that round to 0 or infinity, define
         *      ZCO = 1/2 2^Q_MIN = 2^{Q_MIN-1}    (zero cutoff)
         *      ICO = (2^P - 1/2) 2^Q_MAX    (infinity cutoff)
         * Return [+/-]0 iff |this| <= ZCO, [+/-]infinity iff |this| >= ICO.
         *
         * To play safely, whenever 2^{P+2} 2^qh <= ZCO then
         *      |this| = beta 2^q < 2^{P+2} 2^qh <= ZCO
         * Now, 2^{P+2} 2^qh <= ZCO means the same as P + 2 + qh < Q_MIN,
         * leading to
         *      if qb < Q_MIN - 2 then return [+/-]0
         *
         * Similarly, whenever 2^{P+1} 2^ql >= 2^P 2^Q_MAX then
         *      |this| = beta 2^q >= 2^{P+1} 2^ql >= 2^P 2^Q_MAX > ICO
         * Here, 2^{P+1} 2^ql >= 2^P 2^Q_MAX is equivalent to ql + 2 > Q_MAX,
         * which entails
         *      if qb > Q_MAX + P + 1 then return [+/-]infinity
         *
         * Observe that |s * L| <= 2^31 4 = 2^33, so
         *      (long) ceil(s * L) = ceil(s * L)
         * since all integers <= 2^P are exact doubles.
         */
        int scale = bd.scale();
        BigInteger w = bd.unscaledValue().abs();
        long qb = w.bitLength() - (long) Math.ceil(scale * L);
        if (qb < Q_MIN_D - 2) {  // qb < -1_076
            return bd.signum() * 0.0;
        }
        if (qb > Q_MAX_D + P_D + 1) {  // qb > 1_025
            /* If s <= -309 then qb >= 1_027, so these cases all end up here. */
            return bd.signum() * Double.POSITIVE_INFINITY;
        }

        /*
         * There's still a small chance to return [+/-]0 or [+/-]infinity.
         * But rather than chasing for specific cases, do the full computations.
         * Here, Q_MIN - 2 <= qb <= Q_MAX + P + 1
         */
        if (scale < 0) {
            /*
             * Here -309 < s < 0, so w 10^{-s} is an integer: delegate to
             * BigInteger.doubleValue() without further ado.
             * Also, |this| < 10^309, so the integers involved are manageable.
             */
            return bd.signum() * w.multiply(bigTenToThe(-scale)).doubleValue();
        }
        if (scale == 0) {
            return bd.signum() * w.doubleValue();
        }

        /*
         * This last case has s > 0 and sometimes unmanageable large integers.
         * It is expected that these computations might exhaust memory or
         * consume an unreasonable amount of cpu when both w and s are huge.
         *
         * Assume a number eta >= 2^{P+1} and split it into i = floor(eta)
         * and f = eta - i. Thus i >= 2^{P+1} and 0 <= f < 1.
         * Define sb = 0 iff f = 0 and sb = 1 iff f > 0.
         * Let j = i | sb (| denotes bitwise "or").
         * j has at least P + 2 bits to accommodate P most significand bits
         * (msb), 1 rounding bit rb just to the right of them and 1 "sticky" bit
         * sb as its least significant bit, as depicted here:
         * eta = | P msb | rb | ... | lsb | bits of fraction f...
         * i   = | P msb | rb | ... | lsb |
         * j   = | P msb | rb | ... | sb  |
         * All the bits in eta, i and j to the left of lsb or sb are identical.
         * It's not hard to see that
         *      roundTiesToEven(eta) = roundTiesToEven(j)
         *
         * To apply the above, define
         *      eta = (w/10^s) 2^{-ql}
         * which meets
         *      eta = (w/10^s) 2^{-q} 2^{q-ql} = beta 2^{q-ql} = beta 2^dq
         * where dq = q - ql. Therefore, since ql <= q <= qh = ql + 3
         *      2^{P+1} <= eta < 2^{P+2}    iff q = ql
         *      2^{P+2} <= eta < 2^{P+3}    iff q = ql + 1
         *      2^{P+3} <= eta < 2^{P+4}    iff q = ql + 2
         *      2^{P+4} <= eta < 2^{P+5}    iff q = ql + 3
         * There are no other cases. The same holds for i = floor(eta),
         * which therefore fits in a long, as P + 5 < Long.SIZE:
         *      2^{P+1} <= i < 2^{P+2}      iff q = ql
         *      2^{P+2} <= i < 2^{P+3}      iff q = ql + 1
         *      2^{P+3} <= i < 2^{P+4}      iff q = ql + 2
         *      2^{P+4} <= i < 2^{P+5}      iff q = ql + 3
         * This shows dq = bitLength(i) - (P + 2).
         *
         * Let integer m = w 2^{-ql} if ql <= 0, or m = w if ql > 0 and
         * let integer n = 10^s if ql <= 0, or n = 10^s 2^ql if ql > 0.
         * It follows that eta = m/n, i = m // n, (// is integer division)
         * and f = (m \\ n) / n (\\ is binary "mod" (remainder)).
         * Of course, f > 0 iff m \\ n > 0, hence sb = signum(m \\ n).
         *
         * If q >= Q_MIN - 2 then |this| is in the normal range or overflows.
         * With eq = Q_MIN - 2 - ql the condition is the same as dq >= eq.
         * Provided |this| = eta 2^ql does not overflow, it follows that
         *      roundTiesToEven(|this|) = roundTiesToEven(eta) 2^ql
         *          = roundTiesToEven(j) 2^ql = scalb((double) j, ql)
         * If |this| overflows, however, so does scalb((double) j, ql). Thus,
         * in either case
         *      roundTiesToEven(|this|) = scalb((double) j, ql)
         *
         * When q < Q_MIN - 2, that is, when dq < eq, |this| is in the
         * subnormal range. The integer j needs to be shortened to ensure that
         * the precision is gradually shortened for the final significand.
         *      |this| = eta 2^ql = (eta/2^eq) 2^{Q_MIN-2}
         * Compare eta and i as depicted here
         * eta = | msb | eq lsb | bits of fraction f...
         * i   = | msb | eq lsb |
         * where there are eq least significant bits in the right section.
         * To obtain j in this case, shift i to the right by eq positions and
         * thereafter "or" its least significant bit with signum(eq lsb) and
         * with sb as defined above. This leads to
         *      roundTiesToEven(|this|) = scalb((double) j, Q_MIN - 2)
         */
        int ql = (int) qb - (P_D + 3);  // narrowing qb to an int is safe
        BigInteger pow10 = bigTenToThe(scale);
        BigInteger m, n;
        if (ql <= 0) {
            m = w.shiftLeft(-ql);
            n = pow10;
        } else {
            m = w;
            n = pow10.shiftLeft(ql);
        }

        BigInteger[] qr = m.divideAndRemainder(n);
        long i = qr[0].longValue();
        int sb = qr[1].signum();
        int dq = (Long.SIZE - (P_D + 2)) - Long.numberOfLeadingZeros(i);
        int eq = (Q_MIN_D - 2) - ql;
        if (dq >= eq) {
            return bd.signum() * Math.scalb((double) (i | sb), ql);
        }

        /* Subnormal */
        long mask = (1L << eq) - 1;
        long j = i >> eq | Long.signum(i & mask) | sb;
        return bd.signum() * Math.scalb((double) j, Q_MIN_D - 2);
    }

    private static volatile BigInteger[] BIG_TEN_POWERS_TABLE = {
            BigInteger.ONE,
            BigInteger.valueOf(10),
            BigInteger.valueOf(100),
            BigInteger.valueOf(1000),
            BigInteger.valueOf(10000),
            BigInteger.valueOf(100000),
            BigInteger.valueOf(1000000),
            BigInteger.valueOf(10000000),
            BigInteger.valueOf(100000000),
            BigInteger.valueOf(1000000000),
            BigInteger.valueOf(10000000000L),
            BigInteger.valueOf(100000000000L),
            BigInteger.valueOf(1000000000000L),
            BigInteger.valueOf(10000000000000L),
            BigInteger.valueOf(100000000000000L),
            BigInteger.valueOf(1000000000000000L),
            BigInteger.valueOf(10000000000000000L),
            BigInteger.valueOf(100000000000000000L),
            BigInteger.valueOf(1000000000000000000L)
    };
    private static final int BIG_TEN_POWERS_TABLE_INITLEN =
            BIG_TEN_POWERS_TABLE.length;
    private static final int BIG_TEN_POWERS_TABLE_MAX =
            16 * BIG_TEN_POWERS_TABLE_INITLEN;

    private static BigInteger bigTenToThe(int n) {
        if (n < 0)
            return BigInteger.ZERO;

        if (n < BIG_TEN_POWERS_TABLE_MAX) {
            BigInteger[] pows = BIG_TEN_POWERS_TABLE;
            if (n < pows.length)
                return pows[n];
            else
                return expandBigIntegerTenPowers(n);
        }

        return BigInteger.TEN.pow(n);
    }

    private static BigInteger expandBigIntegerTenPowers(int n) {
        synchronized(BigDecimal.class) {
            BigInteger[] pows = BIG_TEN_POWERS_TABLE;
            int curLen = pows.length;
            // The following comparison and the above synchronized statement is
            // to prevent multiple threads from expanding the same array.
            if (curLen <= n) {
                int newLen = curLen << 1;
                while (newLen <= n) {
                    newLen <<= 1;
                }
                pows = Arrays.copyOf(pows, newLen);
                for (int i = curLen; i < newLen; i++) {
                    pows[i] = pows[i - 1].multiply(BigInteger.TEN);
                }
                // Based on the following facts:
                // 1. pows is a private local variable;
                // 2. the following store is a volatile store.
                // the newly created array elements can be safely published.
                BIG_TEN_POWERS_TABLE = pows;
            }
            return pows[n];
        }
    }

    public static void main(String[] args) {

        MyApfloat.setPrecision(1200);
        BigDecNum.reinitialize(1200);

        BigDecNum zre = new BigDecNum();
        BigDecNum zim = new BigDecNum();
        BigDecNum cre = new BigDecNum(new MyApfloat("-1.7685653943536636812525937129345323689264178203655023808403568327813984751602057983694075544809385380635853233562272021595486038792597346267621026418533261924962244609714446430169626331467057579947033159779441985348128008304981334471441266997824980553144563791567792114724296121872944832614093450944724492334683449190475005836849397812504637683710510408861059046995537629068401998856337076707729082352934525561410799942777533219989198396355688230764560224323159046285245196155466314399804239661358274503819615557368332052446821888562141313581523317892262380088027074432482039394916527640029121955174213203521566050696042500969919946717868654263385845711621571084795576591138961066064076009303183875949501143195217469003173308513486183081233774459137115288547807735477262202007250717008710283162223251492449408111203225925774749231087606075791025786880373414419640567812051244300178691506999924356763498211814367907336500103294768040948403104863777932123346593677739181711573037377537612597105572567432844531248119698069826986109774175430124235000261952935148608089775593025955571455701166888054695292300393363716767974622037919908584052332982637466"));
        BigDecNum cim = new BigDecNum(new MyApfloat("0.00149693415390767795776818884840489556855946301445691471574014563855527433886417969977385819538260268120841953162872636930325763746322273045770475720864573841501787930094585669029854545526055550254240550601638349230447392478835897915689588386917873306732459133130195499040290663241163281171562214964938877814041525983714426684720617999806166857035264185620487882712073265176954914054913266203287997924901540871019242527521230712886590484380712839459054394699971951683593643432733875864612142164058384584027531954686991700717520592706134315477867770419967332102686480959769035927998828366145957010260008071330081671951130257876517738836139132327131150083875547829353693231330986024536074662266149266972020406424662729505261246207754916338512723205243386084554727716044392705072728590247105881028092304993724655676823686703579759639901910397135711042548453158584111749222905493046484296618244721966973379997931675069363108125568864266991641443350605262290076130999673222331940884558082142583551902556005768303536299446355536559649684565312212482597275388117026700207573378170627060834006934127513560312023382257072757055987599151386137785304306581858"));

        long time = System.currentTimeMillis();
        int i, max_iter = 1500000;
        for(i = 0; i < max_iter; i++) {
            double dre = zre.doubleValue();
            double dim = zim.doubleValue();

            if(dre * dre + dim * dim > 4) {
                break;
            }

            BigDecNum temp = zre.add(zim).mult(zre.sub(zim)).add(cre);
            zim = zre.mult(zim).mult2().add(cim);
            zre = temp;
        }

        System.out.println(i);
        System.out.println(System.currentTimeMillis() - time);
    }


}
