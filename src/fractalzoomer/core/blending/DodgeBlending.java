
package fractalzoomer.core.blending;

/**
 *
 * @author hrkalona2
 */
public class DodgeBlending extends Blending {

    public DodgeBlending(int color_interpolation, int color_space) {

        super(color_interpolation, color_space);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        if(redB == 0 && greenB == 0 && blueB == 0) {
            redB = 1;
            greenB = 1;
            blueB = 1;
        }
        
        int temp_red = (int)((256.0 * redB) / ((255.0 - redA) + 1) + 0.5);
        int temp_green = (int)((256.0 * greenB) / ((255.0 - greenA) + 1) + 0.5);
        int temp_blue = (int)((256.0 * blueB) / ((255.0 - blueA) + 1) + 0.5);
        
        temp_red = temp_red > 255 ? 255 : temp_red;
        temp_green = temp_green > 255 ? 255 : temp_green;
        temp_blue = temp_blue > 255 ? 255 : temp_blue;
        
        return method.interpolateColors(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef, false);

    }
    
}
