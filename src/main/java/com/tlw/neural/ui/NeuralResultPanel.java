package com.tlw.neural.ui;

import javax.swing.*;
import java.awt.*;

public class NeuralResultPanel extends JPanel {

    protected JLabel convergenceTimesLabel = new JLabel("Convergence Times: ");
    protected JLabel convergenceTimesValueLabel = new JLabel("?");

    protected JLabel trainingRecognitionRateLabel = new JLabel("Training Recognition Rate: ");
    protected JLabel trainingRecognitionRateValueLabel = new JLabel("?");

    protected JLabel testingRecognitionRateLabel = new JLabel("Testing Recognition Rate: ");
    protected JLabel testingRecognitionRateValueLabel = new JLabel("?");


    public NeuralResultPanel(){
        setBorder(BorderFactory.createTitledBorder("Neural Result"));
        convergenceTimesValueLabel.setHorizontalAlignment(JLabel.CENTER);
        trainingRecognitionRateValueLabel.setHorizontalAlignment(JLabel.CENTER);
        testingRecognitionRateValueLabel.setHorizontalAlignment(JLabel.CENTER);
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        add(convergenceTimesLabel, gridBagConstraints);
        add(trainingRecognitionRateLabel, gridBagConstraints);
        add(testingRecognitionRateLabel, gridBagConstraints);
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(convergenceTimesValueLabel, gridBagConstraints);
        add(trainingRecognitionRateValueLabel, gridBagConstraints);
        add(testingRecognitionRateValueLabel, gridBagConstraints);
    }
}
