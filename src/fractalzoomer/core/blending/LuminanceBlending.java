
package fractalzoomer.core.blending;

import fractalzoomer.utils.ColorSpaceConverter;


/**
 *
 * @author kaloch
 */
public class LuminanceBlending extends Blending {
    
    public LuminanceBlending(int color_interpolation, int color_space) {

        super(color_interpolation, color_space);
        
    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {
      
        int temp_red = 0, temp_green = 0, temp_blue = 0;
        
        if(redB == 0 && greenB == 0 && blueB == 0) {
            redB = 1;
            greenB = 1;
            blueB = 1;
        }
        
        double[] resB = ColorSpaceConverter.RGBtoLAB(redB, greenB, blueB);
        double[] resA = ColorSpaceConverter.RGBtoLAB(redA, greenA, blueA);
        
        resB[0] = resB[0] == 0 ? 1e-16 : resB[0];
        
        double ratio = resA[0] / resB[0];
        
        temp_red = (int)(redB * ratio + 0.5);
        temp_green = (int)(greenB * ratio + 0.5);
        temp_blue = (int)(blueB * ratio + 0.5);

        temp_red = temp_red > 255 ? 255 : temp_red;
        temp_green = temp_green > 255 ? 255 : temp_green;
        temp_blue = temp_blue > 255 ? 255 : temp_blue;
        
        return method.interpolateColors(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef, false);
        
    }     
}
