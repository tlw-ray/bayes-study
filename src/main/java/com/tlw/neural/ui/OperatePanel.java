package com.tlw.neural.ui;

import com.tlw.neural.perceptron.PerceptronParameterPanel;

import javax.swing.*;
import java.awt.*;

public class OperatePanel extends JPanel {

    protected JButton loadButton = new JButton("Load");
    protected JLabel loadLabel = new JLabel("File not loaded");

    protected PerceptronParameterPanel perceptronParameterPanel = new PerceptronParameterPanel();
    protected NeuralResultPanel neuralResultPanel = new NeuralResultPanel();

    public OperatePanel(){
        loadLabel.setHorizontalAlignment(JLabel.CENTER);
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 1;
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagConstraints.gridx = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5,5,5,5);
        add(loadButton, gridBagConstraints);
        add(loadLabel, gridBagConstraints);
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        add(perceptronParameterPanel, gridBagConstraints);
        add(neuralResultPanel, gridBagConstraints);
    }

}
