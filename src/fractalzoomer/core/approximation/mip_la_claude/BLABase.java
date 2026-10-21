package fractalzoomer.core.approximation.mip_la_claude;

import fractalzoomer.core.Complex;

public abstract class BLABase extends BLA {
    public double Ax;
    public double Ay;

    protected BLABase(double r2, Complex A) {
        super(r2);
        this.Ax = A.re;
        this.Ay = A.im;
    }

    @Override
    public Complex getValue(Complex DeltaSubN) {
        double zx = DeltaSubN.re;
        double zy = DeltaSubN.im;
        return new Complex(Ax * zx - Ay * zy, Ax * zy + Ay * zx);
    }

    @Override
    public double hypotA() {
        return Math.hypot(Ax, Ay);
    }

    @Override
    public double getAx() {
        return Ax;
    }

    @Override
    public double getAy() {
        return Ay;
    }
}
