package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.CosinePaletteSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ColorPaletteEditorDialog extends JDialog {
    private MainWindow ptra2;
    private ColorPaletteEditorPanel p;

    public ColorPaletteEditorDialog(MainWindow ptra, boolean outcoloring_mode, JRadioButtonMenuItem[] palettes, int color_space) {
        super();

        ptra2 = ptra;

        int width = 900;
        int height = 100;

        setModal(true);
        int filters_options_window_width = 1030;
        int filters_options_window_height = 750;
        setTitle("Custom Direct Palette Editor");
        setIconImage(MainWindow.getIcon("palette.png").getImage());
        setSize(filters_options_window_width, filters_options_window_height);
        setLocation((int)(ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (filters_options_window_width / 2), (int)(ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (filters_options_window_height / 2));

        p = new ColorPaletteEditorPanel(width, height, color_space, this);
        p.setBackground(MainWindow.bg_color);
        p.setPreferredSize(new Dimension(width + 40, 600));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {


                p.Ok();

                dispose();

            }
        });


        JPanel buttons = new JPanel();
        buttons.setBackground(MainWindow.bg_color);

        JButton ok = new MyButton("Ok");
        getRootPane().setDefaultButton(ok);
        ok.setFocusable(false);
        ok.addActionListener(e -> {

            if(p.getTotalColors() == 0) {
                JOptionPane.showMessageDialog(ptra2, "The palette must contain at least one color!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }


            p.Ok();

            dispose();

            ptra2.setPalette(MainWindow.DIRECT_PALETTE_ID, p.getPalette(), outcoloring_mode ? 0 : 1);
            palettes[MainWindow.DIRECT_PALETTE_ID].setSelected(true);


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

        JButton seed_button = new MyButton("Seed");
        seed_button.setFocusable(false);
        seed_button.addActionListener(e -> new RandomPaletteSeedDialog(this));

        buttons.add(seed_button);

        JButton cancel = new MyButton("Cancel");
        cancel.setFocusable(false);
        cancel.addActionListener(e -> {


            p.Ok();

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


        RoundedPanel round_panel = LAFManager.createRoundedPanel();
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(950, 660));
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

    public void colorChanged() {
        p.colorChanged();
    }

    public void setProceduralPalettePost(int length, CosinePaletteSettings cps, int step) {
        p.setProceduralPalettePost(length, cps, step);
    }

    public void setContrastVariationPost(boolean contrast_variation, double range_min, double range_max, int contrast_method, double period, double offset, double merging) {
        p.setContrastVariationPost(contrast_variation, range_min, range_max, contrast_method, period, offset, merging);
    }
}
