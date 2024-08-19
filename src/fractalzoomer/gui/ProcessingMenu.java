
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 *
 * @author hrkalona2
 */
public class ProcessingMenu extends MyMenu {
	private static final long serialVersionUID = 880061806683700045L;
	private MainWindow ptr;
    private JMenuItem light_opt;

    private JMenuItem slope_opt;
    private JMenuItem bump_map_opt;
    private JMenuItem rainbow_palette_opt;
    private JMenuItem fake_de_opt;
    private JMenuItem entropy_coloring_opt;
    private JMenuItem offset_coloring_opt;
    private JMenuItem greyscale_coloring_opt;
    private JMenuItem smoothing_opt;
    private JMenuItem exterior_de_opt;
    private JMenuItem orbit_traps_opt;
    private JMenuItem contour_coloring_opt;
    private JMenuItem statistics_coloring_opt;
    private JMenuItem histogram_coloring_opt;

    private JMenuItem numerical_distance_estimator_opt;
    private JMenuItem order_opt;
    
    public ProcessingMenu(MainWindow ptr2, String name) {
        super(name);

        this.ptr = ptr2;
        
        setIcon(MainWindow.getIcon("processing.png"));
        
        smoothing_opt = new MyMenuItem("Smoothing", MainWindow.getIcon("smoothing.png"));
        exterior_de_opt = new MyMenuItem("Distance Estimation", MainWindow.getIcon("distance_estimation.png"));
        bump_map_opt = new MyMenuItem("Bump Mapping", MainWindow.getIcon("bump_map.png"));
        fake_de_opt = new MyMenuItem("Fake Distance Estimation", MainWindow.getIcon("fake_distance_estimation.png"));
        entropy_coloring_opt = new MyMenuItem("Entropy Coloring", MainWindow.getIcon("entropy_coloring.png"));
        offset_coloring_opt = new MyMenuItem("Offset Coloring", MainWindow.getIcon("offset_coloring.png"));
        greyscale_coloring_opt = new MyMenuItem("Greyscale Coloring", MainWindow.getIcon("greyscale_coloring.png"));
        rainbow_palette_opt = new MyMenuItem("Rainbow Palette", MainWindow.getIcon("rainbow_palette.png"));
        orbit_traps_opt = new MyMenuItem("Orbit Traps", MainWindow.getIcon("orbit_traps.png"));
        contour_coloring_opt = new MyMenuItem("Contour Coloring", MainWindow.getIcon("contour_coloring.png"));
        light_opt = new MyMenuItem("Light", MainWindow.getIcon("light.png"));
        order_opt = new MyMenuItem("Processing Order", MainWindow.getIcon("list.png"));
        statistics_coloring_opt = new MyMenuItem("Statistical Coloring", MainWindow.getIcon("statistics_coloring.png"));
        histogram_coloring_opt = new MyMenuItem("Histogram Coloring", MainWindow.getIcon("histogram.png"));
        slope_opt = new MyMenuItem("Slopes", MainWindow.getIcon("slopes.png"));
        numerical_distance_estimator_opt = new MyMenuItem("Numerical Distance Estimator", MainWindow.getIcon("numerical_dem.png"));

        smoothing_opt.setToolTipText("Smooths the image's color transitions.");
        exterior_de_opt.setToolTipText("<html>Sets some points near the boundary of<br>the set to the maximum iterations value.</html>");
        bump_map_opt.setToolTipText("Emulates a light source to create a pseudo 3d image.");
        fake_de_opt.setToolTipText("Emulates the effect of distance estimation.");
        entropy_coloring_opt.setToolTipText("Calculates the entropy of nearby points to create a coloring effect.");
        offset_coloring_opt.setToolTipText("Adds a palette offset, into areas with smooth iteration gradients.");
        greyscale_coloring_opt.setToolTipText("Converts the areas with smooth iteration gradients, to greyscale.");
        rainbow_palette_opt.setToolTipText("Creates a pseudo 3d image, by applying the palette colors as a rainbow.");
        orbit_traps_opt.setToolTipText("Applies a coloring effect when an orbit gets trapped under a specific condition.");
        contour_coloring_opt.setToolTipText("Blends the contours that are created by the fractional part of the iterations.");
        order_opt.setToolTipText("Changes the application order of the processing algorithms.");
        light_opt.setToolTipText("Emulates a light source to create a pseudo 3d image.");
        statistics_coloring_opt.setToolTipText("Enables the use of statistical coloring algorithms, that work along with out-coloring modes.");
        histogram_coloring_opt.setToolTipText("Calculates the histogram of the iterations and re-scales the palette accordingly.");
        slope_opt.setToolTipText("Emulates a light source to create a pseudo 3d image.");
        numerical_distance_estimator_opt.setToolTipText("Calculates the distance estimator using numerical methods.");

        smoothing_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0));
        exterior_de_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0));
        bump_map_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0));
        fake_de_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0));
        entropy_coloring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0));
        offset_coloring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0));
        greyscale_coloring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0));
        rainbow_palette_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
        orbit_traps_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0));
        contour_coloring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0));
        light_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0));
        order_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_9, 0));
        statistics_coloring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0));
        histogram_coloring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.SHIFT_MASK));
        slope_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.SHIFT_MASK | ActionEvent.CTRL_MASK));
        numerical_distance_estimator_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.SHIFT_MASK | ActionEvent.CTRL_MASK));

        contour_coloring_opt.addActionListener(e -> ptr.setContourColoring());
        
        smoothing_opt.addActionListener(e -> ptr.setSmoothing());

        exterior_de_opt.addActionListener(e -> ptr.setExteriorDistanceEstimation());

        bump_map_opt.addActionListener(e -> ptr.setBumpMap());

        rainbow_palette_opt.addActionListener(e -> ptr.setRainbowPalette());

        fake_de_opt.addActionListener(e -> ptr.setFakeDistanceEstimation());
        
        greyscale_coloring_opt.addActionListener(e -> ptr.setGreyScaleColoring());

        entropy_coloring_opt.addActionListener(e -> ptr.setEntropyColoring());

        offset_coloring_opt.addActionListener(e -> ptr.setOffsetColoring());
        
        orbit_traps_opt.addActionListener(e -> ptr.setOrbitTraps());
        
        light_opt.addActionListener(e -> ptr.setLighting());

        slope_opt.addActionListener(e -> ptr.setSlopes());
        
        order_opt.addActionListener(e -> ptr.setProcessingOrder());
        
        statistics_coloring_opt.addActionListener(e -> ptr.openStatisticsColoringFrame());
        
        histogram_coloring_opt.addActionListener(e -> ptr.setHistogramColoring());

        numerical_distance_estimator_opt.addActionListener(e -> ptr.setNumericalDistanceEstimator());
        
        add(smoothing_opt);
        add(statistics_coloring_opt);
        add(histogram_coloring_opt);
        add(exterior_de_opt);
        add(fake_de_opt);
        add(numerical_distance_estimator_opt);
        add(entropy_coloring_opt);
        add(offset_coloring_opt);
        add(rainbow_palette_opt);
        add(orbit_traps_opt);
        add(greyscale_coloring_opt);
        add(contour_coloring_opt);
        add(bump_map_opt);
        add(light_opt);
        add(slope_opt);
        addSeparator();
        add(order_opt);
    }
    
    public JMenuItem getEntropyColoring() {
        
        return entropy_coloring_opt;
        
    }
    
    public JMenuItem getOffsetColoring() {
        
        return offset_coloring_opt;
        
    }
    
    public JMenuItem getGreyScaleColoring() {
        
        return greyscale_coloring_opt;
        
    }
    
    public JMenuItem getRainbowPalette() {
        
        return rainbow_palette_opt;
        
    }
    
    public JMenuItem getBumpMap() {
        
        return bump_map_opt;
        
    }
    
    public JMenuItem getLight() {
        
        return light_opt;
        
    }

    public JMenuItem getSlopes() {

        return slope_opt;

    }
    
    public JMenuItem getFakeDistanceEstimation() {
        
        return fake_de_opt;
        
    }

    public JMenuItem getNumericalDistanceEstimator() {

        return numerical_distance_estimator_opt;

    }
    
    public JMenuItem getDistanceEstimation() {
        
        return exterior_de_opt;
        
    }
    
    public JMenuItem getSmoothing() {
        
        return smoothing_opt;
        
    }
    
    public JMenuItem getOrbitTraps() {
        
        return orbit_traps_opt;
        
    }
    
    public JMenuItem getContourColoring() {
        
        return contour_coloring_opt;
        
    }
    
    public JMenuItem getHistogramColoring() {
        
        return histogram_coloring_opt;
        
    }
    
    public JMenuItem getProcessingOrder() {
        
        return order_opt;
        
    }
    
    public void updateIcons(Settings s) {
        
        if(s.hasSmoothing()) {
            smoothing_opt.setIcon(MainWindow.getIcon("smoothing_enabled.png"));
        }
        else {
            smoothing_opt.setIcon(MainWindow.getIcon("smoothing.png"));
        }
        
        if(s.pps.hss.histogramColoring) {
            histogram_coloring_opt.setIcon(MainWindow.getIcon("histogram_enabled.png"));
        }
        else {
            histogram_coloring_opt.setIcon(MainWindow.getIcon("histogram.png"));
        }
        
        if(s.pps.bms.bump_map) {
            bump_map_opt.setIcon(MainWindow.getIcon("bump_map_enabled.png"));
        }
        else {
            bump_map_opt.setIcon(MainWindow.getIcon("bump_map.png"));
        }
        
        if(s.pps.rps.rainbow_palette) {
            rainbow_palette_opt.setIcon(MainWindow.getIcon("rainbow_palette_enabled.png"));
        }
        else {
            rainbow_palette_opt.setIcon(MainWindow.getIcon("rainbow_palette.png"));
        }
        
        if(s.pps.ots.useTraps) {
            orbit_traps_opt.setIcon(MainWindow.getIcon("orbit_traps_enabled.png"));
        }
        else {
            orbit_traps_opt.setIcon(MainWindow.getIcon("orbit_traps.png"));
        }
        
        if(s.pps.cns.contour_coloring) {
            contour_coloring_opt.setIcon(MainWindow.getIcon("contour_coloring_enabled.png"));
        }
        else {
            contour_coloring_opt.setIcon(MainWindow.getIcon("contour_coloring.png"));
        }
        
        if(s.pps.gss.greyscale_coloring) {
            greyscale_coloring_opt.setIcon(MainWindow.getIcon("greyscale_coloring_enabled.png"));
        }
        else {
            greyscale_coloring_opt.setIcon(MainWindow.getIcon("greyscale_coloring.png"));
        }
        
        if(s.pps.ofs.offset_coloring) {
            offset_coloring_opt.setIcon(MainWindow.getIcon("offset_coloring_enabled.png"));
        }
        else {
            offset_coloring_opt.setIcon(MainWindow.getIcon("offset_coloring.png"));
        }
        
        if(s.pps.fdes.fake_de) {
            fake_de_opt.setIcon(MainWindow.getIcon("fake_distance_estimation_enabled.png"));
        }
        else {
            fake_de_opt.setIcon(MainWindow.getIcon("fake_distance_estimation.png"));
        }

        if(s.pps.ndes.useNumericalDem) {
            numerical_distance_estimator_opt.setIcon(MainWindow.getIcon("numerical_dem_enabled.png"));
        }
        else {
            numerical_distance_estimator_opt.setIcon(MainWindow.getIcon("numerical_dem.png"));
        }
        
        if(s.pps.ens.entropy_coloring) {
            entropy_coloring_opt.setIcon(MainWindow.getIcon("entropy_coloring_enabled.png"));
        }
        else {
            entropy_coloring_opt.setIcon(MainWindow.getIcon("entropy_coloring.png"));
        }
        
        if(s.exterior_de) {
            exterior_de_opt.setIcon(MainWindow.getIcon("distance_estimation_enabled.png"));
        }
        else {
            exterior_de_opt.setIcon(MainWindow.getIcon("distance_estimation.png"));
        }
        
        if(s.pps.ls.lighting) {
            light_opt.setIcon(MainWindow.getIcon("light_enabled.png"));
        }
        else {
            light_opt.setIcon(MainWindow.getIcon("light.png"));
        }

        if(s.pps.ss.slopes) {
            slope_opt.setIcon(MainWindow.getIcon("slopes_enabled.png"));
        }
        else {
            slope_opt.setIcon(MainWindow.getIcon("slopes.png"));
        }
        
        if(s.pps.sts.statistic) {
            statistics_coloring_opt.setIcon(MainWindow.getIcon("statistics_coloring_enabled.png"));
        }
        else {
            statistics_coloring_opt.setIcon(MainWindow.getIcon("statistics_coloring.png"));
        }
        
    }
    
    public JMenuItem getStatisticsColoring() {
        
        return statistics_coloring_opt;
        
    }
    
}
