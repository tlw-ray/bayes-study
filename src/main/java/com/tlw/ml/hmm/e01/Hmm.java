package com.tlw.ml.hmm.e01;

//////////////////////////////////////////////////////////////////////////
// The contents of this file are subject to the Mozilla Public License
// Version 1.0 (the "License"); you may not use this file except in
// compliance with the License. You may obtain a copy of the License at
// http://www.mozilla.org/MPL/
//
// Software distributed under the License is distributed on an "AS IS"
// basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
// License for the specific language governing rights and limitations under
// the License.
//
// The Original Code is Hidden Markov Model Library in Java.
//
// The Initial Developer of the Original Code is Hyoungsoo Yoon.
// Portions created by Hyoungsoo Yoon are
// Copyright (C) 1999 Hyoungsoo Yoon.  All Rights Reserved.
//
// Contributor(s):
//
//////////////////////////////////////////////////////////////////////////


/*
Hidden Markov Model Library in Java.
Please refer to Rabiner 1989.
All algorithms are directly taken from this article.
Notations and variable names also closely follow the conventions used in this paper.

@copyright  Hyoungsoo Yoon
@date  Feb 21st, 1999

来源网址:
http://hmmsdk.sourceforge.net/hmmlib-0.3/src/Hmm_java.html
*/

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.util.*;
import java.io.*;

/**
 Hidden Markov model.
 This class adds a sequence of observed symbols to the class Model.
 Please refer to Rabiner 1989.

 @author  Hyoungsoo Yoon
 @version  0.3
 */
public class Hmm implements Serializable {

    private static final int MAX_ITERATION = 100;
    private static final double MIN_ERROR = 0.001;
    private static final int MAX_LEN_OBSEQ = 10;

    private static Random rand = new Random();

    /**
     @serial  Underlying Markov model and its parameters (A, B, and pi)
     */
    private Model lambda;
    /**
     @serial  Forward variables  (Rabiner eq. 18)
     */
    private double[][] alpha;
    /**
     @serial  Backward variables  (Rabiner eq. 23)
     */
    private double[][] beta;
    /**
     @serial  Internal state probabilities (Rabiner eq. 26)
     */
    private double[][] gamma;
    /**
     @serial  Internal pair-state probabilities (Rabiner eq. 36)
     */
    private double[][][] ksi;
    /**
     @serial  Best score variables (Rabiner eq. 30)
     */
    private double[][] delta;
    /**
     @serial  Backtracking variables (Rabiner eq. 33b)
     */
    private int[][] psi;
    /**
     @serial  Most likely path (Rabiner eq. 35)
     */
    private int[] qstar;
    /**
     @serial  Scale factor (Rabiner eq. 91)
     */
    private double[] scaleFactor;
    private transient int  len_obseq = 0;
    private transient int[] obSeq;

    private transient String file_name = null;  // saves to and loads from this file

