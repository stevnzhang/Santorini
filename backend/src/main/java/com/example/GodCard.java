package com.example;

public interface GodCard {

    void basicLegalChecks(int row, int col) throws InvalidMoveException;

    boolean checkLegalMove(int row, int col, Worker worker) throws InvalidMoveException;

    boolean checkLegalPlacement(int row, int col, Worker worker) throws InvalidMoveException;

    void initiatePlayer(int row1, int col1, int row2, int col2, Player player) throws InvalidMoveException;

    void initiateMove(Cell[] positions, Worker worker, Player player) throws InvalidMoveException, GameOverException, InvalidTurnException;

    void initiateTower(Cell[] towers, Worker worker) throws InvalidMoveException, GameOverException;

    void gameOver();

}
