package com.tlw.neural.ui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class NeuralPanel extends JPanel implements ActionListener {

    protected NeuralModel neuralModel;

    protected DrawPanel drawPanel = new DrawPanel();
    protected OperatePanel operatePanel = new OperatePanel();
    protected DataPanel dataPanel = new DataPanel();

    public NeuralPanel(){
        getDrawPanel().setBorder(BorderFactory.createTitledBorder("Draw"));
        getOperatePanel().setBorder(BorderFactory.createTitledBorder("Operate"));
        getDataPanel().setBorder(BorderFactory.createTitledBorder("Data"));

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

        operatePanel.getLoadButton().addActionListener(this);
        operatePanel.getGenerateButton().addActionListener(this);
    }

    public DrawPanel getDrawPanel() {
        return drawPanel;
    }

    public OperatePanel getOperatePanel() {
        return operatePanel;
    }

    public DataPanel getDataPanel() {
        return dataPanel;
    }

    protected void startTrain() {
        //Draw
    }

    private void resetData() {
        neuralModel.getInput().clear();
        neuralModel.getTrainData().clear();
        neuralModel.getTestData().clear();
        neuralModel.getOutputKinds().clear();
        dataPanel.getTrainTableModel().setColumnCount(0);
        dataPanel.getTrainTableModel().setRowCount(0);
        dataPanel.getTestTableModel().setColumnCount(0);
        dataPanel.getTestTableModel().setRowCount(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == getOperatePanel().getLoadButton()){
            loadFile();
        }else if(e.getSource() == getOperatePanel().getGenerateButton()){
            startTrain();
        }
    }

    public void loadFile(){
        JFileChooser fileChooser = new JFileChooser("data/");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files(*.txt)", "txt", "text");
        fileChooser.setFileFilter(filter);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File loadedFile = fileChooser.getSelectedFile();
            operatePanel.getLoadLabel().setText(loadedFile.getPath());
            resetData();
            try (BufferedReader br = new BufferedReader(new FileReader(loadedFile))) {
                String line = br.readLine();
                while (line != null) {
                    // Split by space or tab
                    String[] lineSplit = line.split("\\s+");
                    // Remove empty elements
                    lineSplit = Arrays.stream(lineSplit).
                            filter(s -> (s != null && s.length() > 0)).
                            toArray(String[]::new);
                    Double[] numbers = new Double[lineSplit.length + 1];
                    numbers[0] = -1.0;
                    for (int i = 1; i <= lineSplit.length; i++) {
                        numbers[i] = Double.parseDouble(lineSplit[i - 1]);
                    }
                    neuralModel.getInput().add(numbers);
                    line = br.readLine();
                }
                for (Double[] x : neuralModel.getInput()) {
                    Double output = x[x.length - 1];
                    if (!neuralModel.getOutputKinds().contains(output))
                        neuralModel.getOutputKinds().add(output);
                }
                int[] trainKindTimes = new int[neuralModel.getOutputKinds().size()];
                int[] testKindTimes = new int[neuralModel.getOutputKinds().size()];
                for (Double[] x : neuralModel.getInput()) {
                    Double output = x[x.length - 1];
                    int i;
                    for (i = 0; i < neuralModel.getOutputKinds().size(); i++)
                        if (output.equals(neuralModel.getOutputKinds().get(i)))
                            break;
                    if (trainKindTimes[i] == 0 || testKindTimes[i] > trainKindTimes[i] / 2) {
                        ++trainKindTimes[i];
                        neuralModel.getTrainData().add(x);
                    } else {
                        ++testKindTimes[i];
                        neuralModel.getTestData().add(x);
                    }
                }
                ArrayList<String> header = new ArrayList<>();
                header.add("w");
                for (int i = 1; i < neuralModel.getTrainData().get(0).length - 1; i++)
                    header.add("x" + i);
                header.add("yd");
                dataPanel.getTrainTableModel().setColumnIdentifiers(header.toArray());
                dataPanel.getTestTableModel().setColumnIdentifiers(header.toArray());
                for (Double[] x : neuralModel.getTrainData())
                    dataPanel.getTrainTableModel().addRow(x);
                for (Double[] x : neuralModel.getTestData())
                    dataPanel.getTestTableModel().addRow(x);
                // TODO - show y result at data table
                operatePanel.getGenerateButton().setEnabled(true);
                startTrain();
                repaint();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public NeuralModel getNeuralModel() {
        return neuralModel;
    }

    protected void setNeuralModel(NeuralModel neuralModel) {
        this.neuralModel = neuralModel;
        getDrawPanel().setNeuralModel(neuralModel);
    }
}
