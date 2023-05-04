package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class PlaneInfluenceMenu extends MyMenu {

    private static final long serialVersionUID = -794450943243L;
    private MainWindow ptr;
    private JRadioButtonMenuItem[] plane_influences;

    public static final String[] planeInfluenceNames;

    static {
        planeInfluenceNames = new String[MainWindow.TOTAL_PLANE_INFLUENCES];
        planeInfluenceNames[MainWindow.NO_PLANE_INFLUENCE] = "No Plane Influence";
        planeInfluenceNames[MainWindow.USER_PLANE_INFLUENCE] = "User Plane Influence";
    }

    public PlaneInfluenceMenu(MainWindow ptr2, int plane_influence) {

        super("Plane Influence");

        this.ptr = ptr2;

        setIcon(MainWindow.getIcon("plane_influence.png"));

        plane_influences = new JRadioButtonMenuItem[planeInfluenceNames.length];

        ButtonGroup function_filter_group = new ButtonGroup();

        for(int i = 0; i < MainWindow.USER_PLANE_INFLUENCE; i++) {

            plane_influences[i] = new JRadioButtonMenuItem(planeInfluenceNames[i]);
            plane_influences[i].setToolTipText(planeInfluenceNames[i]);

            final int temp = i;
            plane_influences[i].addActionListener(e -> ptr.setPlaneInfluence(temp));
            add(plane_influences[i]);
            function_filter_group.add(plane_influences[i]);

        }

        plane_influences[MainWindow.USER_PLANE_INFLUENCE] = new JRadioButtonMenuItem(planeInfluenceNames[MainWindow.USER_PLANE_INFLUENCE]);
        plane_influences[MainWindow.USER_PLANE_INFLUENCE].setToolTipText(planeInfluenceNames[MainWindow.USER_PLANE_INFLUENCE]);
        plane_influences[MainWindow.USER_PLANE_INFLUENCE].addActionListener(e -> ptr.setPlaneInfluence(MainWindow.USER_PLANE_INFLUENCE));
        add(plane_influences[MainWindow.USER_PLANE_INFLUENCE]);

        plane_influences[MainWindow.USER_PLANE_INFLUENCE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_7, ActionEvent.CTRL_MASK));

        function_filter_group.add(plane_influences[MainWindow.USER_PLANE_INFLUENCE]);

        plane_influences[plane_influence].setSelected(true);

    }

    public JRadioButtonMenuItem[] getPlaneInfluences() {

        return plane_influences;

    }

}
