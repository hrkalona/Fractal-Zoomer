
package fractalzoomer.gui;

import fractalzoomer.core.TaskRender;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static fractalzoomer.main.Constants.bumpProcessingMethod;
import static fractalzoomer.main.Constants.bumpTransferNames;

/**
 *
 * @author hrkalona2
 */
public class BumpMappingDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    private final JScrollPane scrollPane;

    public BumpMappingDialog(MainWindow ptr, Settings s, boolean greedy_algorithm, boolean julia_map) {

        super(ptr);
        
        ptra = ptr;

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(600, 700));

        setTitle("Bump Mapping");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        final JCheckBox enable_bump_map = new JCheckBox("Bump Mapping");
        enable_bump_map.setSelected(s.pps.bms.bump_map);
        enable_bump_map.setFocusable(false);

        JSlider direction_of_light = new JSlider(JSlider.HORIZONTAL, -360, 360, ((int) (s.pps.bms.lightDirectionDegrees)));
        direction_of_light.setPreferredSize(new Dimension(300, 40));
        direction_of_light.setMajorTickSpacing(90);
        direction_of_light.setMinorTickSpacing(1);
        direction_of_light.setToolTipText("Sets the direction of light.");
        //color_blend.setPaintTicks(true);
        direction_of_light.setPaintLabels(true);
        //direction_of_light.setSnapToTicks(true);
        direction_of_light.setFocusable(false);

        JSlider depth = new JSlider(JSlider.HORIZONTAL, 0, 100, ((int) (s.pps.bms.bumpMappingDepth)));
        depth.setPreferredSize(new Dimension(300, 40));
        depth.setMajorTickSpacing(25);
        depth.setMinorTickSpacing(1);
        depth.setToolTipText("Sets the depth of the effect.");
        //color_blend.setPaintTicks(true);
        depth.setPaintLabels(true);
        //depth.setSnapToTicks(true);
        depth.setFocusable(false);

        JSlider strength = new JSlider(JSlider.HORIZONTAL, 0, 100, ((int) (s.pps.bms.bumpMappingStrength)));
        strength.setPreferredSize(new Dimension(300, 40));
        strength.setMajorTickSpacing(25);
        strength.setMinorTickSpacing(1);
        strength.setToolTipText("Sets the strength of the effect.");
        //color_blend.setPaintTicks(true);
        strength.setPaintLabels(true);
        //strength.setSnapToTicks(true);
        strength.setFocusable(false);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.pps.bms.bm_noise_reducing_factor);

        final JComboBox<String> bump_transfer_functions_opt = new JComboBox<>(bumpTransferNames);
        bump_transfer_functions_opt.setSelectedIndex(s.pps.bms.bump_transfer_function);
        bump_transfer_functions_opt.setFocusable(false);
        bump_transfer_functions_opt.setToolTipText("Sets the transfer function.");

        JTextField bump_transfer_factor_field = new JTextField(6);
        bump_transfer_factor_field.setText("" + s.pps.bms.bump_transfer_factor);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Transfer Function:"));
        panel.add(new JLabel("Transfer Factor:"));

        JPanel p12 = new JPanel();
        p12.add(bump_transfer_functions_opt);
        JPanel p13 = new JPanel();
        p13.add(bump_transfer_factor_field);
        panel.add(p12);
        panel.add(p13);

        final JComboBox<String> bump_processing_method_opt = new JComboBox<>(bumpProcessingMethod);
        bump_processing_method_opt.setSelectedIndex(s.pps.bms.bumpProcessing);
        bump_processing_method_opt.setFocusable(false);
        bump_processing_method_opt.setToolTipText("Sets the image processing method.");
        bump_processing_method_opt.setPreferredSize(new Dimension(150, 20));

        final JSlider color_blend_opt = new SliderGradient(JSlider.HORIZONTAL, 0, 100, (int) (s.pps.bms.bump_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        color_blend_opt.setEnabled(s.pps.bms.bumpProcessing == 1 || s.pps.bms.bumpProcessing == 2);

        bump_processing_method_opt.addActionListener(e -> color_blend_opt.setEnabled(bump_processing_method_opt.getSelectedIndex() == 1 || bump_processing_method_opt.getSelectedIndex() == 2));

        JPanel p2 = new JPanel();
        p2.add(bump_processing_method_opt);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 2));
        p1.add(new JLabel("Image Processing Method:", SwingConstants.HORIZONTAL));
        p1.add(new JLabel("Color Blending:", SwingConstants.HORIZONTAL));
        p1.add(p2);
        p1.add(color_blend_opt);

        final JComboBox<String> fractional_transfer = new JComboBox<>(Constants.fractionalTransfer);
        fractional_transfer.setSelectedIndex(s.pps.bms.fractionalTransfer);
        fractional_transfer.setFocusable(false);
        fractional_transfer.setToolTipText("Sets the fractional transfer function.");

        final JComboBox<String> fractional_smoothing = new JComboBox<>(Constants.FadeAlgs);
        fractional_smoothing.setSelectedIndex(s.pps.bms.fractionalSmoothing);
        fractional_smoothing.setFocusable(false);
        fractional_smoothing.setToolTipText("Sets the fractional smoothing function.");

        final JComboBox<String> fractional_transfer_mode = new JComboBox<>(Constants.fractionalTransferMode);
        fractional_transfer_mode.setSelectedIndex(s.pps.bms.fractionalTransferMode);
        fractional_transfer_mode.setFocusable(false);
        fractional_transfer_mode.setToolTipText("Sets the fractional transfer mode.");

        fractional_transfer_mode.setEnabled(fractional_transfer.getSelectedIndex() != 0);
        fractional_transfer.addActionListener(e -> fractional_transfer_mode.setEnabled(fractional_transfer.getSelectedIndex() != 0));

        JPanel p20 = new JPanel();
        p20.add(fractional_transfer);
        JPanel p22 = new JPanel();
        p22.add(fractional_transfer_mode);
        JPanel p21 = new JPanel();
        p21.add(fractional_smoothing);

        JPanel p3 = new JPanel();
        p3.setLayout(new GridLayout(2, 3));

        p3.add(new JLabel("Fractional Transfer:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Transfer Mode:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Fractional Smoothing:", SwingConstants.HORIZONTAL));
        p3.add(p20);
        p3.add(p22);
        p3.add(p21);

        Object[] message = {
            " ",
            enable_bump_map,
            " ",
            "Set the direction of light in degrees, depth, and strength.",
            "Direction of light:", direction_of_light,
            " ",
            "Depth:", depth,
            " ",
            "Strength:", strength,
            " ",
            "Set the transfer function and the factor.",
            panel,
            " ",
                "Set the fractional transfer/smoothing.",
                p3,
                " ",
            "Set the image processing method.",
            p1,
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
                            double temp = Double.parseDouble(noise_factor_field.getText());
                            double temp2 = Double.parseDouble(bump_transfer_factor_field.getText());

                            if (temp <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The noise reduction factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp2 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The transfer factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.pps.bms.bump_map = enable_bump_map.isSelected();
                            s.pps.bms.lightDirectionDegrees = direction_of_light.getValue();
                            s.pps.bms.bumpMappingDepth = depth.getValue();
                            s.pps.bms.bumpMappingStrength = strength.getValue();
                            s.pps.bms.bm_noise_reducing_factor = temp;
                            s.pps.bms.bump_transfer_function = bump_transfer_functions_opt.getSelectedIndex();
                            s.pps.bms.bump_transfer_factor = temp2;
                            s.pps.bms.bumpProcessing = bump_processing_method_opt.getSelectedIndex();
                            s.pps.bms.bump_blending = color_blend_opt.getValue() / 100.0;
                            s.pps.bms.fractionalTransfer = fractional_transfer.getSelectedIndex();
                            s.pps.bms.fractionalSmoothing = fractional_smoothing.getSelectedIndex();
                            s.pps.bms.fractionalTransferMode = fractional_transfer_mode.getSelectedIndex();

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();

                        if (greedy_algorithm && !TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA && enable_bump_map.isSelected() && !julia_map && !s.d3s.d3 && !s.ds.domain_coloring) {
                            JOptionPane.showMessageDialog(ptra, Constants.greedyWarning, "Warning!", JOptionPane.WARNING_MESSAGE);
                        }

                        ptra.setPostProcessingPost();
                    }
                });

        //Make this dialog display it.
        scrollPane.setViewportView(optionPane);
        setContentPane(scrollPane);

        pack();

        setResizable(false);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }

}
