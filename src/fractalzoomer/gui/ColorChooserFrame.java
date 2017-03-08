/*
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author hrkalona2
 */
public class ColorChooserFrame extends JFrame {

    private JFrame ptr2;
    private MainWindow ptra2;
    private Object obj2;

    public ColorChooserFrame(MainWindow ptra, JFrame ptr, String title, Object obj, final int num) {

        super();

        ptr2 = ptr;
        ptra2 = ptra;
        obj2 = obj;

        ptr2.setEnabled(false);
        int color_window_width = 720;
        int color_window_height = 480;
        setTitle(title);
        setSize(color_window_width, color_window_height);
        setIconImage(getIcon("/fractalzoomer/icons/color.png").getImage());
        setLocation((int)(ptr2.getLocation().getX() + ptr2.getSize().getWidth() / 2) - (color_window_width / 2), (int)(ptr2.getLocation().getY() + ptr2.getSize().getHeight() / 2) - (color_window_height / 2));
        final JColorChooser color_chooser = new JColorChooser();
        color_chooser.setBackground(MainWindow.bg_color);

        if(obj2 instanceof JLabel) {
            color_chooser.setColor(((JLabel)obj2).getBackground());
        }
        else if(obj2 instanceof Color) {
            color_chooser.setColor((Color)obj2);
        }

        color_chooser.setPreferredSize(new Dimension(600, 360));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                ptr2.setEnabled(true);
                dispose();

            }
        });

        JButton ok = new JButton("Ok");
        ok.setFocusable(false);

        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if(obj2 instanceof JLabel) {
                    ((JLabel)obj2).setBackground(new Color(color_chooser.getColor().getRed(), color_chooser.getColor().getGreen(), color_chooser.getColor().getBlue()));
                }
                else if(obj2 instanceof Color) {

                    ptra2.storeColor(num, color_chooser.getColor());
                }

                ptr2.setEnabled(true);
                dispose();

            }
        });

        JButton close = new JButton("Cancel");
        close.setFocusable(false);
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr2.setEnabled(true);
                dispose();

            }
        });

        JPanel buttons = new JPanel();
        buttons.setBackground(MainWindow.bg_color);

        buttons.add(ok);
        buttons.add(close);

        RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(640, 380));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(color_chooser, con);

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
