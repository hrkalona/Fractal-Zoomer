package fractalzoomer.gui;

import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.MainWindow;
import fractalzoomer.palettes.PresetPalette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ColorMapDialog extends JDialog {
    private static final long serialVersionUID = 8403887235484988L;
    private MainWindow ptra2;
    private JList<String> list;

    public static final String DirName = "ColorMaps";

    public ColorMapDialog(MainWindow ptra, boolean smoothing, boolean outcoloring_mode, JRadioButtonMenuItem[] palettes) {

        super();

        ptra2 = ptra;

        setModal(true);
        int filters_options_window_width = 810;
        int filters_options_window_height = 690;
        setTitle("Direct Palette Loader");
        setIconImage(MainWindow.getIcon("palette.png").getImage());
        setSize(filters_options_window_width, filters_options_window_height);
        setLocation((int)(ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (filters_options_window_width / 2), (int)(ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (filters_options_window_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                dispose();

            }
        });


        DefaultListModel<String> m = new DefaultListModel<>();
        ArrayList<int[]> colorMaps = new ArrayList<>();

        Path dir = Paths.get(DirName);
        try {
            if(!dir.toFile().exists()) {
                Files.createDirectory(dir);
            }

            if(dir.toFile().exists() && dir.toFile().isDirectory()) {
                Files.walk(dir).filter(path -> path.toFile().isFile()).sorted().forEach(path -> {
                    String name = path.toString();
                    name = name.replace(DirName + "/", "");
                    name = name.replace(DirName + "\\", "");
                    name = name.length() > 60 ? name.substring(0, 59) + "..." : name;
                    int[] res = PaletteMenu.loadDirectPalette(path.toAbsolutePath().toString(), ptra, false);

                    if (res != null) {
                        m.addElement(name);
                        colorMaps.add(res);
                    }
                });
            }
        } catch (IOException e) {

        }


        list = new JList<>(m);
        list.getSelectionModel().setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        list.setTransferHandler(new ListItemTransferHandler());

        list.getInputMap( JComponent.WHEN_FOCUSED ).getParent().remove( KeyStroke.getKeyStroke( KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK ) );
        list.getInputMap( JComponent.WHEN_FOCUSED ).getParent().remove( KeyStroke.getKeyStroke( KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK ) );
        list.getInputMap( JComponent.WHEN_FOCUSED ).getParent().remove( KeyStroke.getKeyStroke( KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK ) );

        //list.setDropMode(DropMode.INSERT);
        //list.setDragEnabled(true);
        //http://java-swing-tips.blogspot.jp/2008/10/rubber-band-selection-drag-and-drop.html
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(0);
        list.setFixedCellWidth(620);
        list.setFixedCellHeight(30);
        list.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        list.setCellRenderer(new ListCellRenderer<String>() {
            private final JPanel p = LAFManager.getJListPanel();
            private final ImageLabel icon = new ImageLabel(null, JLabel.LEFT);
            private final JLabel label = new JLabel("", JLabel.LEFT);

            @Override
            public Component getListCellRendererComponent(
                    JList list, String value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                if(index < colorMaps.size()) {
                    Color[] c = PresetPalette.getPalette(colorMaps.get(index), 0);

                    BufferedImage palette_preview = new BufferedImage(250, 24, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g = palette_preview.createGraphics();

                    for (int j = 0; j < c.length; j++) {
                        if (smoothing) {
                            GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / ((float) c.length), 0, c[j], (j + 1) * palette_preview.getWidth() / ((float) c.length), 0, c[(j + 1) % c.length]);
                            g.setPaint(gp);
                            g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / ((double) c.length), 0, (j + 1) * palette_preview.getWidth() / ((double) c.length) - j * palette_preview.getWidth() / ((double) c.length), palette_preview.getHeight()));
                        } else {
                            g.setColor(c[j]);
                            g.fillRect(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight());
                        }
                    }

                    if(MainWindow.useCustomLaf) {
                        palette_preview = CommonFunctions.makeRoundedCorner(palette_preview, 5);
                    }

                    icon.setIcon(new ImageIcon(palette_preview));
                }

                label.setText(value);
                label.setForeground(list.getForeground());
                p.add(icon);
                p.add(label, BorderLayout.EAST);
                p.setBackground(list.getBackground());

                if(isSelected) {
                    p.setBackground(list.getSelectionBackground());
                    label.setForeground(list.getSelectionForeground());
                }
                return p;
            }
        });

        JScrollPane scroll_pane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll_pane.setPreferredSize(new Dimension(680, 485));

        JPanel p = new JPanel();
        p.setBackground(MainWindow.bg_color);
        p.setPreferredSize(new Dimension(700, 540));
        JLabel total = new JLabel("Loaded Color Maps: " + colorMaps.size());
        p.add(total);


        JButton loadInExplorer = new MyButton();
        loadInExplorer.setIcon(MainWindow.getIcon("folder.png"));
        loadInExplorer.setPreferredSize(new Dimension(32, 32));
        loadInExplorer.setFocusable(false);
        loadInExplorer.setToolTipText("Open map directory in explorer.");

        loadInExplorer.addActionListener( e -> {
            try {
                Desktop.getDesktop().open(dir.toFile());
            } catch (IOException ex) {

            }
        });

        JButton reload = new MyButton();
        reload.setIcon(MainWindow.getIcon("reset.png"));
        reload.setPreferredSize(new Dimension(32, 32));
        reload.setFocusable(false);
        reload.setToolTipText("Reloads the directory contents.");

        reload.addActionListener( e -> {
            m.clear();
            colorMaps.clear();

            try {
                if(!dir.toFile().exists()) {
                    Files.createDirectory(dir);
                }

                if(dir.toFile().exists() && dir.toFile().isDirectory()) {
                    Files.walk(dir).filter(path -> path.toFile().isFile()).sorted().forEach(path -> {
                        String name = path.toString();
                        name = name.replace(DirName + "/", "");
                        name = name.replace(DirName + "\\", "");
                        name = name.length() > 60 ? name.substring(0, 59) + "..." : name;
                        int[] res = PaletteMenu.loadDirectPalette(path.toAbsolutePath().toString(), ptra, false);

                        if (res != null) {
                            m.addElement(name);
                            colorMaps.add(res);
                        }
                    });
                }
            } catch (IOException ex) {

            }

            total.setText("Loaded Color Maps: " + colorMaps.size());
        });

        p.add(Box.createRigidArea(new Dimension(30,0)));
        p.add(loadInExplorer);
        p.add(reload);

        p.add(scroll_pane);

        JPanel buttons = new JPanel();
        buttons.setBackground(MainWindow.bg_color);

        JButton ok = new MyButton("Ok");
        getRootPane().setDefaultButton(ok);
        ok.setFocusable(false);
        ok.addActionListener(e -> {

            int[] vals = list.getSelectedIndices();

            if(vals == null || vals.length != 1) {
                JOptionPane.showMessageDialog(ptra2, "You must select one palette!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            java.util.List<Integer> rgbs = PaletteMenu.importDialog(Arrays.stream(colorMaps.get(vals[0])).boxed().collect(Collectors.toList()), ptra2);
            int[] palette = rgbs.stream().mapToInt(Integer::valueOf).toArray();

            ptra2.setPalette(MainWindow.DIRECT_PALETTE_ID, palette, outcoloring_mode ? 0 : 1);
            palettes[MainWindow.DIRECT_PALETTE_ID].setSelected(true);

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

        JButton cancel = new MyButton("Cancel");
        cancel.setFocusable(false);
        cancel.addActionListener(e -> dispose());

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
        round_panel.setPreferredSize(new Dimension(740, 610));
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

        repaint();
    }
}
