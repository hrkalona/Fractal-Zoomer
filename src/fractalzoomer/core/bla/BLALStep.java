package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLALStep extends BLA {
    public double Bx, By;
    public int l;

    public BLALStep(double r2, Complex A, Complex B, int l) {
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
    public double hypotB() {
        return Math.hypot(Bx, By);
    }

    @Override
    public int getL() {
        return l;
    }

    @Override
    public double getBx() { return Bx;}

    @Override
    public double getBy() { return By;}

}
