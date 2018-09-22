/*
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
import fractalzoomer.main.MainWindow;
import fractalzoomer.palettes.PaletteColor;
import fractalzoomer.palettes.transfer_functions.TransferFunction;

/**
 *
 * @author hrkalona2
 */
public class GridContoursIsoLinesDomainColoring extends DomainColoring {

    public GridContoursIsoLinesDomainColoring(boolean use_palette_domain_coloring, PaletteColor palette, TransferFunction color_transfer, int color_cycling_location, Blending blending) {

        super(use_palette_domain_coloring, palette, color_transfer, color_cycling_location, MainWindow.INTERPOLATION_LINEAR, blending);        
        
        iso_distance = 1.0 / 12.0;
        iso_factor = 0.5;
        
        gradient = blackToWhite;
        
        contourBlending = 0.5;
        
        gridFactor = 2 * Math.PI;
        
        isoLinesColorRed = 255;
        isoLinesColorGreen = 255;
        isoLinesColorBlue = 255;
        
        isoLinesBlendingFactor = 1;
        
    }

    @Override
    public int getDomainColor(Complex res) {
        
        double arg = res.arg();

        int color = applyArgColor(arg);
        
        color = applyGridContours(color, res.getRe(), res.getIm());  
        
        return applyIsoLines(color, arg);

    }
    
}