    /**
     Construct HMM with a minimum size (length of observation sequence: 1).
     The elements of the matrices are initialized by random values.
     */
    public Hmm() {
        this(true);
    }
    /**
     Construct HMM with a minimum size (length of observation sequence: 1).
     @param bRandomize If true, the elements of the matrices are initialized by random values
     */
    public Hmm(boolean bRandomize) {
        this(1, bRandomize);
    }
    /**
     Construct HMM by specifying its length (number of states: 1, number of symbols: 1).
     The elements of the matrices are initialized by random values.
     @param lobseq Length of symbols observation sequence
     */
    public Hmm(int lobseq) {
        this(lobseq, true);
    }
    /**
     Construct HMM by specifying its length (number of states: 1, number of symbols: 1).
     @param lobseq Length of symbols observation sequence
     @param bRandomize If true, the elements of the matrices are initialized by random values
     */
    public Hmm(int lobseq, boolean bRandomize) {
        this(lobseq, 1, 1, 0, 0, bRandomize);
    }
    /**
     Construct HMM by specifying its length, number of states, and number of symbols.
     The elements of the matrices are initialized by random values.
     @param lobseq Length of symbols observation sequence
     @param nstates Number of states
     @param nsymbols Number of symbols
     */
    public Hmm(int lobseq, int nstates, int nsymbols) {
        this(lobseq, nstates, nsymbols, true);
    }
    /**
     Construct HMM by specifying its length, number of states, and number of symbols.
     @param lobseq Length of symbols observation sequence
     @param nstates Number of states
     @param nsymbols Number of symbols
     @param bRandomize If true, the elements of the matrices are initialized by random values
     */
    public Hmm(int lobseq, int nstates, int nsymbols, boolean bRandomize) {
        this(lobseq, nstates, nsymbols, nstates-1, nstates-1, bRandomize);
    }
    /**
     Construct HMM by specifying its length, number of states, and number of symbols.
     Upper bound and lower bound along the diagonal direction are also specified, outside which all probability elements are identically zero.
     The elements of the matrices are initialized by random values.
     @param lobseq Length of symbols observation sequence
     @param nstates Number of states
     @param nsymbols Number of symbols
     @param llb Lower bound below which all probability elements are identically zero.
     @param urb Upper bound beyond which all probability elements are identically zero.
     */
    public Hmm(int lobseq, int nstates, int nsymbols, int llb, int urb) {
        this(lobseq, nstates, nsymbols, llb, urb, true);
    }
    /**
     Construct HMM by specifying its length, number of states, and number of symbols.
     Upper bound and lower bound along the diagonal direction are also specified, outside which all probability elements are identically zero.
     @param lobseq Length of symbols observation sequence
     @param nstates Number of states
     @param nsymbols Number of symbols
     @param llb Lower bound below which all probability elements are identically zero.
     @param urb Upper bound beyond which all probability elements are identically zero.
     @param bRandomize If true, the elements of the matrices are initialized by random values
     */
    public Hmm(int lobseq, int nstates, int nsymbols, int llb, int urb, boolean bRandomize) {
        lambda = new Model(nstates, nsymbols, llb, urb, bRandomize);

        len_obseq = lobseq;

        alpha = new double[len_obseq][getNumStates()];
        beta = new double[len_obseq][getNumStates()];
        gamma = new double[len_obseq][getNumStates()];
        ksi = new double[len_obseq-1][getNumStates()][getNumStates()];
        delta = new double[len_obseq][getNumStates()];
        psi = new int[len_obseq][getNumStates()];
        qstar = new int[len_obseq];
        scaleFactor = new double[len_obseq];
        obSeq = new int[len_obseq];

        initializeModel(bRandomize);
    }
    /**
     Construct HMM by loading model parameters from an XML file.
     @param file Name of input file
     */
    public Hmm(String file) throws ParserConfigurationException, SAXException, IOException {
        this(file, 1, true);
    }
    /**
     Construct HMM by loading model parameters from an XML file.
     @param file Name of input file
     @param lobseq Length of observation sequence
     */
    public Hmm(String file, int lobseq) throws ParserConfigurationException, SAXException, IOException {
        this(file, lobseq, true);
    }
    /**
     Construct HMM by loading model parameters from a file.
     @param file Name of input file
     @param bXml If true, read input file in XML format
     */
    public Hmm(String file, boolean bXml) throws ParserConfigurationException, SAXException, IOException {
        this(file, 1, bXml);
    }
    /**
     Construct HMM by loading model parameters from a file.
     @param file Name of input file
     @param lobseq Length of observation sequence
     @param bXml If true, read input file in XML format
     */
    public Hmm(String file, int lobseq, boolean bXml) throws IOException, SAXException, ParserConfigurationException {
        lambda = new Model(file, bXml);
        len_obseq = lobseq;

        alpha = new double[len_obseq][getNumStates()];
        beta = new double[len_obseq][getNumStates()];
        gamma = new double[len_obseq][getNumStates()];
        ksi = new double[len_obseq-1][getNumStates()][getNumStates()];
        delta = new double[len_obseq][getNumStates()];
        psi = new int[len_obseq][getNumStates()];
        qstar = new int[len_obseq];
        scaleFactor = new double[len_obseq];
        obSeq = new int[len_obseq];
    }

    private void computeScaleFactor() {
        for(int t=0;t<len_obseq;t++) {
            computeScaleFactor(t);
        }
    }
    private void computeScaleFactor(int t) {
        scaleFactor[t] = 0.0;
        for(int i=0;i<getNumStates();i++) {
            scaleFactor[t] += alpha[t][i];
        }
    }

