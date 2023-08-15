package fractalzoomer.gui;

import javax.swing.*;

public class MyMenu extends JMenu {
    public MyMenu(String text) {
        super(text);
    }

    @Override
    public Icon getDisabledIcon() {
        return new MyIcon(super.getDisabledIcon());
    }

    @Override
    public Icon getDisabledSelectedIcon() {
        return new MyIcon(super.getDisabledSelectedIcon());
    }
}
