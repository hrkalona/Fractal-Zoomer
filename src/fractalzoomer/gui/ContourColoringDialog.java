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

import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author hrkalona2
 */
public class ContourColoringDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public ContourColoringDialog(MainWindow ptr, Settings s, boolean greedy_algorithm, boolean julia_map) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Contour Coloring");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        final JCheckBox enable_contour_coloring = new JCheckBox("Contour Coloring");
        enable_contour_coloring.setSelected(s.cns.contour_coloring);
        enable_contour_coloring.setFocusable(false);

        final JComboBox contour_coloring_algorithm_opt = new JComboBox(Constants.contourColorAlgorithmNames);
        contour_coloring_algorithm_opt.setSelectedIndex(s.cns.contour_algorithm);
        contour_coloring_algorithm_opt.setFocusable(false);
        contour_coloring_algorithm_opt.setToolTipText("Sets the contour coloring algorithm.");
        contour_coloring_algorithm_opt.setPreferredSize(new Dimension(150, 20));

        JSlider color_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (s.cns.cn_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        JComboBox color_method_combo = new JComboBox(Constants.colorMethod);
        color_method_combo.setSelectedIndex(s.cns.contourColorMethod);
        color_method_combo.setFocusable(false);
        color_method_combo.setToolTipText("Sets the color method.");

        color_method_combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color_blend_opt.setEnabled(color_method_combo.getSelectedIndex() == 3);
            }
        });

        color_blend_opt.setEnabled(s.cns.contourColorMethod == 3);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.cns.cn_noise_reducing_factor);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 2));

        JPanel p2 = new JPanel();
        p2.add(color_method_combo);

        p1.add(new JLabel("Color Method:", SwingConstants.HORIZONTAL));
        p1.add(new JLabel("Color Blending:", SwingConstants.HORIZONTAL));
        p1.add(p2);
        p1.add(color_blend_opt);

        Object[] message = {
            " ",
            enable_contour_coloring,
            " ",
            "Set the contour coloring algorthm",
            "Contour Coloring Algorithm:", contour_coloring_algorithm_opt,
            " ",
            "Set the color method and blending percentage.",
            p1,
            " ",
            "Set the image noise reduction factor.",
            "Noise Reduction Factor:",
            noise_factor_field,
            " "};

        optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
            }
        });

        optionPane.addPropertyChangeListener(
                new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
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
                        return;
                    }

                    try {
                        double temp2 = Double.parseDouble(noise_factor_field.getText());

                        if (temp2 <= 0) {
                            JOptionPane.showMessageDialog(ptra, "The noise reduction factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.cns.contour_coloring = enable_contour_coloring.isSelected();
                        s.cns.cn_noise_reducing_factor = temp2;
                        s.cns.cn_blending = color_blend_opt.getValue() / 100.0;
                        s.cns.contour_algorithm = contour_coloring_algorithm_opt.getSelectedIndex();
                        s.cns.contourColorMethod = color_method_combo.getSelectedIndex();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dispose();

                    if (greedy_algorithm && enable_contour_coloring.isSelected() && !julia_map && !s.d3s.d3) {
                        JOptionPane.showMessageDialog(ptra, "Greedy Drawing Algorithm is enabled, which creates glitches in the image.\nYou should disable it for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
                    }

                    if (!s.fns.smoothing && s.cns.contour_coloring) {
                        JOptionPane.showMessageDialog(ptra, "Smoothing is disabled.\nYou should enable smoothing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
                    }

                    ptra.setPostProcessingPost();
                }
            }
        });

        //Make this dialog display it.
        setContentPane(optionPane);

        pack();

        setResizable(false);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }

}
