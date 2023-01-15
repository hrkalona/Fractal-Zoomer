package fractalzoomer.gui;

import javax.swing.*;

public class MyJSpinner extends JSpinner {

    public MyJSpinner(SpinnerModel var1) {
        super(var1);
        this.setEditor(new JSpinner.NumberEditor(this,"#.##################;-#.##################"));
    }

    public MyJSpinner(int length, SpinnerModel var1) {
        super(var1);
        this.setEditor(new JSpinner.NumberEditor(this,"#.##################;-#.##################"));
        ((JSpinner.NumberEditor)this.getEditor()).getTextField().setColumns(length);
    }

    public String getText() {
       return  ((JSpinner.DefaultEditor) this.getEditor()).getTextField().getText();
    }
}
