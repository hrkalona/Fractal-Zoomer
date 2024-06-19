
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;

import java.awt.image.BufferedImage;

import static fractalzoomer.main.Constants.TRAP_CHECK_TYPE_TRAPPED_FIRST;
import static fractalzoomer.main.Constants.TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE;

/**
 *
 * @author hrkalona
 */
public class TransparentImageOrbitTrap extends OrbitTrap {
    private int color;
    public static BufferedImage image;

    public TransparentImageOrbitTrap(int checkType, double pointRe, double pointIm, int lastXItems) {

        super(checkType, pointRe, pointIm, 0, 0, false, lastXItems);
        if(checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE) {
            this.checkType = TRAP_CHECK_TYPE_TRAPPED_FIRST;
        }

    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        if (iteration > 0) {
            Complex diff = val.sub(point);


            if (image != null) {

                double x = diff.getRe();
                double y = diff.getIm();

                if(x < 0 || y < 0) {
                    return;
                }

                int j = (int)(y * (image.getHeight() - 1) + 0.5);
                int i = (int)(x * (image.getWidth() - 1) + 0.5);

                if(i >= image.getWidth() || j >= image.getHeight()) {
                    return;
                }

                color = image.getRGB(i, j);

                if(color >>> 24 > 0) {
                    setTrappedData(val, iteration);
                }

            }

        }

    }

    @Override
    public void initialize(Complex pixel) {

        super.initialize(pixel);

        color = 0;

    }

    @Override
    public double getMaxValue() {
        return 0;
    }

    @Override
    public int getColor() {

        if(keepLastXItems && !processedLastItems) {
            processLastItems();
        }

        return color;

    }

    @Override
    public boolean hasColor() {

        return true;

    }

}
