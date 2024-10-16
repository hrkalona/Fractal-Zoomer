
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static fractalzoomer.main.Constants.MANDELBROTWTH;
import static fractalzoomer.main.Constants.MANDELPOLY;

/**
 *
 * @author hrkalona2
 */
public class MandelgrassDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public MandelgrassDialog(MainWindow ptr, Settings s, JMenuItem mandel_grass_opt) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Mandel Grass");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        final JCheckBox mandelgrass = new JCheckBox("Mandel Grass");
        mandelgrass.setSelected(s.fns.mandel_grass);
        mandelgrass.setFocusable(false);
        mandelgrass.setToolTipText("Enables mandel grass.");

        JTextField field_real = new JTextField();
        field_real.setText("" + s.fns.mandel_grass_vals[0]);

        JTextField field_imaginary = new JTextField();
        field_imaginary.setText("" + s.fns.mandel_grass_vals[1]);

        Object[] message = {
                " ",
                mandelgrass,
            " ",
            "Set the real and imaginary part of the mandel grass.",
            "Real:", field_real,
            "Imaginary:", field_imaginary,
            " ",};

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
                            double temp = Double.parseDouble(field_real.getText());
                            double temp2 = Double.parseDouble(field_imaginary.getText());

                            s.fns.mandel_grass_vals[0] = temp;
                            s.fns.mandel_grass_vals[1] = temp2;

                            boolean oldMandelGrass = s.fns.mandel_grass;

                            s.fns.mandel_grass = mandelgrass.isSelected();

                            if(s.fns.mandel_grass && !oldMandelGrass) {
                                mandel_grass_opt.setIcon(MainWindow.getIcon("check.png"));
                            }
                            else if(!s.fns.mandel_grass && oldMandelGrass) {
                                mandel_grass_opt.setIcon(null);
                            }

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();

                        if (s.fns.function <= 9 || s.fns.function == MANDELPOLY || s.fns.function == MANDELBROTWTH) {
                            ptra.defaultFractalSettings(true, true);
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

}
