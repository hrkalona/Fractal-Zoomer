
package fractalzoomer.true_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.ColorSpaceConverter;
import fractalzoomer.utils.TrueColorData;

/**
 *
 * @author hrkalona
 */
public class Xaos1TrueColorAlgorithm extends TrueColorAlgorithm {

    public Xaos1TrueColorAlgorithm() {
        super();
    }

    @Override
    public int createColor(TrueColorData data) {

        Complex z = data.z;
        double re = z.getRe();
        double im = -z.getIm();
        
        int r = (int) ((Math.sin(Math.atan2(re, im) * 20) + 1) * 127 + 0.5);
        int w = (int) (Math.sin((im / re)) * 127 + 0.5);
        int b = (int) (re * im + 0.5);
        int g = (int) ((Math.sin((re * re) * 0.5) + 1) * 127 + 0.5);

        r += w;
        g += w;
        b += w;
        
        r = ColorSpaceConverter.clamp(r);
        g = ColorSpaceConverter.clamp(g);
        b = ColorSpaceConverter.clamp(b);

        return  0xFF000000 | (r << 16) | (g << 8) | b;
        
    }

}
