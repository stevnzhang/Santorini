//package com.example;
//
//public class Board {
//    private int numRows;
//    private int numCols;
//    private Cell[][] board;
//
//    public Board(int numRows, int numCols) {
//        this.numRows = numRows;
//        this.numCols = numCols;
//        this.board = new Cell[numRows][numCols];
//        for (int row = 0; row < this.numRows; row++) {
//            for (int col = 0; col < this.numCols; col++) {
//                this.board[row][col] = new Cell(0, false);
//            }
//        }
//    }
//
//    public Cell getCell(int row, int col) { return this.board[row][col]; }
//
//    public int getNumRows() { return this.numRows; }
//
//    public int getNumCols() { return this.numCols; }
//
//    public void printBoard() {
//        for (int row = 0; row < this.numRows; row++) {
//            for (int col = 0; col < this.numCols; col++) {
//                System.out.println("[" + board[row][col].getLevels() + "]");
//            }
//        }
//    }
//
//    /**
//     * Places a tower at the specified location on the board
//     *
//     * @param row
//     * @param col
//     */
//    public void placeTower(int row, int col) {
//        Cell cell = this.board[row][col];
//        cell.addLevel();
//    }
//}
