package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.utils.RefreshCpuTask;
import fractalzoomer.utils.RefreshMemoryTask;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;

import static fractalzoomer.gui.CpuLabel.CPU_DELAY;
import static fractalzoomer.gui.MemoryLabel.MEMORY_DELAY;

public class Debugbar extends JToolBar {
    private static final long serialVersionUID = 576943261889826L;
    private JTextField R;
    private JTextField G;

    private JTextField B;

    private JTextField X;

    private JTextField Y;

    private JTextField IterData;

    private Timer timer;
    private Timer timer2;

    private MemoryLabel memory_label;
    private CpuLabel cpuLabel;


    public Debugbar() {
        super();

        int definedHeight = 22;

        if(MainWindow.useCustomLaf) {
            definedHeight = 24;
        }

        setFloatable(false);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        setBorderPainted(true);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setPreferredSize(new Dimension(0, definedHeight));

        JLabel label = new JLabel(" X: ");
        // label.setFont(new Font("Arial", Font.PLAIN, 11));
        add(label);

        X = new JTextField("X");
        X .setHorizontalAlignment(JTextField.RIGHT);
        X.setPreferredSize(new Dimension(50, definedHeight));
        X .setMaximumSize(X.getPreferredSize());
        X .setEditable(false);
        X.setFocusable(false);
        X .setToolTipText("Displays the x coordinate the hovered pixel.");

        add(X);
        add(new JLabel(" "));

        label = new JLabel(" Y: ");
        // label.setFont(new Font("Arial", Font.PLAIN, 11));
        add(label);

        Y = new JTextField("Y");
        Y .setHorizontalAlignment(JTextField.RIGHT);
        Y.setPreferredSize(new Dimension(50, definedHeight));
        Y .setMaximumSize(Y.getPreferredSize());
        Y .setEditable(false);
        Y.setFocusable(false);
        Y .setToolTipText("Displays the y coordinate the hovered pixel.");

        add(Y);

        add(new JLabel(" "));
        addSeparator();
        add(new JLabel(" "));

        label = new JLabel(" R: ");
        // label.setFont(new Font("Arial", Font.PLAIN, 11));
        add(label);

        R = new JTextField("R");
        R .setHorizontalAlignment(JTextField.RIGHT);
        R .setPreferredSize(new Dimension(40, definedHeight));
        R .setMaximumSize(R.getPreferredSize());
        R .setEditable(false);
        R.setFocusable(false);
        R .setForeground(Color.RED);
        R .setToolTipText("Displays the red component of the hovered pixel.");

        add(R);
        add(new JLabel(" "));

        label = new JLabel("G: ");
        add(label);

        G = new JTextField("G");
        G .setHorizontalAlignment(JTextField.RIGHT);
        G .setPreferredSize(new Dimension(40, definedHeight));
        G .setMaximumSize(G.getPreferredSize());
        G .setEditable(false);
        G.setFocusable(false);
        G .setForeground(Color.GREEN.darker());
        G .setToolTipText("Displays the green component of the hovered pixel.");

        add(G);

        add(new JLabel(" "));

        label = new JLabel("B: ");
        add(label);

        B = new JTextField("B");
        B .setHorizontalAlignment(JTextField.RIGHT);
        B .setPreferredSize(new Dimension(40, definedHeight));
        B .setMaximumSize(B.getPreferredSize());
        B .setEditable(false);
        B.setFocusable(false);
        B .setForeground(Color.BLUE);
        B .setToolTipText("Displays the blue component of the hovered pixel.");

        add(B);

        add(new JLabel(" "));
        addSeparator();
        add(new JLabel(" "));

        label = new JLabel(" Iterations: ");
        // label.setFont(new Font("Arial", Font.PLAIN, 11));
        add(label);

        IterData = new JTextField("Iteration Data");
        IterData .setHorizontalAlignment(JTextField.RIGHT);
        IterData.setPreferredSize(new Dimension(160, definedHeight));
        IterData .setMaximumSize(IterData.getPreferredSize());
        IterData .setEditable(false);
        IterData.setFocusable(false);
        IterData .setToolTipText("Displays the iteration data of the hovered pixel.");

        add(IterData);




        add(Box.createHorizontalGlue());
        //statusbar.add(Box.createRigidArea(new Dimension(100,10)));
        addSeparator();

        memory_label = new MemoryLabel(190);
        add(new JLabel(" Mem: "));
        add(memory_label);

        cpuLabel = new CpuLabel(90);
        add(new JLabel(" CPU: "));
        add(cpuLabel);


    }

    public JTextField getR() {

        return R;

    }

    public JTextField getG() {

        return G;

    }

    public JTextField getB() {

        return B;

    }

    public JTextField getXTf() {

        return X;

    }

    public JTextField getYTf() {

        return Y;

    }

    public JTextField getIterData() {

        return IterData;

    }
    private void setMemoryTask(boolean enable) {
        if(enable) {
            if (timer == null) {
                timer = new Timer();
                timer.schedule(new RefreshMemoryTask(memory_label), MEMORY_DELAY, MEMORY_DELAY);
            }
            else {
                timer.cancel();
                timer = new Timer();
                timer.schedule(new RefreshMemoryTask(memory_label), MEMORY_DELAY, MEMORY_DELAY);
            }

            if (timer2 == null) {
                timer2 = new Timer();
                timer2.schedule(new RefreshCpuTask(cpuLabel), CPU_DELAY, CPU_DELAY);
            }
            else {
                timer2.cancel();
                timer2 = new Timer();
                timer2.schedule(new RefreshCpuTask(cpuLabel), CPU_DELAY, CPU_DELAY);
            }
        }
        else {
            if(timer != null) {
                timer.cancel();
                timer = null;
            }
            if(timer2 != null) {
                timer2.cancel();
                timer2 = null;
            }
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        setMemoryTask(visible);
    }

}
