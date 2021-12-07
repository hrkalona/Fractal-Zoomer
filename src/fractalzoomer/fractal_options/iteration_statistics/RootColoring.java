package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.main.MainWindow;
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;

import java.util.ArrayList;

public class RootColoring extends GenericStatistic {
    public static ArrayList<Complex> roots;
    private double rootTolerance;
    private double highlightTolerance;
    private int[] rootColors;
    private double log_convergent_bailout;
    private double scaling;
    private int max_iterations;
    private boolean rootSmooting;

    static {
        roots = new ArrayList<>();
    }

    public RootColoring(double log_convergent_bailout, double scaling, int max_iterations, int[] rootColors, boolean rootSmooting) {
        super(0, false, false);
        rootTolerance = 1e-10;
        highlightTolerance = 1e-3;
        this.log_convergent_bailout = log_convergent_bailout;
        this.scaling = scaling;
        this.max_iterations = max_iterations;
        this.rootColors = rootColors;
        this.rootSmooting = rootSmooting;
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
            return 0;
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

        return rootColors[rootId % rootColors.length];

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
