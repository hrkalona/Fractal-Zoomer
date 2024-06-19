
package fractalzoomer.core.blending;

/**
 *
 * @author hrkalona2
 */
public class AdditionBlending extends Blending {

    public AdditionBlending(int color_interpolation) {

        super(color_interpolation);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        int temp_red = Math.min(redB + redA, 255);
        int temp_green = Math.min(greenB + greenA, 255);
        int temp_blue = Math.min(blueB + blueA, 255);
        
        return method.interpolate(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef);

    }
    
}
