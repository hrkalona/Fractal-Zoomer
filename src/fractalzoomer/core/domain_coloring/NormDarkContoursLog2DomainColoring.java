
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
public class NormDarkContoursLog2DomainColoring extends DomainColoring {

    public NormDarkContoursLog2DomainColoring(int domain_coloring_mode, PaletteColor palette, TransferFunction color_transfer, int color_cycling_location, GeneratedPaletteSettings gps, Blending blending, int interpolation, double countourFactor) {

        super(domain_coloring_mode, palette, color_transfer, color_cycling_location, gps, interpolation, blending, countourFactor);

        gradient = whiteToBlack;

        logBaseFinal = Math.log(2);

        contourBlending = 0.65;

    }

    @Override
    public int getDomainColor(Complex res) {

        res = round(res);

        double norm = res.norm();

        int color = applyNormColor(norm);

        return applyNormContours(color, norm);

    }

}
