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
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.bla.BLA;
import fractalzoomer.core.bla.BLADeep;
import fractalzoomer.core.interpolation.InterpolationMethod;
import fractalzoomer.core.la.LAstep;
import org.apfloat.Apfloat;

import java.util.Arrays;

/**
 *
 * @author hrkalona2
 */
public abstract class GenericStatistic {
    public static final int NORMAL_ESCAPE = 0;
    public static final int NORMAL_CONVERGE = 1;
    public static final int MAGNET_ROOT = 2;

    protected StatisticSample[] sampleItems;
    protected int sampleItem;
    protected int lastXItems;
    protected boolean keepLastXItems;
    protected int samples;
    protected double statistic_intensity;
    protected Complex z_val;
    protected Complex zold_val;
    protected Complex zold2_val;
    protected Complex start_val;
    protected Complex c_val;
    protected Complex c0_val;
    protected Complex pixel_val;

    protected MantExpComplex z_val_deep;
    protected MantExpComplex zold_val_deep;
    protected MantExpComplex c_val_deep;
    protected boolean useSmoothing;
    protected boolean useAverage;
    protected int mode;
    protected int iterations;
    protected Apfloat size;
    protected MantExp mSize;
    protected double log_power;
    protected boolean usePower;
    protected InterpolationMethod method;
    private boolean hasData;
    protected int escaping_smoothing_algorithm;
    protected int converging_smoothing_algorithm;
    
    protected GenericStatistic(double statistic_intensity, boolean useSmoothing, boolean useAverage, int lastXItems) {
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
        hasData = false;
        this.lastXItems = lastXItems;

        if(lastXItems > 0) {
            keepLastXItems = true;
            sampleItems = new StatisticSample[lastXItems];
            sampleItem = 0;
        }
    }
    
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {
        z_val.assign(z);
        zold_val.assign(zold);
        zold2_val.assign(zold2);
        start_val.assign(start);
        c_val.assign(c);
        c0_val.assign(c0);
        this.iterations = iterations;
        hasData = true;

        if(keepLastXItems) {
            sampleItems[sampleItem % sampleItems.length] = new StatisticSample(z, zold, zold2, iterations, c, start, c0);
            sampleItem++;
        }
    }

    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, MantExpComplex zDeep, MantExpComplex zoldDeep, MantExpComplex cDeep) {
        z_val.assign(z);
        zold_val.assign(zold);
        zold2_val.assign(zold2);
        start_val.assign(start);
        c_val.assign(c);
        c0_val.assign(c0);
        this.iterations = iterations;
        z_val_deep = zDeep;
        zold_val_deep = zoldDeep;
        c_val_deep = cDeep;
        hasData = true;

        if(keepLastXItems) {
            sampleItems[sampleItem % sampleItems.length] = new StatisticSample(z, zold, zold2, iterations, c, start, c0);
            sampleItem++;
        }
    }

    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, GenericComplex zDeep, GenericComplex zoldDeep, GenericComplex cDeep) {
        z_val.assign(z);
        zold_val.assign(zold);
        zold2_val.assign(zold2);
        start_val.assign(start);
        c_val.assign(c);
        c0_val.assign(c0);
        this.iterations = iterations;

        if(zDeep instanceof MantExpComplex) {
            z_val_deep = (MantExpComplex) zDeep;
        }
        else {
            z_val_deep = MantExpComplex.create((Complex)zDeep);
        }

        if(zoldDeep instanceof MantExpComplex) {
            zold_val_deep = (MantExpComplex) zoldDeep;
        }
        else {
            zold_val_deep = MantExpComplex.create((Complex)zoldDeep);
        }

        if(cDeep != null) {
            if(cDeep instanceof MantExpComplex) {
                c_val_deep = (MantExpComplex) cDeep;
            }
            else {
                c_val_deep = MantExpComplex.create((Complex) cDeep);
            }
        }
        hasData = true;

        if(keepLastXItems) {
            sampleItems[sampleItem % sampleItems.length] = new StatisticSample(z, zold, zold2, iterations, c, start, c0);
            sampleItem++;
        }
    }

    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, BLA bla) {
        insert(z, zold, zold2, iterations, c, start, c0);
    }

    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, BLADeep bla) {
        insert(z, zold, zold2, iterations, c, start, c0);
    }

    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, LAstep las, MantExpComplex zDeep, MantExpComplex zoldDeep) {
        insert(z, zold, zold2, iterations, c, start, c0);
    }

    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, LAstep las) {
        insert(z, zold, zold2, iterations, c, start, c0);
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
        zold_val_deep = null;
        z_val_deep = null;
        c_val_deep = null;
        hasData = false;

        if(keepLastXItems) {
            Arrays.fill(sampleItems, 0, sampleItems.length, null);
            sampleItem = 0;
        }
    }

    protected void addSample(StatisticSample sam, double[] data) {

    }

    protected double[][] calculateLastX() {
        int length = sampleItems.length;

        double[] common_data = new double[2];
        double[] data1 = new double[2];
        double[] data2 = new double[2];

        //Common part

        int start = (sampleItem + 1) % sampleItems.length;
        int lenm1 = length - 1;
        int i = start;
        for(int count = 1; count < lenm1; i++, count++) {
            StatisticSample sam = sampleItems[i % sampleItems.length];

            if(sam != null) {
                addSample(sam, common_data);
            }
        }

        //sum2
        data2[0] = common_data[0];
        data2[1] = common_data[1];

        StatisticSample sam = sampleItems[sampleItem % sampleItems.length];

        if(sam != null) {
            addSample(sam, data2);
        }

        //sum
        data1[0] = common_data[0];
        data1[1] = common_data[1];

        sam = sampleItems[i % sampleItems.length];

        if(sam != null) {
            addSample(sam, data1);
        }


        return new double[][] {data1, data2};

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

    public boolean hasData() {
        return hasData;
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

    public void setSize(Apfloat size, double height_ratio) {
        this.size = size;
        this.mSize = new MantExp(size);
        mSize.Normalize();
    }

    public void setZValue(Complex z) {
        z_val = z;
    }

    public void setLogPower(double log_power) {
        this.log_power = log_power;
        usePower = true;
    }

    public boolean isAdditive() {
        return true;
    }

    public void setInterpolationMethod(InterpolationMethod method) {
        this.method = method;
    }

    public void setEscapingSmoothingAlgorithm(int escaping_smoothing_algorithm) {
        this.escaping_smoothing_algorithm = escaping_smoothing_algorithm;
    }

    public void setConvergingSmoothingAlgorithm(int converging_smoothing_algorithm) {
        this.converging_smoothing_algorithm = converging_smoothing_algorithm;
    }

    public void initializeApproximationDerivatives(MantExpComplex dz, MantExpComplex ddz, int iterations) {

    }

    public boolean usesSecondDerivative() {
        return false;
    }

    public void setJuliterOptions(boolean isJuliter, int juliterIterations) {

    }

    public void setFunctionId(int function) {

    }

    public boolean hasDEenabled() {
        return false;
    }

    public void setVariablePixelSize(MantExp pixelSize) {

    }

    public boolean hasNormalMap() {
        return false;
    }
    
}