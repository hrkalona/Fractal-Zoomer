
package fractalzoomer.true_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.palettes.PaletteColor;
import fractalzoomer.palettes.transfer_functions.TransferFunction;

/**
 *
 * @author hrkalona
 */
public abstract class TrueColorAlgorithm {
    public static PaletteColor palette_outcoloring;
    public static PaletteColor palette_incoloring;
    public static TransferFunction color_transfer_outcoloring;
    public static TransferFunction color_transfer_incoloring;
    public static boolean usePaletteForInColoring;
    public static int color_cycling_location_outcoloring;
    public static int color_cycling_location_incoloring;
    public static int[] gradient;
    public static int gradient_offset;

    protected TrueColorAlgorithm() {

    }

    public abstract int createColor(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel, double stat, double trap, boolean escaped, double fractional_part);

    
}
