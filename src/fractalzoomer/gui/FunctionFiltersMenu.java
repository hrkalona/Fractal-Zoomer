package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class FunctionFiltersMenu extends JMenu {

    private static final long serialVersionUID = -794450943243L;
    private MainWindow ptr;
    private JRadioButtonMenuItem[] function_filters;

    public static final String[] functionFilternNames;

    static {
        functionFilternNames = new String[MainWindow.TOTAL_FUNCTION_FILTERS];
        functionFilternNames[MainWindow.NO_FUNCTION_FILTER] = "No Filter";
        functionFilternNames[MainWindow.ABS_FUNCTION_FILTER] = "abs(z)";
        functionFilternNames[MainWindow.SQUARE_FUNCTION_FILTER] = "z^2";
        functionFilternNames[MainWindow.SQRT_FUNCTION_FILTER] = "sqrt(z)";
        functionFilternNames[MainWindow.RECIPROCAL_FUNCTION_FILTER] = "1 / z";
        functionFilternNames[MainWindow.SIN_FUNCTION_FILTER] = "sin(z)";
        functionFilternNames[MainWindow.COS_FUNCTION_FILTER] = "cos(z)";
        functionFilternNames[MainWindow.EXP_FUNCTION_FILTER] = "exp(z)";
        functionFilternNames[MainWindow.LOG_FUNCTION_FILTER] = "log(z)";
        functionFilternNames[MainWindow.USER_FUNCTION_FILTER] = "User Filter";
    }

    public FunctionFiltersMenu(MainWindow ptr2, String name, int filter, boolean isPostFilter) {

        super(name);

        this.ptr = ptr2;

        setIcon(MainWindow.getIcon("function_filter.png"));

        function_filters = new JRadioButtonMenuItem[functionFilternNames.length];

        ButtonGroup function_filter_group = new ButtonGroup();

        for(int i = 0; i < MainWindow.USER_FUNCTION_FILTER; i++) {

            final int temp = i;

            function_filters[i] = new JRadioButtonMenuItem(functionFilternNames[i]);
            function_filters[i].setToolTipText("The \"" + functionFilternNames[i] + "\" function filter.");
            function_filters[i].addActionListener(e -> ptr.setFunctionFilter(temp, isPostFilter));
            add(function_filters[i]);
            function_filter_group.add(function_filters[i]);

        }

        function_filters[MainWindow.USER_FUNCTION_FILTER] = new JRadioButtonMenuItem(functionFilternNames[MainWindow.USER_FUNCTION_FILTER]);
        function_filters[MainWindow.USER_FUNCTION_FILTER].setToolTipText("The \"" + functionFilternNames[MainWindow.USER_FUNCTION_FILTER] + "\" function filter.");
        function_filters[MainWindow.USER_FUNCTION_FILTER].addActionListener(e -> ptr.setFunctionFilter(MainWindow.USER_FUNCTION_FILTER, isPostFilter));
        add(function_filters[MainWindow.USER_FUNCTION_FILTER]);

        if(isPostFilter) {
            function_filters[MainWindow.USER_FUNCTION_FILTER].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, ActionEvent.CTRL_MASK));
        }
        else {
            function_filters[MainWindow.USER_FUNCTION_FILTER].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, ActionEvent.ALT_MASK));
        }

        function_filter_group.add(function_filters[MainWindow.USER_FUNCTION_FILTER]);

        function_filters[filter].setSelected(true);

    }

    public JRadioButtonMenuItem[] getFunctionFilters() {

        return function_filters;

    }

}
