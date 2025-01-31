
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author hrkalona2
 */
public class FractalColorsDialog extends JDialog {
	private static final long serialVersionUID = -5730162942925825123L;
	private MainWindow ptra2;
    private FractalColorsDialog this_frame;
    
    public FractalColorsDialog(MainWindow ptra, Color max_it_color, Color dem_color, Color special_color, boolean use_palette_color_special, boolean special_bypass, int magnetOffset) {
        super();
        this.ptra2 = ptra;
        this_frame = this;
        
        setModal(true);
        int color_window_width = 400;
        int color_window_height = 370;
        setTitle("Fractal Colors");
        setIconImage(MainWindow.getIcon("color.png").getImage());
        setSize(color_window_width, color_window_height);
        setLocation((int) (ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (color_window_width / 2), (int) (ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (color_window_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                dispose();

            }
        });

        JPanel fractal_color_panel = new JPanel();
        fractal_color_panel.setBackground(MainWindow.bg_color);
        fractal_color_panel.setLayout(new FlowLayout());
           
        final JLabel max_it_color_label = new ColorLabel();
        max_it_color_label.setPreferredSize(new Dimension(22, 22));
        max_it_color_label.setBackground(max_it_color);
        max_it_color_label.setToolTipText("Left click to change this color.");

        max_it_color_label.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                new ColorChooserDialog(this_frame, "Maximum Iterations Color", max_it_color_label, -1, -1);

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

        final JPanel dem_color_panel = new JPanel();
        dem_color_panel.setBackground(MainWindow.bg_color);
        dem_color_panel.setLayout(new FlowLayout());
       
        final JLabel dem_color_label = new ColorLabel();
        dem_color_label.setPreferredSize(new Dimension(22, 22));

        dem_color_label.setBackground(dem_color);

        dem_color_label.setToolTipText("Left click to change this color.");

        dem_color_label.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                new ColorChooserDialog(this_frame, "Distance Estimation Color", dem_color_label, -1, -1);

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

        final JPanel special_color_panel = new JPanel();
        special_color_panel.setBackground(MainWindow.bg_color);
        special_color_panel.setLayout(new FlowLayout());

        final JPanel magnet_color_panel = new JPanel();
        magnet_color_panel.setBackground(MainWindow.bg_color);
        magnet_color_panel.setLayout(new FlowLayout());

        
        final JPanel special_color_panel2 = new JPanel();
        special_color_panel2.setBackground(MainWindow.bg_color);
        special_color_panel2.setLayout(new FlowLayout());

        final JCheckBox use_palette_color = new JCheckBox("Use Palette Color");
        use_palette_color.setBackground(MainWindow.bg_color);
        use_palette_color.setFocusable(false);
        use_palette_color.setSelected(use_palette_color_special);
        use_palette_color.setToolTipText("<html>Sets the special color for the following color algorithms:<br>Binary Decomposition<br>Binary Decomposition 2<br>Biomorph<br>Escape Time + Grid<br>Escape Time + Field Lines<br>Escape Time + Field Lines 2<br>cos(norm(z))<br>Squares 2<br>User Out/In Coloring algorithm with negative values</html>");

        final JLabel special_color_label = new ColorLabel();
        special_color_label.setPreferredSize(new Dimension(22, 22));

        special_color_label.setBackground(special_color);

        special_color_label.setToolTipText("Left click to change this color.");

        special_color_label.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                new ColorChooserDialog(this_frame, "Special Color", special_color_label, -1, -1);

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

        final JCheckBox remove_special_increment = new JCheckBox("Remove Special Offset");
        remove_special_increment.setBackground(MainWindow.bg_color);
        remove_special_increment.setFocusable(false);
        remove_special_increment.setSelected(special_bypass);
        remove_special_increment.setToolTipText("Removes the special offset, applicable in processing algorithms and 3d rendering.");

        JButton help_button = new MyButton();
        help_button.setIcon(MainWindow.getIcon("help.png"));
        help_button.setFocusable(false);
        help_button.setToolTipText("Some info regarding special offset.");
        help_button.setPreferredSize(new Dimension(30, 30));

        help_button.addActionListener(e -> ptra2.displaySpecialHelp());

        fractal_color_panel.add(new JLabel("Maximum Iterations Color: "));
        fractal_color_panel.add(max_it_color_label);

        dem_color_panel.add(new JLabel("Distance Estimation Color: "));
        dem_color_panel.add(dem_color_label);
       
        special_color_panel.add(new JLabel("Special Color: "));
        special_color_panel.add(special_color_label);
        special_color_panel.add(use_palette_color);
        
        special_color_panel2.add(remove_special_increment);
        special_color_panel2.add(help_button);

        JPanel color_panel = new JPanel();
        color_panel.setBackground(MainWindow.bg_color);
        color_panel.setLayout(new GridLayout(5, 1));
        color_panel.setPreferredSize(new Dimension(290, 230));

        final JTextField magnetOffsetopt = new JTextField(5);
        magnetOffsetopt.setText("" + magnetOffset);
        magnetOffsetopt.setToolTipText("Adds an offset to the magnet functions coloring.");

        magnet_color_panel.add(new JLabel("Magnet Color Offset: "));
        magnet_color_panel.add(magnetOffsetopt);
        
        color_panel.setBorder(LAFManager.createTitledBorderCenter( "Fractal Colors"));
 
        color_panel.add(fractal_color_panel);
        color_panel.add(dem_color_panel);
        color_panel.add(special_color_panel);
        color_panel.add(magnet_color_panel);
        color_panel.add(special_color_panel2);

        JButton ok = new MyButton("Ok");
        getRootPane().setDefaultButton(ok);
        ok.setFocusable(false);

        ok.addActionListener(e -> {

            int temp = 0;
            try {
                temp = Integer.parseInt(magnetOffsetopt.getText());
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (temp < 0) {
                JOptionPane.showMessageDialog(ptra, "The magnet color offset value must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dispose();
            ptra2.fractalColorsChanged(max_it_color_label.getBackground(), dem_color_label.getBackground(), special_color_label.getBackground(), use_palette_color.isSelected(), remove_special_increment.isSelected(), temp);

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

        JPanel buttons = new JPanel();
        buttons.setBackground(MainWindow.bg_color);

        buttons.add(ok);
        buttons.add(close);

        RoundedPanel round_panel = LAFManager.createRoundedPanel();
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(330, 290));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(color_panel, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 1;

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

        repaint();
    }
    
}
