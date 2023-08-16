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
package fractalzoomer.main.app_settings;

import fractalzoomer.bailout_conditions.SkipBailoutCondition;
import fractalzoomer.convergent_bailout_conditions.SkipConvergentBailoutCondition;
import fractalzoomer.core.Complex;
import fractalzoomer.core.Derivative;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.core.TaskDraw;
import fractalzoomer.fractal_options.orbit_traps.ImageOrbitTrap;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.palettes.CustomPalette;
import fractalzoomer.palettes.PresetPalette;
import fractalzoomer.parser.Parser;
import fractalzoomer.parser.ParserException;
import fractalzoomer.settings.*;
import fractalzoomer.utils.ColorAlgorithm;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author hrkalona2
 */
public class Settings implements Constants {

    public PostProcessSettings pps;
    public PaletteSettings ps;
    public PaletteSettings ps2;
    public boolean exterior_de;
    public double exterior_de_factor;
    public boolean polar_projection;
    public boolean inverse_dem;
    public DomainColoringSettings ds;
    public Apfloat xCenter;
    public Apfloat yCenter;
    public Apfloat xJuliaCenter;
    public Apfloat yJuliaCenter;
    public Apfloat size;
    public double height_ratio;
    public int max_iterations;
    public double circle_period;
    public int color_smoothing_method;
    public Color fractal_color;
    public Color dem_color;
    public Color special_color;
    public boolean special_use_palette_color;
    public BlendingSettings color_blending;
    public int temp_color_cycling_location;
    public int temp_color_cycling_location_second_palette;
    public D3Settings d3s;
    public FiltersSettings fs;
    public GradientSettings gs;
    public FunctionSettings fns;
    public PaletteGradientMergingSettings pbs;
    public boolean usePaletteForInColoring;
    public int[] post_processing_order;
    public String poly;
    public boolean userFormulaHasC;
    public Parser parser;
    public boolean useDirectColor;
    public boolean globalIncrementBypass;
    public boolean julia_map;
    public double contourFactor;
    public int MagnetColorOffset;
    public GeneratedPaletteSettings gps;
    public JitterSettings js;
    public float hsb_constant_b;
    public float hsb_constant_s;
    public double lchab_constant_l;
    public double lchab_constant_c;
    public double lchuv_constant_l;
    public double lchuv_constant_c;


    public Settings() {
        defaultValues();
    }

    public void defaultValues() {

        Parser.CURRENT_USER_CODE_FILE = Parser.DEFAULT_USER_CODE_FILE;
        Parser.CURRENT_USER_CODE_CLASS = Parser.DEFAULT_USER_CODE_CLASS;

        parser = new Parser(true);

        try {
            Parser.compileUserFunctions();
        }
        catch (ParserException ex) {

        }
        catch (Exception ex) {}

        hsb_constant_b = 1;
        hsb_constant_s = 1;
        lchab_constant_l = 50;
        lchab_constant_c = 100;
        lchuv_constant_l = 50;
        lchuv_constant_c = 130;


        userFormulaHasC = true;
        useDirectColor = false;
        globalIncrementBypass = false;

        xCenter = new MyApfloat(0.0);
        yCenter = new MyApfloat(0.0);

        xJuliaCenter = new MyApfloat(0.0);
        yJuliaCenter = new MyApfloat(0.0);

        size = new MyApfloat(6);

        height_ratio = 1;
        max_iterations = 500;

        julia_map = false;

        fns = new FunctionSettings();
        ds = new DomainColoringSettings();
        fs = new FiltersSettings();
        gs = new GradientSettings();
        pbs = new PaletteGradientMergingSettings();

        ps = new PaletteSettings();
        ps2 = new PaletteSettings();

        gps = new GeneratedPaletteSettings();
        js = new JitterSettings();
        pps = new PostProcessSettings();

        usePaletteForInColoring = false;

        color_blending = new BlendingSettings();

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
        dem_color = new Color(1, 1, 1);
        special_color = Color.WHITE;

        d3s = new D3Settings();

        contourFactor = 2;

        MagnetColorOffset = ColorAlgorithm.MAGNET_INCREMENT_DEFAULT;

        post_processing_order = new int[TOTAL_POST_PROCESS_ALGORITHMS];
        defaultProcessingOrder();

    }

    private void defaultProcessingOrder() {

        post_processing_order[0] = HISTOGRAM_COLORING;
        post_processing_order[1] = OFFSET_COLORING;
        post_processing_order[2] = ENTROPY_COLORING;
        post_processing_order[3] = RAINBOW_PALETTE;
        post_processing_order[4] = NUMERICAL_DISTANCE_ESTIMATOR;
        post_processing_order[5] = CONTOUR_COLORING;
        post_processing_order[6] = GREYSCALE_COLORING;
        post_processing_order[7] = BUMP_MAPPING;
        post_processing_order[8] = LIGHT;
        post_processing_order[9] = SLOPES;
        post_processing_order[10] = FAKE_DISTANCE_ESTIMATION;

    }

