
package fractalzoomer.gui;

import fractalzoomer.main.MinimalRendererWindow;
import fractalzoomer.main.MainWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class ThreadsDialog extends JDialog {

    private JFrame ptra;
    private JOptionPane optionPane;

    public ThreadsDialog(JFrame ptr, int n, int m, int grouping) {

        super(ptr);

        ptra = ptr;

        setTitle("Threads Number");
        setModal(true);
        
        if (ptra instanceof MainWindow) {
            setIconImage(MainWindow.getIcon("mandel2.png").getImage());
        } else if (ptra instanceof MinimalRendererWindow) {
            setIconImage(MainWindow.getIcon("mandelMinimalRenderer.png").getImage());
        }

        JComboBox<String> thread_grouping = new JComboBox<>(new String[]{"Grid Split (nxn)", "Horizontal Split (n)", "Vertical Split (n)", "Auto Grid Split (n) Horizontal >= Vertical", "Auto Grid Split (n) Vertical >= Horizontal", "Grid Split(mxn)"});
        thread_grouping.setFocusable(false);
        thread_grouping.setToolTipText("Sets the thread grouping method.");

        thread_grouping.setSelectedIndex(grouping);

        JTextField field = new JTextField(20);
        field.addAncestorListener(new RequestFocusListener());
        field.setText("" + (grouping == 3 || grouping == 4 ? m * n : n));

        JTextField field2 = new JTextField(20);
        field2.setText("" + m);

        int threadNumber = 0;

        switch (thread_grouping.getSelectedIndex()) {
            case 0:
                threadNumber = n * n;
                break;
            case 1:
            case 2:
                threadNumber = n;
                break;
            case 3:
            case 4:
            case 5:
                threadNumber = m * n;
                break;
        }

        JLabel currentThreads = new JLabel("Current Thread Number: " + threadNumber);


        field.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                handleChange(field, field2, thread_grouping, currentThreads);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                handleChange(field, field2, thread_grouping, currentThreads);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                handleChange(field, field2, thread_grouping, currentThreads);
            }
        });

        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT));

        p.add(new JLabel("n = "));
        p.add(field);

        JLabel l2 = new JLabel("Insert the number of m.");

        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));

        p2.add(new JLabel("m = "));
        p2.add(field2);

        l2.setVisible(thread_grouping.getSelectedIndex() == 5);
        p2.setVisible(thread_grouping.getSelectedIndex() == 5);

        thread_grouping.addActionListener(e -> {
            handleChange(field, field2, thread_grouping, currentThreads);
            l2.setVisible(thread_grouping.getSelectedIndex() == 5);
            p2.setVisible(thread_grouping.getSelectedIndex() == 5);
            pack();
        });


        Object[] message3 = {
            " ",
                "Logical Processors: " + Runtime.getRuntime().availableProcessors(),
                currentThreads,
                " ",
                "Thread Split:",
                thread_grouping,
                " ",
                l2,
                p2,
            "Insert the number of n.",
                p,
            " ",};

        optionPane = new JOptionPane(message3, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            dispose();
                            return;
                        }

                        try {
                            int temp = Integer.parseInt(field.getText());
                            int tempm = Integer.parseInt(field2.getText());

                            int tempgrouping = thread_grouping.getSelectedIndex();

                            if(tempgrouping == 0) {
                                if (temp < 1) {
                                    JOptionPane.showMessageDialog(ptra, "The first dimension number of the 2D threads\ngrid must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else if (temp > 100) {
                                    JOptionPane.showMessageDialog(ptra, "The first dimension number of the 2D threads\ngrid must be lower than 101.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }
                            if(tempgrouping == 5) {


                                if (tempm < 1) {
                                    JOptionPane.showMessageDialog(ptra, "The value m must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else if (tempm > 100) {
                                    JOptionPane.showMessageDialog(ptra, "The value m must be lower than 101.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                if (temp < 1) {
                                    JOptionPane.showMessageDialog(ptra, "The value n must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else if (temp > 100) {
                                    JOptionPane.showMessageDialog(ptra, "The value n must be lower than 101.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }
                            else {
                                if (temp < 1) {
                                    JOptionPane.showMessageDialog(ptra, "The thread number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else if (temp > 10000) {
                                    JOptionPane.showMessageDialog(ptra, "The thread number must be lower than 10001.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }

                            if (ptra instanceof MainWindow) {
                                ((MainWindow) ptra).setThreadsNumberPost(tempgrouping, temp, tempm);
                            } else if (ptra instanceof MinimalRendererWindow) {
                                ((MinimalRendererWindow) ptra).setThreadsNumberPost(tempgrouping, temp, tempm);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                    }
                });

        //Make this dialog display it.
        setContentPane(optionPane);

        pack();

        setResizable(false);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }

    private void handleChange(JTextField field, JTextField field2, JComboBox<String> thread_grouping, JLabel currentThreads) {
        try {
            int tempn = Integer.parseInt(field.getText());
            int tempm = Integer.parseInt(field2.getText());

            int threadNumberTemp = 0;

            switch (thread_grouping.getSelectedIndex()) {
                case 0:
                    threadNumberTemp = tempn * tempn;
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                    threadNumberTemp = tempn;
                    break;

                case 5:
                    threadNumberTemp = tempn * tempm;
                    break;
            }

            if(threadNumberTemp > 0) {
                currentThreads.setText("Current Thread Number: " + threadNumberTemp);
            }
            else {
                currentThreads.setText("Current Thread Number: ");
            }
        }
        catch (Exception ex) {
            currentThreads.setText("Current Thread Number: ");
        }
    }

}
