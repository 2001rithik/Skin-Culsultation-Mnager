package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MyJLabel extends JLabel {
    public MyJLabel(Font font, Border border,String text) {
        this.setText(text);
        this.setFont(font);
        this.setBorder(border);
    }

    public MyJLabel(Font font, String text) {
        this.setText(text);
        this.setFont(font);
    }
}
