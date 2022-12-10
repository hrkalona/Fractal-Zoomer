/*
 * Copyright (C) 2020 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.gui;

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
public class CornersDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public CornersDialog(MainWindow ptr, Settings s, JTextArea field_real, JTextArea field_imaginary, JTextArea field_size) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Corners");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        Apfloat tempx, tempy, tempSize;
        try {
            if(MyApfloat.setAutomaticPrecision) {
                long precision = MyApfloat.getAutomaticPrecision(new String[] {field_size.getText(), field_real.getText(), field_imaginary.getText()}, new boolean[] {true, false, false});

                if (MyApfloat.shouldSetPrecision(precision, false)) {
                    Fractal.clearReferences(true);
                    MyApfloat.setPrecision(precision, s);
                }
            }

            tempx = new MyApfloat(field_real.getText());
            tempy = new MyApfloat(field_imaginary.getText());
            tempSize =new MyApfloat(field_size.getText());
        } catch (Exception ex) {
            tempx = s.xCenter;
            tempy = s.yCenter;
            tempSize = s.size;
        }

        Apfloat[] corners = MathUtils.convertFromCenterSizeToCorners(tempx, tempy, tempSize);


        JTextArea corner1_real = new JTextArea(3, 25);
        corner1_real.setFont(TEMPLATE_TFIELD.getFont());
        corner1_real.setLineWrap(true);

        JScrollPane corner1Real = new JScrollPane (corner1_real,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(corner1_real.getInputMap());
        CenterSizeDialog.disableKeys(corner1Real.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        corner1_real.setText("" + corners[0].toString(true));


        JTextArea corner1_imag = new JTextArea(3, 25);
        corner1_imag.setFont(TEMPLATE_TFIELD.getFont());
        corner1_imag.setLineWrap(true);

        JScrollPane corner1Imag = new JScrollPane (corner1_imag,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(corner1_imag.getInputMap());
        CenterSizeDialog.disableKeys(corner1Imag.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        corner1_imag.setText("" + corners[1].toString(true));

        JTextArea corner2_real = new JTextArea(3, 25);
        corner2_real.setFont(TEMPLATE_TFIELD.getFont());
        corner2_real.setLineWrap(true);

        JScrollPane corner2Real = new JScrollPane (corner2_real,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(corner2_real.getInputMap());
        CenterSizeDialog.disableKeys(corner2Real.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        corner2_real.setText("" + corners[2].toString(true));

        JTextArea corner2_imag = new JTextArea(3, 25);
        corner2_imag.setFont(TEMPLATE_TFIELD.getFont());
        corner2_imag.setLineWrap(true);

        JScrollPane corner2Imag = new JScrollPane (corner2_imag,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(corner2_imag.getInputMap());
        CenterSizeDialog.disableKeys(corner2Imag.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        corner2_imag.setText("" + corners[3].toString(true));

        JPanel p1 = new JPanel();
        p1.add(new JLabel("Real: "));
        p1.add(corner1Real);
        p1.add(new JLabel(" Imaginary: "));
        p1.add(corner1Imag);

        JPanel p2 = new JPanel();
        p2.add(new JLabel("Real: "));
        p2.add(corner2Real);
        p2.add(new JLabel(" Imaginary: "));
        p2.add(corner2Imag);


        SwingUtilities.invokeLater(() -> {
            corner1Real.getVerticalScrollBar().setValue(0);
            corner1Imag.getVerticalScrollBar().setValue(0);
            corner2Real.getVerticalScrollBar().setValue(0);
            corner2Imag.getVerticalScrollBar().setValue(0);

        });

        Object[] message = {
            " ",
            "Set the corners.",
            "Corner 1 (Top Left):",
            p1,
            " ",
            "Corner 2 (Bottom Right):",
            p2,
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
                            dispose();
                            return;
                        }

                        try {

                            if(MyApfloat.setAutomaticPrecision) {
                                long precision = MyApfloat.getAutomaticPrecision(new String[] {corner1_real.getText(), corner1_imag.getText(), corner2_real.getText(), corner2_imag.getText()}, new boolean[] {false, false, false, false});

                                if (MyApfloat.shouldSetPrecision(precision, false)) {
                                    Fractal.clearReferences(true);
                                    MyApfloat.setPrecision(precision, s);
                                }
                            }

                            Apfloat tempc1_re = new MyApfloat(corner1_real.getText());
                            Apfloat tempc1_im = new MyApfloat(corner1_imag.getText());
                            Apfloat tempc2_re = new MyApfloat(corner2_real.getText());
                            Apfloat tempc2_im = new MyApfloat(corner2_imag.getText());

                            Apfloat[] centersize = MathUtils.convertFromCornersToCenterSize(new Apfloat[]{tempc1_re, tempc1_im, tempc2_re, tempc2_im});
                            field_real.setText("" + centersize[0].toString(true));
                            field_imaginary.setText("" + centersize[1].toString(true));
                            field_size.setText("" + centersize[2]);
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
