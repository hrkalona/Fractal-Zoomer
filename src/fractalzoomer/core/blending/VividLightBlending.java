
package fractalzoomer.core.blending;

/**
 *
 * @author kaloch
 */
public class VividLightBlending extends Blending {
    
    public VividLightBlending(int color_interpolation, int color_space) {

        super(color_interpolation, color_space);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        if(redB == 0 && greenB == 0 && blueB == 0) {
            redB = 1;
            greenB = 1;
            blueB = 1;
        }
        
        if(redB == 255 && greenB == 255 && blueB == 255) {
            redB = 254;
            greenB = 254;
            blueB = 254;
        }
        
        int temp_red = 0;
        
        if(redA <= 128) {
            temp_red = (int)(255 - (256.0 * (255 - redB)) / (2 * redA + 1));
        }
        else {
            temp_red = (int)((256.0 * redB) / (2 * (255.0 - redA) + 1) + 0.5);
        }
        
        int temp_green = 0;
        if(greenA <= 128) {
            temp_green = (int)(255 - (256.0 * (255 - greenB)) / (2 * greenA + 1));
        }
        else {
            temp_green = (int)((256.0 * greenB) / (2 * (255.0 - greenA) + 1) + 0.5);
        }
        
        int temp_blue = 0;
        if(blueA <= 128) {
            temp_blue = (int)(255 - (256.0 * (255 - blueB)) / (2 * blueA + 1));
        }
        else {
            temp_blue = (int)((256.0 * blueB) / (2 * (255.0 - blueA) + 1) + 0.5);
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
