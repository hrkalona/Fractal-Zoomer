
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 *
 * @author hrkalona2
 */
public class OutColoringModesMenu extends MyMenu {
	private static final long serialVersionUID = -1025728538507111408L;
	private MainWindow ptr;
    private JRadioButtonMenuItem[] out_coloring_modes;

    public static final String[] outColoringNames;

    static {
        outColoringNames = new String[MainWindow.TOTAL_OUTCOLORING_ALGORITHMS];
        outColoringNames[MainWindow.ESCAPE_TIME] = "Escape Time";
        outColoringNames[MainWindow.BINARY_DECOMPOSITION] = "Binary Decomposition";
        outColoringNames[MainWindow.BINARY_DECOMPOSITION2] = "Binary Decomposition 2";
        outColoringNames[MainWindow.ITERATIONS_PLUS_RE] = "Escape Time + Re";
        outColoringNames[MainWindow.ITERATIONS_PLUS_IM] = "Escape Time + Im";
        outColoringNames[MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM] = "Escape Time + Re / Im";
        outColoringNames[MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM] = "Escape Time + Re + Im + Re / Im";
        outColoringNames[MainWindow.BIOMORPH] = "Biomorph";
        outColoringNames[MainWindow.COLOR_DECOMPOSITION] = "Color Decomposition";
        outColoringNames[MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION] = "Escape Time + Color Decomposition";
        outColoringNames[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER] = "Escape Time + Gaussian Integer";
        outColoringNames[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2] = "Escape Time + Gaussian Integer 2";
        outColoringNames[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3] = "Escape Time + Gaussian Integer 3";
        outColoringNames[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4] = "Escape Time + Gaussian Integer 4";
        outColoringNames[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5] = "Escape Time + Gaussian Integer 5";
        outColoringNames[MainWindow.ESCAPE_TIME_ALGORITHM] = "Escape Time + Algorithm";
        outColoringNames[MainWindow.ESCAPE_TIME_ALGORITHM2] = "Escape Time + Algorithm 2";
        outColoringNames[MainWindow.DISTANCE_ESTIMATOR] = "Distance Estimator";
        outColoringNames[MainWindow.ESCAPE_TIME_ESCAPE_RADIUS] = "Escape Time + Escape Radius";
        outColoringNames[MainWindow.ESCAPE_TIME_GRID] = "Escape Time + Grid";
        outColoringNames[MainWindow.BANDED] = "Banded";
        outColoringNames[MainWindow.USER_OUTCOLORING_ALGORITHM] = "User Out Coloring Method";
        outColoringNames[MainWindow.ESCAPE_TIME_FIELD_LINES] = "Escape Time + Field Lines";
        outColoringNames[MainWindow.ESCAPE_TIME_FIELD_LINES2] = "Escape Time + Field Lines 2";
        outColoringNames[MainWindow.ESCAPE_TIME_SQUARES] = "Escape Time + Squares";
        outColoringNames[MainWindow.ESCAPE_TIME_SQUARES2] = "Escape Time + Squares2";
    }