    private void rescaleAlpha() {
        rescaleAlpha(true);
    }
    private void rescaleAlpha(boolean bNewScale) {
        if(bNewScale) {
            computeScaleFactor();
        }
        for(int t=0;t<len_obseq;t++) {
            rescaleAlpha(t);
        }
    }
    private void rescaleAlpha(int t) {
        rescaleAlpha(t,true);
    }
    private void rescaleAlpha(int t, boolean bNewScale) {
        if(bNewScale) {
            computeScaleFactor(t);
        }
        for(int i=0;i<getNumStates();i++) {
            alpha[t][i] /= scaleFactor[t];
        }
    }

    private void rescaleBeta() {
        rescaleBeta(false);
    }
    private void rescaleBeta(boolean bNewScale) {
        if(bNewScale) {
            computeScaleFactor();
        }
        for(int t=0;t<len_obseq;t++) {
            rescaleBeta(t);
        }
    }
    private void rescaleBeta(int t) {
        rescaleBeta(t,false);
    }
    private void rescaleBeta(int t, boolean bNewScale) {
        if(bNewScale) {
            computeScaleFactor(t);
        }
        for(int i=0;i<getNumStates();i++) {
            beta[t][i] /= scaleFactor[t];
        }
    }

    private void rescaleTrellis() {
        for(int t=0;t<len_obseq;t++) {
            rescaleTrellis(t);
        }
    }
    private void rescaleTrellis(int t) {
        rescaleAlpha(t,true);
        rescaleBeta(t,false);
    }

    private void clearAlpha() {
        for(int t=0;t<len_obseq;t++) {
            for(int i=0;i<getNumStates();i++) {
                alpha[t][i] = 0.0;
            }
        }
    }
    private void clearBeta() {
        for(int t=0;t<len_obseq;t++) {
            for(int i=0;i<getNumStates();i++) {
                beta[t][i] = 0.0;
            }
        }
    }
    private void clearGamma() {
        for(int t=0;t<len_obseq;t++) {
            for(int i=0;i<getNumStates();i++) {
                gamma[t][i] = 0.0;
            }
        }
    }
    private void clearTrellis() {
        clearAlpha();
        clearBeta();
        clearGamma();
    }

    private double forwardAlpha() {
        clearAlpha();
        for(int j=0;j<getNumStates();j++) {
            alpha[0][j] = lambda.getPi(j) * lambda.getB(j,obSeq[0]);
        }
        rescaleAlpha(0);

        for(int t=1;t<len_obseq;t++) {
            for(int i=0;i<getNumStates();i++) {
                double sum = 0.0;
                for(int j=lambda.getLLimit(i);j<=lambda.getRLimit(i);j++) {
                    sum += alpha[t-1][i] * lambda.getA(i,j);
                }
                alpha[t][i] = sum*lambda.getB(i,obSeq[t]);
            }
            rescaleAlpha(t);
        }
        double sum = 0.0;
        for(int i=0;i<getNumStates();i++) {
            sum += alpha[len_obseq-1][i];
        }
        return sum;
    }

    private void backwardBeta() {
        clearBeta();
        for(int j=0;j<getNumStates();j++) {
            beta[len_obseq-1][j] = 1.0;
        }
        rescaleBeta(len_obseq-1);

        for(int t=len_obseq-2;t>=0;t--) {
            for(int i=0;i<getNumStates();i++) {
                double sum = 0.0;
                for(int j=lambda.getLLimit(i);j<=lambda.getRLimit(i);j++) {
                    sum += beta[t+1][i] * lambda.getA(i,j) * lambda.getB(j,obSeq[t+1]);
                }
                beta[t][i] = sum;
            }
            rescaleBeta(t);
        }
    }

    private void computeGamma() {
        for(int t=0;t<len_obseq;t++) {
            double sum = 0.0;
            for(int i=0;i<getNumStates();i++) {
                gamma[t][i] = alpha[t][i] * beta[t][i];
                sum += gamma[t][i];
            }
            for(int i=0;i<getNumStates();i++) {
                gamma[t][i] /= sum;
            }
        }
    }

