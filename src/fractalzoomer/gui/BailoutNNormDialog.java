
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.parser.ParserException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class BailoutNNormDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public BailoutNNormDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] bailout_tests, boolean mode) {

        super(ptr);
        
        ptra = ptr;

        setTitle("N-Norm");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextField field = new JTextField();

        if(mode) {
            field.setText("" + s.fns.n_norm);
        }
        else {
            field.setText("" + s.fns.cbs.convergent_n_norm);
        }
        field.addAncestorListener(new RequestFocusListener());

        JTextField field2 = new JTextField();

        if(mode) {
            field2.setText("" + s.fns.norm_a);
        }
        else {
            field2.setText("" + s.fns.cbs.norm_a);
        }

        JTextField field3 = new JTextField();
        if(mode) {
            field3.setText("" + s.fns.norm_b);
        }
        else {
            field3.setText("" + s.fns.cbs.norm_b);
        }

        Object[] message3 = {
            " ",
                "Set the N-Norm.",
            "N-Norm:",
            field,
                " ",
                "Set A and B coefficients.",
                "A:",
                field2,
                "B:",
                field3,
            " ",};

        optionPane = new JOptionPane(message3, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            bailout_tests[oldSelected].setSelected(true);

                            if(mode) {
                                s.fns.bailout_test_algorithm = oldSelected;
                            }
                            else {
                                s.fns.cbs.convergent_bailout_test_algorithm = oldSelected;
                            }
                            dispose();
                            return;
                        }

                        try {
                            double temp3 = Double.parseDouble(field.getText());
                            double temp4 = Double.parseDouble(field2.getText());
                            double temp5 = Double.parseDouble(field3.getText());

                            if(mode) {
                                s.fns.n_norm = temp3;
                                s.fns.norm_a = temp4;
                                s.fns.norm_b = temp5;
                            }
                            else {
                                s.fns.cbs.convergent_n_norm  = temp3;
                                s.fns.cbs.norm_a = temp4;
                                s.fns.cbs.norm_b = temp5;
                            }
                        } catch (ParserException ex) {
                            JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();

                        ptra.setBailoutTestPost();
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
