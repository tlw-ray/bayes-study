package com.tlw.neural.backprop;

import com.tlw.neural.algorithm.NeuralNetwork;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static javax.swing.border.TitledBorder.CENTER;
import static javax.swing.border.TitledBorder.DEFAULT_POSITION;

public class MainFrame {
    private static JMenuItem loadMenuItem;
    private static JMenuItem generateMenuItem;
    private static JFrame frame;
    private JPanel layoutPanel;
    private JPanel coordinatePanel;
    private JButton loadButton;
    private JLabel loadValue;
    private JButton generateButton;
    private JTextField learningTextField;
    private JTextField thresholdTextField;
    private JLabel trainingValue;
    private JLabel testingValue;
    private JSlider zoomerSlider;
    private JLabel timesValue;
    private JTextField maxTimesValue;
    private JTextField wRangeMinValue;
    private JTextField wRangeMaxValue;
    private JTextField hiddenTextField;
    private JTextField momentumTextField;
    private JTable trainTable;
    private JTable testTable;
    private JTextField minErrorTextField;
    private JLabel MSEValue;
    private JTextField sizeTextField;
    private JCheckBox drawModeCheckBox;
    private JButton trainByAllDataButton;
    private DefaultTableModel trainTableModel = new DefaultTableModel();
    private DefaultTableModel testTableModel = new DefaultTableModel();
    private DecimalFormat df = new DecimalFormat("####0.00");
    private Color[] colorArray = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.CYAN, Color.PINK};
    private NeuralNetwork network;
    private ArrayList<Double[]> inputs = new ArrayList<>();
    private ArrayList<Double[]> trainData = new ArrayList<>();
    private ArrayList<Double[]> testData = new ArrayList<>();
    private ArrayList<Double> outputKinds = new ArrayList<>();
    private Point mouse;
    private int maxTimes = 1000;
    private int magnification = 50;
    private String hidden = "4,4";
    private double momentum = 0.7;
    private double learningRate = 0.1;
    private double threshold = 0;
    private double minRange = -0.5;
    private double maxRange = 0.5;
    private double minError = 0.01;
    private double size = 20.0;
    private boolean drawMode = false;

    private MainFrame() {
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("data/");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files(*.txt)", "txt", "text");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showOpenDialog(layoutPanel) == JFileChooser.APPROVE_OPTION) {
                loadFile(fileChooser);
            }
        });
        loadMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files(*.txt)", "txt", "text");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showOpenDialog(layoutPanel) == JFileChooser.APPROVE_OPTION) {
                loadFile(fileChooser);
            }
        });
        generateButton.addActionListener(e -> startTrain(trainData));
        generateMenuItem.addActionListener(e -> startTrain(trainData));
        trainByAllDataButton.addActionListener(e -> startTrain(testData));
        zoomerSlider.addChangeListener(e -> {
            zoomerSlider.setBorder(
                    BorderFactory.createTitledBorder(null,
                            Integer.toString(zoomerSlider.getValue()), CENTER, DEFAULT_POSITION));
            magnification = zoomerSlider.getValue();
            coordinatePanel.repaint();
        });
        coordinatePanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (!drawMode) {
                    mouse = e.getPoint();
                    coordinatePanel.repaint();
                }
            }
        });
        drawModeCheckBox.addActionListener(e -> {
            drawMode = !drawMode;
            coordinatePanel.repaint();
        });
        hiddenTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeHidden();
            }

            public void removeUpdate(DocumentEvent e) {
                changeHidden();
            }

            public void insertUpdate(DocumentEvent e) {
                changeHidden();
            }

            void changeHidden() {
                hidden = hiddenTextField.getText();
            }
        });
        momentumTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeMomentum();
            }

            public void removeUpdate(DocumentEvent e) {
                changeMomentum();
            }

            public void insertUpdate(DocumentEvent e) {
                changeMomentum();
            }

            void changeMomentum() {
                try {
                    alertBackground(momentumTextField, false);
                    momentum = Double.valueOf(momentumTextField.getText());
                } catch (NumberFormatException e) {
                    alertBackground(momentumTextField, true);
                    momentum = 0.5;
                }
            }
        });
        learningTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeRate();
            }

            public void removeUpdate(DocumentEvent e) {
                changeRate();
            }

            public void insertUpdate(DocumentEvent e) {
                changeRate();
            }

            void changeRate() {
                try {
                    alertBackground(learningTextField, false);
                    learningRate = Double.valueOf(learningTextField.getText());
                } catch (NumberFormatException e) {
                    alertBackground(learningTextField, true);
                    learningRate = 0.5f;
                }
            }
        });
        thresholdTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeThreshold();
            }

            public void removeUpdate(DocumentEvent e) {
                changeThreshold();
            }

            public void insertUpdate(DocumentEvent e) {
                changeThreshold();
            }

            void changeThreshold() {
                try {
                    alertBackground(thresholdTextField, false);
                    threshold = Double.valueOf(thresholdTextField.getText());
                } catch (NumberFormatException e) {
                    alertBackground(thresholdTextField, true);
                    threshold = 0;
                }
            }
        });
        maxTimesValue.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeMaxTimes();
            }

            public void removeUpdate(DocumentEvent e) {
                changeMaxTimes();
            }

            public void insertUpdate(DocumentEvent e) {
                changeMaxTimes();
            }

            void changeMaxTimes() {
                try {
                    alertBackground(maxTimesValue, false);
                    maxTimes = Integer.valueOf(maxTimesValue.getText());
                } catch (NumberFormatException e) {
                    alertBackground(maxTimesValue, true);
                    maxTimes = 1000;
                }
            }
        });
        minErrorTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeMinError();
            }

            public void removeUpdate(DocumentEvent e) {
                changeMinError();
            }

            public void insertUpdate(DocumentEvent e) {
                changeMinError();
            }

            void changeMinError() {
                try {
                    alertBackground(minErrorTextField, false);
                    minError = Double.valueOf(minErrorTextField.getText());
                } catch (NumberFormatException e) {
                    alertBackground(minErrorTextField, true);
                    minError = 0.01;
                }
            }
        });
        wRangeMinValue.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeMinRange();
            }

            public void removeUpdate(DocumentEvent e) {
                changeMinRange();
            }

            public void insertUpdate(DocumentEvent e) {
                changeMinRange();
            }

            void changeMinRange() {
                try {
                    if (Double.valueOf(wRangeMinValue.getText()) > maxRange)
                        alertBackground(wRangeMinValue, true);
                    else {
                        alertBackground(wRangeMinValue, false);
                        minRange = Double.valueOf(wRangeMinValue.getText());
                    }
                } catch (NumberFormatException e) {
                    alertBackground(wRangeMinValue, true);
                    minRange = -0.5f;
                }
            }
        });
        wRangeMaxValue.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeMaxRange();
            }

            public void removeUpdate(DocumentEvent e) {
                changeMaxRange();
            }

            public void insertUpdate(DocumentEvent e) {
                changeMaxRange();
            }

            void changeMaxRange() {
                try {
                    if (Double.valueOf(wRangeMaxValue.getText()) < minRange)
                        alertBackground(wRangeMaxValue, true);
                    else {
                        alertBackground(wRangeMaxValue, false);
                        maxRange = Double.valueOf(wRangeMaxValue.getText());
                    }
                } catch (NumberFormatException e) {
                    alertBackground(wRangeMaxValue, true);
                    maxRange = 0.5f;
                }
            }
        });
        sizeTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeSize();
            }

            public void removeUpdate(DocumentEvent e) {
                changeSize();
            }

            public void insertUpdate(DocumentEvent e) {
                changeSize();
            }

            void changeSize() {
                try {
                    alertBackground(sizeTextField, false);
                    size = Double.valueOf(sizeTextField.getText());
                } catch (NumberFormatException e) {
                    alertBackground(sizeTextField, true);
                    size = 20.0;
                }
            }
        });
    }

    private void loadFile(JFileChooser fileChooser) {
        File loadedFile = fileChooser.getSelectedFile();
        loadValue.setText(loadedFile.getPath());
        resetFrame();
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
                for (int i = 1; i <= lineSplit.length; i++)
                    numbers[i] = Double.parseDouble(lineSplit[i - 1]);
                inputs.add(numbers);
                Double output = numbers[numbers.length - 1];
                if (!outputKinds.contains(output))
                    outputKinds.add(output);
                line = br.readLine();
            }
            initialData();
            ArrayList<String> header = new ArrayList<>();
            for (int i = 1; i < trainData.get(0).length - 1; i++)
                header.add("x" + i);
            header.add("yd");
            trainTableModel.setColumnIdentifiers(header.toArray());
            testTableModel.setColumnIdentifiers(header.toArray());
            for (Double[] x : trainData) {
                x = Arrays.copyOfRange(x, 1, x.length);
                trainTableModel.addRow(x);
            }
            for (Double[] x : testData) {
                x = Arrays.copyOfRange(x, 1, x.length);
                testTableModel.addRow(x);
            }
            trainTable.setModel(trainTableModel);
            testTable.setModel(testTableModel);
            // TODO - show y result at data table
            generateButton.setEnabled(true);
            startTrain(trainData);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void resetData() {
        inputs.clear();
        trainData.clear();
        testData.clear();
        outputKinds.clear();
        trainTableModel.setColumnCount(0);
        trainTableModel.setRowCount(0);
        testTableModel.setColumnCount(0);
        testTableModel.setRowCount(0);
    }

    private void initialData() {
        // Normalize expected output
        Double outputMin = Collections.min(outputKinds);
        Double outputMax = Collections.max(outputKinds);
        for (Double[] input : inputs) {
            input[input.length - 1] = normalize(input[input.length - 1], outputMin, outputMax);
        }
        for (int i = 0; i < outputKinds.size(); i++) {
            outputKinds.set(i, normalize(outputKinds.get(i), outputMin, outputMax));
        }
        // Split input into train & test
        int[] trainKindTimes = new int[outputKinds.size()];
        int[] testKindTimes = new int[outputKinds.size()];
        for (Double[] x : inputs) {
            Double output = x[x.length - 1];
            int i;
            for (i = 0; i < outputKinds.size(); i++)
                if (output.equals(outputKinds.get(i)))
                    break;
            if (trainKindTimes[i] == 0 || testKindTimes[i] > trainKindTimes[i] / 2) {
                ++trainKindTimes[i];
                trainData.add(x);
            } else {
                ++testKindTimes[i];
                testData.add(x);
            }
        }
    }

    private void startTrain(ArrayList<Double[]> inputs) {
        network = new NeuralNetwork(inputs, outputKinds, hidden, momentum,
                learningRate, threshold, minRange, maxRange);
        String[] resultTrain = network.run(maxTimes, minError).split(" ");
        timesValue.setText(resultTrain[0]);
        MSEValue.setText(resultTrain[1]);
        trainingValue.setText(resultTrain[2]);
        String resultTest = network.test(testData, maxTimes, minError);
        testingValue.setText(resultTest);
        coordinatePanel.repaint();
    }

    private Double round(Double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private Double normalize(Double input, Double min, Double max) {
        return round((input - min) / (max - min), 4);
    }

    private Double[]                                              convertCoordinate(Double[] oldPoint) {
        Double[] newPoint = new Double[2];
        newPoint[0] = (oldPoint[0] * magnification) + 250;
        newPoint[1] = 250 - (oldPoint[1] * magnification);
        return newPoint;
    }

    private void alertBackground(JTextField textField, boolean alert) {
        if (alert)
            textField.setBackground(Color.PINK);
        else
            textField.setBackground(Color.WHITE);
    }

    private static void resetFrame() {
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private static void changeLAF(String name) {
        try {
            if (name.equals("Nimbus")) {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
            } else {
                UIManager.setLookAndFeel(name);
            }
            resetFrame();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
            System.out.println("Failed to load the skin!");
        }
    }

    private void createUIComponents() {
        coordinatePanel = new GPanel();
        zoomerSlider = new JSlider();
        zoomerSlider.setBorder(
                BorderFactory.createTitledBorder(null,
                        Integer.toString(zoomerSlider.getValue()), CENTER, DEFAULT_POSITION));
    }

    public static void main(String[] args) {
        JMenuBar menuBar = new JMenuBar();
        JMenu filesMenu = new JMenu("Files");
        JMenu skinsMenu = new JMenu("Skins");
        // Files menu
        loadMenuItem = new JMenuItem("Load", KeyEvent.VK_L);
        generateMenuItem = new JMenuItem("Generate", KeyEvent.VK_G);
        filesMenu.setMnemonic(KeyEvent.VK_F);
        filesMenu.add(loadMenuItem);
        filesMenu.add(generateMenuItem);
        menuBar.add(filesMenu);
        // Skins menu
        skinsMenu.setMnemonic(KeyEvent.VK_S);
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem skinsMetalMenuItem = new JRadioButtonMenuItem("Metal");
        skinsMetalMenuItem.setMnemonic(KeyEvent.VK_M);
        skinsMenu.add(skinsMetalMenuItem);
        group.add(skinsMetalMenuItem);
        skinsMetalMenuItem.addActionListener(e -> changeLAF(UIManager.getCrossPlatformLookAndFeelClassName()));
        JRadioButtonMenuItem skinsDefaultMenuItem = new JRadioButtonMenuItem("Default");
        skinsDefaultMenuItem.setMnemonic(KeyEvent.VK_D);
        skinsMenu.add(skinsDefaultMenuItem);
        group.add(skinsDefaultMenuItem);
        skinsDefaultMenuItem.addActionListener(e -> changeLAF(UIManager.getSystemLookAndFeelClassName()));
        JRadioButtonMenuItem skinsMotifMenuItem = new JRadioButtonMenuItem("Motif");
        skinsMotifMenuItem.setMnemonic(KeyEvent.VK_M);
        skinsMenu.add(skinsMotifMenuItem);
        group.add(skinsMotifMenuItem);
        skinsMotifMenuItem.addActionListener(e -> changeLAF("com.sun.java.swing.plaf.motif.MotifLookAndFeel"));
        JRadioButtonMenuItem skinsGTKMenuItem = new JRadioButtonMenuItem("GTK");
        skinsGTKMenuItem.setMnemonic(KeyEvent.VK_G);
        skinsMenu.add(skinsGTKMenuItem);
        group.add(skinsGTKMenuItem);
        skinsGTKMenuItem.addActionListener(e -> changeLAF("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"));
        JRadioButtonMenuItem skinsWindowsMenuItem = new JRadioButtonMenuItem("Windows");
        skinsWindowsMenuItem.setMnemonic(KeyEvent.VK_G);
        skinsMenu.add(skinsWindowsMenuItem);
        group.add(skinsWindowsMenuItem);
        skinsWindowsMenuItem.addActionListener(e -> changeLAF("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"));
        JRadioButtonMenuItem skinsNimbusMenuItem = new JRadioButtonMenuItem("Nimbus");
        skinsNimbusMenuItem.setMnemonic(KeyEvent.VK_N);
        skinsNimbusMenuItem.setSelected(true);
        skinsMenu.add(skinsNimbusMenuItem);
        group.add(skinsNimbusMenuItem);
        skinsNimbusMenuItem.addActionListener(e -> changeLAF("Nimbus"));
        menuBar.add(skinsMenu);
        // BackpropFrame frame
        frame = new JFrame("Backpropagation");
        frame.setContentPane(new MainFrame().layoutPanel);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        changeLAF("Nimbus");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private class GPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            // Draw output kind area
            if (drawMode && size > 0 && inputs.size() > 0 && inputs.get(0).length == 4) {
                ArrayList<Double[]> drawInputs = new ArrayList<>();
                for (Double x = -250.0; x <= 250; x += size) {
                    for (Double y = -250.0; y <= 250; y += size) {
                        drawInputs.add(new Double[]{-1.0, x / magnification, y / magnification});
                    }
                }
                int id[] = network.getOutputKind(drawInputs, maxTimes, minError);
                int i = 0;
                for (Double x = -250.0; x <= 250; x += size) {
                    for (Double y = -250.0; y <= 250; y += size) {
                        g2.setColor(colorArray[colorArray.length - id[i] - 1]);
                        Double[] point = convertCoordinate(new Double[]{x / magnification, y / magnification});
                        Rectangle2D rect = new Rectangle2D.Double(point[0], point[1], size, size);
                        g2.fill(rect);
                        i++;
                    }
                }
            }
            g.setColor(Color.black);
            g.drawLine(250, 0, 250, 500);
            g.drawLine(0, 250, 500, 250);
            g2.setColor(Color.black);
            // Draw scale
            for (Double i = 250.0; i >= 0; i -= 5.0 * magnification / 10) {
                drawScale(g2, i);
            }
            for (Double i = 250.0; i <= 500; i += 5.0 * magnification / 10) {
                drawScale(g2, i);
            }
            g2.setStroke(new BasicStroke(3));
            // Draw mouse position
            if (mouse != null) {
                Double mouse_x = (mouse.getX() - 250) / magnification;
                Double mouse_y = (250 - mouse.getY()) / magnification;
                g2.drawString("(" + df.format(mouse_x) + ", " + df.format(mouse_y) + ")", 420, 20);
            }
            // Draw point of file
            if (inputs.size() > 0 && inputs.get(0).length == 4) {
                for (Double[] x : inputs) {
                    Double[] point = convertCoordinate(new Double[]{x[1], x[2]});
                    for (int i = 0; i < outputKinds.size(); i++) {
                        Double outputKind = outputKinds.get(i);
                        if (x[x.length - 1].equals(outputKind)) {
                            g2.setColor(colorArray[i]);
                            break;
                        }
                    }
                    g2.draw(new Line2D.Double(point[0], point[1], point[0], point[1]));
                }
            }
            // TODO: Draw line of decision boundary
        }

        private void drawScale(Graphics2D g2, Double i) {
            Double[] top, btn;
            Double scaleLength = (i % (5.0 * magnification / 5) == 0) ? 2.0 * magnification / 20 : 1.0 * magnification / 20;
            top = convertCoordinate(new Double[]{(i - 250) / magnification, scaleLength / magnification});
            btn = convertCoordinate(new Double[]{(i - 250) / magnification, -scaleLength / magnification});
            g2.draw(new Line2D.Double(top[0], top[1], btn[0], btn[1]));
            top = convertCoordinate(new Double[]{-scaleLength / magnification, (250 - i) / magnification});
            btn = convertCoordinate(new Double[]{scaleLength / magnification, (250 - i) / magnification});
            g2.draw(new Line2D.Double(top[0], top[1], btn[0], btn[1]));
        }
    }
}
