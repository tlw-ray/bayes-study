package com.tlw.neural.ui;

import javax.swing.*;

public class DrawPanel extends JPanel {

    protected CoordinatePanel coordinatePanel = new CoordinatePanel();

    protected JLabel zoomLabel = new JLabel("Zoomer");
    protected JSlider zoomSlider = new JSlider();

    public DrawPanel(){

    }
}
