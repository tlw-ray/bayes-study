package com.tlw.neural.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;

public class CoordinatePanel extends JPanel {

    protected Color[] colorArray = {Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW, Color.CYAN, Color.PINK};
    protected DecimalFormat df = new DecimalFormat("####0.00");
    protected int magnification = 50;
    protected Point mouse;

    public CoordinatePanel(){
        Dimension size = new Dimension(500, 500);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        setOpaque(true);
        setBackground(Color.WHITE);
    }

    protected void drawScale(Graphics2D g2, Double i) {
        Double[] top, btn;
        Double scaleLength = (i % (5.0 * magnification / 5) == 0) ? 2.0 * magnification / 20 : 1.0 * magnification / 20;
        top = convertCoordinate(new Double[]{(i - 250) / magnification, scaleLength / magnification});
        btn = convertCoordinate(new Double[]{(i - 250) / magnification, -scaleLength / magnification});
        g2.draw(new Line2D.Double(top[0], top[1], btn[0], btn[1]));
        top = convertCoordinate(new Double[]{-scaleLength / magnification, (250 - i) / magnification});
        btn = convertCoordinate(new Double[]{scaleLength / magnification, (250 - i) / magnification});
        g2.draw(new Line2D.Double(top[0], top[1], btn[0], btn[1]));
    }

    protected Double[] convertCoordinate(Double[] oldPoint) {
        Double[] newPoint = new Double[2];
        newPoint[0] = (oldPoint[0] * magnification) + 250;
        newPoint[1] = 250 - (oldPoint[1] * magnification);
        return newPoint;
    }
}
