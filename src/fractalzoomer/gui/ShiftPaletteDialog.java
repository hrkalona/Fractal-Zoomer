
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static fractalzoomer.main.Constants.CUSTOM_PALETTE_ID;

/**
 *
 * @author hrkalona2
 */
public class ShiftPaletteDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public ShiftPaletteDialog(MainWindow ptr, Settings s, boolean outcoloring) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Shift Palette");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        MyJSpinner field = new MyJSpinner(new SpinnerNumberModel((outcoloring ? s.ps.color_cycling_location : s.ps2.color_cycling_location), 0, Integer.MAX_VALUE, 1));
        field.addAncestorListener(new RequestFocusListener());

        Object[] message3 = {
            " ",
            "The palette is shifted by " + (outcoloring ? s.ps.color_cycling_location : s.ps2.color_cycling_location) + ".\nEnter a number to shift the palette.",
            field,
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

                            if (temp < 0) {
                                JOptionPane.showMessageDialog(ptra, "Palette shift value must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (outcoloring) {
                                s.ps.color_cycling_location = temp;

                                if (s.ps.color_choice == CUSTOM_PALETTE_ID) {
                                    s.temp_color_cycling_location = s.ps.color_cycling_location;
                                }
                            } else {
                                s.ps2.color_cycling_location = temp;

                                if (s.ps2.color_choice == CUSTOM_PALETTE_ID) {
                                    s.temp_color_cycling_location_second_palette = s.ps2.color_cycling_location;
                                }
                            }

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptr.shiftPalettePost();
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
