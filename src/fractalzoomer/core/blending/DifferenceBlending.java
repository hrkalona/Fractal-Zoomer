
package fractalzoomer.core.blending;

/**
 *
 * @author hrkalona2
 */
public class DifferenceBlending extends Blending {

    public DifferenceBlending(int color_interpolation, int color_space) {

        super(color_interpolation, color_space);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        int temp_red = Math.abs(redB - redA);
        int temp_green = Math.abs(greenB - greenA);
        int temp_blue = Math.abs(blueB - blueA);
        
        return method.interpolateColors(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef, false);

    }
    
}
