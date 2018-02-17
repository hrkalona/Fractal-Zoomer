/*
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author kaloch
 */
public class FileMenu extends JMenu {
    private MainWindow ptr;
    private JMenuItem exit;
    private JMenuItem save_image;
    private JMenuItem starting_position;
    private JMenuItem go_to;
    private JMenuItem save_settings;
    private JMenuItem load_settings;
    private JMenuItem save_settings_image;
    private JMenuItem code_editor;
    private JMenuItem compile_code;
    private JMenuItem zoom_in;
    private JMenuItem zoom_out;
    private JMenuItem up;
    private JMenuItem down;
    private JMenuItem left;
    private JMenuItem right;
    
    public FileMenu(MainWindow ptr2, String name) {

        super(name);

        this.ptr = ptr2;
        
        starting_position = new JMenuItem("Starting Position", getIcon("/fractalzoomer/icons/starting_position.png"));

        go_to = new JMenuItem("Go To", getIcon("/fractalzoomer/icons/go_to.png"));

        zoom_in = new JMenuItem("Zoom In", getIcon("/fractalzoomer/icons/zoom_in.png"));

        zoom_out = new JMenuItem("Zoom Out", getIcon("/fractalzoomer/icons/zoom_out.png"));

        up = new JMenuItem("Up", getIcon("/fractalzoomer/icons/up.png"));
        down = new JMenuItem("Down", getIcon("/fractalzoomer/icons/down.png"));
        left = new JMenuItem("Left", getIcon("/fractalzoomer/icons/left.png"));
        right = new JMenuItem("Right", getIcon("/fractalzoomer/icons/right.png"));

        save_settings = new JMenuItem("Save Settings As...", getIcon("/fractalzoomer/icons/save.png"));

        load_settings = new JMenuItem("Load Settings", getIcon("/fractalzoomer/icons/load.png"));

        save_image = new JMenuItem("Save Image As...", getIcon("/fractalzoomer/icons/save_image.png"));
        
        save_settings_image = new JMenuItem("Save Settings and Image As...", getIcon("/fractalzoomer/icons/save_image_settings.png"));

        code_editor = new JMenuItem("Edit User Code", getIcon("/fractalzoomer/icons/code_editor.png"));
        compile_code = new JMenuItem("Compile User Code", getIcon("/fractalzoomer/icons/compile.png"));

        exit = new JMenuItem("Exit", getIcon("/fractalzoomer/icons/exit.png"));
        
        starting_position.setToolTipText("Resets the fractal to the default position.");
        go_to.setToolTipText("Sets the center and size of the fractal, or the julia seed.");
        zoom_in.setToolTipText("Zooms in with a fixed rate to the current center.");
        zoom_out.setToolTipText("Zooms out with a fixed rate to the current center.");
        up.setToolTipText("Moves one screen up.");
        down.setToolTipText("Moves one screen down.");
        left.setToolTipText("Moves one screen left.");
        right.setToolTipText("Moves one screen right.");
        save_settings.setToolTipText("<html>Saves the function, plane, center, size, color options, iterations,<br> rotation, perturbation, initial value, bailout, julia settings,<br>and image filters.</html>");
        load_settings.setToolTipText("<html>Loads the function, plane, center, size, color options, iterations,<br> rotation, perturbation, initial value, bailout, julia settings,<br>and image filters.</html>");
        save_image.setToolTipText("Saves a png image.");
        save_settings_image.setToolTipText("Saves the current settings and a png image.");
        code_editor.setToolTipText("<html>Opens the java code, containing the user defined functions,<br>with a text editor.</html>");
        compile_code.setToolTipText("Compiles the java code, containing the user defined functions.");
        exit.setToolTipText("Exits the application.");
        
        starting_position.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.CTRL_MASK));
        go_to.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.CTRL_MASK));
        zoom_in.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0));
        zoom_out.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0));
        up.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, ActionEvent.ALT_MASK));
        down.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, ActionEvent.ALT_MASK));
        left.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ActionEvent.ALT_MASK));
        right.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ActionEvent.ALT_MASK));
        save_settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        load_settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        save_image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.CTRL_MASK));
        save_settings_image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.SHIFT_MASK));
        compile_code.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        code_editor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        
        starting_position.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.startingPosition();

            }
        });

        go_to.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.goTo();
                
            }
        });

        save_settings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.saveSettings();

            }
        });

        load_settings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.loadSettings();

            }
        });

        save_image.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.saveImage();

            }
        });
        
        save_settings_image.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.saveSettingsAndImage();

            }
        });

        compile_code.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.compileCode(true);

            }
        });

        code_editor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.codeEditor();

            }
        });

        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.exit();

            }
        });
        
        zoom_in.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.zoomIn();

            }
        });

        zoom_out.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.zoomOut();

            }
        });

        up.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.moveTo(MainWindow.UP);

            }
        });

        down.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.moveTo(MainWindow.DOWN);

            }
        });

        left.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.moveTo(MainWindow.LEFT);

            }
        });

        right.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.moveTo(MainWindow.RIGHT);

            }
        });
        
        add(starting_position);
        add(go_to);
        add(zoom_in);
        add(zoom_out);
        addSeparator();
        add(up);
        add(down);
        add(left);
        add(right);
        addSeparator();
        add(save_settings);
        add(load_settings);
        addSeparator();
        add(save_image);
        add(save_settings_image);
        addSeparator();
        add(code_editor);
        add(compile_code);
        addSeparator();
        add(exit);
        
    }
    
    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

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
    
    public JMenuItem getSaveImageAndSettings() {
        
        return save_settings_image;
        
    }
    
}
