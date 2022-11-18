package fractalzoomer.core.mpfr;

import com.sun.jna.Memory;
import org.apfloat.Apfloat;

import static fractalzoomer.core.mpfr.LibMpfr.*;

public class MpfrMemory extends Memory {
    public final mpfr_t peer;


    public MpfrMemory(boolean noinit) {
        super(mpfr_t.SIZE);
        peer = new mpfr_t(this);
    }

    public MpfrMemory() {
        super(mpfr_t.SIZE);
        peer = new mpfr_t(this);
        mpfr_init2(peer, MpfrBigNum.precision);
        mpfr_set_d(peer, 0, MpfrBigNum.rounding);
    }

    public MpfrMemory(mpfr_t op) {
        super(mpfr_t.SIZE);
        peer = new mpfr_t(this);
        mpfr_init2(peer, MpfrBigNum.precision);
        mpfr_set(peer, op, MpfrBigNum.rounding);
    }

    public MpfrMemory(double value) {
        super(mpfr_t.SIZE);
        peer = new mpfr_t(this);
        mpfr_init2(peer, MpfrBigNum.doublePrec);
        mpfr_set_d(peer, value, MpfrBigNum.rounding);
    }

    public MpfrMemory(int value) {
        super(mpfr_t.SIZE);
        peer = new mpfr_t(this);
        mpfr_init2(peer, MpfrBigNum.intPrec);
        mpfr_set_si(peer, value, MpfrBigNum.rounding);
    }

    public MpfrMemory(String value) {
        super(mpfr_t.SIZE);
        peer = new mpfr_t(this);
        mpfr_init2(peer, MpfrBigNum.precision);
        mpfr_set_str(peer, value, MpfrBigNum.base, MpfrBigNum.rounding);
    }

    public MpfrMemory(Apfloat number) {
        super(mpfr_t.SIZE);
        peer = new mpfr_t(this);
        mpfr_init2(peer, MpfrBigNum.precision);
        mpfr_set_str(peer, number.toString(true), MpfrBigNum.base, MpfrBigNum.rounding);
    }

    public void set(MpfrMemory memory) {
        mpfr_set(peer, memory.peer, MpfrBigNum.rounding);
    }

    public void set(double number) {
        mpfr_set_d(peer, number, MpfrBigNum.rounding);
    }

    public void set(int number) {
        mpfr_set_si(peer, number, MpfrBigNum.rounding);
    }

    public void set(String number) {
        mpfr_set_str(peer, number, MpfrBigNum.base, MpfrBigNum.rounding);
    }

    @Override protected void finalize() throws Throwable {
        mpfr_clear(peer);
        super.finalize();
    }
}
