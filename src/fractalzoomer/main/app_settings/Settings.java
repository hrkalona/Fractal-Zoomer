/*
 * Fractal Zoomer, Copyright (C) 2019 hrkalona2
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
package fractalzoomer.main.app_settings;

import fractalzoomer.bailout_conditions.SkipBailoutCondition;
import fractalzoomer.core.Complex;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.main.Constants;
import fractalzoomer.palettes.CustomPalette;
import fractalzoomer.palettes.PresetPalette;
import fractalzoomer.parser.Parser;
import java.awt.Color;
import fractalzoomer.settings.*;
import fractalzoomer.utils.ColorAlgorithm;
import java.awt.Component;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author hrkalona2
 */
public class Settings implements Constants {

    public PaletteSettings ps;
    public PaletteSettings ps2;
    public boolean exterior_de;
    public double exterior_de_factor;
    public boolean polar_projection;
    public boolean inverse_dem;
    public DomainColoringSettings ds;
    public double xCenter;
    public double yCenter;
    public double xJuliaCenter;
    public double yJuliaCenter;
    public double size;
    public double height_ratio;
    public int max_iterations;
    public double circle_period;
    public int color_smoothing_method;
    public Color fractal_color;
    public Color dem_color;
    public Color special_color;
    public boolean special_use_palette_color;
    public int color_blending;
    public int temp_color_cycling_location;
    public int temp_color_cycling_location_second_palette;
    public D3Settings d3s;
    public BumpMapSettings bms;
    public EntropyColoringSettings ens;
    public RainbowPaletteSettings rps;
    public OffsetColoringSettings ofs;
    public GreyscaleColoringSettings gss;
    public FakeDistanceEstimationSettings fdes;
    public FiltersSettings fs;
    public GradientSettings gs;
    public OrbitTrapSettings ots;
    public ContourColoringSettings cns;
    public FunctionSettings fns;
    public LightSettings ls;
    public PaletteGradientMergingSettings pbs;
    public StatisticsSettings sts;
    public boolean usePaletteForInColoring;
    public int[] post_processing_order;
    public String poly;
    public boolean userFormulaHasC;
    public Parser parser;
    public boolean useDirectColor;
    public boolean globalIncrementBypass;

    public Settings() {
        defaultValues();
    }

    public void defaultValues() {

        parser = new Parser(true);
        userFormulaHasC = true;
        useDirectColor = false;
        globalIncrementBypass = false;

        xCenter = 0;
        yCenter = 0;

        size = 6;

        height_ratio = 1;
        max_iterations = 500;

        fns = new FunctionSettings();
        ds = new DomainColoringSettings();
        bms = new BumpMapSettings();
        ens = new EntropyColoringSettings();
        rps = new RainbowPaletteSettings();
        ofs = new OffsetColoringSettings();
        gss = new GreyscaleColoringSettings();
        fdes = new FakeDistanceEstimationSettings();
        fs = new FiltersSettings();
        gs = new GradientSettings();
        ots = new OrbitTrapSettings();
        cns = new ContourColoringSettings();
        ls = new LightSettings();
        pbs = new PaletteGradientMergingSettings();
        sts = new StatisticsSettings();

        ps = new PaletteSettings();
        ps2 = new PaletteSettings();
        usePaletteForInColoring = false;

        color_blending = NORMAL_BLENDING;

        temp_color_cycling_location = ps.color_cycling_location;
        temp_color_cycling_location_second_palette = ps2.color_cycling_location;

        exterior_de = false;
        exterior_de_factor = 1;
        inverse_dem = false;

        polar_projection = false;
        circle_period = 1;

        color_smoothing_method = INTERPOLATION_LINEAR;

        special_use_palette_color = true;

        fractal_color = Color.BLACK;
        dem_color = new Color(1, 0, 0);
        special_color = Color.WHITE;

        d3s = new D3Settings();

        post_processing_order = new int[TOTAL_POST_PROCESS_ALGORITHMS];
        defaultProcessingOrder();

    }

    private void defaultProcessingOrder() {

        post_processing_order[0] = OFFSET_COLORING;
        post_processing_order[1] = ENTROPY_COLORING;
        post_processing_order[2] = RAINBOW_PALETTE;
        post_processing_order[3] = CONTOUR_COLORING;
        post_processing_order[4] = GREYSCALE_COLORING;
        post_processing_order[5] = BUMP_MAPPING;
        post_processing_order[6] = LIGHT;
        post_processing_order[7] = FAKE_DISTANCE_ESTIMATION;

    }

