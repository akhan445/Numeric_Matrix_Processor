package processor;

import java.util.Scanner;

public class Matrix {
    private Scanner scanner = new Scanner(System.in);
    private int rows;
    private int cols;
    private double[][] matrix;

    public Matrix() {
        this.rows = scanner.nextInt();
        this.cols = scanner.nextInt();

        matrix = new double[rows][cols];
    }

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        matrix = new double[rows][cols];
    }

    public void readMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    private boolean checkMatrices(Matrix matrixB, int operation) {

        boolean validOp = false;

        if (operation == 1) { //addition
            validOp = this.getRows() == matrixB.getRows() && this.getCols() == matrixB.getCols();
        } else if (operation == 3) { //matrix multiplication
            validOp = this.getCols() == matrixB.getRows();
        }

        return validOp;
    }

    public double[][] add(Matrix matrixB) {

        boolean validOp = checkMatrices(matrixB, 1);

        double[][] sum = new double[this.rows][this.cols];

        if (validOp) {
            System.out.println("The addition result is:");
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.cols; j++) {
                    sum[i][j] =  this.matrix[i][j] + matrixB.matrix[i][j];
                }
            }
        } else {
            return null;
        }
        return sum;
    }

    public double[][] multiplyByConstant(double constant) {

        double[][] scaled = new double[this.rows][this.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                scaled[i][j] = this.matrix[i][j] * constant;
            }
        }

        return scaled;
    }

    public double[][] multiplyMatrices(Matrix matrixB) {
        boolean validOp = checkMatrices(matrixB, 3);
        double[][] product = new double[this.rows][matrixB.cols];

        if (validOp) {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < matrixB.cols; j++) {
                    for (int k = 0; k < this.cols; k++) {
                        product[i][j] += this.matrix[i][k] * matrixB.matrix[k][j];
                    }
                }
            }
        } else {
            return null;
        }

        return product;

    }

    public double[][] transposeMatrix(int transposeType) {
        double[][] transposed = new double[this.rows][this.cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                switch (transposeType) {
                    case 1:     // main diagonal
                        transposed[i][j] = this.matrix[j][i];
                        break;
                    case 2:     // side diagonal
                        double temp = this.matrix[i][j];
                        transposed[i][j] = this.matrix[this.rows - 1 - j][this.rows - 1 - i];
                        transposed[this.rows - 1 - j][this.rows - 1 - i] = temp;
                        break;
                    case 3:     // vertical line
                        transposed[i][j] = this.matrix[i][this.cols - 1 - j];
                        break;
                    case 4:     // horizontal line
                        transposed[i][j] = this.matrix[this.rows - 1 - i][j];
                        break;
                }
            }
        }
        return transposed;
    }

    public void calculateDeterminant() {

        if (this.rows == this.cols && this.rows >= 2) {
            double det = det(this.matrix);
            System.out.println("The result is:");
            System.out.println(det);
        } else {
            System.out.println("ERROR");
        }
    }

    private double det(double[][] matrix) {

        double determinant = 0;
        if (matrix.length == 2) {
            return matrix[0][0] * matrix[1][1] -
                    matrix[0][1] * matrix[1][0];
        } else {
            for (int firstRow = 0; firstRow < matrix[0].length; firstRow++) {
                double a = matrix[0][firstRow];
                double[][] c = new double[matrix.length - 1][matrix[0].length - 1];

                for (int row = 1; row < matrix.length; row++) {
                    for (int col = 0; col < matrix[row].length; col++) {

                        if (col < firstRow) {
                            c[row - 1][col] = matrix[row][col];
                        } else if (col > firstRow) {
                            c[row - 1][col - 1] = matrix[row][col];
                        }
                    }
                }
                determinant += a * Math.pow(-1, 2 + firstRow) * det(c);
            }
        }
        return determinant;
    }

    public double[][] findInverse() {

        double determinant = det(this.matrix);

        Matrix cfMatrix = new Matrix(this.rows, this.cols);

        cfMatrix.matrix = getCofactorMatrix();

        Matrix cfTranspose = new Matrix(this.rows, this.cols);

        cfTranspose.matrix = cfMatrix.transposeMatrix(1);

        Matrix inverse = new Matrix(this.rows, this.cols);

        inverse.matrix = cfTranspose.multiplyByConstant(1 / determinant);

        return inverse.matrix;
    }

    private double[][] getCofactorMatrix() {

        double[][] cfMatrix = new double[this.rows][this.cols];

        for (int i = 0; i < cfMatrix.length; i++) {
            for (int j = 0; j < cfMatrix[i].length; j++) {

                double[][] submatrix = new double[this.matrix.length - 1][this.matrix[0].length - 1];

                int a = 0, b = 0;
                for (int k = 0; k < this.rows; k++) {
                    if (k == i) continue;
                    for (int l = 0; l < this.cols; l++) {
                        if (l == j) continue;
                        submatrix[b][a] = this.matrix[k][l];

                        a = (a + 1) % (this.rows - 1);
                        if (a == 0) b++;
                    }
                }

                double subDet = det(submatrix);
                double cofactor = Math.pow(-1, 2 + i + j) * subDet;
                cfMatrix[i][j] = cofactor;

            }
        }

        return cfMatrix;
    }

}
