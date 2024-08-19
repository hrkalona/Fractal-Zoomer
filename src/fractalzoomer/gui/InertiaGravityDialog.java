
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class InertiaGravityDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public InertiaGravityDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType, boolean wasEscapingOrConvergingType, boolean wasMagnetPatakiType) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Modified Inertia/Gravity");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());

        p1.add(new JLabel("Inertia Contribution Re: "));
        MyJSpinner inertia_contribution_re = new MyJSpinner(10, new SpinnerNumberModel(s.fns.igs.inertia_contribution[0], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        p1.add(inertia_contribution_re);

        p1.add(new JLabel(" Im: "));
        MyJSpinner inertia_contribution_im = new MyJSpinner(10, new SpinnerNumberModel(s.fns.igs.inertia_contribution[1], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        p1.add(inertia_contribution_im);

        p1.add(new JLabel(" Initial Inertia Re: "));
        MyJSpinner init_inertia_re = new MyJSpinner(10, new SpinnerNumberModel(s.fns.igs.initial_inertia[0], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        p1.add(init_inertia_re);

        p1.add(new JLabel(" Im: "));
        MyJSpinner init_inertia_im = new MyJSpinner(10, new SpinnerNumberModel(s.fns.igs.initial_inertia[1], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        p1.add(init_inertia_im);
        
        JComboBox<String> pull_choice = new JComboBox<>(MainWindow.inertiaGravityPullFunction);
        pull_choice.setSelectedIndex(s.fns.igs.pull_scaling_function);
        pull_choice.setToolTipText("Sets the pull scaling function.");
        pull_choice.setFocusable(false);
        
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        
        p2.add(new JLabel("Scaling Function: "));
        p2.add(pull_choice);
        
        p2.add(new JLabel(" Exponent: "));
        MyJSpinner exponent = new MyJSpinner(10, new SpinnerNumberModel(s.fns.igs.inertia_exponent, -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        p2.add(exponent);
        
        p2.add(new JLabel(" Timestep Re: "));
        MyJSpinner timestep_re = new MyJSpinner(10, new SpinnerNumberModel(s.fns.igs.time_step[0], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        p2.add(timestep_re);

        p2.add(new JLabel(" Im: "));
        MyJSpinner timestep_im = new MyJSpinner(10, new SpinnerNumberModel( s.fns.igs.time_step[1], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        p2.add(timestep_im);
        
        exponent.setEnabled(s.fns.igs.pull_scaling_function == MainWindow.PULL_EXP);
        
        pull_choice.addActionListener(e -> exponent.setEnabled(pull_choice.getSelectedIndex() == MainWindow.PULL_EXP));

        JPanel[] inertia_panels = new JPanel[s.fns.igs.bodyLocation.length];
        MyJSpinner[] body_re = new MyJSpinner[inertia_panels.length];
        MyJSpinner[] body_im = new MyJSpinner[inertia_panels.length];
        MyJSpinner[] body_gravity_re = new MyJSpinner[inertia_panels.length];
        MyJSpinner[] body_gravity_im = new MyJSpinner[inertia_panels.length];

        for (int k = 0; k < inertia_panels.length; k++) {
            inertia_panels[k] = new JPanel();
            inertia_panels[k].setLayout(new FlowLayout());

            inertia_panels[k].add(new JLabel("[Body " + String.format("%02d", (k + 1)) + "] "));
            inertia_panels[k].add(new JLabel("Strength Re: "));
            body_gravity_re[k] = new MyJSpinner(10, new SpinnerNumberModel( s.fns.igs.bodyGravity[k][0], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
            inertia_panels[k].add(body_gravity_re[k]);

            inertia_panels[k].add(new JLabel(" Im: "));
            body_gravity_im[k] = new MyJSpinner(10, new SpinnerNumberModel( s.fns.igs.bodyGravity[k][1], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
            inertia_panels[k].add(body_gravity_im[k]);

            inertia_panels[k].add(new JLabel(" Location Re: "));
            body_re[k] = new MyJSpinner(10, new SpinnerNumberModel( s.fns.igs.bodyLocation[k][0], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
            inertia_panels[k].add(body_re[k]);

            inertia_panels[k].add(new JLabel(" Im: "));
            body_im[k] = new MyJSpinner(10, new SpinnerNumberModel( s.fns.igs.bodyLocation[k][1], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
            inertia_panels[k].add(body_im[k]);
        }
        

        Object[] magnets = {
            " ",
            "Insert the modified inertia/gravity simulation parameters",
            p1,
            p2,
            inertia_panels[0],
            inertia_panels[1],
            inertia_panels[2],
            inertia_panels[3],
            inertia_panels[4],
            inertia_panels[5],
            inertia_panels[6],
            inertia_panels[7],
            inertia_panels[8],
            inertia_panels[9],
            " "};

        optionPane = new JOptionPane(magnets, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                optionPane.setValue(JOptionPane.CLOSED_OPTION);
            }
        });

        optionPane.addPropertyChangeListener(
                e -> {
                    String prop = e.getPropertyName();

                    if (isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {

                        Object value = optionPane.getValue();

                        if (value == JOptionPane.UNINITIALIZED_VALUE) {
                            //ignore reset
                            return;
                        }

                        //Reset the JOptionPane's value.
                        //If you don't do this, then if the user
                        //presses the same button next time, no
                        //property change event will be fired.
                        optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

                        if ((Integer) value == JOptionPane.CANCEL_OPTION || (Integer) value == JOptionPane.NO_OPTION || (Integer) value == JOptionPane.CLOSED_OPTION) {
                            fractal_functions[oldSelected].setSelected(true);
                            s.fns.function = oldSelected;
                            dispose();
                            return;
                        }

                        try {
                            double[][] temp_bodies = new double[inertia_panels.length][2];
                            double[][] temp_bodies_gravity = new double[inertia_panels.length][2];
                            double[] temp_timestep = new double[2];
                            double[] temp_inertia_contribution = new double[2];
                            double[] temp_init_inertia = new double[2];

                            for (int k = 0; k < inertia_panels.length; k++) {
                                temp_bodies[k][0] = Double.parseDouble(body_re[k].getText());
                                temp_bodies[k][1] = Double.parseDouble(body_im[k].getText());
                                temp_bodies_gravity[k][0] = Double.parseDouble(body_gravity_re[k].getText());
                                temp_bodies_gravity[k][1] = Double.parseDouble(body_gravity_im[k].getText());
                            }

                            temp_inertia_contribution[0] = Double.parseDouble(inertia_contribution_re.getText());
                            temp_inertia_contribution[1] = Double.parseDouble(inertia_contribution_im.getText());

                            temp_init_inertia[0] = Double.parseDouble(init_inertia_re.getText());
                            temp_init_inertia[1] = Double.parseDouble(init_inertia_im.getText());

                            temp_timestep[0] = Double.parseDouble(timestep_re.getText());
                            temp_timestep[1] = Double.parseDouble(timestep_im.getText());

                            double temp_exponent = Double.parseDouble(exponent.getText());

                            s.fns.igs.bodyLocation = temp_bodies;
                            s.fns.igs.bodyGravity = temp_bodies_gravity;
                            s.fns.igs.inertia_contribution = temp_inertia_contribution;
                            s.fns.igs.initial_inertia = temp_init_inertia;
                            s.fns.igs.pull_scaling_function = pull_choice.getSelectedIndex();
                            s.fns.igs.inertia_exponent = temp_exponent;
                            s.fns.igs.time_step = temp_timestep;
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        ptra.setInertiaGravitySierpinskiPost();
                        dispose();
                        ptra.setFunctionPost(oldSelected, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                    }
                });

        //Make this dialog display it.
        setContentPane(optionPane);

        pack();

        setResizable(false);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }

    
}
