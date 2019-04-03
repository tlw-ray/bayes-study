package com.tlw.neural.ui.common;

import javax.swing.*;
import java.awt.*;

public class PropertyPanel extends JPanel {

    protected GridBagConstraints gridBagConstraints = new GridBagConstraints();

    public PropertyPanel(){
        setLayout(new GridBagLayout());
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraints.gridy = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
    }

    public void addRow(Component labelComponent, Component valueComponent){
        add(labelComponent, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(valueComponent, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy++;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.fill = GridBagConstraints.NONE;
    }

}
