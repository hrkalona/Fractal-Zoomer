
package fractalzoomer.core.domain_coloring;

import fractalzoomer.core.Complex;
import fractalzoomer.core.blending.Blending;
import fractalzoomer.main.app_settings.GeneratedPaletteSettings;
import fractalzoomer.palettes.PaletteColor;
import fractalzoomer.palettes.transfer_functions.TransferFunction;

/**
 *
 * @author hrkalona2
 */
public class IsoContoursContoursLog2DomainColoring extends DomainColoring {

    public IsoContoursContoursLog2DomainColoring(int domain_coloring_mode, PaletteColor palette, TransferFunction color_transfer, int color_cycling_location, GeneratedPaletteSettings gps, Blending blending, int interpolation, double countourFactor, int color_space) {

        super(domain_coloring_mode, palette, color_transfer, color_cycling_location, gps, interpolation, blending, countourFactor, color_space);
        
        iso_distance = 1.0 / 12.0;
        iso_factor = 0.5;
        
        gradient = blackToWhite;

        logBaseFinal = Math.log(2);
        
        contourBlending = 0.5;
        
    }

    @Override
    public int getDomainColor(Complex res) {

        res = round(res);
        
        double arg = res.arg();

        int color = applyArgColor(arg);
        
        return applyNormArgContours(color, res.norm(), arg);   

    }
    
}
