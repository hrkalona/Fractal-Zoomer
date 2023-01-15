package fractalzoomer.core.mpir;

import com.sun.jna.Memory;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpfr.mpfr_t;
import org.apfloat.Apfloat;

import static fractalzoomer.core.mpfr.LibMpfr.mpfr_get_f;
import static fractalzoomer.core.mpir.LibMpir.*;

public class MpfMemory extends Memory {
    public mpf_t peer;

    public MpfMemory(boolean noinit) {
        super(mpf_t.SIZE);
        peer = new mpf_t(this);
    }
    public MpfMemory() {
        super(mpf_t.SIZE);
        peer = new mpf_t(this);
        __gmpf_init2(peer, MpirBigNum.precision);
        __gmpf_set_d(peer, 0);
    }

    public MpfMemory(mpf_t op) {
        super(mpf_t.SIZE);
        peer = new mpf_t(this);
        __gmpf_init2(peer, MpirBigNum.precision);
        __gmpf_set(peer, op);
    }

    public MpfMemory(mpfr_t op) {
        super(mpf_t.SIZE);
        peer = new mpf_t(this);
        __gmpf_init2(peer, MpirBigNum.precision);
        mpfr_get_f(peer, op, MpfrBigNum.rounding);
    }

    public MpfMemory(double value) {
        super(mpf_t.SIZE);
        peer = new mpf_t(this);
        __gmpf_init2(peer, MpirBigNum.doublePrec);
        __gmpf_set_d(peer, value);
    }

    public MpfMemory(int value) {
        super(mpf_t.SIZE);
        peer = new mpf_t(this);
        __gmpf_init2(peer, MpirBigNum.intPrec);
        __gmpf_set_si(peer, value);
    }

    public MpfMemory(String value) {
        super(mpf_t.SIZE);
        peer = new mpf_t(this);
        __gmpf_init2(peer, MpirBigNum.precision);
        __gmpf_set_str(peer, value, MpirBigNum.base);
    }

    public MpfMemory(Apfloat number) {
        super(mpf_t.SIZE);
        peer = new mpf_t(this);
        __gmpf_init2(peer, MpirBigNum.precision);
        __gmpf_set_str(peer, number.toString(true), MpirBigNum.base);
    }

    public void set(MpfMemory memory) {
        __gmpf_set(peer, memory.peer);
    }

    public void set(double number) {
        __gmpf_set_d(peer, number);
    }

    public void set(int number) {
        __gmpf_set_si(peer, number);
    }

    public void set(String number) {
        __gmpf_set_str(peer, number, MpirBigNum.base);
    }


    @Override protected void finalize() throws Throwable {
        if(peer != null) {
            __gmpf_clear(peer);
            peer = null;
        }
        super.finalize();
    }

    @Override
    public void close() {
        if(peer != null) {
            __gmpf_clear(peer);
            peer = null;
        }
        super.close();
    }
}
