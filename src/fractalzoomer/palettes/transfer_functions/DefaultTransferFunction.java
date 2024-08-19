
package fractalzoomer.palettes.transfer_functions;

/**
 *
 * @author hrkalona2
 */
public class DefaultTransferFunction extends TransferFunction {
    private double color_intensity;
    
    public DefaultTransferFunction(int paletteLength, double color_intensity, boolean banded) {
        
        super(paletteLength, banded);
        this.color_intensity = color_intensity;
        
    }
    
    @Override
    public double transfer(double result) {



        if(banded) {
            boolean isNeg = result < 0;

            if(isNeg) {
                result = -result;
            }

            //double fract_part = result - (long) result;
            result = (long) (result);
            double final_result = color_intensity * result;// + (int)(fract_part / (1 / color_intensity));

            return isNeg ? -final_result : final_result;
        }
        else {
            return color_intensity * result;
        }
        
    }
    
}
