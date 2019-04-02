package com.tlw.neural.ui;

import javax.swing.*;
import java.awt.*;

public class DataPanel extends JPanel {

    JLabel trainingLabel = new JLabel("Training Data");
    JTable trainingTable = new JTable();
    JScrollPane trainingScrollPane = new JScrollPane(trainingTable);

    JLabel testingLabel = new JLabel("Testing Data");
    JTable testingTable = new JTable();
    JScrollPane testingScrollPane = new JScrollPane(testingTable);

    public DataPanel(){
        trainingLabel.setHorizontalAlignment(JLabel.CENTER);
        testingLabel.setHorizontalAlignment(JLabel.CENTER);

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagConstraints.gridx = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        add(trainingLabel, gridBagConstraints);
        gridBagConstraints.weighty = 1;
        add(trainingScrollPane, gridBagConstraints);
        gridBagConstraints.weighty = 0;
        add(testingLabel, gridBagConstraints);
        gridBagConstraints.weighty = 1;
        add(testingScrollPane, gridBagConstraints);
    }
}
