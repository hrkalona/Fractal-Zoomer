package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class LAFManager {

    public static RoundedPanel createRoundedPanel() {
        if(MainWindow.useCustomLaf) {
            return new RoundedPanel(false, true, false, 15);
        }
        else {
            return new RoundedPanel(true, true, true, 15);
        }
    }

    public static Border createTitledBorder(String name) {
        if(MainWindow.useCustomLaf) {
            return BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), name, TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION);
        }
        else {
            return BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), name, TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION);
        }
    }

    public static Border createTitledBorderCenter(String name) {
        if(MainWindow.useCustomLaf) {
            return BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), name, TitledBorder.CENTER, TitledBorder.CENTER);
        }
        else {
            return BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), name, TitledBorder.CENTER, TitledBorder.CENTER);
        }
    }

    public static Border createUnTitledBorder() {
        if(MainWindow.useCustomLaf) {
            return BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        }
        else {
            return BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder());
        }
    }

    public static Border createSimpleBorder() {
        if(MainWindow.useCustomLaf) {
            return BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        }
        else {
            return BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        }
    }

    public static JPanel getJListPanel() {
        if(MainWindow.useCustomLaf) {
            return new RoundedPanel(false, true, false, 5, new BorderLayout());
        }
        else {
            return new JPanel(new BorderLayout());
        }
    }

}
