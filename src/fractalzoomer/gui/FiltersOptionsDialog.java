
package fractalzoomer.gui;

import fractalzoomer.core.TaskRender;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.FiltersSettings;
import fractalzoomer.utils.QuadTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;

/**
 *
 * @author hrkalona
 */
public class FiltersOptionsDialog extends JDialog {
   private static final long serialVersionUID = 8403887745045484988L;
   private MainWindow ptra2;
   private Object[] components_filters;
   private int[] filters_options_vals;
   private int[][] filters_options_extra_vals;
   private Color[] filters_colors;
   private Color[][] filters_extra_colors;
   private FiltersOptionsDialog this_frame;
   private FilterOrderSelectionPanel order_panel;
   private boolean[] mActiveFilters;
   private static int tab_index;
   
   
   static {
       tab_index = 0;
   }
   
    public FiltersOptionsDialog(MainWindow ptra, int[] filters_options_vals2, int[][] filters_options_extra_vals2, Color[] filters_colors2, Color[][] filters_extra_colors2, JCheckBoxMenuItem[] filter_names, int[] filter_order, boolean[] active_filters, FiltersSettings fs) {
        
        super();

        ptra2 = ptra;
        filters_options_vals = filters_options_vals2;
        filters_options_extra_vals = filters_options_extra_vals2;
        filters_colors = filters_colors2;
        filters_extra_colors = filters_extra_colors2;
        
        mActiveFilters = new boolean[active_filters.length];
        
        for(int i = 0; i < mActiveFilters.length; i++) {
            mActiveFilters[i] = active_filters[i];
        }
        
        this_frame = this;
        
        components_filters = new Object[filters_options_vals.length];
        
        setModal(true);
        int filters_options_window_width = 1240;
        int filters_options_window_height = 690;
        setTitle("Filters Options");
        setIconImage(MainWindow.getIcon("filter_options.png").getImage());
        setSize(filters_options_window_width, filters_options_window_height);
        setLocation((int)(ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (filters_options_window_width / 2), (int)(ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (filters_options_window_height / 2));

        final JTabbedPane tabbedPane = new JTabbedPane();
         
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                tab_index = tabbedPane.getSelectedIndex();

                dispose();

            }
        });

        JPanel panel = new JPanel();
        panel.setBackground(MainWindow.bg_color);
        
        JPanel panel_filters = new ActiveFiltersPanel(filter_names, mActiveFilters);
        panel_filters.setBackground(MainWindow.bg_color);
        JPanel panel_details = new JPanel();
        panel_details.setBackground(MainWindow.bg_color);
        JPanel panel_colors = new JPanel();
        panel_colors.setBackground(MainWindow.bg_color);
        JPanel panel_texture = new JPanel();
        panel_texture.setBackground(MainWindow.bg_color);
        JPanel panel_texture2 = new JPanel();
        panel_texture2.setBackground(MainWindow.bg_color);
        JPanel panel_lighting = new JPanel();
        panel_lighting.setBackground(MainWindow.bg_color);
        order_panel = new FilterOrderSelectionPanel(filter_names, filter_order, mActiveFilters);
        order_panel.setBackground(MainWindow.bg_color);
        
       
        tabbedPane.setFocusable(false);
        tabbedPane.setPreferredSize(new Dimension(1110, 550));
        tabbedPane.setBackground(MainWindow.bg_color);     

        tabbedPane.addTab("Active Filters", panel_filters);
        tabbedPane.setIconAt(0, MainWindow.getIcon("list2.png"));

        tabbedPane.addTab("Order", order_panel);
        tabbedPane.setIconAt(1, MainWindow.getIcon("list.png"));

        tabbedPane.addTab("Details", panel_details);
        tabbedPane.setIconAt(2, MainWindow.getIcon("filter_details.png"));

        tabbedPane.addTab("Colors", panel_colors);
        tabbedPane.setIconAt(3, MainWindow.getIcon("filter_colors.png"));

        tabbedPane.addTab("Texture", panel_texture);
        tabbedPane.setIconAt(4, MainWindow.getIcon("filter_texture.png"));

        tabbedPane.addTab("Texture(2)", panel_texture2);
        tabbedPane.setIconAt(5, MainWindow.getIcon("filter_texture.png"));

        tabbedPane.addTab("Lighting", panel_lighting);
        tabbedPane.setIconAt(6, MainWindow.getIcon("filter_lighting.png"));
        
        
        tabbedPane.setSelectedIndex(tab_index);

        panel.setPreferredSize(new Dimension(1130, 560));

        JPanel[] panels = new JPanel[components_filters.length];

        panels[MainWindow.ANTIALIASING] = new JPanel();
        panels[MainWindow.ANTIALIASING].setBackground(MainWindow.bg_color);
        String[] antialiasing_str = {"5x Samples", "9x Samples", "17x Samples", "25x Samples", "49x Samples", "81x Samples", "121x Samples", "169x Samples", "225x Samples", "289x Samples"};

