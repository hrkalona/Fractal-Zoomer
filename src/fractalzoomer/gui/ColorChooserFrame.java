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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class ColorChooserFrame extends JFrame {
	private static final long serialVersionUID = 2164955068218376522L;
	private JFrame ptr2;
    private Object obj2;

    public ColorChooserFrame(JFrame ptr, String title, Object obj, final int num, final int num2) {

        super();

        ptr2 = ptr;
        obj2 = obj;

        ptr2.setEnabled(false);
        int color_window_width = 720;
        int color_window_height = 480;
        setTitle(title);
        setSize(color_window_width, color_window_height);
        setIconImage(MainWindow.getIcon("color.png").getImage());
        setLocation((int)(ptr2.getLocation().getX() + ptr2.getSize().getWidth() / 2) - (color_window_width / 2), (int)(ptr2.getLocation().getY() + ptr2.getSize().getHeight() / 2) - (color_window_height / 2));
        final JColorChooser color_chooser = new JColorChooser();
        color_chooser.setBackground(MainWindow.bg_color);

        if(obj2 instanceof JLabel) {
            color_chooser.setColor(((JLabel)obj2).getBackground());
        }
        else if(obj2 instanceof Color) {
            color_chooser.setColor((Color)obj2);
        }
        else if(obj2 instanceof String) {
            color_chooser.setColor(new Color(Integer.valueOf((String)obj2)));
        }
        else if(obj2 instanceof ColorPaletteEditorPanel) {
            color_chooser.setColor(new Color(num2, num2, num2));
        }

        color_chooser.setPreferredSize(new Dimension(600, 360));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                ptr2.setEnabled(true);
                dispose();

            }
        });

        JButton ok = new MyButton("Ok");
        ok.setFocusable(false);
        
        getRootPane().setDefaultButton(ok);

        ok.addActionListener(e -> {

            if(obj2 instanceof JLabel) {
                ((JLabel)obj2).setBackground(new Color(color_chooser.getColor().getRed(), color_chooser.getColor().getGreen(), color_chooser.getColor().getBlue()));

                if(ptr2 instanceof GradientFrame) {
                    ((GradientFrame)ptr2).colorChanged();
                }
                else if(ptr2 instanceof CustomPaletteEditorFrame) {
                    ((CustomPaletteEditorFrame)ptr2).colorChanged(num);
                }
                else if(ptr2 instanceof  ColorPaletteEditorFrame) {
                    ((ColorPaletteEditorFrame)ptr2).colorChanged();
                }
            }
            else if(obj2 instanceof Color) {
                if(ptr2 instanceof MainWindow) {
                    ((MainWindow)ptr2).storeColor(num, color_chooser.getColor());
                }
            }
            else if(obj2 instanceof String) {

                if(ptr2 instanceof StatisticsColoringFrame) {
                    ((StatisticsColoringFrame)ptr2).storeColor(num, color_chooser.getColor());
                }
            }
            else if(obj2 instanceof ColorPaletteEditorPanel) {
                ((ColorPaletteEditorPanel)obj2).doAdd(num, color_chooser.getColor());
            }

            ptr2.setEnabled(true);
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

            ptr2.setEnabled(true);
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
}
