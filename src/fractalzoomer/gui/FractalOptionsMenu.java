
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 *
 * @author kaloch
 */
public class FractalOptionsMenu extends MyMenu {
	private static final long serialVersionUID = 3353731296448038687L;
	private MainWindow ptr;
    private FractalFunctionsMenu fractal_functions_menu;
    private PlanesMenu planes_menu;
    private JMenuItem perturbation_opt;
    private JMenuItem init_val_opt;
    private FunctionFiltersMenu pre_function_filters;
    private FunctionFiltersMenu post_function_filters;
    private PlaneInfluenceMenu plane_influence_menu;
    
    public FractalOptionsMenu(MainWindow ptr2, String name, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, int function, int plane_type, int pre_filter, int post_filter, int plane_influence, boolean flip_re, boolean flip_im) {
        super(name);

        this.ptr = ptr2;
        
        setIcon(MainWindow.getIcon("fractal_options.png"));
        
        fractal_functions_menu = new FractalFunctionsMenu(ptr, "Fractal Functions", function);
        
        planes_menu = new PlanesMenu(ptr, "Planes", apply_plane_on_julia, apply_plane_on_julia_seed, plane_type, flip_re, flip_im);

        pre_function_filters = new FunctionFiltersMenu(ptr, "Pre Function Filter", pre_filter, false);
        post_function_filters = new FunctionFiltersMenu(ptr, "Post Function Filter", post_filter, true);

        plane_influence_menu = new PlaneInfluenceMenu(ptr, plane_influence);
        
        perturbation_opt = new MyMenuItem("Initial Perturbation");
        init_val_opt = new MyMenuItem("Initial Value");
        
        perturbation_opt.setToolTipText("Adds a complex number to the initial value of the fractal calculation.");
        init_val_opt.setToolTipText("Changes the initial value of the fractal calculation.");
        
        perturbation_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));      
        init_val_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.SHIFT_MASK));
        
        perturbation_opt.addActionListener(e -> ptr.setPerturbation());

        init_val_opt.addActionListener(e -> ptr.setInitialValue());
        
        add(fractal_functions_menu);
        addSeparator();
        add(planes_menu);
        addSeparator();
        add(plane_influence_menu);
        addSeparator();
        add(pre_function_filters);
        addSeparator();
        add(post_function_filters);
        addSeparator();
        add(init_val_opt);
        addSeparator();
        add(perturbation_opt);
    }
    
    public FractalFunctionsMenu getFractalFunctionsMenu() {
        
        return fractal_functions_menu;
        
    }
    
    public JRadioButtonMenuItem[] getPlanes() {

        return planes_menu.getPlanes();

    }
    
    public PlanesMenu getPlanesMenu() {

        return planes_menu;

    }

    public JCheckBoxMenuItem getApplyPlaneOnWholeJuliaOpt() {

        return planes_menu.getApplyPlaneOnWholeJuliaOpt();

    }
    
    public JCheckBoxMenuItem getApplyPlaneOnJuliaSeedOpt() {

        return planes_menu.getApplyPlaneOnJuliaSeedOpt();

    }

    public JCheckBoxMenuItem getFlipReOpt() {

        return planes_menu.getFlipReOpt();

    }

    public JCheckBoxMenuItem getFlipImOpt() {

        return planes_menu.getFlipImOpt();

    }
    
    public JRadioButtonMenuItem[] getFractalFunctions() {

        return fractal_functions_menu.getFractalFunctions();

    }
    
    public JCheckBoxMenuItem getBurningShipOpt() {
        
        return fractal_functions_menu.getBurningShipOpt();
        
    }
    
    public JMenuItem getMandelGrassOpt() {
        
        return fractal_functions_menu.getMandelGrassOpt();
        
    }
    
    public JMenuItem getInitialValue() {
        
        return init_val_opt;
        
    }
    
    public JMenuItem getPerturbation() {
        
        return perturbation_opt;
        
    }

    public JRadioButtonMenuItem[] getPreFunctionFilters() {

        return pre_function_filters.getFunctionFilters();

    }

    public JMenu getPostFunctionFiltersMenu() {

        return post_function_filters;

    }

    public JMenu getPreFunctionFiltersMenu() {

        return pre_function_filters;

    }

    public JRadioButtonMenuItem[] getPostFunctionFilters() {

        return post_function_filters.getFunctionFilters();

    }

    public JRadioButtonMenuItem[] getPlaneInfluences() {

        return plane_influence_menu.getPlaneInfluences();

    }

    public JMenu getPlaneInfluenceMenu() {

        return plane_influence_menu;

    }
    
}
