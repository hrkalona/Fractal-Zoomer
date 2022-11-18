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
package fractalzoomer.gui;

import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.parser.ParserException;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author hrkalona2
 */
public class StatisticsColoringFrame extends JFrame {

    private static final long serialVersionUID = -17558636585L;
    private MainWindow ptra2;
    private StatisticsColoringFrame this_frame;
    private StatisticsSettings sts;
    private JCheckBox normalMap;
    private JSlider color_blend_opt;
    private JCheckBox rootShading;
    private JComboBox<String> color_method_combo;
    private JSlider nmLightFactor;
    private JCheckBox normalMapOverrideColoring;
    private JSlider root_color_blend_opt;
    private JComboBox<String> root_color_method_combo;
    private JList<String> list;
    private JLabel colorsLength;
    private JComboBox<String> langNormType;
    private JTextField langNNorm;
    private JRadioButton lagrangian;
    private JComboBox<String> atomNormType;
    private JTextField atomNNorm;
    private JRadioButton atomDomain;
    private JCheckBox de;
    private JTextField deUpperLimitFactor;

    private JCheckBox smoothDE;

    private JComboBox<String> root_shading_function_combo;

    private JCheckBox rootSmoothing;

    private JComboBox<String> de_fade_method_combo;

    public StatisticsColoringFrame(MainWindow ptra, StatisticsSettings sts2, Settings s, boolean periodicity_checking) {

        super();

        ptra2 = ptra;
        this_frame = this;

        sts = new StatisticsSettings(sts2);

        JRadioButton stripe_average = new JRadioButton(Constants.statisticalColoringName[MainWindow.STRIPE_AVERAGE]);
        JRadioButton curvature_average = new JRadioButton(Constants.statisticalColoringName[MainWindow.CURVATURE_AVERAGE]);
        JRadioButton triangle_inequality_average = new JRadioButton(Constants.statisticalColoringName[MainWindow.TRIANGLE_INEQUALITY_AVERAGE]);
        JRadioButton alg1 = new JRadioButton(Constants.statisticalColoringName[MainWindow.COS_ARG_DIVIDE_NORM_AVERAGE]);
        JRadioButton alg2 = new JRadioButton(Constants.statisticalColoringName[MainWindow.COS_ARG_DIVIDE_INVERSE_NORM]);
        atomDomain  = new JRadioButton(Constants.statisticalColoringName[MainWindow.ATOM_DOMAIN_BOF60_BOF61]);
        lagrangian = new JRadioButton(Constants.statisticalColoringName[MainWindow.DISCRETE_LAGRANGIAN_DESCRIPTORS]);
        JRadioButton twinLamps = new JRadioButton(Constants.statisticalColoringName[MainWindow.TWIN_LAMPS]);

        if(s.fns.function != MainWindow.MANDELBROT) {
            triangle_inequality_average.setEnabled(false);
        }

        if(s.isConvergingType()) {
            stripe_average.setEnabled(false);
            curvature_average.setEnabled(false);
            alg1.setEnabled(false);
            triangle_inequality_average.setEnabled(false);
            twinLamps.setEnabled(false);
        }
        else if(!s.isMagnetType() && !s.isEscapingOrConvergingType()) {
            alg2.setEnabled(false);
        }

        ptra2.setEnabled(false);
        int custom_palette_window_width = (MainWindow.runsOnWindows ? 680 : 780) + 90;
        int custom_palette_window_height = 780;
        setTitle("Statistical Coloring");
        setIconImage(MainWindow.getIcon("statistics_coloring.png").getImage());

        setSize(custom_palette_window_width, custom_palette_window_height);
        setLocation((int)(ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (custom_palette_window_width / 2), (int)(ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (custom_palette_window_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                ptra2.setEnabled(true);
                dispose();

            }
        });

        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        panel2.setBackground(MainWindow.bg_color);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout());
        panel3.setBackground(MainWindow.bg_color);
        panel3.setPreferredSize(new Dimension((MainWindow.runsOnWindows ? 580 : 680) + 90, 640));

        JCheckBox statistics = new JCheckBox("Statistical Coloring");
        statistics.setFocusable(false);
        statistics.setBackground(MainWindow.bg_color);
        statistics.setSelected(sts.statistic);

        JTextField intensity = new JTextField(10);
        intensity.setText("" + sts.statistic_intensity);
        panel2.add(statistics);
        panel2.add(new JLabel(" Intensity: "));
        panel2.add(intensity);
        
        final JCheckBox include_escaped_opt = new JCheckBox("Include Escaped");
        include_escaped_opt.setFocusable(false);
        include_escaped_opt.setToolTipText("Includes the escaped points into the statistic application.");
        include_escaped_opt.setBackground(MainWindow.bg_color);
        include_escaped_opt.setSelected(sts.statisticIncludeEscaped);
        
        final JCheckBox include_notescaped_opt = new JCheckBox("Include Not Escaped");
        include_notescaped_opt.setFocusable(false);
        include_notescaped_opt.setToolTipText("Includes the not escaped points into the statistic application.");
        include_notescaped_opt.setBackground(MainWindow.bg_color);
        include_notescaped_opt.setSelected(sts.statisticIncludeNotEscaped);
        
        panel2.add(include_escaped_opt);
        panel2.add(include_notescaped_opt);

        JCheckBox smoothing = new JCheckBox("Smooth Sum");
        smoothing.setFocusable(false);
        smoothing.setSelected(sts.useSmoothing);
        smoothing.setBackground(MainWindow.bg_color);
        smoothing.setToolTipText("Smooths the computed value of the sum.");

        JCheckBox average = new JCheckBox("Average Sum");
        average.setFocusable(false);
        average.setSelected(sts.useAverage);
        average.setBackground(MainWindow.bg_color);
        average.setToolTipText("Averages the computed value of the sum.");


        if(sts.statisticGroup == 1) {
            smoothing.setEnabled(sts.reductionFunction == MainWindow.REDUCTION_SUM);
            average.setEnabled(sts.reductionFunction == MainWindow.REDUCTION_SUM);
        }
        else if (sts.statisticGroup == 0) {
            smoothing.setEnabled(sts.statistic_type != MainWindow.ATOM_DOMAIN_BOF60_BOF61 && sts.statistic_type != MainWindow.COS_ARG_DIVIDE_INVERSE_NORM && sts.statistic_type != MainWindow.TWIN_LAMPS);
            average.setEnabled(sts.statistic_type != MainWindow.ATOM_DOMAIN_BOF60_BOF61 && sts.statistic_type != MainWindow.COS_ARG_DIVIDE_INVERSE_NORM && sts.statistic_type != MainWindow.TWIN_LAMPS);
        }
        else if(sts.statisticGroup == 2) {
            smoothing.setEnabled(true);
            average.setEnabled(true);
        }
        else if(sts.statisticGroup == 3) {
            smoothing.setEnabled(false);
            average.setEnabled(false);
        }
        else if(sts.statisticGroup == 4) {
            smoothing.setEnabled(false);
            average.setEnabled(false);
            intensity.setEnabled(false);
        }

        JPanel panel6 = new JPanel();
        panel6.setLayout(new FlowLayout());
        panel6.setBackground(MainWindow.bg_color);
        panel6.add(smoothing);
        panel6.add(average);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension((MainWindow.runsOnWindows ? 560 : 660) + 90, 560));
        tabbedPane.setFocusable(false);


        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1));
        panel.setBackground(MainWindow.bg_color);
        panel.setPreferredSize(new Dimension((int)tabbedPane.getPreferredSize().getWidth() - 20, (int)tabbedPane.getPreferredSize().getHeight() - 40));

        final JPanel panel5 = new JPanel();
        panel5.setBackground(MainWindow.bg_color);
        panel5.add(panel);

        JPanel panel4 = new JPanel();
        panel4.setBackground(MainWindow.bg_color);

        JPanel panel7 = new JPanel();
        panel7.setBackground(MainWindow.bg_color);

        JPanel panel8 = new JPanel();
        panel8.setBackground(MainWindow.bg_color);

        JPanel panel9 = new JPanel();
        panel9.setBackground(MainWindow.bg_color);

        tabbedPane.addTab("Presets", panel5);
        tabbedPane.addTab("User Statistical Coloring", panel4);
        tabbedPane.addTab("Equicontinuity", panel7);
        tabbedPane.addTab("Normal Map", panel8);
        tabbedPane.addTab("Root Coloring", panel9);

        tabbedPane.setEnabledAt(3, (s.fns.function >= MainWindow.MANDELBROT && s.fns.function <= MainWindow.MANDELBROTNTH ) || s.fns.function == MainWindow.LAMBDA);
        tabbedPane.setEnabledAt(4, s.isConvergingType() && s.fns.function != MainWindow.MAGNETIC_PENDULUM);

        if(periodicity_checking) {
            tabbedPane.setEnabledAt(2, false);
            tabbedPane.setEnabledAt(3, false);
            tabbedPane.setEnabledAt(4, false);
        }

        JPanel panel21 = new JPanel();
        panel21.setLayout(new FlowLayout());
        panel21.setBackground(MainWindow.bg_color);
        
        JPanel panel22 = new JPanel();
        panel22.setLayout(new FlowLayout());
        panel22.setBackground(MainWindow.bg_color);

        JLabel html_label = CommonFunctions.createUserFormulaHtmlLabels("z, c, s, c0, pixel, p, pp, n, maxn, bail, cbail, center, size, sizei, v1 - v30, point", "user statistical formula", " Only the real component of the complex number will be used on the final value.");
        panel21.add(html_label);

        JTextField field_formula = new JTextField(45);
        field_formula.setText("" + sts.user_statistic_formula);
        
        JTextField field_formula_init = new JTextField(45);
        field_formula_init.setText("" + sts.user_statistic_init_value);
        
        JComboBox<String> reduction = new JComboBox<>(MainWindow.reductionMethod);
        reduction.setSelectedIndex(sts.reductionFunction);
        reduction.setFocusable(false);

        panel22.add(new JLabel("value = "));
        panel22.add(reduction);
        panel22.add(new JLabel("(value, "));
        panel22.add(field_formula);
        panel22.add(new JLabel(")"));
        
        JPanel panel24 = new JPanel();
        panel24.setLayout(new FlowLayout());
        panel24.setBackground(MainWindow.bg_color);
        
        panel24.add(new JLabel("Initial value = "));
        panel24.add(field_formula_init);

        
        JCheckBox iterations = new JCheckBox("Similar Iterations");
        iterations.setFocusable(false);
        iterations.setSelected(sts.useIterations);
        iterations.setBackground(MainWindow.bg_color);
        iterations.setToolTipText("Uses the iterations of the minimum or maximum calculated value.");
        iterations.setEnabled(sts.reductionFunction == MainWindow.REDUCTION_MAX || sts.reductionFunction == MainWindow.REDUCTION_MIN);

        reduction.addActionListener(e -> {
            average.setEnabled(reduction.getSelectedIndex() == MainWindow.REDUCTION_SUM);
            smoothing.setEnabled(reduction.getSelectedIndex() == MainWindow.REDUCTION_SUM);
            iterations.setEnabled(reduction.getSelectedIndex() == MainWindow.REDUCTION_MAX || reduction.getSelectedIndex() == MainWindow.REDUCTION_MIN);

            if(reduction.getSelectedIndex() == MainWindow.REDUCTION_MAX) {
                field_formula_init.setText("" + (-Double.MAX_VALUE));
            }
            else if(reduction.getSelectedIndex() == MainWindow.REDUCTION_MIN) {
                field_formula_init.setText("" + Double.MAX_VALUE);
            }
            else if(reduction.getSelectedIndex() == MainWindow.REDUCTION_SUM || reduction.getSelectedIndex() == MainWindow.REDUCTION_SUB || reduction.getSelectedIndex() == MainWindow.REDUCTION_ASSIGN) {
                field_formula_init.setText("0.0");
            }
            else if(reduction.getSelectedIndex() == MainWindow.REDUCTION_MULT) {
                field_formula_init.setText("1.0");
            }
        });

        tabbedPane.addChangeListener(e -> {
            if(tabbedPane.getSelectedIndex() == 1) {
                smoothing.setEnabled(reduction.getSelectedIndex() == MainWindow.REDUCTION_SUM);
                average.setEnabled(reduction.getSelectedIndex() == MainWindow.REDUCTION_SUM);
                intensity.setEnabled(true);
            }
            else if (tabbedPane.getSelectedIndex() == 0) {
               smoothing.setEnabled(!atomDomain.isSelected() && !alg2.isSelected() && !twinLamps.isSelected());
               average.setEnabled(!atomDomain.isSelected() && !alg2.isSelected() && !twinLamps.isSelected());
               intensity.setEnabled(true);
            }
            else if(tabbedPane.getSelectedIndex() == 3) {
                smoothing.setEnabled(false);
                average.setEnabled(false);
                intensity.setEnabled(true);
            }
            else if(tabbedPane.getSelectedIndex() == 4) {
                smoothing.setEnabled(false);
                average.setEnabled(false);
                intensity.setEnabled(false);
            }
            else {
                smoothing.setEnabled(true);
                average.setEnabled(true);
                intensity.setEnabled(true);
            }
        });

        JComboBox<String> escape_type = new JComboBox<>(new String[] {"Escaping", "Converging"});
        escape_type.setSelectedIndex(sts.statistic_escape_type);
        escape_type.setFocusable(false);

        JPanel panel23 = new JPanel();
        panel23.setLayout(new FlowLayout());
        panel23.setBackground(MainWindow.bg_color);
        panel23.setPreferredSize(new Dimension((MainWindow.runsOnWindows ? 500 : 600) + 90, 30));

        panel23.add(iterations);
        if(s.isMagnetType() || s.isEscapingOrConvergingType()) {
            panel23.add(new JLabel("Type: "));
            panel23.add(escape_type);
        }

        JButton info_user = new JButton("Help");
        info_user.setToolTipText("Shows the details of the user formulas.");
        info_user.setFocusable(false);
        info_user.setIcon(MainWindow.getIcon("help2.png"));
        info_user.setPreferredSize(new Dimension(105, 23));

        info_user.addActionListener(e -> ptra.showUserFormulaHelp());

        JButton code_editor = new JButton("Edit User Code");
        code_editor.setToolTipText("<html>Opens the java code, containing the user defined functions,<br>with a text editor.</html>");
        code_editor.setFocusable(false);
        code_editor.setIcon(MainWindow.getIcon("code_editor2.png"));
        code_editor.setPreferredSize(new Dimension(160, 23));

        code_editor.addActionListener(e -> ptra.codeEditor());

        JButton compile_code = new JButton("Compile User Code");
        compile_code.setToolTipText("Compiles the java code, containing the user defined functions.");
        compile_code.setFocusable(false);
        compile_code.setIcon(MainWindow.getIcon("compile2.png"));
        compile_code.setPreferredSize(new Dimension(180, 23));

        compile_code.addActionListener(e -> ptra.compileCode(true));

        JPanel info_panel = new JPanel();
        info_panel.setLayout(new FlowLayout());
        info_panel.add(info_user);
        info_panel.add(code_editor);
        info_panel.add(compile_code);
        info_panel.setPreferredSize(new Dimension((MainWindow.runsOnWindows ? 500 : 600) + 90, 28));
        info_panel.setBackground(MainWindow.bg_color);

        panel4.add(info_panel);
        panel4.add(panel21);
        panel4.add(panel23);
        panel4.add(panel22);
        panel4.add(panel24);

        tabbedPane.setSelectedIndex(sts.statisticGroup);


        JPanel panel70 = new JPanel();
        panel70.setBackground(MainWindow.bg_color);

        JTextField equiDenominatorFactor = new JTextField(10);
        equiDenominatorFactor.setText("" + sts.equicontinuityDenominatorFactor);

        JCheckBox inverseFactor = new JCheckBox("Invert Factor");
        inverseFactor.setToolTipText("Inverts the calculated value.");
        inverseFactor.setBackground(MainWindow.bg_color);
        inverseFactor.setSelected(sts.equicontinuityInvertFactor);
        inverseFactor.setFocusable(false);

        JTextField equiDelta = new JTextField(10);
        equiDelta.setText("" + sts.equicontinuityDelta);

        panel70.add(new JLabel("Delta: "));
        panel70.add(equiDelta);

        panel70.add(new JLabel(" Denominator Factor: "));
        panel70.add(equiDenominatorFactor);

        panel70.add(new JLabel(" "));
        panel70.add(inverseFactor);


        JPanel panel71 = new JPanel();
        panel71.setBackground(MainWindow.bg_color);

        JCheckBox overrideColoring = new JCheckBox("Override Coloring");
        overrideColoring.setToolTipText("Overrides the normal coloring is order to use the equicontinuity coloring.");
        overrideColoring.setBackground(MainWindow.bg_color);
        overrideColoring.setSelected(sts.equicontinuityOverrideColoring);
        overrideColoring.setFocusable(false);

        JComboBox<String> argValue = new JComboBox<>(Constants.equicontinuityArgs);
        argValue.setSelectedIndex(sts.equicontinuityArgValue);
        argValue.setFocusable(false);
        argValue.setToolTipText("Sets the color argument value.");

        panel71.add(Box.createRigidArea(new Dimension(70, 10)));
        panel71.add(overrideColoring);
        panel71.add(new JLabel(" Argument: "));
        panel71.add(argValue);
        panel71.add(Box.createRigidArea(new Dimension(70, 10)));

        JPanel panel72 = new JPanel();
        panel72.setBackground(MainWindow.bg_color);

        JComboBox<String> equiColorMethods = new JComboBox<>(Constants.equicontinuityColorMethods);
        equiColorMethods.setSelectedIndex(sts.equicontinuityColorMethod);
        equiColorMethods.setFocusable(false);
        equiColorMethods.setToolTipText("Sets the coloring method.");
        equiColorMethods.setEnabled(overrideColoring.isSelected());

        final JSlider saturationChroma = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        saturationChroma.setValue((int)(100 * sts.equicontinuitySatChroma));
        saturationChroma.setBackground(MainWindow.bg_color);
        saturationChroma.setMajorTickSpacing(25);
        saturationChroma.setMinorTickSpacing(1);
        saturationChroma.setToolTipText("Sets the saturation/chroma.");
        saturationChroma.setFocusable(false);
        saturationChroma.setPaintLabels(true);

        panel72.add(new JLabel("Color Method: "));
        panel72.add(equiColorMethods);

        panel72.add(new JLabel(" Saturation/Chroma: "));
        panel72.add(saturationChroma);

        JPanel panel73 = new JPanel();
        panel73.setBackground(MainWindow.bg_color);

        JComboBox<String> equiMixingMethods = new JComboBox<>(Constants.colorMethod);
        equiMixingMethods.setSelectedIndex(sts.equicontinuityMixingMethod);
        equiMixingMethods.setFocusable(false);
        equiMixingMethods.setToolTipText("Sets the mixing method.");


        JSlider blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        blend_opt.setValue((int)(100 * sts.equicontinuityBlending));
        blend_opt.setBackground(MainWindow.bg_color);
        blend_opt.setMajorTickSpacing(25);
        blend_opt.setMinorTickSpacing(1);
        blend_opt.setToolTipText("Sets the blending percentage.");
        blend_opt.setFocusable(false);
        blend_opt.setPaintLabels(true);

        equiMixingMethods.addActionListener(e -> blend_opt.setEnabled(equiMixingMethods.getSelectedIndex() == 3));

        blend_opt.setEnabled(overrideColoring.isSelected() && ((equiColorMethods.getSelectedIndex() == 3 || equiColorMethods.getSelectedIndex() == 4) && equiMixingMethods.getSelectedIndex() == 3));

        panel73.add(new JLabel("Color Mixing: "));
        panel73.add(equiMixingMethods);

        panel73.add(new JLabel(" Color Blending: "));
        panel73.add(blend_opt);


        saturationChroma.setEnabled(overrideColoring.isSelected() && (equiColorMethods.getSelectedIndex() != 3 && equiColorMethods.getSelectedIndex() != 4));
        equiMixingMethods.setEnabled(overrideColoring.isSelected() && (equiColorMethods.getSelectedIndex() == 3 || equiColorMethods.getSelectedIndex() == 4));
        argValue.setEnabled(overrideColoring.isSelected() && equiColorMethods.getSelectedIndex() != 4);

        equiColorMethods.addActionListener(e -> {
            saturationChroma.setEnabled(overrideColoring.isSelected() && (equiColorMethods.getSelectedIndex() != 3 && equiColorMethods.getSelectedIndex() != 4));
            equiMixingMethods.setEnabled(overrideColoring.isSelected() && (equiColorMethods.getSelectedIndex() == 3 || equiColorMethods.getSelectedIndex() == 4));
            argValue.setEnabled(overrideColoring.isSelected() && equiColorMethods.getSelectedIndex() != 4);
            blend_opt.setEnabled(overrideColoring.isSelected() && ((equiColorMethods.getSelectedIndex() == 3 || equiColorMethods.getSelectedIndex() == 4) && equiMixingMethods.getSelectedIndex() == 3));
        });


        overrideColoring.addActionListener(e -> {
            equiColorMethods.setEnabled(overrideColoring.isSelected());
            saturationChroma.setEnabled(overrideColoring.isSelected() && (equiColorMethods.getSelectedIndex() != 3 && equiColorMethods.getSelectedIndex() != 4));
            equiMixingMethods.setEnabled(overrideColoring.isSelected() && (equiColorMethods.getSelectedIndex() == 3 || equiColorMethods.getSelectedIndex() == 4));
            argValue.setEnabled(overrideColoring.isSelected() && equiColorMethods.getSelectedIndex() != 4);
            blend_opt.setEnabled(overrideColoring.isSelected() && ((equiColorMethods.getSelectedIndex() == 3 || equiColorMethods.getSelectedIndex() == 4) && equiMixingMethods.getSelectedIndex() == 3));
        });


        panel7.add(panel70);
        panel7.add(panel71);
        panel7.add(panel72);
        panel7.add(panel73);



        JPanel panel80 = new JPanel();
        panel80.setBackground(MainWindow.bg_color);

        normalMap = new JCheckBox("Normal Map");
        normalMap.setToolTipText("Enables the use of normal map.");
        normalMap.setBackground(MainWindow.bg_color);
        normalMap.setSelected(sts.useNormalMap);
        normalMap.setFocusable(false);

        normalMap.setEnabled((s.fns.function >= MainWindow.MANDELBROT && s.fns.function <= MainWindow.MANDELBROTNTH && !s.fns.burning_ship) || s.fns.function == MainWindow.LAMBDA);

        JPanel panel83 = new JPanel();
        panel83.setBackground(MainWindow.bg_color);
        panel83.setPreferredSize(new Dimension((MainWindow.runsOnWindows ? 530 : 630) + 90, 150));

        ComponentTitledBorder normalMap_border = new ComponentTitledBorder(normalMap, panel83, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), this_frame);
        normalMap_border.setChangeListener();

        panel83.setBorder(normalMap_border);


        JTextField nmHeight = new JTextField(10);
        nmHeight.setText("" + sts.normalMapHeight);

        JTextField nmAngle = new JTextField(10);
        nmAngle.setText("" + sts.normalMapAngle);

        nmLightFactor = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        nmLightFactor.setValue((int)(100 * sts.normalMapLightFactor));
        nmLightFactor.setBackground(MainWindow.bg_color);
        nmLightFactor.setMajorTickSpacing(25);
        nmLightFactor.setMinorTickSpacing(1);
        nmLightFactor.setToolTipText("Sets the light factor.");
        nmLightFactor.setFocusable(false);
        nmLightFactor.setPaintLabels(true);

        panel80.add(new JLabel(" Height: "));
        panel80.add(nmHeight);
        panel80.add(new JLabel(" Angle: "));
        panel80.add(nmAngle);
        panel80.add(new JLabel(" Light: "));
        panel80.add(nmLightFactor);


        JPanel panel82 = new JPanel();
        panel82.setBackground(MainWindow.bg_color);

        color_method_combo = new JComboBox<>(Constants.colorMethod);
        color_method_combo.setSelectedIndex(sts.normalMapColorMode);
        color_method_combo.setFocusable(false);
        color_method_combo.setToolTipText("Sets the normal map color mode.");

        color_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (sts.normalMapBlending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);
        color_blend_opt.setBackground(MainWindow.bg_color);

        color_method_combo.addActionListener(e -> color_blend_opt.setEnabled(color_method_combo.getSelectedIndex() == 3));

        JCheckBox useSecondDer = new JCheckBox("Second Derivative");
        useSecondDer.setToolTipText("Enables the use of second derivative in normal map.");
        useSecondDer.setBackground(MainWindow.bg_color);
        useSecondDer.setSelected(sts.normalMapUseSecondDerivative);
        useSecondDer.setFocusable(false);

        panel82.add(useSecondDer);
        panel82.add(new JLabel(" Color Mode: "));
        panel82.add(color_method_combo);
        panel82.add(new JLabel(" Color Blending: "));
        panel82.add(color_blend_opt);

        panel83.add(panel80);
        panel83.add(panel82);


        JPanel panel81 = new JPanel();
        panel81.setBackground(MainWindow.bg_color);
        panel81.setPreferredSize(new Dimension((MainWindow.runsOnWindows ? 530 : 630) + 90, 100));

        de = new JCheckBox("Distance Estimation");
        de.setToolTipText("Enables the use of distance estimation.");
        de.setBackground(MainWindow.bg_color);
        de.setSelected(sts.normalMapUseDE);
        de.setFocusable(false);

        ComponentTitledBorder de_border = new ComponentTitledBorder(de, panel81, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), this_frame);
        de_border.setChangeListener();

        panel81.setBorder(de_border);


        smoothDE = new JCheckBox("Fading");
        smoothDE.setToolTipText("Fades the distance estimation and creates an Anti-aliasing effect.");
        smoothDE.setBackground(MainWindow.bg_color);
        smoothDE.setSelected(sts.normalMapDEAAEffect);
        smoothDE.setFocusable(false);

        JCheckBox inverDe = new JCheckBox("Invert Coloring");
        inverDe.setToolTipText("Inverts the application of distance estimation.");
        inverDe.setBackground(MainWindow.bg_color);
        inverDe.setSelected(sts.normalMapInvertDE);
        inverDe.setFocusable(false);

        JTextField deFactor = new JTextField(10);
        deFactor.setText("" + sts.normalMapDEfactor);

        deUpperLimitFactor = new JTextField(10);
        deUpperLimitFactor.setText("" + sts.normalMapDEUpperLimitFactor);
        deUpperLimitFactor.setToolTipText("Change the color which corresponds to maximum iteration to see the effect of this setting.");

        smoothDE.addActionListener(actionEvent -> {
            deUpperLimitFactor.setEnabled(de.isSelected() && !smoothDE.isSelected());
            de_fade_method_combo.setEnabled(de.isSelected() && smoothDE.isSelected());
        });

        de_fade_method_combo = new JComboBox<>(Constants.deFadeAlgs);
        de_fade_method_combo.setSelectedIndex(sts.normalMapDeFadeAlgorithm);
        de_fade_method_combo.setFocusable(false);
        de_fade_method_combo.setToolTipText("Sets the fading method.");

        JPanel panel86 = new JPanel();
        panel86.setBackground(MainWindow.bg_color);

        panel86.add(smoothDE);

        panel86.add(new JLabel( " Fade Method: "));
        panel86.add(de_fade_method_combo);

        panel86.add(new JLabel(" Lower Limit: "));
        panel86.add(deFactor);

        JPanel panel87 = new JPanel();
        panel87.setBackground(MainWindow.bg_color);
        panel87.setPreferredSize(new Dimension(450, 30));

        panel87.add(new JLabel(" Upper Limit: "));
        panel87.add(deUpperLimitFactor);
        panel87.add(inverDe);

        panel81.add(panel86);
        panel81.add(panel87);

        JPanel panel84 = new JPanel();
        panel84.setBackground(MainWindow.bg_color);

        normalMapOverrideColoring = new JCheckBox("Override Coloring");
        normalMapOverrideColoring.setToolTipText("Overrides the normal coloring is order to use the normal map coloring.");
        normalMapOverrideColoring.setBackground(MainWindow.bg_color);
        normalMapOverrideColoring.setSelected(sts.normalMapOverrideColoring);
        normalMapOverrideColoring.setFocusable(false);
        normalMapOverrideColoring.setEnabled((s.fns.function >= MainWindow.MANDELBROT && s.fns.function <= MainWindow.MANDELBROTNTH && !s.fns.burning_ship) || s.fns.function == MainWindow.LAMBDA);


        JComboBox<String> normal_map_color_method_combo = new JComboBox<>(Constants.normalMapColoringMethods);
        normal_map_color_method_combo.setSelectedIndex(sts.normalMapColoring);
        normal_map_color_method_combo.setFocusable(false);
        normal_map_color_method_combo.setToolTipText("Sets the normal map palette mode.");

        panel84.add(normalMapOverrideColoring);
        panel84.add(new JLabel( " Coloring Algorithm: "));
        panel84.add(normal_map_color_method_combo);

        normal_map_color_method_combo.setEnabled(sts.normalMapOverrideColoring);



        normalMapOverrideColoring.addActionListener(e -> {
            normal_map_color_method_combo.setEnabled(normalMapOverrideColoring.isSelected());
            nmLightFactor.setEnabled(normalMapOverrideColoring.isSelected() && normalMap.isSelected());
            color_method_combo.setEnabled(normalMapOverrideColoring.isSelected() && normalMap.isSelected());
            color_blend_opt.setEnabled(normalMapOverrideColoring.isSelected() && normalMap.isSelected() && color_method_combo.getSelectedIndex() == 3);
        });

        panel8.add(panel84);
        panel8.add(panel83);
        panel8.add(panel81);

        normalMap_border.setState(normalMap.isSelected());
        de_border.setState(de.isSelected());

        color_method_combo.setEnabled(normalMapOverrideColoring.isSelected() && normalMap.isSelected());
        nmLightFactor.setEnabled(normalMapOverrideColoring.isSelected() && normalMap.isSelected());
        color_blend_opt.setEnabled(normalMapOverrideColoring.isSelected() && normalMap.isSelected() && color_method_combo.getSelectedIndex() == 3);
        deUpperLimitFactor.setEnabled(de.isSelected() && !smoothDE.isSelected());
        de_fade_method_combo.setEnabled(de.isSelected() && smoothDE.isSelected());

        JPanel panel90 = new JPanel();
        panel90.setBackground(MainWindow.bg_color);

        JTextField rootIterScaling = new JTextField(10);
        rootIterScaling.setText("" + sts.rootIterationsScaling);

        rootShading = new JCheckBox("Contouring");
        rootShading.setToolTipText("Enables the use of contouring in the roots.");
        rootShading.setBackground(MainWindow.bg_color);
        rootShading.setSelected(sts.rootShading);
        rootShading.setFocusable(false);

        JCheckBox invertShading = new JCheckBox("Invert Coloring");
        invertShading.setToolTipText("Inverts the coloring application.");
        invertShading.setBackground(MainWindow.bg_color);
        invertShading.setSelected(sts.revertRootShading);
        invertShading.setFocusable(false);

        JCheckBox hightlight = new JCheckBox("Highlight Roots");
        hightlight.setToolTipText("Highlights the roots.");
        hightlight.setBackground(MainWindow.bg_color);
        hightlight.setSelected(sts.highlightRoots);
        hightlight.setFocusable(false);

        root_shading_function_combo = new JComboBox<>(Constants.rootShadingFunction);
        root_shading_function_combo.setSelectedIndex(sts.rootShadingFunction);
        root_shading_function_combo.setFocusable(false);
        root_shading_function_combo.setToolTipText("Sets the contouring function.");

        panel90.add(invertShading);
        panel90.add(hightlight);
        panel90.add(new JLabel(" Scaling: "));
        panel90.add(rootIterScaling);
        panel90.add(new JLabel(" Function: "));
        panel90.add(root_shading_function_combo);


        root_color_method_combo = new JComboBox<>(Constants.colorMethod);
        root_color_method_combo.setSelectedIndex(sts.rootContourColorMethod);
        root_color_method_combo.setFocusable(false);
        root_color_method_combo.setToolTipText("Sets the contouring color mode.");

        root_color_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (sts.rootBlending * 100));
        root_color_blend_opt.setMajorTickSpacing(25);
        root_color_blend_opt.setMinorTickSpacing(1);
        root_color_blend_opt.setToolTipText("Sets the color blending percentage.");
        root_color_blend_opt.setFocusable(false);
        root_color_blend_opt.setPaintLabels(true);
        root_color_blend_opt.setBackground(MainWindow.bg_color);

        root_color_method_combo.addActionListener(e -> root_color_blend_opt.setEnabled(rootShading.isSelected() && root_color_method_combo.getSelectedIndex() == 3));

        JPanel panel91 = new JPanel();
        panel91.setBackground(MainWindow.bg_color);

        panel91.add(new JLabel("Contour Method: "));
        panel91.add(root_color_method_combo);
        panel91.add(new JLabel(" Color Blending: "));
        panel91.add(root_color_blend_opt);

        JPanel panel93 = new JPanel();
        panel93.setBackground(MainWindow.bg_color);
        panel93.setPreferredSize(new Dimension((MainWindow.runsOnWindows ? 530 : 630) + 90, 170));

        ComponentTitledBorder rootShading_border = new ComponentTitledBorder(rootShading, panel93, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), this_frame);
        rootShading_border.setChangeListener();

        panel93.setBorder(rootShading_border);

        JPanel panel98 = new JPanel();
        panel98.setBackground(MainWindow.bg_color);

        rootSmoothing = new JCheckBox("Contour Smoothing");
        rootSmoothing.setToolTipText("Enables the contour smoothing.");
        rootSmoothing.setBackground(MainWindow.bg_color);
        rootSmoothing.setSelected(sts.rootSmooting);
        rootSmoothing.setFocusable(false);


        root_shading_function_combo.addActionListener(actionEvent -> {
            root_color_method_combo.setEnabled(rootShading.isSelected() && root_shading_function_combo.getSelectedIndex() != 5);
            rootSmoothing.setEnabled(rootShading.isSelected() && root_shading_function_combo.getSelectedIndex() != 5);
            root_color_blend_opt.setEnabled(rootShading.isSelected() && root_color_method_combo.getSelectedIndex() == 3 && root_shading_function_combo.getSelectedIndex() != 5);
        });

        panel98.add(rootSmoothing);

        final JLabel unmmapped_root_label = new JLabel();

        unmmapped_root_label.setPreferredSize(new Dimension(22, 22));
        unmmapped_root_label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        unmmapped_root_label.setBackground(sts.unmmapedRootColor);
        unmmapped_root_label.setOpaque(true);
        unmmapped_root_label.setToolTipText("Changes the un-mapped root color.");

        unmmapped_root_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(!unmmapped_root_label.isEnabled()) {
                    return;
                }

                new ColorChooserFrame(this_frame, "Un-mapped Root Color", unmmapped_root_label, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        final JLabel root_shading_color_label = new JLabel();

        root_shading_color_label.setPreferredSize(new Dimension(22, 22));
        root_shading_color_label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        root_shading_color_label.setBackground(sts.rootShadingColor);
        root_shading_color_label.setOpaque(true);
        root_shading_color_label.setToolTipText("Changes the root shading color.");

        root_shading_color_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(!root_shading_color_label.isEnabled()) {
                    return;
                }

                new ColorChooserFrame(this_frame, "Root Shading Color", root_shading_color_label, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        panel98.add(new JLabel(" Root Shading Color: "));
        panel98.add(root_shading_color_label);


        panel93.add(panel90);
        panel93.add(panel91);
        panel93.add(panel98);

        panel9.add(panel93);

        rootShading_border.setState(rootShading.isSelected());

        root_color_method_combo.setEnabled(rootShading.isSelected() && root_shading_function_combo.getSelectedIndex() != 5);
        rootSmoothing.setEnabled(rootShading.isSelected() && root_shading_function_combo.getSelectedIndex() != 5);
        root_color_blend_opt.setEnabled(rootShading.isSelected() && root_color_method_combo.getSelectedIndex() == 3 && root_shading_function_combo.getSelectedIndex() != 5);



        DefaultListModel<String> m = new DefaultListModel<>();
        for(int i = 0; i < sts.rootColors.length; i++) {
            m.addElement("" + sts.rootColors[i]);
        }

        list = new JList<>(m);
        list.getSelectionModel().setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setTransferHandler(new ListItemTransferHandler());

        list.getInputMap( JComponent.WHEN_FOCUSED ).getParent().remove( KeyStroke.getKeyStroke( KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK ) );
        list.getInputMap( JComponent.WHEN_FOCUSED ).getParent().remove( KeyStroke.getKeyStroke( KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK ) );
        list.getInputMap( JComponent.WHEN_FOCUSED ).getParent().remove( KeyStroke.getKeyStroke( KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK ) );

        list.setDropMode(DropMode.INSERT);
        list.setDragEnabled(true);
        //http://java-swing-tips.blogspot.jp/2008/10/rubber-band-selection-drag-and-drop.html
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(0);
        list.setFixedCellWidth(28);
        list.setFixedCellHeight(28);
        list.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));


        list.setCellRenderer(new ListCellRenderer<String>() {
            private final JPanel p = new JPanel(new BorderLayout());
            private final JLabel icon = new JLabel((Icon)null, JLabel.CENTER);

            @Override
            public Component getListCellRendererComponent(
                    JList list, String value, int index,
                    boolean isSelected, boolean cellHasFocus) {


                Icon ic = new Icon() {
                    @Override
                    public void paintIcon(Component c, Graphics g, int x, int y) {
                        g.setColor(new Color(Integer.valueOf(value)));
                        g.fillRect(2, 2 ,24, 24);
                    }

                    @Override
                    public int getIconWidth() {
                        return 24;
                    }

                    @Override
                    public int getIconHeight() {
                        return 24;
                    }
                };

                icon.setIcon(ic);

                p.setToolTipText("Root " + (index + 1));

                p.add(icon);

                p.setBackground(list.getBackground());

                if(isSelected) {
                    p.setBackground(list.getSelectionBackground());
                }
                return p;
            }
        });

        JScrollPane scroll_pane = new JScrollPane(list);
        scroll_pane.setPreferredSize(new Dimension((MainWindow.runsOnWindows ? 530 : 630) + 90, 190));
        scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        JPanel panel94 = new JPanel();
        panel94.setBackground(MainWindow.bg_color);
        panel94.add(scroll_pane);

        JPanel panel95 = new JPanel();
        panel95.setBackground(MainWindow.bg_color);

        JButton addColor = new JButton();
        addColor.setIcon(MainWindow.getIcon("add.png"));
        addColor.setPreferredSize(new Dimension(32, 32));
        addColor.setFocusable(false);
        addColor.setToolTipText("Add Color");

        JButton editColor = new JButton();
        editColor.setIcon(MainWindow.getIcon("edit.png"));
        editColor.setPreferredSize(new Dimension(32, 32));
        editColor.setFocusable(false);
        editColor.setToolTipText("Edit Color");

        JButton removeColor = new JButton();
        removeColor.setIcon(MainWindow.getIcon("delete.png"));
        removeColor.setPreferredSize(new Dimension(32, 32));
        removeColor.setFocusable(false);
        removeColor.setToolTipText("Remove Color");

        JButton resetColor = new JButton();
        resetColor.setIcon(MainWindow.getIcon("reset.png"));
        resetColor.setPreferredSize(new Dimension(32, 32));
        resetColor.setFocusable(false);
        resetColor.setToolTipText("Reset Colors");

        colorsLength = new JLabel("Root Color(s): " + list.getModel().getSize());

        removeColor.addActionListener(e -> {
            int[] indx = list.getSelectedIndices();

            if(indx == null || indx.length == 0) {
                return;
            }
            DefaultListModel model = (DefaultListModel) list.getModel();

            for (int i = indx.length-1; i >=0; i--) {
                model.remove(indx[i]);
            }

            colorsLength.setText("Root Color(s): " + list.getModel().getSize());
        });

        editColor.addActionListener(e -> {
            int[] indx = list.getSelectedIndices();

            if(indx == null || indx.length == 0) {
                return;
            }

            DefaultListModel model = (DefaultListModel) list.getModel();

            for (int i = 0; i < indx.length; i++) {
                new ColorChooserFrame(this_frame, "Edit Color",  model.getElementAt(indx[i]), indx[i]);
            }
        });


        addColor.addActionListener(e -> {
            DefaultListModel model = (DefaultListModel) list.getModel();
            new ColorChooserFrame(this_frame, "Add Color",  "000000", model.getSize());
        });


        resetColor.addActionListener(e -> {
            DefaultListModel model = (DefaultListModel) list.getModel();

            for (int i = model.getSize()-1; i >=0; i--) {
                model.remove(i);
            }

            for(int i = 0; i < StatisticsSettings.defRootColors.length; i++) {
                storeColor(i, new Color(StatisticsSettings.defRootColors[i]));
            }
        });



        panel95.add(addColor);
        panel95.add(editColor);
        panel95.add(removeColor);
        panel95.add(resetColor);

        JPanel panel96 = new JPanel();
        panel96.setBackground(MainWindow.bg_color);
        panel96.add(new JLabel("Set the root colors (Due to threads, the order of root convergence might differ each time)"));

        JPanel panel97 = new JPanel();
        panel97.setBackground(MainWindow.bg_color);
        panel97.setPreferredSize(new Dimension(620, 30));

        JPanel panel99 = new JPanel();
        panel99.setBackground(MainWindow.bg_color);

        panel99.add(new JLabel(" Un-mapped Root Color: "));
        panel99.add(unmmapped_root_label);


        panel97.add(colorsLength);

        panel9.add(panel96);
        panel9.add(panel94);
        panel9.add(panel95);
        panel9.add(panel97);
        panel9.add(panel99);



        ButtonGroup button_group = new ButtonGroup();

        final JPanel stripe_panel = new JPanel();
        stripe_panel.setLayout(new FlowLayout());
        stripe_panel.setBackground(MainWindow.bg_color);

        stripe_average.setBackground(MainWindow.bg_color);
        stripe_average.setSelected(sts.statistic_type == MainWindow.STRIPE_AVERAGE);
        button_group.add(stripe_average);

        ComponentTitledBorder stripe_border = new ComponentTitledBorder(stripe_average, stripe_panel, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), this_frame);
        stripe_border.setChangeListener();

        stripe_panel.setBorder(stripe_border);

        JTextField stripeDensity1 = new JTextField(10);
        stripeDensity1.setText("" + sts.stripeAvgStripeDensity);
        stripe_panel.add(new JLabel("Stripe Density: "));
        stripe_panel.add(stripeDensity1);

        final JPanel curvature_panel = new JPanel();
        curvature_panel.setLayout(new FlowLayout());
        curvature_panel.setBackground(MainWindow.bg_color);

        curvature_average.setBackground(MainWindow.bg_color);
        curvature_average.setSelected(sts.statistic_type == MainWindow.CURVATURE_AVERAGE);
        button_group.add(curvature_average);

        ComponentTitledBorder curvature_border = new ComponentTitledBorder(curvature_average, curvature_panel, BorderFactory.createEmptyBorder(), this_frame);
        curvature_border.setChangeListener();

        curvature_panel.setBorder(curvature_border);

        final JPanel triangle_inequality_panel = new JPanel();
        triangle_inequality_panel.setLayout(new FlowLayout());
        triangle_inequality_panel.setBackground(MainWindow.bg_color);

        triangle_inequality_average.setBackground(MainWindow.bg_color);
        triangle_inequality_average.setSelected(sts.statistic_type == MainWindow.TRIANGLE_INEQUALITY_AVERAGE);
        button_group.add(triangle_inequality_average);

        ComponentTitledBorder triangle_inequality_border = new ComponentTitledBorder(triangle_inequality_average, triangle_inequality_panel, BorderFactory.createEmptyBorder(), this_frame);
        triangle_inequality_border.setChangeListener();

        triangle_inequality_panel.setBorder(triangle_inequality_border);

        final JPanel alg1_panel = new JPanel();
        alg1_panel.setLayout(new FlowLayout());
        alg1_panel.setBackground(MainWindow.bg_color);

        JTextField stripeDensity2 = new JTextField(10);
        stripeDensity2.setText("" + sts.cosArgStripeDensity);
        alg1_panel.add(new JLabel("Stripe Density: "));
        alg1_panel.add(stripeDensity2);

        alg1.setBackground(MainWindow.bg_color);
        alg1.setSelected(sts.statistic_type == MainWindow.COS_ARG_DIVIDE_NORM_AVERAGE);
        button_group.add(alg1);

        ComponentTitledBorder alg1_border = new ComponentTitledBorder(alg1, alg1_panel, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), this_frame);
        alg1_border.setChangeListener();

        alg1_panel.setBorder(alg1_border);

        final JPanel alg2_panel = new JPanel();
        alg2_panel.setLayout(new FlowLayout());
        alg2_panel.setBackground(MainWindow.bg_color);

        JTextField stripeDensity3 = new JTextField(10);
        stripeDensity3.setText("" + sts.cosArgInvStripeDensity);

        JTextField denominatorFactor = new JTextField(10);
        denominatorFactor.setText("" + sts.StripeDenominatorFactor);

        alg2_panel.add(new JLabel("Stripe Density: "));
        alg2_panel.add(stripeDensity3);
        alg2_panel.add(new JLabel(" Denominator Factor: "));
        alg2_panel.add(denominatorFactor);

        alg2.setBackground(MainWindow.bg_color);
        alg2.setSelected(sts.statistic_type == MainWindow.COS_ARG_DIVIDE_INVERSE_NORM);
        button_group.add(alg2);

        ComponentTitledBorder alg2_border = new ComponentTitledBorder(alg2, alg2_panel, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), this_frame);
        alg2_border.setChangeListener();

        alg2_panel.setBorder(alg2_border);
        
        
        
        final JPanel atom_domain_panel = new JPanel();
        atom_domain_panel.setLayout(new FlowLayout());
        atom_domain_panel.setBackground(MainWindow.bg_color);

        JCheckBox showAtomDomain = new JCheckBox("Similar Iterations");
        showAtomDomain.setToolTipText("Highlights all the areas that share the same iteration number.");
        showAtomDomain.setBackground(MainWindow.bg_color);
        showAtomDomain.setSelected(sts.showAtomDomains);
        showAtomDomain.setFocusable(false);

        atom_domain_panel.add(showAtomDomain);

        atomNormType = new JComboBox<>(Constants.atomNormTypes);
        atomNormType.setSelectedIndex(sts.atomNormType);
        atomNormType.setFocusable(false);
        atomNormType.setToolTipText("Sets the norm type.");

        atom_domain_panel.add(new JLabel(" Norm-Type: "));
        atom_domain_panel.add(atomNormType);

        atomNNorm = new JTextField(10);
        atomNNorm.setText("" + sts.atomNNorm);

        atomNormType.addActionListener(e -> atomNNorm.setEnabled(atomNormType.getSelectedIndex() == 3));

        atom_domain_panel.add(new JLabel(" N-Norm: "));
        atom_domain_panel.add(atomNNorm);


        atomDomain.setBackground(MainWindow.bg_color);
        atomDomain.setSelected(sts.statistic_type == MainWindow.ATOM_DOMAIN_BOF60_BOF61);
        button_group.add(atomDomain);

        ComponentTitledBorder atom_domain_border = new ComponentTitledBorder(atomDomain, atom_domain_panel, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), this_frame);
        atom_domain_border.setChangeListener();

        atom_domain_panel.setBorder(atom_domain_border);

        final JPanel lagrangian_panel = new JPanel();
        lagrangian_panel.setLayout(new FlowLayout());
        lagrangian_panel.setBackground(MainWindow.bg_color);

        JTextField lagrPower = new JTextField(10);
        lagrPower.setText("" + sts.lagrangianPower);
        lagrangian_panel.add(new JLabel("Power: "));
        lagrangian_panel.add(lagrPower);

        langNormType = new JComboBox<>(Constants.langNormTypes);
        langNormType.setSelectedIndex(sts.langNormType);
        langNormType.setFocusable(false);
        langNormType.setToolTipText("Sets the norm type.");

        lagrangian_panel.add(new JLabel(" Norm-Type: "));
        lagrangian_panel.add(langNormType);

        langNNorm = new JTextField(10);
        langNNorm.setText("" + sts.langNNorm);

        langNormType.addActionListener(e -> langNNorm.setEnabled(langNormType.getSelectedIndex() == 4));

        lagrangian_panel.add(new JLabel(" N-Norm: "));
        lagrangian_panel.add(langNNorm);

        lagrangian.setBackground(MainWindow.bg_color);
        lagrangian.setSelected(sts.statistic_type == MainWindow.DISCRETE_LAGRANGIAN_DESCRIPTORS);
        button_group.add(lagrangian);

        ComponentTitledBorder lagrangian_border = new ComponentTitledBorder(lagrangian, lagrangian_panel, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), this_frame);
        lagrangian_border.setChangeListener();

        lagrangian_panel.setBorder(lagrangian_border);

        final JPanel twin_lamps_panel = new JPanel();
        twin_lamps_panel.setLayout(new FlowLayout());
        twin_lamps_panel.setBackground(MainWindow.bg_color);

        JTextField twinPointRe = new JTextField(10);
        twinPointRe.setText("" + sts.twlPoint[0]);
        twin_lamps_panel.add(new JLabel("Re: "));
        twin_lamps_panel.add(twinPointRe);

        JTextField twinPointIm = new JTextField(10);
        twinPointIm.setText("" + sts.twlPoint[1]);

        twin_lamps_panel.add(new JLabel(" Im: "));
        twin_lamps_panel.add(twinPointIm);

        JComboBox<String> twin_l_function = new JComboBox<>(Constants.twinLampsFunction);
        twin_l_function.setSelectedIndex(sts.twlFunction);
        twin_l_function.setFocusable(false);
        twin_l_function.setToolTipText("Sets the twin lamps function.");

        twin_lamps_panel.add(new JLabel(" Function: "));
        twin_lamps_panel.add(twin_l_function);

        twinLamps.setBackground(MainWindow.bg_color);
        twinLamps.setSelected(sts.statistic_type == MainWindow.TWIN_LAMPS);
        button_group.add(twinLamps);

        ComponentTitledBorder twin_lamps_border = new ComponentTitledBorder(twinLamps, twin_lamps_panel, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), this_frame);
        twin_lamps_border.setChangeListener();

        twin_lamps_panel.setBorder(twin_lamps_border);

        panel.add(stripe_panel);
        panel.add(curvature_panel);
        panel.add(triangle_inequality_panel);
        panel.add(alg1_panel);
        panel.add(alg2_panel);
        panel.add(atom_domain_panel);
        panel.add(lagrangian_panel);
        panel.add(twin_lamps_panel);

        stripe_border.setState(stripe_average.isSelected());
        curvature_border.setState(curvature_average.isSelected());
        triangle_inequality_border.setState(triangle_inequality_average.isSelected());
        alg1_border.setState(alg1.isSelected());
        alg2_border.setState(alg2.isSelected());
        atom_domain_border.setState(atomDomain.isSelected());
        lagrangian_border.setState(lagrangian.isSelected());
        twin_lamps_border.setState(twinLamps.isSelected());

        langNNorm.setEnabled(lagrangian.isSelected() && langNormType.getSelectedIndex() == 4);
        atomNNorm.setEnabled(atomDomain.isSelected() && atomNormType.getSelectedIndex() == 3);

        stripe_average.addActionListener(e -> {
            smoothing.setEnabled(true);
            average.setEnabled(true);
        });

        curvature_average.addActionListener(e -> {
            smoothing.setEnabled(true);
            average.setEnabled(true);
        });

        triangle_inequality_average.addActionListener(e -> {
            smoothing.setEnabled(true);
            average.setEnabled(true);
        });

        alg1.addActionListener(e -> {
            smoothing.setEnabled(true);
            average.setEnabled(true);
        });

        atomDomain.addActionListener(e -> {
            smoothing.setEnabled(false);
            average.setEnabled(false);
        });

        alg2.addActionListener(e -> {
            smoothing.setEnabled(false);
            average.setEnabled(false);
        });

        lagrangian.addActionListener(e -> {
            smoothing.setEnabled(true);
            average.setEnabled(true);
        });

        twinLamps.addActionListener(e -> {
            smoothing.setEnabled(false);
            average.setEnabled(false);
        });

        panel3.add(panel2);
        panel3.add(panel6);
        panel3.add(tabbedPane);

        JPanel buttons = new JPanel();

        buttons.setLayout(new FlowLayout());
        buttons.setBackground(MainWindow.bg_color);

        JButton ok = new JButton("Ok");
        getRootPane().setDefaultButton(ok);
        ok.setFocusable(false);
        ok.addActionListener(e -> {

            double temp, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp15, temp16, temp17;

            try {
                temp = Double.parseDouble(intensity.getText());
                temp2 = Double.parseDouble(stripeDensity1.getText());
                temp3 = Double.parseDouble(stripeDensity2.getText());
                temp4 = Double.parseDouble(stripeDensity3.getText());
                temp5 = Double.parseDouble(denominatorFactor.getText());
                temp6 = Double.parseDouble(lagrPower.getText());
                temp7 = Double.parseDouble(equiDenominatorFactor.getText());
                temp8 = Double.parseDouble(equiDelta.getText());
                temp9 = Double.parseDouble(deFactor.getText());
                temp10 = Double.parseDouble(nmHeight.getText());
                temp11 = Double.parseDouble(nmAngle.getText());
                temp12 = Double.parseDouble(rootIterScaling.getText());
                temp13 = Double.parseDouble(twinPointRe.getText());
                temp14 = Double.parseDouble(twinPointIm.getText());
                temp15 = Double.parseDouble(langNNorm.getText());
                temp16 = Double.parseDouble(atomNNorm.getText());
                temp17 = Double.parseDouble(deUpperLimitFactor.getText());
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(this_frame, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(temp < 0) {
                JOptionPane.showMessageDialog(this_frame, "Intensity must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(temp6 < 0) {
                JOptionPane.showMessageDialog(this_frame, "Discrete Lagrangian Descriptor power must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(temp7 < 0) {
                JOptionPane.showMessageDialog(this_frame, "Equicontinuity denominator factor must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (temp9 <= 0) {
                JOptionPane.showMessageDialog(this_frame, "The distance estimation factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (temp10 < 1) {
                JOptionPane.showMessageDialog(this_frame, "The normal map height factor must be greater or equal to 1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (temp12 < 1) {
                JOptionPane.showMessageDialog(this_frame, "The root contour scaling value  must be greater or equal to 1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(temp17 < 0) {
                JOptionPane.showMessageDialog(this_frame, "The distance estimation upper limit factor must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int [] tempColors = getRootColors();

            if(tempColors.length == 0) {
                JOptionPane.showMessageDialog(this_frame, "You need to include at least one root color.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                s.parser.parse(field_formula.getText());

                if(s.parser.foundR()|| s.parser.foundStat() || s.parser.foundTrap()) {
                    JOptionPane.showMessageDialog(ptra, "The variables: r, stat, trap cannot be used in the value formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(s.isConvergingType()) {
                    if(s.parser.foundBail()) {
                        JOptionPane.showMessageDialog(this_frame, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                else if(s.parser.foundCbail()) {
                    JOptionPane.showMessageDialog(this_frame, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                s.parser.parse(field_formula_init.getText());

                if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundC0() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                    JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, c0, p, pp, bail, cbail, r, stat, trap cannot be used in the value(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            catch(ParserException ex) {
                JOptionPane.showMessageDialog(this_frame, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            sts.statistic = statistics.isSelected();
            sts.statistic_intensity = temp;
            sts.stripeAvgStripeDensity = temp2;
            sts.cosArgStripeDensity = temp3;
            sts.cosArgInvStripeDensity = temp4;
            sts.StripeDenominatorFactor = temp5;
            sts.statisticIncludeNotEscaped = include_notescaped_opt.isSelected();
            sts.statisticIncludeEscaped = include_escaped_opt.isSelected();
            sts.reductionFunction = reduction.getSelectedIndex();
            sts.useIterations = iterations.isSelected();
            sts.useSmoothing = smoothing.isSelected();
            sts.lagrangianPower = temp6;
            sts.equicontinuityDenominatorFactor = temp7;
            sts.equicontinuityArgValue = argValue.getSelectedIndex();
            sts.equicontinuityColorMethod = equiColorMethods.getSelectedIndex();
            sts.equicontinuitySatChroma = saturationChroma.getValue() / 100.0;
            sts.equicontinuityMixingMethod = equiMixingMethods.getSelectedIndex();
            sts.equicontinuityBlending = blend_opt.getValue() / 100.0;
            sts.equicontinuityInvertFactor = inverseFactor.isSelected();
            sts.equicontinuityOverrideColoring = overrideColoring.isSelected();
            sts.equicontinuityDelta = temp8;

            sts.useNormalMap = normalMap.isSelected();
            sts.normalMapAngle = temp11;
            sts.normalMapHeight = temp10;
            sts.normalMapOverrideColoring = normalMapOverrideColoring.isSelected();
            sts.normalMapDEfactor = temp9;
            sts.normalMapUseDE = de.isSelected();
            sts.normalMapInvertDE = inverDe.isSelected();
            sts.normalMapLightFactor = nmLightFactor.getValue() / 100.0;
            sts.normalMapColorMode = color_method_combo.getSelectedIndex();
            sts.normalMapBlending = color_blend_opt.getValue() / 100.0;
            sts.normalMapUseSecondDerivative = useSecondDer.isSelected();
            sts.normalMapColoring = normal_map_color_method_combo.getSelectedIndex();
            sts.normalMapDEUpperLimitFactor = temp17;
            sts.normalMapDEAAEffect = smoothDE.isSelected();
            sts.normalMapDeFadeAlgorithm = de_fade_method_combo.getSelectedIndex();

            sts.rootShading = rootShading.isSelected();
            sts.revertRootShading = invertShading.isSelected();
            sts.rootIterationsScaling = temp12;
            sts.rootContourColorMethod = root_color_method_combo.getSelectedIndex();
            sts.rootBlending = root_color_blend_opt.getValue() / 100.0;
            sts.rootShadingFunction = root_shading_function_combo.getSelectedIndex();
            sts.highlightRoots = hightlight.isSelected();
            sts.rootColors = tempColors;
            sts.rootSmooting = rootSmoothing.isSelected();
            sts.unmmapedRootColor = unmmapped_root_label.getBackground();
            sts.rootShadingColor = root_shading_color_label.getBackground();

            sts.twlPoint[0] = temp13;
            sts.twlPoint[1] = temp14;
            sts.twlFunction = twin_l_function.getSelectedIndex();

            sts.langNormType = langNormType.getSelectedIndex();
            sts.langNNorm = temp15;

            sts.atomNormType = atomNormType.getSelectedIndex();
            sts.atomNNorm = temp16;

            if(stripe_average.isSelected()) {
                sts.statistic_type = MainWindow.STRIPE_AVERAGE;
            }
            else if(curvature_average.isSelected()) {
                sts.statistic_type = MainWindow.CURVATURE_AVERAGE;
            }
            else if(alg1.isSelected()) {
                sts.statistic_type = MainWindow.COS_ARG_DIVIDE_NORM_AVERAGE;
            }
            else if(alg2.isSelected()) {
                sts.statistic_type = MainWindow.COS_ARG_DIVIDE_INVERSE_NORM;
            }
            else if(triangle_inequality_average.isSelected()) {
                sts.statistic_type = MainWindow.TRIANGLE_INEQUALITY_AVERAGE;
            }
            else if(atomDomain.isSelected()) {
                sts.statistic_type = MainWindow.ATOM_DOMAIN_BOF60_BOF61;
            }
            else if(lagrangian.isSelected()) {
                sts.statistic_type = MainWindow.DISCRETE_LAGRANGIAN_DESCRIPTORS;
            }
            else if(twinLamps.isSelected()) {
                sts.statistic_type = MainWindow.TWIN_LAMPS;
            }

            sts.statisticGroup = tabbedPane.getSelectedIndex();
            sts.user_statistic_formula = field_formula.getText();
            sts.user_statistic_init_value = field_formula_init.getText();
            sts.useAverage = average.isSelected();
            sts.statistic_escape_type = escape_type.getSelectedIndex();
            sts.showAtomDomains = showAtomDomain.isSelected();

            if(!s.fns.smoothing && sts.statistic && sts.statisticGroup != 4) {
                JOptionPane.showMessageDialog(this_frame, "Smoothing is disabled.\nYou should enable smoothing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
            }

            ptra2.setEnabled(true);
            ptra2.statisticsColorAlgorithmChanged(sts);
            dispose();

        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Ok");
        getRootPane().getActionMap().put("Ok", new AbstractAction()
        {

            public void actionPerformed(ActionEvent e)
            {
                ok.doClick();
            }
        });

        buttons.add(ok);

        JButton cancel = new JButton("Cancel");
        cancel.setFocusable(false);
        cancel.addActionListener(e -> {

            ptra2.setEnabled(true);
            dispose();

        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
        getRootPane().getActionMap().put("Cancel", new AbstractAction()
        {

            public void actionPerformed(ActionEvent e)
            {
                cancel.doClick();
            }
        });

        buttons.add(cancel);

        RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension((MainWindow.runsOnWindows ? 610 : 710) + 90, 700));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(panel3, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 1;

        round_panel.add(buttons, con);

        JPanel main_panel = new JPanel();
        main_panel.setLayout(new GridBagLayout());
        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;
        main_panel.add(round_panel, con);

        JScrollPane scrollPane = new JScrollPane(main_panel);
        add(scrollPane);

        setVisible(true);
    }

    public void toggled(boolean toggled) {

        if (!toggled) {
            return;
        }

        if(normalMap.isSelected()) {
            nmLightFactor.setEnabled(normalMapOverrideColoring.isSelected());
            color_method_combo.setEnabled(normalMapOverrideColoring.isSelected());
            color_blend_opt.setEnabled(normalMapOverrideColoring.isSelected() && color_method_combo.getSelectedIndex() == 3);
        }

        if(rootShading.isSelected()) {
            root_color_method_combo.setEnabled(root_shading_function_combo.getSelectedIndex() != 5);
            rootSmoothing.setEnabled(rootShading.isSelected() && root_shading_function_combo.getSelectedIndex() != 5);
            root_color_blend_opt.setEnabled(root_color_method_combo.getSelectedIndex() == 3 && root_shading_function_combo.getSelectedIndex() != 5);

        }

        if(de.isSelected()) {
            deUpperLimitFactor.setEnabled(!smoothDE.isSelected());
            de_fade_method_combo.setEnabled(smoothDE.isSelected());
        }

        if(lagrangian.isSelected()) {
            langNNorm.setEnabled(langNormType.getSelectedIndex() == 4);
        }

        if(atomDomain.isSelected()) {
            atomNNorm.setEnabled(atomNormType.getSelectedIndex() == 3);
        }

    }

    public int[] getRootColors() {

        int[] order = new int[list.getModel().getSize()];

        for(int i = 0; i < list.getModel().getSize(); i++) {
            String name =  list.getModel().getElementAt(i);
            order[i] = Integer.parseInt(name);
        }

        return order;
    }

    public void storeColor(int index, Color color) {
        DefaultListModel model = (DefaultListModel) list.getModel();

        if(index == model.getSize()) {
            model.add(index, "" + color.getRGB());
        }
        else {
            model.setElementAt("" + color.getRGB(), index);
        }

        colorsLength.setText("Root Color(s): " + list.getModel().getSize());
    }

}
