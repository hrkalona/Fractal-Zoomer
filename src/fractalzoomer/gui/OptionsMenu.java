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
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.PaletteSettings;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author kaloch
 */
public class OptionsMenu extends JMenu {

    private static final long serialVersionUID = -7875948962252862696L;
    private MainWindow ptr;
    private ColorsMenu colors_menu;
    private JMenu iterations_menu;
    private BailoutConditionsMenu bailout_condition_menu;
    private JMenu rotation_menu;
    private OptimizationsMenu optimizations_menu;
    private ToolsOptionsMenu tools_options_menu;
    private JMenuItem filters_options;
    private JMenuItem size_of_image;
    private JMenuItem iterations;
    private JMenuItem increase_iterations;
    private JMenuItem decrease_iterations;
    private JMenuItem height_ratio_number;
    private JMenuItem set_rotation;
    private JMenuItem increase_rotation;
    private JMenuItem decrease_rotation;
    private JMenuItem bailout_number;
    private JMenuItem change_zooming_factor;
    private JMenuItem point_opt;
    private JMenuItem overview_opt;
    private FractalOptionsMenu fractal_options_menu;
    private JMenu window_menu;
    private JCheckBoxMenuItem toolbar_opt;
    private JCheckBoxMenuItem statusbar_opt;
    private JCheckBoxMenuItem infobar_opt;
    private JCheckBoxMenuItem fullscreen_opt;

