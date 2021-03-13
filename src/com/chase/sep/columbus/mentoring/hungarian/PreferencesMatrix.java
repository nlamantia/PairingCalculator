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

    public void optimize() {
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

    public void subtractFromRow(int rowNum, int value) {
        int[] row = matrix[rowNum];
        for (int i = 0; i < row.length; i++) {
            row[i] -= value;
        }
    }

    public void subtractFromColumn(int colNum, int value) {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][colNum] -= value;
        }
    }

    public int rowMin(int rowNum) {
        int[] row = matrix[rowNum];
        int min = Integer.MAX_VALUE;
        for (int value : row) {
            min = Math.min(min, value);
        }
        return min;
    }

    public int columnMin(int col) {
        int min = Integer.MAX_VALUE;
        for (int[] row : matrix) {
            min = Math.min(min, row[col]);
        }
        return min;
    }

    public int uncoveredMin() {
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

    public boolean isCovered(int rowNum, int colNum) {
        boolean isCoveredInRow = coveredRows.contains(rowNum);
        boolean isCoveredInColumn = coveredColumns.contains(colNum);
        return isCoveredInRow || isCoveredInColumn;
    }

    public boolean isDoubleCovered(int rowNum, int colNum) {
        boolean isCoveredInRow = coveredRows.contains(rowNum);
        boolean isCoveredInColumn = coveredColumns.contains(colNum);
        return isCoveredInRow && isCoveredInColumn;
    }

    public void cover() {
        this.uncover();

        int maxZeroRow = -1;
        int maxZeroCol = -1;

        int[] rowZeroes = new int[matrix.length];
        Arrays.fill(rowZeroes, 0);

        int[] colZeroes = new int[matrix.length];
        Arrays.fill(colZeroes, 0);

        while (uncoveredZeroExists()) {
            for (int row = 0; row < matrix.length; row++) {
                for (int col = 0; col < matrix.length; col++) {
                    if (matrix[row][col] == 0 && !this.isCovered(row, col)) {
                        rowZeroes[row]++;
                        if (maxZeroRow == -1 || rowZeroes[row] > rowZeroes[maxZeroRow]) {
                            maxZeroRow = row;
                        }

                        colZeroes[col]++;
                        if (maxZeroCol == -1 || colZeroes[col] > colZeroes[maxZeroCol]) {
                            maxZeroCol = col;
                        }
                    }
                }
            }

            if (rowZeroes[maxZeroRow] > colZeroes[maxZeroCol]) {
                this.coveredRows.add(maxZeroRow);
            } else {
                this.coveredColumns.add(maxZeroCol);
            }

            // reset
            Arrays.fill(rowZeroes, 0);
            Arrays.fill(colZeroes, 0);
            maxZeroRow = -1;
            maxZeroCol = -1;
        }
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

    public void uncover() {
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
