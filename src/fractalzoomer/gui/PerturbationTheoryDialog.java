
package fractalzoomer.gui;

import fractalzoomer.core.*;
import fractalzoomer.core.approximation.la_zhuoran.LAReference;
import fractalzoomer.core.approximation.la_zhuoran.MagnitudeDetection;
import fractalzoomer.core.approximation.la_zhuoran.MagnitudeDetectionDeep;
import fractalzoomer.core.approximation.la_zhuoran.impl.LAInfo;
import fractalzoomer.core.approximation.la_zhuoran.impl.LAInfoDeep;
import fractalzoomer.core.approximation.mip_la_zhuoran.MipLAStep;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.LibMpir;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.core.reference.ReferenceCompressor;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MinimalRendererWindow;
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
    private final JScrollPane scrollPane;

    public PerturbationTheoryDialog(Component ptr, Settings s) {
        
        super((JFrame)ptr);

        ptra = ptr;
        that = this;

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(750, 700));

        setTitle("Perturbation Theory");
        setModal(true);

        JButton info_user = new MyButton("Help");
        info_user.setToolTipText("Shows info on Perturbation Theory.");
        info_user.setFocusable(false);
        info_user.setIcon(MainWindow.getIcon("help2.png"));
        info_user.setPreferredSize(new Dimension(105, 23));

        info_user.addActionListener(e -> CommonFunctions.showPerturbationTheoryHelp(that));

        JButton reset_approximation = new MyButton();
        reset_approximation.setToolTipText("Resets the approximation values.");
        reset_approximation.setFocusable(false);
        reset_approximation.setIcon(MainWindow.getIcon("reset_small.png"));
        reset_approximation.setPreferredSize(new Dimension(23, 23));


        JPanel SApanel = new JPanel();
        JPanel FloatExpPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel BLApanel = new JPanel();
        JPanel BLA2panel = new JPanel();
        JPanel BLA3panel = new JPanel();
        JPanel Nanomb1Panel = new JPanel();

        if (ptra instanceof MainWindow) {
            setIconImage(MainWindow.getIcon("mandel2.png").getImage());
        } else if (ptra instanceof MinimalRendererWindow) {
            setIconImage(MainWindow.getIcon("mandelMinimalRenderer.png").getImage());
        }

        final JCheckBox enable_perturbation = new JCheckBox("Perturbation Theory");
        enable_perturbation.setSelected(TaskRender.PERTURBATION_THEORY);
        enable_perturbation.setFocusable(false);

        final JCheckBox detect_period = new JCheckBox("Detect Period if possible");
        detect_period.setSelected(TaskRender.DETECT_PERIOD);
        detect_period.setFocusable(false);

        final JCheckBox automatic_precision = new JCheckBox("Automatic Precision");
        automatic_precision.setSelected(MyApfloat.setAutomaticPrecision);
        automatic_precision.setFocusable(false);

        final JCheckBox compress_reference = new JCheckBox("Compress Reference");
        compress_reference.setSelected(TaskRender.COMPRESS_REFERENCE_IF_POSSIBLE);
        compress_reference.setFocusable(false);

        JTextField compressionError = new JTextField();
        compressionError.setText("" + ReferenceCompressor.CompressionError);

        final JCheckBox alwaysCheckForPrecisionDecrease = new JCheckBox("Check for Reduction");
        alwaysCheckForPrecisionDecrease.setSelected(MyApfloat.alwaysCheckForDecrease);
        alwaysCheckForPrecisionDecrease.setFocusable(false);
        alwaysCheckForPrecisionDecrease.setToolTipText("Checks if the precision should be reduced when you modify coordinates in dialogs.");

        final JCheckBox use_threads_for_sa = new JCheckBox("Use Threads for SA if possible");
        use_threads_for_sa.setSelected(TaskRender.USE_THREADS_FOR_SA);
        use_threads_for_sa.setFocusable(false);

        final JCheckBox use_threads_for_bla = new JCheckBox("Use Threads for BLA");
        use_threads_for_bla.setSelected(TaskRender.USE_THREADS_FOR_BLA);
        use_threads_for_bla.setFocusable(false);

        final JCheckBox use_threads_for_bla2 = new JCheckBox("Use Threads for BLA");
        use_threads_for_bla2.setSelected(TaskRender.USE_THREADS_FOR_BLA2);
        use_threads_for_bla2.setFocusable(false);

        final JCheckBox use_threads_for_bla3 = new JCheckBox("Use Threads for BLA");
        use_threads_for_bla3.setSelected(TaskRender.USE_THREADS_FOR_BLA3);
        use_threads_for_bla3.setFocusable(false);

        final JSlider bla_starting_level_slid = new JSlider(JSlider.HORIZONTAL, 1, 32, TaskRender.BLA_STARTING_LEVEL);

        bla_starting_level_slid.setPreferredSize(new Dimension(300, 55));

        bla_starting_level_slid.setToolTipText("Sets the BLA starting level.");

        bla_starting_level_slid.setPaintLabels(true);
        bla_starting_level_slid.setFocusable(false);
        bla_starting_level_slid.setPaintTicks(true);
        bla_starting_level_slid.setMajorTickSpacing(2);


        final JSlider bla3_starting_level_slid = new JSlider(JSlider.HORIZONTAL, 1, 32, TaskRender.BLA3_STARTING_LEVEL);

        bla3_starting_level_slid.setPreferredSize(new Dimension(300, 55));

        bla3_starting_level_slid.setToolTipText("Sets the BLA starting level.");

        bla3_starting_level_slid.setPaintLabels(true);
        bla3_starting_level_slid.setFocusable(false);
        bla3_starting_level_slid.setPaintTicks(true);
        bla3_starting_level_slid.setMajorTickSpacing(2);


        JLabel blaLevel = new JLabel("BLA Starting Level: " + bla_starting_level_slid.getValue());
        JLabel bla3Level = new JLabel("BLA Starting Level: " + bla3_starting_level_slid.getValue());

        bla_starting_level_slid.addChangeListener(e -> blaLevel.setText("BLA Starting Level: " + bla_starting_level_slid.getValue()));
        bla3_starting_level_slid.addChangeListener(e -> bla3Level.setText("BLA Starting Level: " + bla3_starting_level_slid.getValue()));

        final JCheckBox full_floatexp_for_deep_zooms = new JCheckBox("Use Extended Range For All Iterations In Deep Zooms");
        full_floatexp_for_deep_zooms.setSelected(TaskRender.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM);
        full_floatexp_for_deep_zooms.setFocusable(false);

        final JCheckBox full_floatexp_for_all_zooms = new JCheckBox("Use Extended Range For All Iterations In All Zooms");
        full_floatexp_for_all_zooms.setSelected(TaskRender.USE_FULL_FLOATEXP_FOR_ALL_ZOOM);
        full_floatexp_for_all_zooms.setFocusable(false);

        FloatExpPanel2.add(full_floatexp_for_deep_zooms);
        FloatExpPanel2.add(full_floatexp_for_all_zooms);

        JTextField precision = new JTextField();
        precision.setText("" + MyApfloat.precision);
        precision.addAncestorListener(new RequestFocusListener());

        final JCheckBox automaticBignumPrecision = new JCheckBox("Automatic BigNum Precision");
        automaticBignumPrecision.setSelected(TaskRender.BIGNUM_AUTOMATIC_PRECISION);
        automaticBignumPrecision.setFocusable(false);

        final JCheckBox gatherPerturbationStatistics = new JCheckBox("Display Perturbation Statistics");
        gatherPerturbationStatistics.setSelected(TaskRender.GATHER_PERTURBATION_STATISTICS);
        gatherPerturbationStatistics.setFocusable(false);

        JTextField bignumPrecision = new JTextField();
        bignumPrecision.setText("" + TaskRender.BIGNUM_PRECISION);

        final JComboBox<String> approximation_alg = new JComboBox<>(new String[] {"No Approximation", "Series Approximation", "Bilinear Approximation Mip (claude)", "Nanomb1", "Bilinear Approximation (Zhuoran)", "Bilinear Approximation Mip (Zhuoran)"});
        approximation_alg.setSelectedIndex(TaskRender.APPROXIMATION_ALGORITHM);
        approximation_alg.setFocusable(false);
        approximation_alg.setToolTipText("Sets approximation algorithm.");

        final JComboBox<String> pixelAlgorithm = new JComboBox<>(new String[] {"Not Scaled", "Scaled"});
        pixelAlgorithm.setSelectedIndex(TaskRender.PERTUBATION_PIXEL_ALGORITHM);
        pixelAlgorithm.setFocusable(false);
        pixelAlgorithm.setToolTipText("Sets the deep zoom pixel calculation algorithm.");

        final JComboBox<String> mantexpcomplex_format = new JComboBox<>(new String[] {"Combined Exponent", "Two Exponents"});
        mantexpcomplex_format.setSelectedIndex(TaskRender.MANTEXPCOMPLEX_FORMAT);
        mantexpcomplex_format.setFocusable(false);
        mantexpcomplex_format.setToolTipText("Sets format of the Complex number using FloatExp.");

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
            BLA3panel.setVisible(approximation_alg.getSelectedIndex() == 5);
            Nanomb1Panel.setVisible(approximation_alg.getSelectedIndex() == 3);
            pack();
        });

        JTextField maxSkipIter = new JTextField();
        maxSkipIter.setText("" + TaskRender.SERIES_APPROXIMATION_MAX_SKIP_ITER);

        final JSlider series_terms_slid = new JSlider(JSlider.HORIZONTAL, 2, 257, TaskRender.SERIES_APPROXIMATION_TERMS);

        series_terms_slid.setPreferredSize(new Dimension(300, 55));

        series_terms_slid.setToolTipText("Sets the terms of series approximation.");

        series_terms_slid.setPaintLabels(true);
        series_terms_slid.setFocusable(false);
        series_terms_slid.setPaintTicks(true);
        series_terms_slid.setMajorTickSpacing(20);

        JTextField seriesApproximationTolerance = new JTextField();
        seriesApproximationTolerance.setText("" + TaskRender.SERIES_APPROXIMATION_OOM_DIFFERENCE);

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

        final JSlider nanomb1N = new JSlider(JSlider.HORIZONTAL, 2, 48, TaskRender.NANOMB1_N);

        nanomb1N.setPreferredSize(new Dimension(300, 55));

        nanomb1N.setToolTipText("Sets Nanomb1 N value.");

        nanomb1N.setPaintLabels(true);
        nanomb1N.setFocusable(false);
        nanomb1N.setPaintTicks(true);
        nanomb1N.setMajorTickSpacing(4);


        final JSlider nanomb1M = new JSlider(JSlider.HORIZONTAL, 2, 48, TaskRender.NANOMB1_M);

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

        nanomb1N.addChangeListener(e -> nanolabelN.setText("N: " + nanomb1N.getValue()));
        nanomb1M.addChangeListener(e -> nanolabelM.setText("M: " + nanomb1M.getValue()));

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

        final JSlider blaBits = new JSlider(JSlider.HORIZONTAL, 1, 63, TaskRender.BLA_BITS);

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

        JPanel bla2ThreadsPAnel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bla2ThreadsPAnel.add(use_threads_for_bla2);

        JPanel bla3ThreadsPAnel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bla3ThreadsPAnel.add(use_threads_for_bla3);


        JPanel blaSkipLevel1PAnelLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        blaSkipLevel1PAnelLabel.add(blaLevel);

        JPanel bla3SkipLevel1PAnelLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bla3SkipLevel1PAnelLabel.add(bla3Level);

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
        BLA3panel.setVisible(approximation_alg.getSelectedIndex() == 5);
        Nanomb1Panel.setVisible(approximation_alg.getSelectedIndex() == 3);

        JComboBox<String> bigNumLibs = new JComboBox<>(new String[] {"Double (53 bits)", "DoubleDouble (106 bits)", "Built-in", "MPFR", "Automatic", "MPIR", "Automatic (No Double/DoubleDouble)", "Fixed Point BigInteger", "Apfloat"});
        bigNumLibs.setSelectedIndex(NumericLibrary.BIGNUM_IMPLEMENTATION);
        JLabel bnliblabel = new JLabel("BigNum Implementation:");
        bigNumLibs.setFocusable(false);


        bignumPrecision.setEnabled(!automaticBignumPrecision.isSelected() && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLE && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLEDOUBLE);

        automaticBignumPrecision.addActionListener(e -> bignumPrecision.setEnabled(!automaticBignumPrecision.isSelected() && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLE && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLEDOUBLE));


        automaticBignumPrecision.setEnabled(bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLE && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLEDOUBLE);

        bigNumLibs.addActionListener(e -> {
            bignumPrecision.setEnabled(!automaticBignumPrecision.isSelected() && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLE && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLEDOUBLE);
            automaticBignumPrecision.setEnabled(bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLE && bigNumLibs.getSelectedIndex() != Constants.BIGNUM_DOUBLEDOUBLE);
        });

        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statsPanel.add(detect_period);
        statsPanel.add(gatherPerturbationStatistics);

        JPanel autoBigNumPrecPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        autoBigNumPrecPanel.add(automaticBignumPrecision);

        JComboBox<String> bla2DetectionMethod = new JComboBox<>(new String[] {"HInfLLA(1)", "HInfLLA(2)", "HInfMLA"});
        bla2DetectionMethod.setSelectedIndex(LAInfo.DETECTION_METHOD);
        bla2DetectionMethod.setFocusable(false);

        BLA2panel.setLayout(new BoxLayout(BLA2panel, BoxLayout.Y_AXIS));
        BLA3panel.setLayout(new BoxLayout(BLA3panel, BoxLayout.Y_AXIS));

        JPanel detectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        detectionPanel.add(new JLabel("Detection Method:"));

        JPanel bla2Thresholds = new JPanel();

        bla2Thresholds.setLayout(new GridLayout(4, 2));
        bla2Thresholds.add(new JLabel("Stage 0 Dip Detection Threshold:", SwingConstants.HORIZONTAL));
        bla2Thresholds.add(new JLabel("Dip Detection Threshold:", SwingConstants.HORIZONTAL));
        JTextField stage0 = new JTextField();
        stage0.setText("" + LAInfo.Stage0DipDetectionThreshold);
        JTextField plimit = new JTextField();
        plimit.setText("" + LAInfo.DipDetectionThreshold);

        JTextField stage01 = new JTextField();
        stage01.setText("" + LAInfo.Stage0DipDetectionThreshold2);
        JTextField plimit1 = new JTextField();
        plimit1.setText("" + LAInfo.DipDetectionThreshold2);

        JTextField stage02 = new JTextField();
        stage02.setText("" + MagnitudeDetection.Stage0DipDetectionThreshold);
        JTextField plimit2 = new JTextField();
        plimit2.setText("" + MagnitudeDetection.DipDetectionThreshold);

        JTextField double_threshold = new JTextField();
        double_threshold.setText("" + LAReference.doubleThresholdLimit.toDouble());

        JTextField period_divisor = new JTextField();
        period_divisor.setText("" + LAReference.rootDivisor);


        bla2Thresholds.add(stage0);
        bla2Thresholds.add(plimit);
        bla2Thresholds.add(stage01);
        bla2Thresholds.add(plimit1);
        bla2Thresholds.add(stage02);
        bla2Thresholds.add(plimit2);

        JPanel bla2ThresholdsScales = new JPanel();

        bla2ThresholdsScales.setLayout(new GridLayout(2, 2));
        bla2ThresholdsScales.add(new JLabel("LA Scale Threshold:", SwingConstants.HORIZONTAL));
        bla2ThresholdsScales.add(new JLabel("LA C Scale Threshold:", SwingConstants.HORIZONTAL));

        JTextField lascale = new JTextField();
        lascale.setText("" + LAInfo.LAThresholdScale);
        JTextField lacscale = new JTextField();
        lacscale.setText("" + LAInfo.LAThresholdCScale);

        bla2ThresholdsScales.add(lascale);
        bla2ThresholdsScales.add(lacscale);

        stage0.setEnabled(bla2DetectionMethod.getSelectedIndex() == 0);
        plimit.setEnabled(bla2DetectionMethod.getSelectedIndex() == 0);

        stage01.setEnabled(bla2DetectionMethod.getSelectedIndex() == 1);
        plimit1.setEnabled(bla2DetectionMethod.getSelectedIndex() == 1);

        stage02.setEnabled(bla2DetectionMethod.getSelectedIndex() == 2);
        plimit2.setEnabled(bla2DetectionMethod.getSelectedIndex() == 2);

        bla2DetectionMethod.addActionListener(e->{
            stage0.setEnabled(bla2DetectionMethod.getSelectedIndex() == 0);
            plimit.setEnabled(bla2DetectionMethod.getSelectedIndex() == 0);

            stage01.setEnabled(bla2DetectionMethod.getSelectedIndex() == 1);
            plimit1.setEnabled(bla2DetectionMethod.getSelectedIndex() == 1);
            bla2ThreadsPAnel.setVisible(bla2DetectionMethod.getSelectedIndex() != 2);

            stage02.setEnabled(bla2DetectionMethod.getSelectedIndex() == 2);
            plimit2.setEnabled(bla2DetectionMethod.getSelectedIndex() == 2);
        });


        JPanel panel3 = new JPanel(new GridLayout(2, 2));
        panel3.add(new JLabel("Double Threshold Limit:", SwingConstants.HORIZONTAL));
        panel3.add(new JLabel("Root Divisor:", SwingConstants.HORIZONTAL));
        panel3.add(double_threshold);
        panel3.add(period_divisor);

        BLA2panel.add(detectionPanel);
        BLA2panel.add(bla2DetectionMethod);
        BLA2panel.add(new JLabel(" "));
        BLA2panel.add(bla2Thresholds);
        BLA2panel.add(bla2ThresholdsScales);
        BLA2panel.add(panel3);
        BLA2panel.add(new JLabel(" "));
        BLA2panel.add(bla2ThreadsPAnel);
        bla2ThreadsPAnel.setVisible(bla2DetectionMethod.getSelectedIndex() != 2);

        JTextField bla3_valid_radius_scale = new JTextField();
        bla3_valid_radius_scale.setText("" + MipLAStep.ValidRadiusScale);

        JPanel rscale = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rscale.add(new JLabel("Valid Radius Scale:"));

        BLA3panel.add(rscale);
        BLA3panel.add(bla3_valid_radius_scale);
        BLA3panel.add(new JLabel(" "));
        BLA3panel.add(bla3SkipLevel1PAnelLabel);
        BLA3panel.add(bla3_starting_level_slid);
        BLA3panel.add(new JLabel(" "));
        BLA3panel.add(bla3ThreadsPAnel);

        reset_approximation.addActionListener(e -> {

            stage0.setText("" + ApproximationDefaultSettings.Stage0DipDetectionThreshold);
            plimit.setText("" + ApproximationDefaultSettings.DipDetectionThreshold);

            stage01.setText("" + ApproximationDefaultSettings.Stage0DipDetectionThreshold2);
            plimit1.setText("" + ApproximationDefaultSettings.DipDetectionThreshold2);

            stage02.setText("" + ApproximationDefaultSettings.Stage0DipDetectionThreshold3);
            plimit2.setText("" + ApproximationDefaultSettings.DipDetectionThreshold3);

            double_threshold.setText("" + ApproximationDefaultSettings.DoubleThresholdLimit);

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

            period_divisor.setText("" + ApproximationDefaultSettings.RootDivisor);

            bla3_valid_radius_scale.setText("" + ApproximationDefaultSettings.BLA3ValidRadiusSCale);
            bla3_starting_level_slid.setValue(ApproximationDefaultSettings.BLA3_STARTING_LEVEL);

        });

        JPanel appr = new JPanel(new FlowLayout(FlowLayout.LEFT));
        appr.add(new JLabel("Approximation:"));
        appr.add(Box.createRigidArea(new Dimension(350, 10)));
        appr.add(reset_approximation);

        JPanel panel_auto_prec = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_auto_prec.add(automatic_precision);
        panel_auto_prec.add(alwaysCheckForPrecisionDecrease);

        alwaysCheckForPrecisionDecrease.setEnabled(automatic_precision.isSelected());

        automatic_precision.addActionListener(e -> alwaysCheckForPrecisionDecrease.setEnabled(automatic_precision.isSelected()));


        JPanel panelmantex = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelmantex.add(new JLabel("Complex Extended Range Format:"));


        Object[] message3 = {
            info_panel,
            " ",
            enable_perturbation,
            " ",
            "Floating point precision:",
            precision,
                panel_auto_prec,
            " ",
                bnliblabel,
                bigNumLibs,
                autoBigNumPrecPanel,
                "BigNum bits precision:",
                bignumPrecision,
                " ",
                statsPanel,
                " ",
                compress_reference,
                "Compression Error:",
                compressionError,
                " ",
                appr,
                approximation_alg,
                " ",
                SApanel,
                BLApanel,
                BLA2panel,
                BLA3panel,
                Nanomb1Panel,
                FloatExpPanel2,
                panelmantex,
                mantexpcomplex_format,
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
                            double double_t_l = Double.parseDouble(double_threshold.getText());
                            double temp13 = Double.parseDouble(bla3_valid_radius_scale.getText());

                            double temp11 = Double.parseDouble(compressionError.getText());
                            double temp12 = Double.parseDouble(period_divisor.getText());
                            double temp14 = Double.parseDouble(stage02.getText());
                            double temp15 = Double.parseDouble(plimit2.getText());

                            if(temp5 <= 0 || temp6 <= 0 || temp7 <= 0 || temp8 <= 0 || temp14 <= 0 || temp15 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The stage 0 dip limit and dip limit values must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp5 > 10 || temp6 > 10 || temp7 > 10 || temp8 > 10) {
                                JOptionPane.showMessageDialog(ptra, "The stage 0 dip limit and dip limit values must less than 10.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp9 <= 0 || temp10 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The LA scale limit values must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(double_t_l <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The double threshold limit value must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp12 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The LA period divisor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
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

                            if(temp11 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The compression error must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp13 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The valid radius scale value must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            TaskRender.PERTURBATION_THEORY = enable_perturbation.isSelected();

                            if(!TaskRender.PERTURBATION_THEORY || tempPrecision != MyApfloat.precision) {
                                Fractal.clearReferences(true, true);
                            }

                            boolean tempAuto = automaticBignumPrecision.isSelected();

                            if(!tempAuto && tempPrecision == MyApfloat.precision) {
                                Fractal.clearReferences(true, true);
                                BigNum.reinitialize(temp4);
                                MpfrBigNum.reinitialize(temp4);
                                MpirBigNum.reinitialize(temp4);
                                BigIntNum.reinitialize(temp4);
                            }
                            else if(tempAuto && !TaskRender.BIGNUM_AUTOMATIC_PRECISION && tempPrecision == MyApfloat.precision) {
                                Fractal.clearReferences(true, true);
                                MyApfloat.setBigNumPrecision();
                            }

                            int oldApproximationAlg = TaskRender.APPROXIMATION_ALGORITHM;
                            TaskRender.APPROXIMATION_ALGORITHM = approximation_alg.getSelectedIndex();

                            if(oldApproximationAlg != TaskRender.APPROXIMATION_ALGORITHM) {
                                Fractal.clearReferences(true, true);
                            }

                            boolean oldDetectedPeriod =  TaskRender.DETECT_PERIOD;
                            TaskRender.DETECT_PERIOD = detect_period.isSelected();

                            if(oldDetectedPeriod != TaskRender.DETECT_PERIOD) {
                                if(TaskRender.DETECT_PERIOD || TaskRender.APPROXIMATION_ALGORITHM == 3) {
                                    Fractal.clearReferences(true, true);
                                }
                                else {
                                    if(TaskRender.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD) {
                                        Fractal.clearReferences(true, true);
                                    }
                                    else {
                                       // Fractal.DetectedAtomPeriod = 0;
                                        Fractal.DetectedPeriod = 0;
                                    }
                                }
                            }

                            TaskRender.GATHER_PERTURBATION_STATISTICS = gatherPerturbationStatistics.isSelected();

                            int oldBignumLib = NumericLibrary.BIGNUM_IMPLEMENTATION;
                            NumericLibrary.BIGNUM_IMPLEMENTATION = bigNumLibs.getSelectedIndex();
                            if(oldBignumLib != NumericLibrary.BIGNUM_IMPLEMENTATION) {
                                Fractal.clearReferences(true, true);
                            }

                            boolean oldUseFullFloatExp = TaskRender.USE_FULL_FLOATEXP_FOR_ALL_ZOOM;
                            TaskRender.USE_FULL_FLOATEXP_FOR_ALL_ZOOM = full_floatexp_for_all_zooms.isSelected();
                            if(oldUseFullFloatExp != TaskRender.USE_FULL_FLOATEXP_FOR_ALL_ZOOM){
                                Fractal.clearReferences(true, true);
                            }

                            boolean oldUseFullFloatExpDeep = TaskRender.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM;
                            TaskRender.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM = full_floatexp_for_deep_zooms.isSelected();
                            if(oldUseFullFloatExpDeep != TaskRender.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM){
                                Fractal.clearReferences(true, true);
                            }

                            int oldFormat = TaskRender.MANTEXPCOMPLEX_FORMAT;
                            TaskRender.MANTEXPCOMPLEX_FORMAT = mantexpcomplex_format.getSelectedIndex();
                            if(oldFormat != TaskRender.MANTEXPCOMPLEX_FORMAT) {
                                Fractal.clearReferences(true, true);
                            }

                            if(TaskRender.PERTURBATION_THEORY && NumericLibrary.BIGNUM_IMPLEMENTATION == Constants.BIGNUM_MPFR && LibMpfr.mpfrHasError()) {
                                JOptionPane.showMessageDialog(ptra, "The MPFR library is not available, and the engine will fallback to an alternative library.", "Warning!", JOptionPane.WARNING_MESSAGE);
                            }

                            if(TaskRender.PERTURBATION_THEORY && NumericLibrary.BIGNUM_IMPLEMENTATION == Constants.BIGNUM_MPIR && LibMpir.mpirHasError()) {
                                JOptionPane.showMessageDialog(ptra, "The MPIR library is not available, and the engine will fallback to an alternative library.", "Warning!", JOptionPane.WARNING_MESSAGE);
                            }

                            TaskRender.BIGNUM_AUTOMATIC_PRECISION = tempAuto;
                            TaskRender.BIGNUM_PRECISION = temp4;

                            if(tempPrecision != MyApfloat.precision) {
                                MyApfloat.setPrecision(tempPrecision, s);
                            }

                            int oldNanomb1N = TaskRender.NANOMB1_N;
                            TaskRender.NANOMB1_N = nanomb1N.getValue();

                            int oldNanomb1M = TaskRender.NANOMB1_M;
                            TaskRender.NANOMB1_M = nanomb1M.getValue();

                            if(oldNanomb1N != TaskRender.NANOMB1_N || oldNanomb1M != TaskRender.NANOMB1_M) {
                                Fractal.clearReferences(true, true);
                            }

                            int oldPixelAlgorithm = TaskRender.PERTUBATION_PIXEL_ALGORITHM;
                            TaskRender.PERTUBATION_PIXEL_ALGORITHM = pixelAlgorithm.getSelectedIndex();
                            if(oldPixelAlgorithm != TaskRender.PERTUBATION_PIXEL_ALGORITHM) {
                                Fractal.clearReferences(true, true);
                            }

                            boolean oldCompressReference = TaskRender.COMPRESS_REFERENCE_IF_POSSIBLE;
                            TaskRender.COMPRESS_REFERENCE_IF_POSSIBLE = compress_reference.isSelected();
                            if(oldCompressReference != TaskRender.COMPRESS_REFERENCE_IF_POSSIBLE) {
                                Fractal.clearReferences(true, true);
                            }

                            double oldCompressionError = ReferenceCompressor.CompressionError;
                            ReferenceCompressor.CompressionError = temp11;
                            ReferenceCompressor.setCompressionError();
                            if(oldCompressionError != ReferenceCompressor.CompressionError) {
                                Fractal.clearReferences(true, true);
                            }

                            MyApfloat.setAutomaticPrecision = automatic_precision.isSelected();
                            MyApfloat.alwaysCheckForDecrease = alwaysCheckForPrecisionDecrease.isSelected();

                            TaskRender.APPROXIMATION_ALGORITHM = approximation_alg.getSelectedIndex();
                            TaskRender.SERIES_APPROXIMATION_TERMS = series_terms_slid.getValue();
                            TaskRender.SERIES_APPROXIMATION_OOM_DIFFERENCE = temp2;
                            TaskRender.SERIES_APPROXIMATION_MAX_SKIP_ITER = temp3;
                            TaskRender.USE_THREADS_FOR_SA = use_threads_for_sa.isSelected();
                            TaskRender.BLA_BITS = blaBits.getValue();
                            TaskRender.BLA_STARTING_LEVEL = bla_starting_level_slid.getValue();
                            TaskRender.BLA3_STARTING_LEVEL = bla3_starting_level_slid.getValue();
                            TaskRender.USE_THREADS_FOR_BLA = use_threads_for_bla.isSelected();
                            TaskRender.USE_THREADS_FOR_BLA2 = use_threads_for_bla2.isSelected();
                            TaskRender.USE_THREADS_FOR_BLA3 = use_threads_for_bla3.isSelected();

                            LAInfo.DETECTION_METHOD = bla2DetectionMethod.getSelectedIndex();
                            LAInfo.Stage0DipDetectionThreshold = temp5;
                            LAInfo.DipDetectionThreshold = temp6;
                            LAInfo.Stage0DipDetectionThreshold2 = temp7;
                            LAInfo.DipDetectionThreshold2 = temp8;
                            LAInfo.LAThresholdScale = temp9;
                            LAInfo.LAThresholdCScale = temp10;
                            MagnitudeDetection.Stage0DipDetectionThreshold = temp14;
                            MagnitudeDetection.DipDetectionThreshold = temp15;

                            LAInfoDeep.Stage0DipDetectionThreshold = new MantExp(temp5);
                            LAInfoDeep.DipDetectionThreshold = new MantExp(temp6);
                            LAInfoDeep.Stage0DipDetectionThreshold2 = new MantExp(temp7);
                            LAInfoDeep.DipDetectionThreshold2 = new MantExp(temp8);
                            LAInfoDeep.LAThresholdScale = new MantExp(temp9);
                            LAInfoDeep.LAThresholdCScale = new MantExp(temp10);
                            LAReference.doubleThresholdLimit = new MantExp(double_t_l);
                            LAReference.rootDivisor = temp12;
                            MagnitudeDetectionDeep.Stage0DipDetectionThreshold = new MantExp(temp14);
                            MagnitudeDetectionDeep.DipDetectionThreshold = new MantExp(temp15);

                            MipLAStep.ValidRadiusScale = temp13;

                            if(ptra instanceof MainWindow) {
                                ((MainWindow)ptra).setPerturbationTheoryPost();
                            }
                            else if(ptra instanceof MinimalRendererWindow) {
                                ((MinimalRendererWindow)ptra).setPerturbationTheoryPost();
                            }

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
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
