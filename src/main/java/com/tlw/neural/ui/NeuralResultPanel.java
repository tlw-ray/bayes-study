package com.tlw.neural.ui;

import com.tlw.neural.ui.common.PropertyPanel;

import javax.swing.*;
import java.awt.*;

public class NeuralResultPanel extends PropertyPanel {

    protected JLabel convergenceTimesLabel = new JLabel("Convergence Times: ");
    protected JLabel convergenceTimesValueLabel = new JLabel("?");

    protected JLabel trainingRecognitionRateLabel = new JLabel("Training Recognition Rate: ");
    protected JLabel trainingRecognitionRateValueLabel = new JLabel("?");

    protected JLabel testingRecognitionRateLabel = new JLabel("Testing Recognition Rate: ");
    protected JLabel testingRecognitionRateValueLabel = new JLabel("?");


    public NeuralResultPanel(){
        convergenceTimesValueLabel.setHorizontalAlignment(JLabel.CENTER);
        trainingRecognitionRateValueLabel.setHorizontalAlignment(JLabel.CENTER);
        testingRecognitionRateValueLabel.setHorizontalAlignment(JLabel.CENTER);

        setBorder(BorderFactory.createTitledBorder("Result"));
        addRow(convergenceTimesLabel, convergenceTimesValueLabel);
        addRow(trainingRecognitionRateLabel, trainingRecognitionRateValueLabel);
        addRow(testingRecognitionRateLabel, testingRecognitionRateValueLabel);
    }
}