    public void readSettings(String filename, Component parent, boolean silent) throws FileNotFoundException, IOException, ClassNotFoundException, Exception {

        ObjectInputStream file_temp = new ObjectInputStream(new FileInputStream(filename));

        SettingsFractals settings = (SettingsFractals) file_temp.readObject();

        int version = settings.getVersion();

        Settings defaults = new Settings();

        if (settings.isJulia()) {
            if (version == 1048) {
                xJuliaCenter = ((SettingsJulia) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia) settings).getYJuliaCenter();
            } else if (version == 1049) {
                xJuliaCenter = ((SettingsJulia1049) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1049) settings).getYJuliaCenter();
            } else if (version == 1050) {
                xJuliaCenter = ((SettingsJulia1050) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1050) settings).getYJuliaCenter();
            } else if (version == 1053) {
                xJuliaCenter = ((SettingsJulia1053) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1053) settings).getYJuliaCenter();
            } else if (version == 1054) {
                xJuliaCenter = ((SettingsJulia1054) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1054) settings).getYJuliaCenter();
            } else if (version == 1055) {
                xJuliaCenter = ((SettingsJulia1055) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1055) settings).getYJuliaCenter();
            } else if (version == 1056) {
                xJuliaCenter = ((SettingsJulia1056) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1056) settings).getYJuliaCenter();
            } else if (version == 1057) {
                xJuliaCenter = ((SettingsJulia1057) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1057) settings).getYJuliaCenter();
            } else if (version == 1058) {
                xJuliaCenter = ((SettingsJulia1058) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1058) settings).getYJuliaCenter();
            } else if (version == 1061) {
                xJuliaCenter = ((SettingsJulia1061) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1061) settings).getYJuliaCenter();
            } else if (version == 1062) {
                xJuliaCenter = ((SettingsJulia1062) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1062) settings).getYJuliaCenter();
            } else if (version == 1063) {
                xJuliaCenter = ((SettingsJulia1063) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1063) settings).getYJuliaCenter();
            } else if (version == 1064) {
                xJuliaCenter = ((SettingsJulia1064) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1064) settings).getYJuliaCenter();
            } else if (version == 1065) {
                xJuliaCenter = ((SettingsJulia1065) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1065) settings).getYJuliaCenter();
            } else if (version == 1066) {
                xJuliaCenter = ((SettingsJulia1066) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1066) settings).getYJuliaCenter();
            } else if (version == 1067) {
                xJuliaCenter = ((SettingsJulia1067) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1067) settings).getYJuliaCenter();
            } else if (version == 1068) {
                xJuliaCenter = ((SettingsJulia1068) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1068) settings).getYJuliaCenter();
            } else if (version == 1069) {
                xJuliaCenter = ((SettingsJulia1069) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1069) settings).getYJuliaCenter();
            } else if (version == 1070) {
                xJuliaCenter = ((SettingsJulia1070) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1070) settings).getYJuliaCenter();
            } else if (version == 1071) {
                xJuliaCenter = ((SettingsJulia1071) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1071) settings).getYJuliaCenter();
            } else if (version == 1072) {
                xJuliaCenter = ((SettingsJulia1072) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1072) settings).getYJuliaCenter();
            } else if (version == 1073) {
                xJuliaCenter = ((SettingsJulia1073) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1073) settings).getYJuliaCenter();
            } else if (version == 1074) {
                xJuliaCenter = ((SettingsJulia1074) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1074) settings).getYJuliaCenter();
            }

            fns.julia = true;

            fns.perturbation = false;
            fns.init_val = false;

        } else {
            fns.julia = false;

            fns.perturbation = settings.getPerturbation();
            fns.init_val = settings.getInitVal();

            if (fns.perturbation) {
                if (version < 1056) {
                    fns.variable_perturbation = defaults.fns.variable_perturbation;
                    fns.perturbation_vals = settings.getPerturbationVals();
                } else {
                    fns.variable_perturbation = ((SettingsFractals1056) settings).getVariablePerturbation();

                    if (fns.variable_perturbation) {
                        if (version < 1058) {
                            fns.user_perturbation_algorithm = defaults.fns.user_perturbation_algorithm;
                        } else {
                            fns.user_perturbation_algorithm = ((SettingsFractals1058) settings).getUserPerturbationAlgorithm();
                        }

                        if (fns.user_perturbation_algorithm == 0) {
                            fns.perturbation_user_formula = ((SettingsFractals1056) settings).getPerturbationUserFormula();
                        } else {
                            fns.user_perturbation_conditions = ((SettingsFractals1058) settings).getUserPerturbationConditions();
                            fns.user_perturbation_condition_formula = ((SettingsFractals1058) settings).getUserPerturbationConditionFormula();
                        }
                    } else {
                        fns.perturbation_vals = settings.getPerturbationVals();
                    }
                }
            }

            if (fns.init_val) {
                if (version < 1056) {
                    fns.variable_init_value = defaults.fns.variable_init_value;
                    fns.initial_vals = settings.getInitialVals();
                } else {
                    fns.variable_init_value = ((SettingsFractals1056) settings).getVariableInitValue();

                    if (fns.variable_init_value) {
                        if (version < 1058) {
                            fns.user_initial_value_algorithm = defaults.fns.user_initial_value_algorithm;
                        } else {
                            fns.user_initial_value_algorithm = ((SettingsFractals1058) settings).getUserInitialValueAlgorithm();
                        }

                        if (fns.user_initial_value_algorithm == 0) {
                            fns.initial_value_user_formula = ((SettingsFractals1056) settings).getInitialValueUserFormula();
                        } else {
                            fns.user_initial_value_conditions = ((SettingsFractals1058) settings).getUserInitialValueConditions();
                            fns.user_initial_value_condition_formula = ((SettingsFractals1058) settings).getUserInitialValueConditionFormula();
                        }
                    } else {
                        fns.initial_vals = settings.getInitialVals();
                    }
                }
            }
        }

        xCenter = settings.getXCenter();
        yCenter = settings.getYCenter();
        size = settings.getSize();
        max_iterations = settings.getMaxIterations();
        ps.color_choice = settings.getColorChoice();

        fractal_color = settings.getFractalColor();

        if (version < 1061) {
            dem_color = defaults.dem_color;
            special_color = defaults.special_color;
            special_use_palette_color = defaults.special_use_palette_color;
        } else {
            dem_color = ((SettingsFractals1061) settings).getDemColor();
            special_color = ((SettingsFractals1061) settings).getSpecialColor();
            special_use_palette_color = ((SettingsFractals1061) settings).getSpecialUsePaletteColor();
        }

        if (version < 1062) {
            rps.rainbow_palette = defaults.rps.rainbow_palette;
            rps.rainbow_palette_factor = defaults.rps.rainbow_palette_factor;

            boolean active_filter = false;
            for (int i = 0; i < fs.filters.length; i++) {
                if (fs.filters[i]) {
                    active_filter = true;
                    break;
                }
            }

            if (active_filter) {
                int ans = JOptionPane.showConfirmDialog(parent, "Some image filters are activated.\nDo you want to reset them to the default values?", "Image Filters", JOptionPane.YES_NO_OPTION);
                if (ans == JOptionPane.YES_OPTION) {
                    fs.defaultFilters(true);
                }
            }
        } else {
            rps.rainbow_palette = ((SettingsFractals1062) settings).getRainbowPalette();
            rps.rainbow_palette_factor = ((SettingsFractals1062) settings).getRainbowPaletteFactor();

            fs.defaultFilters(true);

            boolean[] loaded_filters = ((SettingsFractals1062) settings).getFilters();

            int[] temp_vals = ((SettingsFractals1062) settings).getFiltersOptionsVals();

            Color[] temp_colors;
            int[][] temp_extra_vals;
            Color[][] temp_extra_colors;
            int[] temp_filters_order;

            if (version < 1063) {
                temp_colors = fs.filters_colors;
            } else {
                temp_colors = ((SettingsFractals1063) settings).getFiltersColors();
            }

            if (version < 1064) {
                temp_extra_vals = fs.filters_options_extra_vals;
                temp_extra_colors = fs.filters_extra_colors;
            } else {
                temp_extra_vals = ((SettingsFractals1064) settings).getFilterExtraVals();
                temp_extra_colors = ((SettingsFractals1064) settings).getFilterExtraColors();
            }

            if (version < 1065) {
                temp_filters_order = fs.filters_order;
            } else {
                temp_filters_order = ((SettingsFractals1065) settings).getFiltersOrder();
            }

            for (int i = 0; i < loaded_filters.length; i++) {
                if (loaded_filters[i]) {
                    fs.filters[i] = loaded_filters[i];
                    fs.filters_colors[i] = temp_colors[i];

                    if (i == EMBOSS && version == 1062) {
                        fs.filters_options_vals[i] = fs.filters_options_vals[i] + temp_vals[i] % 10;
                    } else {
                        fs.filters_options_vals[i] = temp_vals[i];
                    }

                    fs.filters_options_extra_vals[0][i] = temp_extra_vals[0][i];
                    fs.filters_options_extra_vals[1][i] = temp_extra_vals[1][i];
                    fs.filters_extra_colors[0][i] = temp_extra_colors[0][i];
                    fs.filters_extra_colors[1][i] = temp_extra_colors[1][i];
                }
            }

            if (temp_filters_order.length == fs.filters_order.length) {
                for (int i = 0; i < temp_filters_order.length; i++) {
                    fs.filters_order[i] = temp_filters_order[i];
                }
            } else if (fs.filters_order.length > temp_filters_order.length) {
                int[] filters_order_union = new int[fs.filters_order.length];

                for (int i = 0; i < filters_order_union.length; i++) {
                    filters_order_union[i] = -1;
                }

                for (int i = 0; i < temp_filters_order.length; i++) {
                    filters_order_union[i] = temp_filters_order[i];
                }

                int k = temp_filters_order.length;
                for (int i = 0; i < fs.filters_order.length; i++) {//add all the missing filters to the end
                    boolean found = false;
                    for (int j = 0; j < filters_order_union.length; j++) {
                        if (filters_order_union[j] == fs.filters_order[i]) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        filters_order_union[k] = fs.filters_order[i];
                        k++;
                    }
                }

                for (int i = 0; i < filters_order_union.length; i++) {
                    fs.filters_order[i] = filters_order_union[i];
                }
            }
        }

        fns.function = settings.getFunction();
        fns.bailout = settings.getBailout();
        fns.burning_ship = settings.getBurningShip();

        fns.mandel_grass = settings.getMandelGrass();
        fns.mandel_grass_vals = settings.getMandelGrassVals();

        ps.color_cycling_location = settings.getColorCyclingLocation();
        fns.plane_type = settings.getPlaneType();

        if (version < 1057) {
            fns.apply_plane_on_julia = defaults.fns.apply_plane_on_julia;
        } else {
            fns.apply_plane_on_julia = ((SettingsFractals1057) settings).getApplyPlaneOnJulia();
        }

        if (version < 1069) {
            cns.contour_coloring = defaults.cns.contour_coloring;
            cns.cn_noise_reducing_factor = defaults.cns.cn_noise_reducing_factor;
            cns.cn_blending = defaults.cns.cn_blending;
            cns.contour_algorithm = defaults.cns.contour_algorithm;

            useDirectColor = defaults.useDirectColor;

            fns.kleinianLine[0] = defaults.fns.kleinianLine[0];
            fns.kleinianLine[1] = defaults.fns.kleinianLine[1];
            fns.kleinianK = defaults.fns.kleinianK;
            fns.kleinianM = defaults.fns.kleinianM;

            ots.lineType = defaults.ots.lineType;
        } else {
            cns.contour_coloring = ((SettingsFractals1069) settings).getContourColoring();
            cns.cn_noise_reducing_factor = ((SettingsFractals1069) settings).getContourColoringNoiseReducingFactor();
            cns.cn_blending = ((SettingsFractals1069) settings).getContourColoringBlending();
            cns.contour_algorithm = ((SettingsFractals1069) settings).getContourColoringAlgorithm();

            useDirectColor = ((SettingsFractals1069) settings).getDirectColor();

            fns.kleinianLine = ((SettingsFractals1069) settings).getKleinianLine();
            fns.kleinianK = ((SettingsFractals1069) settings).getKleinianK();
            fns.kleinianM = ((SettingsFractals1069) settings).getKleinianM();

            ots.lineType = ((SettingsFractals1069) settings).getLineType();
        }

        if (version < 1070) {
            defaultProcessingOrder();
        } else {
            defaultProcessingOrder();

            int[] temp_post_processing_order = ((SettingsFractals1070) settings).getPostProcessingOrder();

            if (temp_post_processing_order.length == post_processing_order.length) {
                for (int i = 0; i < temp_post_processing_order.length; i++) {
                    post_processing_order[i] = temp_post_processing_order[i];
                }
            } else if (post_processing_order.length > temp_post_processing_order.length) {
                int[] post_processing_order_union = new int[post_processing_order.length];

                for (int i = 0; i < post_processing_order_union.length; i++) {
                    post_processing_order_union[i] = -1;
                }

                for (int i = 0; i < temp_post_processing_order.length; i++) {
                    post_processing_order_union[i] = temp_post_processing_order[i];
                }

                int k = temp_post_processing_order.length;
                for (int i = 0; i < post_processing_order.length; i++) {//add all the missing post processing to the end
                    boolean found = false;
                    for (int j = 0; j < post_processing_order_union.length; j++) {
                        if (post_processing_order_union[j] == post_processing_order[i]) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        post_processing_order_union[k] = post_processing_order[i];
                        k++;
                    }
                }

                for (int i = 0; i < post_processing_order_union.length; i++) {
                    post_processing_order[i] = post_processing_order_union[i];
                }
            }
        }

        if (version < 1068) {
            ens.entropy_algorithm = defaults.ens.entropy_algorithm;

            ds.domainOrder[0] = defaults.ds.domainOrder[0];
            ds.domainOrder[1] = defaults.ds.domainOrder[1];
            ds.domainOrder[2] = defaults.ds.domainOrder[2];

            gs.colorA = defaults.gs.colorA;
            gs.colorB = defaults.gs.colorB;
            gs.gradient_color_space = defaults.gs.gradient_color_space;
            gs.gradient_interpolation = defaults.gs.gradient_interpolation;
            gs.gradient_reversed = defaults.gs.gradient_reversed;

            rps.rainbow_algorithm = defaults.rps.rainbow_algorithm;

            ots.useTraps = defaults.ots.useTraps;
            ots.trapType = defaults.ots.trapType;
            ots.trapPoint[0] = defaults.ots.trapPoint[0];
            ots.trapPoint[1] = defaults.ots.trapPoint[1];
            ots.trapLength = defaults.ots.trapLength;
            ots.trapWidth = defaults.ots.trapWidth;
            ots.trapBlending = defaults.ots.trapBlending;
            ots.trapNorm = defaults.ots.trapNorm;
            ots.trapMaxDistance = defaults.ots.trapMaxDistance;
        } else {
            ens.entropy_algorithm = ((SettingsFractals1068) settings).getEntropyAlgorithm();

            ds.domainOrder = ((SettingsFractals1068) settings).getDomainOrder();

            gs.colorA = ((SettingsFractals1068) settings).getColorA();
            gs.colorB = ((SettingsFractals1068) settings).getColorB();
            gs.gradient_color_space = ((SettingsFractals1068) settings).getGradientColorSpace();
            gs.gradient_interpolation = ((SettingsFractals1068) settings).getGradientInterpolation();
            gs.gradient_reversed = ((SettingsFractals1068) settings).getGradientReversed();

            rps.rainbow_algorithm = ((SettingsFractals1068) settings).getRainbowAlgorithm();

            ots.useTraps = ((SettingsFractals1068) settings).getUseTraps();
            ots.trapType = ((SettingsFractals1068) settings).getTrapType();
            ots.trapPoint = ((SettingsFractals1068) settings).getTrapPoint();
            ots.trapLength = ((SettingsFractals1068) settings).getTrapLength();
            ots.trapWidth = ((SettingsFractals1068) settings).getTrapWidth();
            ots.trapBlending = ((SettingsFractals1068) settings).getTrapBlending();
            ots.trapNorm = ((SettingsFractals1068) settings).getTrapNorm();
            ots.trapMaxDistance = ((SettingsFractals1068) settings).getTrapMaxDistance();
        }

        if (version < 1067) {
            ps.transfer_function = defaults.ps.transfer_function;
            color_blending = defaults.color_blending;

            bms.bump_transfer_function = defaults.bms.bump_transfer_function;
            bms.bump_transfer_factor = defaults.bms.bump_transfer_factor;
            bms.bump_blending = defaults.bms.bump_blending;
            bms.bumpProcessing = defaults.bms.bumpProcessing;

            globalIncrementBypass = defaults.globalIncrementBypass;

            fns.waveType = defaults.fns.waveType;
            fns.plane_transform_wavelength[0] = defaults.fns.plane_transform_wavelength[0];
            fns.plane_transform_wavelength[1] = defaults.fns.plane_transform_wavelength[1];

            ds.iso_distance = defaults.ds.iso_distance;
            ds.iso_factor = defaults.ds.iso_factor;
            ds.logBase = defaults.ds.logBase;
            ds.normType = defaults.ds.normType;
            ds.gridFactor = defaults.ds.gridFactor;
            ds.circlesBlending = defaults.ds.circlesBlending;
            ds.isoLinesBlendingFactor = defaults.ds.isoLinesBlendingFactor;
            ds.gridBlending = defaults.ds.gridBlending;
            ds.gridColor = defaults.ds.gridColor;
            ds.circlesColor = defaults.ds.circlesColor;
            ds.isoLinesColor = defaults.ds.isoLinesColor;
            ds.contourBlending = defaults.ds.contourBlending;
            ds.drawColor = defaults.ds.drawColor;
            ds.drawContour = defaults.ds.drawContour;
            ds.drawGrid = defaults.ds.drawGrid;
            ds.drawCircles = defaults.ds.drawCircles;
            ds.drawIsoLines = defaults.ds.drawIsoLines;
            ds.customDomainColoring = defaults.ds.customDomainColoring;
            ds.colorType = defaults.ds.colorType;
            ds.contourType = defaults.ds.contourType;
        } else {
            ps.transfer_function = ((SettingsFractals1067) settings).getTransferFunction();
            color_blending = ((SettingsFractals1067) settings).getColorBlending();

            bms.bump_transfer_function = ((SettingsFractals1067) settings).getBumpTransferFunction();
            bms.bump_transfer_factor = ((SettingsFractals1067) settings).getBumpTransferFactor();
            bms.bump_blending = ((SettingsFractals1067) settings).getBumpBlending();
            bms.bumpProcessing = ((SettingsFractals1067) settings).getBumpProcessing();

            globalIncrementBypass = ((SettingsFractals1067) settings).getGlobalIncrementBypass();

            fns.waveType = ((SettingsFractals1067) settings).getWaveType();
            fns.plane_transform_wavelength = ((SettingsFractals1067) settings).getPlaneTransformWavelength();

            ds.iso_distance = ((SettingsFractals1067) settings).getIsoDistance();
            ds.iso_factor = ((SettingsFractals1067) settings).getIsoFactor();
            ds.logBase = ((SettingsFractals1067) settings).getLogBase();
            ds.normType = ((SettingsFractals1067) settings).getNormType();
            ds.gridFactor = ((SettingsFractals1067) settings).getGridFactor();
            ds.circlesBlending = ((SettingsFractals1067) settings).getCirclesBlending();
            ds.isoLinesBlendingFactor = ((SettingsFractals1067) settings).getIsoLinesBlendingFactor();
            ds.gridBlending = ((SettingsFractals1067) settings).getGridBlending();
            ds.gridColor = ((SettingsFractals1067) settings).getGridColor();
            ds.circlesColor = ((SettingsFractals1067) settings).getCirclesColor();
            ds.isoLinesColor = ((SettingsFractals1067) settings).getIsoLinesColor();
            ds.contourBlending = ((SettingsFractals1067) settings).getContourBlending();
            ds.drawColor = ((SettingsFractals1067) settings).getDrawColor();
            ds.drawContour = ((SettingsFractals1067) settings).getDrawContour();
            ds.drawGrid = ((SettingsFractals1067) settings).getDrawGrid();
            ds.drawCircles = ((SettingsFractals1067) settings).getDrawCircles();
            ds.drawIsoLines = ((SettingsFractals1067) settings).getDrawIsoLines();
            ds.customDomainColoring = ((SettingsFractals1067) settings).getCustomDomainColoring();
            ds.colorType = ((SettingsFractals1067) settings).getColorType();
            ds.contourType = ((SettingsFractals1067) settings).getContourType();
        }

        if (version < 1066) {
            ens.entropy_coloring = defaults.ens.entropy_coloring;
            ens.entropy_palette_factor = defaults.ens.entropy_palette_factor;
            ens.en_noise_reducing_factor = defaults.ens.en_noise_reducing_factor;
            fns.apply_plane_on_julia_seed = defaults.fns.apply_plane_on_julia_seed;
            ofs.offset_coloring = defaults.ofs.offset_coloring;
            ofs.post_process_offset = defaults.ofs.post_process_offset;
            ofs.of_noise_reducing_factor = defaults.ofs.of_noise_reducing_factor;
            ens.en_blending = defaults.ens.en_blending;
            ofs.of_blending = defaults.ofs.of_blending;
            rps.rp_blending = defaults.rps.rp_blending;
            ens.entropy_offset = defaults.ens.entropy_offset;
            rps.rainbow_offset = defaults.rps.rainbow_offset;
            gss.greyscale_coloring = defaults.gss.greyscale_coloring;
            gss.gs_noise_reducing_factor = defaults.gss.gs_noise_reducing_factor;
        } else {
            ens.entropy_coloring = ((SettingsFractals1066) settings).getEntropyColoring();
            ens.entropy_palette_factor = ((SettingsFractals1066) settings).getEntropyPaletteFactor();
            ens.en_noise_reducing_factor = ((SettingsFractals1066) settings).getEntropyColoringNoiseReducingFactor();
            fns.apply_plane_on_julia_seed = ((SettingsFractals1066) settings).getApplyPlaneOnJuliaSeed();
            ofs.offset_coloring = ((SettingsFractals1066) settings).getOffsetColoring();
            ofs.post_process_offset = ((SettingsFractals1066) settings).getPostProcessOffset();
            ofs.of_noise_reducing_factor = ((SettingsFractals1066) settings).getOffsetColoringNoiseReducingFactor();
            ens.en_blending = ((SettingsFractals1066) settings).getEntropyColoringBlending();
            ofs.of_blending = ((SettingsFractals1066) settings).getOffsetColoringBlending();
            rps.rp_blending = ((SettingsFractals1066) settings).getRainbowPaletteBlending();
            ens.entropy_offset = ((SettingsFractals1066) settings).getEntropyColoringOffset();
            rps.rainbow_offset = ((SettingsFractals1066) settings).getRainbowPaletteOffset();
            gss.greyscale_coloring = ((SettingsFractals1066) settings).getGreyscaleColoring();
            gss.gs_noise_reducing_factor = ((SettingsFractals1066) settings).getGreyscaleColoringNoiseReducingFactor();
        }

        if (version < 1053) {
            exterior_de = defaults.exterior_de;
            exterior_de_factor = defaults.exterior_de_factor;
            height_ratio = defaults.height_ratio;

            if (fns.plane_type == FOLDUP_PLANE) {
                fns.plane_transform_center[0] = 0;
                fns.plane_transform_center[1] = -0.25;
            } else if (fns.plane_type == FOLDRIGHT_PLANE) {
                fns.plane_transform_center[0] = -1;
                fns.plane_transform_center[1] = 0;
            } else if (fns.plane_type == FOLDIN_PLANE || fns.plane_type == FOLDOUT_PLANE) {
                fns.plane_transform_radius = 1;
            }

        } else {
            exterior_de = ((SettingsFractals1053) settings).getExteriorDe();
            exterior_de_factor = ((SettingsFractals1053) settings).getExteriorDeFactor();

            height_ratio = ((SettingsFractals1053) settings).getHeightRatio();

            if (fns.plane_type == TWIRL_PLANE) {
                fns.plane_transform_center = ((SettingsFractals1053) settings).getPlaneTransformCenter();
                fns.plane_transform_angle = ((SettingsFractals1053) settings).getPlaneTransformAngle();
                fns.plane_transform_radius = ((SettingsFractals1053) settings).getPlaneTransformRadius();
            } else if (fns.plane_type == SHEAR_PLANE) {
                fns.plane_transform_scales = ((SettingsFractals1053) settings).getPlaneTransformScales();
            } else if (fns.plane_type == KALEIDOSCOPE_PLANE) {
                fns.plane_transform_center = ((SettingsFractals1053) settings).getPlaneTransformCenter();
                fns.plane_transform_angle = ((SettingsFractals1053) settings).getPlaneTransformAngle();
                fns.plane_transform_angle2 = ((SettingsFractals1053) settings).getPlaneTransformAngle2();
                fns.plane_transform_radius = ((SettingsFractals1053) settings).getPlaneTransformRadius();
                fns.plane_transform_sides = ((SettingsFractals1053) settings).getPlaneTransformSides();
            } else if (fns.plane_type == PINCH_PLANE) {
                fns.plane_transform_center = ((SettingsFractals1053) settings).getPlaneTransformCenter();
                fns.plane_transform_angle = ((SettingsFractals1053) settings).getPlaneTransformAngle();
                fns.plane_transform_radius = ((SettingsFractals1053) settings).getPlaneTransformRadius();
                fns.plane_transform_amount = ((SettingsFractals1053) settings).getPlaneTransformAmount();
            } else if (fns.plane_type == FOLDUP_PLANE || fns.plane_type == FOLDDOWN_PLANE || fns.plane_type == FOLDRIGHT_PLANE || fns.plane_type == FOLDLEFT_PLANE || fns.plane_type == INFLECTION_PLANE) {
                fns.plane_transform_center = ((SettingsFractals1053) settings).getPlaneTransformCenter();
            } else if (fns.plane_type == FOLDIN_PLANE || fns.plane_type == FOLDOUT_PLANE) {
                fns.plane_transform_radius = ((SettingsFractals1053) settings).getPlaneTransformRadius();
            } else if (fns.plane_type == CIRCLEINVERSION_PLANE) {
                fns.plane_transform_center = ((SettingsFractals1053) settings).getPlaneTransformCenter();
                fns.plane_transform_radius = ((SettingsFractals1053) settings).getPlaneTransformRadius();
            } else if (fns.plane_type == SKEW_PLANE) {
                fns.plane_transform_angle = ((SettingsFractals1053) settings).getPlaneTransformAngle();
                fns.plane_transform_angle2 = ((SettingsFractals1053) settings).getPlaneTransformAngle2();
            }
        }

        if (version < 1063) {
            if (fns.plane_type == BIPOLAR_PLANE || fns.plane_type == INVERSED_BIPOLAR_PLANE) {
                fns.plane_transform_center[0] = 2;
                fns.plane_transform_center[1] = 0;
            }

            ds.domain_coloring = defaults.ds.domain_coloring;
            ds.domain_coloring_mode = defaults.ds.domain_coloring_mode;
            ds.domain_coloring_alg = defaults.ds.domain_coloring_alg;
        } else {
            if (fns.plane_type == BIPOLAR_PLANE || fns.plane_type == INVERSED_BIPOLAR_PLANE) {
                fns.plane_transform_center = ((SettingsFractals1053) settings).getPlaneTransformCenter();
            }

            ds.domain_coloring = ((SettingsFractals1063) settings).getDomainColoring();
            ds.domain_coloring_mode = ((SettingsFractals1063) settings).getUsePaletteDomainColoring() ? 1 : 0;
            ds.domain_coloring_alg = ((SettingsFractals1063) settings).getDomainColoringAlg();
        }

        if (version < 1071) {
            ps2.color_choice = defaults.ps2.color_choice;
            ps2.color_cycling_location = defaults.ps2.color_cycling_location;
            ps2.color_intensity = defaults.ps2.color_intensity;
            ps2.transfer_function = defaults.ps2.transfer_function;
            ps2.color_interpolation = defaults.ps2.color_interpolation;
            ps2.color_space = defaults.ps2.color_space;
            ps2.reversed_palette = defaults.ps2.reversed_palette;
            ps2.scale_factor_palette_val = defaults.ps2.scale_factor_palette_val;
            ps2.processing_alg = defaults.ps2.processing_alg;
            ps2.custom_palette = defaults.ps2.custom_palette;
            usePaletteForInColoring = defaults.usePaletteForInColoring;
            temp_color_cycling_location_second_palette = ps2.color_cycling_location;

            ds.circleFadeFunction = defaults.ds.circleFadeFunction;
            ds.gridFadeFunction = defaults.ds.gridFadeFunction;
            ds.max_norm_re_im_value = defaults.ds.max_norm_re_im_value;
            ds.contourMethod = defaults.ds.contourMethod;
            //ds.domain_coloring_mode = defaults.ds.domain_coloring_mode; // already handled by 1.0.6.3
            ds.domainProcessingHeightFactor = defaults.ds.domainProcessingHeightFactor;
            ds.domainProcessingTransfer = defaults.ds.domainProcessingTransfer;

            pbs.palette_gradient_merge = defaults.pbs.palette_gradient_merge;
            pbs.gradient_intensity = defaults.pbs.gradient_intensity;
            pbs.palette_blending = defaults.pbs.palette_blending;
            pbs.gradient_offset = defaults.pbs.gradient_offset;
            pbs.merging_type = defaults.pbs.merging_type;

            cns.contourColorMethod = defaults.cns.contourColorMethod;

            ots.trapColorMethod = defaults.ots.trapColorMethod;
            ots.trapIntensity = defaults.ots.trapIntensity;

            sts.statistic = defaults.sts.statistic;
            sts.statistic_type = defaults.sts.statistic_type;
            sts.statistic_intensity = defaults.sts.statistic_intensity;
            sts.stripeAvgStripeDensity = defaults.sts.stripeAvgStripeDensity;
            sts.cosArgStripeDensity = defaults.sts.cosArgStripeDensity;
            sts.cosArgInvStripeDensity = defaults.sts.cosArgInvStripeDensity;
            sts.StripeDenominatorFactor = defaults.sts.StripeDenominatorFactor;
            sts.statisticGroup = defaults.sts.statisticGroup;
            sts.user_statistic_formula = defaults.sts.user_statistic_formula;
            sts.useAverage = defaults.sts.useAverage;
            sts.statistic_escape_type = defaults.sts.statistic_escape_type;

            ls.lighting = defaults.ls.lighting;
            ls.lightintensity = defaults.ls.lightintensity;
            ls.ambientlight = defaults.ls.ambientlight;
            ls.specularintensity = defaults.ls.specularintensity;
            ls.shininess = defaults.ls.shininess;
            ls.heightTransfer = defaults.ls.heightTransfer;
            ls.heightTransferFactor = defaults.ls.heightTransferFactor;
            ls.lightMode = defaults.ls.lightMode;
            ls.colorMode = defaults.ls.colorMode;
            ls.l_noise_reducing_factor = defaults.ls.l_noise_reducing_factor;
            ls.light_blending = defaults.ls.light_blending;
            ls.light_direction = defaults.ls.light_direction;
            ls.light_magnitude = defaults.ls.light_magnitude;

            double lightAngleRadians = Math.toRadians(ls.light_direction);
            ls.lightVector[0] = Math.cos(lightAngleRadians) * ls.light_magnitude;
            ls.lightVector[1] = Math.sin(lightAngleRadians) * ls.light_magnitude;
        } else {
            ps2.color_choice = ((SettingsFractals1071) settings).getColorChoice2();
            ps2.color_cycling_location = ((SettingsFractals1071) settings).getColorCyclingLocation2();
            ps2.color_intensity = ((SettingsFractals1071) settings).getColorIntensity2();
            ps2.transfer_function = ((SettingsFractals1071) settings).getTransferFunction2();

            if (ps2.color_choice == CUSTOM_PALETTE_ID) {
                ps2.color_interpolation = ((SettingsFractals1071) settings).getColorInterpolation2();
                ps2.color_space = ((SettingsFractals1071) settings).getColorSpace2();
                ps2.reversed_palette = ((SettingsFractals1071) settings).getReversedPalette2();
                ps2.scale_factor_palette_val = ((SettingsFractals1071) settings).getScaleFactorPaletteVal2();
                ps2.processing_alg = ((SettingsFractals1071) settings).getProcessingAlg2();
                ps2.custom_palette = ((SettingsFractals1071) settings).getCustomPalette2();
                temp_color_cycling_location_second_palette = ps2.color_cycling_location;
            }

            usePaletteForInColoring = ((SettingsFractals1071) settings).getUsePaletteForInColoring();

            ds.circleFadeFunction = ((SettingsFractals1071) settings).getCircleFadeFunction();
            ds.gridFadeFunction = ((SettingsFractals1071) settings).getGridFadeFunction();
            ds.max_norm_re_im_value = ((SettingsFractals1071) settings).getMaxNormReImValue();
            ds.contourMethod = ((SettingsFractals1071) settings).getContourMethod();
            ds.domain_coloring_mode = ((SettingsFractals1071) settings).getDomainColoringMode();
            ds.domainProcessingHeightFactor = ((SettingsFractals1071) settings).getDomainProcessingHeightFactor();
            ds.domainProcessingTransfer = ((SettingsFractals1071) settings).getDomainProcessingTransfer();

            pbs.palette_gradient_merge = ((SettingsFractals1071) settings).getPaletteGradientMerge();
            pbs.gradient_intensity = ((SettingsFractals1071) settings).getGradienIntensity();
            pbs.palette_blending = ((SettingsFractals1071) settings).getPaletteBlending();
            pbs.gradient_offset = ((SettingsFractals1071) settings).getGradientOffset();
            pbs.merging_type = ((SettingsFractals1071) settings).getMergingType();

            cns.contourColorMethod = ((SettingsFractals1071) settings).getContourColorMethod();

            ots.trapColorMethod = ((SettingsFractals1071) settings).getTrapColorMethod();
            ots.trapIntensity = ((SettingsFractals1071) settings).getTrapIntesity();

            sts.statistic = ((SettingsFractals1071) settings).getStatistic();
            sts.statistic_type = ((SettingsFractals1071) settings).getStatisticType();
            sts.statistic_intensity = ((SettingsFractals1071) settings).getStatisticIntensity();
            sts.stripeAvgStripeDensity = ((SettingsFractals1071) settings).getStripeAvgStripeDensity();
            sts.cosArgStripeDensity = ((SettingsFractals1071) settings).getCosArgStripeDensity();
            sts.cosArgInvStripeDensity = ((SettingsFractals1071) settings).getCosArgInvStripeDensity();
            sts.StripeDenominatorFactor = ((SettingsFractals1071) settings).getStripeDenominatorFactor();
            sts.statisticGroup = ((SettingsFractals1071) settings).getStatisticGroup();
            sts.user_statistic_formula = ((SettingsFractals1071) settings).getUserStatisticFormula();
            sts.useAverage = ((SettingsFractals1071) settings).getUseAverage();
            sts.statistic_escape_type = ((SettingsFractals1071) settings).getStatisticEscapeType();

            ls.lighting = ((SettingsFractals1071) settings).getLighting();
            ls.lightintensity = ((SettingsFractals1071) settings).getLightintensity();
            ls.ambientlight = ((SettingsFractals1071) settings).getAmbientlight();
            ls.specularintensity = ((SettingsFractals1071) settings).getSpecularintensity();
            ls.shininess = ((SettingsFractals1071) settings).getShininess();
            ls.heightTransfer = ((SettingsFractals1071) settings).getLightHeightTransfer();
            ls.heightTransferFactor = ((SettingsFractals1071) settings).getLightHeightTransferFactor();
            ls.lightMode = ((SettingsFractals1071) settings).getLightMode();
            ls.colorMode = ((SettingsFractals1071) settings).getLightColorMode();
            ls.l_noise_reducing_factor = ((SettingsFractals1071) settings).getLightNoiseReducingFactor();
            ls.light_blending = ((SettingsFractals1071) settings).getLightBlending();
            ls.light_direction = ((SettingsFractals1071) settings).getLightDirection();
            ls.light_magnitude = ((SettingsFractals1071) settings).getLightMagnitude();

            double lightAngleRadians = Math.toRadians(ls.light_direction);
            ls.lightVector[0] = Math.cos(lightAngleRadians) * ls.light_magnitude;
            ls.lightVector[1] = Math.sin(lightAngleRadians) * ls.light_magnitude;
        }

        if (version < 1072) {
            ots.trapColor1 = defaults.ots.trapColor1;
            ots.trapColor2 = defaults.ots.trapColor2;
            ots.trapColor3 = defaults.ots.trapColor3;
            ots.trapColorInterpolation = defaults.ots.trapColorInterpolation;
            ots.trapIncludeNotEscaped = defaults.ots.trapIncludeNotEscaped;
            ots.trapIncludeEscaped = defaults.ots.trapIncludeEscaped;

            sts.statisticIncludeEscaped = defaults.sts.statisticIncludeEscaped;
            sts.statisticIncludeNotEscaped = defaults.sts.statisticIncludeNotEscaped;

            ps.direct_palette = defaults.ps.direct_palette;
            ps2.direct_palette = defaults.ps2.direct_palette;
        } else {
            ots.trapColor1 = ((SettingsFractals1072) settings).getTrapColor1();
            ots.trapColor2 = ((SettingsFractals1072) settings).getTrapColor2();
            ots.trapColor3 = ((SettingsFractals1072) settings).getTrapColor3();
            ots.trapColorInterpolation = ((SettingsFractals1072) settings).getTrapColorInterpolation();
            ots.trapIncludeNotEscaped = ((SettingsFractals1072) settings).getTrapIncludeNotEscaped();
            ots.trapIncludeEscaped = ((SettingsFractals1072) settings).getTrapIncludeEscaped();

            sts.statisticIncludeEscaped = ((SettingsFractals1072) settings).getStatisticIncludeEscaped();
            sts.statisticIncludeNotEscaped = ((SettingsFractals1072) settings).getStatisticIncludeNotEscaped();

            if (ps.color_choice == DIRECT_PALETTE_ID) {
                ps.direct_palette = ((SettingsFractals1072) settings).getDirectPalette();
            }

            if (ps2.color_choice == DIRECT_PALETTE_ID) {
                ps2.direct_palette = ((SettingsFractals1072) settings).getDirectPalette2();
            }
        }

        if (version < 1073) {
            sts.user_statistic_init_value = defaults.sts.user_statistic_init_value;
            fns.skip_bailout_iterations = defaults.fns.skip_bailout_iterations;
            gs.gradient_offset = defaults.gs.gradient_offset;
        } else {
            sts.user_statistic_init_value = ((SettingsFractals1073) settings).getUserStatisticInitValue();
            fns.skip_bailout_iterations = ((SettingsFractals1073) settings).getSkipBailoutIterations();
            gs.gradient_offset = ((SettingsFractals1073) settings).getGradientOffset();
        }

        if (version < 1074) {
            fns.tcs.trueColorOut = defaults.fns.tcs.trueColorOut;
            fns.tcs.trueColorIn = defaults.fns.tcs.trueColorIn;
            fns.tcs.trueColorOutMode = defaults.fns.tcs.trueColorOutMode;
            fns.tcs.trueColorInMode = defaults.fns.tcs.trueColorInMode;
            fns.tcs.trueColorOutPreset = defaults.fns.tcs.trueColorOutPreset;
            fns.tcs.trueColorInPreset = defaults.fns.tcs.trueColorInPreset;
            fns.tcs.outTcComponent1 = defaults.fns.tcs.outTcComponent1;
            fns.tcs.outTcComponent2 = defaults.fns.tcs.outTcComponent2;
            fns.tcs.outTcComponent3 = defaults.fns.tcs.outTcComponent3;
            fns.tcs.inTcComponent1 = defaults.fns.tcs.inTcComponent1;
            fns.tcs.inTcComponent2 = defaults.fns.tcs.inTcComponent2;
            fns.tcs.inTcComponent3 = defaults.fns.tcs.inTcComponent3;
            fns.tcs.outTcColorSpace = defaults.fns.tcs.outTcColorSpace;
            fns.tcs.inTcColorSpace = defaults.fns.tcs.inTcColorSpace;
            sts.showAtomDomains = defaults.sts.showAtomDomains;
            sts.reductionFunction = defaults.sts.reductionFunction;
            sts.useIterations = defaults.sts.useIterations;
            sts.useSmoothing = defaults.sts.useSmoothing;
        } else {
            fns.tcs.trueColorOut = ((SettingsFractals1074) settings).getTrueColorOut();
            fns.tcs.trueColorIn = ((SettingsFractals1074) settings).getTrueColorIn();
            fns.tcs.trueColorOutMode = ((SettingsFractals1074) settings).getTrueColorOutMode();
            fns.tcs.trueColorInMode = ((SettingsFractals1074) settings).getTrueColorInMode();
            fns.tcs.trueColorOutPreset = ((SettingsFractals1074) settings).getTrueColorOutPreset();
            fns.tcs.trueColorInPreset = ((SettingsFractals1074) settings).getTrueColorInPreset();
            fns.tcs.outTcComponent1 = ((SettingsFractals1074) settings).getOutTcComponent1();
            fns.tcs.outTcComponent2 = ((SettingsFractals1074) settings).getOutTcComponent2();
            fns.tcs.outTcComponent3 = ((SettingsFractals1074) settings).getOutTcComponent3();
            fns.tcs.inTcComponent1 = ((SettingsFractals1074) settings).getInTcComponent1();
            fns.tcs.inTcComponent2 = ((SettingsFractals1074) settings).getInTcComponent2();
            fns.tcs.inTcComponent3 = ((SettingsFractals1074) settings).getInTcComponent3();
            fns.tcs.outTcColorSpace = ((SettingsFractals1074) settings).getOutTcColorSpace();
            fns.tcs.inTcColorSpace = ((SettingsFractals1074) settings).getInTcColorSpace();
            sts.showAtomDomains = ((SettingsFractals1074) settings).getShowAtomDomains();
            sts.reductionFunction = ((SettingsFractals1074) settings).getReductionFunction();
            sts.useIterations = ((SettingsFractals1074) settings).getUseIterations();
            sts.useSmoothing = ((SettingsFractals1074) settings).getUseSmoothing();
        }

        if (fns.plane_type == USER_PLANE) {
            if (version < 1058) {
                fns.user_plane_algorithm = defaults.fns.user_plane_algorithm;
            } else {
                fns.user_plane_algorithm = ((SettingsFractals1058) settings).getUserPlaneAlgorithm();
            }

            if (fns.user_plane_algorithm == 0) {
                fns.user_plane = settings.getUserPlane();
            } else {
                fns.user_plane_conditions = ((SettingsFractals1058) settings).getUserPlaneConditions();
                fns.user_plane_condition_formula = ((SettingsFractals1058) settings).getUserPlaneConditionFormula();
            }
        }

        if (ps.color_choice == CUSTOM_PALETTE_ID) {
            ps.custom_palette = settings.getCustomPalette();
            ps.color_interpolation = settings.getColorInterpolation();
            ps.color_space = settings.getColorSpace();
            ps.reversed_palette = settings.getReveresedPalette();
            temp_color_cycling_location = ps.color_cycling_location;

            if (version < 1062) {
                ps.scale_factor_palette_val = defaults.ps.scale_factor_palette_val;
                ps.processing_alg = defaults.ps.processing_alg;
            } else {
                ps.processing_alg = ((SettingsFractals1062) settings).getProcessingAlgorithm();
                ps.scale_factor_palette_val = ((SettingsFractals1062) settings).getScaleFactorPaletteValue();
            }
        }

        fns.rotation = settings.getRotation();
        fns.rotation_center = settings.getRotationCenter();

        fns.bailout_test_algorithm = settings.getBailoutTestAlgorithm();

        if (fns.bailout_test_algorithm == BAILOUT_CONDITION_NNORM) {
            fns.n_norm = settings.getNNorm();
        } else if (fns.bailout_test_algorithm == BAILOUT_CONDITION_USER) {
            fns.bailout_test_user_formula = ((SettingsFractals1056) settings).getBailoutTestUserFormula();
            fns.bailout_test_comparison = ((SettingsFractals1056) settings).getBailoutTestComparison();
            if (version < 1058) {
                fns.bailout_test_user_formula2 = defaults.fns.bailout_test_user_formula2;
            } else {
                fns.bailout_test_user_formula2 = ((SettingsFractals1058) settings).getBailoutTestUserFormula2();
            }
        }

        fns.rotation_vals[0] = Math.cos(Math.toRadians(fns.rotation));
        fns.rotation_vals[1] = Math.sin(Math.toRadians(fns.rotation));

        fns.out_coloring_algorithm = settings.getOutColoringAlgorithm();

        if (fns.out_coloring_algorithm == ATOM_DOMAIN) { //removed atom domain
            fns.out_coloring_algorithm = defaults.fns.out_coloring_algorithm;
        }

        if (fns.out_coloring_algorithm == USER_OUTCOLORING_ALGORITHM) {
            fns.user_out_coloring_algorithm = ((SettingsFractals1057) settings).getUserOutColoringAlgorithm();
            fns.outcoloring_formula = ((SettingsFractals1057) settings).getOutcoloringFormula();
            fns.user_outcoloring_conditions = ((SettingsFractals1057) settings).getUserOutcoloringConditions();
            fns.user_outcoloring_condition_formula = ((SettingsFractals1057) settings).getUserOutcoloringConditionFormula();
        }

        fns.in_coloring_algorithm = settings.getInColoringAlgorithm();

        if (fns.in_coloring_algorithm == USER_INCOLORING_ALGORITHM) {
            fns.user_in_coloring_algorithm = ((SettingsFractals1057) settings).getUserInColoringAlgorithm();
            fns.incoloring_formula = ((SettingsFractals1057) settings).getIncoloringFormula();
            fns.user_incoloring_conditions = ((SettingsFractals1057) settings).getUserIncoloringConditions();
            fns.user_incoloring_condition_formula = ((SettingsFractals1057) settings).getUserIncoloringConditionFormula();
        }

        if (version < 1064) {
            inverse_dem = defaults.inverse_dem;
            fdes.inverse_fake_dem = defaults.fdes.inverse_fake_dem;
        } else {
            inverse_dem = ((SettingsFractals1064) settings).getInverseDe();
            fdes.inverse_fake_dem = ((SettingsFractals1064) settings).getInverseFakeDe();
            if (version == 1064) {
                boolean[] user_outcoloring_special_color = ((SettingsFractals1064) settings).getUserOutColoringSpecialColor();
                boolean[] user_incoloring_special_color = ((SettingsFractals1064) settings).getUserInColoringSpecialColor();

                if (user_incoloring_special_color[0]) {
                    fns.user_incoloring_condition_formula[0] = "-( " + fns.user_incoloring_condition_formula[0] + " )";
                }
                if (user_incoloring_special_color[1]) {
                    fns.user_incoloring_condition_formula[1] = "-( " + fns.user_incoloring_condition_formula[1] + " )";
                }
                if (user_incoloring_special_color[2]) {
                    fns.user_incoloring_condition_formula[2] = "-( " + fns.user_incoloring_condition_formula[2] + " )";
                }

                if (user_outcoloring_special_color[0]) {
                    fns.user_outcoloring_condition_formula[0] = "-( " + fns.user_outcoloring_condition_formula[0] + " )";
                }
                if (user_outcoloring_special_color[1]) {
                    fns.user_outcoloring_condition_formula[1] = "-( " + fns.user_outcoloring_condition_formula[1] + " )";
                }
                if (user_outcoloring_special_color[2]) {
                    fns.user_outcoloring_condition_formula[2] = "-( " + fns.user_outcoloring_condition_formula[2] + " )";
                }
            }
        }

        fns.smoothing = settings.getSmoothing();

        if (version < 1065) {
            color_smoothing_method = defaults.color_smoothing_method;
            bms.bm_noise_reducing_factor = defaults.bms.bm_noise_reducing_factor;
            rps.rp_noise_reducing_factor = defaults.rps.rp_noise_reducing_factor;
        } else {
            color_smoothing_method = ((SettingsFractals1065) settings).getColorSmoothingMethod();
            bms.bm_noise_reducing_factor = ((SettingsFractals1065) settings).getBumpMappingNoiseReducingFactor();
            rps.rp_noise_reducing_factor = ((SettingsFractals1065) settings).getRainbowPaletteNoiseReducingFactor();
        }

        if (version < 1054) {
            fns.escaping_smooth_algorithm = defaults.fns.escaping_smooth_algorithm;
            fns.converging_smooth_algorithm = defaults.fns.converging_smooth_algorithm;

            bms.bump_map = defaults.bms.bump_map;
            polar_projection = defaults.polar_projection;
            ps.color_intensity = defaults.ps.color_intensity;
        } else {
            fns.escaping_smooth_algorithm = ((SettingsFractals1054) settings).getEscapingSmoothAgorithm();
            fns.converging_smooth_algorithm = ((SettingsFractals1054) settings).getConvergingSmoothAgorithm();

            bms.bump_map = ((SettingsFractals1054) settings).getBumpMap();

            bms.bumpMappingStrength = ((SettingsFractals1054) settings).getBumpMapStrength();
            bms.bumpMappingDepth = ((SettingsFractals1054) settings).getBumpMapDepth();
            bms.lightDirectionDegrees = ((SettingsFractals1054) settings).getLightDirectionDegrees();

            polar_projection = ((SettingsFractals1054) settings).getPolarProjection();

            circle_period = ((SettingsFractals1054) settings).getCirclePeriod();

            ps.color_intensity = ((SettingsFractals1054) settings).getColorIntensity();
        }

        if (version < 1055) {
            fdes.fake_de = defaults.fdes.fake_de;
        } else {
            fdes.fake_de = ((SettingsFractals1055) settings).getFakeDe();
            fdes.fake_de_factor = ((SettingsFractals1055) settings).getFakeDeFactor();
        }

        switch (fns.function) {
            case MANDELBROTNTH:
                fns.z_exponent = settings.getZExponent();
                break;
            case MANDELBROTWTH:
                fns.z_exponent_complex = settings.getZExponentComplex();
                break;
            case MANDELPOLY:
            case NEWTONPOLY:
            case HALLEYPOLY:
            case SCHRODERPOLY:
            case HOUSEHOLDERPOLY:
            case SECANTPOLY:
            case STEFFENSENPOLY:
            case MULLERPOLY:
            case PARHALLEYPOLY:
            case LAGUERREPOLY:
            case DURAND_KERNERPOLY:
            case BAIRSTOWPOLY:
            case NEWTON_HINESPOLY:
                fns.coefficients = settings.getCoefficients();

                if (version < 1072) {
                    fns.coefficients_im = defaults.fns.coefficients_im;
                } else {
                    fns.coefficients_im = ((SettingsFractals1072) settings).getCoefficientsIm();
                }

                if (fns.function == DURAND_KERNERPOLY) {
                    fns.durand_kerner_init_val = ((SettingsFractals1072) settings).getDurandKernerInitVal();
                }

                if (version < 1074) {
                    fns.newton_hines_k = defaults.fns.newton_hines_k;
                } else {
                    fns.newton_hines_k = ((SettingsFractals1074) settings).getNewtonHinesK();
                }

                createPoly();
                break;
            case MAGNETIC_PENDULUM:
                fns.mps.magnetLocation = ((SettingsFractals1072) settings).getMagnetLocation();
                fns.mps.magnetStrength = ((SettingsFractals1072) settings).getMagnetStrength();
                fns.mps.friction = ((SettingsFractals1072) settings).getFriction();
                fns.mps.gravity = ((SettingsFractals1072) settings).getGravity();
                fns.mps.height = ((SettingsFractals1072) settings).getPendulumHeight();
                fns.mps.pendulum = ((SettingsFractals1072) settings).getPendulum();
                fns.mps.stepsize = ((SettingsFractals1072) settings).getPendulumStepsize();

                if (version < 1073) {
                    fns.mps.magnetPendVariableId = defaults.fns.mps.magnetPendVariableId;
                } else {
                    fns.mps.magnetPendVariableId = ((SettingsFractals1073) settings).getMagnetPendVariableId();
                }
                
                if (version < 1074) {
                    fns.mps.stepsize_im = defaults.fns.mps.stepsize_im;
                } else {
                    fns.mps.stepsize_im = ((SettingsFractals1074) settings).getStepsizeIm();
                }

                break;
            case LYAPUNOV:
                fns.lpns.lyapunovA = ((SettingsFractals1072) settings).getLyapunovA();
                fns.lpns.lyapunovB = ((SettingsFractals1072) settings).getLyapunovB();
                fns.lpns.lyapunovC = ((SettingsFractals1072) settings).getLyapunovC();
                fns.lpns.lyapunovD = ((SettingsFractals1072) settings).getLyapunovD();
                fns.lpns.useLyapunovExponent = ((SettingsFractals1072) settings).getUseLyapunovExponent();
                fns.lpns.lyapunovExpression = ((SettingsFractals1072) settings).getLyapunovExpression();

                if (version < 1073) {
                    fns.lpns.lyapunovFunction = defaults.fns.lpns.lyapunovFunction;
                    fns.lpns.lyapunovExponentFunction = defaults.fns.lpns.lyapunovExponentFunction;
                    fns.lpns.lyapunovVariableId = defaults.fns.lpns.lyapunovVariableId;
                } else {
                    fns.lpns.lyapunovFunction = ((SettingsFractals1073) settings).getLyapunovFunction();
                    fns.lpns.lyapunovExponentFunction = ((SettingsFractals1073) settings).getLyapunovExponentFunction();
                    fns.lpns.lyapunovVariableId = ((SettingsFractals1073) settings).getLyapunovVariableId();
                }
                
                if (version < 1074) {
                    fns.lpns.lyapunovInitialValue = defaults.fns.lpns.lyapunovInitialValue;
                } else {
                    fns.lpns.lyapunovInitialValue = ((SettingsFractals1074) settings).getLyapunovInitialValue();
                }

                String[] subExpressions = LyapunovSettings.getTokens(fns.lpns.lyapunovExpression);

                if (subExpressions == null) {
                    throw new Exception();
                }

                subExpressions = LyapunovSettings.flatten(subExpressions, "$A", fns.lpns.lyapunovA);
                subExpressions = LyapunovSettings.flatten(subExpressions, "$B", fns.lpns.lyapunovB);
                subExpressions = LyapunovSettings.flatten(subExpressions, "$C", fns.lpns.lyapunovC);
                subExpressions = LyapunovSettings.flatten(subExpressions, "$D", fns.lpns.lyapunovD);

                boolean temp_bool = false;
                for (String subExpression : subExpressions) {
                    parser.parse(subExpression);
                    temp_bool = temp_bool | parser.foundC();
                }

                fns.lpns.lyapunovFinalExpression = subExpressions;
                userFormulaHasC = temp_bool;
                break;
            case GENERIC_CaZbdZe:
                fns.gcs.alpha = ((SettingsFractals1072) settings).getAlpha();
                fns.gcs.beta = ((SettingsFractals1072) settings).getBeta();
                fns.gcs.delta = ((SettingsFractals1072) settings).getDelta();
                fns.gcs.epsilon = ((SettingsFractals1072) settings).getEpsilon();
                break;
            case NEWTONFORMULA:
                fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();
                break;
            case HALLEYFORMULA:
                fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();
                fns.user_ddfz_formula = ((SettingsFractals1058) settings).getUserDdfzFormula();
                break;
            case SCHRODERFORMULA:
                fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();
                fns.user_ddfz_formula = ((SettingsFractals1058) settings).getUserDdfzFormula();
                break;
            case HOUSEHOLDERFORMULA:
                fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();
                fns.user_ddfz_formula = ((SettingsFractals1058) settings).getUserDdfzFormula();
                break;
            case SECANTFORMULA:
                fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                break;
            case STEFFENSENFORMULA:
                fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                break;
            case MULLERFORMULA:
                fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                break;
            case PARHALLEYFORMULA:
                fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();
                fns.user_ddfz_formula = ((SettingsFractals1058) settings).getUserDdfzFormula();
                break;
            case LAGUERREFORMULA:
                fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();
                fns.user_ddfz_formula = ((SettingsFractals1058) settings).getUserDdfzFormula();
                fns.laguerre_deg = ((SettingsFractals1067) settings).getLaguerreDeg();
                break;
            case NEWTON_HINESFORMULA:
                fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();
                fns.newton_hines_k = ((SettingsFractals1074) settings).getNewtonHinesK();
                break;
            case NOVA:
                fns.z_exponent_nova = settings.getZExponentNova();
                fns.relaxation = settings.getRelaxation();
                fns.nova_method = settings.getNovaMethod();

                if (fns.nova_method == NOVA_NEWTON_HINES) {
                    fns.newton_hines_k = ((SettingsFractals1074) settings).getNewtonHinesK();
                }
                break;
            case INERTIA_GRAVITY:
                fns.igs.bodyLocation = ((SettingsFractals1073) settings).getBodyLocation();
                fns.igs.bodyGravity = ((SettingsFractals1073) settings).getBodyGravity();
                fns.igs.inertia_contribution = ((SettingsFractals1073) settings).getInertiaContribution();
                fns.igs.initial_inertia = ((SettingsFractals1073) settings).getInitialInertia();
                fns.igs.inertia_exponent = ((SettingsFractals1073) settings).getInertiaExponent();
                fns.igs.pull_scaling_function = ((SettingsFractals1073) settings).getPullScalingFunction();
                fns.igs.time_step = ((SettingsFractals1073) settings).getTimeStep();
                break;
            case GENERIC_CpAZpBC:
                fns.gcps.alpha2 = ((SettingsFractals1073) settings).getAlpha2();
                fns.gcps.beta2 = ((SettingsFractals1073) settings).getBeta2();
                break;
            case LAMBDA_FN_FN:
                fns.lfns.lambda_formula_conditions = ((SettingsFractals1073) settings).getLambdaFormulaConditions();
                fns.lfns.lambda_formula_condition_formula = ((SettingsFractals1073) settings).getLambdaFormulaConditionFormula();
                break;
            case USER_FORMULA_NOVA:
                fns.nova_method = settings.getNovaMethod();

                fns.user_relaxation_formula = ((SettingsFractals1073) settings).getUserRelaxationFormula();
                fns.user_nova_addend_formula = ((SettingsFractals1073) settings).getUserNovaAddendFormula();

                temp_bool = false;

                switch (fns.nova_method) {
                    case NOVA_NEWTON:
                        fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                        fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();

                        parser.parse(fns.user_fz_formula);
                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(fns.user_dfz_formula);
                        temp_bool = temp_bool | parser.foundC();
                        break;
                    case NOVA_HALLEY:
                        fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                        fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();
                        fns.user_ddfz_formula = ((SettingsFractals1058) settings).getUserDdfzFormula();

                        parser.parse(fns.user_fz_formula);
                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(fns.user_dfz_formula);
                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(fns.user_ddfz_formula);
                        temp_bool = temp_bool | parser.foundC();
                        break;
                    case NOVA_SCHRODER:
                        fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                        fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();
                        fns.user_ddfz_formula = ((SettingsFractals1058) settings).getUserDdfzFormula();

                        parser.parse(fns.user_fz_formula);
                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(fns.user_dfz_formula);
                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(fns.user_ddfz_formula);
                        temp_bool = temp_bool | parser.foundC();
                        break;
                    case NOVA_HOUSEHOLDER:
                        fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                        fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();
                        fns.user_ddfz_formula = ((SettingsFractals1058) settings).getUserDdfzFormula();

                        parser.parse(fns.user_fz_formula);
                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(fns.user_dfz_formula);
                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(fns.user_ddfz_formula);
                        temp_bool = temp_bool | parser.foundC();
                        break;
                    case NOVA_SECANT:
                        fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();

                        parser.parse(fns.user_fz_formula);
                        temp_bool = temp_bool | parser.foundC();
                        break;
                    case NOVA_STEFFENSEN:
                        fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();

                        parser.parse(fns.user_fz_formula);
                        temp_bool = temp_bool | parser.foundC();
                        break;
                    case NOVA_MULLER:
                        fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();

                        parser.parse(fns.user_fz_formula);
                        temp_bool = temp_bool | parser.foundC();
                        break;
                    case NOVA_PARHALLEY:
                        fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                        fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();
                        fns.user_ddfz_formula = ((SettingsFractals1058) settings).getUserDdfzFormula();

                        parser.parse(fns.user_fz_formula);
                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(fns.user_dfz_formula);
                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(fns.user_ddfz_formula);
                        temp_bool = temp_bool | parser.foundC();
                        break;
                    case NOVA_LAGUERRE:
                        fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                        fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();
                        fns.user_ddfz_formula = ((SettingsFractals1058) settings).getUserDdfzFormula();
                        fns.laguerre_deg = ((SettingsFractals1067) settings).getLaguerreDeg();

                        parser.parse(fns.user_fz_formula);
                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(fns.user_dfz_formula);
                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(fns.user_ddfz_formula);
                        temp_bool = temp_bool | parser.foundC();
                        break;
                    case NOVA_NEWTON_HINES:
                        fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                        fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();

                        parser.parse(fns.user_fz_formula);
                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(fns.user_dfz_formula);
                        temp_bool = temp_bool | parser.foundC();
                        fns.newton_hines_k = ((SettingsFractals1074) settings).getNewtonHinesK();
                        break;

                }

                parser.parse(fns.user_relaxation_formula);
                temp_bool = temp_bool | parser.foundC();

                parser.parse(fns.user_nova_addend_formula);
                temp_bool = temp_bool | parser.foundC();

                userFormulaHasC = temp_bool;
                break;
            case USER_FORMULA:
                fns.user_formula = settings.getUserFormula();
                fns.bail_technique = settings.getBailTechnique();

                if (version < 1049) {
                    fns.user_formula2 = defaults.fns.user_formula2;
                } else {
                    fns.user_formula2 = ((SettingsFractals1049) settings).getUserFormula2();
                }

                parser.parse(fns.user_formula);

                userFormulaHasC = parser.foundC();
                break;
            case USER_FORMULA_ITERATION_BASED:
                fns.user_formula_iteration_based = ((SettingsFractals1049) settings).getUserFormulaIterationBased();
                fns.bail_technique = settings.getBailTechnique();

                temp_bool = false;

                for (int m = 0; m < fns.user_formula_iteration_based.length; m++) {
                    parser.parse(fns.user_formula_iteration_based[m]);
                    temp_bool = temp_bool | parser.foundC();
                }

                userFormulaHasC = temp_bool;
                break;
            case USER_FORMULA_CONDITIONAL:
                fns.user_formula_conditions = ((SettingsFractals1050) settings).getUserFormulaConditions();
                fns.user_formula_condition_formula = ((SettingsFractals1050) settings).getUserFormulaConditionFormula();
                fns.bail_technique = settings.getBailTechnique();

                boolean temp_bool2 = false;

                for (int m = 0; m < fns.user_formula_condition_formula.length; m++) {
                    parser.parse(fns.user_formula_condition_formula[m]);
                    temp_bool2 = temp_bool2 | parser.foundC();
                }

                userFormulaHasC = temp_bool2;
                break;
            case USER_FORMULA_COUPLED:
                fns.user_formula_coupled = ((SettingsFractals1063) settings).getUserFormulaCoupled();
                fns.coupling = ((SettingsFractals1063) settings).getCoupling();
                fns.coupling_amplitude = ((SettingsFractals1063) settings).getCouplingAmplitude();
                fns.coupling_frequency = ((SettingsFractals1063) settings).getCouplingFrequency();
                fns.coupling_seed = ((SettingsFractals1063) settings).getCouplingSeed();
                fns.coupling_method = ((SettingsFractals1063) settings).getCouplingMethod();
                fns.bail_technique = settings.getBailTechnique();

                temp_bool = false;

                for (int m = 0; m < fns.user_formula_coupled.length - 1; m++) {
                    parser.parse(fns.user_formula_coupled[m]);
                    temp_bool = temp_bool | parser.foundC();
                }

                userFormulaHasC = temp_bool;
                break;
        }

        if (fns.out_coloring_algorithm != USER_OUTCOLORING_ALGORITHM) {
            resetUserOutColoringFormulas();
        }

        if (sts.statisticGroup != 1) {
            resetStatisticalColoringFormulas();
        }

        applyStaticSettings();

        file_temp.close();

        if (!silent) {
            loadedSettings(filename, parent, version);
        }
    }

    public void save(String filename) {
        ObjectOutputStream file_temp = null;

        try {
            file_temp = new ObjectOutputStream(new FileOutputStream(filename));
            SettingsFractals settings;
            if (fns.julia) {
                settings = new SettingsJulia1074(this);
            } else {
                settings = new SettingsFractals1074(this);
            }
            file_temp.writeObject(settings);
            file_temp.flush();
        } catch (IOException ex) {
        }

        try {
            file_temp.close();
        } catch (Exception ex) {
        }
    }

    public void startingPosition() {
        switch (fns.function) {
            case MANDEL_NEWTON:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                break;
            case MAGNET1:
                if (fns.julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 28;
                } else {
                    xCenter = 1.35;
                    yCenter = 0;
                    size = 8;
                }
                break;
            case MAGNET2:
                if (fns.julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 56;
                } else {
                    xCenter = 1;
                    yCenter = 0;
                    size = 7;
                }
                break;
            case BARNSLEY1:
            case BARNSLEY2:
                xCenter = 0;
                yCenter = 0;
                size = 7;
                break;
            case SIERPINSKI_GASKET:
                xCenter = 0.5;
                yCenter = 0.5;
                size = 3;
                break;
            case FORMULA1:
            case FORMULA44:
            case FORMULA45:
            case FORMULA43:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                break;
            case FORMULA3:
            case FORMULA9:
            case FORMULA10:
            case FORMULA8:
                xCenter = 0;
                yCenter = 0;
                size = 3;
                break;
            case FORMULA4:
            case FORMULA5:
            case FORMULA11:
                xCenter = 0;
                yCenter = 0;
                size = 1.5;
                break;
            case FORMULA7:
            case FORMULA12:
            case SZEGEDI_BUTTERFLY1:
            case SZEGEDI_BUTTERFLY2:
            case KLEINIAN:
            case FORMULA42:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                break;
            case FORMULA27:
                if (fns.julia) {
                    xCenter = -2;
                    yCenter = 0;
                    size = 6;
                } else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 3;
                }
                break;
            case FORMULA28:
            case FORMULA29:
                if (fns.julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 12;
                } else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 3;
                }
                break;
            case FORMULA38:
                if (fns.julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                } else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 1.5;
                }
                break;
            case LYAPUNOV:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                break;
            default:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
        }
    }

    public void resetUserOutColoringFormulas() {

        if (fns.function == MAGNETIC_PENDULUM) {
            fns.user_out_coloring_algorithm = 0;

            fns.outcoloring_formula = "norm(z)";

            fns.user_outcoloring_conditions[0] = "im(z)";
            fns.user_outcoloring_conditions[1] = "0";

            fns.user_outcoloring_condition_formula[0] = "norm(z)";
            fns.user_outcoloring_condition_formula[1] = "-(norm(z) + 50)";
            fns.user_outcoloring_condition_formula[2] = "norm(z)";
        } else if (isConvergingType()) {
            fns.user_out_coloring_algorithm = 0;

            fns.outcoloring_formula = "n + (log(cbail) / 2 - log(norm(p - pp))) / (log(norm(z - p)) - log(norm(p - pp)))";

            fns.user_outcoloring_conditions[0] = "im(z)";
            fns.user_outcoloring_conditions[1] = "0";

            fns.user_outcoloring_condition_formula[0] = "n + (log(cbail) / 2 - log(norm(p - pp))) / (log(norm(z - p)) - log(norm(p - pp)))";
            fns.user_outcoloring_condition_formula[1] = "-(n + (log(cbail) / 2 - log(norm(p - pp))) / (log(norm(z - p)) - log(norm(p - pp))) + 50)";
            fns.user_outcoloring_condition_formula[2] = "n + (log(cbail) / 2 - log(norm(p - pp))) / (log(norm(z - p)) - log(norm(p - pp)))";
        } else if (fns.function == MAGNET1) {
            fns.user_out_coloring_algorithm = 1;

            fns.outcoloring_formula = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";

            fns.user_outcoloring_conditions[0] = "norm(z-1)^2";
            fns.user_outcoloring_conditions[1] = "1e-12";

            fns.user_outcoloring_condition_formula[0] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";
            fns.user_outcoloring_condition_formula[1] = "n + (log(1e-12) / 2 - log(norm(p - 1))) / (log(norm(z - 1)) - log(norm(p - 1)))";
            fns.user_outcoloring_condition_formula[2] = "n + (log(1e-12) / 2 - log(norm(p - 1))) / (log(norm(z - 1)) - log(norm(p - 1)))";
        } else if (fns.function == MAGNET2) {
            fns.user_out_coloring_algorithm = 1;

            fns.outcoloring_formula = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";

            fns.user_outcoloring_conditions[0] = "norm(z-1)^2";
            fns.user_outcoloring_conditions[1] = "1e-9";

            fns.user_outcoloring_condition_formula[0] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";
            fns.user_outcoloring_condition_formula[1] = "n + (log(1e-9) / 2 - log(norm(p - 1))) / (log(norm(z - 1)) - log(norm(p - 1)))";
            fns.user_outcoloring_condition_formula[2] = "n + (log(1e-9) / 2 - log(norm(p - 1))) / (log(norm(z - 1)) - log(norm(p - 1)))";
        } else {
            fns.user_out_coloring_algorithm = 0;

            fns.outcoloring_formula = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";

            fns.user_outcoloring_conditions[0] = "im(z)";
            fns.user_outcoloring_conditions[1] = "0";

            fns.user_outcoloring_condition_formula[0] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";
            fns.user_outcoloring_condition_formula[1] = "-(n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001)) + 50)";
            fns.user_outcoloring_condition_formula[2] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";
        }
    }

    public void resetStatisticalColoringFormulas() {

        if (isConvergingType()) {
            sts.user_statistic_formula = "(0.5 * cos(6 * (arg(z-p) + pi)) + 0.5) / (12 + 1 / norm(z-p))";
            sts.useAverage = false;
        } else {
            sts.user_statistic_formula = "(0.5 * cos(12 * arg(z)) + 0.5) / norm(z)";
            sts.useAverage = true;
        }
        sts.reductionFunction = REDUCTION_SUM;
        sts.user_statistic_init_value = "0.0";
    }

    public void defaultFractalSettings() {

        switch (fns.function) {
            case MANDEL_NEWTON:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                fns.bailout = fns.bailout < 8 ? 8 : fns.bailout;
                break;
            case MAGNET1:
                if (fns.julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 28;
                    fns.bailout = fns.bailout < 13 ? 13 : fns.bailout;
                } else {
                    xCenter = 1.35;
                    yCenter = 0;
                    size = 8;
                    fns.bailout = fns.bailout < 13 ? 13 : fns.bailout;
                }
                break;
            case MAGNET2:
                if (fns.julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 56;
                    fns.bailout = fns.bailout < 13 ? 13 : fns.bailout;
                } else {
                    xCenter = 1;
                    yCenter = 0;
                    size = 7;
                    fns.bailout = fns.bailout < 13 ? 13 : fns.bailout;
                }
                break;
            case NEWTON3:
            case NEWTON4:
            case NEWTONGENERALIZED3:
            case NEWTONGENERALIZED8:
            case NEWTONSIN:
            case NEWTONCOS:
            case NEWTONPOLY:
            case NEWTONFORMULA:
            case HALLEY3:
            case HALLEY4:
            case HALLEYGENERALIZED3:
            case HALLEYGENERALIZED8:
            case HALLEYSIN:
            case HALLEYCOS:
            case HALLEYPOLY:
            case HALLEYFORMULA:
            case SCHRODER3:
            case SCHRODER4:
            case SCHRODERGENERALIZED3:
            case SCHRODERGENERALIZED8:
            case SCHRODERSIN:
            case SCHRODERCOS:
            case SCHRODERPOLY:
            case SCHRODERFORMULA:
            case HOUSEHOLDER3:
            case HOUSEHOLDER4:
            case HOUSEHOLDERGENERALIZED3:
            case HOUSEHOLDERGENERALIZED8:
            case HOUSEHOLDERSIN:
            case HOUSEHOLDERCOS:
            case HOUSEHOLDERPOLY:
            case HOUSEHOLDERFORMULA:
            case SECANT3:
            case SECANT4:
            case SECANTGENERALIZED3:
            case SECANTGENERALIZED8:
            case SECANTCOS:
            case SECANTPOLY:
            case SECANTFORMULA:
            case STEFFENSEN3:
            case STEFFENSEN4:
            case STEFFENSENGENERALIZED3:
            case STEFFENSENPOLY:
            case STEFFENSENFORMULA:
            case MULLER3:
            case MULLER4:
            case MULLERGENERALIZED3:
            case MULLERGENERALIZED8:
            case MULLERSIN:
            case MULLERCOS:
            case MULLERPOLY:
            case MULLERFORMULA:
            case PARHALLEY3:
            case PARHALLEY4:
            case PARHALLEYGENERALIZED3:
            case PARHALLEYGENERALIZED8:
            case PARHALLEYSIN:
            case PARHALLEYCOS:
            case PARHALLEYPOLY:
            case PARHALLEYFORMULA:
            case LAGUERRE3:
            case LAGUERRE4:
            case LAGUERREGENERALIZED3:
            case LAGUERREGENERALIZED8:
            case LAGUERRESIN:
            case LAGUERRECOS:
            case LAGUERREPOLY:
            case LAGUERREFORMULA:
            case DURAND_KERNER3:
            case DURAND_KERNER4:
            case DURAND_KERNERGENERALIZED3:
            case DURAND_KERNERGENERALIZED8:
            case DURAND_KERNERPOLY:
            case NOVA:
            case MAGNETIC_PENDULUM:
            case BAIRSTOW3:
            case BAIRSTOW4:
            case BAIRSTOWGENERALIZED3:
            case BAIRSTOWGENERALIZED8:
            case BAIRSTOWPOLY:
            case NEWTON_HINES3:
            case NEWTON_HINES4:
            case NEWTON_HINESGENERALIZED3:
            case NEWTON_HINESGENERALIZED8:
            case NEWTON_HINESSIN:
            case NEWTON_HINESCOS:
            case NEWTON_HINESPOLY:
            case NEWTON_HINESFORMULA:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case BARNSLEY1:
            case BARNSLEY2:
                xCenter = 0;
                yCenter = 0;
                size = 7;
                fns.bailout = fns.bailout < 2 ? 2 : fns.bailout;
                break;
            case SIERPINSKI_GASKET:
                xCenter = 0.5;
                yCenter = 0.5;
                size = 3;
                fns.bailout = fns.bailout < 100 ? 100 : fns.bailout;
                break;
            case KLEINIAN:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                break;
            case EXP:
            case LOG:
            case SIN:
            case COS:
            case TAN:
            case COT:
            case SINH:
            case COSH:
            case TANH:
            case COTH:
            case FORMULA30:
            case FORMULA31:
            case FORMULA18:
            case FORMULA34:
            case FORMULA39:
            case FORMULA40:
            case FORMULA41:
            case COUPLED_MANDELBROT:
            case COUPLED_MANDELBROT_BURNING_SHIP:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                fns.bailout = fns.bailout < 8 ? 8 : fns.bailout;
                break;
            case FORMULA1:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                fns.bailout = fns.bailout < 8 ? 8 : fns.bailout;
                break;
            case FORMULA2:
            case FORMULA13:
            case FORMULA14:
            case FORMULA15:
            case FORMULA16:
            case FORMULA17:
            case FORMULA19:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                fns.bailout = fns.bailout < 4 ? 4 : fns.bailout;
                break;
            case FORMULA3:
            case FORMULA9:
            case FORMULA10:
                xCenter = 0;
                yCenter = 0;
                size = 3;
                fns.bailout = fns.bailout < 4 ? 4 : fns.bailout;
                break;
            case FORMULA4:
            case FORMULA5:
                xCenter = 0;
                yCenter = 0;
                size = 1.5;
                fns.bailout = fns.bailout < 4 ? 4 : fns.bailout;
                break;
            case FORMULA7:
            case FORMULA12:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                fns.bailout = fns.bailout < 8 ? 8 : fns.bailout;
                break;
            case FORMULA8:
                xCenter = 0;
                yCenter = 0;
                size = 3;
                fns.bailout = fns.bailout < 16 ? 16 : fns.bailout;
                break;
            case FORMULA11:
                xCenter = 0;
                yCenter = 0;
                size = 1.5;
                fns.bailout = fns.bailout < 100 ? 100 : fns.bailout;
                break;
            case FORMULA27:
                if (fns.julia) {
                    xCenter = -2;
                    yCenter = 0;
                    size = 6;
                    fns.bailout = fns.bailout < 8 ? 8 : fns.bailout;
                } else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 3;
                    fns.bailout = fns.bailout < 8 ? 8 : fns.bailout;
                }
                break;
            case FORMULA28:
            case FORMULA29:
                if (fns.julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 12;
                    fns.bailout = fns.bailout < 16 ? 16 : fns.bailout;
                } else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 3;
                    fns.bailout = fns.bailout < 16 ? 16 : fns.bailout;
                }
                break;
            case FORMULA32:
            case FORMULA33:
            case FORMULA35:
            case FORMULA36:
            case FORMULA37:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                fns.bailout = fns.bailout < 16 ? 16 : fns.bailout;
                break;
            case FORMULA38:
                if (fns.julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    fns.bailout = fns.bailout < 2 ? 2 : fns.bailout;
                } else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 1.5;
                    fns.bailout = fns.bailout < 2 ? 2 : fns.bailout;
                }
                break;
            case FORMULA42:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                fns.bailout = fns.bailout < 12 ? 12 : fns.bailout;
                break;
            case FORMULA43:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                fns.bailout = fns.bailout < 12 ? 12 : fns.bailout;
                break;
            case FORMULA44:
            case FORMULA45:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                fns.bailout = fns.bailout < 16 ? 16 : fns.bailout;
                break;
            case FORMULA46:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                fns.bailout = fns.bailout < 100 ? 100 : fns.bailout;
                break;
            case LYAPUNOV:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                fns.bailout = fns.bailout < 100 ? 100 : fns.bailout;
                break;
            case SZEGEDI_BUTTERFLY1:
            case SZEGEDI_BUTTERFLY2:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                fns.bailout = fns.bailout < 4 ? 4 : fns.bailout;
                break;
            default:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                fns.bailout = fns.bailout < 2 ? 2 : fns.bailout;
                break;
        }
    }

    public void createPoly() {

        int l;

        poly = "p(z) = ";

        for (l = 0; l < fns.coefficients.length - 2; l++) {
            if (fns.coefficients[l] != 0 || fns.coefficients_im[l] != 0) {
                if (poly.length() == 7) {
                    poly += "(" + Complex.toString2(fns.coefficients[l], fns.coefficients_im[l]) + ")*z^" + (fns.coefficients.length - l - 1) + "  ";
                } else {
                    poly += "+ (" + Complex.toString2(fns.coefficients[l], fns.coefficients_im[l]) + ")*z^" + (fns.coefficients.length - l - 1) + "  ";
                }
            }
        }

        if (fns.coefficients[l] != 0 || fns.coefficients_im[l] != 0) {
            if (poly.length() == 7) {
                poly += "(" + Complex.toString2(fns.coefficients[l], fns.coefficients_im[l]) + ")*z  ";
            } else {
                poly += "+ (" + Complex.toString2(fns.coefficients[l], fns.coefficients_im[l]) + ")*z  ";
            }
        }

        l++;
        if (fns.coefficients[l] != 0 || fns.coefficients_im[l] != 0) {
            if (poly.length() == 7) {
                poly += Complex.toString2(fns.coefficients[l], fns.coefficients_im[l]);
            } else {
                poly += "+ (" + Complex.toString2(fns.coefficients[l], fns.coefficients_im[l]) + ")";
            }

        }
    }

    public boolean isConvergingType() {

        return isRootFindingMethod()
                || fns.function == NOVA
                || fns.function == USER_FORMULA_NOVA
                || fns.function == LAMBERT_W_VARIATION
                || (fns.function == USER_FORMULA && fns.bail_technique == 1) || (fns.function == USER_FORMULA_ITERATION_BASED && fns.bail_technique == 1) || (fns.function == USER_FORMULA_CONDITIONAL && fns.bail_technique == 1) || (fns.function == USER_FORMULA_COUPLED && fns.bail_technique == 1);

    }

    public boolean isMagnetType() {

        return fns.function == MAGNET1 || fns.function == MAGNET2;

    }

    public boolean isRootFindingMethod() {

        return fns.function == NEWTON3 || fns.function == NEWTON4 || fns.function == NEWTONGENERALIZED3 || fns.function == NEWTONGENERALIZED8 || fns.function == NEWTONSIN || fns.function == NEWTONCOS || fns.function == NEWTONPOLY || fns.function == NEWTONFORMULA
                || fns.function == HALLEY3 || fns.function == HALLEY4 || fns.function == HALLEYGENERALIZED3 || fns.function == HALLEYGENERALIZED8 || fns.function == HALLEYSIN || fns.function == HALLEYCOS || fns.function == HALLEYPOLY || fns.function == HALLEYFORMULA
                || fns.function == SCHRODER3 || fns.function == SCHRODER4 || fns.function == SCHRODERGENERALIZED3 || fns.function == SCHRODERGENERALIZED8 || fns.function == SCHRODERSIN || fns.function == SCHRODERCOS || fns.function == SCHRODERPOLY || fns.function == SCHRODERFORMULA
                || fns.function == HOUSEHOLDER3 || fns.function == HOUSEHOLDER4 || fns.function == HOUSEHOLDERGENERALIZED3 || fns.function == HOUSEHOLDERGENERALIZED8 || fns.function == HOUSEHOLDERSIN || fns.function == HOUSEHOLDERCOS || fns.function == HOUSEHOLDERPOLY || fns.function == HOUSEHOLDERFORMULA
                || fns.function == SECANT3 || fns.function == SECANT4 || fns.function == SECANTGENERALIZED3 || fns.function == SECANTGENERALIZED8 || fns.function == SECANTCOS || fns.function == SECANTPOLY || fns.function == SECANTFORMULA
                || fns.function == STEFFENSEN3 || fns.function == STEFFENSEN4 || fns.function == STEFFENSENGENERALIZED3 || fns.function == STEFFENSENPOLY || fns.function == STEFFENSENFORMULA
                || fns.function == MULLER3 || fns.function == MULLER4 || fns.function == MULLERGENERALIZED3 || fns.function == MULLERGENERALIZED8 || fns.function == MULLERSIN || fns.function == MULLERCOS || fns.function == MULLERPOLY || fns.function == MULLERFORMULA
                || fns.function == PARHALLEY3 || fns.function == PARHALLEY4 || fns.function == PARHALLEYGENERALIZED3 || fns.function == PARHALLEYGENERALIZED8 || fns.function == PARHALLEYSIN || fns.function == PARHALLEYCOS || fns.function == PARHALLEYPOLY || fns.function == PARHALLEYFORMULA
                || fns.function == LAGUERRE3 || fns.function == LAGUERRE4 || fns.function == LAGUERREGENERALIZED3 || fns.function == LAGUERREGENERALIZED8 || fns.function == LAGUERRESIN || fns.function == LAGUERRECOS || fns.function == LAGUERREPOLY || fns.function == LAGUERREFORMULA
                || fns.function == DURAND_KERNER3 || fns.function == DURAND_KERNER4 || fns.function == DURAND_KERNERGENERALIZED3 || fns.function == DURAND_KERNERGENERALIZED8 || fns.function == DURAND_KERNERPOLY
                || fns.function == BAIRSTOW3 || fns.function == BAIRSTOW4 || fns.function == BAIRSTOWGENERALIZED3 || fns.function == BAIRSTOWGENERALIZED8 || fns.function == BAIRSTOWPOLY
                || fns.function == MAGNETIC_PENDULUM
                || fns.function == NEWTON_HINES3 || fns.function == NEWTON_HINES4 || fns.function == NEWTON_HINESGENERALIZED3 || fns.function == NEWTON_HINESGENERALIZED8 || fns.function == NEWTON_HINESSIN || fns.function == NEWTON_HINESCOS || fns.function == NEWTON_HINESPOLY || fns.function == NEWTON_HINESFORMULA;

    }

    public boolean functionSupportsC() {

        return !isRootFindingMethod() && fns.function != KLEINIAN && fns.function != SIERPINSKI_GASKET && fns.function != INERTIA_GRAVITY
                && (fns.function != USER_FORMULA_NOVA || (fns.function == USER_FORMULA_NOVA && userFormulaHasC == true)) && (fns.function != USER_FORMULA || (fns.function == USER_FORMULA && userFormulaHasC == true)) && (fns.function != USER_FORMULA_ITERATION_BASED || (fns.function == USER_FORMULA_ITERATION_BASED && userFormulaHasC == true)) && (fns.function != USER_FORMULA_CONDITIONAL || (fns.function == USER_FORMULA_CONDITIONAL && userFormulaHasC == true)) && (fns.function != USER_FORMULA_COUPLED || (fns.function == USER_FORMULA_COUPLED && userFormulaHasC == true))
                && (fns.function != LYAPUNOV || (fns.function == LYAPUNOV && userFormulaHasC == true));

    }

    public void applyStaticSettings() {

        if (ps.color_choice == CUSTOM_PALETTE_ID) {
            ThreadDraw.palette_outcoloring = new CustomPalette(ps.custom_palette, ps.color_interpolation, ps.color_space, ps.reversed_palette, ps.scale_factor_palette_val, ps.processing_alg, fns.smoothing, special_color, color_smoothing_method, special_use_palette_color).getRawPalette();
        } else {
            ThreadDraw.palette_outcoloring = new PresetPalette(ps.color_choice, ps.direct_palette, fns.smoothing, special_color, color_smoothing_method, special_use_palette_color).getRawPalette();
        }

        if (ps2.color_choice == CUSTOM_PALETTE_ID) {
            ThreadDraw.palette_incoloring = new CustomPalette(ps2.custom_palette, ps2.color_interpolation, ps2.color_space, ps2.reversed_palette, ps2.scale_factor_palette_val, ps2.processing_alg, fns.smoothing, special_color, color_smoothing_method, special_use_palette_color).getRawPalette();
        } else {
            ThreadDraw.palette_incoloring = new PresetPalette(ps2.color_choice, ps2.direct_palette, fns.smoothing, special_color, color_smoothing_method, special_use_palette_color).getRawPalette();
        }

        ThreadDraw.USE_DIRECT_COLOR = useDirectColor;

        ColorAlgorithm.GlobalIncrementBypass = globalIncrementBypass;

        ThreadDraw.gradient = CustomPalette.createGradient(gs.colorA.getRGB(), gs.colorB.getRGB(), Constants.GRADIENT_LENGTH, gs.gradient_interpolation, gs.gradient_color_space, gs.gradient_reversed, 0);

        ThreadDraw.COLOR_SMOOTHING_METHOD = color_smoothing_method;

        SkipBailoutCondition.SKIPPED_ITERATION_COUNT = fns.skip_bailout_iterations;

    }

    public void loadedSettings(String file, Component parent, int version) {
        String temp2 = "" + version;
        String versionStr = "";

        int i;
        for (i = 0; i < temp2.length() - 1; i++) {
            versionStr += temp2.charAt(i) + ".";
        }
        versionStr += temp2.charAt(i);

        JOptionPane.showMessageDialog(parent, file + " (version:  " + versionStr + ")\nwas successfully loaded.", "Settings Loaded", JOptionPane.INFORMATION_MESSAGE);
    }

}