        components_filters[MainWindow.ANTIALIASING] = new JPanel();
        ((JPanel)components_filters[MainWindow.ANTIALIASING]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.ANTIALIASING]).setLayout(new GridLayout(6, 1));

        JComboBox<String> aaSamples = new JComboBox<>(antialiasing_str);
        aaSamples.setSelectedIndex((filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10);
        aaSamples.setFocusable(false);
        aaSamples.setToolTipText("Sets the sampled pixels number for the anti-aliasing.");

        String[] antialiasing_method_str = {"Mean", "Median", "Geometric Median", "Closest to Mean", "TriMean", "Adaptive (Min 5, Max 25)", "Gaussian", "Mean Without Outliers", "Geometric Mean", "RMS Mean"};

        JComboBox<String> aaMethod = new JComboBox<>(antialiasing_method_str);
        aaMethod.setSelectedIndex((filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10);
        aaMethod.setFocusable(false);
        aaMethod.setToolTipText("Sets the anti-aliasing method.");


        final JCheckBox aaAvgWithMean = new JCheckBox("AA Method & Mean");
        aaAvgWithMean.setFocusable(false);
        aaAvgWithMean.setBackground(MainWindow.bg_color);
        aaAvgWithMean.setToolTipText("Computes the mean value and the method's value and then averages them.");
        aaAvgWithMean.setSelected(((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1);

        aaAvgWithMean.setEnabled(aaMethod.getSelectedIndex() != 0 && aaMethod.getSelectedIndex() != 5 && aaMethod.getSelectedIndex() != 7);


        String[] antialiasing_color_space_str = {"RGB", "XYZ", "Lab", "RYB", "Luv", "OKLab", "JzAzBz", "Linear sRGB", "YCbCr"};//, "HSB", "HSL", "LCH"};

        JComboBox<String> antialiasing_color_space = new JComboBox<>(antialiasing_color_space_str);
        antialiasing_color_space.setSelectedIndex((filters_options_extra_vals[0][MainWindow.ANTIALIASING]));
        antialiasing_color_space.setFocusable(false);
        antialiasing_color_space.setToolTipText("Sets the anti-aliasing color space.");

        final JCheckBox useJitter = new JCheckBox("Jitter");
        useJitter.setFocusable(false);
        useJitter.setBackground(MainWindow.bg_color);
        useJitter.setToolTipText("Adds jitter to the sampling.");
        useJitter.setSelected(((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4);

        useJitter.setEnabled(aaMethod.getSelectedIndex() != 6);

        antialiasing_color_space.setEnabled(aaMethod.getSelectedIndex() != 5);


        JPanel samples = new JPanel();
        samples.setBackground(MainWindow.bg_color);
        samples.add(new JLabel("Samples:"));
        samples.add(aaSamples);

        JPanel method = new JPanel();
        method.setBackground(MainWindow.bg_color);
        method.add(new JLabel("Method:"));
        method.add(aaMethod);

        JPanel avg = new JPanel();
        avg.setBackground(MainWindow.bg_color);
        avg.add(aaAvgWithMean);

        JPanel jitterPanel = new JPanel();
        jitterPanel.setBackground(MainWindow.bg_color);
        jitterPanel.add(useJitter);


        JPanel space = new JPanel();
        space.setBackground(MainWindow.bg_color);
        space.add(new JLabel("Color Space:"));
        space.add(antialiasing_color_space);

        JPanel sigmapanel = new JPanel();
        sigmapanel.setBackground(MainWindow.bg_color);

        JTextField sigmaR = new JTextField(6);
        sigmaR.setText("" + fs.aaSigmaR);


        sigmapanel.add(new JLabel("Sigma: "));
        sigmapanel.add(sigmaR);

        sigmaR.setEnabled(aaMethod.getSelectedIndex() == 6);

        aaMethod.addActionListener(e -> {
            aaAvgWithMean.setEnabled(aaMethod.getSelectedIndex() != 0 && aaMethod.getSelectedIndex() != 5 && aaMethod.getSelectedIndex() != 7);
            antialiasing_color_space.setEnabled(aaMethod.getSelectedIndex() != 5);
            useJitter.setEnabled(aaMethod.getSelectedIndex() != 6);
            sigmaR.setEnabled(aaMethod.getSelectedIndex() == 6);
        });

        ((JPanel)components_filters[MainWindow.ANTIALIASING]).add(samples);
        ((JPanel)components_filters[MainWindow.ANTIALIASING]).add(method);
        ((JPanel)components_filters[MainWindow.ANTIALIASING]).add(avg);
        ((JPanel)components_filters[MainWindow.ANTIALIASING]).add(jitterPanel);
        ((JPanel)components_filters[MainWindow.ANTIALIASING]).add(space);
        ((JPanel)components_filters[MainWindow.ANTIALIASING]).add(sigmapanel);

        panels[MainWindow.ANTIALIASING].add(((JPanel)components_filters[MainWindow.ANTIALIASING]));
        panels[MainWindow.ANTIALIASING].setBorder(LAFManager.createTitledBorder("Anti-Aliasing"));

        panels[MainWindow.EDGE_DETECTION] = new JPanel();
        panels[MainWindow.EDGE_DETECTION].setBackground(MainWindow.bg_color);
       
       
       components_filters[MainWindow.EDGE_DETECTION] = new JPanel();
       ((JPanel)components_filters[MainWindow.EDGE_DETECTION]).setBackground(MainWindow.bg_color);
       ((JPanel)components_filters[MainWindow.EDGE_DETECTION]).setLayout(new GridLayout(5, 1));
        
       
        String[] edge_detection_str = {"Thin Edges", "Thick Edges"};

        final JComboBox<String> edge_alg = new JComboBox<>(edge_detection_str);
        edge_alg.setSelectedIndex((int)(filters_options_vals[MainWindow.EDGE_DETECTION] % 100.0));
        edge_alg.setFocusable(false);
        edge_alg.setToolTipText("Sets the thickness of the edge.");
        
        JPanel temp_panel_edge = new JPanel();
        temp_panel_edge.setBackground(MainWindow.bg_color);
        temp_panel_edge.add(edge_alg);
        
        final JSlider edge_sensitivity_slid = new JSlider(JSlider.HORIZONTAL, 0, 10, 10 - (int)(filters_options_vals[MainWindow.EDGE_DETECTION] / 100.0));
        edge_sensitivity_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            edge_sensitivity_slid.setBackground(MainWindow.bg_color);
        }
        edge_sensitivity_slid.setFocusable(false);
        edge_sensitivity_slid.setToolTipText("Sets the edge detection sensitivity.");
        edge_sensitivity_slid.setPaintLabels(true);

        Hashtable<Integer, JLabel> table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(5, new JLabel("5"));
        table3.put(10, new JLabel("10"));
        edge_sensitivity_slid.setLabelTable(table3);
        
        JPanel edge_panel1 = new JPanel();
        edge_panel1.setBackground(MainWindow.bg_color);
        edge_panel1.add(new JLabel("Thickness:"));
        
        JPanel edge_panel2 = new JPanel();
        edge_panel2.setBackground(MainWindow.bg_color);
        edge_panel2.add(new JLabel("Sensitivity:"));
        
        final JLabel filter_color_label5 = new ColorLabel();
        
        filter_color_label5.setPreferredSize(new Dimension(22, 22));
        filter_color_label5.setBackground(filters_colors[MainWindow.EDGE_DETECTION]);
        filter_color_label5.setToolTipText("Changes the edge detection background color.");
        
        filter_color_label5.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                new ColorChooserDialog(this_frame, "Edge Detection Color", filter_color_label5, -1, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });
        
        JPanel filter_color_panel5 = new JPanel();
        filter_color_panel5.setBackground(MainWindow.bg_color);
        filter_color_panel5.add(new JLabel("Color: "));
        filter_color_panel5.add(filter_color_label5);
        
        ((JPanel)components_filters[MainWindow.EDGE_DETECTION]).add(edge_panel1);
        ((JPanel)components_filters[MainWindow.EDGE_DETECTION]).add(temp_panel_edge);
        ((JPanel)components_filters[MainWindow.EDGE_DETECTION]).add(edge_panel2);
        ((JPanel)components_filters[MainWindow.EDGE_DETECTION]).add(edge_sensitivity_slid);
        ((JPanel)components_filters[MainWindow.EDGE_DETECTION]).add(filter_color_panel5);
        
        panels[MainWindow.EDGE_DETECTION].add(((JPanel)components_filters[MainWindow.EDGE_DETECTION]));
        panels[MainWindow.EDGE_DETECTION].setBorder(LAFManager.createTitledBorder( "Edge Detection"));

        
        panels[MainWindow.EDGE_DETECTION2] = new JPanel();
        panels[MainWindow.EDGE_DETECTION2].setBackground(MainWindow.bg_color);
        
        components_filters[MainWindow.EDGE_DETECTION2] = new JPanel();
       ((JPanel)components_filters[MainWindow.EDGE_DETECTION2]).setBackground(MainWindow.bg_color);
       ((JPanel)components_filters[MainWindow.EDGE_DETECTION2]).setLayout(new GridLayout(2, 1));
       
        String[] horizontal_str = {"Sobel", "Roberts", "Prewitt", "Frei-Chen"};

        final JComboBox<String> horizontal_edge_alg = new JComboBox<>(horizontal_str);
        horizontal_edge_alg.setSelectedIndex((int)(filters_options_vals[MainWindow.EDGE_DETECTION2] / 10.0));
        horizontal_edge_alg.setFocusable(false);
        horizontal_edge_alg.setToolTipText("Sets the horizontal edge algorithm.");
        
        final JComboBox<String> vertical_edge_alg = new JComboBox<>(horizontal_str);
        vertical_edge_alg.setSelectedIndex((int)(filters_options_vals[MainWindow.EDGE_DETECTION2] % 10.0));
        vertical_edge_alg.setFocusable(false);
        vertical_edge_alg.setToolTipText("Sets the vertical edge algorithm.");
        
        JPanel p1 = new JPanel();
        p1.setBackground(MainWindow.bg_color);
        
        JPanel p2 = new JPanel();
        p2.setBackground(MainWindow.bg_color);
 
        p1.add(new JLabel("Horizontal:"));
        p1.add(horizontal_edge_alg);
        p2.add(new JLabel("Vertical:"));
        p2.add(vertical_edge_alg);
        
        ((JPanel)components_filters[MainWindow.EDGE_DETECTION2]).add(p1);
        ((JPanel)components_filters[MainWindow.EDGE_DETECTION2]).add(p2);
        
        panels[MainWindow.EDGE_DETECTION2].add(((JPanel)components_filters[MainWindow.EDGE_DETECTION2]));
        panels[MainWindow.EDGE_DETECTION2].setBorder(LAFManager.createTitledBorder( "Edge Detection 2"));
       
        panels[MainWindow.SHARPNESS] = new JPanel();
        panels[MainWindow.SHARPNESS].setBackground(MainWindow.bg_color);
        String[] sharpness_str = {"Low", "High", "Unsharp"};

        components_filters[MainWindow.SHARPNESS] = new JComboBox<>(sharpness_str);
        ((JComboBox)components_filters[MainWindow.SHARPNESS]).setSelectedIndex(filters_options_vals[MainWindow.SHARPNESS]);
        ((JComboBox)components_filters[MainWindow.SHARPNESS]).setFocusable(false);
        ((JComboBox)components_filters[MainWindow.SHARPNESS]).setToolTipText("Sets the sharpness algorithm.");


        panels[MainWindow.SHARPNESS].add(((JComboBox)components_filters[MainWindow.SHARPNESS]));
        panels[MainWindow.SHARPNESS].setBorder(LAFManager.createTitledBorder( "Sharpness"));

        panels[MainWindow.EMBOSS] = new JPanel();
        panels[MainWindow.EMBOSS].setBackground(MainWindow.bg_color);
       
       
        components_filters[MainWindow.EMBOSS] = new JPanel();
       ((JPanel)components_filters[MainWindow.EMBOSS]).setBackground(MainWindow.bg_color);
       ((JPanel)components_filters[MainWindow.EMBOSS]).setLayout(new GridLayout(6, 1));
        
        String[] emboss_str = {"Grayscale", "Colored", "Emboss Edges 1", "Emboss Edges 2"};

        final JComboBox<String> emboss_algorithm = new JComboBox<>(emboss_str);
        emboss_algorithm.setSelectedIndex((int)(((int)(((int)(filters_options_vals[MainWindow.EMBOSS] % 100000.0)) % 1000.0)) % 10.0));
        emboss_algorithm.setFocusable(false);
        emboss_algorithm.setToolTipText("Sets the color format of the emboss.");
     
        final JSlider emboss_angle_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(((int)(filters_options_vals[MainWindow.EMBOSS] % 100000.0)) % 1000.0)) / 10.0));
        emboss_angle_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            emboss_angle_slid.setBackground(MainWindow.bg_color);
        }
        emboss_angle_slid.setFocusable(false);
        emboss_angle_slid.setToolTipText("Sets the emboss's light direction.");
        emboss_angle_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(20, new JLabel("90"));
        table3.put(40, new JLabel("180"));
        table3.put(60, new JLabel("270"));
        table3.put(80, new JLabel("360"));
        emboss_angle_slid.setLabelTable(table3);
        
        final JSlider emboss_elevation_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(filters_options_vals[MainWindow.EMBOSS] % 100000.0)) / 1000.0));
        emboss_elevation_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            emboss_elevation_slid.setBackground(MainWindow.bg_color);
        }
        emboss_elevation_slid.setFocusable(false);
        emboss_elevation_slid.setToolTipText("Sets the emboss's light elevation.");
        emboss_elevation_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(40, new JLabel("45"));
        table3.put(80, new JLabel("90"));
        emboss_elevation_slid.setLabelTable(table3);
        
        final JSlider emboss_bumpheight_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(filters_options_vals[MainWindow.EMBOSS] / 100000.0));
        emboss_bumpheight_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            emboss_bumpheight_slid.setBackground(MainWindow.bg_color);
        }
        emboss_bumpheight_slid.setFocusable(false);
        emboss_bumpheight_slid.setToolTipText("Sets the emboss's bump height.");
        emboss_bumpheight_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(20, new JLabel("25"));
        table3.put(40, new JLabel("50"));
        table3.put(60, new JLabel("75"));
        table3.put(80, new JLabel("100"));
        emboss_bumpheight_slid.setLabelTable(table3);
        
        if(emboss_algorithm.getSelectedIndex() == 0 || emboss_algorithm.getSelectedIndex() == 1) {
            emboss_bumpheight_slid.setEnabled(true);
            emboss_angle_slid.setEnabled(true);
            emboss_elevation_slid.setEnabled(true);
        }
        else {
            emboss_bumpheight_slid.setEnabled(false);
            emboss_angle_slid.setEnabled(false);
            emboss_elevation_slid.setEnabled(false);
        }
        
         emboss_algorithm.addActionListener(e -> {
            if(emboss_algorithm.getSelectedIndex() == 0 || emboss_algorithm.getSelectedIndex() == 1) {
                 emboss_bumpheight_slid.setEnabled(true);
                 emboss_angle_slid.setEnabled(true);
                 emboss_elevation_slid.setEnabled(true);
             }
             else {
                 emboss_bumpheight_slid.setEnabled(false);
                 emboss_angle_slid.setEnabled(false);
                 emboss_elevation_slid.setEnabled(false);
             }
         });
         
        JPanel emboss_panel1 = new JPanel();
        emboss_panel1.setBackground(MainWindow.bg_color);
        emboss_panel1.add(emboss_algorithm);
        
        JPanel emboss_label_panel1 = new JPanel();
        emboss_label_panel1.setBackground(MainWindow.bg_color);
        emboss_label_panel1.add(new JLabel("Algorithm:"));
        
        JPanel emboss_label_panel2 = new JPanel();
        emboss_label_panel2.setBackground(MainWindow.bg_color);
        emboss_label_panel2.add(new JLabel("Light Direction, Elevation, Bump Height:"));
        
        ((JPanel)components_filters[MainWindow.EMBOSS]).add(emboss_label_panel1);
        ((JPanel)components_filters[MainWindow.EMBOSS]).add(emboss_panel1);
        ((JPanel)components_filters[MainWindow.EMBOSS]).add(emboss_label_panel2);
        ((JPanel)components_filters[MainWindow.EMBOSS]).add(emboss_angle_slid);
        ((JPanel)components_filters[MainWindow.EMBOSS]).add(emboss_elevation_slid);
        ((JPanel)components_filters[MainWindow.EMBOSS]).add(emboss_bumpheight_slid);
        
        panels[MainWindow.EMBOSS].add(((JPanel)components_filters[MainWindow.EMBOSS]));
        panels[MainWindow.EMBOSS].setBorder(LAFManager.createTitledBorder( "Emboss"));

        panels[MainWindow.HISTOGRAM_EQUALIZATION] = new JPanel();
        panels[MainWindow.HISTOGRAM_EQUALIZATION].setBackground(MainWindow.bg_color);
        String[] histogram_str = {"Brightness", "GIMP levels", "Red", "Green", "Blue", "Hue", "Saturation", "Lightness (Lab)"};

        components_filters[MainWindow.HISTOGRAM_EQUALIZATION] = new JComboBox<>(histogram_str);
        ((JComboBox)components_filters[MainWindow.HISTOGRAM_EQUALIZATION]).setSelectedIndex(filters_options_vals[MainWindow.HISTOGRAM_EQUALIZATION]);
        ((JComboBox)components_filters[MainWindow.HISTOGRAM_EQUALIZATION]).setFocusable(false);
        ((JComboBox)components_filters[MainWindow.HISTOGRAM_EQUALIZATION]).setToolTipText("Sets the histogram algorithm.");
        panels[MainWindow.HISTOGRAM_EQUALIZATION].add(((JComboBox)components_filters[MainWindow.HISTOGRAM_EQUALIZATION]));
        panels[MainWindow.HISTOGRAM_EQUALIZATION].setBorder(LAFManager.createTitledBorder( "Histogram Equalization"));

        panels[MainWindow.POSTERIZE] = new JPanel();
        panels[MainWindow.POSTERIZE].setBackground(MainWindow.bg_color);

        components_filters[MainWindow.POSTERIZE] = new JPanel();
        ((JPanel)components_filters[MainWindow.POSTERIZE]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.POSTERIZE]).setLayout(new GridLayout(2, 1));
        
        final JSlider posterize_slid = new JSlider(JSlider.HORIZONTAL, 1, 256, filters_options_vals[MainWindow.POSTERIZE] % 1000);
        posterize_slid.setPreferredSize(new Dimension(180, 35));
        if(!MainWindow.useCustomLaf) {
            posterize_slid.setBackground(MainWindow.bg_color);
        }
        posterize_slid.setFocusable(false);
        posterize_slid.setToolTipText("Sets the posterization level.");
        
        posterize_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(1, new JLabel("1"));
        table3.put(64, new JLabel("64"));
        table3.put(128, new JLabel("128"));
        table3.put(192, new JLabel("192"));
        table3.put(256, new JLabel("256"));
        posterize_slid.setLabelTable(table3);
        
        JPanel levels_panel1 = new JPanel();
        levels_panel1.setBackground(MainWindow.bg_color);
        levels_panel1.add(new JLabel("Levels:"));
        levels_panel1.add(posterize_slid);

        JPanel mode_panel1 = new JPanel();
        mode_panel1.setBackground(MainWindow.bg_color);
        mode_panel1.add(new JLabel("Mode:"));

        JComboBox<String> modes = new JComboBox<>(new String[] {"RGB", "HSB", "HSL", "Lab"});
        modes.setFocusable(false);
        modes.setSelectedIndex(filters_options_vals[MainWindow.POSTERIZE] / 1000);
        modes.setToolTipText("Sets the posterization mode.");

        mode_panel1.add(modes);
        
        ((JPanel)components_filters[MainWindow.POSTERIZE]).add(levels_panel1);
        ((JPanel)components_filters[MainWindow.POSTERIZE]).add(mode_panel1);

        
        panels[MainWindow.POSTERIZE].add(((JPanel)components_filters[MainWindow.POSTERIZE]));
        panels[MainWindow.POSTERIZE].setBorder(LAFManager.createTitledBorder( "Posterize"));

        panels[MainWindow.CONTRAST_BRIGHTNESS] = new JPanel();
        panels[MainWindow.CONTRAST_BRIGHTNESS].setBackground(MainWindow.bg_color);

        components_filters[MainWindow.CONTRAST_BRIGHTNESS] = new JPanel();
        ((JPanel)components_filters[MainWindow.CONTRAST_BRIGHTNESS]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.CONTRAST_BRIGHTNESS]).setLayout(new GridLayout(2, 1));

        final JSlider contrast_slid = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(filters_options_vals[MainWindow.CONTRAST_BRIGHTNESS] / 1000.0));
        contrast_slid.setPreferredSize(new Dimension(175, 35));
        if(!MainWindow.useCustomLaf) {
            contrast_slid.setBackground(MainWindow.bg_color);
        }
        contrast_slid.setFocusable(false);
        contrast_slid.setToolTipText("Sets the contrast scaling factor.");
        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("C:"));
        panel1.setBackground(MainWindow.bg_color);
        panel1.add(contrast_slid);
        
        contrast_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0.0"));
        table3.put(25, new JLabel("0.5"));
        table3.put(50, new JLabel("1.0"));
        table3.put(75, new JLabel("1.5"));
        table3.put(100, new JLabel("2.0"));
        contrast_slid.setLabelTable(table3);
        
        ((JPanel)components_filters[MainWindow.CONTRAST_BRIGHTNESS]).add(panel1);

        final JSlider brightness_slid = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(filters_options_vals[MainWindow.CONTRAST_BRIGHTNESS] % 1000.0));
        brightness_slid.setPreferredSize(new Dimension(175, 35));
        if(!MainWindow.useCustomLaf) {
            brightness_slid.setBackground(MainWindow.bg_color);
        }
        brightness_slid.setFocusable(false);
        brightness_slid.setToolTipText("Sets the brightness scaling factor.");
        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("B:"));
        panel2.setBackground(MainWindow.bg_color);
        panel2.add(brightness_slid);
        
        brightness_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0.0"));
        table3.put(25, new JLabel("0.5"));
        table3.put(50, new JLabel("1.0"));
        table3.put(75, new JLabel("1.5"));
        table3.put(100, new JLabel("2.0"));
        brightness_slid.setLabelTable(table3);
        
        ((JPanel)components_filters[MainWindow.CONTRAST_BRIGHTNESS]).add(panel2);

        panels[MainWindow.CONTRAST_BRIGHTNESS].add(((JPanel)components_filters[MainWindow.CONTRAST_BRIGHTNESS]));

        panels[MainWindow.CONTRAST_BRIGHTNESS].setBorder(LAFManager.createTitledBorder( "Contrast/Brightness"));

        panels[MainWindow.COLOR_TEMPERATURE] = new JPanel();
        panels[MainWindow.COLOR_TEMPERATURE].setBackground(MainWindow.bg_color);

        components_filters[MainWindow.COLOR_TEMPERATURE] = new JSlider(JSlider.HORIZONTAL, 1000, 40000, filters_options_vals[MainWindow.COLOR_TEMPERATURE]);
        ((JSlider)components_filters[MainWindow.COLOR_TEMPERATURE]).setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            ((JSlider) components_filters[MainWindow.COLOR_TEMPERATURE]).setBackground(MainWindow.bg_color);
        }
        ((JSlider)components_filters[MainWindow.COLOR_TEMPERATURE]).setToolTipText("Sets the temperature of the colors in Kelvin.");
        ((JSlider)components_filters[MainWindow.COLOR_TEMPERATURE]).setFocusable(false);
        
        ((JSlider)components_filters[MainWindow.COLOR_TEMPERATURE]).setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(1000, new JLabel("1000"));
        table3.put(10000, new JLabel("10000"));
        table3.put(20000, new JLabel("20000"));
        table3.put(30000, new JLabel("30000"));
        table3.put(40000, new JLabel("40000"));
        ((JSlider)components_filters[MainWindow.COLOR_TEMPERATURE]).setLabelTable(table3);

        panels[MainWindow.COLOR_TEMPERATURE].add(((JSlider)components_filters[MainWindow.COLOR_TEMPERATURE]));
        panels[MainWindow.COLOR_TEMPERATURE].setBorder(LAFManager.createTitledBorder( "Color Temperature"));

        panels[MainWindow.INVERT_COLORS] = new JPanel();
        panels[MainWindow.INVERT_COLORS].setBackground(MainWindow.bg_color);
        String[] color_invert_str = {"Colors", "Brightness", "Hue", "Saturation", "Red", "Green", "Blue"};

        components_filters[MainWindow.INVERT_COLORS] = new JComboBox<>(color_invert_str);
        ((JComboBox)components_filters[MainWindow.INVERT_COLORS]).setSelectedIndex(filters_options_vals[MainWindow.INVERT_COLORS]);
        ((JComboBox)components_filters[MainWindow.INVERT_COLORS]).setFocusable(false);
        ((JComboBox)components_filters[MainWindow.INVERT_COLORS]).setToolTipText("Sets the color inverting algorithm.");
        panels[MainWindow.INVERT_COLORS].add(((JComboBox)components_filters[MainWindow.INVERT_COLORS]));
        panels[MainWindow.INVERT_COLORS].setBorder(LAFManager.createTitledBorder( "Inverted Colors"));

        panels[MainWindow.COLOR_CHANNEL_MASKING] = new JPanel();
        panels[MainWindow.COLOR_CHANNEL_MASKING].setBackground(MainWindow.bg_color);
        String[] mask_color_channel_str = {"Red", "Green", "Blue"};

        components_filters[MainWindow.COLOR_CHANNEL_MASKING] = new JComboBox<>(mask_color_channel_str);
        ((JComboBox)components_filters[MainWindow.COLOR_CHANNEL_MASKING]).setSelectedIndex(filters_options_vals[MainWindow.COLOR_CHANNEL_MASKING]);
        ((JComboBox)components_filters[MainWindow.COLOR_CHANNEL_MASKING]).setFocusable(false);
        ((JComboBox)components_filters[MainWindow.COLOR_CHANNEL_MASKING]).setToolTipText("Sets the color channel that will be removed.");
        panels[MainWindow.COLOR_CHANNEL_MASKING].add(((JComboBox)components_filters[MainWindow.COLOR_CHANNEL_MASKING]));
        panels[MainWindow.COLOR_CHANNEL_MASKING].setBorder(LAFManager.createTitledBorder( "Mask Color Channel"));

        panels[MainWindow.COLOR_CHANNEL_SWAPPING] = new JPanel();
        panels[MainWindow.COLOR_CHANNEL_SWAPPING].setBackground(MainWindow.bg_color);
        String[] color_channel_swapping_str = {"RBG", "GRB", "GBR", "BGR", "BRG"};

        components_filters[MainWindow.COLOR_CHANNEL_SWAPPING] = new JComboBox<>(color_channel_swapping_str);
        ((JComboBox)components_filters[MainWindow.COLOR_CHANNEL_SWAPPING]).setSelectedIndex(filters_options_vals[MainWindow.COLOR_CHANNEL_SWAPPING]);
        ((JComboBox)components_filters[MainWindow.COLOR_CHANNEL_SWAPPING]).setFocusable(false);
        ((JComboBox)components_filters[MainWindow.COLOR_CHANNEL_SWAPPING]).setToolTipText("Sets the color swapping format.");
        panels[MainWindow.COLOR_CHANNEL_SWAPPING].add(((JComboBox)components_filters[MainWindow.COLOR_CHANNEL_SWAPPING]));
        panels[MainWindow.COLOR_CHANNEL_SWAPPING].setBorder(LAFManager.createTitledBorder( "Color Channel Swapping"));

        panels[MainWindow.COLOR_CHANNEL_SWIZZLING] = new JPanel();
        panels[MainWindow.COLOR_CHANNEL_SWIZZLING].setBackground(MainWindow.bg_color);
        
        components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING] = new JPanel();
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).setBackground(MainWindow.bg_color);
        
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).setLayout(new GridLayout(4, 9));
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).setPreferredSize(new Dimension(170, 85));
        
        final JCheckBox[] swizzle_checks = new JCheckBox[12];
        
        for(int j = 0; j < swizzle_checks.length; j++) {
            swizzle_checks[j] = new JCheckBox();
            swizzle_checks[j].setBackground(MainWindow.bg_color);
            swizzle_checks[j].setToolTipText("Enable this to activate the channel above.");
            
            if(((filters_options_vals[MainWindow.COLOR_CHANNEL_SWIZZLING] >> j) & 0x1) == 1) {
                swizzle_checks[j].setSelected(true);
            }
        }
        
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel(" ", SwingConstants.HORIZONTAL));
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel(" ", SwingConstants.HORIZONTAL));
        
        JLabel r_label1 = new JLabel("R", SwingConstants.HORIZONTAL);
        r_label1.setForeground(Color.red);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(r_label1);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel(" ", SwingConstants.HORIZONTAL));
        JLabel g_label1 = new JLabel("G", SwingConstants.HORIZONTAL);
        g_label1.setForeground(Color.green);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(g_label1);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel(" ", SwingConstants.HORIZONTAL));
        JLabel b_label1 = new JLabel("B", SwingConstants.HORIZONTAL);
        b_label1.setForeground(Color.blue);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(b_label1);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel(" ", SwingConstants.HORIZONTAL));
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel("1", SwingConstants.HORIZONTAL));
        
        JLabel r_label2 = new JLabel("R", SwingConstants.HORIZONTAL);
        r_label2.setForeground(Color.red);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(r_label2);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel("=", SwingConstants.HORIZONTAL));
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(swizzle_checks[0]);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel("+", SwingConstants.HORIZONTAL));
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(swizzle_checks[1]);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel("+", SwingConstants.HORIZONTAL));
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(swizzle_checks[2]);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel("+", SwingConstants.HORIZONTAL));
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(swizzle_checks[3]);
        
        JLabel g_label2 = new JLabel("G", SwingConstants.HORIZONTAL);
        g_label2.setForeground(Color.green);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(g_label2);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel("=", SwingConstants.HORIZONTAL));
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(swizzle_checks[4]);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel("+", SwingConstants.HORIZONTAL));
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(swizzle_checks[5]);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel("+", SwingConstants.HORIZONTAL));
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(swizzle_checks[6]);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel("+", SwingConstants.HORIZONTAL));
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(swizzle_checks[7]);
        
        
        JLabel b_label2 = new JLabel("B", SwingConstants.HORIZONTAL);
        b_label2.setForeground(Color.blue);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(b_label2);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel("=", SwingConstants.HORIZONTAL));
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(swizzle_checks[8]);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel("+", SwingConstants.HORIZONTAL));
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(swizzle_checks[9]);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel("+", SwingConstants.HORIZONTAL));
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(swizzle_checks[10]);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(new JLabel("+", SwingConstants.HORIZONTAL));
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]).add(swizzle_checks[11]);
        
        panels[MainWindow.COLOR_CHANNEL_SWIZZLING].add(((JPanel)components_filters[MainWindow.COLOR_CHANNEL_SWIZZLING]));
        panels[MainWindow.COLOR_CHANNEL_SWIZZLING].setBorder(LAFManager.createTitledBorder( "Color Channel Swizzling"));

        panels[MainWindow.GRAYSCALE] = new JPanel();
        panels[MainWindow.GRAYSCALE].setBackground(MainWindow.bg_color);
        String[] grayscale_str = {"Luminance NTSC", "Luminance HDTV", "Average", "Lightness", "Lightness Lab", "Luminance HDR"};

        components_filters[MainWindow.GRAYSCALE] = new JComboBox<>(grayscale_str);
        ((JComboBox)components_filters[MainWindow.GRAYSCALE]).setSelectedIndex(filters_options_vals[MainWindow.GRAYSCALE]);
        ((JComboBox)components_filters[MainWindow.GRAYSCALE]).setFocusable(false);
        ((JComboBox)components_filters[MainWindow.GRAYSCALE]).setToolTipText("Sets the grayscale format.");
        panels[MainWindow.GRAYSCALE].add(((JComboBox)components_filters[MainWindow.GRAYSCALE]));
        panels[MainWindow.GRAYSCALE].setBorder(LAFManager.createTitledBorder( "Grayscale"));

        panels[MainWindow.COLOR_CHANNEL_ADJUSTING] = new JPanel();
        panels[MainWindow.COLOR_CHANNEL_ADJUSTING].setBackground(MainWindow.bg_color);

        components_filters[MainWindow.COLOR_CHANNEL_ADJUSTING] = new JPanel();
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_ADJUSTING]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_ADJUSTING]).setLayout(new GridLayout(1, 3));

        final JSlider slid1 = new JSlider(JSlider.VERTICAL, 0, 100, (int)(filters_options_vals[MainWindow.COLOR_CHANNEL_ADJUSTING] / 1000000.0));
        slid1.setPreferredSize(new Dimension(40, 85));
        if(!MainWindow.useCustomLaf) {
            slid1.setBackground(MainWindow.bg_color);
        }
        slid1.setFocusable(false);
        slid1.setToolTipText("Sets the red channel adjusting.");
        JPanel panel3 = new JPanel();
        JLabel r_label = new JLabel("    R:");
        r_label.setForeground(Color.red);
        panel3.add(r_label );
        panel3.setBackground(MainWindow.bg_color);
        panel3.add(slid1);
        
        slid1.setPaintLabels(true);
        
        table3 = new Hashtable<>();
        table3.put(0, new JLabel("-1.0"));
        table3.put(50, new JLabel(" 0.0"));
        table3.put(100, new JLabel(" 1.0"));
        slid1.setLabelTable(table3);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_ADJUSTING]).add(panel3);

        final JSlider slid2 = new JSlider(JSlider.VERTICAL, 0, 100, (int)(((int)(filters_options_vals[MainWindow.COLOR_CHANNEL_ADJUSTING] % 1000000.0)) / 1000.0));
        slid2.setPreferredSize(new Dimension(40, 85));
        if(!MainWindow.useCustomLaf) {
            slid2.setBackground(MainWindow.bg_color);
        }
        slid2.setFocusable(false);
        slid2.setToolTipText("Sets the green channel adjusting.");
        JPanel panel4 = new JPanel();
        JLabel g_label = new JLabel("    G:");
        g_label.setForeground(Color.green);
        panel4.add(g_label);
        panel4.setBackground(MainWindow.bg_color);
        panel4.add(slid2);
        
        slid2.setPaintLabels(true);
        
        table3 = new Hashtable<>();
        table3.put(0, new JLabel("-1.0"));
        table3.put(50, new JLabel(" 0.0"));
        table3.put(100, new JLabel(" 1.0"));
        slid2.setLabelTable(table3);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_ADJUSTING]).add(panel4);

        final JSlider slid3 = new JSlider(JSlider.VERTICAL, 0, 100, (int)(((int)(filters_options_vals[MainWindow.COLOR_CHANNEL_ADJUSTING] % 1000000.0)) % 1000.0));
        slid3.setPreferredSize(new Dimension(40, 85));
        if(!MainWindow.useCustomLaf) {
            slid3.setBackground(MainWindow.bg_color);
        }
        slid3.setFocusable(false);
        slid3.setToolTipText("Sets the blue channel adjusting.");
        JPanel panel5 = new JPanel();
        JLabel b_label = new JLabel("    B:");
        b_label.setForeground(Color.blue);
        panel5.add(b_label);
        panel5.setBackground(MainWindow.bg_color);
        panel5.add(slid3);
        
        slid3.setPaintLabels(true);
        
        table3 = new Hashtable<>();
        table3.put(0, new JLabel("-1.0"));
        table3.put(50, new JLabel(" 0.0"));
        table3.put(100, new JLabel(" 1.0"));
        slid3.setLabelTable(table3);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_ADJUSTING]).add(panel5);

        panels[MainWindow.COLOR_CHANNEL_ADJUSTING].add(((JPanel)components_filters[MainWindow.COLOR_CHANNEL_ADJUSTING]));

        panels[MainWindow.COLOR_CHANNEL_ADJUSTING].setBorder(LAFManager.createTitledBorder( "Color Channel Adjusting"));

        panels[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING] = new JPanel();
        panels[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING].setBackground(MainWindow.bg_color);

        components_filters[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING] = new JPanel();
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING]).setLayout(new GridLayout(1, 3));

        final JSlider slid4 = new JSlider(JSlider.VERTICAL, 0, 100, (int)(filters_options_vals[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING] / 1000000.0));
        slid4.setPreferredSize(new Dimension(40, 85));
        if(!MainWindow.useCustomLaf) {
            slid4.setBackground(MainWindow.bg_color);
        }
        slid4.setFocusable(false);
        slid4.setToolTipText("Sets the hue channel adjusting.");
        JPanel panel6 = new JPanel();
        panel6.add(new JLabel("    H:"));
        panel6.setBackground(MainWindow.bg_color);
        panel6.add(slid4);
        
        slid4.setPaintLabels(true);
        
        table3 = new Hashtable<>();
        table3.put(0, new JLabel("-1.0"));
        table3.put(50, new JLabel(" 0.0"));
        table3.put(100, new JLabel(" 1.0"));
        slid4.setLabelTable(table3);
        
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING]).add(panel6);

        final JSlider slid5 = new JSlider(JSlider.VERTICAL, 0, 100, (int)(((int)(filters_options_vals[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING] % 1000000.0)) / 1000.0));
        slid5.setPreferredSize(new Dimension(40, 85));
        if(!MainWindow.useCustomLaf) {
            slid5.setBackground(MainWindow.bg_color);
        }
        slid5.setFocusable(false);
        slid5.setToolTipText("Sets the saturation channel adjusting.");
        JPanel panel7 = new JPanel();
        panel7.add(new JLabel("    S:"));
        panel7.setBackground(MainWindow.bg_color);
        panel7.add(slid5);
        
        slid5.setPaintLabels(true);
        
        table3 = new Hashtable<>();
        table3.put(0, new JLabel("-1.0"));
        table3.put(50, new JLabel(" 0.0"));
        table3.put(100, new JLabel(" 1.0"));
        slid5.setLabelTable(table3);
        
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING]).add(panel7);

        final JSlider slid6 = new JSlider(JSlider.VERTICAL, 0, 100, (int)(((int)(filters_options_vals[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING] % 1000000.0)) % 1000.0));
        slid6.setPreferredSize(new Dimension(40, 85));
        if(!MainWindow.useCustomLaf) {
            slid6.setBackground(MainWindow.bg_color);
        }
        slid6.setFocusable(false);
        slid6.setToolTipText("Sets the brightness channel adjusting.");
        JPanel panel8 = new JPanel();
        panel8.add(new JLabel("    B:"));
        panel8.setBackground(MainWindow.bg_color);
        panel8.add(slid6);
        
        slid6.setPaintLabels(true);
        
        table3 = new Hashtable<>();
        table3.put(0, new JLabel("-1.0"));
        table3.put(50, new JLabel(" 0.0"));
        table3.put(100, new JLabel(" 1.0"));
        slid6.setLabelTable(table3);
        
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING]).add(panel8);

        panels[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING].add(((JPanel)components_filters[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING]));

        panels[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING].setBorder(LAFManager.createTitledBorder( "Color Channel HSB Adjusting"));

        panels[MainWindow.DITHER] = new JPanel();
        panels[MainWindow.DITHER].setBackground(MainWindow.bg_color);

        components_filters[MainWindow.DITHER] = new JPanel();
        ((JPanel)components_filters[MainWindow.DITHER]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.DITHER]).setLayout(new GridLayout(5, 1));
        

        String[] dither_str = {"2x2 Ordered", "4x4 Ordered", "6x6 Ordered", "8x8 Ordered", "4x4 Magic Square", "Cluster 3", "Cluster 4", "Cluster 8", "4x4 Horizontal Lines", "6x6 90 Degree Halftone"};

        final JComboBox<String> dither_alg = new JComboBox<>(dither_str);
        dither_alg.setSelectedIndex((int)((int)(((int)(filters_options_vals[MainWindow.DITHER] % 100000.0)) % 10000.0) / 1000.0));
        dither_alg.setFocusable(false);
        dither_alg.setToolTipText("Sets the dithering algorithm.");

        JPanel temp_panel_dither = new JPanel();
        temp_panel_dither.setBackground(MainWindow.bg_color);
        temp_panel_dither.add(dither_alg);

        ((JPanel)components_filters[MainWindow.DITHER]).add(temp_panel_dither);

        final JSlider dither_slid = new JSlider(JSlider.HORIZONTAL, 1, 32, (int)((int)(((int)(filters_options_vals[MainWindow.DITHER] % 100000.0)) % 10000.0) % 1000.0));
        dither_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            dither_slid.setBackground(MainWindow.bg_color);
        }
        dither_slid.setFocusable(false);
        dither_slid.setToolTipText("Sets the dithering levels.");
        
        dither_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(1, new JLabel("1"));
        table3.put(8, new JLabel("8"));
        table3.put(16, new JLabel("16"));
        table3.put(24, new JLabel("24"));
        table3.put(32, new JLabel("32"));
        dither_slid.setLabelTable(table3);
        
        final JCheckBox diffusion_dither_box = new JCheckBox("Diffusion");
        diffusion_dither_box.setFocusable(false);
        diffusion_dither_box.setBackground(MainWindow.bg_color);
        diffusion_dither_box.setToolTipText("Sets the diffusion algorithm.");
        
        if((int)(((int)(filters_options_vals[MainWindow.DITHER] % 100000.0)) / 10000.0) == 1) {
            diffusion_dither_box.setSelected(true);
        }
        
        
        
        final JCheckBox serpentine_box = new JCheckBox("Serpentine");
        serpentine_box.setFocusable(false);
        serpentine_box.setBackground(MainWindow.bg_color);
        serpentine_box.setToolTipText("Sets the serpentine algorithm.");
        
        if((int)(filters_options_vals[MainWindow.DITHER] / 100000.0) == 1) {
            serpentine_box.setSelected(true);
        }
        
        diffusion_dither_box.addActionListener(e -> {
            if(diffusion_dither_box.isSelected()) {
                dither_alg.setEnabled(false);
                serpentine_box.setEnabled(true);
            }
            else {
                serpentine_box.setEnabled(false);
                dither_alg.setEnabled(true);
            }
        });
        
        if(diffusion_dither_box.isSelected()) {
            dither_alg.setEnabled(false);
            serpentine_box.setEnabled(true);
        }
        else {
            serpentine_box.setEnabled(false);
            dither_alg.setEnabled(true);
        }
        
        JPanel diffusion_panel = new JPanel();
        diffusion_panel.setBackground(MainWindow.bg_color);
        
        diffusion_panel.add(diffusion_dither_box);
        diffusion_panel.add(serpentine_box);
              
        JPanel levels_panel2 = new JPanel();
        levels_panel2.setBackground(MainWindow.bg_color);
        levels_panel2.add(new JLabel("Levels:"));
        
        ((JPanel)components_filters[MainWindow.DITHER]).add(levels_panel2);
        ((JPanel)components_filters[MainWindow.DITHER]).add(dither_slid);
        ((JPanel)components_filters[MainWindow.DITHER]).add(new JLabel(""));
        ((JPanel)components_filters[MainWindow.DITHER]).add(diffusion_panel);

        panels[MainWindow.DITHER].add((JPanel)components_filters[MainWindow.DITHER]);

        panels[MainWindow.DITHER].setBorder(LAFManager.createTitledBorder( "Dither"));

        panels[MainWindow.GAIN] = new JPanel();
        panels[MainWindow.GAIN].setBackground(MainWindow.bg_color);

        components_filters[MainWindow.GAIN] = new JPanel();
        ((JPanel)components_filters[MainWindow.GAIN]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.GAIN]).setLayout(new GridLayout(2, 1));

        final JSlider gain_slid = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(filters_options_vals[MainWindow.GAIN] / 1000.0));
        gain_slid.setPreferredSize(new Dimension(175, 35));
        if(!MainWindow.useCustomLaf) {
            gain_slid.setBackground(MainWindow.bg_color);
        }
        gain_slid.setFocusable(false);
        gain_slid.setToolTipText("Sets the gain scaling factor.");
        JPanel panel9 = new JPanel();
        panel9.add(new JLabel("G:"));
        panel9.setBackground(MainWindow.bg_color);
        panel9.add(gain_slid);
        
        gain_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(25, new JLabel("25"));
        table3.put(50, new JLabel("50"));
        table3.put(75, new JLabel("75"));
        table3.put(100, new JLabel("100"));
        gain_slid.setLabelTable(table3);
        
        ((JPanel)components_filters[MainWindow.GAIN]).add(panel9);

        final JSlider bias_slid = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(filters_options_vals[MainWindow.GAIN] % 1000.0));
        bias_slid.setPreferredSize(new Dimension(175, 35));
        if(!MainWindow.useCustomLaf) {
            bias_slid.setBackground(MainWindow.bg_color);
        }
        bias_slid.setFocusable(false);
        bias_slid.setToolTipText("Sets the bias scaling factor.");
        JPanel panel10 = new JPanel();
        panel10.add(new JLabel("B:"));
        panel10.setBackground(MainWindow.bg_color);
        panel10.add(bias_slid);
        
        bias_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(25, new JLabel("25"));
        table3.put(50, new JLabel("50"));
        table3.put(75, new JLabel("75"));
        table3.put(100, new JLabel("100"));
        bias_slid.setLabelTable(table3);
        ((JPanel)components_filters[MainWindow.GAIN]).add(panel10);

        panels[MainWindow.GAIN].add(((JPanel)components_filters[MainWindow.GAIN]));

        panels[MainWindow.GAIN].setBorder(LAFManager.createTitledBorder( "Gain/Bias"));

        panels[MainWindow.GAMMA] = new JPanel();
        panels[MainWindow.GAMMA].setBackground(MainWindow.bg_color);

        components_filters[MainWindow.GAMMA] = new JSlider(JSlider.HORIZONTAL, 0, 100, filters_options_vals[MainWindow.GAMMA]);
        ((JSlider)components_filters[MainWindow.GAMMA]).setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            ((JSlider) components_filters[MainWindow.GAMMA]).setBackground(MainWindow.bg_color);
        }
        ((JSlider)components_filters[MainWindow.GAMMA]).setToolTipText("Sets the gamma scaling factor.");
        ((JSlider)components_filters[MainWindow.GAMMA]).setFocusable(false);

        ((JSlider)components_filters[MainWindow.GAMMA]).setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0.0"));
        table3.put(33, new JLabel("1.0"));
        table3.put(66, new JLabel("2.0"));
        table3.put(100, new JLabel("3.0"));
        ((JSlider)components_filters[MainWindow.GAMMA]).setLabelTable(table3);

        panels[MainWindow.GAMMA].add(((JSlider)components_filters[MainWindow.GAMMA]));

        panels[MainWindow.GAMMA].setBorder(LAFManager.createTitledBorder( "Gamma"));

        panels[MainWindow.EXPOSURE] = new JPanel();
        panels[MainWindow.EXPOSURE].setBackground(MainWindow.bg_color);

        components_filters[MainWindow.EXPOSURE] = new JSlider(JSlider.HORIZONTAL, 0, 100, filters_options_vals[MainWindow.EXPOSURE]);
        ((JSlider)components_filters[MainWindow.EXPOSURE]).setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            ((JSlider) components_filters[MainWindow.EXPOSURE]).setBackground(MainWindow.bg_color);
        }
        ((JSlider)components_filters[MainWindow.EXPOSURE]).setToolTipText("Sets the exposure scaling factor.");
        ((JSlider)components_filters[MainWindow.EXPOSURE]).setFocusable(false);

        ((JSlider)components_filters[MainWindow.EXPOSURE]).setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0.0"));
        table3.put(20, new JLabel("1.0"));
        table3.put(40, new JLabel("2.0"));
        table3.put(60, new JLabel("3.0"));
        table3.put(80, new JLabel("4.0"));
        table3.put(100, new JLabel("5.0"));
        ((JSlider)components_filters[MainWindow.EXPOSURE]).setLabelTable(table3);

        panels[MainWindow.EXPOSURE].add(((JSlider)components_filters[MainWindow.EXPOSURE]));

        panels[MainWindow.EXPOSURE].setBorder(LAFManager.createTitledBorder( "Exposure"));

        panels[MainWindow.BLURRING] = new JPanel();
        panels[MainWindow.BLURRING].setBackground(MainWindow.bg_color);

        components_filters[MainWindow.BLURRING] = new JPanel();
        ((JPanel)components_filters[MainWindow.BLURRING]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.BLURRING]).setLayout(new GridLayout(6, 1));

        String[] blurring_str = {"Default", "Gaussian, Kernel 3", "Gaussian, Kernel 5", "Gaussian, Kernel 7", "Gaussian, Kernel 9", "Gaussian, Kernel 11", "Maximum", "Median", "Minimum", "High Pass", "Blur", "Despeckle", "Lens Blur", "Reduce Noise", "Smart Blur", "Gaussian", "Bilateral"};

        final JComboBox<String> blurring_radius = new JComboBox<>(blurring_str);
        blurring_radius.setSelectedIndex((int)(filters_options_vals[MainWindow.BLURRING] / 1000.0));
        blurring_radius.setFocusable(false);
        blurring_radius.setToolTipText("Sets the blurring method.");

        JPanel temp_panel_blurring = new JPanel();
        temp_panel_blurring.setBackground(MainWindow.bg_color);
        temp_panel_blurring.add(blurring_radius);
        
        JPanel alg_panel = new JPanel();
        alg_panel.setBackground(MainWindow.bg_color);
        alg_panel.add(new JLabel("Algorithm:"));
        
        JPanel radius_panel = new JPanel();
        radius_panel.setBackground(MainWindow.bg_color);
        radius_panel.add(new JLabel("Radius:"));

        JPanel kernel_panel = new JPanel();
        kernel_panel.setBackground(MainWindow.bg_color);
        kernel_panel.add(new JLabel("Kernel: "));

        String[] kernels = {"3", "5", "7", "9", "11"};
        final JComboBox<String> kernels_size_opt = new JComboBox<>(kernels);
        kernels_size_opt.setSelectedIndex(fs.blurringKernelSelection);
        kernels_size_opt.setFocusable(false);
        kernels_size_opt.setToolTipText("Sets the kernel size of the bilateral smoothing.");

        kernel_panel.add(kernels_size_opt);

        JPanel sigmapanel2 = new JPanel();
        sigmapanel2.setBackground(MainWindow.bg_color);

        JTextField sigmaR2 = new JTextField(6);
        sigmaR2.setText("" + fs.bluringSigmaR);


        JTextField sigmaS2 = new JTextField(6);
        sigmaS2.setText("" + fs.bluringSigmaS);
        sigmapanel2.add(new JLabel("Sigma R: "));
        sigmapanel2.add(sigmaR2);
        sigmapanel2.add(new JLabel(" S: "));
        sigmapanel2.add(sigmaS2);

        final JSlider radius_slid = new JSlider(JSlider.HORIZONTAL, 1, 100, (int)(filters_options_vals[MainWindow.BLURRING] % 1000.0));
        radius_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            radius_slid.setBackground(MainWindow.bg_color);
        }
        radius_slid.setFocusable(false);
        radius_slid.setToolTipText("Sets the blurring radius.");
        radius_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(1, new JLabel("1"));
        table3.put(25, new JLabel("25"));
        table3.put(50, new JLabel("50"));
        table3.put(75, new JLabel("75"));
        table3.put(100, new JLabel("100"));
        radius_slid.setLabelTable(table3);


        ((JPanel)components_filters[MainWindow.BLURRING]).add(alg_panel);
        ((JPanel)components_filters[MainWindow.BLURRING]).add(temp_panel_blurring);
        ((JPanel)components_filters[MainWindow.BLURRING]).add(sigmapanel2);
        ((JPanel)components_filters[MainWindow.BLURRING]).add(kernel_panel);
        ((JPanel)components_filters[MainWindow.BLURRING]).add(radius_panel);
        ((JPanel)components_filters[MainWindow.BLURRING]).add(radius_slid);


        sigmaR2.setEnabled((blurring_radius.getSelectedIndex() > 0 && blurring_radius.getSelectedIndex() < 6) || blurring_radius.getSelectedIndex() == 16);
        sigmaS2.setEnabled(blurring_radius.getSelectedIndex() == 16);
        kernels_size_opt.setEnabled(blurring_radius.getSelectedIndex() == 16);
        radius_slid.setEnabled(blurring_radius.getSelectedIndex() == 9 || blurring_radius.getSelectedIndex() == 15);
        blurring_radius.addActionListener(e -> {
            sigmaR2.setEnabled((blurring_radius.getSelectedIndex() > 0 && blurring_radius.getSelectedIndex() < 6) || blurring_radius.getSelectedIndex() == 16);
            sigmaS2.setEnabled(blurring_radius.getSelectedIndex() == 16);
            kernels_size_opt.setEnabled(blurring_radius.getSelectedIndex() == 16);
            radius_slid.setEnabled(blurring_radius.getSelectedIndex() == 9 || blurring_radius.getSelectedIndex() == 15);
        });

        panels[MainWindow.BLURRING].add((JPanel)components_filters[MainWindow.BLURRING]);

        panels[MainWindow.BLURRING].setBorder(LAFManager.createTitledBorder( "Blurring"));

 
        
        panels[MainWindow.CRYSTALLIZE] = new JPanel();
        panels[MainWindow.CRYSTALLIZE].setBackground(MainWindow.bg_color);
       
        
        components_filters[MainWindow.CRYSTALLIZE] = new JPanel();
        ((JPanel)components_filters[MainWindow.CRYSTALLIZE]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.CRYSTALLIZE]).setLayout(new GridLayout(6, 1));
        
        final JSlider size_slid = new JSlider(JSlider.HORIZONTAL, 1, 80, (int)(((int)(((int)(((int)(filters_options_vals[MainWindow.CRYSTALLIZE] % 10000000.0)) % 1000000.0)) % 10000.0)) % 100.0));
        size_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            size_slid.setBackground(MainWindow.bg_color);
        }
        size_slid.setFocusable(false);
        size_slid.setToolTipText("Sets the crystal's size.");
        size_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(1, new JLabel("1.0"));
        table3.put(40, new JLabel("50.0"));
        table3.put(80, new JLabel("100.0"));
        size_slid.setLabelTable(table3);
        
        final JSlider random_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(((int)(((int)(filters_options_vals[MainWindow.CRYSTALLIZE] % 10000000.0)) % 1000000.0)) % 10000.0)) / 100.0));
        random_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            random_slid.setBackground(MainWindow.bg_color);
        }
        random_slid.setFocusable(false);
        random_slid.setToolTipText("Sets the crystal's randomness.");
        random_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(40, new JLabel("50"));
        table3.put(80, new JLabel("100"));
        random_slid.setLabelTable(table3);
        
        final JSlider edge_size_slid = new JSlider(JSlider.HORIZONTAL, 1, 80, (int)(((int)(((int)(filters_options_vals[MainWindow.CRYSTALLIZE] % 10000000.0)) % 1000000.0)) / 10000.0));
        edge_size_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            edge_size_slid.setBackground(MainWindow.bg_color);
        }
        edge_size_slid.setFocusable(false);
        edge_size_slid.setToolTipText("Sets the crystal's edge size.");
        edge_size_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(1, new JLabel("1"));
        table3.put(40, new JLabel("50"));
        table3.put(80, new JLabel("100"));
        edge_size_slid.setLabelTable(table3);
        
        String[] shape_str = {"Random", "Squares", "Hexagons", "Octagons & Squares", "Triangles"};

        final JComboBox<String> shape_box = new JComboBox<>(shape_str);
        shape_box.setSelectedIndex((int)(((int)(filters_options_vals[MainWindow.CRYSTALLIZE] % 10000000.0)) / 1000000.0));
        shape_box.setFocusable(false);
        shape_box.setToolTipText("Sets the crystal's shape.");

        JPanel shape_panel = new JPanel();
        shape_panel.setBackground(MainWindow.bg_color);
        shape_panel.add(new JLabel("Shape: "));
        shape_panel.add(shape_box);
        
        final JLabel filter_color_label = new ColorLabel();
        
        filter_color_label.setPreferredSize(new Dimension(22, 22));
        filter_color_label.setBackground(filters_colors[MainWindow.CRYSTALLIZE]);
        filter_color_label.setToolTipText("Changes the crystal's edge color.");
        
        filter_color_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                new ColorChooserDialog(this_frame, "Crystallize Color", filter_color_label, -1, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });
        
        final JCheckBox original_color_crys = new JCheckBox("Original");
        original_color_crys.setFocusable(false);
        original_color_crys.setBackground(MainWindow.bg_color);
        original_color_crys.setToolTipText("Uses the original color pixel color.");
        
        final JCheckBox fade_edges = new JCheckBox("Fade");
        fade_edges.setFocusable(false);
        fade_edges.setBackground(MainWindow.bg_color);
        fade_edges.setToolTipText("Fades the edges.");
        
        fade_edges.addActionListener(e -> original_color_crys.setEnabled(!fade_edges.isSelected()));
        
        original_color_crys.addActionListener(e -> fade_edges.setEnabled(!original_color_crys.isSelected()));
        
        if((int)(filters_options_vals[MainWindow.CRYSTALLIZE] / 10000000.0) == 1) {
            fade_edges.setSelected(true);
            original_color_crys.setEnabled(false);
        }
        
        if(filters_options_extra_vals[0][MainWindow.CRYSTALLIZE] == 1) {
            original_color_crys.setSelected(true);
            fade_edges.setEnabled(false);
        }
        
        JPanel filter_color_panel = new JPanel();
        filter_color_panel.setBackground(MainWindow.bg_color);
        filter_color_panel.add(new JLabel("Color: "));
        filter_color_panel.add(filter_color_label);
        filter_color_panel.add(fade_edges);
        filter_color_panel.add(original_color_crys);
        
        JPanel crys_label_panel = new JPanel();
        crys_label_panel.setBackground(MainWindow.bg_color);
        crys_label_panel.add(new JLabel("Crystal Size, Randomness, Edge Size:"));
        
        ((JPanel)components_filters[MainWindow.CRYSTALLIZE]).add(crys_label_panel);
        ((JPanel)components_filters[MainWindow.CRYSTALLIZE]).add(size_slid);
        ((JPanel)components_filters[MainWindow.CRYSTALLIZE]).add(random_slid);
        ((JPanel)components_filters[MainWindow.CRYSTALLIZE]).add(edge_size_slid);
        ((JPanel)components_filters[MainWindow.CRYSTALLIZE]).add(shape_panel);
        ((JPanel)components_filters[MainWindow.CRYSTALLIZE]).add(filter_color_panel);
        
        
        panels[MainWindow.CRYSTALLIZE].add(((JPanel)components_filters[MainWindow.CRYSTALLIZE]));

        panels[MainWindow.CRYSTALLIZE].setBorder(LAFManager.createTitledBorder( "Crystallize"));
        
        
        panels[MainWindow.POINTILLIZE] = new JPanel();
        panels[MainWindow.POINTILLIZE].setBackground(MainWindow.bg_color);
        
        components_filters[MainWindow.POINTILLIZE] = new JPanel();
        ((JPanel)components_filters[MainWindow.POINTILLIZE]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.POINTILLIZE]).setLayout(new GridLayout(6, 1));
        
        final JSlider grid_point_size_slid = new JSlider(JSlider.HORIZONTAL, 1, 80, (int)(((int)(((int)(((int)(((int)(filters_options_vals[MainWindow.POINTILLIZE] % 1000000000.0)) % 100000000.0)) % 1000000.0)) % 10000.0)) % 100.0));
        grid_point_size_slid.setPreferredSize(new Dimension(190, 35));
        if(!MainWindow.useCustomLaf) {
            grid_point_size_slid.setBackground(MainWindow.bg_color);
        }
        grid_point_size_slid.setFocusable(false);
        grid_point_size_slid.setToolTipText("Sets the grid's size.");
        grid_point_size_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(1, new JLabel("1.0"));
        table3.put(40, new JLabel("50.0"));
        table3.put(80, new JLabel("100.0"));
        grid_point_size_slid.setLabelTable(table3);
        
        final JSlider point_randomness_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(((int)(((int)(((int)(filters_options_vals[MainWindow.POINTILLIZE] % 1000000000.0)) % 100000000.0)) % 1000000.0)) % 10000.0)) / 100.0));
        point_randomness_slid.setPreferredSize(new Dimension(95, 35));
        if(!MainWindow.useCustomLaf) {
            point_randomness_slid.setBackground(MainWindow.bg_color);
        }
        point_randomness_slid.setFocusable(false);
        point_randomness_slid.setToolTipText("Sets the point's randomness.");
        point_randomness_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(40, new JLabel("50"));
        table3.put(80, new JLabel("100"));
        point_randomness_slid.setLabelTable(table3);
        
        final JSlider point_fuzziness_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(((int)(((int)(filters_options_vals[MainWindow.POINTILLIZE] % 1000000000.0)) % 100000000.0)) % 1000000.0)) / 10000.0));
        point_fuzziness_slid.setPreferredSize(new Dimension(95, 35));
        if(!MainWindow.useCustomLaf) {
            point_fuzziness_slid.setBackground(MainWindow.bg_color);
        }
        point_fuzziness_slid.setFocusable(false);
        point_fuzziness_slid.setToolTipText("Sets the point's fuzziness.");
        point_fuzziness_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(40, new JLabel("50"));
        table3.put(80, new JLabel("100"));
        point_fuzziness_slid.setLabelTable(table3);
        
        final JSlider point_size_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(((int)(filters_options_vals[MainWindow.POINTILLIZE] % 1000000000.0)) % 100000000.0)) / 1000000.0));
        point_size_slid.setPreferredSize(new Dimension(190, 35));
        if(!MainWindow.useCustomLaf) {
            point_size_slid.setBackground(MainWindow.bg_color);
        }
        point_size_slid.setFocusable(false);
        point_size_slid.setToolTipText("Sets the point's size.");
        point_size_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(40, new JLabel("50"));
        table3.put(80, new JLabel("100"));
        point_size_slid.setLabelTable(table3);
 
        String[] shape_str2 = {"Random", "Squares", "Hexagons", "Octagons & Squares", "Triangles"};

        final JComboBox<String> shape_box2 = new JComboBox<>(shape_str2);
        shape_box2.setSelectedIndex((int)(((int)(filters_options_vals[MainWindow.POINTILLIZE] % 1000000000.0)) / 100000000.0));
        shape_box2.setFocusable(false);
        shape_box2.setToolTipText("Sets the point's shape.");

        JPanel shape_panel2 = new JPanel();
        shape_panel2.setBackground(MainWindow.bg_color);
        shape_panel2.add(new JLabel("Shape: "));
        shape_panel2.add(shape_box2);
        
        final JCheckBox fill = new JCheckBox("Fill");
        fill.setFocusable(false);
        fill.setBackground(MainWindow.bg_color);
        fill.setToolTipText("Fills the point.");
        
        final JCheckBox original_color_point = new JCheckBox("Original");
        original_color_point.setFocusable(false);
        original_color_point.setBackground(MainWindow.bg_color);
        original_color_point.setToolTipText("Uses the original color pixel color.");
        
        fill.addActionListener(e -> original_color_point.setEnabled(!fill.isSelected()));
        
        original_color_point.addActionListener(e -> fill.setEnabled(!original_color_point.isSelected()));
        
        if((int)(filters_options_vals[MainWindow.POINTILLIZE] / 1000000000.0) == 1) {
            fill.setSelected(true);
            original_color_point.setEnabled(false);
        }
        
        if(filters_options_extra_vals[0][MainWindow.POINTILLIZE] == 1) {
            original_color_point.setSelected(true);
            fill.setEnabled(false);
        }
        
        JPanel rand_fuz_panel = new JPanel();
        rand_fuz_panel.setLayout(new GridLayout(1, 2));
        rand_fuz_panel.setBackground(MainWindow.bg_color);
        rand_fuz_panel.add(point_randomness_slid);
        rand_fuz_panel.add(point_fuzziness_slid);
        
        final JLabel filter_color_label2 = new ColorLabel();
        
        filter_color_label2.setPreferredSize(new Dimension(22, 22));
        filter_color_label2.setBackground(filters_colors[MainWindow.POINTILLIZE]);
        filter_color_label2.setToolTipText("Changes the background color.");
        
        filter_color_label2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {           
                new ColorChooserDialog(this_frame, "Pointillize Color", filter_color_label2, -1, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });
        
        JPanel filter_color_panel2 = new JPanel();
        filter_color_panel2.setBackground(MainWindow.bg_color);
        filter_color_panel2.add(new JLabel("Color: "));
        filter_color_panel2.add(filter_color_label2);
        filter_color_panel2.add(fill);
        filter_color_panel2.add(original_color_point);
        
        JPanel pointi_label_panel = new JPanel();
        pointi_label_panel.setBackground(MainWindow.bg_color);
        pointi_label_panel.add(new JLabel("Grid Size, Random-Fuzzy Factors, Point Size:"));
        
        ((JPanel)components_filters[MainWindow.POINTILLIZE]).add(pointi_label_panel);
        ((JPanel)components_filters[MainWindow.POINTILLIZE]).add(grid_point_size_slid);
        ((JPanel)components_filters[MainWindow.POINTILLIZE]).add(rand_fuz_panel);
        ((JPanel)components_filters[MainWindow.POINTILLIZE]).add(point_size_slid);
        ((JPanel)components_filters[MainWindow.POINTILLIZE]).add(shape_panel2);
        ((JPanel)components_filters[MainWindow.POINTILLIZE]).add(filter_color_panel2);

        panels[MainWindow.POINTILLIZE].add(((JPanel)components_filters[MainWindow.POINTILLIZE]));

        panels[MainWindow.POINTILLIZE].setBorder(LAFManager.createTitledBorder( "Pointillize"));
        
        
        panels[MainWindow.OIL] = new JPanel();
        panels[MainWindow.OIL].setBackground(MainWindow.bg_color);
        
        components_filters[MainWindow.OIL] = new JPanel();
        ((JPanel)components_filters[MainWindow.OIL]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.OIL]).setLayout(new GridLayout(4, 1));
        
        final JSlider oil_range_slid = new JSlider(JSlider.HORIZONTAL, 1, 5, (int)(filters_options_vals[MainWindow.OIL] % 100.0));
        oil_range_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            oil_range_slid.setBackground(MainWindow.bg_color);
        }
        oil_range_slid.setFocusable(false);
        oil_range_slid.setToolTipText("Sets the oil's range.");
        oil_range_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(1, new JLabel("1"));
        table3.put(2, new JLabel("2"));
        table3.put(3, new JLabel("3"));
        table3.put(4, new JLabel("4"));
        table3.put(5, new JLabel("5"));
        oil_range_slid.setLabelTable(table3);
        
        final JSlider oil_levels_slid = new JSlider(JSlider.HORIZONTAL, 1, 256, (int)(filters_options_vals[MainWindow.OIL] / 100.0));
        oil_levels_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            oil_levels_slid.setBackground(MainWindow.bg_color);
        }
        oil_levels_slid.setFocusable(false);
        oil_levels_slid.setToolTipText("Sets the oil's levels.");
        oil_levels_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(1, new JLabel("1"));
        table3.put(64, new JLabel("64"));
        table3.put(128, new JLabel("128"));
        table3.put(192, new JLabel("192"));
        table3.put(256, new JLabel("256"));
        oil_levels_slid.setLabelTable(table3);

        JPanel oil_label_panel1 = new JPanel();
        oil_label_panel1.setBackground(MainWindow.bg_color);
        oil_label_panel1.add(new JLabel("Range:"));
        
        JPanel oil_label_panel2 = new JPanel();
        oil_label_panel2.setBackground(MainWindow.bg_color);
        oil_label_panel2.add(new JLabel("Levels:"));
        
        ((JPanel)components_filters[MainWindow.OIL]).add(oil_label_panel1);
        ((JPanel)components_filters[MainWindow.OIL]).add(oil_range_slid);
        ((JPanel)components_filters[MainWindow.OIL]).add(oil_label_panel2);
        ((JPanel)components_filters[MainWindow.OIL]).add(oil_levels_slid);
        
        panels[MainWindow.OIL].add(((JPanel)components_filters[MainWindow.OIL]));

        panels[MainWindow.OIL].setBorder(LAFManager.createTitledBorder( "Oil"));
        
        
        panels[MainWindow.MARBLE] = new JPanel();
        panels[MainWindow.MARBLE].setBackground(MainWindow.bg_color);
        
        components_filters[MainWindow.MARBLE] = new JPanel();
        ((JPanel)components_filters[MainWindow.MARBLE]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.MARBLE]).setLayout(new GridLayout(6, 1));
        
        final JSlider marble_scale_slid = new JSlider(JSlider.HORIZONTAL, 1, 80, (int)(((int)(((int)(((int)(filters_options_vals[MainWindow.MARBLE] % 10000000.0)) % 1000000.0)) % 10000.0)) % 100.0));
        marble_scale_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            marble_scale_slid.setBackground(MainWindow.bg_color);
        }
        marble_scale_slid.setFocusable(false);
        marble_scale_slid.setToolTipText("Sets the marble's scale.");
        marble_scale_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(1, new JLabel("1.0"));
        table3.put(40, new JLabel("150.0"));
        table3.put(80, new JLabel("300.0"));
        marble_scale_slid.setLabelTable(table3);
        
        final JSlider marble_angle_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(((int)(((int)(filters_options_vals[MainWindow.MARBLE] % 10000000.0)) % 1000000.0)) % 10000.0)) / 100.0));
        marble_angle_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            marble_angle_slid.setBackground(MainWindow.bg_color);
        }
        marble_angle_slid.setFocusable(false);
        marble_angle_slid.setToolTipText("Sets the marble's angle.");
        marble_angle_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(20, new JLabel("90"));
        table3.put(40, new JLabel("180"));
        table3.put(60, new JLabel("270"));
        table3.put(80, new JLabel("360"));
        marble_angle_slid.setLabelTable(table3);
        
        final JSlider marble_stretch_slid = new JSlider(JSlider.HORIZONTAL, 1, 80, (int)(((int)(((int)(filters_options_vals[MainWindow.MARBLE] % 10000000.0)) % 1000000.0)) / 10000.0));
        marble_stretch_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            marble_stretch_slid.setBackground(MainWindow.bg_color);
        }
        marble_stretch_slid.setFocusable(false);
        marble_stretch_slid.setToolTipText("Sets the marble's stretch.");
        marble_stretch_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(1, new JLabel("1.0"));
        table3.put(40, new JLabel("25.0"));
        table3.put(80, new JLabel("50.0"));
        marble_stretch_slid.setLabelTable(table3);
        
        final JSlider marble_turbulence_slid = new JSlider(JSlider.HORIZONTAL, 0, 8, (int)(((int)(filters_options_vals[MainWindow.MARBLE] % 10000000.0)) / 1000000.0));
        marble_turbulence_slid.setPreferredSize(new Dimension(100, 35));
        if(!MainWindow.useCustomLaf) {
            marble_turbulence_slid.setBackground(MainWindow.bg_color);
        }
        marble_turbulence_slid.setFocusable(false);
        marble_turbulence_slid.setToolTipText("Sets the marble's turbulence.");
        marble_turbulence_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0.0"));
        table3.put(4, new JLabel("4.0"));
        table3.put(8, new JLabel("8.0"));
        marble_turbulence_slid.setLabelTable(table3);
        
        final JSlider marble_turbulence_factor_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(filters_options_vals[MainWindow.MARBLE] / 10000000.0));
        marble_turbulence_factor_slid.setPreferredSize(new Dimension(100, 35));
        if(!MainWindow.useCustomLaf) {
            marble_turbulence_factor_slid.setBackground(MainWindow.bg_color);
        }
        marble_turbulence_factor_slid.setFocusable(false);
        marble_turbulence_factor_slid.setToolTipText("Sets the marble's turbulence factor.");
        marble_turbulence_factor_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(40, new JLabel("50"));
        table3.put(80, new JLabel("100"));
        marble_turbulence_factor_slid.setLabelTable(table3);
        
        JPanel turb_factor_panel = new JPanel();
        turb_factor_panel.setLayout(new GridLayout(1, 2));
        turb_factor_panel.setBackground(MainWindow.bg_color);
        turb_factor_panel.add(marble_turbulence_slid);
        turb_factor_panel.add(marble_turbulence_factor_slid);
        
        
        JPanel marble_turb_label_panel = new JPanel();
        marble_turb_label_panel.setBackground(MainWindow.bg_color);
        marble_turb_label_panel.add(new JLabel("Turbulence, Turbulence Factor:"));
        
        JPanel marble_label_panel = new JPanel();
        marble_label_panel.setBackground(MainWindow.bg_color);
        marble_label_panel.add(new JLabel("Scale, Angle, Stretch:"));
        
        ((JPanel)components_filters[MainWindow.MARBLE]).add(marble_label_panel );
        ((JPanel)components_filters[MainWindow.MARBLE]).add(marble_scale_slid);
        ((JPanel)components_filters[MainWindow.MARBLE]).add(marble_angle_slid);
        ((JPanel)components_filters[MainWindow.MARBLE]).add(marble_stretch_slid);
        ((JPanel)components_filters[MainWindow.MARBLE]).add(marble_turb_label_panel);
        ((JPanel)components_filters[MainWindow.MARBLE]).add(turb_factor_panel);

        panels[MainWindow.MARBLE].add(((JPanel)components_filters[MainWindow.MARBLE]));

        panels[MainWindow.MARBLE].setBorder(LAFManager.createTitledBorder( "Marble"));
        
        
        panels[MainWindow.WEAVE] = new JPanel();
        panels[MainWindow.WEAVE].setBackground(MainWindow.bg_color);
        
        components_filters[MainWindow.WEAVE] = new JPanel();
        ((JPanel)components_filters[MainWindow.WEAVE]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.WEAVE]).setLayout(new GridLayout(6, 1));
        
        final JSlider weave_x_width = new JSlider(JSlider.HORIZONTAL, 1, 80, (int)(((int)(((int)(((int)(((int)(filters_options_vals[MainWindow.WEAVE] % 1000000000.0)) % 100000000.0)) % 1000000.0)) % 10000.0)) % 100.0));
        weave_x_width.setPreferredSize(new Dimension(100, 35));
        if(!MainWindow.useCustomLaf) {
            weave_x_width.setBackground(MainWindow.bg_color);
        }
        weave_x_width.setFocusable(false);
        weave_x_width.setToolTipText("Sets the weave's x width.");
        weave_x_width.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(1, new JLabel("1"));
        table3.put(40, new JLabel("128"));
        table3.put(80, new JLabel("256"));
        weave_x_width.setLabelTable(table3);
     
        final JSlider weave_y_width = new JSlider(JSlider.HORIZONTAL, 1, 80, (int)(((int)(((int)(((int)(((int)(filters_options_vals[MainWindow.WEAVE] % 1000000000.0)) % 100000000.0)) % 1000000.0)) % 10000.0)) / 100.0));
        weave_y_width.setPreferredSize(new Dimension(100, 35));
        if(!MainWindow.useCustomLaf) {
            weave_y_width.setBackground(MainWindow.bg_color);
        }
        weave_y_width.setFocusable(false);
        weave_y_width.setToolTipText("Sets the weave's y width.");
        weave_y_width.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(1, new JLabel("1"));
        table3.put(40, new JLabel("128"));
        table3.put(80, new JLabel("256"));
        weave_y_width.setLabelTable(table3);
        
        final JSlider weave_x_gap = new JSlider(JSlider.HORIZONTAL, 1, 80, (int)(((int)(((int)(((int)(filters_options_vals[MainWindow.WEAVE] % 1000000000.0)) % 100000000.0)) % 1000000.0)) / 10000.0));
        weave_x_gap.setPreferredSize(new Dimension(100, 35));
        if(!MainWindow.useCustomLaf) {
            weave_x_gap.setBackground(MainWindow.bg_color);
        }
        weave_x_gap.setFocusable(false);
        weave_x_gap.setToolTipText("Sets the weave's x gap.");
        weave_x_gap.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(1, new JLabel("1"));
        table3.put(40, new JLabel("128"));
        table3.put(80, new JLabel("256"));
        weave_x_gap.setLabelTable(table3);
        
        final JSlider weave_y_gap = new JSlider(JSlider.HORIZONTAL, 1, 80, (int)(((int)(((int)(filters_options_vals[MainWindow.WEAVE] % 1000000000.0)) % 100000000.0)) / 1000000.0));
        weave_y_gap.setPreferredSize(new Dimension(100, 35));
        if(!MainWindow.useCustomLaf) {
            weave_y_gap.setBackground(MainWindow.bg_color);
        }
        weave_y_gap.setFocusable(false);
        weave_y_gap.setToolTipText("Sets the weave's y gap.");
        weave_y_gap.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(1, new JLabel("1"));
        table3.put(40, new JLabel("128"));
        table3.put(80, new JLabel("256"));
        weave_y_gap.setLabelTable(table3);
        
        final JCheckBox round_threads = new JCheckBox("Round Threads");
        round_threads.setFocusable(false);
        round_threads.setBackground(MainWindow.bg_color);
        round_threads.setToolTipText("Rounds the threads.");
        
        if((int)(((int)(filters_options_vals[MainWindow.WEAVE] % 1000000000.0)) / 100000000.0) == 1) {
            round_threads.setSelected(true);
        }
        
        final JCheckBox shade_crossings = new JCheckBox("Shade Crossings");
        shade_crossings.setFocusable(false);
        shade_crossings.setBackground(MainWindow.bg_color);
        shade_crossings.setToolTipText("Shades the crossings.");
        
        if((int)(filters_options_vals[MainWindow.WEAVE] / 1000000000.0) == 1) {
            shade_crossings.setSelected(true);
        }
        
        final JLabel filter_color_label3 = new ColorLabel();
        
        filter_color_label3.setPreferredSize(new Dimension(22, 22));
        filter_color_label3.setBackground(filters_colors[MainWindow.WEAVE]);
        filter_color_label3.setToolTipText("Changes the background color.");
        
        filter_color_label3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {           
                new ColorChooserDialog(this_frame, "Weave Color", filter_color_label3, -1, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });
        
        JPanel filter_color_panel3 = new JPanel();
        filter_color_panel3.setBackground(MainWindow.bg_color);
        filter_color_panel3.add(new JLabel("Color: "));
        filter_color_panel3.add(filter_color_label3);
        
        JPanel x_panel = new JPanel();
        x_panel.setLayout(new GridLayout(1, 2));
        x_panel.setBackground(MainWindow.bg_color);
        x_panel.add(weave_x_width);
        x_panel.add(weave_x_gap);
        
        JPanel y_panel = new JPanel();
        y_panel.setLayout(new GridLayout(1, 2));
        y_panel.setBackground(MainWindow.bg_color);
        y_panel.add(weave_y_width);
        y_panel.add(weave_y_gap);
        
        JPanel rt_sc_panel = new JPanel();
        rt_sc_panel.setBackground(MainWindow.bg_color);
        rt_sc_panel.add(round_threads);
        rt_sc_panel.add(shade_crossings);
        
        JPanel x_label_panel = new JPanel();
        x_label_panel.setBackground(MainWindow.bg_color);
        x_label_panel.add(new JLabel("X Width, X Gap:"));
        
        JPanel y_label_panel = new JPanel();
        y_label_panel.setBackground(MainWindow.bg_color);
        y_label_panel.add(new JLabel("Y Width, Y Gap:"));
        
        ((JPanel)components_filters[MainWindow.WEAVE]).add(x_label_panel);
        ((JPanel)components_filters[MainWindow.WEAVE]).add(x_panel);
        ((JPanel)components_filters[MainWindow.WEAVE]).add(y_label_panel);
        ((JPanel)components_filters[MainWindow.WEAVE]).add(y_panel);
        ((JPanel)components_filters[MainWindow.WEAVE]).add(rt_sc_panel);
        ((JPanel)components_filters[MainWindow.WEAVE]).add(filter_color_panel3);
        

        panels[MainWindow.WEAVE].add(((JPanel)components_filters[MainWindow.WEAVE]));

        panels[MainWindow.WEAVE].setBorder(LAFManager.createTitledBorder( "Weave"));
        
        
        panels[MainWindow.SPARKLE] = new JPanel();
        panels[MainWindow.SPARKLE].setBackground(MainWindow.bg_color);
        
        components_filters[MainWindow.SPARKLE] = new JPanel();
        ((JPanel)components_filters[MainWindow.SPARKLE]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.SPARKLE]).setLayout(new GridLayout(6, 1));
        
        final JSlider sparkle_rays_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(((int)(filters_options_vals[MainWindow.SPARKLE] % 1000000.0)) % 10000.0)) % 100.0));
        sparkle_rays_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            sparkle_rays_slid.setBackground(MainWindow.bg_color);
        }
        sparkle_rays_slid.setFocusable(false);
        sparkle_rays_slid.setToolTipText("Sets the sparkle's rays.");
        sparkle_rays_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(40, new JLabel("150"));
        table3.put(80, new JLabel("300"));
        sparkle_rays_slid.setLabelTable(table3);
        
        final JSlider sparkle_radius_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(((int)(filters_options_vals[MainWindow.SPARKLE] % 1000000.0)) % 10000.0)) / 100.0));
        sparkle_radius_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            sparkle_radius_slid.setBackground(MainWindow.bg_color);
        }
        sparkle_radius_slid.setFocusable(false);
        sparkle_radius_slid.setToolTipText("Sets the sparkle's radius.");
        sparkle_radius_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(40, new JLabel("150"));
        table3.put(80, new JLabel("300"));
        sparkle_radius_slid.setLabelTable(table3);
        
        final JSlider sparkle_shine_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(filters_options_vals[MainWindow.SPARKLE] % 1000000.0)) / 10000.0));
        sparkle_shine_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            sparkle_shine_slid.setBackground(MainWindow.bg_color);
        }
        sparkle_shine_slid.setFocusable(false);
        sparkle_shine_slid.setToolTipText("Sets the sparkle's shine.");
        sparkle_shine_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(40, new JLabel("50"));
        table3.put(80, new JLabel("100"));
        sparkle_shine_slid.setLabelTable(table3);
        
        final JSlider sparkle_randomness_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(filters_options_vals[MainWindow.SPARKLE] / 1000000.0));
        sparkle_randomness_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            sparkle_randomness_slid.setBackground(MainWindow.bg_color);
        }
        sparkle_randomness_slid.setFocusable(false);
        sparkle_randomness_slid.setToolTipText("Sets the sparkle's randomness.");
        sparkle_randomness_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(40, new JLabel("25"));
        table3.put(80, new JLabel("50"));
        sparkle_randomness_slid.setLabelTable(table3);
        
        final JLabel filter_color_label4 = new ColorLabel();
                
        filter_color_label4.setPreferredSize(new Dimension(22, 22));
        filter_color_label4.setBackground(filters_colors[MainWindow.SPARKLE]);
        filter_color_label4.setToolTipText("Changes the sparkle's color.");
        
        filter_color_label4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {           
                new ColorChooserDialog(this_frame, "Sparkle Color", filter_color_label4, -1, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });
        
        JPanel filter_color_panel4 = new JPanel();
        filter_color_panel4.setBackground(MainWindow.bg_color);
        filter_color_panel4.add(new JLabel("Color: "));
        filter_color_panel4.add(filter_color_label4);
        
        JPanel sparkle_label_panel = new JPanel();
        sparkle_label_panel.setBackground(MainWindow.bg_color);
        sparkle_label_panel.add(new JLabel("Rays, Radius, Shine, Randomness:"));
        
        ((JPanel)components_filters[MainWindow.SPARKLE]).add(sparkle_label_panel);
        ((JPanel)components_filters[MainWindow.SPARKLE]).add(sparkle_rays_slid);
        ((JPanel)components_filters[MainWindow.SPARKLE]).add(sparkle_radius_slid);
        ((JPanel)components_filters[MainWindow.SPARKLE]).add(sparkle_shine_slid);
        ((JPanel)components_filters[MainWindow.SPARKLE]).add(sparkle_randomness_slid);
        ((JPanel)components_filters[MainWindow.SPARKLE]).add(filter_color_panel4);

        panels[MainWindow.SPARKLE].add(((JPanel)components_filters[MainWindow.SPARKLE]));

        panels[MainWindow.SPARKLE].setBorder(LAFManager.createTitledBorder( "Sparkle"));
        
        
        panels[MainWindow.MIRROR] = new JPanel();
        panels[MainWindow.MIRROR].setBackground(MainWindow.bg_color);
        
        components_filters[MainWindow.MIRROR] = new JPanel();
        ((JPanel)components_filters[MainWindow.MIRROR]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.MIRROR]).setLayout(new GridLayout(6, 1));
        
        final JSlider opacity_slid = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(filters_options_vals[MainWindow.MIRROR] / 1000000.0));
        opacity_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            opacity_slid.setBackground(MainWindow.bg_color);
        }
        opacity_slid.setFocusable(false);
        opacity_slid.setToolTipText("Sets the mirror's opacity.");
        opacity_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(25, new JLabel("25"));
        table3.put(50, new JLabel("50"));
        table3.put(75, new JLabel("75"));
        table3.put(100, new JLabel("100"));
        opacity_slid.setLabelTable(table3);
        
        final JSlider mirrory_slid = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(((int)(filters_options_vals[MainWindow.MIRROR] % 1000000.0)) / 1000.0));
        mirrory_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            mirrory_slid.setBackground(MainWindow.bg_color);
        }
        mirrory_slid.setFocusable(false);
        mirrory_slid.setToolTipText("Sets the mirror's Y offset.");
        mirrory_slid.setPaintLabels(true);

    
        mirrory_slid.setLabelTable(table3);
        
        final JSlider mirror_gap_slid = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(((int)(filters_options_vals[MainWindow.MIRROR] % 1000000.0)) % 1000.0));
        mirror_gap_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            mirror_gap_slid.setBackground(MainWindow.bg_color);
        }
        mirror_gap_slid.setFocusable(false);
        mirror_gap_slid.setToolTipText("Sets the mirror's gap.");
        mirror_gap_slid.setPaintLabels(true);
        
        mirror_gap_slid.setLabelTable(table3);

        JPanel mirror_label_panel1 = new JPanel();
        mirror_label_panel1.setBackground(MainWindow.bg_color);
        mirror_label_panel1.add(new JLabel("Opacity:"));
        
        JPanel mirror_label_panel2 = new JPanel();
        mirror_label_panel2.setBackground(MainWindow.bg_color);
        mirror_label_panel2.add(new JLabel("Mirror Y:"));
        
        JPanel mirror_label_panel3 = new JPanel();
        mirror_label_panel3.setBackground(MainWindow.bg_color);
        mirror_label_panel3.add(new JLabel("Gap:"));
        
        ((JPanel)components_filters[MainWindow.MIRROR]).add(mirror_label_panel1);
        ((JPanel)components_filters[MainWindow.MIRROR]).add(opacity_slid);
        ((JPanel)components_filters[MainWindow.MIRROR]).add(mirror_label_panel2);
        ((JPanel)components_filters[MainWindow.MIRROR]).add(mirrory_slid);
        ((JPanel)components_filters[MainWindow.MIRROR]).add(mirror_label_panel3);
        ((JPanel)components_filters[MainWindow.MIRROR]).add(mirror_gap_slid);
        
        panels[MainWindow.MIRROR].add(((JPanel)components_filters[MainWindow.MIRROR]));

        panels[MainWindow.MIRROR].setBorder(LAFManager.createTitledBorder( "Mirror"));


        panels[MainWindow.QUAD_TREE_COMPRESSION] = new JPanel();
        panels[MainWindow.QUAD_TREE_COMPRESSION].setBackground(MainWindow.bg_color);

        components_filters[MainWindow.QUAD_TREE_COMPRESSION] = new JPanel();
        ((JPanel)components_filters[MainWindow.QUAD_TREE_COMPRESSION]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.QUAD_TREE_COMPRESSION]).setLayout(new GridLayout(7, 1));

        JPanel quad_tree_threshold_panel = new JPanel();
        quad_tree_threshold_panel.setBackground(MainWindow.bg_color);

        JTextField quad_tree_threshold = new JTextField(4);
        quad_tree_threshold.setText("" + fs.quadtree_threshold);

        quad_tree_threshold_panel.add(new JLabel("Threshold: "));
        quad_tree_threshold_panel.add(quad_tree_threshold);

        JCheckBox dynamicThreshold = new JCheckBox("Dynamic");
        dynamicThreshold.setBackground(MainWindow.bg_color);
        dynamicThreshold.setFocusable(false);
        dynamicThreshold.setSelected(fs.quadtree_dynamic_threshold);
        dynamicThreshold.setToolTipText("The threshold is dynamic depending on the area size.");

        quad_tree_threshold_panel.add(dynamicThreshold);


        JPanel quad_tree_threshold_panel2 = new JPanel();
        quad_tree_threshold_panel2.setBackground(MainWindow.bg_color);

        final JComboBox<String> dynamicThreshodCurve = new JComboBox<>(Constants.FadeAlgs);
        dynamicThreshodCurve.setSelectedIndex(fs.quadtree_dynamic_threshold_curve);
        dynamicThreshodCurve.setFocusable(false);
        dynamicThreshodCurve.setToolTipText("Sets the dynamic threshold curve algorithm.");

        quad_tree_threshold_panel2.add(new JLabel("Dynamic Curve: "));
        quad_tree_threshold_panel2.add(dynamicThreshodCurve);


        JPanel quad_tree_threshold_panel3 = new JPanel();
        quad_tree_threshold_panel3.setBackground(MainWindow.bg_color);

        final JComboBox<String> thresholdCalculation = new JComboBox<>(new String[]{"Average", "NTSC Luma", "HDTV Luma", "HDR Luma"});
        thresholdCalculation.setSelectedIndex(fs.quadtree_threshold_calculation);
        thresholdCalculation.setFocusable(false);
        thresholdCalculation.setToolTipText("Sets the combine calculation algorithm.");

        quad_tree_threshold_panel3.add(new JLabel("Combine Calculation: "));
        quad_tree_threshold_panel3.add(thresholdCalculation);


        JCheckBox quadtree_showDivision = new JCheckBox("Show Division");
        quadtree_showDivision.setBackground(MainWindow.bg_color);
        quadtree_showDivision.setToolTipText("Displays the borders of each division step.");
        quadtree_showDivision.setSelected(fs.quadtree_show_division);


        String[] tree_alg_str = {"Quad", "Binary H", "Binary V", "Binary HV", "Binary VH"};

        final JComboBox<String> treeAlgorithm = new JComboBox<>(tree_alg_str);
        treeAlgorithm.setSelectedIndex(fs.quadtree_algorithm);
        treeAlgorithm.setFocusable(false);
        treeAlgorithm.setToolTipText("Sets the tree algorithm.");

        JCheckBox quadtree_merge = new JCheckBox("Merge");
        quadtree_merge.setBackground(MainWindow.bg_color);
        quadtree_merge.setToolTipText("Merges same quad-tree nodes on the same level.");
        quadtree_merge.setSelected(fs.quadtree_merge_nodes);

        quadtree_merge.setEnabled(treeAlgorithm.getSelectedIndex() == 0);

        treeAlgorithm.addActionListener( e -> {
            quadtree_merge.setEnabled(treeAlgorithm.getSelectedIndex() == 0);
        });

        String[] fill_alg_str = {"Original Color", "Level Color", "Fill Color"};

        final JComboBox<String> fillAlgorithm = new JComboBox<>(fill_alg_str);
        fillAlgorithm.setSelectedIndex(fs.quadtree_fill_algorithm);
        fillAlgorithm.setFocusable(false);
        fillAlgorithm.setToolTipText("Sets the fill algorithm.");

        JPanel treeAlgPanel = new JPanel();
        treeAlgPanel.setBackground(MainWindow.bg_color);
        treeAlgPanel.add(new JLabel("Tree Algorithm: "));
        treeAlgPanel.add(treeAlgorithm);
        treeAlgPanel.add(quadtree_merge);

        JPanel drawAlgPanel = new JPanel();
        drawAlgPanel.setBackground(MainWindow.bg_color);
        drawAlgPanel.add(quadtree_showDivision);
        drawAlgPanel.add(new JLabel(" Fill: "));
        drawAlgPanel.add(fillAlgorithm);

        final JSlider quadtree_lod = new JSlider(JSlider.HORIZONTAL, 0, QuadTree.MAX_LEVEL, fs.quadtree_lod);
        quadtree_lod.setPreferredSize(new Dimension(150, 35));
        if(!MainWindow.useCustomLaf) {
            quadtree_lod.setBackground(MainWindow.bg_color);
        }
        quadtree_lod.setFocusable(false);
        quadtree_lod.setToolTipText("Sets the edge detection sensitivity.");
        quadtree_lod.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("Min"));
        table3.put(QuadTree.MAX_LEVEL, new JLabel("Max"));
        quadtree_lod.setLabelTable(table3);

        JPanel quadtree_lod_panel = new JPanel();
        quadtree_lod_panel.setBackground(MainWindow.bg_color);
        JLabel lod_label = new JLabel("Detail: " + String.format("%02d", fs.quadtree_lod) + " ");
        quadtree_lod_panel.add(lod_label);
        quadtree_lod_panel.add(quadtree_lod);

        quadtree_lod.addChangeListener(e -> lod_label.setText("Detail: " + String.format("%02d", quadtree_lod.getValue()) + " "));

        JPanel quadtree_colors_panel = new JPanel();
        quadtree_colors_panel.setBackground(MainWindow.bg_color);

        final JLabel quadTreeFillColor = new ColorLabel();

        quadTreeFillColor.setPreferredSize(new Dimension(22, 22));
        quadTreeFillColor.setBackground(filters_colors[MainWindow.QUAD_TREE_COMPRESSION]);
        quadTreeFillColor.setToolTipText("Changes the fill color.");

        quadTreeFillColor.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                new ColorChooserDialog(this_frame, "Fill Color", quadTreeFillColor, -1, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        final JLabel quadTreeDivisionColor = new ColorLabel();

        quadTreeDivisionColor.setPreferredSize(new Dimension(22, 22));
        quadTreeDivisionColor.setBackground(filters_extra_colors[0][MainWindow.QUAD_TREE_COMPRESSION]);
        quadTreeDivisionColor.setToolTipText("Changes the division color.");

        quadTreeDivisionColor.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                new ColorChooserDialog(this_frame, "Division Color", quadTreeDivisionColor, -1, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        quadtree_colors_panel.add(new JLabel("Fill Color: "));
        quadtree_colors_panel.add(quadTreeFillColor);
        quadtree_colors_panel.add(new JLabel(" Division Color: "));
        quadtree_colors_panel.add(quadTreeDivisionColor);


        ((JPanel)components_filters[MainWindow.QUAD_TREE_COMPRESSION]).add(quad_tree_threshold_panel);
        ((JPanel)components_filters[MainWindow.QUAD_TREE_COMPRESSION]).add(quad_tree_threshold_panel2);
        ((JPanel)components_filters[MainWindow.QUAD_TREE_COMPRESSION]).add(quad_tree_threshold_panel3);
        ((JPanel)components_filters[MainWindow.QUAD_TREE_COMPRESSION]).add(treeAlgPanel);
        ((JPanel)components_filters[MainWindow.QUAD_TREE_COMPRESSION]).add(quadtree_lod_panel);
        ((JPanel)components_filters[MainWindow.QUAD_TREE_COMPRESSION]).add(drawAlgPanel);
        ((JPanel)components_filters[MainWindow.QUAD_TREE_COMPRESSION]).add(quadtree_colors_panel);


        panels[MainWindow.QUAD_TREE_COMPRESSION].add(((JPanel)components_filters[MainWindow.QUAD_TREE_COMPRESSION]));
        panels[MainWindow.QUAD_TREE_COMPRESSION].setBorder(LAFManager.createTitledBorder( "Quad-Tree"));
        
        panels[MainWindow.GLOW] = new JPanel();
        panels[MainWindow.GLOW].setBackground(MainWindow.bg_color);
        
        components_filters[MainWindow.GLOW] = new JPanel();
        ((JPanel)components_filters[MainWindow.GLOW]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.GLOW]).setLayout(new GridLayout(4, 1));
        
        final JSlider glow_softness_slid = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(filters_options_vals[MainWindow.GLOW] / 1000.0));
        glow_softness_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            glow_softness_slid.setBackground(MainWindow.bg_color);
        }
        glow_softness_slid.setFocusable(false);
        glow_softness_slid.setToolTipText("Sets the glow softness.");
        glow_softness_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(25, new JLabel("25"));
        table3.put(50, new JLabel("50"));
        table3.put(75, new JLabel("75"));
        table3.put(100, new JLabel("100"));
        glow_softness_slid.setLabelTable(table3);
        
        final JSlider glow_amount_slid = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(filters_options_vals[MainWindow.GLOW] % 1000.0));
        glow_amount_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            glow_amount_slid.setBackground(MainWindow.bg_color);
        }
        glow_amount_slid.setFocusable(false);
        glow_amount_slid.setToolTipText("Sets the glow amount.");
        glow_amount_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(25, new JLabel("25"));
        table3.put(50, new JLabel("50"));
        table3.put(75, new JLabel("75"));
        table3.put(100, new JLabel("100"));
        glow_amount_slid.setLabelTable(table3);

        JPanel softness_panel = new JPanel();
        softness_panel.setBackground(MainWindow.bg_color);
        softness_panel.add(new JLabel("Softness:"));
        
        JPanel amount_panel = new JPanel();
        amount_panel.setBackground(MainWindow.bg_color);
        amount_panel.add(new JLabel("Amount:"));
        
        ((JPanel)components_filters[MainWindow.GLOW]).add(softness_panel);
        ((JPanel)components_filters[MainWindow.GLOW]).add(glow_softness_slid);
        ((JPanel)components_filters[MainWindow.GLOW]).add(amount_panel);
        ((JPanel)components_filters[MainWindow.GLOW]).add(glow_amount_slid);
        
        panels[MainWindow.GLOW].add(((JPanel)components_filters[MainWindow.GLOW]));

        panels[MainWindow.GLOW].setBorder(LAFManager.createTitledBorder( "Glow"));
        
        
        panels[MainWindow.COLOR_CHANNEL_SCALING] = new JPanel();
        panels[MainWindow.COLOR_CHANNEL_SCALING].setBackground(MainWindow.bg_color);

        components_filters[MainWindow.COLOR_CHANNEL_SCALING] = new JSlider(JSlider.HORIZONTAL, 0, 100, filters_options_vals[MainWindow.COLOR_CHANNEL_SCALING]);
        ((JSlider)components_filters[MainWindow.COLOR_CHANNEL_SCALING]).setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            ((JSlider) components_filters[MainWindow.COLOR_CHANNEL_SCALING]).setBackground(MainWindow.bg_color);
        }
        ((JSlider)components_filters[MainWindow.COLOR_CHANNEL_SCALING]).setToolTipText("Sets the color channel scaling.");
        ((JSlider)components_filters[MainWindow.COLOR_CHANNEL_SCALING]).setFocusable(false);

        ((JSlider)components_filters[MainWindow.COLOR_CHANNEL_SCALING]).setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0.0"));
        table3.put(20, new JLabel("1.0"));
        table3.put(40, new JLabel("2.0"));
        table3.put(60, new JLabel("3.0"));
        table3.put(80, new JLabel("4.0"));
        table3.put(100, new JLabel("5.0"));
        ((JSlider)components_filters[MainWindow.COLOR_CHANNEL_SCALING]).setLabelTable(table3);

        panels[MainWindow.COLOR_CHANNEL_SCALING].add(((JSlider)components_filters[MainWindow.COLOR_CHANNEL_SCALING]));

        panels[MainWindow.COLOR_CHANNEL_SCALING].setBorder(LAFManager.createTitledBorder( "Color Channel Scaling"));
        
        
        panels[MainWindow.NOISE] = new JPanel();
        panels[MainWindow.NOISE].setBackground(MainWindow.bg_color);
        
        components_filters[MainWindow.NOISE] = new JPanel();
        ((JPanel)components_filters[MainWindow.NOISE]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.NOISE]).setLayout(new GridLayout(5, 1));
        
        
        final JSlider noise_amount_slid = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(((int)(((int)(filters_options_vals[MainWindow.NOISE] % 10000000.0)) % 1000000.0)) % 1000.0));
        noise_amount_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            noise_amount_slid.setBackground(MainWindow.bg_color);
        }
        noise_amount_slid.setFocusable(false);
        noise_amount_slid.setToolTipText("Sets the noise amount.");
        noise_amount_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(25, new JLabel("25"));
        table3.put(50, new JLabel("50"));
        table3.put(75, new JLabel("75"));
        table3.put(100, new JLabel("100"));
        noise_amount_slid.setLabelTable(table3);
        
        final JSlider noise_density_slid = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(((int)(((int)(filters_options_vals[MainWindow.NOISE] % 10000000.0)) % 1000000.0)) / 1000.0));
        noise_density_slid.setPreferredSize(new Dimension(200, 35));
        if(!MainWindow.useCustomLaf) {
            noise_density_slid.setBackground(MainWindow.bg_color);
        }
        noise_density_slid.setFocusable(false);
        noise_density_slid.setToolTipText("Sets the noise density.");
        noise_density_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(25, new JLabel("25"));
        table3.put(50, new JLabel("50"));
        table3.put(75, new JLabel("75"));
        table3.put(100, new JLabel("100"));
        noise_density_slid.setLabelTable(table3);
        
        String[] distri_str = {"Gaussian", "Uniform"};

        final JComboBox<String> distri_box = new JComboBox<>(distri_str);
        distri_box.setSelectedIndex((int)(((int)(filters_options_vals[MainWindow.NOISE] % 10000000.0)) / 1000000.0));
        distri_box.setFocusable(false);
        distri_box.setToolTipText("Sets the noise distribution.");

        JPanel distri_panel = new JPanel();
        distri_panel.setBackground(MainWindow.bg_color);
        distri_panel.add(new JLabel("Distribution: "));
        distri_panel.add(distri_box);
        
        final JCheckBox monochrome = new JCheckBox("Monochrome");
        monochrome.setFocusable(false);
        monochrome.setBackground(MainWindow.bg_color);
        monochrome.setToolTipText("Sets the monochrome algorithm.");
        
        JPanel noise_panel2 = new JPanel();
        noise_panel2.setBackground(MainWindow.bg_color);
        noise_panel2.add(monochrome);
        
        if((int)(filters_options_vals[MainWindow.NOISE] / 10000000.0) == 1) {
            monochrome.setSelected(true);
        }
        
        JPanel noise_panel1 = new JPanel();
        noise_panel1.setBackground(MainWindow.bg_color);
        noise_panel1.add(new JLabel("Amount, Density:"));
        
        ((JPanel)components_filters[MainWindow.NOISE]).add(noise_panel1);
        ((JPanel)components_filters[MainWindow.NOISE]).add(noise_amount_slid);
        ((JPanel)components_filters[MainWindow.NOISE]).add(noise_density_slid);
        ((JPanel)components_filters[MainWindow.NOISE]).add(distri_panel);
        ((JPanel)components_filters[MainWindow.NOISE]).add(noise_panel2);

        
        panels[MainWindow.NOISE].add(((JPanel)components_filters[MainWindow.NOISE]));

        panels[MainWindow.NOISE].setBorder(LAFManager.createTitledBorder( "Noise"));
        
        
        panels[MainWindow.COLOR_CHANNEL_MIXING] = new JPanel();
        panels[MainWindow.COLOR_CHANNEL_MIXING].setBackground(MainWindow.bg_color);

        components_filters[MainWindow.COLOR_CHANNEL_MIXING] = new JPanel();
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_MIXING]).setBackground(MainWindow.bg_color);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_MIXING]).setLayout(new GridLayout(3, 1));

        final SliderGradient blue_green_slid = new SliderGradient(JSlider.HORIZONTAL, 0, 255, (int)(((int)(filters_options_vals[MainWindow.COLOR_CHANNEL_MIXING] % 1000000.0)) % 1000.0));
        blue_green_slid.setPreferredSize(new Dimension(80, 22));
        if(!MainWindow.useCustomLaf) {
            blue_green_slid.setBackground(MainWindow.bg_color);
        }
        blue_green_slid.setBackground(MainWindow.bg_color);
        blue_green_slid.setFocusable(false);
        blue_green_slid.setToolTipText("Sets the percentage of blue and green.");
       
        
        //blue_green_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(128, new JLabel("128"));
        table3.put(255, new JLabel("255"));
        blue_green_slid.setLabelTable(table3);
        
        final SliderGradient to_red_slid = new SliderGradient(JSlider.HORIZONTAL, 0, 255, filters_colors[MainWindow.COLOR_CHANNEL_MIXING].getRed());
        to_red_slid.setPreferredSize(new Dimension(80, 22));
        if(!MainWindow.useCustomLaf) {
            to_red_slid.setBackground(MainWindow.bg_color);
        }
        to_red_slid.setBackground(MainWindow.bg_color);
        to_red_slid.setFocusable(false);
        to_red_slid.setToolTipText("Sets the percentage into red.");
       
        
        //to_red_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(128, new JLabel("128"));
        table3.put(255, new JLabel("255"));
        to_red_slid.setLabelTable(table3);
        
        JPanel panel_red = new JPanel();

        panel_red.setBackground(MainWindow.bg_color);
        JLabel b_label3 = new JLabel("B");
        b_label3.setForeground(Color.blue);
        JLabel g_label3 = new JLabel("G");
        g_label3.setForeground(Color.green);
        JLabel r_label3 = new JLabel("R");
        r_label3.setForeground(Color.red);
        panel_red.add(b_label3);
        panel_red.add(blue_green_slid);
        panel_red.add(g_label3);
        panel_red.add(new JLabel("into"));   
        panel_red.add(to_red_slid);
        panel_red.add(r_label3);
        
        final SliderGradient red_blue_slid = new SliderGradient(JSlider.HORIZONTAL, 0, 255, (int)(((int)(filters_options_vals[MainWindow.COLOR_CHANNEL_MIXING] % 1000000.0)) / 1000.0));
        red_blue_slid.setPreferredSize(new Dimension(80, 22));
        if(!MainWindow.useCustomLaf) {
            red_blue_slid.setBackground(MainWindow.bg_color);
        }
        red_blue_slid.setBackground(MainWindow.bg_color);
        red_blue_slid.setFocusable(false);
        red_blue_slid.setToolTipText("Sets the percentage of red and blue.");
       
        
        //red_blue_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(128, new JLabel("128"));
        table3.put(255, new JLabel("255"));
        red_blue_slid.setLabelTable(table3);
        
        final SliderGradient to_green_slid = new SliderGradient(JSlider.HORIZONTAL, 0, 255, filters_colors[MainWindow.COLOR_CHANNEL_MIXING].getGreen());
        to_green_slid.setPreferredSize(new Dimension(80, 22));
        if(!MainWindow.useCustomLaf) {
            to_green_slid.setBackground(MainWindow.bg_color);
        }
        to_green_slid.setBackground(MainWindow.bg_color);
        to_green_slid.setFocusable(false);
        to_green_slid.setToolTipText("Sets the percentage into green.");
        
        JPanel panel_green = new JPanel();

        panel_green.setBackground(MainWindow.bg_color);
        JLabel b_label4 = new JLabel("B");
        b_label4.setForeground(Color.blue);
        JLabel g_label4 = new JLabel("G");
        g_label4.setForeground(Color.green);
        JLabel r_label4 = new JLabel("R");
        r_label4.setForeground(Color.red);
        panel_green.add(r_label4);
        panel_green.add(red_blue_slid);
        panel_green.add(b_label4);
        panel_green.add(new JLabel("into"));     
        panel_green.add(to_green_slid);
        panel_green.add(g_label4);
        
        
        final SliderGradient green_red_slid = new SliderGradient(JSlider.HORIZONTAL, 0, 255, (int)(filters_options_vals[MainWindow.COLOR_CHANNEL_MIXING] / 1000000.0));
        green_red_slid.setPreferredSize(new Dimension(80, 22));
        if(!MainWindow.useCustomLaf) {
            green_red_slid.setBackground(MainWindow.bg_color);
        }
        green_red_slid.setBackground(MainWindow.bg_color);
        green_red_slid.setFocusable(false);
        green_red_slid.setToolTipText("Sets the percentage of green and red.");
       
        
        //green_red_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(128, new JLabel("128"));
        table3.put(255, new JLabel("255"));
        green_red_slid.setLabelTable(table3);
        
        final SliderGradient to_blue_slid = new SliderGradient(JSlider.HORIZONTAL, 0, 255, filters_colors[MainWindow.COLOR_CHANNEL_MIXING].getBlue());
        to_blue_slid.setPreferredSize(new Dimension(80, 22));
        if(!MainWindow.useCustomLaf) {
            to_blue_slid.setBackground(MainWindow.bg_color);
        }
        to_blue_slid.setBackground(MainWindow.bg_color);
        to_blue_slid.setFocusable(false);
        to_blue_slid.setToolTipText("Sets the percentage into blue.");
        
        //to_blue_slid.setPaintLabels(true);
        
        JPanel panel_blue = new JPanel();

        panel_blue.setBackground(MainWindow.bg_color);
        JLabel b_label5 = new JLabel("B");
        b_label5.setForeground(Color.blue);
        JLabel g_label5 = new JLabel("G");
        g_label5.setForeground(Color.green);
        JLabel r_label5 = new JLabel("R");
        r_label5.setForeground(Color.red);
        panel_blue.add(g_label5);
        panel_blue.add(green_red_slid);
        panel_blue.add(r_label5);
        panel_blue.add(new JLabel("into"));    
        panel_blue.add(to_blue_slid);
        panel_blue.add(b_label5);

        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_MIXING]).add(panel_red);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_MIXING]).add(panel_green);
        ((JPanel)components_filters[MainWindow.COLOR_CHANNEL_MIXING]).add(panel_blue);


        panels[MainWindow.COLOR_CHANNEL_MIXING].add(((JPanel)components_filters[MainWindow.COLOR_CHANNEL_MIXING]));

        panels[MainWindow.COLOR_CHANNEL_MIXING].setBorder(LAFManager.createTitledBorder( "Color Channel Mixing"));
        
        panels[MainWindow.LIGHT_EFFECTS] = new JPanel();
        panels[MainWindow.LIGHT_EFFECTS].setLayout(new GridLayout(1, 1));
        panels[MainWindow.LIGHT_EFFECTS].setBackground(MainWindow.bg_color);

        components_filters[MainWindow.LIGHT_EFFECTS] = new JPanel();
        ((JPanel)components_filters[MainWindow.LIGHT_EFFECTS]).setLayout(new GridLayout(1, 3));
        ((JPanel)components_filters[MainWindow.LIGHT_EFFECTS]).setBackground(MainWindow.bg_color);
        
        JPanel light_panel = new JPanel();
        light_panel.setBackground(MainWindow.bg_color);
        light_panel.setBorder(LAFManager.createTitledBorder( "Light"));
        light_panel.setLayout(new GridLayout(10, 1));
                
        JPanel material_panel = new JPanel();
        material_panel.setBackground(MainWindow.bg_color);
        material_panel.setBorder(LAFManager.createTitledBorder( "Material"));
        material_panel.setLayout(new GridLayout(5, 1));     
        
        JPanel bumps_panel = new JPanel();
        bumps_panel.setBackground(MainWindow.bg_color);
        bumps_panel.setBorder(LAFManager.createTitledBorder( "Bumps"));
        bumps_panel.setLayout(new GridLayout(7, 1));
        
        String[] light_str = {"Point Light", "Distant Light", "Spotlight"};

        final JComboBox<String> light_box = new JComboBox<>(light_str);
        light_box.setSelectedIndex((int)(filters_options_vals[MainWindow.LIGHT_EFFECTS] / 100000000.0));
        light_box.setFocusable(false);
        light_box.setToolTipText("Sets the type of the light.");
        
        JPanel type_panel = new JPanel();
        type_panel.setBackground(MainWindow.bg_color);
        type_panel.add(new JLabel("Type: "));
        type_panel.add(light_box);
        
        light_panel.add(type_panel);
        
        JPanel dir_panel = new JPanel();
        dir_panel.setBackground(MainWindow.bg_color);
        dir_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        dir_panel.add(new JLabel("Direction:"));
        
        final JSlider light_direction_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(filters_options_vals[MainWindow.LIGHT_EFFECTS] % 100000000.0)) / 1000000.0));
        light_direction_slid.setPreferredSize(new Dimension(240, 35));
        if(!MainWindow.useCustomLaf) {
            light_direction_slid.setBackground(MainWindow.bg_color);
        }
        light_direction_slid.setFocusable(false);
        light_direction_slid.setToolTipText("Sets the light's direction.");
        light_direction_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(20, new JLabel("90"));
        table3.put(40, new JLabel("180"));
        table3.put(60, new JLabel("270"));
        table3.put(80, new JLabel("360"));
        light_direction_slid.setLabelTable(table3);
        
        dir_panel.add(light_direction_slid);
                    
        light_panel.add(dir_panel);
        
        JPanel elev_panel = new JPanel();
        elev_panel.setBackground(MainWindow.bg_color);
        elev_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        elev_panel.add(new JLabel("Elevation:"));
        
        final JSlider light_elevation_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(((int)(filters_options_vals[MainWindow.LIGHT_EFFECTS] % 100000000.0)) % 1000000.0)) / 10000.0));
        light_elevation_slid.setPreferredSize(new Dimension(240, 35));
        if(!MainWindow.useCustomLaf) {
            light_elevation_slid.setBackground(MainWindow.bg_color);
        }
        light_elevation_slid.setFocusable(false);
        light_elevation_slid.setToolTipText("Sets the light's elevation.");
        light_elevation_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(40, new JLabel("45"));
        table3.put(80, new JLabel("90"));
        light_elevation_slid.setLabelTable(table3);
        
        elev_panel.add(light_elevation_slid);
        
        light_panel.add(elev_panel);
        
        
        JPanel dis_panel = new JPanel();
        dis_panel.setBackground(MainWindow.bg_color);
        dis_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        dis_panel.add(new JLabel("Distance:"));
        
        final JSlider light_distance_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(((int)(((int)(filters_options_vals[MainWindow.LIGHT_EFFECTS] % 100000000.0)) % 1000000.0)) % 10000.0)) / 100));
        light_distance_slid.setPreferredSize(new Dimension(240, 35));
        if(!MainWindow.useCustomLaf) {
            light_distance_slid.setBackground(MainWindow.bg_color);
        }
        light_distance_slid.setFocusable(false);
        light_distance_slid.setToolTipText("Sets the light's distance.");
        light_distance_slid.setPaintLabels(true);
        
        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0.0"));
        table3.put(40, new JLabel("500.0"));
        table3.put(80, new JLabel("1000.0"));
        light_distance_slid.setLabelTable(table3);
        
        dis_panel.add(light_distance_slid);
        
        light_panel.add(dis_panel);
        
        JPanel inte_panel = new JPanel();
        inte_panel.setBackground(MainWindow.bg_color);
        inte_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        inte_panel.add(new JLabel("Intensity:"));
        
        final JSlider light_intensity_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(((int)(((int)(filters_options_vals[MainWindow.LIGHT_EFFECTS] % 100000000.0)) % 1000000.0)) % 10000.0)) % 100));
        light_intensity_slid.setPreferredSize(new Dimension(240, 35));
        if(!MainWindow.useCustomLaf) {
            light_intensity_slid.setBackground(MainWindow.bg_color);
        }
        light_intensity_slid.setFocusable(false);
        light_intensity_slid.setToolTipText("Sets the light's intensity.");
        light_intensity_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0.00"));
        table3.put(20, new JLabel("0.25"));
        table3.put(40, new JLabel("0.50"));
        table3.put(60, new JLabel("0.75"));
        table3.put(80, new JLabel("1.00"));
        light_intensity_slid.setLabelTable(table3);
        
        inte_panel.add(light_intensity_slid);
        light_panel.add(inte_panel);
        
        JPanel light_x_panel = new JPanel();
        light_x_panel.setBackground(MainWindow.bg_color);
        light_x_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        light_x_panel.add(new JLabel("X:"));
        
        final JSlider light_x_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(filters_options_extra_vals[0][MainWindow.LIGHT_EFFECTS] / 1000000.0));
        light_x_slid.setPreferredSize(new Dimension(240, 35));
        if(!MainWindow.useCustomLaf) {
            light_x_slid.setBackground(MainWindow.bg_color);
        }
        light_x_slid.setFocusable(false);
        light_x_slid.setToolTipText("Sets the light's x position.");
        light_x_slid.setPaintLabels(true);
        
        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(20, new JLabel("25"));
        table3.put(40, new JLabel("50"));
        table3.put(60, new JLabel("75"));
        table3.put(80, new JLabel("100"));
        light_x_slid.setLabelTable(table3);
        
        light_x_panel.add(light_x_slid);
        light_panel.add(light_x_panel);
        
        JPanel light_y_panel = new JPanel();
        light_y_panel.setBackground(MainWindow.bg_color);
        light_y_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        light_y_panel.add(new JLabel("Y:"));
        
        final JSlider light_y_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(filters_options_extra_vals[0][MainWindow.LIGHT_EFFECTS] % 1000000.0)) / 10000.0));
        light_y_slid.setPreferredSize(new Dimension(240, 35));
        if(!MainWindow.useCustomLaf) {
            light_y_slid.setBackground(MainWindow.bg_color);
        }
        light_y_slid.setFocusable(false);
        light_y_slid.setToolTipText("Sets the light's y position.");
        light_y_slid.setPaintLabels(true);
        
        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(20, new JLabel("25"));
        table3.put(40, new JLabel("50"));
        table3.put(60, new JLabel("75"));
        table3.put(80, new JLabel("100"));
        light_y_slid.setLabelTable(table3);
        
        light_y_panel.add(light_y_slid);
        light_panel.add(light_y_panel);
        
        JPanel light_cone_angle_panel = new JPanel();
        light_cone_angle_panel.setBackground(MainWindow.bg_color);
        light_cone_angle_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        light_cone_angle_panel.add(new JLabel("Cone Angle:"));
        
        final JSlider light_cone_angle_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(((int)(filters_options_extra_vals[0][MainWindow.LIGHT_EFFECTS] % 1000000.0)) % 10000.0)) / 100.0));
        light_cone_angle_slid.setPreferredSize(new Dimension(240, 35));
        if(!MainWindow.useCustomLaf) {
            light_cone_angle_slid.setBackground(MainWindow.bg_color);
        }
        light_cone_angle_slid.setFocusable(false);
        light_cone_angle_slid.setToolTipText("Sets the light's cone angle.");
        light_cone_angle_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(40, new JLabel("45"));
        table3.put(80, new JLabel("90"));
        light_cone_angle_slid.setLabelTable(table3);
        
        light_cone_angle_panel.add(light_cone_angle_slid);
        light_panel.add(light_cone_angle_panel);
        
        JPanel light_focus_panel = new JPanel();
        light_focus_panel.setBackground(MainWindow.bg_color);
        light_focus_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        light_focus_panel.add(new JLabel("Focus:"));
        
        final JSlider light_focus_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(((int)(filters_options_extra_vals[0][MainWindow.LIGHT_EFFECTS] % 1000000.0)) % 10000.0)) % 100.0));
        light_focus_slid.setPreferredSize(new Dimension(240, 35));
        if(!MainWindow.useCustomLaf) {
            light_focus_slid.setBackground(MainWindow.bg_color);
        }
        light_focus_slid.setFocusable(false);
        light_focus_slid.setToolTipText("Sets the light's focus.");
        light_focus_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0.00"));
        table3.put(20, new JLabel("0.25"));
        table3.put(40, new JLabel("0.50"));
        table3.put(60, new JLabel("0.75"));
        table3.put(80, new JLabel("1.00"));
        light_focus_slid.setLabelTable(table3);
        
        light_focus_panel.add(light_focus_slid);
        light_panel.add(light_focus_panel);
        
        if(light_box.getSelectedIndex() == 0) {
            light_cone_angle_slid.setEnabled(false);
            light_focus_slid.setEnabled(false);
        }
        else if(light_box.getSelectedIndex() == 1) {
            light_cone_angle_slid.setEnabled(false);
            light_focus_slid.setEnabled(false);
            light_x_slid.setEnabled(false);
            light_y_slid.setEnabled(false);
        }
        
        light_box.addActionListener(e -> {
            if(light_box.getSelectedIndex() == 0) {
                light_cone_angle_slid.setEnabled(false);
                light_focus_slid.setEnabled(false);
                light_x_slid.setEnabled(true);
                light_y_slid.setEnabled(true);
            }
            else if(light_box.getSelectedIndex() == 1) {
                light_cone_angle_slid.setEnabled(false);
                light_focus_slid.setEnabled(false);
                light_x_slid.setEnabled(false);
                light_y_slid.setEnabled(false);
            }
            else {
                light_cone_angle_slid.setEnabled(true);
                light_focus_slid.setEnabled(true);
                light_x_slid.setEnabled(true);
                light_y_slid.setEnabled(true);
            }
        });
        
        final JLabel filter_color_label6 = new ColorLabel();
                
        filter_color_label6.setPreferredSize(new Dimension(22, 22));
        filter_color_label6.setBackground(filters_colors[MainWindow.LIGHT_EFFECTS]);
        filter_color_label6.setToolTipText("Changes the light's color.");
        
        filter_color_label6.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {           
                new ColorChooserDialog(this_frame, "Light Color", filter_color_label6, -1, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });
        
        JPanel filter_color_panel6 = new JPanel();
        filter_color_panel6.setBackground(MainWindow.bg_color);
        filter_color_panel6.add(new JLabel("Color: "));
        filter_color_panel6.add(filter_color_label6);
        
        light_panel.add(filter_color_panel6);

        ((JPanel)components_filters[MainWindow.LIGHT_EFFECTS]).add(light_panel);
        ((JPanel)components_filters[MainWindow.LIGHT_EFFECTS]).add(material_panel);
        ((JPanel)components_filters[MainWindow.LIGHT_EFFECTS]).add(bumps_panel);
        panels[MainWindow.LIGHT_EFFECTS].add(((JPanel)components_filters[MainWindow.LIGHT_EFFECTS]));      

        JPanel material_colors = new JPanel();
        material_colors.setBackground(MainWindow.bg_color);
        material_colors.setLayout(new GridLayout(3, 2));
        material_colors.add(new JLabel("Colors from:"));
        material_colors.add(new JLabel(""));
        
        final JRadioButton source_image = new JRadioButton("Source Image");
        source_image.setFocusable(false);
        source_image.setToolTipText("Sets the color source from the image.");
        source_image.setBackground(MainWindow.bg_color);
        
        final JRadioButton constant_color = new JRadioButton("Constant Color:");
        constant_color.setFocusable(false);
        source_image.setToolTipText("Sets a constant color source.");
        constant_color.setBackground(MainWindow.bg_color);
        
        ButtonGroup button_group = new ButtonGroup();
        button_group.add(source_image);
        button_group.add(constant_color);
        
        if((int)(filters_options_extra_vals[1][MainWindow.LIGHT_EFFECTS] / 10000000.0) == 0) {
            source_image.setSelected(true);
        }
        else {
            constant_color.setSelected(true);
        }
        
        material_colors.add(source_image);
        material_colors.add(new JLabel(""));
        
        material_colors.add(constant_color);
        
        final JLabel filter_color_label7 = new ColorLabel();
                
        filter_color_label7.setPreferredSize(new Dimension(22, 22));
        filter_color_label7.setBackground(filters_extra_colors[0][MainWindow.LIGHT_EFFECTS]);
        filter_color_label7.setToolTipText("Changes the material's diffuse color.");
        
        filter_color_label7.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {           
                new ColorChooserDialog(this_frame, "Diffuse Color", filter_color_label7, -1, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });
        
        JPanel filter_color_panel7 = new JPanel();
        filter_color_panel7.setBackground(MainWindow.bg_color);
        filter_color_panel7.add(new JLabel("Diffuse: "));
        filter_color_panel7.add(filter_color_label7);
        
        final JLabel filter_color_label8 = new ColorLabel();
                
        filter_color_label8.setPreferredSize(new Dimension(22, 22));
        filter_color_label8.setBackground(filters_extra_colors[1][MainWindow.LIGHT_EFFECTS]);
        filter_color_label8.setToolTipText("Changes the material's specular color.");
        
        filter_color_label8.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {           
                new ColorChooserDialog(this_frame, "Specular Color", filter_color_label8, -1, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });
        
        JPanel filter_color_panel8 = new JPanel();
        filter_color_panel8.setBackground(MainWindow.bg_color);
        filter_color_panel8.add(new JLabel("Specular Color: "));
        filter_color_panel8.add(filter_color_label8);
        
        material_colors.add(filter_color_panel7);
        
        material_panel.add(material_colors);
        
        JPanel material_shininess_panel = new JPanel();
        material_shininess_panel.setBackground(MainWindow.bg_color);
        material_shininess_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        material_shininess_panel.add(new JLabel("Shininess:"));
        
        final JSlider material_shininess_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(filters_options_extra_vals[1][MainWindow.LIGHT_EFFECTS] % 10000000.0)) / 100000.0));
        material_shininess_slid.setPreferredSize(new Dimension(240, 35));
        if(!MainWindow.useCustomLaf) {
            material_shininess_slid.setBackground(MainWindow.bg_color);
        }
        material_shininess_slid.setFocusable(false);
        material_shininess_slid.setToolTipText("Sets the material's shininess.");
        material_shininess_slid.setPaintLabels(true);
        
        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0.0"));
        table3.put(20, new JLabel("2.5"));
        table3.put(40, new JLabel("5.0"));
        table3.put(60, new JLabel("7.5"));
        table3.put(80, new JLabel("10.0"));
        material_shininess_slid.setLabelTable(table3);
        
        material_shininess_panel.add(material_shininess_slid);
        
        material_panel.add(material_shininess_panel);
        material_panel.add(filter_color_panel8);
        
        
        material_panel.add(new JLabel());
  
        String[] bump_str = {"Normal", "Outer", "Inner", "Pillow", "Up", "Down"};

        final JComboBox<String> bump_box = new JComboBox<>(bump_str);
        bump_box.setSelectedIndex((int)(((int)(((int)(filters_options_extra_vals[1][MainWindow.LIGHT_EFFECTS] % 10000000.0)) % 100000.0)) / 10000.0));
        bump_box.setFocusable(false);
        bump_box.setToolTipText("Sets the bump's shape.");
        
        JPanel bump_shape_panel = new JPanel();
        bump_shape_panel.setBackground(MainWindow.bg_color);
        bump_shape_panel.add(new JLabel("Shape: "));
        bump_shape_panel.add(bump_box);
        
        bumps_panel.add(bump_shape_panel);
        
        JPanel bump_height_panel = new JPanel();
        bump_height_panel.setBackground(MainWindow.bg_color);
        bump_height_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bump_height_panel.add(new JLabel("Height:"));
        
        final JSlider bump_height_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(((int)(((int)(filters_options_extra_vals[1][MainWindow.LIGHT_EFFECTS] % 10000000.0)) % 100000.0)) % 10000.0)) / 100.0));
        bump_height_slid.setPreferredSize(new Dimension(240, 35));
        if(!MainWindow.useCustomLaf) {
            bump_height_slid.setBackground(MainWindow.bg_color);
        }
        bump_height_slid.setFocusable(false);
        bump_height_slid.setToolTipText("Sets the bump's height.");
        bump_height_slid.setPaintLabels(true);

        table3 = new Hashtable<>();
        table3.put(0, new JLabel("-5.0"));
        table3.put(20, new JLabel("-2.5"));
        table3.put(40, new JLabel("0.0"));
        table3.put(60, new JLabel("2.5"));
        table3.put(80, new JLabel("5.0"));
        bump_height_slid.setLabelTable(table3);
        
        bump_height_panel.add(bump_height_slid);
        bumps_panel.add(bump_height_panel);
        
        JPanel bump_softness_panel = new JPanel();
        bump_softness_panel.setBackground(MainWindow.bg_color);
        bump_softness_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bump_softness_panel.add(new JLabel("Softness:"));
        
        final JSlider bump_softness_slid = new JSlider(JSlider.HORIZONTAL, 0, 80, (int)(((int)(((int)(((int)(filters_options_extra_vals[1][MainWindow.LIGHT_EFFECTS] % 10000000.0)) % 100000.0)) % 10000.0)) % 100.0));
        bump_softness_slid.setPreferredSize(new Dimension(240, 35));
        if(!MainWindow.useCustomLaf) {
            bump_softness_slid.setBackground(MainWindow.bg_color);
        }
        bump_softness_slid.setFocusable(false);
        bump_softness_slid.setToolTipText("Sets the bump's softness.");
        bump_softness_slid.setPaintLabels(true);
        
        table3 = new Hashtable<>();
        table3.put(0, new JLabel("0"));
        table3.put(20, new JLabel("25"));
        table3.put(40, new JLabel("50"));
        table3.put(60, new JLabel("75"));
        table3.put(80, new JLabel("100"));
        bump_softness_slid.setLabelTable(table3);
        
        bump_softness_panel.add(bump_softness_slid);
        bumps_panel.add(bump_softness_panel);
        bumps_panel.add(new JLabel());
        bumps_panel.add(new JLabel());
        bumps_panel.add(new JLabel());
        bumps_panel.add(new JLabel());
        
        panel_details.setLayout(new GridLayout(2, 4));
        
        panel_details.add(panels[MainWindow.ANTIALIASING]);
        panel_details.add(panels[MainWindow.EDGE_DETECTION]);
        panel_details.add(panels[MainWindow.EDGE_DETECTION2]);
        panel_details.add(panels[MainWindow.SHARPNESS]);
        panel_details.add(panels[MainWindow.BLURRING]);
        panel_details.add(panels[MainWindow.EMBOSS]);
        panel_details.add(panels[MainWindow.GLOW]);
        panel_details.add(panels[MainWindow.NOISE]);
        
        panel_colors.setLayout(new GridLayout(4, 4));

        
        panel_colors.add(panels[MainWindow.HISTOGRAM_EQUALIZATION]);
        panel_colors.add(panels[MainWindow.POSTERIZE]);
        panel_colors.add(panels[MainWindow.CONTRAST_BRIGHTNESS]);
        panel_colors.add(panels[MainWindow.GAIN]);
        panel_colors.add(panels[MainWindow.GAMMA]);
        panel_colors.add(panels[MainWindow.EXPOSURE]);
        panel_colors.add(panels[MainWindow.COLOR_TEMPERATURE]);
        panel_colors.add(panels[MainWindow.INVERT_COLORS]);
        panel_colors.add(panels[MainWindow.COLOR_CHANNEL_MASKING]);
        panel_colors.add(panels[MainWindow.COLOR_CHANNEL_SWAPPING]);
        panel_colors.add(panels[MainWindow.COLOR_CHANNEL_SWIZZLING]);
        panel_colors.add(panels[MainWindow.COLOR_CHANNEL_MIXING]);
        panel_colors.add(panels[MainWindow.COLOR_CHANNEL_ADJUSTING]);
        panel_colors.add(panels[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING]);
        panel_colors.add(panels[MainWindow.COLOR_CHANNEL_SCALING]);  
        panel_colors.add(panels[MainWindow.GRAYSCALE]);
        
        
        panel_texture.setLayout(new GridLayout(2, 4));
        panel_texture.add(panels[MainWindow.DITHER]);
        panel_texture.add(panels[MainWindow.CRYSTALLIZE]);
        panel_texture.add(panels[MainWindow.POINTILLIZE]);
        panel_texture.add(panels[MainWindow.OIL]);
        panel_texture.add(panels[MainWindow.MARBLE]);
        panel_texture.add(panels[MainWindow.WEAVE]);
        panel_texture.add(panels[MainWindow.SPARKLE]);
        panel_texture.add(panels[MainWindow.MIRROR]);

        panel_texture2.setLayout(new GridLayout(1, 3));
        panel_texture2.add(panels[MainWindow.QUAD_TREE_COMPRESSION]);
        panel_texture2.add(new JLabel(""));
        panel_texture2.add(new JLabel(""));
        
        panel_lighting.setLayout(new GridLayout(1, 1));
        panel_lighting.add(panels[MainWindow.LIGHT_EFFECTS]);
        
        panel.add(tabbedPane);

        JPanel buttons = new JPanel();
        buttons.setBackground(MainWindow.bg_color);

        JButton ok = new MyButton("Ok");
        getRootPane().setDefaultButton(ok);
        ok.setFocusable(false);
        ok.addActionListener(e -> {

//            if(aaMethod.getSelectedIndex() != 0) {
//                int cspace = antialiasing_color_space.getSelectedIndex();
//                if(cspace == 4 || cspace == 5 || cspace == 6) {
//                    JOptionPane.showMessageDialog(ptra, "The color spaces HSB, HSL, LCH can only be used with Mean AA Method.", "Error!", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//            }
            double temp, temp3, temp4, temp5;

            try {
                temp = Double.parseDouble(sigmaR.getText());
                temp3 = Double.parseDouble(sigmaR2.getText());
                temp4 = Double.parseDouble(sigmaS2.getText());
                temp5 = Double.parseDouble(quad_tree_threshold.getText());
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(temp < 0 || temp3 < 0 || temp4 < 0) {
                JOptionPane.showMessageDialog(ptra, "The sigma values must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(temp5 < 0) {
                JOptionPane.showMessageDialog(ptra, "The quad tree threshold must be greater or equal to 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            fs.aaSigmaR = temp;
            fs.bluringSigmaS = temp4;
            fs.bluringSigmaR = temp3;
            fs.blurringKernelSelection = kernels_size_opt.getSelectedIndex();

            fs.quadtree_show_division = quadtree_showDivision.isSelected();
            fs.quadtree_algorithm = treeAlgorithm.getSelectedIndex();
            fs.quadtree_merge_nodes = quadtree_merge.isSelected();
            fs.quadtree_fill_algorithm = fillAlgorithm.getSelectedIndex();
            fs.quadtree_lod = quadtree_lod.getValue();
            fs.quadtree_threshold = temp5;
            fs.quadtree_dynamic_threshold = dynamicThreshold.isSelected();
            fs.quadtree_dynamic_threshold_curve = dynamicThreshodCurve.getSelectedIndex();
            fs.quadtree_threshold_calculation = thresholdCalculation.getSelectedIndex();


            boolean useJitterOld = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
            int aaMethodOld = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
            int aaSamplesIndexOld = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
            int old_supersampling_num = TaskRender.getExtraSamples(aaSamplesIndexOld, aaMethodOld);

            for(int k = 0; k < filters_options_vals.length; k++) {
                if(components_filters[k] != null) {
                    if(k == MainWindow.COLOR_TEMPERATURE || k == MainWindow.EXPOSURE || k == MainWindow.GAMMA || k == MainWindow.COLOR_CHANNEL_SCALING) {
                        filters_options_vals[k] = ((JSlider)components_filters[k]).getValue();
                    }
                    else if(k == MainWindow.CONTRAST_BRIGHTNESS) {
                        filters_options_vals[k] = contrast_slid.getValue() * 1000 + brightness_slid.getValue();
                    }
                    else if(k == MainWindow.GAIN) {
                        filters_options_vals[k] = gain_slid.getValue() * 1000 + bias_slid.getValue();
                    }
                    else if(k == MainWindow.COLOR_CHANNEL_ADJUSTING) {
                        filters_options_vals[k] = slid1.getValue() * 1000000 + slid2.getValue() * 1000 + slid3.getValue();
                    }
                    else if(k == MainWindow.COLOR_CHANNEL_HSB_ADJUSTING) {
                        filters_options_vals[k] = slid4.getValue() * 1000000 + slid5.getValue() * 1000 + slid6.getValue();
                    }
                    else if(k == MainWindow.DITHER) {
                        int db = diffusion_dither_box.isSelected() ? 1 : 0;
                        int sb = serpentine_box.isSelected() ? 1 : 0;
                        filters_options_vals[k] = sb * 100000 + db * 10000 + dither_alg.getSelectedIndex() * 1000 + dither_slid.getValue();
                    }
                    else if(k == MainWindow.BLURRING) {
                        filters_options_vals[k] = blurring_radius.getSelectedIndex() * 1000 + radius_slid.getValue();
                    }
                    else if(k == MainWindow.POSTERIZE) {
                        filters_options_vals[k] = modes.getSelectedIndex() * 1000 + posterize_slid.getValue();
                    }
                    else if(k == MainWindow.COLOR_CHANNEL_SWIZZLING) {

                        filters_options_vals[k] = 0;
                        for(int j = 0; j < swizzle_checks.length; j++) {
                            if(swizzle_checks[j].isSelected()) {
                                filters_options_vals[k] = filters_options_vals[k] | (0x1 << j);
                            }
                        }
                    }
                    else if(k == MainWindow.CRYSTALLIZE) {
                        int orig = original_color_crys.isSelected() ? 1 : 0 ;
                        int fe = fade_edges.isSelected() ? 1 : 0;
                        filters_options_vals[k] = fe * 10000000 + shape_box.getSelectedIndex() * 1000000 + edge_size_slid.getValue() * 10000 + random_slid.getValue() * 100 + size_slid.getValue();
                        filters_colors[k] = filter_color_label.getBackground();
                        filters_options_extra_vals[0][k] = orig;
                    }
                    else if(k == MainWindow.POINTILLIZE) {
                        int fl = fill.isSelected() ? 1 : 0;
                        int orig = original_color_point.isSelected() ? 1 : 0 ;
                        filters_options_vals[k] = fl * 1000000000 + shape_box2.getSelectedIndex() * 100000000 + point_size_slid.getValue() * 1000000 + point_fuzziness_slid.getValue() * 10000 + point_randomness_slid.getValue() * 100 + grid_point_size_slid.getValue();
                        filters_colors[k] = filter_color_label2.getBackground();
                        filters_options_extra_vals[0][k] = orig;
                    }
                    else if(k == MainWindow.QUAD_TREE_COMPRESSION) {
                        filters_colors[k] = quadTreeFillColor.getBackground();
                        filters_extra_colors[0][k] = quadTreeDivisionColor.getBackground();
                    }
                    else if(k == MainWindow.GLOW) {
                        filters_options_vals[k] = glow_softness_slid.getValue() * 1000 + glow_amount_slid.getValue();
                    }
                    else if(k == MainWindow.MARBLE) {
                        filters_options_vals[k] = marble_turbulence_factor_slid.getValue() * 10000000 + marble_turbulence_slid.getValue() * 1000000 + marble_stretch_slid.getValue() * 10000 + marble_angle_slid.getValue() * 100 + marble_scale_slid.getValue();
                    }
                    else if(k == MainWindow.WEAVE) {
                        int rt = round_threads.isSelected() ? 1 : 0;
                        int sc = shade_crossings.isSelected() ? 1 : 0;

                        filters_options_vals[k] = sc * 1000000000 + rt * 100000000 + weave_y_gap.getValue() * 1000000 + weave_x_gap.getValue() * 10000 + weave_y_width.getValue() * 100 + weave_x_width.getValue();
                        filters_colors[k] = filter_color_label3.getBackground();
                    }
                    else if(k == MainWindow.SPARKLE) {
                        filters_options_vals[k] = sparkle_randomness_slid.getValue() * 1000000 + sparkle_shine_slid.getValue() * 10000 + sparkle_radius_slid.getValue() * 100 + sparkle_rays_slid.getValue();
                        filters_colors[k] = filter_color_label4.getBackground();
                    }
                    else if(k == MainWindow.OIL) {
                        filters_options_vals[k] = oil_levels_slid.getValue() * 100 + oil_range_slid.getValue();
                    }
                    else if(k == MainWindow.EDGE_DETECTION) {
                        filters_options_vals[k] = (10 - edge_sensitivity_slid.getValue()) * 100 + edge_alg.getSelectedIndex();
                        filters_colors[k] = filter_color_label5.getBackground();
                    }
                    else if(k == MainWindow.NOISE) {
                        int mc = monochrome.isSelected() ? 1 : 0;
                        filters_options_vals[k] = mc * 10000000 + distri_box.getSelectedIndex() * 1000000 + noise_density_slid.getValue() * 1000 + noise_amount_slid.getValue();
                    }
                    else if(k == MainWindow.EMBOSS) {
                        filters_options_vals[k] = emboss_bumpheight_slid.getValue() * 100000 + emboss_elevation_slid.getValue() * 1000 + emboss_angle_slid.getValue() * 10 + emboss_algorithm.getSelectedIndex();
                    }
                    else if(k == MainWindow.COLOR_CHANNEL_MIXING) {
                        filters_options_vals[k] = green_red_slid.getValue() * 1000000 + red_blue_slid.getValue() * 1000 + blue_green_slid.getValue();
                        filters_colors[k] = new Color(to_red_slid.getValue(), to_green_slid.getValue(), to_blue_slid.getValue());
                    }
                    else if(k == MainWindow.LIGHT_EFFECTS) {
                        filters_options_vals[k] = light_box.getSelectedIndex() * 100000000 + light_direction_slid.getValue() * 1000000 + light_elevation_slid.getValue() * 10000 + light_distance_slid.getValue() * 100 + light_intensity_slid.getValue();
                        filters_options_extra_vals[0][k] = light_x_slid.getValue() * 1000000 + light_y_slid.getValue() * 10000 + light_cone_angle_slid.getValue() * 100 + light_focus_slid.getValue();
                        int source = source_image.isSelected() ? 0 : 1;
                        filters_options_extra_vals[1][k] = source * 10000000 + material_shininess_slid.getValue() * 100000 + bump_box.getSelectedIndex() * 10000 + bump_height_slid.getValue() * 100 + bump_softness_slid.getValue();
                        filters_colors[k] = filter_color_label6.getBackground();
                        filters_extra_colors[0][k] = filter_color_label7.getBackground();
                        filters_extra_colors[1][k] = filter_color_label8.getBackground();
                    }
                    else if(k == MainWindow.MIRROR) {
                        filters_options_vals[k] = 1000000 * opacity_slid.getValue() + 1000 * mirrory_slid.getValue() + mirror_gap_slid.getValue();
                    }
                    else if(k == MainWindow.EDGE_DETECTION2) {
                        filters_options_vals[k] = horizontal_edge_alg.getSelectedIndex() * 10 + vertical_edge_alg.getSelectedIndex();
                    }
                    else if(k == MainWindow.ANTIALIASING) {
                        int flag = 0;

                        if(aaAvgWithMean.isSelected()) {
                            flag |= 1;
                        }

                        filters_options_extra_vals[0][k] = antialiasing_color_space.getSelectedIndex();

                        if(useJitter.isSelected()) {
                            flag |= (1 << 2);
                        }

                        flag *= 100;

                        filters_options_vals[k] = flag + aaMethod.getSelectedIndex() * 10 + aaSamples.getSelectedIndex();
                    }
                    else if ( components_filters[k] instanceof JComboBox){
                        filters_options_vals[k] = ((JComboBox)components_filters[k]).getSelectedIndex();
                    }
                }
            }

            boolean useJitterNew = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
            int aaSamplesIndexNew = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
            int aaMethodNew = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
            int new_supersampling_num = TaskRender.getExtraSamples(aaSamplesIndexNew, aaMethodNew);

            dispose();

            ptra2.filtersOptionsChanged(filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, order_panel.getFilterOrder(), mActiveFilters, (aaSamplesIndexNew != aaSamplesIndexOld) || (old_supersampling_num != new_supersampling_num) || (aaMethodOld == 5 && aaMethodNew != 5) || (aaMethodOld != 5 && aaMethodNew == 5), useJitterOld != useJitterNew);

            tab_index = tabbedPane.getSelectedIndex();

        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Ok");
        getRootPane().getActionMap().put("Ok", new AbstractAction()
        {

            public void actionPerformed(ActionEvent e)
            {
                ok.doClick();
            }
        });

        buttons.add(ok);

        JButton cancel = new MyButton("Cancel");
        cancel.setFocusable(false);
        cancel.addActionListener(e -> {

            tab_index = tabbedPane.getSelectedIndex();

            dispose();

        });

        buttons.add(cancel);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
        getRootPane().getActionMap().put("Cancel", new AbstractAction()
        {

            public void actionPerformed(ActionEvent e)
            {
                cancel.doClick();
            }
        });
        
        JButton reset = new MyButton("Reset");
        reset.setFocusable(false);
        reset.addActionListener(e -> {

            ptra2.filtersOptionsChanged(null, null, null, null, null, null, true, true);

            tab_index = tabbedPane.getSelectedIndex();

            dispose();

        });

        buttons.add(reset);

        RoundedPanel round_panel = LAFManager.createRoundedPanel();
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(1170, 610));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(panel, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 1;

        round_panel.add(buttons, con);

        JPanel main_panel = new JPanel();
        main_panel.setLayout(new GridBagLayout());
        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;
        main_panel.add(round_panel, con);

        JScrollPane scrollPane = new JScrollPane(main_panel);
        add(scrollPane);

        requestFocus();

        setVisible(true);

        repaint();
        
    }
   
}
