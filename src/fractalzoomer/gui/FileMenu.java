
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 *
 * @author kaloch
 */
public class FileMenu extends MyMenu {
	private static final long serialVersionUID = -3961782140061944788L;
	private MainWindow ptr;
    private JMenuItem exit;
    private JMenuItem save_image;
    private JMenuItem starting_position;
    private JMenuItem go_to;
    private JMenuItem save_settings;
    private JMenuItem load_settings;
    private JMenuItem load_kfr_settings;
    private JMenuItem save_kfr_settings;
    private JMenuItem save_settings_image;
    private JMenuItem code_editor;
    private JMenuItem library_code;
    private JMenuItem compile_code;
    private JMenuItem zoom_in;
    private JMenuItem zoom_out;
    private JMenuItem repaint_opt;
    private JMenuItem up;
    private JMenuItem down;
    private JMenuItem left;
    private JMenuItem right;
    private JMenuItem default_opt;
    private JMenuItem save_initial_settings_opt;

    private JMenuItem cancel_operation;
    
    public FileMenu(MainWindow ptr2, String name) {

        super(name);

        this.ptr = ptr2;
        
        starting_position = new MyMenuItem("Starting Position", MainWindow.getIcon("starting_position.png"));

        go_to = new MyMenuItem("Go To", MainWindow.getIcon("go_to.png"));

        zoom_in = new MyMenuItem("Zoom In", MainWindow.getIcon("zoom_in.png"));

        zoom_out = new MyMenuItem("Zoom Out", MainWindow.getIcon("zoom_out.png"));
        
        repaint_opt = new MyMenuItem("Repaint", MainWindow.getIcon("refresh_image.png"));

        up = new MyMenuItem("Up", MainWindow.getIcon("up.png"));
        down = new MyMenuItem("Down", MainWindow.getIcon("down.png"));
        left = new MyMenuItem("Left", MainWindow.getIcon("left.png"));
        right = new MyMenuItem("Right", MainWindow.getIcon("right.png"));
        
        default_opt = new MyMenuItem("Default Settings", MainWindow.getIcon("default.png"));

        save_settings = new MyMenuItem("Save Settings", MainWindow.getIcon("save.png"));
        save_kfr_settings = new MyMenuItem("Save KFR Settings", MainWindow.getIcon("save.png"));

        load_settings = new MyMenuItem("Load Settings", MainWindow.getIcon("load.png"));
        load_kfr_settings = new MyMenuItem("Load KFR Settings", MainWindow.getIcon("load.png"));
        
        save_initial_settings_opt = new MyMenuItem("Set Initial Settings", MainWindow.getIcon("init_settings.png"));

        save_image = new MyMenuItem("Save Image As...", MainWindow.getIcon("save_image.png"));
        
        save_settings_image = new MyMenuItem("Save Settings and Image As...", MainWindow.getIcon("save_image_settings.png"));

        code_editor = new MyMenuItem("Edit User Code", MainWindow.getIcon("code_editor.png"));
        library_code = new MyMenuItem("Library Code", MainWindow.getIcon("code_editor.png"));
        compile_code = new MyMenuItem("Compile User Code", MainWindow.getIcon("compile.png"));

        cancel_operation  = new MyMenuItem("Cancel Operation", MainWindow.getIcon("abort.png"));
        exit = new MyMenuItem("Exit", MainWindow.getIcon("exit.png"));
        
        starting_position.setToolTipText("Resets the fractal to the default position.");
        go_to.setToolTipText("Sets the center and size of the fractal, or the julia seed.");
        zoom_in.setToolTipText("Zooms in with a fixed rate to the current center.");
        zoom_out.setToolTipText("Zooms out with a fixed rate to the current center.");
        up.setToolTipText("Moves one screen up.");
        down.setToolTipText("Moves one screen down.");
        left.setToolTipText("Moves one screen left.");
        right.setToolTipText("Moves one screen right.");
        save_settings.setToolTipText("Saves the active settings.");
        save_kfr_settings.setToolTipText("Saves some basic settings into Kalles Fraktaler format.");
        load_settings.setToolTipText("Load settings created by Fractal Zoomer.");
        load_kfr_settings.setToolTipText("Loads settings created by Kalles Fraktaler.");
        save_image.setToolTipText("Saves an image.");
        save_settings_image.setToolTipText("Saves the current settings and an image.");
        code_editor.setToolTipText("<html>Opens the java code, containing the user defined functions,<br>with a text editor.</html>");
        library_code.setToolTipText("Opens the library code with a text editor.");
        compile_code.setToolTipText("Compiles the java code, containing the user defined functions.");
        exit.setToolTipText("Exits the application.");
        default_opt.setToolTipText("Resets all fractal settings to the default values.");
        repaint_opt.setToolTipText("Repaints the image, using the current active settings.");
        save_initial_settings_opt.setToolTipText("Creates a save file, called autoload.fzs, that will be always loaded at start-up.");
        cancel_operation.setToolTipText("Cancels the current rendering operation and resets.");

        starting_position.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.CTRL_MASK));
        go_to.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.CTRL_MASK));
        zoom_in.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0));
        zoom_out.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0));
        up.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0));
        down.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0));
        left.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ActionEvent.ALT_MASK));
        right.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ActionEvent.ALT_MASK));
        save_settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        load_settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        save_image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.CTRL_MASK));
        save_settings_image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        compile_code.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        code_editor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        default_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.SHIFT_MASK));
        repaint_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, ActionEvent.CTRL_MASK));
        save_initial_settings_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));
        cancel_operation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        
        default_opt.addActionListener(e -> ptr.resetSettings());
        
        save_initial_settings_opt.addActionListener(e -> ptr.setInitialSettings());

        
        starting_position.addActionListener(e -> ptr.startingPosition());
        
        repaint_opt.addActionListener(e -> ptr.reRender(false));

        go_to.addActionListener(e -> ptr.goTo());

        save_settings.addActionListener(e -> ptr.saveSettings());
        save_kfr_settings.addActionListener(e -> ptr.saveBasicKFRSettings());

        load_settings.addActionListener(e -> ptr.loadSettings());
        load_kfr_settings.addActionListener(e -> ptr.loadKFRSettings(false));

        save_image.addActionListener(e -> ptr.saveImage(false));
        
        save_settings_image.addActionListener(e -> ptr.saveImage(true));

        compile_code.addActionListener(e -> ptr.compileCode(true));

        code_editor.addActionListener(e -> ptr.codeEditor());


        library_code.addActionListener(e -> ptr.libraryCode());

        //cancel_operation.addActionListener(e -> ptr.cancelOperation());

        exit.addActionListener(e -> ptr.exit());
        
        zoom_in.addActionListener(e -> ptr.zoomIn());

        zoom_out.addActionListener(e -> ptr.zoomOut());

        up.addActionListener(e -> ptr.moveTo(MainWindow.UP));

        down.addActionListener(e -> ptr.moveTo(MainWindow.DOWN));

        left.addActionListener(e -> ptr.moveTo(MainWindow.LEFT));

        right.addActionListener(e -> ptr.moveTo(MainWindow.RIGHT));
        
        add(starting_position);
        add(go_to);
        add(zoom_in);
        add(zoom_out);
        add(repaint_opt);
        addSeparator();
        add(up);
        add(down);
        add(left);
        add(right);
        addSeparator();
        add(save_settings);
        add(load_settings);
        add(save_kfr_settings);
        add(load_kfr_settings);
        add(default_opt);
        add(save_initial_settings_opt);
        addSeparator();
        add(save_image);
        add(save_settings_image);
        addSeparator();
        add(code_editor);
        add(library_code);
        add(compile_code);
        addSeparator();

