
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class BoundariesDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public BoundariesDialog(MainWindow ptr, int boundaries_num, int boundaries_spacing_method, int boundaries_type) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Boundaries Options");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextField field = new JTextField();
        field.setText("" + boundaries_num);

        String[] method = {"Power of two", "Increment of one"};

        JComboBox<String> draw_choice = new JComboBox<>(method);
        draw_choice.setSelectedIndex(boundaries_spacing_method);
        draw_choice.setToolTipText("Selects the spacing method.");
        draw_choice.setFocusable(false);

        String[] method2 = {"Circle", "Square", "Rhombus"};

        JComboBox<String> draw_choice2 = new JComboBox<>(method2);
        draw_choice2.setSelectedIndex(boundaries_type);
        draw_choice2.setToolTipText("Selects the type of boundary.");
        draw_choice2.setFocusable(false);

        Object[] message3 = {
            " ",
            "You are using " + boundaries_num + " as the number of boundaries.\nInsert the new boundaries number.",
            field,
            " ",
            "Select the spacing method.",
            draw_choice,
            " ",
            "Select the boundary type.",
            draw_choice2,
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
                            dispose();
                            return;
                        }

                        try {
                            int temp = Integer.parseInt(field.getText());

                            if (temp < 1) {
                                JOptionPane.showMessageDialog(ptra, "Boundaries number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            } else if (temp > 64) {
                                JOptionPane.showMessageDialog(ptra, "Boundaries number must be less than 65.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            ptra.setBoundariesNumberPost(temp, draw_choice.getSelectedIndex(), draw_choice2.getSelectedIndex());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
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
