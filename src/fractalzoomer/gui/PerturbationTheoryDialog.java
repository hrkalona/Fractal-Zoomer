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
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.Constants;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private PerturbationTheoryDialog that;

    public PerturbationTheoryDialog(Component ptr, Settings s) {
        
        super((JFrame)ptr);

        ptra = ptr;
        that = this;

        setTitle("Perturbation Theory");
        setModal(true);

        JButton info_user = new JButton("Help");
        info_user.setToolTipText("Shows show info on Perturbation Theory.");
        info_user.setFocusable(false);
        info_user.setIcon(getIcon("/fractalzoomer/icons/help2.png"));
        info_user.setPreferredSize(new Dimension(105, 23));

        info_user.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                CommonFunctions.showPerturbationTheoryHelp(that);

            }

        });

        JPanel SApanel = new JPanel();
        JPanel FloatExpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel BLApanel = new JPanel();

        if (ptra instanceof MainWindow) {
            setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());
        } else if (ptra instanceof ImageExpanderWindow) {
            setIconImage(getIcon("/fractalzoomer/icons/mandelExpander.png").getImage());
        }

        final JCheckBox enable_perturbation = new JCheckBox("Perturbation Theory");
        enable_perturbation.setSelected(ThreadDraw.PERTURBATION_THEORY);
        enable_perturbation.setFocusable(false);

        final JCheckBox detect_period = new JCheckBox("Display Detected Period in Statistics if possible");
        detect_period.setSelected(ThreadDraw.DETECT_PERIOD);
        detect_period.setFocusable(false);

        final JCheckBox enable_bignum = new JCheckBox("Use BigNum for Reference calculation if possible");
        enable_bignum.setSelected(ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE);
        enable_bignum.setFocusable(false);

        final JCheckBox enable_bignum_pixels = new JCheckBox("Use BigNum for Pixels if possible");
        enable_bignum_pixels.setSelected(ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE);
        enable_bignum_pixels.setFocusable(false);

        final JCheckBox use_threads_for_sa = new JCheckBox("Use Threads for SA if possible");
        use_threads_for_sa.setSelected(ThreadDraw.USE_THREADS_FOR_SA);
        use_threads_for_sa.setFocusable(false);

        final JCheckBox use_threads_for_bla = new JCheckBox("Use Threads for BLA");
        use_threads_for_bla.setSelected(ThreadDraw.USE_THREADS_FOR_BLA);
        use_threads_for_bla.setFocusable(false);

        final JSlider bla_starting_level_slid = new JSlider(JSlider.HORIZONTAL, 1, 32, ThreadDraw.BLA_STARTING_LEVEL);

        bla_starting_level_slid.setPreferredSize(new Dimension(350, 55));

        bla_starting_level_slid.setToolTipText("Sets the BLA starting level.");

        bla_starting_level_slid.setPaintLabels(true);
        bla_starting_level_slid.setFocusable(false);
        bla_starting_level_slid.setPaintTicks(true);
        bla_starting_level_slid.setMajorTickSpacing(2);


        JLabel blaLevel = new JLabel("BLA Starting Level: " + bla_starting_level_slid.getValue());

        bla_starting_level_slid.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                blaLevel.setText("BLA Starting Level: " + bla_starting_level_slid.getValue());
            }
        });

        enable_bignum_pixels.setEnabled(enable_bignum.isSelected());

        final JCheckBox full_floatexp = new JCheckBox("Use FloatExp For All Iterations In Deep Zooms");
        full_floatexp.setSelected(ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM);
        full_floatexp.setFocusable(false);

        JTextField precision = new JTextField();
        precision.addAncestorListener(new RequestFocusListener());
        precision.setText("" + MyApfloat.precision);

        final JCheckBox automaticBignumPrecision = new JCheckBox("Automatic BigNum Precision");
        automaticBignumPrecision.setSelected(ThreadDraw.BIGNUM_AUTOMATIC_PRECISION);
        automaticBignumPrecision.setFocusable(false);

        JTextField bignumPrecision = new JTextField();
        bignumPrecision.setText("" + ThreadDraw.BIGNUM_PRECISION);

        final JComboBox approximation_alg = new JComboBox(new String[] {"No Approximation", "Series Approximation", "Bilinear Approximation"});
        approximation_alg.setSelectedIndex(ThreadDraw.APPROXIMATION_ALGORITHM);
        approximation_alg.setFocusable(false);
        approximation_alg.setToolTipText("Sets approximation algorithm.");

        approximation_alg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SApanel.setVisible(approximation_alg.getSelectedIndex() == 1);
                FloatExpPanel.setVisible(approximation_alg.getSelectedIndex() == 0 || approximation_alg.getSelectedIndex() == 1);
                BLApanel.setVisible(approximation_alg.getSelectedIndex() == 2);
                pack();
            }
        });

        JTextField maxSkipIter = new JTextField();
        maxSkipIter.setText("" + ThreadDraw.SERIES_APPROXIMATION_MAX_SKIP_ITER);

        enable_bignum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enable_bignum_pixels.setEnabled(enable_bignum.isSelected());
            }
        });

        final JSlider series_terms_slid = new JSlider(JSlider.HORIZONTAL, 2, 257, ThreadDraw.SERIES_APPROXIMATION_TERMS);

        series_terms_slid.setPreferredSize(new Dimension(350, 55));

        series_terms_slid.setToolTipText("Sets the terms of series approximation.");

        series_terms_slid.setPaintLabels(true);
        series_terms_slid.setFocusable(false);
        series_terms_slid.setPaintTicks(true);
        series_terms_slid.setMajorTickSpacing(20);

        JTextField seriesApproximationTolerance = new JTextField();
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

        JPanel info_panel = new JPanel();
        info_panel.setLayout(new FlowLayout());
        info_panel.add(info_user);

        bignumPrecision.setEnabled(!automaticBignumPrecision.isSelected());

        automaticBignumPrecision.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bignumPrecision.setEnabled(!automaticBignumPrecision.isSelected());
            }
        });


        SApanel.setLayout(new BoxLayout(SApanel, BoxLayout.Y_AXIS));

        SApanel.add(panel);
        SApanel.add(series_terms_slid);
        SApanel.add(new JLabel(" "));

        JPanel OOMPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        OOMPanel.add(new JLabel( "Series Approximation Orders Of Magnitude Difference:"));
        SApanel.add(OOMPanel);
        SApanel.add(seriesApproximationTolerance);
        SApanel.add(new JLabel(" "));

        JPanel maxSkipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maxSkipPanel.add(new JLabel("Maximum SA Skipped Iterations:"));
        SApanel.add(maxSkipPanel);
        SApanel.add(maxSkipIter);
        SApanel.add(new JLabel(" "));


        JPanel saThreadsPAnel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        saThreadsPAnel.add(use_threads_for_sa);
        SApanel.add(saThreadsPAnel);

        SApanel.add(new JLabel(" "));

        FloatExpPanel.add(full_floatexp);

        FloatExpPanel.add(new JLabel(" "));

        final JSlider blaBits = new JSlider(JSlider.HORIZONTAL, 1, 63, ThreadDraw.BLA_BITS);

        blaBits.setPreferredSize(new Dimension(350, 55));

        blaBits.setToolTipText("Sets the precision bits of BLA.");

        blaBits.setPaintLabels(true);
        blaBits.setFocusable(false);
        blaBits.setPaintTicks(true);
        blaBits.setMajorTickSpacing(5);


        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel blaBitsLabel = new JLabel("Precision Bits: " + blaBits.getValue());

        blaBits.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                blaBitsLabel.setText("Precision Bits: " + blaBits.getValue());
            }
        });


        panel2.add(blaBitsLabel);


        JPanel blaThreadsPAnel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        blaThreadsPAnel.add(use_threads_for_bla);

        JPanel blaSkipLevel1PAnel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        blaSkipLevel1PAnel.add(bla_starting_level_slid);


        JPanel blaSkipLevel1PAnelLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        blaSkipLevel1PAnelLabel.add(blaLevel);

        BLApanel.setLayout(new BoxLayout(BLApanel, BoxLayout.Y_AXIS));

        BLApanel.add(panel2);
        BLApanel.add(blaBits);
        BLApanel.add(new JLabel(" "));
        BLApanel.add(blaSkipLevel1PAnelLabel);
        BLApanel.add(blaSkipLevel1PAnel);
        BLApanel.add(new JLabel(" "));
        BLApanel.add(blaThreadsPAnel);
        BLApanel.add(new JLabel(" "));

        SApanel.setVisible(approximation_alg.getSelectedIndex() == 1);
        FloatExpPanel.setVisible(approximation_alg.getSelectedIndex() == 0 || approximation_alg.getSelectedIndex() == 1);
        BLApanel.setVisible(approximation_alg.getSelectedIndex() == 2);


        Object[] message3 = {
            info_panel,
            " ",
            enable_perturbation,
            " ",
            "Floating point precision:",
            precision,
            " ",
                enable_bignum,
                enable_bignum_pixels,
                automaticBignumPrecision,
                "BigNum bits precision:",
                bignumPrecision,
                " ",
                detect_period,
                " ",
                approximation_alg,
                " ",
                SApanel,
                BLApanel,
                FloatExpPanel,};

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
                        int tempPrecision = Integer.parseInt(precision.getText());
                        long temp2 = Long.parseLong(seriesApproximationTolerance.getText());
                        int temp3 = Integer.parseInt(maxSkipIter.getText());
                        int temp4 = Integer.parseInt(bignumPrecision.getText());

                        if (tempPrecision < 1) {
                            JOptionPane.showMessageDialog(ptra, "Precision number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (temp4 < 1) {
                            JOptionPane.showMessageDialog(ptra, "BigNum bits Precision number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (temp3 < 0) {
                            JOptionPane.showMessageDialog(ptra, "Maximum skipped iterations must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        ThreadDraw.PERTURBATION_THEORY = enable_perturbation.isSelected();
                        boolean tempBigNum = enable_bignum.isSelected();
                        if(!ThreadDraw.PERTURBATION_THEORY || tempPrecision != MyApfloat.precision || tempBigNum != ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE) {
                            Fractal.clearReferences();
                        }

                        boolean tempAuto = automaticBignumPrecision.isSelected();

                        if(!tempAuto && tempPrecision == MyApfloat.precision) {
                            Fractal.clearReferences();
                            BigNum.reinitialize(temp4);
                        }
                        else if(tempAuto && (tempAuto != ThreadDraw.BIGNUM_AUTOMATIC_PRECISION) && tempPrecision == MyApfloat.precision) {
                            Fractal.clearReferences();
                            MyApfloat.setBigNumPrecision();
                        }

                        boolean oldDetectedPeriod =  ThreadDraw.DETECT_PERIOD;
                        ThreadDraw.DETECT_PERIOD = detect_period.isSelected();

                        if(oldDetectedPeriod != ThreadDraw.DETECT_PERIOD) {
                            Fractal.clearReferences();
                        }

                        ThreadDraw.BIGNUM_AUTOMATIC_PRECISION = tempAuto;
                        ThreadDraw.BIGNUM_PRECISION = temp4;

                        if(tempPrecision != MyApfloat.precision) {
                            MyApfloat.setPrecision(tempPrecision, s);
                        }

                        int oldApproximationAlg = ThreadDraw.APPROXIMATION_ALGORITHM;
                        ThreadDraw.APPROXIMATION_ALGORITHM = approximation_alg.getSelectedIndex();

                        if(oldApproximationAlg != ThreadDraw.APPROXIMATION_ALGORITHM) {
                            Fractal.clearReferences();
                        }


                        ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE = tempBigNum;
                        ThreadDraw.APPROXIMATION_ALGORITHM = approximation_alg.getSelectedIndex();
                        ThreadDraw.SERIES_APPROXIMATION_TERMS = series_terms_slid.getValue();
                        ThreadDraw.SERIES_APPROXIMATION_OOM_DIFFERENCE = temp2;
                        ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM = full_floatexp.isSelected();
                        ThreadDraw.SERIES_APPROXIMATION_MAX_SKIP_ITER = temp3;
                        ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE = enable_bignum_pixels.isSelected();
                        ThreadDraw.USE_THREADS_FOR_SA = use_threads_for_sa.isSelected();
                        ThreadDraw.BLA_BITS = blaBits.getValue();
                        ThreadDraw.BLA_STARTING_LEVEL = bla_starting_level_slid.getValue();
                        ThreadDraw.USE_THREADS_FOR_BLA = use_threads_for_bla.isSelected();

                        if(ptra instanceof MainWindow) {
                            ((MainWindow)ptra).setPerturbationTheoryPost();
                        }
                        else if(ptra instanceof ImageExpanderWindow) {
                            ((ImageExpanderWindow)ptra).setPerturbationTheoryPost();
                        }

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
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
