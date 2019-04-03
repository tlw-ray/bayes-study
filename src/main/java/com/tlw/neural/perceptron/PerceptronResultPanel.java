package com.tlw.neural.perceptron;

import com.tlw.neural.ui.NeuralResultPanel;

import javax.swing.*;

public class PerceptronResultPanel extends NeuralResultPanel {

    protected JLabel finalThresholdLabel = new JLabel("Final Threshold");
    protected JLabel finalThresholdValueLabel = new JLabel("?");

    protected JLabel synapticWeightLabel = new JLabel("Synaptic Weight");
    protected JLabel synapticWeightValueLabel = new JLabel("?");

    public PerceptronResultPanel(){
        super();
        addRow(finalThresholdLabel, finalThresholdValueLabel);
        addRow(synapticWeightLabel, synapticWeightValueLabel);
    }

}
