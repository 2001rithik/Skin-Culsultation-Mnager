package GUI;
import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {
    public MyButton(String text,Color color) {
        this.setForeground(Color.BLACK);
        this.setBackground(color);
        this.setPreferredSize(new Dimension(100,40));
        this.setFocusable(false);
//        this.setBorder(BorderFactory.createEtchedBorder());
        this.setText(text);
    }
}
