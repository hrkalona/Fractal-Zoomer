
package fractalzoomer.gui;

import fractalzoomer.core.BigPoint;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.utils.MathUtils;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static fractalzoomer.gui.CenterSizeDialog.TEMPLATE_TFIELD;

/**
 *
 * @author hrkalona2
 */
public class StretchPlaneDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public StretchPlaneDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] planes) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Stretch");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextField field_rotation = new JTextField();
        field_rotation.setText("" + s.fns.plane_transform_angle);

        JTextArea field_real = new JTextArea(6, 50);
        field_real.setFont(TEMPLATE_TFIELD.getFont());
        field_real.setLineWrap(true);

        JScrollPane scrollReal = new JScrollPane (field_real,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(field_real.getInputMap());
        CenterSizeDialog.disableKeys(scrollReal.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));


        JTextArea field_imaginary = new JTextArea(6, 50);
        field_imaginary.setFont(TEMPLATE_TFIELD.getFont());
        field_imaginary.setLineWrap(true);

        JScrollPane scrollImaginary = new JScrollPane (field_imaginary,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(field_imaginary.getInputMap());
        CenterSizeDialog.disableKeys(scrollImaginary.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        final JCheckBox current_center = new JCheckBox("Current Center");
        current_center.setSelected(false);
        current_center.setFocusable(false);

        current_center.addActionListener(e -> {


            if (!current_center.isSelected()) {
                if (s.fns.plane_transform_center_hp[0].compareTo(MyApfloat.ZERO) == 0) {
                    field_real.setText("" + 0.0);
                } else {
                    field_real.setText("" + s.fns.plane_transform_center_hp[0].toString(true));
                }

                field_real.setEnabled(true);

                if (s.fns.plane_transform_center_hp[1].compareTo(MyApfloat.ZERO) == 0) {
                    field_imaginary.setText("" + 0.0);
                } else {
                    field_imaginary.setText("" + s.fns.plane_transform_center_hp[1].toString(true));
                }
                field_imaginary.setEnabled(true);
            } else {
                BigPoint p = MathUtils.rotatePointRelativeToPoint(new BigPoint(s.xCenter, s.yCenter), s.fns.rotation_vals, s.fns.rotation_center);

                field_real.setText("" + p.x.toString(true));
                field_real.setEnabled(false);
                field_imaginary.setText("" + p.y.toString(true));
                field_imaginary.setEnabled(false);
            }
        });


        if(current_center.isSelected()) {
            BigPoint p = MathUtils.rotatePointRelativeToPoint(new BigPoint(s.xCenter, s.yCenter), s.fns.rotation_vals, s.fns.rotation_center);

            field_real.setText("" + p.x.toString(true));
            field_real.setEnabled(false);
            field_imaginary.setText("" + p.y.toString(true));
            field_imaginary.setEnabled(false);
        }
        else {
            if (s.fns.plane_transform_center_hp[0].compareTo(MyApfloat.ZERO) == 0) {
                field_real.setText("" + 0.0);
            } else {
                field_real.setText("" + s.fns.plane_transform_center_hp[0].toString(true));
            }
            if (s.fns.plane_transform_center_hp[1].compareTo(MyApfloat.ZERO) == 0) {
                field_imaginary.setText("" + 0.0);
            } else {
                field_imaginary.setText("" + s.fns.plane_transform_center_hp[1].toString(true));
            }
            field_imaginary.setEnabled(true);
            field_real.setEnabled(true);
        }


        SwingUtilities.invokeLater(() -> {
            scrollReal.getVerticalScrollBar().setValue(0);
            scrollImaginary.getVerticalScrollBar().setValue(0);

        });


        JTextField field_amount = new JTextField();
        field_amount.setText("" + s.fns.plane_transform_amount * 100);

        //Todo add help for alt + click

        Object[] message = {
            " ",
            "Set the stretch angle in degrees.",
            "Angle:", field_rotation,
            " ",
            "Set the stretch amount.",
            "Amount:", field_amount,
            " ",
            "Set the stretch center.",
            "Real:", scrollReal,
            "Imaginary:", scrollImaginary,
            current_center, " "
            };

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
                            if(MyApfloat.setAutomaticPrecision) {
                                long precision = MyApfloat.getAutomaticPrecision(new String[]{field_real.getText(), field_imaginary.getText()}, new boolean[] {false, false}, s.fns.function);

                                if (MyApfloat.shouldSetPrecision(precision, MyApfloat.alwaysCheckForDecrease, s.fns.function)) {
                                    Fractal.clearReferences(true, true);
                                    MyApfloat.setPrecision(precision, s);
                                }
                            }

                            Apfloat tempReal = new MyApfloat(field_real.getText());
                            Apfloat tempImaginary = new MyApfloat(field_imaginary.getText());
                            double temp3 = Double.parseDouble(field_rotation.getText());
                            double temp5 = Double.parseDouble(field_amount.getText()) / 100;

                            s.fns.plane_transform_center_hp[0] = tempReal;
                            s.fns.plane_transform_center_hp[1] = tempImaginary;

                            Apfloat zero = MyApfloat.ZERO;

                            s.fns.plane_transform_center_hp[0] = s.fns.plane_transform_center_hp[0].compareTo(zero) == 0 ? zero : s.fns.plane_transform_center_hp[0];
                            s.fns.plane_transform_center_hp[1] = s.fns.plane_transform_center_hp[1].compareTo(zero) == 0? zero : s.fns.plane_transform_center_hp[1];


                            s.fns.plane_transform_angle = temp3;
                            s.fns.plane_transform_amount = temp5 == 0.0 ? 0.0 : temp5;

                            s.fns.plane_transform_center[0] = s.fns.plane_transform_center_hp[0].doubleValue();
                            s.fns.plane_transform_center[1] = s.fns.plane_transform_center_hp[1].doubleValue();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        Fractal.clearReferences(true, true);
                        ptra.reRender(false);
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
