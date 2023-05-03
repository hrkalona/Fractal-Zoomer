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
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.core.la.LAInfo;
import fractalzoomer.core.la.LAInfoDeep;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.LibMpir;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.Constants;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.ApproximationDefaultSettings;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        info_user.setToolTipText("Shows info on Perturbation Theory.");
        info_user.setFocusable(false);
        info_user.setIcon(MainWindow.getIcon("help2.png"));
        info_user.setPreferredSize(new Dimension(105, 23));

        info_user.addActionListener(e -> CommonFunctions.showPerturbationTheoryHelp(that));

        JButton reset_approximation = new JButton();
        reset_approximation.setToolTipText("Resets the approximation values.");
        reset_approximation.setFocusable(false);
        reset_approximation.setIcon(MainWindow.getIcon("reset_small.png"));
        reset_approximation.setPreferredSize(new Dimension(23, 23));


        JPanel SApanel = new JPanel();
        JPanel FloatExpPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel BLApanel = new JPanel();
        JPanel BLA2panel = new JPanel();
        JPanel Nanomb1Panel = new JPanel();

        if (ptra instanceof MainWindow) {
            setIconImage(MainWindow.getIcon("mandel2.png").getImage());
        } else if (ptra instanceof ImageExpanderWindow) {
            setIconImage(MainWindow.getIcon("mandelExpander.png").getImage());
        }

        final JCheckBox enable_perturbation = new JCheckBox("Perturbation Theory");
        enable_perturbation.setSelected(ThreadDraw.PERTURBATION_THEORY);
        enable_perturbation.setFocusable(false);

        final JCheckBox detect_period = new JCheckBox("Detect Period if possible");
        detect_period.setSelected(ThreadDraw.DETECT_PERIOD);
        detect_period.setFocusable(false);

        final JCheckBox automatic_precision = new JCheckBox("Automatic Precision");
        automatic_precision.setSelected(MyApfloat.setAutomaticPrecision);
        automatic_precision.setFocusable(false);

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

        bla_starting_level_slid.setPreferredSize(new Dimension(300, 55));

        bla_starting_level_slid.setToolTipText("Sets the BLA starting level.");

        bla_starting_level_slid.setPaintLabels(true);
        bla_starting_level_slid.setFocusable(false);
        bla_starting_level_slid.setPaintTicks(true);
        bla_starting_level_slid.setMajorTickSpacing(2);


        JLabel blaLevel = new JLabel("BLA Starting Level: " + bla_starting_level_slid.getValue());

        bla_starting_level_slid.addChangeListener(e -> blaLevel.setText("BLA Starting Level: " + bla_starting_level_slid.getValue()));

        final JCheckBox full_floatexp_for_deep_zooms = new JCheckBox("Use FloatExp For All Iterations In Deep Zooms");
        full_floatexp_for_deep_zooms.setSelected(ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM);
        full_floatexp_for_deep_zooms.setFocusable(false);

        final JCheckBox full_floatexp_for_all_zooms = new JCheckBox("Use FloatExp For All Iterations In All Zooms");
        full_floatexp_for_all_zooms.setSelected(ThreadDraw.USE_FULL_FLOATEXP_FOR_ALL_ZOOM);
        full_floatexp_for_all_zooms.setFocusable(false);

        FloatExpPanel2.add(full_floatexp_for_deep_zooms);
        FloatExpPanel2.add(full_floatexp_for_all_zooms);

        JTextField precision = new JTextField();
        precision.setText("" + MyApfloat.precision);
        precision.addAncestorListener(new RequestFocusListener());

        final JCheckBox automaticBignumPrecision = new JCheckBox("Automatic BigNum Precision");
        automaticBignumPrecision.setSelected(ThreadDraw.BIGNUM_AUTOMATIC_PRECISION);
        automaticBignumPrecision.setFocusable(false);

        final JCheckBox gatherPerturbationStatistics = new JCheckBox("Display Perturbation Statistics");
        gatherPerturbationStatistics.setSelected(ThreadDraw.GATHER_PERTURBATION_STATISTICS);
        gatherPerturbationStatistics.setFocusable(false);

        JTextField bignumPrecision = new JTextField();
        bignumPrecision.setText("" + ThreadDraw.BIGNUM_PRECISION);

        final JComboBox<String> approximation_alg = new JComboBox<>(new String[] {"No Approximation", "Series Approximation", "Bilinear Approximation (claude)", "Nanomb1", "Bilinear Approximation (Zhuoran)"});
        approximation_alg.setSelectedIndex(ThreadDraw.APPROXIMATION_ALGORITHM);
        approximation_alg.setFocusable(false);
        approximation_alg.setToolTipText("Sets approximation algorithm.");

        final JComboBox<String> pixelAlgorithm = new JComboBox<>(new String[] {"Not Scaled", "Scaled"});
        pixelAlgorithm.setSelectedIndex(ThreadDraw.PERTUBATION_PIXEL_ALGORITHM);
        pixelAlgorithm.setFocusable(false);
        pixelAlgorithm.setToolTipText("Sets the deep zoom pixel calculation algorithm.");

        JLabel pixelLabel = new JLabel("Deep Zoom Pixel Calculation Algorithm:");
        JLabel pixelLabel2 = new JLabel(" ");

        approximation_alg.addActionListener(e -> {
            SApanel.setVisible(approximation_alg.getSelectedIndex() == 1);
            full_floatexp_for_deep_zooms.setEnabled(!full_floatexp_for_all_zooms.isSelected()  && (approximation_alg.getSelectedIndex() == 0 || approximation_alg.getSelectedIndex() == 1 || approximation_alg.getSelectedIndex() == 3 || approximation_alg.getSelectedIndex() == 4));
            pixelAlgorithm.setVisible(approximation_alg.getSelectedIndex() == 0 || approximation_alg.getSelectedIndex() == 1 || approximation_alg.getSelectedIndex() == 3);
            pixelLabel.setVisible(approximation_alg.getSelectedIndex() == 0 || approximation_alg.getSelectedIndex() == 1 || approximation_alg.getSelectedIndex() == 3);
            pixelLabel2.setVisible(approximation_alg.getSelectedIndex() == 0 || approximation_alg.getSelectedIndex() == 1 || approximation_alg.getSelectedIndex() == 3);
            BLApanel.setVisible(approximation_alg.getSelectedIndex() == 2);
            BLA2panel.setVisible(approximation_alg.getSelectedIndex() == 4);
            Nanomb1Panel.setVisible(approximation_alg.getSelectedIndex() == 3);
            pack();
        });

        JTextField maxSkipIter = new JTextField();
        maxSkipIter.setText("" + ThreadDraw.SERIES_APPROXIMATION_MAX_SKIP_ITER);

        final JSlider series_terms_slid = new JSlider(JSlider.HORIZONTAL, 2, 257, ThreadDraw.SERIES_APPROXIMATION_TERMS);

        series_terms_slid.setPreferredSize(new Dimension(300, 55));

        series_terms_slid.setToolTipText("Sets the terms of series approximation.");

        series_terms_slid.setPaintLabels(true);
        series_terms_slid.setFocusable(false);
        series_terms_slid.setPaintTicks(true);
        series_terms_slid.setMajorTickSpacing(20);

        JTextField seriesApproximationTolerance = new JTextField();
        seriesApproximationTolerance.setText("" + ThreadDraw.SERIES_APPROXIMATION_OOM_DIFFERENCE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel saTerms = new JLabel("Series Approximation Terms: " + series_terms_slid.getValue());

        series_terms_slid.addChangeListener(e -> saTerms.setText("Series Approximation Terms: " + series_terms_slid.getValue()));


        panel.add(saTerms);

        JPanel info_panel = new JPanel();
        info_panel.setLayout(new FlowLayout());
        info_panel.add(info_user);

        JPanel sa_panel2 = new JPanel();
        sa_panel2.setLayout(new GridLayout(2, 2));
        sa_panel2.add(new JLabel("SA Orders Of Magnitude Difference:", SwingConstants.HORIZONTAL));
        sa_panel2.add(new JLabel("Maximum SA Skipped Iterations:", SwingConstants.HORIZONTAL));
        sa_panel2.add(seriesApproximationTolerance);
        sa_panel2.add(maxSkipIter);


        Nanomb1Panel.setLayout(new BoxLayout(Nanomb1Panel, BoxLayout.Y_AXIS));

        final JSlider nanomb1N = new JSlider(JSlider.HORIZONTAL, 2, 48, ThreadDraw.NANOMB1_N);

        nanomb1N.setPreferredSize(new Dimension(300, 55));

        nanomb1N.setToolTipText("Sets Nanomb1 N value.");

        nanomb1N.setPaintLabels(true);
        nanomb1N.setFocusable(false);
        nanomb1N.setPaintTicks(true);
        nanomb1N.setMajorTickSpacing(4);


        final JSlider nanomb1M = new JSlider(JSlider.HORIZONTAL, 2, 48, ThreadDraw.NANOMB1_M);

        nanomb1M.setPreferredSize(new Dimension(300, 55));

        nanomb1M.setToolTipText("Sets Nanomb1 M value.");

        nanomb1M.setPaintLabels(true);
        nanomb1M.setFocusable(false);
        nanomb1M.setPaintTicks(true);
        nanomb1M.setMajorTickSpacing(4);

        JPanel nanopanelN = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel nanolabelN = new JLabel("N: " + nanomb1N.getValue());

        JPanel nanopanelM = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel nanolabelM = new JLabel("M: " + nanomb1M.getValue());

        nanomb1N.addChangeListener(e -> {nanolabelN.setText("N: " + nanomb1N.getValue());});
        nanomb1M.addChangeListener(e -> {nanolabelM.setText("M: " + nanomb1M.getValue());});

        nanopanelN.add(nanolabelN);
        nanopanelM.add(nanolabelM);

        Nanomb1Panel.add(nanopanelM);
        Nanomb1Panel.add(nanomb1M);
        Nanomb1Panel.add(nanopanelN);
        Nanomb1Panel.add(nanomb1N);


        SApanel.setLayout(new BoxLayout(SApanel, BoxLayout.Y_AXIS));

        SApanel.add(panel);
        SApanel.add(series_terms_slid);
        SApanel.add(new JLabel(" "));
        SApanel.add(sa_panel2);
        SApanel.add(new JLabel(" "));


        JPanel saThreadsPAnel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        saThreadsPAnel.add(use_threads_for_sa);
        SApanel.add(saThreadsPAnel);

        full_floatexp_for_deep_zooms.setEnabled(!full_floatexp_for_all_zooms.isSelected() && (approximation_alg.getSelectedIndex() == 0 || approximation_alg.getSelectedIndex() == 1 || approximation_alg.getSelectedIndex() == 3 || approximation_alg.getSelectedIndex() == 4));

        full_floatexp_for_all_zooms.addActionListener(e -> full_floatexp_for_deep_zooms.setEnabled(!full_floatexp_for_all_zooms.isSelected() && (approximation_alg.getSelectedIndex() == 0 || approximation_alg.getSelectedIndex() == 1 || approximation_alg.getSelectedIndex() == 3 || approximation_alg.getSelectedIndex() == 4)));

        final JSlider blaBits = new JSlider(JSlider.HORIZONTAL, 1, 63, ThreadDraw.BLA_BITS);

        blaBits.setPreferredSize(new Dimension(300, 55));

        blaBits.setToolTipText("Sets the precision bits of BLA.");

        blaBits.setPaintLabels(true);
        blaBits.setFocusable(false);
        blaBits.setPaintTicks(true);
        blaBits.setMajorTickSpacing(5);


        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel blaBitsLabel = new JLabel("Precision Bits: " + blaBits.getValue());

        blaBits.addChangeListener(e -> blaBitsLabel.setText("Precision Bits: " + blaBits.getValue()));


        panel2.add(blaBitsLabel);


        JPanel blaThreadsPAnel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        blaThreadsPAnel.add(use_threads_for_bla);


        JPanel blaSkipLevel1PAnelLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        blaSkipLevel1PAnelLabel.add(blaLevel);

        BLApanel.setLayout(new BoxLayout(BLApanel, BoxLayout.Y_AXIS));

        BLApanel.add(panel2);
        BLApanel.add(blaBits);
        BLApanel.add(new JLabel(" "));
        BLApanel.add(blaSkipLevel1PAnelLabel);
        BLApanel.add(bla_starting_level_slid);
        BLApanel.add(new JLabel(" "));
        BLApanel.add(blaThreadsPAnel);

        SApanel.setVisible(approximation_alg.getSelectedIndex() == 1);
        pixelAlgorithm.setVisible(approximation_alg.getSelectedIndex() == 0 || approximation_alg.getSelectedIndex() == 1 || approximation_alg.getSelectedIndex() == 3);
        pixelLabel.setVisible(approximation_alg.getSelectedIndex() == 0 || approximation_alg.getSelectedIndex() == 1 || approximation_alg.getSelectedIndex() == 3);
        pixelLabel2.setVisible(approximation_alg.getSelectedIndex() == 0 || approximation_alg.getSelectedIndex() == 1 || approximation_alg.getSelectedIndex() == 3);
        BLApanel.setVisible(approximation_alg.getSelectedIndex() == 2);
        BLA2panel.setVisible(approximation_alg.getSelectedIndex() == 4);
        Nanomb1Panel.setVisible(approximation_alg.getSelectedIndex() == 3);

        JComboBox<String> bigNumLibs = new JComboBox<>(new String[] {"Double (53 bits)", "DoubleDouble (106 bits)", "Built-in", "MPFR", "Automatic", "MPIR", "Automatic (No Double/DoubleDouble)"});
        bigNumLibs.setSelectedIndex(ThreadDraw.BIGNUM_LIBRARY);
        JLabel bnliblabel = new JLabel("BigNum Library:");
        bigNumLibs.setFocusable(false);

        bigNumLibs.setEnabled(enable_bignum.isSelected());


        bignumPrecision.setEnabled(enable_bignum.isSelected() && !automaticBignumPrecision.isSelected() && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLE && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLEDOUBLE);

        automaticBignumPrecision.addActionListener(e -> bignumPrecision.setEnabled(enable_bignum.isSelected() && !automaticBignumPrecision.isSelected() && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLE && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLEDOUBLE));

        enable_bignum_pixels.setEnabled(enable_bignum.isSelected());

        automaticBignumPrecision.setEnabled(enable_bignum.isSelected() && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLE && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLEDOUBLE);

        bigNumLibs.addActionListener(e -> {
            bignumPrecision.setEnabled(enable_bignum.isSelected() && !automaticBignumPrecision.isSelected() && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLE && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLEDOUBLE);
            automaticBignumPrecision.setEnabled(enable_bignum.isSelected()&& bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLE && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLEDOUBLE);
        });

        enable_bignum.addActionListener(e -> {
            bigNumLibs.setEnabled(enable_bignum.isSelected());
            enable_bignum_pixels.setEnabled(enable_bignum.isSelected());
            bignumPrecision.setEnabled(enable_bignum.isSelected() && !automaticBignumPrecision.isSelected() && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLE && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLEDOUBLE);
            automaticBignumPrecision.setEnabled(enable_bignum.isSelected()&& bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLE && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLEDOUBLE);
        });

        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statsPanel.add(detect_period);
        statsPanel.add(gatherPerturbationStatistics);

        JPanel enable_bignum_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        enable_bignum_panel.add(enable_bignum);
        enable_bignum_panel.add(enable_bignum_pixels);

        JPanel autoBigNumPrecPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        autoBigNumPrecPanel.add(automaticBignumPrecision);

        JComboBox<String> bla2DetectionMethod = new JComboBox<>(new String[] {"Algorithm 1", "Algorithm 2"});
        bla2DetectionMethod.setSelectedIndex(LAInfo.DETECTION_METHOD);
        bla2DetectionMethod.setFocusable(false);

        BLA2panel.setLayout(new BoxLayout(BLA2panel, BoxLayout.Y_AXIS));

        JPanel detectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        detectionPanel.add(new JLabel("Detection Method:"));

        JPanel bla2Thresholds = new JPanel();
        bla2Thresholds.setLayout(new GridLayout(5, 2));
        bla2Thresholds.add(new JLabel("Stage 0 Period limit:", SwingConstants.HORIZONTAL));
        bla2Thresholds.add(new JLabel("Period limit:", SwingConstants.HORIZONTAL));
        JTextField stage0 = new JTextField();
        stage0.setText("" + LAInfo.Stage0PeriodDetectionThreshold);
        JTextField plimit = new JTextField();
        plimit.setText("" + LAInfo.PeriodDetectionThreshold);

        JTextField stage01 = new JTextField();
        stage01.setText("" + LAInfo.Stage0PeriodDetectionThreshold2);
        JTextField plimit1 = new JTextField();
        plimit1.setText("" + LAInfo.PeriodDetectionThreshold2);


        bla2Thresholds.add(stage0);
        bla2Thresholds.add(plimit);
        bla2Thresholds.add(stage01);
        bla2Thresholds.add(plimit1);

        bla2Thresholds.add(new JLabel("LA scale limit:", SwingConstants.HORIZONTAL));
        bla2Thresholds.add(new JLabel("LA C scale limit:", SwingConstants.HORIZONTAL));

        JTextField lascale = new JTextField();
        lascale.setText("" + LAInfo.LAThresholdScale);
        JTextField lacscale = new JTextField();
        lacscale.setText("" + LAInfo.LAThresholdCScale);

        bla2Thresholds.add(lascale);
        bla2Thresholds.add(lacscale);

        stage0.setEnabled(bla2DetectionMethod.getSelectedIndex() == 0);
        plimit.setEnabled(bla2DetectionMethod.getSelectedIndex() == 0);

        stage01.setEnabled(bla2DetectionMethod.getSelectedIndex() == 1);
        plimit1.setEnabled(bla2DetectionMethod.getSelectedIndex() == 1);

        bla2DetectionMethod.addActionListener(e->{
            stage0.setEnabled(bla2DetectionMethod.getSelectedIndex() == 0);
            plimit.setEnabled(bla2DetectionMethod.getSelectedIndex() == 0);

            stage01.setEnabled(bla2DetectionMethod.getSelectedIndex() == 1);
            plimit1.setEnabled(bla2DetectionMethod.getSelectedIndex() == 1);
        });

        BLA2panel.add(detectionPanel);
        BLA2panel.add(bla2DetectionMethod);
        BLA2panel.add(new JLabel(" "));
        BLA2panel.add(bla2Thresholds);
        BLA2panel.add(new JLabel(" "));

        reset_approximation.addActionListener(e -> {

            stage0.setText("" + ApproximationDefaultSettings.Stage0PeriodDetectionThreshold);
            plimit.setText("" + ApproximationDefaultSettings.PeriodDetectionThreshold);

            stage01.setText("" + ApproximationDefaultSettings.Stage0PeriodDetectionThreshold2);
            plimit1.setText("" + ApproximationDefaultSettings.PeriodDetectionThreshold2);

            lascale.setText("" + ApproximationDefaultSettings.LAThresholdScale);
            lacscale.setText("" + ApproximationDefaultSettings.LAThresholdCScale);

            bla2DetectionMethod.setSelectedIndex(ApproximationDefaultSettings.DETECTION_METHOD);

            seriesApproximationTolerance.setText("" + ApproximationDefaultSettings.SERIES_APPROXIMATION_OOM_DIFFERENCE);
            maxSkipIter.setText("" + ApproximationDefaultSettings.SERIES_APPROXIMATION_MAX_SKIP_ITER);
            series_terms_slid.setValue(ApproximationDefaultSettings.SERIES_APPROXIMATION_TERMS);

            nanomb1M.setValue(ApproximationDefaultSettings.NANOMB1_M);
            nanomb1N.setValue(ApproximationDefaultSettings.NANOMB1_N);

            blaBits.setValue(ApproximationDefaultSettings.BLA_BITS);
            bla_starting_level_slid.setValue(ApproximationDefaultSettings.BLA_STARTING_LEVEL);


        });

        JPanel appr = new JPanel(new FlowLayout(FlowLayout.LEFT));
        appr.add(new JLabel("Approximation:"));
        appr.add(Box.createRigidArea(new Dimension(300, 10)));
        appr.add(reset_approximation);


        Object[] message3 = {
            info_panel,
            " ",
            enable_perturbation,
            " ",
            "Floating point precision:",
            precision,
                automatic_precision,
            " ",
                enable_bignum_panel,
                bnliblabel,
                bigNumLibs,
                autoBigNumPrecPanel,
                "BigNum bits precision:",
                bignumPrecision,
                " ",
                statsPanel,
                appr,
                approximation_alg,
                " ",
                SApanel,
                BLApanel,
                BLA2panel,
                Nanomb1Panel,
                FloatExpPanel2,
                pixelLabel2,
                pixelLabel,
                pixelAlgorithm};

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
                            long temp2 = Long.parseLong(seriesApproximationTolerance.getText());
                            int temp3 = Integer.parseInt(maxSkipIter.getText());
                            int temp4 = Integer.parseInt(bignumPrecision.getText());

                            double temp5 = Double.parseDouble(stage0.getText());
                            double temp6 = Double.parseDouble(plimit.getText());
                            double temp7 = Double.parseDouble(stage01.getText());
                            double temp8 = Double.parseDouble(plimit1.getText());
                            double temp9 = Double.parseDouble(lascale.getText());
                            double temp10 = Double.parseDouble(lacscale.getText());

                            if(temp5 <= 0 || temp6 <= 0 || temp7 <= 0 || temp8 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The stage 0 and period limit values must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp5 > 2.5 || temp6 > 2.5 || temp7 > 2.5 || temp8 > 2.5) {
                                JOptionPane.showMessageDialog(ptra, "The stage 0 period limit and period limit values must less than 2.5.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp9 <= 0 || temp10 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The LA scale limit values must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

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
                                Fractal.clearReferences(true);
                            }

                            boolean tempAuto = automaticBignumPrecision.isSelected();

                            if(!tempAuto && tempPrecision == MyApfloat.precision) {
                                Fractal.clearReferences(true);
                                BigNum.reinitialize(temp4);
                                MpfrBigNum.reinitialize(temp4);
                                MpirBigNum.reinitialize(temp4);
                            }
                            else if(tempAuto && !ThreadDraw.BIGNUM_AUTOMATIC_PRECISION && tempPrecision == MyApfloat.precision) {
                                Fractal.clearReferences(true);
                                MyApfloat.setBigNumPrecision();
                            }

                            int oldApproximationAlg = ThreadDraw.APPROXIMATION_ALGORITHM;
                            ThreadDraw.APPROXIMATION_ALGORITHM = approximation_alg.getSelectedIndex();

                            if(oldApproximationAlg != ThreadDraw.APPROXIMATION_ALGORITHM) {
                                Fractal.clearReferences(true);
                            }

                            boolean oldDetectedPeriod =  ThreadDraw.DETECT_PERIOD;
                            ThreadDraw.DETECT_PERIOD = detect_period.isSelected();

                            if(oldDetectedPeriod != ThreadDraw.DETECT_PERIOD) {
                                if(ThreadDraw.DETECT_PERIOD || ThreadDraw.APPROXIMATION_ALGORITHM == 3) {
                                    Fractal.clearReferences(true);
                                }
                                else {
                                    if(ThreadDraw.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD) {
                                        Fractal.clearReferences(true);
                                    }
                                    else {
                                       // Fractal.DetectedAtomPeriod = 0;
                                        Fractal.DetectedPeriod = 0;
                                    }
                                }
                            }

                            boolean oldGatherStatistics = ThreadDraw.GATHER_PERTURBATION_STATISTICS;
                            ThreadDraw.GATHER_PERTURBATION_STATISTICS = gatherPerturbationStatistics.isSelected();

                            if(oldGatherStatistics != ThreadDraw.GATHER_PERTURBATION_STATISTICS && ThreadDraw.GATHER_PERTURBATION_STATISTICS) {
                                Fractal.clearReferences(true);
                            }

                            int oldBignumLib = ThreadDraw.BIGNUM_LIBRARY;
                            ThreadDraw.BIGNUM_LIBRARY = bigNumLibs.getSelectedIndex();
                            if(oldBignumLib != ThreadDraw.BIGNUM_LIBRARY) {
                                Fractal.clearReferences(true);
                            }

                            boolean oldUseFullFloatExp = ThreadDraw.USE_FULL_FLOATEXP_FOR_ALL_ZOOM;
                            ThreadDraw.USE_FULL_FLOATEXP_FOR_ALL_ZOOM = full_floatexp_for_all_zooms.isSelected();
                            if(oldUseFullFloatExp != ThreadDraw.USE_FULL_FLOATEXP_FOR_ALL_ZOOM){
                                Fractal.clearReferences(true);
                            }

                            if(ThreadDraw.PERTURBATION_THEORY && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && ThreadDraw.BIGNUM_LIBRARY == Constants.BIGNUM_MPFR && LibMpfr.hasError()) {
                                JOptionPane.showMessageDialog(ptra, "The MPFR library is not available, and the engine will fallback to an alternative library.", "Warning!", JOptionPane.WARNING_MESSAGE);
                            }

                            if(ThreadDraw.PERTURBATION_THEORY && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && ThreadDraw.BIGNUM_LIBRARY == Constants.BIGNUM_MPIR && LibMpir.hasError()) {
                                JOptionPane.showMessageDialog(ptra, "The MPIR library is not available, and the engine will fallback to an alternative library.", "Warning!", JOptionPane.WARNING_MESSAGE);
                            }

                            ThreadDraw.BIGNUM_AUTOMATIC_PRECISION = tempAuto;
                            ThreadDraw.BIGNUM_PRECISION = temp4;

                            if(tempPrecision != MyApfloat.precision) {
                                MyApfloat.setPrecision(tempPrecision, s);
                            }

                            int oldNanomb1N = ThreadDraw.NANOMB1_N;
                            ThreadDraw.NANOMB1_N = nanomb1N.getValue();

                            int oldNanomb1M = ThreadDraw.NANOMB1_M;
                            ThreadDraw.NANOMB1_M = nanomb1M.getValue();

                            if(oldNanomb1N != ThreadDraw.NANOMB1_N || oldNanomb1M != ThreadDraw.NANOMB1_M) {
                                Fractal.clearReferences(true);
                            }

                            int oldPixelAlgorithm = ThreadDraw.PERTUBATION_PIXEL_ALGORITHM;
                            ThreadDraw.PERTUBATION_PIXEL_ALGORITHM = pixelAlgorithm.getSelectedIndex();
                            if(oldPixelAlgorithm != ThreadDraw.PERTUBATION_PIXEL_ALGORITHM) {
                                Fractal.clearReferences(true);
                            }

                            MyApfloat.setAutomaticPrecision = automatic_precision.isSelected();

                            ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE = tempBigNum;
                            ThreadDraw.APPROXIMATION_ALGORITHM = approximation_alg.getSelectedIndex();
                            ThreadDraw.SERIES_APPROXIMATION_TERMS = series_terms_slid.getValue();
                            ThreadDraw.SERIES_APPROXIMATION_OOM_DIFFERENCE = temp2;
                            ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM = full_floatexp_for_deep_zooms.isSelected();
                            ThreadDraw.SERIES_APPROXIMATION_MAX_SKIP_ITER = temp3;
                            ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE = enable_bignum_pixels.isSelected();
                            ThreadDraw.USE_THREADS_FOR_SA = use_threads_for_sa.isSelected();
                            ThreadDraw.BLA_BITS = blaBits.getValue();
                            ThreadDraw.BLA_STARTING_LEVEL = bla_starting_level_slid.getValue();
                            ThreadDraw.USE_THREADS_FOR_BLA = use_threads_for_bla.isSelected();

                            LAInfo.DETECTION_METHOD = bla2DetectionMethod.getSelectedIndex();
                            LAInfo.Stage0PeriodDetectionThreshold = temp5;
                            LAInfo.PeriodDetectionThreshold = temp6;
                            LAInfo.Stage0PeriodDetectionThreshold2 = temp7;
                            LAInfo.PeriodDetectionThreshold2 = temp8;
                            LAInfo.LAThresholdScale = temp9;
                            LAInfo.LAThresholdCScale = temp10;

                            LAInfoDeep.Stage0PeriodDetectionThreshold = new MantExp(temp5);
                            LAInfoDeep.PeriodDetectionThreshold = new MantExp(temp6);
                            LAInfoDeep.Stage0PeriodDetectionThreshold2 = new MantExp(temp7);
                            LAInfoDeep.PeriodDetectionThreshold2 = new MantExp(temp8);
                            LAInfoDeep.LAThresholdScale = new MantExp(temp9);
                            LAInfoDeep.LAThresholdCScale = new MantExp(temp10);

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
                });

        //Make this dialog display it.
        setContentPane(optionPane);

        pack();

        setResizable(false);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }

}
