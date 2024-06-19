
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class KleinianDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public KleinianDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType, boolean wasEscapingOrConvergingType, boolean wasMagnetPatakiType) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Kleinian Maskit Parametrisation");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextField field_real = new JTextField();
        field_real.setText("" + s.fns.kleinianLine[0]);

        JTextField field_imaginary = new JTextField();
        field_imaginary.setText("" + s.fns.kleinianLine[1]);

        JTextField field_K = new JTextField();
        field_K.setText("" + s.fns.kleinianK);

        JTextField field_M = new JTextField();
        field_M.setText("" + s.fns.kleinianM);

        Object[] message6 = {
            " ",
            "Set the real and imaginary part of the Moebius Transformation.",
            "Real:", field_real,
            "Imaginary:", field_imaginary,
            " ",
            "Set the constants of the exponential function.",
            "scale factor K:", field_K,
            "exponent M:", field_M,
            " "};

        optionPane = new JOptionPane(message6, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            fractal_functions[oldSelected].setSelected(true);
                            s.fns.function = oldSelected;
                            dispose();
                            return;
                        }

                        try {
                            double temp3 = Double.parseDouble(field_real.getText());
                            double temp4 = Double.parseDouble(field_imaginary.getText());
                            double temp5 = Double.parseDouble(field_K.getText());
                            double temp6 = Double.parseDouble(field_M.getText());

                            s.fns.kleinianLine[0] = temp3;
                            s.fns.kleinianLine[1] = temp4;
                            s.fns.kleinianK = temp5;
                            s.fns.kleinianM = temp6;

                            ptr.setFunctionPostKleinian();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        ptra.optionsEnableShortcut();
                        dispose();
                        ptra.setFunctionPost(oldSelected, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
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
