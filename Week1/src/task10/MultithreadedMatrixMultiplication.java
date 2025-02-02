package task10;

import java.util.Arrays;

public class MultithreadedMatrixMultiplication {
    public static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        if (colsA != matrixB.length) {
            throw new IllegalArgumentException("Matrix multiplication not possible: Invalid dimensions.");
        }

        int[][] result = new int[rowsA][colsB];
        Thread[][] threads = new Thread[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                threads[i][j] = new Thread(new MatrixMul(matrixA, matrixB, result, i, j));
                threads[i][j].start();
            }
        }

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                try {
                    threads[i][j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void main(String[] args) {
        int[][] matrixA = {{4, 2}, {1, 4}};
        int[][] matrixB = {{5, 0}, {2, 2}};

        int[][] result = multiplyMatrices(matrixA, matrixB);

        System.out.println("Result of the multiplication:");
        printMatrix(result);
    }
}