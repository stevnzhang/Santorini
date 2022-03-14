package com.example;

public class GodCard {

    /** Build an additional tower but not on the same space. Has the option to pass (UI). **/
    public void Demeter(int row, int col, Worker worker, Game game) {

    }

    private Cell MinotaurHelper(Cell src, Cell tgt, Game game) {
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
     *  straight backwards to an unoccupied space of any level. **/
    public void Minotaur(int row, int col, Worker worker, Game game) throws InvalidMoveException {
        // Initial variables and legality checks
        int originalRow = worker.getRow();
        int originalCol = worker.getCol();
        Cell src = game.getBoard()[originalRow][originalCol];
        Cell tgt = game.getBoard()[row][col];
        Cell behind = MinotaurHelper(src, tgt, game);
        game.basicLegalChecks(behind.getRow(), behind.getCol()); // Will throw an error and end game if illegal move, otherwise:

        // Finding the opponent's worker
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
        opponentWorker.moveWorker(behind.getRow(), behind.getCol(), game.getBoard());
        worker.moveWorker(tgt.getRow(), tgt.getCol(), game.getBoard());

    }

    /** Win if worker moves down 2+ levels **/
    public void Pan(int row, int col, Worker worker, Game game) {

    }

}