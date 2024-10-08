
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static fractalzoomer.main.MainWindow.activeColor;

/**
 *
 * @author hrkalona2
 */
public class FilterOrderSelectionPanel extends JPanel {
	private static final long serialVersionUID = -136372746736364960L;
	private JList<String> list;
    private JCheckBoxMenuItem[] mFilters;
    private int[] filter_order;
    private boolean[] mActiveFilters;
    
    public FilterOrderSelectionPanel(JCheckBoxMenuItem[] filters, int[] filter_order, boolean[] activeFilters) {
        
        super();
        mFilters = filters;
        this.filter_order = filter_order;
        mActiveFilters = activeFilters;
        
        DefaultListModel<String> m = new DefaultListModel<>();
        for(int i = 0; i < filter_order.length; i++) {
            m.addElement("" + filters[filter_order[i]].getText());
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
                
                if(FiltersMenu.getDetailNamesList().contains(value)) {
                    icon.setIcon(MainWindow.getIcon("filter_details.png"));
                }
                else if(FiltersMenu.getColorNamesList().contains(value)) {
                    icon.setIcon(MainWindow.getIcon("filter_colors.png"));
                }
                else if(FiltersMenu.getTextureNamesList().contains(value)) {
                    icon.setIcon(MainWindow.getIcon("filter_texture.png"));
                }
                else {
                    icon.setIcon(MainWindow.getIcon("filter_lighting.png"));
                }
                
                label.setText(value);
                label.setForeground(list.getForeground());
                p.add(icon);
                p.add(label, BorderLayout.EAST);
                p.setBackground(list.getBackground());
                
                int k;
                for(k = 0; k < mFilters.length; k++) {
                    if(mFilters[k].getText().equals(value)) {
                        break;
                    }
                }
                        
                if(mActiveFilters[k]) {
                    p.setBackground(activeColor);
                    label.setForeground(list.getSelectionForeground());
                }
                
                if(isSelected) {
                    p.setBackground(list.getSelectionBackground());
                    label.setForeground(list.getSelectionForeground());
                }
                return p;
            }
        });
 
        JScrollPane scroll_pane = new JScrollPane(list);
        scroll_pane.setPreferredSize(new Dimension(400, 485));
        JPanel text = new JPanel();
        text.setBackground(MainWindow.bg_color);
        text.setLayout(new GridLayout(3, 1));
        
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));
        p1.setBackground(MainWindow.bg_color);
        p1.add(new JLabel("Drag and drop any filter to change its execution order."));
        
        text.add(p1);
        
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));
        p2.setBackground(MainWindow.bg_color);
        p2.add(new JLabel("Anti-Aliasing is always performed first."));
        text.add(p2);
        
        JLabel color = new ColorLabel();
        color.setPreferredSize(new Dimension(22, 22));
        color.setBackground(activeColor);
        
        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        p3.setBackground(MainWindow.bg_color);
        p3.add(color);
        p3.add(new JLabel(": Active Filters"));
        
        text.add(p3);
        
        add(text);
        add(Box.createRigidArea(new Dimension(30,0)));
        add(scroll_pane);

    }
    
    public int[] getFilterOrder() {
        
        for(int i = 0; i < filter_order.length; i++) {
            String name =  list.getModel().getElementAt(i);
            for(int j = 0; j < mFilters.length; j++) {
                if(mFilters[j].getText().equals(name)) {
                    filter_order[i] = j;
                    break;
                }
            }          
        }
        
        return filter_order;
    }

}
