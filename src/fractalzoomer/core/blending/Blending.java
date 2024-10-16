
package fractalzoomer.core.blending;

import fractalzoomer.core.interpolation.InterpolationMethod;
import fractalzoomer.utils.ColorCorrection;

/**
 *
 * @author hrkalona2
 */
public abstract class Blending {
    protected InterpolationMethod method;
    protected boolean reverseColors;
    
    protected Blending(int color_interpolation, int color_space) {
        
        method = InterpolationMethod.create(color_interpolation);
        method.setColorSpace(color_space);

    }

    public void setReverseColors(boolean reverseColors) {
        this.reverseColors = reverseColors;
    }
    public int blend(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        redA = ColorCorrection.gammaToLinear(redA);
        greenA = ColorCorrection.gammaToLinear(greenA);
        blueA = ColorCorrection.gammaToLinear(blueA);

        redB = ColorCorrection.gammaToLinear(redB);
        greenB = ColorCorrection.gammaToLinear(greenB);
        blueB = ColorCorrection.gammaToLinear(blueB);

        if(reverseColors) {
            int tempRed = redA;
            int tempGreen = greenA;
            int tempBlue = blueA;

            redA = redB;
            greenA = greenB;
            blueA = blueB;

            redB = tempRed;
            greenB = tempGreen;
            blueB = tempBlue;
        }

        return ColorCorrection.linearToGamma(blendInternal(redA, greenA, blueA, redB, greenB, blueB, coef));
    }
    
    protected abstract int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef);
}
