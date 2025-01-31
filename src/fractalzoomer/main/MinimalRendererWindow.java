
package fractalzoomer.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import fractalzoomer.core.*;
import fractalzoomer.core.approximation.la_zhuoran.MagnitudeDetection;
import fractalzoomer.core.approximation.la_zhuoran.MagnitudeDetectionDeep;
import fractalzoomer.core.interpolation.LinearInterpolation;
import fractalzoomer.core.approximation.la_zhuoran.LAReference;
import fractalzoomer.core.approximation.la_zhuoran.impl.LAInfo;
import fractalzoomer.core.approximation.la_zhuoran.impl.LAInfoDeep;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.approximation.mip_la_zhuoran.MipLAStep;
import fractalzoomer.core.reference.ReferenceCompressor;
import fractalzoomer.core.rendering_algorithms.*;
import fractalzoomer.functions.Fractal;
import fractalzoomer.gui.*;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.parser.FunctionDerivative2ArgumentsExpressionNode;
import fractalzoomer.parser.ParserException;
import fractalzoomer.utils.*;
import org.apfloat.Apfloat;

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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;

import static fractalzoomer.gui.CpuLabel.CPU_DELAY;
import static fractalzoomer.gui.MemoryLabel.MEMORY_DELAY;
import static fractalzoomer.main.MainWindow.saveImage;

/**
 *
 * @author hrkalona2
 */
public class MinimalRendererWindow extends JFrame implements Constants {
	private static final long serialVersionUID = 2304630285456716327L;

    private ArrayList<Future<?>> futures = new ArrayList<>();
	private Settings s;
    private ZoomSequenceSettings zss;
    private int n;
    private int m;

    private String lastLoadedFile;
    private int thread_grouping;
    private int image_width;
    private int image_height;
    private JButton loadButton;

    private JButton batchRenderButton;

    private JButton sequenceRenderButton;
    private JButton renderButton;

    private JButton polarLargeRenderButton;

    private JButton splitImageRenderButton;
    private JButton compileButton;
    private JButton threadsButton;
    private JButton imageSizeButton;
    private JButton renderingAlgorithmsButton;
    private JButton perturbationTheoryButton;
    private JButton aboutButton;
    private JButton helpButton;
    private JButton overviewButton;
    private JButton metricsButton;
    private JButton statsButton;
    private JButton taskStatsButton;
    private JProgressBar progress;
    private JProgressBar totalprogress;
    private JFileChooser file_chooser;

    private JButton outputDirectoryButton;

    private JButton multipleRendersTrendButton;
    private JButton stopRenderButton;

    private JDialog previewFrame;
    private JPanel previewPanel;
    private MinimalRendererWindow ptr;
    private TaskRender[][] tasks;
    private BufferedImage image;
    private BufferedImage largePolarImage;
    private long calculation_time;
    private boolean periodicity_checking;
    private JLabel settings_label;
    private MemoryLabel memory_label;
    private CpuLabel cpuLabel;
    private Timer timer;
    private Timer timer2;
    private CommonFunctions common;
    private boolean runsOnBatchingMode;
    private boolean runsOnSequenceMode;

    private boolean runsOnLargePolarImageMode;

    private boolean runsOnSplitImageMode;
    private int batchIndex;
    private long sequenceIndex;

    private long sequenceIndexOffset;

    private long numberOfSequenceSteps;

    public static String outputDirectory = ".";

    private int number_of_polar_images = 5;
    private int polar_orientation = 0;

    private int split_image_grid_dimension = 2;

    private int downscale_algorithm = 1;

    private int gridI;
    private int gridJ;

    private String settingsName;
    private int imageFormat;
    private float jpegQuality = 0.75f;
    private int downscaleFactor = 1;

    private BufferedImage preview_image;
    private static final int PREVIEW_IMAGE_SIZE = 300;

    private static final String TITLE = "Fractal Zoomer Minimal Renderer";
    private static final String PREVIEW_TITLE = "Preview";

    private boolean stopped;
    private String largePolarStats;
    private long imageWriteTime;

