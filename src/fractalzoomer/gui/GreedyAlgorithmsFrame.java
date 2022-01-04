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

import fractalzoomer.core.ThreadDraw;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author kaloch
 */
public class GreedyAlgorithmsFrame extends JFrame {
	private static final long serialVersionUID = 6531106654109761330L;
	private Component ptra2;
    private JFrame this_frame;

    public GreedyAlgorithmsFrame(Component ptra, boolean greedy_algorithm, int greedy_algorithm_selection, int brute_force_alg) {

        super();
        ptra2 = ptra;
        this_frame = this;

        ptra2.setEnabled(false);
        int color_window_width = 800;
        int color_window_height = 340;
        setTitle("Greedy Drawing Algorithms");
        setSize(color_window_width, color_window_height);
        setIconImage(getIcon("/fractalzoomer/icons/greedy_algorithm.png").getImage());
        setLocation((int)(ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (color_window_width / 2), (int)(ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (color_window_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                ptra2.setEnabled(true);
                dispose();

            }
        });

        JPanel options_panel = new JPanel();
        options_panel.setPreferredSize(new Dimension(700, 160));
        options_panel.setBackground(MainWindow.bg_color);
        options_panel.setLayout(new FlowLayout());
        
        JPanel options_panel2 = new JPanel();
        options_panel2.setPreferredSize(new Dimension(680, 120));
        options_panel2.setBackground(MainWindow.bg_color);
        options_panel2.setLayout(new GridLayout(2, 1));
      

        JPanel greedy_algorithm_opt_panel = new JPanel();
        greedy_algorithm_opt_panel.setBackground(MainWindow.bg_color);

        final JCheckBox greedy_algorithm_opt = new JCheckBox("Greedy Drawing Algorithm");
        greedy_algorithm_opt.setFocusable(false);
        greedy_algorithm_opt.setToolTipText("Calculates only parts of the image based on greedy algorithms.");
        greedy_algorithm_opt.setBackground(MainWindow.bg_color);
        greedy_algorithm_opt.setSelected(greedy_algorithm);
        
        ComponentTitledBorder options_border = new ComponentTitledBorder(greedy_algorithm_opt, options_panel, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), this_frame);
        options_border.setChangeListener();
        
        options_panel.setBorder(options_border);

        ButtonGroup greedy_algorithm_button_group = new ButtonGroup();

        JPanel greedy_algorithm_panel = new JPanel();
        greedy_algorithm_panel.setBackground(MainWindow.bg_color);
        greedy_algorithm_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Algorithm", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        final JRadioButton boundary_tracing_opt = new JRadioButton("Boundary Tracing");
        boundary_tracing_opt.setFocusable(false);
        boundary_tracing_opt.setToolTipText("Calculates only the boundaries of the image.");
        boundary_tracing_opt.setBackground(MainWindow.bg_color);

        final JRadioButton divide_and_conquer_algorithm = new JRadioButton("Divide and Conquer");
        divide_and_conquer_algorithm.setFocusable(false);
        divide_and_conquer_algorithm.setToolTipText("Divides the image in halves and skips rectangles with the same boundary.");
        divide_and_conquer_algorithm.setBackground(MainWindow.bg_color);

        greedy_algorithm_button_group.add(boundary_tracing_opt);
        greedy_algorithm_button_group.add(divide_and_conquer_algorithm);

        if(greedy_algorithm_selection == MainWindow.BOUNDARY_TRACING) {
            boundary_tracing_opt.setSelected(true);
        }
        else if(greedy_algorithm_selection == MainWindow.DIVIDE_AND_CONQUER) {
            divide_and_conquer_algorithm.setSelected(true);
        }

        greedy_algorithm_panel.add(boundary_tracing_opt);
        greedy_algorithm_panel.add(divide_and_conquer_algorithm);

        JPanel coloring_option_panel = new JPanel();
        coloring_option_panel.setBackground(MainWindow.bg_color);
        coloring_option_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Skipped Pixels Coloring", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        final JRadioButton original_color = new JRadioButton("Original Color");
        original_color.setFocusable(false);
        original_color.setToolTipText("Uses the boundary color as the color for the skipped pixels.");
        original_color.setBackground(MainWindow.bg_color);

        final JRadioButton based_on_thread_id = new JRadioButton("Based on Thread");
        based_on_thread_id.setFocusable(false);
        based_on_thread_id.setToolTipText("Uses the color based on the thread as the color for the skipped pixels.");
        based_on_thread_id.setBackground(MainWindow.bg_color);

        final JRadioButton based_on_square_size = new JRadioButton("Based on Square size");
        based_on_square_size.setFocusable(false);
        based_on_square_size.setToolTipText("Uses the color based on the size of the square as the color for the skipped pixels.");
        based_on_square_size.setBackground(MainWindow.bg_color);

        final JRadioButton user_selected = new JRadioButton("User Selected");
        user_selected.setFocusable(false);
        user_selected.setToolTipText("Uses the color selected by the user as the color for the skipped pixels.");
        user_selected.setBackground(MainWindow.bg_color);

        final JRadioButton grid = new JRadioButton("White/Grey Grid");
        grid.setFocusable(false);
        grid.setToolTipText("Uses the transparent like background as the color for the skipped pixels.");
        grid.setBackground(MainWindow.bg_color);

        ButtonGroup coloring_button_group = new ButtonGroup();
        coloring_button_group.add(original_color);
        coloring_button_group.add(based_on_thread_id);
        coloring_button_group.add(based_on_square_size);
        coloring_button_group.add(user_selected);
        coloring_button_group.add(grid);

        if(ThreadDraw.SKIPPED_PIXELS_ALG == 0) {
            original_color.setSelected(true);
        }
        else if(ThreadDraw.SKIPPED_PIXELS_ALG == 1) {
            based_on_thread_id.setSelected(true);
        }
        else if(ThreadDraw.SKIPPED_PIXELS_ALG == 2) {
            user_selected.setSelected(true);
        }
        else if(ThreadDraw.SKIPPED_PIXELS_ALG == 3) {
            based_on_square_size.setSelected(true);
        }
        else if(ThreadDraw.SKIPPED_PIXELS_ALG == 4) {
            grid.setSelected(true);
        }

        coloring_option_panel.add(original_color);
        coloring_option_panel.add(based_on_thread_id);
        coloring_option_panel.add(based_on_square_size);
        coloring_option_panel.add(grid);
        coloring_option_panel.add(user_selected);

        final JLabel filter_color_label = new JLabel();
        coloring_option_panel.add(filter_color_label);

        filter_color_label.setPreferredSize(new Dimension(22, 22));
        filter_color_label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        filter_color_label.setBackground(new Color(ThreadDraw.SKIPPED_PIXELS_COLOR));
        filter_color_label.setOpaque(true);
        filter_color_label.setToolTipText("Set the user selected color.");

        filter_color_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(!filter_color_label.isEnabled()) {
                    return;
                }
                new ColorChooserFrame(this_frame, "Skipped Pixels Color", filter_color_label, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        JPanel panel3 = new JPanel();
        panel3.setBackground(MainWindow.bg_color);

        JComboBox brute_force_alg_opt = new JComboBox(new String[] {"Chunks", "Grid"});
        brute_force_alg_opt.setSelectedIndex(brute_force_alg);
        brute_force_alg_opt.setFocusable(false);
        brute_force_alg_opt.setToolTipText("Sets the brute force algorithm.");

        brute_force_alg_opt.setEnabled(!greedy_algorithm_opt.isSelected());

        greedy_algorithm_opt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                brute_force_alg_opt.setEnabled(!greedy_algorithm_opt.isSelected());
            }
        });

        panel3.add(new JLabel("Brute Force Algorithm: "));
        panel3.add(brute_force_alg_opt);

        JButton ok = new JButton("Ok");
        getRootPane().setDefaultButton(ok);
        ok.setFocusable(false);

        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ThreadDraw.SKIPPED_PIXELS_COLOR = filter_color_label.getBackground().getRGB();
                if(original_color.isSelected()) {
                    ThreadDraw.SKIPPED_PIXELS_ALG = 0;
                }
                else if(based_on_thread_id.isSelected()) {
                    ThreadDraw.SKIPPED_PIXELS_ALG = 1;
                }
                else if(user_selected.isSelected()) {
                    ThreadDraw.SKIPPED_PIXELS_ALG = 2;
                }
                else if(based_on_square_size.isSelected()) {
                    ThreadDraw.SKIPPED_PIXELS_ALG = 3;
                }
                else if(grid.isSelected()) {
                    ThreadDraw.SKIPPED_PIXELS_ALG = 4;
                }

                int algorithm = MainWindow.BOUNDARY_TRACING;
                if(boundary_tracing_opt.isSelected()) {
                    algorithm = MainWindow.BOUNDARY_TRACING;
                }
                else if(divide_and_conquer_algorithm.isSelected()) {
                    algorithm = MainWindow.DIVIDE_AND_CONQUER;
                }

                if(ptra2 instanceof MainWindow) {
                    ((MainWindow)ptra2).boundaryTracingOptionsChanged(greedy_algorithm_opt.isSelected(), algorithm, brute_force_alg_opt.getSelectedIndex());
                }
                else {
                    ((ImageExpanderWindow)ptra2).boundaryTracingOptionsChanged(greedy_algorithm_opt.isSelected(), algorithm,  brute_force_alg_opt.getSelectedIndex());
                }

                ptra2.setEnabled(true);
                dispose();

            }
        });

        JButton close = new JButton("Cancel");
        close.setFocusable(false);
        close.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptra2.setEnabled(true);
                dispose();

            }
        });

        options_panel2.add(greedy_algorithm_panel);
        options_panel2.add(coloring_option_panel);
        
        options_panel.add(options_panel2);

        options_border.setState(greedy_algorithm_opt.isSelected());
        
        JPanel buttons = new JPanel();
        buttons.setBackground(MainWindow.bg_color);

        buttons.add(ok);
        buttons.add(close);

        RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(730, 260));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(options_panel, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 1;

        round_panel.add(panel3, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 2;

        round_panel.add(buttons, con);

        JPanel main_panel = new JPanel();
        main_panel.setLayout(new GridBagLayout());
        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;
        main_panel.add(round_panel, con);

        JScrollPane scrollPane = new JScrollPane(main_panel);
        add(scrollPane);

        setVisible(true);
    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }

}
