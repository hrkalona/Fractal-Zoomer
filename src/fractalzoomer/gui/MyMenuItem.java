package fractalzoomer.gui;

import javax.swing.*;

public class MyMenuItem extends JMenuItem {

    public MyMenuItem(String text) {
        super(text);
    }

    public MyMenuItem(String text, Icon icon) {
        super(text, icon);
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
