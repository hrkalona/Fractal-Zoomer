
package fractalzoomer.core.blending;

/**
 *
 * @author hrkalona2
 */
public class NormalBlending extends Blending {

    public NormalBlending(int color_interpolation) {

        super(color_interpolation);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        return method.interpolate(redB, greenB, blueB, redA, greenA, blueA, coef);

    }

}