    private double computeDelta(int t, int j) {
        if(t==0) {
            psi[t][j] = 0;
            return delta[t][j] = lambda.getPi(j) * lambda.getB(j,obSeq[t]);
        }
        else if(t==len_obseq) {
            double max = 0.0;
            double tmp;
            int indx = 0;
            for(int i=0;i<getNumStates();i++) {
                tmp = computeDelta(t-1,i);
                if(tmp >= max) {
                    max = tmp;
                    indx = i;
                }
            }
            qstar[t-1] = indx;
            for(int s=t-2;s>=0;s--) {
                qstar[s] = psi[s+1][qstar[s+1]];
            }
            return max;
        }
        else {
            double max = 0.0;
            for(int i=0;i<getNumStates();i++) {
                double tmp = computeDelta(t-1,i) * lambda.getA(i,j) * lambda.getB(j,obSeq[t]);
                if(tmp >= max) {
                    max = tmp;
                }
            }
            double amax = 0.0;
            int indx = 0;
            for(int i=0;i<getNumStates();i++) {
                double atmp = computeDelta(t-1,i) * lambda.getA(i,j);
                if(atmp >= amax) {
                    amax = atmp;
                    indx = i;
                }
            }
            psi[t][j] = indx;
            return delta[t][j] = max;
        }
    }

    public double viterbiAlgorithm() {
        return computeDelta(len_obseq,0); // 0 arbitrary
    }

    private void computeKsi() {
        for(int t=0;t<len_obseq-1;t++) {
            double sum = 0.0;
            for(int i=0;i<getNumStates();i++) {
                for(int j=lambda.getLLimit(i);j<=lambda.getRLimit(i);j++) {
                    ksi[t][i][j] = alpha[t][i] * beta[t+1][i] * lambda.getA(i,j) * lambda.getB(j,obSeq[t+1]);
                    sum += ksi[t][i][j];
                }
            }
            for(int i=0;i<getNumStates();i++) {
                for(int j=lambda.getLLimit(i);j<=lambda.getRLimit(i);j++) {
                    ksi[t][i][j] /= sum;
                }
            }
        }
    }

    private double forwardBackwardTrellis() {
        double ret = forwardAlpha();
        backwardBeta();
        computeGamma();
        computeKsi();
        return ret;
    }

    private double sumGamma(int t, int i) {
        double sum = 0.0;
        for(int s=0;s<t;s++) {
            sum += gamma[s][i];
        }
        return sum;
    }
    private double sumGamma(int t, int i, int k) {
        double sum = 0.0;
        for(int s=0;s<t;s++) {
            if(obSeq[s] == k) {
                sum += gamma[s][i];
            }
        }
        return sum;
    }

    private double sumKsi(int t, int i, int j) {
        double sum = 0.0;
        for(int s=0;s<t;s++) {
            sum += ksi[s][i][j];
        }
        return sum;
    }

    private void reestimateLambda() {
        // (1) pi
        for(int i=0;i<getNumStates();i++) {
            lambda.setPi(i,gamma[0][i]);
        }

        // (2) a
        for(int i=0;i<getNumStates();i++) {
            double a_denom = sumGamma(len_obseq-1,i);
            for(int j=lambda.getLLimit(i);j<=lambda.getRLimit(i);j++) {
                double a_numer = sumKsi(len_obseq-1,i,j);
                lambda.setA(i,j,a_numer/a_denom);
            }
        }

        // (3) b
        for(int i=0;i<getNumStates();i++) {
            double b_denom = sumGamma(len_obseq,i);
            for(int k=0;k<getNumSymbols();k++) {
                double b_numer = sumGamma(len_obseq,i,k);
                lambda.setB(i,k,b_numer/b_denom);
            }
        }
    }

    /**
     Retrieves the number of internal states.
     @return  Number of internal states of the underlying model.
     */
    public int getNumStates() {
        return lambda.getNumStates();
    }
    /**
     Retrieves the number of observation symbols.
     @return  Number of observation symbols.
     */
    public int getNumSymbols() {
        return lambda.getNumSymbols();
    }
    /**
     Retrieves the length of the observation sequence.
     @return  Length of the observation sequence.
     */
    public int getLenObSeq() {
        return len_obseq;
    }

