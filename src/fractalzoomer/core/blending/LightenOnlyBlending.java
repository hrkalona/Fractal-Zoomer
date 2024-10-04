
package fractalzoomer.core.blending;

/**
 *
 * @author hrkalona2
 */
public class LightenOnlyBlending extends Blending {

    public LightenOnlyBlending(int color_interpolation, int color_space) {

        super(color_interpolation, color_space);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        int temp_red = Math.max(redB, redA);
        int temp_green = Math.max(greenB, greenA);
        int temp_blue = Math.max(blueB, blueA);
        
        return method.interpolateColors(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef, false);

    }
    
}
