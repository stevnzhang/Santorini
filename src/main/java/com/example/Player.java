package com.example;

public class Player {
    private Worker worker1;
    private Worker worker2;

    public Player() {}

    public Worker getWorker1() { return this.worker1; }

    public Worker getWorker2() { return this.worker2; }

    /**
     * Places a tower at the specified location on the board
     *
     * @param row1 worker1's row
     * @param col1 worker1's col
     * @param row2 worker2's row
     * @param col2 worker2's col
     * @param board the game board
     */
    public void placeWorker(int row1, int col1, int row2, int col2, Cell[][] board) {
        worker1 = new Worker(row1, col1, 0);
        board[row1][col1].setOccupied();
        worker2 = new Worker(row2, col2, 0);
        board[row2][col2].setOccupied();
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
