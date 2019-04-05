package com.tlw.neural.backprop;

import com.tlw.neural.algorithm.NeuralNetwork;
import com.tlw.neural.ui.CoordinatePanel;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class BackpropCoordinatePanel extends CoordinatePanel {

    protected BackpropModel backpropModel;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int magnification = backpropModel.getMagnification();
        Graphics2D g2 = (Graphics2D) g;
        // Draw output kind area
        List<Double[]> input = backpropModel.getInput();
        int maxTimes = backpropModel.getMaxTimes();
        double minError = backpropModel.getMinError();
        double size = backpropModel.getMySize();
        List<Double> outputKinds = backpropModel.getOutputKinds();
        NeuralNetwork network = backpropModel.getNetwork();
        boolean drawMode  = backpropModel.isDrawMode();
        if (drawMode && size > 0 && input.size() > 0 && input.get(0).length == 4) {
            ArrayList<Double[]> drawInputs = new ArrayList<>();
            for (Double x = -250.0; x <= 250; x += size) {
                for (Double y = -250.0; y <= 250; y += size) {
                    drawInputs.add(new Double[]{-1.0, x / magnification, y / magnification});
                }
            }
            int id[] = network.getOutputKind(drawInputs, maxTimes, minError);
            int i = 0;
            for (Double x = -250.0; x <= 250; x += size) {
                for (Double y = -250.0; y <= 250; y += size) {
                    g2.setColor(colorArray[colorArray.length - id[i] - 1]);
                    Double[] point = convertCoordinate(new Double[]{x / magnification, y / magnification});
                    Rectangle2D rect = new Rectangle2D.Double(point[0], point[1], size, size);
                    g2.fill(rect);
                    i++;
                }
            }
        }
        g.setColor(Color.black);
        g.drawLine(250, 0, 250, 500);
        g.drawLine(0, 250, 500, 250);
        g2.setColor(Color.black);
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
        if (input.size() > 0 && input.get(0).length == 4) {
            for (Double[] x : input) {
                Double[] point = convertCoordinate(new Double[]{x[1], x[2]});
                for (int i = 0; i < outputKinds.size(); i++) {
                    Double outputKind = outputKinds.get(i);
                    if (x[x.length - 1].equals(outputKind)) {
                        g2.setColor(colorArray[i]);
                        break;
                    }
                }
                g2.draw(new Line2D.Double(point[0], point[1], point[0], point[1]));
            }
        }
        // TODO: Draw line of decision boundary
    }
}
