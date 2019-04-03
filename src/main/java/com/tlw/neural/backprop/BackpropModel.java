package com.tlw.neural.backprop;

import com.tlw.neural.algorithm.NeuralNetwork;
import com.tlw.neural.ui.NeuralModel;

public class BackpropModel extends NeuralModel {

    private String hidden = "4,4";
    private double momentum = 0.7;
    private double learningRate = 0.1;
    private double minError = 0.01;
    private double size = 20.0;
    private boolean drawMode = false;

    private NeuralNetwork network;

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public double getMomentum() {
        return momentum;
    }

    public void setMomentum(double momentum) {
        this.momentum = momentum;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public double getMinError() {
        return minError;
    }

    public void setMinError(double minError) {
        this.minError = minError;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public boolean isDrawMode() {
        return drawMode;
    }

    public void setDrawMode(boolean drawMode) {
        this.drawMode = drawMode;
    }

    public NeuralNetwork getNetwork() {
        return network;
    }

    public void setNetwork(NeuralNetwork network) {
        this.network = network;
    }
}
