
package fractalzoomer.main.app_settings;

import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.utils.QuadTree;

import java.awt.*;

/**
 *
 * @author kaloch
 */
public class FiltersSettings {
    public boolean[] filters;
    public int[] filters_options_vals;
    public int[][] filters_options_extra_vals;
    public int[] filters_order;
    public Color[] filters_colors;
    public Color[][] filters_extra_colors;
    public double aaSigmaR;
    public double bluringSigmaR;
    public double bluringSigmaS;
    public int blurringKernelSelection;
    public double quadtree_threshold;
    public boolean quadtree_show_division;
    public int quadtree_fill_algorithm;
    public int quadtree_lod;

    public int quadtree_algorithm;

    public boolean quadtree_dynamic_threshold;
    public int quadtree_dynamic_threshold_curve;

    public int quadtree_threshold_calculation;

    public boolean quadtree_merge_nodes;
    
    public FiltersSettings() {
        
        filters = new boolean[Constants.TOTAL_FILTERS];

        filters_options_vals = new int[filters.length];
        filters_options_extra_vals = new int[2][filters.length];
        filters_order = new int[filters.length - 1];
        
        filters_colors = new Color[filters.length];
        filters_extra_colors = new Color[2][filters.length];
     
        defaultFilters(true);
    }
    
    public void defaultFilters(boolean reset_checked) {

        aaSigmaR = 0;
        bluringSigmaR = 0;
        bluringSigmaS = 0;
        blurringKernelSelection = 0;
        quadtree_threshold = 8;
        quadtree_show_division = true;
        quadtree_fill_algorithm = 0;
        quadtree_lod = QuadTree.MAX_LEVEL;
        quadtree_algorithm = 0;
        quadtree_dynamic_threshold = true;
        quadtree_dynamic_threshold_curve = 0;
        quadtree_threshold_calculation = 0;
        quadtree_merge_nodes = false;

        for(int k = 0; k < filters_options_vals.length; k++) {

            if(reset_checked) {
                filters[k] = false;
            }
            
            filters_options_extra_vals[0][k] = 0;
            filters_options_extra_vals[1][k] = 0;
            filters_colors[k] = Color.BLACK;

            if(k == Constants.SPARKLE || k == Constants.COLOR_CHANNEL_MIXING || k == Constants.QUAD_TREE_COMPRESSION) {
                filters_colors[k] = Color.WHITE;
            }
            else {
                filters_colors[k] = Color.BLACK;
            }

            if(k == Constants.POSTERIZE) {
                filters_options_vals[k] = 128;
            }
            else if(k == Constants.COLOR_TEMPERATURE) {
                filters_options_vals[k] = 2000;
            }
            else if(k == Constants.CONTRAST_BRIGHTNESS) {
                filters_options_vals[k] = 100100;
            }
            else if(k == Constants.COLOR_CHANNEL_ADJUSTING) {
                filters_options_vals[k] = 50050050;
            }
            else if(k == Constants.COLOR_CHANNEL_HSB_ADJUSTING) {
                filters_options_vals[k] = 50050050;
            }
            else if(k == Constants.GAMMA) {
                filters_options_vals[k] = 33;
            }
            else if(k == Constants.EXPOSURE) {
                filters_options_vals[k] = 20;
            }
            else if(k == Constants.GAIN) {
                filters_options_vals[k] = 70070;
            }
            else if(k == Constants.DITHER) {
                filters_options_vals[k] = 4;
            }
            else if(k == Constants.BLURRING) {
                filters_options_vals[k] = 5;
            }
            else if(k == Constants.COLOR_CHANNEL_SWIZZLING) {
                filters_options_vals[k] = 1057;
            }
            else if(k == Constants.CRYSTALLIZE) {
                filters_options_vals[k] = 2300015;
            }
            else if(k == Constants.GLOW) {
                filters_options_vals[k] = 10020;
            }
            else if(k == Constants.COLOR_CHANNEL_SCALING) {
                filters_options_vals[k] = 20;
            }
            else if(k == Constants.POINTILLIZE) {
                filters_options_vals[k] = 230100015;
            }
            else if(k == Constants.MARBLE) {
                filters_options_vals[k] = 401010010;
            }
            else if(k == Constants.WEAVE) {
                filters_options_vals[k] = 1002020505;
            }
            else if(k == Constants.SPARKLE) {
                filters_options_vals[k] = 40400613;
            }
            else if(k == Constants.OIL) {
                filters_options_vals[k] = 25603;
            }
            else if(k == Constants.NOISE) {
                filters_options_vals[k] = 100025;
            }
            else if(k == Constants.EMBOSS) {
                filters_options_vals[k] = 8026300;
            }
            else if(k == Constants.MIRROR) {
                filters_options_vals[k] = 100050000;
            }
            else if(k == Constants.LIGHT_EFFECTS) {
                filters_options_vals[k] = 60260880;
                filters_options_extra_vals[0][k] = 40402640;
                filters_options_extra_vals[1][k] = 5404804;
                filters_colors[k] = Color.WHITE;
                filters_extra_colors[0][k] = new Color(0xff888888);
                filters_extra_colors[1][k] = Color.WHITE;
            }
            else if(k == Constants.QUAD_TREE_COMPRESSION) {
                filters_extra_colors[0][k] = Color.BLACK;
            }
            else {
                filters_options_vals[k] = 0;
            }
        }

        createDefaultFilterOrder(filters_order);
    }
    
