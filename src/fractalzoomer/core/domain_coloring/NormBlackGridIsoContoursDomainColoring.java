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
import fractalzoomer.palettes.Palette;
import fractalzoomer.palettes.transfer_functions.TransferFunction;

/**
 *
 * @author hrkalona2
 */
public class NormBlackGridIsoContoursDomainColoring extends DomainColoring {

    public NormBlackGridIsoContoursDomainColoring(boolean use_palette_domain_coloring, Palette palette, TransferFunction color_transfer, int color_cycling_location, Blending blending) {

        super(use_palette_domain_coloring, palette, color_transfer, color_cycling_location, MainWindow.INTERPOLATION_LINEAR, blending);
  
        iso_distance = 1.0 / 12.0;
        iso_factor = 0.5;
        
        gridColorRed = 0;
        gridColorGreen = 0;
        gridColorBlue = 0;
        
        gridFactor = 2;
        
        gridBlending = 0.4;
        
        gradient = blackToWhite;

        contourBlending = 0.5;
        
    }

    @Override
    public int getDomainColor(Complex res) {
        
        int color = applyNormColor(res.norm());
        
        color = applyArgContours(color, res.arg());    

        return applyGrid(color, res.getRe(), res.getIm());  

    }
    
}
