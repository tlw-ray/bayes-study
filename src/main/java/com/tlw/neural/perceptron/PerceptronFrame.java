package com.tlw.neural.perceptron;

import com.tlw.neural.ui.NeuralFrame;
import com.tlw.neural.ui.NeuralPanel;

public class PerceptronFrame extends NeuralFrame {

    public static void main(String[] args){
        PerceptronFrame perceptronFrame = new PerceptronFrame();
        perceptronFrame.setVisible(true);
    }

    private PerceptronPanel perceptronPanel;

    protected synchronized NeuralPanel getNeuralPanel(){
        if(perceptronPanel == null){
            perceptronPanel = new PerceptronPanel();
        }
        return perceptronPanel;
    }
}
