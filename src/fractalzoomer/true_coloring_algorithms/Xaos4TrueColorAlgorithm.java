
package fractalzoomer.true_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.ColorSpaceConverter;

/**
 *
 * @author hrkalona
 */
public class Xaos4TrueColorAlgorithm extends TrueColorAlgorithm {

    public Xaos4TrueColorAlgorithm() {
        super();
    }

    @Override
    public int createColor(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel, double stat, double trap, boolean escaped, double fractional_part) {

        double h, s, br;
        
        double re = z.getRe();
        double im = z.getIm();

        if (escaped) {                        
            h = Math.sin((re * re + im * im) * 0.01);
            s = Math.sin(Math.atan2(re, im) * 10 + Math.PI) * 0.5 + 0.5;
            br = Math.sin((re - im) * 0.3) * 0.25390625 + 0.5;
        } else {
            h = Math.sin((re * re + im * im) * 0.1);
            s = Math.sin(Math.atan2(re, im) * 10 + Math.PI) * 0.5 + 0.5;
            br = Math.sin((re - im) * 10) * 0.25390625 + 0.5;
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

        return 0xFF000000 | (rgb[2] << 16) | (rgb[1] << 8) | rgb[0];  //switched blue with red

    }

}
