package com.example;

public final class Worker {
    private int row;
    private int col;
    private int height;

    public Worker(int row, int col, int height) {
        this.row = row;
        this.col = col;
        this.height = height;
    }

    /**
     * Moves the specified worker to the row and col position
     *
     * @param row
     * @param col
     * @param worker
     */
    public moveWorker(int row, int col, Worker worker) {
        if (row < 0 || row > numRows - 1 ||
            col < 0 || col > numCols - 1 ||
            checkLegal(row, col)) {
            System.out.println("Illegal move! Please pick a different location.");
        } else {
            this.row = row;
            this.col = col;
            this.height = (board[row][col]).getHeight();
        }
    }

    public int getRow { return this.row }

    public int getCol { return this.row }

    public int getHeight { return this.row }

}