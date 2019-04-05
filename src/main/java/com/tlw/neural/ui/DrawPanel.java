package com.tlw.neural.ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

import static javax.swing.border.TitledBorder.CENTER;
import static javax.swing.border.TitledBorder.DEFAULT_POSITION;

public class DrawPanel extends JPanel implements ChangeListener {

    private NeuralModel neuralModel;

    protected CoordinatePanel coordinatePanel = new CoordinatePanel();

    protected JLabel zoomLabel = new JLabel("Zoomer");
    protected JSlider zoomSlider = new JSlider();
    protected JPanel zoomPanel = new JPanel();

    //TODO 绘制参数面板
    protected JPanel drawParameterPanel = new JPanel();

    public DrawPanel(){
        zoomPanel.setLayout(new BorderLayout());
        zoomPanel.add(zoomLabel, BorderLayout.WEST);
        zoomPanel.add(zoomSlider, BorderLayout.CENTER);

        zoomSlider.setBorder(BorderFactory.createTitledBorder(null, Integer.toString(zoomSlider.getValue()), CENTER, DEFAULT_POSITION));
        zoomSlider.setInverted(false);
        zoomSlider.setMajorTickSpacing(10);
        zoomSlider.setMinimum(10);
        zoomSlider.setMinorTickSpacing(5);
        zoomSlider.setPaintLabels(true);
        zoomSlider.setPaintTicks(true);
        zoomSlider.setSnapToTicks(false);
        zoomSlider.setValue(50);
        zoomSlider.setValueIsAdjusting(false);
        zoomSlider.putClientProperty("JSlider.isFilled", true);
        zoomSlider.putClientProperty("Slider.paintThumbArrowShape", false);

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagConstraints.gridx = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        add(coordinatePanel, gridBagConstraints);
        gridBagConstraints.weighty = 1;
        if(getDrawParameterPanel() != null){
            add(drawParameterPanel, gridBagConstraints);
        }
        add(zoomPanel, gridBagConstraints);

        zoomSlider.addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        getZoomSlider().setBorder(
                BorderFactory.createTitledBorder(null,
                        Integer.toString(getZoomSlider().getValue()), CENTER, DEFAULT_POSITION));
        getNeuralModel().setMagnification(getZoomSlider().getValue());
        getCoordinatePanel().repaint();
    }

    protected JPanel getDrawParameterPanel(){
        return null;
    }

    public CoordinatePanel getCoordinatePanel() {
        return coordinatePanel;
    }

    public JLabel getZoomLabel() {
        return zoomLabel;
    }

    public JSlider getZoomSlider() {
        return zoomSlider;
    }

    public JPanel getZoomPanel() {
        return zoomPanel;
    }

    public NeuralModel getNeuralModel() {
        return neuralModel;
    }

    public void setNeuralModel(NeuralModel neuralModel) {
        this.neuralModel = neuralModel;
        getCoordinatePanel().setNeuralModel(neuralModel);
    }
}
