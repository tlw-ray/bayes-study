package com.tlw.neural.ui;

import javax.swing.*;
import java.awt.*;

public class NeuralFrame extends JFrame {

    public static void main(String[] args){
        NeuralFrame neuralFrame = new NeuralFrame();
        neuralFrame.setVisible(true);
    }

    protected NeuralPanel neuralPanel = new NeuralPanel();

    public NeuralFrame(){
        add(neuralPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

}
