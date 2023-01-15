package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ColorPaletteEditorFrame extends JFrame {
    private MainWindow ptra2;

    public ColorPaletteEditorFrame(MainWindow ptra, boolean outcoloring_mode, JRadioButtonMenuItem[] palettes, int color_space) {
        super();

        ptra2 = ptra;

        int width = 900;
        int height = 100;

        ptra2.setEnabled(false);
        int filters_options_window_width = 1030;
        int filters_options_window_height = 730;
        setTitle("Custom Direct Palette Editor");
        setIconImage(MainWindow.getIcon("palette.png").getImage());
        setSize(filters_options_window_width, filters_options_window_height);
        setLocation((int)(ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (filters_options_window_width / 2), (int)(ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (filters_options_window_height / 2));

        ColorPaletteEditorPanel p = new ColorPaletteEditorPanel(width, height, color_space, this);
        p.setBackground(MainWindow.bg_color);
        p.setPreferredSize(new Dimension(width + 40, 580));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {


                p.Ok();
                ptra2.setEnabled(true);

                dispose();

            }
        });


        JPanel buttons = new JPanel();
        buttons.setBackground(MainWindow.bg_color);

        JButton ok = new JButton("Ok");
        getRootPane().setDefaultButton(ok);
        ok.setFocusable(false);
        ok.addActionListener(e -> {

            if(p.getTotalColors() == 0) {
                JOptionPane.showMessageDialog(ptra2, "The palette must contain at least one color!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }


            p.Ok();
            ptra2.setEnabled(true);

            dispose();

            ptra2.setPalette(MainWindow.DIRECT_PALETTE_ID, p.getPalette(), outcoloring_mode ? 0 : 1);
            palettes[MainWindow.DIRECT_PALETTE_ID].setSelected(true);

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

        buttons.add(ok);

        JButton cancel = new JButton("Cancel");
        cancel.setFocusable(false);
        cancel.addActionListener(e -> {


            p.Ok();

            ptra2.setEnabled(true);

            dispose();

        });

        buttons.add(cancel);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
        getRootPane().getActionMap().put("Cancel", new AbstractAction()
        {

            public void actionPerformed(ActionEvent e)
            {
                cancel.doClick();
            }
        });


        RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(950, 640));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(p, con);

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

}
