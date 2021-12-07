/*
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public abstract class GenericStatistic {
    public static final int NORMAL_ESCAPE = 0;
    public static final int NORMAL_CONVERGE = 1;
    public static final int MAGNET_ROOT = 2;

    protected int samples;
    protected double statistic_intensity;
    protected Complex z_val;
    protected Complex zold_val;
    protected Complex zold2_val;
    protected Complex start_val;
    protected Complex c_val;
    protected Complex c0_val;
    protected Complex pixel_val;
    protected boolean useSmoothing;
    protected boolean useAverage;
    protected int mode;
    protected int iterations;
    
    public GenericStatistic(double statistic_intensity, boolean useSmoothing, boolean useAverage) {
        this.statistic_intensity = statistic_intensity;
        z_val = new Complex();
        zold_val = new Complex();
        zold2_val = new Complex();
        start_val = new Complex();
        c_val = new Complex();
        c0_val = new Complex();
        pixel_val = new Complex();
        samples = 0;
        this.useSmoothing = useSmoothing;
        this.useAverage = useAverage;
        mode = NORMAL_ESCAPE;
    }
    
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {
        z_val.assign(z);
        zold_val.assign(zold);
        zold2_val.assign(zold2);
        start_val.assign(start);
        c_val.assign(c);
        c0_val.assign(c0);
        this.iterations = iterations;
    }

    public void initialize(Complex pixel, Complex untransformedPixel) {
        z_val.reset();
        zold_val.reset();
        zold2_val.reset();
        start_val.reset();
        c_val.reset();
        c0_val.reset();
        pixel_val.assign(pixel);
        iterations = 0;
    }

    public abstract double getValue();

    public double getValueForColoring() {
        return getValue();
    }

    public double getValueNotEscapedForColoring() {
        return getValueNotEscaped();
    }

    public abstract int getType();

    public double getValueNotEscaped() {
        boolean oldSmoothingValue = useSmoothing;
        useSmoothing = false;
        double val = getValue();
        useSmoothing = oldSmoothingValue;
        return val;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Complex getZ() {
        return z_val;
    }

    public Complex getZold() {
        return zold_val;
    }

    public Complex getZold2() {
        return zold2_val;
    }

    public int getSamples() {
        return samples;
    }

    public Complex getStart() {
        return start_val;
    }

    public Complex getC0() {
        return c0_val;
    }

    public Complex getC() {
        return c_val;
    }

    public Complex getPixel() { return pixel_val; }

    public double getIntensity() {
        return statistic_intensity;
    }

    public double getExtraValue() {
        return 0;
    }

    public int getIterations() { return iterations;}
    
}