package com.example;

public interface GodCard {
    int NUM_ROWS = 5;
    int NUM_COLS = 5;
    int WIN_HEIGHT = 3;

    /**
     *  Checks if the row and col has a player on it.
     *
     * @param row the row to check.
     * @param col the col to check.
     * @param board the game board.
     */
    default void playerCheck(int row, int col, Cell[][] board) throws InvalidMoveException {
        Cell cell = board[row][col];
        if (cell.occupancy()) { throw new InvalidMoveException("Location has a worker on it!"); }
    }

    /**
     * Checks if the row and col is a legal move on the board.
     *
     * @param row the row to check.
     * @param col the col to check.
     * @param board the game board.
     */
    default void basicLegalChecks(int row, int col, Cell[][] board) throws InvalidMoveException {
        if (row < 0 || row > NUM_ROWS - 1 ||
            col < 0 || col > NUM_COLS - 1) {
            throw new InvalidMoveException("Out of bounds!");
        }
        Cell cell = board[row][col];
        if (cell.hasDome()) { throw new InvalidMoveException("Location has a dome on it!"); }
    }

    /**
     * Checks if the row and col is a legal move for the worker.
     *
     * @param row the row to check.
     * @param col the col to check.
     * @param worker the worker we want to move.
     * @param board the game board.
     * @return {@code true} if row, col is a legal move for the worker.
     */
    default boolean checkLegalMove(int row, int col, Worker worker, Cell[][] board) throws InvalidMoveException {
        basicLegalChecks(row, col, board); // Will throw an error if not legal row, col
        playerCheck(row, col, board);
        int originalRow = worker.getRow();
        int originalCol = worker.getCol();
        int originalHeight = worker.getHeight();
        Cell cell = board[row][col];

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
     * @param board the game board.
     * @return {@code true} if row, col is a legal place for a tower.
     */
    default boolean checkLegalPlacement(int row, int col, Worker worker, Cell[][] board) throws InvalidMoveException {
        basicLegalChecks(row, col, board); // Will throw an error if not legal row, col
        playerCheck(row, col, board);
        int originalRow = worker.getRow();
        int originalCol = worker.getCol();

        if (-1 > originalRow - row || originalRow - row > 1 ||
                -1 > originalCol - col || originalCol - col > 1) {
            throw new InvalidMoveException("Build has to be adjacent from current worker!");
        }
        return true;
    }

    /**
     * Checks if the cell already has a worker in it.
     *
     * @param position the position we want to move our worker to.
     * @param worker the worker we want to move.
     * @param player the current player.
     * @param board the game board.
     */
    default void initiateMove(Cell position, Worker worker, Player player, Cell[][] board) throws InvalidMoveException, InvalidTurnException {
        int row = position.getRow();
        int col = position.getCol();
        if (checkLegalMove(row, col, worker, board)) {
            if (player.getWorker1() == worker || player.getWorker2() == worker) { // Player is guaranteed to be the currPlayer
                worker.setPrevHeight(worker.getHeight());
                board[worker.getRow()][worker.getCol()].setWorker(null);
                worker.moveWorker(row, col, board);
                board[row][col].setWorker(worker);
            }
        }
        worker.setForced(false); // Current worker just moved, so they have not been forced to their new position
    }

    /**
     * Checks if the cell already has a worker in it.
     *
     * @param tower the tower we want to place on the board.
     * @param worker the worker that recently moved.
     * @param board the game board.
     * @param state the current state of a player's turn
     */
    default void initiateTower(Cell tower, Worker worker, Cell[][] board, String state) throws InvalidMoveException {
        int row = tower.getRow();
        int col = tower.getCol();
        basicLegalChecks(row, col, board);
        playerCheck(row, col, board);
        if (checkLegalPlacement(row, col, worker, board)) {
            worker.placeTower(row, col, board);
        }
    }

    /**
     * Checks if the game is over.
     *
     * @param player the current player.
     * @return {@code true} if player has any workers at win height.
     */
    default boolean gameOver(Player player) { // Player is guaranteed to be the currPlayer
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        if ((worker1.getHeight() == WIN_HEIGHT && !worker1.isForced()) ||
            (worker2.getHeight() == WIN_HEIGHT && !worker2.isForced())) {
            return true;
        }
        return false;
    }

}
