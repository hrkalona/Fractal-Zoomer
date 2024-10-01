package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLALStep extends BLA {

    public double Bx;
    public double By;
    public int l;

    protected BLALStep(double r2, Complex A, Complex B, int l) {
        super(r2, A);
        this.Bx = B.getRe();
        this.By = B.getIm();
        this.l = l;
    }

    @Override
    public Complex getValue(Complex DeltaSubN, Complex DeltaSub0) {

        //double zxn = Ax * zx - Ay * zy + Bx * cx - By * cy;
        //double zyn = Ax * zy + Ay * zx + Bx * cy + By * cx;
        double zx = DeltaSubN.getRe();
        double zy = DeltaSubN.getIm();
        double cx = DeltaSub0.getRe();
        double cy = DeltaSub0.getIm();
        return new Complex(Ax * zx - Ay * zy + Bx * cx - By * cy, Ax * zy + Ay * zx + Bx * cy + By * cx);
        //return  Complex.AtXpBtY(A, DeltaSubN, B, DeltaSub0);
    }

    @Override
    public Complex getValue(double dre, double dim, double d0re, double d0im) {
        return new Complex(Ax * dre - Ay * dim + Bx * d0re - By * d0im, Ax * dim + Ay * dre + Bx * d0im + By * d0re);
    }

    @Override
    public Complex getValue(Complex DeltaSubN, double cx) {
        double zx = DeltaSubN.getRe();
        double zy = DeltaSubN.getIm();
        return new Complex(Ax * zx - Ay * zy + Bx * cx, Ax * zy + Ay * zx + By * cx);
    }

    @Override
    public double hypotB() {
        return Math.hypot(Bx, By);
    }

    @Override
    public double getBx() { return Bx;}

    @Override
    public double getBy() { return By;}

    @Override
    public int getL() {
        return l;
    }
}