    public void createDefaultFilterOrder(int[] filter_order) {
        
        filter_order[0] = MainWindow.CRYSTALLIZE;
        filter_order[1] = MainWindow.POINTILLIZE;
        filter_order[2] = MainWindow.LIGHT_EFFECTS;
        filter_order[3] = MainWindow.OIL;
        filter_order[4] = MainWindow.MARBLE;
        filter_order[5] = MainWindow.WEAVE;
        filter_order[6] = MainWindow.SPARKLE;
        filter_order[7] = MainWindow.COLOR_CHANNEL_SWAPPING;
        filter_order[8] = MainWindow.COLOR_CHANNEL_SWIZZLING;
        filter_order[9] = MainWindow.COLOR_CHANNEL_MIXING;
        filter_order[10] = MainWindow.COLOR_CHANNEL_ADJUSTING;
        filter_order[11] = MainWindow.COLOR_CHANNEL_HSB_ADJUSTING;
        filter_order[12] = MainWindow.COLOR_CHANNEL_SCALING;
        filter_order[13] = MainWindow.GRAYSCALE;
        filter_order[14] = MainWindow.POSTERIZE;
        filter_order[15] = MainWindow.DITHER;
        filter_order[16] = MainWindow.INVERT_COLORS;
        filter_order[17] = MainWindow.SOLARIZE;
        filter_order[18] = MainWindow.COLOR_TEMPERATURE;
        filter_order[19] = MainWindow.CONTRAST_BRIGHTNESS;
        filter_order[20] = MainWindow.GAIN;
        filter_order[21] = MainWindow.GAMMA;
        filter_order[22] = MainWindow.EXPOSURE;
        filter_order[23] = MainWindow.HISTOGRAM_EQUALIZATION;
        filter_order[24] = MainWindow.COLOR_CHANNEL_MASKING;
        filter_order[25] = MainWindow.NOISE;
        filter_order[26] = MainWindow.SHARPNESS;
        filter_order[27] = MainWindow.BLURRING;
        filter_order[28] = MainWindow.GLOW;
        filter_order[29] = MainWindow.EMBOSS;
        filter_order[30] = MainWindow.EDGE_DETECTION;
        filter_order[31] = MainWindow.EDGE_DETECTION2;
        filter_order[32] = MainWindow.FADE_OUT;
        filter_order[33] = MainWindow.MIRROR;
        filter_order[34] = MainWindow.QUAD_TREE_COMPRESSION;
        
    }
}
