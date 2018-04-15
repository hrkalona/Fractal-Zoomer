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

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.GradientSettings;
import fractalzoomer.palettes.CustomPalette;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author kaloch
 */
public class GradientFrame extends JFrame {

    private MainWindow ptra2;
    private GradientFrame this_frame;
    private JLabel gradient_label;
    private BufferedImage colors;
    private JComboBox combo_box_color_interp;
    private JLabel color_a_label;
    private JLabel color_b_label;
    private JComboBox combo_box_color_space;
    private JCheckBox check_box_reveres_palette;

    public GradientFrame(MainWindow ptra, GradientSettings gs) {

        super();

        ptra2 = ptra;

        this_frame = this;

        ptra2.setEnabled(false);
        int custom_palette_window_width = 580;
        int custom_palette_window_height = 260;
        setTitle("Gradient");
        setIconImage(getIcon("/fractalzoomer/icons/gradient.png").getImage());

        setSize(custom_palette_window_width, custom_palette_window_height);
        setLocation((int) (ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (custom_palette_window_width / 2), (int) (ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (custom_palette_window_height / 2));

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                ptra2.setEnabled(true);
                dispose();

            }
        });

        JPanel gradient_panel = new JPanel();

        gradient_panel.setPreferredSize(new Dimension(500, 130));
        gradient_panel.setLayout(new GridLayout(2, 1));
        gradient_panel.setBackground(MainWindow.bg_color);

        color_a_label = new JLabel();

        color_a_label.setPreferredSize(new Dimension(22, 22));
        color_a_label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        color_a_label.setBackground(gs.colorA);
        color_a_label.setOpaque(true);
        color_a_label.setToolTipText("Changes first color.");

        color_a_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                new ColorChooserFrame(this_frame, "First Color", color_a_label, -1);

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

        color_b_label = new JLabel();

        color_b_label.setPreferredSize(new Dimension(22, 22));
        color_b_label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        color_b_label.setBackground(gs.colorB);
        color_b_label.setOpaque(true);
        color_b_label.setToolTipText("Changes second color.");

        color_b_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                new ColorChooserFrame(this_frame, "Second Color", color_b_label, -1);

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
        
        check_box_reveres_palette = new JCheckBox("Reversed");
        check_box_reveres_palette.setSelected(gs.gradient_reversed);
        check_box_reveres_palette.setFocusable(false);
        check_box_reveres_palette.setToolTipText("Reverses the current palette.");
        check_box_reveres_palette.setBackground(MainWindow.bg_color);

        check_box_reveres_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Color[] c = CustomPalette.getGradient(color_a_label.getBackground().getRGB(), color_b_label.getBackground().getRGB(), MainWindow.GRADIENT_LENGTH, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected());

