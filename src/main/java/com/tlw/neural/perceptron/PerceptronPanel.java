package com.tlw.neural.perceptron;

import com.tlw.neural.ui.NeuralPanel;

public class PerceptronPanel extends NeuralPanel {

    public PerceptronPanel(){
        super();
        this.neuralModel = new PerceptronModel();
    }

    @Override
    public PerceptronModel getNeuralModel() {
        return (PerceptronModel)super.getNeuralModel();
    }

    public void setPerceptronModel(PerceptronModel neuralModel) {
        super.setNeuralModel(neuralModel);
    }

    @Override
    protected void startTrain() {
        PerceptronResult perceptronResult = getNeuralModel().startTrain();
//        timesValue.setText(String.valueOf(times));
//        weightsValue.setText(weightOutput.toString());
//        fThresholdValue.setText(weights.get(wi)[0].toString());
//        trainingValue.setText((double) correct / trainData.size() * 100 + "%");
        //        testingValue.setText( + "%");
//        coordinatePanel.repaint();
    }
}
