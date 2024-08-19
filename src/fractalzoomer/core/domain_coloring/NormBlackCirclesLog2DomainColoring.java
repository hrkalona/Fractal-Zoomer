
package fractalzoomer.core.domain_coloring;

import fractalzoomer.core.Complex;
import fractalzoomer.core.blending.Blending;
import fractalzoomer.main.app_settings.GeneratedPaletteSettings;
import fractalzoomer.palettes.PaletteColor;
import fractalzoomer.palettes.transfer_functions.TransferFunction;

/**
 *
 * @author kaloch
 */
public class NormBlackCirclesLog2DomainColoring extends DomainColoring {

    public NormBlackCirclesLog2DomainColoring(int domain_coloring_mode, PaletteColor palette, TransferFunction color_transfer, int color_cycling_location, GeneratedPaletteSettings gps, Blending blending, int interpolation, double countourFactor) {

        super(domain_coloring_mode, palette, color_transfer, color_cycling_location, gps, interpolation, blending, countourFactor);

        circlesColorRed = 0;
        circlesColorGreen = 0;
        circlesColorBlue = 0;
        
        logBaseFinal = Math.log(2);
        
        circlesBlending = 1;
    }

    @Override
    public int getDomainColor(Complex res) {

        res = round(res);

        double norm = res.norm();
        
        int color = applyNormColor(norm);
        
        return applyCircles(color, norm, 0);
    }

}
