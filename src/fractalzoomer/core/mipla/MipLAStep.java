package fractalzoomer.core.mipla;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.Fractal;

public abstract class MipLAStep {
    public static double ValidRadiusScale = 0x1.0p-24;
    protected double Ax;
    protected double Ay;
    protected double ValidRadius, ValidRadiusC;

    public MipLAStep() {

    }

    public MipLAStep(Complex z, Fractal f) {
        Complex A = f.getBlaA(z);
        Ax = A.getRe();
        Ay = A.getIm();
        ValidRadius = z.chebyshevNorm() * ValidRadiusScale;
        ValidRadiusC = Double.MAX_VALUE;
    }

    public abstract MipLAStep Composite(MipLAStep step);

    public abstract Complex getValue(Complex dz, Complex dc);

    public abstract Complex getValue(double dre, double dim, double d0re, double d0im);

    public abstract Complex getValue(Complex DeltaSubN, double DeltaSub0);

    public Complex getValue(Complex DeltaSubN) {
        double zx = DeltaSubN.getRe();
        double zy = DeltaSubN.getIm();
        return new Complex(Ax * zx - Ay * zy, Ax * zy + Ay * zx);
    }

    public abstract Complex getB();
}
