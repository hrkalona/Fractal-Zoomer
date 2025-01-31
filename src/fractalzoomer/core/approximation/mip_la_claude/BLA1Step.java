package fractalzoomer.core.approximation.mip_la_claude;

import fractalzoomer.core.Complex;

public class BLA1Step extends BLA {
    //This class Assumes that B is 1 + 0i
    public BLA1Step(double r2, Complex A) {
        super(r2, A);
    }

    @Override
    public Complex getValue(Complex DeltaSubN, Complex DeltaSub0) {

        //double zxn = Ax * zx - Ay * zy + Bx * cx - By * cy;
        //double zyn = Ax * zy + Ay * zx + Bx * cy + By * cx;
        double zx = DeltaSubN.getRe();
        double zy = DeltaSubN.getIm();
        double cx = DeltaSub0.getRe();
        double cy = DeltaSub0.getIm();
        return new Complex(Ax * zx - Ay * zy + cx, Ax * zy + Ay * zx + cy);
        //return  Complex.AtXpBtY(A, DeltaSubN, B, DeltaSub0);
    }

    @Override
    public Complex getValue(double dre, double dim, double d0re, double d0im) {
        return new Complex(Ax * dre - Ay * dim + d0re, Ax * dim + Ay * dre + d0im);
    }

    @Override
    public Complex getValue(Complex DeltaSubN, double cx) {
        double zx = DeltaSubN.getRe();
        double zy = DeltaSubN.getIm();
        return new Complex(Ax * zx - Ay * zy + cx, Ax * zy + Ay * zx);
    }

    @Override
    public double hypotB() {
        return 1;
    }

    @Override
    public int getL() {
        return 1;
    }

    @Override
    public double getBx() { return 1.0;}

    @Override
    public double getBy() { return 0.0;}

}
