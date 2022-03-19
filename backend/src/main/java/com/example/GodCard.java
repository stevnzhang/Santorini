package com.example;

public interface GodCard {

    // default allows god card interface to act like an abstract class
    // move over game implementation to god card
    // instead of extends game, we should extend the god card class
    // change Demeter to overriding a getNumMoves function that tells initiateMove how many times to loop (reduces coupling)

    default void basicLegalChecks(int row, int col) throws InvalidMoveException { return; }


    boolean checkLegalMove(int row, int col, Worker worker) throws InvalidMoveException;

    boolean checkLegalPlacement(int row, int col, Worker worker) throws InvalidMoveException;

    void initiatePlayer(int row1, int col1, int row2, int col2, Player player) throws InvalidMoveException;

    void initiateMove(Cell[] positions, Worker worker, Player player) throws InvalidMoveException, GameOverException, InvalidTurnException;

    void initiateTower(Cell[] towers, Worker worker) throws InvalidMoveException, GameOverException;

    void gameOver();

}
