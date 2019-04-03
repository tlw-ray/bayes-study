package com.tlw.neural.perceptron;

import com.tlw.neural.ui.CoordinatePanel;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import java.util.List;

public class PerceptronCoordinatePanel extends CoordinatePanel {

    protected PerceptronModel perceptronModel;

    public void paint(Graphics g) {
        g.drawLine(250, 0, 250, 500);
        g.drawLine(0, 250, 500, 250);
        Graphics2D g2 = (Graphics2D) g;
        // Draw scale
        for (Double i = 250.0; i >= 0; i -= 5.0 * magnification / 10) {
            drawScale(g2, i);
        }
        for (Double i = 250.0; i <= 500; i += 5.0 * magnification / 10) {
            drawScale(g2, i);
        }
        g2.setStroke(new BasicStroke(3));
        // Draw mouse position
        if (mouse != null) {
            Double mouse_x = (mouse.getX() - 250) / magnification;
            Double mouse_y = (250 - mouse.getY()) / magnification;
            g2.drawString("(" + df.format(mouse_x) + ", " + df.format(mouse_y) + ")", 420, 20);
        }
        // Draw point of file
        List<Double[]> input = perceptronModel.getInput();
        if (input.size() > 0 && input.get(0).length == 4) {
            for (Double[] x : input) {
                Double[] point = convertCoordinate(new Double[]{x[1], x[2]});
                g2.setColor(colorArray[(int) Math.round(x[x.length - 1])]);
                g2.draw(new Line2D.Double(point[0], point[1], point[0], point[1]));
            }
        }
        g2.setStroke(new BasicStroke(2));
        // Draw line of perceptron
        ArrayList<Double[]> weights = perceptronModel.getWeights();
        if (weights.size() != 0 && input.get(0).length == 4) {
            g2.setColor(Color.MAGENTA);
            for (Double[] weight : weights) {
                Double[] lineStart, lineEnd;
                if (weight[2] != 0) {
                    lineStart = convertCoordinate(
                            new Double[]{-250.0 / magnification,
                                    (weight[0] + 250.0 / magnification * weight[1]) / weight[2]});
                    lineEnd = convertCoordinate(
                            new Double[]{250.0 / magnification,
                                    (weight[0] - 250.0 / magnification * weight[1]) / weight[2]});
                } else {
                    lineStart = convertCoordinate(
                            new Double[]{weight[0] / weight[1], 250.0 / magnification});
                    lineEnd = convertCoordinate(
                            new Double[]{weight[0] / weight[1], -250.0 / magnification});
                }
                g2.draw(new Line2D.Double(lineStart[0], lineStart[1], lineEnd[0], lineEnd[1]));
            }
        }
    }

    public PerceptronModel getPerceptronModel() {
        return perceptronModel;
    }

    public void setPerceptronModel(PerceptronModel perceptronModel) {
        this.perceptronModel = perceptronModel;
    }
}
