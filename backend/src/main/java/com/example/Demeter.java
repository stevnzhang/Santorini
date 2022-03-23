package com.example;

public class Demeter implements GodCard {


    /**
     * Build an additional tower but not on the same space. Has the option to pass (UI).
     *
     * @param tower the tower we want to place on the board.
     * @param worker the worker we moved.
     * @param board the game board.
     */
    @Override
    public void initiateTower(Cell tower, Worker worker, Cell[][] board) throws InvalidMoveException {
        // First tower legality checks and placement
        Cell firstTower = tower;
        if (checkLegalPlacement(firstTower.getRow(), firstTower.getCol(), worker, board)) {
            worker.placeTower(firstTower.getRow(), firstTower.getCol(), board);
        }

//        // Second tower legality checks and placement
//        if (towers.length > 1) { // Guarantee no indexing out of bounds
//            Cell secondTower = towers[1];
//            checkLegalPlacement(secondTower.getRow(), secondTower.getCol(), worker, board);
//            if (firstTower.getRow() != secondTower.getRow() ||
//                firstTower.getCol() != secondTower.getCol()) {
//                worker.placeTower(secondTower.getRow(), secondTower.getCol(), board);
//            }
//        }
    }

}
