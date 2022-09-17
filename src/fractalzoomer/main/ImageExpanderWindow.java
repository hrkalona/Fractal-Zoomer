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
import fractalzoomer.core.drawing_algorithms.BoundaryTracingDraw;
import fractalzoomer.core.drawing_algorithms.BruteForce2Draw;
import fractalzoomer.core.drawing_algorithms.BruteForceDraw;
import fractalzoomer.core.drawing_algorithms.DivideAndConquerDraw;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.gui.*;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.parser.ParserException;
import fractalzoomer.utils.RefreshMemoryTask;
import fractalzoomer.utils.ThreadSplitCoordinates;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Timer;

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
    private JButton renderButton;
    private JButton compileButton;
    private JButton threadsButton;
    private JButton imageSizeButton;
    private JButton greedyAlgorithmsButton;
    private JButton perturbationTheoryButton;
    private JButton aboutButton;
    private JButton helpButton;
    private JButton overviewButton;
    private JProgressBar progress;
    private JFileChooser file_chooser;
    private ImageExpanderWindow ptr;
    private ThreadDraw[][] threads;
    private BufferedImage image;
    private long calculation_time;
    private boolean greedy_algorithm;
    private int greedy_algorithm_selection;
    private boolean periodicity_checking;
    private JLabel settings_label;
    private MemoryLabel memory_label;
    private Timer timer;
    private CommonFunctions common;
    private int brute_force_alg;

    public ImageExpanderWindow() {
        super();

        ptr = this;

        s = new Settings();
        s.applyStaticSettings();

        brute_force_alg = 0;

        n = 2;
        thread_grouping = 0;

        greedy_algorithm = true;
        greedy_algorithm_selection = BOUNDARY_TRACING;
        periodicity_checking = false;

        Locale.setDefault(Locale.US);

        image_size = 1000;
        setSize(540, 420);
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
        main_panel.setLayout(new GridLayout(7, 1));
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

        JPanel p2 = new JPanel();
        p2.setBackground(Constants.bg_color);
        p2.add(loadButton);

        imageSizeButton = new JButton("Image Size", MainWindow.getIcon("image_size.png"));
        imageSizeButton.setFocusable(false);
        imageSizeButton.setPreferredSize(buttonDimension);
        imageSizeButton.setToolTipText("Sets the image size.");
        imageSizeButton.setEnabled(false);

        imageSizeButton.addActionListener(e -> setSizeOfImage());

        p2.add(imageSizeButton);

        threadsButton = new JButton("Threads", MainWindow.getIcon("threads.png"));
        threadsButton.setFocusable(false);
        threadsButton.setPreferredSize(buttonDimension);
        threadsButton.setToolTipText("Sets the number of parallel drawing threads.");

        threadsButton.addActionListener(e -> setThreadsNumber());

        JPanel p3 = new JPanel();
        p3.setBackground(Constants.bg_color);
        p3.add(threadsButton);

        greedyAlgorithmsButton = new JButton("Greedy Drawing Algorithms", MainWindow.getIcon("greedy_algorithm.png"));
        greedyAlgorithmsButton.setFocusable(false);
        greedyAlgorithmsButton.setPreferredSize(buttonDimension);
        greedyAlgorithmsButton.setToolTipText("Sets the greedy algorithms options.");
        greedyAlgorithmsButton.setEnabled(false);

        greedyAlgorithmsButton.addActionListener(e -> setGreedyAlgorithms());

        p3.add(greedyAlgorithmsButton);

        renderButton = new JButton("Render Image", MainWindow.getIcon("save_image.png"));
        renderButton.setFocusable(false);
        renderButton.setEnabled(false);
        renderButton.setPreferredSize(buttonDimension);
        renderButton.setToolTipText("Renders the image and saves it to disk.");

        renderButton.addActionListener(e -> render());

        JPanel p6 = new JPanel();
        p6.setBackground(Constants.bg_color);

        perturbationTheoryButton = new JButton("Perturbation Theory", MainWindow.getIcon("perturbation.png"));
        perturbationTheoryButton.setFocusable(false);
        perturbationTheoryButton.setPreferredSize(buttonDimension);
        perturbationTheoryButton.setToolTipText("Sets the perturbation theory settings.");
        perturbationTheoryButton.setEnabled(false);

        perturbationTheoryButton.addActionListener(e -> setPerturbationTheory());

        p6.add(perturbationTheoryButton);

        compileButton = new JButton("Compile User Code", MainWindow.getIcon("compile.png"));
        compileButton.setFocusable(false);
        compileButton.setPreferredSize(buttonDimension);
        compileButton.setToolTipText("Compiles the java code, containing the user defined functions.");

        compileButton.addActionListener(e -> common.compileCode(true));

        JPanel p4 = new JPanel();
        p4.setBackground(Constants.bg_color);
        p4.add(compileButton);
        p4.add(renderButton);

        aboutButton = new JButton("About", MainWindow.getIcon("about.png"));
        aboutButton.setFocusable(false);
        aboutButton.setPreferredSize(buttonDimension);

        aboutButton.addActionListener(e -> displayAboutInfo());

        helpButton = new JButton("Help", MainWindow.getIcon("help.png"));
        helpButton.setFocusable(false);
        helpButton.setPreferredSize(buttonDimension);

        helpButton.addActionListener(e -> displayHelp());

        JPanel p5 = new JPanel();
        p5.setBackground(Constants.bg_color);
        p5.add(helpButton);
        p5.add(aboutButton);

        progress = new JProgressBar();
        progress = new JProgressBar();
        progress.setPreferredSize(new Dimension(220, 22));
        progress.setMaximumSize(progress.getPreferredSize());
        //progress.setMinimumSize(progress.getPreferredSize());
        progress.setStringPainted(true);
        progress.setForeground(Constants.progress_color);
        progress.setValue(0);

        progress.setVisible(false);

        memory_label = new MemoryLabel(220);
        memory_label.setVisible(false);

        JPanel stats = new JPanel();
        stats.setBackground(Constants.bg_color);
        stats.add(memory_label);
        stats.add(progress);

        main_panel.add(p1);
        main_panel.add(p2);
        main_panel.add(p3);
        main_panel.add(p6);
        main_panel.add(p4);
        main_panel.add(p5);
        main_panel.add(stats);

        RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
        round_panel.setBackground(Constants.bg_color);
        round_panel.setPreferredSize(new Dimension(490, 330));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(main_panel, con);

        setLayout(new GridBagLayout());
        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;
        add(round_panel, con);

        common = new CommonFunctions(ptr, runsOnWindows);

    }

    public void setOptions(boolean opt) {

        loadButton.setEnabled(opt);
        perturbationTheoryButton.setEnabled(opt);
        renderButton.setEnabled(opt);
        compileButton.setEnabled(opt);
        threadsButton.setEnabled(opt);
        imageSizeButton.setEnabled(opt);
        greedyAlgorithmsButton.setEnabled(opt);

    }

    public void loadSettings() {

        file_chooser = new JFileChooser(MainWindow.SaveSettingsPath.isEmpty() ? "." : MainWindow.SaveSettingsPath);

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fractal Zoomer Settings (*.fzs)", "fzs"));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
            FileNameExtensionFilter filter = (FileNameExtensionFilter)evt.getNewValue();

            String extension = filter.getExtensions()[0];

            String file_name = ((BasicFileChooserUI)file_chooser.getUI()).getFileName();

            int index = file_name.lastIndexOf(".");

            if(index != -1) {
                file_name = file_name.substring(0, index);
            }

            file_chooser.setSelectedFile(new File(file_name + "." + extension));
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
                overviewButton.setVisible(true);
                imageSizeButton.setEnabled(true);
                greedyAlgorithmsButton.setEnabled(true);
                perturbationTheoryButton.setEnabled(true);

                MainWindow.SaveSettingsPath = file.getParent();
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

        clearThreads();

        threads = null;
    }

    private void render() {

        try {
            if(timer == null) {
                timer = new Timer();
                timer.schedule(new RefreshMemoryTask(memory_label), 2000, 2000);
            }

            ThreadDraw.setArraysExpander(image_size);

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
            ThreadDraw.resetThreadData(n * n);
        }
        else {
            threads = new ThreadDraw[1][n];
            ThreadDraw.resetThreadData(n);
        }

        try {
            for(int i = 0; i < threads.length; i++) {
                for(int j = 0; j < threads[i].length; j++) {

                    ThreadSplitCoordinates tsc = ThreadSplitCoordinates.get(j, i, thread_grouping, n, image_size);
                    if(s.fns.julia) {
                        if(greedy_algorithm) {
                            if(greedy_algorithm_selection == BOUNDARY_TRACING) {
                                threads[i][j] = new BoundaryTracingDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js, s.xJuliaCenter, s.yJuliaCenter);
                            }
                            else if(greedy_algorithm_selection == DIVIDE_AND_CONQUER) {
                                threads[i][j] = new DivideAndConquerDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js, s.xJuliaCenter, s.yJuliaCenter);
                            }
                        }
                        else {
                            if (brute_force_alg == 0) {
                                threads[i][j] = new BruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js, s.xJuliaCenter, s.yJuliaCenter);
                            }
                            else {
                                threads[i][j] = new BruteForce2Draw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js, s.xJuliaCenter, s.yJuliaCenter);
                            }
                        }
                    }
                    else {
                        if(greedy_algorithm) {
                            if(greedy_algorithm_selection == BOUNDARY_TRACING) {
                                threads[i][j] = new BoundaryTracingDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js);
                            }
                            else if(greedy_algorithm_selection == DIVIDE_AND_CONQUER) {
                                threads[i][j] = new DivideAndConquerDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js);
                            }
                        }
                        else {
                            if (brute_force_alg == 0) {
                                threads[i][j] = new BruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js);
                            }
                            else {
                                threads[i][j] = new BruteForce2Draw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.hss, s.contourFactor, s.gps, s.js);
                            }
                        }
                    }
                    threads[i][j].setThreadId(i * threads.length + j);
                }
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

    public void writeImageToDisk() {
        try {
            String name = "fractal " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH;mm;ss").format(LocalDateTime.now()) + ".png";
            File file = new File(name);
            ImageIO.write(image, "png", file);
        }
        catch(IOException ex) {
        }
        cleanUp();
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

        new GreedyAlgorithmsFrame(ptr, greedy_algorithm, greedy_algorithm_selection, brute_force_alg);

    }

    public void setPerturbationTheory() {
        new PerturbationTheoryDialog(ptr, s);
    }

    public void boundaryTracingOptionsChanged(boolean greedy_algorithm, int algorithm, int brute_force_alg) {

        this.greedy_algorithm = greedy_algorithm;
        greedy_algorithm_selection = algorithm;
        this.brute_force_alg = brute_force_alg;

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

        JOptionPane.showMessageDialog(ptr, "<html><center><font size='5' face='arial' color='blue'><b><u>Fractal Zoomer Image Expander</u></b></font><br><br><font size='4' face='arial'><img src=\"" + getClass().getResource("mandelExpander.png") + "\"><br><br>Version: <b>" + versionStr + "</b><br><br>Author: <b>Christos Kalonakis</b><br><br>Contact: <a href=\"mailto:hrkalona@gmail.com\">hrkalona@gmail.com</a><br><br></center></font></html>", "About", JOptionPane.INFORMATION_MESSAGE);

    }

    private void displayHelp() {

        JEditorPane textArea = new JEditorPane();

        textArea.setEditable(false);
        textArea.setContentType("text/html");
        textArea.setPreferredSize(new Dimension(550, 380));

        JScrollPane scroll_pane_2 = new JScrollPane(textArea);

        String help = "<html><center><font size='5' face='arial' color='blue'><b><u>Help</u></b></font></center><br>"
                + "<font size='4' face='arial'>This tool lets you create larger images than the main application.<br><br>"
                + "Basically the main application was set to reach maximum 1.1Gb of heap.<br>"
                + "For that reason a limit size of 6000x6000 was set.<br><br>"
                + "In order to use this tool the right way you must set the JVM's heap size, through<br>"
                + "command line. For instance to execute it using the jar file, use<br>"
                + "<b>java -jar -Xmx2000m FZImageExpander.jar</b> in order to request maximum 2Gb<br>"
                + "of Heap from the JVM.<br><br>"
                + "Please check some online java tutorials for more thorough heap size allocation!<br><br>"
                + "If you are using the *.exe version of the application please edit<br>"
                + "<b>FZImageExpander.l4j.ini</b> and add <b>-Xmx2000m</b> or any other memory size<br>"
                + "value into the *.ini file. The *.ini file name must match the name of the executable.<br><br>"
                
                + "If you dont set the maximum heap, the JVM's default will be used,<br>"
                + "which scales based on your memory, and it might be lower than the main application.</font></html>";

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
            writer.println("save_image_path \"" + MainWindow.SaveImagesPath + "\"");

            writer.println("[Optimizations]");
            writer.println("thread_dim " + n);
            writer.println("thread_grouping " + thread_grouping);
            writer.println("greedy_drawing_algorithm " + greedy_algorithm);
            writer.println("greedy_drawing_algorithm_id " + greedy_algorithm_selection);
            writer.println("skipped_pixels_coloring " + ThreadDraw.SKIPPED_PIXELS_ALG);
            int color = ThreadDraw.SKIPPED_PIXELS_COLOR;
            writer.println("skipped_pixels_user_color " + ((color >> 16) & 0xff) + " " + ((color >> 8) & 0xff) + " " + (color & 0xff));
            writer.println("perturbation_theory " + ThreadDraw.PERTURBATION_THEORY);
            writer.println("precision " + MyApfloat.precision);
            writer.println("approximation_algorithm " + ThreadDraw.APPROXIMATION_ALGORITHM);
            writer.println("series_approximation_terms " + ThreadDraw.SERIES_APPROXIMATION_TERMS);
            writer.println("series_approximation_oom_diff " + ThreadDraw.SERIES_APPROXIMATION_OOM_DIFFERENCE);
            writer.println("series_approximation_max_skip " + ThreadDraw.SERIES_APPROXIMATION_MAX_SKIP_ITER);
            writer.println("use_full_floatexp " + ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM);
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
            writer.println("brute_force_alg " + brute_force_alg);
            writer.println("exponent_diff_ignored " + MantExp.EXPONENT_DIFF_IGNORED);
            writer.println("bignum_library " + ThreadDraw.BIGNUM_LIBRARY);
            writer.println("automatic_precision " + MyApfloat.setAutomaticPrecision);
            writer.println("nanomb1_n " + ThreadDraw.NANOMB1_N);
            writer.println("nanomb1_m " + ThreadDraw.NANOMB1_M);
            
            writer.println();
            
            writer.println("[Window]");
            writer.println("image_size " + image_size);

            writer.println();
            writer.println("[Core]");
            writer.println("derivative_step " + Derivative.DZ.getRe());

            writer.close();
        }
        catch(FileNotFoundException ex) {

        }
    }
    
    private void loadPreferences() {
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader("IEpreferences.ini"));
            String str_line;

            while((str_line = br.readLine()) != null) {

                StringTokenizer tokenizer;

                if(str_line.startsWith("save_settings_path") || str_line.startsWith("save_image_path")) {
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

                            if (temp >= 0 && temp <= 3) {
                                ThreadDraw.BIGNUM_LIBRARY = temp;
                            }
                        } catch (Exception ex) {
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
                            greedy_algorithm = false;
                        }
                        else if(token.equals("true")) {
                            greedy_algorithm = true;
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
                    else if (token.startsWith("save_image_path") && tokenizer.countTokens() == 1) {

                        MainWindow.SaveImagesPath = tokenizer.nextToken();

                        try {
                            Path path = Paths.get(MainWindow.SaveImagesPath);
                            MainWindow.SaveImagesPath = Files.notExists(path) || !Files.isDirectory(path) ? "" : MainWindow.SaveImagesPath;
                        }
                        catch (Exception ex) {
                            MainWindow.SaveImagesPath = "";
                        }

                    }
                    else if(token.equals("greedy_drawing_algorithm_id") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp == BOUNDARY_TRACING || temp == DIVIDE_AND_CONQUER) {
                                greedy_algorithm_selection = temp;
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if (token.equals("brute_force_alg") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 1) {
                                brute_force_alg = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("nanomb1_n") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 2 && temp <= 32) {
                                ThreadDraw.NANOMB1_N = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("nanomb1_m") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 2 && temp <= 32) {
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

                            if (temp >= 0 && temp <= 3) {
                                ThreadDraw.APPROXIMATION_ALGORITHM = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("use_full_floatexp") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM = false;
                        } else if (token.equals("true")) {
                            ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM = true;
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

        if(ThreadDraw.PERTURBATION_THEORY) {
            periodicity_checking = false;
        }
    }

    public void exit(int val) {

        LibMpfr.delete();
        savePreferences();
        System.exit(val);

    }

    /*public static void main(String[] args) throws Exception {

        ImageExpanderWindow mw = new ImageExpanderWindow();
        mw.setVisible(true);

        boolean actionOk = mw.getCommonFunctions().copyLib();
        mw.getCommonFunctions().exportCodeFilesNoUi();
        
        if(actionOk) {
            mw.getCommonFunctions().compileCode(false);
        }
        
        mw.getCommonFunctions().checkForUpdate(false);

    }*/
}
