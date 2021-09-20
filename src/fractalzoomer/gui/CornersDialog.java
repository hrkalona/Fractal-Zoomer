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
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.utils.MathUtils;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author hrkalona2
 */
public class CornersDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public CornersDialog(MainWindow ptr, Settings s, JTextField field_real, JTextField field_imaginary, JTextField field_size) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Corners");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        Apfloat tempx, tempy, tempSize;
        try {
            tempx = new MyApfloat(field_real.getText());
            tempy = new MyApfloat(field_imaginary.getText());
            tempSize =new MyApfloat(field_size.getText());
        } catch (Exception ex) {
            tempx = s.xCenter;
            tempy = s.yCenter;
            tempSize = s.size;
        }

        Apfloat[] corners = MathUtils.convertFromCenterSizeToCorners(tempx, tempy, tempSize);

        JTextField corner1_real = new JTextField(40);
        corner1_real.setText("" + corners[0].toString(true));

        JTextField corner1_imag = new JTextField(40);
        corner1_imag.setText("" + corners[1].toString(true));
        JTextField corner2_real = new JTextField(40);
        corner2_real.setText("" + corners[2].toString(true));
        JTextField corner2_imag = new JTextField(40);
        corner2_imag.setText("" + corners[3].toString(true));

        JPanel p1 = new JPanel();
        p1.add(new JLabel("Real: "));
        p1.add(corner1_real);
        p1.add(new JLabel(" Imaginary: "));
        p1.add(corner1_imag);

        JPanel p2 = new JPanel();
        p2.add(new JLabel("Real: "));
        p2.add(corner2_real);
        p2.add(new JLabel(" Imaginary: "));
        p2.add(corner2_imag);

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
            public void windowClosing(WindowEvent we) {
                optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
            }
        });

        optionPane.addPropertyChangeListener(
                new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
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
                        Apfloat tempc1_re = new MyApfloat(corner1_real.getText());
                        Apfloat tempc1_im = new MyApfloat(corner1_imag.getText());
                        Apfloat tempc2_re = new MyApfloat(corner2_real.getText());
                        Apfloat tempc2_im = new MyApfloat(corner2_imag.getText());

                        Apfloat[] centersize = MathUtils.convertFromCornersToCenterSize(new Apfloat[]{tempc1_re, tempc1_im, tempc2_re, tempc2_im});
                        field_real.setText("" + centersize[0].toString(true));
                        field_imaginary.setText("" + centersize[1].toString(true));
                        field_size.setText("" + centersize[2]);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dispose();
                }
            }
        });

        //Make this dialog display it.
        setContentPane(optionPane);

        pack();

        setResizable(false);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }

}
