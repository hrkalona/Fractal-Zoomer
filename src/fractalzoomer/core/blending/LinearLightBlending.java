
package fractalzoomer.core.blending;

/**
 *
 * @author kaloch
 */
public class LinearLightBlending extends Blending {
    
    public LinearLightBlending(int color_interpolation, int color_space) {

        super(color_interpolation, color_space);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {
      
        int temp_red = 0;
        if(redA <= 128) {
            temp_red = redB + 2 * redA - 255;
        }
        else {
            temp_red = (int)(redB + 2.0 * (redA - 128) + 0.5);
        }
        
        int temp_green = 0;
        if(greenA <= 128) {
            temp_green = greenB + 2 * greenA - 255;
        }
        else {
            temp_green = (int)(greenB + 2.0 * (greenA - 128) + 0.5);
        }
        
        int temp_blue = 0;
        if(blueA <= 128) {
            temp_blue = blueB + 2 * blueA - 255;
        }
        else {
            temp_blue = (int)(blueB + 2.0 * (blueA - 128) + 0.5);
        }
        
        temp_red = temp_red > 255 ? 255 : temp_red;
        temp_green = temp_green > 255 ? 255 : temp_green;
        temp_blue = temp_blue > 255 ? 255 : temp_blue;
        
        temp_red = temp_red < 0 ? 0 : temp_red;
        temp_green = temp_green < 0 ? 0 : temp_green;
        temp_blue = temp_blue < 0 ? 0 : temp_blue;
        
        return method.interpolateColors(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef, false);
        
    }    
    
}
