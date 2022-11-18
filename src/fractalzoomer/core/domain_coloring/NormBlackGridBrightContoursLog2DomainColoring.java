/*
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
public class NormBlackGridBrightContoursLog2DomainColoring extends DomainColoring {

    public NormBlackGridBrightContoursLog2DomainColoring(int domain_coloring_mode, PaletteColor palette, TransferFunction color_transfer, int color_cycling_location, GeneratedPaletteSettings gps, Blending blending, int interpolation, double countourFactor) {

        super(domain_coloring_mode, palette, color_transfer, color_cycling_location, gps, interpolation, blending, countourFactor);

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

        double norm = res.norm();
        
        int color = applyNormColor(norm);

        color = applyNormContours(color, norm);

        return applyGrid(color, res.getRe(), res.getIm());

    }

}
