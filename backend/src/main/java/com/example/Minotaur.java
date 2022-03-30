package com.example;

public class Minotaur implements GodCard {

    // Helper function to find the cell the opponent worker will be pushed to.
    private Cell minotaurHelper(Cell src, Cell tgt) {
        int srcRow = src.getRow();
        int srcCol = src.getCol();
        int tgtRow = tgt.getRow();
        int tgtCol = tgt.getCol();
        int behindRow = tgtRow;
        int behindCol = tgtCol;

        if (srcRow < tgtRow)
            behindRow += 1;
        else if (srcRow > tgtRow)
            behindRow -= 1;

        if (srcCol < tgtCol)
            behindCol += 1;
        else if (srcCol > tgtCol)
            behindCol -= 1;

        return new Cell(behindRow, behindCol);
    }

    private void updateBoard(int row, int col, Worker worker, Cell[][] board) {
        int originalRow = worker.getRow();
        int originalCol = worker.getCol();
        // Make Move
        board[originalRow][originalCol].setWorker(null);
        board[originalRow][originalCol].setUnoccupied();
        board[row][col].setWorker(worker);
        board[row][col].setOccupied();
        // Update Worker
        worker.setRow(row);
        worker.setCol(col);
        worker.setHeight(board[row][col].getLevels());
    }

    // Helper function that moves the workers and updates the board state (To make code more readable and reusable)
    private void pushWorkerHelper(Cell src, Cell tgt, Worker worker, Cell[][] board) {
        int row = tgt.getRow();
        int col = tgt.getCol();
        Cell behind = minotaurHelper(src, tgt);
        Worker opponentWorker = tgt.getWorker();

        board[opponentWorker.getRow()][opponentWorker.getCol()].setWorker(null);
        updateBoard(behind.getRow(), behind.getCol(), opponentWorker, board);
        board[behind.getRow()][behind.getCol()].setWorker(opponentWorker);
        opponentWorker.setForced(true);

        board[worker.getRow()][worker.getCol()].setWorker(null);
        updateBoard(tgt.getRow(), tgt.getCol(), worker, board);
        board[row][col].setWorker(worker);
    }

    /**
     * Worker may move into an opponent worker's space if their worker can be forced one space
     * straight backwards to an unoccupied space of any level. Can only force opponents to move
     * and opponents that are forced onto a 3-level tower do not win.
     *
     * @param position the position we want to move our worker to.
     * @param worker the worker we want to move.
     * @param player the current player.
     * @param board the game board.
     */
    @Override
    public void initiateMove(Cell position, Worker worker, Player player, Cell[][] board) throws InvalidMoveException {
        // Initial variables and legality checks
        int row = position.getRow();
        int col = position.getCol();
        int originalRow = worker.getRow();
        int originalCol = worker.getCol();
        Cell src = board[originalRow][originalCol];
        Cell tgt = board[row][col];
        Cell behind = minotaurHelper(src, tgt);
        Worker opponentWorker = tgt.getWorker();

        if (!tgt.occupancy()) { // Default move - No pushing
            playerCheck(row, col, board); // Throw error is trying to move to an occupied cell
            if (checkLegalMove(row, col, worker, board)) {
                if (player.getWorker1() == worker || player.getWorker2() == worker) {
                    updateBoard(tgt.getRow(), tgt.getCol(), worker, board);
                }
            }
        } else {
            // Target cell cannot be out of bounds or have a dome on it, but it can have a worker on it
            checkLegalMove(row, col, worker, board);

            // Player cannot push its own worker
            Worker samePlayerOtherWorker = (worker == player.getWorker1() ? player.getWorker2() : player.getWorker1());
            if (samePlayerOtherWorker.getRow() == tgt.getRow() && samePlayerOtherWorker.getCol() == tgt.getCol()) {
                throw new InvalidMoveException("Cannot push your own worker!");
            }

            // Behind cell cannot be out of bounds, have a dome, or have another player on it
            basicLegalChecks(behind.getRow(), behind.getCol(), board); // Throws error and end game if illegal move, otherwise continue (doesn't check dome)
            playerCheck(behind.getRow(), behind.getCol(), board);

            // Actually moving the workers on the board and updating the board state
            pushWorkerHelper(src, tgt, worker, board);
        }

    }

}
