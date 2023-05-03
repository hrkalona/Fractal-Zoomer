/*
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
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
package fractalzoomer.main;

import com.sun.jna.Platform;
import fractalzoomer.core.*;
import fractalzoomer.core.drawing_algorithms.*;
import fractalzoomer.core.la.LAInfo;
import fractalzoomer.core.la.LAInfoDeep;
import fractalzoomer.core.location.Location;
import fractalzoomer.gui.*;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.parser.ParserException;
import fractalzoomer.utils.*;
import org.apfloat.Apfloat;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 *
 * @author hrkalona2
 */
public class ImageExpanderWindow extends JFrame implements Constants {
	private static final long serialVersionUID = 2304630285456716327L;
	private Settings s;
    private int n;

    private int thread_grouping;
    boolean runsOnWindows;
    private int image_size;
    private JButton loadButton;

    private JButton batchRenderButton;

    private JButton sequenceRenderButton;
    private JButton renderButton;

    private JButton polarLargeRenderButton;

    private JButton splitImageRenderButton;
    private JButton compileButton;
    private JButton threadsButton;
    private JButton imageSizeButton;
    private JButton greedyAlgorithmsButton;
    private JButton perturbationTheoryButton;
    private JButton aboutButton;
    private JButton helpButton;
    private JButton overviewButton;
    private JProgressBar progress;
    private JProgressBar totalprogress;
    private JFileChooser file_chooser;

    private JButton outputDirectoryButton;
    private ImageExpanderWindow ptr;
    private ThreadDraw[][] threads;
    private BufferedImage image;
    private BufferedImage largePolarImage;
    private long calculation_time;
    private boolean periodicity_checking;
    private JLabel settings_label;
    private MemoryLabel memory_label;
    private Timer timer;
    private CommonFunctions common;
    private boolean runsOnBatchingMode;
    private boolean runsOnSequenceMode;

    private boolean runsOnLargePolarImageMode;

    private boolean runsOnSplitImageMode;
    private int batchIndex;
    private int sequenceIndex;

    private int numberOfSequenceSteps;

    private String outputDirectory = ".";

    private double zoom_factor = 2.0;
    private int zooming_mode = 0;
    private double rotation_adjusting_value = 0;
    private int color_cycling_adjusting_value = 0;

    private int gradient_color_cycling_adjusting_value = 0;

    private double light_light_direction_adjusting_value = 0;
    private double bump_light_direction_adjusting_value = 0;

    private int zoom_every_n_frame = 1;

    private int number_of_polar_images = 5;
    private int polar_orientation = 0;

    private int split_image_grid_dimension = 2;

    private int gridI;
    private int gridJ;

    private Apfloat size = Constants.DEFAULT_MAGNIFICATION;

    private String settingsName;

