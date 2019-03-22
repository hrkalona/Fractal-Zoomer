/*
 * Copyright (C) 2019 hrkalona2
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

import static fractalzoomer.main.Constants.MANDELBROTWTH;
import static fractalzoomer.main.Constants.MANDELPOLY;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author hrkalona2
 */
public class MandelgrassDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public MandelgrassDialog(MainWindow ptr, Settings s, JCheckBoxMenuItem mandel_grass_opt) {

        super();
        
        ptra = ptr;

        setTitle("Mandel Grass");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        JTextField field_real = new JTextField();
        field_real.setText("" + s.fns.mandel_grass_vals[0]);
        field_real.addAncestorListener(new RequestFocusListener());

        JTextField field_imaginary = new JTextField();
        field_imaginary.setText("" + s.fns.mandel_grass_vals[1]);

        Object[] message = {
            " ",
            "Set the real and imaginary part of the mandel grass.",
            "Real:", field_real,
            "Imaginary:", field_imaginary,
            " ",};

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
                        mandel_grass_opt.setSelected(false);
                        dispose();
                        return;
                    }

                    try {
                        double temp = Double.parseDouble(field_real.getText());
                        double temp2 = Double.parseDouble(field_imaginary.getText());

                        s.fns.mandel_grass_vals[0] = temp;
                        s.fns.mandel_grass_vals[1] = temp2;

                        s.fns.mandel_grass = true;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dispose();

                    if (s.fns.function <= 9 || s.fns.function == MANDELPOLY || s.fns.function == MANDELBROTWTH) {
                        ptra.defaultFractalSettings();
                    }
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
