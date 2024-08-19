
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public class InflectionsPlaneDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    private final JScrollPane scrollPane;

    public InflectionsPlaneDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] planes) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Multiple Inflections");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextField infPower = new JTextField();
        infPower.addAncestorListener(new RequestFocusListener());
        infPower.setText("" + s.fns.inflectionsPower);

        JPanel p2 = new JPanel();
        p2.setLayout(new VerticalLayout());

        ArrayList<JPanel> inflectionsPanel = new ArrayList<>();
        ArrayList<JTextField> inflections_re = new ArrayList<>();
        ArrayList<JTextField> inflections_im = new ArrayList<>();
        ArrayList<JButton> deleteButtons = new ArrayList<>();

        for (int k = 0; k < s.fns.inflections_re.size(); k++) {
            JPanel p = new JPanel();
            p.setLayout(new FlowLayout());
            inflectionsPanel.add(p);

            JTextField re = new JTextField(18);
            re.setText("" + s.fns.inflections_re.get(k));

            JTextField im = new JTextField(18);
            im.setText("" + s.fns.inflections_im.get(k));

            inflections_re.add(re);
            inflections_im.add(im);

            p.add(new JLabel("Point "));
            p.add(new JLabel(" Re: "));
            p.add(re);
            p.add(new JLabel(" Im: "));
            p.add(im);

            JButton del = new MyButton();
            del.setIcon(MainWindow.getIcon("delete_small2.png"));
            deleteButtons.add(del);
            del.setPreferredSize(new Dimension(24, 24));
            del.setToolTipText("Deletes this inflection point.");

            p.add(del);

            del.addActionListener(new ActionListener() {
                JPanel pid = p;
                @Override
                public void actionPerformed(ActionEvent e) {

                    int index = inflectionsPanel.indexOf(pid);

                    deleteButtons.remove(index);
                    inflections_re.remove(index);
                    inflections_im.remove(index);
                    inflectionsPanel.remove(index);
                    p2.remove(index);
                    pack();
                }
            });
        }



        for(int i = 0; i < inflectionsPanel.size(); i++) {
            p2.add(inflectionsPanel.get(i));
        }


        JButton addInflection = new MyButton();
        addInflection.setToolTipText("Adds a new inflection point.");
        addInflection.setFocusable(false);
        addInflection.setIcon(MainWindow.getIcon("add_small.png"));
        addInflection.setPreferredSize(new Dimension(24, 24));

        addInflection.addActionListener(e -> {
            JPanel p = new JPanel();
            p.setLayout(new FlowLayout());
            inflectionsPanel.add(p);

            JTextField re = new JTextField(20);
            re.setText("" + 0);

            JTextField im = new JTextField(20);
            im.setText("" + 0);

            inflections_re.add(re);
            inflections_im.add(im);

            p.add(new JLabel("Point "));
            p.add(new JLabel(" Re: "));
            p.add(re);
            p.add(new JLabel(" Im: "));
            p.add(im);

            p2.add(p);

            JButton del = new MyButton();
            del.setIcon(MainWindow.getIcon("delete_small2.png"));
            del.setPreferredSize(new Dimension(24, 24));
            del.setToolTipText("Deletes this inflection point.");
            deleteButtons.add(del);

            p.add(del);

            del.addActionListener(new ActionListener() {
                JPanel pid = p;
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = inflectionsPanel.indexOf(pid);

                    deleteButtons.remove(index);
                    inflections_re.remove(index);
                    inflections_im.remove(index);
                    inflectionsPanel.remove(index);
                    p2.remove(index);
                    pack();
                }
            });

            pack();
        });

        JPanel top_panel = new JPanel();
        top_panel.setLayout(new FlowLayout());
        top_panel.add(addInflection);

        JButton info_user = new MyButton("Help");
        info_user.setToolTipText("Shows info on Perturbation Theory.");
        info_user.setFocusable(false);
        info_user.setIcon(MainWindow.getIcon("help2.png"));
        info_user.setPreferredSize(new Dimension(105, 23));

        info_user.addActionListener(e -> {
            JEditorPane textArea = new JEditorPane();

            textArea.setEditable(false);
            textArea.setContentType("text/html");
            textArea.setPreferredSize(new Dimension(600, 120));

            JScrollPane scroll_pane_2 = new JScrollPane(textArea);
            scroll_pane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            String overview = "<font size='5' face='arial' color='blue'><center><b><u>Multiple Inflections</u></b></center></font><br><br>"
                    + "<font  face='arial'>"
                    + "<li>You can use Alt+Left Click add an inflection point on the image mode.<br>"
                    + "<li>You can use Alt+Right Click to remove the last inflection point on the image mode.<br>"
                    + "</font>"
                    + "</font>";
            textArea.setText(overview);

            Object[] message = {
                    scroll_pane_2,};

            textArea.setCaretPosition(0);

            JOptionPane.showMessageDialog(this, message, "Help", JOptionPane.QUESTION_MESSAGE);
        });

        JPanel info_panel = new JPanel();
        info_panel.setLayout(new FlowLayout());
        info_panel.add(info_user);

        scrollPane = new JScrollPane(p2);
        scrollPane.setPreferredSize(new Dimension(650, 200));

        Object[] poly_poly = {
                info_panel,
                " ",
                "Insert the inflection power.",
                "Inflection Power:",
                infPower,
            " ",
            "Insert the inflection points.",
                top_panel,
            " ",
            scrollPane,
            " ",};

        optionPane = new JOptionPane(poly_poly, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            planes[oldSelected].setSelected(true);
                            s.fns.plane_type = oldSelected;
                            dispose();
                            return;
                        }

                        try {
                            double temp = Double.parseDouble(infPower.getText());

                            if (temp <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The inflection power must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            ArrayList<Double> inflec_re = new ArrayList<>();
                            ArrayList<Double> inflec_im = new ArrayList<>();
                            for(int i = 0; i < inflections_re.size(); i++) {
                                inflec_re.add(Double.parseDouble(inflections_re.get(i).getText()));
                                inflec_im.add(Double.parseDouble(inflections_im.get(i).getText()));
                            }

                            s.fns.inflectionsPower = temp;
                            s.fns.inflections_re = inflec_re;
                            s.fns.inflections_im = inflec_im;

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.defaultFractalSettings(true);

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
