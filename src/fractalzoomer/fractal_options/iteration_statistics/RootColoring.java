package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.ExtendedConvergentType;
import fractalzoomer.functions.Fractal;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.MainWindow;
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class RootColoring extends GenericStatistic {
    public static List<Complex> roots;
    private double rootTolerance;
    private double highlightTolerance;
    private int[] rootColors;
    private double scaling;
    private int max_iterations;
    private boolean rootSmooting;
    private Fractal fractal;
    private int unmmaped_root_color;

    private boolean capTo1;

    static {
        roots = new ArrayList<>();
    }

    public RootColoring(double scaling, int max_iterations, int[] rootColors, boolean rootSmooting, Fractal fractal, int unmmaped_root_color, boolean capTo1) {
        super(0, false, false, 0);
        rootTolerance = 1e-10;
        highlightTolerance = 1e-3;
        this.scaling = scaling;
        this.max_iterations = max_iterations;
        this.rootColors = rootColors;
        this.rootSmooting = rootSmooting;
        this.fractal = fractal;
        this.unmmaped_root_color = unmmaped_root_color;
        this.capTo1 = capTo1;
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {
        super.insert(z, zold, zold2, iterations, c, start, c0);

        samples++;

    }

    @Override
    public void initialize(Complex pixel, Complex untransformedPixel) {
        super.initialize(pixel, untransformedPixel);
        samples = 0;
    }

    @Override
    protected double getValue() {
        return 0;
    }

    @Override
    public int getType() {
        return MainWindow.CONVERGING;
    }

    public int getRootColor() {

        if(iterations == max_iterations) {
            return unmmaped_root_color;
        }

        if(fractal instanceof RootFindingMethods) {
            Complex result = ((RootFindingMethods)fractal).evaluateFunction(z_val, c_val);
            if(!(result == null || cNormSmoothingImpl.computeWithoutRoot(result) < rootTolerance)) {
                return unmmaped_root_color;
            }
        }

        if(fractal instanceof ExtendedConvergentType) {
            Complex result = ((ExtendedConvergentType)fractal).evaluateFunction(z_val, c_val);
            if(!(result == null || cNormSmoothingImpl.computeWithoutRoot(result) < rootTolerance)) {
                return unmmaped_root_color;
            }
        }

        int rootId = 0;
        synchronized (roots) {

            for(Complex root : roots) {
                if(cNormSmoothingImpl.computeWithoutRoot(root.sub(z_val)) <= rootTolerance) {
                    break;
                }
                rootId++;
            }

            if(rootId == roots.size()) {
                roots.add(new Complex(z_val));
            }
        }

        if(rootId >= rootColors.length) {
            return unmmaped_root_color;
        }

        return rootColors[rootId];

    }

    public double getDepthFactor() {
        if(rootSmooting) {
            double smoothing;

            if(converging_smoothing_algorithm == 0) {
                smoothing = OutColorAlgorithm.fractionalPartConverging1(z_val, zold_val, zold2_val, log_convergent_bailout, cNormSmoothingImpl);
            }
            else {
                smoothing = OutColorAlgorithm.fractionalPartConverging2(z_val, zold_val, zold2_val, log_convergent_bailout, cNormSmoothingImpl);
            }

            double val = (iterations + 1 - smoothing) / scaling;
            return capTo1 ? (val > 1 ? 1 : val) : val % 2;
        }
        else {
            double val = iterations / scaling;
            return capTo1 ? (val > 1 ? 1 : val) : val % 2;
        }
    }

    public double getHighlightFactor() {

        synchronized (roots) {

            for(Complex root : roots) {
                double dist = cNormSmoothingImpl.computeWithoutRoot(root.sub(pixel_val));
                if(dist <= highlightTolerance) {
                    return dist / highlightTolerance;
                }
            }

        }

        return 0;
    }

}
