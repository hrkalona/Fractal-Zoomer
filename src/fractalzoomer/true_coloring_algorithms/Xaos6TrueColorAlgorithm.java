
package fractalzoomer.true_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.ColorSpaceConverter;

/**
 *
 * @author hrkalona
 */
public class Xaos6TrueColorAlgorithm extends TrueColorAlgorithm {

    public Xaos6TrueColorAlgorithm() {
        super();
    }

    @Override
    public int createColor(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel, double stat, double trap, boolean escaped, double fractional_part) {

        double re = z.getRe();
        double im = -z.getIm();

        int r, g, b;

        r = (int) (im * im * 256 + 0.5);
        g = (int) (re * re * 256 + 0.5);
        b = (int) (re * im * 256 + 0.5);
        
        r = ColorSpaceConverter.clamp(r);
        g = ColorSpaceConverter.clamp(g);
        b = ColorSpaceConverter.clamp(b);

        return 0xFF000000 | (r << 16) | (g << 8) | b;

    }

}