    public MinimalRendererWindow() {
        super();

        preloadPreferences();

        NativeLoader.init();

        ptr = this;

        imageFormat = 0;

        largePolarStats = "";

        stopped = false;

        s = new Settings();
        s.applyStaticSettings();
        zss = new ZoomSequenceSettings();

        TaskRender.ALWAYS_SAVE_EXTRA_PIXEL_DATA_ON_AA = false;

        TaskRender.USE_NON_BLOCKING_RENDERING = false;

        int procs = Runtime.getRuntime().availableProcessors();
        ArrayList<Integer> factors = CommonFunctions.getAllFactors(procs);
        int index = factors.size() / 2 - 1;
        m = factors.get(index);
        n = factors.get(index + 1);

        TaskRender.initThreadPoolExecutor(m * n);
        TaskRender.single_thread_executor = Executors.newSingleThreadExecutor();
        TaskRender.approximation_thread_executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(procs);

        thread_grouping = 3;

        periodicity_checking = false;

        Locale.setDefault(Locale.US);

        image_width = 1000;
        image_height = 1000;
        setSize(615, 605);
        setTitle(TITLE);

        loadPreferences();

        previewPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2d = ((Graphics2D)g);
                //if(ptr.getOrbit()) {
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                //}
                g.drawImage(preview_image, 0, 0, null);

            }
        };

        previewFrame = new JDialog();
        previewFrame.setTitle(PREVIEW_TITLE);
        previewFrame.setContentPane(previewPanel);
        previewFrame.setResizable(false);
        previewFrame.setIconImage(MainWindow.getIcon("mandelMinimalRenderer.png").getImage());
        previewFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        previewFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {



            }
        });

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
        main_panel.setLayout(new GridLayout(10, 1));
        main_panel.setBackground(Constants.bg_color);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                exit(0);

            }
        });

        setIconImage(MainWindow.getIcon("mandelMinimalRenderer.png").getImage());

        settings_label = new JLabel("");
        settings_label.setIcon(MainWindow.getIcon("checkmark.png"));
        settings_label.setVisible(false);
        settings_label.setPreferredSize(new Dimension(380, 32));

        overviewButton = new MyButton("", MainWindow.getIcon("overview.png"));
        overviewButton.setFocusable(false);
        overviewButton.setToolTipText("Creates a report of all the active fractal options.");
        overviewButton.setVisible(false);
        overviewButton.addActionListener(e -> overview());

        metricsButton = new MyButton("", MainWindow.getIcon("chart.png"));
        metricsButton.setFocusable(false);
        metricsButton.setToolTipText("Displays some time-series metrics.");
        metricsButton.addActionListener(e -> Metrics());

        statsButton = new MyButton("", MainWindow.getIcon("stats.png"));
        statsButton.setFocusable(false);
        statsButton.setToolTipText("Displays the statistics of last rendered fractal.");
        statsButton.setEnabled(false);
        statsButton.addActionListener(e -> Stats());

        taskStatsButton = new MyButton("", MainWindow.getIcon("stats_tasks.png"));
        taskStatsButton.setFocusable(false);
        taskStatsButton.setToolTipText("Displays the task statistics of last rendered fractal.");
        taskStatsButton.addActionListener(e -> TaskStats());
        taskStatsButton.setEnabled(false);

        multipleRendersTrendButton = new MyButton("", MainWindow.getIcon("line_chart.png"));
        multipleRendersTrendButton.setFocusable(false);
        multipleRendersTrendButton.setToolTipText("Displays a trend of some statistics.");
        multipleRendersTrendButton.addActionListener(e -> TrendStats());
        multipleRendersTrendButton.setEnabled(false);

        JPanel p1 = new JPanel();
        p1.setBackground(Constants.bg_color);
        p1.add(settings_label);
        p1.add(overviewButton);

        stopRenderButton = new MyButton("");
        stopRenderButton.setIcon(MainWindow.getIcon("abort.png"));
        stopRenderButton.setFocusable(false);
        stopRenderButton.setEnabled(false);
        stopRenderButton.setToolTipText("Stops the render.");
        stopRenderButton.addActionListener(e -> {
            stopped = true;
            stopRenderButton.setEnabled(false);
        });

        JPanel p10 = new JPanel(new FlowLayout());
        p10.setBackground(Constants.bg_color);
        p10.add(statsButton);
        p10.add(taskStatsButton);
        p10.add(stopRenderButton);
        p10.add(multipleRendersTrendButton);
        p10.add(metricsButton);

        Dimension buttonDimension = new Dimension(220, 32);

        loadButton = new MyButton("Load", MainWindow.getIcon("load.png"));
        loadButton.setFocusable(false);
        loadButton.setPreferredSize(buttonDimension);
        loadButton.setToolTipText("<html>Loads the function, plane, center, size, color options, iterations,<br> rotation, perturbation, initial value, bailout, julia settings,<br>and image filters.</html>");

        loadButton.addActionListener(e -> loadSettings());

        batchRenderButton = new MyButton("Batch Render", MainWindow.getIcon("batch_render.png"));
        batchRenderButton.setFocusable(false);
        batchRenderButton.setPreferredSize(buttonDimension);
        batchRenderButton.setToolTipText("Renders a batch of parameters.");

        batchRenderButton.addActionListener(e -> batchRender());

        sequenceRenderButton = new MyButton("Zoom Sequence Render", MainWindow.getIcon("sequence.png"));
        sequenceRenderButton.setFocusable(false);
        sequenceRenderButton.setPreferredSize(buttonDimension);
        sequenceRenderButton.setToolTipText("Renders a zoom sequence.");
        sequenceRenderButton.setEnabled(false);
        sequenceRenderButton.addActionListener(e -> sequenceRender());


        imageSizeButton = new MyButton("Image Size", MainWindow.getIcon("image_size.png"));
        imageSizeButton.setFocusable(false);
        imageSizeButton.setPreferredSize(buttonDimension);
        imageSizeButton.setToolTipText("Sets the image size.");

        imageSizeButton.addActionListener(e -> setSizeOfImage());


        threadsButton = new MyButton("Threads", MainWindow.getIcon("threads.png"));
        threadsButton.setFocusable(false);
        threadsButton.setPreferredSize(buttonDimension);
        threadsButton.setToolTipText("Sets the number of parallel rendering threads.");

        threadsButton.addActionListener(e -> setThreadsNumber());



        renderingAlgorithmsButton = new MyButton("Rendering Algorithms", MainWindow.getIcon("rendering_algorithm.png"));
        renderingAlgorithmsButton.setFocusable(false);
        renderingAlgorithmsButton.setPreferredSize(buttonDimension);
        renderingAlgorithmsButton.setToolTipText("Sets the rendering algorithms options.");

        renderingAlgorithmsButton.addActionListener(e -> setRenderingAlgorithms());


        renderButton = new MyButton("Render Image", MainWindow.getIcon("save_image.png"));
        renderButton.setFocusable(false);
        renderButton.setEnabled(false);
        renderButton.setPreferredSize(buttonDimension);
        renderButton.setToolTipText("Renders the image and saves it to disk.");

        renderButton.addActionListener(e -> {
            startGlobalTimer();
            Location.offset = new PixelOffset();
            render();
        });

        polarLargeRenderButton = new MyButton("Large Polar Image", MainWindow.getIcon("polar_projection.png"));
        polarLargeRenderButton.setFocusable(false);
        polarLargeRenderButton.setEnabled(false);
        polarLargeRenderButton.setPreferredSize(buttonDimension);
        polarLargeRenderButton.setToolTipText("Renders a large polar projection image and saves it to disk.");

        polarLargeRenderButton.addActionListener(e -> polarLargeRender());

        splitImageRenderButton = new MyButton("Split Image Render", MainWindow.getIcon("split_image.png"));
        splitImageRenderButton.setFocusable(false);
        splitImageRenderButton.setEnabled(false);
        splitImageRenderButton.setPreferredSize(buttonDimension);
        splitImageRenderButton.setToolTipText("Renders a grid of images.");

        splitImageRenderButton.addActionListener(e -> splitImageRender());

        perturbationTheoryButton = new MyButton("Perturbation Theory", MainWindow.getIcon("perturbation.png"));
        perturbationTheoryButton.setFocusable(false);
        perturbationTheoryButton.setPreferredSize(buttonDimension);
        perturbationTheoryButton.setToolTipText("Sets the perturbation theory settings.");

        perturbationTheoryButton.addActionListener(e -> setPerturbationTheory());

        outputDirectoryButton = new MyButton("Output Directory");
        outputDirectoryButton.setIcon(MainWindow.getIcon("output_directory.png"));
        outputDirectoryButton.setFocusable(false);
        outputDirectoryButton.setPreferredSize(buttonDimension);
        outputDirectoryButton.setToolTipText("Set the output directory.");


        outputDirectoryButton.addActionListener(e -> setOutputDirectory());


        compileButton = new MyButton("Compile User Code", MainWindow.getIcon("compile.png"));
        compileButton.setFocusable(false);
        compileButton.setPreferredSize(buttonDimension);
        compileButton.setToolTipText("Compiles the java code, containing the user defined functions.");

        compileButton.addActionListener(e -> common.compileCode(true));

        aboutButton = new MyButton("About", MainWindow.getIcon("about.png"));
        aboutButton.setFocusable(false);
        aboutButton.setPreferredSize(buttonDimension);

        aboutButton.addActionListener(e -> displayAboutInfo());

        helpButton = new MyButton("Help", MainWindow.getIcon("help.png"));
        helpButton.setFocusable(false);
        helpButton.setPreferredSize(buttonDimension);

        helpButton.addActionListener(e -> displayHelp());

        progress = new JProgressBar();
        progress.setPreferredSize(new Dimension(445, 22));
        progress.setMaximumSize(progress.getPreferredSize());
        if(MainWindow.useCustomLaf) {
            progress.setFont(new Font("Arial", Font.PLAIN, 11));
        }
        //progress.setMinimumSize(progress.getPreferredSize());
        progress.setStringPainted(true);
        progress.setForeground(Constants.progress_color);
        progress.setValue(0);

        progress.setVisible(false);


        totalprogress = new JProgressBar();
        totalprogress.setPreferredSize(new Dimension(445, 22));
        if(MainWindow.useCustomLaf) {
            totalprogress.setFont(new Font("Arial", Font.PLAIN, 11));
        }
        totalprogress.setMaximumSize(progress.getPreferredSize());
        //progress.setMinimumSize(progress.getPreferredSize());
        totalprogress.setStringPainted(true);
        totalprogress.setForeground(Constants.progress_filters_color);
        totalprogress.setValue(0);

        totalprogress.setVisible(false);

        memory_label = new MemoryLabel(220, 20);
        memory_label.setVisible(false);
        cpuLabel = new CpuLabel(220, 20);
        cpuLabel.setVisible(false);

        JPanel stats = new JPanel();
        stats.setBackground(Constants.bg_color);
        stats.add(memory_label);
        stats.add(cpuLabel);

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
        p7.add(renderingAlgorithmsButton);
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

        main_panel.add(p10);
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

        JPanel singleProgressPanel = new JPanel();
        singleProgressPanel.setBackground(Constants.bg_color);
        singleProgressPanel.add(progress);

        RoundedPanel round_panel = LAFManager.createRoundedPanel();
        round_panel.setBackground(Constants.bg_color);
        round_panel.setPreferredSize(new Dimension(540, 530));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(main_panel, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 1;

        round_panel.add(singleProgressPanel, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 2;

        round_panel.add(overallProgressPanel, con);

        setLayout(new GridBagLayout());
        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;
        add(round_panel, con);

        common = new CommonFunctions(ptr);

        repaint();

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
            renderingAlgorithmsButton.setEnabled(opt);
            batchRenderButton.setEnabled(opt);
            sequenceRenderButton.setEnabled(opt);
            outputDirectoryButton.setEnabled(opt);
            if(s.polar_projection) {
                polarLargeRenderButton.setEnabled(opt);
            }
            splitImageRenderButton.setEnabled(opt);

            if(opt) {
                stopGlobalTimer();
            }
            statsButton.setEnabled(opt);
            taskStatsButton.setEnabled(opt);
        }

    }

    public void batchRender() {
        new BatchRenderDialog(ptr);
    }

    public void sequenceRender() {
        new SequenceRenderDialog(ptr, s, zss);
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
                s.readSettings(ptr, file.toString(), ptr, false, false);
                String fname = file.getName();
                lastLoadedFile = file.toString();

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
                setTitle(TITLE);
                previewFrame.setVisible(false);
                progress.setVisible(false);
                cpuLabel.setVisible(false);
                memory_label.setVisible(false);

                statsButton.setEnabled(false);
                taskStatsButton.setEnabled(false);
                zss = new ZoomSequenceSettings();
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

    private void cleanUp() {
        image = null;
        largePolarImage = null;
    }

    private void render() {

        try {

            if(timer == null) {
                timer = new Timer();
                timer.schedule(new RefreshMemoryTask(memory_label), MEMORY_DELAY, MEMORY_DELAY);
            }

            if(timer2 == null) {
                timer2 = new Timer();
                timer2.schedule(new RefreshCpuTask(cpuLabel), CPU_DELAY, CPU_DELAY);
            }

            TaskRender.setArraysMinimalRenderer(image_width, image_height, s.needsExtraData());

            progress.setMaximum(image_width * image_height + 1);

            progress.setValue(0);

            progress.setVisible(true);
            memory_label.setVisible(true);
            cpuLabel.setVisible(true);

            setOptions(false);

            if(image == null || image_width != image.getWidth() || image_height != image.getHeight()) {
                image = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_ARGB);

                double aspect_ratio = ((double) image_width) / image_height;
                if (aspect_ratio > 1) {
                    preview_image = new BufferedImage(PREVIEW_IMAGE_SIZE, (int)(PREVIEW_IMAGE_SIZE / aspect_ratio + 0.5), BufferedImage.TYPE_INT_ARGB);
                }
                else {
                    preview_image = new BufferedImage((int)(PREVIEW_IMAGE_SIZE * aspect_ratio + 0.5), PREVIEW_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
                }
            }

            MainWindow.ArraysFillColor(image, EMPTY_COLOR);

            createTasks();

            calculation_time = System.currentTimeMillis();

            startThreads();

        }
        catch (OutOfMemoryError e) {
            JOptionPane.showMessageDialog(ptr, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            exit(-1);
        }
        catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            exit(-1);
        }
        catch (Error e) {
            e.printStackTrace();
            exit(-1);
        }
        catch(Throwable e) {
            e.printStackTrace();
            exit(-1);
        }
    }

    private void createTasks() {

        if (thread_grouping == 0) {
            tasks = new TaskRender[n][n];
            TaskRender.resetTaskData(n * n, false, s.size);
        } else if (thread_grouping == 1 || thread_grouping == 2){
            tasks = new TaskRender[1][n];
            TaskRender.resetTaskData(n, false, s.size);
        }
        else if(thread_grouping == 3 || thread_grouping == 4 || thread_grouping == 5) {
            tasks = new TaskRender[m][n];
            TaskRender.resetTaskData(m * n, false, s.size);
        }

        try {
            for(int i = 0; i < tasks.length; i++) {
                for(int j = 0; j < tasks[i].length; j++) {

                    TaskSplitCoordinates tsc = TaskSplitCoordinates.get(j, i, thread_grouping, n, m, image_width, image_height);
                    if(s.fns.julia) {
                        if(TaskRender.GREEDY_ALGORITHM) {
                            if(TaskRender.GREEDY_ALGORITHM_SELECTION == BOUNDARY_TRACING) {
                                if (TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                    tasks[i][j] = new BoundaryTracingColorsAndIterationDataRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                } else {
                                    tasks[i][j] = new BoundaryTracingRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                }
                            }
                            else if(TaskRender.GREEDY_ALGORITHM_SELECTION == MARIANI_SILVER) {
                                if (TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM > 0) {
                                    if (TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new MarianiSilver3ColorsAndIterationDataRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    } else {
                                        tasks[i][j] = new MarianiSilver3Render(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    }
                                }
                                else {
                                    if (TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new MarianiSilverColorsAndIterationDataRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    } else {
                                        tasks[i][j] = new MarianiSilverRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    }
                                }
                            }
                            else if(TaskRender.GREEDY_ALGORITHM_SELECTION == SUCCESSIVE_REFINEMENT) {
                                if(TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM > 0) {
                                    if (TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new SuccessiveRefinementGuessing2ColorsAndIterationDataRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    } else {
                                        tasks[i][j] = new SuccessiveRefinementGuessing2Render(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    }
                                }
                                else {
                                    if (TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new SuccessiveRefinementGuessingColorsAndIterationDataRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    } else {
                                        tasks[i][j] = new SuccessiveRefinementGuessingRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    }
                                }
                            }
                            else if(TaskRender.GREEDY_ALGORITHM_SELECTION == PATTERNED_SUCCESSIVE_REFINEMENT) {
                                if(TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM > 0) {
                                    if (TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new PatternedSuccessiveRefinementGuessing2ColorsAndIterationDataRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    } else {
                                        tasks[i][j] = new PatternedSuccessiveRefinementGuessing2Render(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    }
                                }
                                else {
                                    if (TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new PatternedSuccessiveRefinementGuessingColorsAndIterationDataRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    } else {
                                        tasks[i][j] = new PatternedSuccessiveRefinementGuessingRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    }
                                }
                            }
                        }
                        else {
                            if (TaskRender.BRUTE_FORCE_ALG == 0) {
                                tasks[i][j] = new BruteForceRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                            }
                            else if (TaskRender.BRUTE_FORCE_ALG == 1) {
                                tasks[i][j] = new BruteForce2Render(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                            }
                            else if (TaskRender.BRUTE_FORCE_ALG == 2) {
                                tasks[i][j] = new PatternedBruteForceRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                            }
                            else {
                                tasks[i][j] = new BruteForceInterleavedRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                            }
                        }
                    }
                    else {
                        if(TaskRender.GREEDY_ALGORITHM) {
                            if(TaskRender.GREEDY_ALGORITHM_SELECTION == BOUNDARY_TRACING) {
                                if (TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                    tasks[i][j] = new BoundaryTracingColorsAndIterationDataRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                                } else {
                                    tasks[i][j] = new BoundaryTracingRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                                }
                            }
                            else if(TaskRender.GREEDY_ALGORITHM_SELECTION == MARIANI_SILVER) {
                                if (TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM > 0) {
                                    if (TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new MarianiSilver3ColorsAndIterationDataRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps);
                                    } else {
                                        tasks[i][j] = new MarianiSilver3Render(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps);
                                    }
                                }
                                else {
                                    if (TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new MarianiSilverColorsAndIterationDataRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps);
                                    } else {
                                        tasks[i][j] = new MarianiSilverRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps);
                                    }
                                }
                            }
                            else if(TaskRender.GREEDY_ALGORITHM_SELECTION == SUCCESSIVE_REFINEMENT) {
                                if(TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM > 0) {
                                    if (TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new SuccessiveRefinementGuessing2ColorsAndIterationDataRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps);
                                    } else {
                                        tasks[i][j] = new SuccessiveRefinementGuessing2Render(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps);
                                    }
                                }
                                else {
                                    if (TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new SuccessiveRefinementGuessingColorsAndIterationDataRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps);
                                    } else {
                                        tasks[i][j] = new SuccessiveRefinementGuessingRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps);
                                    }
                                }
                            }
                            else if(TaskRender.GREEDY_ALGORITHM_SELECTION == PATTERNED_SUCCESSIVE_REFINEMENT) {
                                if(TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM > 0) {
                                    if (TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new PatternedSuccessiveRefinementGuessing2ColorsAndIterationDataRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps);
                                    } else {
                                        tasks[i][j] = new PatternedSuccessiveRefinementGuessing2Render(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps);
                                    }
                                }
                                else {
                                    if (TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new PatternedSuccessiveRefinementGuessingColorsAndIterationDataRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps);
                                    } else {
                                        tasks[i][j] = new PatternedSuccessiveRefinementGuessingRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.polar_projection, s.circle_period, s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending, s.post_processing_order, s.pbs, s.gs.gradient_offset, s.contourFactor, s.gps, s.js, s.pps);
                                    }
                                }
                            }
                        }
                        else {
                            if (TaskRender.BRUTE_FORCE_ALG == 0) {
                                tasks[i][j] = new BruteForceRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                            }
                            else if (TaskRender.BRUTE_FORCE_ALG == 1) {
                                tasks[i][j] = new BruteForce2Render(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                            }
                            else if (TaskRender.BRUTE_FORCE_ALG == 2) {
                                tasks[i][j] = new PatternedBruteForceRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                            }
                            else {
                                tasks[i][j] = new BruteForceInterleavedRender(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                            }
                        }
                    }
                    tasks[i][j].setTaskId(i * tasks.length + j);
                    tasks[i][j].setUsesSquareChunks(thread_grouping == 0 || ((thread_grouping == 3 || thread_grouping == 4 || thread_grouping == 5) && m == n));
                }
            }

            if(tasks[0][0].hasPatternedLogic()) {
                PatternedBruteForceRender.initCoordinates(image_width, image_height, false, true, tasks[0][0].usesSuccessiveRefinement());
                if(tasks[0][0].usesSuccessiveRefinement()) {
                    if(TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM > 0) {
                        PatternedSuccessiveRefinementGuessing2Render.initCoordinates(false);
                    }
                    else {
                        PatternedSuccessiveRefinementGuessingRender.initCoordinates(false);
                    }
                }
            }
            else {
                PatternedBruteForceRender.clear();
            }

            if(tasks[0][0].usesSuccessiveRefinement()) {
                SuccessiveRefinementGuessingRender.examined = new boolean[image_width * image_height];
                SuccessiveRefinementGuessingRender.filled = new boolean[image_width * image_height];
                if(TaskRender.SKIPPED_PIXELS_ALG != 0) {
                    SuccessiveRefinementGuessingRender.skipped_colors = new int[image_width * image_height];
                }
            }
            else {
                SuccessiveRefinementGuessingRender.examined = null;
                SuccessiveRefinementGuessingRender.filled = null;
                SuccessiveRefinementGuessingRender.skipped_colors = null;
            }
        }
        catch(ParserException e) {
            JOptionPane.showMessageDialog(ptr, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            exit(-1);
        }
    }

    private void startThreads() {

        futures.clear();
        for(int i = 0; i < tasks.length; i++) {
            for(int j = 0; j < tasks[i].length; j++) {
                futures.add(TaskRender.thread_calculation_executor.submit(tasks[i][j]));
            }
        }

    }

    public int getNumberOfThreads() {

        if(thread_grouping == 0) {
            return n * n;
        }
        else if(thread_grouping == 1 || thread_grouping == 2) {
            return n;
        }
        else {
            return m * n;
        }

    }

    private String getImageFormatExtension() {
        switch (imageFormat) {
            case 0:
                return ".png";
            case 1:
                return ".jpg";
            case 2:
                return ".bmp";
            case 3:
                return ".ppm";
            case 4:
                return ".pgm";
            default:
                return ".png";
        }
    }

    private void writeLargePolarImageToDisk() {

        try {
            String name;
            String extension = getImageFormatExtension();
            String baseName = settingsName + " - polar -";

            Path path = Paths.get(outputDirectory);
            if (Files.exists(path) && Files.isDirectory(path)) {
                name = path.resolve(baseName + extension).toString();

                int counter = 1;
                while (Files.exists(Paths.get(name))) {
                    baseName = settingsName + " - polar - (" + counter + ")";
                    name = path.resolve( baseName + extension).toString();
                    counter++;
                }
            }
            else {
                name = baseName + extension;
            }

            File file = new File(name);
            writeStats(path, baseName, largePolarStats);
            saveImage(largePolarImage, imageFormat, jpegQuality, file, downscaleFactor, downscale_algorithm);
        }
        catch(IOException ex) {
        }
    }

    private long startTime;
    private Timer globalTimer;
    private void startGlobalTimer() {
        // Set up the JFrame
        setTitle(TITLE + "     -     " + "00:00:00");

        // Initialize the start time
        startTime = System.currentTimeMillis();

        if(globalTimer != null) {
            globalTimer.cancel();
        }

        // Create and start the timer
        globalTimer = new Timer();
        globalTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Calculate the elapsed time
                long elapsedMillis = System.currentTimeMillis() - startTime;
                int elapsedSeconds = (int) (elapsedMillis / 1000);

                // Calculate hours, minutes, and seconds
                int hours = elapsedSeconds / 3600;
                int minutes = (elapsedSeconds % 3600) / 60;
                int seconds = elapsedSeconds % 60;

                // Format the time as HH:MM:SS
                String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                setTitle(TITLE + "     -     " + timeString);

            }
        }, 1000, 1000);


    }

    private void stopGlobalTimer() {
        if(globalTimer != null) {
            globalTimer.cancel();
            globalTimer = null;
            //setTitle(TITLE);
        }
    }

    public void writeImageToDisk() {

        if(runsOnLargePolarImageMode) {
            largePolarStats += progress.getToolTipText() + "\n";
            return;
        }

        Path path = Paths.get(outputDirectory);
        String baseName = "";
        String extension = getImageFormatExtension();

        try {
            String name;
            if (runsOnSequenceMode) {
                if(!zss.file_name_pattern.isEmpty()) {
                    baseName = String.format(zss.file_name_pattern, sequenceIndex + sequenceIndexOffset);
                }
                else {
                    baseName = settingsName + " - zoom sequence - " + " (" + (sequenceIndex + sequenceIndexOffset) + ")";
                }
                if (Files.exists(path) && Files.isDirectory(path)) {
                    name = path.resolve(baseName + extension).toString();
                } else {
                    name = baseName + extension;
                }
            }
            else if(runsOnSplitImageMode) {
                baseName = settingsName + " (" + String.format("%03d", gridI) + ", " + String.format("%03d", gridJ) + ")";
                if (Files.exists(path) && Files.isDirectory(path)) {
                    name = path.resolve(baseName + extension).toString();
                }
                else {
                    name = baseName + extension;
                }
            }
            else {
                baseName = settingsName;
                if (Files.exists(path) && Files.isDirectory(path)) {
                    name = path.resolve(baseName + extension).toString();

                    int counter = 1;
                    while (Files.exists(Paths.get(name))) {
                        baseName = settingsName + " (" + counter + ")";
                        name = path.resolve( baseName + extension).toString();
                        counter++;
                    }
                }
                else {
                    name = baseName + extension;
                }

            }

            writeStats(path, baseName, progress.getToolTipText());
            writeImage(image, name, 1);
        }
        catch(IOException ex) {
        }

        if(!runsOnBatchingMode && !runsOnSequenceMode && !runsOnSplitImageMode) {
            cleanUp();
        }
    }

    private void writeImage(BufferedImage image, String name, double aspect_ratio) throws IOException {

        imageWriteTime = System.currentTimeMillis();
        if(aspect_ratio == 1) {
            File file = new File(name);
            saveImage(image, imageFormat, jpegQuality, file, downscaleFactor, downscale_algorithm);
        }
        else if(aspect_ratio > 1) {
            int width = image.getWidth();

            int height = (int)(width / aspect_ratio + 0.5);

            BufferedImage subImage = image.getSubimage(0, (image.getHeight() - height) / 2, width, height);

            File file = new File(name);
            saveImage(subImage, imageFormat, jpegQuality, file, downscaleFactor, downscale_algorithm);
        }
        else  {
            int height = image.getHeight();

            int width = (int)(height * aspect_ratio + 0.5);

            BufferedImage subImage = image.getSubimage((image.getWidth() - width) / 2, 0, width, height);

            File file = new File(name);
            saveImage(subImage, imageFormat, jpegQuality, file, downscaleFactor, downscale_algorithm);
        }
        imageWriteTime = System.currentTimeMillis() - imageWriteTime;

        updatePreview();
    }

    private void updatePreview() {

        if (image.getWidth() > preview_image.getWidth() || image.getHeight() > preview_image.getHeight()) {
            preview_image = MainWindow.downscaleImage(image, preview_image.getWidth(), preview_image.getHeight(), preview_image, downscale_algorithm);
        } else {
            Graphics g = preview_image.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, preview_image.getWidth(), preview_image.getHeight());
            g.drawImage(image, 0, 0, null);
        }

        if(previewPanel.getWidth() != preview_image.getWidth() || previewPanel.getHeight() != preview_image.getHeight()) {
            previewPanel.setPreferredSize(new Dimension(preview_image.getWidth(), preview_image.getHeight()));
            previewFrame.pack();
        }

        previewPanel.repaint();

        if(!previewFrame.isVisible()) {
            previewFrame.setVisible(true);
            previewFrame.setLocation(getLocation().x - previewFrame.getWidth(), getLocation().y);
        }

        if(runsOnSequenceMode) {
            previewFrame.setTitle(PREVIEW_TITLE + "  -  Size: " + MainWindow.normalizeValue(s.size.toString(), 4));
        }
        else {
            previewFrame.setTitle(PREVIEW_TITLE);
        }
    }

    public void setThreadsNumberPost(int grouping, int val, int val2) {
        if(grouping == 3 || grouping == 4) {
            ArrayList<Integer> factors = CommonFunctions.getAllFactors(val);

            int index = factors.size() / 2 - 1;

            m = factors.get(index);
            this.n = factors.get(index + 1);
        }
        else if (grouping == 5) {
            m = val2;
            n = val;
        }
        else {
            this.n = val;
        }
        this.thread_grouping = grouping;

        TaskRender.initThreadPoolExecutor(getNumberOfThreads());
    }

    public void setThreadsNumber() {

        new ThreadsDialog(ptr, n, m, thread_grouping);

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

    public void setSizeOfImagePost(int width, int height, int imageFormat, float jpegQuality, int downscaleFactor) {
        image_width = width;
        image_height = height;
        this.imageFormat = imageFormat;
        this.jpegQuality = jpegQuality;
        this.downscaleFactor = downscaleFactor;
    }

    private void setSizeOfImage() {
        new ImageSizeDialog(ptr, image_width, image_height, imageFormat, jpegQuality, downscaleFactor);
    }

    public void setRenderingAlgorithms() {

        new RenderingAlgorithmsDialog(ptr);

    }

    public void setPerturbationTheory() {
        new PerturbationTheoryDialog(ptr, s);
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

        String javaVersion = System.getProperty("java.version");

        JOptionPane.showMessageDialog(ptr, "<html><center><font size='5' face='arial' color='blue'><b><u>Fractal Zoomer Minimal Renderer</u></b></font><br><br><font size='4' face='arial'>Version: <b>" + versionStr + "</b><br><br>" +
                "Java Version: <b>" + javaVersion + "</b><br><br>" +
                "Author: <b>Christos Kalonakis</b><br><br>Contact: <a href=\"mailto:hrkalona@gmail.com\">hrkalona@gmail.com</a><br><br></center></font></html>", "About", JOptionPane.INFORMATION_MESSAGE, MainWindow.getIcon("mandelMinimalRenderer.png"));


    }

    private void displayHelp() {

        JEditorPane textArea = new JEditorPane();

        textArea.setEditable(false);
        textArea.setContentType("text/html");
        textArea.setPreferredSize(new Dimension(550, 380));

        JScrollPane scroll_pane_2 = new JScrollPane(textArea);

        String help = "<html><center><font size='5' face='arial' color='blue'><b><u>Help</u></b></font></center><br>"
                + "<font size='4' face='arial'>This tool lets you perform some additional renderings,<br>"
                + "which are not provided in the main application.<br><br>"
                + "In order to use this tool the right way you must set the JVM's heap size, through<br>"
                + "command line. For instance to execute it using the jar file, use<br>"
                + "<b>java -jar -Xmx4000m FZMinimalRenderer.jar</b> in order to request maximum 4Gb<br>"
                + "of Heap from the JVM.<br><br>"
                + "Please check some online java tutorials for more thorough heap size allocation!<br><br>"
                + "If you are using the *.exe version of the application please edit<br>"
                + "<b>FZMinimalRenderer.l4j.ini</b> and add <b>-Xmx4000m</b> or any other memory size<br>"
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
            writer = new PrintWriter("IEpreferences.ini");

            writer.println("#Fractal Zoomer Minimal Renderer " + VERSION + " preferences.");
            writer.println("#This file contains all the preferences of the user and it is updated,");
            writer.println("#every time the application terminates. Settings that wont have the");
            writer.println("#correct name/number of arguments/argument value, will be ignored");
            writer.println("#and the default values will be used instead.");

            writer.println();
            writer.println();

            writer.println("[General]");
            writer.println("save_settings_path \"" + MainWindow.SaveSettingsPath + "\"");
            writer.println("output_directory \"" + outputDirectory + "\"");
            writer.println("use_smoothing_for_processing_algs " + TaskRender.USE_SMOOTHING_FOR_PROCESSING_ALGS);
            writer.println("display_user_code_warning " + Settings.DISPLAY_USER_CODE_WARNING);
            writer.println("output_image_format " + imageFormat);
            writer.println("jpeg_quality " + jpegQuality);
            writer.println("downscale_algorithm " + downscale_algorithm);
            writer.println("downscale_factor " + downscaleFactor);

            writer.println();

            writer.println("[Optimizations]");
            writer.println("thread_dim " + ((thread_grouping == 3 || thread_grouping == 4) ? m * n : n));
            if(thread_grouping == 5) {
                writer.println("thread_dim2 " + m);
            }
            writer.println("thread_grouping " + thread_grouping);
            writer.println("greedy_drawing_algorithm " + TaskRender.GREEDY_ALGORITHM);
            writer.println("greedy_drawing_algorithm_id " + TaskRender.GREEDY_ALGORITHM_SELECTION);
            writer.println("greedy_drawing_algorithm_use_iter_data " + TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA);
            writer.println("skipped_pixels_coloring " + TaskRender.SKIPPED_PIXELS_ALG);
            writer.println("mariani_silver_use_dfs " + QueueBasedRender.RENDER_USING_DFS);
            writer.println("mariani_silver_work_stealing_enabled " + QueueBasedRender.WORK_STEALING_ENABLED);
            writer.println("mariani_silver_work_steal_algorithm " + QueueBasedRender.WORK_STEAL_ALGORITHM);
            writer.println("mariani_silver_wait_and_steal " + QueueBasedRender.WAIT_AND_STEAL);
            writer.println("mariani_silver_initial_work_stealing_enabled " + QueueBasedRender.INITIAL_WORK_STEALING_ENABLED);
            writer.println("mariani_silver_perimeter_accuracy " + QueueBasedRender.PERIMETER_ACCURACY);
            writer.println("guess_blocks_selection " + TaskRender.GUESS_BLOCKS_SELECTION);
            writer.println("greedy_successive_refinement_squares_and_rectangles_algorithm " + TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM);
            writer.println("two_pass_successive_refinement " + TaskRender.TWO_PASS_SUCCESSIVE_REFINEMENT);
            writer.println("two_pass_check_center " + TaskRender.TWO_PASS_CHECK_CENTER);
            writer.println("square_rect_chunk_aggregation " + TaskRender.SQUARE_RECT_CHUNK_AGGERAGATION);
            int color = TaskRender.SKIPPED_PIXELS_COLOR;
            writer.println("skipped_pixels_user_color " + ((color >> 16) & 0xff) + " " + ((color >> 8) & 0xff) + " " + (color & 0xff));
            writer.println("perturbation_theory " + TaskRender.PERTURBATION_THEORY);
            writer.println("precision " + MyApfloat.precision);
            writer.println("approximation_algorithm " + TaskRender.APPROXIMATION_ALGORITHM);
            writer.println("series_approximation_terms " + TaskRender.SERIES_APPROXIMATION_TERMS);
            writer.println("series_approximation_oom_diff " + TaskRender.SERIES_APPROXIMATION_OOM_DIFFERENCE);
            writer.println("series_approximation_max_skip " + TaskRender.SERIES_APPROXIMATION_MAX_SKIP_ITER);
            writer.println("use_full_floatexp_for_deep_zoom " + TaskRender.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM);
            writer.println("use_full_floatexp_for_all_zoom " + TaskRender.USE_FULL_FLOATEXP_FOR_ALL_ZOOM);
            writer.println("automatic_bignum_precision " + TaskRender.BIGNUM_AUTOMATIC_PRECISION);
            writer.println("bignum_precision_bits " + TaskRender.BIGNUM_PRECISION);
            writer.println("bignum_precision_factor " + TaskRender.BIGNUM_PRECISION_FACTOR);
            writer.println("use_threads_for_sa " + TaskRender.USE_THREADS_FOR_SA);
            writer.println("bla_precision_bits " + TaskRender.BLA_BITS);
            writer.println("use_threads_for_bla " + TaskRender.USE_THREADS_FOR_BLA);
            writer.println("bla_starting_level " + TaskRender.BLA_STARTING_LEVEL);
            writer.println("bla3_valid_radius_scale " + MipLAStep.ValidRadiusScale);
            writer.println("bla3_starting_level " + TaskRender.BLA3_STARTING_LEVEL);
            writer.println("detect_period " + TaskRender.DETECT_PERIOD);
            writer.println("brute_force_alg " + TaskRender.BRUTE_FORCE_ALG);
            writer.println("one_chunk_per_row " + TaskRender.CHUNK_SIZE_PER_ROW);
            writer.println("exponent_diff_ignored " + MantExp.EXPONENT_DIFF_IGNORED);
            writer.println("bignum_implementation " + NumericLibrary.BIGNUM_IMPLEMENTATION);
            writer.println("automatic_precision " + MyApfloat.setAutomaticPrecision);
            writer.println("nanomb1_n " + TaskRender.NANOMB1_N);
            writer.println("nanomb1_m " + TaskRender.NANOMB1_M);
            writer.println("perturbation_pixel_algorithm " + TaskRender.PERTUBATION_PIXEL_ALGORITHM);
            writer.println("gather_perturbation_statistics " + TaskRender.GATHER_PERTURBATION_STATISTICS);
            writer.println("check_bailout_during_deep_not_full_floatexp_mode " + TaskRender.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE);
            writer.println("gather_tiny_ref_indexes " + TaskRender.GATHER_TINY_REF_INDEXES);
            writer.println("bignum_sqrt_max_iterations " + BigNum.SQRT_MAX_ITERATIONS);
            writer.println("built_in_bignum_implementation " + TaskRender.BUILT_IT_BIGNUM_IMPLEMENTATION);
            writer.println("stop_reference_calculation_after_detected_period " + TaskRender.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD);
            writer.println("use_custom_floatexp_requirement " + TaskRender.USE_CUSTOM_FLOATEXP_REQUIREMENT);
            writer.println("load_mpfr " + TaskRender.LOAD_MPFR);
            writer.println("load_mpir " + TaskRender.LOAD_MPIR);
            writer.println("#available architectures: " + String.join(", ", TaskRender.mpirWinArchitecture));
            writer.println("mpir_win_architecture " + TaskRender.MPIR_WINDOWS_ARCHITECTURE);
            writer.println("#available architectures: " + String.join(", ", TaskRender.mpfrWinArchitecture));
            writer.println("mpfr_win_architecture " + TaskRender.MPFR_WINDOWS_ARCHITECTURE);
            writer.println("period_detection_algorithm " + TaskRender.PERIOD_DETECTION_ALGORITHM);
            writer.println("pattern_compare_alg " + TaskRender.PATTERN_COMPARE_ALG);
            writer.println("pattern_n " + TaskRender.PATTERN_N);
            writer.println("pattern_revert_alg " + TaskRender.PATTERN_REVERT_ALG);
            writer.println("pattern_repeat_alg " + TaskRender.PATTERN_REPEAT_ALG);
            writer.println("pattern_centered " + TaskRender.PATTERN_CENTER);
            writer.println("pattern_repeat_spacing " + TaskRender.PATTERN_REPEAT_SPACING);
            writer.println("load_drawing_algorithm_from_saves " + TaskRender.LOAD_RENDERING_ALGORITHM_FROM_SAVES);
            writer.println("bla2_detection_method " + LAInfo.DETECTION_METHOD);
            writer.println("bla2_stage0_dip_detection_threshold " + LAInfo.Stage0DipDetectionThreshold);
            writer.println("bla2_stage0_dip_detection_threshold2 " + LAInfo.Stage0DipDetectionThreshold2);
            writer.println("bla2_stage0_dip_detection_threshold3 " + MagnitudeDetection.Stage0DipDetectionThreshold);
            writer.println("bla2_dip_detection_threshold " + LAInfo.DipDetectionThreshold);
            writer.println("bla2_dip_detection_threshold2 " + LAInfo.DipDetectionThreshold2);
            writer.println("bla2_dip_detection_threshold3 " + MagnitudeDetection.DipDetectionThreshold);
            writer.println("bla2_la_threshold_scale " + LAInfo.LAThresholdScale);
            writer.println("bla2_la_threshold_c_scale " + LAInfo.LAThresholdCScale);
            writer.println("bla2_double_threshold_limit " + LAReference.doubleThresholdLimit.toDouble());
            writer.println("bla2_convert_to_double_when_possible " + LAReference.CONVERT_TO_DOUBLE_WHEN_POSSIBLE);
            writer.println("bla2_root_divisor " + LAReference.rootDivisor);
            writer.println("bla2_create_at " + LAReference.CREATE_AT);
            writer.println("bla2_fake_period_limit " + LAReference.fakePeriodLimit);
            writer.println("use_threads_for_bla2 " + TaskRender.USE_THREADS_FOR_BLA2);
            writer.println("use_ref_index_on_bla2 " + TaskRender.USE_RI_ON_BLA2);
            writer.println("disable_ref_index_on_bla2 " + TaskRender.DISABLE_RI_ON_BLA2);
            writer.println("use_threads_for_bla3 " + TaskRender.USE_THREADS_FOR_BLA3);
            writer.println("always_check_for_precision_decrease " + MyApfloat.alwaysCheckForDecrease);
            writer.println("use_threads_in_bignum_libs " + TaskRender.USE_THREADS_IN_BIGNUM_LIBS);
            writer.println("calculate_period_every_time_from_start " + TaskRender.CALCULATE_PERIOD_EVERY_TIME_FROM_START);
            writer.println("mantexpcomplex_format " + TaskRender.MANTEXPCOMPLEX_FORMAT);
            writer.println("use_fast_delta_location " + TaskRender.USE_FAST_DELTA_LOCATION);
            writer.println("always_save_extra_pixel_data_on_aa_with_pp " + TaskRender.ALWAYS_SAVE_EXTRA_PIXEL_DATA_ON_AA_WITH_PP);
            writer.println("reference_compression " + TaskRender.COMPRESS_REFERENCE_IF_POSSIBLE);
            writer.println("reference_compression_error " + ReferenceCompressor.CompressionError);
            writer.println("check_bailout_during_mip_bla_step " + TaskRender.CHECK_BAILOUT_DURING_MIP_BLA_STEP);
            writer.println("split_into_rectangle_areas " + TaskRender.SPLIT_INTO_RECTANGLE_AREAS);
            writer.println("rectangle_area_split_algorithm " + TaskRender.RECTANGLE_AREA_SPLIT_ALGORITHM);
            writer.println("area_dimension_x " + TaskRender.AREA_DIMENSION_X);
            writer.println("area_dimension_y " + TaskRender.AREA_DIMENSION_Y);

            writer.println();

            writer.println("[Window]");
            writer.println("image_width " + image_width);
            writer.println("image_height " + image_height);
            writer.println("window_location " + (int) getLocation().getX() + " " + (int) getLocation().getY());

            writer.println();
            writer.println("[Core]");
            writer.println("derivative_step " + Derivative.DZ.getRe());
            writer.println("aa_jitter_size " + Location.AA_JITTER_SIZE);
            writer.println("aa_number_of_jitter_kernels " + Location.NUMBER_OF_AA_JITTER_KERNELS);
            writer.println("aa_fixed_jitter_size " + Location.FIXED_JITTER_SIZE);
            writer.println("whitepoint " + ColorSpaceConverter.whitePointId);
            writer.println("include_aa_data_on_rank_order " + TaskRender.INCLUDE_AA_DATA_ON_RANK_ORDER);
            writer.println("seed " + TaskRender.SEED);
            writer.println("user_formula_derivative_method " + FunctionDerivative2ArgumentsExpressionNode.USER_FORMULA_DERIVATIVE_METHOD);

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

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.LOAD_MPIR = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.LOAD_MPIR = true;
                        }
                    }
                    else if(token.equals("load_mpfr") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.LOAD_MPFR = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.LOAD_MPFR = true;
                        }
                    }
                    else if(token.equals("display_user_code_warning") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            Settings.DISPLAY_USER_CODE_WARNING = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            Settings.DISPLAY_USER_CODE_WARNING = true;
                        }
                    }
                    else if(token.equals("load_drawing_algorithm_from_saves") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.LOAD_RENDERING_ALGORITHM_FROM_SAVES = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.LOAD_RENDERING_ALGORITHM_FROM_SAVES = true;
                        }
                    }
                    else if(token.equals("use_threads_in_bignum_libs") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.USE_THREADS_IN_BIGNUM_LIBS = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.USE_THREADS_IN_BIGNUM_LIBS = true;
                        }
                    }
                    else if(token.equals("built_in_bignum_implementation") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        try {
                            int val = Integer.parseInt(token);
                            if(val >= 0 && val <= 4) {
                                TaskRender.BUILT_IT_BIGNUM_IMPLEMENTATION = val;
                            }
                        }
                        catch (Exception ex) {

                        }
                    }
                    else if(token.equals("mpir_win_architecture") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(Arrays.asList(TaskRender.mpirWinArchitecture).contains(token)) {
                            TaskRender.MPIR_WINDOWS_ARCHITECTURE = token;
                        }
                    }
                    else if(token.equals("mpfr_win_architecture") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(Arrays.asList(TaskRender.mpfrWinArchitecture).contains(token)) {
                            TaskRender.MPFR_WINDOWS_ARCHITECTURE = token;
                        }
                    }
                    else if (token.equals("thread_grouping") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 5) {
                                thread_grouping = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("greedy_successive_refinement_squares_and_rectangles_algorithm") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 4) {
                                TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM = temp;
                                TaskRender.setSuccessiveRefinementChunks();
                            }
                        } catch (Exception ex) {
                        }
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

                    if (token.equals("bignum_implementation") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 8) {
                                NumericLibrary.BIGNUM_IMPLEMENTATION = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("downscale_algorithm") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 2) {
                                downscale_algorithm = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("downscale_factor") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0 && temp <= 25) {
                                downscaleFactor = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("mantexpcomplex_format") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 1) {
                                TaskRender.MANTEXPCOMPLEX_FORMAT = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("user_formula_derivative_method") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= Derivative.NUMERICAL_FORWARD && temp <= Derivative.NUMERICAL_BACKWARD) {
                                FunctionDerivative2ArgumentsExpressionNode.USER_FORMULA_DERIVATIVE_METHOD = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("seed") && tokenizer.countTokens() == 1) {

                        try {
                            long temp = Long.parseLong(tokenizer.nextToken());
                            TaskRender.SEED = temp;
                            TaskRender.reSeed();
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
                                TaskRender.PERTUBATION_PIXEL_ALGORITHM = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("output_image_format") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 4) {
                                imageFormat = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("jpeg_quality") && tokenizer.countTokens() == 1) {

                        try {
                            float temp = Float.parseFloat(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 1) {
                                jpegQuality = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("guess_blocks_selection") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 7) {
                                TaskRender.GUESS_BLOCKS_SELECTION = temp;
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
                    else if (token.equals("pattern_compare_alg") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 23) {
                                TaskRender.PATTERN_COMPARE_ALG = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("pattern_n") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp != 0) {
                                TaskRender.PATTERN_N = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("pattern_repeat_spacing") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                TaskRender.PATTERN_REPEAT_SPACING = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_root_divisor") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                LAReference.rootDivisor = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_fake_period_limit") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0) {
                                LAReference.fakePeriodLimit = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("period_detection_algorithm") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 2) {
                                TaskRender.PERIOD_DETECTION_ALGORITHM = temp;
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

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD = true;
                        }
                    }
                    else if(token.equals("mariani_silver_work_stealing_enabled") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            QueueBasedRender.WORK_STEALING_ENABLED = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            QueueBasedRender.WORK_STEALING_ENABLED = true;
                        }
                    }
                    else if(token.equals("mariani_silver_initial_work_stealing_enabled") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            QueueBasedRender.INITIAL_WORK_STEALING_ENABLED = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            QueueBasedRender.INITIAL_WORK_STEALING_ENABLED = true;
                        }
                    }
                    else if(token.equals("split_into_rectangle_areas") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.SPLIT_INTO_RECTANGLE_AREAS = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.SPLIT_INTO_RECTANGLE_AREAS = true;
                        }
                    }
                    else if (token.equals("rectangle_area_split_algorithm") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 2) {
                                TaskRender.RECTANGLE_AREA_SPLIT_ALGORITHM = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("mariani_silver_perimeter_accuracy") && tokenizer.countTokens() == 1) {
                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 1) {
                                QueueBasedRender.PERIMETER_ACCURACY = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("area_dimension_x") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0) {
                                TaskRender.AREA_DIMENSION_X = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("area_dimension_y") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0) {
                                TaskRender.AREA_DIMENSION_Y = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("mariani_silver_wait_and_steal") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            QueueBasedRender.WAIT_AND_STEAL = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            QueueBasedRender.WAIT_AND_STEAL = true;
                        }
                    }
                    else if (token.equals("mariani_silver_work_steal_algorithm") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 2) {
                                QueueBasedRender.WORK_STEAL_ALGORITHM = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("use_threads_for_bla3") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.USE_THREADS_FOR_BLA3 = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.USE_THREADS_FOR_BLA3 = true;
                        }
                    }
                    else if(token.equals("check_bailout_during_mip_bla_step") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.CHECK_BAILOUT_DURING_MIP_BLA_STEP = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.CHECK_BAILOUT_DURING_MIP_BLA_STEP = true;
                        }
                    }
                    else if(token.equals("aa_fixed_jitter_size") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            Location.FIXED_JITTER_SIZE = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            Location.FIXED_JITTER_SIZE = true;
                        }
                    }
                    else if(token.equals("bla2_create_at") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            LAReference.CREATE_AT = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            LAReference.CREATE_AT = true;
                        }
                    }
                    else if(token.equals("include_aa_data_on_rank_order") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.INCLUDE_AA_DATA_ON_RANK_ORDER = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.INCLUDE_AA_DATA_ON_RANK_ORDER = true;
                        }
                    }
                    else if(token.equals("use_threads_for_bla2") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.USE_THREADS_FOR_BLA2 = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.USE_THREADS_FOR_BLA2 = true;
                        }
                    }
                    else if(token.equals("reference_compression") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.COMPRESS_REFERENCE_IF_POSSIBLE = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.COMPRESS_REFERENCE_IF_POSSIBLE = true;
                        }
                    }
                    else if (token.equals("reference_compression_error") && tokenizer.countTokens() == 1) {
                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                ReferenceCompressor.CompressionError = temp;
                                ReferenceCompressor.setCompressionError();
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla3_valid_radius_scale") && tokenizer.countTokens() == 1) {
                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                MipLAStep.ValidRadiusScale = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("use_ref_index_on_bla2") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.USE_RI_ON_BLA2 = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.USE_RI_ON_BLA2 = true;
                        }
                    }
                    else if(token.equals("disable_ref_index_on_bla2") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.DISABLE_RI_ON_BLA2 = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.DISABLE_RI_ON_BLA2 = true;
                        }
                    }
                    else if(token.equals("two_pass_successive_refinement") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.TWO_PASS_SUCCESSIVE_REFINEMENT = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.TWO_PASS_SUCCESSIVE_REFINEMENT = true;
                        }
                    }
                    else if(token.equals("two_pass_check_center") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.TWO_PASS_CHECK_CENTER = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.TWO_PASS_CHECK_CENTER = true;
                        }
                    }
                    else if (token.equals("square_rect_chunk_aggregation") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 1) {
                                TaskRender.SQUARE_RECT_CHUNK_AGGERAGATION = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("one_chunk_per_row") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.CHUNK_SIZE_PER_ROW = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.CHUNK_SIZE_PER_ROW = true;
                        }
                    }
                    else if(token.equals("always_save_extra_pixel_data_on_aa_with_pp") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.ALWAYS_SAVE_EXTRA_PIXEL_DATA_ON_AA_WITH_PP = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.ALWAYS_SAVE_EXTRA_PIXEL_DATA_ON_AA_WITH_PP = true;
                        }
                    }
                    else if(token.equals("use_fast_delta_location") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.USE_FAST_DELTA_LOCATION = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.USE_FAST_DELTA_LOCATION = true;
                        }
                    }
                    else if(token.equals("pattern_revert_alg") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.PATTERN_REVERT_ALG = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.PATTERN_REVERT_ALG = true;
                        }
                    }
                    else if(token.equals("pattern_repeat_alg") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.PATTERN_REPEAT_ALG = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.PATTERN_REPEAT_ALG = true;
                        }
                    }
                    else if(token.equals("pattern_centered") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.PATTERN_CENTER = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.PATTERN_CENTER = true;
                        }
                    }
                    else if(token.equals("mariani_silver_use_dfs") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            QueueBasedRender.RENDER_USING_DFS = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            QueueBasedRender.RENDER_USING_DFS = true;
                        }
                    }
                    else if(token.equals("bla2_convert_to_double_when_possible") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            LAReference.CONVERT_TO_DOUBLE_WHEN_POSSIBLE = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            LAReference.CONVERT_TO_DOUBLE_WHEN_POSSIBLE = true;
                        }
                    }
                    else if(token.equals("calculate_period_every_time_from_start") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.CALCULATE_PERIOD_EVERY_TIME_FROM_START = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.CALCULATE_PERIOD_EVERY_TIME_FROM_START = true;
                        }
                    }
                    else if(token.equals("always_check_for_precision_decrease") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            MyApfloat.alwaysCheckForDecrease = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            MyApfloat.alwaysCheckForDecrease = true;
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

                            if (temp > 0 && temp <= 200) {
                                Location.NUMBER_OF_AA_JITTER_KERNELS = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("use_custom_floatexp_requirement") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.USE_CUSTOM_FLOATEXP_REQUIREMENT = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.USE_CUSTOM_FLOATEXP_REQUIREMENT = true;
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
                            else if (thread_grouping == 1 || thread_grouping == 2){
                                if (temp >= 1 && temp <= 10000) {
                                    n = temp;
                                }
                            }
                            else {
                                if (temp >= 1 && temp <= 10000) {
                                    ArrayList<Integer> factors = CommonFunctions.getAllFactors(temp);

                                    int index = factors.size() / 2 - 1;

                                    m = factors.get(index);
                                    this.n = factors.get(index + 1);
                                }
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("thread_dim2") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(thread_grouping == 5) {
                                if (temp >= 1 && temp <= 100) {
                                    m = temp;
                                }
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("greedy_drawing_algorithm") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.GREEDY_ALGORITHM = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.GREEDY_ALGORITHM = true;
                        }
                    }
                    else if(token.equals("use_smoothing_for_processing_algs") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.USE_SMOOTHING_FOR_PROCESSING_ALGS = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.USE_SMOOTHING_FOR_PROCESSING_ALGS = true;
                        }
                    }
                    else if(token.equals("greedy_drawing_algorithm_use_iter_data") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA = true;
                        }
                    }
                    else if(token.equals("check_bailout_during_deep_not_full_floatexp_mode") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE = true;
                        }
                    }
                    else if(token.equals("gather_perturbation_statistics") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.GATHER_PERTURBATION_STATISTICS = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.GATHER_PERTURBATION_STATISTICS = true;
                        }
                    }
                    else if(token.equals("gather_tiny_ref_indexes") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            TaskRender.GATHER_TINY_REF_INDEXES = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
                            TaskRender.GATHER_TINY_REF_INDEXES = true;
                        }
                    }
                    else if(token.equals("automatic_precision") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equalsIgnoreCase("false")) {
                            MyApfloat.setAutomaticPrecision = false;
                        }
                        else if(token.equalsIgnoreCase("true")) {
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

                            if(temp == BOUNDARY_TRACING || temp == MARIANI_SILVER || temp == SUCCESSIVE_REFINEMENT || temp == PATTERNED_SUCCESSIVE_REFINEMENT) {
                                TaskRender.GREEDY_ALGORITHM_SELECTION = temp;
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if (token.equals("brute_force_alg") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 3) {
                                TaskRender.BRUTE_FORCE_ALG = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_detection_method") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 2) {
                                LAInfo.DETECTION_METHOD = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_stage0_dip_detection_threshold") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0 && temp <= 10) {
                                LAInfo.Stage0DipDetectionThreshold = temp;
                                LAInfoDeep.Stage0DipDetectionThreshold = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_stage0_dip_detection_threshold3") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                MagnitudeDetection.Stage0DipDetectionThreshold = temp;
                                MagnitudeDetectionDeep.Stage0DipDetectionThreshold = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_dip_detection_threshold3") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                MagnitudeDetection.DipDetectionThreshold = temp;
                                MagnitudeDetectionDeep.DipDetectionThreshold = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_double_threshold_limit") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                LAReference.doubleThresholdLimit = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_stage0_dip_detection_threshold2") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0 && temp <= 10) {
                                LAInfo.Stage0DipDetectionThreshold2 = temp;
                                LAInfoDeep.Stage0DipDetectionThreshold2 = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_dip_detection_threshold") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0 && temp <= 10) {
                                LAInfo.DipDetectionThreshold = temp;
                                LAInfoDeep.DipDetectionThreshold = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_dip_detection_threshold2") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0 && temp <= 10) {
                                LAInfo.DipDetectionThreshold2 = temp;
                                LAInfoDeep.DipDetectionThreshold2 = new MantExp(temp);
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
                                TaskRender.NANOMB1_N = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("nanomb1_m") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 2 && temp <= 48) {
                                TaskRender.NANOMB1_M = temp;
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
                                TaskRender.SKIPPED_PIXELS_ALG = temp;
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
                                TaskRender.SKIPPED_PIXELS_COLOR = new Color(red, green, blue).getRGB();
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if (token.equals("perturbation_theory") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equalsIgnoreCase("false")) {
                            TaskRender.PERTURBATION_THEORY = false;
                        } else if (token.equalsIgnoreCase("true")) {
                            TaskRender.PERTURBATION_THEORY = true;
                        }
                    }
                    else if (token.equals("detect_period") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equalsIgnoreCase("false")) {
                            TaskRender.DETECT_PERIOD = false;
                        } else if (token.equalsIgnoreCase("true")) {
                            TaskRender.DETECT_PERIOD = true;
                        }
                    }
                    else if (token.equals("use_threads_for_sa") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equalsIgnoreCase("false")) {
                            TaskRender.USE_THREADS_FOR_SA = false;
                        } else if (token.equalsIgnoreCase("true")) {
                            TaskRender.USE_THREADS_FOR_SA = true;
                        }
                    }
                    else if (token.equals("use_threads_for_bla") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equalsIgnoreCase("false")) {
                            TaskRender.USE_THREADS_FOR_BLA = false;
                        } else if (token.equalsIgnoreCase("true")) {
                            TaskRender.USE_THREADS_FOR_BLA = true;
                        }
                    }
                    else if (token.equals("bla_starting_level") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0 && temp <= 32) {
                                TaskRender.BLA_STARTING_LEVEL = temp;
                            }
                        } catch (Exception ex) {
                        }

                    }
                    else if (token.equals("bla3_starting_level") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0 && temp <= 32) {
                                TaskRender.BLA3_STARTING_LEVEL = temp;
                            }
                        } catch (Exception ex) {
                        }

                    }
                    else if (token.equals("automatic_bignum_precision") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equalsIgnoreCase("false")) {
                            TaskRender.BIGNUM_AUTOMATIC_PRECISION = false;
                        } else if (token.equalsIgnoreCase("true")) {
                            TaskRender.BIGNUM_AUTOMATIC_PRECISION = true;
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
                                TaskRender.BLA_BITS = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bignum_precision_factor") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0) {
                                TaskRender.BIGNUM_PRECISION_FACTOR = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bignum_precision_bits") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0) {
                                TaskRender.BIGNUM_PRECISION = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("approximation_algorithm") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 5) {
                                TaskRender.APPROXIMATION_ALGORITHM = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("use_full_floatexp_for_deep_zoom") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equalsIgnoreCase("false")) {
                            TaskRender.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM = false;
                        } else if (token.equalsIgnoreCase("true")) {
                            TaskRender.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM = true;
                        }
                    }
                    else if (token.equals("use_full_floatexp_for_all_zoom") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equalsIgnoreCase("false")) {
                            TaskRender.USE_FULL_FLOATEXP_FOR_ALL_ZOOM = false;
                        } else if (token.equalsIgnoreCase("true")) {
                            TaskRender.USE_FULL_FLOATEXP_FOR_ALL_ZOOM = true;
                        }
                    }
                    else if (token.equals("series_approximation_oom_diff") && tokenizer.countTokens() == 1) {

                        try {
                            long temp = Long.parseLong(tokenizer.nextToken());
                            TaskRender.SERIES_APPROXIMATION_OOM_DIFFERENCE = temp;

                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("series_approximation_terms") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >=2  && temp <= 257) {
                                TaskRender.SERIES_APPROXIMATION_TERMS = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("series_approximation_max_skip") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >=0) {
                                TaskRender.SERIES_APPROXIMATION_MAX_SKIP_ITER = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("image_width") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp > 0 && temp < 46501) {
                                image_width = temp;
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("image_height") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp > 0 && temp < 46501) {
                                image_height = temp;
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
                }

            }

        }
        catch(FileNotFoundException ex) {

        }
        catch(IOException ex) {

        }

        TaskRender.initThreadPoolExecutor(getNumberOfThreads());

        MyApfloat.setPrecision(MyApfloat.precision, s);

        Location.setJitter(TaskRender.SEED);

        ColorSpaceConverter.init();

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            periodicity_checking = false;
        }
    }

    public void exit(int val) {

        savePreferences();
        NumericLibrary.deleteLibs();

        new Timer().schedule(new TimerTask() {
            public void run() {
                Runtime.getRuntime().halt(-1);
            }
        }, 8000);

        TaskRender.shutdownThreadPools();

        System.exit(val);

    }

    private void polarLargeRender() {
        new PolarLargeRenderDialog(ptr, number_of_polar_images, polar_orientation);
    }

    private void splitImageRender() {
        new SplitImageRenderDialog(ptr, split_image_grid_dimension);
    }

    private void enableStop() {
        stopRenderButton.setEnabled(true);
        stopped = false;
    }

    private void disableStop() {
        stopRenderButton.setEnabled(false);
        stopped = false;
    }

    public void startSplitImageRender(int split_image_grid_dimension) {

        this.split_image_grid_dimension = split_image_grid_dimension;

        TaskRender.single_thread_executor.submit(() -> {


            startGlobalTimer();
            int total = split_image_grid_dimension * split_image_grid_dimension;
            totalprogress.setMaximum(total);
            totalprogress.setValue(0);
            totalprogress.setString("Image: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());
            totalprogress.setVisible(true);
            overviewButton.setVisible(true);
            overviewButton.setEnabled(false);
            multipleRendersTrendButton.setEnabled(true);
            enableStop();
            setOptions(false);
            runsOnSplitImageMode = true;

            for(int k = 0; k < total; k++) {

                gridI = k / split_image_grid_dimension;
                gridJ = k % split_image_grid_dimension;

                Location.offset = new SplitImagePixelOffset(image_width * split_image_grid_dimension, image_height * split_image_grid_dimension, gridJ * image_width, gridI * image_height);

                render();


                try {
                    for(Future<?> future : futures) {
                        future.get();
                    }
                } catch (InterruptedException ex) {
                }
                catch (ExecutionException ex) {
                }


                totalprogress.setValue(k + 1);
                totalprogress.setString("Image: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());
                addTrendData(k, progress.getToolTipText());

                if(stopped) {
                    break;
                }
            }

            runsOnSplitImageMode = false;
            Location.offset = new PixelOffset();
            cleanUp();
            setOptions(true);
            totalprogress.setVisible(false);
            overviewButton.setEnabled(true);
            disableStop();
            multipleRendersTrendButton.setEnabled(false);
            closeTrendDialog();
            stopGlobalTimer();
        });
    }

    public void startLargePolarImageRender(int number_of_polar_images, int polar_orientation) {

        this.number_of_polar_images = number_of_polar_images;
        this.polar_orientation = polar_orientation;

        final int largeDimension = image_width * number_of_polar_images;

        if(polar_orientation == 0) {
            largePolarImage = new BufferedImage(largeDimension, image_height, BufferedImage.TYPE_INT_ARGB);
        }
        else {
            largePolarImage = new BufferedImage(image_height, largeDimension, BufferedImage.TYPE_INT_ARGB);
        }

        int largePolarImageWidth = largePolarImage.getWidth();

        Location.offset = new PixelOffset();

        TaskRender.single_thread_executor.submit(() -> {


            startGlobalTimer();
            largePolarStats = "";
            totalprogress.setMaximum(number_of_polar_images);
            totalprogress.setValue(0);
            totalprogress.setString("Image: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());
            totalprogress.setVisible(true);
            overviewButton.setVisible(true);
            overviewButton.setEnabled(false);
            multipleRendersTrendButton.setEnabled(true);
            enableStop();
            setOptions(false);
            runsOnLargePolarImageMode = true;

            Apfloat originalSize = s.size;

            for(int k = 0; k < number_of_polar_images; k++) {
                render();

                Apfloat start =  MyApfloat.fp.log(s.size);

                Apfloat ddmuly = MyApfloat.fp.divide(MyApfloat.fp.multiply(MyApfloat.fp.multiply(new MyApfloat(s.circle_period), MyApfloat.TWO), MyApfloat.getPi()), new MyApfloat(Math.min(image_width, image_height)));

                Apfloat ddmulx = MyApfloat.fp.multiply(ddmuly, new MyApfloat(s.height_ratio));

                Apfloat end = MyApfloat.fp.add(MyApfloat.fp.multiply(ddmulx, new MyApfloat(image_width)), start);

                s.size = MyApfloat.exp(end);

                try {
                    for(Future<?> future : futures) {
                        future.get();
                    }
                } catch (InterruptedException ex) {
                }
                catch (ExecutionException ex) {
                }

                int [] rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
                int [] large_rgsbs = ((DataBufferInt) largePolarImage.getRaster().getDataBuffer()).getData();

                int total = image_width * image_height;
                final int offsetJ = polar_orientation == 0 ? k * image_width : k * total;
                IntStream.range(0, total)
                        .parallel().forEach(p -> {
                                int i = p / image_width;
                                int j = p % image_width;
                                if (polar_orientation == 0) {
                                    large_rgsbs[i * largePolarImageWidth + j + offsetJ] = rgbs[p];
                                } else {
                                    large_rgsbs[j * largePolarImageWidth + i + offsetJ] = rgbs[p];
                                }
                        });


                totalprogress.setValue(k + 1);
                totalprogress.setString("Image: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());
                updatePreview();
                addTrendData(k, progress.getToolTipText());

                if(stopped) {
                    break;
                }
            }

            writeLargePolarImageToDisk();
            runsOnLargePolarImageMode = false;

            s.size = originalSize;

            cleanUp();
            setOptions(true);
            totalprogress.setVisible(false);
            overviewButton.setEnabled(true);
            disableStop();
            multipleRendersTrendButton.setEnabled(false);
            closeTrendDialog();
            stopGlobalTimer();
        });
    }

    public void startBatchRender(ArrayList<String> files, DefaultListModel<String> fileNames) {

        Location.offset = new PixelOffset();

        TaskRender.single_thread_executor.submit(() -> {


            startGlobalTimer();
            totalprogress.setMaximum(files.size());
            totalprogress.setValue(0);
            totalprogress.setString("Files: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());
            totalprogress.setVisible(true);
            overviewButton.setVisible(true);
            overviewButton.setEnabled(false);
            enableStop();
            setOptions(false);
            runsOnBatchingMode = true;
            batchIndex = 1;

            for(int k = 0; k < files.size(); k++) {
                try {
                    s.readSettings(ptr, files.get(k), ptr, true, false);
                    String fname = fileNames.get(k);
                    lastLoadedFile = files.get(k);

                    if(fname.length() > 45) {
                        fname = fname.substring(0, 45) + "...";
                    }
                    settings_label.setText("Loaded: " + fname);
                    settings_label.setVisible(true);

                    settingsName = fileNames.get(k).substring(0, fileNames.get(k).lastIndexOf("."));

                    zss = new ZoomSequenceSettings();

                    render();
                }
                catch(IOException ex) {

                }
                catch(ClassNotFoundException ex) {

                }
                catch(Exception ex) {

                }

                try {
                    for(Future<?> future : futures) {
                        future.get();
                    }
                } catch (InterruptedException ex) {
                }
                catch (ExecutionException ex) {
                }
                batchIndex++;
                totalprogress.setValue(batchIndex - 1);
                totalprogress.setString("Files: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());

                if(stopped) {
                    break;
                }
            }

            runsOnBatchingMode = false;
            cleanUp();
            setOptions(true);
            totalprogress.setVisible(false);
            overviewButton.setEnabled(true);
            disableStop();
            stopGlobalTimer();
        });


    }

    public void startSequenceRender() {

        Location.offset = new PixelOffset();

        numberOfSequenceSteps = 0;
        sequenceIndexOffset = zss.sequenceIndexOffset;

        Apfloat original_size = s.size;

        Apfloat temp = zss.zooming_mode == 0 ? zss.startSize : zss.endSize;
        while ((zss.zooming_mode == 0 && temp.compareTo(zss.endSize) > 0) || (zss.zooming_mode == 1 && temp.compareTo(zss.startSize) < 0)) {
            if(zss.zooming_mode == 0) {
                temp = MyApfloat.fp.divide(temp, new MyApfloat(zss.zoom_factor));
            }
            else {
                temp = MyApfloat.fp.multiply(temp, new MyApfloat(zss.zoom_factor));
            }
            numberOfSequenceSteps++;
        }

        numberOfSequenceSteps++;

        numberOfSequenceSteps *= zss.zoom_every_n_frame;

        Path path = Paths.get(outputDirectory);

        String name;
        if (Files.exists(path) && Files.isDirectory(path)) {
            name = path.resolve(settingsName + " - zoom sequence.json").toString();
        }
        else {
            name = settingsName + " - zoom sequence.json";
        }

        File file = new File(name);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, zss);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        int outPaletteLength = CommonFunctions.getOutPaletteLength(s.ds.domain_coloring, s.ds.domain_coloring_mode);
        int inPaletteLength = CommonFunctions.getInPaletteLength(s.ds.domain_coloring);

        TaskRender.single_thread_executor.submit(() -> {

            startGlobalTimer();

            long total = numberOfSequenceSteps;

            if(zss.stop_after_n_steps > 0) {
                total = Math.min(zss.stop_after_n_steps, numberOfSequenceSteps);
            }

            Apfloat setOverrideMaxIterationsSizeLimit2 = MyApfloat.fp.divide(zss.overrideMaxIterationsSizeLimit, new MyApfloat(50));
            if(setOverrideMaxIterationsSizeLimit2.compareTo(s.size) < 0) {
                setOverrideMaxIterationsSizeLimit2 = s.size;
            }

            int max_steps = 1000000;
            long divisor = total > max_steps ? total / 100 : 1;
            totalprogress.setMaximum((int)(total > max_steps ? 100 : total));

            totalprogress.setValue(0);

            if(divisor == 1) {
                totalprogress.setString("Zoom Sequence: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());
            }
            else {
                totalprogress.setString("Zoom Sequence: " + String.format("%3d", (int) ((double) (totalprogress.getValue()) / totalprogress.getMaximum() * 100)) + "%");
            }

            totalprogress.setVisible(true);
            overviewButton.setVisible(true);
            overviewButton.setEnabled(false);
            multipleRendersTrendButton.setEnabled(true);
            enableStop();
            setOptions(false);
            runsOnSequenceMode = true;
            sequenceIndex = zss.flipSequenceIndexing ? numberOfSequenceSteps : 1;

            Apfloat[] originalRotCenter = new Apfloat[2];
            originalRotCenter[0] = s.fns.rotation_center[0];
            originalRotCenter[1] = s.fns.rotation_center[1];
            double originalRotation = s.fns.rotation;
            Apfloat[] originalRotVals = new Apfloat[2];
            originalRotVals[0] = s.fns.rotation_vals[0];
            originalRotVals[1] = s.fns.rotation_vals[1];
            int originalColorCyclingLocation = s.ps.color_cycling_location;
            int originalColorCyclingLocation2 = s.ps2.color_cycling_location;

            BumpMapSettings bumpBack = new BumpMapSettings(s.pps.bms);
            LightSettings lightBack = new LightSettings(s.pps.ls);
            SlopeSettings slopeBack = new SlopeSettings(s.pps.ss);

            int originalGradientColorCyclingLocation = s.gs.gradient_offset;

            int originalMaxIterations = s.max_iterations;

            LinearInterpolation lerp = new LinearInterpolation();

            if(zss.rotation_adjusting_value != 0) {
                s.fns.rotation_center[0] = s.xCenter;
                s.fns.rotation_center[1] = s.yCenter;
            }

            Fractal.clearStatistics();

            s.size = zss.zooming_mode == 0 ? zss.startSize : zss.endSize;

            long renderCount = 0;

            for(int k = 1; k <= numberOfSequenceSteps; k++) {

                if (s.size.compareTo(zss.overrideMaxIterationsSizeLimit) > 0 && zss.override_max_iterations > 0 && originalMaxIterations > zss.override_max_iterations) {
                    s.max_iterations = zss.override_max_iterations;
                } else if (s.size.compareTo(setOverrideMaxIterationsSizeLimit2) >= 0 && zss.override_max_iterations > 0 && originalMaxIterations > zss.override_max_iterations) {
                    double coef = MyApfloat.fp.divide(MyApfloat.fp.subtract(zss.overrideMaxIterationsSizeLimit, s.size),
                    MyApfloat.fp.subtract(zss.overrideMaxIterationsSizeLimit, setOverrideMaxIterationsSizeLimit2)).doubleValue();

                    s.max_iterations = lerp.interpolate(zss.override_max_iterations, originalMaxIterations, coef);
                }
                else {
                    s.max_iterations = originalMaxIterations;
                }

                if(zss.startAtSequenceIndex == 0 || (!zss.flipSequenceIndexing && sequenceIndex >= zss.startAtSequenceIndex) || (zss.flipSequenceIndexing && sequenceIndex <= zss.startAtSequenceIndex) ) {
                    render();

                    try {
                        for(Future<?> future : futures) {
                            future.get();
                        }
                    } catch (InterruptedException ex) {
                    }
                    catch (ExecutionException ex) {
                    }

                    writeInfo(path);

                    addTrendData(renderCount, progress.getToolTipText());

                    renderCount++;
                }

                if(zss.flipSequenceIndexing) {
                    sequenceIndex--;
                }
                else {
                    sequenceIndex++;
                }

                if(zss.stop_after_n_steps > 0) {
                    totalprogress.setValue((int)(renderCount / divisor));
                }
                else {
                    totalprogress.setValue((int)(k / divisor));
                }

                if(divisor == 1) {
                    totalprogress.setString("Zoom Sequence: " + totalprogress.getValue() + "/" + totalprogress.getMaximum());
                }
                else {
                    totalprogress.setString("Zoom Sequence: " + String.format("%3d", (int) ((double) (totalprogress.getValue()) / totalprogress.getMaximum() * 100)) + "%");
                }

                if(k >= zss.zoom_every_n_frame && k % zss.zoom_every_n_frame == 0) {
                    if (zss.zooming_mode == 0) {
                        s.size = MyApfloat.fp.divide(s.size, new MyApfloat(zss.zoom_factor));
                    } else {
                        s.size = MyApfloat.fp.multiply(s.size, new MyApfloat(zss.zoom_factor));
                    }
                }

                if(zss.rotation_adjusting_value != 0) {
                    CommonFunctions.adjustRotationOffset(s,zss.rotation_adjusting_value);
                }

                if(zss.color_cycling_adjusting_value != 0) {
                    s.ps.color_cycling_location = CommonFunctions.adjustPaletteOffset(s.ps.color_cycling_location, zss.color_cycling_adjusting_value, outPaletteLength);
                    s.ps2.color_cycling_location = CommonFunctions.adjustPaletteOffset(s.ps2.color_cycling_location, zss.color_cycling_adjusting_value, inPaletteLength);
                }

                if(zss.gradient_color_cycling_adjusting_value != 0) {
                    s.gs.gradient_offset = CommonFunctions.adjustPaletteOffset(s.gs.gradient_offset, zss.gradient_color_cycling_adjusting_value, s.gs.gradient_length);
                }

                if (s.pps.bms.bump_map && zss.bump_direction_adjusting_value != 0) {
                    CommonFunctions.adjustBumpOffset(s.pps.bms, zss.bump_direction_adjusting_value);
                }

                if (s.pps.ls.lighting && zss.light_direction_adjusting_value != 0) {
                    CommonFunctions.adjustLightOffset(s.pps.ls, zss.light_direction_adjusting_value);
                }

                if (s.pps.ss.slopes && zss.slopes_direction_adjusting_value != 0) {
                    CommonFunctions.adjustSlopeOffset(s.pps.ss, zss.slopes_direction_adjusting_value);
                }

                if(zss.stop_after_n_steps > 0 && renderCount >= zss.stop_after_n_steps) {
                    break;
                }
                if(stopped) {
                    break;
                }
            }

            runsOnSequenceMode = false;
            cleanUp();

            boolean reLoaded = false;
            if (lastLoadedFile != null) {
                try {
                    s.readSettings(ptr, lastLoadedFile, ptr, true, true);
                    reLoaded = true;
                } catch (Exception ex) {

                }
            }

            if(!reLoaded) {
                //Rollback
                s.size = original_size;
                s.fns.rotation_center[0] = originalRotCenter[0];
                s.fns.rotation_center[1] = originalRotCenter[1];
                s.fns.rotation = originalRotation;
                s.fns.rotation_vals[0] = originalRotVals[0];
                s.fns.rotation_vals[1] = originalRotVals[1];
                s.ps.color_cycling_location = originalColorCyclingLocation;
                s.ps2.color_cycling_location = originalColorCyclingLocation2;

                s.pps.bms = bumpBack;
                s.pps.ls = lightBack;
                s.pps.ss = slopeBack;

                s.max_iterations = originalMaxIterations;
                s.gs.gradient_offset = originalGradientColorCyclingLocation;
            }

            setOptions(true);
            totalprogress.setVisible(false);
            overviewButton.setEnabled(true);
            multipleRendersTrendButton.setEnabled(false);
            closeTrendDialog();
            disableStop();
            stopGlobalTimer();
        });


    }

    private void closeTrendDialog() {
        RenderingTrendDialog.closeInstance();
    }

    private void writeInfo(Path path) {
        String infoName = "";
        String zoomSettingsName = "";
        if(!zss.file_name_pattern.isEmpty()) {
            if (Files.exists(path) && Files.isDirectory(path)) {
                zoomSettingsName = path.resolve(String.format(zss.file_name_pattern, sequenceIndex + sequenceIndexOffset) + ".fzs").toString();
                infoName = path.resolve(String.format(zss.file_name_pattern, sequenceIndex + sequenceIndexOffset) + ".info.json").toString();
            } else {
                zoomSettingsName = String.format(zss.file_name_pattern, sequenceIndex + sequenceIndexOffset) + ".fzs";
                infoName = String.format(zss.file_name_pattern, sequenceIndex + sequenceIndexOffset) + ".info.json";
            }
        }
        else {
            if (Files.exists(path) && Files.isDirectory(path)) {
                zoomSettingsName = path.resolve(settingsName + " - zoom sequence - " + " (" + (sequenceIndex + sequenceIndexOffset) + ")" + ".fzs").toString();
                infoName = path.resolve(settingsName + " - zoom sequence - " + " (" + (sequenceIndex + sequenceIndexOffset) + ")" + ".info.json").toString();
            } else {
                zoomSettingsName = settingsName + " - zoom sequence - " + " (" + (sequenceIndex + sequenceIndexOffset) + ")" + ".fzs";
                infoName = settingsName + " - zoom sequence - " + " (" + (sequenceIndex + sequenceIndexOffset) + ")" + ".info.json";
            }
        }

        BasicLocationSettings bs = MainWindow.createBasicSettings(s);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String text = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bs);
            Files.write(Paths.get(infoName), text.getBytes());
        }
        catch (Exception ex) {
        }


        if(zss.saveSettingsOnEachStep) {
            s.save(zoomSettingsName, null);
        }
    }

    private void writeStats(Path path, String fileName, String stats) {
        String infoName = "";
        if(!zss.file_name_pattern.isEmpty()) {
            if (Files.exists(path) && Files.isDirectory(path)) {
                infoName = path.resolve(fileName + ".stats").toString();
            } else {
                infoName = fileName + ".stats";
            }
        }
        else {
            if (Files.exists(path) && Files.isDirectory(path)) {
                infoName = path.resolve(fileName + ".stats").toString();
            } else {
                infoName = fileName + ".stats";
            }
        }

        stats = stats.replace("<br>", "\n");
        stats = stats.replace("<html>", "");
        stats = stats.replace("</html>", "");
        stats = stats.replace("<li>", "");
        stats = stats.replace("</li>", "");
        stats = stats.replace("<ul>", "");
        stats = stats.replace("</ul>", "");
        stats = stats.replace("<b>", "");
        stats = stats.replace("</b>", "");

        try {
            Files.write(Paths.get(infoName), stats.getBytes());
        }
        catch (Exception ex) {}
    }

     private void setOutputDirectory() {

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

    private void Metrics() {
        MetricsDialog.getInstance(ptr).setVisible(true);
    }

    private void Stats() {

        try {

            common.stats(ptr, s);

        } catch (Exception ex) {
        }

    }

    private void TaskStats() {

        try {
            common.taskStats(ptr, tasks);
        } catch (Exception ex) {
        }

    }

    private void TrendStats() {
        RenderingTrendDialog.getInstance(ptr).setVisible(true);
    }

    private void addTrendData(long render, String report) {
        RenderingTrendDialog d = RenderingTrendDialog.getInstance(ptr);
        if(d != null && d.isVisible()) {
            d.addSampleData(render, report, imageWriteTime);
        }
    }

    public static void main(String[] args) throws Exception {

       // SwingUtilities.invokeLater(() -> {
            if(args.length > 0 && args[0].equals("l4jini")) {
                CommonFunctions.exportL4jIni("FZMinimalRenderer", Constants.MRL4j);
            }

            MainWindow.setLaf();

            MinimalRendererWindow mw = new MinimalRendererWindow();
            mw.setVisible(true);

            boolean actionOk = mw.getCommonFunctions().copyLib();
            mw.getCommonFunctions().exportCodeFilesNoUi();

            if(actionOk) {
                mw.getCommonFunctions().compileCode(false);
            }

            mw.getCommonFunctions().checkForUpdate(false);

        //});
    }
}
