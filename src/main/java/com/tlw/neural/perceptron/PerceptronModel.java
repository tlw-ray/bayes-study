package com.tlw.neural.perceptron;

import com.tlw.neural.ui.NeuralModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerceptronModel extends NeuralModel {

    protected DecimalFormat df = new DecimalFormat("####0.00");

    protected List<Double[]> weights = new ArrayList<>();
    protected double rate = 0.1;

    public PerceptronResult startTrain() {
//        if (outputKinds.size() > 2)
//            outputKinds.forEach(this::trainPerceptron);
//        else
        return trainPerceptron(outputKinds.get(0));
    }

    public PerceptronResult trainPerceptron(Double dy) {
        if (trainData.size() == 0) return null;
        Double[] w = new Double[trainData.get(0).length - 1];
        w[0] = threshold;
        for (int i = 1; i < trainData.get(0).length - 1; i++)
            w[i] = getRandomNumber();
        int wi = outputKinds.indexOf(dy);
        if (wi == 0) weights.clear();
        weights.add(w);
        int trainingTimes = 0, correct = 0;
        while (trainingTimes < maxTimes) {
            correct = 0;
            for (Double[] x : trainData) {
                Double sum = 0.0;
                for (int i = 0; i < weights.get(wi).length; i++) {
                    sum += weights.get(wi)[i] * x[i];
                }
                Double fx = Math.signum(sum);
                Double y = (x[x.length - 1].equals(dy)) ? 1.0 : -1.0;
                Double e = y - fx;
                if (e == 0) ++correct;
                for (int i = 0; i < weights.get(wi).length; i++) {
                    weights.get(wi)[i] = weights.get(wi)[i] + rate * e * x[i];
                }
            }
            if (correct == trainData.size()) break;
            ++trainingTimes;
        }
        StringBuilder weightOutput = new StringBuilder("(");
        weightOutput.append(df.format(weights.get(wi)[1]));
        for (int i = 2; i < weights.get(wi).length; i++) {
            weightOutput.append(", ").append(df.format(weights.get(wi)[i]));
        }
        weightOutput.append(")");

        double trainingPercent = (double) correct / trainData.size() * 100;
        double testingPercent = testPerceptron(dy);

        return new PerceptronResult(trainingPercent, testingPercent);
    }

    private double testPerceptron(Double dy) {
        if (testData.size() == 0) return 0;
        int wi = outputKinds.indexOf(dy);
        int correct = 0;
        for (Double[] x : testData) {
            Double sum = 0.0;
            for (int i = 0; i < weights.get(wi).length; i++) {
                sum += weights.get(wi)[i] * x[i];
            }
            Double fx = Math.signum(sum);
            Double y = (x[x.length - 1].equals(dy)) ? 1.0 : -1.0;
            Double e = y - fx;
            if (e == 0) ++correct;
        }
        return (double) correct / testData.size() * 100;
    }

    private Double getRandomNumber() {
        Random r = new Random();
        return minRange + (maxRange - minRange) * r.nextDouble();
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public List<Double[]> getWeights() {
        return weights;
    }

    public void setWeights(List<Double[]> weights) {
        this.weights = weights;
    }
}
