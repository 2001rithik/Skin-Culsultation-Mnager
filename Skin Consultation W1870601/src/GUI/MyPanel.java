package GUI;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    private Color color1;
    private Color color2;

    //passing colors as constructor parameters cause some panels use different colors
    public MyPanel( Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
    }

    /*
        referred stackoverflow about how to add gradient colors
    https://stackoverflow.com/questions/14364291/jpanel-gradient-background
    */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth(), h = getHeight();

        GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