    public OptionsMenu(MainWindow ptr2, String name, PaletteSettings ps, PaletteSettings ps2, boolean smoothing, boolean show_orbit_converging_point, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, int out_coloring_algorithm, int in_coloring_algorithm, int function, int plane_type, int bailout_test_algorithm, int color_blending, int temp_color_cycling_location, int temp_color_cycling_location2) {

        super(name);

        this.ptr = ptr2;

        fractal_options_menu = new FractalOptionsMenu(ptr, "Fractal Options", apply_plane_on_julia, apply_plane_on_julia_seed, function, plane_type);

        iterations_menu = new JMenu("Iterations");
        iterations_menu.setIcon(getIcon("/fractalzoomer/icons/iterations.png"));

        increase_iterations = new JMenuItem("Increase Iterations", getIcon("/fractalzoomer/icons/plus.png"));

        decrease_iterations = new JMenuItem("Decrease Iterations", getIcon("/fractalzoomer/icons/minus.png"));

        iterations = new JMenuItem("Set Iterations", getIcon("/fractalzoomer/icons/iterations.png"));

        bailout_condition_menu = new BailoutConditionsMenu(ptr, "Bailout Condition", bailout_test_algorithm);

        bailout_number = new JMenuItem("Bailout", getIcon("/fractalzoomer/icons/bailout.png"));

        rotation_menu = new JMenu("Rotation");
        rotation_menu.setIcon(getIcon("/fractalzoomer/icons/rotate.png"));

        set_rotation = new JMenuItem("Set Rotation", getIcon("/fractalzoomer/icons/rotate.png"));

        increase_rotation = new JMenuItem("Increase Rotation", getIcon("/fractalzoomer/icons/plus.png"));

        decrease_rotation = new JMenuItem("Decrease Rotation", getIcon("/fractalzoomer/icons/minus.png"));

        point_opt = new JMenuItem("User Point", getIcon("/fractalzoomer/icons/user_point.png"));

        change_zooming_factor = new JMenuItem("Zooming Factor", getIcon("/fractalzoomer/icons/zooming_factor.png"));

        size_of_image = new JMenuItem("Image Size", getIcon("/fractalzoomer/icons/image_size.png"));

        height_ratio_number = new JMenuItem("Stretch Factor", getIcon("/fractalzoomer/icons/stretch.png"));

        optimizations_menu = new OptimizationsMenu(ptr, "Optimizations");

        tools_options_menu = new ToolsOptionsMenu(ptr, "Tools Options", show_orbit_converging_point);

        filters_options = new JMenuItem("Filters Options", getIcon("/fractalzoomer/icons/filter_options.png"));

        window_menu = new JMenu("Window");
        window_menu.setIcon(getIcon("/fractalzoomer/icons/window.png"));

        colors_menu = new ColorsMenu(ptr, "Colors", ps, ps2, smoothing, out_coloring_algorithm, in_coloring_algorithm, color_blending, temp_color_cycling_location, temp_color_cycling_location2);

        overview_opt = new JMenuItem("Options Overview", getIcon("/fractalzoomer/icons/overview.png"));

        toolbar_opt = new JCheckBoxMenuItem("Tool Bar");
        statusbar_opt = new JCheckBoxMenuItem("Status Bar");
        infobar_opt = new JCheckBoxMenuItem("Information Bar");
        fullscreen_opt = new JCheckBoxMenuItem("Full Screen");

        size_of_image.setToolTipText("Sets the image size.");
        iterations.setToolTipText("Sets the maximum number of iterations.");
        increase_iterations.setToolTipText("Increases the maximum iteterations number by one.");
        decrease_iterations.setToolTipText("Decreases the maximum iteterations number by one.");
        bailout_number.setToolTipText("Sets the bailout. Above this number the norm of a complex numbers is not bounded.");
        set_rotation.setToolTipText("Sets the rotation in degrees.");
        point_opt.setToolTipText("A point picked by the user, for the point variable.");
        increase_rotation.setToolTipText("Increases the rotation by one degree.");
        decrease_rotation.setToolTipText("Decreases the rotation by one degree.");
        change_zooming_factor.setToolTipText("Sets the rate of each zoom.");
        height_ratio_number.setToolTipText("Changes the ratio of the image and creates a stretch.");
        filters_options.setToolTipText("Sets the options of the image filters.");
        overview_opt.setToolTipText("Creates a report of all the active fractal options.");
        toolbar_opt.setToolTipText("Activates the tool bar.");
        statusbar_opt.setToolTipText("Activates the status bar.");
        infobar_opt.setToolTipText("Activates the information bar.");
        fullscreen_opt.setToolTipText("Toggles the application from window mode to full screen.");

        size_of_image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        iterations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0));
        increase_iterations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.ALT_MASK));
        decrease_iterations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.ALT_MASK));
        bailout_number.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.ALT_MASK));
        set_rotation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        increase_rotation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.CTRL_MASK));
        decrease_rotation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
        point_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0));
        change_zooming_factor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        height_ratio_number.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.SHIFT_MASK));
        filters_options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.SHIFT_MASK));
        toolbar_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.ALT_MASK));
        statusbar_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        infobar_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        fullscreen_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));

        overview_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.SHIFT_MASK));

        size_of_image.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setSizeOfImage();

            }
        });

        iterations.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setIterations();

            }
        });

        increase_iterations.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.increaseIterations();

            }
        });

        decrease_iterations.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.decreaseIterations();

            }
        });

        bailout_number.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setBailout();

            }
        });

        set_rotation.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setRotation();

            }
        });

        increase_rotation.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.increaseRotation();

            }
        });

        decrease_rotation.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.decreaseRotation();

            }
        });

        point_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setPoint();

            }
        });

        change_zooming_factor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setZoomingFactor();

            }
        });

        height_ratio_number.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setHeightRatio();

            }
        });

        overview_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.Overview();

            }
        });

        toolbar_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setToolbar();

            }
        });

        statusbar_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setStatubar();

            }
        });

        infobar_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setInfobar();

            }
        });

        fullscreen_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFullScreen();

            }
        });

        window_menu.add(toolbar_opt);
        window_menu.add(infobar_opt);
        window_menu.add(statusbar_opt);
        window_menu.add(fullscreen_opt);

        toolbar_opt.setSelected(true);
        infobar_opt.setSelected(true);
        statusbar_opt.setSelected(true);
        fullscreen_opt.setSelected(false);

        filters_options.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.filtersOptions();
            }
        });

        iterations_menu.add(iterations);
        iterations_menu.add(increase_iterations);
        iterations_menu.add(decrease_iterations);

        rotation_menu.add(set_rotation);
        rotation_menu.add(increase_rotation);
        rotation_menu.add(decrease_rotation);

        add(fractal_options_menu);
        addSeparator();
        add(colors_menu);
        addSeparator();
        add(iterations_menu);
        addSeparator();
        add(bailout_condition_menu);
        add(bailout_number);
        addSeparator();
        add(rotation_menu);
        addSeparator();
        add(point_opt);
        addSeparator();
        add(size_of_image);
        addSeparator();
        add(height_ratio_number);
        addSeparator();
        add(change_zooming_factor);
        addSeparator();
        add(optimizations_menu);
        addSeparator();
        add(tools_options_menu);
        addSeparator();
        add(filters_options);
        addSeparator();
        add(overview_opt);
        addSeparator();
        add(window_menu);
    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }

    public JCheckBoxMenuItem getPeriodicityChecking() {

        return optimizations_menu.getPeriodicityChecking();

    }

    public JMenuItem getGreedyAlgorithm() {

        return optimizations_menu.getGreedyAlgorithm();

    }

    public JRadioButtonMenuItem[] getOutColoringPalette() {

        return colors_menu.getOutColoringPalette();

    }

    public JRadioButtonMenuItem[] getInColoringPalette() {

        return colors_menu.getInColoringPalette();

    }

    public JMenuItem getEntropyColoring() {

        return colors_menu.getEntropyColoring();

    }

    public JMenuItem getGreyScaleColoring() {

        return colors_menu.getGreyScaleColoring();

    }

    public JMenuItem getOffsetColoring() {

        return colors_menu.getOffsetColoring();

    }

    public JMenuItem getRainbowPalette() {

        return colors_menu.getRainbowPalette();

    }

    public JMenuItem getBumpMap() {

        return colors_menu.getBumpMap();

    }

    public JMenuItem getFakeDistanceEstimation() {

        return colors_menu.getFakeDistanceEstimation();

    }

    public JMenuItem getSmoothing() {

        return colors_menu.getSmoothing();

    }

    public JMenuItem getDistanceEstimation() {

        return colors_menu.getDistanceEstimation();

    }

    public JMenuItem getFractalColor() {

        return colors_menu.getFractalColor();

    }

    public JRadioButtonMenuItem[] getOutColoringModes() {

        return colors_menu.getOutColoringModes();

    }

    public JRadioButtonMenuItem[] getInColoringModes() {

        return colors_menu.getInColoringModes();

    }

    public OutColoringModesMenu getOutColoringMenu() {

        return colors_menu.getOutColoringMenu();

    }

    public InColoringModesMenu getInColoringMenu() {

        return colors_menu.getInColoringMenu();

    }

    public ColorsMenu getColorsMenu() {

        return colors_menu;

    }

    public JCheckBoxMenuItem getShowOrbitConvergingPoint() {

        return tools_options_menu.getShowOrbitConvergingPoint();

    }

    public JMenuItem getJuliaMapOptions() {

        return tools_options_menu.getJuliaMapOptions();

    }

    public JMenuItem getPolarProjectionOptions() {

        return tools_options_menu.getPolarProjectionOptions();

    }

    public JCheckBoxMenuItem getFastJuliaFiltersOptions() {

        return tools_options_menu.getFastJuliaFiltersOptions();

    }

    public JMenuItem getDomainColoringOptions() {

        return tools_options_menu.getDomainColoringOptions();

    }

    public JMenuItem get3DOptions() {

        return tools_options_menu.get3DOptions();

    }

    public JRadioButtonMenuItem getLine() {

        return tools_options_menu.getLine();

    }

    public JRadioButtonMenuItem getDot() {

        return tools_options_menu.getDot();

    }

    public JRadioButtonMenuItem getZoomWindowLine() {

        return tools_options_menu.getZoomWindowLine();

    }

    public JRadioButtonMenuItem getZoomWindowDashedLine() {

        return tools_options_menu.getZoomWindowDashedLine();

    }

    public JCheckBoxMenuItem getInitialValue() {

        return fractal_options_menu.getInitialValue();

    }

    public JCheckBoxMenuItem getPerturbation() {

        return fractal_options_menu.getPerturbation();

    }

    public JMenuItem getBailout() {

        return bailout_number;

    }

    public BailoutConditionsMenu getBailoutConditionMenu() {

        return bailout_condition_menu;

    }

    public JMenu getRotationMenu() {

        return rotation_menu;

    }

    public JRadioButtonMenuItem[] getPlanes() {

        return fractal_options_menu.getPlanes();

    }

    public PlanesMenu getPlanesMenu() {

        return fractal_options_menu.getPlanesMenu();

    }

    public JCheckBoxMenuItem getApplyPlaneOnWholeJuliaOpt() {

        return fractal_options_menu.getApplyPlaneOnWholeJuliaOpt();

    }

    public JCheckBoxMenuItem getApplyPlaneOnJuliaSeedOpt() {

        return fractal_options_menu.getApplyPlaneOnJuliaSeedOpt();

    }

    public JRadioButtonMenuItem[] getFractalFunctions() {

        return fractal_options_menu.getFractalFunctions();

    }

    public JCheckBoxMenuItem getBurningShipOpt() {

        return fractal_options_menu.getBurningShipOpt();

    }

    public JCheckBoxMenuItem getMandelGrassOpt() {

        return fractal_options_menu.getMandelGrassOpt();

    }

    public JRadioButtonMenuItem[] getBailoutConditions() {

        return bailout_condition_menu.getBailoutConditions();

    }

    public JMenu getIterationsMenu() {

        return iterations_menu;

    }

    public JMenuItem getSizeOfImage() {

        return size_of_image;

    }

    public JMenuItem getHeightRatio() {

        return height_ratio_number;

    }

    public JMenuItem getPoint() {

        return point_opt;

    }

    public FractalFunctionsMenu getFractalFunctionsMenu() {

        return fractal_options_menu.getFractalFunctionsMenu();

    }

    public JMenuItem getFiltersOptions() {

        return filters_options;

    }

    public FractalOptionsMenu getFractalOptionsMenu() {

        return fractal_options_menu;

    }

    public JMenuItem getOverview() {

        return overview_opt;

    }

    public JCheckBoxMenuItem getToolbar() {

        return toolbar_opt;

    }

    public JCheckBoxMenuItem getInfobar() {

        return infobar_opt;

    }

    public JCheckBoxMenuItem getFullscreen() {

        return fullscreen_opt;

    }

    public JCheckBoxMenuItem getStatusbar() {

        return statusbar_opt;

    }

    public OptimizationsMenu getOptimizationsMenu() {

        return optimizations_menu;

    }

    public ToolsOptionsMenu getToolsOptionsMenu() {

        return tools_options_menu;

    }

    public JMenuItem getRandomPalette() {

        return colors_menu.getRandomPalette();

    }

    public OutColoringPaletteMenu getOutColoringPaletteMenu() {

        return colors_menu.getOutColoringPaletteMenu();

    }

    public InColoringPaletteMenu getInColoringPaletteMenu() {

        return colors_menu.getInColoringPaletteMenu();

    }

    public ProcessingMenu getProcessing() {

        return colors_menu.getProcessing();

    }

    public JMenuItem getLight() {

        return colors_menu.getLight();

    }
    
    public JMenuItem getHistogramColoring() {
        
        return colors_menu.getHistogramColoring();
        
    }

    public ColorBlendingMenu getColorBlending() {

        return colors_menu.getColorBlending();

    }

    public JRadioButtonMenuItem[] getBlendingModes() {

        return colors_menu.getBlendingModes();

    }

    public JRadioButtonMenuItem[] getInColoringTranferFunctions() {

        return colors_menu.getInColoringTranferFunctions();

    }

    public JRadioButtonMenuItem[] getOutColoringTranferFunctions() {

        return colors_menu.getOutColoringTranferFunctions();

    }

    public JMenuItem getGradient() {

        return colors_menu.getGradient();

    }

    public JMenuItem getOrbitTraps() {

        return colors_menu.getOrbitTraps();

    }

    public JCheckBoxMenuItem getDirectColor() {

        return colors_menu.getDirectColor();

    }

    public JMenuItem getContourColoring() {

        return colors_menu.getContourColoring();

    }

    public JMenuItem getProcessingOrder() {

        return colors_menu.getProcessingOrder();

    }

    public JCheckBoxMenuItem getUsePaletteForInColoring() {

        return colors_menu.getUsePaletteForInColoring();

    }

    public JMenuItem getPaletteGradientMerging() {
        return colors_menu.getPaletteGradientMerging();
    }

    public JMenuItem getStatisticsColoring() {

        return colors_menu.getStatisticsColoring();

    }

    public JMenuItem getOutTrueColoring() {
        return colors_menu.getOutTrueColoring();
    }

    public JMenuItem getInTrueColoring() {
        return colors_menu.getInTrueColoring();
    }

}
