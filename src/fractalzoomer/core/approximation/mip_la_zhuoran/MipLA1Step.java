package fractalzoomer.core.approximation.mip_la_zhuoran;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.Fractal;

public class MipLA1Step extends MipLAStep {
    public MipLA1Step(Complex z, Fractal f) {
        super(z, f);
    }

    @Override
    public MipLAStep Composite(MipLAStep step) {
        MipLALStep result = new MipLALStep();

        Complex A = new Complex(Ax, Ay);
        result.ValidRadius = Math.min(ValidRadius, step.ValidRadius / A.chebyshevNorm());
        result.ValidRadiusC = Math.min(ValidRadiusC, Math.min(step.ValidRadiusC, step.ValidRadius));

        Complex stepA = new Complex(step.Ax, step.Ay);
        Complex stepB = step.getB();

        Complex resultA = A.times(stepA);
        Complex resultB = stepA.plus(stepB);

        result.Ax = resultA.getRe();
        result.Ay = resultA.getIm();
        result.Bx = resultB.getRe();
        result.By = resultB.getIm();

        return result;
    }

    @Override
    public Complex getB() {
        return new Complex(1, 0);
    }

    @Override
    public Complex getValue(Complex dz, Complex dc) {
        double zx = dz.getRe();
        double zy = dz.getIm();
        double cx = dc.getRe();
        double cy = dc.getIm();
        return new Complex(Ax * zx - Ay * zy + cx, Ax * zy + Ay * zx + cy);
    }

    @Override
    public Complex getValue(double zx, double zy, double cx, double cy) {
        return new Complex(Ax * zx - Ay * zy + cx, Ax * zy + Ay * zx + cy);
    }

    @Override
    public Complex getValue(Complex dz, double dc) {
        double zx = dz.getRe();
        double zy = dz.getIm();
        double cx = dc;
        return new Complex(Ax * zx - Ay * zy + cx, Ax * zy + Ay * zx);
    }
}
