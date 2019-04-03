package com.tlw.neural.ui;

import java.util.ArrayList;
import java.util.List;

public class NeuralModel {

    private List<Double[]> input = new ArrayList();
    private List<Double[]> trainData = new ArrayList();
    private List<Double[]> testData = new ArrayList();
    private List<Double> outputKinds = new ArrayList();

    private int maxTimes = 1000;
    private int magnification = 50;
    private double threshold = 0;
    private double minRange = -0.5;
    private double maxRange = 0.5;

    public int getMaxTimes() {
        return maxTimes;
    }

    public void setMaxTimes(int maxTimes) {
        this.maxTimes = maxTimes;
    }

    public int getMagnification() {
        return magnification;
    }

    public void setMagnification(int magnification) {
        this.magnification = magnification;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getMinRange() {
        return minRange;
    }

    public void setMinRange(double minRange) {
        this.minRange = minRange;
    }

    public double getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(double maxRange) {
        this.maxRange = maxRange;
    }

    public List<Double[]> getInput() {
        return input;
    }

    public void setInput(List<Double[]> input) {
        this.input = input;
    }

    public List<Double[]> getTrainData() {
        return trainData;
    }

    public void setTrainData(List<Double[]> trainData) {
        this.trainData = trainData;
    }

    public List<Double[]> getTestData() {
        return testData;
    }

    public void setTestData(List<Double[]> testData) {
        this.testData = testData;
    }

    public List<Double> getOutputKinds() {
        return outputKinds;
    }

    public void setOutputKinds(List<Double> outputKinds) {
        this.outputKinds = outputKinds;
    }
}
