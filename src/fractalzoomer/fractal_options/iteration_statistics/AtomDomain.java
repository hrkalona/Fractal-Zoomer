
package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.*;
import fractalzoomer.main.MainWindow;

/**
 *
 * @author hrkalona2
 */
public class AtomDomain extends GenericStatistic {
    private double minNorm;
    private int minIteration;
    private boolean showAtomDomains;
    private Norm normImpl;

    
    public AtomDomain(boolean showAtomDomains, double statistic_intensity, int normtType, double atomNorm, int lastXItems, double a, double b) {
        super(statistic_intensity, false, false, lastXItems);
        this.showAtomDomains = showAtomDomains;

        switch (normtType) {
            case 0:
                normImpl = new Norm2();
                break;
            case 1: //Rhombus
                normImpl = new Norm1();
                break;
            case 2: //Square
                normImpl = new NormInfinity();
                break;
            case 3:
                normImpl = new NormN(atomNorm, a, b);
                break;
        }
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {

        super.insert(z, zold, zold2, iterations, c, start, c0);

        if(keepLastXItems) {
            return;
        }

        addSample(z);

    }

    private void sampleLastX() {
        minNorm = Double.MAX_VALUE;
        minIteration = 0;
        samples = 0;

        int start = sampleItem >= sampleItems.length ? sampleItem % sampleItems.length : 0;
        for(int i = start, count = 0; count < sampleItems.length; i++, count++) {
            StatisticSample sam = sampleItems[i % sampleItems.length];

            if(sam != null) {
                addSample(sam.z_val);
            }
        }
    }

    private void addSample(Complex z) {
        double currentNorm = normImpl.computeWithRoot(z);

        if(currentNorm < minNorm) {
            minNorm = currentNorm;
            minIteration = iterations;
        }

        samples++;
    }

    @Override
    public void initialize(Complex pixel, Complex untransformedPixel) {
        super.initialize(pixel, untransformedPixel);
        minNorm = Double.MAX_VALUE;
        minIteration = 0;
    }

    @Override
    protected double getValue() {

        if(keepLastXItems) {
            sampleLastX();
        }

        return showAtomDomains ? statistic_intensity * minIteration : statistic_intensity * minNorm;
        
    }

    @Override
    public int getType() {
        return MainWindow.ESCAPING_AND_CONVERGING;
    }
    
}
