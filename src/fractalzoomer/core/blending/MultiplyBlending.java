
package fractalzoomer.core.blending;

/**
 *
 * @author hrkalona2
 */
public class MultiplyBlending extends Blending {

    public MultiplyBlending(int color_interpolation) {

        super(color_interpolation);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        int temp_red = (int)((redA * redB) / 255.0 + 0.5);
        int temp_green = (int)((greenA * greenB) / 255.0 + 0.5);
        int temp_blue = (int)((blueA * blueB) / 255.0 + 0.5);
        
        return method.interpolate(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef);

    }
}
