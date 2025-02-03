package fractalzoomer.core.approximation.mip_la_claude;

import fractalzoomer.core.Complex;

public class BLA {
    public double r2;

    protected BLA() {
        r2 = 0;
    }

    protected BLA(double r2) {
        this.r2 = r2;
    }

    public Complex getValue(Complex DeltaSubN, Complex DeltaSub0) { return null; }
    public Complex getValue(double dre, double dim, double d0re, double d0im) { return null; }
    public Complex getValue(Complex DeltaSubN, double DeltaSub0) { return null; }
    public Complex getValue(Complex DeltaSubN) { return null; }

    public double hypotA() { return 0; }

    public double hypotB() { return 0; }

    public int getL() {
        return 0;
    }

    public double getAx() {
        return 0;
    }

    public double getAy() {
        return 0;
    }

    public double getBx() { return  0; }

    public double getBy() { return  0; }

    // A = y.A * x.A
    public static Complex getNewA(BLA x, BLA y) {
        double yAx = y.getAx();
        double yAy = y.getAy();
        double xAx = x.getAx();
        double xAy = x.getAy();
        return new Complex(yAx * xAx - yAy * xAy, yAx * xAy + yAy * xAx);
    }

    // B = y.A * x.B + y.B
    public static Complex getNewB(BLA x, BLA y) {
        double xBx = x.getBx();
        double xBy = x.getBy();
        double yAx = y.getAx();
        double yAy = y.getAy();
        return new Complex(yAx * xBx - yAy * xBy + y.getBx(), yAx * xBy + yAy * xBx + y.getBy());
    }
}