    /**
     Load model parameters from an XML file.
     @param file Name of input file
     */
    public void loadHmm(String file) throws ParserConfigurationException, SAXException, IOException {
        loadHmm(file, 1, true);
    }
    /**
     Load model parameters from an XML file.
     @param file Name of input file
     @param bXml If true, read file in XML format
     */
    public void loadHmm(String file, boolean bXml) throws ParserConfigurationException, SAXException, IOException {
        loadHmm(file, 1, bXml);
    }
    /**
     Load model parameters from a file.
     @param file Name of input file
     @param lobseq Length of observation sequence
     */
    public void loadHmm(String file, int lobseq) throws ParserConfigurationException, SAXException, IOException {
        loadHmm(file, lobseq, true);
    }
    /**
     Load model parameters from a file.
     @param file Name of input file
     @param lobseq Length of observation sequence
     @param bXml If true, read file in XML format
     */
    public void loadHmm(String file, int lobseq, boolean bXml) throws IOException, SAXException, ParserConfigurationException {
        if(bXml) {
            loadHmmXml(file, lobseq);
        } else {
            loadHmmAscii(file, lobseq);
        }
    }
    /**
     Load model parameters from an Ascii file.
     Length of observation sequence is set to 1.
     @param file Name of input file
     */
    public void loadHmmAscii(String file) {
        loadHmmAscii(file, 1);
    }
    /**
     Load model parameters from an Ascii file.
     @param file Name of input file
     @param lobseq Length of observation sequence
     */
    public void loadHmmAscii(String file, int lobseq) {
        lambda.loadModelAscii(file);
        len_obseq = lobseq;

        alpha = new double[len_obseq][getNumStates()];
        beta = new double[len_obseq][getNumStates()];
        gamma = new double[len_obseq][getNumStates()];
        ksi = new double[len_obseq-1][getNumStates()][getNumStates()];
        delta = new double[len_obseq][getNumStates()];
        psi = new int[len_obseq][getNumStates()];
        qstar = new int[len_obseq];
        scaleFactor = new double[len_obseq];
        obSeq = new int[len_obseq];
    }
    /**
     Load model parameters from an Xml file.
     Length of observation sequence is set to 1.
     @param file Name of input file
     */
    public void loadHmmXml(String file) throws IOException, SAXException, ParserConfigurationException {
        loadHmmXml(file, 1);
    }
    /**
     Load model parameters from an Xml file.
     @param file Name of input file
     @param lobseq Length of the observation sequence
     */
    public void loadHmmXml(String file, int lobseq) throws ParserConfigurationException, SAXException, IOException {
        lambda.loadModelXml(file);
        len_obseq = lobseq;

        alpha = new double[len_obseq][getNumStates()];
        beta = new double[len_obseq][getNumStates()];
        gamma = new double[len_obseq][getNumStates()];
        ksi = new double[len_obseq-1][getNumStates()][getNumStates()];
        delta = new double[len_obseq][getNumStates()];
        psi = new int[len_obseq][getNumStates()];
        qstar = new int[len_obseq];
        scaleFactor = new double[len_obseq];
        obSeq = new int[len_obseq];
    }

    /**
     Initializes the model with random parameters.
     */
    public void initializeModel() {
        initializeModel(true);
    }
    /**
     Initializes the model.
     @param bRandomize  If true, initialize the parameters with random values
     */
    public void initializeModel(boolean bRandomize) {
        lambda.setProbability(bRandomize);
    }

    /**
     Sets the observation sequence with random vlaues.
     */
    public void setObSeq() {
        setObSeq(true);
    }
    /**
     Sets the observation sequence.
     @param bRandomize  If true, set the sequence with random values
     */
    public void setObSeq(boolean bRandomize) {
        if(bRandomize == true) {
            for(int t=0;t<len_obseq;t++) {
                obSeq[t] = (int) (getNumSymbols() * rand.nextDouble());
            }
        }
        else {
            for(int t=0;t<len_obseq;t++) {
                obSeq[t] = 0;
            }
        }
    }
    /**
     Sets the observation sequence.
     @param seq  Observation sequence to be assigned
     */
    public void setObSeq(int[] seq) {
        for(int t=0;t<len_obseq;t++) {
            obSeq[t] = seq[t];
        }
    }
    /**
     Retrieves the observation symbol at time t.
     @param t  Time at which the observation symbol is to be retrieved
     */
    public int getObSeq(int t) {
        return obSeq[t];
    }
    /**
     Retrieves the observation sequence.
     */
    public int[] getObSeq() {
        return obSeq;
    }

