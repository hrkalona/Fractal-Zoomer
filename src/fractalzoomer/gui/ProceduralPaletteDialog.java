
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.CosinePaletteSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class ProceduralPaletteDialog extends JDialog {

    private ColorPaletteEditorDialog ptra;
    private JOptionPane optionPane;

    public ProceduralPaletteDialog(ColorPaletteEditorDialog ptr, int length, CosinePaletteSettings cps, int max_length, int step) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Procedural Palette");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());


        JTextField generated_palette_restart_field = new JTextField();
        generated_palette_restart_field.setText("" + length);

        JTextField step_field = new JTextField();
        step_field.setText("" + step);


//        final JComboBox<String> generated_palettes_combon = new JComboBox<>(new String[] {"IQ: A + B * cos(2 * pi * (C * t + D) + G)"});
//        generated_palettes_combon.setSelectedIndex(id);
//        generated_palettes_combon.setFocusable(false);
//        generated_palettes_combon.setToolTipText("Sets the procedural palette algorithm.");

        JPanel abcd_panel = new JPanel();
        abcd_panel.setLayout(new GridLayout(10, 3));
        abcd_panel.add(new JLabel("Red A:"));
        abcd_panel.add(new JLabel("Green A:"));
        abcd_panel.add(new JLabel("Blue A:"));

        JTextField reda = new JTextField(18);
        reda.setText("" + (cps.redA));

        JTextField greena = new JTextField(18);
        greena.setText("" + (cps.greenA));

        JTextField bluea = new JTextField(18);
        bluea.setText("" + (cps.blueA));

        abcd_panel.add(reda);
        abcd_panel.add(greena);
        abcd_panel.add(bluea);


        abcd_panel.add(new JLabel("Red B:"));
        abcd_panel.add(new JLabel("Green B:"));
        abcd_panel.add(new JLabel("Blue B:"));

        JTextField redb = new JTextField(18);
        redb.setText("" + (cps.redB));

        JTextField greenb = new JTextField(18);
        greenb.setText("" + (cps.greenB));

        JTextField blueb = new JTextField(18);
        blueb.setText("" + (cps.blueB));

        abcd_panel.add(redb);
        abcd_panel.add(greenb);
        abcd_panel.add(blueb);

        abcd_panel.add(new JLabel("Red C:"));
        abcd_panel.add(new JLabel("Green C:"));
        abcd_panel.add(new JLabel("Blue C:"));

        JTextField redc = new JTextField(18);
        redc.setText("" + (cps.redC));

        JTextField greenc = new JTextField(18);
        greenc.setText("" + (cps.greenC));

        JTextField bluec = new JTextField(18);
        bluec.setText("" + (cps.blueC));

        abcd_panel.add(redc);
        abcd_panel.add(greenc);
        abcd_panel.add(bluec);

        abcd_panel.add(new JLabel("Red D:"));
        abcd_panel.add(new JLabel("Green D:"));
        abcd_panel.add(new JLabel("Blue D:"));

        JTextField redd = new JTextField(18);
        redd.setText("" + (cps.redD));

        JTextField greend = new JTextField(18);
        greend.setText("" + (cps.greenD));

        JTextField blued = new JTextField(18);
        blued.setText("" + (cps.blueD));

        abcd_panel.add(redd);
        abcd_panel.add(greend);
        abcd_panel.add(blued);


        abcd_panel.add(new JLabel("Red G:"));
        abcd_panel.add(new JLabel("Green G:"));
        abcd_panel.add(new JLabel("Blue G:"));

        JTextField redg = new JTextField(18);
        redg.setText("" + (cps.redG));

        JTextField greeng = new JTextField(18);
        greeng.setText("" + (cps.greenG));

        JTextField blueg = new JTextField(18);
        blueg.setText("" + (cps.blueG));

        abcd_panel.add(redg);
        abcd_panel.add(greeng);
        abcd_panel.add(blueg);

        Object[] message = {
                " ",
                "IQ: A + B * cos(2 * pi * (C * t + D) + G)",
                " ",
            "Maximum Palette Length:", generated_palette_restart_field,
                " ",
                "Step:", step_field,
                " ",
                "Parameters:",
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

                        int new_length;
                        int new_step;

                        try {
                            int temp2 = Integer.parseInt(generated_palette_restart_field.getText());
                            int temp = Integer.parseInt(step_field.getText());

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
                                JOptionPane.showMessageDialog(ptra, "The maximum palette length value must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp2 > max_length) {
                                JOptionPane.showMessageDialog(ptra, "The maximum palette length value must be lower than " + (max_length + 1) + ".", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp <= 0 || temp > 50) {
                                JOptionPane.showMessageDialog(ptra, "The step must be between [1, 50].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp > temp2) {
                                JOptionPane.showMessageDialog(ptra, "The maximum palette length must be greater or equal than the step.", "Error!", JOptionPane.ERROR_MESSAGE);
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


                            cps.redA = temp3;
                            cps.greenA = temp4;
                            cps.blueA = temp5;

                            cps.redB = temp6;
                            cps.greenB = temp7;
                            cps.blueB = temp8;

                            cps.redC = temp9;
                            cps.greenC = temp10;
                            cps.blueC = temp11;

                            cps.redD = temp12;
                            cps.greenD = temp13;
                            cps.blueD = temp14;

                            cps.redG = temp15;
                            cps.greenG = temp16;
                            cps.blueG = temp17;

                            new_length = temp2;
                            new_step = temp;

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.setProceduralPalettePost(new_length, cps, new_step);
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
