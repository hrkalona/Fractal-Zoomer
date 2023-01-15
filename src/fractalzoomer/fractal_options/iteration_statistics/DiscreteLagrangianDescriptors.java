package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.main.MainWindow;
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;

public class DiscreteLagrangianDescriptors extends GenericStatistic {
    private double sum;
    private double sum2;
    private double lastx;
    private double lasty;
    private double lastz;
    private double power;
    private double log_bailout_squared;
    private double log_convergent_bailout;
    private Complex root;
    private int normtType;
    private double langNorm;
    private double langNormReciprocal;

    public DiscreteLagrangianDescriptors(double statistic_intensity, double power, double log_bailout_squared, boolean useSmoothing, boolean useAverage, boolean rootFindingMode, double log_convergent_bailout, int normtType, double langNorm) {
        super(statistic_intensity, useSmoothing, useAverage);
        sum = 0;
        sum2 = 0;
        lastx = 0;
        lasty = 0;
        lastz = 0;
        this.power = power;
        this.log_convergent_bailout = log_convergent_bailout;
        this.log_bailout_squared = log_bailout_squared;

        if(rootFindingMode) {
            mode = NORMAL_CONVERGE;
        }
        else {
            mode = NORMAL_ESCAPE;
        }

        root = new Complex(1, 0);
        this.normtType = normtType;
        this.langNorm = langNorm;
        langNormReciprocal = 1 / langNorm;
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {
        super.insert(z, zold, zold2, iterations, c, start, c0);

        double d = 0;

        switch (normtType) {
            case 0:
                d = z.norm_squared();
                break;
            case 1:
                d = z.norm();
                break;
            case 2: //Rhombus
                d = z.getAbsRe() + z.getAbsIm();
                break;
            case 3: //Square
                d = Math.max(z.getAbsRe(), z.getAbsIm());
                break;
            case 4:
                d = z.nnorm(langNorm, langNormReciprocal);
                break;
        }

        double xx = 0;
        double yy = 0;
        double zz = 1;

        if(!Double.isInfinite(d) && !Double.isNaN(d)) {
            double dd = 1 / (d + 1);

            xx = 2 * z.getRe() * dd;
            yy = 2 * z.getIm() * dd;
            zz = (d - 1) * dd; //Riemann sphere coordinates
        }

        if (samples > 0) {
            sum2 = sum;
            sum += Math.pow(Math.abs(xx - lastx), power) + Math.pow(Math.abs(yy - lasty), power) + Math.pow(Math.abs(zz - lastz), power);
        }

        samples++;
        lastx = xx;
        lasty = yy;
        lastz = zz;
    }

    @Override
    public void initialize(Complex pixel, Complex untransformedPixel) {
        super.initialize(pixel, untransformedPixel);
        samples = 0;
        sum = 0;
        sum2 = 0;
        lastx = 0;
        lasty = 0;
        lastz = 0;
    }

    @Override
    public double getValue() {

        if (samples < 2) {
            return 0;
        }

        if(useAverage) {
            sum = sum  / (samples - 1);
            sum2 = samples < 3 ? 0 : sum2 / (samples - 2);
        }

        if(!useSmoothing) {
            return sum * statistic_intensity;
        }

        double smoothing;

        if(mode == NORMAL_ESCAPE) {
            smoothing = OutColorAlgorithm.fractionalPartEscaping(z_val, zold_val, log_bailout_squared);
        }
        else if (mode == NORMAL_CONVERGE){
            smoothing = OutColorAlgorithm.fractionalPartConverging(z_val, zold_val, zold2_val, log_convergent_bailout);
        }
        else {
            smoothing = OutColorAlgorithm.fractionalPartMagnetConverging(z_val, zold_val, root, log_convergent_bailout);
        }

        return (sum + (sum2 - sum) * smoothing) * statistic_intensity;

    }

    @Override
    public double getValueNotEscaped() {
        return 20 * super.getValueNotEscaped();
    }

    @Override
    public int getType() {
        return MainWindow.ESCAPING_AND_CONVERGING;
    }

}
