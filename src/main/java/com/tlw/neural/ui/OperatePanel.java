package com.tlw.neural.ui;

import javax.swing.*;
import java.awt.*;

public class OperatePanel extends JPanel {

    protected JButton loadButton = new JButton("Load");
    protected JLabel loadLabel = new JLabel("File not loaded");

    protected NeuralPropertyPanel neuralPropertyPanel = new NeuralPropertyPanel();
    protected NeuralResultPanel neuralResultPanel = new NeuralResultPanel();

    protected JButton generateButton = new JButton("Generate");

    public OperatePanel(){
        loadLabel.setHorizontalAlignment(JLabel.CENTER);
        Dimension dimension = new Dimension(300, 200);
        neuralPropertyPanel.setPreferredSize(dimension);
        neuralResultPanel.setPreferredSize(dimension);
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 1;
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagConstraints.gridx = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(loadButton, gridBagConstraints);
        add(loadLabel, gridBagConstraints);
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        add(neuralPropertyPanel, gridBagConstraints);
        add(neuralResultPanel, gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 0;
        add(generateButton, gridBagConstraints);
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JLabel getLoadLabel() {
        return loadLabel;
    }

    public NeuralPropertyPanel getNeuralPropertyPanel() {
        return neuralPropertyPanel;
    }

    public NeuralResultPanel getNeuralResultPanel() {
        return neuralResultPanel;
    }

    public JButton getGenerateButton() {
        return generateButton;
    }
}
