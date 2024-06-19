
package fractalzoomer.core.domain_coloring;

import fractalzoomer.core.Complex;
import fractalzoomer.core.blending.Blending;
import fractalzoomer.core.norms.Norm;
import fractalzoomer.core.norms.NormN;
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

    private boolean applyShading;
    private boolean drawGrid;
    private boolean drawCircles;
    private boolean drawIsoLines;
    private int colorType;
    private int contourType;
    private int[] order;

    private int iso_color_type;
    private int grid_color_type;
    private int circle_color_type;

    private Norm normImpl;

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
        base = ds.logBase;

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

        normImpl = new NormN(ds.normType, ds.normA, ds.normB);
        
        combineType = ds.combineType;

        circle_color_type = ds.circle_color_type;
        grid_color_type = ds.grid_color_type;
        iso_color_type = ds.iso_color_type;

        applyShading = ds.applyShading;
        saturation_adjustment = ds.saturation_adjustment;
        mapNormReImWithAbsScale = ds.mapNormReImWithAbsScale;
        shadingType = ds.shadingType;
        shadingColorAlgorithm = ds.shadingColorAlgorithm;
        invertShading = ds.invertShading;
        shadingPercent = ds.shadingPercent;

    }

    @Override
    public int getDomainColor(Complex res) {

        res = round(res);

        int color = 0xffffffff;

        double norm = normImpl.computeWithRoot(res);

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

        if(applyShading) {
            color = applyShading(color, norm, re, im);
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
                        color = applyGrid(color, re, im, grid_color_type, norm);
                    }
                    break;
                case MainWindow.CIRCLES:
                    if(drawCircles) {
                        color = applyCircles(color, norm, circle_color_type);
                    }
                    break;
                case MainWindow.ISO_LINES:
                    if(drawIsoLines && contourType != 9 && contourType != 11) {
                        color = applyIsoLines(color, arg, iso_color_type, norm);
                    }
                    break;
            }
        }

        return color;
    }

}
