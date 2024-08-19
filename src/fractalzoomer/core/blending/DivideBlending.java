
package fractalzoomer.core.blending;

/**
 *
 * @author hrkalona2
 */
public class DivideBlending extends Blending {

    public DivideBlending(int color_interpolation) {

        super(color_interpolation);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {
        
        if(redB == 0 && greenB == 0 && blueB == 0) {
            redB = 1;
            greenB = 1;
            blueB = 1;
        }
        
        int temp_red = (int)((redB * 256.0) / (redA + 1) + 0.5);
        int temp_green = (int)((greenB * 256.0) / (greenA + 1) + 0.5);
        int temp_blue = (int)((blueB * 256.0) / (blueA + 1) + 0.5);
        
        temp_red = temp_red > 255 ? 255 : temp_red;
        temp_green = temp_green > 255 ? 255 : temp_green;
        temp_blue = temp_blue > 255 ? 255 : temp_blue;
        
        return method.interpolate(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef);

    }
}

