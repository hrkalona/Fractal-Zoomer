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
    private double log_convergent_bailout;
    private double scaling;
    private int max_iterations;
    private boolean rootSmooting;
    private Fractal fractal;

    private int unmmaped_root_color;

    static {
        roots = new ArrayList<>();
    }

    public RootColoring(double log_convergent_bailout, double scaling, int max_iterations, int[] rootColors, boolean rootSmooting, Fractal fractal, int unmmaped_root_color) {
        super(0, false, false);
        rootTolerance = 1e-10;
        highlightTolerance = 1e-3;
        this.log_convergent_bailout = log_convergent_bailout;
        this.scaling = scaling;
        this.max_iterations = max_iterations;
        this.rootColors = rootColors;
        this.rootSmooting = rootSmooting;
        this.fractal = fractal;
        this.unmmaped_root_color = unmmaped_root_color;
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
    public double getValue() {
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
            if(!(result == null || result.norm_squared() < rootTolerance)) {
                return unmmaped_root_color;
            }
        }

        if(fractal instanceof ExtendedConvergentType) {
            Complex result = ((ExtendedConvergentType)fractal).evaluateFunction(z_val, c_val);
            if(!(result == null || result.norm_squared() < rootTolerance)) {
                return unmmaped_root_color;
            }
        }

        int rootId = 0;
        synchronized (roots) {

            for(Complex root : roots) {
                if(root.distance_squared(z_val) <= rootTolerance) {
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
            double val = (iterations + 1 - OutColorAlgorithm.fractionalPartConverging(z_val, zold_val, zold2_val, log_convergent_bailout)) / scaling;
            return val > 1 ? 1 : val;
        }
        else {
            double val = iterations / scaling;
            return val > 1 ? 1 : val;
        }
    }

    public double getHighlightFactor() {

        synchronized (roots) {

            for(Complex root : roots) {
                double dist = root.distance_squared(pixel_val);
                if(dist <= highlightTolerance) {
                    return dist / highlightTolerance;
                }
            }

        }

        return 0;
    }

}
