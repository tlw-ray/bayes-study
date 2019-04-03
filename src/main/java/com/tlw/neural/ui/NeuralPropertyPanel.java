package com.tlw.neural.ui;

import com.tlw.neural.ui.common.PropertyPanel;
import com.tlw.neural.ui.common.RangePanel;

import javax.swing.*;

public class NeuralPropertyPanel extends PropertyPanel {
    protected JLabel learningRateLabel = new JLabel("Learning Rate: ");
    protected JTextField learningRateTextField = new JTextField("0.1");

    protected JLabel initialThresholdLabel = new JLabel("Initial Threshold: ");
    protected JTextField initialThresholdTextField = new JTextField("0");

    protected JLabel maximumConvergenceLabel = new JLabel("Maximum Convergence: ");
    protected JTextField maximumConvergenceTextField = new JTextField("1000");

    protected JLabel initialWeightsRangeLabel = new JLabel("Initial Weights Range: ");
    protected RangePanel rangePanel = new RangePanel();

    public NeuralPropertyPanel(){
        super();
        setBorder(BorderFactory.createTitledBorder("Perceptron Parameter"));
        addRow(learningRateLabel, learningRateTextField);
        addRow(initialThresholdLabel, initialThresholdTextField);
        addRow(maximumConvergenceLabel, maximumConvergenceTextField);
        addRow(initialWeightsRangeLabel, rangePanel);
    }
}
