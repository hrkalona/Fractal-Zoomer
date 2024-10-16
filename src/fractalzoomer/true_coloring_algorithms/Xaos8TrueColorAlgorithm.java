
package fractalzoomer.true_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.ColorSpaceConverter;

/**
 *
 * @author hrkalona
 */
public class Xaos8TrueColorAlgorithm extends TrueColorAlgorithm {

    public Xaos8TrueColorAlgorithm() {
        super();
    }

    @Override
    public int createColor(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel, double stat, double trap, boolean escaped, double fractional_part) {

        double re = z.getRe();
        double im = z.getIm();

        double cre = c.getRe();
        double cim = c.getIm();

        int r, g, b;

        if (escaped) {
            r = (int) ((Math.abs(re * cim)) * 64 + 0.5);
            b = (int) ((Math.abs(im * cim)) * 64 + 0.5);
            g = (int) ((Math.abs(re * cre)) * 64 + 0.5);
        } else {
            r = (int) ((Math.abs(re * cim)) * 256 + 0.5);
            b = (int) ((Math.abs(im * cim)) * 256 + 0.5);
            g = (int) ((Math.abs(re * cre)) * 256 + 0.5);
        }
        
        r = ColorSpaceConverter.clamp(r);
        g = ColorSpaceConverter.clamp(g);
        b = ColorSpaceConverter.clamp(b);

        return 0xFF000000 | (r << 16) | (g << 8) | b;

    }

}
