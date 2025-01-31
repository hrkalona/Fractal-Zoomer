
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
public class ColorChooserDialog extends JDialog {
	private static final long serialVersionUID = 2164955068218376522L;
    private Object obj2;

    public ColorChooserDialog(Object ptr, String title, Object obj, final int num, final int num2) {

        super();

        obj2 = obj;

        setModal(true);

        int color_window_width = 720 + (MainWindow.useCustomLaf ? 40 : 0);
        int color_window_height = 480;
        setTitle(title);
        setSize(color_window_width, color_window_height);
        setIconImage(MainWindow.getIcon("color.png").getImage());


        if(ptr instanceof JFrame) {
            JFrame ptr2 = (JFrame) ptr;
            setLocation((int)(ptr2.getLocation().getX() + ptr2.getSize().getWidth() / 2) - (color_window_width / 2), (int)(ptr2.getLocation().getY() + ptr2.getSize().getHeight() / 2) - (color_window_height / 2));
        }
        else if(ptr instanceof JDialog) {
            JDialog ptr2 = (JDialog) ptr;
            setLocation((int)(ptr2.getLocation().getX() + ptr2.getSize().getWidth() / 2) - (color_window_width / 2), (int)(ptr2.getLocation().getY() + ptr2.getSize().getHeight() / 2) - (color_window_height / 2));
        }

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

                dispose();

            }
        });

        JButton ok = new MyButton("Ok");
        ok.setFocusable(false);
        
        getRootPane().setDefaultButton(ok);

        ok.addActionListener(e -> {

            if(obj2 instanceof JLabel) {
                ((JLabel)obj2).setBackground(new Color(color_chooser.getColor().getRed(), color_chooser.getColor().getGreen(), color_chooser.getColor().getBlue()));

                if(ptr instanceof GradientDialog) {
                    ((GradientDialog)ptr).colorChanged();
                }
                else if(ptr instanceof CustomPaletteEditorDialog) {
                    ((CustomPaletteEditorDialog)ptr).colorChanged(num);
                }
                else if(ptr instanceof ColorPaletteEditorDialog) {
                    ((ColorPaletteEditorDialog)ptr).colorChanged();
                }
            }
            else if(obj2 instanceof Color) {
                if(ptr instanceof MainWindow) {
                    ((MainWindow)ptr).storeColor(num, color_chooser.getColor());
                }
            }
            else if(obj2 instanceof String) {

                if(ptr instanceof StatisticsColoringDialog) {
                    ((StatisticsColoringDialog)ptr).storeColor(num, color_chooser.getColor());
                }
            }
            else if(obj2 instanceof ColorPaletteEditorPanel) {
                ((ColorPaletteEditorPanel)obj2).doAdd(num, color_chooser.getColor());
            }

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
        round_panel.setPreferredSize(new Dimension(640  + (MainWindow.useCustomLaf ? 40 : 0), 380));
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

        repaint();
    }
}
