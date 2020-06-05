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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author hrkalona2
 */
public class StatisticsColoringFrame extends JFrame {

    private static final long serialVersionUID = -17558636585L;
    private MainWindow ptra2;
    private StatisticsColoringFrame this_frame;
    private StatisticsSettings sts;

    public StatisticsColoringFrame(MainWindow ptra, StatisticsSettings sts2, Settings s) {

        super();

        ptra2 = ptra;
        this_frame = this;

        sts = new StatisticsSettings(sts2);

        JRadioButton stripe_average = new JRadioButton(Constants.statisticalColoringName[MainWindow.STRIPE_AVERAGE]);
        JRadioButton curvature_average = new JRadioButton(Constants.statisticalColoringName[MainWindow.CURVATURE_AVERAGE]);
        JRadioButton triangle_inequality_average = new JRadioButton(Constants.statisticalColoringName[MainWindow.TRIANGLE_INEQUALITY_AVERAGE]);
        JRadioButton alg1 = new JRadioButton(Constants.statisticalColoringName[MainWindow.COS_ARG_DIVIDE_NORM_AVERAGE]);
        JRadioButton alg2 = new JRadioButton(Constants.statisticalColoringName[MainWindow.COS_ARG_DIVIDE_INVERSE_NORM]);
        JRadioButton atomDomain = new JRadioButton(Constants.statisticalColoringName[MainWindow.ATOM_DOMAIN_BOF60_BOF61]);

        if(s.fns.function != MainWindow.MANDELBROT) {
            triangle_inequality_average.setEnabled(false);
        }

        if(s.isConvergingType()) {
            stripe_average.setEnabled(false);
            curvature_average.setEnabled(false);
            alg1.setEnabled(false);
            triangle_inequality_average.setEnabled(false);
        }
        else if(!s.isMagnetType()) {
            alg2.setEnabled(false);
        }

        ptra2.setEnabled(false);
        int custom_palette_window_width = (MainWindow.runsOnWindows ? 680 : 780) + 50;
        int custom_palette_window_height = 700;
        setTitle("Statistical Coloring");
        setIconImage(getIcon("/fractalzoomer/icons/statistics_coloring.png").getImage());

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
        panel3.setPreferredSize(new Dimension((MainWindow.runsOnWindows ? 580 : 680) + 50, 560));

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

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension((MainWindow.runsOnWindows ? 560 : 660) + 50, 510));
        tabbedPane.setFocusable(false);

        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        panel.setBackground(MainWindow.bg_color);
        panel.setPreferredSize(new Dimension((int)tabbedPane.getPreferredSize().getWidth() - 20, (int)tabbedPane.getPreferredSize().getHeight() - 40));

        final JPanel panel5 = new JPanel();
        panel5.setBackground(MainWindow.bg_color);
        panel5.add(panel);

        JPanel panel4 = new JPanel();
        panel4.setBackground(MainWindow.bg_color);

        tabbedPane.addTab("Presets", panel5);
        tabbedPane.addTab("User Statistical Coloring", panel4);

        JPanel panel21 = new JPanel();
        panel21.setLayout(new FlowLayout());
        panel21.setBackground(MainWindow.bg_color);
        
        JPanel panel22 = new JPanel();
        panel22.setLayout(new FlowLayout());
        panel22.setBackground(MainWindow.bg_color);

        JLabel html_label = CommonFunctions.createUserFormulaHtmlLabels("z, c, s, p, pp, n, maxn, bail, cbail, center, size, sizei, v1 - v30, point", "user statistical formula", " Only the real component of the complex number will be used on the final value.");
        panel21.add(html_label);

        JTextField field_formula = new JTextField(45);
        field_formula.setText("" + sts.user_statistic_formula);
        
        JTextField field_formula_init = new JTextField(45);
        field_formula_init.setText("" + sts.user_statistic_init_value);
        
        JComboBox reduction = new JComboBox(MainWindow.reductionMethod);
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

