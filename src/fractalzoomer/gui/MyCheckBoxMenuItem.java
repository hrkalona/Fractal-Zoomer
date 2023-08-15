package fractalzoomer.gui;

import javax.swing.*;

public class MyCheckBoxMenuItem extends JCheckBoxMenuItem {

    public MyCheckBoxMenuItem(String text) {
        super(text);
    }

    public MyCheckBoxMenuItem(String text, Icon icon) {
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
