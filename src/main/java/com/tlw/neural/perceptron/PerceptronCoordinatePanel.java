package com.tlw.neural.perceptron;

import com.tlw.neural.ui.CoordinatePanel;

import java.awt.*;
import java.awt.geom.Line2D;

import java.util.List;

public class PerceptronCoordinatePanel extends CoordinatePanel {

    private PerceptronModel perceptronModel;

    public void paint(Graphics g) {
        super.paint(g);
        int magnification = perceptronModel.getMagnification();
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        // Draw line of perceptron
        List<Double[]> weights = perceptronModel.getWeights();
        if (weights.size() != 0 && perceptronModel.getInput().get(0).length == 4) {
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