        JCheckBox average = new JCheckBox("Average Sum");
        average.setFocusable(false);
        average.setSelected(sts.useAverage);
        average.setBackground(MainWindow.bg_color);
        average.setToolTipText("Averages the computed value of the sum.");
        average.setEnabled(sts.reductionFunction == MainWindow.REDUCTION_SUM);
        
        JCheckBox smoothing = new JCheckBox("Smooth Sum");
        smoothing.setFocusable(false);
        smoothing.setSelected(sts.useSmoothing);
        smoothing.setBackground(MainWindow.bg_color);
        smoothing.setToolTipText("Smooths the computed value of the sum.");
        smoothing.setEnabled(sts.reductionFunction == MainWindow.REDUCTION_SUM);
        
        JCheckBox iterations = new JCheckBox("Similar Iterations");
        iterations.setFocusable(false);
        iterations.setSelected(sts.useIterations);
        iterations.setBackground(MainWindow.bg_color);
        iterations.setToolTipText("Uses the iterations of the minimum or maximum calculated value.");
        iterations.setEnabled(sts.reductionFunction == MainWindow.REDUCTION_MAX || sts.reductionFunction == MainWindow.REDUCTION_MIN);

        reduction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                average.setEnabled(reduction.getSelectedIndex() == MainWindow.REDUCTION_SUM);
                smoothing.setEnabled(reduction.getSelectedIndex() == MainWindow.REDUCTION_SUM);
                iterations.setEnabled(reduction.getSelectedIndex() == MainWindow.REDUCTION_MAX || reduction.getSelectedIndex() == MainWindow.REDUCTION_MIN);
                
