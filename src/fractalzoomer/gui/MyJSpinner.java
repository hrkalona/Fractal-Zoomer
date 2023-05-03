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

    public MyJSpinner(int length, SpinnerModel var1, int digitslen) {
        super(var1);
        String pos = "#.";
        for(int i = 0 ; i < digitslen; i++) {
            pos += "#";
        }

        String neg = "-#.";
        for(int i = 0 ; i < digitslen; i++) {
            neg += "#";
        }
        this.setEditor(new JSpinner.NumberEditor(this,pos + ";" + neg));
        ((JSpinner.NumberEditor)this.getEditor()).getTextField().setColumns(length);
    }

    public String getText() {
       return  ((JSpinner.DefaultEditor) this.getEditor()).getTextField().getText();
    }
}
