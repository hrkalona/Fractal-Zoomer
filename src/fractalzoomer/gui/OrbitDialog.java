
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.utils.MathUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;

/**
 *
 * @author hrkalona2
 */
public class OrbitDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public OrbitDialog(MainWindow ptr, Settings s, double[] orbit_vals) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Orbit");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        final JTextField field_real = new JTextField();

        if (orbit_vals[0] == 0) {
            field_real.setText("" + 0.0);
        } else {
            field_real.setText("" + orbit_vals[0]);
        }

        final JTextField field_imaginary = new JTextField();

        if (orbit_vals[1] == 0) {
            field_imaginary.setText("" + 0.0);
        } else {
            field_imaginary.setText("" + orbit_vals[1]);
        }

        final JCheckBox current_center = new JCheckBox("Current Center");
        current_center.setSelected(false);
        current_center.setFocusable(false);

        current_center.addActionListener(e -> {

            if (!current_center.isSelected()) {
                if (orbit_vals[0] == 0) {
                    field_real.setText("" + 0.0);
                } else {
                    field_real.setText("" + orbit_vals[0]);
                }

                field_real.setEditable(true);

                if (orbit_vals[1] == 0) {
                    field_imaginary.setText("" + 0.0);
                } else {
                    field_imaginary.setText("" + orbit_vals[1]);
                }
                field_imaginary.setEditable(true);
            } else {

                Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter.doubleValue(), s.yCenter.doubleValue(), Settings.fromDDArray(s.fns.rotation_vals), Settings.fromDDArray(s.fns.rotation_center));

                field_real.setText("" + p.x);
                field_real.setEditable(false);
                field_imaginary.setText("" + p.y);
                field_imaginary.setEditable(false);
            }
        });

        JTextField sequence_points = new JTextField();
        sequence_points.setText("" + (s.max_iterations > 400 ? 400 : s.max_iterations));

        Object[] message = {
            " ",
            "Set the real and imaginary part of the new orbit.",
            "Real:", field_real,
            "Imaginary:", field_imaginary,
            current_center,
            " ",
            "Set the number of sequence points.",
            "Sequence Points:", sequence_points,
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

                        double x_real = 0, y_imag = 0;
                        int seq_points = 0;
                        try {
                            double tempReal = Double.parseDouble(field_real.getText());
                            double tempImaginary = Double.parseDouble(field_imaginary.getText());
                            seq_points = Integer.parseInt(sequence_points.getText());

                            if (seq_points <= 0) {
                                JOptionPane.showMessageDialog(ptra, "Sequence points number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            orbit_vals[0] = tempReal;
                            orbit_vals[1] = tempImaginary;

                            double[] rot_center = Settings.fromDDArray(s.fns.rotation_center);
                            double[] rot_vals = Settings.fromDDArray(s.fns.rotation_vals);

                            tempReal -= rot_center[0];
                            tempImaginary -= rot_center[1];

                            /* Inversed Rotation */
                            x_real = tempReal * rot_vals[0] + tempImaginary * rot_vals[1] + rot_center[0];
                            y_imag = -tempReal * rot_vals[1] + tempImaginary * rot_vals[0] + rot_center[1];
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.setOrbitPointPost(x_real, y_imag, seq_points);
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
