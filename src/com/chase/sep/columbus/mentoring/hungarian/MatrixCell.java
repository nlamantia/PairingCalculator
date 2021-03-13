package com.chase.sep.columbus.mentoring.hungarian;

public class MatrixCell {

    private final int x;
    private final int y;
    private final int value;

    public MatrixCell(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }
}
