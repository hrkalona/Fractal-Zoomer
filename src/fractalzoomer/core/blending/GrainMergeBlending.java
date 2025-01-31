
package fractalzoomer.core.blending;

/**
 *
 * @author hrkalona2
 */
public class GrainMergeBlending extends Blending {

    public GrainMergeBlending(int color_interpolation, int color_space) {

        super(color_interpolation, color_space);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        int temp_red = redB + redA - 128;
        int temp_green = greenB + greenA - 128;
        int temp_blue = blueB + blueA - 128;
        
        temp_red = temp_red > 255 ? 255 : temp_red;
        temp_green = temp_green > 255 ? 255 : temp_green;
        temp_blue = temp_blue > 255 ? 255 : temp_blue;
        
        temp_red = temp_red < 0 ? 0 : temp_red;
        temp_green = temp_green < 0 ? 0 : temp_green;
        temp_blue = temp_blue < 0 ? 0 : temp_blue;
        
        return method.interpolateColors(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef, false);

    }
    
}
