package com.tlw.neural.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.util.List;

public class CoordinatePanel extends JPanel implements MouseMotionListener {

    protected NeuralModel neuralModel;
    protected Color[] colorArray = {Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW, Color.CYAN, Color.PINK};
    protected DecimalFormat df = new DecimalFormat("####0.00");
    protected Point mouse;

    public CoordinatePanel(){
        Dimension size = new Dimension(501, 501);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        setOpaque(true);
        setBackground(Color.WHITE);

        addMouseMotionListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Draw axis
        g.drawLine(250, 0, 250, 500);
        g.drawLine(0, 250, 500, 250);
        Graphics2D g2 = (Graphics2D) g;
        // Draw scale
        for (Double i = 250.0; i >= 0; i -= 5.0 * neuralModel.getMagnification() / 10) {
            drawScale(g2, i);
        }
        for (Double i = 250.0; i <= 500; i += 5.0 * neuralModel.getMagnification() / 10) {
            drawScale(g2, i);
        }
        // Draw mouse position
        g2.setStroke(new BasicStroke(3));
        if (mouse != null) {
            Double mouse_x = (mouse.getX() - 250) / neuralModel.getMagnification();
            Double mouse_y = (250 - mouse.getY()) / neuralModel.getMagnification();
            g2.drawString("(" + df.format(mouse_x) + ", " + df.format(mouse_y) + ")", 420, 20);
        }
        // Draw point of file
        List<Double[]> input = neuralModel.getInput();
        if (input.size() > 0 && input.get(0).length == 4) {
            for (Double[] x : input) {
                Double[] point = convertCoordinate(new Double[]{x[1], x[2]});
                g2.setColor(colorArray[(int) Math.round(x[x.length - 1])]);
                g2.draw(new Line2D.Double(point[0], point[1], point[0], point[1]));
            }
        }    }

    protected void drawScale(Graphics2D g2, Double i) {
        Double[] top, btn;
        int magnification = neuralModel.getMagnification();
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
        int magnification = neuralModel.getMagnification();
        newPoint[0] = (oldPoint[0] * magnification) + 250;
        newPoint[1] = 250 - (oldPoint[1] * magnification);
        return newPoint;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse = e.getPoint();
        repaint();
    }

    public NeuralModel getNeuralModel() {
        return neuralModel;
    }

    public void setNeuralModel(NeuralModel neuralModel) {
        this.neuralModel = neuralModel;
    }
}
