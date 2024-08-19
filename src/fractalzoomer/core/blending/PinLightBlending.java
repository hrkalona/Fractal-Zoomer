
package fractalzoomer.core.blending;

/**
 *
 * @author kaloch
 */
public class PinLightBlending extends Blending {
    
    public PinLightBlending(int color_interpolation) {

        super(color_interpolation);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {
      
        int temp_red = 0;
        if(redA > 128) {
            temp_red = Math.max(redB, (int)(2.0 * (redA - 128) + 0.5));
        }
        else {
            temp_red = Math.min(redB, 2 * redA);
        }
        
        int temp_green = 0;
        if(greenA > 128) {
            temp_green = Math.max(greenB, (int)(2.0 * (greenA - 128) + 0.5));
        }
        else {
            temp_green = Math.min(greenB, 2 * greenA);
        }
        
        int temp_blue = 0;
        if(blueA > 128) {
            temp_blue = Math.max(blueB, (int)(2.0 * (blueA - 128) + 0.5));
        }
        else {
            temp_blue = Math.min(blueB, 2 * blueA);
        }
        
        temp_red = temp_red > 255 ? 255 : temp_red;
        temp_green = temp_green > 255 ? 255 : temp_green;
        temp_blue = temp_blue > 255 ? 255 : temp_blue;
        
        temp_red = temp_red < 0 ? 0 : temp_red;
        temp_green = temp_green < 0 ? 0 : temp_green;
        temp_blue = temp_blue < 0 ? 0 : temp_blue;
        
        return method.interpolate(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef);
        
    }    
}
