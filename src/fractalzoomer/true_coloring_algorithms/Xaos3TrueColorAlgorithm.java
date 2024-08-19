
package fractalzoomer.true_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.ColorSpaceConverter;

/**
 *
 * @author hrkalona
 */
public class Xaos3TrueColorAlgorithm extends TrueColorAlgorithm {

    public Xaos3TrueColorAlgorithm() {
        super();
    }

    @Override
    public int createColor(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel, double stat, double trap, boolean escaped) {

        double h, s, br;
        
        double re = z.getRe();
        double im = z.getIm();

        if (escaped) {
            h = 1 - (Math.atan2(re, im)) / (Math.PI);
            s = (Math.sin(re) + 1) * 0.5;
            br = (Math.sin(im + Math.PI) + 1) * 0.5;
        } else {
            h = 1 - (Math.atan2(re, im)) / (Math.PI);
            s = (Math.sin(re * 50) + 1) * 0.5;
            br = (Math.sin(im * 50 + Math.PI) + 1) * 0.5;
        }

        if (h < 0) {
            h = 0;
        }
        if (s < 0) {
            s = 0;
        }
        if (br < 0) {
            br = 0;
        }

        h = h % 1.0;
        if (s > 1) {
            s = 1;
        }
        if (br > 1) {
            br = 1;
        }

        int[] rgb = ColorSpaceConverter.HSBtoRGB(h, s, br);

        return 0xFF000000 | (rgb[2] << 16) | (rgb[1] << 8) | rgb[0]; //switched blue with red

    }

}
