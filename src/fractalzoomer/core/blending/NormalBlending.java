
package fractalzoomer.core.blending;

/**
 *
 * @author hrkalona2
 */
public class NormalBlending extends Blending {

    public NormalBlending(int color_interpolation, int color_space) {

        super(color_interpolation, color_space);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        return method.interpolateColors(redB, greenB, blueB, redA, greenA, blueA, coef, false);

    }

}
