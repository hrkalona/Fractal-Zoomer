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

    private JMenuItem high_precision;
    private JCheckBoxMenuItem auto_repaint_image_opt;
    
    public OptimizationsMenu(MainWindow ptr2, String name) {

        super(name);

        this.ptr = ptr2;
        
        setIcon(MainWindow.getIcon("optimizations.png"));
        
        thread_number = new JMenuItem("Threads", MainWindow.getIcon("threads.png"));
        greedy_algorithm_item = new JMenuItem("Greedy Drawing Algorithms", MainWindow.getIcon("greedy_algorithm.png"));
        periodicity_checking_opt = new JCheckBoxMenuItem("Periodicity Checking");
        auto_repaint_image_opt  = new JCheckBoxMenuItem("Show Drawing Progress");
        perturbation_theory = new JMenuItem("Perturbation Theory", MainWindow.getIcon("perturbation.png"));
        high_precision = new JMenuItem("High Precision", MainWindow.getIcon("high_precision.png"));

        quick_draw_opt = new JMenuItem("Quick Draw", MainWindow.getIcon("quickdraw.png"));
        
        thread_number.setToolTipText("Sets the number of parallel drawing threads.");
        quick_draw_opt.setToolTipText("Sets the options for the quick draw method.");
        periodicity_checking_opt.setToolTipText("Renders the image faster when containing a lot of bounded areas.");
        greedy_algorithm_item.setToolTipText("Sets the greedy algorithms options.");
        perturbation_theory.setToolTipText("Sets the perturbation theory settings.");
        high_precision.setToolTipText("Sets the use of high precision calculation for all pixels and for only for supported fractals.");
        
        thread_number.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        quick_draw_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.SHIFT_MASK));
        periodicity_checking_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        greedy_algorithm_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
        perturbation_theory.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        auto_repaint_image_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK | ActionEvent.SHIFT_MASK));
        high_precision.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK | ActionEvent.SHIFT_MASK));
        
        thread_number.addActionListener(e -> ptr.setThreadsNumber());

        quick_draw_opt.addActionListener(e -> ptr.setQuickDrawTiles());
        
        greedy_algorithm_item.addActionListener(e -> ptr.setGreedyAlgorithms());

        periodicity_checking_opt.addActionListener(e -> ptr.setPeriodicityChecking());

        auto_repaint_image_opt.addActionListener(e -> ptr.setAutoRepaintImage());

        perturbation_theory.addActionListener(e -> ptr.setPerturbationTheory());

        high_precision.addActionListener(e -> ptr.setHighPrecision());

        
        add(thread_number);
        addSeparator();
        add(greedy_algorithm_item);
        addSeparator();
        add(perturbation_theory);
        addSeparator();
        add(high_precision);
        addSeparator();
        add(periodicity_checking_opt);
        addSeparator();
        add(quick_draw_opt);
        addSeparator();
        add(auto_repaint_image_opt);
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
