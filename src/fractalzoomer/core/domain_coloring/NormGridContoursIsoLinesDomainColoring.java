
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
public class NormGridContoursIsoLinesDomainColoring extends DomainColoring {

    public NormGridContoursIsoLinesDomainColoring(int domain_coloring_mode, PaletteColor palette, TransferFunction color_transfer, int color_cycling_location, GeneratedPaletteSettings gps, Blending blending, int interpolation, double countourFactor) {

        super(domain_coloring_mode, palette, color_transfer, color_cycling_location, gps, interpolation, blending, countourFactor);
        
        iso_distance = 1.0 / 12.0;
        iso_factor = 0.5;
        
        gradient = blackToWhite;
        
        contourBlending = 0.5;
        
        gridFactor = 2 * Math.PI;
        
        isoLinesBlendingFactor = 1;
        
        isoLinesColorRed = 255;
        isoLinesColorGreen = 255;
        isoLinesColorBlue = 255;
        
    }

    @Override
    public int getDomainColor(Complex res) {

        res = round(res);

        int color = applyNormColor(res.norm());
        
        color = applyGridContours(color, res.getRe(), res.getIm());  
        
        return applyIsoLines(color, res.arg(), 0, 0);

    }
    
}
