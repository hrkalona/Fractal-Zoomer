package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsFractals1093 extends SettingsFractals1092 implements Serializable {
    private static final long serialVersionUID = 32494714141159L;

    private double quadtree_threshold;
    private boolean quadtree_show_division;
    private int quadtree_fill_algorithm;
    private int quadtree_lod;

    private int quadtree_algorithm;

    private boolean quadtree_dynamic_threshold;
    private int quadtree_dynamic_threshold_curve;
    private boolean quadtree_merge_nodes;

    private int quadtree_threshold_calculation;
    private boolean split_into_rectagle_areas;
    private int rectangle_area_split_algorithm;
    private int area_dimension_x;
    private int area_dimension_y;

    public SettingsFractals1093(Settings s, boolean perturbation_theory, boolean greedy_drawing_algorithm, int brute_force_alg, int greedy_drawing_algorithm_id, boolean greedy_algorithm_check_iter_data, String userDefinedCode, int guess_blocks, int blocks_format, boolean two_step_refinement, boolean one_chunk_per_row, boolean split_into_rectagle_areas, int rectangle_area_split_algorithm, int area_dimension_x, int area_dimension_y) {
        super(s, perturbation_theory, greedy_drawing_algorithm, brute_force_alg, greedy_drawing_algorithm_id, greedy_algorithm_check_iter_data, userDefinedCode, guess_blocks, blocks_format, two_step_refinement,  one_chunk_per_row);
        this.quadtree_threshold = s.fs.quadtree_threshold;
        this.quadtree_show_division = s.fs.quadtree_show_division;
        this.quadtree_fill_algorithm = s.fs.quadtree_fill_algorithm;
        this.quadtree_lod = s.fs.quadtree_lod;
        this.quadtree_algorithm = s.fs.quadtree_algorithm;
        this.quadtree_dynamic_threshold = s.fs.quadtree_dynamic_threshold;
        this.quadtree_dynamic_threshold_curve = s.fs.quadtree_dynamic_threshold_curve;
        this.quadtree_threshold_calculation = s.fs.quadtree_threshold_calculation;
        this.quadtree_merge_nodes = s.fs.quadtree_merge_nodes;
        this.split_into_rectagle_areas = split_into_rectagle_areas;
        this.rectangle_area_split_algorithm = rectangle_area_split_algorithm;
        this.area_dimension_x = area_dimension_x;
        this.area_dimension_y = area_dimension_y;
    }

    @Override
    public int getVersion() {

        return 1093;

    }

    public double getQuadtreeThreshold() {
        return quadtree_threshold;
    }

    public boolean getQuadtreeShowDivision() {
        return quadtree_show_division;
    }

    public int getQuadtreeFillAlgorithm() {
        return quadtree_fill_algorithm;
    }

    public int getQuadtreeLod() {
        return quadtree_lod;
    }

    public int getQuadtreeAlgorithm() {
        return quadtree_algorithm;
    }

    public boolean getQuadtreeDynamicThreshold() {
        return quadtree_dynamic_threshold;
    }

    public int getQuadtreeDynamicThresholdCurve() {
        return quadtree_dynamic_threshold_curve;
    }

    public int getQuadtreeThresholdCalculation() {
        return quadtree_threshold_calculation;
    }

    public boolean getSplitIntoRectagleAreas() {
        return split_into_rectagle_areas;
    }

    public int getRectangleAreaSplitAlgorithm() {
        return rectangle_area_split_algorithm;
    }

    public int getAreaDimensionX() {
        return area_dimension_x;
    }

    public int getAreaDimensionY() {
        return area_dimension_y;
    }

    public boolean getQuadtreeMergeNodes() {
        return quadtree_merge_nodes;
    }

}
