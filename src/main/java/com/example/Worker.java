package com.example;

public class Worker {
    private int row;
    private int col;
    private int height;

    public Worker(int row, int col, int height) {
        this.row = row;
        this.col = col;
        this.height = height;
    }

    public int getRow() { return this.row; }

    public int getCol() { return this.col; }

    public int getHeight() { return this.height; }

    /**
     * Moves the specified worker to the row and col position
     *
     * @param row the row we want to move the worker to
     * @param col the col we want to move the worker to
     * @param board the game board
     */
    public void moveWorker(int row, int col, Cell[][] board) {
        int originalRow = this.row;
        int originalCol = this.col;
        Cell originalCell = board[originalRow][originalCol];
        originalCell.setUnoccupied();
        this.row = row;
        this.col = col;
        this.height = board[row][col].getLevels();
        board[row][col].setOccupied();
    }

    /**
     * Places a tower at the specified location on the board
     *
     * @param row the row we want to place our tower on
     * @param col thw col we want to place our tower on
     * @param board the game board
     */
    public void placeTower(int row, int col, Cell[][] board) {
        Cell cell = board[row][col];
        cell.addLevel();
    }

}
