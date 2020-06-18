package processor;

import java.util.Scanner;

public class Main {

    public static void menu() {
        System.out.println("\nChoose an option:");
        System.out.println("\t1. Add matrices");
        System.out.println("\t2. Multiply matrix to a constant");
        System.out.println("\t3. Multiply matrices");
        System.out.println("\t4. Transpose matrix");
        System.out.println("\t5. Calculate a determinant");
        System.out.println("\t6. Inverse Matrix");
        System.out.println("\t0. Exit");

    }

    public static void transposeMenu() {
        System.out.println("\nChoose for transpose:");
        System.out.println("\t1. Main Diagonal");
        System.out.println("\t2. Side Diagonal");
        System.out.println("\t3. Vertical Line");
        System.out.println("\t4. Horizontal Line");

    }

    public static Matrix processMatrix(boolean first) {
        Matrix matrix;
        if (first) {
            System.out.print("Enter the size of the matrix: ");
            matrix = new Matrix();
            System.out.println("Enter matrix:");
        } else {
            System.out.print("Enter the size of second matrix: ");
            matrix = new Matrix();
            System.out.println("Enter second matrix:");
        }
        matrix.readMatrix();

        return matrix;
    }

    public static void printResult(double[][] matrix) {
        System.out.println("The result is:");
        for (double[] i : matrix) {
            for (double j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        menu();
        int command = sc.nextInt();
        int transposeType = 0;
        System.out.println("Your choice: " + command);
        if (command == 4) {
            transposeMenu();
            transposeType = sc.nextInt();
            System.out.println("Your choice: " + transposeType);
        }

        while (command != 0) {
            Matrix matrixA;
            switch (command) {
                case 1:     // add matrices
                    matrixA = processMatrix(true);
                    Matrix matrixB = processMatrix(false);
                    double[][] sum = matrixA.add(matrixB);
                    if (sum == null) {
                        System.out.println("ERROR");
                    } else {
                        printResult(sum);
                    }
                    break;
                case 2:     // multiply by constant
                    matrixA = processMatrix(true);
                    System.out.print("Enter constant: ");
                    double constant = sc.nextDouble();
                    double[][] scaled = matrixA.multiplyByConstant(constant);
                    printResult(scaled);
                    break;
                case 3:
                    matrixA = processMatrix(true);
                    Matrix matrixC = processMatrix(false);
                    double[][] product = matrixA.multiplyMatrices(matrixC);
                    if (product == null) {
                        System.out.println("ERROR");
                    } else {
                        printResult(product);
                    }
                    break;
                case 4:
                    matrixA = processMatrix(true);
                    double[][] transposed = matrixA.transposeMatrix(transposeType);
                    printResult(transposed);
                    break;
                case 5:
                    matrixA = processMatrix(true);
                    matrixA.calculateDeterminant();
                    break;

                case 6:
                    matrixA = processMatrix(true);
                    double[][] inverse = matrixA.findInverse();
                    System.out.println("The result is:");
                    for (double[] i : inverse) {
                        for (double j : i) {
                            System.out.print(j + " ");
                        }
                        System.out.println();
                    }
                    break;
            }

            menu();
            command = sc.nextInt();
            System.out.println("Your choice: " + command);
            if (command == 4) {
                transposeMenu();
                transposeType = sc.nextInt();
                System.out.println("Your choice: " + transposeType);
            }
        }
        sc.close();
        System.exit(0);
    }
}
