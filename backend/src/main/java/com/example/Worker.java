package com.example;

public class Worker {
    private int row;
    private int col;
    private int height;
    private int prevHeight;
    private boolean forced;

    public Worker(int row, int col, int height) {
        this.row = row;
        this.col = col;
        this.height = height;
    }

    public int getRow() { return this.row; }

    public void setRow(int row) { this.row = row; }

    public int getCol() { return this.col; }

    public void setCol(int col) { this.col = col; }

    public int getHeight() { return this.height; }

    public void setHeight(int height) { this.height = height;}

    public boolean isForced() { return this.forced; }

    public void setForced(boolean val) { this.forced = val; }

    public int getPrevHeight() { return this.prevHeight; }

    public void setPrevHeight(int height) { this.prevHeight = height; }

}
