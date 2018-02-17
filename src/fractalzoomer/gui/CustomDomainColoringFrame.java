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

import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.DomainColoringSettings;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author hrkalona2
 */
public class CustomDomainColoringFrame extends JFrame {

    private DomainColoringFrame ptra2;
    private CustomDomainColoringFrame this_frame;
    private static DomainColoringSettings ds;

    public static void setSettings(DomainColoringSettings settings) {

        ds = settings;

    }

    public static DomainColoringSettings getSettings() {

        return ds;

    }

    public CustomDomainColoringFrame(DomainColoringFrame ptra) {

        super();

        ptra2 = ptra;

        this_frame = this;

        ptra2.setEnabled(false);
        int custom_palette_window_width = 800;
        int custom_palette_window_height = 670;
        setTitle("Custom Domain Coloring");
        setIconImage(getIcon("/fractalzoomer/icons/domain_coloring.png").getImage());
        setSize(custom_palette_window_width, custom_palette_window_height);
        setLocation((int)(ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (custom_palette_window_width / 2), (int)(ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (custom_palette_window_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                ptra2.setEnabled(true);
                dispose();

            }
        });

        JPanel domain_coloring_panel = new JPanel();

        domain_coloring_panel.setPreferredSize(new Dimension(700, 523));
        domain_coloring_panel.setLayout(new GridLayout(6, 1));
        domain_coloring_panel.setBackground(MainWindow.bg_color);

        domain_coloring_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Custom Domain Coloring", TitledBorder.CENTER, TitledBorder.CENTER));

        JPanel general_settings_panel = new JPanel();
        general_settings_panel.setLayout(new GridLayout(2, 1));
        general_settings_panel.setBackground(MainWindow.bg_color);

        final JTextField log_base_textfield = new JTextField(10);
        log_base_textfield.addAncestorListener(new RequestFocusListener());
        log_base_textfield.setText("" + ds.logBase);

        final JTextField grid_spacing_textfield = new JTextField(10);
        grid_spacing_textfield.setText("" + ds.gridFactor);
        
        final JTextField norm_type_textfield = new JTextField(10);
        norm_type_textfield.setText("" + ds.normType);
        
        final JComboBox iso_lines_distance_opt = new JComboBox(Constants.argumentLinesDistance);
        iso_lines_distance_opt.setSelectedIndex(ds.iso_distance);
        iso_lines_distance_opt.setFocusable(false);
        iso_lines_distance_opt.setToolTipText("Sets the iso-argument distance.");

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        p1.setBackground(MainWindow.bg_color);
        
        p1.add(new JLabel("Circle Log Base: "));
        p1.add(log_base_textfield);
        p1.add(new JLabel("  Grid Spacing: "));
        p1.add(grid_spacing_textfield);
        p1.add(new JLabel("  Norm Type: "));
        p1.add(norm_type_textfield);
        
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        p2.setBackground(MainWindow.bg_color);
        
        p2.add(new JLabel("Iso-Argument Line Distance: "));
        p2.add(iso_lines_distance_opt);
        
        general_settings_panel.add(p1);       
        general_settings_panel.add(p2);

        general_settings_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "General Settings", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        final JPanel color_panel = new JPanel();
        color_panel.setLayout(new FlowLayout());
        color_panel.setBackground(MainWindow.bg_color);

        final JPanel contours_panel = new JPanel();
        contours_panel.setLayout(new FlowLayout());
        contours_panel.setBackground(MainWindow.bg_color);

        final JPanel grid_panel = new JPanel();
        grid_panel.setLayout(new FlowLayout());
        grid_panel.setBackground(MainWindow.bg_color);

        final JPanel circles_panel = new JPanel();
        circles_panel.setLayout(new FlowLayout());
        circles_panel.setBackground(MainWindow.bg_color);

        final JPanel iso_lines_panel = new JPanel();
        iso_lines_panel.setLayout(new FlowLayout());
        iso_lines_panel.setBackground(MainWindow.bg_color);

        final JCheckBox enable_colors = new JCheckBox("Colors");
        enable_colors.setBackground(MainWindow.bg_color);
        enable_colors.setSelected(ds.drawColor);

        final JComboBox domain_colors_combo = new JComboBox(Constants.domainColors);
        domain_colors_combo.setSelectedIndex(ds.colorType);
        domain_colors_combo.setFocusable(false);
        domain_colors_combo.setToolTipText("Sets the coloring option.");

        ComponentTitledBorder colors_border = new ComponentTitledBorder(enable_colors, color_panel, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
        colors_border.setCheckBoxListener();

        color_panel.setBorder(colors_border);

        color_panel.add(new JLabel("Type: "));
        color_panel.add(domain_colors_combo);

        final JCheckBox enable_contours = new JCheckBox("Contours");
        enable_contours.setBackground(MainWindow.bg_color);
        enable_contours.setSelected(ds.drawContour);

        final JComboBox domain_contours_combo = new JComboBox(Constants.domainContours);
        domain_contours_combo.setSelectedIndex(ds.contourType);
        domain_contours_combo.setFocusable(false);
        domain_contours_combo.setToolTipText("Sets the contouring option.");

        final JSlider contour_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        contour_blend_opt.setValue((int)(100 * ds.contourBlending));
        contour_blend_opt.setBackground(MainWindow.bg_color);
        contour_blend_opt.setMajorTickSpacing(25);
        contour_blend_opt.setMinorTickSpacing(1);
        contour_blend_opt.setToolTipText("Sets the contour blending percentage.");
        contour_blend_opt.setFocusable(false);
        contour_blend_opt.setPaintLabels(true);

        contours_panel.add(new JLabel("Type: "));
        contours_panel.add(domain_contours_combo);
        contours_panel.add(new JLabel(" Color Blending: "));
        contours_panel.add(contour_blend_opt);

        ComponentTitledBorder contours_border = new ComponentTitledBorder(enable_contours, contours_panel, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
        contours_border.setCheckBoxListener();

        contours_panel.setBorder(contours_border);

        final JCheckBox enable_grid = new JCheckBox("Grid");
        enable_grid.setBackground(MainWindow.bg_color);
        enable_grid.setSelected(ds.drawGrid);

        final JSlider grid_coef_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        grid_coef_opt.setValue((int)(100 * ds.gridBlending));
        grid_coef_opt.setBackground(MainWindow.bg_color);
        grid_coef_opt.setMajorTickSpacing(25);
        grid_coef_opt.setMinorTickSpacing(1);
        grid_coef_opt.setToolTipText("Sets the grid strength percentage.");
        grid_coef_opt.setFocusable(false);
        grid_coef_opt.setPaintLabels(true);

        final JLabel grid_color_label = new JLabel();

        grid_color_label.setPreferredSize(new Dimension(22, 22));
        grid_color_label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        grid_color_label.setBackground(ds.gridColor);
        grid_color_label.setOpaque(true);
        grid_color_label.setToolTipText("Changes the grid color.");

        grid_color_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(!grid_color_label.isEnabled()) {
                    return;
                }

                new ColorChooserFrame(ptra2, this_frame, "Grid Color", grid_color_label, -1);
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

        grid_panel.add(new JLabel("Strength: "));
        grid_panel.add(grid_coef_opt);
        grid_panel.add(new JLabel(" Color: "));
        grid_panel.add(grid_color_label);

        ComponentTitledBorder grid_border = new ComponentTitledBorder(enable_grid, grid_panel, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
        grid_border.setCheckBoxListener();

        grid_panel.setBorder(grid_border);

        final JCheckBox enable_circles = new JCheckBox("Circles");
        enable_circles.setBackground(MainWindow.bg_color);
        enable_circles.setSelected(ds.drawCircles);

        final JSlider circles_coef_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        circles_coef_opt.setValue((int)(100 * ds.circlesBlending));
        circles_coef_opt.setBackground(MainWindow.bg_color);
        circles_coef_opt.setMajorTickSpacing(25);
        circles_coef_opt.setMinorTickSpacing(1);
        circles_coef_opt.setToolTipText("Sets the circles strentgh percentage.");
        circles_coef_opt.setFocusable(false);
        circles_coef_opt.setPaintLabels(true);

        final JLabel circles_color_label = new JLabel();

        circles_color_label.setPreferredSize(new Dimension(22, 22));
        circles_color_label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        circles_color_label.setBackground(ds.circlesColor);
        circles_color_label.setOpaque(true);
        circles_color_label.setToolTipText("Changes the circles color.");

        circles_color_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(!circles_color_label.isEnabled()) {
                    return;
                }

                new ColorChooserFrame(ptra2, this_frame, "Circles Color", circles_color_label, -1);
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

        circles_panel.add(new JLabel("Strength: "));
        circles_panel.add(circles_coef_opt);
        circles_panel.add(new JLabel(" Color: "));
        circles_panel.add(circles_color_label);

        ComponentTitledBorder circles_border = new ComponentTitledBorder(enable_circles, circles_panel, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
        circles_border.setCheckBoxListener();

        circles_panel.setBorder(circles_border);

        final JCheckBox enable_iso_lines = new JCheckBox("Iso-Argument Lines");
        enable_iso_lines.setBackground(MainWindow.bg_color);
        enable_iso_lines.setSelected(ds.drawIsoLines);

        final JSlider iso_lines_factor_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        iso_lines_factor_opt.setValue((int)(100 * ds.iso_factor));
        iso_lines_factor_opt.setBackground(MainWindow.bg_color);
        iso_lines_factor_opt.setToolTipText("Sets the iso-argument lines width factor.");
        iso_lines_factor_opt.setFocusable(false);
        iso_lines_factor_opt.setPaintLabels(true);

        Hashtable<Integer, JLabel> table3 = new Hashtable<Integer, JLabel>();
        table3.put(0, new JLabel("0.0"));
        table3.put(25, new JLabel("0.25"));
        table3.put(50, new JLabel("0.5"));
        table3.put(75, new JLabel("0.75"));
        table3.put(100, new JLabel("1.0"));

        iso_lines_factor_opt.setLabelTable(table3);

        final JSlider iso_lines_coef_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        iso_lines_coef_opt.setValue((int)(100 * ds.isoLinesBlendingFactor));
        iso_lines_coef_opt.setBackground(MainWindow.bg_color);
        iso_lines_coef_opt.setMajorTickSpacing(25);
        iso_lines_coef_opt.setMinorTickSpacing(1);
        iso_lines_coef_opt.setToolTipText("Sets the iso-argument lines strentgh percentage.");
        iso_lines_coef_opt.setFocusable(false);
        iso_lines_coef_opt.setPaintLabels(true);

        final JLabel iso_lines_color_label = new JLabel();

        iso_lines_color_label.setPreferredSize(new Dimension(22, 22));
        iso_lines_color_label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        iso_lines_color_label.setBackground(ds.isoLinesColor);
        iso_lines_color_label.setOpaque(true);
        iso_lines_color_label.setToolTipText("Changes the iso-argument lines color.");

        iso_lines_color_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(!iso_lines_color_label.isEnabled()) {
                    return;
                }

                new ColorChooserFrame(ptra2, this_frame, "Iso-Argument Lines Color", iso_lines_color_label, -1);
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

        iso_lines_panel.add(new JLabel("Width: "));
        iso_lines_panel.add(iso_lines_factor_opt);
        iso_lines_panel.add(new JLabel(" Strength: "));
        iso_lines_panel.add(iso_lines_coef_opt);
        iso_lines_panel.add(new JLabel(" Color: "));
        iso_lines_panel.add(iso_lines_color_label);

        ComponentTitledBorder iso_lines_border = new ComponentTitledBorder(enable_iso_lines, iso_lines_panel, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
        iso_lines_border.setCheckBoxListener();

        iso_lines_panel.setBorder(iso_lines_border);

        colors_border.setState(enable_colors.isSelected());
        iso_lines_border.setState(enable_iso_lines.isSelected());
        circles_border.setState(enable_circles.isSelected());
        grid_border.setState(enable_grid.isSelected());
        contours_border.setState(enable_contours.isSelected());

        domain_coloring_panel.add(general_settings_panel);
        domain_coloring_panel.add(color_panel);
        domain_coloring_panel.add(contours_panel);
        domain_coloring_panel.add(grid_panel);
        domain_coloring_panel.add(circles_panel);
        domain_coloring_panel.add(iso_lines_panel);

        JPanel buttons = new JPanel();

        buttons.setLayout(new FlowLayout());
        buttons.setBackground(MainWindow.bg_color);

        JButton palette_ok = new JButton("Ok");
        palette_ok.setFocusable(false);
        palette_ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                double temp, temp2, temp3;
                try {
                    temp = Double.parseDouble(log_base_textfield.getText());
                    temp2 = Double.parseDouble(grid_spacing_textfield.getText());
                    temp3 = Double.parseDouble(norm_type_textfield.getText());
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(this_frame, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if(temp <= 1) {
                    JOptionPane.showMessageDialog(this_frame, "Log Base must be greater than 1.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if(temp2 <= 0) {
                    JOptionPane.showMessageDialog(this_frame, "Grid Spacing must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                ds.gridFactor = temp2;
                ds.logBase = temp;
                ds.normType = temp3;
                ds.iso_distance = iso_lines_distance_opt.getSelectedIndex();
                
                ds.drawColor = enable_colors.isSelected();
                ds.drawContour = enable_contours.isSelected();
                ds.drawGrid = enable_grid.isSelected();
                ds.drawCircles = enable_circles.isSelected();
                ds.drawIsoLines = enable_iso_lines.isSelected();
                
                ds.gridBlending = grid_coef_opt.getValue() / 100.0;
                ds.circlesBlending = circles_coef_opt.getValue() / 100.0;
                ds.isoLinesBlendingFactor = iso_lines_coef_opt.getValue() / 100.0;
                ds.contourBlending = contour_blend_opt.getValue() / 100.0;
                
                ds.gridColor = grid_color_label.getBackground();
                ds.circlesColor = circles_color_label.getBackground();
                ds.isoLinesColor = iso_lines_color_label.getBackground();
                
                ds.iso_factor = iso_lines_factor_opt.getValue() / 100.0;
                
                ds.colorType = domain_colors_combo.getSelectedIndex();
                ds.contourType = domain_contours_combo.getSelectedIndex();
                
                ptra2.setEnabled(true);
                dispose();

            }

        });

        buttons.add(palette_ok);

        JButton palette_cancel = new JButton("Cancel");
        palette_cancel.setFocusable(false);
        palette_cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptra2.setEnabled(true);
                dispose();

            }
        });

        buttons.add(palette_cancel);

        RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(740, 580));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(domain_coloring_panel, con);

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
    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }
}
