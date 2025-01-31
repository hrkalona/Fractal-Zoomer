
package fractalzoomer.core.blending;

/**
 *
 * @author hrkalona2
 */
public class SoftLightBlending extends Blending {

    public SoftLightBlending(int color_interpolation, int color_space) {

        super(color_interpolation, color_space);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        int temp_red = (int)((redB / 255.0) * (redB + ((2 * redA) / 255.0) * (255 - redB)) + 0.5);
        int temp_green = (int)((greenB / 255.0) * (greenB + ((2 * greenA) / 255.0) * (255 - greenB)) + 0.5);
        int temp_blue = (int)((blueB / 255.0) * (blueB + ((2 * blueA) / 255.0) * (255 - blueB)) + 0.5);
        
        return method.interpolateColors(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef, false);
        
    }
    
}