    public OutColoringModesMenu(MainWindow ptr2, String name, int out_coloring_algorithm) {

        super(name);

        this.ptr = ptr2;

        setIcon(MainWindow.getIcon("out_coloring_mode.png"));

        out_coloring_modes = new JRadioButtonMenuItem[outColoringNames.length];

        ButtonGroup outcoloring_button_group = new ButtonGroup();

        out_coloring_modes[MainWindow.ESCAPE_TIME] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ESCAPE_TIME]);
        out_coloring_modes[MainWindow.ESCAPE_TIME].setToolTipText("Sets the out-coloring method, using the iterations.");
        out_coloring_modes[MainWindow.ESCAPE_TIME].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ESCAPE_TIME));
        add(out_coloring_modes[MainWindow.ESCAPE_TIME]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ESCAPE_TIME]);

        out_coloring_modes[MainWindow.BINARY_DECOMPOSITION] = new JRadioButtonMenuItem(outColoringNames[MainWindow.BINARY_DECOMPOSITION]);
        out_coloring_modes[MainWindow.BINARY_DECOMPOSITION].setToolTipText("Sets the out-coloring method, using binary decomposition.");
        out_coloring_modes[MainWindow.BINARY_DECOMPOSITION].addActionListener(e -> ptr.setOutColoringMode(MainWindow.BINARY_DECOMPOSITION));
        add(out_coloring_modes[MainWindow.BINARY_DECOMPOSITION]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.BINARY_DECOMPOSITION]);

        out_coloring_modes[MainWindow.BINARY_DECOMPOSITION2] = new JRadioButtonMenuItem(outColoringNames[MainWindow.BINARY_DECOMPOSITION2]);
        out_coloring_modes[MainWindow.BINARY_DECOMPOSITION2].setToolTipText("Sets the out-coloring method, using binary decomposition 2.");
        out_coloring_modes[MainWindow.BINARY_DECOMPOSITION2].addActionListener(e -> ptr.setOutColoringMode(MainWindow.BINARY_DECOMPOSITION2));
        add(out_coloring_modes[MainWindow.BINARY_DECOMPOSITION2]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.BINARY_DECOMPOSITION2]);

        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ITERATIONS_PLUS_RE]);
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE].setToolTipText("Sets the out-coloring method, using the iterations + Re(z).");
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ITERATIONS_PLUS_RE));
        add(out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE]);

        out_coloring_modes[MainWindow.ITERATIONS_PLUS_IM] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ITERATIONS_PLUS_IM]);
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_IM].setToolTipText("Sets the out-coloring method, using the iterations + Im(z).");
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_IM].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ITERATIONS_PLUS_IM));
        add(out_coloring_modes[MainWindow.ITERATIONS_PLUS_IM]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ITERATIONS_PLUS_IM]);

        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM]);
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM].setToolTipText("Sets the out-coloring method, using the iterations + Re(z)/Im(z).");
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM));
        add(out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM]);

        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM]);
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setToolTipText("Sets the out-coloring method, using the iterations + Re(z) + Im(z) + Re(z)/Im(z).");
        out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM));
        add(out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM]);

        out_coloring_modes[MainWindow.BIOMORPH] = new JRadioButtonMenuItem(outColoringNames[MainWindow.BIOMORPH]);
        out_coloring_modes[MainWindow.BIOMORPH].setToolTipText("Sets the out-coloring method, using biomorph.");
        out_coloring_modes[MainWindow.BIOMORPH].addActionListener(e -> ptr.setOutColoringMode(MainWindow.BIOMORPH));
        add(out_coloring_modes[MainWindow.BIOMORPH]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.BIOMORPH]);

        out_coloring_modes[MainWindow.COLOR_DECOMPOSITION] = new JRadioButtonMenuItem(outColoringNames[MainWindow.COLOR_DECOMPOSITION]);
        out_coloring_modes[MainWindow.COLOR_DECOMPOSITION].setToolTipText("Sets the out-coloring method, using color decomposition.");
        out_coloring_modes[MainWindow.COLOR_DECOMPOSITION].addActionListener(e -> ptr.setOutColoringMode(MainWindow.COLOR_DECOMPOSITION));
        add(out_coloring_modes[MainWindow.COLOR_DECOMPOSITION]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.COLOR_DECOMPOSITION]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION]);
        out_coloring_modes[MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION].setToolTipText("Sets the out-coloring method, using iterations + color decomposition.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION));
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER]);
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER].setToolTipText("Sets the out-coloring method, using Escape Time + Gaussian Integer.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER));
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2]);
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2].setToolTipText("Sets the out-coloring method, using Escape Time + Gaussian Integer 2.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2));
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3]);
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3].setToolTipText("Sets the out-coloring method, using Escape Time + Gaussian Integer 3.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3));
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4]);
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4].setToolTipText("Sets the out-coloring method, using Escape Time + Gaussian Integer 4.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4));
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5]);
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5].setToolTipText("Sets the out-coloring method, using Escape Time + Gaussian Integer 5.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5));
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ESCAPE_TIME_ALGORITHM]);
        out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM].setToolTipText("Sets the out-coloring method, using Escape Time + Algorithm.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_ALGORITHM));
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM2] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ESCAPE_TIME_ALGORITHM2]);
        out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM2].setToolTipText("Sets the out-coloring method, using Escape Time + Algorithm 2.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM2].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_ALGORITHM2));
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM2]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ESCAPE_TIME_ALGORITHM2]);

        out_coloring_modes[MainWindow.DISTANCE_ESTIMATOR] = new JRadioButtonMenuItem(outColoringNames[MainWindow.DISTANCE_ESTIMATOR]);
        out_coloring_modes[MainWindow.DISTANCE_ESTIMATOR].setToolTipText("Sets the out-coloring method, using Distance Estimator.");
        out_coloring_modes[MainWindow.DISTANCE_ESTIMATOR].addActionListener(e -> ptr.setOutColoringMode(MainWindow.DISTANCE_ESTIMATOR));
        add(out_coloring_modes[MainWindow.DISTANCE_ESTIMATOR]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.DISTANCE_ESTIMATOR]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_ESCAPE_RADIUS] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ESCAPE_TIME_ESCAPE_RADIUS]);
        out_coloring_modes[MainWindow.ESCAPE_TIME_ESCAPE_RADIUS].setToolTipText("Sets the out-coloring method, using Escape Time + Escape Radius.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_ESCAPE_RADIUS].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_ESCAPE_RADIUS));
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_ESCAPE_RADIUS]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ESCAPE_TIME_ESCAPE_RADIUS]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_GRID] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ESCAPE_TIME_GRID]);
        out_coloring_modes[MainWindow.ESCAPE_TIME_GRID].setToolTipText("Sets the out-coloring method, using Escape Time + Grid.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_GRID].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_GRID));
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_GRID]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ESCAPE_TIME_GRID]);

        /*out_coloring_modes[MainWindow.ATOM_DOMAIN] = new JRadioButtonMenuItem("Atom Domain");
         out_coloring_modes[MainWindow.ATOM_DOMAIN].setToolTipText("Sets the out-coloring method, using atom domain.");
         out_coloring_modes[MainWindow.ATOM_DOMAIN].addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {

         ptr.setOutColoringMode(MainWindow.ATOM_DOMAIN);

         }
         });
         add(out_coloring_modes[MainWindow.ATOM_DOMAIN]);
         outcoloring_button_group.add(out_coloring_modes[MainWindow.ATOM_DOMAIN]);*/
        out_coloring_modes[MainWindow.BANDED] = new JRadioButtonMenuItem(outColoringNames[MainWindow.BANDED]);
        out_coloring_modes[MainWindow.BANDED].setToolTipText("Sets the out-coloring method, using an iteration based coloring.");
        out_coloring_modes[MainWindow.BANDED].addActionListener(e -> ptr.setOutColoringMode(MainWindow.BANDED));
        add(out_coloring_modes[MainWindow.BANDED]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.BANDED]);
        
        
        out_coloring_modes[MainWindow.ESCAPE_TIME_FIELD_LINES] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ESCAPE_TIME_FIELD_LINES]);
        out_coloring_modes[MainWindow.ESCAPE_TIME_FIELD_LINES].setToolTipText("Sets the out-coloring method, using Escape Time + Field Lines.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_FIELD_LINES].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_FIELD_LINES));
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_FIELD_LINES]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ESCAPE_TIME_FIELD_LINES]);
        
        
        out_coloring_modes[MainWindow.ESCAPE_TIME_FIELD_LINES2] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ESCAPE_TIME_FIELD_LINES2]);
        out_coloring_modes[MainWindow.ESCAPE_TIME_FIELD_LINES2].setToolTipText("Sets the out-coloring method, using Escape Time + Field Lines 2.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_FIELD_LINES2].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_FIELD_LINES2));
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_FIELD_LINES2]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ESCAPE_TIME_FIELD_LINES2]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_SQUARES] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ESCAPE_TIME_SQUARES]);
        out_coloring_modes[MainWindow.ESCAPE_TIME_SQUARES].setToolTipText("Sets the out-coloring method, using Escape Time + Squares.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_SQUARES].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_SQUARES));
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_SQUARES]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ESCAPE_TIME_SQUARES]);

        out_coloring_modes[MainWindow.ESCAPE_TIME_SQUARES2] = new JRadioButtonMenuItem(outColoringNames[MainWindow.ESCAPE_TIME_SQUARES2]);
        out_coloring_modes[MainWindow.ESCAPE_TIME_SQUARES2].setToolTipText("Sets the out-coloring method, using Escape Time + Squares 2.");
        out_coloring_modes[MainWindow.ESCAPE_TIME_SQUARES2].addActionListener(e -> ptr.setOutColoringMode(MainWindow.ESCAPE_TIME_SQUARES2));
        add(out_coloring_modes[MainWindow.ESCAPE_TIME_SQUARES2]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.ESCAPE_TIME_SQUARES2]);

        out_coloring_modes[MainWindow.USER_OUTCOLORING_ALGORITHM] = new JRadioButtonMenuItem(outColoringNames[MainWindow.USER_OUTCOLORING_ALGORITHM]);
        out_coloring_modes[MainWindow.USER_OUTCOLORING_ALGORITHM].setToolTipText("A user defined out-coloring method.");
        out_coloring_modes[MainWindow.USER_OUTCOLORING_ALGORITHM].addActionListener(e -> ptr.setOutColoringMode(MainWindow.USER_OUTCOLORING_ALGORITHM));
        out_coloring_modes[MainWindow.USER_OUTCOLORING_ALGORITHM].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        add(out_coloring_modes[MainWindow.USER_OUTCOLORING_ALGORITHM]);
        outcoloring_button_group.add(out_coloring_modes[MainWindow.USER_OUTCOLORING_ALGORITHM]);

        out_coloring_modes[out_coloring_algorithm].setSelected(true);

    }

    public JRadioButtonMenuItem[] getOutColoringModes() {

        return out_coloring_modes;

    }

}
