
package fractalzoomer.gui;

import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.GeneratedPaletteSettings;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.utils.Multiwave;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static fractalzoomer.main.app_settings.GeneratedPaletteSettings.DEFAULT_LARGE_LENGTH;

/**
 *
 * @author hrkalona2
 */
public class GeneratedPaletteDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;
    private GeneratedPaletteSettings currentGps;

    public GeneratedPaletteDialog(MainWindow ptr, Settings s, boolean outcoloring) {
        
        super(ptr);

        ptra = ptr;
        currentGps = new GeneratedPaletteSettings(s.gps);

        setTitle("Generated Palette");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());


        JTextField generated_palette_restart_field = new JTextField();
        generated_palette_restart_field.setText("" + (outcoloring ? s.gps.restartGeneratedOutColoringPaletteAt : s.gps.restartGeneratedInColoringPaletteAt));

        JTextField generated_palette_offset_field = new JTextField();
        generated_palette_offset_field.setText("" + (outcoloring ? s.gps.GeneratedOutColoringPaletteOffset : s.gps.GeneratedInColoringPaletteOffset));

        JTextField generated_palette_factor_field = new JTextField();
        generated_palette_factor_field.setText("" + (outcoloring ? s.gps.GeneratedOutColoringPaletteFactor : s.gps.GeneratedInColoringPaletteFactor));


        final JCheckBox enable_generated_palette = new JCheckBox("Generated Palette");
        enable_generated_palette.setSelected(outcoloring ? s.gps.useGeneratedPaletteOutColoring : s.gps.useGeneratedPaletteInColoring);
        enable_generated_palette.setFocusable(false);

        final JCheckBox blend_generated_and_normal_palette = new JCheckBox("Blend Normal and Generated Palettes");
        blend_generated_and_normal_palette.setSelected(outcoloring ? s.gps.blendNormalPaletteWithGeneratedPaletteOutColoring : s.gps.blendNormalPaletteWithGeneratedPaletteInColoring);
        blend_generated_and_normal_palette.setFocusable(false);

        JSlider color_blend_opt = new SliderGradient(JSlider.HORIZONTAL, 0, 100, (int) (outcoloring ? (s.gps.blendingOutColoring * 100) : (s.gps.blendingInColoring * 100)));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        final JComboBox<String> generated_palettes_combon = new JComboBox<>(Constants.generatedPalettes);
        generated_palettes_combon.setSelectedIndex(outcoloring ? s.gps.generatedPaletteOutColoringId : s.gps.generatedPaletteInColoringId);
        generated_palettes_combon.setFocusable(false);
        generated_palettes_combon.setToolTipText("Sets the generated palette algorithm.");

        color_blend_opt.setEnabled(blend_generated_and_normal_palette.isSelected());
        blend_generated_and_normal_palette.addActionListener(e -> color_blend_opt.setEnabled(blend_generated_and_normal_palette.isSelected()));

        JPanel buttons_panel = new JPanel();

        JButton multiwave_edit = new MyButton("Multiwave Palette");
        multiwave_edit.setIcon(MainWindow.getIcon("multiwave.png"));
        multiwave_edit.setFocusable(false);
        multiwave_edit.setEnabled(generated_palettes_combon.getSelectedIndex() == 4);
        multiwave_edit.addActionListener(e -> new MultiwaveDialog(this, outcoloring, currentGps));

        JButton iq_edit = new MyButton("IQ Cosine Palette");
        iq_edit.setIcon(MainWindow.getIcon("sine.png"));
        iq_edit.setFocusable(false);
        iq_edit.setEnabled(generated_palettes_combon.getSelectedIndex() == 3);
        iq_edit.addActionListener(e -> new IQPaletteDialog(this, outcoloring, currentGps));

        buttons_panel.add(iq_edit);
        buttons_panel.add(multiwave_edit);

        JPanel buttons_panel2 = new JPanel();
        JButton infinitewave_edit = new MyButton("Infinite Waves (KF) Palette");
        infinitewave_edit.setIcon(MainWindow.getIcon("multiwave.png"));
        infinitewave_edit.setFocusable(false);
        infinitewave_edit.setEnabled(generated_palettes_combon.getSelectedIndex() == 5);
        infinitewave_edit.addActionListener(e -> new InfiniteWaveDialog(this, outcoloring, currentGps));
        buttons_panel2.add(infinitewave_edit);

        JButton simple_multiwave_edit = new MyButton("Simple Multiwave Palette");
        simple_multiwave_edit.setIcon(MainWindow.getIcon("multiwave.png"));
        simple_multiwave_edit.setFocusable(false);
        simple_multiwave_edit.setEnabled(generated_palettes_combon.getSelectedIndex() == 6);
        simple_multiwave_edit.addActionListener(e -> new SimpleMultiwaveDialog(this, outcoloring, currentGps));
        buttons_panel2.add(simple_multiwave_edit);

        generated_palettes_combon.addActionListener(e -> {
            iq_edit.setEnabled(generated_palettes_combon.getSelectedIndex() == 3);
            multiwave_edit.setEnabled(generated_palettes_combon.getSelectedIndex() == 4);
            infinitewave_edit.setEnabled(generated_palettes_combon.getSelectedIndex() == 5);
            simple_multiwave_edit.setEnabled(generated_palettes_combon.getSelectedIndex() == 6);
            if(generated_palettes_combon.getSelectedIndex() == 3) {
                try {
                    int length = Integer.parseInt(generated_palette_restart_field.getText());

                    if(length == DEFAULT_LARGE_LENGTH) {
                        generated_palette_restart_field.setText("" + GeneratedPaletteSettings.DEFAULT_SMALL_LENGTH);
                    }
                }
                catch (Exception ex) {

                }
            }
        });

        JPanel panel2 = new JPanel();

        JButton overview = new MyButton("Preview");
        overview.setIcon(MainWindow.getIcon("preview.png"));
        overview.setFocusable(false);
        overview.addActionListener(e -> {
            try {
                int temp2 = Integer.parseInt(generated_palette_restart_field.getText());
                int temp3 = Integer.parseInt(generated_palette_offset_field.getText());
                double temp4 = Double.parseDouble(generated_palette_factor_field.getText());

                if (temp2 < 0) {
                    JOptionPane.showMessageDialog(ptra, "The generated palette length value must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (temp2 > 2100000000) {
                    JOptionPane.showMessageDialog(ptra, "The generated palette length value must be lower than 2100000001.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(temp3 < 0) {
                    JOptionPane.showMessageDialog(ptra, "The generated palette offset value must be greater or equal to 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(temp4 <= 0) {
                    JOptionPane.showMessageDialog(ptra, "The generated palette scale factor value must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                new GeneratedPaletteOverviewDialog(this, outcoloring, currentGps, generated_palettes_combon.getSelectedIndex(), temp2, temp3, temp4);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel2.add(overview);

        Object[] message = {
            " ",
                enable_generated_palette,
                blend_generated_and_normal_palette,
                " ",
                "Color Blending:",
                color_blend_opt,
                " ",
                "Set the generated palette algorithm.",
                "Generated Palette algorithm:", generated_palettes_combon,
                buttons_panel,
                buttons_panel2,
            " ",
            "Set the Palette Length and Offset and Scale Factor.",
            "Length:", generated_palette_restart_field,
                "Offset:", generated_palette_offset_field,
                "Scale Factor:", generated_palette_factor_field,
                " ",
                panel2,
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
                            int temp2 = Integer.parseInt(generated_palette_restart_field.getText());
                            int temp3 = Integer.parseInt(generated_palette_offset_field.getText());
                            double temp4 = Double.parseDouble(generated_palette_factor_field.getText());

                            if (temp2 < 0) {
                                JOptionPane.showMessageDialog(ptra, "The generated palette length value must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp2 > DEFAULT_LARGE_LENGTH) {
                                JOptionPane.showMessageDialog(ptra, "The generated palette length value must be lower than 2100000001.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp3 < 0) {
                                JOptionPane.showMessageDialog(ptra, "The generated palette offset value must be greater or equal to 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp4 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The generated palette scale factor value must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.gps = currentGps;

                            if(outcoloring) {
                                s.gps.useGeneratedPaletteOutColoring = enable_generated_palette.isSelected();
                                s.gps.restartGeneratedOutColoringPaletteAt = temp2;
                                s.gps.generatedPaletteOutColoringId = generated_palettes_combon.getSelectedIndex();
                                s.gps.blendNormalPaletteWithGeneratedPaletteOutColoring = blend_generated_and_normal_palette.isSelected();
                                s.gps.blendingOutColoring = color_blend_opt.getValue() / 100.0;
                                s.gps.GeneratedOutColoringPaletteOffset = temp3;
                                s.gps.GeneratedOutColoringPaletteFactor = temp4;
                            }
                            else {
                                s.gps.useGeneratedPaletteInColoring = enable_generated_palette.isSelected();
                                s.gps.restartGeneratedInColoringPaletteAt = temp2;
                                s.gps.generatedPaletteInColoringId = generated_palettes_combon.getSelectedIndex();
                                s.gps.blendNormalPaletteWithGeneratedPaletteInColoring = blend_generated_and_normal_palette.isSelected();
                                s.gps.blendingInColoring = color_blend_opt.getValue() / 100.0;
                                s.gps.GeneratedInColoringPaletteOffset = temp3;
                                s.gps.GeneratedInColoringPaletteFactor = temp4;
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.setGeneratedPalettePost(outcoloring);
                    }
                });

        //Make this dialog display it.
        setContentPane(optionPane);

        pack();

        setResizable(false);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }

    public void setParams(GeneratedPaletteSettings gps) {
        currentGps = gps;
    }
}
