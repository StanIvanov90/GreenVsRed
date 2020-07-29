package com.unkn;

public class Field {
    private int width;
    private int height;
    private long[][] board;

    public Field(int height, int width) {
        this.width = width;
        this.height = height;
        this.board = new long[width][height];
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long[][] getBoard() {
        return board;
    }

    public void setBoard(long[][] board) {
        this.board = board;
    }
}
