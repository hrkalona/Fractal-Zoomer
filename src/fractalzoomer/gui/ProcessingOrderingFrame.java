/*
 * Copyright (C) 2018 kaloch
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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author kaloch
 */
public class ProcessingOrderingFrame extends JFrame {

    private static final long serialVersionUID = -1756185420359636585L;
    private MainWindow ptra2;
    private JList<String> list;
    private int[] processing_order;
    private Color activeColor;

    public ProcessingOrderingFrame(MainWindow ptra, int[] processing_order, final boolean fake_distance_estimation, final boolean entropy_coloring, final boolean offset_coloring, final boolean rainbow_palette, final boolean greyscale_coloring, final boolean contour_coloring, final boolean bump_mapping) {

        super();

        ptra2 = ptra;
        
        this.processing_order = processing_order;
        
        activeColor = new Color(185, 223, 147);

        ptra2.setEnabled(false);
        int custom_palette_window_width = 580;
        int custom_palette_window_height = 460;
        setTitle("Processing Order");
        setIconImage(getIcon("/fractalzoomer/icons/list.png").getImage());

        setSize(custom_palette_window_width, custom_palette_window_height);
        setLocation((int) (ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (custom_palette_window_width / 2), (int) (ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (custom_palette_window_height / 2));

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                ptra2.setEnabled(true);
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

        list.setCellRenderer(new ListCellRenderer<String>() {
            private final JPanel p = new JPanel(new BorderLayout());
            private final JLabel icon = new JLabel((Icon)null, JLabel.LEFT);
            private final JLabel label = new JLabel("", JLabel.LEFT);

            @Override
            public Component getListCellRendererComponent(
                    JList list, String value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                
                if(MainWindow.processingAlgorithNames[0].equals(value)) {
                    icon.setIcon(getIcon("/fractalzoomer/icons/fake_distance_estimation.png"));
                }
                else if(MainWindow.processingAlgorithNames[1].equals(value)) {
                    icon.setIcon(getIcon("/fractalzoomer/icons/entropy_coloring.png"));
                }
                else if(MainWindow.processingAlgorithNames[2].equals(value)) {
                    icon.setIcon(getIcon("/fractalzoomer/icons/offset_coloring.png"));
                }
                else if(MainWindow.processingAlgorithNames[3].equals(value)) {
                    icon.setIcon(getIcon("/fractalzoomer/icons/rainbow_palette.png"));
                }
                else if(MainWindow.processingAlgorithNames[4].equals(value)) {
                    icon.setIcon(getIcon("/fractalzoomer/icons/greyscale_coloring.png"));
                }
                else if(MainWindow.processingAlgorithNames[5].equals(value)) {
                    icon.setIcon(getIcon("/fractalzoomer/icons/contour_coloring.png"));
                }
                else if(MainWindow.processingAlgorithNames[6].equals(value)) {
                    icon.setIcon(getIcon("/fractalzoomer/icons/bump_map.png"));
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
        text.setLayout(new GridLayout(3, 1));
        text.setPreferredSize(new Dimension(440, 100));
        
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));
        p1.setBackground(MainWindow.bg_color);
        p1.add(new JLabel("Drag and drop any processing algorithm to change its execution order."));
        
        text.add(p1);
        
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));
        p2.setBackground(MainWindow.bg_color);
        p2.add(new JLabel("Smoothing, Distance Estimation, and Orbit Traps are always performed first."));
        text.add(p2);
        
        JLabel color = new JLabel();
        color.setPreferredSize(new Dimension(22, 22));
        color.setOpaque(true);
        color.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        color.setBackground(activeColor);
        
        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        p3.setBackground(MainWindow.bg_color);
        p3.add(color);
        p3.add(new JLabel(": Active Processing Algorithms"));
        
        text.add(p3);
        
        JPanel panel = new JPanel();
        panel.setBackground(MainWindow.bg_color);
        panel.setPreferredSize(new Dimension(480, 320));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Processing Order", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        
        panel.add(text);
        panel.add(scroll_pane);

        JPanel buttons = new JPanel();

        buttons.setLayout(new FlowLayout());
        buttons.setBackground(MainWindow.bg_color);

        JButton ok = new JButton("Ok");
        ok.setFocusable(false);
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptra2.setProcessingOrder(getProcessingOrder());
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
        round_panel.setPreferredSize(new Dimension(510, 380));
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

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

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
