package com.tlw.neural.perceptron;

import com.tlw.neural.ui.NeuralResultPanel;

import javax.swing.*;
import java.awt.*;

public class PerceptronResultPanel extends JPanel {

    protected JLabel rootMeanSquaredErrorLabel = new JLabel("Root Mean-Squared Error");
    protected JLabel rootMeanSquaredErrorValueLabel = new JLabel("?");

    public PerceptronResultPanel(){
        setBorder(BorderFactory.createTitledBorder("Perceptron Result"));
        rootMeanSquaredErrorValueLabel.setHorizontalAlignment(JLabel.CENTER);
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        add(rootMeanSquaredErrorLabel, gridBagConstraints);
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(rootMeanSquaredErrorValueLabel, gridBagConstraints);
    }

}
