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

import fractalzoomer.main.MainWindow;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author hrkalona2
 */
public class ColorCyclingDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public ColorCyclingDialog(MainWindow ptr, boolean cycle_colors, boolean cycle_gradient, boolean cycle_lights, int color_cycling_speed) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Color Cycling Options");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        JCheckBox cycleColors = new JCheckBox("Cycle Colors");
        cycleColors.setFocusable(false);
        cycleColors.setSelected(cycle_colors);
        cycleColors.setToolTipText("Cycles through the palette.");

        JCheckBox cycleGradient = new JCheckBox("Cycle Gradient");
        cycleGradient.setFocusable(false);
        cycleGradient.setSelected(cycle_gradient);
        cycleGradient.setToolTipText("Cycles through the gradient.");

        JCheckBox cycleLights = new JCheckBox("Cycle Lights");
        cycleLights.setFocusable(false);
        cycleLights.setSelected(cycle_lights);
        cycleColors.setToolTipText("Rotates the light (Light/Bump Mapping).");

        final JSlider speed_slid = new JSlider(JSlider.HORIZONTAL, 0, 1000, 1000 - color_cycling_speed);

        speed_slid.setPreferredSize(new Dimension(200, 35));

        speed_slid.setToolTipText("Sets the color cycling speed.");

        speed_slid.setPaintLabels(true);
        speed_slid.setFocusable(false);

        Hashtable labelTable = new Hashtable();

        labelTable.put(speed_slid.getMinimum(), new JLabel("Slow"));
        labelTable.put(speed_slid.getMaximum(), new JLabel("Fast"));
        speed_slid.setLabelTable(labelTable);

        Object[] message3 = {
            " ",
            "Set the cycling modes.",
            cycleColors,
            cycleGradient,
            cycleLights,
            " ",
            "Set the color cycling speed.",
            speed_slid,
            " ",};

        optionPane = new JOptionPane(message3, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                        ptra.setColorCyclingOptionsPost(cycleColors.isSelected(), cycleGradient.isSelected(), cycleLights.isSelected(), speed_slid.getMaximum() - speed_slid.getValue());
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
