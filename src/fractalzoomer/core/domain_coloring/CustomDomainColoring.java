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
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.DomainColoringSettings;
import fractalzoomer.main.app_settings.GeneratedPaletteSettings;
import fractalzoomer.palettes.PaletteColor;
import fractalzoomer.palettes.transfer_functions.TransferFunction;

/**
 *
 * @author hrkalona2
 */
public class CustomDomainColoring extends DomainColoring {

    private boolean drawColor;
    private boolean drawContour;
    private boolean drawGrid;
    private boolean drawCircles;
    private boolean drawIsoLines;
    private int colorType;
    private int contourType;
    private double normType;
    private double normTypeReciprocal;
    private int[] order;

    public CustomDomainColoring(DomainColoringSettings ds, PaletteColor palette, TransferFunction color_transfer, int color_cycling_location, GeneratedPaletteSettings gps, Blending blending, int[] gradient, int interpolation, int gradient_offset, double countourFactor) {

        super(ds.domain_coloring_mode, palette, color_transfer, color_cycling_location, gps, interpolation, blending, countourFactor);

        circlesBlending = ds.circlesBlending;
        gridBlending = ds.gridBlending;
        isoLinesBlendingFactor = ds.isoLinesBlendingFactor;
        contourBlending = ds.contourBlending;

        circlesColorRed = ds.circlesColor.getRed();
        circlesColorGreen = ds.circlesColor.getGreen();
        circlesColorBlue = ds.circlesColor.getBlue();

        gridColorRed = ds.gridColor.getRed();
        gridColorGreen = ds.gridColor.getGreen();
        gridColorBlue = ds.gridColor.getBlue();

        isoLinesColorRed = ds.isoLinesColor.getRed();
        isoLinesColorGreen = ds.isoLinesColor.getGreen();
        isoLinesColorBlue = ds.isoLinesColor.getBlue();
        
        contourMethod = ds.contourMethod;

        this.gradient = gradient;
        this.gradient_offset = gradient_offset;

        gridFactor = (Math.PI / ds.gridFactor);
        logBaseFinal = Math.log(ds.logBase);

        order = ds.domainOrder;
        
        circleFadeFunction = ds.circleFadeFunction;
        gridFadeFunction = ds.gridFadeFunction;
        max_norm_re_im_value = ds.max_norm_re_im_value;
        
        circleWidth = ds.circleWidth;
        gridWidth = ds.gridWidth;

        gridAlgorithm = ds.gridAlgorithm;

        switch (ds.iso_distance) {
            case 0: //2pi
                iso_distance = 1;
                break;
            case 1: //pi
                iso_distance = Math.PI / (2 * Math.PI);
                break;
            case 2: //pi/2
                iso_distance = Math.PI / 2 / (2 * Math.PI);
                break;
            case 3: //pi/4
                iso_distance = Math.PI / 4 / (2 * Math.PI);
                break;
            case 4: //pi/6
                iso_distance = Math.PI / 6 / (2 * Math.PI);
                break;
            case 5: //pi/8
                iso_distance = Math.PI / 8 / (2 * Math.PI);
                break;
            case 6: //pi/10
                iso_distance = Math.PI / 10 / (2 * Math.PI);
                break;
            case 7: //pi/12
                iso_distance = Math.PI / 12 / (2 * Math.PI);
                break;
            case 8: //pi/14
                iso_distance = Math.PI / 14 / (2 * Math.PI);
                break;
            case 9: //pi/16
                iso_distance = Math.PI / 16 / (2 * Math.PI);
                break;

        }
        iso_factor = ds.iso_factor;

        drawColor = ds.drawColor;
        drawContour = ds.drawContour;
        drawGrid = ds.drawGrid;
        drawCircles = ds.drawCircles;
        drawIsoLines = ds.drawIsoLines;

        colorType = ds.colorType;
        contourType = ds.contourType;

        normType = ds.normType;
        normTypeReciprocal = 1 / normType;
        
        combineType = ds.combineType;

    }

    @Override
    public int getDomainColor(Complex res) {

        res = round(res);

        int color = 0xffffffff;

        double norm = 0;

        if(normType == 2) {
            norm = res.norm();
        }
        else {
            norm = res.nnorm(normType, normTypeReciprocal);
        }

        double arg = res.arg();
        double re = res.getRe();
        double im = res.getIm();

        if(drawColor) {
            switch (colorType) {
                case 0:
                    color = applyArgColor(arg);
                    break;
                case 1:
                    color = applyNormColor(norm);
                    break;
                case 2:
                    color = applyReColor(re);
                    break;
                case 3:
                    color = applyImColor(im);
                    break;
            }
        }

        if(drawContour) {

            switch (contourType) {
                case 0:
                    color = applyNormContours(color, norm);
                    break;
                case 1:
                    color = applyArgContours(color, arg);
                    break;
                case 2:
                    color = applyNormArgContours(color, norm, arg);
                    break;
                case 3:
                    color = applyGridContours(color, re, im);
                    break;
                case 4:
                    color = applyGridNormContours(color, re, im, norm);
                    break;
                case 5:
                    color = applyGridArgContours(color, re, im, arg);
                    break;
                case 6:
                    color = applyGridNormArgContours(color, re, im, norm, arg);
                    break;
                case 7:
                    color = applyGridLinesContours(color, re, im);
                    break;
                case 8:
                    color = applyCirclesLinesContours(color, norm);
                    break;
                case 9:
                    color = applyIsoLinesContours(color, arg);
                    break;
                case 10:
                    color = applyGridLinesCirclesLinesContours(color, re, im, norm);
                    break;
                case 11:
                    color = applyCirclesLinesIsoLinesContours(color, norm, arg);
                    break;
            }
        }

        for(int i = 0; i < order.length; i++) {
            switch (order[i]) {
                case MainWindow.GRID:
                    if(drawGrid) {
                        color = applyGrid(color, re, im);
                    }
                    break;
                case MainWindow.CIRCLES:
                    if(drawCircles) {
                        color = applyCircles(color, norm);
                    }
                    break;
                case MainWindow.ISO_LINES:
                    if(drawIsoLines && contourType != 9 && contourType != 11) {
                        color = applyIsoLines(color, arg);
                    }
                    break;
            }
        }

        return color;
    }

}
