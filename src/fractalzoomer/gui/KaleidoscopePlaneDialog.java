
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
public class KaleidoscopePlaneDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public KaleidoscopePlaneDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] planes) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Kaleidoscope");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextField field_rotation = new JTextField();
        field_rotation.setText("" + s.fns.plane_transform_angle);

        JTextField field_rotation2 = new JTextField();
        field_rotation2.setText("" + s.fns.plane_transform_angle2);

        final JTextField field_real = new JTextField();

        if (s.fns.plane_transform_center[0] == 0) {
            field_real.setText("" + 0.0);
        } else {
            field_real.setText("" + s.fns.plane_transform_center[0]);
        }

        final JTextField field_imaginary = new JTextField();

        if (s.fns.plane_transform_center[1] == 0) {
            field_imaginary.setText("" + 0.0);
        } else {
            field_imaginary.setText("" + s.fns.plane_transform_center[1]);
        }

        JTextField field_radius = new JTextField();
        field_radius.setText("" + s.fns.plane_transform_radius);

        JTextField field_sides = new JTextField();
        field_sides.setText("" + s.fns.plane_transform_sides);

        final JCheckBox current_center = new JCheckBox("Current Center");
        current_center.setSelected(false);
        current_center.setFocusable(false);

        current_center.addActionListener(e -> {

            if (!current_center.isSelected()) {
                if (s.fns.plane_transform_center[0] == 0) {
                    field_real.setText("" + 0.0);
                } else {
                    field_real.setText("" + s.fns.plane_transform_center[0]);
                }

                field_real.setEditable(true);

                if (s.fns.plane_transform_center[1] == 0) {
                    field_imaginary.setText("" + 0.0);
                } else {
                    field_imaginary.setText("" + s.fns.plane_transform_center[1]);
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

        Object[] message = {
            " ",
            "Set the kaleidoscope angles in degrees.",
            "Angle:", field_rotation,
            "Angle 2:", field_rotation2,
            " ",
            "Set the kaleidoscope radius.",
            "Radius:", field_radius,
            " ",
            "Set the kaleidoscope center (User Point).",
            "Real:", field_real,
            "Imaginary:", field_imaginary,
            current_center, " ",
            "Set the kaleidoscope sides.",
            "Sides:", field_sides,
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
                            double temp3 = Double.parseDouble(field_rotation.getText());
                            double temp5 = Double.parseDouble(field_rotation2.getText());
                            double temp4 = Double.parseDouble(field_radius.getText());
                            double tempReal = Double.parseDouble(field_real.getText());
                            double tempImaginary = Double.parseDouble(field_imaginary.getText());
                            int temp6 = Integer.parseInt(field_sides.getText());

                            if (temp4 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "Kaleidoscope radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp6 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "Kaleidoscope sides must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.fns.plane_transform_center[0] = tempReal;
                            s.fns.plane_transform_center[1] = tempImaginary;
                            s.fns.plane_transform_angle = temp3;
                            s.fns.plane_transform_radius = temp4;
                            s.fns.plane_transform_angle2 = temp5;
                            s.fns.plane_transform_sides = temp6;
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