    public void readSettings(String filename, Component parent, boolean silent) throws FileNotFoundException, IOException, ClassNotFoundException, Exception {

        ObjectInputStream file_temp = new ObjectInputStream(new FileInputStream(filename));

        SettingsFractals settings = (SettingsFractals) file_temp.readObject();

        int version = settings.getVersion();

        Settings defaults = new Settings();
        julia_map = false;

        String xCenterStr;
        String yCenterStr;
        String sizeStr;

        if(version < 1078) {
            xCenterStr = "" + settings.getXCenter();
            yCenterStr = "" + settings.getYCenter();
            sizeStr = "" + settings.getSize();
        }
        else {
            xCenterStr = ((SettingsFractals1078) settings).getxCenterStr();
            yCenterStr = ((SettingsFractals1078) settings).getyCenterStr();
            sizeStr = ((SettingsFractals1078) settings).getSizeStr();
        }

        if (settings.isJulia()) {

            String xJuliaCenterStr = "";
            String yJuliaCenterStr = "";
            double xJuliaCenterD = 0;
            double yJuliaCenterD = 0;

            if (version == 1048) {
                xJuliaCenterD = ((SettingsJulia) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia) settings).getYJuliaCenter();
            } else if (version == 1049) {
                xJuliaCenterD = ((SettingsJulia1049) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1049) settings).getYJuliaCenter();
            } else if (version == 1050) {
                xJuliaCenterD = ((SettingsJulia1050) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1050) settings).getYJuliaCenter();
            } else if (version == 1053) {
                xJuliaCenterD = ((SettingsJulia1053) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1053) settings).getYJuliaCenter();
            } else if (version == 1054) {
                xJuliaCenterD = ((SettingsJulia1054) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1054) settings).getYJuliaCenter();
            } else if (version == 1055) {
                xJuliaCenterD = ((SettingsJulia1055) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1055) settings).getYJuliaCenter();
            } else if (version == 1056) {
                xJuliaCenterD = ((SettingsJulia1056) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1056) settings).getYJuliaCenter();
            } else if (version == 1057) {
                xJuliaCenterD = ((SettingsJulia1057) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1057) settings).getYJuliaCenter();
            } else if (version == 1058) {
                xJuliaCenterD = ((SettingsJulia1058) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1058) settings).getYJuliaCenter();
            } else if (version == 1061) {
                xJuliaCenterD = ((SettingsJulia1061) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1061) settings).getYJuliaCenter();
            } else if (version == 1062) {
                xJuliaCenterD = ((SettingsJulia1062) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1062) settings).getYJuliaCenter();
            } else if (version == 1063) {
                xJuliaCenterD = ((SettingsJulia1063) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1063) settings).getYJuliaCenter();
            } else if (version == 1064) {
                xJuliaCenterD = ((SettingsJulia1064) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1064) settings).getYJuliaCenter();
            } else if (version == 1065) {
                xJuliaCenterD = ((SettingsJulia1065) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1065) settings).getYJuliaCenter();
            } else if (version == 1066) {
                xJuliaCenterD = ((SettingsJulia1066) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1066) settings).getYJuliaCenter();
            } else if (version == 1067) {
                xJuliaCenterD = ((SettingsJulia1067) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1067) settings).getYJuliaCenter();
            } else if (version == 1068) {
                xJuliaCenterD = ((SettingsJulia1068) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1068) settings).getYJuliaCenter();
            } else if (version == 1069) {
                xJuliaCenterD = ((SettingsJulia1069) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1069) settings).getYJuliaCenter();
            } else if (version == 1070) {
                xJuliaCenterD = ((SettingsJulia1070) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1070) settings).getYJuliaCenter();
            } else if (version == 1071) {
                xJuliaCenterD = ((SettingsJulia1071) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1071) settings).getYJuliaCenter();
            } else if (version == 1072) {
                xJuliaCenterD = ((SettingsJulia1072) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1072) settings).getYJuliaCenter();
            } else if (version == 1073) {
                xJuliaCenterD = ((SettingsJulia1073) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1073) settings).getYJuliaCenter();
            } else if (version == 1074) {
                xJuliaCenterD = ((SettingsJulia1074) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1074) settings).getYJuliaCenter();
            }
            else if (version == 1075) {
                xJuliaCenterD = ((SettingsJulia1075) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1075) settings).getYJuliaCenter();
            }
            else if (version == 1076) {
                xJuliaCenterD = ((SettingsJulia1076) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1076) settings).getYJuliaCenter();
            }
            else if (version == 1077) {
                xJuliaCenterD = ((SettingsJulia1077) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1077) settings).getYJuliaCenter();
            }
            else if (version == 1078) {
                xJuliaCenterD = ((SettingsJulia1078) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1078) settings).getYJuliaCenter();
            }
            else if (version == 1079) {
                xJuliaCenterD = ((SettingsJulia1079) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1079) settings).getYJuliaCenter();
            }
            else if (version == 1080) {
                xJuliaCenterD = ((SettingsJulia1080) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1080) settings).getYJuliaCenter();
            }
            else if (version == 1081) {
                xJuliaCenterD = ((SettingsJulia1081) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1081) settings).getYJuliaCenter();
            }
            else if (version == 1083) {
                xJuliaCenterD = ((SettingsJulia1083) settings).getXJuliaCenter();
                yJuliaCenterD = ((SettingsJulia1083) settings).getYJuliaCenter();
            }
            else if (version == 1084) {
                xJuliaCenterStr = ((SettingsJulia1084) settings).getXJuliaCenterStr();
                yJuliaCenterStr = ((SettingsJulia1084) settings).getYJuliaCenterStr();
            }
            else if (version >= 1085) {
                xJuliaCenterStr = ((SettingsFractals1085) settings).getXJuliaCenterStr();
                yJuliaCenterStr = ((SettingsFractals1085) settings).getYJuliaCenterStr();
            }


            if(version < 1084) {
                xJuliaCenterStr = "" + xJuliaCenterD;
                yJuliaCenterStr = "" + yJuliaCenterD;
            }

            if(MyApfloat.setAutomaticPrecision) {

                long precision = MyApfloat.getAutomaticPrecision(new String[]{sizeStr, xCenterStr, yCenterStr, xJuliaCenterStr, yJuliaCenterStr}, new boolean[] {true, false, false, false, false});

                if (MyApfloat.shouldSetPrecision(precision, true)) {
                    Fractal.clearReferences(true, true);
                    MyApfloat.setPrecision(precision, this);
                }
            }

            if(version < 1084) {
                xJuliaCenter = new MyApfloat(xJuliaCenterD);
                yJuliaCenter = new MyApfloat(yJuliaCenterD);
            }
            else {
                xJuliaCenter = new MyApfloat(xJuliaCenterStr);
                yJuliaCenter = new MyApfloat(yJuliaCenterStr);
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

            if(MyApfloat.setAutomaticPrecision) {

                long precision = MyApfloat.getAutomaticPrecision(new String[]{sizeStr, xCenterStr, yCenterStr}, new boolean[] {true, false, false});

                if (MyApfloat.shouldSetPrecision(precision, true)) {
                    Fractal.clearReferences(true, true);
                    MyApfloat.setPrecision(precision, this);
                }
            }
        }

        if(version < 1078) {
            xCenter = new MyApfloat(settings.getXCenter());
            yCenter = new MyApfloat(settings.getYCenter());
            size = new MyApfloat(settings.getSize());
        }
        else {
            xCenter = new MyApfloat(((SettingsFractals1078) settings).getxCenterStr());
            yCenter = new MyApfloat(((SettingsFractals1078) settings).getyCenterStr());
            size = new MyApfloat(((SettingsFractals1078) settings).getSizeStr());
        }

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
            pps.rps.rainbow_palette = defaults.pps.rps.rainbow_palette;
            pps.rps.rainbow_palette_factor = defaults.pps.rps.rainbow_palette_factor;

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
            pps.rps.rainbow_palette = ((SettingsFractals1062) settings).getRainbowPalette();
            pps.rps.rainbow_palette_factor = ((SettingsFractals1062) settings).getRainbowPaletteFactor();

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
            pps.cns.contour_coloring = defaults.pps.cns.contour_coloring;
            pps.cns.cn_noise_reducing_factor = defaults.pps.cns.cn_noise_reducing_factor;
            pps.cns.cn_blending = defaults.pps.cns.cn_blending;
            pps.cns.contour_algorithm = defaults.pps.cns.contour_algorithm;

            useDirectColor = defaults.useDirectColor;

            fns.kleinianLine[0] = defaults.fns.kleinianLine[0];
            fns.kleinianLine[1] = defaults.fns.kleinianLine[1];
            fns.kleinianK = defaults.fns.kleinianK;
            fns.kleinianM = defaults.fns.kleinianM;

            pps.ots.lineType = defaults.pps.ots.lineType;
        } else {
            pps.cns.contour_coloring = ((SettingsFractals1069) settings).getContourColoring();
            pps.cns.cn_noise_reducing_factor = ((SettingsFractals1069) settings).getContourColoringNoiseReducingFactor();
            pps.cns.cn_blending = ((SettingsFractals1069) settings).getContourColoringBlending();
            pps.cns.contour_algorithm = ((SettingsFractals1069) settings).getContourColoringAlgorithm();

            useDirectColor = ((SettingsFractals1069) settings).getDirectColor();

            fns.kleinianLine = ((SettingsFractals1069) settings).getKleinianLine();
            fns.kleinianK = ((SettingsFractals1069) settings).getKleinianK();
            fns.kleinianM = ((SettingsFractals1069) settings).getKleinianM();

            pps.ots.lineType = ((SettingsFractals1069) settings).getLineType();
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
            pps.ens.entropy_algorithm = defaults.pps.ens.entropy_algorithm;

            ds.domainOrder[0] = defaults.ds.domainOrder[0];
            ds.domainOrder[1] = defaults.ds.domainOrder[1];
            ds.domainOrder[2] = defaults.ds.domainOrder[2];

            gs.colorA = defaults.gs.colorA;
            gs.colorB = defaults.gs.colorB;
            gs.gradient_color_space = defaults.gs.gradient_color_space;
            gs.gradient_interpolation = defaults.gs.gradient_interpolation;
            gs.gradient_reversed = defaults.gs.gradient_reversed;

            pps.rps.rainbow_algorithm = defaults.pps.rps.rainbow_algorithm;

            pps.ots.useTraps = defaults.pps.ots.useTraps;
            pps.ots.trapType = defaults.pps.ots.trapType;
            pps.ots.trapPoint[0] = defaults.pps.ots.trapPoint[0];
            pps.ots.trapPoint[1] = defaults.pps.ots.trapPoint[1];
            pps.ots.trapLength = defaults.pps.ots.trapLength;
            pps.ots.trapWidth = defaults.pps.ots.trapWidth;
            pps.ots.trapBlending = defaults.pps.ots.trapBlending;
            pps.ots.trapNorm = defaults.pps.ots.trapNorm;
            pps.ots.trapMaxDistance = defaults.pps.ots.trapMaxDistance;
        } else {
            pps.ens.entropy_algorithm = ((SettingsFractals1068) settings).getEntropyAlgorithm();

            ds.domainOrder = ((SettingsFractals1068) settings).getDomainOrder();

            gs.colorA = ((SettingsFractals1068) settings).getColorA();
            gs.colorB = ((SettingsFractals1068) settings).getColorB();
            gs.gradient_color_space = ((SettingsFractals1068) settings).getGradientColorSpace();
            gs.gradient_interpolation = ((SettingsFractals1068) settings).getGradientInterpolation();
            gs.gradient_reversed = ((SettingsFractals1068) settings).getGradientReversed();

            pps.rps.rainbow_algorithm = ((SettingsFractals1068) settings).getRainbowAlgorithm();

            pps.ots.useTraps = ((SettingsFractals1068) settings).getUseTraps();
            pps.ots.trapType = ((SettingsFractals1068) settings).getTrapType();
            pps.ots.trapPoint = ((SettingsFractals1068) settings).getTrapPoint();
            pps.ots.trapLength = ((SettingsFractals1068) settings).getTrapLength();
            pps.ots.trapWidth = ((SettingsFractals1068) settings).getTrapWidth();
            pps.ots.trapBlending = ((SettingsFractals1068) settings).getTrapBlending();
            pps.ots.trapNorm = ((SettingsFractals1068) settings).getTrapNorm();
            pps.ots.trapMaxDistance = ((SettingsFractals1068) settings).getTrapMaxDistance();
        }

        if (version < 1067) {
            ps.transfer_function = defaults.ps.transfer_function;
            color_blending.color_blending = defaults.color_blending.color_blending;

            pps.bms.bump_transfer_function = defaults.pps.bms.bump_transfer_function;
            pps.bms.bump_transfer_factor = defaults.pps.bms.bump_transfer_factor;
            pps.bms.bump_blending = defaults.pps.bms.bump_blending;
            pps.bms.bumpProcessing = defaults.pps.bms.bumpProcessing;

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
            color_blending.color_blending = ((SettingsFractals1067) settings).getColorBlending();

            pps.bms.bump_transfer_function = ((SettingsFractals1067) settings).getBumpTransferFunction();
            pps.bms.bump_transfer_factor = ((SettingsFractals1067) settings).getBumpTransferFactor();
            pps.bms.bump_blending = ((SettingsFractals1067) settings).getBumpBlending();
            pps.bms.bumpProcessing = ((SettingsFractals1067) settings).getBumpProcessing();

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
            pps.ens.entropy_coloring = defaults.pps.ens.entropy_coloring;
            pps.ens.entropy_palette_factor = defaults.pps.ens.entropy_palette_factor;
            pps.ens.en_noise_reducing_factor = defaults.pps.ens.en_noise_reducing_factor;
            fns.apply_plane_on_julia_seed = defaults.fns.apply_plane_on_julia_seed;
            pps.ofs.offset_coloring = defaults.pps.ofs.offset_coloring;
            pps.ofs.post_process_offset = defaults.pps.ofs.post_process_offset;
            pps.ofs.of_noise_reducing_factor = defaults.pps.ofs.of_noise_reducing_factor;
            pps.ens.en_blending = defaults.pps.ens.en_blending;
            pps.ofs.of_blending = defaults.pps.ofs.of_blending;
            pps.rps.rp_blending = defaults.pps.rps.rp_blending;
            pps.ens.entropy_offset = defaults.pps.ens.entropy_offset;
            pps.rps.rainbow_offset = defaults.pps.rps.rainbow_offset;
            pps.gss.greyscale_coloring = defaults.pps.gss.greyscale_coloring;
            pps.gss.gs_noise_reducing_factor = defaults.pps.gss.gs_noise_reducing_factor;
        } else {
            pps.ens.entropy_coloring = ((SettingsFractals1066) settings).getEntropyColoring();
            pps.ens.entropy_palette_factor = ((SettingsFractals1066) settings).getEntropyPaletteFactor();
            pps.ens.en_noise_reducing_factor = ((SettingsFractals1066) settings).getEntropyColoringNoiseReducingFactor();
            fns.apply_plane_on_julia_seed = ((SettingsFractals1066) settings).getApplyPlaneOnJuliaSeed();
            pps.ofs.offset_coloring = ((SettingsFractals1066) settings).getOffsetColoring();
            pps.ofs.post_process_offset = ((SettingsFractals1066) settings).getPostProcessOffset();
            pps.ofs.of_noise_reducing_factor = ((SettingsFractals1066) settings).getOffsetColoringNoiseReducingFactor();
            pps.ens.en_blending = ((SettingsFractals1066) settings).getEntropyColoringBlending();
            pps.ofs.of_blending = ((SettingsFractals1066) settings).getOffsetColoringBlending();
            pps.rps.rp_blending = ((SettingsFractals1066) settings).getRainbowPaletteBlending();
            pps.ens.entropy_offset = ((SettingsFractals1066) settings).getEntropyColoringOffset();
            pps.rps.rainbow_offset = ((SettingsFractals1066) settings).getRainbowPaletteOffset();
            pps.gss.greyscale_coloring = ((SettingsFractals1066) settings).getGreyscaleColoring();
            pps.gss.gs_noise_reducing_factor = ((SettingsFractals1066) settings).getGreyscaleColoringNoiseReducingFactor();
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

            pps.cns.contourColorMethod = defaults.pps.cns.contourColorMethod;

            pps.ots.trapColorMethod = defaults.pps.ots.trapColorMethod;
            pps.ots.trapIntensity = defaults.pps.ots.trapIntensity;

            pps.sts.statistic = defaults.pps.sts.statistic;
            pps.sts.statistic_type = defaults.pps.sts.statistic_type;
            pps.sts.statistic_intensity = defaults.pps.sts.statistic_intensity;
            pps.sts.stripeAvgStripeDensity = defaults.pps.sts.stripeAvgStripeDensity;
            pps.sts.cosArgStripeDensity = defaults.pps.sts.cosArgStripeDensity;
            pps.sts.cosArgInvStripeDensity = defaults.pps.sts.cosArgInvStripeDensity;
            pps.sts.StripeDenominatorFactor = defaults.pps.sts.StripeDenominatorFactor;
            pps.sts.statisticGroup = defaults.pps.sts.statisticGroup;
            pps.sts.user_statistic_formula = defaults.pps.sts.user_statistic_formula;
            pps.sts.useAverage = defaults.pps.sts.useAverage;
            pps.sts.statistic_escape_type = defaults.pps.sts.statistic_escape_type;

            pps.ls.lighting = defaults.pps.ls.lighting;
            pps.ls.lightintensity = defaults.pps.ls.lightintensity;
            pps.ls.ambientlight = defaults.pps.ls.ambientlight;
            pps.ls.specularintensity = defaults.pps.ls.specularintensity;
            pps.ls.shininess = defaults.pps.ls.shininess;
            pps.ls.heightTransfer = defaults.pps.ls.heightTransfer;
            pps.ls.heightTransferFactor = defaults.pps.ls.heightTransferFactor;
            pps.ls.lightMode = defaults.pps.ls.lightMode;
            pps.ls.colorMode = defaults.pps.ls.colorMode;
            pps.ls.l_noise_reducing_factor = defaults.pps.ls.l_noise_reducing_factor;
            pps.ls.light_blending = defaults.pps.ls.light_blending;
            pps.ls.light_direction = defaults.pps.ls.light_direction;
            pps.ls.light_magnitude = defaults.pps.ls.light_magnitude;

            double lightAngleRadians = Math.toRadians(pps.ls.light_direction);
            pps.ls.lightVector[0] = Math.cos(lightAngleRadians) * pps.ls.light_magnitude;
            pps.ls.lightVector[1] = Math.sin(lightAngleRadians) * pps.ls.light_magnitude;
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

            pps.cns.contourColorMethod = ((SettingsFractals1071) settings).getContourColorMethod();

            pps.ots.trapColorMethod = ((SettingsFractals1071) settings).getTrapColorMethod();
            pps.ots.trapIntensity = ((SettingsFractals1071) settings).getTrapIntesity();

            pps.sts.statistic = ((SettingsFractals1071) settings).getStatistic();
            pps.sts.statistic_type = ((SettingsFractals1071) settings).getStatisticType();
            pps.sts.statistic_intensity = ((SettingsFractals1071) settings).getStatisticIntensity();
            pps.sts.stripeAvgStripeDensity = ((SettingsFractals1071) settings).getStripeAvgStripeDensity();
            pps.sts.cosArgStripeDensity = ((SettingsFractals1071) settings).getCosArgStripeDensity();
            pps.sts.cosArgInvStripeDensity = ((SettingsFractals1071) settings).getCosArgInvStripeDensity();
            pps.sts.StripeDenominatorFactor = ((SettingsFractals1071) settings).getStripeDenominatorFactor();
            pps.sts.statisticGroup = ((SettingsFractals1071) settings).getStatisticGroup();
            pps.sts.user_statistic_formula = ((SettingsFractals1071) settings).getUserStatisticFormula();
            pps.sts.useAverage = ((SettingsFractals1071) settings).getUseAverage();
            pps.sts.statistic_escape_type = ((SettingsFractals1071) settings).getStatisticEscapeType();

            pps.ls.lighting = ((SettingsFractals1071) settings).getLighting();
            pps.ls.lightintensity = ((SettingsFractals1071) settings).getLightintensity();
            pps.ls.ambientlight = ((SettingsFractals1071) settings).getAmbientlight();
            pps.ls.specularintensity = ((SettingsFractals1071) settings).getSpecularintensity();
            pps.ls.shininess = ((SettingsFractals1071) settings).getShininess();
            pps.ls.heightTransfer = ((SettingsFractals1071) settings).getLightHeightTransfer();
            pps.ls.heightTransferFactor = ((SettingsFractals1071) settings).getLightHeightTransferFactor();
            pps.ls.lightMode = ((SettingsFractals1071) settings).getLightMode();
            pps.ls.colorMode = ((SettingsFractals1071) settings).getLightColorMode();
            pps.ls.l_noise_reducing_factor = ((SettingsFractals1071) settings).getLightNoiseReducingFactor();
            pps.ls.light_blending = ((SettingsFractals1071) settings).getLightBlending();
            pps.ls.light_direction = ((SettingsFractals1071) settings).getLightDirection();
            pps.ls.light_magnitude = ((SettingsFractals1071) settings).getLightMagnitude();

            double lightAngleRadians = Math.toRadians(pps.ls.light_direction);
            pps.ls.lightVector[0] = Math.cos(lightAngleRadians) * pps.ls.light_magnitude;
            pps.ls.lightVector[1] = Math.sin(lightAngleRadians) * pps.ls.light_magnitude;
        }

        if (version < 1072) {
            pps.ots.trapColor1 = defaults.pps.ots.trapColor1;
            pps.ots.trapColor2 = defaults.pps.ots.trapColor2;
            pps.ots.trapColor3 = defaults.pps.ots.trapColor3;
            pps.ots.trapColorInterpolation = defaults.pps.ots.trapColorInterpolation;
            pps.ots.trapIncludeNotEscaped = defaults.pps.ots.trapIncludeNotEscaped;
            pps.ots.trapIncludeEscaped = defaults.pps.ots.trapIncludeEscaped;

            pps.sts.statisticIncludeEscaped = defaults.pps.sts.statisticIncludeEscaped;
            pps.sts.statisticIncludeNotEscaped = defaults.pps.sts.statisticIncludeNotEscaped;

            ps.direct_palette = defaults.ps.direct_palette;
            ps2.direct_palette = defaults.ps2.direct_palette;
        } else {
            pps.ots.trapColor1 = ((SettingsFractals1072) settings).getTrapColor1();
            pps.ots.trapColor2 = ((SettingsFractals1072) settings).getTrapColor2();
            pps.ots.trapColor3 = ((SettingsFractals1072) settings).getTrapColor3();
            pps.ots.trapColorInterpolation = ((SettingsFractals1072) settings).getTrapColorInterpolation();
            pps.ots.trapIncludeNotEscaped = ((SettingsFractals1072) settings).getTrapIncludeNotEscaped();
            pps.ots.trapIncludeEscaped = ((SettingsFractals1072) settings).getTrapIncludeEscaped();

            pps.sts.statisticIncludeEscaped = ((SettingsFractals1072) settings).getStatisticIncludeEscaped();
            pps.sts.statisticIncludeNotEscaped = ((SettingsFractals1072) settings).getStatisticIncludeNotEscaped();

            if (ps.color_choice == DIRECT_PALETTE_ID) {
                ps.direct_palette = ((SettingsFractals1072) settings).getDirectPalette();
            }

            if (ps2.color_choice == DIRECT_PALETTE_ID) {
                ps2.direct_palette = ((SettingsFractals1072) settings).getDirectPalette2();
            }
        }

        if (version < 1073) {
            pps.sts.user_statistic_init_value = defaults.pps.sts.user_statistic_init_value;
            fns.skip_bailout_iterations = defaults.fns.skip_bailout_iterations;
            gs.gradient_offset = defaults.gs.gradient_offset;
        } else {
            pps.sts.user_statistic_init_value = ((SettingsFractals1073) settings).getUserStatisticInitValue();
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
            pps.sts.showAtomDomains = defaults.pps.sts.showAtomDomains;
            pps.sts.reductionFunction = defaults.pps.sts.reductionFunction;
            pps.sts.useIterations = defaults.pps.sts.useIterations;
            pps.sts.useSmoothing = defaults.pps.sts.useSmoothing;
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
            pps.sts.showAtomDomains = ((SettingsFractals1074) settings).getShowAtomDomains();
            pps.sts.reductionFunction = ((SettingsFractals1074) settings).getReductionFunction();
            pps.sts.useIterations = ((SettingsFractals1074) settings).getUseIterations();
            pps.sts.useSmoothing = ((SettingsFractals1074) settings).getUseSmoothing();
        }
        
        if(version < 1075) {
            fns.derivative_method = defaults.fns.derivative_method;
            pps.ots.trapColorFillingMethod = defaults.pps.ots.trapColorFillingMethod;
            ds.gridAlgorithm = defaults.ds.gridAlgorithm;
            ds.circleWidth = defaults.ds.circleWidth;
            ds.gridWidth = defaults.ds.gridWidth;
            pps.ots.trapImage = defaults.pps.ots.trapImage;
        }
        else {
            fns.derivative_method = ((SettingsFractals1075) settings).getDerivativeMethod();
            pps.ots.trapColorFillingMethod = ((SettingsFractals1075) settings).getTrapColorFillingMethod();
            ds.gridAlgorithm = ((SettingsFractals1075) settings).getGridAlgorithm();
            ds.circleWidth = ((SettingsFractals1075) settings).getCircleWidth();
            ds.gridWidth = ((SettingsFractals1075) settings).getGridWidth();
            
            if(pps.ots.trapType == IMAGE_TRAP || pps.ots.trapType == IMAGE_TRANSPARENT_TRAP) {
                pps.ots.trapImage = ((SettingsFractals1075) settings).getTrapImage();
            }
        }
        
        if(version < 1076) {
            pps.hss.histogramColoring = defaults.pps.hss.histogramColoring;
            pps.hss.histogramDensity = defaults.pps.hss.histogramDensity;
            pps.hss.hs_noise_reducing_factor = defaults.pps.hss.hs_noise_reducing_factor;
            pps.hss.histogramBinGranularity = defaults.pps.hss.histogramBinGranularity;
            pps.hss.hs_blending = defaults.pps.hss.hs_blending;
            pps.hss.histogramScaleMin = defaults.pps.hss.histogramScaleMin;
            pps.hss.histogramScaleMax = defaults.pps.hss.histogramScaleMax;
            fns.lpns.lyapunovInitializationIteratons = defaults.fns.lpns.lyapunovInitializationIteratons;
            fns.lpns.lyapunovskipBailoutCheck = defaults.fns.lpns.lyapunovskipBailoutCheck;
            pps.ots.trapCellularStructure = defaults.pps.ots.trapCellularStructure;
            pps.ots.trapCellularInverseColor = defaults.pps.ots.trapCellularInverseColor;
            pps.ots.trapCellularColor = defaults.pps.ots.trapCellularColor;
            pps.ots.countTrapIterations = defaults.pps.ots.countTrapIterations;
            pps.ots.trapCellularSize = defaults.pps.ots.trapCellularSize;
            ds.combineType = defaults.ds.combineType;
            pps.ots.trapHeightFunction = defaults.pps.ots.trapHeightFunction;
            pps.ots.invertTrapHeight = defaults.pps.ots.invertTrapHeight;
        }
        else {
            pps.hss.histogramColoring = ((SettingsFractals1076) settings).getHistogramColoring();
            pps.hss.histogramDensity = ((SettingsFractals1076) settings).getHistogramDensity();
            pps.hss.hs_noise_reducing_factor = ((SettingsFractals1076) settings).getHsNoiseReducingFactor();
            pps.hss.histogramBinGranularity = ((SettingsFractals1076) settings).getHistogramBinGranularity();
            pps.hss.hs_blending = ((SettingsFractals1076) settings).getHsBlending();
            pps.hss.histogramScaleMin = ((SettingsFractals1076) settings).getHistogramScaleMin();
            pps.hss.histogramScaleMax = ((SettingsFractals1076) settings).getHistogramScaleMax();
            fns.lpns.lyapunovInitializationIteratons = ((SettingsFractals1076) settings).getLyapunovInitializationIteratons();
            fns.lpns.lyapunovskipBailoutCheck = ((SettingsFractals1076) settings).getLyapunovskipBailoutCheck();
            pps.ots.trapCellularStructure = ((SettingsFractals1076) settings).getTrapCellularStructure();
            pps.ots.trapCellularInverseColor = ((SettingsFractals1076) settings).getTrapCellularInverseColor();
            pps.ots.trapCellularColor = ((SettingsFractals1076) settings).getTrapCellularColor();
            pps.ots.countTrapIterations = ((SettingsFractals1076) settings).getCountTrapIterations();
            pps.ots.trapCellularSize = ((SettingsFractals1076) settings).getTrapCellularSize();
            ds.combineType = ((SettingsFractals1076) settings).getCombineType();
            pps.ots.trapHeightFunction = ((SettingsFractals1076) settings).getTrapHeightFunction();
            pps.ots.invertTrapHeight = ((SettingsFractals1076) settings).getInvertTrapHeight();
        }

        if(version < 1077) {
            fns.preffs.functionFilter = defaults.fns.preffs.functionFilter;
            fns.preffs.userFormulaFunctionFilter = defaults.fns.preffs.userFormulaFunctionFilter;
            fns.preffs.user_function_filter_condition_formula = defaults.fns.preffs.user_function_filter_condition_formula;
            fns.preffs.user_function_filter_conditions = defaults.fns.preffs.user_function_filter_conditions;
            fns.preffs.user_function_filter_algorithm = defaults.fns.preffs.user_function_filter_algorithm;

            fns.postffs.functionFilter = defaults.fns.postffs.functionFilter;
            fns.postffs.userFormulaFunctionFilter = defaults.fns.postffs.userFormulaFunctionFilter;
            fns.postffs.user_function_filter_condition_formula = defaults.fns.postffs.user_function_filter_condition_formula;
            fns.postffs.user_function_filter_conditions = defaults.fns.postffs.user_function_filter_conditions;
            fns.postffs.user_function_filter_algorithm = defaults.fns.postffs.user_function_filter_algorithm;

            fns.root_initialization_method = defaults.fns.root_initialization_method;

            pps.sts.lagrangianPower = defaults.pps.sts.lagrangianPower;
            pps.sts.equicontinuityDenominatorFactor = defaults.pps.sts.equicontinuityDenominatorFactor;
            pps.sts.equicontinuityColorMethod = defaults.pps.sts.equicontinuityColorMethod;
            pps.sts.equicontinuityMixingMethod = defaults.pps.sts.equicontinuityMixingMethod;
            pps.sts.equicontinuityBlending = defaults.pps.sts.equicontinuityBlending;
            pps.sts.equicontinuitySatChroma = defaults.pps.sts.equicontinuitySatChroma;
            pps.sts.equicontinuityArgValue = defaults.pps.sts.equicontinuityArgValue;
            pps.sts.equicontinuityInvertFactor = defaults.pps.sts.equicontinuityInvertFactor;
            pps.sts.equicontinuityOverrideColoring = defaults.pps.sts.equicontinuityOverrideColoring;
            pps.sts.equicontinuityDelta = defaults.pps.sts.equicontinuityDelta;
        }
        else {
            fns.preffs.functionFilter = ((SettingsFractals1077) settings).getPreFunctionFilter();
            fns.preffs.userFormulaFunctionFilter = ((SettingsFractals1077) settings).getPreUserFormulaFunctionFilter();
            fns.preffs.user_function_filter_condition_formula = ((SettingsFractals1077) settings).getPreUserFunctionFilterConditionFormula();
            fns.preffs.user_function_filter_conditions = ((SettingsFractals1077) settings).getPreUserFunctionFilterConditions();
            fns.preffs.user_function_filter_algorithm = ((SettingsFractals1077) settings).getPreUserFunctionFilterAlgorithm();

            fns.postffs.functionFilter = ((SettingsFractals1077) settings).getPostFunctionFilter();
            fns.postffs.userFormulaFunctionFilter = ((SettingsFractals1077) settings).getPostUserFormulaFunctionFilter();
            fns.postffs.user_function_filter_condition_formula = ((SettingsFractals1077) settings).getPostUserFunctionFilterConditionFormula();
            fns.postffs.user_function_filter_conditions = ((SettingsFractals1077) settings).getPostUserFunctionFilterConditions();
            fns.postffs.user_function_filter_algorithm = ((SettingsFractals1077) settings).getPostUserFunctionFilterAlgorithm();

            fns.root_initialization_method = ((SettingsFractals1077) settings).getRootInitialization_method();

            pps.sts.lagrangianPower = ((SettingsFractals1077) settings).getLagrangianPower();
            pps.sts.equicontinuityDenominatorFactor = ((SettingsFractals1077) settings).getEquicontinuityDenominatorFactor();
            pps.sts.equicontinuityColorMethod = ((SettingsFractals1077) settings).getEquicontinuityColorMethod();
            pps.sts.equicontinuityMixingMethod = ((SettingsFractals1077) settings).getEquicontinuityMixingMethod();
            pps.sts.equicontinuityBlending = ((SettingsFractals1077) settings).getEquicontinuityBlending();
            pps.sts.equicontinuitySatChroma = ((SettingsFractals1077) settings).getEquicontinuitySatChroma();
            pps.sts.equicontinuityArgValue = ((SettingsFractals1077) settings).getEquicontinuityArgValue();
            pps.sts.equicontinuityInvertFactor = ((SettingsFractals1077) settings).isEquicontinuityInvertFactor();
            pps.sts.equicontinuityOverrideColoring = ((SettingsFractals1077) settings).isEquicontinuityOverrideColoring();
            pps.sts.equicontinuityDelta = ((SettingsFractals1077) settings).getEquicontinuityDelta();
        }

        if(version < 1078) {
            fns.juliter = defaults.fns.juliter;
            fns.juliterIterations = defaults.fns.juliterIterations;
            fns.juliterIncludeInitialIterations = defaults.fns.juliterIncludeInitialIterations;
            fns.ips.influencePlane = defaults.fns.ips.influencePlane;
            fns.ips.userFormulaPlaneInfluence = defaults.fns.ips.userFormulaPlaneInfluence;
            fns.ips.user_plane_influence_conditions = defaults.fns.ips.user_plane_influence_conditions;
            fns.ips.user_plane_influence_condition_formula = defaults.fns.ips.user_plane_influence_condition_formula;
            fns.ips.user_plane_influence_algorithm = defaults.fns.ips.user_plane_influence_algorithm;
        }
        else {
            fns.juliter = ((SettingsFractals1078) settings).getJuliter();
            fns.juliterIterations = ((SettingsFractals1078) settings).getJuliterIterations();
            fns.juliterIncludeInitialIterations = ((SettingsFractals1078) settings).getJuliterIncludeInitialIterations();
            fns.ips.influencePlane = ((SettingsFractals1078) settings).getInfluencePlane();
            fns.ips.userFormulaPlaneInfluence = ((SettingsFractals1078) settings).getUserFormulaPlaneInfluence();
            fns.ips.user_plane_influence_conditions = ((SettingsFractals1078) settings).getUserPlaneInfluenceConditions();
            fns.ips.user_plane_influence_condition_formula = ((SettingsFractals1078) settings).getUserPlaneInfluenceConditionFormula();
            fns.ips.user_plane_influence_algorithm = ((SettingsFractals1078) settings).getUserPlaneInfluenceAlgorithm();
        }

        if(version < 1079) {
            pps.sts.useNormalMap = defaults.pps.sts.useNormalMap;
            pps.sts.normalMapColorMode = defaults.pps.sts.normalMapColorMode;
            pps.sts.normalMapOverrideColoring = defaults.pps.sts.normalMapOverrideColoring;
            pps.sts.normalMapLightFactor = defaults.pps.sts.normalMapLightFactor;
            pps.sts.normalMapBlending = defaults.pps.sts.normalMapBlending;
            pps.sts.normalMapHeight = defaults.pps.sts.normalMapHeight;
            pps.sts.normalMapAngle = defaults.pps.sts.normalMapAngle;
            pps.sts.normalMapUseSecondDerivative = defaults.pps.sts.normalMapUseSecondDerivative;
            pps.sts.normalMapUseDE = defaults.pps.sts.normalMapUseDE;
            pps.sts.normalMapDEfactor = defaults.pps.sts.normalMapDEfactor;
            pps.sts.normalMapInvertDE = defaults.pps.sts.normalMapInvertDE;
            pps.sts.normalMapColoring = defaults.pps.sts.normalMapColoring;
            fns.defaultNovaInitialValue = false;
            contourFactor = defaults.contourFactor;
        }
        else {
            pps.sts.useNormalMap = ((SettingsFractals1079) settings).getUseNormalMap();
            pps.sts.normalMapColorMode = ((SettingsFractals1079) settings).getNormalMapColorMode();
            pps.sts.normalMapOverrideColoring = ((SettingsFractals1079) settings).getNormalMapOverrideColoring();
            pps.sts.normalMapLightFactor = ((SettingsFractals1079) settings).getNormalMapLightFactor();
            pps.sts.normalMapBlending = ((SettingsFractals1079) settings).getNormalMapBlending();
            pps.sts.normalMapHeight = ((SettingsFractals1079) settings).getNormalMapHeight();
            pps.sts.normalMapAngle = ((SettingsFractals1079) settings).getNormalMapAngle();
            pps.sts.normalMapUseSecondDerivative = ((SettingsFractals1079) settings).getNormalMapUseSecondDerivative();
            pps.sts.normalMapUseDE = ((SettingsFractals1079) settings).getNormalMapUseDE();
            pps.sts.normalMapDEfactor = ((SettingsFractals1079) settings).getNormalMapDEfactor();
            pps.sts.normalMapInvertDE = ((SettingsFractals1079) settings).getNormalMapInvertDE();
            pps.sts.normalMapColoring = ((SettingsFractals1079) settings).getNormalMapColoring();
            fns.defaultNovaInitialValue = ((SettingsFractals1079) settings).getDefaultNovaInitialValue();
            contourFactor = ((SettingsFractals1079) settings).getContourFactor();
        }

        if(version < 1080) {
            pps.ots.checkType = defaults.pps.ots.checkType;
            pps.ots.sfm1 = defaults.pps.ots.sfm1;
            pps.ots.sfm2 = defaults.pps.ots.sfm2;
            pps.ots.sfn1 = defaults.pps.ots.sfn1;
            pps.ots.sfn2 = defaults.pps.ots.sfn2;
            pps.ots.sfn3 = defaults.pps.ots.sfn3;
            pps.ots.sfa = defaults.pps.ots.sfa;
            pps.ots.sfb = defaults.pps.ots.sfb;

            pps.sts.rootIterationsScaling = defaults.pps.sts.rootIterationsScaling;
            pps.sts.rootShading = defaults.pps.sts.rootShading;
            pps.sts.rootContourColorMethod = defaults.pps.sts.rootContourColorMethod;
            pps.sts.rootBlending = defaults.pps.sts.rootBlending;
            pps.sts.rootShadingFunction = defaults.pps.sts.rootShadingFunction;
            pps.sts.revertRootShading = defaults.pps.sts.revertRootShading;
            pps.sts.highlightRoots = defaults.pps.sts.highlightRoots;
            pps.sts.rootSmooting = defaults.pps.sts.rootSmooting;
            pps.sts.rootColors = defaults.pps.sts.rootColors;

            MagnetColorOffset = defaults.MagnetColorOffset;

            fns.cbs.convergent_bailout_test_algorithm = defaults.fns.cbs.convergent_bailout_test_algorithm;
            fns.cbs.convergent_bailout_test_user_formula = defaults.fns.cbs.convergent_bailout_test_user_formula;
            fns.cbs.convergent_bailout_test_user_formula2 = defaults.fns.cbs.convergent_bailout_test_user_formula2;
            fns.cbs.convergent_bailout_test_comparison = defaults.fns.cbs.convergent_bailout_test_comparison;
            fns.cbs.convergent_n_norm = defaults.fns.cbs.convergent_n_norm;

            fns.skip_convergent_bailout_iterations = defaults.fns.skip_convergent_bailout_iterations;

            pps.sts.twlPoint = defaults.pps.sts.twlPoint;
            pps.sts.twlFunction = defaults.pps.sts.twlFunction;
        }
        else {
            pps.ots.checkType = ((SettingsFractals1080) settings).getCheckType();
            pps.ots.sfm1 = ((SettingsFractals1080) settings).getSfm1();
            pps.ots.sfm2 = ((SettingsFractals1080) settings).getSfm2();
            pps.ots.sfn1 = ((SettingsFractals1080) settings).getSfn1();
            pps.ots.sfn2 = ((SettingsFractals1080) settings).getSfn2();
            pps.ots.sfn3 = ((SettingsFractals1080) settings).getSfn3();
            pps.ots.sfa = ((SettingsFractals1080) settings).getSfa();
            pps.ots.sfb = ((SettingsFractals1080) settings).getSfb();

            pps.sts.rootIterationsScaling = ((SettingsFractals1080) settings).getRootIterationsScaling();
            pps.sts.rootShading = ((SettingsFractals1080) settings).getRootShading();
            pps.sts.rootContourColorMethod = ((SettingsFractals1080) settings).getRootContourColorMethod();
            pps.sts.rootBlending = ((SettingsFractals1080) settings).getRootBlending();
            pps.sts.rootShadingFunction = ((SettingsFractals1080) settings).getRootShadingFunction();
            pps.sts.revertRootShading = ((SettingsFractals1080) settings).getRevertRootShading();
            pps.sts.highlightRoots = ((SettingsFractals1080) settings).getHighlightRoots();
            pps.sts.rootSmooting = ((SettingsFractals1080) settings).getRootSmooting();
            pps.sts.rootColors = ((SettingsFractals1080) settings).getRootColors();

            MagnetColorOffset = ((SettingsFractals1080) settings).getMagnetColorOffset();

            fns.cbs.convergent_bailout_test_algorithm = ((SettingsFractals1080) settings).getConvergentBailoutTestAlgorithm();
            fns.cbs.convergent_bailout_test_user_formula = ((SettingsFractals1080) settings).getConvergentBailoutTestUserFormula();
            fns.cbs.convergent_bailout_test_user_formula2 = ((SettingsFractals1080) settings).getConvergentBailoutTestUserFormula2();
            fns.cbs.convergent_bailout_test_comparison = ((SettingsFractals1080) settings).getConvergentBailoutTestComparison();
            fns.cbs.convergent_n_norm = ((SettingsFractals1080) settings).getConvergentNNorm();

            fns.skip_convergent_bailout_iterations = ((SettingsFractals1080) settings).getSkipConvergentBailoutIterations();

            pps.sts.twlPoint = ((SettingsFractals1080) settings).getTwlPoint();
            pps.sts.twlFunction = ((SettingsFractals1080) settings).getTwlFunction();
        }

        if(version < 1081) {
            fns.period = defaults.fns.period;
            pps.sts.langNormType = defaults.pps.sts.langNormType;
            pps.sts.langNNorm = defaults.pps.sts.langNNorm;
            pps.sts.atomNormType = defaults.pps.sts.atomNormType;
            pps.sts.atomNNorm = defaults.pps.sts.atomNNorm;
            pps.hss.hmapping = defaults.pps.hss.hmapping;
            fns.useGlobalMethod = defaults.fns.useGlobalMethod;
            fns.globalMethodFactor = defaults.fns.globalMethodFactor;
            pps.ots.showOnlyTraps = defaults.pps.ots.showOnlyTraps;
            pps.ots.background = defaults.pps.ots.background;
            ds.domain_height_method = defaults.ds.domain_height_method;
        }
        else {
            fns.period = ((SettingsFractals1081) settings).getPeriod();
            pps.sts.langNormType = ((SettingsFractals1081) settings).getLangNormType();
            pps.sts.langNNorm = ((SettingsFractals1081) settings).getLangNNorm();
            pps.sts.atomNormType = ((SettingsFractals1081) settings).getAtomNormType();
            pps.sts.atomNNorm = ((SettingsFractals1081) settings).getAtomNNorm();
            pps.hss.hmapping = ((SettingsFractals1081) settings).getHmapping();
            fns.useGlobalMethod = ((SettingsFractals1081) settings).getUseGlobalMethod();
            fns.globalMethodFactor = ((SettingsFractals1081) settings).getGlobalMethodFactor();
            pps.ots.showOnlyTraps = ((SettingsFractals1081) settings).getShowOnlyTraps();
            pps.ots.background = ((SettingsFractals1081) settings).getTrapBgColor();
            ds.domain_height_method = ((SettingsFractals1081) settings).getDomainHeightMethod();
        }

        if(version < 1083) {
            pps.sts.normalMapDeFadeAlgorithm = defaults.pps.sts.normalMapDeFadeAlgorithm;
            pps.sts.normalMapDEUpperLimitFactor = defaults.pps.sts.normalMapDEUpperLimitFactor;
            pps.sts.normalMapDEAAEffect = defaults.pps.sts.normalMapDEAAEffect;
            pps.sts.unmmapedRootColor = defaults.pps.sts.unmmapedRootColor;
            pps.sts.rootShadingColor = defaults.pps.sts.rootShadingColor;
        }
        else {
            pps.sts.normalMapDeFadeAlgorithm = ((SettingsFractals1083) settings).getNormalMapDeFadeAlgorithm();
            pps.sts.normalMapDEUpperLimitFactor = ((SettingsFractals1083) settings).getNormalMapDEUpperLimitFactor();
            pps.sts.normalMapDEAAEffect = ((SettingsFractals1083) settings).getNormalMapDEAAEffect();
            pps.sts.unmmapedRootColor = ((SettingsFractals1083) settings).getUnmmapedRootColor();
            pps.sts.rootShadingColor = ((SettingsFractals1083) settings).getRootShadingColor();
        }

        if(version < 1084) {
            gps.generatedPaletteInColoringId = defaults.gps.generatedPaletteInColoringId;
            gps.generatedPaletteOutColoringId = defaults.gps.generatedPaletteOutColoringId;
            gps.useGeneratedPaletteInColoring = defaults.gps.useGeneratedPaletteInColoring;
            gps.useGeneratedPaletteOutColoring = defaults.gps.useGeneratedPaletteOutColoring;
            gps.restartGeneratedOutColoringPaletteAt = defaults.gps.restartGeneratedOutColoringPaletteAt;
            gps.restartGeneratedInColoringPaletteAt = defaults.gps.restartGeneratedInColoringPaletteAt;

            js.enableJitter = defaults.js.enableJitter;
            js.jitterScale = defaults.js.jitterScale;
            js.jitterSeed = defaults.js.jitterSeed;
            js.jitterShape = defaults.js.jitterShape;
        }
        else {
            gps.generatedPaletteInColoringId = ((SettingsFractals1084) settings).getGeneratedPaletteInColoringId();
            gps.generatedPaletteOutColoringId = ((SettingsFractals1084) settings).getGeneratedPaletteOutColoringId();
            gps.useGeneratedPaletteInColoring = ((SettingsFractals1084) settings).getUseGeneratedPaletteInColoring();
            gps.useGeneratedPaletteOutColoring = ((SettingsFractals1084) settings).getUseGeneratedPaletteOutColoring();
            gps.restartGeneratedOutColoringPaletteAt = ((SettingsFractals1084) settings).getRestartGeneratedOutColoringPaletteAt();
            gps.restartGeneratedInColoringPaletteAt = ((SettingsFractals1084) settings).getRestartGeneratedInColoringPaletteAt();

            js.enableJitter = ((SettingsFractals1084) settings).getEnableJitter();
            js.jitterScale = ((SettingsFractals1084) settings).getJitterScale();
            js.jitterSeed = ((SettingsFractals1084) settings).getJitterSeed();
            js.jitterShape = ((SettingsFractals1084) settings).getJitterShape();
        }

        if(version < 1085) {
            pps.cns.min_contour = defaults.pps.cns.min_contour;
            color_blending.blending_reversed_colors = defaults.color_blending.blending_reversed_colors;
        }
        else {
            pps.cns.min_contour = ((SettingsFractals1085) settings).getMinContour();
            color_blending.blending_reversed_colors = ((SettingsFractals1085) settings).getBlendingReversedColors();
            TaskDraw.PERTURBATION_THEORY = ((SettingsFractals1085) settings).getPerturbationTheory();

            if(TaskDraw.LOAD_DRAWING_ALGORITHM_FROM_SAVES) {
                TaskDraw.GREEDY_ALGORITHM = ((SettingsFractals1085) settings).getGreedyDrawingAlgorithm();
            }
        }

        if(version < 1086) {
            Parser.CURRENT_USER_CODE_FILE = Parser.DEFAULT_USER_CODE_FILE;
            Parser.CURRENT_USER_CODE_CLASS = Parser.DEFAULT_USER_CODE_CLASS;

            try {
                Parser.compileUserFunctions();
            }
            catch (ParserException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE, MainWindow.getIcon("compile_error.png"));
            }
            catch (Exception ex) {}
        }
        else {
            if(TaskDraw.LOAD_DRAWING_ALGORITHM_FROM_SAVES) {
                TaskDraw.BRUTE_FORCE_ALG = ((SettingsFractals1086) settings).getBruteForceAlg();
                TaskDraw.GREEDY_ALGORITHM_SELECTION = ((SettingsFractals1086) settings).getGreedyDrawingAlgorithmId();
                TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA = ((SettingsFractals1086) settings).getGreedyAlgorithmCheckIterData();
            }

            String userCode = ((SettingsFractals1086) settings).getUserDefinedCode();

            if(!userCode.isEmpty()) {
                Path path = Paths.get(Parser.SAVED_USER_CODE_FILE);
                byte[] strToBytes = userCode.getBytes();
                Files.write(path, strToBytes);
                path.toFile().deleteOnExit();
                Parser.CURRENT_USER_CODE_FILE = Parser.SAVED_USER_CODE_FILE;
                Parser.CURRENT_USER_CODE_CLASS = Parser.SAVED_USER_CODE_CLASS;
            }
            else {
                Parser.CURRENT_USER_CODE_FILE = Parser.DEFAULT_USER_CODE_FILE;
                Parser.CURRENT_USER_CODE_CLASS = Parser.DEFAULT_USER_CODE_CLASS;
            }

            try {
                Parser.compileUserFunctions();
            }
            catch (ParserException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE, MainWindow.getIcon("compile_error.png"));
            }
            catch (Exception ex) {}
        }

        if(version < 1087) {
            pps.sts.normalMapDistanceEstimatorfactor = defaults.pps.sts.normalMapDistanceEstimatorfactor;
            pps.hss.hs_outliers_method = defaults.pps.hss.hs_outliers_method;
            pps.hss.hs_remove_outliers = defaults.pps.hss.hs_remove_outliers;

            pps.sts.normalMapDEUseColorPerDepth = defaults.pps.sts.normalMapDEUseColorPerDepth;
            pps.sts.normalMapDEOffset = defaults.pps.sts.normalMapDEOffset;
            pps.sts.normalMapDEOffsetFactor = defaults.pps.sts.normalMapDEOffsetFactor;

            if(pps.sts.statistic) {
                pps.sts.lastXItems = 0;
            }
            else {
                pps.sts.lastXItems = defaults.pps.sts.lastXItems;
            }

            if(pps.ots.useTraps) {
                pps.ots.lastXItems = 0;
            }
            else {
                pps.ots.lastXItems = defaults.pps.ots.lastXItems;
            }

            pps.fdes.fade_algorithm = defaults.pps.fdes.fade_algorithm;
        }
        else {
            pps.sts.normalMapDistanceEstimatorfactor = ((SettingsFractals1087) settings).getNormalMapDistanceEstimatorfactor();
            pps.hss.hs_outliers_method = ((SettingsFractals1087) settings).getHsOutliersMethod();
            pps.hss.hs_remove_outliers = ((SettingsFractals1087) settings).getHsRemoveOutliers();
            pps.sts.lastXItems = ((SettingsFractals1087) settings).getStatisticlastXItems();
            pps.ots.lastXItems = ((SettingsFractals1087) settings).getTraplastXItems();

            pps.sts.normalMapDEUseColorPerDepth = ((SettingsFractals1087) settings).getNormalMapDEUseColorPerDepth();
            pps.sts.normalMapDEOffset = ((SettingsFractals1087) settings).getNormalMapDEOffset();
            pps.sts.normalMapDEOffsetFactor = ((SettingsFractals1087) settings).getNormalMapDEOffsetFactor();
            pps.fdes.fade_algorithm = ((SettingsFractals1087)settings).getFakeDEfadingAlgorithm();
        }

        if(version < 1088) {
            fns.variable_re = defaults.fns.variable_re;
            fns.variable_im = defaults.fns.variable_im;
            pps.sts.normalMapCombineWithOtherStatistics = defaults.pps.sts.normalMapCombineWithOtherStatistics;
        }
        else {
            fns.variable_re = ((SettingsFractals1088) settings).getVariableRe();
            fns.variable_im = ((SettingsFractals1088) settings).getVariableIm();
            pps.sts.normalMapCombineWithOtherStatistics = ((SettingsFractals1088) settings).getNormalMapCombineWithOtherStatistics();
        }

        if(version < 1089) {

            pps.bms.fractionalTransfer = defaults.pps.bms.fractionalTransfer;
            pps.bms.fractionalSmoothing = defaults.pps.bms.fractionalSmoothing;
            pps.bms.fractionalTransferMode = defaults.pps.bms.fractionalTransferMode;
            pps.cns.fractionalTransfer = defaults.pps.cns.fractionalTransfer;
            pps.cns.fractionalSmoothing = defaults.pps.cns.fractionalSmoothing;
            gps.inColoringIQ = defaults.gps.inColoringIQ;
            gps.outColoringIQ = defaults.gps.outColoringIQ;
            ds.iso_color_type = defaults.ds.iso_color_type;
            ds.grid_color_type = defaults.ds.grid_color_type;
            ds.circle_color_type = defaults.ds.circle_color_type;

            fns.inflections_re = defaults.fns.inflections_re;
            fns.inflections_im = defaults.fns.inflections_im;
            fns.inflectionsPower = defaults.fns.inflectionsPower;
            fns.smoothing_fractional_transfer_method = defaults.fns.smoothing_fractional_transfer_method;

            pps.ls.specularReflectionMethod = defaults.pps.ls.specularReflectionMethod;
            pps.ls.fractionalTransfer = defaults.pps.ls.fractionalTransfer;
            pps.ls.fractionalSmoothing = defaults.pps.ls.fractionalSmoothing;
            pps.ls.fractionalTransferMode = defaults.pps.ls.fractionalTransferMode;

            pps.ndes.useNumericalDem = defaults.pps.ndes.useNumericalDem;
            pps.ndes.distanceFactor = defaults.pps.ndes.distanceFactor;
            pps.ndes.distanceOffset = defaults.pps.ndes.distanceOffset;
            pps.ndes.differencesMethod = defaults.pps.ndes.differencesMethod;

            pps.ndes.n_noise_reducing_factor = defaults.pps.ndes.n_noise_reducing_factor;
            pps.ndes.numerical_blending = defaults.pps.ndes.numerical_blending;
            pps.ndes.cap_to_palette_length = defaults.pps.ndes.cap_to_palette_length;
            ps.color_density = defaults.ps.color_density;
            ps2.color_density = defaults.ps2.color_density;

            pps.ss.slopes = defaults.pps.ss.slopes;
            pps.ss.SlopeAngle = defaults.pps.ss.SlopeAngle;
            pps.ss.SlopePower = defaults.pps.ss.SlopePower;
            pps.ss.SlopeRatio = defaults.pps.ss.SlopeRatio;

            double lightAngleRadians = Math.toRadians(pps.ss.SlopeAngle);
            pps.ss.lightVector[0] = Math.cos(lightAngleRadians);
            pps.ss.lightVector[1] = Math.sin(lightAngleRadians);

            pps.ss.s_noise_reducing_factor = defaults.pps.ss.s_noise_reducing_factor;
            pps.ss.colorMode = defaults.pps.ss.colorMode;
            pps.ss.slope_blending = defaults.pps.ss.slope_blending;
            pps.ss.fractionalTransfer = defaults.pps.ss.fractionalTransfer;
            pps.ss.fractionalSmoothing = defaults.pps.ss.fractionalSmoothing;
            pps.ss.fractionalTransferMode = defaults.pps.ss.fractionalTransferMode;

            pps.ss.heightTransfer = defaults.pps.ss.heightTransfer;
            pps.ss.heightTransferFactor = defaults.pps.ss.heightTransferFactor;

            ds.mapNormReImWithAbsScale = false;
            ds.applyShading = defaults.ds.applyShading;
            ds.saturation_adjustment = defaults.ds.saturation_adjustment;
            ds.shadingType = defaults.ds.shadingType;
            ds.shadingColorAlgorithm = defaults.ds.shadingColorAlgorithm;

            hsb_constant_b = defaults.hsb_constant_b;
            hsb_constant_s = defaults.hsb_constant_s;
            lchab_constant_l = defaults.lchab_constant_l;
            lchab_constant_c = defaults.lchab_constant_c;
            lchuv_constant_l = defaults.lchuv_constant_l;
            lchuv_constant_c = defaults.lchuv_constant_c;

            ds.domain_height_normalization_method = defaults.ds.domain_height_normalization_method;
            ds.invertShading = defaults.ds.invertShading;
            ds.shadingPercent = defaults.ds.shadingPercent;

            pps.sts.patternScale = defaults.pps.sts.patternScale;
            pps.sts.checkerNormType = defaults.pps.sts.checkerNormType;
            pps.sts.checkerNormValue = defaults.pps.sts.checkerNormValue;

            fs.aaSigmaR = defaults.fs.aaSigmaR;
            fs.aaSigmaS = defaults.fs.aaSigmaS;
            fs.bluringSigmaS = defaults.fs.bluringSigmaS;
            fs.bluringSigmaR = defaults.fs.bluringSigmaR;
            fs.blurringKernelSelection = defaults.fs.blurringKernelSelection;
        }
        else {
            if(TaskDraw.LOAD_DRAWING_ALGORITHM_FROM_SAVES) {
                TaskDraw.GUESS_BLOCKS_SELECTION = ((SettingsFractals1089) settings).getGuessBlocks();
            }

            pps.bms.fractionalTransfer = ((SettingsFractals1089) settings).getBumpfractionalTransfer();
            pps.bms.fractionalSmoothing = ((SettingsFractals1089) settings).getBumpfractionalSmoothing();
            pps.bms.fractionalTransferMode = ((SettingsFractals1089) settings).getBumpfractionalTransferMode();
            pps.cns.fractionalTransfer = ((SettingsFractals1089) settings).getContourfractionalTransfer();
            pps.cns.fractionalSmoothing = ((SettingsFractals1089) settings).getContourfractionalSmoothing();
            gps.inColoringIQ = ((SettingsFractals1089) settings).getInColoringIQ();
            gps.outColoringIQ = ((SettingsFractals1089) settings).getOutColoringIQ();
            ds.iso_color_type = ((SettingsFractals1089) settings).getIsoColorType();
            ds.grid_color_type = ((SettingsFractals1089) settings).getGridColorType();
            ds.circle_color_type = ((SettingsFractals1089) settings).getCircleColorType();

            fns.inflections_re = ((SettingsFractals1089) settings).getInflectionsRe();
            fns.inflections_im = ((SettingsFractals1089) settings).getInflectionsIm();
            fns.inflectionsPower = ((SettingsFractals1089) settings).getInflectionsPower();
            fns.smoothing_fractional_transfer_method = ((SettingsFractals1089) settings).getSmoothingFractionalTransferMethod();

            pps.ls.specularReflectionMethod = ((SettingsFractals1089) settings).getSpecularReflectionMethod();
            pps.ls.fractionalTransfer = ((SettingsFractals1089) settings).getLightfractionalTransfer();
            pps.ls.fractionalSmoothing = ((SettingsFractals1089) settings).getLightfractionalSmoothing();
            pps.ls.fractionalTransferMode = ((SettingsFractals1089) settings).getLightfractionalTransferMode();

            pps.ndes.useNumericalDem = ((SettingsFractals1089) settings).getUseNumericalDem();
            pps.ndes.distanceFactor = ((SettingsFractals1089) settings).getDistanceFactor();
            pps.ndes.distanceOffset = ((SettingsFractals1089) settings).getDistanceOffset();
            pps.ndes.differencesMethod = ((SettingsFractals1089) settings).getDifferencesMethod();

            pps.ndes.n_noise_reducing_factor = ((SettingsFractals1089) settings).getNdesNoiseReducingFactor();
            pps.ndes.numerical_blending = ((SettingsFractals1089) settings).getNumericalBlending();
            pps.ndes.cap_to_palette_length = ((SettingsFractals1089) settings).getCapToPaletteLength();
            ps.color_density = ((SettingsFractals1089) settings).getOutColorDensity();
            ps2.color_density = ((SettingsFractals1089) settings).getInColorDensity();

            pps.ss.slopes = ((SettingsFractals1089) settings).getSlopes();
            pps.ss.SlopeAngle = ((SettingsFractals1089) settings).getSlopeAngle();
            pps.ss.SlopePower = ((SettingsFractals1089) settings).getSlopePower();
            pps.ss.SlopeRatio = ((SettingsFractals1089) settings).getSlopeRatio();

            double lightAngleRadians = Math.toRadians(pps.ss.SlopeAngle);
            pps.ss.lightVector[0] = Math.cos(lightAngleRadians);
            pps.ss.lightVector[1] = Math.sin(lightAngleRadians);

            pps.ss.s_noise_reducing_factor = ((SettingsFractals1089) settings).getSlopesNoiseReducingFactor();
            pps.ss.colorMode = ((SettingsFractals1089) settings).getSlopescolorMode();
            pps.ss.slope_blending = ((SettingsFractals1089) settings).getSlopeBlending();
            pps.ss.fractionalTransfer = ((SettingsFractals1089) settings).getSlopesfractionalTransfer();
            pps.ss.fractionalSmoothing = ((SettingsFractals1089) settings).getSlopesfractionalSmoothing();
            pps.ss.fractionalTransferMode = ((SettingsFractals1089) settings).getSlopesfractionalTransferMode();

            pps.ss.heightTransfer = ((SettingsFractals1089) settings).getSlopesheightTransfer();
            pps.ss.heightTransferFactor = ((SettingsFractals1089) settings).getSlopesheightTransferFactor();

            ds.mapNormReImWithAbsScale = ((SettingsFractals1089) settings).getMapNormReImWithAbsScale();
            ds.applyShading = ((SettingsFractals1089) settings).getApplyShading();
            ds.saturation_adjustment = ((SettingsFractals1089) settings).getSaturationAdjustment();
            ds.shadingType = ((SettingsFractals1089) settings).getShadingType();
            ds.shadingColorAlgorithm = ((SettingsFractals1089) settings).getShadingColorAlgorithm();

            hsb_constant_b = ((SettingsFractals1089) settings).getHsbConstantB();
            hsb_constant_s = ((SettingsFractals1089) settings).getHsbConstantS();
            lchab_constant_l = ((SettingsFractals1089) settings).getLchabConstantL();
            lchab_constant_c = ((SettingsFractals1089) settings).getLchabConstantC();
            lchuv_constant_l = ((SettingsFractals1089) settings).getLchuvConstantL();
            lchuv_constant_c = ((SettingsFractals1089) settings).getLchuvConstantC();

            ds.domain_height_normalization_method = ((SettingsFractals1089) settings).getDomainHeightNormalizationMethod();
            ds.invertShading = ((SettingsFractals1089) settings).getInvertShading();
            ds.shadingPercent = ((SettingsFractals1089) settings).getShadingPercent();

            pps.sts.patternScale = ((SettingsFractals1089) settings).getPatternScale();
            pps.sts.checkerNormType = ((SettingsFractals1089) settings).getCheckerNormType();
            pps.sts.checkerNormValue = ((SettingsFractals1089) settings).getCheckerNormValue();

            fs.aaSigmaR = ((SettingsFractals1089) settings).getAaSigmaR();
            fs.aaSigmaS = ((SettingsFractals1089) settings).getAaSigmaS();
            fs.bluringSigmaS = ((SettingsFractals1089) settings).getBluringSigmaS();
            fs.bluringSigmaR = ((SettingsFractals1089) settings).getBluringSigmaR();
            fs.blurringKernelSelection = ((SettingsFractals1089) settings).getBlurringKernelSelection();
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

        fns.rotation_center = new Apfloat[2];

        if(version < 1078) {
            double[] tempRotCenter = settings.getRotationCenter();
            fns.rotation_center[0] = new MyApfloat(tempRotCenter[0]);
            fns.rotation_center[1] = new MyApfloat(tempRotCenter[1]);
        }
        else {
            String[] tempRotCenter = ((SettingsFractals1078) settings).getRotationCenterStr();
            fns.rotation_center[0] = new MyApfloat(tempRotCenter[0]);
            fns.rotation_center[1] = new MyApfloat(tempRotCenter[1]);
        }

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

        Apfloat tempRadians =  MyApfloat.fp.toRadians(new MyApfloat(fns.rotation));
        fns.rotation_vals[0] = MyApfloat.cos(tempRadians);
        fns.rotation_vals[1] = MyApfloat.sin(tempRadians);

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
            pps.fdes.inverse_fake_dem = defaults.pps.fdes.inverse_fake_dem;
        } else {
            inverse_dem = ((SettingsFractals1064) settings).getInverseDe();
            pps.fdes.inverse_fake_dem = ((SettingsFractals1064) settings).getInverseFakeDe();
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
            pps.bms.bm_noise_reducing_factor = defaults.pps.bms.bm_noise_reducing_factor;
            pps.rps.rp_noise_reducing_factor = defaults.pps.rps.rp_noise_reducing_factor;
        } else {
            color_smoothing_method = ((SettingsFractals1065) settings).getColorSmoothingMethod();
            pps.bms.bm_noise_reducing_factor = ((SettingsFractals1065) settings).getBumpMappingNoiseReducingFactor();
            pps.rps.rp_noise_reducing_factor = ((SettingsFractals1065) settings).getRainbowPaletteNoiseReducingFactor();
        }

        if (version < 1054) {
            fns.escaping_smooth_algorithm = defaults.fns.escaping_smooth_algorithm;
            fns.converging_smooth_algorithm = defaults.fns.converging_smooth_algorithm;

            pps.bms.bump_map = defaults.pps.bms.bump_map;
            polar_projection = defaults.polar_projection;
            ps.color_intensity = defaults.ps.color_intensity;
        } else {
            fns.escaping_smooth_algorithm = ((SettingsFractals1054) settings).getEscapingSmoothAgorithm();
            fns.converging_smooth_algorithm = ((SettingsFractals1054) settings).getConvergingSmoothAgorithm();

            pps.bms.bump_map = ((SettingsFractals1054) settings).getBumpMap();

            pps.bms.bumpMappingStrength = ((SettingsFractals1054) settings).getBumpMapStrength();
            pps.bms.bumpMappingDepth = ((SettingsFractals1054) settings).getBumpMapDepth();
            pps.bms.lightDirectionDegrees = ((SettingsFractals1054) settings).getLightDirectionDegrees();

            polar_projection = ((SettingsFractals1054) settings).getPolarProjection();

            circle_period = ((SettingsFractals1054) settings).getCirclePeriod();

            ps.color_intensity = ((SettingsFractals1054) settings).getColorIntensity();
        }

        if (version < 1055) {
            pps.fdes.fake_de = defaults.pps.fdes.fake_de;
        } else {
            pps.fdes.fake_de = ((SettingsFractals1055) settings).getFakeDe();
            pps.fdes.fake_de_factor = ((SettingsFractals1055) settings).getFakeDeFactor();
        }

        switch (fns.function) {
            case MANDELBROTNTH:
                fns.z_exponent = settings.getZExponent();
                break;
            case MANDELBROTWTH:
                fns.z_exponent_complex = settings.getZExponentComplex();
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
                    temp_bool = temp_bool || parser.foundC();
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

                if(isTwoFunctionsNovaFormula(fns.nova_method)) {
                    fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                    fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();

                    parser.parse(fns.user_fz_formula);
                    temp_bool = temp_bool || parser.foundC();

                    parser.parse(fns.user_dfz_formula);
                    temp_bool = temp_bool || parser.foundC();

                    if(fns.nova_method == NOVA_NEWTON_HINES) {
                        fns.newton_hines_k = ((SettingsFractals1074) settings).getNewtonHinesK();
                    }
                }
                else if(isThreeFunctionsNovaFormula(fns.nova_method)) {
                    fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                    fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();
                    fns.user_ddfz_formula = ((SettingsFractals1058) settings).getUserDdfzFormula();

                    parser.parse(fns.user_fz_formula);
                    temp_bool = temp_bool || parser.foundC();

                    parser.parse(fns.user_dfz_formula);
                    temp_bool = temp_bool || parser.foundC();

                    parser.parse(fns.user_ddfz_formula);
                    temp_bool = temp_bool || parser.foundC();

                    if(fns.nova_method == NOVA_LAGUERRE) {
                        fns.laguerre_deg = ((SettingsFractals1067) settings).getLaguerreDeg();
                    }
                }
                else if (isOneFunctionsNovaFormula(fns.nova_method)){
                    fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();

                    parser.parse(fns.user_fz_formula);
                    temp_bool = temp_bool || parser.foundC();
                }

                parser.parse(fns.user_relaxation_formula);
                temp_bool = temp_bool || parser.foundC();

                parser.parse(fns.user_nova_addend_formula);
                temp_bool = temp_bool || parser.foundC();

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
                    temp_bool = temp_bool || parser.foundC();
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
                    temp_bool2 = temp_bool2 || parser.foundC();
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
                    temp_bool = temp_bool || parser.foundC();
                }

                userFormulaHasC = temp_bool;
                break;
             default:
                    if(Settings.isPolynomialFunction(fns.function)) {
                        fns.coefficients = settings.getCoefficients();

                        if (version < 1072) {
                            fns.coefficients_im = defaults.fns.coefficients_im;
                        } else {
                            fns.coefficients_im = ((SettingsFractals1072) settings).getCoefficientsIm();
                        }

                        if (fns.function == DURAND_KERNERPOLY || fns.function == ABERTH_EHRLICHPOLY) {
                            fns.durand_kerner_init_val = ((SettingsFractals1072) settings).getDurandKernerInitVal();
                        }

                        if (version < 1074) {
                            fns.newton_hines_k = defaults.fns.newton_hines_k;
                        } else {
                            fns.newton_hines_k = ((SettingsFractals1074) settings).getNewtonHinesK();
                        }

                        createPoly();
                    }
                    else if(isTwoFunctionsRootFindingMethodFormula(fns.function)) {
                        fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                        fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();

                        if(fns.function == NEWTON_HINESFORMULA) {
                            fns.newton_hines_k = ((SettingsFractals1074) settings).getNewtonHinesK();
                        }
                    }
                    else if(isThreeFunctionsRootFindingMethodFormula(fns.function)) {
                        fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                        fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();
                        fns.user_ddfz_formula = ((SettingsFractals1058) settings).getUserDdfzFormula();

                        if(fns.function == LAGUERREFORMULA) {
                            fns.laguerre_deg = ((SettingsFractals1067) settings).getLaguerreDeg();
                        }
                    }
                    else if(isOneFunctionsRootFindingMethodFormula(fns.function)) {
                        fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                    }
                    else if(isFourFunctionsRootFindingMethodFormula(fns.function)) {
                        fns.user_fz_formula = ((SettingsFractals1058) settings).getUserFzFormula();
                        fns.user_dfz_formula = ((SettingsFractals1058) settings).getUserDfzFormula();
                        fns.user_ddfz_formula = ((SettingsFractals1058) settings).getUserDdfzFormula();
                        fns.user_dddfz_formula = ((SettingsFractals1077) settings).getUserDddfzFormula();
                    }
                    break;
        }

        if (fns.out_coloring_algorithm != USER_OUTCOLORING_ALGORITHM) {
            resetUserOutColoringFormulas();
        }

        if (pps.sts.statisticGroup != 1) {
            resetStatisticalColoringFormulas();
        }

        if(fns.function >= MainWindow.MANDELBROT && fns.function <= MainWindow.MANDELBROTNTH && fns.burning_ship) {
            pps.sts.useNormalMap = false;
            pps.sts.normalMapOverrideColoring = false;
        }

        Fractal.clearReferences(true, true);

        applyStaticSettings();

        file_temp.close();

        if (!silent) {
            loadedSettings(filename, parent, version);
        }

        if (supportsPerturbationTheory() && !TaskDraw.PERTURBATION_THEORY && size.compareTo(MyApfloat.MIN_DOUBLE_SIZE) <= 0) {
            TaskDraw.PERTURBATION_THEORY = true;
        }
        else if(!supportsPerturbationTheory() && TaskDraw.PERTURBATION_THEORY) {
            TaskDraw.PERTURBATION_THEORY = false;
        }
    }

    public void save(String filename) {
        ObjectOutputStream file_temp = null;

        try {
            file_temp = new ObjectOutputStream(new FileOutputStream(filename));

            String userCode = "";

            if(Parser.usesUserCode) {
                Path path = Paths.get(Parser.CURRENT_USER_CODE_FILE);
                if(Files.exists(path) && Files.isRegularFile(path)) {
                    Stream<String> lines = Files.lines(path);
                    userCode = lines.collect(Collectors.joining("\r\n"));
                    lines.close();
                }

                userCode = userCode.replaceAll("\\b" + Parser.DEFAULT_USER_CODE_CLASS + "\\b", Parser.SAVED_USER_CODE_CLASS);
            }

            SettingsFractals settings = new SettingsFractals1089(this, TaskDraw.PERTURBATION_THEORY, TaskDraw.GREEDY_ALGORITHM, TaskDraw.BRUTE_FORCE_ALG, TaskDraw.GREEDY_ALGORITHM_SELECTION, TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA, userCode, TaskDraw.GUESS_BLOCKS_SELECTION);
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
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(24);
                break;
            case MAGNET1:
            case MAGNET13:
            case MAGNET14:
                if (fns.julia) {
                    xCenter = new MyApfloat(0.0);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(28);
                } else {
                    xCenter = new MyApfloat(1.35);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(8);
                }
                break;
            case MAGNET2:
            case MAGNET23:
            case MAGNET24:
                if (fns.julia) {
                    xCenter = new MyApfloat(0.0);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(56);
                } else {
                    xCenter = new MyApfloat(1.0);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(7);
                }
                break;
            case BARNSLEY1:
            case BARNSLEY2:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(7);
                break;
            case SIERPINSKI_GASKET:
                xCenter = new MyApfloat(0.5);
                yCenter = new MyApfloat(0.5);
                size = new MyApfloat(3);
                break;
            case FORMULA44:
            case FORMULA45:
            case FORMULA43:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(24);
                break;
            case FORMULA3:
            case FORMULA9:
            case FORMULA10:
            case FORMULA8:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(3);
                break;
            case FORMULA4:
            case FORMULA5:
            case FORMULA11:
            case FORMULA38:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(1.5);
                break;
            case FORMULA7:
            case FORMULA12:
            case SZEGEDI_BUTTERFLY1:
            case SZEGEDI_BUTTERFLY2:
            case KLEINIAN:
            case FORMULA42:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(12);
                break;
            case FORMULA27:
                if (fns.julia) {
                    xCenter = new MyApfloat(-2);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(6);
                } else {
                    xCenter = new MyApfloat(0.0);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(3);
                }
                break;
            case FORMULA28:
            case FORMULA29:
                if (fns.julia) {
                    xCenter = new MyApfloat(0.0);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(12);
                } else {
                    xCenter = new MyApfloat(0.0);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(3);
                }
                break;
            case LYAPUNOV:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(24);
                break;
            default:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(6);
                break;
        }
    }

    public void resetConvergentBailoutFormulas() {
        if (isConvergingType() || isEscapingOrConvergingType()) {
            fns.cbs.convergent_bailout_test_user_formula = "norm(z - p)";
            fns.cbs.convergent_bailout_test_user_formula2 = "cbail";
        } else if(isMagnetType()) {
            fns.cbs.convergent_bailout_test_user_formula = "norm(z - 1)";
            fns.cbs.convergent_bailout_test_user_formula2 = "cbail";
        }
        else {
            fns.cbs.convergent_bailout_test_user_formula = "norm(z - p)";
            fns.cbs.convergent_bailout_test_user_formula2 = "cbail";
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
        }
        else if(isMagnetPataki(fns.function)) {
            String power;

            if(fns.function == MAGNET_PATAKI2) {
                power = "2.0";
            }
            else if(fns.function == MAGNET_PATAKI3) {
                power = "3.0";
            }
            else if(fns.function == MAGNET_PATAKI4) {
                power = "4.0";
            }
            else if(fns.function == MAGNET_PATAKI5) {
                power = "5.0";
            }
            else {
                power = "" + fns.z_exponent;
            }

            fns.user_out_coloring_algorithm = 0;

            fns.outcoloring_formula = "n - log(log(norm(z))/log(bail)) / log(sqrt(" + power +"))";

            fns.user_outcoloring_conditions[0] = "im(z)";
            fns.user_outcoloring_conditions[1] = "0";

            fns.user_outcoloring_condition_formula[0] = "n - log(log(norm(z))/log(bail)) / log(sqrt(" + power +"))";
            fns.user_outcoloring_condition_formula[1] = "-(n - log(log(norm(z))/log(bail)) / log(sqrt(" + power +")) + 50)";
            fns.user_outcoloring_condition_formula[2] = "n - log(log(norm(z))/log(bail)) / log(sqrt(" + power +"))";
        }
        else {
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
            pps.sts.user_statistic_formula = "(0.5 * cos(6 * (arg(z-p) + pi)) + 0.5) / (12 + 1 / norm(z-p))";
            pps.sts.useAverage = false;
        } else {
            pps.sts.user_statistic_formula = "(0.5 * cos(12 * arg(z)) + 0.5) / norm(z)";
            pps.sts.useAverage = true;
        }
        pps.sts.reductionFunction = REDUCTION_SUM;
        pps.sts.user_statistic_init_value = "0.0";
    }

    public void defaultFractalSettings() {

        switch (fns.function) {
            case MANDEL_NEWTON:
            case FORMULA1:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(24);
                fns.bailout = fns.bailout < 8 ? 8 : fns.bailout;
                break;
            case MAGNET1:
            case MAGNET13:
            case MAGNET14:
                if (fns.julia) {
                    xCenter = new MyApfloat(0.0);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(28);
                    fns.bailout = fns.bailout < 13 ? 13 : fns.bailout;
                } else {
                    xCenter = new MyApfloat(1.35);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(8);
                    fns.bailout = fns.bailout < 13 ? 13 : fns.bailout;
                }
                break;
            case MAGNET2:
            case MAGNET23:
            case MAGNET24:
                if (fns.julia) {
                    xCenter = new MyApfloat(0.0);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(56);
                    fns.bailout = fns.bailout < 13 ? 13 : fns.bailout;
                } else {
                    xCenter = new MyApfloat(1.0);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(7);
                    fns.bailout = fns.bailout < 13 ? 13 : fns.bailout;
                }
                break;
            case BARNSLEY1:
            case BARNSLEY2:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(7);
                fns.bailout = fns.bailout < 2 ? 2 : fns.bailout;
                break;
            case SIERPINSKI_GASKET:
                xCenter = new MyApfloat(0.5);
                yCenter = new MyApfloat(0.5);
                size = new MyApfloat(3);
                fns.bailout = fns.bailout < 100 ? 100 : fns.bailout;
                break;
            case KLEINIAN:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(12);
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
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(6);
                fns.bailout = fns.bailout < 8 ? 8 : fns.bailout;
                break;
            case FORMULA2:
            case FORMULA13:
            case FORMULA14:
            case FORMULA15:
            case FORMULA16:
            case FORMULA17:
            case FORMULA19:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(6);
                fns.bailout = fns.bailout < 4 ? 4 : fns.bailout;
                break;
            case FORMULA3:
            case FORMULA9:
            case FORMULA10:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(3);
                fns.bailout = fns.bailout < 4 ? 4 : fns.bailout;
                break;
            case FORMULA4:
            case FORMULA5:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(1.5);
                fns.bailout = fns.bailout < 4 ? 4 : fns.bailout;
                break;
            case FORMULA7:
            case FORMULA12:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(12);
                fns.bailout = fns.bailout < 8 ? 8 : fns.bailout;
                break;
            case FORMULA8:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(3);
                fns.bailout = fns.bailout < 16 ? 16 : fns.bailout;
                break;
            case FORMULA11:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(1.5);
                fns.bailout = fns.bailout < 100 ? 100 : fns.bailout;
                break;
            case FORMULA27:
                if (fns.julia) {
                    xCenter = new MyApfloat(-2);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(6);
                    fns.bailout = fns.bailout < 8 ? 8 : fns.bailout;
                } else {
                    xCenter = new MyApfloat(0.0);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(3);
                    fns.bailout = fns.bailout < 8 ? 8 : fns.bailout;
                }
                break;
            case FORMULA28:
            case FORMULA29:
                if (fns.julia) {
                    xCenter = new MyApfloat(0.0);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(12);
                    fns.bailout = fns.bailout < 16 ? 16 : fns.bailout;
                } else {
                    xCenter = new MyApfloat(0.0);
                    yCenter = new MyApfloat(0.0);
                    size = new MyApfloat(3);
                    fns.bailout = fns.bailout < 16 ? 16 : fns.bailout;
                }
                break;
            case FORMULA32:
            case FORMULA33:
            case FORMULA35:
            case FORMULA36:
            case FORMULA37:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(6);
                fns.bailout = fns.bailout < 16 ? 16 : fns.bailout;
                break;
            case FORMULA38:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(1.5);
                fns.bailout = fns.bailout < 2 ? 2 : fns.bailout;
                break;
            case FORMULA42:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(12);
                fns.bailout = fns.bailout < 12 ? 12 : fns.bailout;
                break;
            case FORMULA43:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(24);
                fns.bailout = fns.bailout < 12 ? 12 : fns.bailout;
                break;
            case FORMULA44:
            case FORMULA45:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(24);
                fns.bailout = fns.bailout < 16 ? 16 : fns.bailout;
                break;
            case FORMULA46:
            case MAGNET_PATAKI2:
            case MAGNET_PATAKI3:
            case MAGNET_PATAKI4:
            case MAGNET_PATAKI5:
            case MAGNET_PATAKIK:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(6);
                fns.bailout = fns.bailout < 100 ? 100 : fns.bailout;
                break;
            case LYAPUNOV:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(24);
                fns.bailout = fns.bailout < 100 ? 100 : fns.bailout;
                break;
            case SZEGEDI_BUTTERFLY1:
            case SZEGEDI_BUTTERFLY2:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(12);
                fns.bailout = fns.bailout < 4 ? 4 : fns.bailout;
                break;
            default:
                xCenter = new MyApfloat(0.0);
                yCenter = new MyApfloat(0.0);
                size = new MyApfloat(6);

                if(!isRootSolvingMethod(fns.function) && fns.function != NOVA && fns.function != MAGNETIC_PENDULUM) {
                    fns.bailout = fns.bailout < 2 ? 2 : fns.bailout;
                }
                break;
        }

        fns.period = 0;
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

    public boolean hasConvergentBailoutCondition() {
        return (isConvergingType() || isMagnetType() || isEscapingOrConvergingType()) && fns.function != MAGNETIC_PENDULUM;
    }

    public boolean isConvergingType() {

        return isRootFindingMethod(fns.function)
                || fns.function == NOVA
                || fns.function == USER_FORMULA_NOVA
                || fns.function == LAMBERT_W_VARIATION
                || fns.function == NEWTON_THIRD_DEGREE_PARAMETER_SPACE
                || (fns.function == USER_FORMULA && fns.bail_technique == 1) || (fns.function == USER_FORMULA_ITERATION_BASED && fns.bail_technique == 1) || (fns.function == USER_FORMULA_CONDITIONAL && fns.bail_technique == 1) || (fns.function == USER_FORMULA_COUPLED && fns.bail_technique == 1);

    }

    public boolean isMagnetType() {

        return fns.function == MAGNET1 || fns.function == MAGNET2 || fns.function == MAGNET13 || fns.function == MAGNET14 || fns.function == MAGNET23 || fns.function == MAGNET24;

    }

    public boolean isEscapingOrConvergingType() {
        return (fns.function == USER_FORMULA && fns.bail_technique == 2) || (fns.function == USER_FORMULA_ITERATION_BASED && fns.bail_technique == 2) || (fns.function == USER_FORMULA_CONDITIONAL && fns.bail_technique == 2) || (fns.function == USER_FORMULA_COUPLED && fns.bail_technique == 2);
    }

    public static boolean isRootFindingMethod(int function ) {

        return function == MAGNETIC_PENDULUM || isRootSolvingMethod(function);

    }

    public static boolean isRootSolvingMethod(int function) {

        return isRoot3Function(function)
                || isRoot4Function(function)
                || isRootGeneralized3Function(function)
                || isRootGeneralized8Function(function)
                || isRootCosFunction(function)
                || isRootSinFunction(function)
                || isRootPolynomialFunction(function)
                || isRootFormulaFunction(function);
    }

    public boolean functionSupportsC() {

        return !isRootFindingMethod(fns.function) && fns.function != KLEINIAN && fns.function != SIERPINSKI_GASKET && fns.function != INERTIA_GRAVITY
                && (fns.function != USER_FORMULA_NOVA || (fns.function == USER_FORMULA_NOVA && userFormulaHasC)) && (fns.function != USER_FORMULA || (fns.function == USER_FORMULA && userFormulaHasC)) && (fns.function != USER_FORMULA_ITERATION_BASED || (fns.function == USER_FORMULA_ITERATION_BASED && userFormulaHasC)) && (fns.function != USER_FORMULA_CONDITIONAL || (fns.function == USER_FORMULA_CONDITIONAL && userFormulaHasC)) && (fns.function != USER_FORMULA_COUPLED || (fns.function == USER_FORMULA_COUPLED && userFormulaHasC))
                && (fns.function != LYAPUNOV || (fns.function == LYAPUNOV && userFormulaHasC));

    }

    public static boolean isPolynomialFunction(int function) {

        return function == MANDELPOLY  || isRootPolynomialFunction(function);
    }

    public static boolean isRootPolynomialFunction(int function) {

        return function == NEWTONPOLY || function == HALLEYPOLY || function == SCHRODERPOLY || function == HOUSEHOLDERPOLY || function == SECANTPOLY || function == STEFFENSENPOLY || function == MULLERPOLY || function == PARHALLEYPOLY || function == LAGUERREPOLY || function == DURAND_KERNERPOLY || function == BAIRSTOWPOLY || function == NEWTON_HINESPOLY || function == WHITTAKERPOLY || function == WHITTAKERDOUBLECONVEXPOLY || function == SUPERHALLEYPOLY || function == TRAUB_OSTROWSKIPOLY || function == STIRLINGPOLY || function == MIDPOINTPOLY
                || function == ABERTH_EHRLICHPOLY || function == JARATTPOLY || function == JARATT2POLY || function == THIRDORDERNEWTONPOLY || function == WEERAKOON_FERNANDOPOLY
                || function == HOUSEHOLDER3POLY || function == ABBASBANDYPOLY
                || function == CONTRA_HARMONIC_NEWTONPOLY || function == CHUN_HAMPOLY || function == CHUN_KIMPOLY
                || function == EULER_CHEBYSHEVPOLY || function == EZZATI_SALEKI2POLY || function == HOMEIER1POLY
                || function == ABBASBANDY2POLY || function == ABBASBANDY3POLY || function == POPOVSKI1POLY
                || function == CHANGBUM_CHUN1POLY || function == CHANGBUM_CHUN2POLY || function == KING3POLY
                || function == HOMEIER2POLY || function == KOU_LI_WANG1POLY || function == KIM_CHUNPOLY
                || function == MAHESHWERIPOLY || function == RAFIS_RAFIULLAHPOLY || function == RAFIULLAH1POLY
                || function == CHANGBUM_CHUN3POLY || function == EZZATI_SALEKI1POLY || function == FENGPOLY
                || function == KING1POLY || function == NOOR_GUPTAPOLY || function == HARMONIC_SIMPSON_NEWTONPOLY
                || function == NEDZHIBOVPOLY || function == SIMPSON_NEWTONPOLY;

    }

    public static boolean isRoot3Function(int function) {

        return function == NEWTON3 || function == HALLEY3 || function == HOUSEHOLDER3 || function == SCHRODER3 || function == SECANT3 || function == STEFFENSEN3 || function == MULLER3 || function == PARHALLEY3 || function == LAGUERRE3 || function == DURAND_KERNER3 || function == BAIRSTOW3 || function == NEWTON_HINES3 || function == WHITTAKER3 || function == WHITTAKERDOUBLECONVEX3 || function == SUPERHALLEY3 || function == TRAUB_OSTROWSKI3 || function == STIRLING3 || function == MIDPOINT3
                || function == ABERTH_EHRLICH3 || function == JARATT3 || function == JARATT23 || function == THIRDORDERNEWTON3 || function == WEERAKOON_FERNANDO3
                || function == HOUSEHOLDER33 || function == ABBASBANDY3
                || function == CONTRA_HARMONIC_NEWTON3 || function == CHUN_HAM3 || function == CHUN_KIM3
                || function == EULER_CHEBYSHEV3 || function == EZZATI_SALEKI23 || function == HOMEIER13
                || function == ABBASBANDY23 || function == ABBASBANDY33 || function == POPOVSKI13
                || function == CHANGBUM_CHUN13 || function == CHANGBUM_CHUN23 || function == KING33
                || function == HOMEIER23 || function == KOU_LI_WANG13 || function == KIM_CHUN3
                || function == MAHESHWERI3 || function == RAFIS_RAFIULLAH3 || function == RAFIULLAH13
                || function == CHANGBUM_CHUN33 || function == EZZATI_SALEKI13 || function == FENG3
                || function == KING13 || function == NOOR_GUPTA3 || function == HARMONIC_SIMPSON_NEWTON3
                || function == NEDZHIBOV3 || function == SIMPSON_NEWTON3;


    }

    public static boolean isRootGeneralized3Function(int function) {

        return function == NEWTONGENERALIZED3 || function == HALLEYGENERALIZED3 || function == HOUSEHOLDERGENERALIZED3 || function == SCHRODERGENERALIZED3 || function == SECANTGENERALIZED3 || function == STEFFENSENGENERALIZED3 || function == MULLERGENERALIZED3 || function == PARHALLEYGENERALIZED3 || function == LAGUERREGENERALIZED3 || function == DURAND_KERNERGENERALIZED3 || function == BAIRSTOWGENERALIZED3 || function == NEWTON_HINESGENERALIZED3 || function == WHITTAKERGENERALIZED3 || function == WHITTAKERDOUBLECONVEXGENERALIZED3 || function == SUPERHALLEYGENERALIZED3 || function == TRAUB_OSTROWSKIGENERALIZED3 || function == STIRLINGGENERALIZED3 || function == MIDPOINTGENERALIZED3
                || function == ABERTH_EHRLICHGENERALIZED3 || function == JARATTGENERALIZED3 || function == JARATT2GENERALIZED3 || function == THIRDORDERNEWTONGENERALIZED3 || function == WEERAKOON_FERNANDOGENERALIZED3
                || function == HOUSEHOLDER3GENERALIZED3 || function == ABBASBANDYGENERALIZED3
        || function == CONTRA_HARMONIC_NEWTONGENERALIZED3 || function == CHUN_HAMGENERALIZED3 || function == CHUN_KIMGENERALIZED3
                || function == EULER_CHEBYSHEVGENERALIZED3 || function == EZZATI_SALEKI2GENERALIZED3 || function == HOMEIER1GENERALIZED3
                || function == ABBASBANDY2GENERALIZED3 || function == ABBASBANDY3GENERALIZED3 || function == POPOVSKI1GENERALIZED3
                || function == CHANGBUM_CHUN1GENERALIZED3 || function == CHANGBUM_CHUN2GENERALIZED3 || function == KING3GENERALIZED3
                || function == HOMEIER2GENERALIZED3 || function == KOU_LI_WANG1GENERALIZED3 || function == KIM_CHUNGENERALIZED3
                || function == MAHESHWERIGENERALIZED3 || function == RAFIS_RAFIULLAHGENERALIZED3 || function == RAFIULLAH1GENERALIZED3
                || function == CHANGBUM_CHUN3GENERALIZED3 || function == EZZATI_SALEKI1GENERALIZED3 || function == FENGGENERALIZED3
                || function == KING1GENERALIZED3 || function == NOOR_GUPTAGENERALIZED3 || function == HARMONIC_SIMPSON_NEWTONGENERALIZED3
                || function == NEDZHIBOVGENERALIZED3 || function == SIMPSON_NEWTONGENERALIZED3;



    }

    public static boolean isRootGeneralized8Function(int function) {

        return function == NEWTONGENERALIZED8 || function == HALLEYGENERALIZED8 || function == HOUSEHOLDERGENERALIZED8 || function == SCHRODERGENERALIZED8 || function == SECANTGENERALIZED8 || function == MULLERGENERALIZED8 || function == PARHALLEYGENERALIZED8 || function == LAGUERREGENERALIZED8 || function == DURAND_KERNERGENERALIZED8 || function == BAIRSTOWGENERALIZED8 || function == NEWTON_HINESGENERALIZED8 || function == WHITTAKERGENERALIZED8 || function == WHITTAKERDOUBLECONVEXGENERALIZED8 || function == SUPERHALLEYGENERALIZED8 || function == TRAUB_OSTROWSKIGENERALIZED8 || function == STIRLINGGENERALIZED8 || function == MIDPOINTGENERALIZED8
                || function == ABERTH_EHRLICHGENERALIZED8 || function == JARATTGENERALIZED8 || function == JARATT2GENERALIZED8 || function == THIRDORDERNEWTONGENERALIZED8 || function == WEERAKOON_FERNANDOGENERALIZED8
                || function == HOUSEHOLDER3GENERALIZED8 || function == ABBASBANDYGENERALIZED8
                || function == CONTRA_HARMONIC_NEWTONGENERALIZED8 || function == CHUN_HAMGENERALIZED8 || function == CHUN_KIMGENERALIZED8
        || function == EULER_CHEBYSHEVGENERALIZED8 || function == EZZATI_SALEKI2GENERALIZED8 || function == HOMEIER1GENERALIZED8
                || function == ABBASBANDY2GENERALIZED8 || function == ABBASBANDY3GENERALIZED8 || function == POPOVSKI1GENERALIZED8
                || function == CHANGBUM_CHUN1GENERALIZED8 || function == CHANGBUM_CHUN2GENERALIZED8 || function == KING3GENERALIZED8
                || function == HOMEIER2GENERALIZED8 || function == KOU_LI_WANG1GENERALIZED8 || function == KIM_CHUNGENERALIZED8
                || function == MAHESHWERIGENERALIZED8 || function == RAFIS_RAFIULLAHGENERALIZED8 || function == RAFIULLAH1GENERALIZED8
                || function == CHANGBUM_CHUN3GENERALIZED8 || function == EZZATI_SALEKI1GENERALIZED8 || function == FENGGENERALIZED8
                || function == KING1GENERALIZED8 || function == NOOR_GUPTAGENERALIZED8 || function == HARMONIC_SIMPSON_NEWTONGENERALIZED8
                || function == NEDZHIBOVGENERALIZED8 || function == SIMPSON_NEWTONGENERALIZED8;


    }

    public static boolean isRootSinFunction(int function) {

        return function == NEWTONSIN || function == HALLEYSIN || function == HOUSEHOLDERSIN || function == SCHRODERSIN || function == MULLERSIN || function == PARHALLEYSIN || function == LAGUERRESIN || function == NEWTON_HINESSIN || function == WHITTAKERSIN || function == WHITTAKERDOUBLECONVEXSIN || function == SUPERHALLEYSIN || function == TRAUB_OSTROWSKISIN || function == STIRLINGSIN || function == MIDPOINTSIN
                || function == JARATTSIN || function == JARATT2SIN || function == THIRDORDERNEWTONSIN || function == WEERAKOON_FERNANDOSIN
                || function == HOUSEHOLDER3SIN || function == ABBASBANDYSIN
                || function == CONTRA_HARMONIC_NEWTONSIN || function == CHUN_HAMSIN || function == CHUN_KIMSIN
                || function == EULER_CHEBYSHEVSIN || function == EZZATI_SALEKI2SIN || function == HOMEIER1SIN
                || function == ABBASBANDY2SIN|| function == ABBASBANDY3SIN || function == POPOVSKI1SIN
                || function == CHANGBUM_CHUN1SIN || function == CHANGBUM_CHUN2SIN || function == KING3SIN
                || function == HOMEIER2SIN || function == KOU_LI_WANG1SIN || function == KIM_CHUNSIN
                || function == MAHESHWERISIN || function == RAFIS_RAFIULLAHSIN || function == RAFIULLAH1SIN
                || function == CHANGBUM_CHUN3SIN || function == EZZATI_SALEKI1SIN || function == FENGSIN
                || function == KING1SIN || function == NOOR_GUPTASIN || function == HARMONIC_SIMPSON_NEWTONSIN
                || function == NEDZHIBOVSIN || function == SIMPSON_NEWTONSIN;


    }

    public static boolean isRootCosFunction(int function) {

        return function == NEWTONCOS || function == HALLEYCOS || function == HOUSEHOLDERCOS || function == SCHRODERCOS || function == SECANTCOS || function == MULLERCOS || function == PARHALLEYCOS || function == LAGUERRECOS || function == NEWTON_HINESCOS || function == WHITTAKERCOS || function == WHITTAKERDOUBLECONVEXCOS || function == SUPERHALLEYCOS || function == TRAUB_OSTROWSKICOS || function == STIRLINGCOS || function == MIDPOINTCOS
                || function == JARATTCOS || function == JARATT2COS || function == THIRDORDERNEWTONCOS || function == WEERAKOON_FERNANDOCOS
                || function == HOUSEHOLDER3COS || function == ABBASBANDYCOS
                || function == CONTRA_HARMONIC_NEWTONCOS|| function == CHUN_HAMCOS || function == CHUN_KIMCOS
                || function == EULER_CHEBYSHEVCOS || function == EZZATI_SALEKI2COS || function == HOMEIER1COS
                || function == ABBASBANDY2COS || function == ABBASBANDY3COS || function == POPOVSKI1COS
                || function == CHANGBUM_CHUN1COS || function == CHANGBUM_CHUN2COS || function == KING3COS
                || function == HOMEIER2COS || function == KOU_LI_WANG1COS || function == KIM_CHUNCOS
                || function == MAHESHWERICOS || function == RAFIS_RAFIULLAHCOS || function == RAFIULLAH1COS
                || function == CHANGBUM_CHUN3COS || function == EZZATI_SALEKI1COS || function == FENGCOS
                || function == KING1COS || function == NOOR_GUPTACOS || function == HARMONIC_SIMPSON_NEWTONCOS
                || function == NEDZHIBOVCOS || function == SIMPSON_NEWTONCOS;


    }

    public static boolean isRoot4Function(int function) {

        return function == NEWTON4 || function == HALLEY4 || function == HOUSEHOLDER4 || function == SCHRODER4 || function == SECANT4 || function == STEFFENSEN4 || function == MULLER4 || function == PARHALLEY4 || function == LAGUERRE4 || function == DURAND_KERNER4 || function == BAIRSTOW4 || function == NEWTON_HINES4 || function == WHITTAKER4 || function == WHITTAKERDOUBLECONVEX4 || function == SUPERHALLEY4 || function == TRAUB_OSTROWSKI4 || function == STIRLING4 || function == MIDPOINT4
                || function == ABERTH_EHRLICH4 || function == JARATT4 || function == JARATT24 || function == THIRDORDERNEWTON4 || function == WEERAKOON_FERNANDO4
                || function == HOUSEHOLDER34 || function == ABBASBANDY4
                || function == CONTRA_HARMONIC_NEWTON4 || function == CHUN_HAM4|| function == CHUN_KIM4
                || function == EULER_CHEBYSHEV4 || function == EZZATI_SALEKI24 || function == HOMEIER14
                || function == ABBASBANDY24 || function == ABBASBANDY34 || function == POPOVSKI14
                || function == CHANGBUM_CHUN14 || function == CHANGBUM_CHUN24 || function == KING34
                || function == HOMEIER24 || function == KOU_LI_WANG14|| function == KIM_CHUN4
                || function == MAHESHWERI4 || function == RAFIS_RAFIULLAH4|| function == RAFIULLAH14
                || function == CHANGBUM_CHUN34 || function == EZZATI_SALEKI14 || function == FENG4
                || function == KING14 || function == NOOR_GUPTA4 || function == HARMONIC_SIMPSON_NEWTON4
                || function == NEDZHIBOV4 || function == SIMPSON_NEWTON4;


    }

    public static boolean hasNovaCombinedFFZ(int nova_method) {
        return nova_method == NOVA_STEFFENSEN || nova_method == NOVA_TRAUB_OSTROWSKI || nova_method == NOVA_THIRD_ORDER_NEWTON
        || nova_method == NOVA_CHUN_HAM || nova_method == NOVA_EZZATI_SALEKI2
        || nova_method == NOVA_CHANGBUM_CHUN1 || nova_method == NOVA_CHANGBUM_CHUN2 || nova_method == NOVA_KING3
                || nova_method == NOVA_KOU_LI_WANG1 || nova_method == NOVA_MAHESHWERI
                || nova_method == NOVA_CHANGBUM_CHUN3 || nova_method == NOVA_EZZATI_SALEKI1 || nova_method == NOVA_FENG
                || nova_method == NOVA_KING1 || nova_method == NOVA_NOOR_GUPTA;
    }

    public static boolean hasNovaCombinedDFZ(int nova_method) {
        return nova_method == NOVA_MIDPOINT || nova_method == NOVA_STIRLING || nova_method == NOVA_JARATT || nova_method == NOVA_JARATT2 || nova_method == NOVA_WEERAKOON_FERNANDO
                || nova_method == NOVA_CONTRA_HARMONIC_NEWTON || nova_method == NOVA_CHUN_KIM || nova_method == NOVA_HOMEIER1
                || nova_method == NOVA_HOMEIER2 || nova_method == NOVA_KIM_CHUN || nova_method == NOVA_RAFIULLAH1
                || nova_method == NOVA_CHANGBUM_CHUN3 || nova_method == NOVA_EZZATI_SALEKI1 || nova_method == NOVA_FENG
                || nova_method == NOVA_KING1 || nova_method == NOVA_NOOR_GUPTA || nova_method == NOVA_HARMONIC_SIMPSON_NEWTON
                || nova_method == NOVA_NEDZHIBOV || nova_method == NOVA_SIMPSON_NEWTON;
    }

    public static boolean hasNovaCombinedDDFZ(int nova_method) {
        return nova_method == NOVA_RAFIS_RAFIULLAH;
    }

    public static boolean isTwoFunctionsNovaFormula(int nova_method) {
        return nova_method == NOVA_NEWTON
                || nova_method == NOVA_NEWTON_HINES
                || nova_method == NOVA_MIDPOINT
                || nova_method == NOVA_TRAUB_OSTROWSKI
                || nova_method == NOVA_STIRLING
                || nova_method == NOVA_JARATT
                || nova_method == NOVA_JARATT2
                || nova_method == NOVA_WEERAKOON_FERNANDO
                || nova_method == NOVA_THIRD_ORDER_NEWTON
                || nova_method == NOVA_CONTRA_HARMONIC_NEWTON
                || nova_method == NOVA_CHUN_HAM
                || nova_method == NOVA_CHUN_KIM
                || nova_method == NOVA_HOMEIER1
                || nova_method == NOVA_EZZATI_SALEKI2
                || nova_method == NOVA_CHANGBUM_CHUN1
                || nova_method == NOVA_CHANGBUM_CHUN2
                || nova_method == NOVA_KING3
                || nova_method == NOVA_KIM_CHUN
                || nova_method == NOVA_HOMEIER2
                || nova_method == NOVA_KOU_LI_WANG1
                || nova_method == NOVA_MAHESHWERI
                || nova_method == NOVA_RAFIULLAH1
                || nova_method == NOVA_CHANGBUM_CHUN3
                || nova_method == NOVA_EZZATI_SALEKI1
                || nova_method == NOVA_FENG
                || nova_method == NOVA_KING1
                || nova_method == NOVA_NOOR_GUPTA
                || nova_method == NOVA_HARMONIC_SIMPSON_NEWTON
                || nova_method == NOVA_NEDZHIBOV
                || nova_method == NOVA_SIMPSON_NEWTON;

    }

    public static boolean isThreeFunctionsNovaFormula(int nova_method) {

        return  nova_method == NOVA_HALLEY
                || nova_method == NOVA_PARHALLEY
                || nova_method == NOVA_SCHRODER
                ||nova_method == NOVA_HOUSEHOLDER
                || nova_method == NOVA_HOUSEHOLDER
                || nova_method == NOVA_LAGUERRE
                || nova_method == NOVA_WHITTAKER
                || nova_method == NOVA_WHITTAKER_DOUBLE_CONVEX
                || nova_method == NOVA_SUPER_HALLEY
                || nova_method == NOVA_EULER_CHEBYSHEV
                || nova_method == NOVA_ABBASBANDY2
                || nova_method == NOVA_POPOVSKI1
                || nova_method == NOVA_RAFIS_RAFIULLAH;

    }

    public static boolean isOneFunctionsNovaFormula(int nova_method) {

        return  nova_method == NOVA_SECANT
                || nova_method == NOVA_STEFFENSEN
                || nova_method == NOVA_MULLER;

    }

    public static boolean isFourFunctionsNovaFormula(int nova_method) {

        return  nova_method == NOVA_ABBASBANDY || nova_method == NOVA_HOUSEHOLDER3 || nova_method == NOVA_ABBASBANDY3;

    }

    public static boolean isThreeFunctionsRootFindingMethodFormula(int function) {
        return function == HALLEYFORMULA ||
                function == SCHRODERFORMULA ||
                function == HOUSEHOLDERFORMULA ||
                function == PARHALLEYFORMULA ||
                function == WHITTAKERFORMULA ||
                function == WHITTAKERDOUBLECONVEXFORMULA ||
                function == SUPERHALLEYFORMULA ||
                function == LAGUERREFORMULA ||
                function == EULER_CHEBYSHEVFORMULA ||
                function == ABBASBANDY2FORMULA ||
                function == POPOVSKI1FORMULA ||
                function == RAFIS_RAFIULLAHFORMULA;
    }

    public static boolean isOneFunctionsRootFindingMethodFormula(int function) {
        return function == SECANTFORMULA ||
                function == STEFFENSENFORMULA ||
                function == MULLERFORMULA;
    }

    public static boolean isTwoFunctionsRootFindingMethodFormula(int function) {
        return function == NEWTONFORMULA || function == NEWTON_HINESFORMULA || function == TRAUB_OSTROWSKIFORMULA || function == STIRLINGFORMULA || function == MIDPOINTFORMULA
                || function == JARATTFORMULA || function == JARATT2FORMULA || function == THIRDORDERNEWTONFORMULA|| function == WEERAKOON_FERNANDOFORMULA
                || function == CONTRA_HARMONIC_NEWTONFORMULA || function == CHUN_HAMFORMULA || function == CHUN_KIMFORMULA
                || function == EZZATI_SALEKI2FORMULA || function == HOMEIER1FORMULA
                || function == CHANGBUM_CHUN1FORMULA || function == CHANGBUM_CHUN2FORMULA || function == KING3FORMULA
                || function == HOMEIER2FORMULA || function == KIM_CHUNFORMULA || function == KOU_LI_WANG1FORMULA
                || function == MAHESHWERIFORMULA || function == RAFIULLAH1FORMULA
                || function == CHANGBUM_CHUN3FORMULA || function == EZZATI_SALEKI1FORMULA || function == FENGFORMULA
                || function == KING1FORMULA || function == NOOR_GUPTAFORMULA || function == HARMONIC_SIMPSON_NEWTONFORMULA
                || function == NEDZHIBOVFORMULA || function == SIMPSON_NEWTONFORMULA;
    }

    public static boolean isFourFunctionsRootFindingMethodFormula(int function) {
        return function == HOUSEHOLDER3FORMULA ||
                function == ABBASBANDYFORMULA ||
                function == ABBASBANDY3FORMULA;
    }

    public static boolean isRootFormulaFunction(int function) {

        return isThreeFunctionsRootFindingMethodFormula(function) || isTwoFunctionsRootFindingMethodFormula(function) || isOneFunctionsRootFindingMethodFormula(function) || isFourFunctionsRootFindingMethodFormula(function);

    }

    public static boolean isMagnetPataki(int function) {
        return function == MAGNET_PATAKI2 || function == MAGNET_PATAKI3
                || function == MAGNET_PATAKI4 || function == MAGNET_PATAKI4
                || function == MAGNET_PATAKI5 || function == MAGNET_PATAKIK;
    }

    public static boolean hasFunctionParameterization(int function) {
        return function == LYAPUNOV || function == MAGNETIC_PENDULUM || function == MANDELBROTNTH || function == MANDELBROTWTH
                || function == GENERIC_CaZbdZe || function == GENERIC_CpAZpBC || function == MULLERFORMULA
                || function == LAGUERREFORMULA || function == NOVA || function == KLEINIAN
                || function == INERTIA_GRAVITY || function == USER_FORMULA_ITERATION_BASED || function == USER_FORMULA_CONDITIONAL
                || function == LAMBDA_FN_FN || function == USER_FORMULA || function == USER_FORMULA_COUPLED || function == USER_FORMULA_NOVA
                || isTwoFunctionsRootFindingMethodFormula(function) || isThreeFunctionsRootFindingMethodFormula(function) || isFourFunctionsRootFindingMethodFormula(function)
                || isOneFunctionsRootFindingMethodFormula(function) || isPolynomialFunction(function);
    }

    public static boolean hasPlaneParameterization(int plane) {
        return plane == USER_PLANE || plane == TWIRL_PLANE || plane == SHEAR_PLANE || plane == RIPPLES_PLANE
                || plane == KALEIDOSCOPE_PLANE || plane == PINCH_PLANE || plane == FOLDUP_PLANE
                || plane == FOLDDOWN_PLANE || plane == FOLDRIGHT_PLANE || plane == FOLDLEFT_PLANE || plane == INFLECTION_PLANE
                || plane == BIPOLAR_PLANE || plane == INVERSED_BIPOLAR_PLANE || plane == FOLDIN_PLANE || plane == FOLDOUT_PLANE
                || plane == CIRCLEINVERSION_PLANE || plane == SKEW_PLANE || plane == INFLECTIONS_PLANE;
    }

    public void applyStaticSettings() {

        if (ps.color_choice == CUSTOM_PALETTE_ID) {
            TaskDraw.palette_outcoloring = new CustomPalette(ps.custom_palette, ps.color_interpolation, ps.color_space, ps.reversed_palette, ps.scale_factor_palette_val, ps.processing_alg, fns.smoothing, special_color, color_smoothing_method, special_use_palette_color, fns.smoothing_fractional_transfer_method).getRawPalette();
        } else {
            TaskDraw.palette_outcoloring = new PresetPalette(ps.color_choice, ps.direct_palette, fns.smoothing, special_color, color_smoothing_method, special_use_palette_color, fns.smoothing_fractional_transfer_method).getRawPalette();
        }

        if (ps2.color_choice == CUSTOM_PALETTE_ID) {
            TaskDraw.palette_incoloring = new CustomPalette(ps2.custom_palette, ps2.color_interpolation, ps2.color_space, ps2.reversed_palette, ps2.scale_factor_palette_val, ps2.processing_alg, fns.smoothing, special_color, color_smoothing_method, special_use_palette_color, fns.smoothing_fractional_transfer_method).getRawPalette();
        } else {
            TaskDraw.palette_incoloring = new PresetPalette(ps2.color_choice, ps2.direct_palette, fns.smoothing, special_color, color_smoothing_method, special_use_palette_color, fns.smoothing_fractional_transfer_method).getRawPalette();
        }

        TaskDraw.palette_outcoloring.setGeneratedPaletteSettings(true, gps);
        TaskDraw.palette_incoloring.setGeneratedPaletteSettings(false, gps);

        TaskDraw.USE_DIRECT_COLOR = useDirectColor;

        ColorAlgorithm.GlobalUsingIncrement = globalIncrementBypass;

        TaskDraw.gradient = CustomPalette.createGradient(gs.colorA.getRGB(), gs.colorB.getRGB(), Constants.GRADIENT_LENGTH, gs.gradient_interpolation, gs.gradient_color_space, gs.gradient_reversed, 0);

        TaskDraw.COLOR_SMOOTHING_METHOD = color_smoothing_method;

        SkipBailoutCondition.SKIPPED_ITERATION_COUNT = fns.skip_bailout_iterations;

        SkipConvergentBailoutCondition.SKIPPED_ITERATION_COUNT = fns.skip_convergent_bailout_iterations;
        
        Derivative.DERIVATIVE_METHOD = fns.derivative_method;

        ImageOrbitTrap.image = pps.ots.trapImage;

        ColorAlgorithm.MAGNET_INCREMENT = MagnetColorOffset;

        if(d3s.d3) {
            TaskDraw.setExtraDataArrays(needsExtraData(), d3s.detail);
        }
        else {
            TaskDraw.setExtraDataArrays(needsExtraData(), TaskDraw.IMAGE_SIZE);
        }

        TaskDraw.loadWindowImage(pps.ls.specularReflectionMethod);

        TaskDraw.HSB_CONSTANT_B = hsb_constant_b;
        TaskDraw.HSB_CONSTANT_S = hsb_constant_s;
        TaskDraw.LCHab_CONSTANT_L = lchab_constant_l;
        TaskDraw.LCHab_CONSTANT_C = lchab_constant_c;
        TaskDraw.LCHuv_CONSTANT_L = lchuv_constant_l;
        TaskDraw.LCHuv_CONSTANT_C = lchuv_constant_c;
        TaskDraw.setAlgorithmColors();
    }

    public boolean supportsPeriod() {

        if(julia_map) {
            return false;
        }

        if(fns.julia && fns.juliter) {
            return false;
        }

        return (fns.function == MANDELBROT || fns.function == MANDELBROTCUBED || fns.function == MANDELBROTFOURTH || fns.function == MANDELBROTFIFTH);
    }
    public boolean supportsPerturbationTheory() {

        if(ds.domain_coloring) {
            return false;
        }

        if(julia_map) {
            return false;
        }

        if(fns.julia && fns.juliter) {
            return false;
        }

        return (fns.function == MANDELBROT || fns.function == MANDELBROTCUBED
                || fns.function == MANDELBROTFOURTH || fns.function == MANDELBROTFIFTH
                || fns.function == MANDELBAR || fns.function == LAMBDA
                || fns.function == MAGNET1 || fns.function == NEWTON_THIRD_DEGREE_PARAMETER_SPACE
                || fns.function == NEWTON3 || (fns.function == NOVA && fns.nova_method == NOVA_NEWTON && fns.defaultNovaInitialValue && fns.z_exponent_nova[0] == 3 &&  fns.z_exponent_nova[1] == 0 && fns.relaxation[0] == 1 && fns.relaxation[1] == 0)
                || fns.function == MAGNET_PATAKI2 || fns.function == MAGNET_PATAKI3
                || fns.function == MAGNET_PATAKI4 || fns.function == MAGNET_PATAKI5 || fns.function == FORMULA47
                || fns.function ==  PERPENDICULAR_MANDELBROT || fns.function == BUFFALO_MANDELBROT || fns.function == CELTIC_MANDELBROT
                || fns.function ==  PERPENDICULAR_BURNING_SHIP || fns.function == PERPENDICULAR_CELTIC_MANDELBROT || fns.function == PERPENDICULAR_BUFFALO_MANDELBROT);
    }

    public boolean isPertubationTheoryInUse() {
        return TaskDraw.PERTURBATION_THEORY && supportsPerturbationTheory();
    }

    public boolean isHighPrecisionInUse() {
        return TaskDraw.HIGH_PRECISION_CALCULATION && supportsPerturbationTheory();
    }

    public boolean isPeriodInUse() {
        return isPertubationTheoryInUse() && ((TaskDraw.APPROXIMATION_ALGORITHM == 2 && supportsBilinearApproximation())
                || (TaskDraw.APPROXIMATION_ALGORITHM == 4 && supportsBilinearApproximation2())
                || (TaskDraw.APPROXIMATION_ALGORITHM == 3 && supportsNanomb1())
        ) && supportsPeriod() && fns.period != 0;
    }

    public boolean isNanomb1InUse() {
        return isPertubationTheoryInUse() && TaskDraw.APPROXIMATION_ALGORITHM == 3 && supportsNanomb1() && supportsPeriod();
    }

    public boolean supportsNanomb1() {
        return fns.function == MANDELBROT;
    }

    private boolean needsSmoothing() {
        return (fns.smoothing || ((pps.ndes.useNumericalDem || pps.ls.lighting || pps.ss.slopes || pps.bms.bump_map || pps.cns.contour_coloring || pps.ens.entropy_coloring || pps.rps.rainbow_palette || pps.fdes.fake_de || pps.sts.statistic) && TaskDraw.USE_SMOOTHING_FOR_PROCESSING_ALGS));
    }
    private boolean requiresSmoothingCalculation() {
        return !TaskDraw.SMOOTH_DATA && needsSmoothing();
    }

    private boolean requiresUnSmoothingCalculation() {
        return TaskDraw.SMOOTH_DATA && !needsSmoothing();
    }

    public boolean needsChangeOfSmoothing() {
        return requiresSmoothingCalculation() || requiresUnSmoothingCalculation();
    }

    public boolean supportsBilinearApproximation() {
        return (fns.function == MANDELBROT || fns.function == MANDELBROTCUBED || fns.function == MANDELBROTFOURTH || fns.function == MANDELBROTFIFTH) && !fns.burning_ship && !fns.julia;
    }

    public boolean supportsBilinearApproximation2() {
        return fns.function == MANDELBROT && !fns.burning_ship && !fns.julia;
    }

    public boolean isBilinearApproximationInUse() {
        return TaskDraw.APPROXIMATION_ALGORITHM == 2 && supportsBilinearApproximation();
    }

    public boolean isBilinearApproximation2InUse() {
        return TaskDraw.APPROXIMATION_ALGORITHM == 4 && supportsBilinearApproximation2();
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

    public static double[] fromDDArray(Apfloat[] array) {
        double[] temp = new double[array.length];

        for(int i = 0; i < array.length; i++) {
            temp[i] = array[i].doubleValue();
        }
        return temp;
    }

    public boolean needsPostProcessing() {
        return (
                (!ds.domain_coloring && (pps.hss.histogramColoring
                || pps.ls.lighting
                || pps.bms.bump_map
                || pps.ss.slopes
                || pps.fdes.fake_de
                || pps.ndes.useNumericalDem
                || pps.rps.rainbow_palette
                || pps.ens.entropy_coloring
                || pps.ofs.offset_coloring
                || pps.gss.greyscale_coloring
                || pps.cns.contour_coloring))
                ||
                (ds.domain_coloring && (pps.ls.lighting || pps.bms.bump_map || pps.ss.slopes))
        ) && !useDirectColor;
    }
    public boolean needsExtraData() {
        return fs.filters[Constants.ANTIALIASING]  && (needsPostProcessing() || TaskDraw.ALWAYS_SAVE_EXTRA_PIXEL_DATA_ON_AA);
    }

}
