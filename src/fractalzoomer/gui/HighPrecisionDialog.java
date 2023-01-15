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

import fractalzoomer.core.BigNum;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.Constants;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class HighPrecisionDialog extends JDialog {

    private Component ptra;
    private JOptionPane optionPane;
    private HighPrecisionDialog that;

    public HighPrecisionDialog(Component ptr, Settings s) {
        
        super((JFrame)ptr);

        ptra = ptr;
        that = this;

        setTitle("High Precision");
        setModal(true);

        JButton info_user = new JButton("Help");
        info_user.setToolTipText("Shows info on High Precision.");
        info_user.setFocusable(false);
        info_user.setIcon(MainWindow.getIcon("help2.png"));
        info_user.setPreferredSize(new Dimension(105, 23));

        info_user.addActionListener(e -> CommonFunctions.showHighPrecisionHelp(that));

        JPanel info_panel = new JPanel();
        info_panel.setLayout(new FlowLayout());
        info_panel.add(info_user);


        final JCheckBox enable_high_precision = new JCheckBox("High Precision");
        enable_high_precision.setSelected(ThreadDraw.HIGH_PRECISION_CALCULATION);
        enable_high_precision.setFocusable(false);

        final JCheckBox automatic_precision = new JCheckBox("Automatic Precision");
        automatic_precision.setSelected(MyApfloat.setAutomaticPrecision);
        automatic_precision.setFocusable(false);



        JTextField precision = new JTextField();
        precision.setText("" + MyApfloat.precision);
        precision.addAncestorListener(new RequestFocusListener());

        final JCheckBox automaticBignumPrecision = new JCheckBox("Automatic BigNum Precision");
        automaticBignumPrecision.setSelected(ThreadDraw.BIGNUM_AUTOMATIC_PRECISION);
        automaticBignumPrecision.setFocusable(false);


        JTextField bignumPrecision = new JTextField();
        bignumPrecision.setText("" + ThreadDraw.BIGNUM_PRECISION);

        JComboBox<String> arbitraryLibs = new JComboBox<>(new String[] {"DoubleDouble (106 bits)", "Built-in", "MPFR", "Apfloat", "Automatic"});
        arbitraryLibs.setSelectedIndex(ThreadDraw.HIGH_PRECISION_LIB);
        arbitraryLibs.setFocusable(false);

        automaticBignumPrecision.addActionListener(e -> bignumPrecision.setEnabled(!automaticBignumPrecision.isSelected() && arbitraryLibs.getSelectedIndex() != Constants.ARBITRARY_DOUBLEDOUBLE && arbitraryLibs.getSelectedIndex() != Constants.ARBITRARY_APFLOAT));

        automaticBignumPrecision.setEnabled(arbitraryLibs.getSelectedIndex() != Constants.ARBITRARY_DOUBLEDOUBLE && arbitraryLibs.getSelectedIndex() != Constants.ARBITRARY_APFLOAT);


        bignumPrecision.setEnabled(!automaticBignumPrecision.isSelected() && arbitraryLibs.getSelectedIndex() != Constants.ARBITRARY_DOUBLEDOUBLE && arbitraryLibs.getSelectedIndex() != Constants.ARBITRARY_APFLOAT );

        arbitraryLibs.addActionListener(e -> {
            bignumPrecision.setEnabled(!automaticBignumPrecision.isSelected() && arbitraryLibs.getSelectedIndex() != Constants.ARBITRARY_DOUBLEDOUBLE && arbitraryLibs.getSelectedIndex() != Constants.ARBITRARY_APFLOAT);
            automaticBignumPrecision.setEnabled(arbitraryLibs.getSelectedIndex() != Constants.ARBITRARY_DOUBLEDOUBLE && arbitraryLibs.getSelectedIndex() != Constants.ARBITRARY_APFLOAT);
        });

        Object[] message3 = {
                info_panel,
            " ",
            enable_high_precision,
                " ",
                "Floating point precision:",
                precision,
                automatic_precision,
            " ",
                "BigNum Library:",
                arbitraryLibs,
                automaticBignumPrecision,
                "BigNum bits precision:",
                bignumPrecision,
        " "};

        optionPane = new JOptionPane(message3, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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

                            int tempPrecision = Integer.parseInt(precision.getText());
                            int temp4 = Integer.parseInt(bignumPrecision.getText());

                            if (tempPrecision < 1) {
                                JOptionPane.showMessageDialog(ptra, "Precision number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp4 < 1) {
                                JOptionPane.showMessageDialog(ptra, "BigNum bits Precision number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            boolean tempAuto = automaticBignumPrecision.isSelected();

                            if(!tempAuto && tempPrecision == MyApfloat.precision) {
                                Fractal.clearReferences(true);
                                BigNum.reinitialize(temp4);
                                MpfrBigNum.reinitialize(temp4);
                            }
                            else if(tempAuto && !ThreadDraw.BIGNUM_AUTOMATIC_PRECISION && tempPrecision == MyApfloat.precision) {
                                Fractal.clearReferences(true);
                                MyApfloat.setBigNumPrecision();
                            }

                            ThreadDraw.BIGNUM_AUTOMATIC_PRECISION = tempAuto;
                            ThreadDraw.BIGNUM_PRECISION = temp4;

                            if(tempPrecision != MyApfloat.precision) {
                                MyApfloat.setPrecision(tempPrecision, s);
                            }

                            MyApfloat.setAutomaticPrecision = automatic_precision.isSelected();

                            ThreadDraw.HIGH_PRECISION_CALCULATION = enable_high_precision.isSelected();


                            ThreadDraw.HIGH_PRECISION_LIB = arbitraryLibs.getSelectedIndex();


                            ((MainWindow)ptra).setHighPrecisionPost();

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
