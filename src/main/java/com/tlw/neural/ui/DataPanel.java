package com.tlw.neural.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DataPanel extends JPanel {

    protected DefaultTableModel trainTableModel = new DefaultTableModel();
    protected JLabel trainingLabel = new JLabel("Training Data");
    protected JTable trainingTable = new JTable(trainTableModel);
    protected JScrollPane trainingScrollPane = new JScrollPane(trainingTable);

    protected DefaultTableModel testTableModel = new DefaultTableModel();
    protected JLabel testingLabel = new JLabel("Testing Data");
    protected JTable testingTable = new JTable(testTableModel);
    protected JScrollPane testingScrollPane = new JScrollPane(testingTable);

    public DataPanel(){
        trainingLabel.setHorizontalAlignment(JLabel.CENTER);
        testingLabel.setHorizontalAlignment(JLabel.CENTER);

        Dimension dimension = new Dimension(300, 200);
        trainingScrollPane.setPreferredSize(dimension);
        testingScrollPane.setPreferredSize(dimension);

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

    public DefaultTableModel getTrainTableModel() {
        return trainTableModel;
    }

    public DefaultTableModel getTestTableModel() {
        return testTableModel;
    }
}
