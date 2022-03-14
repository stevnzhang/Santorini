package com.example;

public class Game {
    private int currentPlayer;
    private boolean gameOver;
    private final int numRows;
    private final int numCols;
    private Cell[][] board;
    private Player player1;
    private Player player2;
    private Player winner;
    private Worker currentWorker;

    private static final int WIN_HEIGHT = 3;

    public Game(int numRows, int numCols) {
        this.currentPlayer = 0;
        this.numRows = numRows;
        this.numCols = numCols;

        this.board = new Cell[numRows][numCols];
        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                this.board[row][col] = new Cell(0, false);
            }
        }
    }

    public int getNumRows() { return this.numRows; }

    public int getNumCols() { return this.numCols; }

    public Player getPlayer1() { return this.player1; }

    public Player getPlayer2() { return this.player2; }

    public void setPlayer1(Player player) { this.player1 = player; }

    public void setPlayer2(Player player) { this.player2 = player; }

    public int getCurrentPlayer() { return this.currentPlayer; }

    public Cell[][] getBoard() { return this.board; }

    public Player getWinner() { return this.winner; }

    public void setWinner(Player player) { this.winner = player; } // Not for client-use

    public boolean getGameOver() { return this.gameOver; }

    public void setGameOver(boolean val) { this.gameOver = val; } // Not for client-use

    /**
     * Checks if the row and col is a legal move on the board.
     *
     * @param row the row to check.
     * @param col the col to check.
     */
    public void basicLegalChecks(int row, int col) throws InvalidMoveException {
        if (row < 0 || row > numRows - 1 ||
            col < 0 || col > numCols - 1) {
            throw new InvalidMoveException("Out of bounds!");
        }
        Cell cell = this.board[row][col];
        if (cell.occupancy()) {
            throw new InvalidMoveException("Location has a worker on it!");
        } else if (cell.hasDome()) {
            throw new InvalidMoveException("Location has a dome on it!");
        }
    }

    /**
     * Checks if the row and col is a legal move for the worker.
     *
     * @param row the row to check.
     * @param col the col to check.
     * @param worker the worker we want to move.
     * @return {@code true} if row, col is a legal move for the worker.
     */
    public boolean checkLegalMove(int row, int col, Worker worker) throws InvalidMoveException {
        basicLegalChecks(row, col); // Will throw an error if not legal row, col
        int originalRow = worker.getRow();
        int originalCol = worker.getCol();
        int originalHeight = worker.getHeight();
        Cell cell = this.board[row][col];
        if (-1 > originalRow - row || originalRow - row > 1 ||
            -1 > originalCol - col || originalCol - col > 1) {
            throw new InvalidMoveException("Move has to be adjacent from current worker!");
        } else if (originalHeight < cell.getLevels() && cell.getLevels() - originalHeight > 1) {
            throw new InvalidMoveException("Height of the location is too high!");
        }
        return true; // If the system doesn't exit, it passes all checks & it is a legal move
    }

    /**
     * Checks if the row and col is a legal place for the tower.
     *
     * @param row the row to check.
     * @param col the col to check.
     * @param worker the worker we recently moved.
     * @return {@code true} if row, col is a legal place for a tower.
     */
    public boolean checkLegalPlacement(int row, int col, Worker worker) throws InvalidMoveException {
        int originalRow = worker.getRow();
        int originalCol = worker.getCol();
        basicLegalChecks(row, col); // Will throw an error if not legal row, col
        if (-1 > originalRow - row || originalRow - row > 1 ||
            -1 > originalCol - col || originalCol - col > 1) {
            throw new InvalidMoveException("Build has to be adjacent from current worker!");
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
     * @param player the player we want to initiate.
     */
    public void initiatePlayer(int row1, int col1, int row2, int col2, Player player) throws InvalidMoveException {
        basicLegalChecks(row1, col1);
        basicLegalChecks(row2, col2);
        if (row1 == row2 && col1 == col2) {
            throw new InvalidMoveException("Cannot initialize both workers in the same location!");
        }
        if (player == player1) { player1.placeWorker(row1, col1, row2, col2, this.board); }
        else if (player == player2) { player2.placeWorker(row1, col1, row2, col2, this.board); }
    }

    /**
     * Checks if the cell already has a worker in it.
     *
     * @param row the row we want to move the worker to.
     * @param col the col we want to move the worker to.
     * @param worker the worker we want to move.
     * @param player the current player.
     */
    public void initiateMove(int row, int col, Worker worker, Player player) throws InvalidMoveException, GameOverException, InvalidTurnException {
        if (this.gameOver) { throw new GameOverException("Game is over!"); }
        Player currentPlayer = new Player();
        if (this.currentPlayer == 0) { currentPlayer = player1; }
        else if (this.currentPlayer == 1) { currentPlayer = player2; }
        if (currentPlayer != player) { throw new InvalidTurnException("It's not your turn!"); }
        if (checkLegalMove(row, col, worker)) {
            if (currentPlayer.getWorker1() == worker || currentPlayer.getWorker2() == worker) {
                worker.moveWorker(row, col, this.board);
                this.currentWorker = worker;
            }
        }
    }

    /**
     * Checks if the cell already has a worker in it.
     *
     * @param row the row we want to place our tower on.
     * @param col the col we want to place our tower on.
     * @param worker the worker that recently moved.
     */
    public void initiateTower(int row, int col, Worker worker) throws InvalidMoveException, GameOverException {
        if (this.gameOver) throw new GameOverException("Game is over!");
        basicLegalChecks(row, col);
        if (this.currentWorker != worker) {
            throw new InvalidMoveException("Build has to be adjacent to recently moved worker!");
        } if (checkLegalPlacement(row, col, worker)) {
            if (this.currentPlayer == 0) { worker.placeTower(row, col, this.board); }
            else if (this.currentPlayer == 1) { worker.placeTower(row, col, this.board); }
            this.currentPlayer = (this.currentPlayer + 1) % 2;
        }
    }

    /**
     * Checks if the game is over.
     */
    public void gameOver() {
        if (player1.getWorker1().getHeight() == WIN_HEIGHT ||
            player1.getWorker2().getHeight() == WIN_HEIGHT ||
            player2.getWorker1().getHeight() == WIN_HEIGHT ||
            player2.getWorker2().getHeight() == WIN_HEIGHT) {
            this.gameOver = true;
        }
        if (player1.getWorker1().getHeight() == WIN_HEIGHT ||
            player1.getWorker2().getHeight() == WIN_HEIGHT) {
            this.winner = player1;
        } if (player2.getWorker1().getHeight() == WIN_HEIGHT ||
              player2.getWorker2().getHeight() == WIN_HEIGHT) {
            this.winner = player2;
        }
    }

}
