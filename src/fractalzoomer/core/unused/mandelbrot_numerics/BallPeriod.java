package fractalzoomer.core.unused.mandelbrot_numerics;

import fractalzoomer.core.*;

public class BallPeriod {
    GenericComplex c;
    GenericComplex z;
    GenericComplex dz;
    Object r, rdz, rz, rr, Ei;
    int p;

    public BallPeriod(GenericComplex center, Object radius) {
        if(center instanceof Complex) {
            c = center;
            z = new Complex();
            dz = new Complex();
            r = radius;
            rdz = 0.0;
            rz = 0.0;
            rr = 0.0;
            Ei = 0.0;
        }
        else if(center instanceof BigIntNumComplex) {
            c = center;
            z = new BigIntNumComplex();
            dz = new BigIntNumComplex();
            r = radius;
            rdz = new BigIntNum();
            rz = new BigIntNum();
            rr = new BigIntNum();
            Ei = new BigIntNum();
        }
        else if(center instanceof DDComplex) {
            c = center;
            z = new DDComplex();
            dz = new DDComplex();
            r = radius;
            rdz = new DoubleDouble();
            rz = new DoubleDouble();
            rr = new DoubleDouble();
            Ei = new DoubleDouble();
        }
        p = 0;
        step();
    }

    private boolean step() {
        boolean check = false;
        if(z instanceof Complex) {
            double Ei = (double)this.Ei;
            double rdz = (double)this.rdz;
            double rz = (double)this.rz;
            double r = (double)this.r;

            this.Ei = rdz * rdz + (2 * rz + r * (2 * rdz + r * Ei)) * Ei;
            dz = z.times(dz).times2_mutable().plus_mutable(1);
            z = z.square().plus_mutable(c);
            this.rdz = dz.Norm();
            rz = (double)z.Norm();
            double rr = r * (rdz + r * Ei);
            check = rz - rr <= 2;

            this.rz = rz;
            this.rr = rr;
        }
        else if(z instanceof BigIntNumComplex) {
            BigIntNum Ei = (BigIntNum)this.Ei;
            BigIntNum rdz = (BigIntNum)this.rdz;
            BigIntNum rz = (BigIntNum)this.rz;
            BigIntNum r = (BigIntNum)this.r;

            this.Ei = rdz.square().add(rz.mult2().add(rdz.mult2().add(r.mult(Ei)).mult(r)).mult(Ei));
            dz = z.times(dz).times2_mutable().plus_mutable(1);
            z = z.square().plus_mutable(c);
            this.rdz = dz.Norm();
            rz = (BigIntNum) z.Norm();
            BigIntNum rr = r.mult(rdz.add(r.mult(Ei)));
            check = rz.sub(rr).compare(new BigIntNum(2)) <= 0;

            this.rz = rz;
            this.rr = rr;
        }
        else if(z instanceof DDComplex) {
            DoubleDouble Ei = (DoubleDouble)this.Ei;
            DoubleDouble rdz = (DoubleDouble)this.rdz;
            DoubleDouble rz = (DoubleDouble)this.rz;
            DoubleDouble r = (DoubleDouble)this.r;

            this.Ei = rdz.sqr().add(rz.multiply2().add(rdz.multiply2().add(r.multiply(Ei)).multiply(r)).multiply(Ei));
            dz = z.times(dz).times2_mutable().plus_mutable(1);
            z = z.square().plus_mutable(c);
            this.rdz = dz.Norm();
            rz = (DoubleDouble) z.Norm();
            DoubleDouble rr = r.multiply(rdz.add(r.multiply(Ei)));
            check = rz.subtract(rr).compareTo(new DoubleDouble(2)) <= 0;

            this.rz = rz;
            this.rr = rr;
        }

        p++;
        return check;
    }

    private boolean hasPeriod() {
        if(rz instanceof Double) {
            return (double)rz <= (double)rr;
        } else if(rz instanceof BigIntNum) {
            return ((BigIntNum)rz).compare((BigIntNum)rr) <= 0;
        }
        else if(rz instanceof DoubleDouble) {
            return ((DoubleDouble)rz).compareTo(rr) <= 0;
        }

        return false;
    }

    private boolean willConverge() {
        if(rz instanceof Double) {
            double Ei = (double)this.Ei;
            double rdz = (double)this.rdz;
            double rz = (double)this.rz;
            double r = (double)this.r;

            return rz < r * (rdz - r * Ei);
        }
        else if(rz instanceof BigIntNum) {
            BigIntNum Ei = (BigIntNum)this.Ei;
            BigIntNum rdz = (BigIntNum)this.rdz;
            BigIntNum rz = (BigIntNum)this.rz;
            BigIntNum r = (BigIntNum)this.r;
            return rz.compare(r.mult(rdz.sub(r.mult(Ei)))) < 0;
        }
        else if(rz instanceof DoubleDouble) {
            DoubleDouble Ei = (DoubleDouble)this.Ei;
            DoubleDouble rdz = (DoubleDouble)this.rdz;
            DoubleDouble rz = (DoubleDouble)this.rz;
            DoubleDouble r = (DoubleDouble)this.r;
            return rz.compareTo(r.multiply(rdz.subtract(r.multiply(Ei)))) < 0;
        }
        return false;
    }

    public static int find(GenericComplex center, Object radius, int maxperiod) {
        BallPeriod ball = new BallPeriod(center, radius);

        int period = 0;
        for (int i = 0; i < maxperiod; ++i) {
            if (ball.hasPeriod()) {
                period = ball.p;
                if (! ball.willConverge())
                {
                    period = -period;
                }
                break;
            }
            if (! ball.step()) {
                break;
            }
        }
        return period;
    }
}
