package com.example;

public final class Tower {

    public Tower(int num, boolean occupied) {
        this.cell = num;
        this.occupied = occupied;
    }

    /**
     * Moves the specified worker to the row and col position
     *
     * @param row
     * @param col
     * @param worker
     */
    public buildTower(int row, int col, Worker worker) {
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

    /**
     * Checks if the cell has another worker in it
     *
     * @return occupancy
     */
    public boolean hasDome { return this.hasDome; }
}