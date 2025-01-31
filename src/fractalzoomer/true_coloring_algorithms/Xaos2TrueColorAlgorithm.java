
package fractalzoomer.true_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.ColorSpaceConverter;

/**
 *
 * @author hrkalona
 */
public class Xaos2TrueColorAlgorithm extends TrueColorAlgorithm {

    public Xaos2TrueColorAlgorithm() {
        super();
    }

    @Override
    public int createColor(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel, double stat, double trap, boolean escaped, double fractional_part) {

        double re = z.getRe();
        double im = -z.getIm();
        
        int w = (int) ((Math.sin(im / re)) * 127 + 0.5);
        int r, g, b;

        if (escaped) {
            r = (int) ((Math.sin((im * im + re * re) * 0.5) + 1) * 127 + 0.5);
            b = (int) ((Math.sin(re * 2) + 1) * 127 + 0.5);
            g = (int) ((Math.sin(im * 2) + 1) * 127 + 0.5);
        } else {
            r = (int) ((Math.sin((im * im + re * re) * 50) + 1) * 127 + 0.5);
            b = (int) ((Math.sin(re * 50) + 1) * 127 + 0.5);
            g = (int) ((Math.sin(im * 50) + 1) * 127 + 0.5);
        }

        r += w;
        g += w;
        b += w;
        
        r = ColorSpaceConverter.clamp(r);
        g = ColorSpaceConverter.clamp(g);
        b = ColorSpaceConverter.clamp(b);

        return 0xFF000000 | (r << 16) | (g << 8) | b;

    }

}
