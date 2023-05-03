/*
 * Copyright (C) 2020 hrkalona2
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

import fractalzoomer.core.ThreadDraw;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class D3Dialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public D3Dialog(MainWindow ptr, Settings s) {

        super(ptr);
        
        ptra = ptr;

        setTitle("3D");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        final JCheckBox d3 = new JCheckBox("3D");
        d3.setSelected(s.d3s.d3);
        d3.setFocusable(false);
        d3.setToolTipText("Enables the 3D rendering.");

        JTextField field = new JTextField();
        field.setText("" + s.d3s.detail);

        JTextField size_opt = new JTextField();
        size_opt.setText("" + s.d3s.d3_size_scale);

        JTextField field2 = new JTextField();
        field2.setText("" + s.d3s.d3_height_scale);

        RangeSlider scale_range = new RangeSlider(0, 100);
        scale_range.setValue(s.d3s.min_range);
        scale_range.setUpperValue(s.d3s.max_range);
        scale_range.setPreferredSize(new Dimension(150, 35));
        scale_range.setMajorTickSpacing(25);
        scale_range.setMinorTickSpacing(1);
        scale_range.setToolTipText("Sets the scaling range.");
        scale_range.setFocusable(false);
        scale_range.setPaintLabels(true);

        JSlider scale_max_val_opt = new JSlider(JSlider.HORIZONTAL, 0, 200, s.d3s.max_scaling);
        scale_max_val_opt.setPreferredSize(new Dimension(150, 35));
        scale_max_val_opt.setMajorTickSpacing(50);
        scale_max_val_opt.setMinorTickSpacing(1);
        scale_max_val_opt.setToolTipText("Sets the scaling value.");
        scale_max_val_opt.setPaintLabels(true);
        scale_max_val_opt.setFocusable(false);

        JPanel temp_p3 = new JPanel();
        temp_p3.setLayout(new GridLayout(2, 2));
        temp_p3.add(new JLabel("Scaling Value:", SwingConstants.HORIZONTAL));
        temp_p3.add(new JLabel("Scaling Range:", SwingConstants.HORIZONTAL));
        temp_p3.add(scale_max_val_opt);
        temp_p3.add(scale_range);


        JPanel temp_p5 = new JPanel();
        temp_p5.setLayout(new GridLayout(1, 3));
        final JCheckBox preHeightScaling = new JCheckBox("Pre-Scaling");
        preHeightScaling.setSelected(s.d3s.preHeightScaling);
        preHeightScaling.setFocusable(false);
        preHeightScaling.setToolTipText("Enables the pre-scaling of height values.");

        final JCheckBox removeOutliers = new JCheckBox("Pre-Remove Outliers");
        removeOutliers.setSelected(s.d3s.remove_outliers_pre);
        removeOutliers.setFocusable(false);
        removeOutliers.setToolTipText("Removes outliers from the values (Pre function application step).");

        final JCheckBox removeOutliersPost = new JCheckBox("Post-Remove Outliers");
        removeOutliersPost.setSelected(s.d3s.remove_outliers_post);
        removeOutliersPost.setFocusable(false);
        removeOutliersPost.setToolTipText("Removes outliers from the values (Post function application step).");

        temp_p5.add(removeOutliers);
        temp_p5.add(removeOutliersPost);
        temp_p5.add(preHeightScaling);

        JComboBox<String> outliersAlgorithm = new JComboBox<>(new String[] {"Tukey's Fences", "Z-score"});
        outliersAlgorithm.setSelectedIndex(s.d3s.outliers_method);
        outliersAlgorithm.setFocusable(false);
        outliersAlgorithm.setToolTipText("Sets the outlier removal method.");

        outliersAlgorithm.setEnabled(removeOutliers.isSelected() || removeOutliersPost.isSelected());

        removeOutliersPost.addActionListener(e -> outliersAlgorithm.setEnabled(removeOutliers.isSelected() || removeOutliersPost.isSelected()));

        removeOutliers.addActionListener(e -> outliersAlgorithm.setEnabled(removeOutliers.isSelected() || removeOutliersPost.isSelected()));

        removeOutliers.addActionListener(e -> outliersAlgorithm.setEnabled(removeOutliers.isSelected()));

        String[] height_options = {"log(x + 1)", "log(log(x + 1) + 1)", "1 / (x + 1)", "1 / (log(x + 1) + 1)", "x"};

        JComboBox<String> height_algorithm_opt = new JComboBox<>(height_options);
        height_algorithm_opt.setSelectedIndex(s.d3s.height_algorithm);
        height_algorithm_opt.setFocusable(false);
        height_algorithm_opt.setToolTipText("Sets the height algorithm.");

        final JCheckBox gaussian_scaling_opt = new JCheckBox("Gaussian Normalization");
        gaussian_scaling_opt.setSelected(s.d3s.gaussian_scaling);
        gaussian_scaling_opt.setFocusable(false);
        gaussian_scaling_opt.setToolTipText("Enables the gaussian normalization.");

        final JTextField field3 = new JTextField();
        field3.setText("" + s.d3s.gaussian_weight);
        field3.setEnabled(s.d3s.gaussian_scaling);

        String[] kernels = {"3", "5", "7", "9", "11"};
        final JComboBox<String> kernels_size_opt = new JComboBox<>(kernels);
        kernels_size_opt.setSelectedIndex(s.d3s.gaussian_kernel);
        kernels_size_opt.setFocusable(false);
        kernels_size_opt.setEnabled(s.d3s.gaussian_scaling);
        kernels_size_opt.setToolTipText("Sets the radius of the gaussian normalization.");

        gaussian_scaling_opt.addActionListener(e -> {
            if (gaussian_scaling_opt.isSelected()) {
                field3.setEnabled(true);
                kernels_size_opt.setEnabled(true);
            } else {
                field3.setEnabled(false);
                kernels_size_opt.setEnabled(false);
            }
        });


        /*final JCheckBox histogram_opt = new JCheckBox("Histogram Equalization");
        histogram_opt.setSelected(s.d3s.histogram_equalization);
        histogram_opt.setFocusable(false);
        histogram_opt.setToolTipText("Enables the histogram equalization.");*/

        JSlider color_blend = new JSlider(JSlider.HORIZONTAL, 0, 100, ((int) (s.d3s.color_3d_blending * 100)));
        color_blend.setPreferredSize(new Dimension(270, 35));
        color_blend.setMajorTickSpacing(25);
        color_blend.setMinorTickSpacing(1);
        color_blend.setToolTipText("Sets the color blending percentage.");
        color_blend.setPaintLabels(true);
        color_blend.setFocusable(false);

        JComboBox<String> d3_color_method_combo = new JComboBox<>(Constants.colorMethod);
        d3_color_method_combo.setSelectedIndex(s.d3s.d3_color_type);
        d3_color_method_combo.setFocusable(false);
        d3_color_method_combo.setToolTipText("Sets the 3d color method.");

        d3_color_method_combo.addActionListener(e -> color_blend.setEnabled(d3_color_method_combo.getSelectedIndex() == 3));

        color_blend.setEnabled(s.d3s.d3_color_type == 3);

        JPanel cblend_panel = new JPanel();
        cblend_panel.setLayout(new GridLayout(4, 1));
        cblend_panel.add(new JLabel("Set the coloring option."));
        JPanel color_method_panel = new JPanel();
        color_method_panel.add(d3_color_method_combo);
        cblend_panel.add(color_method_panel);
        cblend_panel.add(new JLabel("Color Blending:"));
        cblend_panel.add(color_blend);

        final JCheckBox height_shading_opt = new JCheckBox("Height Shading");
        height_shading_opt.setSelected(s.d3s.shade_height);
        height_shading_opt.setFocusable(false);
        height_shading_opt.setToolTipText("Enables the height shading.");

        JPanel height_color_panel = new JPanel();
        height_color_panel.setLayout(new GridLayout(3, 1));
        height_color_panel.add(new JLabel("Set the height shading."));

        JPanel triangle_color_panel = new JPanel();
        triangle_color_panel.setLayout(new GridLayout(2, 1));
        triangle_color_panel.add(new JLabel("Set the triangle coloring method."));

        String[] shades = {"White & Black", "White", "Black"};

        final JComboBox<String> shade_choice_box = new JComboBox<>(shades);
        shade_choice_box.setSelectedIndex(s.d3s.shade_choice);
        shade_choice_box.setToolTipText("Selects the shade colors.");
        shade_choice_box.setFocusable(false);

        String[] shade_algorithms = {"Linear Interpolation", "Cosine Interpolation", "<10% and >90% Lin. Int.", "<20% and >80% Lin. Int.", "<30% and >70% Lin. Int.", "<40% and >60% Lin. Int."};

        final JComboBox<String> shade_algorithm_box = new JComboBox<>(shade_algorithms);
        shade_algorithm_box.setSelectedIndex(s.d3s.shade_algorithm);
        shade_algorithm_box.setToolTipText("Selects the shade algorithm.");
        shade_algorithm_box.setFocusable(false);

        final JCheckBox shade_invert_opt = new JCheckBox("Invert Shading");
        shade_invert_opt.setSelected(s.d3s.shade_invert);
        shade_invert_opt.setFocusable(false);
        shade_invert_opt.setToolTipText("Inverts the height shading.");

        JComboBox<String> triangle_coloring = new JComboBox<>(new String[]{"Simple", "Barycentric Gradient", "Average"});
        triangle_coloring.setSelectedIndex(ThreadDraw.D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS);
        triangle_coloring.setFocusable(false);
        triangle_coloring.setToolTipText("Sets the triangle coloring method.");

        JPanel pp = new JPanel();
        pp.add(triangle_coloring);
        triangle_color_panel.add(pp);

        if (!height_shading_opt.isSelected()) {
            shade_choice_box.setEnabled(false);
            shade_algorithm_box.setEnabled(false);
            shade_invert_opt.setEnabled(false);
        } else {
            shade_choice_box.setEnabled(true);
            shade_algorithm_box.setEnabled(true);
            shade_invert_opt.setEnabled(true);
        }

        height_shading_opt.addActionListener(e -> {
            if (!height_shading_opt.isSelected()) {
                shade_choice_box.setEnabled(false);
                shade_algorithm_box.setEnabled(false);
                shade_invert_opt.setEnabled(false);
            } else {
                shade_choice_box.setEnabled(true);
                shade_algorithm_box.setEnabled(true);
                shade_invert_opt.setEnabled(true);
            }
        });

        JPanel temp_height_color_panel = new JPanel();

        temp_height_color_panel.add(shade_choice_box);
        temp_height_color_panel.add(shade_algorithm_box);
        temp_height_color_panel.add(shade_invert_opt);

        height_color_panel.add(height_shading_opt);

        height_color_panel.add(temp_height_color_panel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);

        tabbedPane.addTab("Color", cblend_panel);
        tabbedPane.addTab("Height Shading", height_color_panel);
        tabbedPane.addTab("Triangle Coloring", triangle_color_panel);

        JPanel temp_p2 = new JPanel();
        temp_p2.setLayout(new GridLayout(2, 2));
        temp_p2.add(new JLabel("3D Detail:", SwingConstants.HORIZONTAL));
        temp_p2.add(new JLabel("Size:", SwingConstants.HORIZONTAL));
        temp_p2.add(field);
        temp_p2.add(size_opt);

        JPanel temp_p = new JPanel();
        temp_p.setLayout(new GridLayout(2, 2));
        temp_p.add(new JLabel("Weight:", SwingConstants.HORIZONTAL));
        temp_p.add(new JLabel("Radius:", SwingConstants.HORIZONTAL));
        temp_p.add(field3);
        temp_p.add(kernels_size_opt);


        /*final JTextField field_granularity = new JTextField();
        field_granularity.setText("" + s.d3s.histogram_granularity);
        field_granularity.setEnabled(s.d3s.histogram_equalization);

        final JTextField field_density = new JTextField();
        field_density.setText("" + s.d3s.histogram_density);
        field_density.setEnabled(s.d3s.histogram_equalization);*/

        /*JPanel temp_p4 = new JPanel();
        temp_p4.setLayout(new GridLayout(2, 2));
        temp_p4.add(new JLabel("Bin Granularity:", SwingConstants.HORIZONTAL));
        temp_p4.add(new JLabel("Density:", SwingConstants.HORIZONTAL));
        temp_p4.add(field_granularity);
        temp_p4.add(field_density);*/

        /*histogram_opt.addActionListener(e -> {
            if (histogram_opt.isSelected()) {
                field_granularity.setEnabled(true);
                field_density.setEnabled(true);
            } else {
                field_granularity.setEnabled(false);
                field_density.setEnabled(false);
            }
        });*/

        JButton d3Pbutton = new JButton("Processing 3D");
        d3Pbutton.setFocusable(false);
        d3Pbutton.setIcon(MainWindow.getIcon("3d.png"));

        d3Pbutton.addActionListener(e->ptr.showP3D());

        JPanel d3p = new JPanel();
        d3p.add(d3);
        d3p.add(d3Pbutton);

        Object[] message3 = {
                " ",
                d3p,
                " ",
            "Set the 3D detail level and size.",
            temp_p2,
            "Set the scale of the height.",
            "Scale:",
            field2,
            temp_p3,
            " ",
            "Set the height algorithm.",
            "Height Algorithm:",
            height_algorithm_opt,
                " ",
                temp_p5,
                "Outliers Removal Method:",
                outliersAlgorithm,
            " ",
            "Select the gaussian normalization weight and radius.",
            gaussian_scaling_opt,
            temp_p,
            //"Select histogram height equalization parameters.",
            //histogram_opt,
            //temp_p4,
            " ",
            tabbedPane,};

        optionPane = new JOptionPane(message3, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                optionPane.setValue(JOptionPane.CLOSED_OPTION);
            }
        });

        optionPane.addPropertyChangeListener(
                e -> {
                    String prop = e.getPropertyName();

                    if (isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {

                        Object value = optionPane.getValue();

                        if (value == JOptionPane.UNINITIALIZED_VALUE) {
                            //ignore reset
                            return;
                        }

                        //Reset the JOptionPane's value.
                        //If you don't do this, then if the user
                        //presses the same button next time, no
                        //property change event will be fired.
                        optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

                        if ((Integer) value == JOptionPane.CANCEL_OPTION || (Integer) value == JOptionPane.NO_OPTION || (Integer) value == JOptionPane.CLOSED_OPTION) {
                            dispose();
                            ptra.bring3dTofront();
                            return;
                        }

                        try {
                            int temp = Integer.parseInt(field.getText());
                            double temp2 = Double.parseDouble(field2.getText());
                            double temp3 = Double.parseDouble(field3.getText());
                            double temp4 = Double.parseDouble(size_opt.getText());
                            //int temp5 = Integer.parseInt(field_granularity.getText());
                            //double temp6 = Double.parseDouble(field_density.getText());

                            if (temp < 10) {
                                JOptionPane.showMessageDialog(ptra, "The 3D detail level must be greater than 9.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            } else if (temp > 4000) {
                                JOptionPane.showMessageDialog(ptra, "The 3D detail level must be less than 4001.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp2 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The height scale must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp3 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The gaussian normalization weight must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp4 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The size must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            /*if(temp5 < 1) {
                                JOptionPane.showMessageDialog(ptra, "The histogram bin granularity must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp5 > 50) {
                                JOptionPane.showMessageDialog(ptra, "The histogram bin granularity must be lower than 51.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp6 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The histogram density must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }*/

                            s.d3s.detail = temp;
                            s.d3s.d3_height_scale = temp2;
                            s.d3s.d3_size_scale = temp4;
                            s.d3s.height_algorithm = height_algorithm_opt.getSelectedIndex();
                            s.d3s.gaussian_scaling = gaussian_scaling_opt.isSelected();
                            s.d3s.gaussian_weight = temp3;
                            s.d3s.gaussian_kernel = kernels_size_opt.getSelectedIndex();
                            s.d3s.max_range = scale_range.getUpperValue();
                            s.d3s.min_range = scale_range.getValue();
                            s.d3s.max_scaling = scale_max_val_opt.getValue();
                            s.d3s.d3_color_type = d3_color_method_combo.getSelectedIndex();
                            s.d3s.remove_outliers_pre = removeOutliers.isSelected();
                            s.d3s.remove_outliers_post = removeOutliersPost.isSelected();

                            s.d3s.shade_height = height_shading_opt.isSelected();
                            s.d3s.shade_choice = shade_choice_box.getSelectedIndex();
                            s.d3s.shade_algorithm = shade_algorithm_box.getSelectedIndex();
                            s.d3s.shade_invert = shade_invert_opt.isSelected();

                            s.d3s.outliers_method = outliersAlgorithm.getSelectedIndex();

                            //d3_draw_method = draw_choice.getSelectedIndex();
                            s.d3s.color_3d_blending = color_blend.getValue() / 100.0;

                            //s.d3s.histogram_equalization = histogram_opt.isSelected();
                            //s.d3s.histogram_granularity = temp5;
                            //s.d3s.histogram_density = temp6;
                            s.d3s.preHeightScaling = preHeightScaling.isSelected();

                            ThreadDraw.D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS = triangle_coloring.getSelectedIndex();

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.set3DOptionPost(d3.isSelected());
                    }
                });

        //Make this dialog display it.
        setContentPane(optionPane);

        pack();

        setResizable(false);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }

}
