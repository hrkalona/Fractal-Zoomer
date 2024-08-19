
package fractalzoomer.gui;

import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.GeneratedPaletteSettings;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class GeneratedPaletteDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public GeneratedPaletteDialog(MainWindow ptr, Settings s, boolean outcoloring) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Generated Palette");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());


        JTextField generated_palette_restart_field = new JTextField();
        generated_palette_restart_field.setText("" + (outcoloring ? s.gps.restartGeneratedOutColoringPaletteAt : s.gps.restartGeneratedInColoringPaletteAt));

        final JCheckBox enable_generated_palette = new JCheckBox("Generated Palette");
        enable_generated_palette.setSelected(outcoloring ? s.gps.useGeneratedPaletteOutColoring : s.gps.useGeneratedPaletteInColoring);
        enable_generated_palette.setFocusable(false);

        final JComboBox<String> generated_palettes_combon = new JComboBox<>(Constants.generatedPalettes);
        generated_palettes_combon.setSelectedIndex(outcoloring ? s.gps.generatedPaletteOutColoringId : s.gps.generatedPaletteInColoringId);
        generated_palettes_combon.setFocusable(false);
        generated_palettes_combon.setToolTipText("Sets the generated palette algorithm.");

        JPanel abcd_panel = new JPanel();
        abcd_panel.setLayout(new GridLayout(10, 3));
        abcd_panel.add(new JLabel("Red A:"));
        abcd_panel.add(new JLabel("Green A:"));
        abcd_panel.add(new JLabel("Blue A:"));

        JTextField reda = new JTextField(18);
        reda.setText("" + (outcoloring ? s.gps.outColoringIQ.redA : s.gps.inColoringIQ.redA));

        JTextField greena = new JTextField(18);
        greena.setText("" + (outcoloring ? s.gps.outColoringIQ.greenA : s.gps.inColoringIQ.greenA));

        JTextField bluea = new JTextField(18);
        bluea.setText("" + (outcoloring ? s.gps.outColoringIQ.blueA : s.gps.inColoringIQ.blueA));

        abcd_panel.add(reda);
        abcd_panel.add(greena);
        abcd_panel.add(bluea);


        abcd_panel.add(new JLabel("Red B:"));
        abcd_panel.add(new JLabel("Green B:"));
        abcd_panel.add(new JLabel("Blue B:"));

        JTextField redb = new JTextField(18);
        redb.setText("" + (outcoloring ? s.gps.outColoringIQ.redB : s.gps.inColoringIQ.redB));

        JTextField greenb = new JTextField(18);
        greenb.setText("" + (outcoloring ? s.gps.outColoringIQ.greenB : s.gps.inColoringIQ.greenB));

        JTextField blueb = new JTextField(18);
        blueb.setText("" + (outcoloring ? s.gps.outColoringIQ.blueB : s.gps.inColoringIQ.blueB));

        abcd_panel.add(redb);
        abcd_panel.add(greenb);
        abcd_panel.add(blueb);

        abcd_panel.add(new JLabel("Red C:"));
        abcd_panel.add(new JLabel("Green C:"));
        abcd_panel.add(new JLabel("Blue C:"));

        JTextField redc = new JTextField(18);
        redc.setText("" + (outcoloring ? s.gps.outColoringIQ.redC : s.gps.inColoringIQ.redC));

        JTextField greenc = new JTextField(18);
        greenc.setText("" + (outcoloring ? s.gps.outColoringIQ.greenC : s.gps.inColoringIQ.greenC));

        JTextField bluec = new JTextField(18);
        bluec.setText("" + (outcoloring ? s.gps.outColoringIQ.blueC : s.gps.inColoringIQ.blueC));

        abcd_panel.add(redc);
        abcd_panel.add(greenc);
        abcd_panel.add(bluec);

        abcd_panel.add(new JLabel("Red D:"));
        abcd_panel.add(new JLabel("Green D:"));
        abcd_panel.add(new JLabel("Blue D:"));

        JTextField redd = new JTextField(18);
        redd.setText("" + (outcoloring ? s.gps.outColoringIQ.redD : s.gps.inColoringIQ.redD));

        JTextField greend = new JTextField(18);
        greend.setText("" + (outcoloring ? s.gps.outColoringIQ.greenD : s.gps.inColoringIQ.greenD));

        JTextField blued = new JTextField(18);
        blued.setText("" + (outcoloring ? s.gps.outColoringIQ.blueD : s.gps.inColoringIQ.blueD));

        abcd_panel.add(redd);
        abcd_panel.add(greend);
        abcd_panel.add(blued);

        abcd_panel.add(new JLabel("Red G:"));
        abcd_panel.add(new JLabel("Green G:"));
        abcd_panel.add(new JLabel("Blue G:"));

        JTextField redg = new JTextField(18);
        redg.setText("" + (outcoloring ? s.gps.outColoringIQ.redG : s.gps.inColoringIQ.redG));

        JTextField greeng = new JTextField(18);
        greeng.setText("" + (outcoloring ? s.gps.outColoringIQ.greenG : s.gps.inColoringIQ.greenG));

        JTextField blueg = new JTextField(18);
        blueg.setText("" + (outcoloring ? s.gps.outColoringIQ.blueG : s.gps.inColoringIQ.blueG));

        abcd_panel.add(redg);
        abcd_panel.add(greeng);
        abcd_panel.add(blueg);

        for(Component c : abcd_panel.getComponents()) {
            c.setEnabled(generated_palettes_combon.getSelectedIndex() == 3);
        }

        generated_palettes_combon.addActionListener(e -> {for(Component c : abcd_panel.getComponents()) {
            c.setEnabled(generated_palettes_combon.getSelectedIndex() == 3);

            if(generated_palettes_combon.getSelectedIndex() == 3) {
                try {
                    int length = Integer.parseInt(generated_palette_restart_field.getText());

                    if(length == GeneratedPaletteSettings.DEFAULT_LARGE_LENGTH) {
                        generated_palette_restart_field.setText("" + GeneratedPaletteSettings.DEFAULT_SMALL_LENGTH);
                    }
                }
                catch (Exception ex) {

                }
            }
        }});

        Object[] message = {
            " ",
                enable_generated_palette,
                " ",
                "Set the generated palette algorithm.",
                "Generated Palette algorithm:", generated_palettes_combon,
            " ",
            "Set the Palette Length.",
            "Palette Length:", generated_palette_restart_field,
                " ",
                "Additional Options:",
                abcd_panel,
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

                            double temp3 = Double.parseDouble(reda.getText());
                            double temp4 = Double.parseDouble(greena.getText());
                            double temp5 = Double.parseDouble(bluea.getText());

                            double temp6 = Double.parseDouble(redb.getText());
                            double temp7 = Double.parseDouble(greenb.getText());
                            double temp8 = Double.parseDouble(blueb.getText());

                            double temp9 = Double.parseDouble(redc.getText());
                            double temp10 = Double.parseDouble(greenc.getText());
                            double temp11 = Double.parseDouble(bluec.getText());

                            double temp12 = Double.parseDouble(redd.getText());
                            double temp13 = Double.parseDouble(greend.getText());
                            double temp14 = Double.parseDouble(blued.getText());

                            double temp15 = Double.parseDouble(redg.getText());
                            double temp16 = Double.parseDouble(greeng.getText());
                            double temp17 = Double.parseDouble(blueg.getText());

                            if (temp2 < 0) {
                                JOptionPane.showMessageDialog(ptra, "The generated palette length value must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp2 > 2100000000) {
                                JOptionPane.showMessageDialog(ptra, "The generated palette length value must be lower than 2100000001.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp3 < 0 || temp4 < 0 || temp5 < 0 || temp6 < 0 || temp7 < 0 || temp8 < 0) {
                                JOptionPane.showMessageDialog(ptra, "The A and B values must be greater or equal to 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp3 + temp6 > 1 || temp4 + temp7 > 1 || temp5 + temp8 > 1) {
                                JOptionPane.showMessageDialog(ptra, "The sum of A and B (A + B) must be less or equal to 1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }


                            if(outcoloring) {
                                s.gps.useGeneratedPaletteOutColoring = enable_generated_palette.isSelected();
                                s.gps.restartGeneratedOutColoringPaletteAt = temp2;
                                s.gps.generatedPaletteOutColoringId = generated_palettes_combon.getSelectedIndex();

                                s.gps.outColoringIQ.redA = temp3;
                                s.gps.outColoringIQ.greenA = temp4;
                                s.gps.outColoringIQ.blueA = temp5;

                                s.gps.outColoringIQ.redB = temp6;
                                s.gps.outColoringIQ.greenB = temp7;
                                s.gps.outColoringIQ.blueB = temp8;

                                s.gps.outColoringIQ.redC = temp9;
                                s.gps.outColoringIQ.greenC = temp10;
                                s.gps.outColoringIQ.blueC = temp11;

                                s.gps.outColoringIQ.redD = temp12;
                                s.gps.outColoringIQ.greenD = temp13;
                                s.gps.outColoringIQ.blueD = temp14;

                                s.gps.outColoringIQ.redG = temp15;
                                s.gps.outColoringIQ.greenG = temp16;
                                s.gps.outColoringIQ.blueG = temp17;
                            }
                            else {
                                s.gps.useGeneratedPaletteInColoring = enable_generated_palette.isSelected();
                                s.gps.restartGeneratedInColoringPaletteAt = temp2;
                                s.gps.generatedPaletteInColoringId = generated_palettes_combon.getSelectedIndex();

                                s.gps.inColoringIQ.redA = temp3;
                                s.gps.inColoringIQ.greenA = temp4;
                                s.gps.inColoringIQ.blueA = temp5;

                                s.gps.inColoringIQ.redB = temp6;
                                s.gps.inColoringIQ.greenB = temp7;
                                s.gps.inColoringIQ.blueB = temp8;

                                s.gps.inColoringIQ.redC = temp9;
                                s.gps.inColoringIQ.greenC = temp10;
                                s.gps.inColoringIQ.blueC = temp11;

                                s.gps.inColoringIQ.redD = temp12;
                                s.gps.inColoringIQ.greenD = temp13;
                                s.gps.inColoringIQ.blueD = temp14;

                                s.gps.inColoringIQ.redG = temp15;
                                s.gps.inColoringIQ.greenG = temp16;
                                s.gps.inColoringIQ.blueG = temp17;
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.setGeneratedPalettePost();
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
