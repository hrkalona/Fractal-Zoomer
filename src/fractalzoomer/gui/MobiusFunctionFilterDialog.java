package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.FunctionFilterSettings;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.parser.ParserException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MobiusFunctionFilterDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public MobiusFunctionFilterDialog(MainWindow ptr, Settings s, int oldSelected, String name, FunctionFilterSettings ffs, JRadioButtonMenuItem[] function_filters) {

        super(ptr);

        ptra = ptr;

        setTitle(name);
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextField a_field = new JTextField();
        a_field.setText("" + ffs.mobiusA);

        JTextField b_field = new JTextField();
        b_field.setText("" + ffs.mobiusB);

        Object[] message3 = {
                " ",
                "Set the Mobius filter values.",
                "A:",
                a_field,
                "B:",
                b_field,
        " "};

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
                            function_filters[oldSelected].setSelected(true);
                            ffs.functionFilter = oldSelected;
                            dispose();
                            return;
                        }

                        try {
                            double temp = Double.parseDouble(a_field.getText());
                            double temp2 = Double.parseDouble(b_field.getText());
                            ffs.mobiusA = temp;
                            ffs.mobiusB = temp2;
                            ptra.defaultFractalSettings(true, false);
                        } catch (ParserException ex) {
                            JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
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