//        add(cancel_operation);
//        addSeparator();

        add(exit);
        
    }

    public JMenuItem getZoomIn() {
        
        return zoom_in;
        
    }
    
    public JMenuItem getZoomOut() {
        
        return zoom_out;
        
    }
    
    public JMenuItem getUp() {
        
        return up;
        
    }
    
    public JMenuItem getDown() {
        
        return down;
        
    }
    
    public JMenuItem getLeft() {
        
        return left;
        
    }
    
    public JMenuItem getRight() {
        
        return right;
        
    }
    
    public JMenuItem getSaveImage() {
        
        return save_image;
        
    }
    
    public JMenuItem getCodeEditor() {
        
        return code_editor;

    }

    public JMenuItem getLibraryCode() {

        return library_code;

    }

    public JMenuItem getCancelOperation() {

        return cancel_operation;

    }
    
    public JMenuItem getCompileCode() {
        
        return compile_code;
        
    }
    
    public JMenuItem getStartingPosition() {
        
        return starting_position;
        
    }
    
    public JMenuItem getGoTo() {
        
        return go_to;
        
    }
    
    public JMenuItem getSaveSettings() {
        
        return save_settings;
        
    }
    
    public JMenuItem getLoadSettings() {
        
        return load_settings;
        
    }

    public JMenuItem getLoadKFRSettings() {
        return load_kfr_settings;
    }
    
    public JMenuItem getSaveImageAndSettings() {
        
        return save_settings_image;
        
    }
    
    public JMenuItem getDefaults() {
        
        return default_opt;
        
    }
    
    public JMenuItem getRepaint() {
        
        return repaint_opt;
        
    }
    
    public JMenuItem getSetInitialSettings() {
        
        return save_initial_settings_opt;
        
    }

    public JMenuItem getSaveKfrSettings() {
        return save_kfr_settings;
    }
    
}
