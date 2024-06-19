
package fractalzoomer.gui;

import fractalzoomer.core.BigPoint;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.utils.MathUtils;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static fractalzoomer.gui.CenterSizeDialog.TEMPLATE_TFIELD;

/**
 *
 * @author hrkalona2
 */
public class CenterSizeJuliaDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;
    private final JScrollPane scrollPane;

    public CenterSizeJuliaDialog(MainWindow ptr, Settings s, int image_width, int image_height) {

        super(ptr);
        
        ptra = ptr;

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(700, 700));

        if(s.fns.juliter) {
            setTitle("Center, Size, Julia Seed, and Juliter");
        }
        else {
            setTitle("Center, Size, and Julia Seed");
        }

        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextArea field_real = new JTextArea(4, 50);
        field_real.setFont(TEMPLATE_TFIELD.getFont());
        field_real.setLineWrap(true);

        JScrollPane scrollReal = new JScrollPane (field_real,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(field_real.getInputMap());
        CenterSizeDialog.disableKeys(scrollReal.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        BigPoint p = MathUtils.rotatePointRelativeToPoint(new BigPoint(s.xCenter, s.yCenter), s.fns.rotation_vals, s.fns.rotation_center);


        if (p.x.compareTo(MyApfloat.ZERO) == 0) {
            field_real.setText("" + 0.0);
        } else {
            field_real.setText("" + p.x.toString(true));
        }

        JTextArea field_imaginary = new JTextArea(4, 50);
        field_imaginary.setFont(TEMPLATE_TFIELD.getFont());
        field_imaginary.setLineWrap(true);

        JScrollPane scrollImaginary = new JScrollPane (field_imaginary,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(field_imaginary.getInputMap());
        CenterSizeDialog.disableKeys(scrollImaginary.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        if (p.y.compareTo(MyApfloat.ZERO) == 0) {
            field_imaginary.setText("" + 0.0);
        } else {
            field_imaginary.setText("" + p.y.toString(true));
        }

        JTextArea field_size = new JTextArea(4, 50);
        field_size.setFont(TEMPLATE_TFIELD.getFont());
        field_size.setLineWrap(true);

        JScrollPane scrollSize = new JScrollPane (field_size,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(field_size.getInputMap());
        CenterSizeDialog.disableKeys(scrollSize.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        field_size.setText("" + s.size);

        JTextArea real_seed = new JTextArea(4, 50);
        real_seed.setFont(TEMPLATE_TFIELD.getFont());
        real_seed.setLineWrap(true);

        JScrollPane scrollRealSeed = new JScrollPane (real_seed,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(real_seed.getInputMap());
        CenterSizeDialog.disableKeys(real_seed.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        if (s.xJuliaCenter.compareTo(MyApfloat.ZERO) == 0) {
            real_seed.setText("" + 0.0);
        } else {
            real_seed.setText("" + s.xJuliaCenter.toString(true));
        }

        JTextArea imag_seed = new JTextArea(4, 50);
        imag_seed.setFont(TEMPLATE_TFIELD.getFont());
        imag_seed.setLineWrap(true);

        JScrollPane scrollImaginarySeed = new JScrollPane (imag_seed,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(imag_seed.getInputMap());
        CenterSizeDialog.disableKeys(imag_seed.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        if (s.yJuliaCenter.compareTo(MyApfloat.ZERO) == 0) {
            imag_seed.setText("" + 0.0);
        } else {
            imag_seed.setText("" + s.yJuliaCenter.toString(true));
        }

        JButton corners = new MyButton("Set Corners");
        corners.setToolTipText("An alternative center/size selection option.");
        corners.setFocusable(false);
        corners.setIcon(MainWindow.getIcon("corners.png"));

        JButton magnification = new MyButton("Set Magnification/Zoom");
        magnification.setToolTipText("An alternative size option.");
        magnification.setFocusable(false);
        magnification.setIcon(MainWindow.getIcon("magnification.png"));

        JPanel cornersPanel = new JPanel();
        cornersPanel.add(corners);
        cornersPanel.add(magnification);

        corners.addActionListener(e -> new CornersDialog(ptr, s, field_real, field_imaginary, field_size, image_width, image_height));

        magnification.addActionListener(e -> new MagnificationDialog(ptr, s, field_size));

        SwingUtilities.invokeLater(() -> {
            scrollSize.getVerticalScrollBar().setValue(0);
            scrollReal.getVerticalScrollBar().setValue(0);
            scrollImaginary.getVerticalScrollBar().setValue(0);
            scrollRealSeed.getVerticalScrollBar().setValue(0);
            scrollImaginarySeed.getVerticalScrollBar().setValue(0);

        });

        Object[] message = null;

        JTextField juliterIterationsfield = new JTextField();
        juliterIterationsfield.addAncestorListener(new RequestFocusListener());
        juliterIterationsfield.setText("" + s.fns.juliterIterations);

        JCheckBox includePreStarting = new JCheckBox("Include pre-starting iterations");
        includePreStarting.setFocusable(false);
        includePreStarting.setSelected(s.fns.juliterIncludeInitialIterations);

        if(s.fns.juliter) {
            Object[] temp = {
                    " ",
                    "Set the real and imaginary part of the new center",
                    "and the new size.",
                    "Real:", scrollReal,
                    "Imaginary:", scrollImaginary,
                    "Size:", scrollSize,
                    " ",
                    cornersPanel,
                    " ",
                    "Set the real and imaginary part of the Julia seed.",
                    "Real:", scrollRealSeed,
                    "Imaginary:", scrollImaginarySeed,
                    " ",
                    "Set the starting iterations of Juliter.",
                    "Starting Iterations:",
                    juliterIterationsfield,
                    " ",
                    includePreStarting,
                    " "};


            message = temp;
        }
        else {
            Object[] temp = {
                    " ",
                    "Set the real and imaginary part of the new center",
                    "and the new size.",
                    "Real:", scrollReal,
                    "Imaginary:", scrollImaginary,
                    "Size:", scrollSize,
                    " ",
                    cornersPanel,
                    " ",
                    "Set the real and imaginary part of the Julia seed.",
                    "Real:", scrollRealSeed,
                    "Imaginary:", scrollImaginarySeed,
                    " ",};


            message = temp;
        }


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
                            if(MyApfloat.setAutomaticPrecision) {
                                long precision = MyApfloat.getAutomaticPrecision(new String[] {field_size.getText(), field_real.getText(), field_imaginary.getText(), real_seed.getText(), imag_seed.getText()}, new boolean[] {true, false, false, false, false}, s.fns.function);

                                if (MyApfloat.shouldSetPrecision(precision, MyApfloat.alwaysCheckForDecrease, s.fns.function)) {
                                    Fractal.clearReferences(true, true);
                                    MyApfloat.setPrecision(precision, s);
                                }
                            }

                            Apfloat tempReal =  MyApfloat.fp.subtract(new MyApfloat(field_real.getText()), s.fns.rotation_center[0]);
                            Apfloat tempImaginary =  MyApfloat.fp.subtract(new MyApfloat(field_imaginary.getText()), s.fns.rotation_center[1]);
                            Apfloat tempSize =  new MyApfloat(field_size.getText());

                            Apfloat tempJuliaReal = new MyApfloat(real_seed.getText());
                            Apfloat tempJuliaImaginary = new MyApfloat(imag_seed.getText());


                            if (tempSize.compareTo(MyApfloat.ZERO) <= 0) {
                                JOptionPane.showMessageDialog(ptra, "Size number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(s.fns.juliter) {
                                int temp = Integer.parseInt(juliterIterationsfield.getText());

                                if (temp < 0) {
                                    JOptionPane.showMessageDialog(ptra, "Juliter's starting iterations must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                s.fns.juliterIterations = temp;
                                s.fns.juliterIncludeInitialIterations = includePreStarting.isSelected();
                            }

                            s.size = tempSize;
                            /* Inverse rotation */
                            s.xCenter = MyApfloat.fp.add(MyApfloat.fp.add(MyApfloat.fp.multiply(tempReal, s.fns.rotation_vals[0]), MyApfloat.fp.multiply(tempImaginary, s.fns.rotation_vals[1])), s.fns.rotation_center[0]);
                            s.yCenter = MyApfloat.fp.add(MyApfloat.fp.add(MyApfloat.fp.multiply(tempReal.negate(), s.fns.rotation_vals[1]), MyApfloat.fp.multiply(tempImaginary, s.fns.rotation_vals[0])), s.fns.rotation_center[1]);

                            s.xJuliaCenter = tempJuliaReal;
                            s.yJuliaCenter = tempJuliaImaginary;
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.setCenterSizePost();
                    }
                });

        //Make this dialog display it.
        scrollPane.setViewportView(optionPane);
        setContentPane(scrollPane);

        pack();

        setResizable(true);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }

}
