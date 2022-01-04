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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 *
 * @author kaloch
 */
public class OptimizationsMenu extends JMenu {
	private static final long serialVersionUID = 6482967865390038590L;
	private MainWindow ptr;
    private JMenuItem greedy_algorithm_item;
    private JCheckBoxMenuItem periodicity_checking_opt;
    private JMenuItem quick_draw_opt;
    private JMenuItem thread_number;
    private JMenuItem perturbation_theory;
    private JCheckBoxMenuItem auto_repaint_image_opt;
    
    public OptimizationsMenu(MainWindow ptr2, String name) {

        super(name);

        this.ptr = ptr2;
        
        setIcon(getIcon("/fractalzoomer/icons/optimizations.png"));
        
        thread_number = new JMenuItem("Threads", getIcon("/fractalzoomer/icons/threads.png"));
        greedy_algorithm_item = new JMenuItem("Greedy Drawing Algorithms", getIcon("/fractalzoomer/icons/greedy_algorithm.png"));
        periodicity_checking_opt = new JCheckBoxMenuItem("Periodicity Checking");
        auto_repaint_image_opt  = new JCheckBoxMenuItem("Show Drawing Progress");
        perturbation_theory = new JMenuItem("Perturbation Theory", getIcon("/fractalzoomer/icons/perturbation.png"));

        quick_draw_opt = new JMenuItem("Quick Draw Tiles", getIcon("/fractalzoomer/icons/quickdraw.png"));
        
        thread_number.setToolTipText("Sets the number of parallel drawing threads.");
        quick_draw_opt.setToolTipText("Sets the tile size for the quick draw method.");
        periodicity_checking_opt.setToolTipText("Renders the image faster when containing a lot of bounded areas.");
        greedy_algorithm_item.setToolTipText("Sets the greedy algorithms options.");
        perturbation_theory.setToolTipText("Sets the perturbation theory settings.");
        
        thread_number.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        quick_draw_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.SHIFT_MASK));
        periodicity_checking_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        greedy_algorithm_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
        perturbation_theory.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        auto_repaint_image_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK | ActionEvent.SHIFT_MASK));
        
        thread_number.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setThreadsNumber();

            }
        });

        quick_draw_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setQuickDrawTiles();

            }
        });
        
        greedy_algorithm_item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setGreedyAlgorithms();

            }
        });

        periodicity_checking_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setPeriodicityChecking();

            }
        });

        auto_repaint_image_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setAutoRepaintImage();

            }
        });

        perturbation_theory.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setPerturbationTheory();

            }
        });

        
        add(thread_number);
        addSeparator();
        add(greedy_algorithm_item);
        addSeparator();
        add(perturbation_theory);
        addSeparator();
        add(periodicity_checking_opt);
        addSeparator();
        add(quick_draw_opt);
        addSeparator();
        add(auto_repaint_image_opt);
    }
    
    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }
    
    public JCheckBoxMenuItem getPeriodicityChecking() {
    
        return periodicity_checking_opt;
        
    }

    public JCheckBoxMenuItem getAutoRepaintImage() {

        return auto_repaint_image_opt;

    }
    
    public JMenuItem getGreedyAlgorithm() {
    
        return greedy_algorithm_item;
        
    }
}
