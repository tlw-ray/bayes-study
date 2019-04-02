package com.tlw.neural.ui;

import javax.swing.*;
import java.awt.*;

public class CoordinatePanel extends JPanel {

    public CoordinatePanel(){
        Dimension size = new Dimension(500, 500);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        setOpaque(true);
        setBackground(Color.WHITE);
    }
}
