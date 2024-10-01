package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static fractalzoomer.main.MainWindow.activeColor;

/**
 *
 * @author kaloch
 */
public class ProcessingOrderingDialog extends JDialog {

    private static final long serialVersionUID = -1756185420359636585L;
    private MainWindow ptra2;
    private JList<String> list;
    private int[] processing_order;

    public ProcessingOrderingDialog(MainWindow ptra, int[] processing_order, final boolean fake_distance_estimation, final boolean entropy_coloring, final boolean offset_coloring, final boolean rainbow_palette, final boolean greyscale_coloring, final boolean contour_coloring, final boolean bump_mapping, final boolean light, final boolean slopes, final boolean numerical_dem, final boolean histogram) {

        super();

        ptra2 = ptra;
        
        this.processing_order = processing_order;

        setModal(true);
        int custom_palette_window_width = 580;
        int custom_palette_window_height = 480;
        setTitle("Processing Order");
        setIconImage(MainWindow.getIcon("list.png").getImage());

        setSize(custom_palette_window_width, custom_palette_window_height);
        setLocation((int) (ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (custom_palette_window_width / 2), (int) (ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (custom_palette_window_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                dispose();

            }
        });
        
        DefaultListModel<String> m = new DefaultListModel<>();
        for(int i = 0; i < processing_order.length; i++) {
            m.addElement("" + MainWindow.processingAlgorithNames[processing_order[i]]);
        }
        list = new JList<>(m);
        list.getSelectionModel().setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setTransferHandler(new ListItemTransferHandler());
        
        list.getInputMap( JComponent.WHEN_FOCUSED ).getParent().remove( KeyStroke.getKeyStroke( KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK ) );
        list.getInputMap( JComponent.WHEN_FOCUSED ).getParent().remove( KeyStroke.getKeyStroke( KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK ) );
        list.getInputMap( JComponent.WHEN_FOCUSED ).getParent().remove( KeyStroke.getKeyStroke( KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK ) );
        
        list.setDropMode(DropMode.INSERT);
        list.setDragEnabled(true);
        //http://java-swing-tips.blogspot.jp/2008/10/rubber-band-selection-drag-and-drop.html
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(0);
        //list.setFixedCellWidth(80);
        //list.setFixedCellHeight(80);
        list.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        list.addListSelectionListener(e -> list.repaint());

        list.setCellRenderer(new ListCellRenderer<String>() {
            private final JPanel p = LAFManager.getJListPanel();
            private final ImageLabel icon = new ImageLabel(null, JLabel.LEFT);
            private final JLabel label = new JLabel("", JLabel.LEFT);

            @Override
            public Component getListCellRendererComponent(
                    JList list, String value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                
                if(MainWindow.processingAlgorithNames[0].equals(value)) {
                    icon.setIcon(MainWindow.getIcon("fake_distance_estimation.png"));
                }
                else if(MainWindow.processingAlgorithNames[1].equals(value)) {
                    icon.setIcon(MainWindow.getIcon("entropy_coloring.png"));
                }
                else if(MainWindow.processingAlgorithNames[2].equals(value)) {
                    icon.setIcon(MainWindow.getIcon("offset_coloring.png"));
                }
                else if(MainWindow.processingAlgorithNames[3].equals(value)) {
                    icon.setIcon(MainWindow.getIcon("rainbow_palette.png"));
                }
                else if(MainWindow.processingAlgorithNames[4].equals(value)) {
                    icon.setIcon(MainWindow.getIcon("greyscale_coloring.png"));
                }
                else if(MainWindow.processingAlgorithNames[5].equals(value)) {
                    icon.setIcon(MainWindow.getIcon("contour_coloring.png"));
                }
                else if(MainWindow.processingAlgorithNames[6].equals(value)) {
                    icon.setIcon(MainWindow.getIcon("bump_map.png"));
                }
                else if(MainWindow.processingAlgorithNames[7].equals(value)) {
                    icon.setIcon(MainWindow.getIcon("light.png"));
                }
                else if(MainWindow.processingAlgorithNames[8].equals(value)) {
                    icon.setIcon(MainWindow.getIcon("slopes.png"));
                }
                else if(MainWindow.processingAlgorithNames[9].equals(value)) {
                    icon.setIcon(MainWindow.getIcon("numerical_dem.png"));
                }
                else if(MainWindow.processingAlgorithNames[10].equals(value)) {
                    icon.setIcon(MainWindow.getIcon("histogram.png"));
                }
                
                label.setText(value);
                label.setForeground(list.getForeground());
                p.add(icon);
                p.add(label, BorderLayout.EAST);
                p.setBackground(list.getBackground());
                
                if(MainWindow.processingAlgorithNames[0].equals(value)) {
                    if(fake_distance_estimation) {
                        p.setBackground(activeColor);
                        label.setForeground(list.getSelectionForeground());
                    }
                }
                else if(MainWindow.processingAlgorithNames[1].equals(value)) {
                    if(entropy_coloring) {
                        p.setBackground(activeColor);
                        label.setForeground(list.getSelectionForeground());
                    }
                }
                else if(MainWindow.processingAlgorithNames[2].equals(value)) {   
                    if(offset_coloring) {
                        p.setBackground(activeColor);
                        label.setForeground(list.getSelectionForeground());
                    }
                }
                else if(MainWindow.processingAlgorithNames[3].equals(value)) {
                    if(rainbow_palette) {
                        p.setBackground(activeColor);
                        label.setForeground(list.getSelectionForeground());
                    }
                }
                else if(MainWindow.processingAlgorithNames[4].equals(value)) {
                    if(greyscale_coloring) {
                        p.setBackground(activeColor);
                        label.setForeground(list.getSelectionForeground());
                    }
                }
                else if(MainWindow.processingAlgorithNames[5].equals(value)) {
                    if(contour_coloring) {
                        p.setBackground(activeColor);
                        label.setForeground(list.getSelectionForeground());
                    }
                }
                else if(MainWindow.processingAlgorithNames[6].equals(value)) {
                    if(bump_mapping) {
                        p.setBackground(activeColor);
                        label.setForeground(list.getSelectionForeground());
                    }
                }
                else if(MainWindow.processingAlgorithNames[7].equals(value)) {
                    if(light) {
                        p.setBackground(activeColor);
                        label.setForeground(list.getSelectionForeground());
                    }
                }
                else if(MainWindow.processingAlgorithNames[8].equals(value)) {
                    if(slopes) {
                        p.setBackground(activeColor);
                        label.setForeground(list.getSelectionForeground());
                    }
                }
                else if(MainWindow.processingAlgorithNames[9].equals(value)) {
                    if(numerical_dem) {
                        p.setBackground(activeColor);
                        label.setForeground(list.getSelectionForeground());
                    }
                }
                else if(MainWindow.processingAlgorithNames[10].equals(value)) {
                    if(histogram) {
                        p.setBackground(activeColor);
                        label.setForeground(list.getSelectionForeground());
                    }
                }
                
                if(isSelected) {
                    p.setBackground(list.getSelectionBackground());
                    label.setForeground(list.getSelectionForeground());
                }
                return p;
            }
        });
 
        JScrollPane scroll_pane = new JScrollPane(list);
        scroll_pane.setPreferredSize(new Dimension(440, 180));
        JPanel text = new JPanel();
        text.setBackground(MainWindow.bg_color);
        text.setLayout(new GridLayout(4, 1));
        text.setPreferredSize(new Dimension(440, 120));
        
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));
        p1.setBackground(MainWindow.bg_color);
        p1.add(new JLabel("Drag and drop any processing algorithm to change its execution order."));
        
