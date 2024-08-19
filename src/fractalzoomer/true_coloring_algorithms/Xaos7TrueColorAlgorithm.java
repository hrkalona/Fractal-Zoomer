
package fractalzoomer.true_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.ColorSpaceConverter;

/**
 *
 * @author hrkalona
 */
public class Xaos7TrueColorAlgorithm extends TrueColorAlgorithm {

    public Xaos7TrueColorAlgorithm() {
        super();
    }

    @Override
    public int createColor(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel, double stat, double trap, boolean escaped) {

        int r, g, b;
        
        double re = z.getRe();
        double im = z.getIm();
        
        double cre = c.getRe();
        double cim = c.getIm();

        double resqr = re * re;
        double imsqr = im * im;
        double cresqr = cre * cre;
        double cimsqr = cim * cim;

        if (escaped) {
            r = (int) ((imsqr * 2 - cresqr - cimsqr) * 16 + 0.5);
            b = (int) ((resqr + imsqr - cresqr - cimsqr) * 16 + 0.5);
            g = (int) ((resqr * 2 - cresqr - cimsqr) * 16 + 0.5);                
        } else {
            r = (int) ((imsqr * 2 - cresqr - cimsqr) * 256 + 0.5);
            b = (int) ((resqr + imsqr - cresqr - cimsqr) * 256 + 0.5);
            g = (int) ((resqr * 2 - cresqr - cimsqr) * 256 + 0.5);
        }
        
        r = ColorSpaceConverter.clamp(r);
        g = ColorSpaceConverter.clamp(g);
        b = ColorSpaceConverter.clamp(b);

        return 0xFF000000 | (r << 16) | (g << 8) | b;

    }

}
