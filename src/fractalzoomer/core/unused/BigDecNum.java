package fractalzoomer.core.unused;

import fractalzoomer.core.MyApfloat;
import fractalzoomer.core.TaskDraw;
import org.apfloat.Apfloat;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecNum {
    private static MathContext mc;
    private BigDecimal bd;

    public static BigDecNum RECIPROCAL_LOG_TWO_BASE_TEN;
    public static BigDecNum TWO;

    public static boolean use_threads;
    public static int THREADS_THRESHOLD = 625;

    static {
        mc = new MathContext((int) MyApfloat.precision, RoundingMode.HALF_EVEN);
        RECIPROCAL_LOG_TWO_BASE_TEN = new BigDecNum(MyApfloat.RECIPROCAL_LOG_TWO_BASE_TEN);
        TWO = new BigDecNum(2);
        use_threads = TaskDraw.USE_THREADS_IN_BIGNUM_LIBS && MyApfloat.precision >= THREADS_THRESHOLD && Runtime.getRuntime().availableProcessors() >= 2;
    }

    public static void reinitialize(double prec) {
        int precision = (int)(prec + 0.5);
        mc = new MathContext(precision, RoundingMode.HALF_EVEN);
        RECIPROCAL_LOG_TWO_BASE_TEN = new BigDecNum(MyApfloat.RECIPROCAL_LOG_TWO_BASE_TEN);
        TWO = new BigDecNum(2);
        use_threads = TaskDraw.USE_THREADS_IN_BIGNUM_LIBS && precision >= THREADS_THRESHOLD && Runtime.getRuntime().availableProcessors() >= 2;
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

    //Very inneficient
    public double doubleValue() {
        return bd.doubleValue();
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

}
