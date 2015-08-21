/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.utils;

import fractalzoomer.main.MainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author hrkalona2
 */
public class InColoringModesMenu extends JMenu {
    private MainWindow ptr;
    private JRadioButtonMenuItem[] in_coloring_modes;
    
    public InColoringModesMenu(MainWindow ptr2, String name) {
        
        super(name);
        
        this.ptr = ptr2;
        
        in_coloring_modes = new JRadioButtonMenuItem[11];

        in_coloring_modes[MainWindow.MAXIMUM_ITERATIONS] = new JRadioButtonMenuItem("Maximum Iterations");
        in_coloring_modes[MainWindow.MAXIMUM_ITERATIONS].setToolTipText("Sets the in-coloring method, using the maximum iterations.");
        in_coloring_modes[MainWindow.MAXIMUM_ITERATIONS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.MAXIMUM_ITERATIONS);

            }
        });
        add(in_coloring_modes[MainWindow.MAXIMUM_ITERATIONS]);

        in_coloring_modes[MainWindow.Z_MAG] = new JRadioButtonMenuItem("norm(z)");
        in_coloring_modes[MainWindow.Z_MAG].setToolTipText("Sets the in-coloring method, using the norm of z.");
        in_coloring_modes[MainWindow.Z_MAG].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.Z_MAG);

            }
        });
        add(in_coloring_modes[MainWindow.Z_MAG]);

        in_coloring_modes[MainWindow.DECOMPOSITION_LIKE] = new JRadioButtonMenuItem("Decomposition Like");
        in_coloring_modes[MainWindow.DECOMPOSITION_LIKE].setToolTipText("Sets the in-coloring method, using decomposition.");
        in_coloring_modes[MainWindow.DECOMPOSITION_LIKE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.DECOMPOSITION_LIKE);

            }
        });
        add(in_coloring_modes[MainWindow.DECOMPOSITION_LIKE]);

        in_coloring_modes[MainWindow.RE_DIVIDE_IM] = new JRadioButtonMenuItem("Re / Im");
        in_coloring_modes[MainWindow.RE_DIVIDE_IM].setToolTipText("Sets the in-coloring method, using Re(z) / Im(z).");
        in_coloring_modes[MainWindow.RE_DIVIDE_IM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.RE_DIVIDE_IM);

            }
        });
        add(in_coloring_modes[MainWindow.RE_DIVIDE_IM]);

        in_coloring_modes[MainWindow.COS_MAG] = new JRadioButtonMenuItem("cos(norm(z))");
        in_coloring_modes[MainWindow.COS_MAG].setToolTipText("Sets the in-coloring method, using the cos of the norm(z).");
        in_coloring_modes[MainWindow.COS_MAG].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.COS_MAG);

            }
        });
        add(in_coloring_modes[MainWindow.COS_MAG]);

        in_coloring_modes[MainWindow.MAG_TIMES_COS_RE_SQUARED] = new JRadioButtonMenuItem("norm(z) * cos(Re^2)");
        in_coloring_modes[MainWindow.MAG_TIMES_COS_RE_SQUARED].setToolTipText("Sets the in-coloring method, using norm(z) * cos(Re(z)^2).");
        in_coloring_modes[MainWindow.MAG_TIMES_COS_RE_SQUARED].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.MAG_TIMES_COS_RE_SQUARED);

            }
        });
        add(in_coloring_modes[MainWindow.MAG_TIMES_COS_RE_SQUARED]);

        in_coloring_modes[MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED] = new JRadioButtonMenuItem("sin(Re^2 - Im^2)");
        in_coloring_modes[MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED].setToolTipText("Sets the in-coloring method, using sin(Re(z)^2 - Im(z)^2).");
        in_coloring_modes[MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED);

            }
        });
        add(in_coloring_modes[MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED]);

        in_coloring_modes[MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM] = new JRadioButtonMenuItem("atan(Re * Im * |Re| * |Im|)");
        in_coloring_modes[MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM].setToolTipText("Sets the in-coloring method, using atan(Re(z) * Im(z) * |Re(z)| * |Im(z)|).");
        in_coloring_modes[MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM);

            }
        });
        add(in_coloring_modes[MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM]);

        in_coloring_modes[MainWindow.SQUARES] = new JRadioButtonMenuItem("Squares");
        in_coloring_modes[MainWindow.SQUARES].setToolTipText("Sets the in-coloring method, using squares.");
        in_coloring_modes[MainWindow.SQUARES].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.SQUARES);

            }
        });
        add(in_coloring_modes[MainWindow.SQUARES]);

        in_coloring_modes[MainWindow.SQUARES2] = new JRadioButtonMenuItem("Squares 2");
        in_coloring_modes[MainWindow.SQUARES2].setToolTipText("Sets the in-coloring method, using squares 2.");
        in_coloring_modes[MainWindow.SQUARES2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.SQUARES2);

            }
        });
        add(in_coloring_modes[MainWindow.SQUARES2]);

        in_coloring_modes[MainWindow.USER_INCOLORING_ALGORITHM] = new JRadioButtonMenuItem("User In Coloring Method");
        in_coloring_modes[MainWindow.USER_INCOLORING_ALGORITHM].setToolTipText("A user defined in-coloring method.");
        in_coloring_modes[MainWindow.USER_INCOLORING_ALGORITHM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setInColoringMode(MainWindow.USER_INCOLORING_ALGORITHM);

            }
        });
        add(in_coloring_modes[MainWindow.USER_INCOLORING_ALGORITHM]);     
        
    }
    
    public JRadioButtonMenuItem[] getInColoringModes() {
        
        return in_coloring_modes;
        
    }
    
}
