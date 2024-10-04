
package fractalzoomer.palettes.transfer_functions;

/**
 *
 * @author hrkalona2
 */
public class KFSqrtTransferFunction extends TransferFunction {

    private double color_intensity;
    public KFSqrtTransferFunction(int paletteLength, double color_intensity, boolean banded) {

        super(paletteLength, banded);
        this.color_intensity = color_intensity;

    }

    @Override
    public double transfer(double result) {

        boolean isNeg = result < 0;

        if (isNeg) {
            result = -result;
        }

        if(banded) {
            result = (long) (result);
        }

        result = Math.sqrt(result);

        double final_result = result * color_intensity;
        return isNeg ? -final_result : final_result;

    }

}