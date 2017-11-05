/*
 * Copyright (C) 2017 hrkalona2
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

import fractalzoomer.core.PlaneVisualizer;
import fractalzoomer.main.MainWindow;
import fractalzoomer.parser.ParserException;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author hrkalona2
 */
public class PlaneVisualizationFrame extends JFrame {

    private MainWindow ptra2;
    private PlaneVisualizationFrame thiss;

    public PlaneVisualizationFrame(MainWindow ptra, final double xCenter, final double yCenter, final int plane_type, final String user_plane, final int user_plane_algorithm, final String[] user_plane_conditions, final String[] user_plane_condition_formula, final double[] plane_transform_center, final double plane_transform_angle, final double plane_transform_radius, final double[] plane_transform_scales, final double plane_transform_angle2, final int plane_transform_sides, final double plane_transform_amount, final int max_iterations, final double zoom_factor) {

        super();

        ptra2 = ptra;
        thiss = this;

        ptra2.setEnabled(false);
        int filters_options_window_width = 1050;
        int filters_options_window_height = 660;
        setTitle("Plane Visualization");
        setIconImage(getIcon("/fractalzoomer/icons/plane_visualization.png").getImage());
        setSize(filters_options_window_width, filters_options_window_height);
        setLocation((int)(ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (filters_options_window_width / 2), (int)(ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (filters_options_window_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                ptra2.setEnabled(true);

                dispose();

            }
        });

        JPanel panel = new JPanel();
        panel.setBackground(MainWindow.bg_color);
        panel.setPreferredSize(new Dimension(940, 520));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Planes", TitledBorder.CENTER, TitledBorder.CENTER));

        final JSlider size_slid = new JSlider(JSlider.HORIZONTAL, 0, 50, 25);
        size_slid.setPreferredSize(new Dimension(300, 40));
        size_slid.setFocusable(false);
        size_slid.setToolTipText("Changes the zooming.");
        size_slid.setPaintLabels(true);
        size_slid.setBackground(MainWindow.bg_color);

        /*Hashtable<Integer, JLabel> table3 = new Hashtable<Integer, JLabel>();
         table3.put(0, new JLabel("0.0"));
         table3.put(25, new JLabel("0.25"));
         table3.put(50, new JLabel("0.5"));
         table3.put(75, new JLabel("0.75"));
         table3.put(100, new JLabel("1.0"));
         coupling_slid.setLabelTable(table3);*/
        final BufferedImage plane_mu_image = new BufferedImage(402, 402, BufferedImage.TYPE_INT_ARGB);
        final BufferedImage new_plane_image = new BufferedImage(402, 402, BufferedImage.TYPE_INT_ARGB);

        String[] color_modes_str = {"Re-Im", "Rainbow"};
        final JComboBox color_modes_opt = new JComboBox(color_modes_str);
        color_modes_opt.setSelectedIndex(0);
        color_modes_opt.setFocusable(false);

        double size = Math.pow(zoom_factor, (size_slid.getMaximum() - size_slid.getValue()) - size_slid.getMaximum() / 2);
        try {
            new PlaneVisualizer(plane_mu_image, new_plane_image, plane_type, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xCenter, yCenter, size, max_iterations).visualizePlanes(color_modes_opt.getSelectedIndex());
        }
        catch(ParserException e) {
            JOptionPane.showMessageDialog(thiss, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            ptra2.savePreferences();
            System.exit(-1);
        }

        final JLabel l1 = new JLabel();
        l1.setIcon(new ImageIcon(plane_mu_image));
        final JLabel l2 = new JLabel();
        l2.setIcon(new ImageIcon(new_plane_image));

        size_slid.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double size = Math.pow(zoom_factor, (size_slid.getMaximum() - size_slid.getValue()) - size_slid.getMaximum() / 2);
                try {
                    new PlaneVisualizer(plane_mu_image, new_plane_image, plane_type, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xCenter, yCenter, size, max_iterations).visualizePlanes(color_modes_opt.getSelectedIndex());
                }
                catch(ParserException ex) {
                    JOptionPane.showMessageDialog(thiss, ex.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                    ptra2.savePreferences();
                    System.exit(-1);
                }
                l1.repaint();
                l2.repaint();
            }

        });

        color_modes_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double size = Math.pow(zoom_factor, (size_slid.getMaximum() - size_slid.getValue()) - size_slid.getMaximum() / 2);
                try {                  
                    new PlaneVisualizer(plane_mu_image, new_plane_image, plane_type, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xCenter, yCenter, size, max_iterations).visualizePlanes(color_modes_opt.getSelectedIndex());
                }
                catch(ParserException ex) {
                    JOptionPane.showMessageDialog(thiss, ex.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                    ptra2.savePreferences();
                    System.exit(-1);
                }
                l1.repaint();
                l2.repaint();
            }

        });

        JLabel l3 = new JLabel();
        l3.setIcon(getIcon("/fractalzoomer/icons/transform.png"));

        JPanel mu_panel = new JPanel();
        mu_panel.setBackground(MainWindow.bg_color);
        mu_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Original Plane", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        JPanel tr_panel = new JPanel();
        tr_panel.setBackground(MainWindow.bg_color);
        tr_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Transformed Plane", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        mu_panel.add(l1);
        tr_panel.add(l2);

        JPanel size_panel = new JPanel();
        size_panel.setBackground(MainWindow.bg_color);
        size_panel.setPreferredSize(new Dimension(500, 50));
        size_panel.add(new JLabel("Zoom Out"));
        size_panel.add(size_slid);
        size_panel.add(new JLabel("Zoom In"));

        JPanel color_panel = new JPanel();
        color_panel.setBackground(MainWindow.bg_color);
        color_panel.add(new JLabel("Coloring Option:"));
        color_panel.add(color_modes_opt);
        
        panel.add(size_panel);
        panel.add(color_panel);
        panel.add(mu_panel);
        panel.add(l3);
        panel.add(tr_panel);

        JPanel buttons = new JPanel();
        buttons.setBackground(MainWindow.bg_color);

        JButton ok = new JButton("Ok");
        ok.setFocusable(false);
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptra2.setEnabled(true);

                dispose();

            }
        });

        buttons.add(ok);

        RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(980, 570));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(panel, con);

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

        requestFocus();

        setVisible(true);
    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }

}
