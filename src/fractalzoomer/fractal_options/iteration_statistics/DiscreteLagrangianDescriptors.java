package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.*;
import fractalzoomer.main.MainWindow;
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;

public class DiscreteLagrangianDescriptors extends GenericStatistic {
    private double sum;
    private double sum2;
    private double lastx;
    private double lasty;
    private double lastz;
    private double power;
    private Complex root;
    private Norm normImpl;
    private int normtType;

    public DiscreteLagrangianDescriptors(double statistic_intensity, double power, boolean useSmoothing, boolean useAverage, boolean rootFindingMode, int normtType, double langNorm, int lastXItems, double a, double b) {
        super(statistic_intensity, useSmoothing, useAverage, lastXItems);
        sum = 0;
        sum2 = 0;
        lastx = 0;
        lasty = 0;
        lastz = 0;
        this.power = power;
        this.normtType = normtType;

        if(rootFindingMode) {
            mode = NORMAL_CONVERGE;
        }
        else {
            mode = NORMAL_ESCAPE;
        }

        root = new Complex(1, 0);

        switch (normtType) {
            case 0:
            case 1:
                normImpl = new Norm2();
                break;
            case 2: //Rhombus
                normImpl = new Norm1();
                break;
            case 3: //Square
                normImpl = new NormInfinity();
                break;
            case 4:
                normImpl = new NormN(langNorm, a, b);
                break;
        }
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {
        super.insert(z, zold, zold2, iterations, c, start, c0);

        if(keepLastXItems) {
            return;
        }

        double d = 0;
        if(normtType == 0) {
            d = normImpl.computeWithoutRoot(z);
        }
        else {
            d = normImpl.computeWithRoot(z);
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
    protected void addSample(StatisticSample sam, double[] data) {
        double d = 0;

        Complex z = sam.z_val;

        if(normtType == 0) {
            d = normImpl.computeWithoutRoot(z);
        }
        else {
            d = normImpl.computeWithRoot(z);
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

        if (data[1] > 0) {
            data[0] += Math.pow(Math.abs(xx - lastx), power) + Math.pow(Math.abs(yy - lasty), power) + Math.pow(Math.abs(zz - lastz), power);
        }

        data[1]++;
        lastx = xx;
        lasty = yy;
        lastz = zz;
    }


    private int[] sampleLastX() {
        int length = sampleItems.length;

        double[] data1 = new double[2];
        double[] data2 = new double[2];

        int start = sampleItem % sampleItems.length;
        for(int i = start, count = 1; count < length; i++, count++) {
            StatisticSample sam = sampleItems[i % sampleItems.length];

            if(sam != null) {
                addSample(sam, data2);
            }
        }

        start = (sampleItem + 1) % sampleItems.length;
        for(int i = start, count = 1; count < length; i++, count++) {
            StatisticSample sam = sampleItems[i % sampleItems.length];

            if(sam != null) {
                addSample(sam, data1);
            }
        }

        samples = (int)Math.min(data1[1], data2[1]);
        sum = data1[0];
        sum2 = data2[0];

        return new int [] {(int)data1[1], (int)data2[1]};
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
    protected double getValue() {

        if(keepLastXItems) {
            int[] sample_vals = sampleLastX();

            if(samples < 1) {
                return 0;
            }

            if(useAverage) {
                sum = sum / sample_vals[0];
                sum2 = sum2 / sample_vals[1];
            }
        }
        else {
            if (samples < 2) {
                return 0;
            }

            if (useAverage) {
                sum = sum / (samples - 1);
                sum2 = samples < 3 ? 0 : sum2 / (samples - 2);
            }
        }

        if(!useSmoothing) {
            return sum * statistic_intensity;
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

        return method.interpolate(sum, sum2, smoothing) * statistic_intensity;

    }

    @Override
    protected double getValueNotEscaped() {
        return 20 * super.getValueNotEscaped();
    }

    @Override
    public int getType() {
        return MainWindow.ESCAPING_AND_CONVERGING;
    }

}
