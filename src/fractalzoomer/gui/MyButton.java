package fractalzoomer.gui;

import javax.swing.*;

public class MyButton extends JButton {

    public MyButton() {
        super();
    }

    public MyButton(String text) {
        super(text);
    }

    public MyButton(String text, Icon icon) {
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
