package fractalzoomer.gui;

import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.palettes.PresetPalette;
import fractalzoomer.settings.SettingsFractals;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BatchRenderFrame extends JFrame {
    private static final long serialVersionUID = 8403887235484988L;
    private ImageExpanderWindow ptra2;
    private JList<String> list;

    private JFileChooser file_chooser;

    private ArrayList<String> files = new ArrayList<>();
    private ArrayList<Boolean> hasLoadedOk = new ArrayList<>();

    private DefaultListModel<String> m;

    private JLabel settingsLength;

    private JButton ok;

    public BatchRenderFrame(ImageExpanderWindow ptra) {

        super();

        ptra2 = ptra;

        ptra2.setEnabled(false);
        int filters_options_window_width = 810;
        int filters_options_window_height = 690;
        setTitle("Batch Render");
        setIconImage(MainWindow.getIcon("batch_render.png").getImage());
        setSize(filters_options_window_width, filters_options_window_height);
        setLocation((int)(ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (filters_options_window_width / 2), (int)(ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (filters_options_window_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {


                ptra2.setEnabled(true);

                dispose();

            }
        });


        m = new DefaultListModel<>();

        list = new JList<>(m);
        list.getSelectionModel().setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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

        list.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    delete();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) { }

            @Override
            public void keyTyped(KeyEvent e) { }
        });


        list.setCellRenderer(new ListCellRenderer<String>() {
            private final JPanel p = new JPanel(new BorderLayout());
            private final JLabel icon = new JLabel((Icon)null, JLabel.LEFT);
            private final JLabel label = new JLabel("", JLabel.LEFT);

            @Override
            public Component getListCellRendererComponent(
                    JList list, String value, int index,
                    boolean isSelected, boolean cellHasFocus) {


                label.setText(value);
                label.setForeground(list.getForeground());

                if(hasLoadedOk.get(index)) {
                    icon.setIcon(MainWindow.getIcon("check.png"));
                }
                else {
                    icon.setIcon(MainWindow.getIcon("error.png"));
                }
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
        scroll_pane.setPreferredSize(new Dimension(680, 470));

        JButton  loadButton = new JButton("Load", MainWindow.getIcon("load.png"));
        loadButton.setFocusable(false);
        loadButton.setToolTipText("<html>Loads the function, plane, center, size, color options, iterations,<br> rotation, perturbation, initial value, bailout, julia settings,<br>and image filters.</html>");
        loadButton.addActionListener(e -> loadSettings());

        JButton removeSettings = new JButton("Delete");
        removeSettings.setIcon(MainWindow.getIcon("delete_small.png"));
        removeSettings.setFocusable(false);
        removeSettings.setToolTipText("Remove settings.");


        settingsLength = new JLabel("Total Files: " + list.getModel().getSize());

        removeSettings.addActionListener(e -> {
            delete();
        });

        JPanel p = new JPanel();
        p.setBackground(MainWindow.bg_color);
        p.setPreferredSize(new Dimension(700, 520));
        p.add(settingsLength);
        p.add(new JLabel("  "));
        p.add(loadButton);
        p.add(new JLabel("  "));
        p.add(removeSettings);
        p.add(scroll_pane);

        JPanel buttons = new JPanel();
        buttons.setBackground(MainWindow.bg_color);

        ok = new JButton("Render");
        getRootPane().setDefaultButton(ok);
        ok.setFocusable(false);
        ok.addActionListener(e -> {


            ptra2.setEnabled(true);

            dispose();

            ptra2.startBatchRender(files, m);

        });

        ok.setEnabled(!hasLoadedOk.isEmpty() && hasLoadedOk.stream().allMatch(v -> v));

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
    }

    public void loadSettings() {

        file_chooser = new JFileChooser(MainWindow.SaveSettingsPath.isEmpty() ? "." : MainWindow.SaveSettingsPath);
        file_chooser.setMultiSelectionEnabled(true);

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fractal Zoomer Settings (*.fzs)", "fzs"));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
            String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
            file_chooser.setSelectedFile(new File(file_name));
        });

        int returnVal = file_chooser.showDialog(this, "Load Multiple Settings");

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File[] loadedFiles = file_chooser.getSelectedFiles();

            if(loadedFiles == null) {
                return;
            }

            for(int i = 0; i < loadedFiles.length; i++) {

                String filePath = loadedFiles[i].toString();
                files.add(filePath);

                String name = loadedFiles[i].getName().length() > 100 ? loadedFiles[i].getName().substring(0, 99) + "..." : loadedFiles[i].getName();
                m.addElement(name);
                try {
                    ObjectInputStream file_temp = new ObjectInputStream(new FileInputStream(filePath));

                    SettingsFractals settings = (SettingsFractals) file_temp.readObject();

                    int version = settings.getVersion();
                    hasLoadedOk.add(true);
                }
                catch (Exception ex) {
                    hasLoadedOk.add(false);
                }

            }

            settingsLength.setText("Total Files: " + list.getModel().getSize());
            ok.setEnabled(!hasLoadedOk.isEmpty() && hasLoadedOk.stream().allMatch(v -> v));

        }
    }

    private void delete() {
        int[] indx = list.getSelectedIndices();

        if(indx == null || indx.length == 0) {
            return;
        }
        DefaultListModel model = (DefaultListModel) list.getModel();

        for (int i = indx.length-1; i >=0; i--) {
            model.remove(indx[i]);
            files.remove(indx[i]);
            hasLoadedOk.remove(indx[i]);
        }

        settingsLength.setText("Total Files: " + list.getModel().getSize());
        ok.setEnabled(!hasLoadedOk.isEmpty() && hasLoadedOk.stream().allMatch(v -> v));
    }
}
