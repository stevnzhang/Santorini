package com.example;

public class Demeter implements GodCard {


    /**
     * Build an additional tower but not on the same space. Has the option to pass (UI).
     *
     * @param tower the tower we want to place on the board.
     * @param worker the worker we moved.
     * @param board the game board.
     * @param state the current state of a player's turn
     */
    @Override
    public void initiateTower(Cell tower, Worker worker, Cell[][] board, String state) throws InvalidMoveException {
        int tRow = tower.getRow();
        int tCol = tower.getCol();

        if (board[tRow][tCol].getJustPlaced()) { throw new InvalidMoveException("Cannot place tower on first placed tower!"); }

        if (checkLegalPlacement(tRow, tCol, worker, board)) {
            if (state == "build") {
                board[tRow][tCol].addLevel();
            } else if (state == "second build") {
                board[tRow][tCol].addLevel();
            }
            board[tRow][tCol].setJustPlaced(true);
        }
    }

}
