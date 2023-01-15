package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;

public class OrbitSample {
    public Complex z_val;

    public int iterations;
    public OrbitSample(Complex z, int iterations) {
        z_val = new Complex(z);
        this.iterations = iterations;
    }
}
