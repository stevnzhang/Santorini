package com.example;

public class Game {
    private int turn;
    private int currentPlayer;
    private boolean gameOver;
    private int numRows;
    private int numCols;
    private Cell[][] board;
    private Player player1;
    private Player player2;
    private Player winner;
    private Worker currentWorker;

    private static final int WIN_HEIGHT = 3;

    public Game(int numRows, int numCols) {
        this.turn = 0;
        this.currentPlayer = 0;
        this.numRows = numRows;
        this.numCols = numCols;
        this.board = new Cell[numRows][numCols];
    }

    public int getTurn() { return this.turn; }

    public int getCurrentPlayer() { return this.currentPlayer; }

    public Player getWinner() { return this.winner; }

    public boolean getGameOver() { return this.gameOver; }

    /**
     * Checks if the row and col is a legal move on the board.
     *
     * @param row the row to check.
     * @param col the col to check.
     */
    public void basicLegalChecks(int row, int col) {
        Cell cell = this.board[row][col];
        if (row < 0 || row > numRows - 1 ||
            col < 0 || col > numCols - 1) {
            System.out.println("Out of bounds!");
            System.exit(-1);
        } else if (cell.occupancy()) {
            System.out.println("Location has a worker on it!");
            System.exit(-1);
        } else if (cell.hasDome()) {
            System.out.println("Location has a dome on it!");
            System.exit(-1);
        }
    }

    /**
     * Checks if the row and col is a legal move for the worker.
     *
     * @param row the row to check.
     * @param col the col to check.
     * @param worker the worker we want to move.
     * @return {@code true} if row, col is a legal move for the worker
     */
    public boolean checkLegalMove(int row, int col, Worker worker) {
        int originalRow = worker.getRow();
        int originalCol = worker.getCol();
        int originalHeight = worker.getHeight();
        Cell originalCell = this.board[originalRow][originalCol];
        Cell cell = this.board[row][col];
        basicLegalChecks(row, col); // Will throw an error if not legal row, col
        if (-1 <= originalRow - row && originalRow - row <= 1 &&
                   -1 <= originalCol - col && originalRow - row <= 1) {
            System.out.println("Move has to be adjacent from current worker!");
            System.exit(-1);
        } else if (originalHeight != cell.getLevels() ||
                originalHeight + 1 != cell.getLevels()) {
            System.out.println("Height of the location is too high!");
            System.exit(-1);
        }
        return true; // If the system doesn't exit, it passes all checks & it is a legal move
    }

    /**
     * Checks if the row and col is a legal place for the tower.
     *
     * @param row the row to check.
     * @param col the col to check.
     * @param worker the worker we recently moved.
     * @return {@code true} if row, col is a legal place for a tower
     */
    public boolean checkLegalPlacement(int row, int col, Worker worker) {
        int originalRow = worker.getRow();
        int originalCol = worker.getCol();
        basicLegalChecks(row, col); // Will throw an error if not legal row, col
        if (-1 <= originalRow - row && originalRow - row <= 1 &&
            -1 <= originalCol - col && originalRow - row <= 1) {
            System.out.println("Have to build adjacent from current worker!");
            System.exit(-1);
        }
        return true;
    }

    /**
     * Creates the initial workers for the player.
     *
     * @param row1 worker1's row.
     * @param col1 worker1's col.
     * @param row2 worker2's row.
     * @param col2 worker2's col.
     */
    public void initiatePlayer(int row1, int col1, int row2, int col2) {
        basicLegalChecks(row1, col1);
        basicLegalChecks(row2, col2);
        if (currentPlayer == 0) { player1.placeWorker(row1, col1, row2, col2, this.board); }
        else if (currentPlayer == 1) { player2.placeWorker(row1, col1, row2, col2, this.board); }
    }

    /**
     * Checks if the cell already has a worker in it.
     *
     * @param row the row we want to move the worker to.
     * @param col the col we want to move the worker to.
     * @param worker the worker we want to move.
     */
    public void initiateMove(int row, int col, Worker worker) {
        if (checkLegalMove(row, col, worker)) {
            worker.moveWorker(row, col, worker, this.board);
            this.currentWorker = worker;
        }
    }

    /**
     * Checks if the cell already has a worker in it.
     *
     * @param row the row we want to place our tower on.
     * @param col the col we want to place our tower on.
     * @param worker the worker that recently moved.
     * @param player the current player.
     */
    public void initiateTower(int row, int col, Worker worker, Player player) {
        if (this.currentWorker != worker) {
            System.out.println("Have to place tower adjacent to recently moved worker!");
            System.exit(-1);
        } if (checkLegalPlacement(row, col, worker)) {
            player.placeTower(row, col, this.board);
            this.turn += 1;
            this.currentPlayer = this.turn % 2;
        }
    }

    /**
     * Checks if the game is over.
     */
    public void gameOver() {
        if (player1.getWorker1().getHeight() == WIN_HEIGHT ||
            player1.getWorker2().getHeight() == WIN_HEIGHT) {
            this.gameOver = true;
            this.winner = player1;
        } else if (player2.getWorker1().getHeight() == WIN_HEIGHT ||
                   player2.getWorker2().getHeight() == WIN_HEIGHT) {
            this.gameOver = true;
            this.winner = player2;
        }
        this.gameOver = false;
    }

    // system sequence doesn't need to match implementation (human player vs player class)
    // object model has to match the implementation
}
