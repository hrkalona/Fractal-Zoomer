package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.Fractal;
import fractalzoomer.functions.FractalWithoutConstant;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.MainWindow;
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;

public class Equicontinuity extends GenericStatistic {
    private Fractal f;
    private double sum;
    private double sum2;
    private Complex[] complex;
    private boolean julia;
    private boolean juliter;
    private Complex root;
    private double denominatorFactor;
    private boolean invertFactor;
    private double delta;
    private boolean isFractalWithoutConstant;

    public Equicontinuity(double statistic_intensity, boolean useSmoothing, boolean useAverage, boolean rootFindingMode, double denominatorFactor, boolean invertFactor, double delta) {
        super(statistic_intensity, useSmoothing, useAverage, 0);
        sum = 0;
        sum2 = 0;

        if(rootFindingMode) {
            mode = NORMAL_CONVERGE;
        }
        else {
            mode = NORMAL_ESCAPE;
        }

        root = new Complex(1, 0);

        this.denominatorFactor = denominatorFactor;
        this.invertFactor = invertFactor;
        this.delta = delta;

        juliter = false;
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {
        super.insert(z, zold, zold2, iterations, c, start, c0);

        f.setIteration(iterations);

        f.updateValues(complex);

        f.updateZoldAndZold2(complex[0]);

        if(!isFractalWithoutConstant) {
            complex[1] = f.getPlaneInfluence().getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel_val);
        }
        complex[0] = f.getPreFilter().getValue(complex[0], iterations, isFractalWithoutConstant ? pixel_val : complex[1], start, c0, pixel_val);
        f.function(complex);
        complex[0] = f.getPostFilter().getValue(complex[0], iterations, isFractalWithoutConstant ? pixel_val : complex[1], start, c0, pixel_val);

        double res = complex[0].distance_squared(z);

        if(!Double.isNaN(res) && !Double.isInfinite(res)) {
            sum2 = sum;
            sum += res;
            samples++;
        }

    }

    @Override
    public void initialize(Complex pixel, Complex untransformedPixel) {
        super.initialize(pixel, untransformedPixel);
        samples = 0;
        sum = 0;

        isFractalWithoutConstant = f instanceof FractalWithoutConstant || f instanceof RootFindingMethods;

        f.setIteration(0);
        Complex deltaPixel = untransformedPixel.plus(delta);
        Complex transformed = julia ? f.getTransformedPixelJulia(deltaPixel) : f.getTransformedPixel(deltaPixel);
        complex = julia ? (juliter ? f.initialize(transformed) : f.initializeSeed(transformed)) : f.initialize(transformed);
    }

    @Override
    protected double getValue() {

        return getValueInternal() * statistic_intensity;

    }

    private double getValueInternal() {
        if(samples < 1) {
            return 0;
        }

        if(useAverage) {
            sum = sum / samples;
            sum2 = samples < 2 ? 0 : sum2 / (samples - 1);
        }

        if(!useSmoothing) {
            double temp = sum * denominatorFactor;
            return invertFactor ? (1 / (1 + temp)) : (1 - 1 / (1 + temp));
        }

        double smoothing;

        if(mode == NORMAL_ESCAPE) {
            if(escaping_smoothing_algorithm == 0 && !usePower) {
                smoothing = OutColorAlgorithm.fractionalPartEscaping1(z_val, zold_val, log_bailout, normSmoothingImpl);
            }
            else if(escaping_smoothing_algorithm == 2 && !usePower) {
                smoothing = OutColorAlgorithm.fractionalPartEscaping3(z_val, zold_val, bailout, normSmoothingImpl);
            }
            else {
                smoothing = usePower ? OutColorAlgorithm.fractionalPartEscapingWithPower(z_val, log_bailout, log_power, normSmoothingImpl) : OutColorAlgorithm.fractionalPartEscaping2(z_val, zold_val, log_bailout, normSmoothingImpl);
            }
        }
        else if (mode == NORMAL_CONVERGE){
            if(converging_smoothing_algorithm == 0) {
                smoothing = OutColorAlgorithm.fractionalPartConverging1(z_val, zold_val, zold2_val, log_convergent_bailout, cNormSmoothingImpl);
            }
            else {
                smoothing = OutColorAlgorithm.fractionalPartConverging2(z_val, zold_val, zold2_val, log_convergent_bailout, cNormSmoothingImpl);
            }
        }
        else {
            if(converging_smoothing_algorithm == 0) {
                smoothing = OutColorAlgorithm.fractionalPartMagnetConverging1(z_val, zold_val, root, log_convergent_bailout, cNormSmoothingImpl);
            }
            else {
                smoothing = OutColorAlgorithm.fractionalPartMagnetConverging2(z_val, zold_val, root, log_convergent_bailout, cNormSmoothingImpl);
            }
        }

        double temp =  method.interpolate(sum, sum2, smoothing) * denominatorFactor;

        return  invertFactor ? (1 / (1 + temp)) : (1 - 1 / (1 + temp));
    }

    @Override
    public double getValueForColoring() {
        return getValueInternal();
    }

    @Override
    public double getValueNotEscapedForColoring() {

        boolean oldSmoothingValue = useSmoothing;
        useSmoothing = false;
        double val = getValueInternal();
        useSmoothing = oldSmoothingValue;
        return val;

    }

    @Override
    public int getType() {
        return MainWindow.ESCAPING_AND_CONVERGING;
    }

    public void setFractal(Fractal f) {
        this.f = f;
    }

    public void setJulia(boolean julia) {
        this.julia = julia;
    }

    public void setJuliIter(boolean juliter) {
        this.juliter = juliter;
    }
}
