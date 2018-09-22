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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author hrkalona2
 */
public class InColoringModesMenu extends JMenu {
	private static final long serialVersionUID = 7587025770325916624L;
	private MainWindow ptr;
    private JRadioButtonMenuItem[] in_coloring_modes;
    
    public static String[] inColoringNames;
    
    static {
        inColoringNames = new String[MainWindow.TOTAL_INCOLORING_ALGORITHMS];
        inColoringNames[MainWindow.MAX_ITERATIONS] = "Maximum Iterations";
        inColoringNames[MainWindow.Z_MAG] = "norm(z)";
        inColoringNames[MainWindow.DECOMPOSITION_LIKE] = "Decomposition Like";
        inColoringNames[MainWindow.RE_DIVIDE_IM] = "Re / Im";
        inColoringNames[MainWindow.COS_MAG] = "cos(norm(z))";
        inColoringNames[MainWindow.MAG_TIMES_COS_RE_SQUARED] = "norm(z) * cos(Re^2)";
        inColoringNames[MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED] = "sin(Re^2 - Im^2)";
        inColoringNames[MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM] = "atan(Re * Im * |Re| * |Im|)";
        inColoringNames[MainWindow.SQUARES] = "Squares";
        inColoringNames[MainWindow.SQUARES2] = "Squares 2";
        inColoringNames[MainWindow.USER_INCOLORING_ALGORITHM] = "User In Coloring Method";      
    }

