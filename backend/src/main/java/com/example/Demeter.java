package com.example;

public class Demeter extends Game {

    public Demeter(int numRows, int numCols) {
        super(numRows, numCols);
    }

    /**
     * Build an additional tower but not on the same space. Has the option to pass (UI).
     *
     * @param towers the towers we want to place on the board.
     * @param worker the worker we moved.
     */
    @Override
    public void initiateTower(Cell[] towers, Worker worker) throws InvalidMoveException, GameOverException {
        if (getGameOver()) throw new GameOverException("Game is over!");

        // First tower legality checks and placement
        Cell firstTower = towers[0];
        checkLegalPlacement(firstTower.getRow(), firstTower.getCol(), worker);
        worker.placeTower(firstTower.getRow(), firstTower.getCol(), getBoard());

        // Second tower legality checks and placement
        if (towers.length > 1) { // Guarantee no indexing out of bounds
            Cell secondTower = towers[1];
            checkLegalPlacement(secondTower.getRow(), secondTower.getCol(), worker);
            if (firstTower.getRow() != secondTower.getRow() ||
                firstTower.getCol() != secondTower.getCol()) {
                worker.placeTower(secondTower.getRow(), secondTower.getCol(), getBoard());
            }
        }
    }

}
