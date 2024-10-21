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

public class IQPaletteDialog extends JDialog {

    private GeneratedPaletteDialog ptra;
    private JOptionPane optionPane;

    public IQPaletteDialog(GeneratedPaletteDialog ptr, boolean outcoloring, GeneratedPaletteSettings gps) {

        super(ptr);

        ptra = ptr;

        setTitle("Inigo Quilez Cosine Palette");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());


        JPanel abcd_panel = new JPanel();
        abcd_panel.setLayout(new GridLayout(10, 3));
        abcd_panel.add(new JLabel("Red A:"));
        abcd_panel.add(new JLabel("Green A:"));
        abcd_panel.add(new JLabel("Blue A:"));

        JTextField reda = new JTextField(18);
        reda.setText("" + (outcoloring ? gps.outColoringIQ.redA : gps.inColoringIQ.redA));

        JTextField greena = new JTextField(18);
        greena.setText("" + (outcoloring ? gps.outColoringIQ.greenA : gps.inColoringIQ.greenA));

        JTextField bluea = new JTextField(18);
        bluea.setText("" + (outcoloring ? gps.outColoringIQ.blueA : gps.inColoringIQ.blueA));

        abcd_panel.add(reda);
        abcd_panel.add(greena);
        abcd_panel.add(bluea);


        abcd_panel.add(new JLabel("Red B:"));
        abcd_panel.add(new JLabel("Green B:"));
        abcd_panel.add(new JLabel("Blue B:"));

        JTextField redb = new JTextField(18);
        redb.setText("" + (outcoloring ? gps.outColoringIQ.redB : gps.inColoringIQ.redB));

        JTextField greenb = new JTextField(18);
        greenb.setText("" + (outcoloring ? gps.outColoringIQ.greenB : gps.inColoringIQ.greenB));

        JTextField blueb = new JTextField(18);
        blueb.setText("" + (outcoloring ? gps.outColoringIQ.blueB : gps.inColoringIQ.blueB));

        abcd_panel.add(redb);
        abcd_panel.add(greenb);
        abcd_panel.add(blueb);

        abcd_panel.add(new JLabel("Red C:"));
        abcd_panel.add(new JLabel("Green C:"));
        abcd_panel.add(new JLabel("Blue C:"));

        JTextField redc = new JTextField(18);
        redc.setText("" + (outcoloring ? gps.outColoringIQ.redC : gps.inColoringIQ.redC));

        JTextField greenc = new JTextField(18);
        greenc.setText("" + (outcoloring ? gps.outColoringIQ.greenC : gps.inColoringIQ.greenC));

        JTextField bluec = new JTextField(18);
        bluec.setText("" + (outcoloring ? gps.outColoringIQ.blueC : gps.inColoringIQ.blueC));

        abcd_panel.add(redc);
        abcd_panel.add(greenc);
        abcd_panel.add(bluec);

        abcd_panel.add(new JLabel("Red D:"));
        abcd_panel.add(new JLabel("Green D:"));
        abcd_panel.add(new JLabel("Blue D:"));

        JTextField redd = new JTextField(18);
        redd.setText("" + (outcoloring ? gps.outColoringIQ.redD : gps.inColoringIQ.redD));

        JTextField greend = new JTextField(18);
        greend.setText("" + (outcoloring ? gps.outColoringIQ.greenD : gps.inColoringIQ.greenD));

        JTextField blued = new JTextField(18);
        blued.setText("" + (outcoloring ? gps.outColoringIQ.blueD : gps.inColoringIQ.blueD));

        abcd_panel.add(redd);
        abcd_panel.add(greend);
        abcd_panel.add(blued);

        abcd_panel.add(new JLabel("Red G:"));
        abcd_panel.add(new JLabel("Green G:"));
        abcd_panel.add(new JLabel("Blue G:"));

        JTextField redg = new JTextField(18);
        redg.setText("" + (outcoloring ? gps.outColoringIQ.redG : gps.inColoringIQ.redG));

        JTextField greeng = new JTextField(18);
        greeng.setText("" + (outcoloring ? gps.outColoringIQ.greenG : gps.inColoringIQ.greenG));

        JTextField blueg = new JTextField(18);
        blueg.setText("" + (outcoloring ? gps.outColoringIQ.blueG : gps.inColoringIQ.blueG));

        abcd_panel.add(redg);
        abcd_panel.add(greeng);
        abcd_panel.add(blueg);

        Object[] message = {
                " ",
                "IQ Cosine Parameters:",
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

                            if(temp3 < 0 || temp4 < 0 || temp5 < 0 || temp6 < 0 || temp7 < 0 || temp8 < 0) {
                                JOptionPane.showMessageDialog(ptra, "The A and B values must be greater or equal to 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp3 + temp6 > 1 || temp4 + temp7 > 1 || temp5 + temp8 > 1) {
                                JOptionPane.showMessageDialog(ptra, "The sum of A and B (A + B) must be less or equal to 1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }


                            if(outcoloring) {
                                gps.outColoringIQ.redA = temp3;
                                gps.outColoringIQ.greenA = temp4;
                                gps.outColoringIQ.blueA = temp5;

                                gps.outColoringIQ.redB = temp6;
                                gps.outColoringIQ.greenB = temp7;
                                gps.outColoringIQ.blueB = temp8;

                                gps.outColoringIQ.redC = temp9;
                                gps.outColoringIQ.greenC = temp10;
                                gps.outColoringIQ.blueC = temp11;

                                gps.outColoringIQ.redD = temp12;
                                gps.outColoringIQ.greenD = temp13;
                                gps.outColoringIQ.blueD = temp14;

                                gps.outColoringIQ.redG = temp15;
                                gps.outColoringIQ.greenG = temp16;
                                gps.outColoringIQ.blueG = temp17;
                            }
                            else {
                                gps.inColoringIQ.redA = temp3;
                                gps.inColoringIQ.greenA = temp4;
                                gps.inColoringIQ.blueA = temp5;

                                gps.inColoringIQ.redB = temp6;
                                gps.inColoringIQ.greenB = temp7;
                                gps.inColoringIQ.blueB = temp8;

                                gps.inColoringIQ.redC = temp9;
                                gps.inColoringIQ.greenC = temp10;
                                gps.inColoringIQ.blueC = temp11;

                                gps.inColoringIQ.redD = temp12;
                                gps.inColoringIQ.greenD = temp13;
                                gps.inColoringIQ.blueD = temp14;

                                gps.inColoringIQ.redG = temp15;
                                gps.inColoringIQ.greenG = temp16;
                                gps.inColoringIQ.blueG = temp17;

                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.setParams(gps);
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
