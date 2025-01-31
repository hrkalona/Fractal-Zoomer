
package fractalzoomer.palettes.transfer_functions;

/**
 *
 * @author hrkalona2
 */
public abstract class TransferFunction {
    protected static double IT_COLOR_DENSITY_MULTIPLIER = 0.05;
    protected int paletteLength;
    protected double paletteMultiplier;
    protected boolean banded;
    
    public TransferFunction(int paletteLength, boolean banded) {
        
        this.paletteLength = paletteLength;
        paletteMultiplier = paletteLength <= 2000 ? 1 : 2000.0 / paletteLength;
        this.banded = banded;
        
    }
    
    public abstract double transfer(double result);

    public void setBanded(boolean banded) {
        this.banded = banded;
    }
}
