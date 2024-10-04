
package fractalzoomer.true_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.ColorSpaceConverter;

/**
 *
 * @author hrkalona
 */
public class Xaos5TrueColorAlgorithm extends TrueColorAlgorithm {

    public Xaos5TrueColorAlgorithm() {
        super();
    }

    @Override
    public int createColor(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel, double stat, double trap, boolean escaped, double fractional_part) {

        int r, g, b;

        double re = z.getRe();
        double im = z.getIm();
        double resqr = re * re;
        double imsqr = im * im;

        if (escaped) {
            r = (int) (Math.cos(Math.abs(imsqr + resqr)) * 128 + 0.5) + 128;
            b = (int) (Math.cos(Math.abs(resqr)) * 128 + 0.5) + 128;
            g = (int) (Math.cos(Math.abs(re * im)) * 128 + 0.5) + 128;
        } else {                     
            r = (int) (Math.cos(Math.abs(imsqr + resqr) * 10) * 128 + 0.5) + 128;
            b = (int) (Math.cos(Math.abs(resqr) * 10) * 128 + 0.5) + 128;
            g = (int) (Math.cos(Math.abs(re * im) * 10) * 128 + 0.5) + 128;
        }
        
        r = ColorSpaceConverter.clamp(r);
        g = ColorSpaceConverter.clamp(g);
        b = ColorSpaceConverter.clamp(b);

        return 0xFF000000 | (r << 16) | (g << 8) | b;

    }

}