    public ImageExpanderWindow() {
        super();

        preloadPreferences();

        NativeLoader.init();

        ptr = this;

        s = new Settings();
        s.applyStaticSettings();

        n = Runtime.getRuntime().availableProcessors();
        thread_grouping = 1;

        periodicity_checking = false;

        Locale.setDefault(Locale.US);

        image_size = 1000;
        setSize(540, 550);
        setTitle("Fractal Zoomer Image Expander");
        
        loadPreferences();

        if(Platform.isWindows()) {
            runsOnWindows = true;

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch(ClassNotFoundException ex) {
            }
            catch(InstantiationException ex) {
            }
            catch(IllegalAccessException ex) {
            }
            catch(UnsupportedLookAndFeelException ex) {
            }
        }
        else {
            runsOnWindows = false;
            CommonFunctions.setUIFont(new javax.swing.plaf.FontUIResource("Arial", Font.PLAIN, 11));
        }

        UIManager.put("OptionPane.errorIcon", MainWindow.getIcon("error_64.png"));
        UIManager.put("OptionPane.informationIcon", MainWindow.getIcon("info_64.png"));
        UIManager.put("OptionPane.warningIcon", MainWindow.getIcon("warning_64.png"));
        UIManager.put("OptionPane.questionIcon", MainWindow.getIcon("question_64.png"));

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // Get the current screen size
        Dimension scrnsize = toolkit.getScreenSize();

        if(scrnsize.getHeight() > getHeight()) {
            setLocation((int)((scrnsize.getWidth() / 2.0) - (getWidth() / 2.0)), (int)((scrnsize.getHeight() / 2.0) - (getHeight() / 2.0)) - 23);
        }
        else {
            setLocation((int)((scrnsize.getWidth() / 2.0) - (getWidth() / 2.0)), 0);
        }

        setResizable(false);

        JPanel main_panel = new JPanel();
        main_panel.setLayout(new GridLayout(9, 1));
        main_panel.setBackground(Constants.bg_color);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                exit(0);

            }
        });

        setIconImage(MainWindow.getIcon("mandelExpander.png").getImage());

        settings_label = new JLabel("");
        settings_label.setIcon(MainWindow.getIcon("checkmark.png"));
        settings_label.setVisible(false);
        settings_label.setPreferredSize(new Dimension(380, 32));

        overviewButton = new JButton("", MainWindow.getIcon("overview.png"));
        overviewButton.setFocusable(false);
        overviewButton.setToolTipText("Creates a report of all the active fractal options.");
        overviewButton.setVisible(false);
        overviewButton.addActionListener(e -> overview());

        JPanel p1 = new JPanel();
        p1.setBackground(Constants.bg_color);
        p1.add(settings_label);
        p1.add(overviewButton);

        Dimension buttonDimension = new Dimension(220, 32);

        loadButton = new JButton("Load", MainWindow.getIcon("load.png"));
        loadButton.setFocusable(false);
        loadButton.setPreferredSize(buttonDimension);
        loadButton.setToolTipText("<html>Loads the function, plane, center, size, color options, iterations,<br> rotation, perturbation, initial value, bailout, julia settings,<br>and image filters.</html>");

        loadButton.addActionListener(e -> loadSettings());

        batchRenderButton = new JButton("Batch Render", MainWindow.getIcon("batch_render.png"));
        batchRenderButton.setFocusable(false);
        batchRenderButton.setPreferredSize(buttonDimension);
        batchRenderButton.setToolTipText("Renders a batch of parameters.");

        batchRenderButton.addActionListener(e -> batchRender());

        sequenceRenderButton = new JButton("Zoom Sequence Render", MainWindow.getIcon("sequence.png"));
        sequenceRenderButton.setFocusable(false);
        sequenceRenderButton.setPreferredSize(buttonDimension);
        sequenceRenderButton.setToolTipText("Renders a zoom sequence.");
        sequenceRenderButton.setEnabled(false);
        sequenceRenderButton.addActionListener(e -> sequenceRender());


        imageSizeButton = new JButton("Image Size", MainWindow.getIcon("image_size.png"));
        imageSizeButton.setFocusable(false);
        imageSizeButton.setPreferredSize(buttonDimension);
        imageSizeButton.setToolTipText("Sets the image size.");

        imageSizeButton.addActionListener(e -> setSizeOfImage());


        threadsButton = new JButton("Threads", MainWindow.getIcon("threads.png"));
        threadsButton.setFocusable(false);
        threadsButton.setPreferredSize(buttonDimension);
        threadsButton.setToolTipText("Sets the number of parallel drawing threads.");

        threadsButton.addActionListener(e -> setThreadsNumber());



        greedyAlgorithmsButton = new JButton("Greedy Drawing Algorithms", MainWindow.getIcon("greedy_algorithm.png"));
        greedyAlgorithmsButton.setFocusable(false);
        greedyAlgorithmsButton.setPreferredSize(buttonDimension);
        greedyAlgorithmsButton.setToolTipText("Sets the greedy algorithms options.");

        greedyAlgorithmsButton.addActionListener(e -> setGreedyAlgorithms());


        renderButton = new JButton("Render Image", MainWindow.getIcon("save_image.png"));
        renderButton.setFocusable(false);
        renderButton.setEnabled(false);
        renderButton.setPreferredSize(buttonDimension);
        renderButton.setToolTipText("Renders the image and saves it to disk.");

        renderButton.addActionListener(e -> {
            Location.offset = new PixelOffset();
            render();
        });

        polarLargeRenderButton = new JButton("Large Polar Image", MainWindow.getIcon("polar_projection.png"));
        polarLargeRenderButton.setFocusable(false);
        polarLargeRenderButton.setEnabled(false);
        polarLargeRenderButton.setPreferredSize(buttonDimension);
        polarLargeRenderButton.setToolTipText("Renders a large polar projection image and saves it to disk.");

        polarLargeRenderButton.addActionListener(e -> polarLargeRender());

        splitImageRenderButton = new JButton("Split Image Render", MainWindow.getIcon("split_image.png"));
        splitImageRenderButton.setFocusable(false);
        splitImageRenderButton.setEnabled(false);
        splitImageRenderButton.setPreferredSize(buttonDimension);
        splitImageRenderButton.setToolTipText("Renders a grid of images.");

        splitImageRenderButton.addActionListener(e -> splitImageRender());


        perturbationTheoryButton = new JButton("Perturbation Theory", MainWindow.getIcon("perturbation.png"));
        perturbationTheoryButton.setFocusable(false);
        perturbationTheoryButton.setPreferredSize(buttonDimension);
        perturbationTheoryButton.setToolTipText("Sets the perturbation theory settings.");

        perturbationTheoryButton.addActionListener(e -> setPerturbationTheory());

        outputDirectoryButton = new JButton("Output Directory");
        outputDirectoryButton.setIcon(MainWindow.getIcon("output_directory.png"));
        outputDirectoryButton.setFocusable(false);
        outputDirectoryButton.setPreferredSize(buttonDimension);
        outputDirectoryButton.setToolTipText("Set the output directory.");


        outputDirectoryButton.addActionListener(e -> setOutputDirectory());


        compileButton = new JButton("Compile User Code", MainWindow.getIcon("compile.png"));
        compileButton.setFocusable(false);
        compileButton.setPreferredSize(buttonDimension);
        compileButton.setToolTipText("Compiles the java code, containing the user defined functions.");

        compileButton.addActionListener(e -> common.compileCode(true));

        aboutButton = new JButton("About", MainWindow.getIcon("about.png"));
        aboutButton.setFocusable(false);
        aboutButton.setPreferredSize(buttonDimension);

        aboutButton.addActionListener(e -> displayAboutInfo());

        helpButton = new JButton("Help", MainWindow.getIcon("help.png"));
        helpButton.setFocusable(false);
        helpButton.setPreferredSize(buttonDimension);

        helpButton.addActionListener(e -> displayHelp());

        progress = new JProgressBar();
        progress.setPreferredSize(new Dimension(220, 22));
        progress.setMaximumSize(progress.getPreferredSize());
        //progress.setMinimumSize(progress.getPreferredSize());
        progress.setStringPainted(true);
        progress.setForeground(Constants.progress_color);
        progress.setValue(0);

        progress.setVisible(false);


        totalprogress = new JProgressBar();
        totalprogress.setPreferredSize(new Dimension(445, 22));
        totalprogress.setMaximumSize(progress.getPreferredSize());
        //progress.setMinimumSize(progress.getPreferredSize());
        totalprogress.setStringPainted(true);
        totalprogress.setForeground(Constants.progress_filters_color);
        totalprogress.setValue(0);

        totalprogress.setVisible(false);

        memory_label = new MemoryLabel(220);
        memory_label.setVisible(false);

        JPanel stats = new JPanel();
        stats.setBackground(Constants.bg_color);
        stats.add(memory_label);
        stats.add(progress);

        JPanel p2 = new JPanel();
        p2.setBackground(Constants.bg_color);
        p2.add(loadButton);
        p2.add(outputDirectoryButton);

        JPanel p3 = new JPanel();
        p3.setBackground(Constants.bg_color);
        p3.add(compileButton);
        p3.add(threadsButton);

        JPanel p7 = new JPanel();
        p7.setBackground(Constants.bg_color);
        p7.add(greedyAlgorithmsButton);
        p7.add(perturbationTheoryButton);

        JPanel p6 = new JPanel();
        p6.setBackground(Constants.bg_color);
        p6.add(imageSizeButton);
        p6.add(batchRenderButton);


        JPanel p4 = new JPanel();
        p4.setBackground(Constants.bg_color);
        p4.add(sequenceRenderButton);
        p4.add(renderButton);

        JPanel p8 = new JPanel();
        p8.setBackground(Constants.bg_color);
        p8.add(polarLargeRenderButton);
        p8.add(splitImageRenderButton);

        JPanel p5 = new JPanel();
        p5.setBackground(Constants.bg_color);
        p5.add(helpButton);
        p5.add(aboutButton);

        main_panel.add(p1);
        main_panel.add(p2);
        main_panel.add(p3);
        main_panel.add(p7);
        main_panel.add(p6);
        main_panel.add(p4);
        main_panel.add(p8);
        main_panel.add(p5);
        main_panel.add(stats);

        JPanel overallProgressPanel = new JPanel();
        overallProgressPanel.setBackground(Constants.bg_color);
        overallProgressPanel.add(totalprogress);

        RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
        round_panel.setBackground(Constants.bg_color);
        round_panel.setPreferredSize(new Dimension(490, 460));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(main_panel, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 1;

        round_panel.add(overallProgressPanel, con);

        setLayout(new GridBagLayout());
        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;
        add(round_panel, con);

        common = new CommonFunctions(ptr, runsOnWindows);

    }

    public Settings getSettings() {
        return s;
    }

    public void setOptions(boolean opt) {

        if(!runsOnBatchingMode && !runsOnSequenceMode && !runsOnLargePolarImageMode && !runsOnSplitImageMode) {
            loadButton.setEnabled(opt);
            perturbationTheoryButton.setEnabled(opt);
            renderButton.setEnabled(opt);
            compileButton.setEnabled(opt);
            threadsButton.setEnabled(opt);
            imageSizeButton.setEnabled(opt);
            greedyAlgorithmsButton.setEnabled(opt);
            batchRenderButton.setEnabled(opt);
            sequenceRenderButton.setEnabled(opt);
            outputDirectoryButton.setEnabled(opt);
            if(s.polar_projection) {
                polarLargeRenderButton.setEnabled(opt);
            }
            splitImageRenderButton.setEnabled(opt);
        }

    }

    public void batchRender() {
        new BatchRenderFrame(ptr);
    }

    public void sequenceRender() {
        new SequenceRenderDialog(ptr, s, zoom_factor, zooming_mode, size, rotation_adjusting_value, color_cycling_adjusting_value, light_light_direction_adjusting_value, bump_light_direction_adjusting_value, zoom_every_n_frame, gradient_color_cycling_adjusting_value);
    }

    public void loadSettings() {

        file_chooser = new JFileChooser(MainWindow.SaveSettingsPath.isEmpty() ? "." : MainWindow.SaveSettingsPath);

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fractal Zoomer Settings (*.fzs)", "fzs"));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
            String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
            file_chooser.setSelectedFile(new File(file_name));
        });

        int returnVal = file_chooser.showDialog(ptr, "Load Settings");

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = file_chooser.getSelectedFile();

            try {
                s.readSettings(file.toString(), ptr, false);
                String fname = file.getName();

                if(fname.length() > 45) {
                    fname = fname.substring(0, 45) + "...";
                }
                settings_label.setText("Loaded: " + fname);
                settings_label.setVisible(true);
                renderButton.setEnabled(true);
                sequenceRenderButton.setEnabled(true);
                overviewButton.setVisible(true);
                polarLargeRenderButton.setEnabled(s.polar_projection);
                splitImageRenderButton.setEnabled(true);

                MainWindow.SaveSettingsPath = file.getParent();

                settingsName = file.getName().substring(0, file.getName().lastIndexOf("."));
            }
            catch(IOException ex) {
                JOptionPane.showMessageDialog(ptr, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            catch(ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(ptr, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(ptr, "Error while loading the file.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                exit(-1);
            }

        }
    }

    private void clearThreads() {

        if(threads == null) {
            return;
        }

        for(int i = 0; i < threads.length; i++) {
            for(int j = 0; j < threads[i].length; j++) {
                threads[i][j] = null;
            }
        }

    }

    private void cleanUp() {
        image = null;
        largePolarImage = null;

        clearThreads();

        threads = null;
    }

    private void render() {

        try {

            if(timer == null) {
                timer = new Timer();
                timer.schedule(new RefreshMemoryTask(memory_label), 30000, 30000);
            }

            ThreadDraw.setArraysExpander(image_size, s.needsExtraData());

            progress.setMaximum((image_size * image_size) + ((image_size * image_size) / 100));

            progress.setValue(0);

            progress.setVisible(true);
            memory_label.setVisible(true);

            setOptions(false);

            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            MainWindow.ArraysFillColor(image, EMPTY_COLOR);

            createThreads();

            calculation_time = System.currentTimeMillis();

            startThreads();

        }
        catch(OutOfMemoryError e) {
            JOptionPane.showMessageDialog(ptr, "Maximum Heap size was reached.\nPlease set the maximum Heap size to a higher value.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            exit(-1);;
        }
    }

    private void createThreads() {

        if(thread_grouping == 0) {
            threads = new ThreadDraw[n][n];
            ThreadDraw.resetThreadData(n * n, false);
        }
        else {
            threads = new ThreadDraw[1][n];
            ThreadDraw.resetThreadData(n, false);
        }

        try {
            for(int i = 0; i < threads.length; i++) {
                for(int j = 0; j < threads[i].length; j++) {

                    ThreadSplitCoordinates tsc = ThreadSplitCoordinates.get(j, i, thread_grouping, n, image_size);
                    if(s.fns.julia) {
                        if(ThreadDraw.GREEDY_ALGORITHM) {
                            if(ThreadDraw.GREEDY_ALGORITHM_SELECTION == BOUNDARY_TRACING) {
                                if (ThreadDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                    threads[i][j] = new BoundaryTracingColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js, s.xJuliaCenter, s.yJuliaCenter);
                                } else {
                                    threads[i][j] = new BoundaryTracingDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js, s.xJuliaCenter, s.yJuliaCenter);
                                }
                            }
                            else if(ThreadDraw.GREEDY_ALGORITHM_SELECTION == DIVIDE_AND_CONQUER) {
                                if (ThreadDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                    threads[i][j] = new DivideAndConquerColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js, s.xJuliaCenter, s.yJuliaCenter);
                                } else {
                                    threads[i][j] = new DivideAndConquerDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js, s.xJuliaCenter, s.yJuliaCenter);
                                }
                            }
                        }
                        else {
                            if (ThreadDraw.BRUTE_FORCE_ALG == 0) {
                                threads[i][j] = new BruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js, s.xJuliaCenter, s.yJuliaCenter);
                            }
                            else if (ThreadDraw.BRUTE_FORCE_ALG == 1) {
                                threads[i][j] = new BruteForce2Draw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js, s.xJuliaCenter, s.yJuliaCenter);
                            }
                            else {
                                threads[i][j] = new CircularBruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js, s.xJuliaCenter, s.yJuliaCenter);
                            }
                        }
                    }
                    else {
                        if(ThreadDraw.GREEDY_ALGORITHM) {
                            if(ThreadDraw.GREEDY_ALGORITHM_SELECTION == BOUNDARY_TRACING) {
                                if (ThreadDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                    threads[i][j] = new BoundaryTracingColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js);
                                } else {
                                    threads[i][j] = new BoundaryTracingDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js);
                                }
                            }
                            else if(ThreadDraw.GREEDY_ALGORITHM_SELECTION == DIVIDE_AND_CONQUER) {
                                if (ThreadDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                    threads[i][j] = new DivideAndConquerColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js);
                                } else {
                                    threads[i][j] = new DivideAndConquerDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js);
                                }
                            }
                        }
                        else {
                            if (ThreadDraw.BRUTE_FORCE_ALG == 0) {
                                threads[i][j] = new BruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js);
                            }
                            else if (ThreadDraw.BRUTE_FORCE_ALG == 1) {
                                threads[i][j] = new BruteForce2Draw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js);
                            }
                            else {
                                threads[i][j] = new CircularBruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js);
                            }
                        }
                    }
                    threads[i][j].setThreadId(i * threads.length + j);
                }
            }

            if(threads[0][0] instanceof CircularBruteForceDraw) {
                CircularBruteForceDraw.initCoordinates(image_size, false, true);
            }
            else {
                CircularBruteForceDraw.clear();
            }
        }
        catch(ParserException e) {
            JOptionPane.showMessageDialog(ptr, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            exit(-1);
        }
    }

    private void startThreads() {

        for(int i = 0; i < threads.length; i++) {
            for(int j = 0; j < threads[i].length; j++) {
                threads[i][j].start();
            }
        }

    }

    public int getNumberOfThreads() {

        return thread_grouping == 0 ? n * n : n;

    }

    private void writeLargePolarImageToDisk() {
        try {
            String name;

            Path path = Paths.get(outputDirectory);
            if (Files.exists(path) && Files.isDirectory(path)) {
                name = path.resolve(settingsName + " - polar -.png").toString();

                int counter = 1;
                while (Files.exists(Paths.get(name))) {
                    name = path.resolve( settingsName + " - polar - (" + counter + ").png").toString();
                    counter++;
                }
            }
            else {
                name = settingsName + " - polar -.png";
            }

            File file = new File(name);
            ImageIO.write(largePolarImage, "png", file);
        }
        catch(IOException ex) {
        }
    }

    public void writeImageToDisk() {

        if(runsOnLargePolarImageMode) {
            return;
        }

        try {
            String name;
            if (runsOnSequenceMode) {
                Path path = Paths.get(outputDirectory);

                if (Files.exists(path) && Files.isDirectory(path)) {
                    name = path.resolve(settingsName + " - zoom sequence - " + " (" + sequenceIndex + ")" + ".png").toString();
                }
                else {
                    name = settingsName + " - zoom sequence - " + " (" + sequenceIndex + ")" + ".png";
                }
            }
            else if(runsOnSplitImageMode) {
                Path path = Paths.get(outputDirectory);

                if (Files.exists(path) && Files.isDirectory(path)) {
                    name = path.resolve(settingsName + " (" + String.format("%03d", gridI) + ", " + String.format("%03d", gridJ) + ")" + ".png").toString();
                }
                else {
                    name = settingsName + " (" + String.format("%03d", gridI) + ", " + String.format("%03d", gridJ) + ")" + ".png";
                }
            }
            else {
                Path path = Paths.get(outputDirectory);
                if (Files.exists(path) && Files.isDirectory(path)) {
                    name = path.resolve(settingsName + ".png").toString();

                    int counter = 1;
                    while (Files.exists(Paths.get(name))) {
                        name = path.resolve( settingsName + " (" + counter + ").png").toString();
                        counter++;
                    }
                }
                else {
                    name = settingsName + ".png";
                }

            }
            File file = new File(name);
            ImageIO.write(image, "png", file);
        }
        catch(IOException ex) {
        }
        if(!runsOnBatchingMode && !runsOnSequenceMode && !runsOnSplitImageMode) {
            cleanUp();
        }
    }

    public void setThreadsNumberPost(int grouping, int n) {
        this.n = n;
        this.thread_grouping = grouping;
    }
    
    public void setThreadsNumber() {

        new ThreadsDialog(ptr, n, thread_grouping);

    }

    public JProgressBar getProgressBar() {

        return progress;

    }

    public long getCalculationTime() {

        return calculation_time;

    }

    private void overview() {

        try {
            common.overview(s, periodicity_checking);
        }
        catch(Exception ex) {
        }

    }
    
    public void setSizeOfImagePost(int image_size) {
        this.image_size = image_size;
    }

    private void setSizeOfImage() {
        new ImageSizeExpanderDialog(ptr, image_size);
    }

    public void setGreedyAlgorithms() {

        new GreedyAlgorithmsFrame(ptr, ThreadDraw.GREEDY_ALGORITHM, ThreadDraw.GREEDY_ALGORITHM_SELECTION, ThreadDraw.BRUTE_FORCE_ALG);

    }

    public void setPerturbationTheory() {
        new PerturbationTheoryDialog(ptr, s);
    }

    public void boundaryTracingOptionsChanged(boolean greedy_algorithm, int algorithm, int brute_force_alg) {

        ThreadDraw.GREEDY_ALGORITHM = greedy_algorithm;
        ThreadDraw.GREEDY_ALGORITHM_SELECTION = algorithm;
        ThreadDraw.BRUTE_FORCE_ALG = brute_force_alg;

    }

    public void setPerturbationTheoryPost() {

    }

    private void displayAboutInfo() {

        String temp2 = "" + VERSION;
        String versionStr = "";

        int i;
        for(i = 0; i < temp2.length() - 1; i++) {
            versionStr += temp2.charAt(i) + ".";
        }
        versionStr += temp2.charAt(i);

        if(Constants.beta) {
            versionStr += " beta";
        }

        JOptionPane.showMessageDialog(ptr, "<html><center><font size='5' face='arial' color='blue'><b><u>Fractal Zoomer Image Expander</u></b></font><br><br><font size='4' face='arial'>Version: <b>" + versionStr + "</b><br><br>Author: <b>Christos Kalonakis</b><br><br>Contact: <a href=\"mailto:hrkalona@gmail.com\">hrkalona@gmail.com</a><br><br></center></font></html>", "About", JOptionPane.INFORMATION_MESSAGE, MainWindow.getIcon("mandelExpander.png"));

    }

    private void displayHelp() {

        JEditorPane textArea = new JEditorPane();

        textArea.setEditable(false);
        textArea.setContentType("text/html");
        textArea.setPreferredSize(new Dimension(550, 380));

        JScrollPane scroll_pane_2 = new JScrollPane(textArea);

        String help = "<html><center><font size='5' face='arial' color='blue'><b><u>Help</u></b></font></center><br>"
                + "<font size='4' face='arial'>This tool lets you create larger images than the main application,<br>"
                + "which has a limit of 6000x6000 as an image size.<br><br>"
                + "In order to use this tool the right way you must set the JVM's heap size, through<br>"
                + "command line. For instance to execute it using the jar file, use<br>"
                + "<b>java -jar -Xmx4000m FZImageExpander.jar</b> in order to request maximum 4Gb<br>"
                + "of Heap from the JVM.<br><br>"
                + "Please check some online java tutorials for more thorough heap size allocation!<br><br>"
                + "If you are using the *.exe version of the application please edit<br>"
                + "<b>FZImageExpander.l4j.ini</b> and add <b>-Xmx4000m</b> or any other memory size<br>"
                + "value into the *.ini file. The *.ini file name must match the name of the executable.<br><br>"
                
                + "If you do not set the maximum heap, the JVM's default will be used,<br>"
                + "which scales based on your memory.</font></html>";

        textArea.setText(help);

        Object[] message = {
            scroll_pane_2,};

        textArea.setCaretPosition(0);

        JOptionPane.showMessageDialog(ptr, message, "Help", JOptionPane.QUESTION_MESSAGE);
    }

    public CommonFunctions getCommonFunctions() {

        return common;

    }
    
    public void savePreferences() {

        PrintWriter writer;
        try {
            writer = new PrintWriter(new File("IEpreferences.ini"));

            writer.println("#Fractal Zoomer Image Expander preferences.");
            writer.println("#This file contains all the preferences of the user and it is updated,");
            writer.println("#every time the application terminates. Settings that wont have the");
            writer.println("#correct name/number of arguments/argument value, will be ignored");
            writer.println("#and the default values will be used instead.");

            writer.println();
            writer.println();

            writer.println("[General]");
            writer.println("save_settings_path \"" + MainWindow.SaveSettingsPath + "\"");
            writer.println("output_directory \"" + outputDirectory + "\"");
            writer.println("use_smoothing_for_processing_algs " + ThreadDraw.USE_SMOOTHING_FOR_PROCESSING_ALGS);

            writer.println();

            writer.println("[Optimizations]");
            writer.println("thread_dim " + n);
            writer.println("thread_grouping " + thread_grouping);
            writer.println("greedy_drawing_algorithm " + ThreadDraw.GREEDY_ALGORITHM);
            writer.println("greedy_drawing_algorithm_id " + ThreadDraw.GREEDY_ALGORITHM_SELECTION);
            writer.println("greedy_drawing_algorithm_use_iter_data " + ThreadDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA);
            writer.println("skipped_pixels_coloring " + ThreadDraw.SKIPPED_PIXELS_ALG);
            int color = ThreadDraw.SKIPPED_PIXELS_COLOR;
            writer.println("skipped_pixels_user_color " + ((color >> 16) & 0xff) + " " + ((color >> 8) & 0xff) + " " + (color & 0xff));
            writer.println("perturbation_theory " + ThreadDraw.PERTURBATION_THEORY);
            writer.println("precision " + MyApfloat.precision);
            writer.println("approximation_algorithm " + ThreadDraw.APPROXIMATION_ALGORITHM);
            writer.println("series_approximation_terms " + ThreadDraw.SERIES_APPROXIMATION_TERMS);
            writer.println("series_approximation_oom_diff " + ThreadDraw.SERIES_APPROXIMATION_OOM_DIFFERENCE);
            writer.println("series_approximation_max_skip " + ThreadDraw.SERIES_APPROXIMATION_MAX_SKIP_ITER);
            writer.println("use_full_floatexp_for_deep_zoom " + ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM);
            writer.println("use_full_floatexp_for_all_zoom " + ThreadDraw.USE_FULL_FLOATEXP_FOR_ALL_ZOOM);
            writer.println("use_bignum_for_ref " + ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE);
            writer.println("use_bignum_for_pixels " + ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE);
            writer.println("automatic_bignum_precision " + ThreadDraw.BIGNUM_AUTOMATIC_PRECISION);
            writer.println("bignum_precision_bits " + ThreadDraw.BIGNUM_PRECISION);
            writer.println("bignum_precision_factor " + ThreadDraw.BIGNUM_PRECISION_FACTOR);
            writer.println("use_threads_for_sa " + ThreadDraw.USE_THREADS_FOR_SA);
            writer.println("bla_precision_bits " + ThreadDraw.BLA_BITS);
            writer.println("use_threads_for_bla " + ThreadDraw.USE_THREADS_FOR_BLA);
            writer.println("bla_starting_level " + ThreadDraw.BLA_STARTING_LEVEL);
            writer.println("detect_period " + ThreadDraw.DETECT_PERIOD);
            writer.println("brute_force_alg " + ThreadDraw.BRUTE_FORCE_ALG);
            writer.println("exponent_diff_ignored " + MantExp.EXPONENT_DIFF_IGNORED);
            writer.println("bignum_library " + ThreadDraw.BIGNUM_LIBRARY);
            writer.println("automatic_precision " + MyApfloat.setAutomaticPrecision);
            writer.println("nanomb1_n " + ThreadDraw.NANOMB1_N);
            writer.println("nanomb1_m " + ThreadDraw.NANOMB1_M);
            writer.println("perturbation_pixel_algorithm " + ThreadDraw.PERTUBATION_PIXEL_ALGORITHM);
            writer.println("gather_perturbation_statistics " + ThreadDraw.GATHER_PERTURBATION_STATISTICS);
            writer.println("check_bailout_during_deep_not_full_floatexp_mode " + ThreadDraw.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE);
            writer.println("gather_tiny_ref_indexes " + ThreadDraw.GATHER_TINY_REF_INDEXES);
            writer.println("bignum_sqrt_max_iterations " + BigNum.SQRT_MAX_ITERATIONS);
            writer.println("stop_reference_calculation_after_detected_period " + ThreadDraw.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD);
            writer.println("use_custom_floatexp_requirement " + ThreadDraw.USE_CUSTOM_FLOATEXP_REQUIREMENT);
            writer.println("load_mpfr " + ThreadDraw.LOAD_MPFR);
            writer.println("load_mpir " + ThreadDraw.LOAD_MPIR);
            writer.println("#available libs: " + String.join(", ", ThreadDraw.mpirWinLibs));
            writer.println("mpir_lib " + ThreadDraw.MPIR_LIB);
            writer.println("period_detection_algorithm " + ThreadDraw.PERIOD_DETECTION_ALGORITHM);
            writer.println("circular_brute_force_compare_alg " + ThreadDraw.CIRCULAR_BRUTE_FORCE_COMPARE_ALG);
            writer.println("circular_brute_force_n " + ThreadDraw.CIRCULAR_BRUTE_FORCE_N);
            writer.println("load_drawing_algorithm_from_saves " + ThreadDraw.LOAD_DRAWING_ALGORITHM_FROM_SAVES);
            writer.println("bla2_detection_method " + LAInfo.DETECTION_METHOD);
            writer.println("bla2_stage0_period_detection_threshold " + LAInfo.Stage0PeriodDetectionThreshold);
            writer.println("bla2_stage0_period_detection_threshold2 " + LAInfo.Stage0PeriodDetectionThreshold2);
            writer.println("bla2_period_detection_threshold " + LAInfo.PeriodDetectionThreshold);
            writer.println("bla2_period_detection_threshold2 " + LAInfo.PeriodDetectionThreshold2);
            writer.println("bla2_la_threshold_scale " + LAInfo.LAThresholdScale);
            writer.println("bla2_la_threshold_c_scale " + LAInfo.LAThresholdCScale);
            
            writer.println();
            
            writer.println("[Window]");
            writer.println("image_size " + image_size);
            writer.println("window_location " + (int) getLocation().getX() + " " + (int) getLocation().getY());

            writer.println();
            writer.println("[Core]");
            writer.println("derivative_step " + Derivative.DZ.getRe());
            writer.println("aa_jitter_size " + Location.AA_JITTER_SIZE);
            writer.println("aa_number_of_jitter_kernels " + Location.NUMBER_OF_AA_JITTER_KERNELS);
            writer.println("whitepoint " + ColorSpaceConverter.whitePointId);

            writer.close();
        }
        catch(FileNotFoundException ex) {

        }
    }

    private void preloadPreferences() {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("IEpreferences.ini"));

            String str_line;

            while ((str_line = br.readLine()) != null) {

                StringTokenizer tokenizer = new StringTokenizer(str_line, " ");

                if (tokenizer.hasMoreTokens()) {

                    String token = tokenizer.nextToken();
                    if(token.equals("load_mpir") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            ThreadDraw.LOAD_MPIR = false;
                        }
                        else if(token.equals("true")) {
                            ThreadDraw.LOAD_MPIR = true;
                        }
                    }
                    else if(token.equals("load_mpfr") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            ThreadDraw.LOAD_MPFR = false;
                        }
                        else if(token.equals("true")) {
                            ThreadDraw.LOAD_MPFR = true;
                        }
                    }
                    else if(token.equals("load_drawing_algorithm_from_saves") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            ThreadDraw.LOAD_DRAWING_ALGORITHM_FROM_SAVES = false;
                        }
                        else if(token.equals("true")) {
                            ThreadDraw.LOAD_DRAWING_ALGORITHM_FROM_SAVES = true;
                        }
                    }
                    else if(token.equals("mpir_lib") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(Arrays.asList(ThreadDraw.mpirWinLibs).contains(token)) {
                            ThreadDraw.MPIR_LIB = token;
                        }
                    }
                    else {
                        continue;
                    }
                }

            }

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
    }
    
    private void loadPreferences() {
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader("IEpreferences.ini"));
            String str_line;

            while((str_line = br.readLine()) != null) {

                StringTokenizer tokenizer;

                if(str_line.startsWith("save_settings_path") || str_line.startsWith("output_directory")) {
                    tokenizer = new StringTokenizer(str_line, "\"");
                }
                else {
                    tokenizer = new StringTokenizer(str_line, " ");
                }

                if(tokenizer.hasMoreTokens()) {

                    String token = tokenizer.nextToken();

                    if (token.equals("thread_grouping") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 2) {
                                thread_grouping = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bignum_library") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 6) {
                                ThreadDraw.BIGNUM_LIBRARY = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("window_location") && tokenizer.countTokens() == 2) {
                        try {
                            int x = Integer.parseInt(tokenizer.nextToken());
                            int y = Integer.parseInt(tokenizer.nextToken());

                            setLocation(x, y);
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("perturbation_pixel_algorithm") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 1) {
                                ThreadDraw.PERTUBATION_PIXEL_ALGORITHM = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("whitepoint") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 3) {
                                ColorSpaceConverter.whitePointId = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("circular_brute_force_compare_alg") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 3) {
                                ThreadDraw.CIRCULAR_BRUTE_FORCE_COMPARE_ALG = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("circular_brute_force_n") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp != 0) {
                                ThreadDraw.CIRCULAR_BRUTE_FORCE_N = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("period_detection_algorithm") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 2) {
                                ThreadDraw.PERIOD_DETECTION_ALGORITHM = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bignum_sqrt_max_iterations") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0) {
                                BigNum.SQRT_MAX_ITERATIONS = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("stop_reference_calculation_after_detected_period") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            ThreadDraw.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD = false;
                        }
                        else if(token.equals("true")) {
                            ThreadDraw.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD = true;
                        }
                    }
                    else if (token.equals("aa_jitter_size") && tokenizer.countTokens() == 1) {
                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                Location.AA_JITTER_SIZE = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("aa_number_of_jitter_kernels") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0 && temp < 100) {
                                Location.NUMBER_OF_AA_JITTER_KERNELS = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("use_custom_floatexp_requirement") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            ThreadDraw.USE_CUSTOM_FLOATEXP_REQUIREMENT = false;
                        }
                        else if(token.equals("true")) {
                            ThreadDraw.USE_CUSTOM_FLOATEXP_REQUIREMENT = true;
                        }
                    }
                    else if(token.equals("thread_dim") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(thread_grouping == 0) {
                                if (temp >= 1 && temp <= 100) {
                                    n = temp;
                                }
                            }
                            else {
                                if (temp >= 1 && temp <= 10000) {
                                    n = temp;
                                }
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("greedy_drawing_algorithm") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            ThreadDraw.GREEDY_ALGORITHM = false;
                        }
                        else if(token.equals("true")) {
                            ThreadDraw.GREEDY_ALGORITHM = true;
                        }
                    }
                    else if(token.equals("use_smoothing_for_processing_algs") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            ThreadDraw.USE_SMOOTHING_FOR_PROCESSING_ALGS = false;
                        }
                        else if(token.equals("true")) {
                            ThreadDraw.USE_SMOOTHING_FOR_PROCESSING_ALGS = true;
                        }
                    }
                    else if(token.equals("greedy_drawing_algorithm_use_iter_data") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            ThreadDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA = false;
                        }
                        else if(token.equals("true")) {
                            ThreadDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA = true;
                        }
                    }
                    else if(token.equals("check_bailout_during_deep_not_full_floatexp_mode") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            ThreadDraw.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE = false;
                        }
                        else if(token.equals("true")) {
                            ThreadDraw.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE = true;
                        }
                    }
                    else if(token.equals("gather_perturbation_statistics") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            ThreadDraw.GATHER_PERTURBATION_STATISTICS = false;
                        }
                        else if(token.equals("true")) {
                            ThreadDraw.GATHER_PERTURBATION_STATISTICS = true;
                        }
                    }
                    else if(token.equals("gather_tiny_ref_indexes") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            ThreadDraw.GATHER_TINY_REF_INDEXES = false;
                        }
                        else if(token.equals("true")) {
                            ThreadDraw.GATHER_TINY_REF_INDEXES = true;
                        }
                    }
                    else if(token.equals("automatic_precision") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            MyApfloat.setAutomaticPrecision = false;
                        }
                        else if(token.equals("true")) {
                            MyApfloat.setAutomaticPrecision = true;
                        }
                    }
                    else if (token.startsWith("save_settings_path") && tokenizer.countTokens() == 1) {

                        MainWindow.SaveSettingsPath = tokenizer.nextToken();

                        try {
                            Path path = Paths.get(MainWindow.SaveSettingsPath);
                            MainWindow.SaveSettingsPath = Files.notExists(path) || !Files.isDirectory(path) ? "" : MainWindow.SaveSettingsPath;
                        }
                        catch (Exception ex) {
                            MainWindow.SaveSettingsPath = "";
                        }

                    }
                    else if (token.startsWith("output_directory") && tokenizer.countTokens() == 1) {

                        outputDirectory = tokenizer.nextToken();

                        try {
                            Path path = Paths.get(outputDirectory);
                            outputDirectory = Files.notExists(path) || !Files.isDirectory(path) ? "." : outputDirectory;
                        }
                        catch (Exception ex) {
                            outputDirectory = ".";
                        }

                    }
                    else if(token.equals("greedy_drawing_algorithm_id") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp == BOUNDARY_TRACING || temp == DIVIDE_AND_CONQUER) {
                                ThreadDraw.GREEDY_ALGORITHM_SELECTION = temp;
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if (token.equals("brute_force_alg") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 2) {
                                ThreadDraw.BRUTE_FORCE_ALG = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_detection_method") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 1) {
                                LAInfo.DETECTION_METHOD = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_stage0_period_detection_threshold") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0 && temp <= 2.5) {
                                LAInfo.Stage0PeriodDetectionThreshold = temp;
                                LAInfoDeep.Stage0PeriodDetectionThreshold = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_stage0_period_detection_threshold2") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0 && temp <= 2.5) {
                                LAInfo.Stage0PeriodDetectionThreshold2 = temp;
                                LAInfoDeep.Stage0PeriodDetectionThreshold2 = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }

                    else if (token.equals("bla2_period_detection_threshold") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0 && temp <= 2.5) {
                                LAInfo.PeriodDetectionThreshold = temp;
                                LAInfoDeep.PeriodDetectionThreshold = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_period_detection_threshold2") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0 && temp <= 2.5) {
                                LAInfo.PeriodDetectionThreshold2 = temp;
                                LAInfoDeep.PeriodDetectionThreshold2 = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_la_threshold_scale") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                LAInfo.LAThresholdScale = temp;
                                LAInfoDeep.LAThresholdScale = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_la_threshold_c_scale") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                LAInfo.LAThresholdCScale = temp;
                                LAInfoDeep.LAThresholdCScale = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("nanomb1_n") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 2 && temp <= 48) {
                                ThreadDraw.NANOMB1_N = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("nanomb1_m") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 2 && temp <= 48) {
                                ThreadDraw.NANOMB1_M = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("exponent_diff_ignored") && tokenizer.countTokens() == 1) {

                        try {
                            long temp = Long.parseLong(tokenizer.nextToken());

                            if (temp >= 0) {
                                MantExp.EXPONENT_DIFF_IGNORED = temp;
                                MantExp.MINUS_EXPONENT_DIFF_IGNORED = -MantExp.EXPONENT_DIFF_IGNORED;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("skipped_pixels_coloring") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp >= 0 && temp <= 4) {
                                ThreadDraw.SKIPPED_PIXELS_ALG = temp;
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("skipped_pixels_user_color") && tokenizer.countTokens() == 3) {

                        try {
                            int red = Integer.parseInt(tokenizer.nextToken());
                            int green = Integer.parseInt(tokenizer.nextToken());
                            int blue = Integer.parseInt(tokenizer.nextToken());

                            if(red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
                                ThreadDraw.SKIPPED_PIXELS_COLOR = new Color(red, green, blue).getRGB();
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if (token.equals("perturbation_theory") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            ThreadDraw.PERTURBATION_THEORY = false;
                        } else if (token.equals("true")) {
                            ThreadDraw.PERTURBATION_THEORY = true;
                        }
                    }
                    else if (token.equals("detect_period") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            ThreadDraw.DETECT_PERIOD = false;
                        } else if (token.equals("true")) {
                            ThreadDraw.DETECT_PERIOD = true;
                        }
                    }
                    else if (token.equals("use_bignum_for_ref") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE = false;
                        } else if (token.equals("true")) {
                            ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE = true;
                        }
                    }
                    else if (token.equals("use_threads_for_sa") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            ThreadDraw.USE_THREADS_FOR_SA = false;
                        } else if (token.equals("true")) {
                            ThreadDraw.USE_THREADS_FOR_SA = true;
                        }
                    }
                    else if (token.equals("use_threads_for_bla") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            ThreadDraw.USE_THREADS_FOR_BLA = false;
                        } else if (token.equals("true")) {
                            ThreadDraw.USE_THREADS_FOR_BLA = true;
                        }
                    }
                    else if (token.equals("bla_starting_level") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0 && temp <= 32) {
                                ThreadDraw.BLA_STARTING_LEVEL = temp;
                            }
                        } catch (Exception ex) {
                        }

                    }
                    else if (token.equals("use_bignum_for_pixels") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE = false;
                        } else if (token.equals("true")) {
                            ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE = true;
                        }
                    }
                    else if (token.equals("automatic_bignum_precision") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            ThreadDraw.BIGNUM_AUTOMATIC_PRECISION = false;
                        } else if (token.equals("true")) {
                            ThreadDraw.BIGNUM_AUTOMATIC_PRECISION = true;
                        }
                    }
                    else if (token.equals("precision") && tokenizer.countTokens() == 1) {

                        try {
                            long temp = Long.parseLong(tokenizer.nextToken());

                            if (temp > 0) {
                                MyApfloat.setPrecision(temp, s);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla_precision_bits") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0 && temp < 64) {
                                ThreadDraw.BLA_BITS = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bignum_precision_factor") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0) {
                                ThreadDraw.BIGNUM_PRECISION_FACTOR = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bignum_precision_bits") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0) {
                                ThreadDraw.BIGNUM_PRECISION = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("approximation_algorithm") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 4) {
                                ThreadDraw.APPROXIMATION_ALGORITHM = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("use_full_floatexp_for_deep_zoom") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM = false;
                        } else if (token.equals("true")) {
                            ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM = true;
                        }
                    }
                    else if (token.equals("use_full_floatexp_for_all_zoom") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            ThreadDraw.USE_FULL_FLOATEXP_FOR_ALL_ZOOM = false;
                        } else if (token.equals("true")) {
                            ThreadDraw.USE_FULL_FLOATEXP_FOR_ALL_ZOOM = true;
                        }
                    }
                    else if (token.equals("series_approximation_oom_diff") && tokenizer.countTokens() == 1) {

                        try {
                            long temp = Long.parseLong(tokenizer.nextToken());
                            ThreadDraw.SERIES_APPROXIMATION_OOM_DIFFERENCE = temp;

                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("series_approximation_terms") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >=2  && temp <= 257) {
                                ThreadDraw.SERIES_APPROXIMATION_TERMS = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("series_approximation_max_skip") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >=0) {
                                ThreadDraw.SERIES_APPROXIMATION_MAX_SKIP_ITER = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("image_size") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp > 0) {
                                image_size = temp;
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if (token.equals("derivative_step") && tokenizer.countTokens() == 1) {
                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                Derivative.DZ = new Complex(temp, temp);
                                Derivative.calculateConstants();
                            }
                        } catch (Exception ex) {
                        }

                    }
                    else {
                        continue;
                    }
                }

            }

        }
        catch(FileNotFoundException ex) {

        }
        catch(IOException ex) {

        }

        MyApfloat.setPrecision(MyApfloat.precision, s);

        Location.setJitter(2);

        ColorSpaceConverter.init();

        if(ThreadDraw.PERTURBATION_THEORY || ThreadDraw.HIGH_PRECISION_CALCULATION) {
            periodicity_checking = false;
        }
    }

    public void exit(int val) {

        savePreferences();
        ThreadDraw.deleteLibs();

        new Timer().schedule(new TimerTask() {
            public void run() {
                Runtime.getRuntime().halt(-1);
            }
        }, 8000);

        System.exit(val);

    }

    private void polarLargeRender() {
        new PolarLargeRenderDialog(ptr, number_of_polar_images, polar_orientation);
    }

    private void splitImageRender() {
        new SplitImageRenderDialog(ptr, split_image_grid_dimension);
    }

    public void startSplitImageRender(int split_image_grid_dimension) {

        this.split_image_grid_dimension = split_image_grid_dimension;

        ExecutorService executor = Executors.newFixedThreadPool(1);

        executor.submit(() -> {


            int total = split_image_grid_dimension * split_image_grid_dimension;
            totalprogress.setMaximum(total);
            totalprogress.setValue(0);
            totalprogress.setString("Image: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());
            totalprogress.setVisible(true);
            overviewButton.setVisible(true);
            overviewButton.setEnabled(false);
            setOptions(false);
            runsOnSplitImageMode = true;

            for(int k = 0; k < total; k++) {

                gridI = k / split_image_grid_dimension;
                gridJ = k % split_image_grid_dimension;

                Location.offset = new SplitImagePixelOffset(image_size * split_image_grid_dimension, gridJ * image_size, gridI * image_size);

                render();


                try {
                    for (int i = 0; i < threads.length; i++) {
                        for (int j = 0; j < threads[i].length; j++) {
                            threads[i][j].join();
                        }
                    }
                } catch (InterruptedException ex) {
                }


                totalprogress.setValue(k + 1);
                totalprogress.setString("Image: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());
            }

            runsOnSplitImageMode = false;
            Location.offset = new PixelOffset();
            cleanUp();
            setOptions(true);
            totalprogress.setVisible(false);
            overviewButton.setEnabled(true);
        });
    }

    public void startLargePolarImageRender(int number_of_polar_images, int polar_orientation) {

        this.number_of_polar_images = number_of_polar_images;
        this.polar_orientation = polar_orientation;

        final int largeDimension = image_size * number_of_polar_images;
        if(polar_orientation == 0) {
            largePolarImage = new BufferedImage(largeDimension, image_size, BufferedImage.TYPE_INT_ARGB);
        }
        else {
            largePolarImage = new BufferedImage(image_size, largeDimension, BufferedImage.TYPE_INT_ARGB);
        }

        ExecutorService executor = Executors.newFixedThreadPool(1);

        Location.offset = new PixelOffset();

        executor.submit(() -> {


            totalprogress.setMaximum(number_of_polar_images);
            totalprogress.setValue(0);
            totalprogress.setString("Image: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());
            totalprogress.setVisible(true);
            overviewButton.setVisible(true);
            overviewButton.setEnabled(false);
            setOptions(false);
            runsOnLargePolarImageMode = true;

            Apfloat originalSize = s.size;

            for(int k = 0; k < number_of_polar_images; k++) {
                render();

                Apfloat start =  MyApfloat.fp.log(s.size);

                Apfloat ddimage_size = new MyApfloat(image_size);

                Apfloat ddmuly = MyApfloat.fp.divide(MyApfloat.fp.multiply(MyApfloat.fp.multiply(new MyApfloat(s.circle_period), MyApfloat.TWO), MyApfloat.getPi()), ddimage_size);

                Apfloat ddmulx = MyApfloat.fp.multiply(ddmuly, new MyApfloat(s.height_ratio));

                Apfloat end = MyApfloat.fp.add(MyApfloat.fp.multiply(ddmulx, ddimage_size), start);

                s.size = MyApfloat.exp(end);

                try {
                    for (int i = 0; i < threads.length; i++) {
                        for (int j = 0; j < threads[i].length; j++) {
                            threads[i][j].join();
                        }
                    }
                } catch (InterruptedException ex) {
                }

                int [] rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
                int [] large_rgsbs = ((DataBufferInt) largePolarImage.getRaster().getDataBuffer()).getData();

                int total = image_size * image_size;
                final int offsetJ = polar_orientation == 0 ? k * image_size : k * total;
                IntStream.range(0, total)
                        .parallel().forEach(p -> {
                                    int i = p / image_size;
                                    int j = p % image_size;
                                    if(polar_orientation == 0) {
                                        large_rgsbs[i * largeDimension + j + offsetJ] = rgbs[p];
                                    }
                                    else {
                                        large_rgsbs[i * image_size + j + offsetJ] = rgbs[j * image_size + i];
                                    }
                                });


                totalprogress.setValue(k + 1);
                totalprogress.setString("Image: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());
            }

            writeLargePolarImageToDisk();
            runsOnLargePolarImageMode = false;

            s.size = originalSize;

            cleanUp();
            setOptions(true);
            totalprogress.setVisible(false);
            overviewButton.setEnabled(true);
        });
    }

    public void startBatchRender(ArrayList<String> files, DefaultListModel<String> fileNames) {

        ExecutorService executor = Executors.newFixedThreadPool(1);

        Location.offset = new PixelOffset();

        executor.submit(() -> {


            totalprogress.setMaximum(files.size());
            totalprogress.setValue(0);
            totalprogress.setString("Files: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());
            totalprogress.setVisible(true);
            overviewButton.setVisible(true);
            overviewButton.setEnabled(false);
            setOptions(false);
            runsOnBatchingMode = true;
            batchIndex = 1;

            for(int k = 0; k < files.size(); k++) {
                try {
                    s.readSettings(files.get(k), ptr, true);
                    String fname = fileNames.get(k);

                    if(fname.length() > 45) {
                        fname = fname.substring(0, 45) + "...";
                    }
                    settings_label.setText("Loaded: " + fname);
                    settings_label.setVisible(true);

                    settingsName = fileNames.get(k).substring(0, fileNames.get(k).lastIndexOf("."));

                    render();
                }
                catch(IOException ex) {

                }
                catch(ClassNotFoundException ex) {

                }
                catch(Exception ex) {

                }

                try {
                    for (int i = 0; i < threads.length; i++) {
                        for (int j = 0; j < threads[i].length; j++) {
                            threads[i][j].join();
                        }
                    }
                } catch (InterruptedException ex) {
                }
                batchIndex++;
                totalprogress.setValue(batchIndex - 1);
                totalprogress.setString("Files: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());
            }

            runsOnBatchingMode = false;
            cleanUp();
            setOptions(true);
            totalprogress.setVisible(false);
            overviewButton.setEnabled(true);
        });


    }

    public void startSequenceRender(Apfloat size, double zoom_factor, int zooming_mode, double rotation_adjusting_value, int color_cycling_adjusting_value, double light_light_direction_adjusting_value, double bump_light_direction_adjusting_value, int zoom_every_n_frame, int gradient_color_cycling_adjusting_value) {

        ExecutorService executor = Executors.newFixedThreadPool(1);

        Location.offset = new PixelOffset();

        numberOfSequenceSteps = 0;

        Apfloat temp = zooming_mode == 0 ? size : s.size;
        while ((zooming_mode == 0 && temp.compareTo(s.size) > 0) || (zooming_mode == 1 && temp.compareTo(size) < 0)) {
            if(zooming_mode == 0) {
                temp = MyApfloat.fp.divide(temp, new MyApfloat(zoom_factor));
            }
            else {
                temp = MyApfloat.fp.multiply(temp, new MyApfloat(zoom_factor));
            }
            numberOfSequenceSteps++;
        }

        numberOfSequenceSteps++;

        this.zoom_factor = zoom_factor;
        this.zooming_mode = zooming_mode;
        this.size = size;
        this.rotation_adjusting_value = rotation_adjusting_value;
        this.color_cycling_adjusting_value = color_cycling_adjusting_value;
        this.light_light_direction_adjusting_value = light_light_direction_adjusting_value;
        this.bump_light_direction_adjusting_value = bump_light_direction_adjusting_value;
        this.zoom_every_n_frame = zoom_every_n_frame;
        this.gradient_color_cycling_adjusting_value = gradient_color_cycling_adjusting_value;

        numberOfSequenceSteps *= zoom_every_n_frame;

        executor.submit(() -> {

            totalprogress.setMaximum(numberOfSequenceSteps);
            totalprogress.setValue(0);
            totalprogress.setString("Zoom Sequence: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());
            totalprogress.setVisible(true);
            overviewButton.setVisible(true);
            overviewButton.setEnabled(false);
            setOptions(false);
            runsOnSequenceMode = true;
            sequenceIndex = 1;

            Apfloat originalSize = s.size;
            Apfloat[] originalRotCenter = new Apfloat[2];
            originalRotCenter[0] = s.fns.rotation_center[0];
            originalRotCenter[1] = s.fns.rotation_center[1];
            double originalRotation = s.fns.rotation;
            Apfloat[] originalRotVals = new Apfloat[2];
            originalRotVals[0] = s.fns.rotation_vals[0];
            originalRotVals[1] = s.fns.rotation_vals[1];
            int originalColorCyclingLocation = s.ps.color_cycling_location;
            int originalColorCyclingLocation2 = s.ps2.color_cycling_location;
            double originalBmsLightDirection = s.bms.lightDirectionDegrees;
            double originalLightDirection = s.ls.light_direction;
            double [] originalLightVector = new double[2];
            originalLightVector[0] = s.ls.lightVector[0];
            originalLightVector[1] = s.ls.lightVector[1];
            int originalGradientColorCyclingLocation = s.gs.gradient_offset;

            if(rotation_adjusting_value != 0) {
                s.fns.rotation_center[0] = s.xCenter;
                s.fns.rotation_center[1] = s.yCenter;
            }

            s.size = zooming_mode == 0 ? size : s.size;
            for(int k = 1; k <= numberOfSequenceSteps; k++) {
                render();
                try {
                    for (int i = 0; i < threads.length; i++) {
                        for (int j = 0; j < threads[i].length; j++) {
                            threads[i][j].join();
                        }
                    }
                } catch (InterruptedException ex) {
                }
                sequenceIndex++;
                totalprogress.setValue(sequenceIndex - 1);
                totalprogress.setString("Zoom Sequence: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());

                if(k >= zoom_every_n_frame && k % zoom_every_n_frame == 0) {
                    if (zooming_mode == 0) {
                        s.size = MyApfloat.fp.divide(s.size, new MyApfloat(zoom_factor));
                    } else {
                        s.size = MyApfloat.fp.multiply(s.size, new MyApfloat(zoom_factor));
                    }
                }

                if(rotation_adjusting_value != 0) {
                    s.fns.rotation += rotation_adjusting_value;
                    if (rotation_adjusting_value > 0) {
                        s.fns.rotation = s.fns.rotation % 360.0;
                    } else if (rotation_adjusting_value < 0) {
                        if (s.fns.rotation <= -360) {
                            s.fns.rotation += 360;
                        }
                    }

                    Apfloat tempRadians =  MyApfloat.fp.toRadians(new MyApfloat(s.fns.rotation));
                    s.fns.rotation_vals[0] = MyApfloat.cos(tempRadians);
                    s.fns.rotation_vals[1] = MyApfloat.sin(tempRadians);
                }

                if(color_cycling_adjusting_value != 0) {
                    s.ps.color_cycling_location += color_cycling_adjusting_value;

                    s.ps.color_cycling_location = s.ps.color_cycling_location > Integer.MAX_VALUE - 40000 ? 0 : s.ps.color_cycling_location;

                    s.ps2.color_cycling_location += color_cycling_adjusting_value;

                    s.ps2.color_cycling_location = s.ps2.color_cycling_location > Integer.MAX_VALUE - 40000 ? 0 : s.ps2.color_cycling_location;

                }

                if(gradient_color_cycling_adjusting_value != 0) {
                    s.gs.gradient_offset += gradient_color_cycling_adjusting_value;
                    s.gs.gradient_offset = s.gs.gradient_offset > Integer.MAX_VALUE - 40000 ? 0 : s.gs.gradient_offset;
                }

                if (s.bms.bump_map && bump_light_direction_adjusting_value != 0) {
                    s.bms.lightDirectionDegrees += bump_light_direction_adjusting_value;
                    if (bump_light_direction_adjusting_value > 0) {
                        s.bms.lightDirectionDegrees = s.bms.lightDirectionDegrees % 360.0;
                    }
                    else {
                        if (s.bms.lightDirectionDegrees <= -360) {
                            s.bms.lightDirectionDegrees += 360;
                        }
                    }
                }

                if (s.ls.lighting && light_light_direction_adjusting_value != 0) {
                    s.ls.light_direction += light_light_direction_adjusting_value;

                    if(light_light_direction_adjusting_value > 0) {
                        s.ls.light_direction = s.ls.light_direction % 360.0;
                    }
                    else {
                        if (s.ls.light_direction < 0) {
                            s.ls.light_direction += 360;
                        }
                    }

                    double lightAngleRadians = Math.toRadians(s.ls.light_direction);
                    s.ls.lightVector[0] = Math.cos(lightAngleRadians) * s.ls.light_magnitude;
                    s.ls.lightVector[1] = Math.sin(lightAngleRadians) * s.ls.light_magnitude;
                }
            }

            runsOnSequenceMode = false;
            cleanUp();

            //Rollback
            s.size = originalSize;
            s.fns.rotation_center[0] = originalRotCenter[0];
            s.fns.rotation_center[1] = originalRotCenter[1];
            s.fns.rotation =  originalRotation;
            s.fns.rotation_vals[0] = originalRotVals[0];
            s.fns.rotation_vals[1] = originalRotVals[1];
            s.ps.color_cycling_location = originalColorCyclingLocation;
            s.ps2.color_cycling_location = originalColorCyclingLocation2;
            s.bms.lightDirectionDegrees =  originalBmsLightDirection;
            s.ls.light_direction =  originalLightDirection;
            s.ls.lightVector[0] = originalLightVector[0];
            s.ls.lightVector[1] = originalLightVector[1];
            s.gs.gradient_offset = originalGradientColorCyclingLocation;

            setOptions(true);
            totalprogress.setVisible(false);
            overviewButton.setEnabled(true);
        });


    }

    public void setOutputDirectory() {

        file_chooser = new JFileChooser(outputDirectory);

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = file_chooser.showDialog(this, "Set Output Directory");

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = file_chooser.getSelectedFile();
            outputDirectory = file.toString();
        }
    }

    public static void main(String[] args) throws Exception {

        if(args.length > 0 && args[0].equals("l4jini")) {
            CommonFunctions.exportL4jIni("FZImageExpander", Constants.IEL4j);
        }

        ImageExpanderWindow mw = new ImageExpanderWindow();
        mw.setVisible(true);

        boolean actionOk = mw.getCommonFunctions().copyLib();
        mw.getCommonFunctions().exportCodeFilesNoUi();
        
        if(actionOk) {
            mw.getCommonFunctions().compileCode(false);
        }
        
        mw.getCommonFunctions().checkForUpdate(false);

    }
}
