
package fractalzoomer.core.blending;

/**
 *
 * @author hrkalona2
 */
public class HardLightBlending extends Blending {

    public HardLightBlending(int color_interpolation) {

        super(color_interpolation);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {
        
        int temp_red = 0;
        int temp_green = 0;
        int temp_blue = 0;
        
        if(redA > 128) {
            temp_red = (int)(255 - (255.0 -  2 * (redA - 128)) * (255 - redB) / 256.0 + 0.5);
        }
        else {
            temp_red = (int)(2 * redA * redB / 256.0 + 0.5);
        }
        
        if(greenA > 128) {
            temp_green = (int)(255 - (255.0 -  2 * (greenA - 128)) * (255 - greenB) / 256.0 + 0.5);
        }
        else {
            temp_green = (int)(2 * greenA * greenB / 256.0 + 0.5);
        }
        
        if(blueA > 128) {
            temp_blue = (int)(255 - (255.0 -  2 * (blueA - 128)) * (255 - blueB) / 256.0 + 0.5);
        }
        else {
            temp_blue = (int)(2 * blueA * blueB / 256.0 + 0.5);
        }
        
        return method.interpolate(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef);

    }
    
}
