package com.tlw.neural.perceptron;

import javax.swing.*;
import java.awt.*;

public class PerceptronParameterPanel extends JPanel {

    protected JLabel learningRateLabel = new JLabel("Learning Rate: ");
    protected JTextField learningRateTextField = new JTextField("0.1");

    protected JLabel initialThresholdLabel = new JLabel("Initial Threshold: ");
    protected JTextField initialThresholdTextField = new JTextField("0");

    protected JLabel maximumConvergenceLabel = new JLabel("Maximum Convergence: ");
    protected JTextField maximumConvergenceTextField = new JTextField("1000");

    protected JLabel initialWeightsRange = new JLabel("Initial Weights Range: ");
    protected JTextField fromWeightsRangeTextField = new JTextField("-0.5");
    protected JLabel rangeLabel = new JLabel("~");
    protected JTextField toWeightsRangeTextField = new JTextField("0.5");

    public PerceptronParameterPanel(){
        setBorder(BorderFactory.createTitledBorder("Perceptron Parameter"));
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridx = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        add(learningRateLabel, gridBagConstraints);
        add(initialThresholdLabel, gridBagConstraints);
        add(maximumConvergenceLabel, gridBagConstraints);
        add(initialWeightsRange, gridBagConstraints);
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        add(learningRateTextField, gridBagConstraints);
        add(initialThresholdTextField, gridBagConstraints);
        add(maximumConvergenceTextField, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
        add(fromWeightsRangeTextField, gridBagConstraints);
        gridBagConstraints.weightx = 0;
        gridBagConstraints.gridx = 2;
        add(rangeLabel, gridBagConstraints);
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 3;
        add(toWeightsRangeTextField, gridBagConstraints);
    }
}
