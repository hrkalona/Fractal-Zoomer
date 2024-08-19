
package fractalzoomer.gui;

import fractalzoomer.core.TaskRender;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class QuickRenderDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public QuickRenderDialog(MainWindow ptr) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Quick Render");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        final JSlider tiles_slid = new JSlider(JSlider.HORIZONTAL, Constants.MIN_QUICK_RENDER_TILES, Constants.MAX_QUICK_RENDER_TILES, TaskRender.TILE_SIZE);

        tiles_slid.setPreferredSize(new Dimension(350, 55));

        tiles_slid.setToolTipText("Sets the size of the tiles.");

        tiles_slid.setPaintLabels(true);
        tiles_slid.setFocusable(false);
        tiles_slid.setPaintTicks(true);
        tiles_slid.setMajorTickSpacing(1);

        JTextField delay = new JTextField();
        delay.addAncestorListener(new RequestFocusListener());
        delay.setText("" + TaskRender.QUICK_RENDER_DELAY);

        JCheckBox successiveRefinement = new JCheckBox("Successive Refinement");
        successiveRefinement.setSelected(TaskRender.QUICKRENDER_SUCCESSIVE_REFINEMENT);
        successiveRefinement.setFocusable(false);
        successiveRefinement.setToolTipText("Starts the quick-render by largest available tile and reduces the tile size gradually.");


        JCheckBox zoomToCurrentCenter = new JCheckBox("Zoom to center (During Scrolling)");
        zoomToCurrentCenter.setSelected(MainWindow.QUICK_RENDER_ZOOM_TO_CURRENT_CENTER);
        zoomToCurrentCenter.setFocusable(false);
        zoomToCurrentCenter.setToolTipText("Locks the zooming to the current center, when zooming in or out with the mouse wheel.");

        JCheckBox renderPreview = new JCheckBox("Always Use Quick Render");
        renderPreview.setSelected(TaskRender.RENDER_IMAGE_PREVIEW);
        renderPreview.setFocusable(false);
        renderPreview.setToolTipText("Create a preview image in low resolution using quick-render on every render (First Pass/Second Pass).");

        JCheckBox useQuickRenderOnGreedySucRef = new JCheckBox("Use Non-Blocking Quick Render on Greedy Successive Refinement");
        useQuickRenderOnGreedySucRef.setSelected(TaskRender.USE_QUICKRENDER_ON_GREEDY_SUCCESSIVE_REFINEMENT);
        useQuickRenderOnGreedySucRef.setFocusable(false);
        useQuickRenderOnGreedySucRef.setToolTipText("Enables the use of quick-render on the greedy successive refinement algorithms.");



        Object[] message3 = {
            " ",
            "Set the quick render tile size.",
                "Tile size:",
            tiles_slid,
                " ",
                "Set the delay until the complete image calculation.",
                "Delay (milliseconds):",
                delay,
                " ",
                successiveRefinement,
                " ",
                useQuickRenderOnGreedySucRef,
                " ",
                renderPreview,
                " ",
                zoomToCurrentCenter,
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
                            TaskRender.TILE_SIZE = tiles_slid.getValue();

                            int temp = Integer.parseInt(delay.getText());

                            if(temp <= 0) {
                                JOptionPane.showMessageDialog(ptra, "Delay must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            MainWindow.QUICK_RENDER_ZOOM_TO_CURRENT_CENTER = zoomToCurrentCenter.isSelected();
                            TaskRender.RENDER_IMAGE_PREVIEW = renderPreview.isSelected();

                            TaskRender.QUICK_RENDER_DELAY = temp;
                            TaskRender.QUICKRENDER_SUCCESSIVE_REFINEMENT = successiveRefinement.isSelected();
                            TaskRender.USE_QUICKRENDER_ON_GREEDY_SUCCESSIVE_REFINEMENT = useQuickRenderOnGreedySucRef.isSelected();
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
    
}
