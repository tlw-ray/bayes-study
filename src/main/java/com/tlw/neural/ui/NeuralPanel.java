package com.tlw.neural.ui;

import javax.swing.*;
import java.awt.*;

public class NeuralPanel extends JPanel {

    protected JPanel drawPanel = new JPanel();
    protected OperatePanel operatePanel = new OperatePanel();
    protected DataPanel dataPanel = new DataPanel();

    public NeuralPanel(){
        drawPanel.setBorder(BorderFactory.createTitledBorder("Draw"));
        operatePanel.setBorder(BorderFactory.createTitledBorder("Operate"));
        dataPanel.setBorder(BorderFactory.createTitledBorder("Data"));

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraints.gridy = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1;
        add(drawPanel, gridBagConstraints);
        gridBagConstraints.weightx = 1;
        add(operatePanel, gridBagConstraints);
        add(dataPanel, gridBagConstraints);
    }

}
