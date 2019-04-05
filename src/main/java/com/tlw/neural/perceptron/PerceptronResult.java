package com.tlw.neural.perceptron;

public class PerceptronResult {

    protected double trainingPercent;
    protected double testingPercent;

    public PerceptronResult(double trainingPercent, double testingPercent) {
        this.trainingPercent = trainingPercent;
        this.testingPercent = testingPercent;
    }

    public double getTrainingPercent() {
        return trainingPercent;
    }

    public void setTrainingPercent(double trainingPercent) {
        this.trainingPercent = trainingPercent;
    }

    public double getTestingPercent() {
        return testingPercent;
    }

    public void setTestingPercent(double testingPercent) {
        this.testingPercent = testingPercent;
    }
}
