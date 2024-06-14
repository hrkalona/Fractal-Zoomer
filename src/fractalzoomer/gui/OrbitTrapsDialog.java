/*
 * Copyright (C) 2020 hrkalona
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

import fractalzoomer.fractal_options.orbit_traps.ImageOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.TransparentImageOrbitTrap;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.Settings;
import raven.slider.SliderGradient;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OrbitTrapsDialog extends JDialog {
	private static final long serialVersionUID = -4097447483434039100L;

    private ArrayList<Component> ignoreComponents = new ArrayList<>();
	private MainWindow ptra2;
    private JDialog this_frame;
    private JTextField trap_norm_field;
    private JComboBox<String> orbit_traps_combo;
    private JComboBox<String> lines_function_combo;
    private JTextField trap_width_field;
    private JTextField trap_length_field;
    private JSlider blend_opt;
    private JComboBox<String> color_method_combo;
    private JButton load_image_button;
    private JTextField trap_threshold_field;
    private JTextField trap_intensity_field;
    private JTextField trap_offset_field;
    private JPanel color_options_panel;
    private BufferedImage image;
    private JTextField m1;
    private JTextField m2;
    private JTextField n1;
    private JTextField n2;
    private JTextField n3;
    private JTextField a;
    private JTextField b;

    private JCheckBox orbit_traps_opt;
    private JCheckBox trap_cellular_opt;
    private JLabel cellular_color_label;

    private JLabel l1;
    private JLabel l2;
    private JCheckBox invert_trap_cellular_opt;
    private JSlider cellular_size_opt;

    public OrbitTrapsDialog(MainWindow ptra, OrbitTrapSettings ots, Settings s) {

        super();
        ptra2 = ptra;
        this_frame = this;

        setModal(true);
        int color_window_width = 700;
        int color_window_height = 790;
        setTitle("Orbit Traps");
        setSize(color_window_width, color_window_height);
        setIconImage(MainWindow.getIcon("orbit_traps.png").getImage());
        setLocation((int) (ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (color_window_width / 2), (int) (ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (color_window_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                dispose();

            }
        });

        JPanel options_panel = new JPanel();
        options_panel.setPreferredSize(new Dimension(600, 645));
        options_panel.setBackground(MainWindow.bg_color);
        options_panel.setLayout(new FlowLayout());

        orbit_traps_opt = new JCheckBox("Orbit Traps");
        orbit_traps_opt.setFocusable(false);
        orbit_traps_opt.setToolTipText("Applies a coloring effect when an orbit gets trapped under a specific condition.");
        orbit_traps_opt.setBackground(MainWindow.bg_color);
        orbit_traps_opt.setSelected(ots.useTraps);

        ComponentTitledBorder options_border = new ComponentTitledBorder(orbit_traps_opt, options_panel, LAFManager.createUnTitledBorder(), this_frame);
        options_border.setChangeListener();

        options_panel.setBorder(options_border);

        orbit_traps_combo = new JComboBox<>(MainWindow.orbitTrapsNames);
        orbit_traps_combo.setSelectedIndex(ots.trapType);
        orbit_traps_combo.setFocusable(false);
        orbit_traps_combo.setToolTipText("Sets the orbit shape.");

        orbit_traps_combo.addActionListener(e -> toggled());

        JCheckBox showOnlyTraps = new JCheckBox("Traps Only");
        showOnlyTraps.setFocusable(false);
        showOnlyTraps.setSelected(ots.showOnlyTraps);
        showOnlyTraps.setToolTipText("Renders only the traps");
        showOnlyTraps.setBackground(MainWindow.bg_color);

        ignoreComponents.add(showOnlyTraps);


        load_image_button = new MyButton();
        load_image_button.setIcon(MainWindow.getIcon("load_image.png"));
        load_image_button.setFocusable(false);
        load_image_button.setToolTipText("Loads an image to be used as a pattern, with the Image based Trap");
        load_image_button.setPreferredSize(new Dimension(30, 30));

        load_image_button.addActionListener(e -> loadPatternImage(this_frame));

        trap_norm_field = new JTextField(9);
        trap_norm_field.setText("" + ots.trapNorm);
        
        lines_function_combo = new JComboBox<>(MainWindow.orbitTrapLineTypes);
        lines_function_combo.setSelectedIndex(ots.lineType);
        lines_function_combo.setFocusable(false);
        lines_function_combo.setToolTipText("Sets the function which is applied to the lines.");

        JPanel p1 = new JPanel();
        p1.setBackground(MainWindow.bg_color);

        p1.add(new JLabel("Shape: "));
        p1.add(orbit_traps_combo);
        p1.add(load_image_button);
        p1.add(new JLabel(" Norm: "));
        p1.add(trap_norm_field);

        final JTextField real_textfield = new JTextField(10);
        real_textfield.setText("" + ots.trapPoint[0]);

        final JTextField imaginary_textfield = new JTextField(10);
        imaginary_textfield.setText("" + ots.trapPoint[1]);

        JTextField useLastX = new JTextField(4);
        useLastX.setToolTipText("Takes into account only the last X samples (0: use all samples).");
        useLastX.setText("" + ots.lastXItems);

        JComboBox<String> checkType = new JComboBox<>(Constants.orbitTrapCheckTypes);
        checkType.setSelectedIndex(ots.checkType);
        checkType.setFocusable(false);
        checkType.setToolTipText("Sets the trap finding method.");

        final JTextField skipCheck = new JTextField(4);
        skipCheck.setText("" + ots.skipTrapCheckForIterations);

        JPanel p2 = new JPanel();
        p2.setBackground(MainWindow.bg_color);

        p2.add(new JLabel(" Check: "));
        p2.add(checkType);
        p2.add(new JLabel(" Use Last X Samples: "));
        p2.add(useLastX);
        p2.add(new JLabel(" Skip Fist X Samples: "));
        p2.add(skipCheck);


        JPanel p12 = new JPanel();
        p12.setBackground(MainWindow.bg_color);

        p12.add(new JLabel("Lines: "));
        p12.add(lines_function_combo);
        p12.add(new JLabel(" Center Re: "));
        p12.add(real_textfield);
        p12.add(new JLabel(" Im: "));
        p12.add(imaginary_textfield);

        trap_length_field = new JTextField(9);
        trap_length_field.setText("" + ots.trapLength);

        trap_width_field = new JTextField(9);
        trap_width_field.setText("" + ots.trapWidth);
        
        trap_threshold_field = new JTextField(9);
        trap_threshold_field.setText("" + ots.trapMaxDistance);
        
        trap_intensity_field = new JTextField(4);
        trap_intensity_field.setText("" + ots.trapIntensity);

        trap_offset_field = new JTextField(4);
        trap_offset_field.setText("" + ots.trapOffset);

        JPanel p3 = new JPanel();
        p3.setBackground(MainWindow.bg_color);

        p3.add(new JLabel("Length: "));
        p3.add(trap_length_field);
        p3.add(new JLabel(" Width: "));
        p3.add(trap_width_field);
        p3.add(new JLabel(" Max Distance: "));
        p3.add(trap_threshold_field);

        blend_opt = new SliderGradient(JSlider.HORIZONTAL, 0, 100, 0);
        blend_opt.setValue((int) (100 * ots.trapBlending));
        blend_opt.setBackground(MainWindow.bg_color);

        if(!MainWindow.useCustomLaf) {
            blend_opt.setBackground(MainWindow.bg_color);
        }

        blend_opt.setMajorTickSpacing(25);
        blend_opt.setMinorTickSpacing(1);
        blend_opt.setToolTipText("Sets the trap blending percentage.");
        blend_opt.setFocusable(false);
        blend_opt.setPaintLabels(true);
        
        color_method_combo = new JComboBox<>(Constants.colorMethod);
        color_method_combo.setSelectedIndex(ots.trapColorMethod);
        color_method_combo.setFocusable(false);
        color_method_combo.setToolTipText("Sets the color method.");
        
        color_method_combo.addActionListener(e -> blend_opt.setEnabled(color_method_combo.getSelectedIndex() == 3));
      
        JPanel p4 = new JPanel();
        p4.setBackground(MainWindow.bg_color);

        p4.add(new JLabel("Color Method: "));
        p4.add(color_method_combo);
        p4.add(new JLabel(" Trap Blending: "));
        p4.add(blend_opt);
        
        final JCheckBox include_escaped_opt = new JCheckBox("Include Escaped");
        include_escaped_opt.setFocusable(false);
        include_escaped_opt.setToolTipText("Includes the escaped points into the trap application.");
        include_escaped_opt.setBackground(MainWindow.bg_color);
        include_escaped_opt.setSelected(ots.trapIncludeEscaped);
        
        final JCheckBox include_notescaped_opt = new JCheckBox("Include Not Escaped");
        include_notescaped_opt.setFocusable(false);
        include_notescaped_opt.setToolTipText("Includes the not escaped points into the trap application.");
        include_notescaped_opt.setBackground(MainWindow.bg_color);
        include_notescaped_opt.setSelected(ots.trapIncludeNotEscaped);
        
        
        final JCheckBox trap_iterations_opt = new JCheckBox("Trap Iterations");
        trap_iterations_opt.setFocusable(false);
        trap_iterations_opt.setToolTipText("Counts the iterations for the orbit getting trapped.");
        trap_iterations_opt.setBackground(MainWindow.bg_color);
        trap_iterations_opt.setSelected(ots.countTrapIterations);
        
        JPanel p5 = new JPanel();
        p5.setBackground(MainWindow.bg_color);
        
       
        p5.add(include_escaped_opt);
        p5.add(include_notescaped_opt);
        p5.add(trap_iterations_opt);
        
        
        JPanel p9 = new JPanel();
        p9.setBackground(MainWindow.bg_color);
        
        JComboBox<String> heightFunction = new JComboBox<>(Constants.trapHeightAlgorithms);
        heightFunction.setFocusable(false);
        heightFunction.setSelectedIndex(ots.trapHeightFunction);
        heightFunction.setToolTipText("Sets the trap height function.");
        
        final JCheckBox invert_height_opt = new JCheckBox("Invert Height");
        invert_height_opt.setFocusable(false);
        invert_height_opt.setToolTipText("Inverts the height function.");
        invert_height_opt.setBackground(MainWindow.bg_color);
        invert_height_opt.setSelected(ots.invertTrapHeight);
        
        p9.add(new JLabel("Intensity: "));
        p9.add(trap_intensity_field);
        p9.add(new JLabel(" Offset: "));
        p9.add(trap_offset_field);
        p9.add(new JLabel(" Height: "));
        p9.add(heightFunction);
        p9.add(invert_height_opt);

        JPanel p10 = new JPanel();
        p10.setBackground(MainWindow.bg_color);

        m1 = new JTextField(7);
        m1.setText("" + ots.sfm1);

        m2 = new JTextField(7);
        m2.setText("" + ots.sfm2);

        n1 = new JTextField(7);
        n1.setText("" + ots.sfn1);

        n2 = new JTextField(7);
        n2.setText("" + ots.sfn2);

        n3 = new JTextField(7);
        n3.setText("" + ots.sfn3);

        a = new JTextField(7);
        a.setText("" + ots.sfa);

        b = new JTextField(7);
        b.setText("" + ots.sfb);

        p10.add(new JLabel("m1: "));
        p10.add(m1);
        p10.add(new JLabel(" m2: "));
        p10.add(m2);
        p10.add(new JLabel(" n1: "));
        p10.add(n1);
        p10.add(new JLabel(" n2: "));
        p10.add(n2);

        JPanel p11 = new JPanel();
        p11.setBackground(MainWindow.bg_color);

        p11.add(new JLabel("n3: "));
        p11.add(n3);
        p11.add(new JLabel(" a: "));
        p11.add(a);
        p11.add(new JLabel(" b: "));
        p11.add(b);

        final JLabel trap1_color_label = new ColorLabel();

        trap1_color_label.setPreferredSize(new Dimension(22, 22));
        trap1_color_label.setBackground(ots.trapColor1);
        trap1_color_label.setToolTipText("Changes the trap color.");

        trap1_color_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(!trap1_color_label.isEnabled()) {
                    return;
                }

                new ColorChooserDialog(this_frame, "Trap Color", trap1_color_label, -1, -1);
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
        
        final JLabel trap2_color_label = new ColorLabel();

        trap2_color_label.setPreferredSize(new Dimension(22, 22));
        trap2_color_label.setBackground(ots.trapColor2);
        trap2_color_label.setToolTipText("Changes the trap color.");

        trap2_color_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(!trap2_color_label.isEnabled()) {
                    return;
                }

                new ColorChooserDialog(this_frame, "Trap Color", trap2_color_label, -1, -1);
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
        
        final JLabel trap3_color_label = new ColorLabel();

        trap3_color_label.setPreferredSize(new Dimension(22, 22));
        trap3_color_label.setBackground(ots.trapColor3);
        trap3_color_label.setToolTipText("Changes the trap color.");

        trap3_color_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(!trap3_color_label.isEnabled()) {
                    return;
                }

                new ColorChooserDialog(this_frame, "Trap Color", trap3_color_label, -1, -1);
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

        final JLabel background_label = new ColorLabel();

        ignoreComponents.add(background_label);

        background_label.setPreferredSize(new Dimension(22, 22));
        background_label.setBackground(ots.background);
        background_label.setToolTipText("Changes the background color.");

        background_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(!background_label.isEnabled()) {
                    return;
                }

                new ColorChooserDialog(this_frame, "Background Color", background_label, -1, -1);
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
        
        JSlider interpolation_opt = new SliderGradient(JSlider.HORIZONTAL, 0, 100, 0);
        interpolation_opt.setValue((int) (100 * ots.trapColorInterpolation));
        if(!MainWindow.useCustomLaf) {
            interpolation_opt.setBackground(MainWindow.bg_color);
        }
        interpolation_opt.setBackground(MainWindow.bg_color);
        interpolation_opt.setMajorTickSpacing(25);
        interpolation_opt.setMinorTickSpacing(1);
        interpolation_opt.setToolTipText("Sets the trap color interpolation percentage.");
        interpolation_opt.setFocusable(false);
        interpolation_opt.setPaintLabels(true);
        
        JPanel p6 = new JPanel();
        p6.setBackground(MainWindow.bg_color);
        
        p6.add(new JLabel("Trap 1: "));
        p6.add(trap1_color_label);
        p6.add(new JLabel(" Trap 2: "));
        p6.add(trap2_color_label);
        p6.add(new JLabel(" Trap 3: "));
        p6.add(trap3_color_label);
        p6.add(new JLabel(" Interpolation: "));
        p6.add(interpolation_opt);
        
        JPanel p7 = new JPanel();
        p7.setBackground(MainWindow.bg_color);
        
        JComboBox<String> colors = new JComboBox<>(new String[] {"Per Trap", "Random", "Hue/Arg HSB", "Hue/Arg LCH_ab", "Random HSB", "Random Palette", "Arg Palette", "Trap Iterations HSB", "Trap Iterations LCH_ab", "Hue/Arg LCH_uv", "Trap Iterations LCH_uv"});
        colors.setFocusable(false);
        colors.setSelectedIndex(ots.trapColorFillingMethod);
        colors.setToolTipText("Sets the trap color filling method.");

        p7.add(showOnlyTraps);

        JLabel bgLabel = new JLabel(" Background: ");
        p7.add(bgLabel);
        p7.add(background_label);
        p7.add(new JLabel(" Color Filling Method: "));
        p7.add(colors);

        ignoreComponents.add(bgLabel);
        
        
        JPanel p8 = new JPanel();
        p8.setBackground(MainWindow.bg_color);

        trap_cellular_opt = new JCheckBox("Cell Structure");
        trap_cellular_opt.setFocusable(false);
        trap_cellular_opt.setToolTipText("Creates a cellular structure around each area.");
        trap_cellular_opt.setBackground(MainWindow.bg_color);
        trap_cellular_opt.setSelected(ots.trapCellularStructure);
        
        
        cellular_color_label = new ColorLabel();

        cellular_color_label.setPreferredSize(new Dimension(22, 22));
        cellular_color_label.setBackground(ots.trapCellularColor);
        cellular_color_label.setToolTipText("Changes the cellular border color.");

        cellular_color_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(!cellular_color_label.isEnabled()) {
                    return;
                }

                new ColorChooserDialog(this_frame, "Cellular Border Color", cellular_color_label, -1, -1);
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

        invert_trap_cellular_opt = new JCheckBox("Invert Coloring");
        invert_trap_cellular_opt.setFocusable(false);
        invert_trap_cellular_opt.setToolTipText("Inverts the cellular coloring.");
        invert_trap_cellular_opt.setBackground(MainWindow.bg_color);
        invert_trap_cellular_opt.setSelected(ots.trapCellularInverseColor);
        
        
        cellular_size_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        cellular_size_opt.setValue((int) (100 * ots.trapCellularSize));
        if(!MainWindow.useCustomLaf) {
            cellular_size_opt.setBackground(MainWindow.bg_color);
        }
        cellular_size_opt.setMajorTickSpacing(25);
        cellular_size_opt.setMinorTickSpacing(1);
        cellular_size_opt.setToolTipText("Sets the trap cellular size.");
        cellular_size_opt.setFocusable(false);
        cellular_size_opt.setPaintLabels(true);

        ComponentTitledBorder cellular_border = new ComponentTitledBorder(trap_cellular_opt, p8, LAFManager.createUnTitledBorder(), this_frame);
        cellular_border.setChangeListener();

        p8.setBorder(cellular_border);

        p8.setPreferredSize(new Dimension(560, 70));

        p8.add(invert_trap_cellular_opt);

        l1 = new JLabel(" Color: ");
        p8.add(l1);
        p8.add(cellular_color_label);
        l2 = new JLabel(" Size: ");
        p8.add(l2);
        p8.add(cellular_size_opt);
        
              
        color_options_panel = new JPanel();
        color_options_panel.setPreferredSize(new Dimension(580, 240));
        color_options_panel.setBorder(LAFManager.createTitledBorder( "Color Options"));
        color_options_panel.setLayout(new FlowLayout());
        color_options_panel.setBackground(MainWindow.bg_color);

        ignoreComponents.add(color_options_panel);
        
        JPanel trap_options_panel = new JPanel();
        trap_options_panel.setPreferredSize(new Dimension(580, 355));
        trap_options_panel.setBorder(LAFManager.createTitledBorder("Trap Options"));
       // trap_options_panel.setLayout(new GridLayout(8, 1));
        trap_options_panel.setBackground(MainWindow.bg_color);
        
        color_options_panel.add(p4);
        color_options_panel.add(p7);
        color_options_panel.add(p6);
        color_options_panel.add(p8);

        JPanel superformula_panel = new JPanel();
        superformula_panel.setPreferredSize(new Dimension(560, 100));
        superformula_panel.setBorder(LAFManager.createTitledBorder( "Super-formula Options"));
        superformula_panel.setBackground(MainWindow.bg_color);

        superformula_panel.add(p10);
        superformula_panel.add(p11);

        trap_options_panel.add(p1);
        trap_options_panel.add(p2);
        trap_options_panel.add(p12);
        trap_options_panel.add(p3);
        trap_options_panel.add(superformula_panel);
        trap_options_panel.add(p9);
        trap_options_panel.add(p5);
        
        options_panel.add(trap_options_panel);
        options_panel.add(color_options_panel);

        JButton ok = new MyButton("Ok");
        ok.setFocusable(false);
        
        getRootPane().setDefaultButton( ok );

        ok.addActionListener(e -> {

            double temp, temp2, temp3, temp4, temp5, temp6, temp7;
            double tempM1, tempM2, tempN1, tempN2, tempN3, tempA, tempB;
            int skipTrapIterations, temp21, temp22;
            try {
                temp = Double.parseDouble(trap_norm_field.getText());
                temp2 = Double.parseDouble(real_textfield.getText());
                temp3 = Double.parseDouble(imaginary_textfield.getText());
                temp4 = Double.parseDouble(trap_length_field.getText());
                temp5 = Double.parseDouble(trap_width_field.getText());
                temp6 = Double.parseDouble(trap_threshold_field.getText());
                temp7 = Double.parseDouble(trap_intensity_field.getText());
                temp22 = Integer.parseInt(trap_offset_field.getText());
                tempM1 = Double.parseDouble(m1.getText());
                tempM2 = Double.parseDouble(m2.getText());
                tempN1 = Double.parseDouble(n1.getText());
                tempN2 = Double.parseDouble(n2.getText());
                tempN3 = Double.parseDouble(n3.getText());
                tempA = Double.parseDouble(a.getText());
                tempB = Double.parseDouble(b.getText());
                skipTrapIterations = Integer.parseInt(skipCheck.getText());
                temp21 = Integer.parseInt(useLastX.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this_frame, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(temp21 < 0) {
                JOptionPane.showMessageDialog(this_frame, "The last x samples value must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(temp22 < 0) {
                JOptionPane.showMessageDialog(this_frame, "Trap Offset value must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (temp4 < 0) {
                JOptionPane.showMessageDialog(this_frame, "Trap Length must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (temp5 < 0) {
                JOptionPane.showMessageDialog(this_frame, "Trap Width must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (temp6 < 0) {
                JOptionPane.showMessageDialog(this_frame, "Trap Max Distance must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (temp7 < 0) {
                JOptionPane.showMessageDialog(this_frame, "Trap Intensity must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(skipTrapIterations < 0) {
                JOptionPane.showMessageDialog(this_frame, "Trap skip first iterations must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ots.trapType = orbit_traps_combo.getSelectedIndex();
            ots.useTraps = orbit_traps_opt.isSelected();
            ots.trapLength = temp4;
            ots.trapWidth = temp5;
            ots.trapMaxDistance = temp6;
            ots.trapIntensity = temp7;
            ots.trapNorm = temp;
            ots.trapPoint[0] = temp2;
            ots.trapPoint[1] = temp3;
            ots.trapBlending = blend_opt.getValue() / 100.0;
            ots.lineType = lines_function_combo.getSelectedIndex();
            ots.trapColorMethod = color_method_combo.getSelectedIndex();
            ots.trapColor1 = trap1_color_label.getBackground();
            ots.trapColor2 = trap2_color_label.getBackground();
            ots.trapColor3 = trap3_color_label.getBackground();
            ots.trapColorInterpolation = interpolation_opt.getValue() / 100.0;
            ots.trapIncludeEscaped = include_escaped_opt.isSelected();
            ots.trapIncludeNotEscaped = include_notescaped_opt.isSelected();
            ots.trapColorFillingMethod = colors.getSelectedIndex();

            ots.trapCellularInverseColor = invert_trap_cellular_opt.isSelected();
            ots.trapCellularStructure = trap_cellular_opt.isSelected();
            ots.trapCellularColor = cellular_color_label.getBackground();
            ots.trapCellularSize = cellular_size_opt.getValue() / 100.0;
            ots.countTrapIterations = trap_iterations_opt.isSelected();
            ots.trapHeightFunction = heightFunction.getSelectedIndex();
            ots.invertTrapHeight = invert_height_opt.isSelected();

            ots.checkType = checkType.getSelectedIndex();
            ots.skipTrapCheckForIterations = skipTrapIterations;

            ots.trapOffset = temp22;

            ots.sfm1 = tempM1;
            ots.sfm2 = tempM2;
            ots.sfn1 = tempN1;
            ots.sfn2 = tempN2;
            ots.sfn3 = tempN3;
            ots.sfa = tempA;
            ots.sfb = tempB;

            ots.lastXItems = temp21;

            if(image != null) {
                ots.trapImage = image;
                ImageOrbitTrap.image = ots.trapImage;
                TransparentImageOrbitTrap.image = ots.trapImage;
            }

            ots.showOnlyTraps = showOnlyTraps.isSelected();
            ots.background = background_label.getBackground();

            dispose();

            if (ots.useTraps && ots.lastXItems == 0
                    && s.isPertubationTheoryInUse()) {
                JOptionPane.showMessageDialog(ptra, "Using all samples will not work correctly while using Perturbation Theory.", "Warning!", JOptionPane.WARNING_MESSAGE);
            }

            ptra2.setOrbitTrapSettings();

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

        JButton close = new MyButton("Cancel");
        close.setFocusable(false);
        close.addActionListener(e -> {

            ptra2.setEnabled(true);
            dispose();

        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
        getRootPane().getActionMap().put("Cancel", new AbstractAction()
        {

            public void actionPerformed(ActionEvent e)
            {
                close.doClick();
            }
        });

        options_border.setState(orbit_traps_opt.isSelected());
        toggled();

        JPanel buttons = new JPanel();
        buttons.setBackground(MainWindow.bg_color);

        buttons.add(ok);
        buttons.add(close);

        RoundedPanel round_panel = LAFManager.createRoundedPanel();
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(630, 705));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(options_panel, con);

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

    public void toggled() {

        if (!orbit_traps_opt.isSelected()) {
            trap_cellular_opt.setEnabled(false);
            return;
        }

        trap_cellular_opt.setEnabled(true);

        int index = orbit_traps_combo.getSelectedIndex();

        if(index != MainWindow.IMAGE_TRAP && index != MainWindow.IMAGE_TRANSPARENT_TRAP) {
            trap_threshold_field.setEnabled(true);
            trap_intensity_field.setEnabled(true);
            trap_offset_field.setEnabled(true);
            setComponentState(color_options_panel ,true);
        }
        else {
            trap_threshold_field.setEnabled(false);
            trap_intensity_field.setEnabled(false);
            trap_offset_field.setEnabled(false);
            setComponentState(color_options_panel ,false);
        }


        trap_norm_field.setEnabled(index == MainWindow.NNORM_ATOM_DOMAIN_TRAP || index == MainWindow.POINT_N_NORM_TRAP || index == MainWindow.N_NORM_TRAP || index == MainWindow.N_NORM_CROSS_TRAP || index == MainWindow.N_NORM_POINT_TRAP || index == MainWindow.N_NORM_POINT_N_NORM_TRAP || index == MainWindow.GOLDEN_RATIO_SPIRAL_POINT_N_NORM_TRAP || index == MainWindow.GOLDEN_RATIO_SPIRAL_N_NORM_TRAP || index == MainWindow.STALKS_POINT_N_NORM_TRAP || index == MainWindow.STALKS_N_NORM_TRAP);
        trap_width_field.setEnabled(!(index == MainWindow.ATOM_DOMAIN_TRAP || index == MainWindow.SQUARE_ATOM_DOMAIN_TRAP || index == MainWindow.RHOMBUS_ATOM_DOMAIN_TRAP || index == MainWindow.NNORM_ATOM_DOMAIN_TRAP || index == MainWindow.POINT_RHOMBUS_TRAP || index == MainWindow.POINT_SQUARE_TRAP || index == MainWindow.POINT_TRAP || index == MainWindow.POINT_N_NORM_TRAP || index == MainWindow.IMAGE_TRANSPARENT_TRAP));
        trap_length_field.setEnabled(!(index == Constants.SUPER_FORMULA_ORBIT_TRAP || index == MainWindow.ATOM_DOMAIN_TRAP || index == MainWindow.SQUARE_ATOM_DOMAIN_TRAP || index == MainWindow.RHOMBUS_ATOM_DOMAIN_TRAP || index == MainWindow.NNORM_ATOM_DOMAIN_TRAP ||  index == MainWindow.GOLDEN_RATIO_SPIRAL_TRAP || index == MainWindow.STALKS_TRAP || index == MainWindow.IMAGE_TRANSPARENT_TRAP || index == MainWindow.MOVING_AVERAGE_1_TRAP || index == MainWindow.MOVING_AVERAGE_2_TRAP));
        lines_function_combo.setEnabled(index == MainWindow.CROSS_TRAP || index ==  MainWindow.RE_TRAP || index ==  MainWindow.IM_TRAP || index ==  MainWindow.CIRCLE_CROSS_TRAP || index ==  MainWindow.SQUARE_CROSS_TRAP || index ==  MainWindow.RHOMBUS_CROSS_TRAP || index ==  MainWindow.N_NORM_CROSS_TRAP || index == MainWindow.GOLDEN_RATIO_SPIRAL_CROSS_TRAP || index == MainWindow.STALKS_CROSS_TRAP);

        if(index == MainWindow.SUPER_FORMULA_ORBIT_TRAP) {
            m1.setEnabled(true);
            m2.setEnabled(true);
            n1.setEnabled(true);
            n2.setEnabled(true);
            n3.setEnabled(true);
            a.setEnabled(true);
            b.setEnabled(true);
        }
        else {
            m1.setEnabled(false);
            m2.setEnabled(false);
            n1.setEnabled(false);
            n2.setEnabled(false);
            n3.setEnabled(false);
            a.setEnabled(false);
            b.setEnabled(false);
        }
        
        blend_opt.setEnabled(color_method_combo.getSelectedIndex() == 3 && index != MainWindow.IMAGE_TRAP && index != MainWindow.IMAGE_TRANSPARENT_TRAP);

        load_image_button.setEnabled(index == MainWindow.IMAGE_TRAP || index == MainWindow.IMAGE_TRANSPARENT_TRAP);

        cellular_color_label.setEnabled(trap_cellular_opt.isSelected() && index != MainWindow.IMAGE_TRAP && index != MainWindow.IMAGE_TRANSPARENT_TRAP);
        invert_trap_cellular_opt.setEnabled(trap_cellular_opt.isSelected() && index != MainWindow.IMAGE_TRAP && index != MainWindow.IMAGE_TRANSPARENT_TRAP);
        cellular_size_opt.setEnabled(trap_cellular_opt.isSelected() && index != MainWindow.IMAGE_TRAP && index != MainWindow.IMAGE_TRANSPARENT_TRAP);
        trap_cellular_opt.setEnabled(index != MainWindow.IMAGE_TRAP && index != MainWindow.IMAGE_TRANSPARENT_TRAP);
        l1.setEnabled(trap_cellular_opt.isSelected() && index != MainWindow.IMAGE_TRAP && index != MainWindow.IMAGE_TRANSPARENT_TRAP);
        l2.setEnabled(trap_cellular_opt.isSelected() && index != MainWindow.IMAGE_TRAP && index != MainWindow.IMAGE_TRANSPARENT_TRAP);
    }

    private void loadPatternImage(Component parent) {

        JFileChooser file_chooser = new JFileChooser(".");

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        FileFilter imageFilter = new FileNameExtensionFilter(
                "Image Files", ImageIO.getReaderFileSuffixes());

        file_chooser.addChoosableFileFilter(imageFilter);

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
            String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
            file_chooser.setSelectedFile(new File(file_name));
        });

        int returnVal = file_chooser.showDialog(parent, "Load Pattern Image");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = file_chooser.getSelectedFile().getPath();

            if(path != null && !path.isEmpty()) {
                try {
                    image = ImageIO.read(new File(path));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this_frame, "Error while loading the " + path + " file.", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void setComponentState(Component component, boolean state) {
        if(ignoreComponents.indexOf(component) == -1) {
            component.setEnabled(state);
        }
        if (component instanceof JComponent) {
            JComponent a = (JComponent) component;
            Component[] comp = a.getComponents();
            for (int i = 0; i < comp.length; i++) {
                setComponentState(comp[i], state);
            }
        }
    }

}
