
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
public class BlinnLightDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public BlinnLightDialog(MainWindow ptr, Settings s, boolean greedy_algorithm, boolean julia_map) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Blinn-Phong Light");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        final JCheckBox enable_light = new JCheckBox("Blinn-Phong Light");
        enable_light.setSelected(s.pps.bls.lighting);
        enable_light.setFocusable(false);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 2));

        JSlider polar_angle = new JSlider(JSlider.HORIZONTAL, 0, 360, ((int) (s.pps.bls.polarAngle)));
        polar_angle.setPreferredSize(new Dimension(200, 40));
        polar_angle.setMajorTickSpacing(60);
        polar_angle.setMinorTickSpacing(1);
        polar_angle.setToolTipText("Sets the polar angle.");
        polar_angle.setPaintLabels(true);
        polar_angle.setFocusable(false);

        JSlider azimuth_angle = new JSlider(JSlider.HORIZONTAL, 0, 360, ((int) (s.pps.bls.azimuthAngle)));
        azimuth_angle.setPreferredSize(new Dimension(200, 40));
        azimuth_angle.setMajorTickSpacing(60);
        azimuth_angle.setMinorTickSpacing(1);
        azimuth_angle.setToolTipText("Sets the azimuth angle.");
        azimuth_angle.setPaintLabels(true);
        azimuth_angle.setFocusable(false);


        JTextField shininess = new JTextField(6);
        shininess.setText("" + s.pps.bls.shininess);
        JPanel pshininess = new JPanel();
        pshininess.add(shininess);

        JTextField ambient = new JTextField(6);
        ambient.setText("" + s.pps.bls.ambient);
        JPanel pambient = new JPanel();
        pambient.add(ambient);


        p1.add(new JLabel("Polar Angle:", SwingConstants.HORIZONTAL));
        p1.add(new JLabel("Azimuth Angle:", SwingConstants.HORIZONTAL));
        p1.add(polar_angle);
        p1.add(azimuth_angle);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.pps.bls.bls_noise_reducing_factor);

        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(2, 3));

        JPanel pambcolor = new JPanel();

        JTextField ambientColor1 = new JTextField();
        ambientColor1.setText("" + s.pps.bls.colorAmbient[0]);
        JTextField ambientColor2 = new JTextField();
        ambientColor2.setText("" + s.pps.bls.colorAmbient[1]);
        JTextField ambientColor3 = new JTextField();
        ambientColor3.setText("" + s.pps.bls.colorAmbient[2]);

        pambcolor.add(ambientColor1);
        pambcolor.add(ambientColor2);
        pambcolor.add(ambientColor3);

        JPanel p3 = new JPanel();
        p3.setLayout(new GridLayout(2, 4));

        JPanel pspeccolor = new JPanel();

        JTextField specular1 = new JTextField();
        specular1.setText("" + s.pps.bls.specular[0]);
        JTextField specular2 = new JTextField();
        specular2.setText("" + s.pps.bls.specular[1]);
        JTextField specular3 = new JTextField();
        specular3.setText("" + s.pps.bls.specular[2]);

        pspeccolor.add(specular1);
        pspeccolor.add(specular2);
        pspeccolor.add(specular3);

        JPanel pdiffuse = new JPanel();

        JTextField diffuse1 = new JTextField();
        diffuse1.setText("" + s.pps.bls.diffuse[0]);
        JTextField diffuse2 = new JTextField();
        diffuse2.setText("" + s.pps.bls.diffuse[1]);
        JTextField diffuse3 = new JTextField();
        diffuse3.setText("" + s.pps.bls.diffuse[2]);

        pdiffuse.add(diffuse1);
        pdiffuse.add(diffuse2);
        pdiffuse.add(diffuse3);

        JPanel pcolor = new JPanel();

        JTextField color1 = new JTextField();
        color1.setText("" + s.pps.bls.color[0]);
        JTextField color2 = new JTextField();
        color2.setText("" + s.pps.bls.color[1]);
        JTextField color3 = new JTextField();
        color3.setText("" + s.pps.bls.color[2]);

        pcolor.add(color1);
        pcolor.add(color2);
        pcolor.add(color3);

        JPanel pmatspeccolor = new JPanel();

        JTextField speccolor1 = new JTextField();
        speccolor1.setText("" +  (s.pps.bls.materialSpecularColor[0] != null ? s.pps.bls.materialSpecularColor[0] : ""));
        JTextField speccolor2 = new JTextField();
        speccolor2.setText("" +  (s.pps.bls.materialSpecularColor[1] != null ? s.pps.bls.materialSpecularColor[1] : ""));
        JTextField speccolor3 = new JTextField();
        speccolor3.setText("" + (s.pps.bls.materialSpecularColor[2] != null ? s.pps.bls.materialSpecularColor[2] : ""));

        pmatspeccolor.add(speccolor1);
        pmatspeccolor.add(speccolor2);
        pmatspeccolor.add(speccolor3);

        p3.add(new JLabel("Specular:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Diffuse:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Color:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Material Specular Color:", SwingConstants.HORIZONTAL));
        p3.add(pspeccolor);
        p3.add(pdiffuse);
        p3.add(pcolor);
        p3.add(pmatspeccolor);

        p2.add(new JLabel("Shininess:", SwingConstants.HORIZONTAL));
        p2.add(new JLabel("Ambient:", SwingConstants.HORIZONTAL));
        p2.add(new JLabel("Ambient Color:", SwingConstants.HORIZONTAL));


        p2.add(pshininess);
        p2.add(pambient);
        p2.add(pambcolor);


        JPanel p40 = new JPanel();
        p40.setLayout(new GridLayout(2, 5));

        p40.add(new JLabel("Transfer Function:", SwingConstants.HORIZONTAL));
        p40.add(new JLabel("Transfer Factor:", SwingConstants.HORIZONTAL));
        p40.add(new JLabel("Fractional Transfer:", SwingConstants.HORIZONTAL));
        p40.add(new JLabel("Transfer Mode:", SwingConstants.HORIZONTAL));
        p40.add(new JLabel("Fractional Smoothing:", SwingConstants.HORIZONTAL));

        JComboBox<String> transfer_combo = new JComboBox<>(Constants.lightTransfer);
        transfer_combo.setSelectedIndex(s.pps.bls.heightTransfer);
        transfer_combo.setFocusable(false);
        transfer_combo.setToolTipText("Sets the height transfer function.");

        JTextField tranfer_factor_field = new JTextField(6);
        tranfer_factor_field.setText("" + s.pps.bls.heightTransferFactor);

        final JComboBox<String> fractional_transfer = new JComboBox<>(Constants.fractionalTransfer);
        fractional_transfer.setSelectedIndex(s.pps.bls.fractionalTransfer);
        fractional_transfer.setFocusable(false);
        fractional_transfer.setToolTipText("Sets the fractional transfer function.");

        final JComboBox<String> fractional_transfer_mode = new JComboBox<>(Constants.fractionalTransferMode);
        fractional_transfer_mode.setSelectedIndex(s.pps.bls.fractionalTransferMode);
        fractional_transfer_mode.setFocusable(false);
        fractional_transfer_mode.setToolTipText("Sets the fractional transfer mode.");

        fractional_transfer_mode.setEnabled(fractional_transfer.getSelectedIndex() != 0);
        fractional_transfer.addActionListener(e -> fractional_transfer_mode.setEnabled(fractional_transfer.getSelectedIndex() != 0));

        final JComboBox<String> fractional_smoothing = new JComboBox<>(Constants.FadeAlgs);
        fractional_smoothing.setSelectedIndex(s.pps.bls.fractionalSmoothing);
        fractional_smoothing.setFocusable(false);
        fractional_smoothing.setToolTipText("Sets the fractional smoothing function.");

        JPanel p4 = new JPanel();
        p4.add(transfer_combo);
        JPanel p5 = new JPanel();
        p5.add(tranfer_factor_field);

        JPanel p20 = new JPanel();
        p20.add(fractional_transfer);
        JPanel p22 = new JPanel();
        p22.add(fractional_transfer_mode);
        JPanel p21 = new JPanel();
        p21.add(fractional_smoothing);

        p40.add(p4);
        p40.add(p5);
        p40.add(p20);
        p40.add(p22);
        p40.add(p21);

        Object[] message = {
            " ",
            enable_light,
            " ",
            "Set the light direction.",
            " ", p1,
            " ",
            "Set the light properties.",
            p2, p3,
            " ",
            "Set the height transfer and fractional transfer/smoothing.",
            p40,
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
                            double temp6 = Double.parseDouble(tranfer_factor_field.getText());
                            double temp2 = Double.parseDouble(ambientColor1.getText());
                            double temp3 = Double.parseDouble(ambientColor2.getText());
                            double temp4 = Double.parseDouble(ambientColor3.getText());

                            double temp7 = Double.parseDouble(specular1.getText());
                            double temp8 = Double.parseDouble(specular2.getText());
                            double temp9 = Double.parseDouble(specular3.getText());

                            double temp10 = Double.parseDouble(diffuse1.getText());
                            double temp11 = Double.parseDouble(diffuse2.getText());
                            double temp12 = Double.parseDouble(diffuse3.getText());

                            double temp13 = Double.parseDouble(color1.getText());
                            double temp14 = Double.parseDouble(color2.getText());
                            double temp15 = Double.parseDouble(color3.getText());

                            double temp16 = Double.NaN;
                            double temp17 = Double.NaN;
                            double temp18 = Double.NaN;
                            if (!speccolor1.getText().isEmpty()) {
                                temp16 = Double.parseDouble(speccolor1.getText());
                            }
                            if (!speccolor2.getText().isEmpty()) {
                                temp17 = Double.parseDouble(speccolor2.getText());
                            }
                            if (!speccolor3.getText().isEmpty()) {
                                temp18 = Double.parseDouble(speccolor3.getText());
                            }

                            double temp19 = Double.parseDouble(ambient.getText());
                            double temp20 = Double.parseDouble(shininess.getText());

                            if (temp <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The noise reduction factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp2 <= 0 || temp3 <= 0 || temp4 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "Ambient color values must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp7 <= 0 || temp8 <= 0 || temp9 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "Specular values must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp10 <= 0 || temp11 <= 0 || temp12 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "Diffuse values must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp13 <= 0 || temp14 <= 0 || temp15 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "Color values must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if ((!Double.isNaN(temp16) && temp16 <= 0) || (!Double.isNaN(temp17) && temp17 <= 0) || (!Double.isNaN(temp18) && temp18 <= 0)) {
                                JOptionPane.showMessageDialog(ptra, "Material specular color values must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp19 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "Ambient value must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp20 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "Shininess value must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.pps.bls.lighting = enable_light.isSelected();
                            s.pps.bls.polarAngle = polar_angle.getValue();
                            s.pps.bls.azimuthAngle = azimuth_angle.getValue();

                            s.pps.bls.ambient = temp19;
                            s.pps.bls.shininess = temp20;

                            s.pps.bls.colorAmbient[0] = temp2;
                            s.pps.bls.colorAmbient[1] = temp3;
                            s.pps.bls.colorAmbient[2] = temp4;

                            s.pps.bls.specular[0] = temp7;
                            s.pps.bls.specular[1] = temp8;
                            s.pps.bls.specular[2] = temp9;

                            s.pps.bls.diffuse[0] = temp10;
                            s.pps.bls.diffuse[1] = temp11;
                            s.pps.bls.diffuse[2] = temp12;

                            s.pps.bls.color[0] = temp13;
                            s.pps.bls.color[1] = temp14;
                            s.pps.bls.color[2] = temp15;

                            s.pps.bls.materialSpecularColor[0] = Double.isNaN(temp16) ? null : temp16;
                            s.pps.bls.materialSpecularColor[1] = Double.isNaN(temp17) ? null : temp17;
                            s.pps.bls.materialSpecularColor[2] = Double.isNaN(temp18) ? null : temp18;

                            s.pps.bls.heightTransfer = transfer_combo.getSelectedIndex();
                            s.pps.bls.heightTransferFactor = temp6;

                            s.pps.bls.bls_noise_reducing_factor = temp;

                            s.pps.bls.fractionalTransfer = fractional_transfer.getSelectedIndex();
                            s.pps.bls.fractionalSmoothing = fractional_smoothing.getSelectedIndex();
                            s.pps.bls.fractionalTransferMode = fractional_transfer_mode.getSelectedIndex();

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
