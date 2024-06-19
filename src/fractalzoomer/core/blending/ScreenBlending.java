
package fractalzoomer.core.blending;

/**
 *
 * @author hrkalona2
 */
public class ScreenBlending extends Blending {

    public ScreenBlending(int color_interpolation) {

        super(color_interpolation);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        int temp_red = (int)(255 - (255 - redA)*(255 - redB) / 255.0 + 0.5);
        int temp_green = (int)(255 - (255 - greenA)*(255 - greenB) / 255.0 + 0.5);
        int temp_blue = (int)(255 - (255 - blueA)*(255 - blueB) / 255.0 + 0.5);
        
        return method.interpolate(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef);

    }
    
}
