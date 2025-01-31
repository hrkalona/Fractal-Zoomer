
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
public class NormWhiteGridBlackCirclesLog2DomainColoring extends DomainColoring {

    public NormWhiteGridBlackCirclesLog2DomainColoring(int domain_coloring_mode, PaletteColor palette, TransferFunction color_transfer, int color_cycling_location, GeneratedPaletteSettings gps, Blending blending, int interpolation, double countourFactor, int color_space) {

        super(domain_coloring_mode, palette, color_transfer, color_cycling_location, gps, interpolation, blending, countourFactor, color_space);
        
        gridColorRed = 255;
        gridColorGreen = 255;
        gridColorBlue = 255;
        
        circlesColorRed = 0;
        circlesColorGreen = 0;
        circlesColorBlue = 0;
        
        logBaseFinal = Math.log(2);
        
        gridFactor = 2 * Math.PI;
        
        gridBlending = 1;
        
        circlesBlending = 1;
    }

    @Override
    public int getDomainColor(Complex res) {

        res = round(res);
        
        double norm = res.norm();
        
        int color = applyNormColor(norm);
        
        color = applyGrid(color, res.getRe(), res.getIm(), 0, 0);
        
        return applyCircles(color, norm, 0);
        
    }

}
