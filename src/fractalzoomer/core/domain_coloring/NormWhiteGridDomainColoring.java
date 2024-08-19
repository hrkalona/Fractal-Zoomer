
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
public class NormWhiteGridDomainColoring extends DomainColoring {

    public NormWhiteGridDomainColoring(int domain_coloring_mode, PaletteColor palette, TransferFunction color_transfer, int color_cycling_location, GeneratedPaletteSettings gps, Blending blending, int interpolation, double countourFactor) {

        super(domain_coloring_mode, palette, color_transfer, color_cycling_location, gps, interpolation, blending, countourFactor);

        gridColorRed = 255;
        gridColorGreen = 255;
        gridColorBlue = 255;
        
        gridFactor = 2 * Math.PI;
        
        gridBlending = 1;
    }

    @Override
    public int getDomainColor(Complex res) {

        res = round(res);

        int color = applyNormColor(res.norm());
        
        return applyGrid(color, res.getRe(), res.getIm(), 0, 0);
        
    }

}