    public InColoringModesMenu(MainWindow ptr2, String name, int in_coloring_algorithm) {

        super(name);

        this.ptr = ptr2;

        setIcon(getIcon("/fractalzoomer/icons/in_coloring_mode.png"));
        
        in_coloring_modes = new JRadioButtonMenuItem[inColoringNames.length];

        ButtonGroup incoloring_button_group = new ButtonGroup();

        in_coloring_modes[MainWindow.MAX_ITERATIONS] = new JRadioButtonMenuItem(inColoringNames[MainWindow.MAX_ITERATIONS]);
        in_coloring_modes[MainWindow.MAX_ITERATIONS].setToolTipText("Sets the in-coloring method, using the maximum iterations.");
        in_coloring_modes[MainWindow.MAX_ITERATIONS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.MAX_ITERATIONS);

            }
        });
        add(in_coloring_modes[MainWindow.MAX_ITERATIONS]);
        incoloring_button_group.add(in_coloring_modes[MainWindow.MAX_ITERATIONS]);

        in_coloring_modes[MainWindow.Z_MAG] = new JRadioButtonMenuItem(inColoringNames[MainWindow.Z_MAG]);
        in_coloring_modes[MainWindow.Z_MAG].setToolTipText("Sets the in-coloring method, using the norm of z.");
        in_coloring_modes[MainWindow.Z_MAG].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.Z_MAG);

            }
        });
        add(in_coloring_modes[MainWindow.Z_MAG]);
        incoloring_button_group.add(in_coloring_modes[MainWindow.Z_MAG]);

        in_coloring_modes[MainWindow.DECOMPOSITION_LIKE] = new JRadioButtonMenuItem(inColoringNames[MainWindow.DECOMPOSITION_LIKE]);
        in_coloring_modes[MainWindow.DECOMPOSITION_LIKE].setToolTipText("Sets the in-coloring method, using decomposition.");
        in_coloring_modes[MainWindow.DECOMPOSITION_LIKE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.DECOMPOSITION_LIKE);

            }
        });
        add(in_coloring_modes[MainWindow.DECOMPOSITION_LIKE]);
        incoloring_button_group.add(in_coloring_modes[MainWindow.DECOMPOSITION_LIKE]);

        in_coloring_modes[MainWindow.RE_DIVIDE_IM] = new JRadioButtonMenuItem(inColoringNames[MainWindow.RE_DIVIDE_IM]);
        in_coloring_modes[MainWindow.RE_DIVIDE_IM].setToolTipText("Sets the in-coloring method, using Re(z) / Im(z).");
        in_coloring_modes[MainWindow.RE_DIVIDE_IM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.RE_DIVIDE_IM);

            }
        });
        add(in_coloring_modes[MainWindow.RE_DIVIDE_IM]);
        incoloring_button_group.add(in_coloring_modes[MainWindow.RE_DIVIDE_IM]);

        in_coloring_modes[MainWindow.COS_MAG] = new JRadioButtonMenuItem(inColoringNames[MainWindow.COS_MAG]);
        in_coloring_modes[MainWindow.COS_MAG].setToolTipText("Sets the in-coloring method, using the cos of the norm(z).");
        in_coloring_modes[MainWindow.COS_MAG].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.COS_MAG);

            }
        });
        add(in_coloring_modes[MainWindow.COS_MAG]);
        incoloring_button_group.add(in_coloring_modes[MainWindow.COS_MAG]);

        in_coloring_modes[MainWindow.MAG_TIMES_COS_RE_SQUARED] = new JRadioButtonMenuItem(inColoringNames[MainWindow.MAG_TIMES_COS_RE_SQUARED]);
        in_coloring_modes[MainWindow.MAG_TIMES_COS_RE_SQUARED].setToolTipText("Sets the in-coloring method, using norm(z) * cos(Re(z)^2).");
        in_coloring_modes[MainWindow.MAG_TIMES_COS_RE_SQUARED].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.MAG_TIMES_COS_RE_SQUARED);

            }
        });
        add(in_coloring_modes[MainWindow.MAG_TIMES_COS_RE_SQUARED]);
        incoloring_button_group.add(in_coloring_modes[MainWindow.MAG_TIMES_COS_RE_SQUARED]);

        in_coloring_modes[MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED] = new JRadioButtonMenuItem(inColoringNames[MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED]);
        in_coloring_modes[MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED].setToolTipText("Sets the in-coloring method, using sin(Re(z)^2 - Im(z)^2).");
        in_coloring_modes[MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED);

            }
        });
        add(in_coloring_modes[MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED]);
        incoloring_button_group.add(in_coloring_modes[MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED]);

        in_coloring_modes[MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM] = new JRadioButtonMenuItem(inColoringNames[MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM]);
        in_coloring_modes[MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM].setToolTipText("Sets the in-coloring method, using atan(Re(z) * Im(z) * |Re(z)| * |Im(z)|).");
        in_coloring_modes[MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM);

            }
        });
        add(in_coloring_modes[MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM]);
        incoloring_button_group.add(in_coloring_modes[MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM]);

        in_coloring_modes[MainWindow.SQUARES] = new JRadioButtonMenuItem(inColoringNames[MainWindow.SQUARES]);
        in_coloring_modes[MainWindow.SQUARES].setToolTipText("Sets the in-coloring method, using squares.");
        in_coloring_modes[MainWindow.SQUARES].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.SQUARES);

            }
        });
        add(in_coloring_modes[MainWindow.SQUARES]);
        incoloring_button_group.add(in_coloring_modes[MainWindow.SQUARES]);

        in_coloring_modes[MainWindow.SQUARES2] = new JRadioButtonMenuItem(inColoringNames[MainWindow.SQUARES2]);
        in_coloring_modes[MainWindow.SQUARES2].setToolTipText("Sets the in-coloring method, using squares 2.");
        in_coloring_modes[MainWindow.SQUARES2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.SQUARES2);

            }
        });
        add(in_coloring_modes[MainWindow.SQUARES2]);
        incoloring_button_group.add(in_coloring_modes[MainWindow.SQUARES2]);

        in_coloring_modes[MainWindow.USER_INCOLORING_ALGORITHM] = new JRadioButtonMenuItem(inColoringNames[MainWindow.USER_INCOLORING_ALGORITHM]);
        in_coloring_modes[MainWindow.USER_INCOLORING_ALGORITHM].setToolTipText("A user defined in-coloring method.");
        in_coloring_modes[MainWindow.USER_INCOLORING_ALGORITHM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.USER_INCOLORING_ALGORITHM);

            }
        });
        in_coloring_modes[MainWindow.USER_INCOLORING_ALGORITHM].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        add(in_coloring_modes[MainWindow.USER_INCOLORING_ALGORITHM]);
        incoloring_button_group.add(in_coloring_modes[MainWindow.USER_INCOLORING_ALGORITHM]);
        
        in_coloring_modes[in_coloring_algorithm].setSelected(true);

    }

    public JRadioButtonMenuItem[] getInColoringModes() {

        return in_coloring_modes;

    }

    public void setEnabledAllButMaxIterations(boolean option) {
        
        in_coloring_modes[MainWindow.Z_MAG].setEnabled(option);
        in_coloring_modes[MainWindow.DECOMPOSITION_LIKE].setEnabled(option);
        in_coloring_modes[MainWindow.RE_DIVIDE_IM].setEnabled(option);
        in_coloring_modes[MainWindow.COS_MAG].setEnabled(option);
        in_coloring_modes[MainWindow.MAG_TIMES_COS_RE_SQUARED].setEnabled(option);
        in_coloring_modes[MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED].setEnabled(option);
        in_coloring_modes[MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM].setEnabled(option);
        in_coloring_modes[MainWindow.SQUARES].setEnabled(option);
        in_coloring_modes[MainWindow.SQUARES2].setEnabled(option);
        in_coloring_modes[MainWindow.USER_INCOLORING_ALGORITHM].setEnabled(option);
        
    }
    
    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }

}
