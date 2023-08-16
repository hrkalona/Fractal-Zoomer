package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public abstract class BLA {
    public double r2;
    public double Ax;
    public double Ay;

    protected BLA(double r2, Complex A) {
        this.r2 = r2;
        this.Ax = A.getRe();
        this.Ay = A.getIm();
        //this.A = A;
        //this.B = B;
    }

    public abstract Complex getValue(Complex DeltaSubN, Complex DeltaSub0);
    public abstract Complex getValue(double dre, double dim, double d0re, double d0im);
    public abstract Complex getValue(Complex DeltaSubN, double DeltaSub0);

    public Complex getValue(Complex DeltaSubN) {
        double zx = DeltaSubN.getRe();
        double zy = DeltaSubN.getIm();
        return new Complex(Ax * zx - Ay * zy, Ax * zy + Ay * zx);
    }

    public double hypotA() {
        return Math.hypot(Ax, Ay);
    }

    public abstract double hypotB();

    public int getL() {
        return 0;
    }

    public double getAx() {
        return Ax;
    }

    public double getAy() {
        return Ay;
    }

    public abstract double getBx();

    public abstract double getBy();

    // A = y.A * x.A
    public static Complex getNewA(BLA x, BLA y) {
        return new Complex(y.Ax * x.Ax - y.Ay * x.Ay, y.Ax * x.Ay + y.Ay * x.Ax);
    }

    // B = y.A * x.B + y.B
    public static Complex getNewB(BLA x, BLA y) {
        double xBx = x.getBx();
        double xBy = x.getBy();
        return new Complex(y.Ax * xBx - y.Ay * xBy + y.getBx(), y.Ax * xBy + y.Ay * xBx + y.getBy());
    }
}
