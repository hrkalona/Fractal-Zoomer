
package fractalzoomer.gui;

import fractalzoomer.core.TaskRender;
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
public class ContourColoringDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public ContourColoringDialog(MainWindow ptr, Settings s, boolean greedy_algorithm, boolean julia_map) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Contour Coloring");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        final JCheckBox enable_contour_coloring = new JCheckBox("Contour Coloring");
        enable_contour_coloring.setSelected(s.pps.cns.contour_coloring);
        enable_contour_coloring.setFocusable(false);

        final JComboBox<String> contour_coloring_algorithm_opt = new JComboBox<>(Constants.contourColorAlgorithmNames);
        contour_coloring_algorithm_opt.setSelectedIndex(s.pps.cns.contour_algorithm);
        contour_coloring_algorithm_opt.setFocusable(false);
        contour_coloring_algorithm_opt.setToolTipText("Sets the contour coloring algorithm.");
        contour_coloring_algorithm_opt.setPreferredSize(new Dimension(150, 20));

        JSlider color_blend_opt = new SliderGradient(JSlider.HORIZONTAL, 0, 100, (int) (s.pps.cns.cn_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        JComboBox<String> color_method_combo = new JComboBox<>(Constants.colorMethod);
        color_method_combo.setSelectedIndex(s.pps.cns.contourColorMethod);
        color_method_combo.setFocusable(false);
        color_method_combo.setToolTipText("Sets the color method.");

        color_method_combo.addActionListener(e -> color_blend_opt.setEnabled(color_method_combo.getSelectedIndex() == 3));

        color_blend_opt.setEnabled(s.pps.cns.contourColorMethod == 3);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.pps.cns.cn_noise_reducing_factor);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 2));

        JPanel p2 = new JPanel();
        p2.add(color_method_combo);

        p1.add(new JLabel("Color Method:", SwingConstants.HORIZONTAL));
        p1.add(new JLabel("Color Blending:", SwingConstants.HORIZONTAL));
        p1.add(p2);
        p1.add(color_blend_opt);

        JTextField min_contour = new JTextField();
        min_contour.setText("" + s.pps.cns.min_contour);

        min_contour.setEnabled(contour_coloring_algorithm_opt.getSelectedIndex() == 0
        || contour_coloring_algorithm_opt.getSelectedIndex() == 2 || contour_coloring_algorithm_opt.getSelectedIndex() == 3);

        contour_coloring_algorithm_opt.addActionListener(e -> {min_contour.setEnabled(contour_coloring_algorithm_opt.getSelectedIndex() == 0
                || contour_coloring_algorithm_opt.getSelectedIndex() == 2 || contour_coloring_algorithm_opt.getSelectedIndex() == 3);});

        final JComboBox<String> fractional_transfer = new JComboBox<>(Constants.fractionalTransfer);
        fractional_transfer.setSelectedIndex(s.pps.cns.fractionalTransfer);
        fractional_transfer.setFocusable(false);
        fractional_transfer.setToolTipText("Sets the fractional transfer function.");

        final JComboBox<String> fractional_smoothing = new JComboBox<>(Constants.FadeAlgs);
        fractional_smoothing.setSelectedIndex(s.pps.cns.fractionalSmoothing);
        fractional_smoothing.setFocusable(false);
        fractional_smoothing.setToolTipText("Sets the fractional smoothing function.");

        JPanel p20 = new JPanel();
        p20.add(fractional_transfer);
        JPanel p21 = new JPanel();
        p21.add(fractional_smoothing);

        JPanel p3 = new JPanel();
        p3.setLayout(new GridLayout(2, 3));

        p3.add(new JLabel("Fractional Transfer:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Fractional Smoothing:", SwingConstants.HORIZONTAL));
        p3.add(p20);
        p3.add(p21);

        Object[] message = {
            " ",
            enable_contour_coloring,
            " ",
            "Set the contour coloring algorithm.",
            "Contour Coloring Algorithm:", contour_coloring_algorithm_opt,
            " ",
            "Set the color method and blending percentage.",
            p1,
                " ",
                "Set the minimum contour factor.",
                "Minimum Contour Factor:",
                min_contour,
                " ",
                "Set the fractional transfer/smoothing.",
                p3,
                " ",
            "Set the image noise reduction factor.",
            "Noise Reduction Factor:",
            noise_factor_field,
            " "};

        optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            return;
                        }

                        try {
                            double temp2 = Double.parseDouble(noise_factor_field.getText());
                            double temp3 = Double.parseDouble(min_contour.getText());

                            if (temp2 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The noise reduction factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp3 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The min contour factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            else if (temp3 > 0.5) {
                                JOptionPane.showMessageDialog(ptra, "The min contour factor must be less or equal to 0.5.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.pps.cns.contour_coloring = enable_contour_coloring.isSelected();
                            s.pps.cns.cn_noise_reducing_factor = temp2;
                            s.pps.cns.cn_blending = color_blend_opt.getValue() / 100.0;
                            s.pps.cns.contour_algorithm = contour_coloring_algorithm_opt.getSelectedIndex();
                            s.pps.cns.contourColorMethod = color_method_combo.getSelectedIndex();
                            s.pps.cns.min_contour = temp3;
                            s.pps.cns.fractionalTransfer = fractional_transfer.getSelectedIndex();
                            s.pps.cns.fractionalSmoothing = fractional_smoothing.getSelectedIndex();

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();

                        if (greedy_algorithm && !TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA && enable_contour_coloring.isSelected() && !julia_map && !s.d3s.d3) {
                            JOptionPane.showMessageDialog(ptra, Constants.greedyWarning, "Warning!", JOptionPane.WARNING_MESSAGE);
                        }

                        ptra.setPostProcessingPost();
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
