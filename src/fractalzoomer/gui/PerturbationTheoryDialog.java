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
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import org.apfloat.Apfloat;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author hrkalona2
 */
public class PerturbationTheoryDialog extends JDialog {

    private Component ptra;
    private JOptionPane optionPane;

    public PerturbationTheoryDialog(Component ptr, Settings s) {
        
        super((JFrame)ptr);

        ptra = ptr;

        setTitle("Perturbation Theory");
        setModal(true);

        if (ptra instanceof MainWindow) {
            setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());
        } else if (ptra instanceof ImageExpanderWindow) {
            setIconImage(getIcon("/fractalzoomer/icons/mandelExpander.png").getImage());
        }

        final JCheckBox enable_perturbation = new JCheckBox("Perturbation Theory");
        enable_perturbation.setSelected(ThreadDraw.PERTURBATION_THEORY);
        enable_perturbation.setFocusable(false);

        final JCheckBox full_reference = new JCheckBox("Calculate Full Reference (Without Bailout for Max Iterations)");
        full_reference.setSelected(ThreadDraw.CALCULATE_FULL_REFERENCE);
        full_reference.setFocusable(false);

        final JCheckBox full_floatexp = new JCheckBox("Use FloatExp For All Iterations In Deep Zooms");
        full_floatexp.setSelected(ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM);
        full_floatexp.setFocusable(false);

        JTextField precision = new JTextField();
        precision.addAncestorListener(new RequestFocusListener());
        precision.setText("" + MyApfloat.precision);

        final JCheckBox enable_series_approximation = new JCheckBox("Series Approximation");
        enable_series_approximation.setSelected(ThreadDraw.SERIES_APPROXIMATION);
        enable_series_approximation.setFocusable(false);

        final JSlider series_terms_slid = new JSlider(JSlider.HORIZONTAL, 2, 129, ThreadDraw.SERIES_APPROXIMATION_TERMS);

        series_terms_slid.setPreferredSize(new Dimension(350, 55));

        series_terms_slid.setToolTipText("Sets the terms of series approximation.");

        series_terms_slid.setPaintLabels(true);
        series_terms_slid.setFocusable(false);
        series_terms_slid.setPaintTicks(true);
        series_terms_slid.setMajorTickSpacing(10);

        JTextField seriesApproximationTolerance = new JTextField();
        seriesApproximationTolerance.addAncestorListener(new RequestFocusListener());
        seriesApproximationTolerance.setText("" + ThreadDraw.SERIES_APPROXIMATION_OOM_DIFFERENCE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel saTerms = new JLabel("Series Approximation Terms: " + series_terms_slid.getValue());

        series_terms_slid.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                saTerms.setText("Series Approximation Terms: " + series_terms_slid.getValue());
            }
        });


        panel.add(saTerms);

        Object[] message3 = {
            " ",
            enable_perturbation,
            " ",
            "Floating point precision:",
            precision,
            " ",
                full_reference,
                " ",
                full_floatexp,
                " ",
            enable_series_approximation,
                " ",
            panel,
                series_terms_slid,
                " ",
                "Series Approximation Orders Of Magnitude Difference:",
                seriesApproximationTolerance,

                " "};

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
                        int temp = Integer.parseInt(precision.getText());
                        long temp2 = Long.parseLong(seriesApproximationTolerance.getText());

                        if (temp < 1) {
                            JOptionPane.showMessageDialog(ptra, "Precision number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }


                        ThreadDraw.PERTURBATION_THEORY = enable_perturbation.isSelected();
                        if(!ThreadDraw.PERTURBATION_THEORY || temp != MyApfloat.precision) {
                            Fractal.clearReferences();
                        }

                        if(temp != MyApfloat.precision) {
                            MyApfloat.setPrecision(temp, s);
                        }
                        ThreadDraw.SERIES_APPROXIMATION = enable_series_approximation.isSelected();
                        ThreadDraw.SERIES_APPROXIMATION_TERMS = series_terms_slid.getValue();
                        ThreadDraw.SERIES_APPROXIMATION_OOM_DIFFERENCE = temp2;
                        ThreadDraw.CALCULATE_FULL_REFERENCE = full_reference.isSelected();
                        ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM = full_floatexp.isSelected();

                        if(ptra instanceof MainWindow) {
                            ((MainWindow)ptra).setPerturbationTheoryPost();
                        }
                        else if(ptra instanceof ImageExpanderWindow) {
                            ((ImageExpanderWindow)ptra).setPerturbationTheoryPost();
                        }

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
