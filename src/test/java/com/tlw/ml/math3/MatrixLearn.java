package com.tlw.ml.math3;

import org.apache.commons.math3.linear.*;
import org.junit.Test;

public class MatrixLearn {
    @Test
    public void testMatrixMulti(){
        double[][] b = new double[][]{
                {1, 2},
                {3, 4},
        };
        RealMatrix realMatrix = new Array2DRowRealMatrix(b);
        System.out.println(realMatrix);

        RealMatrix multiRealMatrix = realMatrix.multiply(realMatrix);
        System.out.println("矩阵乘: " + multiRealMatrix);
        System.out.println("矩阵转置: " + realMatrix.transpose());
        System.out.println("逆矩阵: " + new LUDecomposition(realMatrix).getSolver().getInverse());
    }

    @Test
    public void testFunction(){
        //矩阵解方程
        // https://www.shuxuele.com/algebra/systems-linear-equations-matrices.html
        double[][] matrix1 = new double[][]{
                {1, 1, 1},
                {0, 2, 5},
                {2, 5, -1}
        };
        double[][] matrix2 = new double[][]{{6}, {-4}, {27}};
        RealMatrix realMatrix1 = MatrixUtils.createRealMatrix(matrix1);
        RealMatrix realMatrix2 = MatrixUtils.createRealMatrix(matrix2);
        DecompositionSolver solver = new LUDecomposition(realMatrix1).getSolver();
        RealMatrix realMatrix3 = solver.getInverse();
        RealMatrix realMatrix4 = realMatrix3.multiply(realMatrix2);
        System.out.println(realMatrix1);
        System.out.println(realMatrix2);
        System.out.println(realMatrix3);
        System.out.println(realMatrix4);
        System.out.println(realMatrix1.multiply(realMatrix4));
    }
}
