
package fractalzoomer.gui;

import fractalzoomer.core.TaskRender;
import fractalzoomer.core.rendering_algorithms.PatternedBruteForceRender;
import fractalzoomer.core.rendering_algorithms.QueueBasedRender;
import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author kaloch
 */
public class RenderingAlgorithmsDialog extends JDialog {
	private static final long serialVersionUID = 6531106654109761330L;
	private Component ptra2;
    private JRadioButton successive_refinement;
    private JRadioButton patterned_successive_refinement;

    private JRadioButton mariani_silver_algorithm;
    private JCheckBox greedy_algorithm_opt;
    private JCheckBox rectangle_areas_splitting_opt;
    private JComboBox<String> mix_squares_and_rectangles;

    private JComboBox<String> rectangle_area_splitting_algorithms;

    private JComboBox<String> guessess;
    private JCheckBox twoPassSuccessiveRefinement;
    private JCheckBox workSteal;

    private JDialog this_dialog;

    private JTextField x_split;
    private JTextField y_split;

    public RenderingAlgorithmsDialog(Component ptra) {

        super();
        ptra2 = ptra;

        this_dialog = this;

        setModal(true);
        int color_window_width = 800;
        int color_window_height = 570;
        setTitle("Rendering Algorithms");
        setSize(color_window_width, color_window_height);
        setIconImage(MainWindow.getIcon("rendering_algorithm.png").getImage());
        setLocation((int)(ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (color_window_width / 2), (int)(ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (color_window_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                dispose();

            }
        });

        JPanel options_panel = new JPanel();
        options_panel.setPreferredSize(new Dimension(700, 240));
        options_panel.setBackground(MainWindow.bg_color);
        options_panel.setLayout(new FlowLayout());
        
        JPanel options_panel2 = new JPanel();
        options_panel2.setPreferredSize(new Dimension(680, 200));
        options_panel2.setBackground(MainWindow.bg_color);
      

        JPanel greedy_algorithm_opt_panel = new JPanel();
        greedy_algorithm_opt_panel.setBackground(MainWindow.bg_color);

        greedy_algorithm_opt = new JCheckBox("Greedy Rendering Algorithm");
        greedy_algorithm_opt.setFocusable(false);
        greedy_algorithm_opt.setToolTipText("Calculates only parts of the image based on greedy algorithms.");
        greedy_algorithm_opt.setBackground(MainWindow.bg_color);
        greedy_algorithm_opt.setSelected(TaskRender.GREEDY_ALGORITHM);
        
        ComponentTitledBorder options_border = new ComponentTitledBorder(greedy_algorithm_opt, options_panel, LAFManager.createUnTitledBorder(), this_dialog);
        options_border.setChangeListener();
        
        options_panel.setBorder(options_border);

        ButtonGroup greedy_algorithm_button_group = new ButtonGroup();

        JPanel greedy_algorithm_panel = new JPanel();
        greedy_algorithm_panel.setBackground(MainWindow.bg_color);
        greedy_algorithm_panel.setBorder(LAFManager.createTitledBorder( "Algorithm"));
        greedy_algorithm_panel.setPreferredSize(new Dimension(670, 130));

        final JRadioButton boundary_tracing_opt = new JRadioButton("Boundary Tracing");
        boundary_tracing_opt.setFocusable(false);
        boundary_tracing_opt.setToolTipText("Calculates only the boundaries of the image.");
        boundary_tracing_opt.setBackground(MainWindow.bg_color);

        mariani_silver_algorithm = new JRadioButton("Mariani/Silver");
        mariani_silver_algorithm.setFocusable(false);
        mariani_silver_algorithm.setToolTipText("Divides the image in halves and skips rectangles with the same boundary.");
        mariani_silver_algorithm.setBackground(MainWindow.bg_color);

        successive_refinement = new JRadioButton("Successive Refinement");
        successive_refinement.setFocusable(false);
        successive_refinement.setToolTipText("Successively increases the resolution and performs guessing.");
        successive_refinement.setBackground(MainWindow.bg_color);

        patterned_successive_refinement = new JRadioButton("Patterned Successive Refinement");
        patterned_successive_refinement.setFocusable(false);
        patterned_successive_refinement.setToolTipText("Successively increases the resolution and performs guessing, using a patterned rendering method.");
        patterned_successive_refinement.setBackground(MainWindow.bg_color);

        final JCheckBox trace_iter_data = new JCheckBox("Use Iteration Data");
        trace_iter_data.setFocusable(false);
        trace_iter_data.setToolTipText("By enabling this option, the greedy algorithms will trace the iteration data and the image's colors.");
        trace_iter_data.setBackground(MainWindow.bg_color);
        trace_iter_data.setSelected(TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA);

        mix_squares_and_rectangles = new JComboBox<>(new String[]{"Square", "Square/Rectangle H", "Square/Rectangle V", "Square/Rectangle HV", "Square/Rectangle VH"});
        mix_squares_and_rectangles.setFocusable(false);
        mix_squares_and_rectangles.setToolTipText("Configure how the successive refinement algorithm will split the area.");
        mix_squares_and_rectangles.setBackground(MainWindow.bg_color);
        mix_squares_and_rectangles.setSelectedIndex(TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM);

        twoPassSuccessiveRefinement = new JCheckBox("2 Steps");
        twoPassSuccessiveRefinement.setFocusable(false);
        twoPassSuccessiveRefinement.setToolTipText("Changes the successive refinement algorithms to use two steps.");
        twoPassSuccessiveRefinement.setSelected(TaskRender.TWO_PASS_SUCCESSIVE_REFINEMENT);
        twoPassSuccessiveRefinement.setBackground(MainWindow.bg_color);

        workSteal = new JCheckBox("Work Steal");
        workSteal.setFocusable(false);
        workSteal.setToolTipText("Enables the use of work stealing between threads.");
        workSteal.setSelected(QueueBasedRender.WORK_STEALING_ENABLED);
        workSteal.setBackground(MainWindow.bg_color);

        guessess = new JComboBox<>(new String[] {"No", "1x", "≤ 2x", "≤ 4x", "≤ 8x", "≤ 16x", "≤ 32x", "≤ 64x"});
        guessess.setSelectedIndex(TaskRender.GUESS_BLOCKS_SELECTION);
        guessess.setFocusable(false);
        guessess.setToolTipText("Sets the blocks to be guessed by the successive refinement algorithms.");

        greedy_algorithm_button_group.add(boundary_tracing_opt);
        greedy_algorithm_button_group.add(mariani_silver_algorithm);
        greedy_algorithm_button_group.add(successive_refinement);
        greedy_algorithm_button_group.add(patterned_successive_refinement);

        patterned_successive_refinement.addItemListener(e->
        {
            guessess.setEnabled(patterned_successive_refinement.isSelected() || successive_refinement.isSelected());
            mix_squares_and_rectangles.setEnabled(mariani_silver_algorithm.isSelected() || successive_refinement.isSelected() || patterned_successive_refinement.isSelected());
            twoPassSuccessiveRefinement.setEnabled(patterned_successive_refinement.isSelected() || successive_refinement.isSelected());
            workSteal.setEnabled(mariani_silver_algorithm.isSelected());
        });

        successive_refinement.addItemListener(e-> {
                    guessess.setEnabled(patterned_successive_refinement.isSelected() || successive_refinement.isSelected());
                    mix_squares_and_rectangles.setEnabled(mariani_silver_algorithm.isSelected() || successive_refinement.isSelected() || patterned_successive_refinement.isSelected());
                    twoPassSuccessiveRefinement.setEnabled(patterned_successive_refinement.isSelected() || successive_refinement.isSelected());
                    workSteal.setEnabled(mariani_silver_algorithm.isSelected());
        });

        mariani_silver_algorithm.addItemListener(e-> {
                    guessess.setEnabled(patterned_successive_refinement.isSelected() || successive_refinement.isSelected());
                    mix_squares_and_rectangles.setEnabled(mariani_silver_algorithm.isSelected() || successive_refinement.isSelected() || patterned_successive_refinement.isSelected());
                    twoPassSuccessiveRefinement.setEnabled(patterned_successive_refinement.isSelected() || successive_refinement.isSelected());
                    workSteal.setEnabled(mariani_silver_algorithm.isSelected());
        });

        boundary_tracing_opt.addItemListener(e-> {
            guessess.setEnabled(patterned_successive_refinement.isSelected() || successive_refinement.isSelected());
            mix_squares_and_rectangles.setEnabled(mariani_silver_algorithm.isSelected() || successive_refinement.isSelected() || patterned_successive_refinement.isSelected());
            twoPassSuccessiveRefinement.setEnabled(patterned_successive_refinement.isSelected() || successive_refinement.isSelected());
            workSteal.setEnabled(mariani_silver_algorithm.isSelected());
        });

        if(TaskRender.GREEDY_ALGORITHM_SELECTION == MainWindow.BOUNDARY_TRACING) {
            boundary_tracing_opt.setSelected(true);
        }
        else if(TaskRender.GREEDY_ALGORITHM_SELECTION == MainWindow.MARIANI_SILVER) {
            mariani_silver_algorithm.setSelected(true);
        }
        else if(TaskRender.GREEDY_ALGORITHM_SELECTION == MainWindow.SUCCESSIVE_REFINEMENT) {
            successive_refinement.setSelected(true);
        }
        else if(TaskRender.GREEDY_ALGORITHM_SELECTION == MainWindow.PATTERNED_SUCCESSIVE_REFINEMENT) {
            patterned_successive_refinement.setSelected(true);
        }

        JPanel p1 = new JPanel();
        p1.setBackground(MainWindow.bg_color);
        p1.setLayout(new GridLayout(4, 1));
        p1.add(boundary_tracing_opt);
        p1.add(mariani_silver_algorithm);
        p1.add(successive_refinement);
        p1.add(patterned_successive_refinement);

        greedy_algorithm_panel.add(p1);



        JPanel p11 = new JPanel();
        p11.setBackground(MainWindow.bg_color);
        p11.add(mix_squares_and_rectangles);

        JPanel p7 = new JPanel();
        p7.setBackground(MainWindow.bg_color);
        p7.setLayout(new GridLayout(3, 1));
        p7.add(trace_iter_data);
        p7.add(twoPassSuccessiveRefinement);
        p7.add(workSteal);


        greedy_algorithm_panel.add(p7);

        JPanel p2 = new JPanel();
        p2.setBackground(MainWindow.bg_color);
        p2.setLayout(new GridLayout(3, 1));

        JPanel p3 = new JPanel();
        p3.setBackground(MainWindow.bg_color);
        p3.add(guessess);

        JPanel p4 = new JPanel();
        p4.setBackground(MainWindow.bg_color);
        p4.add(new JLabel("Guess Blocks:"));
        p2.add(p4);
        p2.add(p3);
        p2.add(p11);

        greedy_algorithm_panel.add(p2);

        JPanel coloring_option_panel = new JPanel();
        coloring_option_panel.setBackground(MainWindow.bg_color);
        coloring_option_panel.setBorder(LAFManager.createTitledBorder("Skipped Pixels Coloring"));
        coloring_option_panel.setPreferredSize(new Dimension(670, 60));

        final JRadioButton original_color = new JRadioButton("Original Color");
        original_color.setFocusable(false);
        original_color.setToolTipText("Uses the boundary color as the color for the skipped pixels.");
        original_color.setBackground(MainWindow.bg_color);

        final JRadioButton based_on_thread_id = new JRadioButton("Based on Task");
        based_on_thread_id.setFocusable(false);
        based_on_thread_id.setToolTipText("Uses the color based on the task as the color for the skipped pixels.");
        based_on_thread_id.setBackground(MainWindow.bg_color);

        final JRadioButton based_on_square_size = new JRadioButton("Based on Pixel Group");
        based_on_square_size.setFocusable(false);
        based_on_square_size.setToolTipText("Uses the color based on the pixel grouping as the color for the skipped pixels.");
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

        if(TaskRender.SKIPPED_PIXELS_ALG == 0) {
            original_color.setSelected(true);
        }
        else if(TaskRender.SKIPPED_PIXELS_ALG == 1) {
            based_on_thread_id.setSelected(true);
        }
        else if(TaskRender.SKIPPED_PIXELS_ALG == 2) {
            user_selected.setSelected(true);
        }
        else if(TaskRender.SKIPPED_PIXELS_ALG == 3) {
            based_on_square_size.setSelected(true);
        }
        else if(TaskRender.SKIPPED_PIXELS_ALG == 4) {
            grid.setSelected(true);
        }

        coloring_option_panel.add(original_color);
        coloring_option_panel.add(based_on_thread_id);
        coloring_option_panel.add(based_on_square_size);
        coloring_option_panel.add(grid);
        coloring_option_panel.add(user_selected);

        final JLabel filter_color_label = new ColorLabel();
        coloring_option_panel.add(filter_color_label);

        filter_color_label.setPreferredSize(new Dimension(22, 22));
        filter_color_label.setBackground(new Color(TaskRender.SKIPPED_PIXELS_COLOR));
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
                new ColorChooserDialog(this_dialog, "Skipped Pixels Color", filter_color_label, -1, -1);
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

        JComboBox<String> brute_force_alg_opt = new JComboBox<>(new String[] {"Chunks", "Thread Split", "Patterned Chunks", "Interleaved"});
        brute_force_alg_opt.setSelectedIndex(TaskRender.BRUTE_FORCE_ALG);
        brute_force_alg_opt.setFocusable(false);
        brute_force_alg_opt.setToolTipText("Sets the brute force algorithm.");

        brute_force_alg_opt.setEnabled(!greedy_algorithm_opt.isSelected());

        JCheckBox chunksPerRow = new JCheckBox("1 Chunk per row");
        chunksPerRow.setFocusable(false);
        chunksPerRow.setSelected(TaskRender.CHUNK_SIZE_PER_ROW);
        chunksPerRow.setBackground(MainWindow.bg_color);

        chunksPerRow.setEnabled(!greedy_algorithm_opt.isSelected() && brute_force_alg_opt.getSelectedIndex() == 0);

        greedy_algorithm_opt.addActionListener(e -> {
            brute_force_alg_opt.setEnabled(!greedy_algorithm_opt.isSelected());
            chunksPerRow.setEnabled(!greedy_algorithm_opt.isSelected() && brute_force_alg_opt.getSelectedIndex() == 0);
        }
        );

        brute_force_alg_opt.addActionListener(e -> chunksPerRow.setEnabled(!greedy_algorithm_opt.isSelected() && brute_force_alg_opt.getSelectedIndex() == 0));

        panel3.add(new JLabel("Brute Force Algorithm: "));
        panel3.add(brute_force_alg_opt);
        panel3.add(chunksPerRow);

        panel3.setBorder( LAFManager.createTitledBorder( "Brute Force Options"));


        JPanel panel4 = new JPanel();
        panel4.setBackground(MainWindow.bg_color);
        panel4.setPreferredSize(new Dimension(704, 60));

        JComboBox<String> pattern = new JComboBox<>(new String[] {"Circle", "Square", "Rhombus", "N-Norm", "Random", "Rectangle (Columns)", "Rectangle (Rows)", "Angle", "Double Angle", "Columns", "Rows", "Diagonal Top", "Diagonal Bottom", "Hourglass", "Star", "Cross", "Interleaved", "Z-Order Curve", "Hilbert Curve", "Peano Curve", "Zig-Zag Curve", "GrayCode Curve", "Peano-Meander Curve", "Original"});
        pattern.setSelectedIndex(TaskRender.PATTERN_COMPARE_ALG);
        pattern.setFocusable(false);
        pattern.setToolTipText("Sets the rendering pattern.");

        JTextField nnorm = new JTextField(6);
        nnorm.setText("" + TaskRender.PATTERN_N);
        nnorm.setEnabled(pattern.getSelectedIndex() == 3);

        JCheckBox revert_pattern = new JCheckBox("Revert");
        revert_pattern.setBackground(MainWindow.bg_color);
        revert_pattern.setSelected(TaskRender.PATTERN_REVERT_ALG);
        revert_pattern.setFocusable(false);
        revert_pattern.setToolTipText("Reverts the order of the pattern.");

        JCheckBox repeat_pattern = new JCheckBox("Repeat");
        repeat_pattern.setBackground(MainWindow.bg_color);
        repeat_pattern.setSelected(TaskRender.PATTERN_REPEAT_ALG);
        repeat_pattern.setFocusable(false);
        repeat_pattern.setToolTipText("Repeats the pattern on a grid.");

        JCheckBox centered_pattern = new JCheckBox("Centered");
        centered_pattern.setBackground(MainWindow.bg_color);
        centered_pattern.setSelected(TaskRender.PATTERN_CENTER);
        centered_pattern.setFocusable(false);
        centered_pattern.setToolTipText("Centers the pattern.");

//        JCheckBox pattern_follows_zoom_center = new JCheckBox("Follow Zoom Center");
//        pattern_follows_zoom_center.setBackground(MainWindow.bg_color);
//        pattern_follows_zoom_center.setSelected(MainWindow.PATTERNED_RENDER_FOLLOWS_ZOOM_TO_CURSOR);
//        pattern_follows_zoom_center.setFocusable(false);
//        pattern_follows_zoom_center.setToolTipText("If zoom on mouse cursor is enabled then, by enabling this the rendering pattern will initiate from the selected point (Requires sorting every time).");

        revert_pattern.setEnabled(pattern.getSelectedIndex() != 4 && pattern.getSelectedIndex() != 16 && pattern.getSelectedIndex() != 17 && pattern.getSelectedIndex() != 18 && pattern.getSelectedIndex() != 19 && pattern.getSelectedIndex() != 20 && pattern.getSelectedIndex() != 21 && pattern.getSelectedIndex() != 22 && pattern.getSelectedIndex() != 23);
        repeat_pattern.setEnabled(pattern.getSelectedIndex() != 4 && pattern.getSelectedIndex() != 16 && pattern.getSelectedIndex() != 17 && pattern.getSelectedIndex() != 18 && pattern.getSelectedIndex() != 19 && pattern.getSelectedIndex() != 20 && pattern.getSelectedIndex() != 21 && pattern.getSelectedIndex() != 22 && pattern.getSelectedIndex() != 23);

        centered_pattern.setEnabled(pattern.getSelectedIndex() == 17 || pattern.getSelectedIndex() == 18 || pattern.getSelectedIndex() == 19 || pattern.getSelectedIndex() == 20 || pattern.getSelectedIndex() == 21 || pattern.getSelectedIndex() == 22);
//        pattern_follows_zoom_center.setEnabled(ptra instanceof MainWindow && circular_pattern.getSelectedIndex() != 4);

        pattern.addActionListener(
                e -> {nnorm.setEnabled(pattern.getSelectedIndex() == 3);
                revert_pattern.setEnabled(pattern.getSelectedIndex() != 4 && pattern.getSelectedIndex() != 16 && pattern.getSelectedIndex() != 17 && pattern.getSelectedIndex() != 18 && pattern.getSelectedIndex() != 19 && pattern.getSelectedIndex() != 20 && pattern.getSelectedIndex() != 21 && pattern.getSelectedIndex() != 22 && pattern.getSelectedIndex() != 23);
                    repeat_pattern.setEnabled(pattern.getSelectedIndex() != 4 && pattern.getSelectedIndex() != 16 && pattern.getSelectedIndex() != 17 && pattern.getSelectedIndex() != 18 && pattern.getSelectedIndex() != 19 && pattern.getSelectedIndex() != 20 && pattern.getSelectedIndex() != 21 && pattern.getSelectedIndex() != 22 && pattern.getSelectedIndex() != 23);
  //                  pattern_follows_zoom_center.setEnabled(ptra instanceof MainWindow && circular_pattern.getSelectedIndex() != 4);
                    centered_pattern.setEnabled(pattern.getSelectedIndex() == 17 || pattern.getSelectedIndex() == 18 || pattern.getSelectedIndex() == 19 || pattern.getSelectedIndex() == 20 || pattern.getSelectedIndex() == 21 || pattern.getSelectedIndex() == 22);
        });

        panel4.add(new JLabel("Pattern: "));
        panel4.add(pattern);
        panel4.add(new JLabel(" N-Norm: "));
        panel4.add(nnorm);
        panel4.add(repeat_pattern);
        panel4.add(revert_pattern);
        panel4.add(centered_pattern);
       // panel4.add(pattern_follows_zoom_center);

        panel4.setBorder( LAFManager.createTitledBorder( "Pattern Options"));

        JPanel panel5 = new JPanel();
        panel5.setBackground(MainWindow.bg_color);
        panel5.setPreferredSize(new Dimension(704, 60));

        rectangle_area_splitting_algorithms = new JComboBox<>(new String[]{"Rectangles of x*y pixels", "Grid x,y on thread split", "Grid x,y on image"});
        rectangle_area_splitting_algorithms.setFocusable(false);
        rectangle_area_splitting_algorithms.setToolTipText("Select the splitting algorithm.");
        rectangle_area_splitting_algorithms.setBackground(MainWindow.bg_color);
        rectangle_area_splitting_algorithms.setSelectedIndex(TaskRender.RECTANGLE_AREA_SPLIT_ALGORITHM);

        x_split = new JTextField(6);
        x_split.setText("" + TaskRender.AREA_DIMENSION_X);

        y_split = new JTextField(6);
        y_split.setText("" + TaskRender.AREA_DIMENSION_Y);

        rectangle_areas_splitting_opt = new JCheckBox("Rectangle Areas Splitting");
        rectangle_areas_splitting_opt.setFocusable(false);
        rectangle_areas_splitting_opt.setToolTipText("Adds the option to further split the rectangular areas.");
        rectangle_areas_splitting_opt.setBackground(MainWindow.bg_color);
        rectangle_areas_splitting_opt.setSelected(TaskRender.SPLIT_INTO_RECTANGLE_AREAS);

        panel5.add(new JLabel("Algorithm: "));
        panel5.add(rectangle_area_splitting_algorithms);
        panel5.add(new JLabel(" X: "));
        panel5.add(x_split);
        panel5.add(new JLabel(" Y: "));
        panel5.add(y_split);

        ComponentTitledBorder rectangle_areas_border = new ComponentTitledBorder(rectangle_areas_splitting_opt, panel5, LAFManager.createUnTitledBorder(), this_dialog);
        rectangle_areas_border.setChangeListener();

        panel5.setBorder(rectangle_areas_border);

        options_panel.setBorder(options_border);


        JButton ok = new MyButton("Ok");
        getRootPane().setDefaultButton(ok);
        ok.setFocusable(false);

        ok.addActionListener(e -> {
            double temp;
            int temp3, temp4;
            try {
                temp = Double.parseDouble(nnorm.getText());
                temp3 = Integer.parseInt(x_split.getText());
                temp4 = Integer.parseInt(y_split.getText());
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this_dialog, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(temp3 <= 0 || temp4 <= 0 ) {
                JOptionPane.showMessageDialog(ptra, "X and Y values must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            TaskRender.SKIPPED_PIXELS_COLOR = filter_color_label.getBackground().getRGB();
            if(original_color.isSelected()) {
                TaskRender.SKIPPED_PIXELS_ALG = 0;
            }
            else if(based_on_thread_id.isSelected()) {
                TaskRender.SKIPPED_PIXELS_ALG = 1;
            }
            else if(user_selected.isSelected()) {
                TaskRender.SKIPPED_PIXELS_ALG = 2;
            }
            else if(based_on_square_size.isSelected()) {
                TaskRender.SKIPPED_PIXELS_ALG = 3;
            }
            else if(grid.isSelected()) {
                TaskRender.SKIPPED_PIXELS_ALG = 4;
            }

            int algorithm = MainWindow.BOUNDARY_TRACING;
            if(boundary_tracing_opt.isSelected()) {
                algorithm = MainWindow.BOUNDARY_TRACING;
            }
            else if(mariani_silver_algorithm.isSelected()) {
                algorithm = MainWindow.MARIANI_SILVER;
            }
            else if(successive_refinement.isSelected()) {
                algorithm = MainWindow.SUCCESSIVE_REFINEMENT;
            }
            else if(patterned_successive_refinement.isSelected()) {
                algorithm = MainWindow.PATTERNED_SUCCESSIVE_REFINEMENT;
            }

            TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA = trace_iter_data.isSelected();
            TaskRender.GUESS_BLOCKS_SELECTION = guessess.getSelectedIndex();

            int oldSuccessiveRefinementSplit = TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM;
            TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM = mix_squares_and_rectangles.getSelectedIndex();
            TaskRender.CHUNK_SIZE_PER_ROW = chunksPerRow.isSelected();
            TaskRender.TWO_PASS_SUCCESSIVE_REFINEMENT = twoPassSuccessiveRefinement.isSelected();
            QueueBasedRender.WORK_STEALING_ENABLED = workSteal.isSelected();

            if(oldSuccessiveRefinementSplit != TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM) {
                TaskRender.setSuccessiveRefinementChunks();
                PatternedBruteForceRender.clear();
                PatternedBruteForceRender.clearFastJulia();
            }

            int oldPatternAlg = TaskRender.PATTERN_COMPARE_ALG;
            TaskRender.PATTERN_COMPARE_ALG = pattern.getSelectedIndex();
            if(oldPatternAlg != TaskRender.PATTERN_COMPARE_ALG) {
                PatternedBruteForceRender.clear();
                PatternedBruteForceRender.clearFastJulia();
            }

            boolean oldRevertPattern = TaskRender.PATTERN_REVERT_ALG;
            TaskRender.PATTERN_REVERT_ALG = revert_pattern.isSelected();
            if(oldRevertPattern != TaskRender.PATTERN_REVERT_ALG) {
                PatternedBruteForceRender.clear();
                PatternedBruteForceRender.clearFastJulia();
            }

            boolean oldRepeatPattern = TaskRender.PATTERN_REPEAT_ALG;
            TaskRender.PATTERN_REPEAT_ALG = repeat_pattern.isSelected();
            if(oldRepeatPattern != TaskRender.PATTERN_REPEAT_ALG) {
                PatternedBruteForceRender.clear();
                PatternedBruteForceRender.clearFastJulia();
            }

            boolean oldCenterPattern = TaskRender.PATTERN_CENTER;
            TaskRender.PATTERN_CENTER = centered_pattern.isSelected();
            if(oldCenterPattern != TaskRender.PATTERN_CENTER) {
                PatternedBruteForceRender.clear();
                PatternedBruteForceRender.clearFastJulia();
            }

            double oldNnorm = TaskRender.PATTERN_N;
            TaskRender.PATTERN_N = temp;
            if(TaskRender.PATTERN_N != oldNnorm) {
                PatternedBruteForceRender.clear();
                PatternedBruteForceRender.clearFastJulia();
            }

//            boolean oldFollowZoom = MainWindow.PATTERNED_RENDER_FOLLOWS_ZOOM_TO_CURSOR;
//            MainWindow.PATTERNED_RENDER_FOLLOWS_ZOOM_TO_CURSOR = pattern_follows_zoom_center.isSelected();
//            if(MainWindow.PATTERNED_RENDER_FOLLOWS_ZOOM_TO_CURSOR != oldFollowZoom) {
//            CircularBruteForceRender.clear();
//            CircularBruteForceRender.clearFastJulia();
//            }
            TaskRender.GREEDY_ALGORITHM = greedy_algorithm_opt.isSelected();
            TaskRender.GREEDY_ALGORITHM_SELECTION = algorithm;
            TaskRender.BRUTE_FORCE_ALG = brute_force_alg_opt.getSelectedIndex();

            TaskRender.RECTANGLE_AREA_SPLIT_ALGORITHM = rectangle_area_splitting_algorithms.getSelectedIndex();
            TaskRender.SPLIT_INTO_RECTANGLE_AREAS = rectangle_areas_splitting_opt.isSelected();
            TaskRender.AREA_DIMENSION_X = temp3;
            TaskRender.AREA_DIMENSION_Y = temp4;

            dispose();

            if(ptra2 instanceof MainWindow) {
                ((MainWindow)ptra2).renderingOptionsChanged();
            }

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
        close.addActionListener(e -> dispose());

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
        rectangle_areas_border.setState(rectangle_areas_splitting_opt.isSelected());

        guessess.setEnabled(greedy_algorithm_opt.isSelected() && (patterned_successive_refinement.isSelected() || successive_refinement.isSelected()));
        mix_squares_and_rectangles.setEnabled(greedy_algorithm_opt.isSelected() && (mariani_silver_algorithm.isSelected() || successive_refinement.isSelected() || patterned_successive_refinement.isSelected()));
        twoPassSuccessiveRefinement.setEnabled(greedy_algorithm_opt.isSelected() && (patterned_successive_refinement.isSelected() || successive_refinement.isSelected()));
        workSteal.setEnabled(greedy_algorithm_opt.isSelected() && mariani_silver_algorithm.isSelected());

        JPanel buttons = new JPanel();
        buttons.setBackground(MainWindow.bg_color);

        buttons.add(ok);
        buttons.add(close);

        RoundedPanel round_panel = LAFManager.createRoundedPanel();
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(730, 490));
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

        round_panel.add(panel5, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 3;

        round_panel.add(panel4, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 4;

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

        guessess.setEnabled(greedy_algorithm_opt.isSelected() && (patterned_successive_refinement.isSelected() || successive_refinement.isSelected()));
        mix_squares_and_rectangles.setEnabled(greedy_algorithm_opt.isSelected() && (mariani_silver_algorithm.isSelected() || successive_refinement.isSelected() || patterned_successive_refinement.isSelected()));
        twoPassSuccessiveRefinement.setEnabled(greedy_algorithm_opt.isSelected() && (patterned_successive_refinement.isSelected() || successive_refinement.isSelected()));
        workSteal.setEnabled(greedy_algorithm_opt.isSelected() && mariani_silver_algorithm.isSelected());
    }

}
