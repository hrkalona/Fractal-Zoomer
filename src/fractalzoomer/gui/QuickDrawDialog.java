/*
 * Copyright (C) 2020 hrkalona2
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

import fractalzoomer.core.ThreadDraw;
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
public class QuickDrawDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public QuickDrawDialog(MainWindow ptr) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Quick Draw");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        final JSlider tiles_slid = new JSlider(JSlider.HORIZONTAL, Constants.MIN_QUICK_DRAW_TILES, Constants.MAX_QUICK_DRAW_TILES, ThreadDraw.TILE_SIZE);

        tiles_slid.setPreferredSize(new Dimension(350, 55));

        tiles_slid.setToolTipText("Sets the size of the tiles.");

        tiles_slid.setPaintLabels(true);
        tiles_slid.setFocusable(false);
        tiles_slid.setPaintTicks(true);
        tiles_slid.setMajorTickSpacing(1);

        JTextField delay = new JTextField();
        delay.addAncestorListener(new RequestFocusListener());
        delay.setText("" + ThreadDraw.QUICK_DRAW_DELAY);

        JCheckBox successiveRefinement = new JCheckBox("Successive Refinement");
        successiveRefinement.setSelected(ThreadDraw.SUCCESSIVE_REFINEMENT);
        successiveRefinement.setFocusable(false);
        successiveRefinement.setToolTipText("Starts the quickdraw by largest available tile and reduces the tile size gradually.");


        JCheckBox zoomToCurrentCenter = new JCheckBox("Zoom to center");
        zoomToCurrentCenter.setSelected(MainWindow.QUICK_DRAW_ZOOM_TO_CURRENT_CENTER);
        zoomToCurrentCenter.setFocusable(false);
        zoomToCurrentCenter.setToolTipText("Locks the zooming to the current center, when zooming in or out with the mouse wheel.");

        JCheckBox drawPreview = new JCheckBox("Always Use Quick Draw");
        drawPreview.setSelected(ThreadDraw.DRAW_IMAGE_PREVIEW);
        drawPreview.setFocusable(false);
        drawPreview.setToolTipText("Create a preview image in low resolution using quick draw on every render (First Pass/Second Pass).");


        Object[] message3 = {
            " ",
            "Set the quick draw tile size.",
                "Tile size:",
            tiles_slid,
                " ",
                "Set the delay until the complete image calculation.",
                "Delay (milliseconds):",
                delay,
                " ",
                successiveRefinement,
                " ",
                drawPreview,
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
                            ThreadDraw.TILE_SIZE = tiles_slid.getValue();

                            int temp = Integer.parseInt(delay.getText());

                            if(temp <= 0) {
                                JOptionPane.showMessageDialog(ptra, "Delay must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            MainWindow.QUICK_DRAW_ZOOM_TO_CURRENT_CENTER = zoomToCurrentCenter.isSelected();
                            ThreadDraw.DRAW_IMAGE_PREVIEW = drawPreview.isSelected();

                            ThreadDraw.QUICK_DRAW_DELAY = temp;
                            ThreadDraw.SUCCESSIVE_REFINEMENT = successiveRefinement.isSelected();
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
