package com.tlw.neural.backprop;

import com.tlw.neural.ui.NeuralResultPanel;

import javax.swing.*;

public class BackpropResultPanel extends NeuralResultPanel {

    protected JLabel rootMeanSquaredErrorLabel = new JLabel("Root Mean-Squared Error");
    protected JLabel rootMeanSquaredErrorValueLabel = new JLabel("?");

    public BackpropResultPanel(){
        super();
        rootMeanSquaredErrorValueLabel.setHorizontalAlignment(JLabel.CENTER);
        addRow(rootMeanSquaredErrorLabel, rootMeanSquaredErrorValueLabel);
    }
}
