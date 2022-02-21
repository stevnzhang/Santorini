package com.example;

import cell

public final class Board {
    private numRows;
    private numCols;
    private Cell[][] grid;

    public Board(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.grid = new Cell[][];
    }

    public placeTower(int row, int col) {
        Cell position = this.grid[row][col];
        if (row < 0 || row > numRows - 1 ||
            col < 0 || col > numCols - 1 ||
            position.getHeight < 0 || position.getHeight > 3 ||
            position.occupancy) {
            System.out.println("Illegal location! Please pick a different location.");
            System.exit(-1);
        } else {
            Cell res = new Cell(this.grid(position.getHeight + 1)) // NOT SURE ABOUT THIS, HOW TO JUST ADD TO row, col?
            this.grid[row][col] = res;
        }
    }
}