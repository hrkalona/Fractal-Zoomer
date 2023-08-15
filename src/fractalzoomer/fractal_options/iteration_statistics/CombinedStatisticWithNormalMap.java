package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.*;
import fractalzoomer.core.bla.BLA;
import fractalzoomer.core.bla.BLADeep;
import fractalzoomer.core.interpolation.InterpolationMethod;
import fractalzoomer.core.la.LAstep;
import fractalzoomer.utils.ColorAlgorithm;
import org.apfloat.Apfloat;

public class CombinedStatisticWithNormalMap extends GenericStatistic {
    private NormalMap nm;
    private GenericStatistic gs;

    public CombinedStatisticWithNormalMap(GenericStatistic gs, NormalMap nm) {
        super(0, false, false, 0);
        this.gs = gs;
        this.nm = nm;
    }

    @Override
    public double getValue() {
        double v1 = nm.getValue();

        if(Math.abs(v1) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            return v1;
        }

        return gs.getValue() + v1;
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {
        gs.insert(z, zold, zold2, iterations, c, start, c0);
        nm.insert(z, zold, zold2, iterations, c, start, c0);
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, MantExpComplex zDeep, MantExpComplex zoldDeep, MantExpComplex cDeep) {
        gs.insert(z, zold, zold2, iterations, c, start, c0, zDeep, zoldDeep, cDeep);
        nm.insert(z, zold, zold2, iterations, c, start, c0, zDeep, zoldDeep, cDeep);
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, GenericComplex zDeep, GenericComplex zoldDeep, GenericComplex cDeep) {
        gs.insert(z, zold, zold2, iterations, c, start, c0, zDeep, zoldDeep, cDeep);
        nm.insert(z, zold, zold2, iterations, c, start, c0, zDeep, zoldDeep, cDeep);
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, BLA bla) {
        nm.insert(z, zold, zold2, iterations, c, start, c0, bla);
        gs.insert(z, zold, zold2, iterations, c, start, c0, bla);
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, BLADeep bla) {
        nm.insert(z, zold, zold2, iterations, c, start, c0, bla);
        gs.insert(z, zold, zold2, iterations, c, start, c0, bla);
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, LAstep las, MantExpComplex zDeep, MantExpComplex zoldDeep) {
        nm.insert(z, zold, zold2, iterations, c, start, c0, las, zDeep, zoldDeep);
        gs.insert(z, zold, zold2, iterations, c, start, c0, las, zDeep, zoldDeep);
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, LAstep las) {
        nm.insert(z, zold, zold2, iterations, c, start, c0, las);
        gs.insert(z, zold, zold2, iterations, c, start, c0, las);
    }

    @Override
    public void initialize(Complex pixel, Complex untransformedPixel) {
        nm.initialize(pixel, untransformedPixel);
        gs.initialize(pixel, untransformedPixel);
    }

    @Override
    public void initializeApproximationDerivatives(MantExpComplex dz, MantExpComplex ddz, int iterations) {
        nm.initializeApproximationDerivatives(dz, ddz, iterations);
    }

    @Override
    public boolean usesSecondDerivative() {
        return nm.usesSecondDerivative();
    }

    @Override
    public void setJuliterOptions(boolean isJuliter, int juliterIterations) {
        nm.setJuliterOptions(isJuliter, juliterIterations);
    }

    @Override
    public void setFunctionId(int function) {
        nm.setFunctionId(function);
    }

    @Override
    public boolean hasDEenabled() {
        return nm.hasDEenabled();
    }

    @Override
    public void setVariablePixelSize(MantExp pixelSize) {
        nm.setVariablePixelSize(pixelSize);
    }

    @Override
    public void setSize(Apfloat size, double height_ratio) {
        nm.setSize(size, height_ratio);
        gs.setSize(size, height_ratio);
    }

    @Override
    public void setZValue(Complex z) {
        nm.setZValue(z);
        gs.setZValue(z);
    }

    @Override
    public boolean isAdditive() {
        return nm.isAdditive() && gs.isAdditive();
    }

    @Override
    public int getType() {
        return gs.getType();
    }

    @Override
    public int getSamples() {
        return gs.getSamples();
    }

    @Override
    public boolean hasData() {
        return gs.hasData();
    }

    @Override
    public Complex getZ() {
        return gs.getZ();
    }

    @Override
    public Complex getZold() {
        return gs.getZold();
    }

    @Override
    public Complex getZold2() {
        return gs.getZold2();
    }

    @Override
    public Complex getStart() {
        return gs.getStart();
    }

    @Override
    public Complex getC0() {
        return gs.getC0();
    }

    @Override
    public Complex getC() {
        return gs.getC();
    }

    @Override
    public Complex getPixel() { return gs.getPixel(); }

    @Override
    public double getIntensity() {
        return gs.getIntensity();
    }

    @Override
    public int getIterations() { return gs.getIterations();}

    @Override
    public void setMode(int mode) {
        nm.setMode(mode);
        gs.setMode(mode);
    }

    @Override
    public double getValueNotEscaped() {
        double v1 = nm.getValueNotEscaped();

        if(Math.abs(v1) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            return v1;
        }

        return gs.getValueNotEscaped() + v1;
    }

    @Override
    public void setLogPower(double log_power) {
        nm.setLogPower(log_power);
        gs.setLogPower(log_power);
    }

    @Override
    public void setInterpolationMethod(InterpolationMethod method) {
        nm.setInterpolationMethod(method);
        gs.setInterpolationMethod(method);
    }

    @Override
    public void setEscapingSmoothingAlgorithm(int escaping_smoothing_algorithm) {
        nm.setEscapingSmoothingAlgorithm(escaping_smoothing_algorithm);
        gs.setEscapingSmoothingAlgorithm(escaping_smoothing_algorithm);
    }

    @Override
    public void setConvergingSmoothingAlgorithm(int converging_smoothing_algorithm) {
        nm.setConvergingSmoothingAlgorithm(converging_smoothing_algorithm);
        gs.setConvergingSmoothingAlgorithm(converging_smoothing_algorithm);
    }

    public NormalMap getNm() {
        return nm;
    }

    @Override
    public double getValueForColoring() {
        return nm.getValueForColoring();
    }

    @Override
    public double getExtraValue() {
        return nm.getExtraValue();
    }
}
