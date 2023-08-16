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

import fractalzoomer.core.TaskDraw;
import fractalzoomer.core.drawing_algorithms.CircularBruteForceDraw;
import fractalzoomer.core.drawing_algorithms.CircularSuccessiveRefinementGuessingDraw;
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
public class DrawingAlgorithmsFrame extends JFrame {
	private static final long serialVersionUID = 6531106654109761330L;
	private Component ptra2;
    private JFrame this_frame;
    private JRadioButton successive_refinement;
    private JRadioButton circular_successive_refinement;
    private JCheckBox greedy_algorithm_opt;

    private JComboBox<String> guessess;

    public DrawingAlgorithmsFrame(Component ptra, boolean greedy_algorithm, int greedy_algorithm_selection, int brute_force_alg, int guesses_selection) {

        super();
        ptra2 = ptra;
        this_frame = this;

        ptra2.setEnabled(false);
        int color_window_width = 800;
        int color_window_height = 520;
        setTitle("Drawing Algorithms");
        setSize(color_window_width, color_window_height);
        setIconImage(MainWindow.getIcon("greedy_algorithm.png").getImage());
        setLocation((int)(ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (color_window_width / 2), (int)(ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (color_window_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                ptra2.setEnabled(true);
                dispose();

            }
        });

        JPanel options_panel = new JPanel();
        options_panel.setPreferredSize(new Dimension(700, 260));
        options_panel.setBackground(MainWindow.bg_color);
        options_panel.setLayout(new FlowLayout());
        
        JPanel options_panel2 = new JPanel();
        options_panel2.setPreferredSize(new Dimension(680, 220));
        options_panel2.setBackground(MainWindow.bg_color);
        options_panel2.setLayout(new GridLayout(2, 1));
      

        JPanel greedy_algorithm_opt_panel = new JPanel();
        greedy_algorithm_opt_panel.setBackground(MainWindow.bg_color);

        greedy_algorithm_opt = new JCheckBox("Greedy Drawing Algorithm");
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

        final JRadioButton divide_and_conquer_algorithm = new JRadioButton("Mariani/Silver");
        divide_and_conquer_algorithm.setFocusable(false);
        divide_and_conquer_algorithm.setToolTipText("Divides the image in halves and skips rectangles with the same boundary.");
        divide_and_conquer_algorithm.setBackground(MainWindow.bg_color);

        successive_refinement = new JRadioButton("Successive Refinement");
        successive_refinement.setFocusable(false);
        successive_refinement.setToolTipText("Successively increases the resolution and performs guessing.");
        successive_refinement.setBackground(MainWindow.bg_color);

        circular_successive_refinement = new JRadioButton("Circular Successive Refinement");
        circular_successive_refinement.setFocusable(false);
        circular_successive_refinement.setToolTipText("Successively increases the resolution and performs guessing, using a circular rendering method.");
        circular_successive_refinement.setBackground(MainWindow.bg_color);

        final JCheckBox trace_iter_data = new JCheckBox("Use Iteration Data");
        trace_iter_data.setFocusable(false);
        trace_iter_data.setToolTipText("By enabling this option, the greedy algorithms will trace the iteration data and the image's colors.");
        trace_iter_data.setBackground(MainWindow.bg_color);
        trace_iter_data.setSelected(TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA);

        guessess = new JComboBox<>(new String[] {"No", "1x1", "<= 2x2", "<= 4x4", "<= 8x8", "<= 16x16", "<= 32x32", "<= 64x64"});
        guessess.setSelectedIndex(guesses_selection);
        guessess.setFocusable(false);
        guessess.setToolTipText("Sets the blocks to be guessed by the successive refinement algorithms.");

        greedy_algorithm_button_group.add(boundary_tracing_opt);
        greedy_algorithm_button_group.add(divide_and_conquer_algorithm);
        greedy_algorithm_button_group.add(successive_refinement);
        greedy_algorithm_button_group.add(circular_successive_refinement);

        circular_successive_refinement.addItemListener(e->guessess.setEnabled(circular_successive_refinement.isSelected() || successive_refinement.isSelected()));

        successive_refinement.addItemListener(e->guessess.setEnabled(circular_successive_refinement.isSelected() || successive_refinement.isSelected()));

        if(greedy_algorithm_selection == MainWindow.BOUNDARY_TRACING) {
            boundary_tracing_opt.setSelected(true);
        }
        else if(greedy_algorithm_selection == MainWindow.DIVIDE_AND_CONQUER) {
            divide_and_conquer_algorithm.setSelected(true);
        }
        else if(greedy_algorithm_selection == MainWindow.SUCCESSIVE_REFINEMENT) {
            successive_refinement.setSelected(true);
        }
        else if(greedy_algorithm_selection == MainWindow.CIRCULAR_SUCCESSIVE_REFINEMENT) {
            circular_successive_refinement.setSelected(true);
        }

        JPanel p1 = new JPanel();
        p1.setBackground(MainWindow.bg_color);
        p1.setLayout(new GridLayout(2, 2));
        p1.add(boundary_tracing_opt);
        p1.add(divide_and_conquer_algorithm);
        p1.add(successive_refinement);
        p1.add(circular_successive_refinement);

        greedy_algorithm_panel.add(p1);
        greedy_algorithm_panel.add(trace_iter_data);

        JPanel p2 = new JPanel();
        p2.setBackground(MainWindow.bg_color);
        p2.setLayout(new GridLayout(2, 1));

        JPanel p3 = new JPanel();
        p3.setBackground(MainWindow.bg_color);
        p3.add(guessess);

        JPanel p4 = new JPanel();
        p4.setBackground(MainWindow.bg_color);
        p4.add(new JLabel("Guess Blocks:"));
        p2.add(p4);
        p2.add(p3);

        greedy_algorithm_panel.add(p2);

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

        if(TaskDraw.SKIPPED_PIXELS_ALG == 0) {
            original_color.setSelected(true);
        }
        else if(TaskDraw.SKIPPED_PIXELS_ALG == 1) {
            based_on_thread_id.setSelected(true);
        }
        else if(TaskDraw.SKIPPED_PIXELS_ALG == 2) {
            user_selected.setSelected(true);
        }
        else if(TaskDraw.SKIPPED_PIXELS_ALG == 3) {
            based_on_square_size.setSelected(true);
        }
        else if(TaskDraw.SKIPPED_PIXELS_ALG == 4) {
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
        filter_color_label.setBackground(new Color(TaskDraw.SKIPPED_PIXELS_COLOR));
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
                new ColorChooserFrame(this_frame, "Skipped Pixels Color", filter_color_label, -1, -1);
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
        panel3.setPreferredSize(new Dimension(704, 60));

        JComboBox<String> brute_force_alg_opt = new JComboBox<>(new String[] {"Chunks", "Thread Split", "Circular Chunks"});
        brute_force_alg_opt.setSelectedIndex(brute_force_alg);
        brute_force_alg_opt.setFocusable(false);
        brute_force_alg_opt.setToolTipText("Sets the brute force algorithm.");

        brute_force_alg_opt.setEnabled(!greedy_algorithm_opt.isSelected());

        greedy_algorithm_opt.addActionListener(e -> brute_force_alg_opt.setEnabled(!greedy_algorithm_opt.isSelected()));

        panel3.add(new JLabel("Brute Force Algorithm: "));
        panel3.add(brute_force_alg_opt);

        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Brute Force Options", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));


        JPanel panel4 = new JPanel();
        panel4.setBackground(MainWindow.bg_color);
        panel4.setPreferredSize(new Dimension(704, 60));

        JComboBox<String> circular_pattern = new JComboBox<>(new String[] {"Circle", "Square", "Rhombus", "N-Norm", "Random", "Rectangle (Columns)", "Rectangle (Rows)", "Angle", "Double Angle", "Columns", "Rows", "Diagonal Top", "Diagonal Bottom", "Hourglass", "Star", "Cross"});
        circular_pattern.setSelectedIndex(TaskDraw.CIRCULAR_COMPARE_ALG);
        circular_pattern.setFocusable(false);
        circular_pattern.setToolTipText("Sets the circular drawing pattern.");

        JTextField nnorm = new JTextField(6);
        nnorm.setText("" + TaskDraw.CIRCULAR_N);
        nnorm.setEnabled(circular_pattern.getSelectedIndex() == 3);

        JCheckBox revert_pattern = new JCheckBox("Revert");
        revert_pattern.setBackground(MainWindow.bg_color);
        revert_pattern.setSelected(TaskDraw.CIRCULAR_REVERT_ALG);
        revert_pattern.setFocusable(false);
        revert_pattern.setToolTipText("Reverts the order of the pattern.");

        JCheckBox repeat_pattern = new JCheckBox("Repeat");
        repeat_pattern.setBackground(MainWindow.bg_color);
        repeat_pattern.setSelected(TaskDraw.CIRCULAR_REPEAT_ALG);
        repeat_pattern.setFocusable(false);
        repeat_pattern.setToolTipText("Repeats the pattern on a grid.");

//        JCheckBox pattern_follows_zoom_center = new JCheckBox("Follow Zoom Center");
//        pattern_follows_zoom_center.setBackground(MainWindow.bg_color);
//        pattern_follows_zoom_center.setSelected(MainWindow.CIRCULAR_DRAW_FOLLOWS_ZOOM_TO_CURSOR);
//        pattern_follows_zoom_center.setFocusable(false);
//        pattern_follows_zoom_center.setToolTipText("If zoom on mouse cursor is enabled then, by enabling this the drawing pattern will initiate from the selected point (Requires sorting every time).");

        revert_pattern.setEnabled(circular_pattern.getSelectedIndex() != 4);
        repeat_pattern.setEnabled(circular_pattern.getSelectedIndex() != 4);

//        pattern_follows_zoom_center.setEnabled(ptra instanceof MainWindow && circular_pattern.getSelectedIndex() != 4);

        circular_pattern.addActionListener(
                e -> {nnorm.setEnabled(circular_pattern.getSelectedIndex() == 3);
                revert_pattern.setEnabled(circular_pattern.getSelectedIndex() != 4);
                    repeat_pattern.setEnabled(circular_pattern.getSelectedIndex() != 4);
  //                  pattern_follows_zoom_center.setEnabled(ptra instanceof MainWindow && circular_pattern.getSelectedIndex() != 4);
        });

        panel4.add(new JLabel("Pattern: "));
        panel4.add(circular_pattern);
        panel4.add(new JLabel(" N-Norm: "));
        panel4.add(nnorm);
        panel4.add(repeat_pattern);
        panel4.add(revert_pattern);
       // panel4.add(pattern_follows_zoom_center);

        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Circular Pattern Options", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));


        JButton ok = new MyButton("Ok");
        getRootPane().setDefaultButton(ok);
        ok.setFocusable(false);

        ok.addActionListener(e -> {
            double temp;
            try {
                temp = Double.parseDouble(nnorm.getText());
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this_frame, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            TaskDraw.SKIPPED_PIXELS_COLOR = filter_color_label.getBackground().getRGB();
            if(original_color.isSelected()) {
                TaskDraw.SKIPPED_PIXELS_ALG = 0;
            }
            else if(based_on_thread_id.isSelected()) {
                TaskDraw.SKIPPED_PIXELS_ALG = 1;
            }
            else if(user_selected.isSelected()) {
                TaskDraw.SKIPPED_PIXELS_ALG = 2;
            }
            else if(based_on_square_size.isSelected()) {
                TaskDraw.SKIPPED_PIXELS_ALG = 3;
            }
            else if(grid.isSelected()) {
                TaskDraw.SKIPPED_PIXELS_ALG = 4;
            }

            int algorithm = MainWindow.BOUNDARY_TRACING;
            if(boundary_tracing_opt.isSelected()) {
                algorithm = MainWindow.BOUNDARY_TRACING;
            }
            else if(divide_and_conquer_algorithm.isSelected()) {
                algorithm = MainWindow.DIVIDE_AND_CONQUER;
            }
            else if(successive_refinement.isSelected()) {
                algorithm = MainWindow.SUCCESSIVE_REFINEMENT;
            }
            else if(circular_successive_refinement.isSelected()) {
                algorithm = MainWindow.CIRCULAR_SUCCESSIVE_REFINEMENT;
            }

            TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA = trace_iter_data.isSelected();
            TaskDraw.GUESS_BLOCKS_SELECTION = guessess.getSelectedIndex();

            int oldCircularAlg = TaskDraw.CIRCULAR_COMPARE_ALG;
            TaskDraw.CIRCULAR_COMPARE_ALG = circular_pattern.getSelectedIndex();
            if(oldCircularAlg != TaskDraw.CIRCULAR_COMPARE_ALG) {
                CircularBruteForceDraw.coordinates = null;
                CircularBruteForceDraw.coordinatesFastJulia = null;
                CircularSuccessiveRefinementGuessingDraw.CoordinatesPerLevel = null;
                CircularSuccessiveRefinementGuessingDraw.CoordinatesPerLevelFastJulia = null;
            }

            boolean oldRevertPattern = TaskDraw.CIRCULAR_REVERT_ALG;
            TaskDraw.CIRCULAR_REVERT_ALG = revert_pattern.isSelected();
            if(oldRevertPattern != TaskDraw.CIRCULAR_REVERT_ALG) {
                CircularBruteForceDraw.coordinates = null;
                CircularBruteForceDraw.coordinatesFastJulia = null;
                CircularSuccessiveRefinementGuessingDraw.CoordinatesPerLevel = null;
                CircularSuccessiveRefinementGuessingDraw.CoordinatesPerLevelFastJulia = null;
            }

            boolean oldRepeatPattern = TaskDraw.CIRCULAR_REPEAT_ALG;
            TaskDraw.CIRCULAR_REPEAT_ALG = repeat_pattern.isSelected();
            if(oldRepeatPattern != TaskDraw.CIRCULAR_REPEAT_ALG) {
                CircularBruteForceDraw.coordinates = null;
                CircularBruteForceDraw.coordinatesFastJulia = null;
                CircularSuccessiveRefinementGuessingDraw.CoordinatesPerLevel = null;
                CircularSuccessiveRefinementGuessingDraw.CoordinatesPerLevelFastJulia = null;
            }

            double oldNnorm = TaskDraw.CIRCULAR_N;
            TaskDraw.CIRCULAR_N = temp;
            if(TaskDraw.CIRCULAR_N != oldNnorm) {
                CircularBruteForceDraw.coordinates = null;
                CircularBruteForceDraw.coordinatesFastJulia = null;
                CircularSuccessiveRefinementGuessingDraw.CoordinatesPerLevel = null;
                CircularSuccessiveRefinementGuessingDraw.CoordinatesPerLevelFastJulia = null;
            }

//            boolean oldFollowZoom = MainWindow.CIRCULAR_DRAW_FOLLOWS_ZOOM_TO_CURSOR;
//            MainWindow.CIRCULAR_DRAW_FOLLOWS_ZOOM_TO_CURSOR = pattern_follows_zoom_center.isSelected();
//            if(MainWindow.CIRCULAR_DRAW_FOLLOWS_ZOOM_TO_CURSOR != oldFollowZoom) {
//                CircularBruteForceDraw.coordinates = null;
//                CircularBruteForceDraw.coordinatesFastJulia = null;
//            CircularSuccessiveRefinementGuessingDraw.CoordinatesPerLevel = null;
//            CircularSuccessiveRefinementGuessingDraw.CoordinatesPerLevelFastJulia = null;
//            }

            if(ptra2 instanceof MainWindow) {
                ((MainWindow)ptra2).boundaryTracingOptionsChanged(greedy_algorithm_opt.isSelected(), algorithm, brute_force_alg_opt.getSelectedIndex());
            }
            else {
                ((ImageExpanderWindow)ptra2).boundaryTracingOptionsChanged(greedy_algorithm_opt.isSelected(), algorithm,  brute_force_alg_opt.getSelectedIndex());
            }

            ptra2.setEnabled(true);
            dispose();

        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Ok");
        getRootPane().getActionMap().put("Ok", new AbstractAction()
        {

            public void actionPerformed(ActionEvent e)
            {
                ok.doClick();
            }
        });

        JButton close = new MyButton("Cancel");
        close.setFocusable(false);
        close.addActionListener(e -> {

            ptra2.setEnabled(true);
            dispose();

        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
        getRootPane().getActionMap().put("Cancel", new AbstractAction()
        {

            public void actionPerformed(ActionEvent e)
            {
                close.doClick();
            }
        });

        options_panel2.add(greedy_algorithm_panel);
        options_panel2.add(coloring_option_panel);
        
        options_panel.add(options_panel2);

        options_border.setState(greedy_algorithm_opt.isSelected());

        guessess.setEnabled(greedy_algorithm_opt.isSelected() && (circular_successive_refinement.isSelected() || successive_refinement.isSelected()));
        
        JPanel buttons = new JPanel();
        buttons.setBackground(MainWindow.bg_color);

        buttons.add(ok);
        buttons.add(close);

        RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(730, 440));
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

        round_panel.add(panel4, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 3;

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

    public void toggled(boolean toggled) {

        if (!toggled) {
            return;
        }

        guessess.setEnabled(greedy_algorithm_opt.isSelected() && (circular_successive_refinement.isSelected() || successive_refinement.isSelected()));
    }

}
