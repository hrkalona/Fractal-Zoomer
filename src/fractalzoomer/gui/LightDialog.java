
package fractalzoomer.gui;

import fractalzoomer.core.TaskRender;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

/**
 *
 * @author hrkalona2
 */
public class LightDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public LightDialog(MainWindow ptr, Settings s, boolean greedy_algorithm, boolean julia_map) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Light");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        final JCheckBox enable_light = new JCheckBox("Light");
        enable_light.setSelected(s.pps.ls.lighting);
        enable_light.setFocusable(false);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 4));

        JSlider direction_of_light = new JSlider(JSlider.HORIZONTAL, 0, 360, ((int) (s.pps.ls.light_direction)));
        direction_of_light.setPreferredSize(new Dimension(200, 40));
        direction_of_light.setMajorTickSpacing(60);
        direction_of_light.setMinorTickSpacing(1);
        direction_of_light.setToolTipText("Sets the direction of light.");
        //color_blend.setPaintTicks(true);
        direction_of_light.setPaintLabels(true);
        //direction_of_light.setSnapToTicks(true);
        direction_of_light.setFocusable(false);

        JSlider depth = new JSlider(JSlider.HORIZONTAL, 0, 99, ((int) (s.pps.ls.light_magnitude * 100)));
        depth.setPreferredSize(new Dimension(200, 40));
        depth.setToolTipText("Sets the magnitude of light.");
        //color_blend.setPaintTicks(true);
        depth.setPaintLabels(true);
        //depth.setSnapToTicks(true);
        depth.setFocusable(false);

        Hashtable<Integer, JLabel> table3 = new Hashtable<>();
        table3.put(0, new JLabel("0.0"));
        table3.put(25, new JLabel("0.25"));
        table3.put(50, new JLabel("0.5"));
        table3.put(75, new JLabel("0.75"));
        table3.put(99, new JLabel("0.99"));
        depth.setLabelTable(table3);

        final JComboBox<String> light_mode_combo = new JComboBox<>(Constants.lightModes);
        light_mode_combo.setSelectedIndex(s.pps.ls.lightMode);
        light_mode_combo.setFocusable(false);
        light_mode_combo.setToolTipText("Sets the light mode.");

        final JComboBox<String> reflection_mode_combo = new JComboBox<>(Constants.reflectionModes);
        reflection_mode_combo.setSelectedIndex(s.pps.ls.specularReflectionMethod);
        reflection_mode_combo.setFocusable(false);
        reflection_mode_combo.setToolTipText("Sets the reflection mode.");

        JPanel p7 = new JPanel();
        p7.add(light_mode_combo);

        JPanel p10 = new JPanel();
        p10.add(reflection_mode_combo);


        p1.add(new JLabel("Direction:", SwingConstants.HORIZONTAL));
        p1.add(new JLabel("Magnitude:", SwingConstants.HORIZONTAL));
        p1.add(new JLabel("Light Mode:", SwingConstants.HORIZONTAL));
        p1.add(new JLabel("Reflection Mode:", SwingConstants.HORIZONTAL));
        p1.add(direction_of_light);
        p1.add(depth);
        p1.add(p7);
        p1.add(p10);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.pps.ls.l_noise_reducing_factor);

        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(2, 4));

        JTextField light_intensity_field = new JTextField(6);
        light_intensity_field.setText("" + s.pps.ls.lightintensity);

        JTextField ambient_light_field = new JTextField(6);
        ambient_light_field.setText("" + s.pps.ls.ambientlight);

        JTextField specular_intensity_field = new JTextField(6);
        specular_intensity_field.setText("" + s.pps.ls.specularintensity);

        JTextField shininess_field = new JTextField(6);
        shininess_field.setText("" + s.pps.ls.shininess);

        p2.add(new JLabel("Light Intensity:", SwingConstants.HORIZONTAL));
        p2.add(new JLabel("Ambient Light:", SwingConstants.HORIZONTAL));
        p2.add(new JLabel("Specular Intensity:", SwingConstants.HORIZONTAL));
        p2.add(new JLabel("Shininess:", SwingConstants.HORIZONTAL));

        JPanel p30 = new JPanel();
        p30.add(light_intensity_field);
        JPanel p31 = new JPanel();
        p31.add(ambient_light_field);
        JPanel p32 = new JPanel();
        p32.add(specular_intensity_field);
        JPanel p33 = new JPanel();
        p33.add(shininess_field);

        p2.add(p30);
        p2.add(p31);
        p2.add(p32);
        p2.add(p33);

        JPanel p3 = new JPanel();
        p3.setLayout(new GridLayout(2, 5));

        p3.add(new JLabel("Transfer Function:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Transfer Factor:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Fractional Transfer:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Transfer Mode:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Fractional Smoothing:", SwingConstants.HORIZONTAL));

        JPanel p15 = new JPanel();
        p15.setLayout(new GridLayout(2, 2));
        p15.add(new JLabel("Color Mode:", SwingConstants.HORIZONTAL));
        p15.add(new JLabel("Color Blending:", SwingConstants.HORIZONTAL));

        JComboBox<String> transfer_combo = new JComboBox<>(Constants.lightTransfer);
        transfer_combo.setSelectedIndex(s.pps.ls.heightTransfer);
        transfer_combo.setFocusable(false);
        transfer_combo.setToolTipText("Sets the height transfer function.");

        JTextField tranfer_factor_field = new JTextField(6);
        tranfer_factor_field.setText("" + s.pps.ls.heightTransferFactor);

        final JComboBox<String> color_method_combo = new JComboBox<>(Constants.colorMethod);
        color_method_combo.setSelectedIndex(s.pps.ls.colorMode);
        color_method_combo.setFocusable(false);
        color_method_combo.setToolTipText("Sets the color mode.");

        final JSlider color_blend_opt = new SliderGradient(JSlider.HORIZONTAL, 0, 100, (int) (s.pps.ls.light_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        color_method_combo.addActionListener(e -> color_blend_opt.setEnabled(color_method_combo.getSelectedIndex() == 3));

        final JComboBox<String> fractional_transfer = new JComboBox<>(Constants.fractionalTransfer);
        fractional_transfer.setSelectedIndex(s.pps.ls.fractionalTransfer);
        fractional_transfer.setFocusable(false);
        fractional_transfer.setToolTipText("Sets the fractional transfer function.");

        final JComboBox<String> fractional_transfer_mode = new JComboBox<>(Constants.fractionalTransferMode);
        fractional_transfer_mode.setSelectedIndex(s.pps.ls.fractionalTransferMode);
        fractional_transfer_mode.setFocusable(false);
        fractional_transfer_mode.setToolTipText("Sets the fractional transfer mode.");

        fractional_transfer_mode.setEnabled(fractional_transfer.getSelectedIndex() != 0);
        fractional_transfer.addActionListener(e -> fractional_transfer_mode.setEnabled(fractional_transfer.getSelectedIndex() != 0));

        final JComboBox<String> fractional_smoothing = new JComboBox<>(Constants.FadeAlgs);
        fractional_smoothing.setSelectedIndex(s.pps.ls.fractionalSmoothing);
        fractional_smoothing.setFocusable(false);
        fractional_smoothing.setToolTipText("Sets the fractional smoothing function.");

        JPanel p4 = new JPanel();
        p4.add(transfer_combo);
        JPanel p5 = new JPanel();
        p5.add(tranfer_factor_field);
        JPanel p6 = new JPanel();
        p6.add(color_method_combo);

        JPanel p20 = new JPanel();
        p20.add(fractional_transfer);
        JPanel p22 = new JPanel();
        p22.add(fractional_transfer_mode);
        JPanel p21 = new JPanel();
        p21.add(fractional_smoothing);

        p3.add(p4);
        p3.add(p5);
        p3.add(p20);
        p3.add(p22);
        p3.add(p21);

        JPanel p16 = new JPanel();
        p16.add(color_blend_opt);

        p15.add(p6);
        p15.add(p16);

        color_blend_opt.setEnabled(s.pps.ls.colorMode == 3);

        Object[] message = {
            " ",
            enable_light,
            " ",
            "Set the light direction, magnitude, light mode, and reflection mode.",
            " ", p1,
            " ",
            "Set the light properties.",
            p2,
            " ",
            "Set the height transfer and fractional transfer/smoothing.",
            p3,
            " ",
                "Set the color options.",
                p15,
                " ",
            "Set the image noise reduction factor.",
            "Noise Reduction Factor:",
            noise_factor_field,};

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
                            double temp2 = Double.parseDouble(light_intensity_field.getText());
                            double temp3 = Double.parseDouble(ambient_light_field.getText());
                            double temp4 = Double.parseDouble(specular_intensity_field.getText());
                            double temp5 = Double.parseDouble(shininess_field.getText());
                            double temp6 = Double.parseDouble(tranfer_factor_field.getText());

                            if (temp <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The noise reduction factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.pps.ls.lighting = enable_light.isSelected();
                            s.pps.ls.light_direction = direction_of_light.getValue();
                            s.pps.ls.light_magnitude = depth.getValue() / 100.0;
                            s.pps.ls.lightintensity = temp2;
                            s.pps.ls.ambientlight = temp3;
                            s.pps.ls.specularintensity = temp4;
                            s.pps.ls.shininess = temp5;
                            s.pps.ls.light_blending = color_blend_opt.getValue() / 100.0;
                            s.pps.ls.colorMode = color_method_combo.getSelectedIndex();
                            s.pps.ls.heightTransfer = transfer_combo.getSelectedIndex();
                            s.pps.ls.heightTransferFactor = temp6;
                            s.pps.ls.lightMode = light_mode_combo.getSelectedIndex();

                            double lightAngleRadians = Math.toRadians(s.pps.ls.light_direction);
                            s.pps.ls.lightVector[0] = Math.cos(lightAngleRadians) * s.pps.ls.light_magnitude;
                            s.pps.ls.lightVector[1] = Math.sin(lightAngleRadians) * s.pps.ls.light_magnitude;

                            s.pps.ls.l_noise_reducing_factor = temp;

                            s.pps.ls.specularReflectionMethod = reflection_mode_combo.getSelectedIndex();
                            TaskRender.loadWindowImage(s.pps.ls.specularReflectionMethod);
                            s.pps.ls.fractionalTransfer = fractional_transfer.getSelectedIndex();
                            s.pps.ls.fractionalSmoothing = fractional_smoothing.getSelectedIndex();
                            s.pps.ls.fractionalTransferMode = fractional_transfer_mode.getSelectedIndex();

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();

                        if (greedy_algorithm && !TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA && enable_light.isSelected() && !julia_map && !s.d3s.d3 && !s.ds.domain_coloring) {
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
