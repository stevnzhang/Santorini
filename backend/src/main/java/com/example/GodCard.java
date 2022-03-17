package com.example;

public class GodCard {

    // Avoid coupling, use game.inititateTower instead of worker.placeTower
    // Create an interface/abstract class (pretty much just game class) that implements the existing moves and placing towers
    // Create additional god cards that implement the interface/abstract class that override move, tower, win
    // Player has a GodCard gc which we check at the beginning of each game (similar to isInstance in HW4)

    /**
     * Build an additional tower but not on the same space. Has the option to pass (UI).
     *
     * @param firstTower the cell that the first tower was built in.
     * @param secondTower the cell that the player wants to build their additional tower in.
     * @param worker the worker we moved.
     * @param game the current game.
     */
    public void demeter(Cell firstTower, Cell secondTower, Worker worker, Game game) throws InvalidMoveException, GameOverException {
        game.checkLegalPlacement(firstTower.getRow(), firstTower.getCol(), worker);
        if (firstTower.getRow() != secondTower.getRow() ||
            firstTower.getCol() != secondTower.getCol()) {
            game.initiateTower(secondTower.getRow(), secondTower.getCol(), worker);
        }
    }

    // Helper function to find the cell the opponent worker will be pushed to.
    private Cell minotaurHelper(Cell src, Cell tgt, Game game) {
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

        return game.getBoard()[behindRow][behindCol];
    }

    /** Worker may move into an opponent worker's space if their worker can be forced one space
     *  straight backwards to an unoccupied space of any level. Can only force opponents to move
     *  and opponents that are forced onto a 3-level tower do not win.
     *
     *  @param row the row we want to move the worker to.
     *  @param col the col we want to move the worker to.
     *  @param worker the worker we want to move.
     *  @param game the current game.
     */
    public void minotaur(int row, int col, Worker worker, Game game) throws InvalidMoveException, InvalidTurnException, GameOverException {
        // Initial variables and legality checks
        int originalRow = worker.getRow();
        int originalCol = worker.getCol();
        Cell src = game.getBoard()[originalRow][originalCol];
        Cell tgt = game.getBoard()[row][col];
        Cell behind = minotaurHelper(src, tgt, game);
        game.basicLegalChecks(behind.getRow(), behind.getCol()); // Will throw an error and end game if illegal move, otherwise continue

        // Finding the opponent's worker
        Player currPlayer = game.getCurrentPlayer() == 0 ? game.getPlayer1() : game.getPlayer2();
        Player opponentPlayer = game.getCurrentPlayer() == 0 ? game.getPlayer2() : game.getPlayer1();
        Worker opponentWorker;
        if (opponentPlayer.getWorker1().getRow() == tgt.getRow() &&
            opponentPlayer.getWorker1().getCol() == tgt.getCol()) {
            opponentWorker = opponentPlayer.getWorker1();
        } else if (opponentPlayer.getWorker2().getRow() == tgt.getRow() &&
                 opponentPlayer.getWorker2().getCol() == tgt.getCol()) {
                 opponentWorker = opponentPlayer.getWorker2();
        } else { return; }

        // Actually moving the workers on the board
        game.initiateMove(behind.getRow(), behind.getCol(), opponentWorker, opponentPlayer);
        game.initiateMove(tgt.getRow(), tgt.getCol(), worker, currPlayer);
    }

    /**
     * Win if worker moves down 2 or more levels
     *
     * @param row the row we want to move the worker to.
     * @param col the col we want to move the worker to.
     * @param worker the worker we want to move.
     * @param game the current game.
     */
    public void pan(int row, int col, Worker worker, Game game) throws InvalidMoveException {
        // Make sure that the moves are legal
        game.checkLegalMove(row, col, worker);
        int originalHeight = worker.getHeight();
        Cell tgtCell = game.getBoard()[row][col];
        Player currPlayer = game.getCurrentPlayer() == 0 ? game.getPlayer1() : game.getPlayer2();
        if (originalHeight - tgtCell.getLevels() >= 2) {
            game.setWinner(currPlayer);
            game.setGameOver(true);
        }
    }

}