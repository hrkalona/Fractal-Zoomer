

package fractalzoomer.core.blending;

/**
 *
 * @author kaloch
 */
public class OverlayBlending extends Blending {
    
    public OverlayBlending(int color_interpolation, int color_space) {

        super(color_interpolation, color_space);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {
      
        int temp_red = 0;
        if(redB < 128) {
            temp_red = (int)((2.0 * redA * redB) / 255.0 + 0.5);
        }
        else {
            temp_red = (int)(255 - 2.0 *  (255 - redA) * (255 - redB) / 255.0 + 0.5);
        }
        
        int temp_green = 0;
        if(greenB < 128) {
            temp_green = (int)((2.0 * greenA * greenB) / 255.0 + 0.5);
        }
        else {
            temp_green = (int)(255 - 2.0 *  (255 - greenA) * (255 - greenB) / 255.0 + 0.5);
        }
        
        int temp_blue = 0;
        if(blueB < 128) {
            temp_blue = (int)((2.0 * blueA * blueB) / 255.0 + 0.5);
        }
        else {
            temp_blue = (int)(255 - 2.0 *  (255 - blueA) * (255 - blueB) / 255.0 + 0.5);
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
