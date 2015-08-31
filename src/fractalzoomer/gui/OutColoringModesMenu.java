/* 
 * Fractal Zoomer, Copyright (C) 2015 hrkalona2
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
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author hrkalona2
 */
public class OutColoringModesMenu extends JMenu {
    private MainWindow ptr;
    private JRadioButtonMenuItem[] out_coloring_modes;
    
    public OutColoringModesMenu(MainWindow ptr2, String name) {
        
        super(name);
        
        this.ptr = ptr2;
        
        out_coloring_modes = new JRadioButtonMenuItem[23];
        
        out_coloring_modes[MainWindow.ESCAPE_TIME] = new JRadioButtonMenuItem("Escape Time");
        out_coloring_modes[MainWindow.ESCAPE_TIME].setToolTipText("Sets the out-coloring method, using the iterations.");
        out_coloring_modes[MainWindow.ESCAPE_TIME].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ESCAPE_TIME);

            }
        });
        add(out_coloring_modes[MainWindow.ESCAPE_TIME]);

        out_coloring_modes[MainWindow.BINARY_DECOMPOSITION] = new JRadioButtonMenuItem("Binary Decomposition");
        out_coloring_modes[MainWindow.BINARY_DECOMPOSITION].setToolTipText("Sets the out-coloring method, using binary decomposition.");
        out_coloring_modes[MainWindow.BINARY_DECOMPOSITION].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.BINARY_DECOMPOSITION);

            }
        });
        add(out_coloring_modes[MainWindow.BINARY_DECOMPOSITION]);

        out_coloring_modes[MainWindow.BINARY_DECOMPOSITION2] = new JRadioButtonMenuItem("Binary Decomposition 2");
        out_coloring_modes[MainWindow.BINARY_DECOMPOSITION2].setToolTipText("Sets the out-coloring method, using binary decomposition 2.");
        out_coloring_modes[MainWindow.BINARY_DECOMPOSITION2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.BINARY_DECOMPOSITION2);

            }
        });
        add(out_coloring_modes[MainWindow.BINARY_DECOMPOSITION2]);

        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE] = new JRadioButtonMenuItem("Escape Time + Re");
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE].setToolTipText("Sets the out-coloring method, using the iterations + Re(z).");
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ITERATIONS_PLUS_RE);

            }
        });
        add(out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE]);

        out_coloring_modes[MainWindow.ITERATIONS_PLUS_IM] = new JRadioButtonMenuItem("Escape Time + Im");
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_IM].setToolTipText("Sets the out-coloring method, using the iterations + Im(z).");
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_IM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ITERATIONS_PLUS_IM);

            }
        });
        add(out_coloring_modes[MainWindow.ITERATIONS_PLUS_IM]);

        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM] = new JRadioButtonMenuItem("Escape Time + Re / Im");
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM].setToolTipText("Sets the out-coloring method, using the iterations + Re(z)/Im(z).");
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM);

            }
        });
        add(out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM]);

        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM] = new JRadioButtonMenuItem("Escape Time + Re + Im + Re / Im");
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setToolTipText("Sets the out-coloring method, using the iterations + Re(z) + Im(z) + Re(z)/Im(z).");
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM);

            }
        });
        add(out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM]);

        out_coloring_modes[MainWindow.BIOMORPH] = new JRadioButtonMenuItem("Biomorph");
        out_coloring_modes[MainWindow.BIOMORPH].setToolTipText("Sets the out-coloring method, using biomorph.");
        out_coloring_modes[MainWindow.BIOMORPH].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.BIOMORPH);

            }
        });
        add(out_coloring_modes[MainWindow.BIOMORPH]);

        out_coloring_modes[MainWindow.COLOR_DECOMPOSITION] = new JRadioButtonMenuItem("Color Decomposition");
        out_coloring_modes[MainWindow.COLOR_DECOMPOSITION].setToolTipText("Sets the out-coloring method, using color decomposition.");
        out_coloring_modes[MainWindow.COLOR_DECOMPOSITION].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.COLOR_DECOMPOSITION);

            }
        });
        add(out_coloring_modes[MainWindow.COLOR_DECOMPOSITION]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION] = new JRadioButtonMenuItem("Escape Time + Color Decomposition");
        out_coloring_modes[MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION].setToolTipText("Sets the out-coloring method, using iterations + color decomposition.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION);

            }
        });
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER] = new JRadioButtonMenuItem("Escape Time + Gaussian Integer");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER].setToolTipText("Sets the out-coloring method, using Escape Time + Gaussian Integer.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER);

            }
        });
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2] = new JRadioButtonMenuItem("Escape Time + Gaussian Integer 2");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2].setToolTipText("Sets the out-coloring method, using Escape Time + Gaussian Integer 2.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2);

            }
        });
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3] = new JRadioButtonMenuItem("Escape Time + Gaussian Integer 3");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3].setToolTipText("Sets the out-coloring method, using Escape Time + Gaussian Integer 3.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3);

            }
        });
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4] = new JRadioButtonMenuItem("Escape Time + Gaussian Integer 4");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4].setToolTipText("Sets the out-coloring method, using Escape Time + Gaussian Integer 4.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4);

            }
        });
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5] = new JRadioButtonMenuItem("Escape Time + Gaussian Integer 5");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5].setToolTipText("Sets the out-coloring method, using Escape Time + Gaussian Integer 5.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5);

            }
        });
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM] = new JRadioButtonMenuItem("Escape Time + Algorithm");
        out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM].setToolTipText("Sets the out-coloring method, using Escape Time + Algorithm.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_ALGORITHM);

            }
        });
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM2] = new JRadioButtonMenuItem("Escape Time + Algorithm 2");
        out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM2].setToolTipText("Sets the out-coloring method, using Escape Time + Algorithm 2.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_ALGORITHM2);

            }
        });
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM2]);

        out_coloring_modes[MainWindow.DISTANCE_ESTIMATOR] = new JRadioButtonMenuItem("Distance Estimator");
        out_coloring_modes[MainWindow.DISTANCE_ESTIMATOR].setToolTipText("Sets the out-coloring method, using Distance Estimator.");
        out_coloring_modes[MainWindow.DISTANCE_ESTIMATOR].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.DISTANCE_ESTIMATOR);

            }
        });
        add(out_coloring_modes[MainWindow.DISTANCE_ESTIMATOR]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_ESCAPE_RADIUS] = new JRadioButtonMenuItem("Escape Time + Escape Radius");
        out_coloring_modes[MainWindow.ESCAPE_TIME_ESCAPE_RADIUS].setToolTipText("Sets the out-coloring method, using Escape Time + Escape Radius.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_ESCAPE_RADIUS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_ESCAPE_RADIUS);

            }
        });
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_ESCAPE_RADIUS]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_GRID] = new JRadioButtonMenuItem("Escape Time + Grid");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GRID].setToolTipText("Sets the out-coloring method, using Escape Time + Grid.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GRID].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_GRID);

            }
        });
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_GRID]);

        out_coloring_modes[MainWindow.ATOM_DOMAIN] = new JRadioButtonMenuItem("Atom Domain");
        out_coloring_modes[MainWindow.ATOM_DOMAIN].setToolTipText("Sets the out-coloring method, using atom domain.");
        out_coloring_modes[MainWindow.ATOM_DOMAIN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.ATOM_DOMAIN);

            }
        });
        add(out_coloring_modes[MainWindow.ATOM_DOMAIN]);

        out_coloring_modes[MainWindow.BANDED] = new JRadioButtonMenuItem("Banded");
        out_coloring_modes[MainWindow.BANDED].setToolTipText("Sets the out-coloring method, using an iteration based coloring.");
        out_coloring_modes[MainWindow.BANDED].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.BANDED);

            }
        });
        add(out_coloring_modes[MainWindow.BANDED]);

        out_coloring_modes[MainWindow.USER_OUTCOLORING_ALGORITHM] = new JRadioButtonMenuItem("User Out Coloring Method");
        out_coloring_modes[MainWindow.USER_OUTCOLORING_ALGORITHM].setToolTipText("A user defined out-coloring method.");
        out_coloring_modes[MainWindow.USER_OUTCOLORING_ALGORITHM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setOutColoringMode(MainWindow.USER_OUTCOLORING_ALGORITHM);

            }
        });
        add(out_coloring_modes[MainWindow.USER_OUTCOLORING_ALGORITHM]);
        
    }
    
    public JRadioButtonMenuItem[] getOutColoringModes() {
        
        return out_coloring_modes;
        
    }
    
}
