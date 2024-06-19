
package fractalzoomer.true_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.ColorSpaceConverter;

/**
 *
 * @author hrkalona
 */
public class Xaos10TrueColorAlgorithm extends TrueColorAlgorithm {

    public Xaos10TrueColorAlgorithm() {
        super();
    }

    @Override
    public int createColor(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel, double stat, double trap, boolean escaped) {

        double re = z.getRe();
        double im = -z.getIm();

        int r, g, b;

        r = (int) (Math.atan2(im, re) * 128 / Math.PI + 0.5) + 128;
        b = (int) (Math.atan2(re, im) * 128 / Math.PI + 0.5) + 128;
        g = (int) (Math.atan2(re, im) * 128 / Math.PI + 0.5) + 128;
        
        r = ColorSpaceConverter.clamp(r);
        g = ColorSpaceConverter.clamp(g);
        b = ColorSpaceConverter.clamp(b);

        return 0xFF000000 | (r << 16) | (g << 8) | b;

    }

}