                if(reduction.getSelectedIndex() == MainWindow.REDUCTION_MAX) {
                    field_formula_init.setText("" + (-Double.MAX_VALUE));
                }
                else if(reduction.getSelectedIndex() == MainWindow.REDUCTION_MIN) {
                    field_formula_init.setText("" + Double.MAX_VALUE);
                }
                else if(reduction.getSelectedIndex() == MainWindow.REDUCTION_SUM) {
                    field_formula_init.setText("0.0");
                }
            }
            
        });

        JComboBox escape_type = new JComboBox(new String[] {"Escaping", "Converging"});
        escape_type.setSelectedIndex(sts.statistic_escape_type);
        escape_type.setFocusable(false);

        JPanel panel23 = new JPanel();
        panel23.setLayout(new FlowLayout());
        panel23.setBackground(MainWindow.bg_color);
        panel23.setPreferredSize(new Dimension((MainWindow.runsOnWindows ? 500 : 600) + 50, 30));

        panel23.add(average);
        panel23.add(smoothing);
        panel23.add(iterations);
        if(s.isMagnetType()) {
            panel23.add(new JLabel("Type: "));
            panel23.add(escape_type);
        }

        JButton info_user = new JButton("Help");
        info_user.setToolTipText("Shows the details of the user formulas.");
        info_user.setFocusable(false);
        info_user.setIcon(getIcon("/fractalzoomer/icons/help2.png"));
        info_user.setPreferredSize(new Dimension(105, 23));

        info_user.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptra.showUserFormulaHelp();

            }

        });

        JButton code_editor = new JButton("Edit User Code");
        code_editor.setToolTipText("<html>Opens the java code, containing the user defined functions,<br>with a text editor.</html>");
        code_editor.setFocusable(false);
        code_editor.setIcon(getIcon("/fractalzoomer/icons/code_editor2.png"));
        code_editor.setPreferredSize(new Dimension(160, 23));

        code_editor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptra.codeEditor();

            }

        });

        JButton compile_code = new JButton("Compile User Code");
        compile_code.setToolTipText("Compiles the java code, containing the user defined functions.");
        compile_code.setFocusable(false);
        compile_code.setIcon(getIcon("/fractalzoomer/icons/compile2.png"));
        compile_code.setPreferredSize(new Dimension(180, 23));

        compile_code.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptra.compileCode(true);

            }

        });

        JPanel info_panel = new JPanel();
        info_panel.setLayout(new FlowLayout());
        info_panel.add(info_user);
        info_panel.add(code_editor);
        info_panel.add(compile_code);
        info_panel.setPreferredSize(new Dimension((MainWindow.runsOnWindows ? 500 : 600) + 50, 28));
        info_panel.setBackground(MainWindow.bg_color);

        panel4.add(info_panel);
        panel4.add(panel21);
        panel4.add(panel23);
        panel4.add(panel22);
        panel4.add(panel24);

        tabbedPane.setSelectedIndex(sts.statisticGroup);

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

        atomDomain.setBackground(MainWindow.bg_color);
        atomDomain.setSelected(sts.statistic_type == MainWindow.ATOM_DOMAIN_BOF60_BOF61);
        button_group.add(atomDomain);

        ComponentTitledBorder atom_domain_border = new ComponentTitledBorder(atomDomain, atom_domain_panel, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), this_frame);
        atom_domain_border.setChangeListener();

        atom_domain_panel.setBorder(atom_domain_border);

        panel.add(stripe_panel);
        panel.add(curvature_panel);
        panel.add(triangle_inequality_panel);
        panel.add(alg1_panel);
        panel.add(alg2_panel);
        panel.add(atom_domain_panel);

        stripe_border.setState(stripe_average.isSelected());
        curvature_border.setState(curvature_average.isSelected());
        triangle_inequality_border.setState(triangle_inequality_average.isSelected());
        alg1_border.setState(alg1.isSelected());
        alg2_border.setState(alg2.isSelected());
        atom_domain_border.setState(atomDomain.isSelected());

        panel3.add(panel2);
        panel3.add(tabbedPane);

        JPanel buttons = new JPanel();

        buttons.setLayout(new FlowLayout());
        buttons.setBackground(MainWindow.bg_color);

        JButton ok = new JButton("Ok");
        getRootPane().setDefaultButton(ok);
        ok.setFocusable(false);
        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                double temp, temp2, temp3, temp4, temp5;
                try {
                    temp = Double.parseDouble(intensity.getText());
                    temp2 = Double.parseDouble(stripeDensity1.getText());
                    temp3 = Double.parseDouble(stripeDensity2.getText());
                    temp4 = Double.parseDouble(stripeDensity3.getText());
                    temp5 = Double.parseDouble(denominatorFactor.getText());
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(this_frame, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(temp < 0) {
                    JOptionPane.showMessageDialog(this_frame, "Intensity must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    s.parser.parse(field_formula.getText());
                    
                    if(s.parser.foundR()) {
                        JOptionPane.showMessageDialog(ptra, "The variable: r cannot be used in the value formula.", "Error!", JOptionPane.ERROR_MESSAGE);
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
                    
                    if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                        JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, p, pp, bail, cbail, r cannot be used in the value(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
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

                sts.statisticGroup = tabbedPane.getSelectedIndex();
                sts.user_statistic_formula = field_formula.getText();
                sts.user_statistic_init_value = field_formula_init.getText();
                sts.useAverage = average.isSelected();
                sts.statistic_escape_type = escape_type.getSelectedIndex();
                sts.showAtomDomains = showAtomDomain.isSelected();

                if(!s.fns.smoothing && sts.statistic) {
                    JOptionPane.showMessageDialog(this_frame, "Smoothing is disabled.\nYou should enable smoothing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
                }

                ptra2.setEnabled(true);
                ptra2.statisticsColorAlgorithmChanged(sts);
                dispose();

            }

        });

        buttons.add(ok);

        JButton cancel = new JButton("Cancel");
        cancel.setFocusable(false);
        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptra2.setEnabled(true);
                dispose();

            }
        });

        buttons.add(cancel);

        RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension((MainWindow.runsOnWindows ? 610 : 710) + 50, 620));
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

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }

}
