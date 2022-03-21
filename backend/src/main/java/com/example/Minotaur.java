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

    /**
     * Worker may move into an opponent worker's space if their worker can be forced one space
     * straight backwards to an unoccupied space of any level. Can only force opponents to move
     * and opponents that are forced onto a 3-level tower do not win.
     *
     * @param positions the positions we want to move our worker to.
     * @param worker the worker we want to move.
     * @param player the current player.
     * @param board the game board.
     */
    @Override
    public void initiateMove(Cell[] positions, Worker worker, Player player, Cell[][] board) throws InvalidMoveException, InvalidTurnException, GameOverException {
        // Initial variables and legality checks
        if (getGameOver()) { throw new GameOverException("Game is over!"); }
        int row = positions[0].getRow();
        int col = positions[0].getCol();
        int originalRow = worker.getRow();
        int originalCol = worker.getCol();
        Cell src = board[originalRow][originalCol];
        Cell tgt = board[row][col];
        Cell behind = minotaurHelper(src, tgt, board);
        basicLegalChecks(behind.getRow(), behind.getCol(), board); // Will throw an error and end game if illegal move, otherwise continue

        // Finding the opponent's worker
        Player opponentPlayer = getCurrentPlayer() == 0 ? getPlayer2() : getPlayer1();
        Worker opponentWorker;
        if (opponentPlayer.getWorker1().getRow() == tgt.getRow() &&
                opponentPlayer.getWorker1().getCol() == tgt.getCol()) {
            opponentWorker = opponentPlayer.getWorker1();
        } else if (opponentPlayer.getWorker2().getRow() == tgt.getRow() &&
                opponentPlayer.getWorker2().getCol() == tgt.getCol()) {
            opponentWorker = opponentPlayer.getWorker2();
        } else { return; }

        // Actually moving the workers on the board
        opponentWorker.moveWorker(behind.getRow(), behind.getCol(), board);
        worker.moveWorker(tgt.getRow(), tgt.getCol(), board);
    }

}