package com.tlw.neural.backprop;

import com.tlw.neural.ui.NeuralPropertyPanel;

import javax.swing.*;

public class BackpropPropertyPanel extends NeuralPropertyPanel {

    private JLabel hiddenLayerLabel = new JLabel("Hidden Layer");
    protected JTextField hiddenLayerTextField = new JTextField();

    protected JLabel momentumLabel = new JLabel("Momentum");
    protected JTextField momentumTextField = new JTextField();

    public BackpropPropertyPanel(){
        super();
        addRow(hiddenLayerLabel, hiddenLayerTextField);
        addRow(momentumLabel, momentumTextField);
    }
}
