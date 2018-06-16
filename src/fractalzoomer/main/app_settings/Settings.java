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
package fractalzoomer.main.app_settings;

import fractalzoomer.core.ThreadDraw;
import fractalzoomer.main.Constants;
import fractalzoomer.palettes.CustomPalette;
import fractalzoomer.palettes.PresetPalette;
import fractalzoomer.parser.Parser;
import fractalzoomer.settings.SettingsFractals;
import fractalzoomer.settings.SettingsFractals1049;
import fractalzoomer.settings.SettingsFractals1050;
import fractalzoomer.settings.SettingsFractals1053;
import fractalzoomer.settings.SettingsFractals1054;
import fractalzoomer.settings.SettingsFractals1055;
import fractalzoomer.settings.SettingsFractals1056;
import fractalzoomer.settings.SettingsFractals1057;
import java.awt.Color;
import fractalzoomer.settings.SettingsFractals1058;
import fractalzoomer.settings.SettingsFractals1061;
import fractalzoomer.settings.SettingsFractals1062;
import fractalzoomer.settings.SettingsFractals1063;
import fractalzoomer.settings.SettingsFractals1064;
import fractalzoomer.settings.SettingsFractals1065;
import fractalzoomer.settings.SettingsFractals1066;
import fractalzoomer.settings.SettingsFractals1067;
import fractalzoomer.settings.SettingsFractals1068;
import fractalzoomer.settings.SettingsFractals1069;
import fractalzoomer.settings.SettingsJulia;
import fractalzoomer.settings.SettingsJulia1049;
import fractalzoomer.settings.SettingsJulia1050;
import fractalzoomer.settings.SettingsJulia1053;
import fractalzoomer.settings.SettingsJulia1054;
import fractalzoomer.settings.SettingsJulia1055;
import fractalzoomer.settings.SettingsJulia1056;
import fractalzoomer.settings.SettingsJulia1057;
import fractalzoomer.settings.SettingsJulia1058;
import fractalzoomer.settings.SettingsJulia1061;
import fractalzoomer.settings.SettingsJulia1062;
import fractalzoomer.settings.SettingsJulia1063;
import fractalzoomer.settings.SettingsJulia1064;
import fractalzoomer.settings.SettingsJulia1065;
import fractalzoomer.settings.SettingsJulia1066;
import fractalzoomer.settings.SettingsJulia1067;
import fractalzoomer.settings.SettingsJulia1068;
import fractalzoomer.settings.SettingsJulia1069;
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
    public boolean exterior_de;
    public double exterior_de_factor;
    public boolean polar_projection;
    public boolean inverse_dem;
    public double color_intensity;
    public boolean reversed_palette;
    public DomainColoringSettings ds;
    public double xCenter;
    public double yCenter;
    public double xJuliaCenter;
    public double yJuliaCenter;
    public int color_interpolation;
    public int color_space;
    public double size;
    public double height_ratio;
    public int max_iterations;
    public int color_choice;
    public int color_cycling_location;    
    public double scale_factor_palette_val;
    public int processing_alg;    
    public double circle_period;
    public int color_smoothing_method;
    public Color fractal_color;
    public Color dem_color;
    public Color special_color;
    public int transfer_function;
    public boolean special_use_palette_color;
    public int color_blending;
    public int temp_color_cycling_location;
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
    public String poly;
    public boolean user_formula_c;
    public Parser parser;

    /**
     * ** Custom Palettes ***
     */
    public int[][] custom_palette = {{12, 255, 0, 0}, {12, 255, 127, 0}, {12, 255, 255, 0}, {12, 0, 255, 0}, {12, 0, 0, 255}, {12, 75, 0, 130}, {12, 143, 0, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};

    public Settings() {
        defaultValues();
    }

    public void defaultValues() {

        parser = new Parser();
        user_formula_c = true;

        xCenter = 0;
        yCenter = 0;

        size = 6;

        height_ratio = 1;
        max_iterations = 500;

        scale_factor_palette_val = 0;
        processing_alg = PROCESSING_NONE;

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

        ThreadDraw.gradient = CustomPalette.createGradient(gs.colorA.getRGB(), gs.colorB.getRGB(), Constants.GRADIENT_LENGTH, gs.gradient_interpolation, gs.gradient_color_space, gs.gradient_reversed);

        color_choice = 0;

        color_interpolation = 0;
        color_space = 0;

        color_blending = NORMAL_BLENDING;

        color_intensity = 1;

        color_cycling_location = 0;

        temp_color_cycling_location = 0;

        exterior_de = false;
        exterior_de_factor = 1;
        inverse_dem = false;

        polar_projection = false;
        circle_period = 1;

        transfer_function = LINEAR;

        color_smoothing_method = INTERPOLATION_LINEAR;

        special_use_palette_color = true;

        fractal_color = Color.BLACK;
        dem_color = new Color(1, 0, 0);
        special_color = Color.WHITE;

        d3s = new D3Settings();

        if (color_choice == CUSTOM_PALETTE_ID) {
            ThreadDraw.palette = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, fns.smoothing, special_color, color_smoothing_method, special_use_palette_color);
        } else {
            ThreadDraw.palette = new PresetPalette(color_choice, fns.smoothing, special_color, color_smoothing_method, special_use_palette_color);
        }
        
        ThreadDraw.USE_DIRECT_COLOR = false;
    }

    public void readSettings(String filename, Component parent) throws FileNotFoundException, IOException, ClassNotFoundException {

        ObjectInputStream file_temp = new ObjectInputStream(new FileInputStream(filename));

        SettingsFractals settings = (SettingsFractals) file_temp.readObject();

        int version = settings.getVersion();

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
            }
            else if (version == 1069) {
                xJuliaCenter = ((SettingsJulia1069) settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1069) settings).getYJuliaCenter();
            }

            fns.julia = true;

        } else {
            fns.julia = false;

            fns.perturbation = settings.getPerturbation();
            fns.init_val = settings.getInitVal();

            if (fns.perturbation) {
                if (version < 1056) {
                    fns.variable_perturbation = false;
                    fns.perturbation_vals = settings.getPerturbationVals();
                } else {
                    fns.variable_perturbation = ((SettingsFractals1056) settings).getVariablePerturbation();

                    if (fns.variable_perturbation) {
                        if (version < 1058) {
                            fns.user_perturbation_algorithm = 0;
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
                    fns.variable_init_value = false;
                    fns.initial_vals = settings.getInitialVals();
                } else {
                    fns.variable_init_value = ((SettingsFractals1056) settings).getVariableInitValue();

                    if (fns.variable_init_value) {
                        if (version < 1058) {
                            fns.user_initial_value_algorithm = 0;
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
        color_choice = settings.getColorChoice();

        fractal_color = settings.getFractalColor();

        if (version < 1061) {
            dem_color = new Color(1, 0, 0);
            special_color = Color.WHITE;
            special_use_palette_color = true;
        } else {
            dem_color = ((SettingsFractals1061) settings).getDemColor();
            special_color = ((SettingsFractals1061) settings).getSpecialColor();
            special_use_palette_color = ((SettingsFractals1061) settings).getSpecialUsePaletteColor();
        }

        if (version < 1062) {
            rps.rainbow_palette = false;
            rps.rainbow_palette_factor = 1;

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

        color_cycling_location = settings.getColorCyclingLocation();
        fns.plane_type = settings.getPlaneType();

        if (version < 1057) {
            fns.apply_plane_on_julia = false;
        } else {
            fns.apply_plane_on_julia = ((SettingsFractals1057) settings).getApplyPlaneOnJulia();
        }
        
        if (version < 1069) {
            cns.contour_coloring = false;
            cns.cn_noise_reducing_factor = 0.4;
            cns.cn_blending = 0.7;
            cns.contour_algorithm = 0;
            
            ThreadDraw.USE_DIRECT_COLOR = false;
            
            fns.kleinianLine[0] = 1.93;
            fns.kleinianLine[1] = 0.04;            
            fns.kleinianK = 0.26;
            fns.kleinianM = 4.9;
            
            ots.lineType = 0;
        }
        else {
            cns.contour_coloring = ((SettingsFractals1069)settings).getContourColoring();
            cns.cn_noise_reducing_factor = ((SettingsFractals1069)settings).getContourColoringNoiseReducingFactor();
            cns.cn_blending = ((SettingsFractals1069)settings).getContourColoringBlending();
            cns.contour_algorithm = ((SettingsFractals1069)settings).getContourColoringAlgorithm();
            
            ThreadDraw.USE_DIRECT_COLOR = ((SettingsFractals1069)settings).getDirectColor();
            
            fns.kleinianLine = ((SettingsFractals1069)settings).getKleinianLine();            
            fns.kleinianK = ((SettingsFractals1069)settings).getKleinianK();
            fns.kleinianM = ((SettingsFractals1069)settings).getKleinianM();
            
            ots.lineType = ((SettingsFractals1069)settings).getLineType();
        }

        if (version < 1068) {
            ens.entropy_algorithm = 0;

            ds.domainOrder[0] = Constants.GRID;
            ds.domainOrder[1] = Constants.CIRCLES;
            ds.domainOrder[2] = Constants.ISO_LINES;

            gs.colorA = Color.BLACK;
            gs.colorB = Color.WHITE;
            gs.gradient_color_space = Constants.COLOR_SPACE_RGB;
            gs.gradient_interpolation = Constants.INTERPOLATION_LINEAR;
            gs.gradient_reversed = false;
            
            rps.rainbow_algorithm = 0;
            
            ots.useTraps = false;
            ots.trapType = Constants.POINT_TRAP;
            ots.trapPoint[0] = 0.0;
            ots.trapPoint[1] = 0.0;
            ots.trapLength = 4;
            ots.trapWidth = 0.4;
            ots.trapMaxDistance = 0.5;
            ots.trapBlending = 0.5;
            ots.trapNorm = 2;
            ots.trapUseSpecialColor = false;
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
            ots.trapMaxDistance = ((SettingsFractals1068) settings).getTrapMaxDistance();
            ots.trapBlending = ((SettingsFractals1068) settings).getTrapBlending();
            ots.trapNorm = ((SettingsFractals1068) settings).getTrapNorm();
            ots.trapUseSpecialColor = ((SettingsFractals1068) settings).getTrapUseSpecialColor();
        }

        if (version < 1067) {
            transfer_function = LINEAR;
            color_blending = NORMAL_BLENDING;

            bms.bump_transfer_function = 0;
            bms.bump_transfer_factor = 1.0;
            bms.bump_blending = 0.5;
            bms.bumpProcessing = 0;

            ColorAlgorithm.GlobalIncrementBypass = false;

            fns.waveType = 0;
            fns.plane_transform_wavelength[0] = 0.15;
            fns.plane_transform_wavelength[1] = 0.15;

            ds.iso_distance = 4;
            ds.iso_factor = 0.5;
            ds.logBase = 2.0;
            ds.normType = 2.0;
            ds.gridFactor = 2.0;
            ds.circlesBlending = 1.0;
            ds.isoLinesBlendingFactor = 1.0;
            ds.gridBlending = 1.0;
            ds.gridColor = Color.BLACK;
            ds.circlesColor = Color.WHITE;
            ds.isoLinesColor = Color.WHITE;
            ds.contourBlending = 0.5;
            ds.drawColor = true;
            ds.drawContour = true;
            ds.drawGrid = true;
            ds.drawCircles = false;
            ds.drawIsoLines = true;
            ds.customDomainColoring = false;
            ds.colorType = 0;
            ds.contourType = 0;
        } else {
            transfer_function = ((SettingsFractals1067) settings).getTransferFunction();
            color_blending = ((SettingsFractals1067) settings).getColorBlending();

            bms.bump_transfer_function = ((SettingsFractals1067) settings).getBumpTransferFunction();
            bms.bump_transfer_factor = ((SettingsFractals1067) settings).getBumpTransferFactor();
            bms.bump_blending = ((SettingsFractals1067) settings).getBumpBlending();
            bms.bumpProcessing = ((SettingsFractals1067) settings).getBumpProcessing();

            ColorAlgorithm.GlobalIncrementBypass = ((SettingsFractals1067) settings).getGlobalIncrementBypass();

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
            ens.entropy_coloring = false;
            ens.entropy_palette_factor = 50;
            ens.en_noise_reducing_factor = 0.4;
            fns.apply_plane_on_julia_seed = true;
            ofs.offset_coloring = false;
            ofs.post_process_offset = 300;
            ofs.of_noise_reducing_factor = 0.4;
            ens.en_blending = 0.7;
            ofs.of_blending = 0.7;
            rps.rp_blending = 0.7;
            ens.entropy_offset = 0;
            rps.rainbow_offset = 0;
            gss.greyscale_coloring = false;
            gss.gs_noise_reducing_factor = 0.4;
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
            exterior_de = false;
            exterior_de_factor = 1;
            height_ratio = 1;

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
            }
        }

        if (version < 1063) {
            if (fns.plane_type == BIPOLAR_PLANE || fns.plane_type == INVERSED_BIPOLAR_PLANE) {
                fns.plane_transform_center[0] = 2;
                fns.plane_transform_center[1] = 0;
            }

            ds.domain_coloring = false;
            ds.use_palette_domain_coloring = false;
            ds.domain_coloring_alg = 0;
        } else {
            if (fns.plane_type == BIPOLAR_PLANE || fns.plane_type == INVERSED_BIPOLAR_PLANE) {
                fns.plane_transform_center = ((SettingsFractals1053) settings).getPlaneTransformCenter();
            }

            ds.domain_coloring = ((SettingsFractals1063) settings).getDomainColoring();
            ds.use_palette_domain_coloring = ((SettingsFractals1063) settings).getUsePaletteDomainColoring();
            ds.domain_coloring_alg = ((SettingsFractals1063) settings).getDomainColoringAlg();
        }

        if (fns.plane_type == USER_PLANE) {
            if (version < 1058) {
                fns.user_plane_algorithm = 0;
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

        if (color_choice == CUSTOM_PALETTE_ID) {
            custom_palette = settings.getCustomPalette();
            color_interpolation = settings.getColorInterpolation();
            color_space = settings.getColorSpace();
            reversed_palette = settings.getReveresedPalette();
            temp_color_cycling_location = color_cycling_location;

            if (version < 1062) {
                scale_factor_palette_val = 0;
                processing_alg = PROCESSING_NONE;
            } else {
                processing_alg = ((SettingsFractals1062) settings).getProcessingAlgorithm();
                scale_factor_palette_val = ((SettingsFractals1062) settings).getScaleFactorPaletteValue();
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
                fns.bailout_test_user_formula2 = "bail";
            } else {
                fns.bailout_test_user_formula2 = ((SettingsFractals1058) settings).getBailoutTestUserFormula2();
            }
        }

        fns.rotation_vals[0] = Math.cos(Math.toRadians(fns.rotation));
        fns.rotation_vals[1] = Math.sin(Math.toRadians(fns.rotation));

        fns.out_coloring_algorithm = settings.getOutColoringAlgorithm();

        if (fns.out_coloring_algorithm == ATOM_DOMAIN) { //removed atom domain
            fns.out_coloring_algorithm = ESCAPE_TIME;
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
            inverse_dem = false;
            fdes.inverse_fake_dem = false;
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
            color_smoothing_method = 0;
            bms.bm_noise_reducing_factor = 0.4;
            rps.rp_noise_reducing_factor = 0.4;
        } else {
            color_smoothing_method = ((SettingsFractals1065) settings).getColorSmoothingMethod();
            bms.bm_noise_reducing_factor = ((SettingsFractals1065) settings).getBumpMappingNoiseReducingFactor();
            rps.rp_noise_reducing_factor = ((SettingsFractals1065) settings).getRainbowPaletteNoiseReducingFactor();
        }

        if (version < 1054) {
            fns.escaping_smooth_algorithm = 0;
            fns.converging_smooth_algorithm = 0;

            bms.bump_map = false;
            polar_projection = false;
            color_intensity = 1;
        } else {
            fns.escaping_smooth_algorithm = ((SettingsFractals1054) settings).getEscapingSmoothAgorithm();
            fns.converging_smooth_algorithm = ((SettingsFractals1054) settings).getConvergingSmoothAgorithm();

            bms.bump_map = ((SettingsFractals1054) settings).getBumpMap();

            bms.bumpMappingStrength = ((SettingsFractals1054) settings).getBumpMapStrength();
            bms.bumpMappingDepth = ((SettingsFractals1054) settings).getBumpMapDepth();
            bms.lightDirectionDegrees = ((SettingsFractals1054) settings).getLightDirectionDegrees();

            polar_projection = ((SettingsFractals1054) settings).getPolarProjection();

            circle_period = ((SettingsFractals1054) settings).getCirclePeriod();

            color_intensity = ((SettingsFractals1054) settings).getColorIntensity();
        }

        if (version < 1055) {
            fdes.fake_de = false;
        } else {
            fdes.fake_de = ((SettingsFractals1055) settings).getFakeDe();
            fdes.fake_de_factor = ((SettingsFractals1055) settings).getFakeDeFactor();
        }

        ThreadDraw.gradient = CustomPalette.createGradient(gs.colorA.getRGB(), gs.colorB.getRGB(), Constants.GRADIENT_LENGTH, gs.gradient_interpolation, gs.gradient_color_space, gs.gradient_reversed);

        if (color_choice == CUSTOM_PALETTE_ID) {
            ThreadDraw.palette = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, fns.smoothing, special_color, color_smoothing_method, special_use_palette_color);
        } else {
            ThreadDraw.palette = new PresetPalette(color_choice, fns.smoothing, special_color, color_smoothing_method, special_use_palette_color);
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
                fns.coefficients = settings.getCoefficients();
                createPoly();
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
            case NOVA:
                fns.z_exponent_nova = settings.getZExponentNova();
                fns.relaxation = settings.getRelaxation();
                fns.nova_method = settings.getNovaMethod();
                break;
            case USER_FORMULA:
                fns.user_formula = settings.getUserFormula();
                fns.bail_technique = settings.getBailTechnique();

                if (version < 1049) {
                    fns.user_formula2 = "c";
                } else {
                    fns.user_formula2 = ((SettingsFractals1049) settings).getUserFormula2();
                }

                parser.parse(fns.user_formula);

                user_formula_c = parser.foundC();
                break;
            case USER_FORMULA_ITERATION_BASED:
                fns.user_formula_iteration_based = ((SettingsFractals1049) settings).getUserFormulaIterationBased();
                fns.bail_technique = settings.getBailTechnique();

                boolean temp_bool = false;

                for (int m = 0; m < fns.user_formula_iteration_based.length; m++) {
                    parser.parse(fns.user_formula_iteration_based[m]);
                    temp_bool = temp_bool | parser.foundC();
                }

                user_formula_c = temp_bool;
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

                user_formula_c = temp_bool2;
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

                user_formula_c = temp_bool;
                break;
        }

        if (fns.out_coloring_algorithm != USER_OUTCOLORING_ALGORITHM) {
            resetUserOutColoringFormulas();
        }

        file_temp.close();
    }

    public void save(String filename) {
        ObjectOutputStream file_temp = null;

        try {
            file_temp = new ObjectOutputStream(new FileOutputStream(filename));
            SettingsFractals settings;
            if (fns.julia) {
                settings = new SettingsJulia1069(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, fns.out_coloring_algorithm, fns.user_out_coloring_algorithm, fns.outcoloring_formula, fns.user_outcoloring_conditions, fns.user_outcoloring_condition_formula, fns.in_coloring_algorithm, fns.user_in_coloring_algorithm, fns.incoloring_formula, fns.user_incoloring_conditions, fns.user_incoloring_condition_formula, fns.smoothing, fns.function, fns.bailout_test_algorithm, fns.bailout, fns.bailout_test_user_formula, fns.bailout_test_user_formula2, fns.bailout_test_comparison, fns.n_norm, fns.plane_type, fns.apply_plane_on_julia, fns.burning_ship, fns.z_exponent, fns.z_exponent_complex, color_cycling_location, fns.coefficients, custom_palette, color_interpolation, color_space, reversed_palette, fns.rotation, fns.rotation_center, fns.mandel_grass, fns.mandel_grass_vals, fns.z_exponent_nova, fns.relaxation, fns.nova_method, fns.user_formula, fns.user_formula2, fns.bail_technique, fns.user_plane, fns.user_plane_algorithm, fns.user_plane_conditions, fns.user_plane_condition_formula, fns.user_formula_iteration_based, fns.user_formula_conditions, fns.user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, fns.plane_transform_center, fns.plane_transform_angle, fns.plane_transform_radius, fns.plane_transform_scales, fns.plane_transform_angle2, fns.plane_transform_sides, fns.plane_transform_amount, color_intensity, fns.escaping_smooth_algorithm, fns.converging_smooth_algorithm, bms.bump_map, bms.bumpMappingStrength, bms.bumpMappingDepth, bms.lightDirectionDegrees, polar_projection, circle_period, fdes.fake_de, fdes.fake_de_factor, dem_color, special_color, special_use_palette_color, rps.rainbow_palette, rps.rainbow_palette_factor, fs.filters, fs.filters_options_vals, scale_factor_palette_val, processing_alg, fs.filters_colors, fns.coupling, fns.user_formula_coupled, fns.coupling_method, fns.coupling_amplitude, fns.coupling_frequency, fns.coupling_seed, ds.domain_coloring, ds.use_palette_domain_coloring, ds.domain_coloring_alg, inverse_dem, fdes.inverse_fake_dem, fs.filters_options_extra_vals, fs.filters_extra_colors, color_smoothing_method, fs.filters_order, bms.bm_noise_reducing_factor, rps.rp_noise_reducing_factor, ens.entropy_coloring, ens.entropy_palette_factor, ens.en_noise_reducing_factor, fns.apply_plane_on_julia_seed, ofs.offset_coloring, ofs.post_process_offset, ofs.of_noise_reducing_factor, ens.en_blending, rps.rp_blending, ofs.of_blending, ens.entropy_offset, rps.rainbow_offset, gss.greyscale_coloring, gss.gs_noise_reducing_factor, transfer_function, color_blending, bms.bump_transfer_function, bms.bump_transfer_factor, bms.bump_blending, bms.bumpProcessing, ColorAlgorithm.GlobalIncrementBypass, fns.waveType, fns.plane_transform_wavelength, fns.laguerre_deg, ds.iso_distance, ds.iso_factor, ds.logBase, ds.normType, ds.gridFactor, ds.circlesBlending, ds.isoLinesBlendingFactor, ds.gridBlending, ds.gridColor, ds.circlesColor, ds.isoLinesColor, ds.contourBlending, ds.drawColor, ds.drawContour, ds.drawGrid, ds.drawCircles, ds.drawIsoLines, ds.customDomainColoring, ds.colorType, ds.contourType, ens.entropy_algorithm, ds.domainOrder, gs.colorA, gs.colorB, gs.gradient_color_space, gs.gradient_interpolation, gs.gradient_reversed, rps.rainbow_algorithm, ots.useTraps, ots.trapPoint, ots.trapLength, ots.trapWidth, ots.trapType, ots.trapMaxDistance, ots.trapBlending, ots.trapNorm, ots.trapUseSpecialColor, ots.lineType, cns.contour_coloring, cns.cn_noise_reducing_factor, cns.cn_blending, cns.contour_algorithm, ThreadDraw.USE_DIRECT_COLOR, fns.kleinianLine, fns.kleinianK, fns.kleinianM, xJuliaCenter, yJuliaCenter);
            } else {
                int temp_bailout_test_algorithm = 0;

                if (!isConvergingType()) {
                    temp_bailout_test_algorithm = fns.bailout_test_algorithm;
                }

                settings = new SettingsFractals1069(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, fns.out_coloring_algorithm, fns.user_out_coloring_algorithm, fns.outcoloring_formula, fns.user_outcoloring_conditions, fns.user_outcoloring_condition_formula, fns.in_coloring_algorithm, fns.user_in_coloring_algorithm, fns.incoloring_formula, fns.user_incoloring_conditions, fns.user_incoloring_condition_formula, fns.smoothing, fns.function, temp_bailout_test_algorithm, fns.bailout, fns.bailout_test_user_formula, fns.bailout_test_user_formula2, fns.bailout_test_comparison, fns.n_norm, fns.plane_type, fns.apply_plane_on_julia, fns.burning_ship, fns.z_exponent, fns.z_exponent_complex, color_cycling_location, fns.coefficients, custom_palette, color_interpolation, color_space, reversed_palette, fns.rotation, fns.rotation_center, fns.perturbation, fns.perturbation_vals, fns.variable_perturbation, fns.user_perturbation_algorithm, fns.user_perturbation_conditions, fns.user_perturbation_condition_formula, fns.perturbation_user_formula, fns.init_val, fns.initial_vals, fns.variable_init_value, fns.user_initial_value_algorithm, fns.user_initial_value_conditions, fns.user_initial_value_condition_formula, fns.initial_value_user_formula, fns.mandel_grass, fns.mandel_grass_vals, fns.z_exponent_nova, fns.relaxation, fns.nova_method, fns.user_formula, fns.user_formula2, fns.bail_technique, fns.user_plane, fns.user_plane_algorithm, fns.user_plane_conditions, fns.user_plane_condition_formula, fns.user_formula_iteration_based, fns.user_formula_conditions, fns.user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, fns.plane_transform_center, fns.plane_transform_angle, fns.plane_transform_radius, fns.plane_transform_scales, fns.plane_transform_angle2, fns.plane_transform_sides, fns.plane_transform_amount, color_intensity, fns.escaping_smooth_algorithm, fns.converging_smooth_algorithm, bms.bump_map, bms.bumpMappingStrength, bms.bumpMappingDepth, bms.lightDirectionDegrees, polar_projection, circle_period, fdes.fake_de, fdes.fake_de_factor, fns.user_fz_formula, fns.user_dfz_formula, fns.user_ddfz_formula, dem_color, special_color, special_use_palette_color, rps.rainbow_palette, rps.rainbow_palette_factor, fs.filters, fs.filters_options_vals, scale_factor_palette_val, processing_alg, fs.filters_colors, fns.coupling, fns.user_formula_coupled, fns.coupling_method, fns.coupling_amplitude, fns.coupling_frequency, fns.coupling_seed, ds.domain_coloring, ds.use_palette_domain_coloring, ds.domain_coloring_alg, inverse_dem, fdes.inverse_fake_dem, fs.filters_options_extra_vals, fs.filters_extra_colors, color_smoothing_method, fs.filters_order, bms.bm_noise_reducing_factor, rps.rp_noise_reducing_factor, ens.entropy_coloring, ens.entropy_palette_factor, ens.en_noise_reducing_factor, fns.apply_plane_on_julia_seed, ofs.offset_coloring, ofs.post_process_offset, ofs.of_noise_reducing_factor, ens.en_blending, rps.rp_blending, ofs.of_blending, ens.entropy_offset, rps.rainbow_offset, gss.greyscale_coloring, gss.gs_noise_reducing_factor, transfer_function, color_blending, bms.bump_transfer_function, bms.bump_transfer_factor, bms.bump_blending, bms.bumpProcessing, ColorAlgorithm.GlobalIncrementBypass, fns.waveType, fns.plane_transform_wavelength, fns.laguerre_deg, ds.iso_distance, ds.iso_factor, ds.logBase, ds.normType, ds.gridFactor, ds.circlesBlending, ds.isoLinesBlendingFactor, ds.gridBlending, ds.gridColor, ds.circlesColor, ds.isoLinesColor, ds.contourBlending, ds.drawColor, ds.drawContour, ds.drawGrid, ds.drawCircles, ds.drawIsoLines, ds.customDomainColoring, ds.colorType, ds.contourType, ens.entropy_algorithm, ds.domainOrder, gs.colorA, gs.colorB, gs.gradient_color_space, gs.gradient_interpolation, gs.gradient_reversed, rps.rainbow_algorithm, ots.useTraps, ots.trapPoint, ots.trapLength, ots.trapWidth, ots.trapType, ots.trapMaxDistance, ots.trapBlending, ots.trapNorm, ots.trapUseSpecialColor, ots.lineType, cns.contour_coloring, cns.cn_noise_reducing_factor, cns.cn_blending, cns.contour_algorithm, ThreadDraw.USE_DIRECT_COLOR, fns.kleinianLine, fns.kleinianK, fns.kleinianM);
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
            case LAMBDA:
                if (fns.julia) {
                    xCenter = 0.5;
                    yCenter = 0;
                    size = 6;
                } else {
                    xCenter = 1;
                    yCenter = 0;
                    size = 8;
                }
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
            default:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
        }
    }

    public void resetUserOutColoringFormulas() {

        if (isConvergingType()) {
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

    public void defaultFractalSettings() {

        switch (fns.function) {
            case LAMBDA:
                if (fns.julia) {
                    xCenter = 0.5;
                    yCenter = 0;
                    size = 6;
                    fns.bailout = fns.bailout < 8 ? 8 : fns.bailout;
                } else {
                    xCenter = 1;
                    yCenter = 0;
                    size = 8;
                    fns.bailout = fns.bailout < 2 ? 2 : fns.bailout;
                }
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
            case NOVA:
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
            if (fns.coefficients[l] > 0) {
                if (poly.length() == 7) {
                    poly += fns.coefficients[l] + "z^" + (fns.coefficients.length - l - 1) + "  ";
                } else {
                    poly += "+" + fns.coefficients[l] + "z^" + (fns.coefficients.length - l - 1) + "  ";
                }
            } else if (fns.coefficients[l] < 0) {
                poly += fns.coefficients[l] + "z^" + (fns.coefficients.length - l - 1) + "  ";
            }
        }

        if (fns.coefficients[l] > 0) {
            if (poly.length() == 7) {
                poly += fns.coefficients[l] + "z  ";
            } else {
                poly += "+" + fns.coefficients[l] + "z  ";
            }
        } else if (fns.coefficients[l] < 0) {
            poly += fns.coefficients[l] + "z  ";
        }

        l++;
        if (fns.coefficients[l] > 0) {
            poly += "+" + fns.coefficients[l];
        } else if (fns.coefficients[l] < 0) {
            poly += fns.coefficients[l];
        }
    }

    public boolean isConvergingType() {

        return isRootFindingMethod()
                || fns.function == NOVA
                || (fns.function == USER_FORMULA && fns.bail_technique == 1) || (fns.function == USER_FORMULA_ITERATION_BASED && fns.bail_technique == 1) || (fns.function == USER_FORMULA_CONDITIONAL && fns.bail_technique == 1) || (fns.function == USER_FORMULA_COUPLED && fns.bail_technique == 1);

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
                || fns.function == LAGUERRE3 || fns.function == LAGUERRE4 || fns.function == LAGUERREGENERALIZED3 || fns.function == LAGUERREGENERALIZED8 || fns.function == LAGUERRESIN || fns.function == LAGUERRECOS || fns.function == LAGUERREPOLY || fns.function == LAGUERREFORMULA;

    }

    public boolean functionSupportsC() {

        return !isRootFindingMethod() && fns.function != KLEINIAN && fns.function != SIERPINSKI_GASKET && (fns.function != USER_FORMULA || (fns.function == USER_FORMULA && user_formula_c == true)) && (fns.function != USER_FORMULA_ITERATION_BASED || (fns.function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (fns.function != USER_FORMULA_CONDITIONAL || (fns.function == USER_FORMULA_CONDITIONAL && user_formula_c == true)) && (fns.function != USER_FORMULA_COUPLED || (fns.function == USER_FORMULA_COUPLED && user_formula_c == true));

    }

}
