
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
public class NormBlackGridContoursLog2IsoLinesDomainColoring extends DomainColoring {

    public NormBlackGridContoursLog2IsoLinesDomainColoring(int domain_coloring_mode, PaletteColor palette, TransferFunction color_transfer, int color_cycling_location, GeneratedPaletteSettings gps, Blending blending, int interpolation, double countourFactor, int color_space) {

        super(domain_coloring_mode, palette, color_transfer, color_cycling_location, gps, interpolation, blending, countourFactor, color_space);
       
        iso_distance = 1.0 / 12.0;
        iso_factor = 0.5;
        
        gridColorRed = 0;
        gridColorGreen = 0;
        gridColorBlue = 0;
        
        gridFactor = 2;
        
        gridBlending = 0.3;
        
        gradient = blackToWhite;
        
        isoLinesColorRed = 255;
        isoLinesColorGreen = 255;
        isoLinesColorBlue = 255;
        
        isoLinesBlendingFactor = 1;

        logBaseFinal = Math.log(2);

        contourBlending = 0.5;

    }

    @Override
    public int getDomainColor(Complex res) {

        res = round(res);

        double norm = res.norm();
        
        int color = applyNormColor(norm);
        
        color = applyNormContours(color, norm);    

        color = applyGrid(color, res.getRe(), res.getIm(), 0, 0);
        
        return applyIsoLines(color, res.arg(), 0, 0);

    }

}
