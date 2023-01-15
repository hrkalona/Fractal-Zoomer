package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;

public class StatisticSample {
    public Complex z_val;
    public Complex zold_val;
    public Complex zold2_val;
    public Complex start_val;
    public Complex c_val;
    public Complex c0_val;

    public int iterations;
    public StatisticSample(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {
        z_val = new Complex(z);
        zold_val = new Complex(zold);
        zold2_val = new Complex(zold2);
        start_val = new Complex(start);
        c_val = new Complex(c);
        c0_val = new Complex(c0);
        this.iterations = iterations;
    }
}
