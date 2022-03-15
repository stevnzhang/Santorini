package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntegrationTest {

    private Game game;
    private Cell[][] board;

    @Before
    public void setUp() {
        game = new Game(5, 5);
        board = game.getBoard();
        game.setPlayer1(new Player());
        game.setPlayer2(new Player());
    }

    @Test
    public void integrationTest() throws InvalidMoveException, InvalidTurnException, GameOverException {
        game.initiatePlayer(0, 0, 2, 2, game.getPlayer1());
        game.initiatePlayer(1, 2, 1, 0, game.getPlayer2());
        Worker worker1 = game.getPlayer1().getWorker1();
        Worker worker2 = game.getPlayer1().getWorker2();
        Worker worker3 = game.getPlayer2().getWorker1();
        Worker worker4 = game.getPlayer2().getWorker2();

        // Building the 3 level tower
        game.initiateMove(0, 1, worker1, game.getPlayer1());
        game.initiateTower(1, 1, worker1);
        assertEquals(1, board[1][1].getLevels());

        game.initiateMove(2, 1, worker3, game.getPlayer2());
        game.initiateTower(1, 1, worker3);
        assertEquals(2, board[1][1].getLevels());

        game.initiateMove(1, 2, worker2, game.getPlayer1());
        game.initiateTower(1, 1, worker2);
        assertEquals(3, board[1][1].getLevels());

        // Building the 2 level tower
        game.initiateMove(2, 0, worker4, game.getPlayer2());
        game.initiateTower(1, 0, worker4);
        assertEquals(1, board[1][0].getLevels());

        game.initiateMove(0, 0, worker1, game.getPlayer1());
        game.initiateTower(1, 0, worker1);
        assertEquals(2, board[1][0].getLevels());

        // Filler turn
        game.initiateMove(2, 2, worker3, game.getPlayer2());
        game.initiateTower(3, 3, worker3);

        // Building the 1 level tower
        game.initiateMove(0, 2, worker2, game.getPlayer1());
        game.initiateTower(0, 1, worker2);

        // Moving worker1 to winning condition (row, col = 1, 1)
        game.initiateMove(3, 1, worker4, game.getPlayer2());
        game.initiateTower(4, 1, worker4);

        game.initiateMove(0, 1, worker1, game.getPlayer1());
        game.initiateTower(0, 0, worker1);

        game.initiateMove(1, 2, worker3, game.getPlayer2());
        game.initiateTower(1, 3, worker3);

        game.initiateMove(1, 0, worker1, game.getPlayer1());
        game.initiateTower(0, 0, worker1);

        game.initiateMove(0, 1, worker3, game.getPlayer2());
        game.initiateTower(0, 0, worker3);

        game.initiateMove(1, 1, worker1, game.getPlayer1());

//        assertTrue(board[0][0].hasDome());
        assertTrue(game.getGameOver());
        assertEquals(game.getPlayer1(), game.getWinner());
    }

}