                    paintGradient(c);
                } catch (ArithmeticException ex) {
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient_label.repaint();
                }
            }
        });


        String[] color_interp_str = {"Linear", "Cosine", "Acceleration", "Deceleration", "Exponential", "Catmull-Rom", "Catmull-Rom 2", "Sigmoid"};

        combo_box_color_interp = new JComboBox(color_interp_str);
        combo_box_color_interp.setSelectedIndex(gs.gradient_interpolation);
        combo_box_color_interp.setFocusable(false);
        combo_box_color_interp.setToolTipText("Sets the color interpolation option.");

        combo_box_color_interp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Color[] c = CustomPalette.getGradient(color_a_label.getBackground().getRGB(), color_b_label.getBackground().getRGB(), MainWindow.GRADIENT_LENGTH, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected());

                    paintGradient(c);
                } catch (ArithmeticException ex) {
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient_label.repaint();
                }
            }

        });

        JPanel color_interp_panel = new JPanel();
        color_interp_panel.setPreferredSize(new Dimension(165, 60));
        color_interp_panel.setLayout(new FlowLayout());
        color_interp_panel.setBackground(MainWindow.bg_color);

        color_interp_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Color Interpolation", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        color_interp_panel.add(combo_box_color_interp);

        String[] color_space_str = {"RGB", "HSB", "Exp", "Square", "Sqrt", "RYB", "LAB", "XYZ", "LCH", "Bezier RGB"};

        combo_box_color_space = new JComboBox(color_space_str);
        combo_box_color_space.setSelectedIndex(gs.gradient_color_space);
        combo_box_color_space.setFocusable(false);
        combo_box_color_space.setToolTipText("Sets the color space option.");

        combo_box_color_space.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Color[] c = CustomPalette.getGradient(color_a_label.getBackground().getRGB(), color_b_label.getBackground().getRGB(), MainWindow.GRADIENT_LENGTH, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected());

                    paintGradient(c);
                } catch (ArithmeticException ex) {
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient_label.repaint();
                }
            }

        });

        JPanel color_space_panel = new JPanel();
        color_space_panel.setPreferredSize(new Dimension(140, 60));
        color_space_panel.setLayout(new FlowLayout());
        color_space_panel.setBackground(MainWindow.bg_color);

        color_space_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Color Space", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        color_space_panel.add(combo_box_color_space);

        JPanel options_panel = new JPanel();

        options_panel.setLayout(new FlowLayout());
        options_panel.setBackground(MainWindow.bg_color);

        JPanel color_panel = new JPanel();
        color_panel.setPreferredSize(new Dimension(160, 60));
        color_panel.setLayout(new FlowLayout());
        color_panel.setBackground(MainWindow.bg_color);
        
        color_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Colors", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        
        color_panel.add(color_a_label);
        color_panel.add(color_b_label);
        color_panel.add(check_box_reveres_palette);
        
        options_panel.add(color_panel);
        options_panel.add(color_space_panel);
        options_panel.add(color_interp_panel);

        colors = new BufferedImage(472, 45, BufferedImage.TYPE_INT_ARGB);

        Color[] c = CustomPalette.getGradient(gs.colorA.getRGB(), gs.colorB.getRGB(), MainWindow.GRADIENT_LENGTH, gs.gradient_interpolation, gs.gradient_color_space, gs.gradient_reversed);

        try {
            Graphics2D g = colors.createGraphics();
            for (int i = 0; i < c.length - 1; i++) {
                GradientPaint gp = new GradientPaint(i * colors.getWidth() / c.length, 0, c[i], (i + 1) * colors.getWidth() / c.length, 0, c[(i + 1) % c.length]);
                g.setPaint(gp);
                g.fill(new Rectangle2D.Double(i * colors.getWidth() / c.length, 0, (i + 1) * colors.getWidth() / c.length - i * colors.getWidth() / c.length, colors.getHeight()));
            }
        } catch (Exception ex) {
            Graphics2D g = colors.createGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
        }

        gradient_label = new JLabel(new ImageIcon(colors));
        gradient_label.setPreferredSize(new Dimension(colors.getWidth(), colors.getHeight()));
        gradient_label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        
        gradient_label.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                try {
                    Color temp_color = new Color(colors.getRGB((int) gradient_label.getMousePosition().getX(), (int) gradient_label.getMousePosition().getY()));
                    String rr = Integer.toHexString(temp_color.getRed());
                    String bb = Integer.toHexString(temp_color.getBlue());
                    String gg = Integer.toHexString(temp_color.getGreen());

                    rr = rr.length() == 1 ? "0" + rr : rr;
                    gg = gg.length() == 1 ? "0" + gg : gg;
                    bb = bb.length() == 1 ? "0" + bb : bb;

                    gradient_label.setToolTipText("<html>R: " + temp_color.getRed() + " G: " + temp_color.getGreen() + " B: " + temp_color.getBlue() + "<br>"
                            + "#" + rr + gg + bb + "</html>");

                } catch (Exception ex) {
                }
            }
        });

        JPanel gp = new JPanel();
        gp.setBackground(MainWindow.bg_color);

        gp.add(gradient_label);

        gradient_panel.add(options_panel);
        gradient_panel.add(gp);

        JPanel buttons = new JPanel();

        buttons.setLayout(new FlowLayout());
        buttons.setBackground(MainWindow.bg_color);

        JButton ok = new JButton("Ok");
        ok.setFocusable(false);
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptra2.gradientChanged(color_a_label.getBackground(), color_b_label.getBackground(), combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected());
                ptra2.setEnabled(true);
                dispose();

            }

        });

        buttons.add(ok);

        JButton cancel = new JButton("Cancel");
        cancel.setFocusable(false);
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptra2.setEnabled(true);
                dispose();

            }
        });

        buttons.add(cancel);

        RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(510, 180));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(gradient_panel, con);

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

    private void paintGradient(Color[] c) {

        try {
            Graphics2D g = colors.createGraphics();
            for (int i = 0; i < c.length - 1; i++) { 
                GradientPaint gp = new GradientPaint(i * colors.getWidth() / c.length, 0, c[i], (i + 1) * colors.getWidth() / c.length, 0, c[(i + 1) % c.length]);
                g.setPaint(gp);
                g.fill(new Rectangle2D.Double(i * colors.getWidth() / c.length, 0, (i + 1) * colors.getWidth() / c.length - i * colors.getWidth() / c.length, colors.getHeight()));
            }
        } catch (Exception ex) {
            Graphics2D g = colors.createGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
        }

        gradient_label.repaint();

    }

    public void colorChanged() {
        try {
            Color[] c = CustomPalette.getGradient(color_a_label.getBackground().getRGB(), color_b_label.getBackground().getRGB(), MainWindow.GRADIENT_LENGTH, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected());

            paintGradient(c);
        } catch (ArithmeticException ex) {
            Graphics2D g = colors.createGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
            gradient_label.repaint();
        }
    }
}
