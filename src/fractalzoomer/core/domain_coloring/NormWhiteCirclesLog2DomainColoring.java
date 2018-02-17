/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.core.domain_coloring;

import fractalzoomer.core.Complex;
import fractalzoomer.core.blending.Blending;
import fractalzoomer.main.MainWindow;
import fractalzoomer.palettes.Palette;
import fractalzoomer.palettes.transfer_functions.TransferFunction;

/**
 *
 * @author kaloch
 */
public class NormWhiteCirclesLog2DomainColoring extends DomainColoring {

    public NormWhiteCirclesLog2DomainColoring(boolean use_palette_domain_coloring, Palette palette, TransferFunction color_transfer, int color_cycling_location, Blending blending) {

        super(use_palette_domain_coloring, palette, color_transfer, color_cycling_location, MainWindow.INTERPOLATION_LINEAR, blending);

        circlesColorRed = 255;
        circlesColorGreen = 255;
        circlesColorBlue = 255;
        
        logBaseFinal = Math.log(2);
        
        circlesBlending = 1;
    }

    @Override
    public int getDomainColor(Complex res) {
        
        double norm = res.norm();
        
        int color = applyNormColor(norm);
        
        return applyCircles(color, norm);
    }

}
