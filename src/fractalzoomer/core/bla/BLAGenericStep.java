package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public abstract class BLAGenericStep extends BLA {

    public double Bx, By;

    public BLAGenericStep(double r2, Complex A, Complex B) {
        super(r2, A);
        this.Bx = B.getRe();
        this.By = B.getIm();
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

    public static BLAGenericStep getGenericStep(double r2, Complex A, Complex B, int l) {

        if(!BLAS.MEMORY_PACKING) {
            return new BLALStep(r2, A, B, l);
        }

        switch (l) {
            case 2:
                return new BLA2Step(r2, A, B);
            case 4:
                return new BLA4Step(r2, A, B);
            case 8:
                return new BLA8Step(r2, A, B);
            case 16:
                return new BLA16Step(r2, A, B);
            case 32:
                return new BLA32Step(r2, A, B);
            case 64:
                return new BLA64Step(r2, A, B);
            case 128:
                return new BLA128Step(r2, A, B);
            case 256:
                return new BLA256Step(r2, A, B);
            case 512:
                return new BLA512Step(r2, A, B);
            case 1024:
                return new BLA1024Step(r2, A, B);
            case 2048:
                return new BLA2048Step(r2, A, B);
            case 4096:
                return new BLA4096Step(r2, A, B);
            case 8192:
                return new BLA8192Step(r2, A, B);
            case 16384:
                return new BLA16384Step(r2, A, B);
            case 32768:
                return new BLA32768Step(r2, A, B);
            case 65536:
                return new BLA65536Step(r2, A, B);
            case 131072:
                return new BLA131072Step(r2, A, B);
            case 262144:
                return new BLA262144Step(r2, A, B);
            case 524288:
                return new BLA524288Step(r2, A, B);
            case 1048576:
                return new BLA1048576Step(r2, A, B);
            case 2097152:
                return new BLA2097152Step(r2, A, B);
            case 4194304:
                return new BLA4194304Step(r2, A, B);
            case 8388608:
                return new BLA8388608Step(r2, A, B);
            case 16777216:
                return new BLA16777216Step(r2, A, B);
            case 33554432:
                return new BLA33554432Step(r2, A, B);
            case 67108864:
                return new BLA67108864Step(r2, A, B);
            case 134217728:
                return new BLA134217728Step(r2, A, B);
            case 268435456:
                return new BLA268435456Step(r2, A, B);
            case 536870912:
                return new BLA536870912Step(r2, A, B);
            case 1073741824:
                return new BLA1073741824Step(r2, A, B);
            default:
                return new BLALStep(r2, A, B, l);
        }
    }
}
