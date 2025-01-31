
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
public class BlackGridBrightContoursLog2DomainColoring extends DomainColoring {

    public BlackGridBrightContoursLog2DomainColoring(int domain_coloring_mode, PaletteColor palette, TransferFunction color_transfer, int color_cycling_location, GeneratedPaletteSettings gps, Blending blending, int interpolation, double countourFactor, int color_space) {

        super(domain_coloring_mode, palette, color_transfer, color_cycling_location, gps, interpolation, blending, countourFactor, color_space);
        
        gridColorRed = 0;
        gridColorGreen = 0;
        gridColorBlue = 0;
        
        gradient = blackToWhite;
        
        logBaseFinal = Math.log(2);
        
        contourBlending = 0.65;
        
        gridFactor = 2 * Math.PI;
        
        gridBlending = 1;
        
    }

    @Override
    public int getDomainColor(Complex res) {

        res = round(res);

        int color = applyArgColor(res.arg());
        
        color = applyNormContours(color, res.norm());
        
        return applyGrid(color, res.getRe(), res.getIm(), 0, 0);
        
    }

}
