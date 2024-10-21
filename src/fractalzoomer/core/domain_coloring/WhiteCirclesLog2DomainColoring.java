
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
public class WhiteCirclesLog2DomainColoring extends DomainColoring {

    public WhiteCirclesLog2DomainColoring(int domain_coloring_mode, PaletteColor palette, TransferFunction color_transfer, int color_cycling_location, GeneratedPaletteSettings gps, Blending blending, int interpolation, double countourFactor, int color_space) {

        super(domain_coloring_mode, palette, color_transfer, color_cycling_location, gps, interpolation, blending, countourFactor, color_space);

        circlesColorRed = 255;
        circlesColorGreen = 255;
        circlesColorBlue = 255;
        
        logBaseFinal = Math.log(2);
        
        circlesBlending = 1;
        
    }
    
    @Override
    public int getDomainColor(Complex res) {

        res = round(res);
        
        int color = applyArgColor(res.arg());
        
        return applyCircles(color, res.norm(), 0);
    }

}
