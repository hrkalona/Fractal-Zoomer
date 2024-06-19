
package fractalzoomer.core.blending;

import fractalzoomer.core.interpolation.InterpolationMethod;

/**
 *
 * @author hrkalona2
 */
public abstract class Blending {
    protected InterpolationMethod method;
    protected boolean reverseColors;
    
    protected Blending(int color_interpolation) {
        
        method = InterpolationMethod.create(color_interpolation);

    }

    public void setReverseColors(boolean reverseColors) {
        this.reverseColors = reverseColors;
    }
    public int blend(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

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

        return blendInternal(redA, greenA, blueA, redB, greenB, blueB, coef);
    }
    
    protected abstract int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef);
}
