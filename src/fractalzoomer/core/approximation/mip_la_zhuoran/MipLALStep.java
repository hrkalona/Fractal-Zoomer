package fractalzoomer.core.approximation.mip_la_zhuoran;

import fractalzoomer.core.Complex;

public class MipLALStep extends MipLAStep {
    protected double Bx;
    protected double By;
    protected int l;

    public MipLALStep() {

    }

    @Override
    public MipLALStep Composite(MipLAStep step) {
        MipLALStep result = new MipLALStep();

        Complex A = new Complex(Ax, Ay);
        Complex B = new Complex(Bx, By);
        result.ValidRadius = Math.min(ValidRadius, step.ValidRadius / A.chebyshevNorm());
        result.ValidRadiusC = Math.min(ValidRadiusC, Math.min(step.ValidRadiusC, step.ValidRadius / B.chebyshevNorm()));

        Complex stepA = new Complex(step.Ax, step.Ay);
        Complex stepB = step.getB();

        Complex resultA = A.times(stepA);
        Complex resultB = B.times(stepA).plus_mutable(stepB);

        result.Ax = resultA.re;
        result.Ay = resultA.im;
        result.Bx = resultB.re;
        result.By = resultB.im;
        result.l = getL() + step.getL();

        return result;
    }

    @Override
    public Complex getValue(Complex dz, Complex dc) {
        double zx = dz.re;
        double zy = dz.im;
        double cx = dc.re;
        double cy = dc.im;
        return new Complex(Ax * zx - Ay * zy + Bx * cx - By * cy, Ax * zy + Ay * zx + Bx * cy + By * cx);
    }

    @Override
    public Complex getValue(double zx, double zy, double cx, double cy) {
        return new Complex(Ax * zx - Ay * zy + Bx * cx - By * cy, Ax * zy + Ay * zx + Bx * cy + By * cx);
    }

    @Override
    public Complex getValue(Complex dz, double dc) {
        double zx = dz.re;
        double zy = dz.im;
        double cx = dc;
        return new Complex(Ax * zx - Ay * zy + Bx * cx, Ax * zy + Ay * zx + By * cx);
    }

    @Override
    public Complex getB() {
        return new Complex(Bx, By);
    }

    @Override
    public int getL() {
        return l;
    }
}
