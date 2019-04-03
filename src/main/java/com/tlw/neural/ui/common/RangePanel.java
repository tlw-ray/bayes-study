package com.tlw.neural.ui.common;

import javax.swing.*;
import java.awt.*;

public class RangePanel extends JPanel {
    protected JTextField fromWeightsRangeTextField = new JTextField("-0.5");
    protected JLabel rangeLabel = new JLabel("~");
    protected JTextField toWeightsRangeTextField = new JTextField("0.5");
    public RangePanel(){
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraints.gridy = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        add(fromWeightsRangeTextField, gridBagConstraints);
        gridBagConstraints.weightx = 0;
        gridBagConstraints.insets = new Insets(0, 5, 0, 5);
        add(rangeLabel, gridBagConstraints);
        gridBagConstraints.weightx = 1;
        gridBagConstraints.insets = new Insets(0,0,0,0);
        add(toWeightsRangeTextField, gridBagConstraints);
    }

    public JTextField getFromWeightsRangeTextField() {
        return fromWeightsRangeTextField;
    }

    public JTextField getToWeightsRangeTextField() {
        return toWeightsRangeTextField;
    }
}
