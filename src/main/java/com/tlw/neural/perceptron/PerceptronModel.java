package com.tlw.neural.perceptron;

import com.tlw.neural.ui.NeuralModel;

import java.util.ArrayList;

public class PerceptronModel extends NeuralModel {

    private ArrayList<Double[]> weights = new ArrayList<>();
    private double rate = 0.1;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public ArrayList<Double[]> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<Double[]> weights) {
        this.weights = weights;
    }
}