    /**
     Baum-Welch (EM) algorithm.
     Train the HMM until MIN_ERROR is reached.
     It also stops if the iteration reaches MAX_ITERATION.
     @return  Likelihood of the model given the observation sequence
     after training
     */
    public double baumWelchAlgorithm() {
        return baumWelchAlgorithm(MIN_ERROR);
    }
    /**
     Baum-Welch (EM) algorithm.
     Train the HMM until likelihood difference becomes smaller than min_error.
     It also stops if the iteration reaches MAX_ITERATION.
     @param  min_error  Minimum desired error
     @return  Likelihood of the model given the observation sequence
     after training
     */
    public double baumWelchAlgorithm(double min_error) {
        double diff=1.0;
        double p1 = forwardBackwardTrellis();
        double p2 = 1.0;
        for(int n=0;n<MAX_ITERATION;n++) {
            reestimateLambda();
            p2 = forwardBackwardTrellis();
            diff = p2 - p1;
            p1 = p2;
            if(diff <= min_error) {
                break;
            }
        }
        return p1;
    }

    /**
     Generates random observation sequence.
     @return  Randomly generated observation sequence according to the model
     */
    public int[] generateSeq() {
        int[] seq = new int[len_obseq];
        for(int t=0;t<len_obseq;t++) {
            double r = rand.nextDouble();
            double p = 0.0;
            for(int k=0;k<getNumSymbols();k++) {
                double s = 0.0;
                for(int i=0;i<getNumStates();i++) {
                    s += lambda.getB(i,k) * lambda.getPi(t,i);
                }
                p += s;
                if(p > r) {
                    seq[t] = k;
                    break;
                }
            }
        }
        return seq;
    }

    /**
     Three canonical questions of HMM a la Ferguson-Rabiner.
     [1] Given the observation sequence obSeq and a model lambda,
     what is the probability of the given sequence given lambda?
     @return  Probability of the given sequence given lambda
     */
    public double getObSeqProbability() {
        /*
        double logP = Math.log(forwardAlpha());
        for(int t=0;t<len_obseq;t++) {
            logP += Math.log(scaleFactor[t]);
        }
        return logP;
        */
        return forwardAlpha();
    }

    /**
     Three canonical questions of HMM a la Ferguson-Rabiner.
     [2] Given the observation sequence obSeq and a model lambda,
     what is the "optimal" sequence of hidden states?
     @return  Most likely sequence of hidden states
     */
    public int[] getMaxLikelyState() {
        viterbiAlgorithm();
        return qstar;
    }

    /**
     Three canonical questions of HMM a la Ferguson-Rabiner.
     [3] How do we adjust the model parameters lambda
     to maximize the likelihood of the given sequence obSeq?
     @return Locally optimal likelihood value of the given sequence
     */
    public double optimizeLambda() {
        return baumWelchAlgorithm();
    }

    /**
     This function is provided for testing purposes.
     Direct modification of this class is not recommended
     unless there is a bug. (In which case please notify me.)
     Please use inheritance or aggregation (composition).
     */
    public static void main(String[] args) {
        //建立隐马尔可夫模型
        Hmm h = new Hmm(3, 4, 5, 0, 1, false);

        //输出模型信息
        for(int i=0;i<h.getNumStates();i++) {
            for(int j=0;j<h.getNumStates();j++) {
                System.out.print(h.lambda.getA(i,j) + "\t");
            }
            System.out.println();
        }
        System.out.println();
        for(int j=0;j<h.getNumStates();j++) {
            System.out.print(h.lambda.getPi(j) + "\t");
        }
        System.out.println();
        System.out.println();
        for(int i=0;i<h.getNumStates();i++) {
            for(int k=0;k<h.getNumSymbols();k++) {
                System.out.print(h.lambda.getB(i,k) + "\t");
            }
            System.out.println();
        }
        System.out.println();

        for(int z=0;z<100;z++) {
            int[] seq = h.generateSeq();
            for(int t=0;t<seq.length;t++) {
                System.out.print(seq[t] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}
