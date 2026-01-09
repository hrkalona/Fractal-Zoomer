package fractalzoomer.fractal_options.filter;

import fractalzoomer.core.Complex;

public class MobiusFunctionFilter extends FunctionFilter {
    private double a;
    private double b;

    public MobiusFunctionFilter(double a, double b) {
        super();
        this.a = a;
        this.b = b;
    }

    @Override
    public Complex getValue(Complex z, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        double re = z.getRe();
        double im = z.getIm();

        if(a != 0) {
            double dist = Math.abs(re) - a;
            if (re > a) {
                re = dist - a;
                im = -im;
            } else if (re < -a) {
                re = a - dist;
                im = -im;
            }
        }

        if(b != 0) {
            double dist = Math.abs(im) - b;
            if (im > b) {
                re = -re;
                im = dist - b;
            } else if (im < -b) {
                re = -re;
                im = b - dist;
            }
        }

        return new Complex(re, im);
    }
}
