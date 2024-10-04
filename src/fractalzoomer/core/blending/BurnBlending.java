
package fractalzoomer.core.blending;

/**
 *
 * @author hrkalona2
 */
public class BurnBlending extends Blending {

    public BurnBlending(int color_interpolation, int color_space) {

        super(color_interpolation, color_space);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {
        
        if(redB == 255 && greenB == 255 && blueB == 255) {
            redB = 254;
            greenB = 254;
            blueB = 254;
        }
        
        int temp_red = (int)(255 - (256.0 * (255 - redB)) / (redA + 1));
        int temp_green = (int)(255 - (256.0 * (255 - greenB)) / (greenA + 1));
        int temp_blue = (int)(255 - (256.0 * (255 - blueB)) / (blueA + 1));
        
        temp_red = temp_red < 0 ? 0 : temp_red;
        temp_green = temp_green < 0 ? 0 : temp_green;
        temp_blue = temp_blue < 0 ? 0 : temp_blue;
        
        return method.interpolateColors(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef, false);

    }
    
}
