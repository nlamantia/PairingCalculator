package com.chase.sep.columbus.mentoring.hungarian;

import java.util.*;

public class PreferencesMatrix {

    private final Set<Integer> coveredRows = new HashSet<>();
    private final Set<Integer> coveredColumns = new HashSet<>();

    private final int[][] matrix;

    public PreferencesMatrix(int[][] matrix) {
        if (matrix == null) {
            throw new IllegalStateException("Preferences matrix cannot be null");
        }

        this.matrix = matrix;
    }

    public int[][] getOptimalAssignments() {
        this.optimize();
        this.uncover();

        int[][] assignments = new int[this.matrix.length][2];

        int assignmentCounter = 0;

        while (assignmentCounter < this.matrix.length) {
            int[][] zeroes = countUncoveredZeroes();
            int[] rowZeroes = zeroes[0];
            int[] colZeroes = zeroes[1];

            int minZeroRow = minNonZeroPos(rowZeroes);
            int minZeroCol = minNonZeroPos(colZeroes);

            int x = -1, y = -1;
            if (rowZeroes[minZeroRow] < colZeroes[minZeroCol]) {
                // find the first zero cell in that row
                x = minZeroRow;
                for (int i = 0; i < matrix.length; i++) {
                    if (matrix[minZeroRow][i] == 0 && !this.isCovered(minZeroRow, i)) {
                        y = i;
                        break;
                    }
                }
            } else {
                // find the first zero cell in that column
                y = minZeroCol;
                for (int i = 0; i < matrix.length; i++) {
                    if (matrix[i][minZeroCol] == 0 && !this.isCovered(i, minZeroCol)) {
                        x = i;
                        break;
                    }
                }
            }

            this.coveredRows.add(x);
            this.coveredColumns.add(y);

            assignments[assignmentCounter] = new int[2];
            assignments[assignmentCounter][0] = y;
            assignments[assignmentCounter][1] = x;
            assignmentCounter++;
        }

        return assignments;
    }

    private void optimize() {
        // step 1: subtract row minima
        for (int i = 0; i < this.matrix.length; i++) {
            subtractFromRow(i, rowMin(i));
        }

        // step 2: subtract column minima
        for (int i = 0; i < this.matrix.length; i++) {
            subtractFromColumn(i, columnMin(i));
        }

        // initial cover
        cover();

        // create more zeroes (if necessary) and cover again
        while ((coveredColumns.size() + coveredRows.size()) != this.matrix.length) {
            int minUncovered = uncoveredMin();
            for (int row = 0; row < matrix.length; row++) {
                for (int col = 0; col < matrix.length; col++) {
                    if (!isCovered(row, col)) {
                        matrix[row][col] -= minUncovered;
                    } else if (isDoubleCovered(row, col)) {
                        matrix[row][col] += minUncovered;
                    }
                }
            }

            cover();
        }
    }

    private void subtractFromRow(int rowNum, int value) {
        int[] row = matrix[rowNum];
        for (int i = 0; i < row.length; i++) {
            row[i] -= value;
        }
    }

    private void subtractFromColumn(int colNum, int value) {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][colNum] -= value;
        }
    }

    private int rowMin(int rowNum) {
        int[] row = matrix[rowNum];
        int min = Integer.MAX_VALUE;
        for (int value : row) {
            min = Math.min(min, value);
        }
        return min;
    }

    private int columnMin(int col) {
        int min = Integer.MAX_VALUE;
        for (int[] row : matrix) {
            min = Math.min(min, row[col]);
        }
        return min;
    }

    private int uncoveredMin() {
        int min = matrix.length + 1;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (!this.isCovered(row, col)) {
                    min = Math.min(min, matrix[row][col]);
                }
            }
        }
        return min;
    }

    private boolean isCovered(int rowNum, int colNum) {
        boolean isCoveredInRow = coveredRows.contains(rowNum);
        boolean isCoveredInColumn = coveredColumns.contains(colNum);
        return isCoveredInRow || isCoveredInColumn;
    }

    private boolean isDoubleCovered(int rowNum, int colNum) {
        boolean isCoveredInRow = coveredRows.contains(rowNum);
        boolean isCoveredInColumn = coveredColumns.contains(colNum);
        return isCoveredInRow && isCoveredInColumn;
    }

    private void cover() {
        this.uncover();

        while (uncoveredZeroExists()) {
            int[][] zeroes = countUncoveredZeroes();
            int[] rowZeroes = zeroes[0];
            int[] colZeroes = zeroes[1];

            int maxZeroRow = maxPos(rowZeroes);
            int maxZeroCol = maxPos(colZeroes);

            if (rowZeroes[maxZeroRow] > colZeroes[maxZeroCol]) {
                this.coveredRows.add(maxZeroRow);
            } else {
                this.coveredColumns.add(maxZeroCol);
            }
        }
    }

    private int[][] countUncoveredZeroes() {
        int[] rowZeroes = new int[matrix.length];
        Arrays.fill(rowZeroes, 0);

        int[] colZeroes = new int[matrix.length];
        Arrays.fill(colZeroes, 0);

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                if (matrix[row][col] == 0 && !this.isCovered(row, col)) {
                    rowZeroes[row]++;
                    colZeroes[col]++;
                }
            }
        }

        return new int[][] { rowZeroes, colZeroes };
    }

    /**
     * Returns the position of the max of a given integer array
     *
     * @param arr - a non-null integer array
     * @return - the POSITION of the maximum in {@code arr}
     */
    private int maxPos(int[] arr) {
        int maxValue = Integer.MIN_VALUE, maxPos = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > maxValue) {
                maxValue = arr[i];
                maxPos = i;
            }
        }
        return maxPos;
    }

    /**
     * Returns the position of the min of a given integer array
     *
     * @param arr - a non-null integer array
     * @return - the POSITION of the minimum in {@code arr}
     */
    private int minNonZeroPos(int[] arr) {
        int minValue = Integer.MAX_VALUE, minPos = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < minValue && arr[i] != 0) {
                minValue = arr[i];
                minPos = i;
            }
        }
        return minPos;
    }

    private boolean uncoveredZeroExists() {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                if (matrix[row][col] == 0 && !this.isCovered(row, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void uncover() {
        coveredRows.clear();
        coveredColumns.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[\n");
        for (int[] row : matrix) {
            sb.append(" [");
            for (int i = 0; i < row.length; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(row[i]);
            }
            sb.append("]\n");
        }
        sb.append("]\n");
        return sb.toString();
    }
}
