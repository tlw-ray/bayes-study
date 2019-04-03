package com.tlw.neural.ui;

import javax.swing.*;
import java.awt.*;

import static javax.swing.border.TitledBorder.CENTER;
import static javax.swing.border.TitledBorder.DEFAULT_POSITION;

public class DrawPanel extends JPanel {

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
//                <properties>
//          <inverted value="false"/>
//          <majorTickSpacing value="10"/>
//          <minimum value="10"/>
//          <minorTickSpacing value="5"/>
//          <paintLabels value="true"/>
//          <paintTicks value="true"/>
//          <snapToTicks value="false"/>
//          <value value="50"/>
//          <valueIsAdjusting value="false"/>
//        </properties>
//        <clientProperties>
//          <JSlider.isFilled class="java.lang.Boolean" value="false"/>
//          <Slider.paintThumbArrowShape class="java.lang.Boolean" value="false"/>
//        </clientProperties>

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagConstraints.gridx = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        add(coordinatePanel, gridBagConstraints);
        if(getDrawParameterPanel() != null){
            add(drawParameterPanel, gridBagConstraints);
        }
        add(zoomPanel, gridBagConstraints);
    }

    protected JPanel getDrawParameterPanel(){
        return null;
    }
}
