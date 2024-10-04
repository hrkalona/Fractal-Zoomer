
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static fractalzoomer.main.Constants.waveTypes;

/**
 *
 * @author hrkalona2
 */
public class RipplesPlaneDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public RipplesPlaneDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] planes) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Ripples");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextField field_scale_real = new JTextField();
        field_scale_real.setText("" + s.fns.plane_transform_scales[0]);

        JTextField field_scale_imaginary = new JTextField();
        field_scale_imaginary.setText("" + s.fns.plane_transform_scales[1]);

        JTextField field_wavelength_real = new JTextField();
        field_wavelength_real.setText("" + s.fns.plane_transform_wavelength[0]);

        JTextField field_wavelength_imaginary = new JTextField();
        field_wavelength_imaginary.setText("" + s.fns.plane_transform_wavelength[1]);

        final JComboBox<String> wavetype_combobox = new JComboBox<>(waveTypes);
        wavetype_combobox.setFocusable(false);
        wavetype_combobox.setToolTipText("Sets type of wave.");
        wavetype_combobox.setSelectedIndex(s.fns.waveType);

        Object[] message = {
            " ",
            "Set the ripple's amplitude.",
            "Amplitude Real:", field_scale_real,
            "Amplitude Imaginary:", field_scale_imaginary,
            " ",
            "Set the ripple's wavelength.",
            "Wavelength Real:", field_wavelength_real,
            "Wavelength Imaginary:", field_wavelength_imaginary,
            " ",
            "Set the ripple's wave type.",
            "Wave Type:",
            wavetype_combobox,
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
                            planes[oldSelected].setSelected(true);
                            s.fns.plane_type = oldSelected;
                            dispose();
                            return;
                        }

                        try {
                            double temp3 = Double.parseDouble(field_scale_real.getText());
                            double temp4 = Double.parseDouble(field_scale_imaginary.getText());
                            double temp7 = Double.parseDouble(field_wavelength_real.getText());
                            double temp8 = Double.parseDouble(field_wavelength_imaginary.getText());

                            s.fns.plane_transform_scales[0] = temp3 == 0.0 ? 0.0 : temp3;
                            s.fns.plane_transform_scales[1] = temp4 == 0.0 ? 0.0 : temp4;
                            s.fns.plane_transform_wavelength[0] = temp7 == 0.0 ? 0.0 : temp7;
                            s.fns.plane_transform_wavelength[1] = temp8 == 0.0 ? 0.0 : temp8;
                            s.fns.waveType = wavetype_combobox.getSelectedIndex();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.defaultFractalSettings(true, false);
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
