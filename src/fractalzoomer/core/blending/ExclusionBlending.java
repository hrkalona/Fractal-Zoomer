
package fractalzoomer.core.blending;

/**
 *
 * @author kaloch
 */
public class ExclusionBlending extends Blending {
    
    public ExclusionBlending(int color_interpolation) {

        super(color_interpolation);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        int temp_red = (int)(redB + redA - 2 * (redB * redA) / 255.0 + 0.5);
        int temp_green = (int)(greenB + greenA - 2 * (greenB * greenA) / 255.0 + 0.5);
        int temp_blue = (int)(blueB + blueA - 2 * (blueB * blueA) / 255.0 + 0.5);
   
        temp_red = temp_red > 255 ? 255 : temp_red;
        temp_green = temp_green > 255 ? 255 : temp_green;
        temp_blue = temp_blue > 255 ? 255 : temp_blue;
        
        temp_red = temp_red < 0 ? 0 : temp_red;
        temp_green = temp_green < 0 ? 0 : temp_green;
        temp_blue = temp_blue < 0 ? 0 : temp_blue;
        
        return method.interpolate(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef);
        
    }
    
}