        text.add(p1);
        
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));
        p2.setBackground(MainWindow.bg_color);
        p2.add(new JLabel("Smoothing, Distance Estimation, Orbit Traps, and Statistical Coloring"));
        text.add(p2);
        
        JPanel p4 = new JPanel();
        p4.setLayout(new FlowLayout(FlowLayout.LEFT));
        p4.setBackground(MainWindow.bg_color);
        p4.add(new JLabel("are always performed first."));
        text.add(p4);
        
        JLabel color = new ColorLabel();
        color.setPreferredSize(new Dimension(22, 22));
        color.setBackground(activeColor);
        
        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        p3.setBackground(MainWindow.bg_color);
        p3.add(color);
        p3.add(new JLabel(": Active Processing Algorithms"));
        
        text.add(p3);
        
        JPanel panel = new JPanel();
        panel.setBackground(MainWindow.bg_color);
        panel.setPreferredSize(new Dimension(480, 340));
        panel.setBorder(LAFManager.createTitledBorder("Processing Order"));
        
        panel.add(text);
        panel.add(scroll_pane);

        JPanel buttons = new JPanel();

        buttons.setLayout(new FlowLayout());
        buttons.setBackground(MainWindow.bg_color);

        JButton ok = new MyButton("Ok");
        getRootPane().setDefaultButton(ok);
        ok.setFocusable(false);
        ok.addActionListener(e -> {

            dispose();
            ptra2.setProcessingOrder(getProcessingOrder());

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

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
        getRootPane().getActionMap().put("Cancel", new AbstractAction()
        {

            public void actionPerformed(ActionEvent e)
            {
                cancel.doClick();
            }
        });

        buttons.add(cancel);

        RoundedPanel round_panel = LAFManager.createRoundedPanel();
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(510, 400));
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

        setVisible(true);
    }
    
    public int[] getProcessingOrder() {
        
        for(int i = 0; i < processing_order.length; i++) {
            String name =  list.getModel().getElementAt(i);
            for(int j = 0; j < MainWindow.processingAlgorithNames.length; j++) {
                if(MainWindow.processingAlgorithNames[j].equals(name)) {
                    processing_order[i] = j;
                    break;
                }
            }          
        }
        
        return processing_order;
    }

}
