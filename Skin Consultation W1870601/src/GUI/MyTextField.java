package GUI;

import javax.swing.*;
import java.awt.*;

public class MyTextField extends JTextField {
    public MyTextField() {
        this.setPreferredSize(new Dimension(100,20));
        this.setFont(new Font("SansSerif", Font.PLAIN,15));
    }
}
