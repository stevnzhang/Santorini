package com.example;

public class Minotaur implements GodCard {

    // Helper function to find the cell the opponent worker will be pushed to.
    private Cell minotaurHelper(Cell src, Cell tgt, Cell[][] board) {
        int srcRow = src.getRow();
        int tgtRow = tgt.getRow();
        int srcCol = src.getCol();
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
            behindCol -= tgtCol - 1;

        return board[behindRow][behindCol];
    }

    // Helper function that moves the workers and updates the board state (To make code more readable and reusable)
    private void pushWorkerHelper(Cell src, Cell tgt, Worker worker, Cell[][] board) {
        int row = tgt.getRow();
        int col = tgt.getCol();
        Cell behind = minotaurHelper(src, tgt, board);
        Worker opponentWorker = tgt.getWorker();

        board[opponentWorker.getRow()][opponentWorker.getCol()].setWorker(null);
        opponentWorker.moveWorker(behind.getRow(), behind.getCol(), board);
        board[behind.getRow()][behind.getCol()].setWorker(opponentWorker);

        board[worker.getRow()][worker.getCol()].setWorker(null);
        worker.moveWorker(tgt.getRow(), tgt.getCol(), board);
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
    public void initiateMove(Cell position, Worker worker, Player player, Cell[][] board) throws InvalidMoveException, InvalidTurnException {
        // Initial variables and legality checks
        int row = position.getRow();
        int col = position.getCol();
        int originalRow = worker.getRow();
        int originalCol = worker.getCol();
        Cell src = board[originalRow][originalCol];
        Cell tgt = board[row][col];
        Cell behind = minotaurHelper(src, tgt, board);
        Worker opponentWorker = tgt.getWorker();

        if (!tgt.occupancy()) { // Default move - No pushing
            if (checkLegalMove(row, col, worker, board)) {
                if (player.getWorker1() == worker || player.getWorker2() == worker) {
//                    worker.setPrevHeight(worker.getHeight()); Don't need this because we don't need to track dy for pan's power
                    worker.moveWorker(row, col, board);
                    board[row][col].setWorker(worker);
                }
            }
//            worker.setForced(false); Don't need this because we don't have Pan card
        } else {
            // Target cell cannot be out of bounds or have a dome on it, but it can have a worker on it
            basicLegalChecks(row, col, board);

            // Player cannot push its own worker
            if (row == opponentWorker.getRow() && col == opponentWorker.getCol()) { throw new InvalidMoveException("Cannot push your own worker!"); }

            // Behind cell cannot be out of bounds, have a dome, or have another player on it
            basicLegalChecks(behind.getRow(), behind.getCol(), board); // Throws error and end game if illegal move, otherwise continue (doesn't check dome)

            // Actually moving the workers on the board and updating the board state
            pushWorkerHelper(src, tgt, worker, board);
        }

    }

}
