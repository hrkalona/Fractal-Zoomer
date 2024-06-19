
package fractalzoomer.main.app_settings;

import fractalzoomer.main.Constants;

import java.awt.*;

/**
 *
 * @author kaloch
 */
public class DomainColoringSettings {
    public boolean domain_coloring;
    public int domain_coloring_alg;
    public int domain_coloring_mode;
    public double logBase;
    public double normType;
    public double normA;
    public double normB;
    public double circlesBlending;
    public int iso_distance;
    public double iso_factor;
    public double isoLinesBlendingFactor;
    public double gridFactor;
    public double gridBlending;
    public Color gridColor;
    public Color circlesColor;
    public Color isoLinesColor;
    public double contourBlending;
    public boolean drawColor;
    public boolean drawContour;
    public boolean drawGrid;
    public boolean drawCircles;
    public boolean drawIsoLines;
    public boolean customDomainColoring;
    public int colorType;
    public int contourType;
    public int[] domainOrder;
    public int circleFadeFunction;
    public int gridFadeFunction;
    public double max_norm_re_im_value;
    public int contourMethod;
    public double domainProcessingHeightFactor;
    public int domainProcessingTransfer;
    public double circleWidth;
    public double gridWidth;
    public int gridAlgorithm;
    public int combineType;
    public int domain_height_method;
    public int domain_height_normalization_method;

    public int iso_color_type;
    public int grid_color_type;
    public int circle_color_type;

    public boolean applyShading;
    public double saturation_adjustment;
    public boolean mapNormReImWithAbsScale;
    public int shadingType;
    public int shadingColorAlgorithm;
    public boolean invertShading;
    public double shadingPercent;


    public DomainColoringSettings(DomainColoringSettings copy ) {
        domain_coloring = copy.domain_coloring;
        domain_coloring_alg = copy.domain_coloring_alg;
        domain_coloring_mode = copy.domain_coloring_mode;
        customDomainColoring = copy.customDomainColoring;
        
        logBase = copy.logBase;
        circlesBlending = copy.circlesBlending;
        normType = copy.normType;
        
        iso_distance = copy.iso_distance;
        iso_factor = copy.iso_factor;
        isoLinesBlendingFactor = copy.isoLinesBlendingFactor;
        
        gridFactor = copy.gridFactor;
        gridBlending = copy.gridBlending;
        
        gridColor = new Color(copy.gridColor.getRGB());
        circlesColor = new Color(copy.circlesColor.getRGB());
        isoLinesColor = new Color(copy.isoLinesColor.getRGB());
 
        contourBlending = copy.contourBlending;
        
        drawColor = copy.drawColor;
        drawContour = copy.drawContour;
        drawGrid = copy.drawGrid;
        drawCircles = copy.drawCircles;
        drawIsoLines = copy.drawIsoLines;
        
        colorType = copy.colorType;
        contourType = copy.contourType;
        
        domainOrder = copy.domainOrder;
        
        gridFadeFunction = copy.gridFadeFunction;
        circleFadeFunction = copy.circleFadeFunction;
        max_norm_re_im_value = copy.max_norm_re_im_value;
        contourMethod = copy.contourMethod;
        
        domainProcessingHeightFactor = copy.domainProcessingHeightFactor;
        domainProcessingTransfer = copy.domainProcessingTransfer;
        
        circleWidth = copy.circleWidth;
        gridWidth = copy.gridWidth;
        gridAlgorithm = copy.gridAlgorithm;
        
        combineType = copy.combineType;
        domain_height_method = copy.domain_height_method;

        iso_color_type = copy.iso_color_type;
        grid_color_type = copy.grid_color_type;
        circle_color_type = copy.circle_color_type;

        applyShading = copy.applyShading;
        saturation_adjustment = copy.saturation_adjustment;
        mapNormReImWithAbsScale = copy.mapNormReImWithAbsScale;
        shadingType = copy.shadingType;
        shadingColorAlgorithm = copy.shadingColorAlgorithm;
        domain_height_normalization_method = copy.domain_height_normalization_method;
        invertShading = copy.invertShading;
        shadingPercent = copy.shadingPercent;

        normA = copy.normA;
        normB = copy.normB;
    }
    
    public DomainColoringSettings() {
        
        domain_coloring = false;
        domain_coloring_alg = 0;
        domain_coloring_mode = 0;
        customDomainColoring = false;

        grid_color_type = 0;
        circle_color_type = 0;
        iso_color_type = 0;
        
        logBase = 2.0;
        normType = 2.0;
        circlesBlending = 1.0;
        
        iso_distance = 4;
        iso_factor = 0.5;
        isoLinesBlendingFactor = 1.0;
        
        gridFactor = 1.0;
        gridBlending = 1.0;
        
        gridColor = Color.BLACK;
        circlesColor = Color.WHITE;
        isoLinesColor = Color.WHITE;
        
        circleWidth = 1;
        gridWidth = 1;
 
        contourBlending = 0.5;
        
        drawColor = true;
        drawContour = true;
        drawGrid = true;
        drawCircles = false;
        drawIsoLines = true;
        
        colorType = 0;
        contourType = 0;
        
        domainOrder = new int[3];
        domainOrder[0] = Constants.GRID;
        domainOrder[1] = Constants.CIRCLES;
        domainOrder[2] = Constants.ISO_LINES;
        
        circleFadeFunction = 1;
        gridFadeFunction = 0;
        max_norm_re_im_value = 20;
        contourMethod = 3;
        
        domainProcessingHeightFactor = 10;
        domainProcessingTransfer = 1;
        
        gridAlgorithm = 0;
        combineType = 0;
        domain_height_method = 0;

        applyShading = false;
        saturation_adjustment = 6;
        mapNormReImWithAbsScale = true;
        shadingType = 0;
        shadingColorAlgorithm = 0;
        domain_height_normalization_method = 0;
        invertShading = false;
        shadingPercent = 0.1;

        normA = 1;
        normB = 1;
        
    }
    
}
